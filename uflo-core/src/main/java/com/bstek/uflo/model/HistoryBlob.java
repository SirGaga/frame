package com.bstek.uflo.model;

import org.springframework.util.SerializationUtils;

import javax.persistence.*;

/**
 * @author Jacky.gao
 * @since 2013年9月27日
 */
@Entity
@Table(name="uflo_his_blob")
public class HistoryBlob {
	@Id
	@Column(name="ID_")
	private long id;
	
	@Lob
	@Column(name="BLOB_VALUE_",length=1024000)
	private byte[] blobValue;

	public HistoryBlob(){}
	public HistoryBlob(Object obj){
		byte[] b=SerializationUtils.serialize(obj);
		setBlobValue(b);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getBlobValue() {
		return blobValue;
	}

	public void setBlobValue(byte[] blobValue) {
		this.blobValue = blobValue;
	}
}
