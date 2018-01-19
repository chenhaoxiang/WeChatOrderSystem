package cn.chenhaoxiang.exception;

import cn.chenhaoxiang.enums.ResultEnum;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 10:46.
 * Explain: 自定义异常
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


    public SellException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code=code;
    }
}
