package com.xin.myblog.exception.extendedException;

import com.xin.myblog.exception.BaseException;
import com.xin.myblog.exception.constant.ErrorCode;

import java.util.Map;

public class ToMuchCommentException extends BaseException {

    public ToMuchCommentException(Map<String, Object> data) {
        super(ErrorCode.TOOMUCH_COMMENTS_ERROR, data);
    }
}
