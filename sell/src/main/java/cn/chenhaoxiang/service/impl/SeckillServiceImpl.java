package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.exception.SellException;
import cn.chenhaoxiang.service.RedisLock;
import cn.chenhaoxiang.service.SeckillService;
import cn.chenhaoxiang.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/26.
 * Time: 下午 9:30.
 * Explain:
 */
@Service
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 10*1000;//超时时间 10s

    /**
     * 活动，特价，限量100000份
     */
    static Map<String,Integer> products;//模拟商品信息表
    static Map<String,Integer> stock;//模拟库存表
    static Map<String,String> orders;//模拟下单成功用户表
    static {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
          */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }

    private String queryMap(String productId){//模拟查询数据库
        return "国庆活动，皮蛋特教，限量"
                +products.get(productId)
                +"份,还剩:"+stock.get(productId)
                +"份,该商品成功下单用户数:"
                +orders.size()+"人";
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    //解决方法二，基于Redis的分布式锁 http://redis.cn/commands/setnx.html  http://redis.cn/commands/getset.html
    //SETNX命令  将key设置值为value，如果key不存在，这种情况下等同SET命令。 当key存在时，什么也不做
    // GETSET命令  先查询出原来的值，值不存在就返回nil。然后再设置值
    //支持分布式，可以更细粒度的控制
    //多台机器上多个线程对一个数据进行操作的互斥。
    //Redis是单线程的!!!
    @Override
    public void orderProductMocckDiffUser(String productId) {//解决方法一:synchronized锁方法是可以解决的，但是请求会变慢,请求变慢是正常的。主要是没做到细粒度控制。比如有很多商品的秒杀，但是这个把所有商品的秒杀都锁住了。而且这个只适合单机的情况，不适合集群

        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if(!redisLock.lock(productId,String.valueOf(time))){
            throw new SellException(101,"很抱歉，人太多了，换个姿势再试试~~");
        }

        //1.查询该商品库存，为0则活动结束
        int stockNum = stock.get(productId);
        if(stockNum==0){
            throw new SellException(100,"活动结束");
        }else {
            //2.下单
            orders.put(KeyUtil.getUniqueKey(),productId);
            //3.减库存
            stockNum =stockNum-1;//不做处理的话，高并发下会出现超卖的情况，下单数，大于减库存的情况。虽然这里减了，但由于并发，减的库存还没存到map中去。新的并发拿到的是原来的库存
            try{
                Thread.sleep(100);//模拟减库存的处理时间
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //解锁
        redisLock.unlock(productId,String.valueOf(time));

    }


}
