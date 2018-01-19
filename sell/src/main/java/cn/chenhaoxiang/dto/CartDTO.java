package cn.chenhaoxiang.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 11:06.
 * Explain:购物车 - 在Service层加减库存使用的
 */
@Data
public class CartDTO {
    /**
     * 商品Id
     */
    private String productId;

    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
