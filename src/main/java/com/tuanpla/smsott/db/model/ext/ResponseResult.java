/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
package com.tuanpla.smsott.db.model.ext;

import com.tuanpla.smsott.myenum.RESULT;
import com.tuanpla.utils.json.GsonUtil;
import static com.tuanpla.utils.logging.LogUtils.getLogMessage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Company
 */
public class ResponseResult implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ResponseResult.class);
    private Integer code;
    private String msg;
    String tk;       // token
    Map opt;        // option parameter

    public ResponseResult() {
        this.code = RESULT.FAIL.code;
        this.msg = "";
    }

    public ResponseResult(String mess) {
        this.code = RESULT.FAIL.code;
        this.msg = mess;
    }

    public ResponseResult(RESULT code, String mess) {
        this.code = code.code;
        this.msg = mess;
    }

    public ResponseResult(int code, String mess) {
        this.code = code;
        this.msg = mess;
    }

    public void setCode(RESULT code) {
        if (code != null) {
            this.code = code.code;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public Map getOpt() {
        return opt;
    }

    public void setOpt(Map opt) {
        this.opt = opt;
    }

    public void putOpt(String key, Object val) {
        if (opt == null) {
            opt = new HashMap();
        }
        opt.put(key, val);
    }

    public ResponseResult done() {
        this.code = RESULT.SUCCESS.code;
        return this;
    }

    public ResponseResult done(String msg) {
        this.code = RESULT.SUCCESS.code;
        this.msg = msg;
        return this;
    }

    public ResponseResult fail() {
        this.code = RESULT.FAIL.code;
        return this;
    }

    public ResponseResult fail(String msg) {
        this.code = RESULT.FAIL.code;
        this.msg = msg;
        return this;
    }

    public ResponseResult fail(RESULT result) {
        this.code = result.code;
        this.msg = result.msg;
        return this;
    }

    /**
     * don't care data null or empty
     *
     * @return
     */
    public boolean success() {
        return RESULT.SUCCESS.code == this.code;
    }

    public String toJson() {
        String str = "";
        try {
            str = GsonUtil.toJson(this);
        } catch (Exception e) {
            logger.error(getLogMessage(e));
        }
        return str;
    }
}
