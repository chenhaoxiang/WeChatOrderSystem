package cn.chenhaoxiang.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 9:00.
 * Explain: 订单状态
 */
@Getter
public enum  OrderStausEnum implements CodeEnum<Integer> {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;
    private Integer code;

    private String message;

    OrderStausEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过cide获取订单状态枚举
     * 不能这么写，如果还有另外的枚举状态，就还得复制拷贝过去
     * 所以用实现接口和工具类来实现
     * @param code
     * @return
     */
//    public static OrderStausEnum getOrderStatusEnum(Integer code){
//        for(OrderStausEnum orderStausEnum:OrderStausEnum.values()){
//            if(orderStausEnum.getCode().equals(code)){
//                return orderStausEnum;
//            }
//        }
//        return null;
//    }

}
