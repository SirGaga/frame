package com.feityz.system.vo;

import com.feityz.system.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 下一节点的处理人员和节点名称
 */
@Data
public class NextNodeVo {

    private String nextNodeName;

    private List<User> assignee;
}
