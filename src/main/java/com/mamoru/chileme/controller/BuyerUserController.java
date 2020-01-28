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
import com.mamoru.chileme.utils.KeyUtil;
import com.mamoru.chileme.utils.LoginVOUtil;
import com.mamoru.chileme.utils.ResultVOUtil;
import com.mamoru.chileme.vo.LoginVO;
import com.mamoru.chileme.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 买家用户
 */

@Controller
@RequestMapping("/buyer")
@Slf4j
@CrossOrigin(value = "*", origins = "*", methods = {}, maxAge = 3600, allowCredentials = "true",
        allowedHeaders = "Origin, X-Requested-With, Content-Type, Accept")
public class BuyerUserController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/register")
    @ResponseBody
    public ResultVO<Map<String, Object>> register(@Valid BuyerForm buyerForm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【用户登录】参数不正确, buyerForm={}", buyerForm);
            throw new ChilemeException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        if (buyerService.findBuyerInfoByOpenid(buyerForm.getOpenid())!=null){
            log.error("【用户登录】openid已存在, buyerForm={}", buyerForm);
            throw new ChilemeException(ResultEnum.USER_EXIST);
        }
        if (buyerForm.getOpenid().equals("")||buyerForm.getPassword().equals("")||buyerForm.getUsername().equals("")){
            log.error("【用户登录】用户信息不完整");
            throw new ChilemeException(ResultEnum.PARAM_ERROR);
        }
        BuyerInfo buyerInfo = new BuyerInfo();
        BeanUtils.copyProperties(buyerForm, buyerInfo);
        buyerInfo.setBuyerId(KeyUtil.genUniqueKey());
        buyerInfo.setAccount(new BigDecimal(0));

        BuyerInfo registerResult = buyerService.save(buyerInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("buyerId", registerResult.getBuyerId());
        map.put("username", registerResult.getUsername());
        map.put("openid", registerResult.getOpenid());
        map.put("account", registerResult.getAccount());
        return ResultVOUtil.success(map);
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginVO login (@Valid BuyerForm buyerForm,
                          HttpServletResponse response,
                          BindingResult bindingResult) {
        String login = "登陆";
        if (bindingResult.hasErrors()) {
            log.error("【用户登录】参数不正确, buyerForm={}", buyerForm);
            throw new ChilemeException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //1.openid和数据库里的数据匹配
        BuyerInfo buyerInfo = buyerService.findBuyerInfoByOpenid(buyerForm.getOpenid());
        if (buyerForm.getOpenid().equals("")) {
            log.error("用户登录】openid不能为空");
            //throw new ChilemeException(ResultEnum.OPENID_EMPTY);
            return LoginVOUtil.error("openid不能为空");
        }
        if (buyerInfo == null) {
            log.error("【用户登录】 用户不存在, buyerForm={}", buyerForm);
            //throw new ChilemeException(ResultEnum.USER_NOT_EXIST);
            return LoginVOUtil.error("用户名不存在");
        }

        if (buyerForm.getPassword().equals("")) {
            log.error("【用户登录】密码不能为空");
            //throw new ChilemeException(ResultEnum.PASSWORD_EMPTY);
            return LoginVOUtil.error("密码不能为空");
        }

        if (!buyerForm.getPassword().equals(buyerInfo.getPassword())){
            log.error("【用户登录】密码不正确, buyerForm={}", buyerForm);
            //throw new ChilemeException(ResultEnum.LOGIN_FAIL);
            return LoginVOUtil.error("用户名或密码不匹配");
        }
        else {
            //2.设置redis
            String token = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;
            //3.设置cookie
            CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), buyerForm.getOpenid(), expire, TimeUnit.SECONDS);
            return LoginVOUtil.success(login);
        }

    }
    //TODO
    @GetMapping("/logout")
    @ResponseBody
    public LoginVO logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session,
                         Map<String, Object> map) {
        String logout = "注销";
        //1.从cookie查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
            //4.清除session
            session.invalidate();

        }
        return LoginVOUtil.success(logout);

    }

}
