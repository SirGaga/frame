package com.asideal.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class RollBackInput {
    @ApiModelProperty(value = "当前任务Id(必填)")
    @NotNull
    private Long taskId;
    @ApiModelProperty(value = "处理意见(可不传)")
    private String opinion;
    @ApiModelProperty(value = "流程变量(可不传)")
    private Map variables;
}
