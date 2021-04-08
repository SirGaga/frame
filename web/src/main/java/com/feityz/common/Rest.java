package com.feityz.common;

import java.util.HashMap;

/**
 * ResponseBody构造器。一般用于ajax、rest等类型的Web服务
 *
 * @author zhangjie
 * @date 2021-04-07
 */
public class Rest extends HashMap<String, Object> {

    private static final Integer SUCCESS_CODE = 0;
    private static final Integer FAIL_CODE = -1;
    private static final String MSG_SUCCESS_STRING = "成功";
    private static final String MSG_FAIL_STRING = "失败";


    public static Rest success() {
        return success("");
    }

    public static Rest failure() {
        return failure(MSG_FAIL_STRING);
    }

    public static Rest success(String message) {
        Rest rest = new Rest();
        rest.setSuccess(true);
        rest.setMessage(message);
        return rest;
    }

    public static Rest failure(String message) {
        Rest rest = new Rest();
        rest.setSuccess(false);
        rest.setMessage(message);
        return rest;
    }


    public Rest setSuccess(Boolean success) {
        if (success != null) {
            put("success", success);
        }
        if (success) {
            put("code", SUCCESS_CODE);
        } else {
            put("code", FAIL_CODE);
        }
        return this;
    }

    public Rest setErroCode(String code) {
        put("code", code);
        return this;
    }

    public Rest setMessage(String message) {
        if (message != null) {
            put("msg", message);
        }
        return this;
    }

    public Rest setData(Object data) {
        if (data != null) {
            put("data", data);
        }
        return this;
    }

    public Rest setPage(Integer page) {
        if (page != null) {
            put("page", page);
        }
        return this;
    }

    public Rest setLimit(Integer limit) {
        if (limit != null) {
            put("limit", limit);
        }
        return this;
    }

    public Rest setTotal(Long total) {
        if (total != null) {
            put("count", total);
        }
        return this;
    }


    public Rest setAny(String key, Object value) {
        if (key != null && value != null) {
            put(key, value);
        }
        return this;
    }
}
