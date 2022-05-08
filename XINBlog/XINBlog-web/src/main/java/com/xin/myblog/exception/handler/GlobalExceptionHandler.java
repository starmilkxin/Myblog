package com.xin.myblog.exception.handler;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.constant.ResultCode;
import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.vo.ErrorResponse;
import com.xin.myblog.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public String exceptionHandler(Exception e, HttpServletRequest request) {
        String error = JSON.toJSONString(new Result(ResultCode.ERROR).data("ErrorResponse", new ErrorResponse(e, request.getRequestURI())));
        log.error(error, e);
        return error;
    }

    @ExceptionHandler(BaseException.class)
    public String baseExceptionHandler(BaseException e, HttpServletRequest request) {
        String error = JSON.toJSONString(Result.error(e).data("ErrorResponse", new ErrorResponse(e, request.getRequestURI())));
        log.error(error);
        return error;
    }


}
