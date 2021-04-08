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
 * @since 2013年8月2日
 */
@Entity
@DiscriminatorValue("Blob")
public class BlobVariable extends Variable {
	@Column(name="BLOB_ID_")
	private long blobId;
	
	@Transient
	private Object obj;
	
	@Transient
	private Blob blob;
	
	public BlobVariable(){}
	public BlobVariable(Object value,Context context){
		Blob lob=new Blob(value);
		long id=IDGenerator.getInstance().nextId();
		lob.setId(id);
		setBlobId(id);
		Session session=context.getSession();
		session.save(lob);
	}
	@Override
	public Object getValue() {
		return obj;
	}
	
	public Blob getBlob(){
		return blob;
	}
	
	public void initValue(Context context){
		Session session=context.getSession();
		blob= session.get(Blob.class,blobId);
		obj=SerializationUtils.deserialize(blob.getBlobValue());
	}
	
	public long getBlobId() {
		return blobId;
	}

	public void setBlobId(long blobId) {
		this.blobId = blobId;
	}
	@Override
	public VariableType getType() {
		return VariableType.Blob;
	}
}
