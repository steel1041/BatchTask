package com.alchemint.service.impl;

import com.alchemint.bo.Operation;
import com.alchemint.bo.OperationFee;
import com.alchemint.bo.SarInfo;
import com.alchemint.constants.Constans;
import com.alchemint.constants.Scenario;
import com.alchemint.contract.SAR;
import com.alchemint.dao.OperationDaoI;
import com.alchemint.dao.OperationFeeDaoI;
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
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;
import java.util.function.Consumer;

/**
 * Created by cheng on 2019/3/8.
 * @author steel
 */
@Service
public class SarInfoServiceImpl extends BaseServiceImpl implements SarInfoServiceI {

    public final static Logger logger = LoggerFactory.getLogger(SarInfoServiceImpl.class);

    @Autowired
    private OperationDaoI operationDaoI;

    @Autowired
    private OperationFeeDaoI operationFeeDaoI;

    @Autowired
    private SarInfoDaoI sarInfoDaoI;

    /**
     * 开始执行sar合约事件
     *
     * @return void
     **/
    @Override
    public void sarEventProcess() {
        logger.info("***start sarEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(WALLET_PASSWORD, ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SAR sar = SAR.load(SAR_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            //最新区块的前100块开始查询
            Flowable<SAR.OperatedEventResponse> operatedEventResponseFlowable = sar.operatedEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);
            operatedEventResponseFlowable.blockingIterable().forEach(new Consumer<SAR.OperatedEventResponse>() {
                @Override
                public void accept(SAR.OperatedEventResponse event) {
                    Log log = event.log;
                    String from = event.from;
                    int type = event.opType.intValue();
                    BigInteger value = event.opValue;
                    Operation oper = new Operation();
                    oper.setAddr(from);
                    oper.setAsset(SAR_CONTRACT_ADDRESS);
                    oper.setType(type);
                    oper.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    oper.setBlockindex(log.getBlockNumber().longValue());
                    oper.setTxid(log.getTransactionHash());
                    if(type == Constans.OPER_TYPE_OPEN) {
                        oper.setSarTxid(log.getTransactionHash());
                    }else{
                        SarInfo info = sarInfoDaoI.findSarByAddr(from,SAR_CONTRACT_ADDRESS);
                        oper.setSarTxid(info.getSarTxid());
                    }
                    oper.setN(log.getTransactionIndex().intValue());
                    oper.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationDaoI.saveOperation(oper);
                    if (type == Constans.OPER_TYPE_OPEN) {
                        SarInfo sar = new SarInfo();
                        sar.setAsset(SAR_CONTRACT_ADDRESS);
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

    /**
     * 开始执行sar合约事件
     *
     * @return void
     **/
    @Override
    public void sarFeeEventProcess() {
        logger.info("***start sarFeeEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SAR sar = SAR.load(SAR_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            //最新区块的前100块开始查询
            Flowable<SAR.OperatedfeeEventResponse> operatedEventResponseFlowable = sar.operatedfeeEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);
            operatedEventResponseFlowable.blockingIterable().forEach(new Consumer<SAR.OperatedfeeEventResponse>() {
                @Override
                public void accept(SAR.OperatedfeeEventResponse event) {
                    Log log = event.log;
                    String from = event.from;
                    BigInteger fee = event.fee;

                    OperationFee oper = new OperationFee();
                    oper.setAddr(from);
                    oper.setAsset(SAR_CONTRACT_ADDRESS);
                    oper.setValue(Convert.fromWei(fee.toString(), Convert.Unit.ETHER));
                    oper.setBlockindex(log.getBlockNumber().longValue());
                    oper.setTxid(log.getTransactionHash());
                    SarInfo info = sarInfoDaoI.findSarByAddr(from,SAR_CONTRACT_ADDRESS);
                    oper.setSarTxid(info.getSarTxid());
                    oper.setN(log.getTransactionIndex().intValue());
                    oper.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationFeeDaoI.saveOperationFee(oper);
                    logger.info(from + "/" + fee);
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
}
