package com.mamoru.chileme.service;

import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.entity.BuyerInfo;

public interface BuyerService {

    //查询买家信息
    BuyerInfo findBuyerInfoByOpenid(String openid);

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);

}
