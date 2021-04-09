package com.asideal.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Line
 * @since 2020-06-03
 */

@Data
@TableName("uflo_task")
public class TaskInfo{

    @TableId("ID_")
    private Long id;

    @TableField("DESCRIPTION_")
    private String description;

    @TableField("NODE_NAME_")
    private String nodeName;

    @TableField("PROCESS_ID_")
    private Long processId;

    @TableField(exist = false)
    private String processName;

    @TableField(exist = false)
    private String processKey;

    @TableField("ASSIGNEE_")
    private String assignee;

    @TableField("BUSINESS_ID_")
    private String businessId;

    @TableField("COUNTERSIGN_COUNT_")
    private Integer countersignCount;

    @TableField("CREATE_DATE_")
    private Date createDate;

    @TableField("DATE_UNIT_")
    private String dateUnit;

    @TableField("DUE_ACTION_DATE_")
    private Date dueActionDate;

    @TableField("DUEDATE_")
    private Date duedate;

    @TableField("END_DATE_")
    private Date endDate;

    @TableField("OPINION_")
    private String opinion;

    @TableField("OWNER_")
    private String owner;

    @TableField("PREV_STATE_")
    private String prevState;

    @TableField("PREV_TASK_")
    private String prevTask;

    @TableField("PRIORITY_")
    private String priority;

    @TableField("PROCESS_INSTANCE_ID_")
    private Long processInstanceId;

    @TableField("PROGRESS_")
    private Integer progress;

    @TableField("ROOT_PROCESS_INSTANCE_ID_")
    private Long rootProcessInstanceId;

    @TableField("STATE_")
    private String state;

    @TableField("SUBJECT_")
    private String subject;

    @TableField("TASK_NAME_")
    private String taskName;

    @TableField("TYPE_")
    private String type;

    @TableField("URL_")
    private String url;

    @TableField("ENTER_FLOW_")
    private String enterFlow;

    @TableField("NEXT_ASSIGNEE_")
    private String nextAssignee;

    public static final String ID_ ="ID_";

    public static final String DESCRIPTION_ ="DESCRIPTION_";

    public static final String NODE_NAME_ ="NODE_NAME_";

    public static final String PROCESS_ID_ ="PROCESS_ID_";

    public static final String ASSIGNEE_ ="ASSIGNEE_";

    public static final String BUSINESS_ID_ ="BUSINESS_ID_";

    public static final String COUNTERSIGN_COUNT_ ="COUNTERSIGN_COUNT_";

    public static final String CREATE_DATE_ ="CREATE_DATE_";

    public static final String DATE_UNIT_ ="DATE_UNIT_";

    public static final String DUE_ACTION_DATE_ ="DUE_ACTION_DATE_";

    public static final String DUEDATE_ ="DUEDATE_";

    public static final String END_DATE_ ="END_DATE_";

    public static final String OPINION_ ="OPINION_";

    public static final String OWNER_ ="OWNER_";

    public static final String PREV_STATE_ ="PREV_STATE_";

    public static final String PREV_TASK_ ="PREV_TASK_";

    public static final String PRIORITY_ ="PRIORITY_";

    public static final String PROCESS_INSTANCE_ID_ ="PROCESS_INSTANCE_ID_";

    public static final String PROGRESS_ ="PROGRESS_";

    public static final String ROOT_PROCESS_INSTANCE_ID_ ="ROOT_PROCESS_INSTANCE_ID_";

    public static final String STATE_ ="STATE_";

    public static final String SUBJECT_ ="SUBJECT_";

    public static final String TASK_NAME_ ="TASK_NAME_";

    public static final String TYPE_ ="TYPE_";

    public static final String URL_ ="URL_";


}
