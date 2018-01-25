package cn.chenhaoxiang.service;

import cn.chenhaoxiang.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 10:05.
 * Explain:
 */
//@Component
//@ServerEndpoint("/webSocket") //url
//@Slf4j
//public class WebSocket {
//    private Session session;
//    /**
//     * 存储websocket
//     */
//    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
//    /**
//     * 打开webSocket时
//     * @param session
//     */
//    @OnOpen
//    public void onOpen(Session session){
//        this.session=session;
//        webSockets.add(this);
//        log.info("[websocket消息] 有新的连接，当前总数:{}",webSockets.size());
//    }
//    @OnClose
//    public void onClose(){
//        webSockets.remove(this);
//        log.info("[websocket消息] 连接断开，当前总数:{}",webSockets.size());
//    }
//    @OnMessage
//    public void onMessage(String message){
//        log.info("[websocket消息] 收到客户端发来的消息:{}",message);
//    }
//
//    public void sendMessage(String message){
//        for(WebSocket webSocket:webSockets){
//            log.info("[websocket消息] 广播消息,message={}",message);
//            try {
//                webSocket.session.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                log.warn("[websocket消息] 发送消息失败,e={}",e);
//            }
//        }
//    }
//}
@Slf4j
@Component
public class WebSocket extends AbstractWebSocketHandler {
    private static Map<String,WebSocketSession> sessionMap = new HashMap<>();

    /**
     * 二进制通讯
     * 你可以做其他处理
     * @param session
     * @param message
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("有二进制消息过来");
        try {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Binary messages not supported"));
        }
        catch (IOException ex) {
            // ignore
        }
    }

    /**
     * 接收到消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(new TextMessage(session.getId()+",你是第" + (sessionMap.size()) + "位访客")); //p2p
        log.info("接收到[{}]消息={},当前一共{}位访客",session.getId(),message.getPayload(),sessionMap.size());
        Collection<WebSocketSession> sessions = sessionMap.values();
        for (WebSocketSession ws : sessions) {//广播
            ws.sendMessage(message);
        }
//        sendMessage(sessionMap.keySet(),"有新的消息收到");//广播 群发
    }

    /**
     * 新的连接
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getId(),session);
        log.info("新增加一个连接={},当前访客总数={}",session.getId(),sessionMap.size());
        super.afterConnectionEstablished(session);
    }

    /**
     * 连接关闭
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId());
        log.info("一个连接关闭={},当前访客总数={}",session.getId(),sessionMap.size());
        super.afterConnectionClosed(session, status);
    }

    /**
     * 发送消息
     * @author
     */
    public void sendMessage(String message) {
        log.info("发送消息={}",message);
        try {
            sendMessage(sessionMap.keySet(),message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @author
     */
    public void sendMessage(String username,String message) throws IOException {
        sendMessage(Arrays.asList(username),Arrays.asList(message));
    }

    /**
     * 发送消息
     * @author
     */
    public void sendMessage(Collection<String> acceptorList,String message) throws IOException {
        sendMessage(acceptorList,Arrays.asList(message));
    }

    /**
     * 发送消息，p2p 群发都支持
     * @author
     */
    public void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
        if (acceptorList != null && msgList != null) {
            for (String acceptor : acceptorList) {
                WebSocketSession session = sessionMap.get(acceptor);
                if (session != null) {
                    for (String msg : msgList) {
                        session.sendMessage(new TextMessage(msg.getBytes()));
                    }
                }
            }
        }
    }

}

