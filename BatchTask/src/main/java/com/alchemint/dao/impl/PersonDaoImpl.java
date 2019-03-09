package com.alchemint.dao.impl;

import com.alchemint.bo.Person;
import com.alchemint.dao.PersonDaoI;
import org.springframework.stereotype.Repository;

/**
 * Created by cheng on 2018/12/14.
 */
@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDaoI {
}
