package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.config.ProjectUrlConfig;
import cn.chenhaoxiang.constans.CookieConstant;
import cn.chenhaoxiang.constans.RedisConstans;
import cn.chenhaoxiang.dataObject.SellerInfo;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.service.SellerService;
import cn.chenhaoxiang.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 7:27.
 * Explain: 卖家用户相关操作
 */
@Controller //涉及到页面跳转。不用@RestController
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    /**
     * 登录
     * @param openid
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        //TODO 未考虑新增的情况，也就是用户微信扫码登录不存在openid的情况下
        if(sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        //2.设置token至Redis
//        stringRedisTemplate.opsForValue().set("abc","122");//操作某些value 写入key-value
        String token= UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstans.TOKEN_PREFIX,token),openid,RedisConstans.EXPIPE, TimeUnit.SECONDS);//key,value,过期时间,时间单位 s

        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.EXPIPE);
        //做一个跳转获取订单列表后再跳转 重定向不要带项目名 - 最好带绝对地址 也就是带http://的绝对地址
        return new ModelAndView("redirect:"+projectUrlConfig.getProject()+"/seller/order/list");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @param map
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1.从Cookie查询
        Cookie cookie =CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie!=null){
            //2.清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstans.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.del(response,CookieConstant.TOKEN);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success",map);
    }

}
