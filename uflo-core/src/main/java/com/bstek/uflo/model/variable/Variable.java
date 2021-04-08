package com.bstek.uflo.model.variable;

import com.bstek.uflo.env.Context;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jacky.gao
 * @since 2013年8月1日
 */
@Entity
@Table(name="UFLO_VARIABLE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_",length=30)
@DiscriminatorValue("")
public abstract class Variable {
	@Id
	@Column(name="ID_")
	private long id;

	@Column(name="KEY_",length=60)
	private String key;
	
	@Column(name="PROCESS_INSTANCE_ID_")
	private long processInstanceId;
	
	@Column(name="ROOT_PROCESS_INSTANCE_ID_")
	private long rootProcessInstanceId;
	
	public static Variable newVariable(Object value,Context context){
		Variable variable;
		if(value instanceof Date){
			variable=new DateVariable((Date)value);
		}else if(value instanceof String){
			String str=(String)value;
			if(str.length()>255){
				variable=new TextVariable(str,context);
			}else{
				variable=new StringVariable((String)value);				
			}
		}else if(value instanceof Double){
			variable=new DoubleVariable((Double)value);
		}else if(value instanceof Float){
			variable=new FloatVariable((Float)value);			
		}else if(value instanceof Long){
			variable=new LongVariable((Long)value);
		}else if(value instanceof Integer){
			variable=new IntegerVariable((Integer)value);
		}else if(value instanceof Boolean){
			variable=new BooleanVariable((Boolean)value);			
		}else if(value instanceof Short){
			variable=new ShortVariable((Short)value);			
		}else if(value instanceof Byte){
			variable=new ByteVariable((Byte)value);			
		}else if(value instanceof Character){
			variable=new CharacterVariable((Character)value);			
		}else{
			if(!(value instanceof java.io.Serializable)){
				throw new IllegalArgumentException("Variable value ["+value.getClass().getName()+"] must implement the java.io.Serializable interface");
			}
			variable=new BlobVariable(value,context);
		}
		return variable;
	}

	/**
	 * 获取值
	 * @return 返回对象
	 */
	public abstract Object getValue();

	/**
	 * 获取变量实体类
	 * @return 返回变量类型
	 */
	public abstract VariableType getType();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public long getRootProcessInstanceId() {
		return rootProcessInstanceId;
	}

	public void setRootProcessInstanceId(long rootProcessInstanceId) {
		this.rootProcessInstanceId = rootProcessInstanceId;
	}
}
