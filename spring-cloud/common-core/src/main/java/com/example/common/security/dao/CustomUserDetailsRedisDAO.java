package com.example.common.security.dao;

import static com.example.common.constants.WebSecurityConstant.ACCESS_TOKEN_EXPIRATION;
import static com.example.common.constants.WebSecurityConstant.REFRESH_TOKEN_EXPIRATION;

import com.example.common.redis.BaseRedisDAO;
import com.example.common.util.AccountInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class CustomUserDetailsRedisDAO extends BaseRedisDAO<String, AccountInfo> {

    @Resource
    private RedisTemplate<String, AccountInfo> redisTemplate;

    public void setUserDetails(String key, AccountInfo value) {
        ValueOperations<String, AccountInfo> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, ACCESS_TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    /**
     * 刷新token放入redis（无过期时间）
     * @param key refreshToken
     * @param value
     */
    public void setRefreshDetail(String key, AccountInfo value){
        ValueOperations<String, AccountInfo> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, REFRESH_TOKEN_EXPIRATION, TimeUnit.DAYS);
    }

    /**
     * 删除redis中的用户登录信息
     * @param key
     */
    public void deleteUserDetail(String key){
        expire(key, 0);
    }

    @Override
    protected RedisTemplate<String, AccountInfo> getRedisTemplate() {
        return redisTemplate;
    }

}
