package cn.chenhaoxiang.dataObject;

import cn.chenhaoxiang.enums.OrderStausEnum;
import cn.chenhaoxiang.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 8:57.
 * Explain: 订单表
 */
@Entity
@Data
@DynamicUpdate //动态更新-需要设置数据库的更新时间字段为自动更新 这样，查询出时间，去设置其他字段后保存，更新时间依然会更新
public class OrderMaster {

    /**
     * 订单ID
     */
    @Id
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
    private Integer orderStatus = OrderStausEnum.NEW.getCode();

    /**
     * 支付状态，默认为0-未支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


//    /**
//     * 关联订单详情
//     */
//    @Transient //在数据库对应的时候能忽略，就不会去数据库找对应的字段了
//    private List<OrderDetail> orderDetailList;
    // 直接新建一个对象 OrderDTO

}
