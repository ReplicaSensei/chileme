package com.mamoru.chileme.controller;

import com.mamoru.chileme.config.ProjectUrlConfig;
import com.mamoru.chileme.constant.CookieConstant;
import com.mamoru.chileme.constant.RedisConstant;
import com.mamoru.chileme.entity.SellerInfo;
import com.mamoru.chileme.enums.ResultEnum;
import com.mamoru.chileme.form.SellerForm;
import com.mamoru.chileme.service.SellerService;
import com.mamoru.chileme.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("")
    public ModelAndView login() {
        return new ModelAndView("seller/login");
    }

    @PostMapping("/submit")
    public ModelAndView save(@Valid SellerForm form,
                             BindingResult bindingResult,
                             HttpServletResponse response,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/chileme/seller");
            return new ModelAndView("common/error", map);
        }
        //1.openid和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(form.getOpenid());
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/chileme/seller");
            return new ModelAndView("common/error", map);
        }

        if (form.getOpenid().equals("")) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/chileme/seller");
            return new ModelAndView("common/error", map);
        }

        if (form.getPassword().equals("")) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/chileme/seller");
            return new ModelAndView("common/error", map);
        }

        if (!form.getPassword().equals(sellerInfo.getPassword())){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/chileme/seller");
            return new ModelAndView("common/error", map);
        }

        else {
            //2.设置redis
            String token = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;
            //3.设置cookie
            CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), form.getOpenid(), expire, TimeUnit.SECONDS);

            return new ModelAndView("redirect:" + projectUrlConfig.getChileme() + "/chileme/seller/order/list");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {

        //1.从cookie查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/chileme/seller");
        return new ModelAndView("common/success", map);

    }

}
