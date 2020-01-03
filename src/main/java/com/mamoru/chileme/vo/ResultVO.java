package com.mamoru.chileme.vo;
/**
 * http请求返回的最外层对象
 */

import lombok.Data;

@Data
public class ResultVO<T> {

    /** 错误吗. */
    private Integer code;

    /** 提示信息. */
    private String msg = "";

    /** 具体内容. */
    private T data;
}
