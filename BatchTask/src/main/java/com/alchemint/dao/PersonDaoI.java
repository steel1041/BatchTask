package com.alchemint.dao;

import com.alchemint.bo.Person;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheng on 2018/12/14.
 */
@Transactional
public interface PersonDaoI extends BaseDaoI<Person> {
}
