package com.mamoru.chileme.vo;

import lombok.Data;

@Data
public class OrderVO<T> {

    private T content;

    private Integer page;

    private Integer size;

    private Integer total;

    /** 错误吗. */
    private Integer code;

    /** 提示信息. */
    private String msg = "";

}
