package cn.chenhaoxiang.service;

import cn.chenhaoxiang.dataObject.ProductCategory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 6:38.
 * Explain: 类目
 */
public interface ProductCategoryService {

    /**
     * 查询一个
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有
     * @return
     */
    List<ProductCategory> findAll();
    /**
     * 通过categoryType集合 查询出ProductCategory集合
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 更新和新增
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
