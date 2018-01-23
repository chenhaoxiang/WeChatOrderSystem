package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dataObject.ProductCategory;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.form.ProductCategoryForm;
import cn.chenhaoxiang.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * Date: 2018/1/23.
 * Time: 下午 10:00.
 * Explain: 卖家类目controller
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList =productCategoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 修改类目
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,
                              Map<String,Object> map){
        if(categoryId!=null&&categoryId > 0){
            //查询类目
            ProductCategory productCategory = productCategoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 增加/修改类目
     * @param productCategoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    public ModelAndView save(@Valid ProductCategoryForm productCategoryForm,
                              BindingResult bindingResult,
                              Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if(productCategoryForm.getCategoryId()!=null){
                productCategory = productCategoryService.findOne(productCategoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(productCategoryForm,productCategory);
            productCategoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/seller/category/list");
        return new ModelAndView("common/success",map);
    }

}
