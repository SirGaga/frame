package com.asideal.system.vo;

import com.asideal.system.entity.User;
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
