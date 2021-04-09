package com.asideal.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bstek.uflo.process.node.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Line
 * @since 2020-06-03
 */

@Data
@TableName("uflo_his_task")
public class HistoryTaskInfo{

    @TableId("ID_")
    @JsonIgnore
    private Long id;

    @TableField("DESCRIPTION_")
    @JsonIgnore
    private String description;

    @TableField("NODE_NAME_")
    private String nodeName;

    @TableField("PROCESS_ID_")
    @JsonIgnore
    private Long processId;

    @TableField("ASSIGNEE_")
    @JsonIgnore
    private String assignee;

    @TableField(exist = false)
    private String assigneeName;

    @TableField(exist = false)
    private String assigneeNum;

    @TableField("BUSINESS_ID_")
    private String businessId;

    @TableField("CREATE_DATE_")
    //@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @TableField("DUEDATE_")
    @JsonIgnore
    //@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date duedate;

    @TableField("END_DATE_")
    //@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date endDate;

    @TableField("HIS_PROCESS_INSTANCE_ID_")
    @JsonIgnore
    private Long hisProcessInstanceId;

    @TableField("OPINION_")
    private String opinion;

    @TableField("OPERATION_")
    private String operation;

    @TableField("OWNER_")
    @JsonIgnore
    private String owner;

    @TableField(exist = false)
    private String ownerName;

    @TableField("PROCESS_INSTANCE_ID_")
    @JsonIgnore
    private Long processInstanceId;

    @TableField("ROOT_PROCESS_INSTANCE_ID_")
    @JsonIgnore
    private Long rootProcessInstanceId;

    @TableField("STATE_")
    @JsonIgnore
    private String state;

    @TableField("SUBJECT_")
    private String subject;

    @TableField("TASK_ID_")
    @JsonIgnore
    private Long taskId;

    @TableField("TASK_NAME_")
    private String taskName;

    @TableField("TYPE_")
    @JsonIgnore
    private String type;

    @TableField("URL_")
    private String url;

    @TableField("ENTER_FLOW_")
    private String enterFlow;

    @TableField("NEXT_ASSIGNEE_")
    private String nextAssignee;

    @TableField(exist = false)
    private List<UserData> userData;

    public static final String ID_ ="ID_";

    public static final String DESCRIPTION_ ="DESCRIPTION_";

    public static final String NODE_NAME_ ="NODE_NAME_";

    public static final String PROCESS_ID_ ="PROCESS_ID_";

    public static final String ASSIGNEE_ ="ASSIGNEE_";

    public static final String BUSINESS_ID_ ="BUSINESS_ID_";

    public static final String CREATE_DATE_ ="CREATE_DATE_";

    public static final String DUEDATE_ ="DUEDATE_";

    public static final String END_DATE_ ="END_DATE_";

    public static final String HIS_PROCESS_INSTANCE_ID_ ="HIS_PROCESS_INSTANCE_ID_";

    public static final String OPINION_ ="OPINION_";

    public static final String OWNER_ ="OWNER_";

    public static final String PROCESS_INSTANCE_ID_ ="PROCESS_INSTANCE_ID_";

    public static final String ROOT_PROCESS_INSTANCE_ID_ ="ROOT_PROCESS_INSTANCE_ID_";

    public static final String STATE_ ="STATE_";

    public static final String SUBJECT_ ="SUBJECT_";

    public static final String TASK_ID_ ="TASK_ID_";

    public static final String TASK_NAME_ ="TASK_NAME_";

    public static final String TYPE_ ="TYPE_";

    public static final String URL_ ="URL_";


}
