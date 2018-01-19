package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 10:07.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567810");
        orderDetail.setOrderId("11111112");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("11111112");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(2.2));
        orderDetail.setProductQuantity(3);

        OrderDetail result = orderDetailDao.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList =  orderDetailDao.findByOrderId("11111111");
        Assert.assertNotEquals(0,orderDetailList.size());

    }

}