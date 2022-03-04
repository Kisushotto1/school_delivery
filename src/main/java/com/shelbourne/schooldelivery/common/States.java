package com.shelbourne.schooldelivery.common;

public enum States {
    SUCCESS,//成功
    USER_EXCEPTION,//用户状态异常
    PERMISSION_DENIED,//权限不足
    NOT_FOUND,//页面找不到
    INTERNAL_ERROR,//服务器错误
    SERVICE_EXCEPTION,//Service层业务异常
    DATABASE_EXCEPTION,//数据库异常
    UNKNOWN_ERROR//未知错误
}
