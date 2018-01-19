package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.dataObject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 6:43.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;//这里选择实现，因为是实现的测试

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("男士专用",5);
        ProductCategory result = productCategoryService.save(productCategory);
        Assert.assertNotEquals(null,result);

    }

}