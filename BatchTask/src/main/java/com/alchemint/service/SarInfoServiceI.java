package com.alchemint.service;

/**
 * Created by cheng on 2019/3/8.
 */
public interface SarInfoServiceI {


    /**
     *  开始执行sar合约事件
     *
     *  @return void
     **/
    void sarEventProcess();

    /**
     *  开始执行sar合约事件
     *
     *  @return void
     **/
    void sarFeeEventProcess();
}
