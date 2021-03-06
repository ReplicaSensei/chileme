package com.mamoru.chileme.service;

import com.mamoru.chileme.dto.CartDTO;
import com.mamoru.chileme.entity.ProductInfo;
import com.mamoru.chileme.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    /** 查询所有在架商品列表. */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /** 加库存. */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存. */
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 下架. */
    ProductInfo offSale(String productId);

    /** 上架. */
    ProductInfo onSale(String productId);

    /** 删除. */
    void delete(String productId);
}
