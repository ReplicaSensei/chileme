package com.mamoru.chileme.dao;

import com.mamoru.chileme.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterDao extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    OrderMaster findByOrderId(String orderId);

    List<OrderMaster> findByBuyerOpenid(String buyerOpenid);
}
