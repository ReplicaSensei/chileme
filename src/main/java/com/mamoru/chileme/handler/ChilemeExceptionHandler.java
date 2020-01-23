package com.mamoru.chileme.handler;

import com.mamoru.chileme.config.ProjectUrlConfig;
import com.mamoru.chileme.exception.ChilemeLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ChilemeExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登陆异常
    @ExceptionHandler(value = ChilemeLoginException.class)
    public ModelAndView handlerLoginException() {
        return new ModelAndView("redirect:"
        .concat(projectUrlConfig.getChileme())
        .concat("/chileme/seller"));
    }
}
