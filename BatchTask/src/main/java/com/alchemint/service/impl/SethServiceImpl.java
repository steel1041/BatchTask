package com.alchemint.service.impl;

import com.alchemint.bo.CommOperation;
import com.alchemint.constants.Constans;
import com.alchemint.constants.Scenario;
import com.alchemint.contract.SDUSD;
import com.alchemint.contract.SETH;
import com.alchemint.dao.CommOperationDaoI;
import com.alchemint.service.SethServiceI;
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
public class SethServiceImpl extends BaseServiceImpl implements SethServiceI {

    public final static Logger logger = LoggerFactory.getLogger(SethServiceImpl.class);

    @Autowired
    private CommOperationDaoI operationDaoI;

    /**
     * 开始执行seth合约事件
     *
     * @return void
     **/
    @Override
    public void depositEventProcess() {
        logger.info("***start depositEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SETH seth = SETH.load(SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SETH.DepositEventResponse> flowable = seth.depositEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SETH.DepositEventResponse>() {
                @Override
                public void accept(SETH.DepositEventResponse event) {
                    Log log = event.log;
                    String from = event.dst;
                    BigInteger value = event.wad;
                    CommOperation comm = new CommOperation();
                    comm.setFrom(from);
                    comm.setType(Constans.SETH_EVENT_DEPOSIT);
                    comm.setAsset(SETH_CONTRACT_ADDRESS);
                    comm.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    comm.setBlockindex(log.getBlockNumber().longValue());
                    comm.setTxid(log.getTransactionHash());
                    comm.setN(log.getTransactionIndex().intValue());
                    comm.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationDaoI.saveCommOperation(comm);
                    logger.info(from + "/" + value);
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
     * 开始执行seth合约事件
     *
     * @return void
     **/
    @Override
    public void withdrawEventProcess() {
        logger.info("***start withdrawEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SETH seth = SETH.load(SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SETH.WithdrawalEventResponse> flowable = seth.withdrawalEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SETH.WithdrawalEventResponse>() {
                @Override
                public void accept(SETH.WithdrawalEventResponse event) {
                    Log log = event.log;
                    String from = event.src;
                    BigInteger value = event.wad;
                    CommOperation comm = new CommOperation();
                    comm.setFrom(from);
                    comm.setType(Constans.SETH_EVENT_WITHDRAW);
                    comm.setAsset(SETH_CONTRACT_ADDRESS);
                    comm.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    comm.setBlockindex(log.getBlockNumber().longValue());
                    comm.setTxid(log.getTransactionHash());
                    comm.setN(log.getTransactionIndex().intValue());
                    comm.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationDaoI.saveCommOperation(comm);
                    logger.info(from + "/" + value);
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
     * 开始执行sdusd合约事件
     *
     * @return void
     **/
    @Override
    public void mintEventProcess() {
        logger.info("***start mintEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SDUSD sdusdToken = SDUSD.load(SDUSD_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SDUSD.MintEventResponse> flowable = sdusdToken.mintEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SDUSD.MintEventResponse>() {
                @Override
                public void accept(SDUSD.MintEventResponse event) {
                    Log log = event.log;
                    String from = event.guy;
                    BigInteger value = event.wad;
                    CommOperation comm = new CommOperation();
                    comm.setFrom(from);
                    comm.setType(Constans.SDUSD_EVENT_MINT);
                    comm.setAsset(SDUSD_CONTRACT_ADDRESS);
                    comm.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    comm.setBlockindex(log.getBlockNumber().longValue());
                    comm.setTxid(log.getTransactionHash());
                    comm.setN(log.getTransactionIndex().intValue());
                    comm.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationDaoI.saveCommOperation(comm);
                    logger.info(from + "/" + value);
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
     * 开始执行sdusd合约事件
     *
     * @return void
     **/
    @Override
    public void burnEventProcess() {
        logger.info("***start burnEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(WALLET_PASSWORD, ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SDUSD sdusdToken = SDUSD.load(SDUSD_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SDUSD.BurnEventResponse> flowable = sdusdToken.burnEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SDUSD.BurnEventResponse>() {
                @Override
                public void accept(SDUSD.BurnEventResponse event) {
                    Log log = event.log;
                    String from = event.guy;
                    BigInteger value = event.wad;
                    CommOperation comm = new CommOperation();
                    comm.setFrom(from);
                    comm.setType(Constans.SDUSD_EVENT_BURN);
                    comm.setAsset(SDUSD_CONTRACT_ADDRESS);
                    comm.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    comm.setBlockindex(log.getBlockNumber().longValue());
                    comm.setTxid(log.getTransactionHash());
                    comm.setN(log.getTransactionIndex().intValue());
                    comm.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    operationDaoI.saveCommOperation(comm);
                    logger.info(from + "/" + value);
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
