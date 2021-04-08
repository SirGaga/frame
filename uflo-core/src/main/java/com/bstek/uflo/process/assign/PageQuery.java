package com.bstek.uflo.process.assign;

import java.util.Collection;

/**
 * @author Jacky.gao
 * @since 2013年8月17日
 */
public class PageQuery<T> {
	private String id;
	private String name;
	private int pageIndex;
	private int pageSize;
	private boolean tree;
	private Collection<T> result;
	private int recordCount;
	private Entity queryParameter;
	
	public PageQuery(int pageIndex,int pageSize){
		this.pageIndex=pageIndex;
		this.pageSize=pageSize;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<T> getResult() {
		return result;
	}
	public void setResult(Collection<T> result) {
		this.result = result;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public Entity getQueryParameter() {
		return queryParameter;
	}

	public void setQueryParameter(Entity queryParameter) {
		this.queryParameter = queryParameter;
	}

	public boolean isTree() {
		return tree;
	}
	public void setTree(boolean tree) {
		this.tree = tree;
	}
}
