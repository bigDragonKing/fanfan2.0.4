package com.fanfan.alon.listener;

import com.fanfan.alon.event.MyEvent;
import com.fanfan.alon.models.FanPersion;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        // 把事件中的信息获取到
        FanPersion user = myEvent.getFanPersion();
        // 处理事件，实际项目中可以通知别的微服务或者处理其他逻辑等等
        System.out.println("用户名：" + user.getpName());
        System.out.println("作者：" + user.getpAuthor());

    }
}
