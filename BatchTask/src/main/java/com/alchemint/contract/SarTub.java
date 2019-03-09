package com.alchemint.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
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
public class SarTub extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b60405160608061202283398101604052808051919060200180519190602001805160058054600160a060020a0319908116600160a060020a0397881617909155600680548216958716959095179094556007805490941694169390931790915550611fa19050806100816000396000f300606060405236156101855763ffffffff60e060020a6000350416630c947ca9811461018a578063143e55e0146101b957806326365456146101de5780632e1a7d4d146101fd5780633031f636146102275780633ed625fb14610246578063421f60ac1461026557806343d726d6146102865780636626b26d14610299578063666e1b39146102ac5780636a72754a146102cb5780636fcca69b146102ea57806371ca57bc14610309578063741fb52314610328578063793b85531461033b5780637a4e4ecf146103515780637adbf973146103735780637b6dba49146103925780637dc0d1d0146103a5578063819b25ba146103b8578063a47889ed146103ce578063a64ea40b14610435578063afe0ae4c14610454578063cbf9fe5f1461046a578063d1a9eebd14610489578063d1fc84ef1461049f578063d5936c9a146104b7578063df32754b146104db578063e47501a8146104ee578063f2fde38b1461050d578063f50d54471461052c578063f851a44014610542578063fcfff16f14610555575b600080fd5b341561019557600080fd5b61019d610568565b604051600160a060020a03909116815260200160405180910390f35b34156101c457600080fd5b6101cc610577565b60405190815260200160405180910390f35b34156101e957600080fd5b6101cc600160a060020a036004351661057b565b341561020857600080fd5b610213600435610599565b604051901515815260200160405180910390f35b341561023257600080fd5b6101cc600160a060020a0360043516610767565b341561025157600080fd5b6101cc600160a060020a0360043516610785565b341561027057600080fd5b610284600160a060020a03600435166107a3565b005b341561029157600080fd5b6102136107e0565b34156102a457600080fd5b6102136108f6565b34156102b757600080fd5b61019d600160a060020a0360043516610906565b34156102d657600080fd5b610213600160a060020a0360043516610924565b34156102f557600080fd5b6101cc600160a060020a0360043516610939565b341561031457600080fd5b6101cc600160a060020a0360043516610957565b341561033357600080fd5b6101cc610976565b341561034657600080fd5b61021360043561097c565b341561035c57600080fd5b610213600160a060020a0360043516602435610bf3565b341561037e57600080fd5b610284600160a060020a0360043516610eb0565b341561039d57600080fd5b61019d610eed565b34156103b057600080fd5b61019d610efc565b34156103c357600080fd5b610213600435610f0b565b34156103d957600080fd5b6103ed600160a060020a036004351661108c565b604051600160a060020a03909716875260208701959095526040808701949094526060860192909252608085015260a084015260c083019190915260e0909101905180910390f35b341561044057600080fd5b610213600160a060020a03600435166110d3565b341561045f57600080fd5b6102136004356110e8565b341561047557600080fd5b6101cc600160a060020a0360043516611416565b341561049457600080fd5b610213600435611434565b34156104aa57600080fd5b61028460043515156116b0565b34156104c257600080fd5b610213600160a060020a036004351660243515156116fa565b34156104e657600080fd5b610284611744565b34156104f957600080fd5b610284600160a060020a0360043516611761565b341561051857600080fd5b610284600160a060020a036004351661179e565b341561053757600080fd5b6102136004356117db565b341561054d57600080fd5b61019d611a1a565b341561056057600080fd5b610213611a29565b600654600160a060020a031681565b4290565b600160a060020a031660009081526002602052604090206005015490565b600080548190819060a060020a900460ff1615156105b657600080fd5b600084116105c357600080fd5b600160a060020a03331660009081526003602052604090205460ff1615156105ea57600080fd5b6105f333610906565b600160a060020a031633600160a060020a031614151561061257600080fd5b61061b33610957565b915061062633611416565b90508381101561063557600080fd5b61064682610641611b65565b611c05565b61065b6106538387611c2d565b610641611c3d565b101561066657600080fd5b600554600160a060020a031663a9059cbb338660006040516020015260405160e060020a63ffffffff8516028152600160a060020a0390921660048301526024820152604401602060405180830381600087803b15156106c557600080fd5b6102c65a03f115156106d657600080fd5b5050506040518051905015156106eb57600080fd5b600160a060020a0333166000908152600260205260409020600101546107119085611c2d565b600160a060020a033316600081815260026020526040908190206001019290925590600080516020611f568339815191529060039087905191825260208201526040908101905180910390a25060019392505050565b600160a060020a031660009081526002602052604090206004015490565b600160a060020a031660009081526002602052604090206003015490565b60005433600160a060020a039081169116146107be57600080fd5b60068054600160a060020a031916600160a060020a0392909216919091179055565b6000805460a060020a900460ff1615156107f957600080fd5b600160a060020a03331660009081526003602052604090205460ff16151561082057600080fd5b61082933610906565b600160a060020a031633600160a060020a031614151561084857600080fd5b61085133611416565b1561085b57600080fd5b61086433610957565b1561086e57600080fd5b61087733610939565b1561088157600080fd5b61088a33610785565b1561089457600080fd5b61089d33610767565b156108a757600080fd5b600160a060020a033316600081815260036020526040808220805460ff19169055600080516020611f56833981519152916007915191825260208201526040908101905180910390a250600190565b60005460a060020a900460ff1681565b600160a060020a039081166000908152600260205260409020541690565b60046020526000908152604090205460ff1681565b600160a060020a031660009081526002602052604090206006015490565b600160a060020a03166000908152600260208190526040909120015490565b60015481565b60008060008060008060008060149054906101000a900460ff1615156109a157600080fd5b600088116109ae57600080fd5b600160a060020a03331660009081526003602052604090205460ff1615156109d557600080fd5b6109de33610906565b600160a060020a031633600160a060020a03161415156109fd57600080fd5b610a0633610957565b9550610a128689611cef565b610a1a611cff565b1015610a2557600080fd5b610a2e33611416565b9450610a4685610641610a3f611c3d565b6064611c05565b9350610a5d610a55878a611cef565b610641611b65565b925082841015610a6c57600080fd5b610a768689611cef565b33600160a060020a03818116600090815260026020819052604091829020019390935560065416916340c10f1991908b905160e060020a63ffffffff8516028152600160a060020a0390921660048301526024820152604401600060405180830381600087803b1515610ae857600080fd5b6102c65a03f11515610af957600080fd5b504393505050851515610b2e57600160a060020a03331660009081526002602052604081206005810184905560060155610bb2565b6402540be400610b5a610b52610b4c85610b473361057b565b611c2d565b89611c05565b610641611d80565b811515610b6357fe5b33600160a060020a03811660009081526002602052604090206005018590559190049150610b9090610939565b600160a060020a03331660009081526002602052604090209082016006909101555b33600160a060020a0316600080516020611f5683398151915260048a60405191825260208201526040908101905180910390a2506001979650505050505050565b600080600080600080600060149054906101000a900460ff161515610c1757600080fd5b60008711610c2457600080fd5b600160a060020a03331660009081526003602052604090205460ff161515610c4b57600080fd5b600160a060020a03881660009081526003602052604090205460ff161515610c7257600080fd5b610c7b88610957565b9450610c8688611416565b9350610c93856064611c05565b610c9f85610641611c3d565b811515610ca857fe5b049250610cbc83610cb7611e01565b611e82565b9150610cca85610641611d80565b610cd685610641611c3d565b10610ce057600080fd5b506000606483118015610cf95750610cf6611d80565b83105b15610d6d57610d0f610d09611c3d565b83611c05565b87811515610d1957fe5b04905060008111610d2957600080fd5b838110610d3557600080fd5b848710610d4157600080fd5b610d4e6106538583611c2d565b610d63610d5b878a611c2d565b610641611ed4565b11610d6d57600080fd5b60648311610d8457848714610d8157600080fd5b50825b600654600160a060020a0316639dc29fac338960405160e060020a63ffffffff8516028152600160a060020a0390921660048301526024820152604401600060405180830381600087803b1515610dda57600080fd5b6102c65a03f11515610deb57600080fd5b505050610df88482611c2d565b600160a060020a038916600090815260026020526040902060010155610e1e8588611c2d565b600160a060020a03808a166000908152600260208190526040808320909101939093553390911681522060010154610e569082611cef565b600160a060020a033316600081815260026020526040908190206001019290925590600080516020611f56833981519152906006908a905191825260208201526040908101905180910390a2506001979650505050505050565b60005433600160a060020a03908116911614610ecb57600080fd5b60078054600160a060020a031916600160a060020a0392909216919091179055565b600554600160a060020a031681565b600754600160a060020a031681565b6000805460a060020a900460ff161515610f2457600080fd5b60008211610f3157600080fd5b600160a060020a03331660009081526003602052604090205460ff161515610f5857600080fd5b610f6133610906565b600160a060020a031633600160a060020a0316141515610f8057600080fd5b600554600160a060020a03166323b872dd33308560006040516020015260405160e060020a63ffffffff8616028152600160a060020a0393841660048201529190921660248201526044810191909152606401602060405180830381600087803b1515610fec57600080fd5b6102c65a03f11515610ffd57600080fd5b50505060405180519050151561101257600080fd5b600160a060020a0333166000908152600260205260409020600101546110389083611cef565b600160a060020a033316600081815260026020819052604091829020600101939093559091600080516020611f568339815191529185905191825260208201526040908101905180910390a2506001919050565b60026020819052600091825260409091208054600182015492820154600383015460048401546005850154600690950154600160a060020a03909416959492939192909187565b60036020526000908152604090205460ff1681565b60008060008060008060149054906101000a900460ff16151561110a57600080fd5b6000861161111757600080fd5b600160a060020a03331660009081526003602052604090205460ff16151561113e57600080fd5b61114733610906565b600160a060020a031633600160a060020a031614151561116657600080fd5b61116f33610957565b93508584101561117e57600080fd5b4392506402540be4006111a0610b5261119a86610b473361057b565b87611c05565b8115156111a957fe5b049150836111c86111c2846111bd33610939565b611cef565b88611c05565b8115156111d157fe5b0490506111de8682611cef565b600654600160a060020a03166370a082313360006040516020015260405160e060020a63ffffffff8416028152600160a060020a039091166004820152602401602060405180830381600087803b151561123757600080fd5b6102c65a03f1151561124857600080fd5b505050604051805190501015151561125f57600080fd5b33600160a060020a0381166000908152600260205260409020600501849055611297906112919084906111bd90610939565b82611c2d565b600160a060020a0333166000908152600260205260409020600601556112bd8487611c2d565b600160a060020a0333818116600090815260026020819052604080832090910194909455600654909216926323b872dd923091869190516020015260405160e060020a63ffffffff8616028152600160a060020a0393841660048201529190921660248201526044810191909152606401602060405180830381600087803b151561134757600080fd5b6102c65a03f1151561135857600080fd5b50505060405180519050151561136d57600080fd5b600654600160a060020a0316639dc29fac338860405160e060020a63ffffffff8516028152600160a060020a0390921660048301526024820152604401600060405180830381600087803b15156113c357600080fd5b6102c65a03f115156113d457600080fd5b50505033600160a060020a0316600080516020611f5683398151915260058860405191825260208201526040908101905180910390a250600195945050505050565b600160a060020a031660009081526002602052604090206001015490565b600080600080600080600060149054906101000a900460ff16151561145857600080fd5b6000871161146557600080fd5b600160a060020a03331660009081526003602052604090205460ff16151561148c57600080fd5b600160a060020a03331660009081526004602052604090205460ff1615156114b357600080fd5b6114bc33610957565b94506114c733611416565b9350600085116114d657600080fd5b868510156114e357600080fd5b6114ee856064611c05565b6114fa85610641611c3d565b81151561150357fe5b04925061150e611d80565b831061151957600080fd5b611521611c3d565b8781151561152b57fe5b0491506064831180156115445750611541611d80565b83105b15611580576115538588611c2d565b6115606106538685611c2d565b81151561156957fe5b049050611574611ed4565b81111561158057600080fd5b83821061158f578391506115b6565b6115998483611c2d565b600160a060020a0333166000908152600260205260409020600101555b6115c08483611c2d565b600160a060020a0333166000908152600260205260409020600101556115e68588611c2d565b600160a060020a033316600090815260026020819052604090912090810191909155600301546116169083611cef565b600160a060020a03331660009081526002602052604090206003810191909155600401546116449088611cef565b600160a060020a03331660009081526002602052604090206004015560015461166d9088611cef565b600155600160a060020a033316600080516020611f5683398151915260068960405191825260208201526040908101905180910390a25060019695505050505050565b60005433600160a060020a039081169116146116cb57600080fd5b6000805491151560a060020a0274ff000000000000000000000000000000000000000019909216919091179055565b6000805433600160a060020a0390811691161461171657600080fd5b50600160a060020a0382166000908152600460205260409020805460ff191682151517905560015b92915050565b60008054600160a060020a03191633600160a060020a0316179055565b60005433600160a060020a0390811691161461177c57600080fd5b60058054600160a060020a031916600160a060020a0392909216919091179055565b60005433600160a060020a039081169116146117b957600080fd5b60008054600160a060020a031916600160a060020a0392909216919091179055565b60008060008060008060149054906101000a900460ff1615156117fd57600080fd5b6000861161180a57600080fd5b600160a060020a03331660009081526003602052604090205460ff16151561183157600080fd5b600160a060020a03331660009081526004602052604090205460ff16151561185857600080fd5b61186133610906565b600160a060020a031633600160a060020a031614151561188057600080fd5b61188933611416565b935061189433610767565b925061189f33610785565b9150600082116118ae57600080fd5b600083116118bb57600080fd5b858210156118c857600080fd5b600654600160a060020a0316639dc29fac338860405160e060020a63ffffffff8516028152600160a060020a0390921660048301526024820152604401600060405180830381600087803b151561191e57600080fd5b6102c65a03f1151561192f57600080fd5b5050508261193d8388611c05565b81151561194657fe5b0490506119538482611cef565b600160a060020a0333166000908152600260205260409020600101556119798282611c2d565b600160a060020a03331660009081526002602052604090206003015561199f8387611c2d565b600160a060020a0333166000908152600260205260409020600401556001546119c89087611c2d565b600181905560009010156119db57600080fd5b33600160a060020a0316600080516020611f5683398151915260088860405191825260208201526040908101905180910390a250600195945050505050565b600054600160a060020a031681565b6000805460a060020a900460ff161515611a4257600080fd5b600160a060020a03331660009081526003602052604090205460ff1615611a6857600080fd5b60e06040519081016040908152600160a060020a0333168083526000602080850182905283850182905260608501829052608085018290524360a086015260c0850182905291815260029091522081518154600160a060020a031916600160a060020a03919091161781556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c082015160069091015550600160a060020a033316600081815260036020526040808220805460ff19166001908117909155600080516020611f568339815191529290915191825260208201526040908101905180910390a250600190565b600754600090600160a060020a031663b44bd51d826040516020015260405160e060020a63ffffffff8316028152602060048201819052601560248301527f6c69717569646174655f6c696e655f726174655f630000000000000000000000604483015260649091019060405180830381600087803b1515611be657600080fd5b6102c65a03f11515611bf757600080fd5b505050604051805191505090565b6000811580611c22575050808202828282811515611c1f57fe5b04145b151561173e57600080fd5b8082038281111561173e57600080fd5b600754600090600160a060020a031663524f3889826040516020015260405160e060020a63ffffffff8316028152602060048201819052600960248301527f6574685f70726963650000000000000000000000000000000000000000000000604483015260649091019060405180830381600087803b1515611cbe57600080fd5b6102c65a03f11515611ccf57600080fd5b50505060405180516fffffffffffffffffffffffffffffffff1691505090565b8082018281101561173e57600080fd5b600754600090600160a060020a031663b44bd51d826040516020015260405160e060020a63ffffffff8316028152602060048201819052600a60248301527f646562745f746f705f6300000000000000000000000000000000000000000000604483015260649091019060405180830381600087803b1515611be657600080fd5b600754600090600160a060020a031663b44bd51d826040516020015260405160e060020a63ffffffff8316028152602060048201819052600a60248301527f6665655f726174655f6300000000000000000000000000000000000000000000604483015260649091019060405180830381600087803b1515611be657600080fd5b600754600090600160a060020a031663b44bd51d826040516020015260405160e060020a63ffffffff8316028152602060048201819052601460248301527f6c69717569646174655f6469735f726174655f63000000000000000000000000604483015260649091019060405180830381600087803b1515611be657600080fd5b600081818085118015611e955750600084115b15611ebf5784620f4240811515611ea857fe5b04905083606402811115611ebf5760648181010491505b83821015611ecc57600080fd5b509392505050565b600754600090600160a060020a031663b44bd51d826040516020015260405160e060020a63ffffffff8316028152602060048201819052601460248301527f6c69717569646174655f746f705f726174655f63000000000000000000000000604483015260649091019060405180830381600087803b1515611be657600080fd0081cd881756e32fce0169981db146fbe56bc9ab93853facc57a8ad609203b56e3a165627a7a72305820ae6e213419854b52d37bdd01eec42eacd70a357f1abff6e683f4e142e540b6c30029";

    public static final String FUNC_SDUSD = "sdusd";

    public static final String FUNC_ERA = "era";

    public static final String FUNC_LASTHEIGHT = "lastHeight";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_BONDDRAWED = "bondDrawed";

    public static final String FUNC_BONDLOCKED = "bondLocked";

    public static final String FUNC_SETSDUSD = "setSDUSD";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_OFF = "off";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_BONDSTATUS = "bondStatus";

    public static final String FUNC_FEE = "fee";

    public static final String FUNC_HASDRAWED = "hasDrawed";

    public static final String FUNC_BONDGLOBAL = "bondGlobal";

    public static final String FUNC_EXPANDE = "expande";

    public static final String FUNC_RESCUE = "rescue";

    public static final String FUNC_SETORACLE = "setOracle";

    public static final String FUNC_SETH = "seth";

    public static final String FUNC_ORACLE = "oracle";

    public static final String FUNC_RESERVE = "reserve";

    public static final String FUNC_SARS = "sars";

    public static final String FUNC_SARSTATUS = "sarStatus";

    public static final String FUNC_CONTR = "contr";

    public static final String FUNC_LOCKED = "locked";

    public static final String FUNC_RESCUET = "rescueT";

    public static final String FUNC_SETOFFSTATUS = "setOffStatus";

    public static final String FUNC_SETBOND = "setBond";

    public static final String FUNC_OWNED = "owned";

    public static final String FUNC_SETSETH = "setSETH";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAWT = "withdrawT";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_OPEN = "open";

    public static final Event OPERATED_EVENT = new Event("Operated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SarTub(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SarTub(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SarTub(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SarTub(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> sdusd() {
        final Function function = new Function(FUNC_SDUSD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> era() {
        final Function function = new Function(FUNC_ERA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> lastHeight(String addr) {
        final Function function = new Function(FUNC_LASTHEIGHT, 
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> withdraw(BigInteger mount) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new Uint256(mount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> bondDrawed(String addr) {
        final Function function = new Function(FUNC_BONDDRAWED,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> bondLocked(String addr) {
        final Function function = new Function(FUNC_BONDLOCKED,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setSDUSD(String sdusd_) {
        final Function function = new Function(
                FUNC_SETSDUSD,
                Arrays.<Type>asList(new Address(sdusd_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> close() {
        final Function function = new Function(
                FUNC_CLOSE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> off() {
        final Function function = new Function(FUNC_OFF,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> owner(String addr) {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> bondStatus(String param0) {
        final Function function = new Function(FUNC_BONDSTATUS,
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> fee(String addr) {
        final Function function = new Function(FUNC_FEE,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> hasDrawed(String addr) {
        final Function function = new Function(FUNC_HASDRAWED,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> bondGlobal() {
        final Function function = new Function(FUNC_BONDGLOBAL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> expande(BigInteger wad) {
        final Function function = new Function(
                FUNC_EXPANDE,
                Arrays.<Type>asList(new Uint256(wad)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rescue(String dest, BigInteger wad) {
        final Function function = new Function(
                FUNC_RESCUE,
                Arrays.<Type>asList(new Address(dest),
                new Uint256(wad)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setOracle(String oracle_) {
        final Function function = new Function(
                FUNC_SETORACLE,
                Arrays.<Type>asList(new Address(oracle_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> seth() {
        final Function function = new Function(FUNC_SETH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> oracle() {
        final Function function = new Function(FUNC_ORACLE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> reserve(BigInteger wad) {
        final Function function = new Function(
                FUNC_RESERVE,
                Arrays.<Type>asList(new Uint256(wad)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple7<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> sars(String param0) {
        final Function function = new Function(FUNC_SARS,
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple7<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> sarStatus(String param0) {
        final Function function = new Function(FUNC_SARSTATUS,
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> contr(BigInteger wad) {
        final Function function = new Function(
                FUNC_CONTR,
                Arrays.<Type>asList(new Uint256(wad)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> locked(String addr) {
        final Function function = new Function(FUNC_LOCKED,
                Arrays.<Type>asList(new Address(addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> rescueT(BigInteger bondMount) {
        final Function function = new Function(
                FUNC_RESCUET,
                Arrays.<Type>asList(new Uint256(bondMount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setOffStatus(Boolean status) {
        final Function function = new Function(
                FUNC_SETOFFSTATUS,
                Arrays.<Type>asList(new Bool(status)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setBond(String src, Boolean status) {
        final Function function = new Function(
                FUNC_SETBOND,
                Arrays.<Type>asList(new Address(src),
                new Bool(status)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> owned() {
        final Function function = new Function(
                FUNC_OWNED,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setSETH(String seth_) {
        final Function function = new Function(
                FUNC_SETSETH,
                Arrays.<Type>asList(new Address(seth_)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newAdmin) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(newAdmin)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawT(BigInteger mount) {
        final Function function = new Function(
                FUNC_WITHDRAWT,
                Arrays.<Type>asList(new Uint256(mount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> open() {
        final Function function = new Function(
                FUNC_OPEN,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OperatedEventResponse> getOperatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OPERATED_EVENT, transactionReceipt);
        ArrayList<OperatedEventResponse> responses = new ArrayList<OperatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OperatedEventResponse typedResponse = new OperatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.opType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.opValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OperatedEventResponse> operatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OperatedEventResponse>() {
            @Override
            public OperatedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OPERATED_EVENT, log);
                OperatedEventResponse typedResponse = new OperatedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.opType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.opValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OperatedEventResponse> operatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OPERATED_EVENT));
        return operatedEventFlowable(filter);
    }

    @Deprecated
    public static SarTub load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SarTub(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SarTub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SarTub(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SarTub load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SarTub(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SarTub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SarTub(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SarTub> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String seth_, String sdusd_, String oracle_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(seth_),
                new Address(sdusd_),
                new Address(oracle_)));
        return deployRemoteCall(SarTub.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SarTub> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String seth_, String sdusd_, String oracle_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(seth_),
                new Address(sdusd_),
                new Address(oracle_)));
        return deployRemoteCall(SarTub.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SarTub> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String seth_, String sdusd_, String oracle_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(seth_),
                new Address(sdusd_),
                new Address(oracle_)));
        return deployRemoteCall(SarTub.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SarTub> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String seth_, String sdusd_, String oracle_) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(seth_),
                new Address(sdusd_),
                new Address(oracle_)));
        return deployRemoteCall(SarTub.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class OperatedEventResponse {
        public Log log;

        public String from;

        public BigInteger opType;

        public BigInteger opValue;
    }
}
