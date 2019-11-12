package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.configuration.ServerEncoder;
import com.woniu.mzjOrder.service.NetInformationService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(value = "/websocket/{sid}",encoders = {ServerEncoder.class})
@Slf4j
public class WebSocketServer {

    @Autowired
    private NetInformationService informationService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.put(sid, this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:{},sessionId为{},当前在线人数为{}", sid, session.getId(), getOnlineCount());
        log.info("当前在线实例名：{}", webSocketSet.keySet().toArray());
        this.sid = sid;
        //sendMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(sid);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        /*for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        this.sendMessage("来自"+session+"的消息");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("sid: {}发生错误,sessionId:{}", sid,session.getId());
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet.values()) {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if (sid == null) {
                item.sendMessage(message);
            } else if (item.sid.equals(sid)) {
                item.sendMessage(message);
            }
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(Object message) {
        //this.session.getBasicRemote().sendText(message);
        try {
            this.session.getBasicRemote().sendObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("websocket IO异常");
        } catch (EncodeException e) {
            e.printStackTrace();
            log.error("websocket Encoder异常");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.incrementAndGet();
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.decrementAndGet();
    }


}
