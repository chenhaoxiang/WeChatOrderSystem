package cn.chenhaoxiang.config;

import cn.chenhaoxiang.handler.HandshakeInterceptor;
import cn.chenhaoxiang.service.WebSocket;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 10:04.
 * Explain: websocket 配置
 */
// 现在已经把之前简单websocket配置换成了继承AbstractWebSocketHandler的方式实现websocket
//@Component
//public class WebSocketConfig {
//    //第一种方式
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter(){
//        return new ServerEndpointExporter();
//    }
//}
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 测试该句会出现异常ServerEndpointExporter causes refresh to fail with java.lang.IllegalStateException: javax.websocket.server.ServerContainer not available
     * 请前去把cn.chenhaoxiang.config.WebSocketConfig中serverEndpointExporter方法上的Bean注解注释
     * 问题解决: https://jira.spring.io/browse/SPR-12340
     *          https://jira.spring.io/browse/SPR-12109
     *
     * @throws Exception
     */
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
//        return new ServerEndpointExporter();
//    }
    /**
     * 解决方法，使用下面的代码替换上面的代码
     * @param applicationContext
     * @return
     */
    @Bean
    public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
        return new ServletContextAware() {
            @Override
            public void setServletContext(ServletContext servletContext) {
                ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
                serverEndpointExporter.setApplicationContext(applicationContext);
                try {
                    serverEndpointExporter.afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocket(), "/webSocket").addInterceptors(new HandshakeInterceptor());//Spring为了保护应用，OriginHandshakeInterceptor拦截器帮我们加了拦截器防止跨域.但是我们自己实现了拦截器，就不用担心了。还可以通过setAllowedOrigins("*")来允许跨域访问
    }
}
