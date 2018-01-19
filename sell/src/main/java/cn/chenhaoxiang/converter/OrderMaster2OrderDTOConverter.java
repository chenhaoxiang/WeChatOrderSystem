package cn.chenhaoxiang.converter;

import cn.chenhaoxiang.dataObject.OrderMaster;
import cn.chenhaoxiang.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/15.
 * Time: 下午 6:35.
 * Explain: 转换器
 */
public class OrderMaster2OrderDTOConverter {

    /**
     * OrderMaster转换为OrderDTO
     * @param orderMaster
     * @return
     */
    private static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    /**
     * lambda
     * List<OrderMaster>转换为List<OrderDTO>
     * @param orderMasterList
     * @return
     */
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e->convert(e))
                .collect(Collectors.toList());
        //很容易理解，e其实就是遍历orderMasterList中的每一个OrderMaster，然后通过convert函数转换为OrderDTO
        //Collectors收集器可以被用来收集流的输出到一个集合，或者产生一个单一的值。toList收集器使用了ArrayList作为列表的实现。
    }

}
