package cn.chenhaoxiang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 7:15.
 * Explain: 配置文件中的url
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权Url  配置授权域即可
     */
    public String wechatMpAuthorize;

    /**
     * 微信开放平台授权url 配置授权域即可
     */
    public String wechatOpenAuthorize;

    /**
     * 本项目的地址
     */
    public String project;
}
