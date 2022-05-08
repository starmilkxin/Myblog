package com.xin.myblog.exception.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNKNOWN_ERROR(400, HttpStatus.BAD_REQUEST, "其他错误"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "身份认证失败"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "未找到该资源"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "服务器错误"),
    REPEAT_ERROR(601, HttpStatus.BAD_REQUEST, "幂等性错误"),
    TOOMUCH_COMMENTS_ERROR(602, HttpStatus.BAD_REQUEST, "评论过多"),
    USERNAME_PASSWORD_ERROR(603, HttpStatus.BAD_REQUEST, "账号密码错误");


    private final int code;

    private final HttpStatus status;

    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return"ErrorCode{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
