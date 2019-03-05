package com.example.common.advice;

import static com.example.common.web.response.ResponseMessage.TOKEN_TIMEOUT;
import static com.example.common.constants.HeaderConstant.HEADER_ACCESS_TOKEN;

import com.example.common.annotation.Authentication;
import com.example.common.exception.BusinessException;
import com.example.common.security.service.CustomUserDetailsService;
import com.example.common.util.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class AuthenticationHandler extends HandlerInterceptorAdapter {

    @Autowired
    protected CustomUserDetailsService customUserDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }

        final HandlerMethod handlerMethod = (HandlerMethod)handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();

        if(clazz.isAnnotationPresent(Authentication.class) || method.isAnnotationPresent(Authentication.class)){
            handlerSession(request);
        }

        return true;
    }

    public void handlerSession(HttpServletRequest request) throws Exception{
        String accessToken = request.getHeader(HEADER_ACCESS_TOKEN);

        if(accessToken == null){
            throw new BusinessException("请先登录。");
        }

        AccountInfo accountInfo = customUserDetailsService.getSession(accessToken);

        if(accountInfo == null){
            log.error("登录超时！");
            throw new BusinessException(TOKEN_TIMEOUT);
        }
    }
}
