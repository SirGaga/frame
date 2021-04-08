package com.bstek.uflo.process.node;

import com.bstek.uflo.command.impl.SaveHistoryTaskCommand;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.env.TaskDueDefinitionProvider;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.*;
import com.bstek.uflo.model.task.reminder.ReminderType;
import com.bstek.uflo.model.task.reminder.TaskReminder;
import com.bstek.uflo.process.assign.Assignee;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.flow.SequenceFlowImpl;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.handler.CountersignHandler;
import com.bstek.uflo.process.listener.GlobalTaskListener;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.calendar.BusinessCalendar;
import com.bstek.uflo.process.node.exception.AssignExprNullException;
import com.bstek.uflo.process.node.exception.AssignerNotFoundException;
import com.bstek.uflo.process.node.exception.SwimlaneNotExistException;
import com.bstek.uflo.process.node.exception.UnsupportAssignmentException;
import com.bstek.uflo.process.node.reminder.*;
import com.bstek.uflo.process.security.Authority;
import com.bstek.uflo.process.security.ComponentAuthority;
import com.bstek.uflo.process.swimlane.Swimlane;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.SchedulerService;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.IDGenerator;
import com.bstek.uflo.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.quartz.spi.OperableTrigger;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jacky.gao
 * @since 2013年7月25日
 */
public class TaskNode extends Node {
	private static final long serialVersionUID = -1984323879432140639L;
	private String taskName;
	private TaskType taskType;
	private boolean allowSpecifyAssignee;
	private int countersignMultiplicity;
	private int countersignPercentMultiplicity;
	private String countersignExpression;
	private String countersignHandler;
	private AssignmentType assignmentType;
	private List<Assignee> assignees;
	private String assignmentHandlerBean;
	private String assignmentHandlerBeanDesc;
	private String swimlane;
	private String expression;
	private String url;
	private String formTemplate;
	private String taskListenerBean;
	private DueDefinition dueDefinition;
	private List<ComponentAuthority> componentAuthorities;
	private List<FormElement> formElements;
	private List<UserData> userData;
	@Override
	public boolean enter(Context context,ProcessInstance processInstance) {
		if(StringUtils.isNotEmpty(taskListenerBean)){
			TaskListener taskListener=(TaskListener)context.getApplicationContext().getBean(taskListenerBean);
			boolean doNext=taskListener.beforeTaskCreate(context, processInstance, this);
			if(doNext){
				return true;
			}
		}
		Collection<GlobalTaskListener> coll=fetchGlobalTaskListener(context);
		for(GlobalTaskListener listener:coll){
			listener.beforeTaskCreate(context, processInstance, this);
		}
		if(allowSpecifyAssignee){
			int size=processAllowSpecifyAssignee(context,processInstance);
			if(size>0){
				return false;
			}
		}
		if(assignmentType.equals(AssignmentType.Handler)){
			processAssignmentHandler(context, processInstance,assignmentHandlerBean);
		}else if(assignmentType.equals(AssignmentType.Swimlane)){
			processSwimlane(context, processInstance);
		}else if(assignmentType.equals(AssignmentType.Assignee)){
			processAssignees(context,processInstance,assignees);
		}else if(assignmentType.equals(AssignmentType.ProcessPromoter)){
			List<String> users= new ArrayList<>();
			users.add(processInstance.getPromoter());
			createTasks(context, processInstance, users);
		}else if(assignmentType.equals(AssignmentType.Expression)){
			processExpression(context, processInstance,expression);
		}else{
			throw new UnsupportAssignmentException("不支持当前分配类型 ["+assignmentType+"]",this);
		}
		return false;
	}

