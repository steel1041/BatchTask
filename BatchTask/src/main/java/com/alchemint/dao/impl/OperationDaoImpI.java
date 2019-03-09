package com.alchemint.dao.impl;

import com.alchemint.bo.Operation;
import com.alchemint.dao.OperationDaoI;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by cheng on 2018/12/14.
 */
@Repository
public class OperationDaoImpI extends BaseDaoImpl<Operation> implements OperationDaoI{

    @Override
    public void saveOperation(Operation operation) {
        //保存操作历史，根据txid判断唯一
        String txid = operation.getTxid();

        Query query = new Query();
        Criteria criteria = Criteria.where("txid").is(txid);
        query.addCriteria(criteria);

        long num = count(query);
        if(num <= 0) {
            insert(operation);
        }
    }
}
