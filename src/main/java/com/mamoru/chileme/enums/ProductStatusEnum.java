package com.mamoru.chileme.enums;

import lombok.Getter;

/**
 * 商品状态status枚举
 */

@Getter
public enum ProductStatusEnum implements CodeEnum {

    /** Up表示在售，Down表示已下架. */
    UP(0, "在售"),
    DOWN(1, "已下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
