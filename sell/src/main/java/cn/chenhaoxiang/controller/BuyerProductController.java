package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dataObject.ProductCategory;
import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.service.ProductCategoryService;
import cn.chenhaoxiang.service.ProductInfoService;
import cn.chenhaoxiang.utils.ResultVOUtil;
import cn.chenhaoxiang.vo.ProductInfoVO;
import cn.chenhaoxiang.vo.ProductVO;
import cn.chenhaoxiang.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 7:32.
 * Explain:买家商品
 */
@RestController
@RequestMapping(value = "/buyer/product")
@Slf4j
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("list")
    @Cacheable(cacheNames = "product",key = "123",unless = "#result.getCode() != 0")
    //Redis缓存注解  Cacheable第一次访问会访问到方内的内容，方法会返回一个对象，返回对象的时候，会把这个对象存储。下一次访问的时候，不会进去这个方法，直接从redis缓存中拿
    //上面的key的值是可以动态写的@Cacheable(cacheNames = "product",key = "#sellerId")  sellerId为方法中的参数名
    //condition判断是否成立的条件  例如key = "#sellerId",condition = "#sellerId.length() > 3"  只有条件成立才会对结果缓存，结果不成立是不缓存的
    //依据结果来判断是否缓存 unless = "#result.getCode() != 0",#result其实就是ResultVO，也就是返回的对象
    //unless(除什么之外,如果不 的意思) 如果=0就缓存，需要写成!=0。理解起来就是，除了不等于0的情况之外，才缓存，也就是等于0才缓存。
    //其实就是，你想要什么条件下缓存，你反过来写就行了
    //测试缓存的话，你可以在方法内打一个断点进行测试
    //注意，返回的缓存对象一定要实现序列化！！！
    public ResultVO list(){
        //1.先查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2.查询类目(一次性查询) 查询需要的
        //一定不要把数据库的查询放到for循环中
        //传统方法
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for(ProductInfo productInfo:productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法 (java8 lambda) 推荐
        List<Integer> categoryTypeList =  productInfoList.stream().map(e->e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装 - 拼装成前端需要的格式
        List<ProductVO> productVOList = new ArrayList<>();
            //先遍历类目
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            //遍历商品详情
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);//Spring中的拷贝工具类，能把一样的属性的属性值拷贝过去
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }


}
