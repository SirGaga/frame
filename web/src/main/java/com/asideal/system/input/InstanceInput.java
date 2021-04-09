package com.asideal.system.input;

import com.asideal.system.entity.ProcessInstanceVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实例输入参数
 * @author zhangjie
 * @date 2021-04-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InstanceInput extends ProcessInstanceVo {

    private int page;

    private int limit;

    private String processKey;
}
