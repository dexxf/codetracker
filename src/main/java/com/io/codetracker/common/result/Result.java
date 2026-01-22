package com.io.codetracker.common.result;

public record Result<T,K> (boolean success, T data, K error){
    public static <T,K> Result<T,K> ok(T data) {
        return new Result<>(true,data,null);
    }

    public static <T,K> Result<T,K> fail(K error) {
        return new Result<>(false, null, error);
    }

}
