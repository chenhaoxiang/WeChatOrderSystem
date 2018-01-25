package cn.chenhaoxiang.dataObject.dao;

import cn.chenhaoxiang.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/26.
 * Time: 上午 12:07.
 * Explain: 进行简单的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryMyBatisDao productCategoryMyBatisDao;
    @Test
    public void insertByMap() throws Exception {
    }

    @Test
    public void findByCategoryName() throws Exception {
        List<ProductCategory> productCategoryList =productCategoryMyBatisDao.findByCategoryName("美味的");
        log.info("productCategoryList={}",productCategoryList);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

}