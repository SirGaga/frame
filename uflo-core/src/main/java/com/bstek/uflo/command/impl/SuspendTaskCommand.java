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

import com.bstek.uflo.command.Command;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;

/**
 * @author Jacky.gao
 * @since 2013年8月14日
 */
public class SuspendTaskCommand implements Command<Task> {
	private Task task;
	public SuspendTaskCommand(Task task){
		this.task=task;
	}
	@Override
    public Task execute(Context context) {
		TaskState state=task.getState();
		if(!state.equals(TaskState.Ready) && !state.equals(TaskState.InProgress) && !state.equals(TaskState.Reserved)){
			throw new IllegalStateException("只有状态为 ready,reserved or inProgress 的任务才能被暂停!");
		}
		task.setPrevState(state);
		task.setState(TaskState.Suspended);
		context.getSession().update(task);
		return null;
	}

}
