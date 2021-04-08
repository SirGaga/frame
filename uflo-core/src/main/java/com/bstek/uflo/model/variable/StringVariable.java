package com.bstek.uflo.model.variable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jacky.gao
 * @since 2013年8月2日
 */
@Entity
@DiscriminatorValue("String")
public class StringVariable extends Variable {
	@Column(name="STRING_VALUE_")
	private String stringValue;
	public StringVariable(){}
	public StringVariable(String value){
		this.stringValue=value;
	}
	
	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	@Override
	public Object getValue() {
		return this.stringValue;
	}
	@Override
	public VariableType getType() {
		return VariableType.String;
	}
}
