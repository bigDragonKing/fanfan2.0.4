package com.fanfan.alon.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 功能描述:使用HttpSessionListener统计在线用户数的监听器
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/23   19:48
 */
@Component
public class MyHttpSessionListener implements HttpSessionListener{
    private static final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    /**记录在线的用户数量*/
    public Long count = 0L;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("新用户上线了");
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("count", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("用户下线了");
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("count", count);
    }
}
