package cn.chenhaoxiang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/18.
 * Time: 下午 7:53.
 * Explain:微信账号相关
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    /**
     * 公众号appId
     */
    private String mpAppId;
    /**
     * 公众号appSecret
     */
    private String mpAppSecret;

    /**
     * 开放平台appId
     */
    private String openAppId;
    /**
     *  开放平台appSecret
     */
    private String openAppSecret;


    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;

    /**
     * 模板消息Id
     */
    private Map<String,String> templateId;

}
