package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 9:17.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {
    @Autowired
    private OrderMasterDao orderMasterDao;

    private final String OPENID ="110110";
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("陈浩翔");
        orderMaster.setBuyerPhone("12345678912");
        orderMaster.setBuyerAddress("长沙");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        OrderMaster result =  orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {

        PageRequest pageRequest = new PageRequest(1,3);
        Page<OrderMaster> result =  orderMasterDao.findByBuyerOpenid(OPENID,pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());
        //System.out.println(result.getTotalElements());//总条数


    }

}