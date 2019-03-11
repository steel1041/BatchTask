package com.alchemint.service.impl;

import com.alchemint.bo.OracleOperation;
import com.alchemint.constants.Scenario;
import com.alchemint.contract.Oracle;
import com.alchemint.dao.OracleOperationDaoI;
import com.alchemint.service.OracleServiceI;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;

/**
 * Created by cheng on 2019/3/8.
 * @author steel
 */
@Service
public class OracleServiceImpl extends BaseServiceImpl implements OracleServiceI {

    public final static Logger logger = LoggerFactory.getLogger(OracleServiceImpl.class);

    @Autowired
    private OracleOperationDaoI operationDaoI;


    /**
     * 开始执行oracle合约事件
     *
     * @return void
     **/
    @Override
    public void oracleEventProcess() {
        logger.info("***start oracleEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(Scenario.RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, Scenario.ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            Oracle oracle = Oracle.load(Scenario.ORACLE_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<Oracle.OracleOperatedEventResponse> flowable = oracle.oracleOperatedEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<Oracle.OracleOperatedEventResponse>() {
                @Override
                public void accept(Oracle.OracleOperatedEventResponse event) {
                    Log log = event.log;
                    String from = event.from;
                    String key = event.opType;
                    BigInteger value = event.opValue;
                    OracleOperation oper = new OracleOperation();
                    oper.setAddr(from);
                    oper.setAsset(Scenario.ORACLE_CONTRACT_ADDRESS);
                    oper.setKey(key);
                    oper.setValue(new BigDecimal(value));
                    oper.setBlockindex(log.getBlockNumber().longValue());
                    oper.setTxid(log.getTransactionHash());
                    oper.setN(log.getTransactionIndex().intValue());
                    oper.setTime(getDateFromHash(web3j,log.getBlockHash()));

                    operationDaoI.saveOracleOperation(oper);
                    logger.info(from + "/" + key + "/" + value);
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
