package com.alchemint.dao;

import com.alchemint.bo.Page;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/12/14.
 * @Description 数据查询接口
 * @author steel
 * @date 2018-12-14
 */
public interface BaseDaoI<T> {

    void createCollection(T object);

    List<T> findList(Query query);

    List<T> findList(int skip, int limit);

    T findOneByItems(Map<String, Object> params);

    List<T> findByItems(Map<String, Object> params);

    List<T> findListByPageAndItems(int skip, int rows, Map<String, Object> params);

    // 分页查询
    Page<T> findPage(Page<T> page, Query query);

    void insert(T t);

    void update(Query query, Update update);

    void update(String id, Map<String, Object> params, T t);

    long count(Query query);

    long count(Map<String, Object> params);

    void deleteById(String id);

    void saveFile(File file, String fileUrl);

    GridFSDBFile retrieveFileOne(String filename);
}
