package com.fanfan.alon.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:该注解用来指定某个方法不用拦截
 * @param:
 * @return: 
 * @auther: zoujiulong
 * @date: 2018/8/24   14:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnInterception {
}
