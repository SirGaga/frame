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
package com.bstek.uflo.service.impl;

import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.command.impl.*;
import com.bstek.uflo.model.HistoryActivity;
import com.bstek.uflo.model.HistoryProcessInstance;
import com.bstek.uflo.model.HistoryTask;
import com.bstek.uflo.model.HistoryVariable;
import com.bstek.uflo.query.HistoryProcessInstanceQuery;
import com.bstek.uflo.query.HistoryProcessVariableQuery;
import com.bstek.uflo.query.HistoryTaskQuery;
import com.bstek.uflo.query.impl.HistoryProcessInstanceQueryImpl;
import com.bstek.uflo.query.impl.HistoryProcessVariableQueryImpl;
import com.bstek.uflo.query.impl.HistoryTaskQueryImpl;
import com.bstek.uflo.service.HistoryService;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年9月12日
 */
public class DefaultHistoryService implements HistoryService {
	private CommandService commandService;
	@Override
    public List<HistoryActivity> getHistoryActivitysByProcesssInstanceId(long processInstanceId) {
		return commandService.executeCommand(new GetHistoryActivitiyCommand(processInstanceId,true));
	}
	
	@Override
    public HistoryProcessVariableQuery createHistoryProcessVariableQuery() {
		return new HistoryProcessVariableQueryImpl(commandService);
	}
	
	@Override
    public HistoryVariable getHistoryVariable(long historyProcessInstanceId, String key) {
		return commandService.executeCommand(new GetHistoryVariableCommand(historyProcessInstanceId,key));
	}

	@Override
    public List<HistoryActivity> getHistoryActivitysByHistoryProcesssInstanceId(long historyProcessInstanceId) {
		return commandService.executeCommand(new GetHistoryActivitiyCommand(historyProcessInstanceId,false));
	}

	@Override
    public List<HistoryProcessInstance> getHistoryProcessInstances(long processId) {
		return commandService.executeCommand(new GetListHistoryProcessInstancesCommand(processId));
	}

	@Override
    public HistoryProcessInstance getHistoryProcessInstance(long processInstanceId) {
		return commandService.executeCommand(new GetHistoryProcessInstanceCommand(processInstanceId));
	}

	@Override
    public List<HistoryTask> getHistoryTasks(long processInstanceId) {
		return commandService.executeCommand(new GetListHistoryTasksCommand(processInstanceId));
	}
	
	@Override
    public HistoryProcessInstanceQuery createHistoryProcessInstanceQuery() {
		return new HistoryProcessInstanceQueryImpl(commandService);
	}
	
	@Override
    public HistoryTaskQuery createHistoryTaskQuery() {
		return new HistoryTaskQueryImpl(commandService);
	}
	
	@Override
    public List<HistoryVariable> getHistoryVariables(long historyProcessInstanceId) {
		return commandService.executeCommand(new GetHistoryVariablesCommand(historyProcessInstanceId));
	}
	
	@Override
    public HistoryTask getHistoryTask(long taskId) {
		return commandService.executeCommand(new GetHistoryTaskCommand(taskId));
	}
	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}
}
