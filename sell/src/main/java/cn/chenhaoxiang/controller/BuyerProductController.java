package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dataObject.ProductCategory;
import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.service.ProductCategoryService;
import cn.chenhaoxiang.service.ProductInfoService;
import cn.chenhaoxiang.utils.ResultVOUtil;
import cn.chenhaoxiang.vo.ProductInfoVO;
import cn.chenhaoxiang.vo.ProductVO;
import cn.chenhaoxiang.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("list")
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
