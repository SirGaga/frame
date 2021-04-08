package com.bstek.uflo.model.variable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jacky.gao
 * @since 2013年8月1日
 */
@Entity
@DiscriminatorValue("Double")
public class DoubleVariable extends Variable {
	@Column(name="DOUBLE_VALUE_")
	private double doubleValue;

	public DoubleVariable(){}
	public DoubleVariable(double value){
		this.doubleValue=value;
	}
	
	public double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}
	@Override
	public Object getValue() {
		return this.doubleValue;
	}
	@Override
	public VariableType getType() {
		return VariableType.Double;
	}
}
