package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.dao.SellerInfoDao;
import cn.chenhaoxiang.dataObject.SellerInfo;
import cn.chenhaoxiang.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 6:48.
 * Explain:
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoDao sellerInfoDao;
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}
