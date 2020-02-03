package com.mamoru.chileme.dao;

import com.mamoru.chileme.entity.RecInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecInfoDao extends JpaRepository<RecInfo, String> {

    List<RecInfo> findByBuyerId(String buyerId);

    RecInfo findByRecId(String recId);

}
