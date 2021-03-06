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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.bstek.uflo.heartbeat.InstanceDetection;
import com.bstek.uflo.service.SchedulerService;
import com.bstek.uflo.utils.EnvironmentUtils;

/**
 * @author Jacky.gao
 * @since 2014年12月24日
 */
public class JobSchedulerServletContextListener implements ServletContextListener {

	@Override
    public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
    public void contextDestroyed(ServletContextEvent sce) {
		ApplicationContext context=EnvironmentUtils.getEnvironment().getApplicationContext();
		SchedulerService schedulerService=(SchedulerService)context.getBean(SchedulerService.BEAN_ID);
		Scheduler scheduler=schedulerService.getScheduler();
		if(scheduler!=null){
			try {
				if(!scheduler.isShutdown()){
					scheduler.shutdown(false);
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		InstanceDetection detection=(InstanceDetection)context.getBean(InstanceDetection.BEAN_ID);
		scheduler=detection.getScheduler();
		if(scheduler!=null){
			try {
				if(!scheduler.isShutdown()){
					scheduler.shutdown(false);
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
}
