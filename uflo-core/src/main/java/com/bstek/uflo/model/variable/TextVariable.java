package com.bstek.uflo.model.variable;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.Blob;
import com.bstek.uflo.utils.IDGenerator;
import org.hibernate.Session;
import org.springframework.util.SerializationUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Jacky.gao
 * @since 2013年9月24日
 */
@Entity
@DiscriminatorValue("Text")
public class TextVariable extends Variable {
	@Column(name="BLOB_ID_")
	private long blobId;
	
	@Transient
	private String text;
	
	@Transient
	private Blob blob;

	public TextVariable(){}
	
	public TextVariable(String value,Context context){
		Blob lob=new Blob(value);
		long id=IDGenerator.getInstance().nextId();
		lob.setId(id);
		setBlobId(id);
		Session session=context.getSession();
		session.save(lob);
	}
	
	public void initValue(Context context){
		Session session=context.getSession();
		blob= session.get(Blob.class,blobId);
		text=(String)SerializationUtils.deserialize(blob.getBlobValue());
	}
	
	public Blob getBlob(){
		return blob;
	}
	
	@Override
	public String getValue() {
		return text;
	}

	@Override
	public VariableType getType() {
		return VariableType.Text;
	}
	
	public long getBlobId() {
		return blobId;
	}

	public void setBlobId(long blobId) {
		this.blobId = blobId;
	}
}
