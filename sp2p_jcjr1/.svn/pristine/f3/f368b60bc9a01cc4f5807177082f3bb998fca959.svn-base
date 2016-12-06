/**
 * 
 */
package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.admin.RewardDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.RecommendUserService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;

/**
 * @author David‎-RYE
 *
 */
public class RewardService extends BaseService {
	public static Log log = LogFactory.getLog(RewardService.class);
	private RewardDao rewardDao;
	private RecommendUserService recommendUserService;
	
	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	/**
	 *  根据id查询规则信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map;
		try {
			map = rewardDao.queryById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	/**
	 *  根据id查询规则信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryByRewardTime(String rewardType,String rewardTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map;
		try {
			map = rewardDao.queryByRewardTime(conn, rewardType,rewardTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	/**
	 * 查询加息的奖励（还款）列表
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryRewardInvestPlusInterest(String rewardTime) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = rewardDao.queryRewardInvestPlusInterest(conn,rewardTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 * 查询加息的奖励（还款）列表
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryRewardInvestPlusInterest(String rewardTime,String borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = rewardDao.queryRewardInvestPlusInterest(conn,rewardTime,borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 *  根据id删除规则信息
	 * @return
	 * @throws Exception
	 */
	public Long deleteById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		
		Long result = -1L;
		try {
			result = rewardDao.deleteById(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 *  推广奖励列表
	 * @return
	 * @throws Exception
	 */
	public void rewardSettingInfo(int rewardSrc, int userType, int rewardLevel, int giveWay, PageBean pageBean) throws Exception {
		
		StringBuffer condition = new StringBuffer();
		if (rewardSrc > 0) {
			condition.append(" and reward_src = " + rewardSrc);
		}
		if (userType > 0) {
			condition.append(" and user_type = " + userType);
		}
		if (rewardLevel > 0) {
			if(rewardLevel == 2){
				condition.append(" and  reward_level like '%" + rewardLevel + "%'");
			}else{
				condition.append(" and  reward_level like '%" + rewardLevel + ",'");
			}
		}
		if (giveWay > 0) {
			condition.append(" and  give_way = " + giveWay);
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_reward_setting as t", " IF(FIND_IN_SET('2',reward_level)> 0,2,1) as level_flag, t.* ",
					"", condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 统计奖励记录总额
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryRewardRecordTotal(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = rewardDao.queryRewardRecordTotal(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 * 奖励记录列表
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> rewardRecordInfoApp(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = rewardDao.rewardRecordInfoApp(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 * 奖励记录列表
	 * @param reward_time奖励时间  1：最近7天 2：最近1个月
	 * @return
	 * @throws Exception
	 */
	public void rewardRecordInfo(long userId, int reward_type, int reward_link, String timeStart, String timeEnd, PageBean pageBean) throws Exception {
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		StringBuffer condition = new StringBuffer();
		if (reward_type > 0) {
			condition.append(" and reward_type = " + reward_type);
		}
		if (reward_link > 0) {
			condition.append(" and reward_link = " + reward_link);
		}
		if (userId > 0) {
			condition.append(" and reward_user = " + userId);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and  reward_time  >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and  reward_time  <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		condition.append(" and reward_type !=6 ");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_reward_record ", " DATE_FORMAT(reward_time,'%Y-%m-%d') as rewardTime, ROUND(reward_amount,2) as rewardAmount,t_reward_record.* ",
					" order by rewardTime desc ", condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 添加奖励记录
	 * @return
	 * @throws Exception
	 */
	public Long addRewardRecord(int reward_type, double reward_amount, int reward_user, 
			int reward_link, String reward_time, String reward_remark) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = rewardDao.addRewardRecord(conn, reward_type, reward_amount, reward_user, 
					reward_link, reward_time, reward_remark,-999);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 添加奖励记录
	 * @return
	 * @throws Exception
	 */
	public Long addRewardRecord(int reward_type, double reward_amount, int reward_user, 
			int reward_link, String reward_time, String reward_remark,long invest_repayment_id) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = rewardDao.addRewardRecord(conn, reward_type, reward_amount, reward_user, 
					reward_link, reward_time, reward_remark,invest_repayment_id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 *  推广奖励添加
	 * @return
	 * @throws Exception
	 */
	public Long addRewardSetting(int reward_src, int user_type, double min_invest_amount, double max_invest_amount, int deadline_start, 
			int deadline_end, int deadline_unit, String borrow_type, String reward_level,int return_type,	double brokerage_one, double up_limit, 
			double down_limit, double brokerage_two, String start_time, String end_time, int give_way, int reward_valid,
			int reward_valid_unit,double amount_invest_sum, int reg_sum, int isopne) throws Exception {
		Connection conn = MySQL.getConnection();
		
		Long rewardId = -1L;
		try {
			rewardId = rewardDao.addRewardSetting(conn, reward_src, user_type, min_invest_amount, max_invest_amount, deadline_start, 
					deadline_end, deadline_unit, borrow_type, reward_level,return_type, brokerage_one, up_limit, down_limit, brokerage_two, 
					start_time, end_time, give_way, reward_valid, reward_valid_unit, amount_invest_sum, reg_sum, isopne);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return rewardId;
	}

	/**
	 * 更新配置
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Long updateRewardSetting(int id, int reward_src, int user_type, double min_invest_amount, double max_invest_amount, int deadline_start, 
			int deadline_end, int deadline_unit, String borrow_type, String reward_level,int return_type,	double brokerage_one, double up_limit, 
			double down_limit, double brokerage_two, String start_time, String end_time, int give_way, int reward_valid,
			int reward_valid_unit,double amount_invest_sum, int reg_sum, int isopne)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long rewardId = -1L;
		try {
			rewardId = rewardDao.updateRewardSetting(conn, id, reward_src, user_type, min_invest_amount, max_invest_amount, deadline_start, 
					deadline_end, deadline_unit, borrow_type, reward_level,return_type, brokerage_one, up_limit, down_limit, brokerage_two, 
					start_time, end_time, give_way, reward_valid, reward_valid_unit, amount_invest_sum, reg_sum, isopne);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return rewardId;
	}
	
	
	public List<Map<String,Object>> queryRecommendInvestBrokerage(String dateString) throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = rewardDao.queryRecommendInvestBrokerage(conn,dateString);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	
	public List<Map<String,Object>> queryRecommendInvestBrokerage(String dateString,String borrowId) throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = rewardDao.queryRecommendInvestBrokerage(conn,dateString,borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	
	public List<Map<String,Object>> queryByRewardSrc(int reward_src,String investTime) throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = rewardDao.queryByRewardSrc(conn,reward_src,investTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Map<String,Object> updateRecommendAccount(long user_id,long supper_user_id,double sumAmount,int type) throws Exception{
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_recommend_brokerage(conn, ds, outParameterValues, user_id, supper_user_id, sumAmount, type,-1, "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally{
			conn.close();
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", Convert.strToLong(outParameterValues.get(0) + "", -1));
		result.put("desc",outParameterValues.get(1) + "");
		return result;
	}
	
	
	public List<Map<String,Object>> queryRewardByRangeTime(String startTime,String endTime, long userId) throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> reuslt = null;
		try {
			reuslt = rewardDao.queryRewardByRangeTime(conn, startTime, endTime, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally{
			conn.close();
		}
		
		return reuslt;
	}
	
	/**
	 * 给推荐人发送奖励
	 * @param dateString
	 */
	public void provideRecommendInvestBrokerage(String dateString,String borrowId){
		// 获取当日还款记录
		List<Map<String,Object>> result = null;
		try {
			
			// 查询是否已经发送过
//			Map<String, String>  rewardMap = queryByRewardTime("5", dateString);
//			if(rewardMap!= null && !rewardMap.isEmpty()){
//				log.info("已经发送完毕，不能重复补发");
//				return;
//			}
			
			if(borrowId!=null && borrowId.trim().length()>0){
				result = queryRecommendInvestBrokerage(dateString,borrowId);
			}else{
				result = queryRecommendInvestBrokerage(dateString);
			}
			
			if(result != null && !result.isEmpty()){
				// 查询参数配置
				for(Map<String,Object> recode:result){
					// 利息
					double hasInterest = Convert.strToDouble(String.valueOf(recode.get("hasInterest")), -1);
					// 投资金额
					double investAmount = Convert.strToDouble(String.valueOf(recode.get("investAmount")), -1);
					// 年化收益率
					double annualRate = Convert.strToDouble(String.valueOf(recode.get("annualRate")), -1);
					// 投资时间
					String investTime = String.valueOf(recode.get("investTime"));
					// 奖励领取人
					long recommendUserId = Convert.strToLong(String.valueOf(recode.get("recommendUserId")), -1);
					
					//  根据投资时间获取有效的配置条目
					Map<String,Object> settingMap = getRewardSetting(investTime, investAmount);
					// 发放奖励
					if(settingMap != null){
						
						// 第一阶层奖金比例
						double brokerage_one = Convert.strToDouble(String.valueOf(settingMap.get("brokerage_one")), -1);
						
						// 第二阶层奖金比例
						double brokerage_two = Convert.strToDouble(String.valueOf(settingMap.get("brokerage_two")), -1);
						
						// 奖励上限
						double up_limit = Convert.strToDouble(String.valueOf(settingMap.get("up_limit")), -1);
						// 奖励下限
						double down_limit = Convert.strToDouble(String.valueOf(settingMap.get("down_limit")), -1);
						
						// 还款ID
						long invest_repayment_id = Convert.strToLong(String.valueOf(recode.get("invest_repayment_id")), -1);
						
						//超级账户ID,这里配置的是生产用户 weixindai1503
						long super_id = Convert.strToLong(IConstants.SUPER_ID, -1);
						
						// 加息
						double add_interest = Convert.strToDouble(String.valueOf(recode.get("add_interest")), -1);

						log.info("超级账户ID：==>"+super_id);
						
						// 处理加息标的推荐人返利
						// 加息标的推荐人返利，根据时间不同，活动的规则不同需要处理不同的情况
						log.info("加息比例：==>"+add_interest);
						log.info("投资时间：==>"+investTime);
						if(add_interest>0){
							// 9月份活动处理(1 是九月份投资， 0 非9月份投资)
							int sep_brokerage_one = Convert.strToInt(String.valueOf(recode.get("sep_brokerage_one")), 0);
							if(sep_brokerage_one==1){
								log.info("九月份活动，所有推荐奖励的比例均为%1");
								brokerage_one =  1;
							}
						}
						
						// 计算奖励金额
						double amount = getAmount(hasInterest,annualRate,brokerage_one,up_limit,down_limit);
						
						// 1、发放奖励
						doReward(amount,recommendUserId,super_id,dateString,invest_repayment_id);
						
						// 2、发放二层奖励
						Map<String,String> commendTwo = getRecommendByUserId(recommendUserId);
						if(commendTwo != null && !commendTwo.isEmpty()){
							long recommendUserIdTwo = Convert.strToLong(String.valueOf(commendTwo.get("recommendUserId")), -1);
							
							// 计算奖励金额
							double amountTwo = getAmount(hasInterest,annualRate,brokerage_two,up_limit,down_limit);
							doReward(amountTwo, recommendUserIdTwo, super_id,dateString,invest_repayment_id);
						}
						
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void provideRewardInvestPlusInterest(String dateString,String borrowId){
		// 获取当日还款记录
		List<Map<String,Object>> result = null;
		try {
			if(borrowId!=null && borrowId.trim().length()>0){
				result = queryRewardInvestPlusInterest(dateString,borrowId);
			}else{
				result = queryRewardInvestPlusInterest(dateString);
			}
			
			if(result != null && !result.isEmpty()){
				// 查询参数配置
				for(Map<String,Object> recode:result){
					// 利息
					double hasInterest = Convert.strToDouble(String.valueOf(recode.get("hasInterest")), -1);
					// 年化收益率
					double annualRate = Convert.strToDouble(String.valueOf(recode.get("annualRate")), -1);
					// 奖励领取人
					long owner = Convert.strToLong(String.valueOf(recode.get("owner")), -1);
					
					// 加息比例
					double add_interest = Convert.strToDouble(String.valueOf(recode.get("add_interest")), -1);
					
					// 还款ID
					long invest_repayment_id = Convert.strToLong(String.valueOf(recode.get("invest_repayment_id")), -1);
					
					//超级账户ID,这里配置的是生产用户 weixindai1503
					long super_id = Convert.strToLong(IConstants.SUPER_ID, -1);
					
					log.info("超级账户ID：============"+super_id);
					log.info("已收利息:"+hasInterest+",标的利率:"+annualRate+",加息："+add_interest);
					
					if(add_interest>0){
						// 计算奖励金额
						double amount = getAmountBase(hasInterest,annualRate,add_interest);
						
						// 1、发放奖励
						doRewardPlusInterest(amount,owner,super_id,invest_repayment_id);
					}else{
						log.info("===========没有加息标，直接跳过============");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	private Map<String,String> getRecommendByUserId(long userId) throws Exception{
		Map<String,String> result = recommendUserService.getRecommendUserByuserId(userId);
		
		return result;
	}
	
	public void doRewardPlusInterest(double amount,long userId,long super_id,long invest_repayment_id) throws Exception{
		
		// 判断是否已经发送过
		Connection conn = MySQL.getConnection();
		Map<String, String> map;
		try {
			map = rewardDao.queryByRepaymentId(conn, "6",String.valueOf(invest_repayment_id));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
		if(map== null || map.isEmpty()){
			// 发放奖励
			if(amount>0){
				Map<String,Object> resultMap = updateRecommendAccount(userId, super_id, amount, 6);
				long status = Convert.strToLong(String.valueOf(resultMap.get("status")), -1);
				if(status==1){
					log.info("**发放奖励成功！**");
					String rewardTime = DateUtil.dateToStringYYMMDD(new Date());
					// 记录日志表
					Long id = addRewardRecord(6, amount, (int)userId, -1, rewardTime, "7.6加息奖励",invest_repayment_id);
					if(id>0){
						log.info("插入日志成功！");
					}else{
						log.info("插入日志失败！");
					}
				}else{
					log.info("发放奖励失败！错误代码：["+status+"],描述："+resultMap.get("desc"));
				}
			}else{
				log.info("奖励金额不能为空");
			}
		}else{
			log.info("发放奖励失败！==>不能重复发放");
		}
	}
	
	
	private void doReward(double amount,long recommendUserId,long super_id,String rewardTime,long invest_repayment_id) throws Exception{
		// 判断是否已经发送过
		Connection conn = MySQL.getConnection();
		Map<String, String> map;
		try {
			map = rewardDao.queryByRepaymentId(conn, "5",String.valueOf(invest_repayment_id));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
		if(map== null || map.isEmpty()){
			// 发放奖励
			if(amount>0){
				Map<String,Object> resultMap = updateRecommendAccount(recommendUserId, super_id, amount, 2);
				long status = Convert.strToLong(String.valueOf(resultMap.get("status")), -1);
				if(status==1){
					log.info("发放奖励成功！");
					
					// 记录日志表
					Long id = addRewardRecord(5, amount, (int)recommendUserId, -1, rewardTime, "推荐单次投资奖励",invest_repayment_id);
					if(id>0){
						log.info("插入日志成功！");
					}else{
						log.info("插入日志失败！");
					}
				}else{
					log.info("发放奖励失败！错误代码：["+status+"],描述："+resultMap.get("desc"));
				}
			}
		}else{
			log.info("发放佣金奖励失败！==>不能重复发放");
		}
	}
	
	private double getAmountBase(double hasInterest,double annualRate,double rate){
		double result = 0;
		result = (hasInterest/annualRate)*rate;
		result = new AmountUtil().getTwoNumber(result);
		
		return result;
	}
	
	private double getAmount(double hasInterest,double annualRate,double brokerage_one,double up_limit,double down_limit){
		double result = 0;
		result = getAmountBase(hasInterest, annualRate, brokerage_one);
		if(result > up_limit){
			result = up_limit;
		}else if(result < down_limit){
			result = down_limit;
		}
		
		return result;
	}
	
	/**
	 * 获取配置
	 * @param investTime
	 * @param investAmount
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getRewardSetting(String investTime,double investAmount) throws Exception{
		Map<String,Object> settingMap = null;
		//  根据投资时间获取有效的配置条目
		List<Map<String,Object>> rewardSettings = queryByRewardSrc(3,investTime);
		if(rewardSettings != null && !rewardSettings.isEmpty()){
			settingBreak:
			for(Map<String,Object> item :rewardSettings){
				double min_invest_amount = Convert.strToDouble(String.valueOf(item.get("min_invest_amount")), -1);
				double max_invest_amount = Convert.strToDouble(String.valueOf(item.get("max_invest_amount")), -1);
				// 1、这里根据投资金额的设置定确定唯一发放奖励标准
				// 2、根据后续的奖励发放规则可多重判断...
				if(min_invest_amount<=investAmount && investAmount<=max_invest_amount){
					settingMap = item;
					break settingBreak;
				}
			}
		}
		
		return settingMap;
	}
	
	

	public RewardDao getRewardDao() {
		return rewardDao;
	}

	public void setRewardDao(RewardDao rewardSettingDao) {
		this.rewardDao = rewardSettingDao;
	}
}
