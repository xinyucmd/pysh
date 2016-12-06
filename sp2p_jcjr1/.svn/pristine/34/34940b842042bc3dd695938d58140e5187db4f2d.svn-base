package com.sp2p.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.sp2p.service.BonusService;
import com.sp2p.util.DateUtil;

public class JobBonusTask extends QuartzJobBean {
	private static boolean isRuning = false;
	private static int waitCount = 0;
	private static Log log = LogFactory.getLog(JobBonusTask.class);
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
	@Override
	synchronized protected void executeInternal(JobExecutionContext jobContext)
			throws JobExecutionException {
		
		 // 任务正在执行，跳过本次执行
        if (isRuning) {
            JobDetail jobDetail = jobContext.getJobDetail();
            log.info(jobDetail.getGroup() + "." + jobDetail.getName()
                    + ":前一次未执行完,跳过本次任务!");
            if(waitCount>10){
            	log.info("等待执行次数大于10次，请检查系统状态！！！");
            }
            waitCount ++;
            return;
        }
		
		long start = System.currentTimeMillis();    
		
	    try {
	    	isRuning = true;
	    	BonusService jobTaskService =  (BonusService) getBean("bonusService");
	    	// 判断红包是否结束
	    	Map<String,String> bonusConfig = jobTaskService.queryBounsConfigAvaliable();
	    	
	    	if(bonusConfig != null){
	    		log.info("====开始发送红包奖励=====");
	    		
	    		// 注册红包奖励
	    		regBonusHandler(bonusConfig);
	    		
	    		// 注册获取10元奖励
	    		regBonusCash(bonusConfig);
	    		
	    		// 投资红包奖励
	    		investBonusHandler(bonusConfig);
	    		
	    		log.info("====结束发送红包奖励=====");
	    		
	    		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
	    				+"SystemMemery:freeMemory"+Runtime.getRuntime().freeMemory()+"-------maxMemory"+Runtime.getRuntime().maxMemory()+"-------totalMemory"+Runtime.getRuntime().totalMemory());
			
	    	}else{
	    		log.info("====红包配置已过期=====");
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}finally{
			isRuning = false;
		}
	    
	}
	
