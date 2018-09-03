package com.fanfan.alon.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   11:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ESearchTypeColumn {

    /**
     * 功能描述:分词器设置
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:51
     */
    String analyzer() default "";

    /**
     * 功能描述:是否存在同义词分词
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:51
     */
    boolean synonym() default false;
}