	private int processAllowSpecifyAssignee(Context context, ProcessInstance processInstance) {
		TaskService taskService=(TaskService)context.getApplicationContext().getBean(TaskService.BEAN_ID);
		List<TaskAppointor> appointors=taskService.getTaskAppointors(getName(), processInstance.getRootId());
		if(appointors!=null && appointors.size()>0){
			List<String> owners= new ArrayList<>();
			for(TaskAppointor ta:appointors){
				owners.add(ta.getOwner());
			}
			if(owners.size()==0){
				return 0;
			}
			createTasks(context,processInstance,owners);
			return appointors.size();
		}
		return 0;
	}

	public List<String> getAssigneeUsers(Context context,ProcessInstance processInstance){
		List<String> resultUsers= new ArrayList<>();
		ApplicationContext applicationContext=context.getApplicationContext();
		if(assignmentType.equals(AssignmentType.Handler)){
			AssignmentHandler handler=(AssignmentHandler)applicationContext.getBean(assignmentHandlerBean);
			Collection<String> users=handler.handle(this, processInstance, context);
			resultUsers.addAll(users);
		}else if(assignmentType.equals(AssignmentType.Swimlane)){
			Swimlane lane=context.getProcessService().getProcessById(processInstance.getProcessId()).getSwimlane(swimlane);
			if(lane==null){
				throw new SwimlaneNotExistException("Swimlane ["+swimlane+"] is not exist!",this);
			}
			if(lane.getAssignmentType().equals(AssignmentType.Handler)){
				AssignmentHandler handler=(AssignmentHandler)applicationContext.getBean(lane.getAssignmentHandlerBean());
				Collection<String> users=handler.handle(this, processInstance, context);
				resultUsers.addAll(users);
			}else if(lane.getAssignmentType().equals(AssignmentType.Expression)){
				Collection<String> users=getExpressionUsers(context, processInstance, lane.getExpression());
				resultUsers.addAll(users);
			}else if(lane.getAssignmentType().equals(AssignmentType.ProcessPromoter)){
				resultUsers.add(processInstance.getPromoter());
			}else if(lane.getAssignmentType().equals(AssignmentType.Assignee)){
				List<String> users= new ArrayList<>();
				retriveAssignees(lane.getAssignees(),context,processInstance,users);
				resultUsers.addAll(users);
			}else{
				throw new UnsupportAssignmentException("Unsupport swimlane ["+swimlane+"] assignment type ["+assignmentType+"]",this);
			}
		}else if(assignmentType.equals(AssignmentType.Assignee)){
			List<String> users= new ArrayList<>();
			retriveAssignees(assignees,context,processInstance,users);
			resultUsers.addAll(users);
		}else if(assignmentType.equals(AssignmentType.ProcessPromoter)){
			resultUsers.add(processInstance.getPromoter());
		}else if(assignmentType.equals(AssignmentType.Expression)){
			Collection<String> users=getExpressionUsers(context, processInstance, expression);
			resultUsers.addAll(users);
		}else{
			throw new UnsupportAssignmentException("Unsupport current assignment type ["+assignmentType+"]",this);
		}
		return resultUsers;
	}

	private void processAssignmentHandler(Context context,ProcessInstance processInstance,String bean) {
		AssignmentHandler handler=(AssignmentHandler)context.getApplicationContext().getBean(bean);
		Collection<String> users=handler.handle(this, processInstance, context);
		createTasks(context,processInstance,users);
	}

	private void processSwimlane(Context context,ProcessInstance processInstance) {
		Swimlane lane=context.getProcessService().getProcessById(processInstance.getProcessId()).getSwimlane(swimlane);
		if(lane==null){
			throw new SwimlaneNotExistException("Swimlane ["+swimlane+"] is not exist!",this);
		}
		if(lane.getAssignmentType().equals(AssignmentType.Handler)){
			processAssignmentHandler(context,processInstance,lane.getAssignmentHandlerBean());
		}else if(lane.getAssignmentType().equals(AssignmentType.Expression)){
			processExpression(context, processInstance,lane.getExpression());
		}else if(lane.getAssignmentType().equals(AssignmentType.ProcessPromoter)){
			List<String> users= new ArrayList<>();
			users.add(processInstance.getPromoter());
			createTasks(context, processInstance, users);
		}else if(lane.getAssignmentType().equals(AssignmentType.Assignee)){
			processAssignees(context,processInstance,lane.getAssignees());
		}else{
			throw new UnsupportAssignmentException("Unsupport swimlane ["+swimlane+"] assignment type ["+assignmentType+"]",this);
		}
	}

