package com.bstek.uflo.model;

import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.TaskType;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Jacky.gao
 * @since 2013年7月25日
 */
@XmlRootElement(name="HistoryTask")
@Entity
@Table(name="uflo_his_task")
public class HistoryTask extends Activity {
	@Enumerated(EnumType.STRING)
	@Column(name="STATE_",length=20)
	private TaskState state;
	
	@Column(name="TASK_NAME_",length=60)
	private String taskName;
	
	@Column(name="CREATE_DATE_")
	private Date createDate;

	@Column(name="END_DATE_")
	private Date endDate;
	
	@Column(name="OWNER_",length=9999)
	private String owner;

	@Column(name="ASSIGNEE_",length=60)
	private String assignee;

	@Column(name="TYPE_",length=20)
	@Enumerated(EnumType.STRING)
	private TaskType type;
	
	@Column(name="DUEDATE_")
	private Date duedate;
	
	@Column(name="HIS_PROCESS_INSTANCE_ID_")
	private long historyProcessInstanceId;
	
	@Column(name="PROCESS_INSTANCE_ID_")
	private long processInstanceId;
	
	@Column(name="ROOT_PROCESS_INSTANCE_ID_")
	private long rootProcessInstanceId;
	
	@Column(name="TASK_ID_")
	private long taskId;
	
	@Column(name="OPINION_",length=200)
	private String opinion;

	@Column(name="URL_",length=120)
	private String url;

	@Column(name="BUSINESS_ID_",length=60)
	private String businessId;
	
	@Column(name="SUBJECT_",length=200)
	private String subject;

	@Column(name="ENTER_FLOW",length=50)
	private String enterFlow;

	@Column(name="NEXT_ASSIGNEE_",length=9999)
	private String nextAssignee;

	@Column(name="OPERATION_",length=50)
	private String operation;
	
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
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

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public long getHistoryProcessInstanceId() {
		return historyProcessInstanceId;
	}

	public void setHistoryProcessInstanceId(long historyProcessInstanceId) {
		this.historyProcessInstanceId = historyProcessInstanceId;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getTaskId() {
		return taskId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public long getRootProcessInstanceId() {
		return rootProcessInstanceId;
	}

	public void setRootProcessInstanceId(long rootProcessInstanceId) {
		this.rootProcessInstanceId = rootProcessInstanceId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
