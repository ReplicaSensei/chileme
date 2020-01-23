package com.mamoru.chileme.dao;

import com.mamoru.chileme.entity.BuyerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerInfoDao extends JpaRepository<BuyerInfo, String> {
    BuyerInfo findByOpenid(String openid);
}
