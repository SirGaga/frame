package com.bstek.uflo.process.assign;

/**
 * @author Jacky.gao
 * @since 2013年8月20日
 */
public class Entity {
	private String id;
	private String name;
	private boolean chosen=true;
	public Entity(String id,String name){
		this.id=id;
		this.name=name;
	}
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
	public boolean isChosen() {
		return chosen;
	}
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
}
