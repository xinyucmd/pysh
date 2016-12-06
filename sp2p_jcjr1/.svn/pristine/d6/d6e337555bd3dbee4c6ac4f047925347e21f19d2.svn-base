package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.sp2p.service.BorrowService;


public class JobDebtTask extends QuartzJobBean {
	
	private static Log log = LogFactory.getLog(JobDebtTask.class);
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
	    BorrowService borrowService = (BorrowService)getBean("borrowService");
	    try {
			if(!isRunning){
				isRunning = true;
				log.info("每日任务处理开始");
				//系统自动撤销债权
				jobTaskService.cancelDebt();
				log.info("系统自动撤销债权OK");
				log.info("每日任务处理结束");
				
				log.info("处理过期借款一轮开始");
				borrowService.refreshBorrowTime();
				log.info("处理过期借款一轮结束");
				
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
