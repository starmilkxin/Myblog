package com.xin.myblog.exception.extendedException;

import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.constant.ErrorCode;
import lombok.NonNull;
import reactor.util.annotation.Nullable;

import java.util.Map;

public class InternalServerErrorException extends BaseException {

    public InternalServerErrorException(Map<String, Object> data) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, data);
    }
}
