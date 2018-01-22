package cn.chenhaoxiang.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 7:16.
 * Explain: 商品状态
 */
@Getter //自动生成getter方法
public enum ProductStatusEnum implements CodeEnum<Integer> {
    UP(0,"在架"),
    DOWN(1,"下架")
    ;
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message=message;
    }
}
