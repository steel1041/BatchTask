package com.alchemint.service;

/**
 * Created by cheng on 2019/3/8.
 */
public interface TransferServiceI {



    /**
     *  开始执行transfer合约事件
     *
     *  @return void
     **/
    void transSETHEventProcess();

    /**
     *  开始执行transfer合约事件
     *
     *  @return void
     **/
    void transSDUSDEventProcess();


    /**
     *  开始执行approve合约事件
     *
     *  @return void
     **/
    void approveSETHEventProcess();

    /**
     *  开始执行tapprove合约事件
     *
     *  @return void
     **/
    void approveSDUSDEventProcess();


}
