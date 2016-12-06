package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;


public class JobExaminationTask extends QuartzJobBean {
	
	private static Log log = LogFactory.getLog(JobExaminationTask.class);
	private static boolean isRunning = false;
	
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		long start = System.currentTimeMillis();    
	    
	    JobTaskService jobTaskService =  (JobTaskService) getBean("jobTaskService");
		
	    try {
			if(!isRunning){
				isRunning = true;
				//处理过期体检卡
				log.info("每日处理过期体检卡开始");
				jobTaskService.expiredExamination();
				log.info("每日处理过期体检卡结束");
				log.info("每日发送体检卡处理开始");
				jobTaskService.sendExamination();
				log.info("每日发送体检卡处理结束");
				isRunning = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
				+"SystemMemery:freeMemory"+Runtime.getRuntime().freeMemory()+"-------maxMemory"+Runtime.getRuntime().maxMemory()+"-------totalMemory"+Runtime.getRuntime().totalMemory());
	}
}
