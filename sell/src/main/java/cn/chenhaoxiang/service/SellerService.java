package cn.chenhaoxiang.service;

import cn.chenhaoxiang.dataObject.SellerInfo;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 6:47.
 * Explain: 卖家端service
 */
public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

}
