package com.sp2p.task;

import java.util.Date;
import java.util.HashMap;
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
import com.sp2p.constants.IConstants;
import com.sp2p.service.PartenersService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.HttpClientHelper;

public class JobOuterTask extends QuartzJobBean {
	private static boolean isRuning = false;
	private static int waitCount = 0;
	private static Log log = LogFactory.getLog(JobOuterTask.class);
	
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
	@Override
	protected synchronized void executeInternal(JobExecutionContext jobContext)
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
	    	log.info("[1-START]====================推送数据至其他平台====================");
	    	updateCsai();
	    	log.info("[1-END]====================推送数据至其他平台====================");
	    	log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
	    			+"SystemMemery:freeMemory"+Runtime.getRuntime().freeMemory()+"-------maxMemory"+Runtime.getRuntime().maxMemory()+"-------totalMemory"+Runtime.getRuntime().totalMemory());
    	} catch (Exception e) {
			e.printStackTrace();
		}finally{
			isRuning = false;
		}
	    
	}
	
	/**
	 * 更新标的给希财网
	 */
	private void updateCsai() {
		try {
			BorrowManageService borrowManageService =  (BorrowManageService) getBean("borrowManageService");
			PartenersService partenersService =  (PartenersService) getBean("partenersService");
			List<Map<String,Object>> result = borrowManageService.updateCsai();
			if(!result.isEmpty()){
				String url = IConstants.CSAI_UPDATE;
				for(Map<String,Object> map:result){
					//product_state--2:暂时不推送，4:永远不推送
					if( ("2".equals(String.valueOf(map.get("product_state"))) && !"2".equals(String.valueOf(map.get("status"))) || !"2".equals(String.valueOf(map.get("product_state"))))){
						Map<String,String> strMap = new HashMap<String, String>();
						for(String key:map.keySet()){
							if(key.equalsIgnoreCase("repay_start_time")||key.equalsIgnoreCase("repay_end_time")){
								if(map.get("repay_start_time") != null && map.get("repay_end_time") != null){
									strMap.put(key, String.valueOf(map.get(key)));
								}
							}
							else{
								strMap.put(key, String.valueOf(map.get(key)));
							}
						}
						strMap.put("access_token", borrowManageService.getCsaiAccessToken());
						
						Map<String,String> restMap = HttpClientHelper.postMap(url, strMap);
						//log.info("更新成功");
						if(restMap.containsKey("code") && restMap.get("code").equals("0")){
							//还完款不再推送
							if("4".equals(strMap.get("product_state"))){
								long id = Convert.strToLong(String.valueOf(map.get("push_id")), -1);
								borrowManageService.updateBorrowPushHis(id, 4);
							}
							//还款中不推送
							else if("2".equals(strMap.get("product_state"))){
								long id = Convert.strToLong(String.valueOf(map.get("push_id")), -1);
								borrowManageService.updateBorrowPushHis(id, 2);
							}
							partenersService.addParenersMessage(6, "希财平台更新数据成功", url, -1L, 5, 0, 0, "", new Date());
						}else{
							log.info("更新失败："+restMap.get("ErrorMessage"));
							partenersService.addParenersMessage(6, "希财平台更新数据失败", url, -1L, 5, 0, 0, "", new Date());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
