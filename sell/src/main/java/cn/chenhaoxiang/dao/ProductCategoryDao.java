package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/8.
 * Time: 下午 10:05.
 * Explain:
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer>{//后面的是主键类型

    /**
     * 通过categoryType集合 查询出ProductCategory集合
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
