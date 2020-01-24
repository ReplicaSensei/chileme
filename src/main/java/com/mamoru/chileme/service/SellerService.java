package com.mamoru.chileme.service;

import com.mamoru.chileme.entity.SellerInfo;

/**
 * 卖家端
 */
public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo save(SellerInfo sellerInfo);

}
