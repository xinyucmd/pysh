/**
 * 
 */
package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.BonusDao;
import com.sp2p.dao.admin.RewardDao;

/**
 * @author David‎-RYE
 *
 */
public class BonusService extends BaseService{

	public static Log log = LogFactory.getLog(BonusService.class);
	private BonusDao bonusDao;
	private RewardDao rewardDao;
	
	public long queryBonusListCount(long userId,int flag)throws Exception {
		Connection conn = MySQL.getConnection();
		long result = 0;
		try {
			result = bonusDao.queryBonusListCount(conn,userId,flag);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public List<Map<String,Object>> queryBonusList(long userId,long start,int end,int flag)throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			//flag 1:红包奖励 2:6.24红包
			if(1 == flag){
				result = bonusDao.queryBonusList(conn,userId, start, end);
			}else {
				result = bonusDao.querySixBonusList(conn,userId, start, end);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 添加红包配置
	 * @return
	 * @throws Exception
	 */
	public Long addBonusConfig(int use_deadline, int reg_deadline, int invest_deadline,double reg_recommended_red, double reg_registration_red, double reg_registration_cash,
			double invest_proportion, double low_amount, double red_limit, String start_time, String end_time) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = bonusDao.addBonusConfig(conn, use_deadline, reg_deadline, invest_deadline, reg_recommended_red, 
					reg_registration_red, reg_registration_cash, invest_proportion, low_amount, red_limit, start_time, end_time);
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
	 * 添加红包金额区间
	 * @return
	 * @throws Exception
	 */
	public Long addBonusAmount(double min_invest_amount, double max_invest_amount, double red_value) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = bonusDao.addBonusAmount(conn, min_invest_amount, max_invest_amount, red_value);
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
	 * 查询红包金额区间
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBonusAmount() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list;
		try {
			list = bonusDao.queryBonusAmount(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 * 查询红包金额区间最大、最小id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> queryBonusAmountId() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String,String> map;
		try {
			map = bonusDao.queryBonusAmountId(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 *  根据id删除红包金额区间
	 * @return
	 * @throws Exception
	 */
	public Long deleteById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		
		Long result = -1L;
		try {
			result = bonusDao.deleteById(conn, id);
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
	 * 更新红包配置
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Long updateBonusConfig(int id, int super_id, int use_deadline, int reg_deadline, int recom_deadline, int invest_deadline,double reg_recommended_red, double reg_registration_red, double reg_registration_cash,
			double invest_proportion, double low_amount, double red_limit, String start_time, String end_time)throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = bonusDao.updateBonusConfig(conn, id, super_id, use_deadline, reg_deadline, recom_deadline, invest_deadline, reg_recommended_red, 
					reg_registration_red, reg_registration_cash, invest_proportion, low_amount, red_limit, start_time, end_time);
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
	 * 更新红包金额区间
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Long updateBonusAmount(int id, double min_invest_amount, double max_invest_amount, double red_value)	throws Exception {
		Connection conn = MySQL.getConnection();
		
		Long result = -1L;
		try {
			result = bonusDao.updateBonusAmount(conn, id, min_invest_amount, max_invest_amount, red_value);
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
	 * 添加红包奖励
	 * @return
	 * @throws Exception
	 */
	public Long addBonusList(long user_id, long from_user_id,
			double bonus_money, double bonus_avaliable, double bonus_used,int bonus_type,int status,String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = bonusDao.addBonusList(conn, user_id,from_user_id, bonus_money, bonus_avaliable,bonus_used,bonus_type,status,endTime);
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
	
	public Long addBonus_6_24(long user_id,double bonus_able)  throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			bonusDao.addBonus_6_24(conn, user_id, bonus_able);
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
	
	public Long addBonus_6_24(long user_id,double bonus_able,int type)  throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = bonusDao.addBonus_6_24(conn, user_id, bonus_able,type);
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
	 * 查询红包的配置
	 * @return
	 */
	public Map<String,String> queryBonusConfig()throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String,String> result = new HashMap<String,String>();
		try {
			result = bonusDao.queryBonusConfig(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Map<String,String> queryBonusListCount()throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String,String> result = new HashMap<String,String>();
		try {
			result = bonusDao.queryBonusListCount(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询红包总额等统计信息
	 * @return
	 */
	public Map<String,String> queryBonusSumCount(long userId,int flag)throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String,String> result = new HashMap<String,String>();
		try {
			if(1 == flag){
				result = bonusDao.queryBonusSumCount(conn, userId);
			}else {
				result = bonusDao.querySixBonusSumCount(conn, userId);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/***
	 * 我的体验金
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void queryMyTyj(PageBean<Map<String, Object>> pageBean,long userId)throws Exception {
		Connection conn = MySQL.getConnection();
		 
		try {
			bonusDao.queryMyTyj(conn, pageBean, userId);;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		 
	}
	
	public List<Map<String,Object>> queryActivityCi(long userId)throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>>  list = new ArrayList<Map<String,Object>>();
		try {
			list = bonusDao.queryActivityCi(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
		return list;
		 
	}
	
	
	public Map<String,String> queryBounsConfigAvaliable()throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String,String> result = new HashMap<String,String>();
		try {
			result = bonusDao.queryBounsConfigAvaliable(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public List<Map<String,Object>> queryBonusListForNothingCash()throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = bonusDao.queryBonusListForNothingCash(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Long updateBonusList(Long id,int cash_type)throws Exception{
		Connection conn = MySQL.getConnection();
		Long result = null;
		try {
			result = bonusDao.updateBonusList(conn,id, cash_type);
			if(result>0){
				conn.commit();
			}else{
				conn.rollback();
			}
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
	 * 查询未发放红包奖励的奖励
	 * @return
	 */
	public List<Map<String,Object>> queryUserTop()throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = bonusDao.queryUserTop(conn);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询投资但未收到红包的投资记录
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryInvestTop()throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = bonusDao.queryInvestTop(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Long updateRecommendBrokerage(long super_id,long user_id,double sumAmount,int type) throws Exception{
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			Map<String,Object> resultMap = bonusDao.updateRecommendAccount(conn, user_id,super_id,sumAmount, type);
			Long status = Convert.strToLong(String.valueOf(resultMap.get("status")), -1);
			if(status>-1){
				conn.commit();
				result = 1L;
			}else{
				conn.rollback();
			}
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
	
	public BonusDao getBonusDao() {
		return bonusDao;
	}

	public void setBonusDao(BonusDao bonusDao) {
		this.bonusDao = bonusDao;
	}

	public RewardDao getRewardDao() {
		return rewardDao;
	}

	public void setRewardDao(RewardDao rewardDao) {
		this.rewardDao = rewardDao;
	}
	
	
}