	private void processExpression(Context context,ProcessInstance processInstance,String expr) {
		List<String> users = getExpressionUsers(context, processInstance, expr);
		createTasks(context, processInstance, users);
	}

	@SuppressWarnings("unchecked")
	private List<String> getExpressionUsers(Context context,
			ProcessInstance processInstance, String expr) {
		Object obj=context.getExpressionContext().eval(processInstance, expr);
		if(obj==null){
			throw new AssignExprNullException("表达式 "+expr+" 值为 null",this);
		}
		List<String> users= new ArrayList<>();
		if(obj instanceof String){
			String user=(String)obj;
			users.add(user);
		}else if(obj instanceof String[]){
			users.addAll(Arrays.asList((String[]) obj));
		}else if(obj instanceof Collection){
			users.addAll((Collection<String>)obj);
		}else{
			throw new UnsupportAssignmentException("Unsupport expression "+expr+" value type :"+obj.getClass().getName(),this);
		}
		return users;
	}

	private void createTasks(Context context,ProcessInstance processInstance,Collection<String> owners) {
		if(owners==null || owners.size()==0){
			throw new AssignerNotFoundException("任务节点 "+getName()+" 必须至少返回一个处理人!",this);
		}
		List<String> users= new ArrayList<>();
		for(String owner:owners){
			if(users.contains(owner)){
				continue;
			}
			users.add(owner);
		}
		TaskListener taskListener=null;
		if(StringUtils.isNotEmpty(taskListenerBean)){
			taskListener=(TaskListener)context.getApplicationContext().getBean(taskListenerBean);
		}
		Collection<GlobalTaskListener> coll=fetchGlobalTaskListener(context);
		Session session=context.getSession();
		if(taskType.equals(TaskType.Normal)){
			Task task=createTask(context, processInstance);
			if(users.size()==1){
				String user=users.iterator().next();
				task.setAssignee(user);
				task.setOwner(user);
				task.setType(taskType);
				session.save(task);
				Date dueDate=createReminder(processInstance,task,context);
				if(dueDate==null){
					DueDefinition dueDef=getCustomDueDefinition(processInstance, task, context.getApplicationContext());
					if(dueDef!=null){
						dueDate=computeDueReminderStartdate(context,dueDef);
					}else{
						dueDate=computeDueReminderStartdate(context,dueDefinition);
					}
				}
				task.setDuedate(dueDate);
				if(taskListener!=null){
					taskListener.onTaskCreate(context, task);
				}
				for(GlobalTaskListener listener:coll){
					listener.onTaskCreate(context, task);
				}
			}else{
				task.setOwner(users.stream().map(m->m.toString()).collect(Collectors.joining(",")));
				task.setType(TaskType.Participative);
				task.setState(TaskState.Ready);
				session.save(task);
				Date dueDate=createReminder(processInstance,task,context);
				if(dueDate==null){
					DueDefinition dueDef=getCustomDueDefinition(processInstance, task, context.getApplicationContext());
					if(dueDef!=null){
						dueDate=computeDueReminderStartdate(context,dueDef);
					}else{
						dueDate=computeDueReminderStartdate(context,dueDefinition);
					}
				}
				task.setDuedate(dueDate);
				long taskId=task.getId();
				for(String user:users){
					TaskParticipator p=new TaskParticipator();
					p.setId(IDGenerator.getInstance().nextId());
					p.setTaskId(taskId);
					p.setUser(user);
					session.save(p);
				}
				if(taskListener!=null){
					taskListener.onTaskCreate(context, task);
				}
				for(GlobalTaskListener listener:coll){
					listener.onTaskCreate(context, task);
				}
				//查找当前节点之前被处理过的任务信息,拿到当时的候选人(之前是第一个处理人)
				/*List<Task> tasks = context.getTaskService().createTaskQuery().processInstanceId(task.getProcessInstanceId())
						.nodeName(task.getNodeName()).addOrderDesc("createDate").list()
						.stream().filter(x->x.getId() != task.getId()).collect(Collectors.toList());
				if(tasks.size()>0){
					Task firstTask = tasks.get(0);
					if(StringUtils.isEmpty(firstTask.getOwner())){
						throw new IllegalArgumentException("当前节点没有处理人");
					}
					String[] taskOwner = firstTask.getOwner().split(",");
					context.getTaskService().saveTaskAppointor(task.getId(),taskOwner,task.getNodeName());
				}*/
				//来源任务
				List<Task> prevtasks = context.getTaskService().createTaskQuery().processInstanceId(task.getProcessInstanceId())
						.nodeName(task.getPrevTask()).list();
			}
		}else if(taskType.equals(TaskType.Countersign)){
			for(String user:users){
				Task task=createTask(context, processInstance);
				task.setAssignee(user);
				task.setOwner(user);
				task.setCountersignCount(users.size());
				task.setType(taskType);
				session.save(task);
				Date dueDate=createReminder(processInstance,task,context);
				if(dueDate==null){
					DueDefinition dueDef=getCustomDueDefinition(processInstance, task, context.getApplicationContext());
					if(dueDef!=null){
						dueDate=computeDueReminderStartdate(context,dueDef);
					}else{
						dueDate=computeDueReminderStartdate(context,dueDefinition);
					}
				}
				task.setDuedate(dueDate);
				if(taskListener!=null){
					taskListener.onTaskCreate(context, task);
				}
				for(GlobalTaskListener listener:coll){
					listener.onTaskCreate(context, task);
				}
			}
		}else{
			throw new IllegalArgumentException("不支持当前的任务类型 :"+taskType);
		}
	}

