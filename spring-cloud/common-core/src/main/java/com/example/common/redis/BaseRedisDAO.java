package com.example.common.redis;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class BaseRedisDAO<K, V> implements GenericRedisDAO<K, V> {

	protected abstract RedisTemplate<K, V> getRedisTemplate(); 

	public void set(K key, V value) {
		ValueOperations<K, V> valueOperations = getRedisTemplate().opsForValue();
		valueOperations.set(key, value);
	}

	public V get(K key) {
		return getRedisTemplate().opsForValue().get(key);
	}

	public boolean expire(K key, long expire) {
		return getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
	}

	public boolean setList(K key, List<V> list) {
		return false;
	}

	public List<V> getList(K key, Class<V> clazz) {
		return null;
	}

	public long lpush(K key, V value) {
		return 0;
	}

	public long rpush(K key, V value) {
		return 0;
	}

	public K lpop(K key) {
		return null;
	}

	public void hset(K name, K key, V value) {
		BoundHashOperations<K, K, V> boundHashOperations = getRedisTemplate().boundHashOps(name);
		boundHashOperations.put(key, value);
	}
	
	public V hget(K name, K key) {
		BoundHashOperations<K, K, V> boundHashOperations = getRedisTemplate().boundHashOps(name);
		return boundHashOperations.get(key);
	}
	
	public void hset(K name, Map<K, V> map) {
		BoundHashOperations<K, K, V> boundHashOperations = getRedisTemplate().boundHashOps(name);
		boundHashOperations.putAll(map);
	}
}
