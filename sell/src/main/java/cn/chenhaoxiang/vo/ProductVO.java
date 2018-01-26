package cn.chenhaoxiang.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 7:40.
 * Explain: 商品(包含类目)
 */
@Data
public class ProductVO implements Serializable{
    private static final long serialVersionUID = 1397557650534605111L;
    /**
     * 类目名
     */
    @JsonProperty("name")//序列化返回给前端时，json里面的该字段名为name
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
