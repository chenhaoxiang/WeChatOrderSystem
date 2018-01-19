package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
//@Transactional有两个不同的包。在Spring的事务管理中应该使用org.springframework.transaction.annotation.Transactional
//在Java EE 应用中，应该使用javax.transaction.Transactional。
/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/8.
 * Time: 下午 10:06.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 测试查找
     */
    @Test
    public void fingOnetest(){
        ProductCategory productCategory =productCategoryDao.findOne(1);
        log.info(productCategory.toString());
    }

    /**
     * 新增
     */
    @Test
    @Transactional //在测试里面的事务是完全回滚，运行完就回滚
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("女生最爱",5);
        ProductCategory result = productCategoryDao.save(productCategory);
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null,result);//不期望是null，期望是result  和上面是一样的
    }
    /**
     * 更新
     * 先查询出来再更新
     */
    @Test
    public void saveTest2(){
        ProductCategory productCategory = productCategoryDao.findOne(2);
        productCategory.setCategoryType(3);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> productCategoryList =  productCategoryDao.findByCategoryTypeIn(list);
        log.info(productCategoryList.toString());
        Assert.assertNotEquals(0,productCategoryList.size());
    }

}