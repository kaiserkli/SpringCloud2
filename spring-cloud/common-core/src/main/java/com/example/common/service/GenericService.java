package com.example.common.service;

import com.example.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericService<E extends BaseEntity, ID extends Serializable> {

    public E get(ID id);

    public void save(E entity);

    public void delete(ID id);

    public List<E> getList(Map<String, Object> params);
}
