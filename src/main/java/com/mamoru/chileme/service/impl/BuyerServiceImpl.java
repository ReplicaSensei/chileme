package com.mamoru.chileme.service.impl;

import com.mamoru.chileme.dao.BuyerInfoDao;
import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.entity.BuyerInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.service.BuyerService;
import com.mamoru.chileme.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerInfoDao dao;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if(orderDTO==null){
            log.error("【取消订单】查不到该订单， orderId={}", orderId);
            throw new ChilemeException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    @Override
    public Boolean payOrder(String openid, String orderId) {
            OrderDTO orderDTO = checkOrderOwner(openid, orderId);
            if(orderDTO==null){
                log.error("【取消订单】查不到该订单， orderId={}", orderId);
                throw new ChilemeException(ResultEnum.ORDER_NOT_EXIST);
            }
            Boolean result = orderService.paid(orderDTO);
            if (result) {
                return true;
            }
            return false;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new ChilemeException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;

    }

    @Override
    public BuyerInfo save(BuyerInfo buyerInfo) {
        return dao.save(buyerInfo);
    }

    @Override
    public BuyerInfo findBuyerInfoByOpenid(String openid) {
        return dao.findByOpenid(openid);
    }

    public BuyerServiceImpl() {
        super();
    }

    @Override
    public void increaseAmount(OrderDTO orderDTO) {
        BuyerInfo buyerInfo = findBuyerInfoByOpenid(orderDTO.getBuyerOpenid());
        if (buyerInfo == null) {
            throw new ChilemeException(ResultEnum.USER_NOT_EXIST);
        }
        BigDecimal result = buyerInfo.getAccount().add(orderDTO.getOrderAmount());
        buyerInfo.setAccount(result);
        dao.save(buyerInfo);
    }

    @Override
    public boolean decreaseAmount(OrderDTO orderDTO) {
        BuyerInfo buyerInfo = findBuyerInfoByOpenid(orderDTO.getBuyerOpenid());
        if (buyerInfo == null) {
            throw new ChilemeException(ResultEnum.USER_NOT_EXIST);
        }
        BigDecimal result = buyerInfo.getAccount().subtract(orderDTO.getOrderAmount());
        if (result.compareTo(new BigDecimal(0))==-1) {
            //throw new ChilemeException(ResultEnum.ACCOUNT_NOT_ENOUGH);
            return false;
        }
        else {
            buyerInfo.setAccount(result);
            dao.save(buyerInfo);
            return true;
        }
    }

    @Override
    public void pay(String openid, BigDecimal payNumber) {
        BuyerInfo buyerInfo = findBuyerInfoByOpenid(openid);
        buyerInfo.setAccount(buyerInfo.getAccount().add(payNumber));
        dao.save(buyerInfo);
    }
}
