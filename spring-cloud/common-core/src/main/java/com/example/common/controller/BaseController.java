package com.example.common.controller;

import com.example.common.constants.HeaderConstant;
import com.example.common.exception.BusinessException;
import com.example.common.security.service.CustomUserDetailsService;
import com.example.common.util.AccountInfo;
import com.example.common.web.response.ResponseEntity;
import com.example.common.web.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 公共控制基础
 */
@Slf4j
public class BaseController {

    @Autowired
    protected CustomUserDetailsService customUserDetailsService;

    protected ResponseEntity success() {
        return success(null);
    }

    protected ResponseEntity failure() {
        return failure(null);
    }

    protected ResponseEntity success(Object data) {
        return success(null, data);
    }

    protected ResponseEntity success(String message, Object data) {
        ResponseEntity responseEntity = ResponseEntity.factory();
        responseEntity.setMessage(message);
        responseEntity.setData(data);
        return responseEntity;
    }

    protected ResponseEntity failure(String message) {
        ResponseEntity responseEntity = ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE);

        if (message != null && !"".equals(message.trim())) {
            responseEntity.setMessage(message);
        }
        return responseEntity;
    }

    /**
     * 通过Access Token获取登录账户信息
     * @param request
     * @return AccountInfo
     */
    protected AccountInfo getAccountInfo(HttpServletRequest request) {

        // 从Header中获取Access-Token数据
        String accessToken = request.getHeader(HeaderConstant.HEADER_ACCESS_TOKEN);

        // 从Cookie中获取Access-Token数据
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(HeaderConstant.HEADER_ACCESS_TOKEN)) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }

        if(accessToken == null){
            log.error("Access Token为空！");
            throw new BusinessException("Access Token为空！");
        }

        AccountInfo accountInfo = customUserDetailsService.getSession(accessToken);

        if(accountInfo == null){
            log.error("账户登录超时！");
            throw new BusinessException(ResponseMessage.TOKEN_TIMEOUT);
        }

        return accountInfo;
    }
}
