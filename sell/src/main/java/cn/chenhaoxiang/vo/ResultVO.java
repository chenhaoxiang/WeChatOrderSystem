package cn.chenhaoxiang.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/9.
 * Time: 下午 7:37.
 * Explain: http请求返回的最外层对象
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
//    private String msg = "";//赋予初始值

    /**
     * 返回的具体内容
     */
    private T data;

}
