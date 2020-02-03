package com.mamoru.chileme.service;

import com.mamoru.chileme.entity.RecInfo;

import java.util.List;

public interface RecService {

    List<RecInfo>  findList(String buyerId);

    RecInfo findOne(String recId);

    RecInfo save(RecInfo recInfo);
}
