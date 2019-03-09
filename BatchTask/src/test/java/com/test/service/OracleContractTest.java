package com.test.service;

import com.alchemint.contract.Oracle;
import io.reactivex.Flowable;
import org.junit.Test;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by cheng on 2019/3/1.
 */
public class OracleContractTest extends Scenario {

    @Test
    public void testDeploySmartContract() throws Exception {
        unlockAccount();
        BigInteger nonce = getNonce(ALICE.getAddress());
        RawTransaction rawTransaction = createSmartContractTransaction(nonce);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction =
                web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();

        assertFalse(transactionHash.isEmpty());

        TransactionReceipt transactionReceipt =
                waitForTransactionReceipt(transactionHash);

        assertThat(transactionReceipt.getTransactionHash(), is(transactionHash));

        assertFalse("Contract execution ran out of gas",
                rawTransaction.getGasLimit().equals(transactionReceipt.getGasUsed()));

        logger.info("contractAddress:"+transactionReceipt.getContractAddress());
    }

    @Test
    public void callTest() throws Exception{
        Oracle oracle = Oracle.load(ORACLE_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());
        //auth(oracle);

        setConfig(oracle);

        setPrice(oracle);
    }

    private void auth(Oracle oracle) throws Exception{
        String authAddr = "0x4e74cba89c96e65c2582d0b481931117352169d7";
        RemoteCall<TransactionReceipt> receiptRemoteCall = oracle.setAuth(authAddr);
        TransactionReceipt receipt = receiptRemoteCall.send();
        logger.info("transactionReceipt:"+receipt.toString());

        RemoteCall<Boolean> authsCall = oracle.auths(authAddr);
        logger.info("auths:"+authsCall.send());
    }

    @Test
    public void getConfigAndPrice() throws Exception{
        Oracle oracle = Oracle.load(ORACLE_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());
        logger.info("debt_top_c:"+Convert.fromWei(oracle.getConfig("debt_top_c").send().toString(), Convert.Unit.ETHER));
        logger.info("liquidate_line_rate_c:"+oracle.getConfig("liquidate_line_rate_c").send());
        logger.info("liquidate_dis_rate_c:"+oracle.getConfig("liquidate_dis_rate_c").send());
        logger.info("fee_rate_c:"+oracle.getConfig("fee_rate_c").send());
        logger.info("liquidate_top_rate_c:"+oracle.getConfig("liquidate_top_rate_c").send());
        logger.info("eth_price:"+oracle.getPrice("eth_price").send());
    }

    private void setPrice(Oracle oracle)throws Exception{
        //200.00->price*100
        TransactionReceipt receipt = oracle.setPrice("eth_price",new BigInteger("20000")).send();
        logger.info(receipt.toString());

        logger.info("eth_price:"+oracle.getPrice("eth_price").send().toString());
    }

    private void setConfig(Oracle oracle)throws Exception{
        TransactionReceipt receipt = oracle.setConfig("debt_top_c", Convert.toWei("10000000000",Convert.Unit.ETHER).toBigInteger()).send();
        logger.info(receipt.toString());
        logger.info("debt_top_c:"+oracle.getConfig("debt_top_c").send());

        receipt = oracle.setConfig("liquidate_line_rate_c",new BigInteger("150")).send();
        logger.info(receipt.toString());
        logger.info("liquidate_line_rate_c:"+oracle.getConfig("liquidate_line_rate_c").send());

        receipt = oracle.setConfig("liquidate_dis_rate_c",new BigInteger("90")).send();
        logger.info(receipt.toString());
        logger.info("liquidate_dis_rate_c:"+oracle.getConfig("liquidate_dis_rate_c").send());

        receipt = oracle.setConfig("fee_rate_c",new BigInteger("50")).send();
        logger.info(receipt.toString());
        logger.info("fee_rate_c:"+oracle.getConfig("fee_rate_c").send());

        receipt = oracle.setConfig("liquidate_top_rate_c",new BigInteger("160")).send();
        logger.info(receipt.toString());
        logger.info("liquidate_top_rate_c:"+oracle.getConfig("liquidate_top_rate_c").send());
    }

    @Test
    public void getOracleLog()throws Exception{
        Oracle oracle = Oracle.load(ORACLE_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

        org.web3j.protocol.core.methods.request.EthFilter ethFilter =
                new org.web3j.protocol.core.methods.request.EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        CONTRACT_ADDRESS
                );

        Event event = new Event("OracleOperated",
                Arrays.asList(new TypeReference<Address>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }));

        String encodedEventSignature = EventEncoder.encode(event);
        ethFilter.addSingleTopic(encodedEventSignature);
        Flowable<Oracle.OracleOperatedEventResponse> responseFlowable = oracle.oracleOperatedEventFlowable(ethFilter);
        Iterable<Oracle.OracleOperatedEventResponse> iterable =  responseFlowable.blockingIterable();
        iterable.forEach(new Consumer<Oracle.OracleOperatedEventResponse>() {
            @Override
            public void accept(Oracle.OracleOperatedEventResponse oracleOperatedEventResponse) {
                logger.info(oracleOperatedEventResponse.toString());
            }
        });

    }


    @Test
    public void getOperatedLog()throws Exception{
        org.web3j.protocol.core.methods.request.EthFilter ethFilter =
                new org.web3j.protocol.core.methods.request.EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        CONTRACT_ADDRESS
                );

        Event event = new Event("OracleOperated",
                Arrays.asList(new TypeReference<Address>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }));

        String encodedEventSignature = EventEncoder.encode(event);
        ethFilter.addSingleTopic(encodedEventSignature);

        EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
        List<EthLog.LogResult> logs = ethLog.getLogs();
        logger.info("size:"+logs.size());

        for (EthLog.LogResult log:logs)
        {
            Log l = (Log) log.get();
            logger.info(l.toString());
            List<Type> types = FunctionReturnDecoder.decode(l.getData(), event.getNonIndexedParameters());

            Utf8String opType = (Utf8String)types.get(0);
            Uint256 opValue = (Uint256)types.get(1);
            logger.info(opType+"/"+opValue);
        }
    }



    private static RawTransaction createSmartContractTransaction(BigInteger nonce)
            throws Exception {
        return RawTransaction.createContractTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, BigInteger.ZERO, getOracleSolidityBinary());
    }

    static String getOracleSolidityBinary() throws Exception {
        return load("/solidity/Oracle.bin");
    }
}
