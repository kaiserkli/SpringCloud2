package com.example.common.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础数据库操作接口
 * @author Lit
 * @since 2017/05/08
 * @param <E> 实体范型
 * @param <ID> ID标示
 */
public interface GenericDao<E, ID extends Serializable> {

	public E getById(ID id);
	
	public List<E> getList(Map<String, Object> params);
	
	public int insert(E entity);
	
	public int update(E entity);
	
	public int updateBySelective(E entity);

	public int deleteById(ID id);
}
