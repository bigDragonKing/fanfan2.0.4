package com.fanfan.alon.service.impl;

import com.fanfan.alon.event.MyEvent;
import com.fanfan.alon.map.FanPersionMapper;
import com.fanfan.alon.models.FanPersion;
import com.fanfan.alon.service.FanPersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FanPersionServiceImpl implements FanPersionService {

    @Autowired
    private FanPersionMapper persionMapper;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void isertUser(FanPersion fanPersion) {
        // 插入用户信息
        persionMapper.insertUser(fanPersion);
        // 手动抛出异常
        throw new RuntimeException();
    }

    /**
     * 功能描述:模拟监听器  获取用户信息（用作缓存）
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   19:29
     */

    public FanPersion getUser() {
        // 实际中会根据具体的业务场景，从数据库中查询对应的信息
        return new FanPersion(1L, "Alon", "123456");
    }

    /**
     * 功能描述:发布事件
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   21:01
     */
    public FanPersion getUser2() {
        FanPersion user = new FanPersion(1L, "Alon", "123456");
        // 发布事件
        MyEvent event = new MyEvent(this, user);
        applicationContext.publishEvent(event);
        return user;
    }
}
