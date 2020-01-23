package com.mamoru.chileme.controller;

import com.mamoru.chileme.config.ProjectUrlConfig;
import com.mamoru.chileme.constant.CookieConstant;
import com.mamoru.chileme.constant.RedisConstant;
import com.mamoru.chileme.entity.BuyerInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.exception.ChilemeException;
import com.mamoru.chileme.form.BuyerForm;
import com.mamoru.chileme.service.BuyerService;
import com.mamoru.chileme.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 买家用户
 */

@Controller
@RequestMapping("/buyer")
@Slf4j
public class BuyerUserController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/login")
    public String login (@Valid BuyerForm buyerForm,
                       HttpServletResponse response,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【用户登录】参数不正确, buyerForm={}", buyerForm);
            throw new ChilemeException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //1.openid和数据库里的数据匹配
        BuyerInfo buyerInfo = buyerService.findBuyerInfoByOpenid(buyerForm.getOpenid());
        if (buyerForm.getOpenid().equals("")) {
            log.error("用户登录】openid不能为空");
            throw new ChilemeException(ResultEnum.OPENID_EMPTY);
        }
        if (buyerInfo == null) {
            log.error("【用户登录】 用户不存在, buyerForm={}", buyerForm);
            throw new ChilemeException(ResultEnum.USER_NOT_EXIST);
        }

        if (buyerForm.getPassword().equals("")) {
            log.error("【用户登录】密码不能为空");
            throw new ChilemeException(ResultEnum.PASSWORD_EMPTY);
        }

        if (!buyerForm.getPassword().equals(buyerInfo.getPassword())){
            log.error("【用户登录】密码不正确, buyerForm={}", buyerForm);
            throw new ChilemeException(ResultEnum.LOGIN_FAIL);
        }
        else {
            //2.设置redis
            String token = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;
            //3.设置cookie
            CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), buyerForm.getOpenid(), expire, TimeUnit.SECONDS);

            return  "redirect:" + projectUrlConfig.getChileme() + "/chileme/buyer/product/list";
        }

    }

}
