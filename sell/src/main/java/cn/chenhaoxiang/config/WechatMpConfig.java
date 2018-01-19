package cn.chenhaoxiang.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/18.
 * Time: 下午 7:47.
 * Explain:MP-公众号的意思
 */
@Component
public class WechatMpConfig {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;
    @Bean//作为一个Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();//基于内存的
        wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
        wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());
        return wxMpInMemoryConfigStorage;
    }
}
