package com.example.common.security.service;

import com.example.common.security.dao.CustomUserDetailsRedisDAO;
import com.example.common.util.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {

    @Autowired
    private CustomUserDetailsRedisDAO dao;

    /**
     * 保存登录信息
     * @param accountInfo
     */
    public void createSession(AccountInfo accountInfo) {
        dao.setUserDetails(accountInfo.getAccessToken(), accountInfo);
    }

    /**
     * 刷新登录信息
     * @param accountInfo
     */
    public void refreshSession(AccountInfo accountInfo) {
        dao.setUserDetails(accountInfo.getAccessToken(), accountInfo);
    }

    /**
     * 将refreshToken作为key将信息存入redis
     * @param accountInfo
     */
    public void createRefreshSession(AccountInfo accountInfo) {
        dao.setRefreshDetail(accountInfo.getRefreshToken(), accountInfo);
    }

    /**
     * 根据request获取登录信息
     * @param key
     * @return
     */
    public AccountInfo getSession(String key) {
        return (AccountInfo) dao.get(key);
    }

    public void deleteUserDetail(String key){
        dao.deleteUserDetail(key);
    }
}
