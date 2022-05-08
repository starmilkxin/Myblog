package com.xin.myblog.exception.extendedException;

import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.constant.ErrorCode;

import java.util.Map;

public class NotFoundException extends BaseException {

    public NotFoundException(Map<String, Object> data) {
        super(ErrorCode.NOT_FOUND, data);
    }
}
