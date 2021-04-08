package com.bstek.uflo.model.calendar;

import javax.persistence.*;
import java.util.List;


/**
 * @author Jacky.gao
 * @since 2013年9月23日
 */
@Entity
@Table(name="uflo_calendar")
public class CalendarDef implements java.io.Serializable{
	private static final long serialVersionUID = 2579525564734297159L;
	@Id
	@Column(name="ID_")
	private long id;
	@Column(name="CATEGORY_ID_",length=60)
	private String categoryId;
	@Column(name="NAME_",length=60)
	private String name;
	@Column(name="DESC_",length=120)
	private String desc;
	@Column(name="TYPE_",length=12)
	@Enumerated(EnumType.STRING)
	private CalendarType type;
	
	@Transient
	private List<CalendarDate> calendarDates;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public CalendarType getType() {
		return type;
	}
	public void setType(CalendarType type) {
		this.type = type;
	}
	public List<CalendarDate> getCalendarDates() {
		return calendarDates;
	}
	public void setCalendarDates(List<CalendarDate> calendarDates) {
		this.calendarDates = calendarDates;
	}
}
