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
        Map map = new HashMap<String, Object>();
        transferServiceI.transSETHEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/approveSETHEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> approveSETHEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***approveSETHEventProcess***");
        Map map = new HashMap<String, Object>();
        transferServiceI.approveSETHEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/depositEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> depositEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***depositEventProcess***");
        Map map = new HashMap<String, Object>();
        sethServiceI.depositEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/withdrawEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> withdrawEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***withdrawEventProcess***");
        Map map = new HashMap<String, Object>();
        sethServiceI.withdrawEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/transSDUSDEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> transSDUSDEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***transSDUSDEventProcess***");
        Map map = new HashMap<String, Object>();
        transferServiceI.transSDUSDEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/approveSDUSDEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> approveSDUSDEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***approveSDUSDEventProcess***");
        Map map = new HashMap<String, Object>();
        transferServiceI.approveSDUSDEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/mintEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> mintEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***mintEventProcess***");
        Map map = new HashMap<String, Object>();
        sethServiceI.mintEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/burnEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> burnEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***burnEventProcess***");
        Map map = new HashMap<String, Object>();
        sethServiceI.burnEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/oracleEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> oracleEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***oracleEventProcess***");
        Map map = new HashMap<String, Object>();
        oracleServiceI.oracleEventProcess();
        map.put("result", true);
        return map;
    }

    @RequestMapping(value="/sarEventProcess",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> sarEventProcess(HttpServletRequest request) throws Exception {
        logger.info("***sarEventProcess***");
        Map map = new HashMap<String, Object>();
        sarInfoServiceI.sarEventProcess();
        map.put("result", true);
        return map;
    }

}
