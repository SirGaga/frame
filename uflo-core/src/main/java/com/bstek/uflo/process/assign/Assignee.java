package com.bstek.uflo.process.assign;

/**
 * @author Jacky.gao
 * @since 2013年8月17日
 */
public class Assignee implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String providerId;
	private boolean chosen;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public boolean isChosen() {
		return chosen;
	}
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
}
