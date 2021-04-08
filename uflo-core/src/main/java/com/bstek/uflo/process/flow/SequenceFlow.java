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
package com.bstek.uflo.process.flow;



/**
 * @author Jacky.gao
 * @since 2013年8月19日
 */
public interface SequenceFlow {
	/**
	 * 获取下一步节点名称
	 * @return 返回节点名称
	 */
	String getToNode();

	/**
	 * 获取表达式
	 * @return 返回表达式
	 */
	String getExpression();

	/**
	 * 返回处理器bean
	 * @return bean名称
	 */
	String getHandlerBean();

	/**
	 * 获取条件类型
	 * @return 条件类型
	 */
	ConditionType getConditionType();

	/**
	 * 返回当前任务流
	 * @return 返回任务流名称
	 */
	String getName();
}
