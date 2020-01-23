package com.mamoru.chileme.aspect;

import com.mamoru.chileme.constant.CookieConstant;
import com.mamoru.chileme.constant.RedisConstant;
import com.mamoru.chileme.exception.ChilemeLoginException;
import com.mamoru.chileme.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerLoginAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.mamoru.chileme.controller.Seller*.*(..))" +
    "&& !execution(public * com.mamoru.chileme.controller.SellerUserController.*(..))")
    private void verify() {}

    @Before("verify()")
    private void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登陆校验】 Cookie中查不到token");
            throw new ChilemeLoginException();
        }

        //查询redis
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登陆校验】 Redis中查不到token");
            throw new ChilemeLoginException();
        }
    }

}
