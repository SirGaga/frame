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
package com.bstek.uflo.command.impl;

import org.hibernate.Session;

import com.bstek.uflo.command.Command;
import com.bstek.uflo.deploy.ProcessDeployer;
import com.bstek.uflo.deploy.parse.impl.ProcessParser;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.Blob;
import com.bstek.uflo.model.ProcessDefinition;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年9月6日
 */
public class GetProcessByKeyCommand implements Command<ProcessDefinition> {
	private String processKey;
	public GetProcessByKeyCommand(String processKey){
		this.processKey=processKey;
	}
	@Override
	public ProcessDefinition execute(Context context) {
		Session session=context.getSession();
		List<ProcessDefinition> processes= session.createQuery("from " + ProcessDefinition.class.getName() + " where key=:key order by version desc").setString("key", processKey).list();
		if(processes==null||processes.size()==0){
			throw new IllegalArgumentException("找不到key为"+processKey+"流程定义");
		}else{
			ProcessDefinition p = processes.get(0);
			return parseProcess(p.getId(),p.getVersion(),p.getName(),session);
		}
	}
	
	private ProcessDefinition parseProcess(long processId,int version,String processName,Session session){
		/*String hql="from "+Blob.class.getName()+" where processId=:processId and name=:name";
		Blob blob=(Blob)session.createQuery(hql).setLong("processId",processId).setString("name",processName+ProcessDeployer.PROCESS_EXTENSION_NAME).uniqueResult();
		try {
			ProcessDefinition process=ProcessParser.parseProcess(blob.getBlobValue(),processId,true);
			process.setId(processId);
			return process;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
		String hql="from "+Blob.class.getName()+" where processId=:processId and name=:name";
		Blob blob=(Blob)session.createQuery(hql).setLong("processId",processId).setString("name",processName+ProcessDeployer.PROCESS_EXTENSION_NAME).uniqueResult();
		try {
			ProcessDefinition process=ProcessParser.parseProcess(blob.getBlobValue(),processId,true);
			process.setId(processId);
			process.setVersion(version);
			return process;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
