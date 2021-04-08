package com.bstek.uflo.heartbeat;

import com.bstek.uflo.service.SchedulerService;
import com.bstek.uflo.utils.EnvironmentUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.simpl.SimpleThreadPool;
import org.springframework.beans.factory.InitializingBean;

import java.text.ParseException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Jacky.gao
 * @since 2013-5-6
 */
public class InstanceDetection implements InitializingBean{
	public static final String BEAN_ID="uflo.instanceDetection";
	private String instanceNames;
	private Scheduler scheduler;
	private boolean disableScheduler;
	private SchedulerService schedulerService;
	private String detectionCron="0/30 * * * * ?";
	private final Logger log=Logger.getGlobal();
	private void startDaemonJob() throws Exception{
		if(disableScheduler){
			log.info("Current uflo application is disabled scheduler...");
			return;
		}
		String currentInstanceName=System.getProperty("uflo.instanceName");
		if(StringUtils.isBlank(instanceNames)){
			if(StringUtils.isNotBlank(currentInstanceName)){
				log.info("Uflo job cluster names is empty,but system property \"uflo.instanceName\" value is \""+currentInstanceName+"\",so Uflo job run mode is single still...");
			}else{
				log.info("Uflo job run mode is single...");				
			}
			schedulerService.resetScheduer();
			return;
		}else{
			if(StringUtils.isBlank(currentInstanceName)){
				String msg="Current uflo application configured cluster names \""+instanceNames+"\",but not configure system property \"uflo.instanceName\".";
				log.info(msg);
				throw new RuntimeException(msg);
			}
		}
		log.info("Uflo job run mode is cluster...");
		initDetectionScheduler();
		
		JobDetailImpl jobDetail=initJobDetail(currentInstanceName);
		Trigger trigger=initTrigger();
		HeartbeatDetectionJob detectionJob=new HeartbeatDetectionJob();
		jobDetail.setJobClass(detectionJob.getClass());
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
		log.info("Uflo cluster daemon scheduler is started...");		
	}
	
	private void initDetectionScheduler() throws Exception{
		StdSchedulerFactory factory=new StdSchedulerFactory();
		Properties mergedProps = new Properties();
		mergedProps.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, SimpleThreadPool.class.getName());
		mergedProps.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon", "true");
		mergedProps.setProperty("org.quartz.scheduler.instanceName", "UfloClusterHeartbeatScheduler");
		mergedProps.setProperty("org.quartz.scheduler.instanceId", "UfloHeartbeatDetectionScheduler");
		mergedProps.setProperty("org.quartz.threadPool.threadCount","2");
		factory.initialize(mergedProps);
		scheduler=factory.getScheduler();
	}
	
	private JobDetailImpl initJobDetail(String currentInstanceName){
		String[] clusterJobInstanceNames =instanceNames.split(",");
		SessionFactory sessionFactory=EnvironmentUtils.getEnvironment().getSessionFactory();
		JobDetailImpl jobDetail=new DetectionJobDetail(sessionFactory,currentInstanceName,clusterJobInstanceNames,schedulerService);
		jobDetail.setKey(new JobKey("UfloDaemonJobDetail"));
		jobDetail.setName("UfloDaemonDetectionJobDetail");
		return jobDetail;
	}
	
	private Trigger initTrigger(){
		CronTriggerImpl trigger=new CronTriggerImpl();
		trigger.setName("UfloHeartbeatTrigger");
		trigger.setKey(new TriggerKey("UfloHeartbeatTrigger"));
		try {
			trigger.setCronExpression(detectionCron);
			return trigger;
		} catch (ParseException e1) {
			throw new RuntimeException(e1);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		startDaemonJob();
	}
	
	public void setDisableScheduler(boolean disableScheduler) {
		this.disableScheduler = disableScheduler;
	}

	
	public void setInstanceNames(String instanceNames) {
		this.instanceNames = instanceNames;
	}
	
	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	public void setDetectionCron(String detectionCron) {
		this.detectionCron = detectionCron;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
}
