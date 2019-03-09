package com.test.service;

import com.alchemint.contract.Fibonacci;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import org.junit.Test;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by cheng on 2019/3/1.
 */
public class FibonacciContractTest extends Scenario {

    @Test
    public void callContract(){
        //Fibonacci fibonacci =
                //Fibonacci.deploy(web3j,ALICE, new DefaultGasProvider());
        //logger.info(fibonacci.getContractAddress());
        try {
            String addr = "0x0098c8938bc51044fec5061c97fe2453eb268022";
            Fibonacci fibonacci = Fibonacci.load(addr, web3j, ALICE, new DefaultGasProvider());
            RemoteCall<BigInteger> remoteCall = fibonacci.fibonacci(new BigInteger("9"));
            BigInteger result =  remoteCall.send();
            logger.info(result.toString());

            RemoteCall<TransactionReceipt> receiptRemoteCall =  fibonacci.fibonacciNotify(new BigInteger("10"));
            TransactionReceipt transactionReceipt =  receiptRemoteCall.send();
            logger.info("transactionReceipt:"+transactionReceipt.toString());

           List<Fibonacci.NotifyEventResponse> notifyEventResponseList =  fibonacci.getNotifyEvents(transactionReceipt);
            for (Fibonacci.NotifyEventResponse res:
                 notifyEventResponseList) {
                logger.info(res.toString());
            }

        }catch (Exception e){

        }
    }

    @Test
    public void flowableEvent(){
        String addr = "0x0098c8938bc51044fec5061c97fe2453eb268022";
        Fibonacci fibonacci = Fibonacci.load(addr, web3j, ALICE, new DefaultGasProvider());

        Flowable<Fibonacci.NotifyEventResponse> flowable =  fibonacci.notifyEventFlowable(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST);
        flowable.blockingIterable().forEach(new java.util.function.Consumer<Fibonacci.NotifyEventResponse>() {
            @Override
            public void accept(Fibonacci.NotifyEventResponse notifyEventResponse) {
                logger.info(notifyEventResponse.getInput()+"/"+notifyEventResponse.getResult());
            }
        });
    }
}
