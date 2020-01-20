package com.mamoru.chileme.controller;

import com.mamoru.chileme.entity.SellerInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              Map<String, Object> map) {

        //1.openid和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "chileme/seller/order/list");
            return new ModelAndView("common/list");
        }
        //2.设置token至redis

        //3.设置token至cookie

        return null;

    }

    @GetMapping("logout")
    public void logout() {

    }

}
