package com.xin.myblog.exception.vo;

import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private int code;
    private int status;
    private String message;
    private String path;
    private Instant timeStrap;
    private Map<String, Object> data;

    public ErrorResponse(BaseException exception, String path) {
        this(exception.getError().getCode(), exception.getError().getStatus().value(), exception.getMessage(), path, Instant.now(), exception.getData());
    }

    public ErrorResponse(Exception exception, String path) {
        this(ErrorCode.UNKNOWN_ERROR.getCode(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), path, Instant.now(), null);
    }
}
