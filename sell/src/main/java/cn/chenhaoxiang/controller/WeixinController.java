package cn.chenhaoxiang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/18.
 * Time: 下午 6:58.
 * Explain: 不用了
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    //视频上的:wxd898fcd01713c658
    //29d8a650db31472aa87800e3b0d739f2

    //uifuture
    //40567729325064e5590df98612846634
    //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxec6162ffa649896b&redirect_uri=http://anyi.s1.natapp.cc/weixin/auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code){
        log.info("进入微信auth方法");
        log.info("code={}",code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxec6162ffa649896b&secret=40567729325064e5590df98612846634&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response =restTemplate.getForObject(url,String.class);//Get请求
        log.info("response={}",response);
    }
}
