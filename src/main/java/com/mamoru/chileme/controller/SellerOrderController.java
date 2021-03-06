package com.mamoru.chileme.controller;


import com.mamoru.chileme.dto.OrderDTO;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页，从1页开始
     * @param size 一页有多少条数据
     * @return
     */

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        PageRequest request = new PageRequest(page-1, size, sort);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);

        } catch (ChilemeException e){
            log.error("【卖家端取消订单】 发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/order/list");

            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("url", "/chileme/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
            if(StringUtils.isEmpty(orderDTO.getPsInfo())) {
                orderDTO.setPsInfo("无");
            }
        }catch (ChilemeException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam(value = "orderId", required = false) String orderId,
                               Map<String, Object> map) {

        try {
            orderService.delete(orderId);
        } catch (ChilemeException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/chileme/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/send")
    public ModelAndView send(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.send(orderDTO);

        } catch (ChilemeException e){
            log.error("【卖家端改变商品状态】 商品已送出", e);
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/order/list");

            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("url", "/chileme/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (ChilemeException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/chileme/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/chileme/seller/order/list");
        return new ModelAndView("common/success");
    }

}
