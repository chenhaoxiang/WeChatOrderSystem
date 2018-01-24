package cn.chenhaoxiang.service;

import cn.chenhaoxiang.dto.OrderDTO;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 9:23.
 * Explain: 推送消息
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息推送
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
