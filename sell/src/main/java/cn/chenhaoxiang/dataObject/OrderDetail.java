package cn.chenhaoxiang.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 9:08.
 * Explain:订单详情
 */
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品Id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 单价
     * 千万不能从前端传过来。必须从数据库由我们自己取出来
     */
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    /**
     * 商品小图
     */
    private String productIcon;

}
