package com.mamoru.chileme.utils;

import com.mamoru.chileme.vo.LoginVO;

public class LoginVOUtil {
    public static LoginVO success(String string) {
        LoginVO loginVO = new LoginVO();
        loginVO.setCode(0);
        loginVO.setMsg(string+"成功");
        return loginVO;
    }
    public static LoginVO error(String string) {
        LoginVO loginVO = new LoginVO();
        loginVO.setCode(500);
        loginVO.setMsg(string);
        return loginVO;
    }
}
