package com.sky.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Backend unified return result
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // Code: 1 success, 0 and other numbers are failures
    private String msg; // Error information
    private T data; // Data

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
