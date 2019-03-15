package com.test.service;

import com.alchemint.contract.SDUSDToken;
import org.junit.Test;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;

/**
 * Created by cheng on 2019/3/1.
 */
public class SDUSDContractTest extends Scenario {


    @Test
    public void callTest() throws Exception{
        SDUSDToken sdusdToken = SDUSDToken.load(SDUSD_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

        RemoteCall<BigInteger> totalSupplyCall = sdusdToken.totalSupply();
        BigInteger totalSupply = totalSupplyCall.send();
        logger.info("totalSupply:"+totalSupply);

        //[2]SDUSD余额
        RemoteCall<BigInteger> balanceOfCall = sdusdToken.balanceOf(ALICE.getAddress());
        logger.info(Convert.fromWei(balanceOfCall.send().toString(),Convert.Unit.ETHER).toString());

    }


    private void totalSupply(SDUSDToken sdusdToken) throws Exception{
        RemoteCall<BigInteger> totalSupplyCall = sdusdToken.totalSupply();
        BigInteger totalSupply = totalSupplyCall.send();
        logger.info("totalSupply:"+Convert.fromWei(totalSupply.toString(),Convert.Unit.ETHER).toString());
    }

    private void balanceOf(SDUSDToken sdusdToken) throws Exception{
        //SDUSD余额
        RemoteCall<BigInteger> balanceOfCall = sdusdToken.balanceOf(ALICE.getAddress());
        logger.info("balanceOf:"+Convert.fromWei(balanceOfCall.send().toString(),Convert.Unit.ETHER).toString());
    }

    @Test
    public void authSDUSD() throws Exception{
        //ALICE授权给SAR合约操作金额
        SDUSDToken sdusdToken = SDUSDToken.load(SDUSD_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());
        TransactionReceipt receipt = sdusdToken.approve(SAR_CONTRACT_ADDRESS,Convert.toWei("1000",Convert.Unit.ETHER).toBigInteger()).send();
        logger.info(receipt.toString());

        BigInteger allowance = sdusdToken.allowance(ALICE.getAddress(),SAR_CONTRACT_ADDRESS).send();
        logger.info("allowance:"+Convert.fromWei(allowance.toString(),Convert.Unit.ETHER));
    }

}
