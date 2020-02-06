package com.mamoru.chileme.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayForm {

    public String openid;

    BigDecimal payNumber;

}
