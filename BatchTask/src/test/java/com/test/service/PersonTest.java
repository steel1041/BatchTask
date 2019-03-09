package com.test.service;

import com.alchemint.bo.Person;
import com.alchemint.dao.PersonDaoI;
import com.alchemint.service.OracleServiceI;
import com.alchemint.service.SarInfoServiceI;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang.math.RandomUtils;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

/**
 * Created by cheng on 2018/12/13.
 */
public class PersonTest extends BaseTest {

    @Autowired
    private PersonDaoI personDaoI;

    @Autowired
    private SarInfoServiceI sarInfoServiceI;

    @Autowired
    private OracleServiceI oracleServiceI;

    @Test
    public void testSarInfo(){
        sarInfoServiceI.sarEventProcess();
    }

    @Test
    public void testOracle(){
        oracleServiceI.oracleEventProcess();
    }
    @Test
    public void testInsert(){

        for (int i=0;i<100;i++) {
            Person p = new Person();
            int random = RandomUtils.nextInt(10);
            p.setName("cheng"+random);
            p.setAge((30+i));
            p.setMount((983.21+i));

            List<String> addrs = new ArrayList<String>();
            addrs.add("one"+i);
            addrs.add("two"+i);
            addrs.add("three"+i);
            p.setTime(new Date());
            p.setAddrs(addrs);

            personDaoI.insert(p);
        }
    }

    @Test
    public void testSelect(){
        Query query = new Query();
        Criteria criteria = Criteria.where("name").is("cheng0");
        query.addCriteria(criteria);

        List<Person> pers = personDaoI.findList(query);
        for (Person p:pers){
            logger.info(p.toString());
        }

        Query query2 = new Query();
        logger.info(personDaoI.count(query2)+"");

    }

    @Test
    public void testAggregation()
    {
        TypedAggregation<Person> agg = Aggregation.newAggregation(
            Person.class,
            //Aggregation.group("name").sum("mount").as("total"),
                Aggregation.group("name").avg("mount").as("avgMount")

        );

        AggregationResults<Document> result = mongoTemplate.aggregate(agg, Document.class);

        List<Document> resultList = result.getMappedResults();

        Document bd = resultList.get(0);

        for(Document dbo : resultList){
            String name = dbo.getString("_id");
            //double total = dbo.getDouble("total");
            double avgMount = dbo.getDouble("avgMount");
            logger.info("name:"+name+"/avgMount:"+avgMount);
        }
    }




}
