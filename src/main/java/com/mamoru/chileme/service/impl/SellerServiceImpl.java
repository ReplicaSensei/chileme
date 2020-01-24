package com.mamoru.chileme.service.impl;

import com.mamoru.chileme.dao.SellerInfoDao;
import com.mamoru.chileme.entity.SellerInfo;
import com.mamoru.chileme.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoDao dao;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return dao.findByOpenid(openid);
    }

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return dao.save(sellerInfo);
    }
}
