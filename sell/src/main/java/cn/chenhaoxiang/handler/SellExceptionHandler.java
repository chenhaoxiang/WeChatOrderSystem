package cn.chenhaoxiang.handler;

import cn.chenhaoxiang.config.ProjectUrlConfig;
import cn.chenhaoxiang.exception.ResponseBankException;
import cn.chenhaoxiang.exception.SellAuthorizeException;
import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.utils.ResultVOUtil;
import cn.chenhaoxiang.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 8:41.
 * Explain: 异常捕获类
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    /**
     * 全局异常捕捉处理
     * 拦截登录异常
     * 重定向至登录页面 - 也就是微信扫码登录
     * @return
     */
    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        return new ModelAndView("redirect:"
        .concat(projectUrlConfig.getWechatOpenAuthorize())//微信开放平台登录授权地址
        .concat("/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(projectUrlConfig.getProject())//服务器访问的地址
        .concat("/seller/login"));
    }

    /**
     * 全局异常捕捉处理
     * @return
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /**
     * 全局异常捕捉处理
     * 返回状态码的修改 不再返回200
     * @return
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //返回状态码的修改
    @ResponseBody
    public ResultVO handlerResponseBankException(ResponseBankException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

}
