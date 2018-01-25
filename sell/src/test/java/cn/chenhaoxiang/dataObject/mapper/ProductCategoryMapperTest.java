package cn.chenhaoxiang.dataObject.mapper;

import cn.chenhaoxiang.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/25.
 * Time: 下午 9:10.
 * Explain:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 测试该句会出现异常ServerEndpointExporter causes refresh to fail with java.lang.IllegalStateException: javax.websocket.server.ServerContainer not available
     * 请前去把cn.chenhaoxiang.config.WebSocketConfig中serverEndpointExporter方法上的Bean注解注释
     * 问题解决: https://jira.spring.io/browse/SPR-12340
     *          https://jira.spring.io/browse/SPR-12109
     *          现在已经把之前简单websocket配置换成了继承AbstractWebSocketHandler的方式实现websocket
     * @throws Exception
     */
    @Test
    public void insertByMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","测试使用");
        map.put("category_type",7);
        int result =productCategoryMapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("美味的");
        productCategory.setCategoryType(11);
        int result =productCategoryMapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findByCategoryType() throws Exception {
        ProductCategory productCategory =productCategoryMapper.findByCategoryType(11);
        log.info("productCategory={}",productCategory);
        Assert.assertNotNull(productCategory);
    }



}