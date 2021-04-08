package com.feityz.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class GetNextNodeInput {

    @ApiModelProperty(value = "当前任务Id")
    private Long taskId;

    @ApiModelProperty(value = "流程变量")
    private Map variables;

    private String assignee;
}
