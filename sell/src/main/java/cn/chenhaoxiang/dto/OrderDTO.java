package cn.chenhaoxiang.dto;

import cn.chenhaoxiang.dataObject.OrderDetail;
import cn.chenhaoxiang.enums.OrderStausEnum;
import cn.chenhaoxiang.enums.PayStatusEnum;
import cn.chenhaoxiang.utils.EnumUtil;
import cn.chenhaoxiang.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 10:34.
 * Explain: 数据传输对象，在数据传输时候使用的，可以看成Service层返回到Controller层的数据
 * 订单DTO
 */
@Data
//Include.Include.ALWAYS 默认
//Include.NON_DEFAULT 属性为默认值不序列化
//Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
//Include.NON_NULL 属性为NULL 不序列化
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //旧版本的，已弃用
//@JsonInclude(JsonInclude.Include.NON_NULL)//如果属性为null就不返回到前端去,也就是转json 为NULL不参加序列化
public class OrderDTO {
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信Openid
     */
    private String buyerOpenid;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认为新下单
     */
    private Integer orderStatus;//初始化状态不需要了

    /**
     * 支付状态，默认为0-未支付
     */
    private Integer payStatus;//初始化状态不需要了

    /**
     * 创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)//让时间戳精度在秒
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)//让时间戳精度在秒
    private Date updateTime;

    /**
     * 订单详情
     */
    private List<OrderDetail> orderDetailList;
//    private List<OrderDetail> orderDetailList = new ArrayList<>();//赋予初始值

    @JsonIgnore //对象转换成json格式返回到前端会忽略该方法
    public OrderStausEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStausEnum.class);
    }
    @JsonIgnore //对象转换成json格式返回到前端会忽略该方法
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
