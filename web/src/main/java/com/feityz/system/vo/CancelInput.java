package com.feityz.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class CancelInput {
    @NotNull
    @ApiModelProperty(value = "任务Id(必填)")
    private Long taskId;
    @ApiModelProperty(value = "处理意见(可不填)")
    private String opinion;
    @ApiModelProperty(value = "流程变量(可不填)")
    private Map variables;
}
