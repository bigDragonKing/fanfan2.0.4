package com.fanfan.alon;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 功能描述:spring boot启动类
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/20   18:13
 */
@EnableScheduling
//@EnableCaching
@SpringBootApplication
@MapperScan("com.fanfan.alon.map")
public class Application {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class,args);
    }
}
