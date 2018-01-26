package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dataObject.ProductCategory;
import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.form.ProductForm;
import cn.chenhaoxiang.service.ProductCategoryService;
import cn.chenhaoxiang.service.ProductInfoService;
import cn.chenhaoxiang.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/22.
 * Time: 下午 11:10.
 * Explain:卖家端商品 Controller层
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){//map - freemarker模板数据返回到页面
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);//当前页
        map.put("size",size);//一页有多少数据
        return new ModelAndView("product/list",map);
    }

    /**
     * 上架商品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/onSale")
    public ModelAndView onSale(@RequestParam(value = "productId")String productId,
                             Map<String,Object> map){//map - freemarker模板数据返回到页面
        try {
            productInfoService.onSale(productId);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.PRODUCT_ONSALE_SUCCESS.getMessage());
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 下架商品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/offSale")
    public ModelAndView offSale(@RequestParam(value = "productId")String productId,
                               Map<String,Object> map){//map - freemarker模板数据返回到页面
        try {
            productInfoService.offSale(productId);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMessage());
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 商品信息编辑
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,//非必传
                                Map<String,Object> map){//map - freemarker模板数据返回到页面
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有类目
        List<ProductCategory> productCategoryList=productCategoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("product/index",map);
    }

    /**
     * 保存/更新商品
     * @param productForm
     * @param bindingResult
     * @param map
     * @return @Valid用来校验变量
     */
    @PostMapping("/save")
//    @CacheEvict(cacheNames = "product",key = "123") //访问这个方法之后删除对应的缓存  对应之前的Redis缓存注解的配置 。key如果不填，默认是空，对应的值应该就是方法的参数的值了.对应BuyerProductController-list的缓存
//    @CachePut(cacheNames = "product",key = "123") //对应之前的Redis缓存注解的配置
    //@CachePut 每次还是会执行方法中的内容，每次执行完成后会把返回的内容放到Redis中去.
    // 这种注解和原来对应的返回对象需要是相同的才行，这里返回的是ModelAndView。可以到service层注解或者dao层注解CachePut
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        //TODO 类目编号不能重复，如果是新增，需要类目编号之前不存在。如果是修改，需要排除当前id的类目，其他类目不存在该类目编号
        try {
            //新增商品  productForm.getProductId 为空
            if(StringUtils.isEmpty(productForm.getProductId())){
                productForm.setProductId(KeyUtil.getUniqueKey());
            }else {
                productInfo = productInfoService.findOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm,productInfo);//拷贝函数
            productInfoService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
