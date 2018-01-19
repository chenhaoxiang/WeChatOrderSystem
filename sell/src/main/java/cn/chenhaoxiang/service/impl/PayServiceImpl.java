package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.service.PayService;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/19.
 * Time: 下午 9:25.
 * Explain:
 */
@Service
public class PayServiceImpl implements PayService {
    @Override
    public void create(OrderDTO orderDTO) {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();

    }
}
