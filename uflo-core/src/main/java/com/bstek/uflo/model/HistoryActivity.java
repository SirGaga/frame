package com.bstek.uflo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhangjie
 * @date 2021-04-08
 */
@Entity
@Table(name="uflo_his_activity")
public class HistoryActivity extends Activity{
	
	@Column(name="CREATE_DATE_")
	private Date createDate;
	
	@Column(name="END_DATE_")
	private Date endDate;
	
	@Column(name="PROCESS_INSTANCE_ID_")
	private long processInstanceId;
	
	@Column(name="ROOT_PROCESS_INSTANCE_ID_")
	private long rootProcessInstanceId;
	
	@Column(name="HIS_PROCESS_INSTANCE_ID_")
	private long historyProcessInstanceId;

	@Column(name="LEAVE_FLOW_NAME_",length=60)
	private String leaveFlowName;
	
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getRootProcessInstanceId() {
		return rootProcessInstanceId;
	}

	public void setRootProcessInstanceId(long rootProcessInstanceId) {
		this.rootProcessInstanceId = rootProcessInstanceId;
	}

	public String getLeaveFlowName() {
		return leaveFlowName;
	}

	public void setLeaveFlowName(String leaveFlowName) {
		this.leaveFlowName = leaveFlowName;
	}
}
