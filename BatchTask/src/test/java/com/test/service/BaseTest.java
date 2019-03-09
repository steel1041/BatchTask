package com.test.service;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath:spring/spring-db.xml"}) //加载配置文件
public class BaseTest {
	
    public final static Logger logger = LoggerFactory.getLogger(BaseTest.class);

    public static final String RPC = "http://47.75.103.144:8546/";

    public static final String RPC_WS = "ws://47.75.103.144:8545/";


    @Autowired
    public MongoTemplate mongoTemplate;

}
