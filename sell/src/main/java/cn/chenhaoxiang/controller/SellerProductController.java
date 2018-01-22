package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dataObject.ProductInfo;
import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.enums.ResultEnum;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

}
