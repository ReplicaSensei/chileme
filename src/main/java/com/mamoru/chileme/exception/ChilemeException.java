package com.mamoru.chileme.exception;

import com.mamoru.chileme.enums.ResultEnum;

public class ChilemeException extends RuntimeException {

    private Integer code;

    public ChilemeException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ChilemeException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
