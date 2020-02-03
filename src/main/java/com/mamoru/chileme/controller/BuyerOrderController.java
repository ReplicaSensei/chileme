package com.mamoru.chileme.controller;

import com.mamoru.chileme.converter.OrderForm2OrderDTOConverter;
import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.entity.RecInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.OrderForm;
import com.mamoru.chileme.service.BuyerService;
import com.mamoru.chileme.service.OrderService;
import com.mamoru.chileme.service.RecService;
import com.mamoru.chileme.utils.ResultVOUtil;
import com.mamoru.chileme.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  买家页面
 */

@RestController
@RequestMapping("/buyer/order")
@Slf4j
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private RecService recService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new ChilemeException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        RecInfo recInfo = recService.findOne(orderForm.getRecId());
        //转换成列表，一次可订多个菜品, 已在OrderForm2OrderDTOConvert中完成
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm, recInfo);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            //throw new ChilemeException(ResultEnum.CART_EMPTY);
            return ResultVOUtil.error(500, "购物车不能为空");
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            //throw new ChilemeException(ResultEnum.PARAM_ERROR);
            return ResultVOUtil.error(500, "openid不能为空");
        }
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        PageRequest request = new PageRequest(page, size, sort);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUtil.success(orderDTOPage.getContent());

    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);

    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }

    //支付订单
    @PostMapping("/pay")
    public ResultVO<OrderDTO> pay(@RequestParam("openid") String openid,
                                  @RequestParam("orderId") String orderId) {
        Boolean result = buyerService.payOrder(openid, orderId);
        if (result) {
            return ResultVOUtil.success();
        }
        else {
            return ResultVOUtil.error(500, "账户余额不足");
        }
    }

}
