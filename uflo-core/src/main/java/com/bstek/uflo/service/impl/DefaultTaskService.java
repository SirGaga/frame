package com.bstek.uflo.service.impl;

import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.command.impl.*;
import com.bstek.uflo.command.impl.jump.JumpNode;
import com.bstek.uflo.expr.ExpressionContext;
import com.bstek.uflo.model.HistoryActivity;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskAppointor;
import com.bstek.uflo.model.task.TaskParticipator;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.reminder.TaskReminder;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.node.UserData;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.query.impl.TaskQueryImpl;
import com.bstek.uflo.service.HistoryService;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2013年7月29日
 */
public class DefaultTaskService implements TaskService {
	private CommandService commandService;
	private HistoryService historyService;
	private ProcessService processService;
	private ExpressionContext expressionContext;
	
	@Override
	public void setProgress(int progress, long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ChangeTaskProgressCommand(task,progress));
	}
	
	@Override
	public void setPriority(String priority, long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ChangeTaskPriorityCommand(task,priority));
	}
	
	@Override
	public Task addCountersign(long taskId, String username) {
		Task task=getTask(taskId);
		return commandService.executeCommand(new AddCountersignCommand(task,username));
	}
	
	@Override
	public void deleteCountersign(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new DeleteCountersignCommand(task));
	}
	
	@Override
	public void deleteTask(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new DeleteTaskCommand(task));
	}
	
	@Override
	public void deleteTaskByNode(long processInstanceId, String nodeName) {
		commandService.executeCommand(new DeleteTaskByNodeCommand(processInstanceId,nodeName));
	}
	
	@Override
	public void cancelTask(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CancelTaskCommand(task,null));
	}
	
	@Override
	public void cancelTask(long taskId, TaskOpinion opinion) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CancelTaskCommand(task,opinion));
	}
	
	@Override
	public List<JumpNode> getAvaliableForwardTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return getAvaliableForwardTaskNodes(task);
	}
	
	public List<JumpNode> getAvaliableForwardTaskNodes(Task task) {
		return commandService.executeCommand(new GetJumpAvaliableTaskNodesCommand(task));
	}
	
	@Override
	public void saveTaskAppointor(long taskId, String assignee, String taskNodeName) {
		saveTaskAppointor(taskId,new String[]{assignee},taskNodeName);
	}
	
	@Override
	public void saveTaskAppointor(long taskId, String[] assignees, String taskNodeName) {
		Task task=getTask(taskId);
		commandService.executeCommand(new SaveTaskAppointorCommand(task,taskNodeName,assignees));
		
	}
	
	@Override
	public List<String> getAvaliableAppointAssigneeTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return commandService.executeCommand(new GetAvaliableAppointAssigneeTaskNodes(task));
	}

	@Override
	public List<String> getAppointAssigneeTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return commandService.executeCommand(new GetAppointAssigneeTaskNodes(task));
	}

	@Override
	public List<String> getTaskNodeAssignees(long taskId, String taskNodeName) {
		return commandService.executeCommand(new GetTaskNodeAssigneesCommand(taskId,taskNodeName));
	}
	@Override
	public List<JumpNode> getAvaliableRollbackTaskNodes(Task task) {
		ProcessInstance pi=processService.getProcessInstanceById(task.getProcessInstanceId());
		List<JumpNode> allNodes=getAvaliableForwardTaskNodes(task);
		List<HistoryActivity> hisActivities=historyService.getHistoryActivitysByProcesssInstanceId(pi.getRootId());
		List<JumpNode> result= new ArrayList<>();
		for(JumpNode node:allNodes){
			for(HistoryActivity activity:hisActivities){
				if(node.getName().equals(activity.getNodeName())){
					result.add(node);
					break;
				}
			}
		}
		return result;
		
	}
	
	@Override
	public List<JumpNode> getAvaliableRollbackTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return getAvaliableRollbackTaskNodes(task);
	}
	
	@Override
	public void rollback(long taskId, String targetNodeName) {
		forward(taskId, targetNodeName);
	}
	
	@Override
	public void rollback(long taskId, String targetNodeName, Map<String, Object> variables) {
		Task task=getTask(taskId);
		rollback(task,targetNodeName,variables,null);
	}

	@Override
	public void rollback(long taskId, String targetNodeName, Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		rollback(task,targetNodeName,variables,opinion);
	}
	
	@Override
	public void rollback(Task task, String targetNodeName, Map<String, Object> variables, TaskOpinion opinion) {
		commandService.executeCommand(new RollbackTaskCommand(task,targetNodeName,variables,opinion));
	}
	
	@Override
	public void complete(long taskId, String flowName) {
		complete(taskId,flowName,null,null);
	}

	@Override
	public void complete(long taskId, String flowName, TaskOpinion opinion) {
		complete(taskId,flowName,null,opinion);
	}
	
	@Override
	public void batchComplete(List<Long> taskIds, Map<String, Object> variables) {
		commandService.executeCommand(new BatchCompleteTasksCommand(taskIds, variables,null));
	}
	
	@Override
	public void batchComplete(List<Long> taskIds, Map<String, Object> variables, TaskOpinion opinion) {
		commandService.executeCommand(new BatchCompleteTasksCommand(taskIds, variables,opinion));
	}
	
	@Override
	public void batchStartAndComplete(List<Long> taskIds, Map<String, Object> variables, TaskOpinion opinion) {
		commandService.executeCommand(new BatchStartAndCompleteTasksCommand(taskIds, variables,opinion));
	}
	
	@Override
	public void batchStart(List<Long> taskIds) {
		commandService.executeCommand(new BatchStartTasksCommand(taskIds));
	}
	
	@Override
	public void batchStartAndComplete(List<Long> taskIds, Map<String, Object> variables) {
		commandService.executeCommand(new BatchStartAndCompleteTasksCommand(taskIds, variables,null));
	}
	
	@Override
	public void complete(long taskId, String flowName, Map<String, Object> variables) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CompleteTaskCommand(task, flowName,null, variables));
	}
	
	@Override
	public void complete(long taskId, String flowName, Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CompleteTaskCommand(task,flowName,opinion, variables));
	}

	@Override
	public void complete(long taskId) {
		complete(taskId,null,null,null);
	}
	
	@Override
	public void complete(long taskId, TaskOpinion opinion) {
		complete(taskId,null,null,opinion);
	}

	@Override
	public void complete(long taskId, Map<String, Object> variables) {
		complete(taskId,null,variables,null);
	}
	
	@Override
	public void complete(long taskId, Map<String, Object> variables, TaskOpinion opinion) {
		complete(taskId,null,variables,opinion);
	}
	
	@Override
	public void forward(long taskId, String targetNodeName) {
		forward(taskId,targetNodeName,null,null);
	}
	@Override
	public void forward(long taskId, String targetNodeName, TaskOpinion opinion) {
		forward(taskId,targetNodeName,null,opinion);
	}
	
	@Override
	public void forward(long taskId, String targetNodeName, Map<String, Object> variables) {
		forward(taskId,targetNodeName,variables,null);
	}
	
	@Override
	public void forward(long taskId, String targetNodeName, Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		forward(task,targetNodeName,variables,opinion,TaskState.Forwarded);
	}
	@Override
	public void forward(Task task, String targetNodeName, Map<String, Object> variables, TaskOpinion opinion) {
		forward(task,targetNodeName,variables,opinion,TaskState.Forwarded);
	}
	
	@Override
	public void forward(Task task, String targetNodeName, Map<String, Object> variables, TaskOpinion opinion, TaskState state) {
		commandService.executeCommand(new ForwardTaskCommand(task,targetNodeName,variables,opinion,state));
	}

	@Override
	public void withdraw(long taskId) {
		withdraw(taskId,null,null);
	}
	
	@Override
	public void withdraw(long taskId, TaskOpinion opinion) {
		withdraw(taskId,null,opinion);
	}

	@Override
	public void withdraw(long taskId, Map<String, Object> variables) {
		withdraw(taskId,variables,null);
	}
	
	@Override
	public void withdraw(long taskId, Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		if(task.getState().equals(TaskState.InProgress)){
			throw new IllegalStateException("Task "+task.getTaskName()+" state is InProgress,can not be withdraw.");
		}
		commandService.executeCommand(new WithdrawTaskCommand(task,variables,opinion));
	}
	
	@Override
	public boolean canWithdraw(long taskId) {
		Task task=getTask(taskId);
		return canWithdraw(task);
	}
	
	@Override
	public boolean canWithdraw(Task task) {
		if(task.getState().equals(TaskState.InProgress)){
			return false;
		}
		return commandService.executeCommand(new CanWithdrawDecisionCommand(task));
	}

	@Override
	public Task getTask(long taskId) {
		return commandService.executeCommand(new GetTaskCommand(taskId));
	}
	
	@Override
	public void claim(long taskId, String username) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ClaimTaskCommand(task,username));
	}

	@Override
	public void release(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ReleaseTaskCommand(task));
	}

	@Override
	public void start(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new StartTaskCommand(task));
	}

	@Override
	public void suspend(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new SuspendTaskCommand(task));
	}

	@Override
	public void resume(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ResumeTaskCommand(task));
	}
	@Override
	public List<TaskAppointor> getTaskAppointors(String taskNodeName,
												 long processInstanceId) {
		return commandService.executeCommand(new GetTaskAppointorCommand(taskNodeName, processInstanceId));
	}

	@Override
	public void changeTaskAssignee(long taskId, String username) {
		commandService.executeCommand(new ChangeTaskAssigneeCommand(taskId, username));
	}
	
	@Override
	public String getUserData(Task task, String key) {
		return getUserData(task.getProcessId(),task.getTaskName(),key);
	}
	
	@Override
	public String getUserData(long processId, String taskNodeName, String key) {
		ProcessDefinition process=processService.getProcessById(processId);
		TaskNode node=(TaskNode)process.getNode(taskNodeName);
		if(node.getUserData()==null) {
			return null;
		}
		for(UserData data:node.getUserData()){
			if(data.getKey().equals(key)){
				return data.getValue();
			}
		}
		return null;
	}
	
	@Override
	public TaskQuery createTaskQuery() {
		return new TaskQueryImpl(commandService);
	}
	
	@Override
	public List<TaskReminder> getAllTaskReminders() {
		return commandService.executeCommand(new GetTaskReminderCommand(0));
	}

	@Override
	public void setTaskVariable(Map<String, Object> variables, Long taskId) {
		Task task = getTask(taskId);
		ProcessInstance	processInstance = processService.getProcessInstanceById(task.getProcessInstanceId());
		this.getExpressionContext().addContextVariables(processInstance, variables);
		commandService.executeCommand(new SaveProcessInstanceVariablesCommand(processInstance, variables));
	}

	@Override
	public void setNextAssignee(String assignee,Long taskId) {
		commandService.executeCommand(new SaveTaskNextAssigneeCommand(assignee,taskId));
	}

	@Override
	public List<TaskParticipator> getTaskParticipators(long taskId) {
		return commandService.executeCommand(new GetTaskParticipatorsCommand(taskId));
	}
	
	@Override
	public List<TaskReminder> getTaskReminders(long taskId) {
		return commandService.executeCommand(new GetTaskReminderCommand(taskId));
	}
	@Override
	public void deleteTaskReminder(long taskReminderId) {
		commandService.executeCommand(new DeleteTaskReminderCommand(taskReminderId));
	}
	
	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public ExpressionContext getExpressionContext() {
		return expressionContext;
	}

	public void setExpressionContext(ExpressionContext expressionContext) {
		this.expressionContext = expressionContext;
	}

}
