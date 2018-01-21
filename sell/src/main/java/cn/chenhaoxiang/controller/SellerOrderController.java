package cn.chenhaoxiang.controller;

import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.service.OrderService;
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
 * Date: 2018/1/21.
 * Time: 下午 2:28.
 * Explain:
 */
@Controller //展示页面
@RequestMapping("seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 订单列表
     * @param page 第几页，从第一页开始
     * @param size  一页有多少数据
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){//map - 模板数据返回到页面
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);//当前页
        map.put("size",size);//一页有多少数据
        return new ModelAndView("order/list",map);
    }


}
