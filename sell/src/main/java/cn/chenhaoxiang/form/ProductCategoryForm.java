package cn.chenhaoxiang.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/23.
 * Time: 下午 10:14.
 * Explain:
 */
@Data
public class ProductCategoryForm {
    private Integer categoryId;
    /**
     * 类目名字
     */
    @NotBlank(message = "类目名字不能为空")
    private String categoryName;
    /**
     * 类目编号
     */
    @NotNull(message = "类目编号不能为空")
    private Integer categoryType;
}
