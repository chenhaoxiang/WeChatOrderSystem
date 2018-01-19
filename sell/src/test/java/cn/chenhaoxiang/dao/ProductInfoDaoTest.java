package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.ProductInfo;
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
 * Date: 2018/1/9.
 * Time: 下午 7:01.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("鸡蛋粥");
        productInfo.setProductPrice(new BigDecimal(6.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("味道不错哦");
        productInfo.setProductIcon("http://XXXX.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result = productInfoDao.save(productInfo);
        Assert.assertNotEquals(null,result);

    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> productInfoList = productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }

}