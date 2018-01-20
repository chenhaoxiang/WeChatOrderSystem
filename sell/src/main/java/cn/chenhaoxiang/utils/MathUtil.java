package cn.chenhaoxiang.utils;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/20.
 * Time: 下午 11:42.
 * Explain:
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;
    /**
     * 比较两个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1,Double d2){
        Double result =Math.abs(d1-d2);
        if(result < MONEY_RANGE){
            return true;
        }else {
            return false;
        }
    }

}
