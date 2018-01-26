package cn.chenhaoxiang.service;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/26.
 * Time: 下午 9:28.
 * Explain:
 */
public interface SeckillService {

    /**
     * 查询特价商品
     * @param productId
     * @return
     */
    String querySecKillProductInfo(String productId);

    /**
     * 秒杀的逻辑方法
     * @param productId
     */
    void orderProductMocckDiffUser(String productId);

}