	/**
	 * 注册奖励
	 */
	private synchronized void regBonusHandler(Map<String,String> bonusConfig){
		BonusService jobTaskService =  (BonusService) getBean("bonusService");
		try {
			if(bonusConfig != null){
				log.info("======[1-START]开始发放注册======");
				List<Map<String,Object>> reuslt = jobTaskService.queryUserTop();
				
				double reg_registration_red = Convert.strToDouble(bonusConfig.get("reg_registration_red"), 0);
				double reg_recommended_red = Convert.strToDouble(bonusConfig.get("reg_recommended_red"), 0);
				int use_deadline =  Convert.strToInt(bonusConfig.get("use_deadline"), 0);
				String endTime = getUseDeadline(use_deadline);
				
				for(Map<String,Object> map : reuslt){
					long userId = Convert.strToLong(String.valueOf(map.get("user_id")), 0);
					long recommendUserId = Convert.strToLong(String.valueOf(map.get("recommendUserId")), 0);
					
					if(reg_registration_red>0){
						if(getBonusAvaliable(bonusConfig)>=reg_registration_red){
							jobTaskService.addBonusList(userId,userId ,reg_registration_red, reg_registration_red, 0, 1,1,endTime);
						}else{
							log.info("======红包金额已用尽！======");
							break;
						}
					}
					
					if(reg_recommended_red>0){
						if(getBonusAvaliable(bonusConfig)>=reg_recommended_red){
							if(recommendUserId >0){// 判断是否有推荐人
								jobTaskService.addBonusList(recommendUserId, userId,reg_recommended_red, reg_recommended_red, 0, 3,1,endTime);
							}
						}else{
							log.info("======红包金额已用尽！======");
							break;
						}
					}
				}
				log.info("======[1-END]结束发放注册======");
			}else{
				log.info("[注册]请检查红包配置");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取注册奖励
	 * @throws Exception 
	 */
	private synchronized void regBonusCash(Map<String,String> bonusConfig) throws Exception{
		BonusService jobTaskService =  (BonusService) getBean("bonusService");
		log.info("======[2-START]开始发放现金红包======");
		double reg_registration_cash = Convert.strToDouble(bonusConfig.get("reg_registration_cash"), 0);
		Long super_id = Convert.strToLong(String.valueOf(bonusConfig.get("super_id")), 0);
		List<Map<String,Object>> result = jobTaskService.queryBonusListForNothingCash();
		
		if(result!= null && reg_registration_cash>0){
			for(Map<String,Object> map : result){
				Long id = Convert.strToLong(String.valueOf(map.get("id")), 0);
				Long userId = Convert.strToLong(String.valueOf(map.get("user_id")), 0);
				// 注册人获取现金奖励
				Long brokerageResult = jobTaskService.updateRecommendBrokerage(super_id,userId,reg_registration_cash,5);
				if(brokerageResult>0){					
					int uid = Convert.strToInt(String.valueOf(map.get("user_id")), 0);
					Long rewordRecordResult = jobTaskService.addRewardRecord(1, 10, uid, -1, DateUtil.dateToString(new Date()), "注册奖励");
					if(rewordRecordResult>0){
						Long bonusListResult = jobTaskService.updateBonusList(id, 2);
						if(bonusListResult<=0){
							log.info("* 更新红包日志失败【updateBonusList】");
						}
					}else{
						log.info("* 记录奖励日志失败【addRewardRecord】");
					}
				}else{
					log.info("* 发放注册奖励失败，请检查超级账户余额是否为0");
				}
			}
		}
		log.info("======[2-END]结束发放现金红包======");
	}
	
	/**
	 * 投资红包
	 */
	private synchronized void investBonusHandler(Map<String,String> bonusConfig){
		BonusService jobTaskService =  (BonusService) getBean("bonusService");
		try {
			// 读取配置
			List<Map<String, Object>> amountSetting = jobTaskService.queryBonusAmount();
			if(amountSetting != null){
				log.info("======[3-START]开始发放投资红包======");
				// 获取新的投资列表
				List<Map<String, Object>> investTop  = jobTaskService.queryInvestTop();
				int use_deadline =  Convert.strToInt(bonusConfig.get("use_deadline"), 0);
				String endTime = getUseDeadline(use_deadline);
				// 发放红包
				for(Map<String, Object> map : investTop){
					// 投资用户
					long userId = Convert.strToLong(String.valueOf(map.get("investor")), 0);
					
					//最早一次投资金额
					double amount = Convert.strToDouble(String.valueOf(map.get("amount")), 0);
					
					// 投资总额
					double investAmount = Convert.strToDouble(String.valueOf(map.get("investAmount")), 0);
					// 红包面值
					double red_value =0;
					
					setting_outer:
					for(Map<String,Object> setMap : amountSetting){
						// 最小投资金额
						double min_invest_amount = Convert.strToDouble(String.valueOf(setMap.get("min_invest_amount")), 0);
						// 最大投资金额
						double max_invest_amount = Convert.strToDouble(String.valueOf(setMap.get("max_invest_amount")), 0);
						
						// 判断单次投资是否达到发送红包的限制
						if(min_invest_amount<=amount && amount<=max_invest_amount){
							red_value = Convert.strToDouble(String.valueOf(setMap.get("red_value")), 0);
							break setting_outer;
						}else{ // 判断累计金额是否达到发送红包的限制
							if(min_invest_amount<=investAmount && investAmount<=max_invest_amount){
								red_value = Convert.strToDouble(String.valueOf(setMap.get("red_value")), 0);
								break setting_outer;
							}
						}
					}
					
					if(red_value>0){
						if(getBonusAvaliable(bonusConfig)>=red_value){
							jobTaskService.addBonusList(userId, userId,red_value, red_value, 0, 2,1,endTime);
						}else{
							log.info("======红包金额已用尽！======");
							break;
						}
						
					}
				}
				
				log.info("======[3-END]结束发放投资红包======");
			}else{
				log.info("[投资]请检查红包配置");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized double getBonusAvaliable(Map<String,String> bonusConfig) throws Exception{
		if(bonusConfig != null){
			double red_limit = Convert.strToDouble(String.valueOf(bonusConfig.get("red_limit")), 0);
			// 已经发放了多少红包
			BonusService jobTaskService =  (BonusService) getBean("bonusService");
			Map<String,String> bonusListCountMap = jobTaskService.queryBonusListCount();
			double bonusMoneySum = 0;
			
			if(bonusListCountMap != null){
				bonusMoneySum = Convert.strToDouble(bonusListCountMap.get("bonusMoneySum"), -1);
			}
			
			if(bonusMoneySum>=0){
				return red_limit - bonusMoneySum;
			}
		}else{
			log.info("[投资]请检查红包配置");
		}
		
		return 0;
	}
	
	private synchronized String getUseDeadline(int useDeadline) throws Exception{
		String result = "";
		if(useDeadline>=1){
			useDeadline = useDeadline-1;
			result = DateUtil.getCurrDateLateDay(useDeadline);
		}
		
		return result;
	}
	
}
