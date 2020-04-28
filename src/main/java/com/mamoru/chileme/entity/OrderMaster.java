package com.mamoru.chileme.entity;

import com.mamoru.chileme.enums.OrderStatusEnum;
import com.mamoru.chileme.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data       //省略getter和setter等方法
public class OrderMaster {

    @Id
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家电话. */
    private String orderPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 订单金额. */
    private BigDecimal orderAmount;

    /** 订单备注. */
    private String psInfo;

    /** 订单状态, 默认为0新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态,默人为0未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 微信openid. */
    private String buyerOpenid;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

}
