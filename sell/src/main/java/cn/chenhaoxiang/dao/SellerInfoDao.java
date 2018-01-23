package cn.chenhaoxiang.dao;

import cn.chenhaoxiang.dataObject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/23.
 * Time: 下午 10:50.
 * Explain:
 */
public interface SellerInfoDao extends JpaRepository<SellerInfo,String>{
    SellerInfo findByOpenid(String openid);
}
