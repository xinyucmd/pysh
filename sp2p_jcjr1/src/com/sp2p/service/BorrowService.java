package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RechargeDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.admin.BorrowManageService;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;

/**
 * @ClassName: FinanceService.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:14:21
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 借款业务处理层
 */
public class BorrowService extends BaseService {

	public static Log log = LogFactory.getLog(BorrowService.class);

	private BorrowDao borrowDao;
	private RechargeDao rechargeDao;
	private FundRecordDao fundRecordDao;
	private SelectedService selectedService;
	private BorrowManageService borrowManageService;
	private AccountUsersDao accountUsersDao;
	private UserDao userDao;
	private OperationLogDao operationLogDao;

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	/**
	 * @param i
	 * @throws DataException
	 * @MethodName: addBorrow
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午05:02:31
	 * @Return:
	 * @Descb: 添加借款业务处理
	 * @Throws:
	 */
	public Map<String,String> addBorrow(String title, String imgPath, int borrowWay,
			//TODO
			int purpose, int deadLine, int paymentMode, double amount,
			double annualRate, double minTenderedSum, double maxTenderedSum,
			int raiseTerm, int excitationType, double sum, String detail,
			int excitationMode, String investPWD, int hasPWD, String remoteIP,
			long publisher, double fee, int daythe, String basePath,
			String username, double smallestFlowUnit, int circulationNumber,
			int hasCirculationNumber, int subscribe_status, String nid_log,
			double frozen_margin, String json, String jsonState,String bonding_letter_path,long bonding_id,int isShowDyw, int isExclus,
			int isLimitMaxMoney,double maxMoneyValue,double add_interest)
			throws Exception {
		title = Utility.filteSqlInfusion(title);
		imgPath = Utility.filteSqlInfusion(imgPath);
		detail = Utility.filteSqlInfusion(detail);
		investPWD = Utility.filteSqlInfusion(investPWD);
		remoteIP = Utility.filteSqlInfusion(remoteIP);
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		nid_log = Utility.filteSqlInfusion(nid_log);
		json = Utility.filteSqlInfusion(json);
		jsonState = Utility.filteSqlInfusion(jsonState);
		bonding_letter_path = Utility.filteSqlInfusion(bonding_letter_path);
		
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			//流转标为认购模式
			int borrowShow = 1;
			if(borrowWay ==6 ){
				borrowShow = 2;
			}
			Procedures.p_borrow_initialization(conn, ds, outParameterValues,
					publisher, title, imgPath, borrowWay, "", deadLine,
					paymentMode, new BigDecimal(amount), new BigDecimal(
							annualRate), new BigDecimal(minTenderedSum),
					new BigDecimal(maxTenderedSum), new BigDecimal(raiseTerm),
					detail, 1, publisher, excitationType, new BigDecimal(sum),
					excitationMode, purpose, hasPWD, investPWD, new Date(),
					remoteIP, daythe, new BigDecimal(smallestFlowUnit),
					circulationNumber, nid_log, new BigDecimal(frozen_margin),
					"", basePath, new BigDecimal(fee), json, jsonState, "", "",
					"", "", "", borrowShow,bonding_letter_path,bonding_id,isShowDyw, isExclus, 
					isLimitMaxMoney,maxMoneyValue,add_interest,-1,-1,0, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			maps.put("ret", result + "");
			maps.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
			}else{
				userMap = userDao.queryUserById(conn, publisher);
				operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
				userMap.get("username"), ""), IConstants.INSERT, Convert
				.strToStr(userMap.get("lastIP"), ""), 0, "用户发布借款", 1);
				conn.commit();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return maps;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryBorrowConcernAppByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:45:15
	 * @Return:
	 * @Descb: app关注的借款列表查询
	 * @Throws:
	 */
	public void queryBorrowConcernAppByCondition(String title,
			String publishTimeStart, String publishTimeEnd, long userId,
			PageBean pageBean, String deadline, String borrowWay)
			throws Exception {
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		deadline = Utility.filteSqlInfusion(deadline);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(deadline)) {
			condition.append(" and deadline ='"
					+ StringEscapeUtils.escapeSql(deadline.trim()) + "'");
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay ='"
					+ StringEscapeUtils.escapeSql(borrowWay.trim()) + "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");
		}
		condition.append(" and userId =" + userId);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_concern", resultFeilds,
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryCreditingByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午05:13:17
	 * @Return:
	 * @Descb: 根据条件查询信用申请信息
	 * @Throws:
	 */
	public void queryCreditingByCondition(long userId, PageBean pageBean)
			throws Exception {
		String resultFeilds = " id,creditingName,applyAmount,applyDetail,status";
		StringBuffer condition = new StringBuffer();
		condition.append(" and applyer =" + userId);
		Connection conn = MySQL.getConnection();

		try {
			dataPage(conn, pageBean, " v_t_crediting_list", resultFeilds,
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryCreditingApply
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午07:53:11
	 * @Return:
	 * @Descb: 查询能够再次申请信用额度的记录
	 * @Throws:
	 */
	public Map<String, String> queryCreditingApply(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditingApply(conn, userId);
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
	 * @throws DataException
	 * @MethodName: addCrediting
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午04:29:23
	 * @Return:
	 * @Descb: 添加信用申请
	 * @Throws:
	 */
	public Long addCrediting(double applyAmount, String applyDetail, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = borrowDao.addCrediting(conn, applyAmount, applyDetail,
					userId);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_crediting", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发布额度申请", 1);
			}

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
	 * @MethodName: queryBorrowTypeNetValueCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-9 下午01:00:07
	 * @Return:
	 * @Descb: 查询用户的净值借款条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeNetValueCondition(long userId,
			double borrowSum) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		double amount = 0;
		// 待收金额
		double forpaySum = 0;
		// 待还金额
		double forRepaySum = 0;
		// 可用金额
		double usableSum = 0;

		try {
			// 待收借款
			Map<String, String> forpayBorrowMap = borrowDao.queryForpayBorrow(
					conn, userId);
			if (forpayBorrowMap == null) {
				forpayBorrowMap = new HashMap<String, String>();
			}
			forpaySum = Convert.strToDouble(forpayBorrowMap.get("forpaySum")
					+ "", 0);
			// 待还借款
			Map<String, String> forRePaySumMap = borrowDao.queryForRepayBorrow(
					conn, userId);
			if (forRePaySumMap == null) {
				forRePaySumMap = new HashMap<String, String>();
			}
			forRepaySum = Convert.strToDouble(forRePaySumMap.get("forRepaySum")
					+ "", 0);
			// 用户可用金额
			Map<String, String> userAmountMap = borrowDao.queryUserAmount(conn,
					userId);

			if (userAmountMap == null) {
				userAmountMap = new HashMap<String, String>();
			}
			usableSum = Convert.strToDouble(
					userAmountMap.get("usableSum") + "", 0);
			amount = usableSum + forpaySum - forRepaySum;
			// 净值金额大于10000才可以发布
			if (amount >= 10000) {
				if (borrowSum > 0) {
					// 发布借款的上限额
					amount = amount * 0.7;
					if (borrowSum <= amount) {
						map.put("result", "1");
					} else {
						map.put("result", "0");
					}
				} else {
					map.put("result", "1");
				}
			} else {
				map.put("result", "0");
			}
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
	 * @MethodName: queryBorrowTypeSecondsCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-9 下午01:01:16
	 * @Return:
	 * @Descb: 查询用户的秒还借款条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeSecondsCondition(
			double borrowAmount, double borrowAnnualRate, long userId,
			Map<String, Object> platformCostMap, double frozenMargin)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowTypeSecondsCondition(conn, borrowAmount,
					borrowAnnualRate, userId, platformCostMap, frozenMargin);

			conn.commit();
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

	public Map<String, Object> queryplatformCostLog(String identifier)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> mapObject = new HashMap<String, Object>();
		try {
			map = borrowDao.queryplatformCostLog(conn,identifier);
			mapObject.putAll(map);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return mapObject;
	}
	
	/**
	 * 查询用户可以资金是否小于保障金额
	 * 
	 * @param frozen
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowFinMoney(double frozen, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowFinMoney(conn, frozen, userId);
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
	 * @MethodName: refreshBorrowTime
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午10:58:33
	 * @Return:
	 * @Descb: 刷新借款时间
	 * @Throws:
	 */
	public Long refreshBorrowTime() throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		// 借款id
		long id = -1;
		// 用户id
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		String date = "";
		Map<String, String> maxTime = null;
		Map<String, String> minTime = null;
		Map<String, String> borrowStatus = null;
		try {
			List<Map<String, Object>> borrowList = borrowDao.queryBorrow(conn);
			for (Map<String, Object> borrowMap : borrowList) {
				date = sf.format(new Date());
				id = Convert.strToLong(borrowMap.get("id") + "", -1);
				// 当前时间小于剩余结束时间,剩余开始时间为当前时间
				maxTime = borrowDao.queryMaxTime(conn, id, date);
				if (maxTime != null && maxTime.size() > 0) {
					borrowDao.updateTime(conn, id, date);
				}
				// 当前时间大于剩余结束时间,剩余开始时间为剩余结束时间
				minTime = borrowDao.queryMinTime(conn, id, date);
				if (minTime != null && minTime.size() > 0) {
					// 借款总额等于投资总额,则为满标,否则流标
					borrowStatus = borrowDao.queryBorrowState(conn, id);
					if (borrowStatus != null && borrowStatus.size() > 0) {
						// 更新借款为满标 满标sorts 为 5
						borrowDao.updateBorrowState(conn, id,IConstants.BORROW_STATUS_3, 5);
					} else {
						// 更新借款为流标
						borrowManageService.reBackBorrow(conn,id, -1, IConstants.WEB_URL);
					}
				}
			}
			
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

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryBorrowConcernByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:45:15
	 * @Return:
	 * @Descb: 关注的借款列表查询
	 * @Throws:
	 */
	public void queryBorrowConcernByCondition(String title,
			String publishTimeStart, String publishTimeEnd, long userId,
			PageBean pageBean) throws Exception {
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");
		}
		condition.append(" and userId =" + userId);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_concern", resultFeilds,
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws DataException
	 * @MethodName: delBorrowConcern
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:24:56
	 * @Return:
	 * @Descb: 删除关注的借款
	 * @Throws:
	 */
	public void delBorrowConcern(long idLong, Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = borrowDao.delBorrowConcern(conn, idLong, userId);
			if (result < 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_concern", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.DELETE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "删除关注的借款", 1);
			}
			
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
	 * @MethodName: queryCreditLimit
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-25 下午10:09:55
	 * @Return:
	 * @Descb: 查询可用信用额度
	 * @Throws:
	 */
	public Map<String, String> queryCreditLimit(double amountDouble, Long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditLimit(conn, amountDouble, id);
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
	 * houli 查询是否有未满标审核的借款标的
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryBorrowStatus(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> lists;
		int count = 0;
		try {
			lists = borrowDao.queryBorrowStatus(conn, userId);
			
			if (lists == null || lists.size() <= 0) {// 如果没有该用户的借款信息，那么该用户可以发布借款
				return 1L;
			} else {
				for (Map<String, Object> map : lists) {
					int status = Convert.strToInt(map.get("borrowStatus").toString(), -1);
					if (status > 3) {// 如果该用户的借款标的已经满标审核通过
						count++;
					}
				}
				return count == lists.size() ? 1L : -1L;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * add by houli
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryBaseApprove(Long userId, int status) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = borrowDao.queryBaseApprove(conn, userId,
					status);
			
			if (map == null || map.size() <= 0) {
				return -1L;
			}
			return 1L;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long queryBaseFiveApprove(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = borrowDao.queryBaseFiveApprove(conn,
					userId);
			
			if (map == null || map.size() <= 0) {
				return -1L;
			}
			int status = Convert.strToInt(map.get("auditStatus"), -1);
			if (status < 15) {
				return -1L;
			}
			return 1L;
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
	 * @MethodName: addCirculationBorrow
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-5-17 下午05:06:07
	 * @Return:
	 * @Descb: 添加流转标借款
	 * @Throws:
	 */
	//TODO
	public long addCirculationBorrow(double bigestFlowUnit,String title, String imgPath,
			int borrowWay, int purposeInt, int deadLineInt, int paymentMode,
			double amountDouble, double annualRateDouble, String remoteIP,
			int circulationNumber, double smallestFlowUnitDouble, Long id,
			String businessDetail, String assets, String moneyPurposes,
			int dayThe, String basePath, String userName,
			int excitationTypeInt, double sumInt, String json,
			String jsonState, String nid, String agent, String counterAgent,
			double fee,String bonding_letter_path, int isExclus,
			int isLimitMaxMoney, double maxMoneyValue,double add_interest,long old_borrow_id,long old_invest_id) throws Exception {
		title = Utility.filteSqlInfusion(title);
		imgPath = Utility.filteSqlInfusion(imgPath);
		remoteIP = Utility.filteSqlInfusion(remoteIP);
		businessDetail = Utility.filteSqlInfusion(businessDetail);
		assets = Utility.filteSqlInfusion(assets);
		moneyPurposes = Utility.filteSqlInfusion(moneyPurposes);
		basePath = Utility.filteSqlInfusion(basePath);
		userName = Utility.filteSqlInfusion(userName);
		json = Utility.filteSqlInfusion(json);
		jsonState = Utility.filteSqlInfusion(jsonState);
		nid = Utility.filteSqlInfusion(nid);
		agent = Utility.filteSqlInfusion(agent);
		counterAgent = Utility.filteSqlInfusion(counterAgent);
		bonding_letter_path = Utility.filteSqlInfusion(bonding_letter_path);
		
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			Procedures.p_borrow_initialization(conn, ds, outParameterValues,
					id, title, imgPath, borrowWay, "", deadLineInt,
					paymentMode, new BigDecimal(amountDouble), new BigDecimal(
							annualRateDouble), new BigDecimal(-1),
					new BigDecimal(bigestFlowUnit), new BigDecimal(deadLineInt), "", 1, id,
					excitationTypeInt, new BigDecimal(sumInt), 1, purposeInt,
					-1, "", new Date(), remoteIP, dayThe, new BigDecimal(
							smallestFlowUnitDouble), circulationNumber, nid,
					new BigDecimal(fee), "", basePath, new BigDecimal(fee),
					json, jsonState, agent, counterAgent, businessDetail,
					assets, moneyPurposes, 2,bonding_letter_path,0,0, isExclus, 
					isLimitMaxMoney,maxMoneyValue,add_interest,old_borrow_id,old_invest_id,0, "");
			
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			maps.put("ret", result + "");
			maps.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			// 添加操作日志
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
					userMap.get("username"), ""), IConstants.INSERT, Convert
					.strToStr(userMap.get("lastIP"), ""), 0, "用户发布借款", 1);
			
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

	public RechargeDao getRechargeDao() {
		return rechargeDao;
	}

	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	/**
	 * @MethodName: queryCurrentCreditLimet
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-5-11 下午04:47:31
	 * @Return:
	 * @Descb: 查询当前可用信用额度
	 * @Throws:
	 */
	public Map<String, String> queryCurrentCreditLimet(Long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditLimit(conn, id);
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
	 * 查询小贷公司和担保公司的合作模式
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryModelType(Long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		 
		try {
			list = borrowDao.queryModelType(conn, id);
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
	 * 查询用户是否为合作机构旗下用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryUser(Long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryUser(conn, id);
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
	 * 根据合作机构adminId查询合作机构信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryLoan(Long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryLoan(conn, id);
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
	 * 根据loanId查询担保机构信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryBonding(Long id,long bondingId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBonding(conn, id,bondingId);
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
	 * 查询选择该担保公司用户的借款总额
	 * @param bondingId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryBorrowMoneySum(long bondingId,long loanId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowMoneySum(conn,bondingId,loanId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}
	
	public Map<String, String> queryBorrow(long borrowId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrow(conn,borrowId);
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
	 * 更新合作机构的的授信可用金额和授信已用金额
	 * @param id
	 * @param availableTotalAmountSum
	 * @param hasTotalAmountSum
	 * @throws Exception
	 */
	public  void updateLoanCompanyMoney(long id,double availableTotalAmountSum,double hasTotalAmountSum) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			borrowDao.updateLoanCompanyMoney(conn,id,availableTotalAmountSum,hasTotalAmountSum);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/***
	 * 查询满足预约条件的预约投标
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAppointInvest()throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		 
		try {
			list = borrowDao.queryAppointInvest(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	public Map<String, String> queryMaxHLId()throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		 
		try {
			map = borrowDao.queryMaxHLId(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public  void updateAppointInvest(long id,long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			borrowDao.updateAppointInvest(conn,id,borrowId);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
}
