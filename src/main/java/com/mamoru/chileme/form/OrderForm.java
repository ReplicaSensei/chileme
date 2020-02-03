package com.mamoru.chileme.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;


@Data
public class OrderForm {

    /**
     * 买家收货信息
     */
    @NotEmpty(message = "地址必填")
    private String recId;

    /**
     * 买家微信openid
     */
    @NotEmpty(message = "openid必填")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

}
