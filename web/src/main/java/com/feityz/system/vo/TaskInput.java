package com.feityz.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class TaskInput {
    @NotNull
    @ApiModelProperty(value = "任务Id(必填)")
    private Long taskId;
    @ApiModelProperty(value = "处理意见(可不填)")
    private String opinion;
    @ApiModelProperty(value = "处理动作)")
    private String operation;
    @ApiModelProperty(value = "下一节点名称,调用nextNode接口,如果获取到节点,则必填")
    private String nextNodeName;
    @ApiModelProperty(value = "下一步处理人,调用nextNode接口,如果获取到处理人,则必填")
    private String assignee;
    @ApiModelProperty(value = "流程变量(可不填)")
    private Map variables;
    @ApiModelProperty(value = "任务完成人员,非集成模式需要把人员传过来")
    private String appointor;
}