	private Task createTask(Context context, ProcessInstance processInstance) {
		Task task=new Task();
		//这里要获取到来时的路线
		task.setEnterFlow(getEnterFlow());
		task.setId(IDGenerator.getInstance().nextId());
		task.setNodeName(getName());
		if(StringUtils.isNotEmpty(taskName)){
			task.setTaskName(context.getExpressionContext().evalString(processInstance, taskName));
		}else{
			String newTaskName=getLabel();
			if(StringUtils.isEmpty(newTaskName)){
				newTaskName=getName();
			}
			task.setTaskName(newTaskName);
		}
		//String var =
		/*String hql="from Parameter as p where p.code=:code and flag = 1";//使用命名参数，推荐使用，易读。
		Query query=context.getSession().createQuery(hql);
		query.setString("code", "SPLIT_VAR");
		List<Parameter> parameters =query.list();
		if(!CollectionUtils.isEmpty(parameters)){
			String vars = parameters.get(0).getValue();
			List<Variable> processVar = context.getProcessService().getProcessVariables(processInstance.getId());
			for (Variable v : processVar) {
				if(vars.contains(v.getKey())){
					task.setTaskName(String.valueOf(v.getValue()));
					break;
				}
			}
		}*/
		Object processVar =  context.getProcessService().getProcessVariable(Utils.getContentInfo(task.getTaskName()),processInstance.getId());
		if(processVar!=null){

			String name = Utils.parseContentInfo(task.getTaskName(),String.valueOf(processVar));
			task.setTaskName(name);
		}
		task.setRootProcessInstanceId(processInstance.getRootId());
		task.setDescription(context.getExpressionContext().evalString(processInstance, getDescription()));
		task.setCreateDate(new Date());
		task.setState(TaskState.Created);
		task.setProcessId(getProcessId());
		task.setProcessInstanceId(processInstance.getId());
		task.setPrevTask(processInstance.getCurrentTask());
		task.setBusinessId(processInstance.getBusinessId());
		task.setSubject(processInstance.getSubject());
		String targetUrl=getUrl();
		if(StringUtils.isEmpty(targetUrl)){
			targetUrl=getFormTemplate();
		}
		task.setUrl(context.getExpressionContext().evalString(processInstance, targetUrl));
		//设置任务的任务变量
		return task;
	}

