package com.feityz.common;

import lombok.Getter;

/**
 * @author zhangjie
 * @date 2021-04-07
 */

@Getter
public enum HandleResult {
    /**
     * 同意
     */
    Agree("同意","1"),
    /**
     * 不同意
     */
    Disagree("不同意","0")
    ;
    /**
     * 文字描述
     */
    private String desc;
    /**
     * 对应的代码
     */
    private String code;

    HandleResult(String desc, String code) {
        this.desc=desc;
        this.code=code;
    }
}
