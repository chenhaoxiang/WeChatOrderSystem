package cn.chenhaoxiang.dataObject;

import cn.chenhaoxiang.enums.ProductStatusEnum;
import cn.chenhaoxiang.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 6:54.
 * Explain: 商品
 */
@Entity
@Data
@DynamicUpdate //修改时间字段自动更新的 需要的注解
public class ProductInfo implements Serializable{
    private static final long serialVersionUID = -3048495745484346347L;
    @Id
    private String productId;

    /**
     * 名字
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private Integer productStock;
    /**
     * 描述
     */
    private String productDescription;
    /**
     * 小图
     */
    private String productIcon;
    /**
     * 状态  0-正常 1-下架
     */
    private Integer productStatus = 0;
    /**
     * 类目编号
     */
    private Integer categoryType;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 上架下架
     * 注意是公开方法
     * @return
     */
    @JsonIgnore //返回json格式的字符串时，忽略该字段/方法 也就是不序列化
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
