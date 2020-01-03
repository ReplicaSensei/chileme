package com.mamoru.chileme.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data       //省略getter和setter等方法
public class OrderDetail {

    @Id
    private String detailId;

    /** 买家ID. */
    private String orderId;

    /** 产品ID. */
    private String productId;

    /** 产品名称. */
    private String productName;

    /** 产品金额. */
    private BigDecimal productPrice;

    /** 产品数量. */
    private Integer productQuantity;

    /** 产品小图. */
    private String productIcon;


}
