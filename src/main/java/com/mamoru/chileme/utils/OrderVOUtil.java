package com.mamoru.chileme.utils;

import com.mamoru.chileme.vo.OrderVO;

public class OrderVOUtil {

    public static OrderVO success (Object object, Integer page, Integer size, Integer total) {

        OrderVO orderVO = new OrderVO();
        orderVO.setPage(page);
        orderVO.setSize(size);
        orderVO.setContent(object);
        orderVO.setTotal(total);
        return orderVO;
    }

    public static OrderVO error(Integer code, String msg) {
        OrderVO orderVO = new OrderVO();
        orderVO.setCode(code);
        orderVO.setMsg(msg);
        return orderVO;
    }

}
