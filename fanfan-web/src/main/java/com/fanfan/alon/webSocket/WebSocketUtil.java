package com.fanfan.alon.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("webSocket/{name}")
public class WebSocketUtil {
    private String name;//记录当前websocket是谁
    private Session session;//链接，用于记录当前链接

    private static Map<String, WebSocketUtil> infoMap = new HashMap<String, WebSocketUtil>();

    @OnOpen
    public void onOpen(@PathParam("name") String name,
                       Session session){
        this.name = name;
        this.session = session;
        infoMap.put(name,this);
    }
    @OnMessage
    public void onMessage(Session session,String message){

        //解析内容，找到目标接收者
        JSONObject jsonObject = JSON.parseObject(message);
        String to = jsonObject.get("toUser").toString();//找到接收者
        String toMessage = jsonObject.get("toMessage").toString();//获取发送的内容
        //根据目标接收者，找到它的session链接
        WebSocketUtil result = infoMap.get(to);
        //通过session发送消息
        if(result != null){
            Session toSession = result.getSession();//获取到服务器和目标接收者的链接
            if(toSession.isOpen()){//如果链接是打开状态
                toSession.getAsyncRemote().sendText(toMessage);//找到链接的另外一段，然后发送消息
            }
        }else {
            //正常来说应该缓存这个消息，这里就先直接给发送者返回一条  对方不在线
            session.getAsyncRemote().sendText("对方不在线");
        }


    }
    @OnError
    public void onError(Session session,Throwable e){

    }
    @OnClose
    public void onClose(Session session){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static Map<String, WebSocketUtil> getInfoMap() {
        return infoMap;
    }

    public static void setInfoMap(Map<String, WebSocketUtil> infoMap) {
        WebSocketUtil.infoMap = infoMap;
    }
}
