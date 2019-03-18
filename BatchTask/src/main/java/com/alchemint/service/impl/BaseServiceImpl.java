package com.alchemint.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by cheng on 2019/3/8.
 * @author steel
 */
public abstract class BaseServiceImpl  {

    public final static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Value("${alchemint.wallet.path.alice}")
    public String ALICE_KEY = "";

    @Value("${alchemint.rpc.http.url}")
    public String RPC = "";

    @Value("${alchemint.rpc.ws.url}")
    public String RPC_WS = "";

    @Value("${alchemint.hash.contract.oracle}")
    public String ORACLE_CONTRACT_ADDRESS = "";

    @Value("${alchemint.hash.contract.seth}")
    public String SETH_CONTRACT_ADDRESS = "";

    @Value("${alchemint.hash.contract.sdusd}")
    public String SDUSD_CONTRACT_ADDRESS = "";

    @Value("${alchemint.hash.contract.sar}")
    public String SAR_CONTRACT_ADDRESS = "";

    public String WALLET_PASSWORD = "123456";

    public BigInteger getLatestBlock(Admin web3j) throws Exception{
        Request<?, EthBlockNumber> blockNumberRequest = web3j.ethBlockNumber();
        BigInteger blockNumber = blockNumberRequest.send().getBlockNumber();
        logger.info("BlockNumber:"+blockNumber.toString());
        return blockNumber;
    }

    public Date getDateFromHash(Admin web3j,String blockHash){
        try {
            Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByHash(blockHash, true);
            EthBlock.Block block = ethBlockRequest.send().getBlock();
            return new Date(block.getTimestamp().longValue() * 1000);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new Date();
    }
}
