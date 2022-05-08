package com.xin.myblog.exception.extendedException;

import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.constant.ErrorCode;

import java.util.Map;

public class UsernamePasswordException extends BaseException {
    public UsernamePasswordException(Map<String, Object> data) {
        super(ErrorCode.USERNAME_PASSWORD_ERROR, data);
    }
}
