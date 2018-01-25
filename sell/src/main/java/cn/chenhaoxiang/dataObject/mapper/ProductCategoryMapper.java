package cn.chenhaoxiang.dataObject.mapper;

import cn.chenhaoxiang.dataObject.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/25.
 * Time: 下午 9:04.
 * Explain: MyBatis的Mapper
 */
//在SellApplication上配置了注解@MapperScan(basePackages = "cn.chenhaoxiang.dataObject.mapper")//配置mybatis mapper扫描路径 所以不用我们再写注解注入Bean
@Mapper
@Component //也可以通过上面两个注解实现注入Bean
public interface ProductCategoryMapper {
    /**
     * 通过Map插入
     * @param map
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    /**
     * 通过对象插入
     * @param productCategory
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    /**
     * 通过CategoryType查询
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type=#{categoryType,jdbcType=INTEGER}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "create_time",property = "createTime"),
    })//映射
    ProductCategory findByCategoryType(Integer categoryType);

    /**
     * 通过CategoryName查询多条数据
     * @param categoryName
     * @return
     */
    @Select("select * from product_category where category_name=#{categoryName}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "create_time",property = "createTime"),
    })//映射
    List<ProductCategory> findByCategoryName(String categoryName);

    /**
     * 通过categoryType修改categoryName
     * @param categoryType
     * @param categoryName
     * @return
     */
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByCategoryType(@Param("categoryType")Integer categoryType,@Param("categoryName")String categoryName);

    /**
     * 通过categoryType修改对象category_name
     * @param productCategory
     * @return
     */
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByObject(ProductCategory productCategory);

    /**
     * 删除
     * @param categoryType
     * @return
     */
    @Delete("delete from product_category where category_type=#{categoryType}")
    int deleteByCategoryType(Integer categoryType);
}
