package com.asideal.system.vo;

import lombok.Data;

@Data
public class ChangeAssigneeInput {

    private Long assignee;

    private String opinion;

    private Long taskId;

}