	public DueDefinition getCustomDueDefinition(ProcessInstance processInstance,Task task,ApplicationContext applicationContext){
		Collection<TaskDueDefinitionProvider> colls=applicationContext.getBeansOfType(TaskDueDefinitionProvider.class).values();
		if(colls.size()==0) {
			return null;
		}
		return colls.iterator().next().getDueDefinition(task, processInstance);
	}

	private Date createReminder(ProcessInstance processInstance,Task task,Context context) {
		Reminder reminder;
		DueDefinition dueDef=getCustomDueDefinition(processInstance, task, context.getApplicationContext());
		Date startReminderDate;
		if(dueDef!=null){
			reminder=dueDef.getReminder();
			startReminderDate=computeDueReminderStartdate(context,dueDef);
		}else{
			if(dueDefinition==null) {
				return null;
			}
			startReminderDate=computeDueReminderStartdate(context,dueDefinition);
			reminder=dueDefinition.getReminder();
		}
		SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
		if(reminder!=null){
			ReminderType type=ReminderType.Once;
			String cron=null;
			if(reminder instanceof PeriodReminder){
				type=ReminderType.Period;
				PeriodReminder period=(PeriodReminder)reminder;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startReminderDate);
				DateUnit unit=period.getUnit();
				int sec=calendar.get(Calendar.SECOND);
				int min=calendar.get(Calendar.MINUTE);
				int hour=calendar.get(Calendar.HOUR);
				if(unit.equals(DateUnit.Minute)){
					cron=""+sec+" 0/"+period.getRepeat()+" * ? * *";
				}else if(unit.equals(DateUnit.Hour)){
					cron=""+sec+" "+min+" 0/"+period.getRepeat()+" ? * *";
				}else if(unit.equals(DateUnit.Day)){
					cron=""+sec+" "+min+" "+hour+" 1/"+period.getRepeat()+" * ?";
				}else{
					throw new IllegalArgumentException("Unsupport date unit :"+unit);
				}
			}
			TaskReminder taskReminder=new TaskReminder();
			taskReminder.setId(IDGenerator.getInstance().nextId());
			taskReminder.setTaskId(task.getId());
			taskReminder.setTaskNodeName(getName());
			taskReminder.setProcessId(processInstance.getProcessId());
			taskReminder.setReminderHandlerBean(reminder.getHandlerBean());
			taskReminder.setType(type);
			taskReminder.setStartDate(startReminderDate);
			taskReminder.setCron(cron);
			schedulerService.addReminderJob(taskReminder,processInstance,task);
			context.getSession().save(taskReminder);
		}
		TaskReminder dueActionReminder=buildDueAction(dueDef,startReminderDate,context,task);
		if(dueActionReminder!=null){
			schedulerService.addReminderJob(dueActionReminder,processInstance,task);
			context.getSession().save(dueActionReminder);
		}
		return startReminderDate;
	}

	private TaskReminder buildDueAction(DueDefinition dueDef,Date startReminderDate,Context context,Task task){
		DueAction dueAction;
		if(dueDef!=null){
			dueAction=dueDef.getDueAction();
		}else{
			if(dueDefinition==null){
				return null;
			}
			dueAction=dueDefinition.getDueAction();
		}
		if(dueAction==null || dueAction.getHandlerBean()==null){
			return null;
		}
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(startReminderDate);
		int day=dueAction.getDay();
		int hour=dueAction.getHour();
		int minute=dueAction.getMinute();
		BusinessCalendar businessCalendar=(BusinessCalendar)context.getApplicationContext().getBean(BusinessCalendar.BEAN_ID);
		int businessDayHours=businessCalendar.getBusinessDayHours();
		if(dueDef!=null && dueDef.getBusinessDayHours()>0){
			businessDayHours=dueDef.getBusinessDayHours();
		}
		if(day>0){
			endCalendar.add(Calendar.HOUR_OF_DAY, day*businessDayHours);
		}
		if(hour>0){
			endCalendar.add(Calendar.HOUR_OF_DAY, hour);
		}
		if(minute>0){
			endCalendar.add(Calendar.MINUTE, minute);
		}
		List<CalendarInfo> infos=dueAction.getCalendarInfos();
		Date executeActionDate;
		if(infos!=null && infos.size()>0){
			Calendar startCalendar = Calendar.getInstance();
			SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
			startCalendar.setTime(startReminderDate);
			org.quartz.Calendar baseCalendar=schedulerService.buildCalendar(infos);
			executeActionDate=businessCalendar.calEndDate(startCalendar,endCalendar,baseCalendar);
		}else{
			executeActionDate=endCalendar.getTime();
		}
		TaskReminder taskReminder=new TaskReminder();
		taskReminder.setId(IDGenerator.getInstance().nextId());
		taskReminder.setTaskId(task.getId());
		taskReminder.setTaskNodeName(getName());
		taskReminder.setProcessId(task.getProcessId());
		taskReminder.setReminderHandlerBean(dueAction.getHandlerBean());
		taskReminder.setType(ReminderType.Once);
		taskReminder.setStartDate(executeActionDate);
		task.setDueActionDate(executeActionDate);
		return taskReminder;
	}

	private Date computeDueReminderStartdate(Context context,DueDefinition dueDef){
		if(dueDef==null) {
			return null;
		}
		int dueDay=dueDef.getDay();
		int dueHour=dueDef.getHour();
		int dueMinute=dueDef.getMinute();
		Calendar endCalendar = Calendar.getInstance();
		BusinessCalendar businessCalendar=(BusinessCalendar)context.getApplicationContext().getBean(BusinessCalendar.BEAN_ID);
		int businessDayHours=businessCalendar.getBusinessDayHours();
		if(dueDef.getBusinessDayHours()>0){
			businessDayHours=dueDef.getBusinessDayHours();
		}
		if(dueDay>0){
			endCalendar.add(Calendar.HOUR_OF_DAY, dueDay*businessDayHours);
		}
		if(dueHour>0){
			endCalendar.add(Calendar.HOUR_OF_DAY, dueHour);
		}
		if(dueMinute>0){
			endCalendar.add(Calendar.MINUTE, dueMinute);
		}
		List<CalendarInfo> infos=dueDef.getCalendarInfos();
		if(infos!=null && infos.size()>0){
			Calendar startCalendar = Calendar.getInstance();
			SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
			org.quartz.Calendar baseCalendar=schedulerService.buildCalendar(infos);
			return businessCalendar.calEndDate(startCalendar, endCalendar,baseCalendar);
		}else{
			return endCalendar.getTime();
		}
	}

    public static Date computeFireTimesWithStart(OperableTrigger trigg,org.quartz.Calendar cal, Date from) {
        OperableTrigger t = (OperableTrigger) trigg.clone();
        if (t.getNextFireTime() == null) {
            t.setStartTime(from);
            t.computeFirstFireTime(cal);
        }
        return t.getNextFireTime();
    }


	private void processAssignees(Context context,ProcessInstance processInstance,List<Assignee> assigneeList) {
		List<String> users= new ArrayList<>();
		retriveAssignees(assigneeList,context,processInstance,users);
		createTasks(context,processInstance,users);
	}

	private void retriveAssignees(List<Assignee> assigneeList,Context context,ProcessInstance processInstance,List<String> users) {
		ApplicationContext applicationContext=context.getApplicationContext();
		for(Assignee assignee:assigneeList){
			AssigneeProvider provider=(AssigneeProvider)applicationContext.getBean(assignee.getProviderId());
			Collection<String> coll=provider.getUsers(assignee.getId(),context,processInstance);
			if(coll!=null && coll.size()>0){
				users.addAll(coll);
			}
		}
	}

	@Override
	public synchronized String leave(Context context,ProcessInstance processInstance,String flowName) {
		if(taskType.equals(TaskType.Countersign)){
			TaskQuery query=context.getTaskService().createTaskQuery();
			query.addTaskState(TaskState.Created).addTaskState(TaskState.Ready).addTaskState(TaskState.Suspended).addTaskState(TaskState.InProgress).addTaskState(TaskState.Reserved);
			query.processInstanceId(processInstance.getId());
			List<Task> countersignTasks=query.list();
			if(countersignTasks.size()>0){
				Task task=countersignTasks.get(0);
				int alreadyCompletedCount=task.getCountersignCount()-countersignTasks.size();
				if(countersignMultiplicity>0){
					if(alreadyCompletedCount>=countersignMultiplicity){
						cancelTasks(context, processInstance, countersignTasks);
					}else{
						return null;
					}
				}else if(countersignPercentMultiplicity>0){
					double percent= (double) countersignPercentMultiplicity / 100.0;
					double alreadyCompletedPercent=((double) alreadyCompletedCount / (double) task.getCountersignCount());
					if(alreadyCompletedPercent>=percent){
						cancelTasks(context, processInstance, countersignTasks);
					}else{
						return null;
					}
				}else if(StringUtils.isNotEmpty(countersignExpression)){
					Object obj=context.getExpressionContext().eval(processInstance.getId(), countersignExpression);
					if(!(obj instanceof Boolean)){
						throw new IllegalArgumentException(getName()+" countersign task expression "+countersignExpression+" value type must be is boolean,current is "+obj+"");
					}
					boolean result=(Boolean)obj;
					if(result){
						cancelTasks(context, processInstance, countersignTasks);
					}else{
						return null;
					}
				}else if(StringUtils.isNotEmpty(countersignHandler)){
					CountersignHandler handler=(CountersignHandler)context.getApplicationContext().getBean(countersignHandler);
					if(handler.handle(context, processInstance)){
						cancelTasks(context, processInstance, countersignTasks);
					}else{
						return null;
					}
				}else{
					if(alreadyCompletedCount<task.getCountersignCount()){
						return null;
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(flowName)){
			SequenceFlowImpl flow=getFlow(flowName);
			if(flow==null){
				throw new IllegalArgumentException("连线 "+flowName+" 不存在!");
			}
			flow.execute(context, processInstance);
			return flow.getName();
		}else{
			return leaveNode(context, processInstance, flowName);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cancel(Context context, ProcessInstance processInstance) {
		Session session=context.getSession();
		Collection<Task> tasks=session.createCriteria(Task.class).add(Restrictions.eq("processInstanceId", processInstance.getId())).list();
		cancelTasks(context, processInstance, tasks);
	}

	private void cancelTasks(Context context, ProcessInstance processInstance, Collection<Task> tasks) {
		Session session=context.getSession();
		SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
		Collection<CancelTaskInterceptor> interceptors=context.getApplicationContext().getBeansOfType(CancelTaskInterceptor.class).values();
		for(Task task:tasks){
			for(CancelTaskInterceptor interceptor:interceptors){
				interceptor.intercept(context, task);
			}
			if(dueDefinition!=null){
				schedulerService.removeReminderJob(task);
			}
			task.setState(TaskState.Canceled);
			session.update(task);
			context.getCommandService().executeCommand(new SaveHistoryTaskCommand(task,processInstance));
		}
	}

	public Authority getComponentAuthority(String component){
		if(componentAuthorities==null || componentAuthorities.size()==0){
			return Authority.ReadAndWrite;
		}
		boolean read=false;
		boolean write=false;
		for(ComponentAuthority componentAuthority:componentAuthorities){
			if(component.equals(componentAuthority.getComponent())){
				if(componentAuthority.getAuthority().equals(Authority.Read)){
					read=true;
				}else if(componentAuthority.getAuthority().equals(Authority.ReadAndWrite)){
					read=true;
					write=true;
					break;
				}
			}
		}
		if(read && write){
			return Authority.ReadAndWrite;
		}
		if(read){
			return Authority.Read;
		}
		return null;
	}

	@Override
	public NodeType getType() {
		if(taskType.equals(TaskType.Countersign)){
			return NodeType.CountersignTask;
		}
		return NodeType.Task;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
	}

	public AssignmentType getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(AssignmentType assignmentType) {
		this.assignmentType = assignmentType;
	}

	public int getCountersignMultiplicity() {
		return countersignMultiplicity;
	}

	public void setCountersignMultiplicity(int countersignMultiplicity) {
		this.countersignMultiplicity = countersignMultiplicity;
	}

	public int getCountersignPercentMultiplicity() {
		return countersignPercentMultiplicity;
	}

	public void setCountersignPercentMultiplicity(int countersignPercentMultiplicity) {
		this.countersignPercentMultiplicity = countersignPercentMultiplicity;
	}

	public String getCountersignExpression() {
		return countersignExpression;
	}

	public void setCountersignExpression(String countersignExpression) {
		this.countersignExpression = countersignExpression;
	}

	public String getCountersignHandler() {
		return countersignHandler;
	}

	public void setCountersignHandler(String countersignHandler) {
		this.countersignHandler = countersignHandler;
	}

	public String getAssignmentHandlerBean() {
		return assignmentHandlerBean;
	}

	public void setAssignmentHandlerBean(String assignmentHandlerBean) {
		this.assignmentHandlerBean = assignmentHandlerBean;
	}

	public List<Assignee> getAssignees() {
		return assignees;
	}

	public void setAssignees(List<Assignee> assignees) {
		this.assignees = assignees;
	}

	public String getSwimlane() {
		return swimlane;
	}

	public void setSwimlane(String swimlane) {
		this.swimlane = swimlane;
	}


	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public List<ComponentAuthority> getComponentAuthorities() {
		return componentAuthorities;
	}


	public void setComponentAuthorities(List<ComponentAuthority> componentAuthorities) {
		this.componentAuthorities = componentAuthorities;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public DueDefinition getDueDefinition() {
		return dueDefinition;
	}

	public void setDueDefinition(DueDefinition reminderDefinition) {
		this.dueDefinition = reminderDefinition;
	}

	public boolean isAllowSpecifyAssignee() {
		return allowSpecifyAssignee;
	}

	public void setAllowSpecifyAssignee(boolean allowSpecifyAssignee) {
		this.allowSpecifyAssignee = allowSpecifyAssignee;
	}

	public List<FormElement> getFormElements() {
		return formElements;
	}

	public void setFormElements(List<FormElement> formElements) {
		this.formElements = formElements;
	}

	public List<UserData> getUserData() {
		return userData;
	}

	public void setUserData(List<UserData> userData) {
		this.userData = userData;
	}

	public String getTaskListenerBean() {
		return taskListenerBean;
	}

	public String getAssignmentHandlerBeanDesc() {
		return assignmentHandlerBeanDesc;
	}

	public void setAssignmentHandlerBeanDesc(String assignmentHandlerBeanDesc) {
		this.assignmentHandlerBeanDesc = assignmentHandlerBeanDesc;
	}

	public void setTaskListenerBean(String taskListenerBean) {
		this.taskListenerBean = taskListenerBean;
	}
	private Collection<GlobalTaskListener> fetchGlobalTaskListener(Context context){
		return context.getApplicationContext().getBeansOfType(GlobalTaskListener.class).values();
	}
}
