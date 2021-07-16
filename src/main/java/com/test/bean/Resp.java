package com.test.bean;

import lombok.Data;

/**
 * 接口统一返回对象
 *
 * @author chengz
 * @date 2020/9/18
 */
@Data
public class Resp<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;
    /**
     * 业务数据
     */
    private T data;

    /**
     * 不允许外部实例化
     */
    private Resp() {
    }

    private Resp(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 操作成功 有业务数据
     *
     * @param data 业务数据
     * @param <T>  业务对象
     * @return com.test.Resp
     */
    public static <T> Resp<T> success(T data) {
        return new Resp<>(0, "执行成功", data);
    }

    /**
     * 操作成功 有业务数据 有提示信息
     *
     * @param errmsg 提示信息
     * @param data   业务数据
     * @param <T>    业务对象
     * @return com.test.Resp
     */
    public static <T> Resp<T> success(String errmsg, T data) {
        return new Resp<>(0, errmsg, data);
    }

    /**
     * 操作成功 无业务数据 默认提示信息
     *
     * @param <T> Object
     * @return com.test.Resp
     */
    public static <T> Resp<T> success() {
        return success("执行成功");
    }

    /**
     * 操作成功 无业务数据 有提示信息
     *
     * @param errmsg 提示信息
     * @param <T>    业务对象
     * @return com.test.Resp
     */
    public static <T> Resp<T> success(String errmsg) {
        return new Resp<>(0, errmsg, null);
    }

    /**
     * 错误信息必须使用枚举,不允许直接在业务代码随意定义
     *
     * @param error 错误枚举
     * @param <T>   Object
     * @return com.test.Resp
     */
    /*public static <T> com.test.Resp<T> error(ErrorEnum error) {
        return new com.test.Resp<>(error.getCode(), error.getMsg(), null);
    }*/

    /**
     * 带业务数据的错误信息
     *
     * @param error 错误枚举
     * @param data  业务数据
     * @param <T>   Object
     * @return com.test.Resp
     */
    /*public static <T> com.test.Resp<T> error(ErrorEnum error, T data) {
        return new com.test.Resp<>(error.getCode(), error.getMsg(), data);
    }*/

    /**
     * 状态码不能更改
     *
     * @param code 状态码
     */
    private void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 状态信息不能更改
     *
     * @param msg 状态信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 为兼容历史代码,状态信息允许添加
     * 当有不确定的状态信息要返回时,应当放到业务数据中
     *
     * @param errmsg 状态信息
     */
    public Resp<T> appendErrmsg(String errmsg) {
        this.msg += "; " + errmsg;
        return this;
    }
}
