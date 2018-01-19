package cn.chenhaoxiang.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/16.
 * Time: 下午 7:27.
 * Explain: 进行表单验证的实体类
 * 参数就是前端传进来的参数
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家微信openId
     */
    @NotEmpty(message = "openid必填")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;


}
