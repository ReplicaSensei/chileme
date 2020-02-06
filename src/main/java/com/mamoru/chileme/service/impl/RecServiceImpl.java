package com.mamoru.chileme.service.impl;

import com.mamoru.chileme.dao.RecInfoDao;
import com.mamoru.chileme.entity.RecInfo;
import com.mamoru.chileme.service.RecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecServiceImpl implements RecService {

    @Autowired
    private RecInfoDao dao;

    @Override
    public List<RecInfo> findList(String buyerId) {
        return dao.findByBuyerId(buyerId);
    }

    @Override
    public RecInfo findOne(String recId) {
        return dao.findByRecId(recId);
    }

    @Override
    public RecInfo save(RecInfo recInfo) {
        return dao.save(recInfo);
    }

    @Override
    public void delete(RecInfo recInfo) {
        dao.delete(recInfo);
    }
}
