package cn.chenhaoxiang.dataObject.mapper;

import cn.chenhaoxiang.dataObject.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/25.
 * Time: 下午 9:04.
 * Explain:
 */
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
     * 查询
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
}
