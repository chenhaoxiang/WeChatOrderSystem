package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.converter.OrderForm2OrderDTOConverter;
import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.form.OrderForm;
import cn.chenhaoxiang.service.BuyerService;
import cn.chenhaoxiang.service.OrderService;
import cn.chenhaoxiang.utils.ResultVOUtil;
import cn.chenhaoxiang.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/16.
 * Time: 下午 7:24.
 * Explain:
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){//表单校验,必须带上@Valid参数，然后必须有BindingResult参数
            log.error("[创建订单] 参数不正确,orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
            //bindingResult.getFieldError().getDefaultMessage()获取的是OrderForm实体中属性上NotEmpty注解的message值
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单列表] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage =  orderService.findList(openid,pageRequest);
        //约束一:时间需要返回时间戳，秒为单位的
        //第一种方法 - 转存Date -> Long.不可取，麻烦
        //第二种  请见Date2LongSerializer类

        //约束二:为null的属性不返回到前端， 也就是类转json 属性为NULL的不参加序列化
        //第一种方法，到类上使用JsonInclude(JsonInclude.Include.NON_NULL)注解
        //但是对象很多的时候，每个类去加的话，有些麻烦。
        //第二种方法:全局配置 spring: jackson: default-property-inclusion: non_null

//        orderDTOPage.getTotalElements();//总行数
//        orderDTOPage.getTotalPages();//总页数
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(orderId)){
            log.error("[查询订单详情] openid为空或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO =buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(orderId)){
            log.error("[查询订单详情] openid为空或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }

}
