package cn.chenhaoxiang.utils;

import cn.chenhaoxiang.enums.CodeEnum;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/21.
 * Time: 下午 7:48.
 * Explain: 枚举工具类
 */
public class EnumUtil {
    /**
     * 通过code和枚举类型获取枚举
     * @param code code
     * @param enumClass 枚举类型class
     * @param <T>  枚举类型
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each: enumClass.getEnumConstants()){//遍历枚举类型
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return  null;
    }
}
