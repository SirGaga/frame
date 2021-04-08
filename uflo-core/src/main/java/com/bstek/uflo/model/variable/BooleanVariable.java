package com.bstek.uflo.model.variable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
/**
 * @author zhangjie
 * @date 2021-04-08
 */
@Entity
@DiscriminatorValue("Boolean")
public class BooleanVariable extends Variable {
	@Column(name="BOOLEAN_VALUE_")
	private boolean booleanValue;

	public BooleanVariable(){}
	
	public BooleanVariable(boolean value){
		this.booleanValue=value;
	}
	@Override
	public Object getValue() {
		return booleanValue;
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
	@Override
	public VariableType getType() {
		return VariableType.Boolean;
	}
}
