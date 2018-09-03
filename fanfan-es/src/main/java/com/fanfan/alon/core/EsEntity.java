package com.fanfan.alon.core;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
@Component
public @interface EsEntity {

    String indexName() default "";

    String typeName() default "";
}
