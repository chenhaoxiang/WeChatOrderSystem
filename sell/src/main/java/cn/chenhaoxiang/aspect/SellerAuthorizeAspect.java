package cn.chenhaoxiang.aspect;

import cn.chenhaoxiang.constans.CookieConstant;
import cn.chenhaoxiang.constans.RedisConstans;
import cn.chenhaoxiang.exception.SellAuthorizeException;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 8:31.
 * Explain: 卖家授权aop
 */
//TODO 暂时屏蔽 能够登录后需要放开，也就是有开放平台登录账号
//@Aspect
//@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 定义切面
     */
    @Pointcut("execution(public * cn.chenhaoxiang.controller.Seller*.*(..))"+
        "&& !execution(public * cn.chenhaoxiang.controller.SellerUserController.*(..))") //排除登录登出
    public void verify(){}

    /**
     * 运行之前
     */
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();//获取HttpServletRequest
        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie==null){
            log.warn("[登录校验] Cookie中查不到token");
            throw new SellAuthorizeException();
        }
        //去redis中查
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstans.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("[登录校验] Redis中查不到token");
            throw new SellAuthorizeException();
        }


    }

}
