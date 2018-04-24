package com.lxf.mvp.model;

/**
 * Created by lixiaofan on 2017/7/24.
 */

public class BaseResult<T> {
    public  Integer code;
    public  T data;
    public String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
