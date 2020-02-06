package com.mamoru.chileme.controller;

import com.mamoru.chileme.entity.BuyerInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.PayForm;
import com.mamoru.chileme.service.BuyerService;
import com.mamoru.chileme.utils.ResultVOUtil;
import com.mamoru.chileme.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/seller/pay")
@Slf4j
public class SellerPayController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/index")
    public ModelAndView pay() {
        return new ModelAndView("doBuyer/pay");
    }

    @PostMapping("/submit")
    public ModelAndView submit(@Valid PayForm payForm,
                        BindingResult bindingResult,
                        Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/chileme/seller/pay/index");
            return new ModelAndView("common/error", map);
        }
        BuyerInfo buyerInfo = buyerService.findBuyerInfoByOpenid(payForm.getOpenid());
        if (buyerInfo==null) {
            map.put("msg", "openid不存在");
            map.put("url", "/chileme/seller/pay/index");
            return new ModelAndView("common/error", map);
        }
        buyerService.pay(payForm.getOpenid(), payForm.getPayNumber());
        map.put("url", "/chileme/seller/pay/index");
        return new ModelAndView("common/success", map);
    }
}
