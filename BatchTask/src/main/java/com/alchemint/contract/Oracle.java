package com.alchemint.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Oracle extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b604051602080610add8339810160405280805160008054600160a060020a03191633600160a060020a0390811691909117909155909250821615905061006b5760008054600160a060020a031916600160a060020a0383161790555b600160a060020a03166000908152600a60205260409020805460ff19166001179055610a418061009c6000396000f300606060405236156100ac5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632b2e05c181146100b15780633e8f5b90146100e4578063524f38891461013757806364e72dbd146101ad5780636b9f96ea146101cc57806379502c55146101e15780638da5cb5b14610232578063b44bd51d14610261578063bef6ff33146102c4578063e74254a7146102e3578063f2fde38b14610348575b600080fd5b34156100bc57600080fd5b6100d0600160a060020a0360043516610367565b604051901515815260200160405180910390f35b34156100ef57600080fd5b6100d060046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965050933593506103ac92505050565b341561014257600080fd5b61018860046024813581810190830135806020601f820181900481020160405190810160405281815292919060208401838380828437509496506104ef95505050505050565b6040516fffffffffffffffffffffffffffffffff909116815260200160405180910390f35b34156101b857600080fd5b6100d0600160a060020a0360043516610570565b34156101d757600080fd5b6101df610585565b005b34156101ec57600080fd5b6101f46107b6565b60405196875260208701959095526040808701949094526060860192909252608085015260a084015260c083019190915260e0909101905180910390f35b341561023d57600080fd5b6102456107ce565b604051600160a060020a03909116815260200160405180910390f35b341561026c57600080fd5b6102b260046024813581810190830135806020601f820181900481020160405190810160405281815292919060208401838380828437509496506107dd95505050505050565b60405190815260200160405180910390f35b34156102cf57600080fd5b6100d0600160a060020a036004351661084c565b34156102ee57600080fd5b6100d060046024813581810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650505092356fffffffffffffffffffffffffffffffff16925061088d915050565b341561035357600080fd5b6101df600160a060020a03600435166109cb565b6000805433600160a060020a0390811691161461038357600080fd5b50600160a060020a03166000908152600a60205260409020805460ff1916600190811790915590565b600160a060020a0333166000908152600a602052604081205460ff1615156103d357600080fd5b816008846040518082805190602001908083835b602083106104065780518252601f1990920191602091820191016103e7565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405190819003902055600160a060020a0333167f52464b6f68ead205e69f8601325cb933973101cfc4478b33606d7db3dbb2654b84846040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156104ab578082015183820152602001610493565b50505050905090810190601f1680156104d85780820380516001836020036101000a031916815260200191505b50935050505060405180910390a250600192915050565b60006009826040518082805190602001908083835b602083106105235780518252601f199092019160209182019101610504565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051908190039020546fffffffffffffffffffffffffffffffff1692915050565b600a6020526000908152604090205460ff1681565b60005433600160a060020a039081169116146105a057600080fd5b60e0604051908101604052806105e860408051908101604052601581527f6c69717569646174655f6c696e655f726174655f63000000000000000000000060208201526107dd565b815260200161062960408051908101604052601481527f6c69717569646174655f6469735f726174655f6300000000000000000000000060208201526107dd565b815260200161066a60408051908101604052600a81527f6665655f726174655f630000000000000000000000000000000000000000000060208201526107dd565b81526020016106ab60408051908101604052601481527f6c69717569646174655f746f705f726174655f6300000000000000000000000060208201526107dd565b81526020016106ec60408051908101604052601681527f6c69717569646174655f6c696e655f72617465545f630000000000000000000060208201526107dd565b815260200161072d60408051908101604052600d81527f69737375696e675f6665655f630000000000000000000000000000000000000060208201526107dd565b815260200161076e60408051908101604052600a81527f646562745f746f705f630000000000000000000000000000000000000000000060208201526107dd565b90526001815181556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c082015160069091015550565b60015460025460035460045460055460065460075487565b600054600160a060020a031681565b60006008826040518082805190602001908083835b602083106108115780518252601f1990920191602091820191016107f2565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b6000805433600160a060020a0390811691161461086857600080fd5b50600160a060020a03166000908152600a60205260409020805460ff19169055600190565b600160a060020a0333166000908152600a602052604081205460ff1615156108b457600080fd5b816009846040518082805190602001908083835b602083106108e75780518252601f1990920191602091820191016108c8565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405190819003902080546fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff9290921691909117905533600160a060020a03167f52464b6f68ead205e69f8601325cb933973101cfc4478b33606d7db3dbb2654b84846040516fffffffffffffffffffffffffffffffff821660208201526040808252819081018481815181526020019150805190602001908083836000838110156104ab578082015183820152602001610493565b60005433600160a060020a039081169116146109e657600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03929092169190911790555600a165627a7a7230582072456ea200d15b9c2fa8f86d6b57f77d4029a34e235740f2f58e664e3da21ca30029";

    public static final String FUNC_SETAUTH = "setAuth";

    public static final String FUNC_SETCONFIG = "setConfig";

    public static final String FUNC_GETPRICE = "getPrice";

    public static final String FUNC_AUTHS = "auths";

    public static final String FUNC_FLUSH = "flush";

    public static final String FUNC_CONFIG = "config";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETCONFIG = "getConfig";

    public static final String FUNC_RELEASEAUTH = "releaseAuth";

    public static final String FUNC_SETPRICE = "setPrice";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event ORACLEOPERATED_EVENT = new Event("OracleOperated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Oracle(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Oracle(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Oracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Oracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> setAuth(String addr) {
        final Function function = new Function(
                FUNC_SETAUTH, 
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setConfig(String key, BigInteger set) {
        final Function function = new Function(
                FUNC_SETCONFIG, 
                Arrays.<Type>asList(new Utf8String(key),
                new Uint256(set)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getPrice(String key) {
        final Function function = new Function(FUNC_GETPRICE, 
                Arrays.<Type>asList(new Utf8String(key)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> auths(String param0) {
        final Function function = new Function(FUNC_AUTHS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> flush() {
        final Function function = new Function(
                FUNC_FLUSH, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> config() {
        final Function function = new Function(FUNC_CONFIG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getConfig(String key) {
        final Function function = new Function(FUNC_GETCONFIG, 
                Arrays.<Type>asList(new Utf8String(key)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> releaseAuth(String addr) {
        final Function function = new Function(
                FUNC_RELEASEAUTH, 
                Arrays.<Type>asList(new Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setPrice(String key, BigInteger set) {
        final Function function = new Function(
                FUNC_SETPRICE, 
                Arrays.<Type>asList(new Utf8String(key),
                new Uint128(set)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OracleOperatedEventResponse> getOracleOperatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ORACLEOPERATED_EVENT, transactionReceipt);
        ArrayList<OracleOperatedEventResponse> responses = new ArrayList<OracleOperatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OracleOperatedEventResponse typedResponse = new OracleOperatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.opType = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.opValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OracleOperatedEventResponse> oracleOperatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OracleOperatedEventResponse>() {
            @Override
            public OracleOperatedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ORACLEOPERATED_EVENT, log);
                OracleOperatedEventResponse typedResponse = new OracleOperatedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.opType = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.opValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OracleOperatedEventResponse> oracleOperatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORACLEOPERATED_EVENT));
        return oracleOperatedEventFlowable(filter);
    }

    @Deprecated
    public static Oracle load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Oracle(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Oracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Oracle(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Oracle load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Oracle(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Oracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Oracle(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Oracle> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(admin)));
        return deployRemoteCall(Oracle.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Oracle> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(admin)));
        return deployRemoteCall(Oracle.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Oracle> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(admin)));
        return deployRemoteCall(Oracle.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Oracle> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(admin)));
        return deployRemoteCall(Oracle.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class OracleOperatedEventResponse {
        public Log log;

        public String from;

        public String opType;

        public BigInteger opValue;

    }
}
