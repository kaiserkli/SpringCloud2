package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 标注类或方法使用需要认证
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {
}
