package com.fanfan.alon.annotation;

import java.lang.annotation.*;

/**
 * 功能描述:数据过滤
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
    /**  表的别名 */
    String tableAlias() default "";

    /**  true：没有本部门数据权限，也能查询本人数据 */
    boolean user() default true;

    /**  true：拥有子部门数据权限 */
    boolean subDept() default false;

    /**  部门ID */
    String deptId() default "dept_id";

    /**  用户ID */
    String userId() default "user_id";
}

