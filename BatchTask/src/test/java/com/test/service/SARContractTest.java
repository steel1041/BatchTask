package com.test.service;

import com.alchemint.bo.Operation;
import com.alchemint.bo.SarInfo;
import com.alchemint.contract.SAR;
import com.alchemint.contract.SarTub;
import com.alchemint.dao.OperationDaoI;
import com.alchemint.dao.SarInfoDaoI;
import io.reactivex.Flowable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;

/**
 * Created by cheng on 2019/3/1.
 */
public class SARContractTest extends Scenario {

    @Autowired
    private OperationDaoI operationDaoI;

    @Autowired
    private SarInfoDaoI sarInfoDaoI;

    @Test
    public void callTest() throws Exception{
        SAR sar = SAR.load(SAR_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());
        logger.info("off:"+sar.off().send());

        //创建SAR
        //open(sar);

        //首先需要授权SAR合约操作SETH,然后进行抵押
        reserve(sar,new BigDecimal(10.0));

        //发行稳定币
        //expande(sar,new BigDecimal(500.0));

        //contr(sar,new BigDecimal(60.00));

        //SAR详细信息
        sarInfo(sar);
    }

    private void open(SAR sar) throws Exception {
        TransactionReceipt receipt = sar.open().send();
        logger.info("open:"+receipt.toString());
        saveOperation(sar,receipt);
    }

    private void saveOperation(SAR sar,TransactionReceipt receipt) throws Exception{
        List<SAR.OperatedEventResponse> events = sar.getOperatedEvents(receipt);
        for (SAR.OperatedEventResponse event:events) {
            Operation oper = new Operation();
            oper.setAddr(ALICE.getAddress());
            oper.setAsset(SAR_CONTRACT_ADDRESS);
            oper.setType(event.opType.intValue());
            oper.setValue(new BigDecimal(event.opValue));
            oper.setBlockindex(receipt.getBlockNumber().longValue());
            oper.setTxid(receipt.getTransactionHash());
            oper.setSarTxid(receipt.getTransactionHash());
            oper.setN(receipt.getTransactionIndex().intValue());
            oper.setTime(getDateFromHash(receipt.getBlockHash()));
            operationDaoI.saveOperation(oper);
        }
    }

    private void reserve(SAR sar,BigDecimal mount) throws Exception{
        TransactionReceipt receipt = sar.reserve(Convert.toWei(mount,Convert.Unit.ETHER).toBigInteger()).send();
        logger.info("reserve:"+receipt.toString());
        saveOperation(sar,receipt);
    }

    private void expande(SAR sar,BigDecimal mount) throws Exception{
        //发行SDUSD
        BigInteger exMount = Convert.toWei(mount,Convert.Unit.ETHER).toBigInteger();
        TransactionReceipt receipt = sar.expande(exMount).send();
        logger.info(receipt.toString());
        saveOperation(sar,receipt);
    }

    private void contr(SAR sar,BigDecimal mount) throws Exception{
        //还回SDUSD
        BigInteger contrMount = Convert.toWei(mount,Convert.Unit.ETHER).toBigInteger();
        TransactionReceipt receipt = sar.contr(contrMount).send();
        logger.info(receipt.toString());
        saveOperation(sar,receipt);
    }

    private void sarInfo(SAR sar) throws Exception{
        Tuple7<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> tuple7 = sar.sars(ALICE.getAddress()).send();
        logger.info("owner:"+tuple7.getValue1());
        logger.info("locked:"+Convert.fromWei(tuple7.getValue2().toString(),Convert.Unit.ETHER));
        logger.info("hasDrawed:"+Convert.fromWei(tuple7.getValue3().toString(),Convert.Unit.ETHER));
        logger.info("bondLocked:"+Convert.fromWei(tuple7.getValue4().toString(),Convert.Unit.ETHER));
        logger.info("bondDrawed:"+Convert.fromWei(tuple7.getValue5().toString(),Convert.Unit.ETHER));
        logger.info("lastHeight:"+tuple7.getValue6());
        logger.info("fee:"+Convert.fromWei(tuple7.getValue7().toString(),Convert.Unit.ETHER));
    }

    private BigInteger getLatestBlock() throws Exception{
        Request<?, EthBlockNumber> blockNumberRequest = web3j.ethBlockNumber();
        BigInteger blockNumber = blockNumberRequest.send().getBlockNumber();
        logger.info("BlockNumber:"+blockNumber.toString());
        return blockNumber;
    }



