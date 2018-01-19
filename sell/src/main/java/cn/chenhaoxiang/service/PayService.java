package cn.chenhaoxiang.service;

import cn.chenhaoxiang.dto.OrderDTO;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/19.
 * Time: 下午 9:25.
 * Explain: 支付
 */
public interface PayService {
    void create(OrderDTO orderDTO);
}
