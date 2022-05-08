package com.xin.myblog.exception;

import com.xin.myblog.exception.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException{
    private final ErrorCode error;
    private final Map<String, Object> data = new HashMap<>();

    public BaseException(ErrorCode error, Map<String, Object> data) {
        super(error.getMessage());
        this.error = error;
        if (data != null) {
            if (!data.isEmpty()) {
                this.data.putAll(data);
            }
        }
    }

    protected BaseException(ErrorCode error, Map<String, Object> data, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
        if (data != null) {
            if (!data.isEmpty()) {
                this.data.putAll(data);
            }
        }
    }

    public ErrorCode getError() {
        return error;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
