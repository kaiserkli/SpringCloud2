package com.example.common.dao.mybatis;

import com.example.common.entity.BaseEntity;

public interface BaseDAO<E extends BaseEntity> extends GenericDao<E, Integer> {

}
