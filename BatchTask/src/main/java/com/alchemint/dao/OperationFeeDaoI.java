package com.alchemint.dao;

import com.alchemint.bo.OperationFee;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface OperationFeeDaoI extends BaseDaoI<OperationFee> {

    void saveOperationFee(OperationFee operation);
}
