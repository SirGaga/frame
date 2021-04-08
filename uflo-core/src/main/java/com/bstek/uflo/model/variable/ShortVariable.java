package com.bstek.uflo.model.variable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
/**
 * @author zhangjie
 * @date 2021-04-08
 */

@Entity
@DiscriminatorValue("Short")
public class ShortVariable extends Variable {
	@Column(name="SHORT_VALUE_")
	private Short shortValue;

	public ShortVariable(){}
	
	public ShortVariable(short value){
		this.shortValue=value;
	}
	@Override
	public Object getValue() {
		return shortValue;
	}

	public Short getShortValue() {
		return shortValue;
	}

	public void setShortValue(Short shortValue) {
		this.shortValue = shortValue;
	}
	@Override
	public VariableType getType() {
		return VariableType.Short;
	}
}
