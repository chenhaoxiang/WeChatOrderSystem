package cn.chenhaoxiang.utils;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 10:56.
 * Explain:
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String getUniqueKey(){//加一个锁
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;//随机六位数
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
