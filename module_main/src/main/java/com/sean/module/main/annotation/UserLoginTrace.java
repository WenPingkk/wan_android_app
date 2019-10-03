package com.sean.module.main.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * 这是 AOSP使用，用来拦截非登录状态
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserLoginTrace {
//    int value();
}

