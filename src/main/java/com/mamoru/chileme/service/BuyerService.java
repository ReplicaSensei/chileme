package com.mamoru.chileme.service;

import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.entity.BuyerInfo;
import com.mamoru.chileme.entity.OrderDetail;
import com.mamoru.chileme.vo.ResultVO;

import java.math.BigDecimal;

public interface BuyerService {

    //查询买家信息
    BuyerInfo findBuyerInfoByOpenid(String openid);

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);

    //支付订单
    Boolean payOrder(String openid, String orderId);

    //保存买家信息
    BuyerInfo save(BuyerInfo buyerInfo);

    //增加余额
    void increaseAmount(OrderDTO orderDTO);

    //减少余额
    boolean decreaseAmount(OrderDTO orderDTO);

    void pay(String openid, BigDecimal bigDecimal);

}
