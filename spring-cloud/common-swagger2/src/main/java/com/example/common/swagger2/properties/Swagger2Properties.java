package com.example.common.swagger2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2Properties {
    private boolean enabled;
    private String basePackage;
    private ApiInfo apiInfo;

    @Data
    public static class ApiInfo {
        private String title = "";
        private String name;
        private String url;
        private String email;
        private String version;
    }
}