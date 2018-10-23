package com.fanfan.alon.webSocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * 功能描述:webSession聊天拦截器；websocket握手拦截器，检查握手请求和响应，对websocketHandler传递对象用于区别websocket
 * @param:
 * @return: 
 * @auther: zoujiulong
 * @date: 2018/10/19   15:10
 */
public class ChatInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //为了区分链接，之前是不是通过用户名来区分是谁的，此处还是一样的逻辑，通过名字区分
        //获取用户的名字，因为地址使用的rest风格，定义的是地址的最后一位是名字，所以此处只需找到请求地址，让背后拿到最后一位就行
        System.out.println("握手");
        String url = request.getURI().toString();
        String name = url.substring(url.lastIndexOf("/")+1);
        attributes.put("name",name);//建议将name抽取为静态常量
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("握手之后");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
