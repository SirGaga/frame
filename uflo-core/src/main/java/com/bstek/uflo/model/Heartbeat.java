package com.bstek.uflo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jacky.gao
 * @since 2013-5-6
 */
@Entity
@Table(name="uflo_job_heartbeat")
public class Heartbeat implements java.io.Serializable{
	private static final long serialVersionUID = 3256795533221464499L;
	@Id
	@Column(name="ID_",length=60)
	private String id;
	@Column(name="INSTANCE_NAME_",length=60)
	private String instanceName;
	@Column(name="DATE_")
	private Date date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
