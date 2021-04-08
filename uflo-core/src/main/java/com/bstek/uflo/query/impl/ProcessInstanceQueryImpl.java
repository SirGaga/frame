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
package com.bstek.uflo.query.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.command.impl.QueryCountCommand;
import com.bstek.uflo.command.impl.QueryListCommand;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.query.ProcessInstanceQuery;
import com.bstek.uflo.query.QueryJob;

/**
 * @author Jacky.gao
 * @since 2013年8月14日
 */
public class ProcessInstanceQueryImpl implements ProcessInstanceQuery,QueryJob {
	private long processId;
	private long parentId=-1;
	private long rootId=-1;
	private int firstResult;
	private int maxResults;
	private String businessId;
	private String promoter;
	private String subject;
	private Date createDateLessThen;
	private Date createDateLessThenOrEquals;
	private Date createDateGreaterThen;
	private Date createDateGreaterThenOrEquals;
	private List<String> ascOrders=new ArrayList<String>();
	private List<String> descOrders=new ArrayList<String>();
	private CommandService commandService;
	public ProcessInstanceQueryImpl(CommandService commandService){
		this.commandService=commandService;
	}

	@Override
    public Criteria getCriteria(Session session, boolean queryCount) {
		Criteria criteria=session.createCriteria(ProcessInstance.class);
		buildCriteria(criteria,queryCount);
		return criteria;
	}

	@Override
    public List<ProcessInstance> list(){
		return commandService.executeCommand(new QueryListCommand<List<ProcessInstance>>(this));
	}

	@Override
    public ProcessInstanceQuery promoter(String promoter) {
		this.promoter=promoter;
		return this;
	}

	@Override
    public int count(){
		return commandService.executeCommand(new QueryCountCommand(this));
	}

	private void buildCriteria(Criteria criteria,boolean queryCount){
		if(!queryCount && firstResult>0){
			criteria.setFirstResult(firstResult);
		}
		if(!queryCount && maxResults>0){
			criteria.setMaxResults(maxResults);
		}
		if(processId>0){
			criteria.add(Restrictions.eq("processId", processId));
		}
		if(parentId>-1){
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if(rootId>-1){
			criteria.add(Restrictions.eq("rootId", rootId));
		}
		if(StringUtils.isNotEmpty(businessId)){
			criteria.add(Restrictions.eq("businessId", businessId));
		}
		if(StringUtils.isNotEmpty(promoter)){
			criteria.add(Restrictions.eq("promoter", promoter));
		}
		if(createDateLessThen!=null){
			criteria.add(Restrictions.lt("createDate", createDateLessThen));
		}
		if(createDateGreaterThen!=null){
			criteria.add(Restrictions.gt("createDate", createDateGreaterThen));
		}
		if(createDateLessThenOrEquals!=null){
			criteria.add(Restrictions.le("createDate", createDateLessThenOrEquals));
		}
		if(createDateGreaterThenOrEquals!=null){
			criteria.add(Restrictions.ge("createDate", createDateGreaterThenOrEquals));
		}

		if(StringUtils.isNotEmpty(subject)){
			criteria.add(Restrictions.eq("subject", subject));
		}
		if(!queryCount){
			for(String ascProperty:ascOrders){
				criteria.addOrder(Order.asc(ascProperty));
			}
			for(String descProperty:descOrders){
				criteria.addOrder(Order.desc(descProperty));
			}
		}
	}

	@Override
    public ProcessInstanceQuery businessId(String businessId) {
		this.businessId=businessId;
		return this;
	}



	@Override
    public ProcessInstanceQuery processId(long processId){
		this.processId=processId;
		return this;
	}

	@Override
    public ProcessInstanceQuery page(int firstResult, int maxResults){
		this.firstResult=firstResult;
		this.maxResults=maxResults;
		return this;
	}

	@Override
    public ProcessInstanceQuery addOrderAsc(String property){
		ascOrders.add(property);
		return this;
	}

	@Override
    public ProcessInstanceQuery addOrderDesc(String property){
		descOrders.add(property);
		return this;
	}

	@Override
    public ProcessInstanceQuery createDateLessThen(Date date){
		this.createDateLessThen=date;
		return this;
	}

	@Override
    public ProcessInstanceQuery createDateLessThenOrEquals(Date date){
		this.createDateLessThenOrEquals=date;
		return this;
	}

	@Override
    public ProcessInstanceQuery createDateGreaterThen(Date date){
		this.createDateGreaterThen=date;
		return this;
	}

	@Override
    public ProcessInstanceQuery createDateGreaterThenOrEquals(Date date){
		this.createDateGreaterThenOrEquals=date;
		return this;
	}

	@Override
    public ProcessInstanceQuery parentId(long parentId) {
		this.parentId=parentId;
		return this;
	}
	@Override
    public ProcessInstanceQuery rootId(long rootId) {
		this.rootId=rootId;
		return this;
	}

	@Override
    public ProcessInstanceQuery subject(String subject) {
		this.subject=subject;
		return this;
	}
}
