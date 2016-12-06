package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

public class JobNovemberInvestAmountTask  extends QuartzJobBean{
	
	private static Log log = LogFactory.getLog(JobDebtTask.class);
	 
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	  
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		JobTaskService jobTaskService =  (JobTaskService) getBean("jobTaskService");
		    try {
		    	
		    	
					log.info("###############11月份活动返红包#################");
					long m = jobTaskService.doBatchNovemberInvestAmount();
					 
					if(m>0){
						log.info("###############执行成功#################");
					}else{
						log.info("###############执行失败#################");
					}
			   } catch (Exception e) {
					log.info("###############执行异常#################");
					    e.printStackTrace();
			   } 
	}
}
