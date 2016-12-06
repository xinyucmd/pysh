/**
 * 
 */
package com.sp2p.task;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.sp2p.service.admin.RewardService;
import com.sp2p.util.DateUtil;

/**
 * 
 * 处理邀请奖励
 * @author David‎-RYE
 */
public class JobRewardTask extends QuartzJobBean{

	private static Log log = LogFactory.getLog(JobRewardTask.class);
	private static boolean isRunning = false;
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
	/**
	 * 执行方法
	 */
	protected synchronized void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		if(!isRunning){
			long start = System.currentTimeMillis();  
			log.info("**************奖励开始执行*******************");
			try {
				isRunning = true;
				RewardService rewardService =  (RewardService) getBean("rewardService");
				log.info(rewardService);
				
				// TODO：单次注册用户发放奖励
				
				// TODO：发放推荐注册奖励
				
				// 发放推荐单次投资佣金奖励
				rewardService.provideRecommendInvestBrokerage(DateUtil.dateToStringYYMMDD(new Date()),null);
				
				// 发放投资累计奖励
				
				// 发放加息
				rewardService.provideRewardInvestPlusInterest(DateUtil.dateToStringYYMMDD(new Date()),null);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				isRunning = false;
				log.info("**************奖励执行结束*******************");
				log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
						+"SystemMemery:freeMemory"+Runtime.getRuntime().freeMemory()+"-------maxMemory"+Runtime.getRuntime().maxMemory()+"-------totalMemory"+Runtime.getRuntime().totalMemory());
			}
		}
	}

}
