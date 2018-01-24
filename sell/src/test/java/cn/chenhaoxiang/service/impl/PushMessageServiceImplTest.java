package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.service.OrderService;
import cn.chenhaoxiang.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 9:45.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private OrderService orderService;
    @Test
    public void orderStatus() throws Exception {
        OrderDTO orderDTO =orderService.findOne("1516538902199931605");
        pushMessageService.orderStatus(orderDTO);
    }

}