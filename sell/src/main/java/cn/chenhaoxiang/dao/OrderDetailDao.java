package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 9:14.
 * Explain:
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {

    /**
     * 查询订单详情 - 根据订单id
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);

}
