package com.sp2p.service.brokerage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.RecommendUserCountDao;
import com.sp2p.service.RecommendBrokerageListService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.SettingActivityService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.CacheRecommendUtils;

public class BrokerageIDRecognition implements IBrokerage{
	
	private SettingActivityService settingActivityService;
	private RecommendUserService recommendUserService;
	private RecommendBrokerageListService recommendBrokerageListService;
	private UserService userService;
	private SMSInterfaceService sMsService;
	private RecommendUserCountDao recommendUserCountDao;
	
	public RecommendUserCountDao getRecommendUserCountDao() {
		return recommendUserCountDao;
	}

	public void setRecommendUserCountDao(RecommendUserCountDao recommendUserCountDao) {
		this.recommendUserCountDao = recommendUserCountDao;
	}

	public static Log log = LogFactory.getLog(BrokerageIDRecognition.class);
	
	@Override
	public void updateBrokerage(Long userId, double sumMoney,Map<String,Object> param){
		try {
			int type = -1;
			if(param.containsKey("type")){
				type = Integer.parseInt(String.valueOf(param.get("type")));
			}
			if(type == IConstants.RECOMMEND_BROKERAGE_TYPE_REG){
				updateBrokerageForReg(userId, sumMoney);
			}else if(type == IConstants.RECOMMEND_BROKERAGE_TYPE_INVEST){
				Long repaymentId = Long.parseLong(String.valueOf(param.get("pid")));
				double annualRate = Double.parseDouble(String.valueOf(param.get("annualRate")));
				updateBrokerageForInvest(repaymentId,annualRate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateBrokerageForReg(long userId,double sumMoney) throws Exception{
		Map<String, String> setting = settingActivityService.getSettingActivity(IConstants.ACTIVITY_RECONMMEND);
		// 有推荐人 并且在活动期间 
		if(setting!= null && setting.size()>0 && setting.get("is_ongoing").equals("true")&&
				setting.get("process").equals("1")// 活动的第一阶段
				){
			
			Map<String,String> recommendPerson = recommendUserService.getRecommendUserByuserId((long)userId);
			Map<String,String> recommendBase =  null;
			if(recommendPerson != null){
				String type = "";
				if(!StringUtils.isNotBlank(type=recommendPerson.get("type")) || !type.equals("1")){
					log.info("无效的推广用户，不予发放奖励！");
				}else{
					// 判断推荐人是否已经领取了奖励
					Map<String,String> brokerageListMap = recommendBrokerageListService.queryBrokerage(String.valueOf(IConstants.RECOMMEND_BROKERAGE_TYPE_REG),
							Convert.strToLong(recommendPerson.get("recommendUserId"),0),Convert.strToLong(String.valueOf(userId),0),"1001");
					
					if(brokerageListMap!=null && brokerageListMap.size()>0){
						log.error("此用户的推荐人已经领取了奖励！");
					}else{
						recommendBase = recommendBrokerageListService.queryRecommendBase(Long.parseLong(recommendPerson.get("recommendUserId")));
					}
				}
			}
			
			if(recommendBase != null){
				long ret = recommendBrokerageListService.addBrokerage(sumMoney, IConstants.RECOMMEND_BROKERAGE_TYPE_REG,
						Integer.parseInt(recommendPerson.get("recommendUserId")), userId, "50元实名认证奖励",Long.parseLong(setting.get("user_id")),1);
				// 发送短信
				if(ret>0){
					Map<String,String> userMap = userService.queryUserById(userId);
					StringBuffer content = new StringBuffer();
					content.append("您的好友"+userMap.get("username")+"加入微信贷，50元已到账。");
					content.append("详情请登录微信贷官网");
					sMsService.sendSMSByConditions(Convert.strToLong(recommendPerson.get("recommendUserId"),-9), content.toString());
				}else{
					log.error("领取奖励失败！");
				}
			}else{
				log.info("没有注册奖励资格，不予发放奖励！");
			}
		}else{
			log.info("活动尚未开始/结束或者第一阶段已经过去！");
		}
	}
	
	private void updateBrokerageForInvest(Long repaymentId,double annualRate) throws Exception{
		//给推荐人发放奖励
		Map<String,Object> result = recommendBrokerageListService.addBrokerageForInvst(repaymentId, annualRate,"投资佣金");
		if(result.get("status").equals("1")){
			log.info("推荐活动-投资更新成功！");
		}else{
			log.error("推荐活动-投资更新失败：["+result.get("desc")+"]");
		}
	}
	
	/**
	 * 投资奖励处理方法
	 * @param conn
	 * @param userId
	 * @param investAmount
	 * @return
	 * @throws Exception
	 */
	public long processInvestReward(Connection conn,long userId,double investAmount,String investId) throws Exception{
		long result = 0;
		try {
			result = processInvestForRecommend( conn, userId, investAmount);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("推广返现奖励失败！");
		}
		
		try {
			result = processInvestForEndYear( conn, userId, investAmount,investId);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("年终推广活动奖励失败！");
		}
		
		return result;
	}
	
	/**
	 * 年终推广活动
	 * @param conn
	 * @param userId
	 * @param investAmount
	 * @return
	 * @throws Exception
	 */
	public long processInvestForEndYear(Connection conn,long userId,double investAmount,String investId)throws Exception{
		long result = 0;
		Map<String, String> setting = settingActivityService.getSettingActivity(IConstants.ACTIVITY_END_YEAR);
		if(setting!= null && setting.size()>0 && setting.get("is_ongoing").equals("true")){
			
			Map<String,Object> resultMap = recommendBrokerageListService.updateRecommendUserForEndYear(conn, investAmount, Convert.strToInt(IConstants.RECOMMEND_PROCESS_1, -1), (int)userId,investId);
			// 更新排名
			updateRecommendInvestAmount(userId,investAmount);
			
			// 投资返现奖励
			if(Convert.strToInt(String.valueOf(resultMap.get("status")), 0)>0){
				StringBuffer content = new StringBuffer();
				content.append(resultMap.get("out_notice_content"));
	            long retCode = sMsService.sendSMSByConditions(userId, content.toString());
	            if ("Sucess".equals(retCode)) {
	            	log.info("年终投资返现奖励成功发放！【CODE:"+resultMap.get("status")+",DESC:"+resultMap.get("desc")+"】");
	            }else{
	            	log.info("年终投资返现奖励成功发放！【CODE:"+resultMap.get("status")+",DESC:"+resultMap.get("desc")+"】");
	            }
			}else{
				log.info("年终投资返现奖励未成功发放！【CODE:"+resultMap.get("status")+",DESC:"+resultMap.get("desc")+"】");
			}
			
			// 首次投资达标后的奖励
			if(Convert.strToInt(String.valueOf(resultMap.get("status_ticket")), 0)>0){
				log.info("年终首次投资奖励！");
				StringBuffer content = new StringBuffer();
				content.append("恭喜获得50元代金券，电子兑换码为：");
				content.append(resultMap.get("ticket"));
				content.append("，请登录微信贷合作伙伴中粮我买网进行消费。");
//				暂时不即时发放礼品券
//	            long retCode = sMsService.sendSMSByConditions(userId, content.toString());
//	            if ("Sucess".equals(retCode)) {
//	            	log.info("礼品电子兑换码成功！");
//	            }else{
//	            	log.info("发送电子码错误：retCode:"+retCode);
//	            }
				log.info(content.toString());
			}else{
				log.info("年终投资返现奖励未成功发放！【CODE:"+resultMap.get("status_ticket")+"】");
			}
			
			
		}
		
		return result;
	}
	
	/**
	 * 推广返现活动
	 * @param conn
	 * @param userId
	 * @param investAmount
	 * @return
	 * @throws Exception
	 */
	public long processInvestForRecommend(Connection conn,long userId,double investAmount)throws Exception{
		Map<String, String> setting = settingActivityService.getSettingActivity(IConstants.ACTIVITY_RECONMMEND);
		// 在活动期间 
		if(setting!= null && setting.size()>0 && setting.get("is_ongoing").equals("true")){
			if(setting.get("process").equals(IConstants.RECOMMEND_PROCESS_1)){
				Map<String,Object> result = recommendUserCountDao.updateRecommendUserCount(conn,investAmount,Convert.strToInt(IConstants.RECOMMEND_PROCESS_1, 1),(int)userId);
				if(Convert.strToInt(String.valueOf(result.get("status")), 0)>0){
					log.info("投资获取机会成功！");
				}
				
				if(Convert.strToInt(String.valueOf(result.get("status_cinema")), 0)>0){
					log.info("电影票发放成功！");
					StringBuffer content = new StringBuffer();
					content.append("您的电影票兑换码为:");
					content.append(result.get("cinema_ticket"));
					content.append("，请登陆微信贷合作伙伴“蜘蛛网”进行在线选座");
		            long retCode = sMsService.sendSMSByConditions(userId, content.toString());
		            if ("Sucess".equals(retCode)) {
		            	log.info("发送电影票成功！");
		            }
				}else{
					log.info("电影票发放失败:"+result.get("status_cinema"));
				}
			}else if(setting.get("process").equals(IConstants.RECOMMEND_PROCESS_2)){
				Map<String,String> recommendPerson = recommendUserService.getRecommendUserByuserId((long)userId);
				
				Map<String,Object> result = recommendUserCountDao.updateRecommendUserCount(conn, investAmount,Convert.strToInt(IConstants.RECOMMEND_PROCESS_2, 1),(int)userId);
				if(Convert.strToInt(String.valueOf(result.get("status")), 0) != -1){
					if(Convert.strToInt(String.valueOf(result.get("status")), 0)>0){
						log.info("获得邀请机会成功！");
					}else{
						log.info("未获得邀请机会："+result.get("desc"));
					}
					
					if(recommendPerson != null && recommendPerson.get("type").equals("1")){
						// 1、电影票给被推荐人
						if(Convert.strToInt(String.valueOf(result.get("status_cinema")), 0)>0){
							log.info("电影票发放成功！");
							StringBuffer content = new StringBuffer();
							content.append("您的电影票兑换码为:");
							content.append(result.get("cinema_ticket"));
							content.append("，请登陆微信贷合作伙伴“蜘蛛网”进行在线选座");
				            long retCode = sMsService.sendSMSByConditions(userId, content.toString());
				            if ("Sucess".equals(retCode)) {
				            	log.info("发送电影票成功！");
				            }
						}else{
							log.info("电影票发放失败:"+result.get("status_cinema"));
						}
						
						// 2、50元奖励给推荐人
						List<Map<String,Object>> recommendList = recommendBrokerageListService.queryRecommendUserCount(conn, Convert.strToLong(recommendPerson.get("recommendUserId"),-1), userId);
						if(recommendList==null || recommendList.size()<=0){
							double investAmountTotal = 0f;
							
							Map <String,String> amountMap = recommendBrokerageListService.queryInvestAmountAtActivity(conn,userId);
							if(amountMap != null && amountMap.containsKey("amount")){
								investAmountTotal = Convert.strToDouble(amountMap.get("amount"), 0f);
							}
							if(investAmountTotal>=500){// 投资金额小于500不发放现金奖励
								long ret = recommendBrokerageListService.addBrokerage(IConstants.ACTIVITY_RECOMMEND_REG_AMOUNT, IConstants.RECOMMEND_BROKERAGE_TYPE_REG,
										Integer.parseInt(recommendPerson.get("recommendUserId")), userId, "50元实名认证奖励",Long.parseLong(setting.get("user_id")),1);
								// 发送短信
								if(ret>0){
									Map<String,String> userMap = userService.queryUserById(userId);
									StringBuffer content = new StringBuffer();
									content.append("您的好友"+userMap.get("username")+"加入微信贷，50元已到账。");
									content.append("详情请登录微信贷官网");
									sMsService.sendSMSByConditions(Convert.strToLong(recommendPerson.get("recommendUserId"),-9), content.toString());
								}else{
									log.error("领取奖励失败！");
								}
							}else{
								log.info("投资金额小于500，不予发放奖励！");
							}
						}else{
							log.info("已经领取过现金奖励，不予在此发放！");
						}
						
					}else{
						log.info("没有推荐人不奖励！");
					}
				}else{
					log.error("执行失败："+result.get("desc"));
				}
			}else{
				log.info("活动进行顺序不正确！see:t_setting_activity - process字段！");
			}
			
		}
		
		return 1;
	}
	
	private void updateRecommendInvestAmount(long userId,double amount) throws Exception{
		Map<String,String> map = recommendUserService.getRecommendUserByuserId(userId);
		//  更新推荐人的累计投资金额
		int ret = -1;
		if(map != null){
			long recommendUserId = Convert.strToLong(map.get("recommendUserId"), 0);
			ret = CacheRecommendUtils.getInstance().modifyAmount(map.get("recommendUserId"), "friendAmountTotal", amount);
			if(ret<0){
				Map<String,String> recommendTopMap = recommendUserService.queryMyTop(recommendUserId);
				if(recommendTopMap != null){
					Map<String,Object> convertRecommendTopMap = new HashMap<String, Object>();
					for(String key:recommendTopMap.keySet()){
						convertRecommendTopMap.put(key, recommendTopMap.get(key));
					}
					
					CacheRecommendUtils.getInstance().add(map.get("recommendUserId"), convertRecommendTopMap);
				}else{
					log.error("新增投资用户排名错误！");
				}
			}
		}
		
		// 更新自己的累计投资金额
		ret = CacheRecommendUtils.getInstance().modifyAmount(String.valueOf(userId), "myAmountTotal", amount);
		Map<String,String> topMap = recommendUserService.queryMyTop(userId);
		if(ret<0){
			if(topMap != null){
				Map<String,Object> converttopMap = new HashMap<String, Object>();
				for(String key:topMap.keySet()){
					converttopMap.put(key, topMap.get(key));
				}
				
				CacheRecommendUtils.getInstance().add(String.valueOf(userId), converttopMap);
			}else{
				log.error("新增投资用户排名错误！");
			}
		}
		
		// 修改返现金额和最后投资时间
		Map<String,Object> mMap =  CacheRecommendUtils.getInstance().getMap(String.valueOf(userId));
		if(mMap != null){
			AmountUtil au = new AmountUtil();
			String lastTime = String.valueOf(topMap.get("lastTime"));
			double rewardAmountTotal =  Convert.strToDouble(String.valueOf(topMap.get("rewardAmountTotal")), 0);
			mMap.put("lastTime", lastTime);
			mMap.put("rewardAmountTotal", au.getDf_two().format(rewardAmountTotal));
		}else{
			log.error("新增投资用户排名错误！");
		}
	}
	
	public SettingActivityService getSettingActivityService() {
		return settingActivityService;
	}

	public void setSettingActivityService(
			SettingActivityService settingActivityService) {
		this.settingActivityService = settingActivityService;
	}
	
	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public RecommendBrokerageListService getRecommendBrokerageListService() {
		return recommendBrokerageListService;
	}

	public void setRecommendBrokerageListService(
			RecommendBrokerageListService recommendBrokerageListService) {
		this.recommendBrokerageListService = recommendBrokerageListService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SMSInterfaceService getsMsService() {
		return sMsService;
	}

	public void setsMsService(SMSInterfaceService sMsService) {
		this.sMsService = sMsService;
	}

}
