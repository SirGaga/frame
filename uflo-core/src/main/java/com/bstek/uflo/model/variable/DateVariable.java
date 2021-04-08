package com.bstek.uflo.model.variable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jacky.gao
 * @since 2013年8月2日
 */
@Entity
@DiscriminatorValue("Date")
public class DateVariable extends Variable {
	@Column(name="DATE_VALUE_")
	private Date dateValue;
	public DateVariable(){
	}
	public DateVariable(Date value){
		this.dateValue=value;
	}
	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	@Override
	public Object getValue() {
		return this.dateValue;
	}
	@Override
	public VariableType getType() {
		return VariableType.Date;
	}
}
