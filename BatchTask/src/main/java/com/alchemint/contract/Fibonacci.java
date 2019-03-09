package com.alchemint.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Fibonacci extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6101408061001e6000396000f300606060405263ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416633c7fdc70811461004757806361047ff41461006f57600080fd5b341561005257600080fd5b61005d600435610085565b60405190815260200160405180910390f35b341561007a57600080fd5b61005d6004356100d1565b6000610090826100d1565b90507f71e71a8458267085d5ab16980fd5f114d2d37f232479c245d523ce8d23ca40ed828260405191825260208201526040908101905180910390a1919050565b60008115156100e25750600061010f565b81600114156100f35750600161010f565b6100ff600283036100d1565b61010b600184036100d1565b0190505b9190505600a165627a7a723058201a310dfa429cb7684b9caeb4d906d8d1d4cf3ca7091b0d128ae410463cbfb10b0029";

    public static final String FUNC_FIBONACCINOTIFY = "fibonacciNotify";

    public static final String FUNC_FIBONACCI = "fibonacci";

    public static final Event NOTIFY_EVENT = new Event("Notify", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Fibonacci(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Fibonacci(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Fibonacci(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Fibonacci(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> fibonacciNotify(BigInteger number) {
        final Function function = new Function(
                FUNC_FIBONACCINOTIFY, 
                Arrays.<Type>asList(new Uint256(number)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> fibonacci(BigInteger number) {
        final Function function = new Function(FUNC_FIBONACCI, 
                Arrays.<Type>asList(new Uint256(number)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<NotifyEventResponse> getNotifyEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(NOTIFY_EVENT, transactionReceipt);
        ArrayList<NotifyEventResponse> responses = new ArrayList<NotifyEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NotifyEventResponse typedResponse = new NotifyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.input = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NotifyEventResponse> notifyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NotifyEventResponse>() {
            @Override
            public NotifyEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(NOTIFY_EVENT, log);
                NotifyEventResponse typedResponse = new NotifyEventResponse();
                typedResponse.log = log;
                typedResponse.input = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NotifyEventResponse> notifyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NOTIFY_EVENT));
        return notifyEventFlowable(filter);
    }

    @Deprecated
    public static Fibonacci load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fibonacci(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Fibonacci load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fibonacci(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Fibonacci load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Fibonacci(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Fibonacci load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Fibonacci(contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static Fibonacci deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
//        return deployRemoteCall(Fibonacci.class, web3j, credentials, contractGasProvider, BINARY, "");
//    }

    @Deprecated
    public static RemoteCall<Fibonacci> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Fibonacci.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Fibonacci> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Fibonacci.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Fibonacci> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Fibonacci.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NotifyEventResponse {
        public Log log;

        public BigInteger input;

        public BigInteger result;

        public Log getLog() {
            return log;
        }

        public void setLog(Log log) {
            this.log = log;
        }

        public BigInteger getInput() {
            return input;
        }

        public void setInput(BigInteger input) {
            this.input = input;
        }

        public BigInteger getResult() {
            return result;
        }

        public void setResult(BigInteger result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "NotifyEventResponse{" +
                    "log=" + log +
                    ", input=" + input +
                    ", result=" + result +
                    '}';
        }
    }
}
