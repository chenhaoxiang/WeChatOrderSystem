package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.ProductInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 6:58.
 * Explain:
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {
    /**
     * 通过商品状态查询
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
