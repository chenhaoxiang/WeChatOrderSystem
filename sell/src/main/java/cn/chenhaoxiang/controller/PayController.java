package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.service.OrderService;
import cn.chenhaoxiang.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/19.
 * Time: 下午 9:22.
 * Explain:
 */
@Controller //返回界面用
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    /**
     *
     * TODO 用"师兄干货"的支付接口来测试的支付接口
     * 支付授权:http://sell.springboot.cn/sell/pay?openid=oTgZpwd3BBEDTuMpw6b6f0gNUCik&orderId=1516539154652409049&returnUrl=chenhaoxiang.cn
     * 回调地址:http://anyi.nat300.top/pay?openid=oTgZpwd3BBEDTuMpw6b6f0gNUCik
     * @param openid
     * @param map
     * @return
     */
    @GetMapping("")
    public ModelAndView index(@RequestParam("openid") String openid,
                              @RequestParam("orderId") String orderId,
                              @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map){
        log.info("openid={},orderId={}.returnUrl={},",openid,orderId,returnUrl);
        //1.查询订单
//        String orderId ="1516539154906548294";//测试的orderId
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);//页面，参数
    }


    /**
     * 发起支付
     * @param orderId
     * @param returnUrl
     * @param map
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                                @RequestParam("returnUrl")String returnUrl,
                                Map<String,Object> map){
        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);//页面，参数
    }

    /**
     * 微信异步通知是否支付成功
     */
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData){//入参为微信返回的xml格式的字符串
        payService.notify(notifyData);
        //通知微信处理结果 - 返回xml字符串
        return new ModelAndView("pay/success");
    }

}
