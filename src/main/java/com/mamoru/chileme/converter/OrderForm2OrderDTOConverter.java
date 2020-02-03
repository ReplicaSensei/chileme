package com.mamoru.chileme.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.entity.OrderDetail;
import com.mamoru.chileme.entity.RecInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.OrderForm;
import com.mamoru.chileme.service.RecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm, RecInfo recInfo) {

        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(recInfo.getRecName());
        orderDTO.setOrderPhone(recInfo.getRecTelephone());
        orderDTO.setBuyerAddress(recInfo.getRecAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //订单详情转换成详情列表， 一个订单可点多个菜品
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new ChilemeException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

}
