package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.config.ProjectUrlConfig;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/18.
 * Time: 下午 7:45.
 * Explain:
 */
//@RestController //返回JSON - 不会重定向
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrl;

    /**
     * 微信公众号
     * 微信授权获取openid
     * @param returnUrl
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){
         //1.配置
        //2.调用方法
        String url = projectUrl.getWechatMpAuthorize()+"/wechat/userInfo";//重定向地址 配置到配置文件中去
        try {
            //第三个参数为附加参数，我们传过去啥，就会带回来啥。最多128字节
            String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl,"utf-8"));
            log.info("[微信网页授权] 获取Code，redirectUrl={}",redirectUrl);
            //重定向
            return "redirect:"+redirectUrl;
        } catch (UnsupportedEncodingException e) {
            log.error("[微信网页授权] Urlencode出现异常，异常信息={}",e.getMessage());
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
    }
    /**
     * 微信公众号 重定向-获取openid
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                         @RequestParam("state")String returnUrl){
        //state是我们自己传的
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] {}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("openid={},returnUrl={}",openId,returnUrl);
        return "redirect:"+returnUrl+"?openid="+openId;//重定向
    }


    /**
     * 微信开放平台授权登录
     * @param returnUrl
     * @return
     */
    @GetMapping("qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl){
        String url = projectUrl.getWechatOpenAuthorize()+"/wechat/qrUserInfo";//重定向地址 配置到配置文件中去
        try {
            String redirectUrl = wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,URLEncoder.encode(returnUrl,"utf-8"));
            log.info("[微信开放平台登录网页授权] ，redirectUrl={}",redirectUrl);
            return "redirect:"+redirectUrl;
        } catch (UnsupportedEncodingException e) {
            log.error("[微信开放平台登录网页授权] Urlencode出现异常，异常信息={}",e.getMessage());
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
    }

    /**
     * 微信开放平台登录 重定向-获取openid
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code")String code,
                           @RequestParam("state")String returnUrl){
        //state是我们自己传的
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信开放平台登录网页授权] {}",e);
            throw new SellException(ResultEnum.WECHAT_QR_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("openid={},returnUrl={}",openId,returnUrl);
        return "redirect:"+returnUrl+"?openid="+openId;//重定向
    }


}
