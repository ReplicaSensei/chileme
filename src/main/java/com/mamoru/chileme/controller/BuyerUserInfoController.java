package com.mamoru.chileme.controller;

import com.mamoru.chileme.entity.BuyerInfo;
import com.mamoru.chileme.service.BuyerService;
import com.mamoru.chileme.utils.ResultVOUtil;
import com.mamoru.chileme.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buyer/user")
@Slf4j
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class BuyerUserInfoController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/info")
    @ResponseBody
    public ResultVO list(@RequestParam("openid") String openid) {
        BuyerInfo buyerInfo = buyerService.findBuyerInfoByOpenid(openid);
        if (buyerInfo==null) {
            log.error("【用户信息】查询不到此用户");
            return ResultVOUtil.error(500, "查询不到此用户");
        }



        return ResultVOUtil.success(buyerInfo);
    }
}
