package cn.chenhaoxiang.service;

import cn.chenhaoxiang.dataObject.OrderMaster;
import cn.chenhaoxiang.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 10:29.
 * Explain: 订单
 */
public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询订单列表 - 单个人
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

  /**
     * 分页查询订单列表
     */
    Page<OrderDTO> findList(Pageable pageable);

    /**
     * 取消订单
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     */
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * 支付订单
     */
    OrderDTO paid(OrderDTO orderDTO);

}
