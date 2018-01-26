package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.dao.ProductInfoDao;
import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.dto.CartDTO;
import cn.chenhaoxiang.enums.ProductStatusEnum;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 7:14.
 * Explain:
 */
@Service
//@CacheConfig(cacheNames = "product") //配置整个类的缓存cacheNames,相当于作用域
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
//    @Cacheable(key = "123") //注解缓存
//    @Cacheable(cacheNames = "product",key = "123") //注解缓存
    public ProductInfo findOne(String productInfoId) {
        return productInfoDao.findOne(productInfoId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
//    @CachePut(key = "123") //和上面findOne的返回对象对应
//    @CachePut(cacheNames = "product",key = "123") //和上面findOne的返回对象对应
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoDao.findOne(cartDTO.getProductId());
            if(productInfo==null){//商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //增加库存
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=productInfoDao.findOne(cartDTO.getProductId());//数据库实际商品
            if(productInfo==null){//商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }

    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        /**
         * 判断上下架状态
         */
        if(productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        /**
         * 更新
         */
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoDao.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        /**
         * 判断上下架状态
         */
        if(productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        /**
         * 更新
         */
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoDao.save(productInfo);
    }
}
