package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

public class JobBatchDoBonus extends QuartzJobBean {
	
	private static Log log = LogFactory.getLog(JobDebtTask.class);
	 
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	  
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		JobTaskService jobTaskService =  (JobTaskService) getBean("jobTaskService");
		    try {
		    	
					log.info("p处理过期红包开始......................");
					long m = jobTaskService.updateUserBonus();
					if(m>0){
						log.info("p处理过期红包成功......................");
					}
					if(m==0){
						log.info("不存在要P处理的过期红包......................");
					}
					
					if(m<0){
						 log.info("p处理过期红包出现失败......................");
					}
					
			   } catch (Exception e) {
					    e.printStackTrace();
			   } 
	}
}
