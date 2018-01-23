package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.SellerInfo;
import cn.chenhaoxiang.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/23.
 * Time: 下午 10:51.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {
    @Autowired
    private SellerInfoDao sellerInfoDao;
    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result =sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() throws Exception {
        SellerInfo result = sellerInfoDao.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }

}