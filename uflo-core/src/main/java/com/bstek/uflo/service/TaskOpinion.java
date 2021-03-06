/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.uflo.service;

/**
 * @author Jacky.gao
 * @since 2013年9月26日
 */
public class TaskOpinion {
	private String opinion;
	/**
	 * 操作
	 */
	private String operation;
	public TaskOpinion(String opinion){
		this.opinion=opinion;
	}
	public TaskOpinion(String opinion,String operation){
		this.operation=operation;
		this.opinion = opinion;
	}
	public String getOpinion() {
		if(opinion==null){
			return opinion;
		}
		if(opinion.length()>250){
			opinion=opinion.substring(0,250);
		}
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
