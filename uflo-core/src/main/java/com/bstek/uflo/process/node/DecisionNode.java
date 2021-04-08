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
package com.bstek.uflo.process.node;

import org.apache.commons.lang.StringUtils;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.flow.SequenceFlowImpl;
import com.bstek.uflo.process.handler.DecisionHandler;

/**
 * @author Jacky.gao
 * @since 2013年8月8日
 */
public class DecisionNode extends Node {
	private static final long serialVersionUID = -6950253419921784972L;
	private String expression;
	private String handlerBean;
	private DecisionType decisionType;
	@Override
	public boolean enter(Context context, ProcessInstance processInstance) {
		return true;
	}

	@Override
	public String leave(Context context, ProcessInstance processInstance,String flowName) {
		if(decisionType.equals(DecisionType.Handler)){
			DecisionHandler handler=(DecisionHandler)context.getApplicationContext().getBean(handlerBean);
			flowName=handler.handle(context, processInstance);
		}else{
			Object obj=context.getExpressionContext().eval(processInstance, expression);
			try {
				flowName=String.valueOf(obj);
			}catch (Exception e){
				throw new IllegalArgumentException("表达式 ["+expression+"] 不是String类型!");
			}
		}
		if(StringUtils.isEmpty(flowName)){
			throw new IllegalArgumentException("决策节点必须至少指定handlerBean或表达式");
		}
		SequenceFlowImpl flow=getFlow(flowName);
		if(flow==null){
			throw new IllegalArgumentException("根据条件找不到下一处理节点");
		}
		flow.execute(context, processInstance);
		return flow.getName();
	}

	@Override
	public void cancel(Context context, ProcessInstance processInstance) {
	}

	@Override
	public NodeType getType() {
		return NodeType.Decision;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getHandlerBean() {
		return handlerBean;
	}

	public void setHandlerBean(String handlerBean) {
		this.handlerBean = handlerBean;
	}

	public DecisionType getDecisionType() {
		return decisionType;
	}

	public void setDecisionType(DecisionType decisionType) {
		this.decisionType = decisionType;
	}
}
