package com.mamoru.chileme.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class RecInfo {
    @Id
    private String recId;

    private String buyerId;

    private String recName;

    private String recTelephone;

    private String recAddress;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
