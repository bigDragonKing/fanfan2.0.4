package com.fanfan.alon.annotation;

import java.lang.annotation.*;

/**
 * 功能描述:系统日志注解
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
