package com.bstek.uflo.model.variable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Jacky.gao
 * @since 2013年8月2日
 */
@Entity
@DiscriminatorValue("Long")
public class LongVariable extends Variable {
	@Column(name="LONG_VALUE_")
	private long longValue;
	public LongVariable(){}
	public LongVariable(long value){
		this.longValue=value;
	}

	public long getLongValue() {
		return longValue;
	}
	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}
	@Override
	public Object getValue() {
		return this.longValue;
	}
	@Override
	public VariableType getType() {
		return VariableType.Long;
	}
}
