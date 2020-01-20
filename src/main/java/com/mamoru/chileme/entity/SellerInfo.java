package com.mamoru.chileme.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate
@Data       //省略getter和setter等方法
public class SellerInfo {
    @Id
    private String sellerId;

    /** 用户名. */
    private String username;

    /** 密码. */
    private String password;

    /** 微信openid. */
    private String openid;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
