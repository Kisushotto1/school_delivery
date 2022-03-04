package com.shelbourne.schooldelivery.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
接口统一返回包装类Result
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private States state;//前后端均可使用的状态信息
    private String msg;
    private Object data;

    public Result(States success, String msg) {
        this.setState(success);
        this.setMsg(msg);
    }

    public static Result success() {
        return new Result(States.SUCCESS, "成功", null);
    }

    public static Result error() {
        return new Result(States.UNKNOWN_ERROR, "未知错误！", null);
    }

    public static Result success(States state, String msg, Object data) {
        return new Result(state, msg, data);
    }

    public static Result error(States state, String msg) {
        return new Result(state, msg);
    }

    public static Result success(States state, String msg) {
        return new Result(state, msg);
    }
}
