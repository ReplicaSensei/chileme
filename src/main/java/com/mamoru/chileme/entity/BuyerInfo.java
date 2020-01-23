package com.mamoru.chileme.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data       //省略getter和setter等方法
public class BuyerInfo {
    @Id
    private String buyerId;

    /** 用户名. */
    private String username;

    /** 密码. */
    private String password;

    /** 微信openid. */
    private String openid;

    /** 账户余额. */
    private BigDecimal account;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
