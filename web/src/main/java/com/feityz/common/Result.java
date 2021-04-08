package com.feityz.common;

import lombok.Data;

import java.util.List;

@Data
public class Result {

    private int code;

    private String msg;

    private List data;
}
