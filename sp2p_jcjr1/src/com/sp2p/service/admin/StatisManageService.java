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
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.admin.StatisManageDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.database.Dao.Views.intentionfund_user;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: AfterCreditManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:18:35
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 统计管理业务处理层
 */
public class StatisManageService extends BaseService {

	public static Log log = LogFactory.getLog(StatisManageService.class);

	private StatisManageDao statisManageDao;

	public StatisManageDao getStatisManageDao() {
		return statisManageDao;
	}

	public void setStatisManageDao(StatisManageDao statisManageDao) {
		this.statisManageDao = statisManageDao;
	}

	/**
	 * 根据面值查询未发送的体检卡(只查询一条)
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> unsendExamination(double amount) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.unsendExamination(conn, amount);
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
	 * 查询过期的体检卡
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryExpiredExamination() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = statisManageDao.queryExpiredExamination(conn);
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
	 * 补发之前未发送的用户
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryReissueUser() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = statisManageDao.queryReissueUser(conn);
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
	 * 待发送体检的用户
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryTobeSendUser() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = statisManageDao.queryTobeSendUser(conn);
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
	 * 7.6-8.6活动
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryInvestRank() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = statisManageDao.queryInvestRank(conn);
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
	 * 查询奖励总额
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryRewardListSum(String userName, String recommendUser, double rewardAmountDown, 
			double rewardAmountUp, int typeInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryRewardListSum(conn, userName, recommendUser, rewardAmountDown, rewardAmountUp, typeInt);
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
	 * 投标统计sum
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSum(String investor, String realName, int borrowWayInt,
			double sumAmountDown, double sumAmountUp, int groupInt,String timeStart,String timeEnd) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryInvestSum(conn, investor, realName,
					borrowWayInt, sumAmountDown, sumAmountUp, groupInt, timeStart, timeEnd);
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
	 * 投标排名sum
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSum(String investor,
			String timeStart, String timeEnd, int groupInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryInvestSum(conn, investor,	timeStart, timeEnd, groupInt);
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
	 * 投标记录sum
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSum(String flag, String bTitle, String investor,
			String timeStart, String timeEnd, int borrowWayInt,
			int isAutoBidInt, int deadlineInt, double sumAmountDown, double sumAmountUp, int groupInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryInvestSum(conn, flag,bTitle, investor,
					timeStart, timeEnd, borrowWayInt, isAutoBidInt, deadlineInt,
					sumAmountDown, sumAmountUp, groupInt);
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
	 * 投资记录sum
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSums(
			String timeStart, String timeEnd) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryInvestSums(conn, timeStart, timeEnd);
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
	 * 短信统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryMs(
			String timeStart, String timeEnd,String status) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryMs(conn, timeStart, timeEnd,status);
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
	 * 注册来源统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryRegSrc(
			String timeStart, String timeEnd,String status,int regSrc) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryRegSrc(conn, timeStart, timeEnd,status,regSrc);
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
	 * 实名认证统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryIdentify(
			String timeStart, String timeEnd,String status) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryIdentify(conn, timeStart, timeEnd,status);
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
	 * 发标统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryGiveBiao(String status, int borrowWayInt,String startTime, String endTime, int deadlineInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryGiveBiao(conn, status,borrowWayInt,
					startTime, endTime,deadlineInt);
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
	 * 投标统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> querytouBiao(String status, int borrowWayInt,String startTime, String endTime, int deadlineInt,int groupInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.querytouBiao(conn, status,borrowWayInt,
					startTime, endTime,deadlineInt,groupInt);
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
	 * 推荐明细查询推荐人数
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryRecommendDetailSum(long recommendUserId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = statisManageDao.queryRecommendDetailSum(conn, recommendUserId);
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
	 * 发送体检卡后修改t_activty_any状态
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long updateActivtyAnyState(long id,String end_time,long user_id, int flag)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			m = statisManageDao.updateActivtyAnyState(conn, id, end_time, user_id, flag);
			conn.commit();
		}catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	    return m;
	}
	
	/**
	 * 补发体检卡后修改t_examination状态
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long updateExamination(long id,int cardNo,int status,String remark)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			m = statisManageDao.updateExamination(conn, id, cardNo, status, remark);
			conn.commit();
		}catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return m;
	}
	
	/**
	 * 添加九华体检卡发送日志
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long addExamination(long user_id, double amount, int card_no, int status, String remark) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = statisManageDao.addExamination(conn, user_id, amount, card_no, status, remark);
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
	 * @throws DataException
	 * @MethodName: queryLoginStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:49:43
	 * @Return:
	 * @Descb: 查询登录统计
	 * @Throws:
	 */
	public void queryLoginStatisByCondition(String userName, String realName,
			String timeStart, String timeEnd, int countInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realname  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and lastDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and lastDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (countInt != -1) {
			condition.append(" and loginCount = " + countInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_login_statis", resultFeilds, " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 推荐明细列表
	 * @Throws:
	 */
	public void queryRecommendDetailList(long userId, String userName, String realName, String timeStart, String timeEnd, int typeInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer();
		
		if(userId > -1){
			condition.append(" and recommendUserId = " + userId);
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and createTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart.trim()) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and createTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd.trim()) + "'");
		}
		if (typeInt != -1) {
			condition.append(" and src = " + typeInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_recommend_user_info", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 投资明细列表
	 * @Throws:
	 */
	public void queryInvestDetailList(long userId, String userName, double investAmountDown, double investAmountUp, String timeStart, String timeEnd, 
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer();
		
		if(userId > -1){
			condition.append(" and recommendUserId = " + userId);
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (investAmountUp > 0) {
			condition.append(" and investAmount >= " + investAmountDown);
			condition.append(" and investAmount <= " + investAmountUp);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart.trim()) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd.trim()) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_detail", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 好友统计列表
	 * @Throws:
	 */
	public void queryRecomendRationList(String userName, String recommendUser, int typeInt, int flagInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		recommendUser = Utility.filteSqlInfusion(recommendUser);
		
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(recommendUser)) {
			condition.append(" and recommendUser like '%"
					+ StringEscapeUtils.escapeSql(recommendUser.trim()) + "%'");
		}
		if (typeInt != -1) {
			condition.append(" and type = " + typeInt);
		}
		//flagInt 2：五一活动
		if(2 == flagInt){
			condition.append(" and createTime >= (SELECT start_time from t_setting_activity where code = 1003)"
					+ "and createTime <= (SELECT end_time from t_setting_activity where code = 1003)");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_recommend_list", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 投资人奖励统计列表5.1
	 * @Throws:
	 */
	public void queryRewardList(String userName, String recommendUser, double rewardAmountDown, double rewardAmountUp,int typeInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		recommendUser = Utility.filteSqlInfusion(recommendUser);
		
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(recommendUser)) {
			condition.append(" and recommendUser like '%"
					+ StringEscapeUtils.escapeSql(recommendUser.trim()) + "%'");
		}
		if (rewardAmountUp > 0) {
			condition.append(" and amount >= " + rewardAmountDown);
			condition.append(" and amount <= " + rewardAmountUp);
		}
		if (typeInt > 0) {
			condition.append(" and type = " + typeInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_reward_list", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 推荐人奖励统计列表5.1
	 * @Throws:
	 */
	public void queryRecommendList(String userName, double rewardAmountDown, double rewardAmountUp, double rewardIssuedAmountDown, double rewardIssuedAmountUp,
			int typeInt, PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (rewardAmountUp > 0) {
			condition.append(" and amount >= " + rewardAmountDown);
			condition.append(" and amount <= " + rewardAmountUp);
		}
		if (rewardIssuedAmountUp > 0) {
			condition.append(" and issuedAmount >= " + rewardIssuedAmountDown);
			condition.append(" and issuedAmount <= " + rewardIssuedAmountUp);
		}
		if (typeInt > 0) {
			condition.append(" and type = " + typeInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_recommend_reward_list", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 奖励明细列表5.1
	 * @Throws:
	 */
	public void queryRewardDetailList(long userId, String userName, double rewardAmountDown, double rewardAmountUp, String timeStart, String timeEnd, 
			int flag, PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (rewardAmountUp > 0) {
			condition.append(" and amount >= " + rewardAmountDown);
			condition.append(" and amount <= " + rewardAmountUp);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and rewardTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart.trim()) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and rewardTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd.trim()) + "'");
		}
		if(userId > -1){
			condition.append(" and id = " + userId);
		}
		//flag：1 投资人奖励 2 推荐人奖励
		if(1 == flag){
			condition.append(" and fundMode like '%5.1活动奖励-投资%'");
		}else if(2 == flag){
			condition.append(" and fundMode like '%5.1活动奖励-推荐%'");
		}else {
			condition.append(" and fundMode like '%5.1活动奖励-投资%'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_reward_detail", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryInvestStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午09:33:22
	 * @Return:
	 * @Descb: 查询投资统计列表
	 * @Throws:
	 */
	public void queryInvestStatisByCondition(String investor, String realName, 	int borrowWayInt,
			double sumAmountDown, double sumAmountUp, int groupInt,String timeStart,String timeEnd,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		realName = Utility.filteSqlInfusion(realName);
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and  realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (borrowWayInt > 0) {
			if(7 == borrowWayInt){
				condition.append(" and isDebt = 2 ");
			}else {
				condition.append(" and isDebt = 1 ");
				condition.append(" and borrowWay = " + borrowWayInt);
			}
		}
		if (sumAmountUp > 0) {
			condition.append(" and realAmount <= " + sumAmountUp);
			condition.append(" and realAmount >= " + sumAmountDown);
		}
		if (groupInt > 0) {
			condition.append(" and groupid = " + groupInt);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_statis ", resultFeilds, " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @throws Exception
	 * @MethodName: investDetailList
	 * @Author: whb
	 * @Return:
	 * @Descb: 投标详情列表
	 * @Throws:
	 */
	public void queryInvestDetailByCondition(String flag, String bTitle, String investor,
			String timeStart, String timeEnd, int borrowWayInt,
			int isAutoBidInt, int deadlineInt, double sumAmountDown, double sumAmountUp, int groupInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		bTitle = Utility.filteSqlInfusion(bTitle);
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer();
		
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_statis_detail ", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 投资详情加推荐人的
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public void queryInvestDetailByConditions(
			String timeStart, String timeEnd,PageBean<Map<String, Object>> pageBean) throws Exception {
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		StringBuffer condition = new StringBuffer();
		
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest2 ", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 短信统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public void queryMsDetailByConditions(
			String timeStart, String timeEnd,String status,PageBean<Map<String, Object>> pageBean) throws Exception {
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		String currDateAgo = DateUtil.getCurrDateAgo().substring(0, 11);//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd().substring(0, 11);//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay().substring(0, 11);//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay().substring(0, 11);//本月第一天
		StringBuffer condition = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			condition.append(" and createTime >= '"+currDateAgo+"'"+" AND createTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			condition.append(" and  createTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append(" and  createTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			condition.append(" and createTime >= '"+timeStart+"'"+" AND createTime < '"+timeEnd.substring(0,10)+"'" );
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_smsrecord", " * ", "",condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 注册来源统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public void queryRegSrcDetailByConditions(
			String timeStart, String timeEnd,String status,int regSrc,PageBean<Map<String, Object>> pageBean) throws Exception {
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		String currDateAgo = DateUtil.getCurrDateAgo().substring(0, 11);//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd().substring(0, 11);//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay().substring(0, 11);//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay().substring(0, 11);//本月第一天
		StringBuffer condition = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			condition.append(" and createTime >= '"+currDateAgo+"'"+" AND createTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			condition.append(" and  createTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append(" and  createTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			condition.append(" and createTime >= '"+timeStart+"'"+" AND createTime < '"+timeEnd.substring(0,10)+"'" );
		}
		if(regSrc>=0){
			//0:'安卓',1:'苹果',2:'电脑'
			condition.append("  AND  regSrc='" + regSrc + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_regsrc", " * ", "",condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 实名认证统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public void queryIdentifyDetailByConditions(
			String timeStart, String timeEnd,String status,PageBean<Map<String, Object>> pageBean) throws Exception {
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		String currDateAgo = DateUtil.getCurrDateAgo().substring(0, 11);//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd().substring(0, 11);//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay().substring(0, 11);//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay().substring(0, 11);//本月第一天
		StringBuffer condition = new StringBuffer();
		
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			condition.append(" and createTime >= '"+currDateAgo+"'"+" AND createTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			condition.append(" and  createTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append(" and  createTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			condition.append(" and createTime >= '"+timeStart+"'"+" AND createTime < '"+timeEnd.substring(0, 10)+"'" );
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_identify ", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryInvestStatisRankByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:35:39
	 * @Return:
	 * @Descb: 查询投资排名列表
	 * @Throws:
	 */
	public void queryInvestStatisRankByCondition(String investor,
			String timeStart, String timeEnd, int groupInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		String resultFeilds = " a.*,b.realSum ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables.append(" v_t_invest_rank a left join (select investor,sum(realAmount) realSum from v_t_invest_rank where 1=1 ");
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and a.investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			tables.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and a.investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
			tables.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and a.investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
			tables.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (groupInt > 0) {
			condition.append(" and a.groupId = " + groupInt);
			tables.append(" and groupId = " + groupInt);
		}
		tables.append(" group by investor) b on a.investor = b.investor ");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds,
					" order by b.realSum desc,a.yearAmount desc ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午10:23:49
	 * @Return:
	 * @Descb: 借款管理费统计
	 * @Throws:
	 */
	public void queryBorrowStatisByCondition(String borrowTitle,
			String borrower, String timeStart, String timeEnd,
			int borrowWayInt, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		borrower = Utility.filteSqlInfusion(borrower);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(borrowTitle.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(borrower)) {
			condition.append(" and borrower  like '%"
					+ StringEscapeUtils.escapeSql(borrower.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and auditTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and auditTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			condition.append(" and borrowWay = " + borrowWayInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_statis ", resultFeilds, " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowStatisAmount
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:00:40
	 * @Return:
	 * @Descb: 查询借款统计总计
	 * @Throws:
	 */
	public Map<String, String> queryBorrowStatisAmount(String borrowTitle,
			String borrower, String timeStart, String timeEnd, int borrowWayInt)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryBorrowStatisAmount(conn, borrowTitle,
					borrower, timeStart, timeEnd, borrowWayInt);
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
	 * @MethodName: queryborrowStatisInterestByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:16:20
	 * @Return:
	 * @Descb: 投标借款管理费统计
	 * @Throws:
	 */
	public void queryborrowStatisInterestByCondition(String investor,
			String timeStart, String timeEnd,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		// String resultFeilds =
		// " a.id,a.investor,a.realName,round(b.manageFI,2) as manageFI,round(b.hasPI,2) as hasPI,round(b.manageFee,2) as manageFee, round(b.hasInterest,2) as hasInterest, round(b.forInterest,2) as forInterest,round(b.forPI,2) as forPI";
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables.append(" v_t_new_invest_statis a ");
		// tables
		// .append(" v_t_invest_interest_statis a left join (select investor,sum(manageFI) manageFI,sum(hasPI) hasPI,sum(manageFee ) manageFee ,");
		// tables
		// .append("sum(hasInterest) hasInterest,sum(forInterest) forInterest,sum(forPI) forPI   from v_t_invest_interest_statis where 1=1 ");
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and a.investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			// tables.append(" and investor like '%"
			// + StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and a.investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
			// tables.append(" and investTime >= '"
			// + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and a.investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
			// tables.append(" and investTime <= '"
			// + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		// tables.append(" group by investor) b on a.investor = b.investor");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds, "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// ........................................................................................................
	/**
	 * 查询用户组统计
	 * 
	 * @param groupName
	 *            查询条件 组名
	 * @param pageBean
	 *            分页
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryborrowStatisUserGroupByCondition(String groupName,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		groupName = Utility.filteSqlInfusion(groupName);

		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables.append(" ( select ifnull(sum(a.totalSum),0) totalSum,ifnull(sum(a.usableSum),0) usableSum,ifnull(sum(a.freezeSum),0) freezeSum,round(ifnull(sum(b.forPI),0),2) forPI,round(ifnull(sum(b.forInterest),0),2) forInterest,ifnull(sum(c.manageFee),0) manageFee,");
		tables.append(" ifnull(sum(d.vipFee),0) vipFee,ifnull(sum(e.hasPI),0) hasPI,ifnull(sum(f.realAmount),0) realAmount,g.groupId,h.groupName from");
		tables.append(" v_t_group_user_amount a left join v_t_group_for_amount b on a.userId = b.userId left join v_t_group_managefee c on a.userId = c.userId left join v_t_group_vip d ");
		tables.append(" on a.userId = d.userId left join v_t_has_amount e on a.userId=e.userId left join  (select sum(realAmount) realAmount,userId from v_t_invest_amount group by userId) f");
		tables.append(" on a.userId = f.userId left join t_group_user g on a.userId = g.userId left join t_group h on g.groupId =h.id");
		tables.append(" where groupId is not null group by g.groupId,h.groupName) t");
		if (StringUtils.isNotBlank(groupName)) {
			condition.append(" and t.groupName ='"
					+ StringEscapeUtils.escapeSql(groupName.trim()) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds,
					" order by groupId desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	// ......................................................................

	/**
	 * @MethodName: queryFinanceStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:17:37
	 * @Return:
	 * @Descb: 查询投资统计
	 * @Throws:
	 */
	public Map<String, String> queryFinanceEarnStatis(String timeStart,
			String timeEnd) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getFinanceEarnStatis(conn, ds, outParameterValues,
					timeStart, timeEnd);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
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
	 * @MethodName: queryWebStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:18:16
	 * @Return:
	 * @Descb: 查询网站统计
	 * @Throws:
	 */
	public Map<String, String> queryWebStatis() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getWebStatis(conn, ds, outParameterValues, -1);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public void changeNumToStr(PageBean<Map<String, Object>> pageBean) {
		List<Map<String, Object>> list = pageBean.getPage();
		if (list != null) {
			for (Map<String, Object> map : list) {
				if (Convert.strToStr(map.get("borrowWay") + "", "").equals(
						IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
						.equals(IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
						.equals(IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
						.equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
						.equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay",
							IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				}

				if (Convert.strToStr(map.get("isAutoBid") + "", "").equals("1")) {
					map.put("isAutoBid", "否");
				} else if (Convert.strToStr(map.get("isAutoBid") + "", "")
						.equals("2")) {
					map.put("isAutoBid", "是");
				}

				if (Convert.strToStr(map.get("borrowStatus") + "", "").equals(
						"4")) {
					map.put("borrowStatus", "是");
				} else if (Convert.strToStr(map.get("borrowStatus") + "", "")
						.equals("5")) {
					map.put("borrowStatus", "是");
				} else {
					map.put("borrowStatus", "否");
				}

			}
		}
	}

	/**
	 * 累计用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllRegiestUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllRegiestUser(conn);

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
	 * 累计安卓用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllARegiestUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllARegiestUser(conn);

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
	 * 累计苹果用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllIRegiestUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllIRegiestUser(conn);

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
	 * 累计PC用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllPRegiestUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllPRegiestUser(conn);

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
	 * 累计用户投资
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllInvestUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllInvestUser(conn);

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
	 * 累计用户充值
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllFundUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllFundUser(conn);

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
	 * 累计用户提现
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllRepay(int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllRepay(conn,ri);
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
	 * 真实用户还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllRepay1(int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllRepay1(conn,ri);

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
	 * 机构用户还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllRepay2(int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllRepay2(conn,ri);

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
	 * 线下理财人用户还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllRepay3(int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllRepay3(conn,ri);

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
	 * 提现统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllWithdrawUser() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllWithdrawUser(conn);

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
	 * 真实用户提现统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllWithdrawUser1() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllWithdrawUser1(conn);

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
	 * 机构用户提现统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllWithdrawUser2() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllWithdrawUser2(conn);

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
	 * 线下理财人提现统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllWithdrawUser3() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllWithdrawUser3(conn);

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
	 * 累计成交金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserUplineAndDownlineSum()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserUplineAndDownlineSum(conn);
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
	 * 所有投标统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllTouSum()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllTouSum(conn);
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
	 * 总发标统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAllGiveSum()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllGiveSum(conn);
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
	 * 累计线下理财
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserDownlineSum() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserDownlineSum(conn);
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
	 * 累计线上投资
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserUplineSum() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserUplineSum(conn);
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
	 * 累计机构投资
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserOrgSum() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserOrgSum(conn);
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
	 * 用户可用余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserAbleSum() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserAbleSum(conn);
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
	 * 新增注册用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewRegUser(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewRegUser(conn, status, startTime,
					endTime);
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
	 * 新增安卓注册用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewARegUser(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewARegUser(conn, status, startTime,
					endTime);
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
	 * 新增苹果注册用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewIRegUser(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewIRegUser(conn, status, startTime,
					endTime);
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
	 * 新增PC注册用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewPRegUser(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewPRegUser(conn, status, startTime,
					endTime);
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
	 * 新增提现金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewWithdraw(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewWithdraw(conn, status, startTime,
					endTime);
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
	 * 新增提现金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewWithdraw2(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewWithdraw1(conn, status, startTime,
					endTime);
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
	 * 新增提现金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewWithdraw3(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewWithdraw2(conn, status, startTime,
					endTime);
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
	 * 新增还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryNewRepay(String status, String startTime,
			String endTime,int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewRepay(conn, status, startTime,
					endTime,ri);
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
	 * 真实用户新增还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryNewRepay1(String status, String startTime,
			String endTime,int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewRepay1(conn, status, startTime,
					endTime, ri);
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
	 * 机构用户新增还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryNewRepay2(String status, String startTime,
			String endTime,int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewRepay2(conn, status, startTime,
					endTime,ri);
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
	 * 线下理财人新增统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryNewRepay3(String status, String startTime,
			String endTime,int ri) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewRepay3(conn, status, startTime,
					endTime,ri);
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
	 * 新增提现金额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewWithdraw1(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewWithdraw3(conn, status, startTime,
					endTime);
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
	 * 新增投资用户
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewInvestUser(String status,
			String startTime, String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewInvestUser(conn, status, startTime,
					endTime);
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
	 * 新增投资金额
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryNewInvestMoney(String status,
			String startTime, String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryNewInvestMoney(conn, status, startTime,
					endTime);
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
	 * 查询提现金额
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryTxMoney(String status, String startTime,
			String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao
					.queryTxMoney(conn, status, startTime, endTime);
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
	 * 新增机构投资
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserNewOrgSum(String status,
			String startTime, String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserNewOrgSum(conn, status,
					startTime, endTime);
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
	 * 新增线上投资
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserNewUpLineSum(String status,
			String startTime, String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserNewUpLineSum(conn, status,
					startTime, endTime);
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
	 * 新增线下理财
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAllUserNewDownLineSum(String status,
			String startTime, String endTime) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryAllUserNewDownLineSum(conn, status,
					startTime, endTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/***
	 * 查询统计详细
	 * 
	 * @param pageBean
	 * @param loanCompanyName
	 * @throws Exception
	 */
	public void sysBaseStatisDetail(PageBean<Map<String, Object>> pageBean,
			String status, String startTime, String endTime) throws Exception {

		Connection conn = MySQL.getConnection();
		try {
			statisManageDao.sysBaseStatisDetail(conn, pageBean, status,
					startTime, endTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryRecomendRationInit(PageBean<Map<String, Object>> pageBean,
			String userName, String reallyName, String mobilePhone)
			throws Exception {

		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer
				.append(" (select m.id,m.username,m.mobilePhone,m.realName,m.recomendSum  ,COUNT(*) AS recomendRzSum from  ");
		tableBuffer
				.append(" (select a.id,a.username,a.mobilePhone, c.realName,COUNT(*) AS recomendSum from t_user a ,t_recommend_user b ,t_person c where a.id = b.recommendUserId and a.id = c.userId ");
		tableBuffer.append(" GROUP BY a.username ORDER BY a.id DESC ) m, ");
		tableBuffer
				.append(" t_recommend_user n,t_person z where m.id = n.recommendUserId and n.userId = z.userId  GROUP BY m.id) p  ");
		tableBuffer
				.append(" LEFT JOIN t_recommend_user o ON p.id = o.recommendUserId  LEFT JOIN  t_invest u ON  o.userId = u.investor  ");

		StringBuffer resultFeilds = new StringBuffer();
		resultFeilds
				.append(" p.id,p.username,p.mobilePhone,p.realName,p.recomendSum,p.recomendRzSum, count(DISTINCT(u.investor)) as recomendTzSum, IFNULL(truncate(sum(u.investAmount),2),0) as recomendInvestAmount  ");

		StringBuffer condition = new StringBuffer();

		if (userName != null && !"".equals(userName)) {
			condition.append("  AND  p.username  like '%" + userName + "%'");
		}

		if (reallyName != null && !"".equals(reallyName)) {
			condition.append("  AND  p.realName  like '%" + reallyName + "%'");
		}

		if (mobilePhone != null && !"".equals(mobilePhone)) {
			condition.append(" AND  p.mobilePhone  = '" + mobilePhone + "'");
		}

		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tableBuffer.toString(),
					resultFeilds.toString(), " GROUP BY p.id ",
					condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public Map<String, String> queryRecomendRationHelp(String userName,
			String reallyName, String mobilePhone) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryRecomendRationHelp(conn, userName,
					reallyName, mobilePhone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public void queryRecomendRationDetail(
			PageBean<Map<String, Object>> pageBean, long id, String userName,
			String reallyName, String mobilePhone, String src, String time1,
			String time2) throws Exception {

		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer
				.append(" (select a.id,b.userId,b.src,c.username,c.mobilePhone, c.createTime,d.realName,d.idNo from t_user a, t_recommend_user b ,t_user c ,t_person d   ");
		tableBuffer
				.append(" WHERE a.id = b.recommendUserId and b.userId = c.id and b.userId = d.userId and a.id = "
						+ id);

		if (userName != null && !"".equals(userName)) {
			tableBuffer.append("  and c.username  like '%" + userName + "%'");
		}

		if (mobilePhone != null && !"".equals(mobilePhone)) {
			tableBuffer.append(" AND  c.mobilePhone  = '" + mobilePhone + "'");
		}

		if (reallyName != null && !"".equals(reallyName)) {
			tableBuffer.append("  AND  d.realName like '%" + reallyName + "%'");
		}

		if (src != null && !"".equals(src)) {
			tableBuffer.append(" AND b.src  = '" + src + "'");
		}
		if (time1 != null && !"".equals(time1)) {
			tableBuffer.append(" and c.createTime  >= '" + time1 + "'");
		}
		if (time2 != null && !"".equals(time2)) {
			tableBuffer.append(" and c.createTime  <= '" + time2 + "'");
		}

		tableBuffer
				.append(") m LEFT JOIN t_invest n on m.userId = n.investor ");

		StringBuffer resultFeilds = new StringBuffer();
		resultFeilds
				.append(" DISTINCT m.id,m.userId,m.src,m.username,m.mobilePhone, date_format(m.createTime,'%Y-%m-%d %H:%i:%s') as createTime,m.realName,m.idNo,n.investor  ");

		StringBuffer condition = new StringBuffer();

		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tableBuffer.toString(),
					resultFeilds.toString(), "", condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	/***
	 * 统计推广返现-现金
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMoneySendPage(PageBean<Map<String, Object>> pageBean,
			String username, String realName, String type, String time1,
			String time2) throws Exception {

		StringBuffer condition = new StringBuffer();
		if (username != null && !"".equals(username)) {
			condition.append("  AND  username  like '%" + username + "%'");
		}

		if (realName != null && !"".equals(realName)) {
			condition.append("  AND  realName  like '%" + realName + "%'");
		}

		if (type != null && !"".equals(type)) {
			condition.append(" AND  reward_type  = '" + type + "'");
		}
		if (time1 != null && !"".equals(time1)) {
			condition.append(" AND  reward_times  >= '" + time1 + "'");
		}
		if (time2 != null && !"".equals(time2)) {
			time2 = time2 + " 23:59:59";
			condition.append(" AND  reward_times  <= '" + time2 + "'");
		}

		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_reward_list", "*",
					" order by  reward_times desc", condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public List<Map<String, Object>> queryMoneySendAll() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = statisManageDao.queryMoneySendAll(conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 创建体检卡
	 * 
	 * @param values
	 * @param end_time
	 * @param temp
	 * @return
	 * @throws Exception
	 */
	public long createActivtyAny(double values, int temp, int day)
			throws Exception {

		Connection conn = MySQL.getConnection();
		long m = 0;
		try {
			for (int i = 0; i < temp; i++) {
				int aa = (int) (Math.random() * 9000 + 1000);
				int bb = (int) (Math.random() * 9000 + 1000);
				String str1 = String.valueOf(aa);
				String str2 = String.valueOf(bb);
				String cheeck_code = "jc" + str1 + str2;
				statisManageDao.addActivtyAny(conn, values, cheeck_code, day);
			}
			conn.commit();
			m = 1;

		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return m;
	}

	public void queryActivtyAny(PageBean<Map<String, Object>> pageBean,
			String check_code, String state, String start_time, String end_time)
			throws Exception {

		StringBuffer condition = new StringBuffer();

		if (check_code != null && !"".equals(check_code)) {
			condition.append("  AND  check_code  = '" + check_code + "'");
		}

		if (state != null && !"".equals(state)) {
			condition.append(" AND  state  = '" + state + "'");
		}
		if (start_time != null && !"".equals(start_time)) {
			condition.append(" AND  start_time  >= '" + start_time + "'");
		}
		if (end_time != null && !"".equals(end_time)) {
			condition.append(" AND  end_time  <= '" + end_time + "'");
		}

		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_activty_any_list", "*", "",
					condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public long updateActivtyAny(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			m = statisManageDao.updateActivtyAny(conn, id);
			conn.commit();
			m = 1;
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return m;
	}

	public List<Map<String, Object>> queryActivtyAnyExcel(String check_code,
			String state, String start_time, String end_time)
			throws SQLException, DataException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = MySQL.getConnection();

		try {
			list = statisManageDao.queryActivtyAnyExcel(conn, check_code,
					state, start_time, end_time);
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
	}
	/**
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List chart() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.chart(conn);
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
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List borrowChart() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart(conn);
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
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List borrowChart1() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart1(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	public List borrowChart11() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart11(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	public List borrowChart2() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart2(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	public List borrowChart3() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart3(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	public List borrowChart4() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart4(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	public List borrowChart5() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart5(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	public List borrowChart6() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.borrowChart6(conn);
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
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List investChart() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.investChart(conn);
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
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List regChart() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.regChart(conn);
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
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List regChart1() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.regChart1(conn);
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
	 * 图表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List regSourceChart() throws Exception {
		Connection conn = MySQL.getConnection();
		List result = null;
		try {
			result = statisManageDao.regSourceChart(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
}
