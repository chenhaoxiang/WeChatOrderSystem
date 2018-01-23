package cn.chenhaoxiang.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/23.
 * Time: 下午 9:27.
 * Explain: 商品字段 - 处理表单提交过来的字段
 */
@Data
public class ProductForm {
    private String productId;
    /**
     * 名字
     * @NotBlank 注解用在String上
     * @NotEmpty 用在集合类上面
     * @NotNull 用在基本类型上
     */
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    /**
     * 单价
     */
    @NotNull(message = "单价不能为空")
    private BigDecimal productPrice;

    /**
     * 库存
     */
    @NotNull(message = "库存不能为空")
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
     * 类目编号
     */
    @NotNull(message = "类目编号不能为空")
    private Integer categoryType;
}
