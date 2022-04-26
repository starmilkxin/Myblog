package com.xin.myblog.pojo;

import com.xin.myblog.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Result implements ResultCode, Serializable {
    private static final long serialVersionUID = 64121313844L;

    private int code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Result(int code) {
        this.code = code;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result error() {
        return new Result(ResultCode.ERROR);
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result data(Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            this.data.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Result data(String key, Object val) {
        this.data.put(key, val);
        return this;
    }
}
