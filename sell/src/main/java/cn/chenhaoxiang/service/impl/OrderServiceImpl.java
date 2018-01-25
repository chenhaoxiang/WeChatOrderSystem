package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.converter.OrderMaster2OrderDTOConverter;
import cn.chenhaoxiang.dao.OrderDetailDao;
import cn.chenhaoxiang.dao.OrderMasterDao;
import cn.chenhaoxiang.dataObject.OrderDetail;
import cn.chenhaoxiang.dataObject.OrderMaster;
import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.dto.CartDTO;
import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.enums.OrderStausEnum;
import cn.chenhaoxiang.enums.PayStatusEnum;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.ResponseBankException;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.service.*;
import cn.chenhaoxiang.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 10:39.
 * Explain:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private PayService payService;
    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList = new ArrayList<>();//方式一
        //1.查询商品(数量，价格，库存等)
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo==null){//商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//                throw new ResponseBankException(ResultEnum.PRODUCT_NOT_EXIST);//修改状态码返回
            }
            //2.计算总价
            orderAmount=productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))//相乘- 计算出一种商品的总价
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            //商品图片，名字等
//            orderDetail.setProductName(productInfo.getProductName());//不要这样写，要写很多
            BeanUtils.copyProperties(productInfo,orderDetail);//拷贝对应的属性值，将productInfo的属性值拷贝到orderDetail ，所以如果有不为null的属性值，记得写在前面
            orderDetailDao.save(orderDetail);
//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());//方式一
//            cartDTOList.add(cartDTO);//方式一
            //扣库存放在下面了，不污染这里的代码，请见方式二
        }

        //3.写入订单数据库(orderMaster和orderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        //Spring的对象拷贝函数 - 属性值是null的也会被拷贝进去，所以如果有不为null的属性值，记得写在前面。但是注意，有点默认值也会被写回去
        BeanUtils.copyProperties(orderDTO,orderMaster);//将orderDTO拷贝到orderMaster

        orderMaster.setOrderAmount(orderAmount);
        //这两个状态值被覆盖为null了，记得写回去
        orderMaster.setOrderStatus(OrderStausEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //4.扣库存
        //方式二
        //TODO 在这里，产生高并发的情况下，会出现超卖的情况 - 也就是会出现把库存扣到负数的情况
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
        new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());//Java8新特性 (java8 lambda) 推荐
        productInfoService.decreaseStock(cartDTOList);//判断数量是否够

        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList= orderDetailDao.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){//判断orderDetailList为空
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);//拷贝
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid,pageable);//这里不需要去查询订单详情
        List<OrderDTO> orderDTOList =  OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage =orderMasterDao.findAll(pageable);//分页查询
        List<OrderDTO> orderDTOList =  OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //先判断订单状态 - 只有指定状态下的订单才能取消
        if(!orderDTO.getOrderStatus().equals(OrderStausEnum.NEW.getCode())){//不是新下单的订单
            log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStausEnum.CANCEL.getCode());
        //状态修改完再拷贝，否则orderMaster的状态还是没变的
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);//进行修改订单状态
        if(updateResult == null){
            log.error("[取消订单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        //先判断有没有商品
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中无商品，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //lambda表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        //加库存
        productInfoService.increaseStock(cartDTOList);
        //如果用户已支付，需要给用户退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //进行退款
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStausEnum.NEW.getCode())){
            log.error("[完结订单] 订单状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStausEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult==null){
            log.error("[完结订单] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板消息  看需求推
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStausEnum.NEW.getCode())){
            log.error("[订单支付完成] 订单状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付完成] 订单支付状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult==null){
            log.error("[订单支付完成] 更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
