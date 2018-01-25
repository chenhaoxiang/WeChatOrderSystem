package cn.chenhaoxiang.dataObject.dao;

import cn.chenhaoxiang.dataObject.ProductCategory;
import cn.chenhaoxiang.dataObject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/25.
 * Time: 下午 11:59.
 * Explain: 使用MyBatis的Dao测试
 */
@Repository
public class ProductCategoryMyBatisDao {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public int insertByMap(Map<String,Object> map){
        return productCategoryMapper.insertByMap(map);
    }

    public List<ProductCategory> findByCategoryName(String categoryName){
        return productCategoryMapper.findByCategoryName(categoryName);
    }

}
