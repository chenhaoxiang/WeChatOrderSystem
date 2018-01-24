package cn.chenhaoxiang.service.impl;

import cn.chenhaoxiang.config.WechatAccountConfig;
import cn.chenhaoxiang.dto.OrderDTO;
import cn.chenhaoxiang.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/24.
 * Time: 下午 9:24.
 * Explain:
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;
    @Override
    public void orderStatus(OrderDTO orderDTO) {
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","亲，请记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","17674031234"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临")
        );
        WxMpTemplateMessage templateMessage =WxMpTemplateMessage.builder()
                .toUser(orderDTO.getBuyerOpenid())
                .templateId(wechatAccountConfig.getTemplateId().get("orderStatus"))
                .data(dataList)
                .build();
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            //不抛异常出去 防止其他地方的事务回滚
            log.error("[微信模板消息] 发送失败,{}",e);
        }
    }
}
