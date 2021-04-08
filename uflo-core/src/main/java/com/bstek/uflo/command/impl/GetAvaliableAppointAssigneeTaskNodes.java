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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bstek.uflo.command.Command;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.expr.ExpressionContext;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.flow.SequenceFlowImpl;
import com.bstek.uflo.process.node.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Jacky.gao
 * @since 2013年10月20日
 */
public class GetAvaliableAppointAssigneeTaskNodes implements Command<List<String>> {
	public Task task;
	public GetAvaliableAppointAssigneeTaskNodes(Task task){
		this.task=task;
	}
	private ExpressionContext expressionContext;

	public void setExpressionContext(ExpressionContext context){
		this.expressionContext = context;
	}

	public List<String> execute(Context context) {
		this.expressionContext = context.getExpressionContext();
		ProcessDefinition pd=context.getProcessService().getProcessById(task.getProcessId());
		Node node=pd.getNode(task.getNodeName());
		List<String> nodes=new ArrayList<String>();
		getAvliableNodes(node,nodes,pd);
		return nodes;
	}
	private void getAvliableNodes(Node node,List<String> nodes,ProcessDefinition pd){
		List<SequenceFlowImpl> flows=node.getSequenceFlows();
		if(flows==null || flows.size()==0)return;
		//判断是否是路由决策节点
		if(node instanceof DecisionNode){
			DecisionNode decisionNode = (DecisionNode)node;
			Object obj = expressionContext.eval(task.getProcessInstanceId(),decisionNode.getExpression());
			flows = flows.stream().filter(n->n.getName().equals(obj)).collect(Collectors.toList());
		}

		for(SequenceFlowImpl flow:flows){
			String flowName = flow.getName();
			if(StringUtils.isNotEmpty(flowName)) {
				if (flowName.indexOf("temp_flow") > 0) {
					continue;
				}
			}
			Node toNode=pd.getNode(flow.getToNode());

			if(StringUtils.isNotEmpty(flowName)){
				toNode.setEnterFlow(flowName);
			}
			if(toNode instanceof StartNode ||
					toNode instanceof JoinNode ||
					toNode instanceof ForeachNode ||
					toNode instanceof SubprocessNode ||
					toNode instanceof ForkNode){
				//toNode.setEnterFlow(flowName);
				continue;
			}
			if(toNode instanceof TaskNode){
				TaskNode taskNode=(TaskNode)toNode;

				if(taskNode.isAllowSpecifyAssignee()){
					nodes.add(toNode.getName());
				}
			} else{

				getAvliableNodes(toNode,nodes,pd);
			}
		}
	}
}
