package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;


public class JobSysBaseStatis extends QuartzJobBean {
	
	private static Log log = LogFactory.getLog(JobDebtTask.class);
	 
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	  
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		JobTaskService jobTaskService =  (JobTaskService) getBean("jobTaskService");
		    try {
		    	
		    	
					log.info("对系统前一天的用户注册，投资，提现等数据进行跑p处理开始......................");
					long m = jobTaskService.addSysBaseStatis();
					log.info("对系统前一天的用户注册，投资，提现等数据进行跑p处理结束......................");
						
					if(m>0){
						log.info("对系统前一天的用户注册，投资，提现等数据进行跑p处理成功......................");
					}else{
					    log.info("对系统前一天的用户注册，投资，提现等数据进行跑p处理失败......................");
					}
					
					
					
			   } catch (Exception e) {
					    log.info("对系统前一天的用户注册，投资，提现等数据进行跑p处理出现异常......................");
					    e.printStackTrace();
			   } 
	}
}
