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
public enum  OrderStausEnum {
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
}
