package com.alchemint.service;

import com.alchemint.bo.Operation;
import org.web3j.protocol.core.methods.response.Log;

/**
 * Created by cheng on 2019/3/8.
 */
public interface SarInfoServiceI {


    /**
     *  保存sar合约操作记录
     *  @param log
     *  @param operation
     *  @return void
     **/
    void saveOperation(Log log, Operation operation);

    /**
     *  开始执行sar合约事件
     *
     *  @return void
     **/
    void sarEventProcess();
}
