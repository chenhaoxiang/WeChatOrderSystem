package cn.chenhaoxiang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 10:05.
 * Explain:
 */
@Component
@ServerEndpoint("/webSocket") //url
@Slf4j
public class WebSocket {
    private Session session;

    /**
     * 存储websocket
     */
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    /**
     * 打开webSocket时
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSockets.add(this);
        log.info("[websocket消息] 有新的连接，当前总数:{}",webSockets.size());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        log.info("[websocket消息] 连接断开，当前总数:{}",webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("[websocket消息] 收到客户端发来的消息:{}",message);
    }

    public void sendMessage(String message){
        for(WebSocket webSocket:webSockets){
            log.info("[websocket消息] 广播消息,message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.warn("[websocket消息] 发送消息失败,e={}",e);
            }
        }
    }

}
