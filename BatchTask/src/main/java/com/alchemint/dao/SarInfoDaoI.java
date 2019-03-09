package com.alchemint.dao;

import com.alchemint.bo.SarInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface SarInfoDaoI extends BaseDaoI<SarInfo> {

    void saveSarInfo(SarInfo info);

    SarInfo findSarByAddr(String addr,String asset);
}
