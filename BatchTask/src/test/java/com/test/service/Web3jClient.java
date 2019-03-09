package com.test.service;

import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.Flowable;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by cheng on 2019/2/26.
 */
public class Web3jClient extends  Scenario {

    @Test
    public  void initClient(){
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            logger.info("clientVersion:"+clientVersion);

            Request<?, EthBlockNumber> blockNumberRequest = web3j.ethBlockNumber();
            BigInteger blockNumber = blockNumberRequest.send().getBlockNumber();
            logger.info("BlockNumber:"+blockNumber.toString());

            Request<?, EthAccounts> accountsRequest = web3j.ethAccounts();
            List<String> accounts = accountsRequest.send().getAccounts();
            for (String acc:accounts) {
                logger.info("account:"+acc);
            }
            String acc = "0x167340652C10BE90Ca16401E09701a9Ec5F51A1f";
            Request<?, EthGetBalance> balance = web3j.ethGetBalance(acc, DefaultBlockParameter.valueOf(blockNumber));
            BigInteger ba = balance.send().getBalance();
            logger.info(acc+"#"+Convert.fromWei(ba.toString(),Convert.Unit.ETHER));

            Request<?, EthCoinbase> coinbaseRequest = web3j.ethCoinbase();
            String coinbase = coinbaseRequest.send().getAddress();
            logger.info("ethCoinbase:"+coinbase);

            String transactionHash = "0x937ef15a9738bd5315642486da89cb0c1fa9d453c7d328a915b436a274479d19";
            Request<?, EthTransaction> transactionReq = web3j.ethGetTransactionByHash(transactionHash);
            Transaction transaction = transactionReq.send().getTransaction().get();
            logger.info("s:"+transaction.getS());
            logger.info("gasPrice:"+transaction.getGasPrice());
            logger.info("gas:"+transaction.getGas());
            logger.info("blockHash:"+transaction.getBlockHash());
            logger.info("blockNumber:"+transaction.getBlockNumber());
            logger.info("creates:"+transaction.getCreates());
            logger.info("from:"+transaction.getFrom());
            logger.info("hash:"+transaction.getHash());
            logger.info("input:"+transaction.getInput());
            logger.info("nonce:"+transaction.getNonce());
            logger.info("publicKey:"+transaction.getPublicKey());
            logger.info("r:"+transaction.getR());
            logger.info("to:"+transaction.getTo());
            logger.info("transactionIndex:"+transaction.getTransactionIndex());
            logger.info("chainId:"+transaction.getChainId());
            logger.info("value:"+transaction.getValue());
            logger.info("v:"+transaction.getV());

            String blockHash = "0x6107c0f776204ebd26ff0da33e640e92d9542903f7cd7d559b9531b5dc12d390";
            Request<?, EthBlock> ethBlockRequest =  web3j.ethGetBlockByHash(transaction.getBlockHash(),true);
            EthBlock.Block block =  ethBlockRequest.send().getBlock();
            logger.info(DateFormatUtils.format(block.getTimestamp().longValue()*1000,"yyyy-MM-dd'T'HH:mm:ssZZ"));


            Flowable<Transaction> pengdings =  web3j.pendingTransactionFlowable();
            pengdings.blockingIterable().forEach(new Consumer<Transaction>() {
                @Override
                public void accept(Transaction transaction) {
                    logger.info(transaction.toString());
                }
            });
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void allBalances(){
        try {
            Request<?, EthBlockNumber> blockNumberRequest = web3j.ethBlockNumber();
            BigInteger blockNumber = blockNumberRequest.send().getBlockNumber();
            logger.info("BlockNumber:"+blockNumber.toString());

            Request<?, EthAccounts> accountsRequest = web3j.ethAccounts();
            List<String> accounts = accountsRequest.send().getAccounts();

            //最新区块的地址余额
            for (String acc:accounts) {
                Request<?, EthGetBalance> balance = web3j.ethGetBalance(acc, DefaultBlockParameter.valueOf(blockNumber));
                BigInteger ba = balance.send().getBalance();
                logger.info(acc+"#"+Convert.fromWei(ba.toString(),Convert.Unit.ETHER));
            }

        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void walletMan(){
        try {
            Web3j web3 = Web3j.build(new HttpService(RPC));
            String passwd = "123456";
            String sourceFile = "D:\\walletfile\\keystore\\";
            String addr = WalletUtils.generateNewWalletFile(passwd,new File(sourceFile));

            logger.info(addr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transfer() {
        try {
            String passwd = "123456";
            //String sourceFile = "D:\\walletfile\\keystore\\801b653ba6e0292bafc975601d5329b59a375be6";
            String toAddress  = "0xd387876101f8c2362b430a5df597b2d7219d05c9";
            //Credentials credentials = WalletUtils.loadCredentials(passwd,sourceFile);
            unlockAccount();
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3j,ALICE,toAddress,
                    BigDecimal.valueOf(200.0), Convert.Unit.ETHER)
                    .send();

            logger.info(transactionReceipt.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void transfer2() {
        try {
            Web3j web3j = Web3j.build(new HttpService(RPC));
            String passwd = "123456";
            String sourceFile = "D:\\walletfile\\keystore\\801b653ba6e0292bafc975601d5329b59a375be6";
            String fromAddress = "0x801b653ba6e0292bafc975601d5329b59a375be6";
            String toAddress  = "0x8535a293c3481083665a6d51b8df4b1e2c192b6d";
            Credentials credentials = WalletUtils.loadCredentials(passwd,sourceFile);

            Request<?, EthGasPrice> gasPriceReq = web3j.ethGasPrice();
            BigInteger gasPrice = gasPriceReq.send().getGasPrice();

            // get the next available nonce
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    fromAddress, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            BigDecimal weiValue = Convert.toWei(BigDecimal.valueOf(2.0), Convert.Unit.ETHER);
            // create our transaction
            RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                    nonce,gasPrice, Transfer.GAS_LIMIT,toAddress,weiValue.toBigIntegerExact());

            // sign & send our transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            logger.info(ethSendTransaction.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createContract(){
        try {
            Web3j web3j = Web3j.build(new HttpService(RPC));
            String passwd = "123456";
            String sourceFile = "D:\\walletfile\\keystore\\801b653ba6e0292bafc975601d5329b59a375be6";
            String fromAddress = "0x801b653ba6e0292bafc975601d5329b59a375be6";
            Credentials credentials = WalletUtils.loadCredentials(passwd,sourceFile);

            Request<?, EthGasPrice> gasPriceReq = web3j.ethGasPrice();
            BigInteger gasPrice = gasPriceReq.send().getGasPrice();

            // get the next available nonce
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    fromAddress, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            // using a raw transaction

            BigDecimal weiValue = Convert.toWei(BigDecimal.valueOf(2.0), Convert.Unit.ETHER);
            RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                    nonce,gasPrice,Transfer.GAS_LIMIT,weiValue.toBigIntegerExact(),"0x60606040526040805190810160405280600781526020017f53204574686572000000000000000000000000000000000000000000000000008152506000908051906020019061004f9291906100b1565b506040805190810160405280600481526020017f53455448000000000000000000000000000000000000000000000000000000008152506001908051906020019061009b9291906100b1565b50601260025534156100ac57600080fd5b610156565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100f257805160ff1916838001178555610120565b82800160010185558215610120579182015b8281111561011f578251825591602001919060010190610104565b5b50905061012d9190610131565b5090565b61015391905b8082111561014f576000816000905550600101610137565b5090565b90565b610db8806101656000396000f300606060405236156100b8576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100c4578063095ea7b31461015357806318160ddd146101ad57806323b872dd146101d65780632e1a7d4d1461024f578063313ce5671461027257806370a082311461029b57806395d89b41146102e8578063a9059cbb14610377578063d0e30db0146103d1578063daea85c5146103db578063dd62ed3e1461042c575b5b6100c1610498565b5b005b34156100cf57600080fd5b6100d7610548565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101185780820151818401525b6020810190506100fc565b50505050905090810190601f1680156101455780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015e57600080fd5b610193600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506105e6565b604051808215151515815260200191505060405180910390f35b34156101b857600080fd5b6101c06106d9565b6040518082815260200191505060405180910390f35b34156101e157600080fd5b610235600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506106e4565b604051808215151515815260200191505060405180910390f35b341561025a57600080fd5b6102706004808035906020019091905050610a53565b005b341561027d57600080fd5b610285610b92565b6040518082815260200191505060405180910390f35b34156102a657600080fd5b6102d2600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610b98565b6040518082815260200191505060405180910390f35b34156102f357600080fd5b6102fb610be2565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561033c5780820151818401525b602081019050610320565b50505050905090810190601f1680156103695780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561038257600080fd5b6103b7600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610c80565b604051808215151515815260200191505060405180910390f35b6103d9610498565b005b34156103e657600080fd5b610412600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610c96565b604051808215151515815260200191505060405180910390f35b341561043757600080fd5b610482600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610cca565b6040518082815260200191505060405180910390f35b34600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055506104f160035434610d52565b6003819055503373ffffffffffffffffffffffffffffffffffffffff167fe1fffcc4923d04b559f4d29a8bfc6cda04eb5b0d3c460751c2402c5c5cc9109c346040518082815260200191505060405180910390a25b565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105de5780601f106105b3576101008083540402835291602001916105de565b820191906000526020600020905b8154815290600101906020018083116105c157829003601f168201915b505050505081565b600081600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a3600190505b92915050565b600060035490505b90565b60003373ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff16141580156107be57507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414155b156108ca57610849600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205483610d6f565b600560008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b610913600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205483610d6f565b600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555061099f600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205483610d52565b600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a3600190505b9392505050565b80600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515610aa157600080fd5b80600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055503373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501515610b2e57600080fd5b610b3a60035482610d6f565b6003819055503373ffffffffffffffffffffffffffffffffffffffff167f7fcf532c15f0a6db0bd6d0e038bea71d30d808c7d98cb3bf7268a95bf5081b65826040518082815260200191505060405180910390a25b50565b60025481565b6000600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b919050565b60018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c785780601f10610c4d57610100808354040283529160200191610c78565b820191906000526020600020905b815481529060010190602001808311610c5b57829003601f168201915b505050505081565b6000610c8d3384846106e4565b90505b92915050565b6000610cc2827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6105e6565b90505b919050565b6000600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b92915050565b60008282840191508110151515610d6857600080fd5b5b92915050565b60008282840391508111151515610d8557600080fd5b5b929150505600a165627a7a72305820caa0f390d69cb5027551b00d1b681ceca7b23706a2809a17f8f8586e51d147b50029");
            // send...
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            logger.info(ethSendTransaction.getResult());

            String transactionHash = "";
            // get contract address
            EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();

            if (transactionReceipt.getTransactionReceipt().isPresent()) {
                String contractAddress = transactionReceipt.getTransactionReceipt().get().getContractAddress();
                logger.info(contractAddress);
            } else {
                // try again
            }
        }catch (IOException e){
            logger.error(e.getMessage());
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEthLog() throws Exception{
        org.web3j.protocol.core.methods.request.EthFilter ethFilter =
                new org.web3j.protocol.core.methods.request.EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        CONTRACT_ADDRESS
                );

        Event event = new Event("Notify",
                Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));

        String encodedEventSignature = EventEncoder.encode(event);
        ethFilter.addSingleTopic(encodedEventSignature);

        EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
        List<EthLog.LogResult> logs = ethLog.getLogs();
        logger.info("size:"+logs.size());
        for (EthLog.LogResult log:logs)
        {
            logger.info(log.get().toString());
        }
    }
}
