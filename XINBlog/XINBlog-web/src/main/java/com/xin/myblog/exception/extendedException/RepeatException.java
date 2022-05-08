package com.xin.myblog.exception.extendedException;

import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.constant.ErrorCode;

import java.util.Map;

public class RepeatException extends BaseException {

    public RepeatException(Map<String, Object> data) {
        super(ErrorCode.REPEAT_ERROR, data);
    }
}
