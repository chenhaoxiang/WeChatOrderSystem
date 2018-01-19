package cn.chenhaoxiang.dataObject;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/8.
 * Time: 下午 10:01.
 * Explain:现在的表名 product_category
 */
//@Table(name = "product_category")//如果类名和表名不是一致的话就需要
@Entity
@DynamicUpdate //动态更新-需要设置数据库的更新时间字段为自动更新 这样，查询出时间，去设置其他字段后保存，更新时间依然会更新
@Data //不用写setter和getter方法,toString也可以省了 性能是一样的，可以去看编译的class文件，和我们写的一样
//@Getter //不用写getter方法
//@Setter //不用写setter方法
public class ProductCategory {
    /**
     * 类目ID
     */
    @Id
    @GeneratedValue //自增
    private Integer categoryId;
    /**
     * 类目名字
     */
    private String categoryName;
    /**
     * 类目编号
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