    @Test
    public void eventFlowableLog() throws Exception{
        SarTub sar = SarTub.load(SAR_CONTRACT_ADDRESS, web3j, ALICE, new DefaultGasProvider());

        Flowable<SarTub.OperatedEventResponse> operatedEventResponseFlowable = sar.operatedEventFlowable(
                DefaultBlockParameter.valueOf(getLatestBlock().subtract(new BigInteger("10"))),
                DefaultBlockParameterName.LATEST);
        operatedEventResponseFlowable.blockingIterable().forEach(new Consumer<SarTub.OperatedEventResponse>() {
            @Override
            public void accept(SarTub.OperatedEventResponse event) {
                Log log = event.log;
                Operation oper = new Operation();
                oper.setAddr(ALICE.getAddress());
                oper.setAsset(SAR_CONTRACT_ADDRESS);
                oper.setType(event.opType.intValue());
                oper.setValue(new BigDecimal(event.opValue));
                oper.setBlockindex(log.getBlockNumber().longValue());
                oper.setTxid(log.getTransactionHash());
                oper.setSarTxid(log.getTransactionHash());
                oper.setN(log.getTransactionIndex().intValue());
                oper.setTime(getDateFromHash(log.getBlockHash()));
                operationDaoI.saveOperation(oper);
                if (event.opType.intValue() == 1){
                    SarInfo sar = new SarInfo();
                    sar.setAsset(SAR_CONTRACT_ADDRESS);
                    sar.setN(log.getTransactionIndex().intValue());
                    sar.setSarTxid(log.getTransactionHash());
                    sar.setAddr(ALICE.getAddress());
                    sar.setBlockindex(log.getBlockNumber().longValue());
                    sar.setStatus(1);
                    sar.setTime(getDateFromHash(log.getBlockHash()));
                    sarInfoDaoI.saveSarInfo(sar);
                }
                logger.info(event.from+"/"+event.opType+"/"+event.opValue);
            }
        });
    }

    private Date getDateFromHash(String blockHash) {
        try {
            Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByHash(blockHash, true);
            EthBlock.Block block = ethBlockRequest.send().getBlock();
            return new Date(block.getTimestamp().longValue() * 1000);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new Date();
    }

    @Test
    public void eventLog() throws Exception{
        EthFilter ethFilter = new EthFilter(
                DefaultBlockParameter.valueOf(getLatestBlock().subtract(new BigInteger("500"))),
                DefaultBlockParameterName.LATEST,
                SAR_CONTRACT_ADDRESS);

        Event OPERATED_EVENT = new Event("Operated",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {},
                        new TypeReference<Uint256>() {},
                        new TypeReference<Uint256>() {}));

        String encodedEventSignature = EventEncoder.encode(OPERATED_EVENT);
        ethFilter.addSingleTopic(encodedEventSignature);

        EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
        List<EthLog.LogResult> logs = ethLog.getLogs();

        assertTrue(Optional.ofNullable(logs).isPresent());

        logger.info("size:"+logs.size());

        for (EthLog.LogResult logresult:logs)
        {
            Log log = (Log) logresult.get();
            logger.info(log.toString());
            List<Type> types = FunctionReturnDecoder.decode(log.getData(), SarTub.OPERATED_EVENT.getNonIndexedParameters());

            Uint256 opType = (Uint256)types.get(0);
            Uint256 opValue = (Uint256)types.get(1);
            BigInteger type = opType.getValue();
            BigInteger value = opType.getValue();

            logger.info(opType.getValue().toString()+"/"+opValue.getValue().toString());

            Operation oper = new Operation();
            oper.setAddr(ALICE.getAddress());
            oper.setAsset(SAR_CONTRACT_ADDRESS);
            oper.setType(type.intValue());
            oper.setValue(new BigDecimal(value));
            oper.setBlockindex(log.getBlockNumber().longValue());
            oper.setTxid(log.getTransactionHash());
            oper.setSarTxid(log.getTransactionHash());
            oper.setN(log.getTransactionIndex().intValue());
            oper.setTime(getDateFromHash(log.getBlockHash()));
            operationDaoI.saveOperation(oper);
        }

    }

}
