package cn.chenhaoxiang.exception;

import cn.chenhaoxiang.enums.ResultEnum;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/25.
 * Time: 下午 8:48.
 * Explain: 返回错误状态码的异常 出现该异常不返回200
 */
@Data
public class ResponseBankException extends RuntimeException{

    private Integer code;

    public ResponseBankException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


    public ResponseBankException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code=code;
    }
}
