package com.cl.common.exception;


import com.cl.common.dto.ResponseResult;
import com.cl.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
//@RestControllerAdvice
public class MyexceptionHander {
    @ExceptionHandler(value =Exception.class )
    public ResponseResult handlerException(Exception e){
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);

    }
}
