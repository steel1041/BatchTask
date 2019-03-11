package com.alchemint.dao;

import com.alchemint.bo.Approval;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface ApprovalDaoI extends BaseDaoI<Approval> {

    void saveApproval(Approval approval);
}
