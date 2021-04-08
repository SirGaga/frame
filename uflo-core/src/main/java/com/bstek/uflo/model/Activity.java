package com.bstek.uflo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author zhangjie
 * @date 2021-04-08
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Activity {
	@Id
	@Column(name="ID_")
	private long id;
	
	@Column(name="NODE_NAME_",length=60)
	private String nodeName;
	
	@Column(name="DESCRIPTION_",length=120)
	private String description;
	
	@Column(name="PROCESS_ID_")
	private long processId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
	}
}
