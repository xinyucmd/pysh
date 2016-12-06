package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

public class JobEmployeeRepayment  extends QuartzJobBean{
	
	private static Log log = LogFactory.getLog(JobDebtTask.class);
	 
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	  
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		JobTaskService jobTaskService =  (JobTaskService) getBean("jobTaskService");
		    try {
		    	
		    	
					log.info("###############员工还款p处理开始#################");
					long m = jobTaskService.doBatchEmployeeRepayment();
					log.info("###############员工还款p处理结束#################");
					if(m>0){
						log.info("【成功】");
					}else{
					    log.info("【失败】");
					}
			   } catch (Exception e) {
				        log.info("【异常】");
					    e.printStackTrace();
			   } 
	}
}
