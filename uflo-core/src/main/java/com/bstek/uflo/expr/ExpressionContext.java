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
package com.bstek.uflo.expr;

import java.util.Map;

import org.apache.commons.jexl2.MapContext;

import com.bstek.uflo.model.ProcessInstance;

/**
 * @author Jacky.gao
 * @since 2013年8月8日
 */
public interface ExpressionContext {
	public static final String BEAN_ID="uflo.expressionContext";
	MapContext createContext(ProcessInstance processInstance, Map<String, Object> variables);
	void addContextVariables(ProcessInstance processInstance, Map<String, Object> variables);
	boolean removeContext(ProcessInstance processInstance);
	void moveContextToParent(ProcessInstance processInstance);
	void removeContextVariables(long processInstanceId, String key);
	Object eval(ProcessInstance processInstance, String expression);
	Object eval(long processInstanceId, String expression);
	String evalString(ProcessInstance processInstance, String str);
}
