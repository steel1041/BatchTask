package com.alchemint.service.impl;

import com.alchemint.bo.Approval;
import com.alchemint.bo.Transfer;
import com.alchemint.constants.Scenario;
import com.alchemint.contract.SDUSDToken;
import com.alchemint.contract.SETHToken;
import com.alchemint.dao.ApprovalDaoI;
import com.alchemint.dao.TransferDaoI;
import com.alchemint.service.TransferServiceI;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;

/**
 * Created by cheng on 2019/3/8.
 * @author steel
 */
@Service
public class TransferServiceImpl extends BaseServiceImpl implements TransferServiceI {

    public final static Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Autowired
    private TransferDaoI transferDaoI;

    @Autowired
    private ApprovalDaoI approvalDaoI;

    /**
     * 开始执行transfer合约事件
     *
     * @return void
     **/
    @Override
    public void transSETHEventProcess() {
        logger.info("***start transSETHEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(Scenario.RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, Scenario.ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SETHToken sethToken = SETHToken.load(Scenario.SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SETHToken.TransferEventResponse> flowable = sethToken.transferEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SETHToken.TransferEventResponse>() {
                @Override
                public void accept(SETHToken.TransferEventResponse event) {
                    Log log = event.log;
                    String from = event.src;
                    String to = event.dst;
                    BigInteger value = event.wad;
                    Transfer tran = new Transfer();
                    tran.setFrom(from);
                    tran.setTo(to);
                    tran.setAsset(Scenario.SETH_CONTRACT_ADDRESS);
                    tran.setValue(new BigDecimal(value));
                    tran.setBlockindex(log.getBlockNumber().longValue());
                    tran.setTxid(log.getTransactionHash());
                    tran.setN(log.getTransactionIndex().intValue());
                    tran.setTime(getDateFromHash(web3j,log.getBlockHash()));

                    transferDaoI.saveTransfer(tran);
                    logger.info(from + "/" + to + "/" + value);
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
     * 开始执行transfer合约事件
     *
     * @return void
     **/
    @Override
    public void transSDUSDEventProcess() {
        logger.info("***start transSDUSDEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(Scenario.RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, Scenario.ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SDUSDToken sdusd = SDUSDToken.load(Scenario.SDUSD_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SDUSDToken.TransferEventResponse> flowable = sdusd.transferEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SDUSDToken.TransferEventResponse>() {
                @Override
                public void accept(SDUSDToken.TransferEventResponse event) {
                    Log log = event.log;
                    String from = event.src;
                    String to = event.dst;
                    BigInteger value = event.wad;
                    Transfer tran = new Transfer();
                    tran.setFrom(from);
                    tran.setTo(to);
                    tran.setAsset(Scenario.SDUSD_CONTRACT_ADDRESS);
                    tran.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    tran.setBlockindex(log.getBlockNumber().longValue());
                    tran.setTxid(log.getTransactionHash());
                    tran.setN(log.getTransactionIndex().intValue());
                    tran.setTime(getDateFromHash(web3j,log.getBlockHash()));

                    transferDaoI.saveTransfer(tran);
                    logger.info(from + "/" + to + "/" + value);
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
     * 开始执行approve合约事件
     *
     * @return void
     **/
    @Override
    public void approveSETHEventProcess() {
        logger.info("***start approveSETHEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(Scenario.RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, Scenario.ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SETHToken sethToken = SETHToken.load(Scenario.SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SETHToken.ApprovalEventResponse> flowable = sethToken.approvalEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SETHToken.ApprovalEventResponse>() {
                @Override
                public void accept(SETHToken.ApprovalEventResponse event) {
                    Log log = event.log;
                    String from = event.src;
                    String to = event.guy;
                    BigInteger value = event.wad;

                    Approval approval = new Approval();
                    approval.setFrom(from);
                    approval.setTo(to);
                    approval.setAsset(Scenario.SETH_CONTRACT_ADDRESS);
                    approval.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    approval.setBlockindex(log.getBlockNumber().longValue());
                    approval.setTxid(log.getTransactionHash());
                    approval.setN(log.getTransactionIndex().intValue());
                    approval.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    approvalDaoI.saveApproval(approval);
                    logger.info(from + "/" + to + "/" + value);
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
     * 开始执行tapprove合约事件
     *
     * @return void
     **/
    @Override
    public void approveSDUSDEventProcess() {
        logger.info("***start approveSDUSDEventProcess***");
        try {
            Admin web3j = Admin.build(new HttpService(Scenario.RPC));
            logger.info("web3j:"+web3j.toString());
            Credentials ALICE = WalletUtils.loadCredentials(Scenario.WALLET_PASSWORD, Scenario.ALICE_KEY);

            logger.info("ALICE:"+ALICE.toString());
            SDUSDToken sdusdToken = SDUSDToken.load(Scenario.SDUSD_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

            Flowable<SDUSDToken.ApprovalEventResponse> flowable = sdusdToken.approvalEventFlowable(
                    DefaultBlockParameter.valueOf(getLatestBlock(web3j).subtract(new BigInteger("100"))),
                    DefaultBlockParameterName.LATEST);

            flowable.blockingIterable().forEach(new Consumer<SDUSDToken.ApprovalEventResponse>() {
                @Override
                public void accept(SDUSDToken.ApprovalEventResponse event) {
                    Log log = event.log;
                    String from = event.src;
                    String to = event.guy;
                    BigInteger value = event.wad;

                    Approval approval = new Approval();
                    approval.setFrom(from);
                    approval.setTo(to);
                    approval.setAsset(Scenario.SDUSD_CONTRACT_ADDRESS);
                    approval.setValue(Convert.fromWei(value.toString(), Convert.Unit.ETHER));
                    approval.setBlockindex(log.getBlockNumber().longValue());
                    approval.setTxid(log.getTransactionHash());
                    approval.setN(log.getTransactionIndex().intValue());
                    approval.setTime(getDateFromHash(web3j,log.getBlockHash()));
                    approvalDaoI.saveApproval(approval);
                    logger.info(from + "/" + to + "/" + value);
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
