package com.fanfan.alon.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextMessageHandler extends TextWebSocketHandler {

    private Map<String,WebSocketSession> allClient = new HashMap<String,WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonObject = JSON.parseObject(new String(message.asBytes()));
        String to = jsonObject.get("toUser").toString();//找到接收者
        String toMessage = jsonObject.get("toMessage").toString();//获取发送的内容
        String fromUser = (String) session.getAttributes().get("name");
        String content = "收到来自" + fromUser + "的消息，内容是" + toMessage;
        TextMessage toTextMessage = new TextMessage(content);//创建消息对象
        sendMessage(to,toTextMessage);
    }
    //当链接建立的时候调用
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String name = (String) session.getAttributes().get("name");//获取到拦截中设置的name
        if(StringUtils.isNotEmpty(name)){
            allClient.put(name,session);//保存当前用户和链接的关系
        }
    }
    //当链接关闭的时候调用
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    public void sendMessage(String toUser,TextMessage textMessage){

        //获取到对方的链接
        WebSocketSession session = allClient.get(toUser);
        if(null != session && session.isOpen()){
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
