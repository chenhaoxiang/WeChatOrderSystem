package cn.chenhaoxiang.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/20.
 * Time: 下午 5:38.
 * Explain: JSON工具类
 */
public class JsonUtil {
    /**
     * 对象转换成JSON格式字符串  并格式化
     * 类似输出:
     * {
         "payTypeEnum": "WXPAY_H5",
         "orderId": "1516104088756358983",
         "orderAmount": 40.0,
         "orderName": "微信点餐订单",
         "openid": "123456789"
         }
     * @param object
     * @return
     */
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
