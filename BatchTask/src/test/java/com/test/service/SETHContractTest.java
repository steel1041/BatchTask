package com.test.service;

import com.alchemint.contract.SETH;
import io.reactivex.Flowable;
import org.junit.Test;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;

/**
 * Created by cheng on 2019/3/1.
 */
public class SETHContractTest extends Scenario {

    @Test
    public void sethBalance()throws Exception{
        Request<?, EthBlockNumber> blockNumberRequest = web3j.ethBlockNumber();
        BigInteger blockNumber = blockNumberRequest.send().getBlockNumber();
        logger.info("BlockNumber:"+blockNumber.toString());

        Request<?, EthGetBalance> balance = web3j.ethGetBalance(SETH_CONTRACT_ADDRESS, DefaultBlockParameter.valueOf(blockNumber));
        logger.info(Convert.fromWei(balance.send().getBalance().toString(),Convert.Unit.ETHER)+" ETH");
    }

    @Test
    public void callTest() throws Exception{
        SETH seth = SETH.load(SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

        //给SETH合约转账
        transfer(200.00);

        balanceOf(seth);

        //提现SETH
        //withdraw(seth,100.00);

        totalSupply(seth);

    }

    private void totalSupply(SETH seth) throws Exception{
        RemoteCall<BigInteger> totalSupplyCall = seth.totalSupply();
        BigInteger totalSupply = totalSupplyCall.send();
        logger.info("totalSupply:"+Convert.fromWei(totalSupply.toString(),Convert.Unit.ETHER).toString());
    }

    private void balanceOf(SETH seth) throws Exception{
        //SETH余额
        RemoteCall<BigInteger> balanceOfCall = seth.balanceOf(ALICE.getAddress());
        logger.info("balanceOf:"+Convert.fromWei(balanceOfCall.send().toString(),Convert.Unit.ETHER).toString());
    }

    private void transfer(double mount) throws Exception{
        Request<?, EthGasPrice> gasPriceReq = web3j.ethGasPrice();
        BigInteger gasPrice = gasPriceReq.send().getGasPrice();

        // get the next available nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                ALICE.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        BigDecimal weiValue = Convert.toWei(BigDecimal.valueOf(mount), Convert.Unit.ETHER);
        // create our transaction
        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                nonce,gasPrice,GAS_LIMIT,SETH_CONTRACT_ADDRESS,weiValue.toBigIntegerExact());

        // sign & send our transaction
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        logger.info(ethSendTransaction.getResult());
    }

    private void withdraw(SETH seth,double mount) throws Exception{
        RemoteCall<TransactionReceipt> withdrawCall = seth.withdraw(Convert.toWei(new BigDecimal(mount),Convert.Unit.ETHER).toBigInteger());
        TransactionReceipt transactionReceipt  =  withdrawCall.send();
        logger.info(transactionReceipt.toString());
    }

    @Test
    public void authSETH() throws Exception{
        //ALICE授权给SAR合约操作金额
        SETH seth = SETH.load(SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());
        TransactionReceipt receipt = seth.approve(SAR_CONTRACT_ADDRESS,Convert.toWei("10000",Convert.Unit.ETHER).toBigInteger()).send();
        logger.info(receipt.toString());

        BigInteger allowance = seth.allowance(ALICE.getAddress(),SAR_CONTRACT_ADDRESS).send();
        logger.info("allowance:"+Convert.fromWei(allowance.toString(),Convert.Unit.ETHER));
    }

    @Test
    public void eventLog() throws Exception{
        SETH seth = SETH.load(SETH_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

        Flowable<SETH.DepositEventResponse>  responseFlowable =
                seth.depositEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST);

        responseFlowable.blockingIterable().forEach(new Consumer<SETH.DepositEventResponse>() {
            @Override
            public void accept(SETH.DepositEventResponse depositEventResponse) {
                logger.info(depositEventResponse.dst+"/"+depositEventResponse.wad);

            }
        });
    }


    private static RawTransaction createSmartContractTransaction(BigInteger nonce)
            throws Exception {
        return RawTransaction.createContractTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, BigInteger.ZERO, getSETHSolidityBinary());
    }

    static String getSETHSolidityBinary() throws Exception {
        return load("/solidity/SETHToken.bin");
    }
}
