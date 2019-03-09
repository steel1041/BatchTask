package com.alchemint.dao.impl;

import com.alchemint.bo.Page;
import com.alchemint.dao.BaseDaoI;
import com.alchemint.utils.ReflectionUtils;
import com.mongodb.gridfs.GridFSDBFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


/**
 * Created by cheng on 2018/12/14.
 */
@Repository
public class BaseDaoImpl<T> implements BaseDaoI<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

    public static final String FILEURL="imgRespository";

    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<?> clz;

    public Class<?> getClz() {
        LOGGER.info("getClz()");
        if(clz==null) {
            LOGGER.info("初始化：getClz()");
            //获取泛型的Class对象
            clz = ((Class<?>)
                    (((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }
    public BaseDaoImpl() {
        LOGGER.info("BaseDaoImpl()");
    }

    @Override
    public void createCollection(T object) {
        LOGGER.info("createCollection_000");
        if (!this.mongoTemplate.collectionExists(getClz())) {
            this.mongoTemplate.createCollection(getClz());
        }

    }

    @Override
    public List<T> findList(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> findList(int skip, int limit) {
        Query query = new Query();
        query.with(new Sort(new Order(Direction.ASC, "_id")));
        query.skip(skip).limit(limit);
        return (List<T>)this.mongoTemplate.find(query, getClz());
    }

    @Override
    public T findOneByItems(Map<String, Object> params) {
        Query query = new Query();
        if ((params != null) && (!(params.isEmpty()))) {
            for (String key : params.keySet()) {
                query.addCriteria(new Criteria(key).is(params.get(key)));
            }
        }
        return (T)mongoTemplate.findOne(query, getClz());
    }

    @Override
    public List<T> findByItems(Map<String, Object> params) {
        Query query = new Query();
        if ((params != null) && (!(params.isEmpty()))) {
            for (String key : params.keySet()) {
                query.addCriteria(new Criteria(key).is(params.get(key)));
            }
        }
        return (List<T>)this.mongoTemplate.find(query, getClz());
    }

    @Override
    public List<T> findListByPageAndItems(int skip, int rows,
                                          Map<String, Object> params) {
        Query query = new Query();
        if ((params != null) && (!(params.isEmpty()))) {
            for (String key : params.keySet()) {
                query.addCriteria(new Criteria(key).is(params.get(key)));
            }
        }
        query.skip(skip).limit(rows);
        return (List<T>)this.mongoTemplate.find(query, getClz());
    }

    @Override
    public Page<T> findPage(Page<T> page, Query query) {
        int count = (int)this.count(query);
        page.setTotalCount(count);
        int pageSize = page.getPageSize();
        query.skip(page.getStartIndex()).limit(pageSize);
        List<T> rows = this.findList(query);
        page.setRows(rows);
        return page;
    }


    public void insert(T t) {
        this.mongoTemplate.insert(t);
    }

    @Override
    public long count(Query query){
        return mongoTemplate.count(query, this.getEntityClass());
    }

    @Override
    public long count(Map<String, Object> params) {
        Query query = new Query();
        if ((params != null) && (!(params.isEmpty()))) {
            for (String key : params.keySet()) {
                query.addCriteria(new Criteria(key).is(params.get(key)));
            }
        }
        return (long)mongoTemplate.find(query, getClz()).size();
    }

    @Override
    public void update(Query query, Update update) {
        mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    @Override
    public void update(String id, Map<String, Object> params,T t) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        Update update = new Update();
        if ((params != null) && (!(params.isEmpty()))) {
            for (String key : params.keySet()) {
                update.set(key, params.get(key));
            }
        }
        this.mongoTemplate.updateFirst(query, update,getClz());
    }
    @Override
    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), getClz());
    }


    @Override
    public void saveFile(File file, String fileUrl) {
//        try {
//            DB db = mongoTemplate.getDb();
//            GridFS fs = new GridFS(db, FILEURL);
//            GridFSInputFile inputFile = fs.createFile(file);
//            inputFile.setFilename(fileUrl);
//            inputFile.setContentType(fileUrl.substring(fileUrl.lastIndexOf(".")));
//            inputFile.save();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public GridFSDBFile retrieveFileOne(String filename) {
//        try {
//            DB db = mongoTemplate.getDb();
//            // 获取fs的根节点
//            GridFS gridFS = new GridFS(db, FILEURL);
//            GridFSDBFile dbfile = gridFS.findOne(filename);
//            if (dbfile != null) {
//                return dbfile;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    private Class<T> getEntityClass(){
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }
}
