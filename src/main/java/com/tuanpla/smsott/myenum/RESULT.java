/*
 * Copyright 2022 by Tuanpla
 * https://tuanpla.com
 */
package com.tuanpla.smsott.myenum;

import com.tuanpla.utils.string.StringUtils;

/**
 *
 * @author tuanp
 */
public enum RESULT {
    UNKNOW_ERROR(-1, "Unknow error"),
    FAIL(0, "Failed"),
    SUCCESS(1, "Success"),
    USER_NOT_FOUND(2, "User not found"),
    IP_NOT_ALLOW(3, "ip not Allow"),
    SECRET_NOT_VALID(4, "Secret not valid"), //
    TIMESEND_NOT_VALID(5, "timeSend not valid"), //
    ;
    public final int code;
    public final String msg;

    private RESULT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public static RESULT getResultByname(String name) {
        RESULT result = null;
        for (RESULT one : RESULT.values()) {
            if (!StringUtils.isEmpty(name) && one.name().equals(name)) {
                result = one;
                break;
            }
        }
        return result;
    }
}
