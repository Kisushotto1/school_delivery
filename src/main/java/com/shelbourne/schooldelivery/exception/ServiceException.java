package com.shelbourne.schooldelivery.exception;

import com.shelbourne.schooldelivery.common.States;
import lombok.Getter;

/*
自定义异常ServiceException继承运行时异常
 */
@Getter
public class ServiceException extends RuntimeException {
    private States state;

    public ServiceException(States state, String message) {
        super(message);
        this.state = state;
    }
}
