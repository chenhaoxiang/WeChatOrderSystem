package cn.chenhaoxiang.constans;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 7:55.
 * Explain: Redis常量
 */
public interface RedisConstans {
    /**
     * token前缀
     */
    String TOKEN_PREFIX="token_%s";
    /**
     * 过期时间
     */
    Integer EXPIPE = 7200; //单位s

}
