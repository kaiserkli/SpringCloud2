package com.example.common.redis;

import java.util.List;
import java.util.Map;

public interface GenericRedisDAO<K, V> {
	
	public void set(K key, V value);

	public V get(K key);

	public boolean expire(K key, long expire);

	public boolean setList(K key, List<V> list);

	public List<V> getList(K key, Class<V> clazz);

	public long lpush(K key, V value);

	public long rpush(K key, V value);

	public K lpop(K key);
	
	public void hset(K name, K key, V value);
	
	public V hget(K name, K key);
	
	public void hset(K name, Map<K, V> map);
}
