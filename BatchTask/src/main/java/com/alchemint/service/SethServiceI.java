package com.alchemint.service;


/**
 * Created by cheng on 2019/3/8.
 */
public interface SethServiceI {


    /**
     *  开始执行seth合约事件
     *
     *  @return void
     **/
    void depositEventProcess();

    /**
     *  开始执行seth合约事件
     *
     *  @return void
     **/
    void withdrawEventProcess();

    /**
     *  开始执行sdusd合约事件
     *
     *  @return void
     **/
    void mintEventProcess();

    /**
     *  开始执行sdusd合约事件
     *
     *  @return void
     **/
    void burnEventProcess();

}
