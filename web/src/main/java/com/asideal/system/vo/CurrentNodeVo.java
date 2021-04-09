package com.asideal.system.vo;

import com.bstek.uflo.process.node.UserData;
import com.asideal.system.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 下一节点的处理人员和节点名称
 */
@Data
public class CurrentNodeVo {

    private String nodeName;

    private List<User> assignee;

    private List<UserData> userData;
}
