package com.bstek.uflo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhangjie
 * @date 2021-04-08
 */
@Entity
@Table(name="uflo_his_process_instance")
public class HistoryProcessInstance{
	@Id
	@Column(name="ID_")
	private long id;
	
	@Column(name="PROCESS_ID_")
	private long processId;
	
	@Column(name="BUSINESS_ID_",length=60)
	private String businessId;
	
	@Column(name="SUBJECT_",length=200)
	private String subject;
	
	@Column(name="CREATE_DATE_")
	private Date createDate;
	
	@Column(name="PROCESS_INSTANCE_ID_")
	private long processInstanceId;
	
	@Column(name="END_DATE_")
	private Date endDate;
	
	@Column(name="TAG_",length=30)
	private String tag;
	
	@Column(name="PROMOTER_",length=60)
	private String promoter;

	@Column(name="END_NAME",length=60)
	private String endName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
	}
	
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
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getPromoter() {
		return promoter;
	}
	public void setPromoter(String promoter) {
		this.promoter = promoter;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEndName() {
		return endName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}
}
