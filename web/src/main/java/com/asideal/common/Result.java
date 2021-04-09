package com.asideal.common;

import lombok.Data;

import java.util.List;

/**
 * 全局返回数据实体
 * @author zhangjie
 * @date 2021-04-08
 */
@Data
public class Result {

    private int code;

    private String msg;

    private List<Object> data;
}
