package com.dlc.electronicbalance.net.exception;

import android.support.annotation.StringRes;

import com.dlc.electronicbalance.utils.ResUtil;


/**
 * Created by John on 2017/6/12.
 */

public class ErrorMsgException extends RuntimeException {

    private int code;

    public ErrorMsgException() {
        super("运行错误");
    }

    public ErrorMsgException(String message) {
        super(message);
    }

    public ErrorMsgException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ErrorMsgException(@StringRes int resId) {
        super(ResUtil.getString(resId));
    }

    public ErrorMsgException(@StringRes int resId, int code) {
        this(resId);
        this.code = code;
    }
}
