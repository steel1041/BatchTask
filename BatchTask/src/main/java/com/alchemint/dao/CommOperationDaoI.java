package com.alchemint.dao;

import com.alchemint.bo.CommOperation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface CommOperationDaoI extends BaseDaoI<CommOperation> {

    void saveCommOperation(CommOperation operation);
}
