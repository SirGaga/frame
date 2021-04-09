package com.asideal.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class ProcessStartInput {

    @ApiModelProperty(value = "流程key")
    @NotNull
    private String processKey;
    @ApiModelProperty(value = "业务表单KEY")
    @NotNull
    private String bizId;
    @ApiModelProperty(value = "表单内容大致描述,如XXX的请假")
    @NotNull
    private String subject;
    @ApiModelProperty(value = "流程变量,请以键值对方式传输如{'xx':'xxx','bb':'xxx'....},流程定义时用到的变量必须传过来")
    private Map variables;
    @ApiModelProperty(value = "非集成模式,流程发起人,集成模式可不传")
    private String promoter;
    @ApiModelProperty(value = "下一节点,流程启动时,若下一步需要指派人员,接口会把下一节点和处理人员返回")
    private String nextNodeName;
    @ApiModelProperty(value = "下一节点处理人,流程启动时,若下一步需要指派人员,接口会把下一节点和处理人员返回")
    private String assignee;

    @ApiModelProperty(value = "处理意见(非必填)")
    private String opinion;
    @ApiModelProperty(value = "处理动作(非必填)")
    private String operation;
}
