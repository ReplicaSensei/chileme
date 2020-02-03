package com.mamoru.chileme.dto;

/**
 * 数据传输对象
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mamoru.chileme.entity.OrderDetail;
import com.mamoru.chileme.enums.OrderStatusEnum;
import com.mamoru.chileme.enums.PayStatusEnum;
import com.mamoru.chileme.utils.EnumUtil;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家电话. */
    private String orderPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 订单金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单 */
    private Integer orderStatus;

    /** 支付状态,默人为0未支付 */
    private Integer payStatus;

    /** 微信openid. */
    private String buyerOpenid;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
