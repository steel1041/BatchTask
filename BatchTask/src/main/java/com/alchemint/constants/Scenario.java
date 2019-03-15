package com.alchemint.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.admin.Admin;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

/**
 * Common methods & settings used accross scenarios.
 */
public class Scenario{

    public final static Logger logger = LoggerFactory.getLogger(Scenario.class);

    public static final String RPC = "http://47.75.103.144:8546/";
    public static final String RPC_WS = "ws://47.75.103.144:8545/";

    static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
    static final BigInteger GAS_LIMIT = BigInteger.valueOf(210000);
    static final StaticGasProvider STATIC_GAS_PROVIDER =
            new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    // testnet
    public static final String WALLET_PASSWORD = "123456";

    /*
    If you want to use regular Ethereum wallet addresses, provide a WALLET address variable
    "0x..." // 20 bytes (40 hex characters) & replace instances of ALICE.getAddress() with this
    WALLET address variable you've defined.
    */

    public static final String ORACLE_CONTRACT_ADDRESS = "0x1ca7ce64a84dd27bbe31f2021e95efa684603444";

    public static final String SETH_CONTRACT_ADDRESS = "0x916b96d20ace37d4ff57f552843f35f61b84e775";

    public static final String SDUSD_CONTRACT_ADDRESS = "0xc805c020502129c6047fc09cc97d75cda1e0d3fa";

    public static final String SAR_CONTRACT_ADDRESS = "0x62273e9ec0da1dd04c882eacb05694a1d9b3aef3";

    private static final BigInteger ACCOUNT_UNLOCK_DURATION = BigInteger.valueOf(30);

    private static final int SLEEP_DURATION = 15000;
    private static final int ATTEMPTS = 40;

    public Admin web3j;

    public Admin web3jws;
    public Scenario() { }

//    static {
//        try {
//            this.web3j = Admin.build(new HttpService(RPC));
//            this.web3jws = Admin.build(new WebSocketService(RPC_WS,true));
//            this.ALICE = WalletUtils.loadCredentials(WALLET_PASSWORD, ALICE_KEY);
//            this.BOB = WalletUtils.loadCredentials(WALLET_PASSWORD,BOB_KEY);
//            this.TOM = WalletUtils.loadCredentials(WALLET_PASSWORD,TOM_KEY);
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//    }


}
