package com.alchemint.service.impl;

import com.alchemint.bo.Operation;
import com.alchemint.bo.SarInfo;
import com.alchemint.constants.Constans;
import com.alchemint.constants.Scenario;
import com.alchemint.contract.SarTub;
import com.alchemint.dao.OperationDaoI;
import com.alchemint.dao.SarInfoDaoI;
import com.alchemint.service.SarInfoServiceI;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Created by cheng on 2019/3/8.
 * @author steel
 */
@Service
public class SarInfoServiceImpl implements SarInfoServiceI {

    public final static Logger logger = LoggerFactory.getLogger(SarInfoServiceImpl.class);

    @Autowired
    OperationDaoI operationDaoI;

    @Autowired
    SarInfoDaoI sarInfoDaoI;

    @Override
    public void saveOperation(Log log, Operation operation) {
        operationDaoI.saveOperation(operation);
    }

    /**
     * 开始执行sar合约事件
     *
     * @return void
     **/
    @Override
    public void sarEventProcess() {
        logger.info("***start sarEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(Scenario.RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, Scenario.ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SarTub sar = SarTub.load(Scenario.SAR_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            //最新区块的前100块开始查询
            Flowable<SarTub.OperatedEventResponse> operatedEventResponseFlowable = sar.operatedEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);
            operatedEventResponseFlowable.blockingIterable().forEach(new Consumer<SarTub.OperatedEventResponse>() {
                @Override
                public void accept(SarTub.OperatedEventResponse event) {
                    Log log = event.log;
                    String from = event.from;
                    int type = event.opType.intValue();
                    BigInteger value = event.opValue;
                    Operation oper = new Operation();
                    oper.setAddr(from);
                    oper.setAsset(Scenario.SAR_CONTRACT_ADDRESS);
                    oper.setType(type);
                    oper.setValue(new BigDecimal(value));
                    oper.setBlockindex(log.getBlockNumber().longValue());
                    oper.setTxid(log.getTransactionHash());
                    if(type == Constans.OPER_TYPE_OPEN) {
                        oper.setSarTxid(log.getTransactionHash());
                    }else{
                        SarInfo info = sarInfoDaoI.findSarByAddr(from,Scenario.SAR_CONTRACT_ADDRESS);
                        oper.setSarTxid(info.getSarTxid());
                    }
                    oper.setN(log.getTransactionIndex().intValue());
                    oper.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationDaoI.saveOperation(oper);
                    if (type == Constans.OPER_TYPE_OPEN) {
                        SarInfo sar = new SarInfo();
                        sar.setAsset(Scenario.SAR_CONTRACT_ADDRESS);
                        sar.setN(log.getTransactionIndex().intValue());
                        sar.setSarTxid(log.getTransactionHash());
                        sar.setAddr(from);
                        sar.setBlockindex(log.getBlockNumber().longValue());
                        sar.setStatus(Constans.SAR_STATUS_NORMAL);
                        sar.setTime(getDateFromHash(web3j,log.getBlockHash()));
                        sarInfoDaoI.saveSarInfo(sar);
                    }
                    logger.info(from + "/" + type + "/" + value);
                }
            });
        }catch (CipherException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private BigInteger getLatestBlock(Admin web3j) throws Exception{
        Request<?, EthBlockNumber> blockNumberRequest = web3j.ethBlockNumber();
        BigInteger blockNumber = blockNumberRequest.send().getBlockNumber();
        logger.info("BlockNumber:"+blockNumber.toString());
        return blockNumber;
    }

    private Date getDateFromHash(Admin web3j,String blockHash) {
        try {
            Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByHash(blockHash, true);
            EthBlock.Block block = ethBlockRequest.send().getBlock();
            return new Date(block.getTimestamp().longValue() * 1000);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new Date();
    }
}
