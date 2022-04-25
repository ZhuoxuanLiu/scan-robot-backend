package com.zhuoxuanliu.backend.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler
    public R HandelException(Exception e){
        e.printStackTrace();
        return new R(e.getMessage());
    }

}
