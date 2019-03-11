package com.alchemint.dao;

import com.alchemint.bo.Transfer;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface TransferDaoI extends BaseDaoI<Transfer> {

    void saveTransfer(Transfer transfer);
}
