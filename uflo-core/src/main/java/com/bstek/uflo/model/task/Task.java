package com.bstek.uflo.model.task;

import com.bstek.uflo.model.Activity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * 流程任务实体类
 * @author zhangjie
 * @date 2021-04-08
 */
@Entity
@Table(name="uflo_task")
public class Task extends Activity{
	@Column(name="TASK_NAME_",length=60)
	private String taskName;
	
	@Column(name="ASSIGNEE_",length=60)
	private String assignee;
	
	@Column(name="OWNER_",length=9999)
	private String owner;
	
	@Column(name="PROGRESS_")
	private Integer progress;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATE_",length=20)
	private TaskState state;
	
	@Column(name="PRIORITY_",length=20)
	private String priority;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PREV_STATE_",length=20)
	private TaskState prevState;
	
	@Column(name="PROCESS_INSTANCE_ID_")
	private long processInstanceId;
	
	@Column(name="ROOT_PROCESS_INSTANCE_ID_")
	private long rootProcessInstanceId;
	
	@Column(name="CREATE_DATE_")
	private Date createDate;
	
	@Column(name="END_DATE_")
	private Date endDate;
	
	@Column(name="PREV_TASK_",length=60)
	private String prevTask;
	
	@Column(name="OPINION_",length=200)
	private String opinion;
	
	@Column(name="URL_",length=120)
	private String url;
	
	@Column(name="SUBJECT_",length=200)
	private String subject;

	@Column(name="TYPE_",length=20)
	@Enumerated(EnumType.STRING)
	private TaskType type;
	
	@Column(name="COUNTERSIGN_COUNT_")
	private int countersignCount;
	
	@Column(name="DUEDATE_")
	private Date duedate;
	
	@Column(name="DUE_ACTION_DATE_")
	private Date dueActionDate;
	
	@Column(name="DATE_UNIT_",length=20)
	@Enumerated(EnumType.STRING)
	private DateUnit dateUnit;
	
	@Column(name="BUSINESS_ID_",length=60)
	private String businessId;

	@Column(name="ENTER_FLOW_",length=60)
	private String enterFlow;

	@Column(name="NEXT_ASSIGNEE_",length=9999)
	private String nextAssignee;

	@Column(name="OPERATION_",length=50)
	private String operation;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.DETACH)
	@JoinColumn(name="TASK_ID_")
	private Collection<TaskParticipator> taskParticipators;
	
	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public TaskState getPrevState() {
		return prevState;
	}

	public void setPrevState(TaskState prevState) {
		this.prevState = prevState;
	}

	public String getPrevTask() {
		return prevTask;
	}

	public void setPrevTask(String prevTask) {
		this.prevTask = prevTask;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCountersignCount() {
		return countersignCount;
	}

	public void setCountersignCount(int countersignCount) {
		this.countersignCount = countersignCount;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Date getDueActionDate() {
		return dueActionDate;
	}

	public void setDueActionDate(Date dueActionDate) {
		this.dueActionDate = dueActionDate;
	}

	public DateUnit getDateUnit() {
		return dateUnit;
	}

	public void setDateUnit(DateUnit dateUnit) {
		this.dateUnit = dateUnit;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getRootProcessInstanceId() {
		return rootProcessInstanceId;
	}

	public void setRootProcessInstanceId(long rootProcessInstanceId) {
		this.rootProcessInstanceId = rootProcessInstanceId;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Collection<TaskParticipator> getTaskParticipators() {
		return taskParticipators;
	}

	public void setTaskParticipators(Collection<TaskParticipator> taskParticipators) {
		this.taskParticipators = taskParticipators;
	}

	public String getEnterFlow() {
		return enterFlow;
	}

	public void setEnterFlow(String enterFlow) {
		this.enterFlow = enterFlow;
	}

	public String getNextAssignee() {
		return nextAssignee;
	}

	public void setNextAssignee(String nextAssignee) {
		this.nextAssignee = nextAssignee;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
