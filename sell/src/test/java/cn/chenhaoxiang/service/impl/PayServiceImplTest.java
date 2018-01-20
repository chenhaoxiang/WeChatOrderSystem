package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/20.
 * Time: 下午 5:17.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderService orderService;
    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1516104088756358983");
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderService.findOne("1516104088756358983");
        payService.refund(orderDTO);
    }

}