package com.mamoru.chileme.vo;

import lombok.Data;

@Data
public class LoginVO {
    /** 错误吗. */
    private Integer code;

    /** 提示信息. */
    private String msg = "";
}
