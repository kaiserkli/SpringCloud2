package com.example.common.service;

import com.example.common.dao.mybatis.BaseDAO;
import com.example.common.entity.BaseEntity;
import com.example.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class BaseService<D extends BaseDAO<E>, E extends BaseEntity> implements GenericService<E, Integer> {

    @Autowired
    protected D dao;

    public E get(Integer id) {
        return dao.getById(id);
    }

    @Transactional
    @Override
    public void save(E entity) {
        StringBuilder sb = new StringBuilder();
        String clazz = entity.getClass().getSimpleName();
        sb.append(Character.toLowerCase(clazz.charAt(0))).append(clazz.substring(1)).append("Id");

        try {
            Field field = entity.getClass().getDeclaredField(sb.toString());
            Object value = field.get(entity);

            if (value == null) {
                dao.insert(entity);
            } else {
                dao.update(entity);
            }
        } catch (NoSuchFieldException e) {
            throw new BusinessException("其他错误");
        } catch (IllegalAccessException e) {
            throw new BusinessException("其他错误");
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public List<E> getList(Map<String, Object> params) {
        return dao.getList(params);
    }
}
