package cn.chenhaoxiang.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/25.
 * Time: 下午 10:24.
 * Explain: websocket拦截器
 */
@Slf4j
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    /**
     * 如果 beforeHandshake 返回 false, 那么该 WebSocket 请求被拒绝连接.
        其中 request 可以转换成 ServletServerHttpRequest, 并获取其中包装的 HttpServletRequest, 以获得 http 请求中 parameter 等信息.
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        //解决 The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        log.info("Before Handshake");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        log.info("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}