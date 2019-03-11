package com.test.service;

import com.alchemint.service.OracleServiceI;
import com.alchemint.service.SarInfoServiceI;
import com.alchemint.service.SethServiceI;
import com.alchemint.service.TransferServiceI;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cheng on 2019/3/11.
 */
public class EventTaskTest extends BaseTest {

    @Autowired
    private OracleServiceI oracleServiceI;

    @Autowired
    private SarInfoServiceI sarInfoServiceI;

    @Autowired
    private SethServiceI sethServiceI;

    @Autowired
    private TransferServiceI transferServiceI;

    @Test
    public void transSETHEventProcess(){
        transferServiceI.transSETHEventProcess();
    }
    @Test
    public void approveSETHEventProcess(){
        transferServiceI.approveSETHEventProcess();
    }

    @Test
    public void depositEventProcess(){
        sethServiceI.depositEventProcess();
    }

    @Test
    public void withdrawEventProcess(){
        sethServiceI.withdrawEventProcess();
    }

    @Test
    public void transSDUSDEventProcess(){
        transferServiceI.transSDUSDEventProcess();
    }

    @Test
    public void approveSDUSDEventProcess(){
        transferServiceI.approveSDUSDEventProcess();
    }

    @Test
    public void mintEventProcess(){
        sethServiceI.mintEventProcess();
    }

    @Test
    public void burnEventProcess(){
        sethServiceI.burnEventProcess();
    }

    @Test
    public void startOracle(){
        oracleServiceI.oracleEventProcess();
    }

    @Test
    public void startSar(){
        sarInfoServiceI.sarEventProcess();
    }

}
