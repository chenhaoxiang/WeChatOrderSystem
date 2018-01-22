package cn.chenhaoxiang.service;

import cn.chenhaoxiang.dataObject.ProductCategory;
import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 7:11.
 * Explain: 商品
 */
public interface ProductInfoService {
    /**
     * 查询一个
     * @param productInfoId
     * @return
     */
    ProductInfo findOne(String productInfoId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有 商品列表
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 更新和新增
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架
     */
    ProductInfo onSale(String productId);

    /**
     * 下架
     */
    ProductInfo offSale(String productId);

}
