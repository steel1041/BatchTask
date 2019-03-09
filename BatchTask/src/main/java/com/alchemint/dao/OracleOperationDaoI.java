package com.alchemint.dao;

import com.alchemint.bo.OracleOperation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface OracleOperationDaoI extends BaseDaoI<OracleOperation> {

    void saveOracleOperation(OracleOperation operation);
}
