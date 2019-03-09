package com.alchemint.dao.impl;

import com.alchemint.bo.SarInfo;
import com.alchemint.dao.SarInfoDaoI;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by cheng on 2018/12/14.
 */
@Repository
public class SarInfoDaoImpI extends BaseDaoImpl<SarInfo> implements SarInfoDaoI{

    @Override
    public void saveSarInfo(SarInfo info) {
        //保存SarInfo，根据txid判断唯一
        String txid = info.getSarTxid();

        Query query = new Query();
        Criteria criteria = Criteria.where("sarTxid").is(txid);
        query.addCriteria(criteria);

        long num = count(query);
        if(num <= 0) {
            insert(info);
        }
    }

    @Override
    public SarInfo findSarByAddr(String addr, String asset) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("addr").is(addr),
                Criteria.where("asset").is(asset),
                Criteria.where("status").is(1)
        );
        query.addCriteria(criteria);
        List<SarInfo> sars = findList(query);
        if(sars == null || sars.isEmpty()){
            return new SarInfo();
        }
        return findList(query).get(0);
    }
}
