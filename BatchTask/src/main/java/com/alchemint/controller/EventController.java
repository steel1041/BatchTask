package com.alchemint.controller;

import com.alchemint.service.OracleServiceI;
import com.alchemint.service.SarInfoServiceI;
import com.alchemint.service.SethServiceI;
import com.alchemint.service.TransferServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng on 2019/3/11.
 */
@Controller()
@RequestMapping("event")
public class EventController {

    public final static Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private OracleServiceI oracleServiceI;

    @Autowired
    private SarInfoServiceI sarInfoServiceI;

    @Autowired
    private SethServiceI sethServiceI;

    @Autowired
    private TransferServiceI transferServiceI;

    @RequestMapping(value="/transSETHEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> transSETHEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***transSETHEventProcess***");
        Map map = new HashMap<String, Object>(1);
        transferServiceI.transSETHEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/approveSETHEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> approveSETHEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***approveSETHEventProcess***");
        Map map = new HashMap<String, Object>(1);
        transferServiceI.approveSETHEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/depositEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> depositEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***depositEventProcess***");
        Map map = new HashMap<String, Object>(1);
        sethServiceI.depositEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/withdrawEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> withdrawEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***withdrawEventProcess***");
        Map map = new HashMap<String, Object>(1);
        sethServiceI.withdrawEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/transSDUSDEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> transSDUSDEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***transSDUSDEventProcess***");
        Map map = new HashMap<String, Object>(1);
        transferServiceI.transSDUSDEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/approveSDUSDEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> approveSDUSDEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***approveSDUSDEventProcess***");
        Map map = new HashMap<String, Object>(1);
        transferServiceI.approveSDUSDEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/mintEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> mintEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***mintEventProcess***");
        Map map = new HashMap<String, Object>(1);
        sethServiceI.mintEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/burnEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> burnEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***burnEventProcess***");
        Map map = new HashMap<String, Object>(1);
        sethServiceI.burnEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/oracleEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> oracleEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***oracleEventProcess***");
        Map map = new HashMap<String, Object>(1);
        oracleServiceI.oracleEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/sarEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> sarEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***sarEventProcess***");
        Map map = new HashMap<String, Object>(1);
        sarInfoServiceI.sarEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/sarFeeEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> sarFeeEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***sarFeeEventProcess***");
        Map map = new HashMap<String, Object>(1);
        sarInfoServiceI.sarFeeEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/startAllEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> startAllEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***startAllEventProcess***");
        Map map = new HashMap<String, Object>(1);
        TransSETHEventProcessThread thread = new TransSETHEventProcessThread();
        thread.start();

        ApproveSETHEventProcessThread thread1 = new ApproveSETHEventProcessThread();
        thread1.start();

        DepositEventProcessThread thread2 = new DepositEventProcessThread();
        thread2.start();

        WithdrawEventProcessThread thread3 = new WithdrawEventProcessThread();
        thread3.start();

        TransSDUSDEventProcessThread thread4 = new TransSDUSDEventProcessThread();
        thread4.start();

        ApproveSDUSDEventProcessThread thread5 = new ApproveSDUSDEventProcessThread();
        thread5.start();

        MintEventProcessThread thread6 = new MintEventProcessThread();
        thread6.start();

        BurnEventProcessThread thread7 = new BurnEventProcessThread();
        thread7.start();

        OracleEventProcessThread thread8 = new OracleEventProcessThread();
        thread8.start();

        SarEventProcessThread thread9 = new SarEventProcessThread();
        thread9.start();

        SarFeeEventProcessThread thread10 = new SarFeeEventProcessThread();
        thread10.start();
        map.put("result", true);
        return map;
    }


    class TransSETHEventProcessThread extends Thread{
        @Override
        public void run(){
            transferServiceI.transSETHEventProcess();
        }
    }

    class ApproveSETHEventProcessThread extends Thread{

        @Override
        public void run(){
            transferServiceI.approveSETHEventProcess();
        }
    }

    class DepositEventProcessThread extends Thread{
        @Override
        public void run(){
            sethServiceI.depositEventProcess();
        }
    }
    class  WithdrawEventProcessThread extends Thread{
        @Override
        public void run(){
            sethServiceI.withdrawEventProcess();
        }
    }

    class TransSDUSDEventProcessThread extends Thread{
        @Override
        public void run(){
            transferServiceI.transSDUSDEventProcess();
        }
    }

    class ApproveSDUSDEventProcessThread extends Thread{
        @Override
        public void run(){
            transferServiceI.approveSDUSDEventProcess();
        }
    }

    class MintEventProcessThread extends Thread{
        @Override
        public void run(){
            sethServiceI.mintEventProcess();
        }
    }

    class BurnEventProcessThread extends Thread{

        @Override
        public void run(){
            sethServiceI.burnEventProcess();
        }
    }

    class OracleEventProcessThread extends Thread{

        @Override
        public void run(){
            oracleServiceI.oracleEventProcess();
        }
    }

    class SarEventProcessThread extends Thread{
        @Override
        public void run(){
            sarInfoServiceI.sarEventProcess();
        }
    }

    class SarFeeEventProcessThread extends Thread{
        @Override
        public void run(){
            sarInfoServiceI.sarFeeEventProcess();
        }
    }
}
