package com.bstek.uflo.process.flow;

import com.bstek.uflo.diagram.SequenceFlowDiagram;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ConditionHandler;
import com.bstek.uflo.process.node.JoinNode;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.StartNode;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.service.ProcessService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Jacky.gao
 * @since 2013年7月31日
 */
public class SequenceFlowImpl implements SequenceFlow,java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String toNode;
	private ConditionType conditionType;
	private String expression;
	private String handlerBean;
	private long processId;
	private String g;
	@JsonIgnore
	private SequenceFlowDiagram diagram;
	public void execute(Context context,ProcessInstance processInstance){
		ProcessService processService=context.getProcessService();
		ProcessDefinition process=processService.getProcessById(processInstance.getProcessId());

		//执行离开节点事件---
		String sourceNodeName=processInstance.getCurrentNode();
		if(StringUtils.isNotEmpty(sourceNodeName)){
			Node source=process.getNode(processInstance.getCurrentNode());
			source.doLeaveEventHandler(context, processInstance);
			//保存节点结束历史
			source.completeActivityHistory(context, processInstance,getName());
		}


		//重设流程实例当前所在节点名称
		Node target=process.getNode(toNode);
		if(!(target instanceof JoinNode)){
			processInstance.setCurrentNode(target.getName());

			//保存节点进入历史
			target.createActivityHistory(context, processInstance);

			//执行节点进入事件
			target.doEnterEventHandler(context, processInstance);
		}

		//执行节点进入动作
		boolean doLeave=target.enter(context,processInstance);
		if(target instanceof TaskNode || target instanceof StartNode){
			processInstance.setCurrentTask(target.getName());
		}
		if(doLeave){
			target.leave(context, processInstance, null);
		}
	}

	public boolean canExecute(Context context,ProcessInstance processInstance){
		boolean result=true;
		if(conditionType==null) {
			return true;
		}
		if(conditionType.equals(ConditionType.Expression) && StringUtils.isNotEmpty(expression)){
			Object obj=context.getExpressionContext().eval(processInstance, expression);
			if(obj==null) {
				return false;
			}
			if(obj instanceof Boolean){
				result=(Boolean)obj;
			}else{
				throw new IllegalArgumentException("Expression ["+expression+"] value type is not a Boolean.");
			}
		}else if(conditionType.equals(ConditionType.Handler) && StringUtils.isNotEmpty(handlerBean)){
			ConditionHandler handler=(ConditionHandler)context.getApplicationContext().getBean(handlerBean);
			result=handler.handle(context, processInstance, this);
		}
		return result;
	}

	@Override
    public String getToNode() {
		return toNode;
	}

	public void setToNode(String toNode) {
		this.toNode = toNode;
	}

	@Override
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String getHandlerBean() {
		return handlerBean;
	}

	public void setHandlerBean(String handlerBean) {
		this.handlerBean = handlerBean;
	}

	@Override
	public ConditionType getConditionType() {
		return conditionType;
	}
	public void setConditionType(ConditionType conditionType) {
		this.conditionType = conditionType;
	}

	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public SequenceFlowDiagram getDiagram() {
		return diagram;
	}

	public void setDiagram(SequenceFlowDiagram diagram) {
		this.diagram = diagram;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}
}
