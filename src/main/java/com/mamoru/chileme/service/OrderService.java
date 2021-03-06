package com.mamoru.chileme.service;

import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.entity.OrderMaster;
import com.mamoru.chileme.vo.ResultVO;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查询订单列表. */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 查询全部订单. */
    List<OrderDTO> findAllList(String buyerOpenid);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    boolean paid(OrderDTO orderDTO);

    /** 查询订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

    /** 送出订单. */
    OrderDTO send(OrderDTO orderDTO);

    void delete(String orderId);
}
