package com.shelbourne.schooldelivery.exception;

import com.shelbourne.schooldelivery.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class) //捕获ServiceException类抛出的异常
    @ResponseBody  //当捕获异常后，返回报错信息响应体
    public Result handle(ServiceException se) {
        return Result.error(se.getState(), se.getMessage());
    }
}
