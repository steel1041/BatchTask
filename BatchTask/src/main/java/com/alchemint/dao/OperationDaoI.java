package com.alchemint.dao;

import com.alchemint.bo.Operation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface OperationDaoI extends BaseDaoI<Operation> {

    void saveOperation(Operation operation);
}
