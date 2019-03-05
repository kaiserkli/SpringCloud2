package com.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccessTokenFilter implements GlobalFilter, Ordered {

    @Value("${anonymous-uris}")
    private String anonymousUris;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //从Header中获取相关参数
        String code = exchange.getRequest().getHeaders().getFirst("app-code");
        String secret = exchange.getRequest().getHeaders().getFirst("app-secret");
        String accessToken = exchange.getRequest().getHeaders().getFirst("access-token");
        String refreshToken = exchange.getRequest().getHeaders().getFirst("refresh-token");

        ServerHttpRequest request = exchange.getRequest();

        log.info("APP_CODE: {}, APP_SECRET: {}, ACCESS_TOKEN: {}, REFRESH_TOKEN: {}", code, secret, accessToken, refreshToken);

        //用户判断是从那个平台过来的请求
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(secret)) {
           //throw new RuntimeException("无访问权限");
        }

        boolean isAnonymous = false;

        //当前URI是否为匿名可访问地址
        for (String uri : StringUtils.split(anonymousUris, ",")){
            if (StringUtils.isNotBlank(uri)){
                if (antPathMatcher.match(uri, request.getURI().getPath())) {
                    isAnonymous = true;
                    break;
                }
            }
        }

        if (!isAnonymous) {
            //如果是非匿名URI，对Access Token进行验证
            if (StringUtils.isEmpty(accessToken)) {
              throw new RuntimeException("请先登录");
            }

            //验证Access Token
            //
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
