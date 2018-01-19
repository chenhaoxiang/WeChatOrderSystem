package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/14.
 * Time: 下午 9:12.
 * Explain:
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {

    /**
     * 按照买家的openid查询
     * 分页查询订单
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
