package com.feityz.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * 流程实体VO
 * @author zhangjie
 * @date 2021-04-07
 */
@Data
public class ProcessInstanceVo {

    private Long id;

    private String subject;

    private String businessId;

    private String currentNode;

    private String currentTask;

    private String promoter;

    private String assignee;

    private Date createDate;

    private Date endDate;

    private String processName;

    private String processKey;

    private String promoterName;

}
