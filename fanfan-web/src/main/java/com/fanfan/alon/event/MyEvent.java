package com.fanfan.alon.event;

import com.fanfan.alon.models.FanPersion;
import org.springframework.context.ApplicationEvent;

/**
 * 功能描述:自定义事件
 * @param:
 * @return: 
 * @auther: zoujiulong
 * @date: 2018/8/23   20:30
 */
public class MyEvent extends ApplicationEvent{
    private FanPersion fanPersion;
    public MyEvent(Object source, FanPersion fanPersion) {
        super(source);
        this.fanPersion = fanPersion;
    }

    public FanPersion getFanPersion() {
        return fanPersion;
    }

    public void setFanPersion(FanPersion fanPersion) {
        this.fanPersion = fanPersion;
    }
}
