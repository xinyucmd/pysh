/**
 * 
 */
package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.AssignmentDebtDao;
import com.sp2p.dao.AuctionDebtDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.dao.admin.GroupDao;
import com.sp2p.dao.admin.RiskManageDao;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.DebtUtil;
import com.sp2p.util.WebUtil;

/**
 * 债权转让
 * 
 * @author Administrator
 * 
 */
public class AssignmentDebtService extends BaseService {
	public static Log log = LogFactory.getLog(AssignmentDebtService.class);

	private AssignmentDebtDao assignmentDebtDao;

	private UserDao userDao;

	private AuctionDebtDao auctionDebtDao;

	private RiskManageDao riskManageDao;

	private FundRecordDao fundRecordDao;

	private SelectedService selectedService;

	private FinanceDao financeDao;

	private InvestDao investDao;

	private AccountUsersDao accountUsersDao;

	private BorrowManageDao borrowManageDao;

	private OperationLogDao operationLogDao;
	
	private AdminDao adminDao;
	
	private UserService userService;

	private PlatformCostService platformCostService;
	
	// 转债相关
	private AssignmentDebtService assignmentDebtService;
	private AuctionDebtService auctionDebtService;
	private FinanceService financeService;
	
	private OperationLogService operationLogService;
	
	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public AuctionDebtService getAuctionDebtService() {
		return auctionDebtService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}


	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public PlatformCostService getPlatformCostService() {
		return platformCostService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}
	//whb添加用户组每月债转限额
	private GroupDao groupDao;
	

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	public void setAssignmentDebtDao(AssignmentDebtDao assignmentDebtDao) {
		this.assignmentDebtDao = assignmentDebtDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public void setAuctionDebtDao(AuctionDebtDao auctionDebtDao) {
		this.auctionDebtDao = auctionDebtDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}
	
	/**
	 * 系统自动撤销债权转让--还款计划表
	 * 
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getDebtInvest(long borrowId, long investId, int flag) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.getDebtInvest(conn, borrowId, investId, flag);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 系统自动撤销查询转让中债权
	 * 
	 * @param flag
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getDebtStatus(int flag) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = assignmentDebtDao.getDebtStatus(conn, flag);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 获取借款信息whb
	 * 
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getOnePay(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.getOnePay(conn, borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 获取投资信息whb
	 * 
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getInvest(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = investDao.getInvest(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询还款债权 信息
	 * @author 黑暗珊瑚
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getDebt(long borrowId,long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.queryDebtCoseInvestingMap(conn, investId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询债转后的价值
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryDebtCoseInvestedMap(long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.queryDebtCoseInvestedMap(conn,investId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Map<String, String> queryDebtMap(long aliUserId,long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.queryDebtMap(conn,aliUserId,borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	
	/**
	 * 查询还款债权
	 * 
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getDebtByConditions(long borrowId,long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.getDebtByConditions(conn, borrowId,investId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 添加债权转让
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public double checkTransMoney(long userId,double thisMoney)throws Exception {
		Connection conn = MySQL.getConnection();
		double result = -1;
		try {
			//用户组每月可转让金额
			double transMoney = Convert.strToDouble(assignmentDebtDao.getTransGroupMoney(conn, userId),0);
			//用户每月累计债转金额
			double debtSum = Convert.strToDouble(assignmentDebtDao.getTransUserMoney(conn, userId),0);
			//超出每月债转限额
			if(thisMoney > 0 && debtSum + thisMoney > transMoney){
				result = transMoney;
			}else{
				result = 1;
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}
	
	
	/**
	 * 添加债权转让
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAssignmentDebt(Map<String, String> paramMap)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1;
		try {
			if (!assignmentDebtDao.isHaveAssignmentDebt(conn, Convert
					.strToLong(paramMap.get("investId"), -1), Convert
					.strToLong(paramMap.get("alienatorId"), -1))) {

				Map<String,String> user_map = assignmentDebtDao.findUserById(conn, Convert.strToLong(paramMap.get("alienatorId"), -1));
				if(user_map!=null  && user_map.size()>0){
					paramMap.put("applyState", "1");
				}
				
				// 添加别名 
				Map<String,String> debtIndexMap = assignmentDebtDao.queryMMaxDebtIndex(conn,paramMap.get("publishTime"));
				String maxDebtIndexLable = "0001";
				String maxDebtIndex = "1";
				if(debtIndexMap !=null && !debtIndexMap.isEmpty()){
					maxDebtIndex = debtIndexMap.get("maxDebtIndex");
					int n = 4-maxDebtIndex.length();
					String appendZero = "";
					for(int i=0;i<n;i++){
						appendZero = "0"+appendZero;// 补零
					}
					if(appendZero!=null && appendZero.length()>0){
						maxDebtIndexLable = appendZero +maxDebtIndex;
					}
				}
				
				paramMap.put("debtIndex", maxDebtIndex);
				paramMap.put("debtIndexLable", maxDebtIndexLable);
				
				result = assignmentDebtDao.addAssignmentDebt(conn, paramMap);
				
				
				String borrowTitle = assignmentDebtDao.getBorrowTitle(conn,
						result);
				// 添加用户动态
				String cotent = "债权转让了借款<a href=queryDebtDetail.do?id="
						+ result + " target=_blank>" + borrowTitle + "</a>";
				financeDao.addUserDynamic(conn, Convert.strToLong(paramMap
						.get("alienatorId"), -1), cotent);
				userMap = userDao.queryUserById(conn, Convert.strToLong(
						paramMap.get("alienatorId"), -1));
				operationLogDao.addOperationLog(conn, "t_assignment_debt",
						Convert.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发布债权转让", 1);
				result = 1;
				
			}

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 添加债权转让
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateAssignmentDebt(long id, long debtStatus,
			Map<String, String> paramMap) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = assignmentDebtDao.updateAssignmentDebt(conn, id,
					debtStatus + "", paramMap);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 删除债权转让，可删除多个
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteAssignmentDebt(String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = assignmentDebtDao.deleteAssignmentDebt(conn, ids);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据ID获取债权转让信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getAssignmentDebt(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.getAssignmentDebt(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}
	
	public Map<String, String> findUserById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.findUserById(conn, id);
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
	 * 查询前台的全部债权
	 * 
	 * @param pageBean
	 * @throws SQLException
	 */
	public void queryAllDebt(String borrowTitle, long debtSum,	long isLate, 
			long publishDays, String debtStatus, PageBean pageBean)
			throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		debtStatus = Utility.filteSqlInfusion(debtStatus);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + debtStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" AND  debtStatus  in(");
			condition.append(idSQL);
			condition.append(") ");
		}

		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" AND  borrowTitle  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowTitle));
			condition.append("%' ");
		}
		if (debtSum > 0) {
			if (debtSum == 1) {
				condition.append(" AND  debtSum  < 3000 ");
			} else if (debtSum == 2) {
				condition
						.append(" AND  debtSum  >= 3000 and  debtSum  < 5000 ");
			} else if (debtSum == 3) {
				condition
						.append(" AND  debtSum  >= 5000 and  debtSum  < 10000 ");
			} else if (debtSum == 4) {
				condition
						.append(" AND  debtSum  >= 10000 and  debtSum  < 20000 ");
			} else if (debtSum == 5) {
				condition
						.append(" AND  debtSum  >= 20000 and  debtSum  < 50000 ");
			} else if (debtSum == 6) {
				condition.append(" AND  debtSum  >= 50000 ");
			}
		}
		
		condition.append(" AND  applyState  in('0','2')");
		if (isLate > 0) {
			condition.append(" AND  isLate  =");
			condition.append(isLate);
		}
		if (publishDays > 0) {
			if (publishDays > 30) {
				condition.append(" AND datediff(now(),publishTime) >= ");
				condition.append(publishDays);
			} else {
				condition.append(" AND datediff(now(),publishTime) <= ");
				condition.append(publishDays);
			}
		}

		try {
			dataPage(
					conn,
					pageBean,
					"v_t_debt_borrow_person",
					" id  ,investId, debtSum,debtPrice,actionTime  ,f_formatting_username( alienatorName) as alienatorName, "
					+ "debtLimit, alienatorId, publishTime,  debtStatus  ,  borrowId  , borrowTitle  ,publisher  , "
					+ "annualRate  , f_formatting_username(  borrowerName ) as borrowerName ,  "
					+ "auctionerName  ,  creditratingIco  ,  isLate  ,  personalHead,borrowWay,deadline,isDayThe,paymentMode,"
					+ "DATE_FORMAT( date_sub(DATE_FORMAT(v_t_debt_borrow_person.publishTime,'%Y-%m-%d'), interval -debtLimit DAY),'%y%m') as newBorrowTile  ",
					" order by debtStatus, publishTime desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询可以转让的借款
	 * 
	 * @param borrowTitle
	 * @param borrowerName
	 * @param pageBean
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryCanAssignmentDebt(String userNames, String debtAnnualRate,String debtDeadline,String eqsDeadline) throws Exception {
		
		Connection conn = MySQL.getConnection();
		
		try {
			// 计算债权,删除不符合条件的债权
			List<Map<String,Object>> list = assignmentDebtDao.queryCanAssignmentDebt(conn, userNames, debtAnnualRate, debtDeadline);
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				Map<String,Object> debtResultMap = coseDebtPrice(Convert.strToLong(String.valueOf(map.get("borrowId")), -1)
						,Convert.strToLong(String.valueOf(map.get("investId")),-1), "", debtAnnualRate);
				
				map.putAll(debtResultMap);
				String days = String.valueOf(map.get("days"));
				log.info(map.get("periodsBanlanceCount")+","+debtDeadline);
				
				if(StringUtils.isNotBlank(eqsDeadline) &&  Integer.parseInt(eqsDeadline)==0){
					if(Integer.parseInt(String.valueOf(map.get("periodsBanlanceCount")))!=Integer.parseInt(debtDeadline) 
							|| Convert.strToInt(days, 0)>0){
						list.remove(i);
						i--;
						continue;
					}
				}else{
					if(Integer.parseInt(String.valueOf(map.get("periodsBanlanceCount")))<Integer.parseInt(debtDeadline) 
							|| Convert.strToInt(days, 0)>0){
						list.remove(i);
						i--;
						continue;
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return null;
	}
	
	/**
	 * 查询可以转让的借款
	 * 
	 * @param borrowTitle
	 * @param borrowerName
	 * @param pageBean
	 * @throws SQLException
	 */
	public void queryCanAssignmentDebtForPage(String userNames, String debtAnnualRate,String debtDeadline,PageBean<Map<String,Object>> pageBean) throws Exception {
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(userNames)) {
			
			String[] userNameArr = userNames.split(",");
			userNames = "";
			for(String userName:userNameArr){
				userNames += "'"+userName+"',";
			}
			// 去掉最后的分隔符
			userNames = userNames.substring(0, userNames.length()-1);
			condition.append(" and  investor in (");
			condition.append("SELECT id from t_user where username in("+userNames+")");
			condition.append(") ");
		}
		
		condition.append(" and  borrowWay  in (3,4) ");
	
		try {
			dataPage(conn, pageBean, "v_t_can_assignment_borrow", "*", " order by investorName desc ",
					condition.toString());
			// 计算债权,删除不符合条件的债权
			List<Map<String,Object>> list = pageBean.getPage();
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				Map<String,Object> debtResultMap = coseDebtPrice(Convert.strToLong(String.valueOf(map.get("borrowId")), -1)
						,Convert.strToLong(String.valueOf(map.get("investId")),-1), "", debtAnnualRate);
				
				map.putAll(debtResultMap);
				log.info(map.get("periodsBanlanceCount")+","+debtDeadline);
				if(Integer.parseInt(String.valueOf(map.get("periodsBanlanceCount")))<Integer.parseInt(debtDeadline)){
					list.remove(i);
					i--;
					continue;
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 查询可以转让的借款
	 * 
	 * @param borrowTitle
	 * @param borrowerName
	 * @param pageBean
	 * @throws SQLException
	 */
	public void queryCanAssignmentDebt(long userId, String borrowTitle,
			String borrowerName, String borrowTypes, PageBean pageBean,int status) throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		borrowerName = Utility.filteSqlInfusion(borrowerName);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (userId > 0) {
			condition.append(" and  investor =");
			condition.append(userId);
			condition.append(" ");
		}
		if (StringUtils.isNotBlank(borrowerName)) {
			condition.append(" and  borrowerName  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowerName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and  borrowTitle  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowTitle));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(borrowTypes)) {
			condition.append(" and  borrowWay  in (");
			condition.append(StringEscapeUtils.escapeSql(borrowTypes));
			condition.append(") ");
		}
		
		if(status==0){//gjc
			condition.append(" and borrowWay!=4");
		}
		
		try {
			dataPage(conn, pageBean, "v_t_can_assignment_borrow", "*", "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	
	/**
	 * 竞拍中的债权
	 * 
	 * @param userId
	 * @param borrowTitle
	 * @param borrowerName
	 * @param pageBean
	 * @throws SQLException
	 */
	public void queryAuctingDebt(int flag, long userId, String borrowTitle,
			String borrowerName, String debtStatus, PageBean pageBean)
			throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		borrowerName = Utility.filteSqlInfusion(borrowerName);
		debtStatus = Utility.filteSqlInfusion(debtStatus);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (1 == flag) {
			condition.append(" and  userId =");
			condition.append(userId);
			condition.append(" ");
		}
		if (1 !=flag && userId > 0) {
			condition.append(" and  alienatorId =");
			condition.append(userId);
			condition.append(" ");
		}
		if (StringUtils.isNotBlank(borrowerName)) {
			condition.append(" and  borrowerName  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowerName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and  borrowTitle  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowTitle));
			condition.append("%' ");
		}

		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + debtStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" and  debtStatus  in(");
			condition.append(idSQL);
			condition.append(") ");
		}

		try {
			dataPage(conn, pageBean, "v_t_auction_assignmentdebt", "*", "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 申请转让中的债权
	 * 
	 * @param borrowerName
	 * @param alienatorName
	 * @param debtStatus
	 * @param pageBean
	 * @throws SQLException
	 */
	public void queryApplyDebt(String borrowerName, String alienatorName,
			String debtStatus, PageBean pageBean) throws Exception {
		borrowerName = Utility.filteSqlInfusion(borrowerName);
		alienatorName = Utility.filteSqlInfusion(alienatorName);
		debtStatus = Utility.filteSqlInfusion(debtStatus);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();

		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + debtStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" AND  debtStatus  in(");
			condition.append(idSQL);
			condition.append(") ");
		}
		if (StringUtils.isNotBlank(borrowerName)) {
			condition.append(" AND  borrowerName  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowerName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(alienatorName)) {
			condition.append(" AND  alienatorName  like '%");
			condition.append(StringEscapeUtils.escapeSql(alienatorName));
			condition.append("%' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_assignment_debt_audit", "*", "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 查询还款债权
	 * 
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryDebtBacking(long borrowId,
			long userId, long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = assignmentDebtDao.queryDebtBacking(conn, borrowId, userId,
					investId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 添加债务信息
	 * 
	 * @param id
	 * @param userId
	 * @param msgContent
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addDebtMsg(long id, Long userId, String msgContent)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long returnId = -1;
		try {
			returnId = assignmentDebtDao.addDebtMsg(conn, id, userId,
					msgContent);
			userMap = userDao.queryUserById(conn, userId);
			operationLogDao
					.addOperationLog(conn, "t_msgboard", Convert.strToStr(
							userMap.get("username"), ""), IConstants.INSERT,
							Convert.strToStr(userMap.get("lastIP"), ""), 0,
							"发布债权留言", 1);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return returnId;
	}

	/**
	 * 根据Id查询债权留言板
	 * 
	 * @param id
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryDebtMSGBord(long id, PageBean pageBean) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_debt_msgbord", " * ",
					" order by  id  desc ", " and  modeId =" + id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询债权借款信息
	 * 
	 * @param borrowerName
	 * @param alienatorName
	 * @param debtStatus
	 * @throws SQLException
	 */
	public void queryAssignmentDebt(String auctionName, String timeStart, String timeEnd,String borrowerName, String alienatorName,
			String debtStatus, PageBean pageBean) throws Exception {
		borrowerName = Utility.filteSqlInfusion(borrowerName);
		alienatorName = Utility.filteSqlInfusion(alienatorName);
		debtStatus = Utility.filteSqlInfusion(debtStatus);
		auctionName = Utility.filteSqlInfusion(auctionName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();

		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + debtStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" AND  debtStatus  in(");
			condition.append(idSQL);
			condition.append(") ");
		}
		if (StringUtils.isNotBlank(borrowerName)) {
			condition.append(" AND  borrowerName  like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowerName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(alienatorName)) {
			condition.append(" AND  alienatorName  like '%");
			condition.append(StringEscapeUtils.escapeSql(alienatorName));
			condition.append("%' ");
		}
		//whb添加债权转让手续费查询
		if (StringUtils.isNotBlank(auctionName)) {
			condition.append(" AND  auctionName  like '%");
			condition.append(StringEscapeUtils.escapeSql(auctionName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			timeStart = timeStart + " 00:00:00";
			condition.append(" AND createTime >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			timeEnd = timeEnd + " 23:59:59";
			condition.append(" AND createTime <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}

		try {
			//whb更换视图 v_admin_assignment_debt_borrow
			dataPage(conn, pageBean, "v_t_auction_assignmentdebt", "*",
					" order by createTime desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 结束债权转让
	 * 
	 * @throws SQLException
	 * @throws DataException
	 */
	public long auctingDebtSuccess(long debtId, long userId, int type, String debtValue)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1;
		try {
			if (assignmentDebtDao.isDebtInStatus(conn, debtId, "1,2")) {
				long dealResult = auctDebtSuccess(conn, debtId, debtValue);
				if (dealResult == 1) {
					if (type == 1) {
						userMap = userDao.queryUserById(conn, userId);
						operationLogDao.addOperationLog(conn,
								"t_assignment_debt", Convert.strToStr(userMap
										.get("username"), ""),
								IConstants.UPDATE, Convert.strToStr(userMap
										.get("lastIP"), ""), 0, "用户结束债权转让", 1);
					} else {
						// 查询后台管理员
						userMap = adminDao.queryAdminById(conn, userId);
						operationLogDao.addOperationLog(conn,
								"t_assignment_debt", Convert.strToStr(userMap
										.get("userName"), ""),
								IConstants.UPDATE, Convert.strToStr(userMap
										.get("lastIP"), ""), 0, "管理员结束债权转让", 2);
					}
					conn.commit();
					result = 1;
				} else {
					conn.rollback();
				}
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return result;
	}
	
	/**
	 * 查询所有竞拍者
	 * author cp
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> getAllauctionerId(long debtId) throws Exception{
		List<Map<String, Object>> auctionList = null;
		Connection conn = MySQL.getConnection();
		try {
			auctionList = auctionDebtDao.queryAuctionDebtByDebtId(conn,debtId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return auctionList;
	}

	/**
	 * 结束债权转让,更新转让人、投资人资金流和债转的状态
	 * @throws Exception
	 */
	private long auctDebtSuccess(Connection conn, long debtId, String debtValue) throws Exception {
		long result = -1;
		Map<String, String> debtMap = null;
		Date nowDate = new Date();
		Map<String, String> maxMap = null;
		Map<String, String> auctionerUserMap = null;
		Map<String, String> userMap = null;
		Map<String, String> fundRecordMap = null;
		Map<String, String> noticeMap = null;
		Map<String, String> investMap = null;
		Map<String, String> investHistoryMap = null;
		Map<String, String> investUpdateMap = null;
		Map<String, String> debtUpdateMap = null;
		Map<String, String> userAmountMap = null;
		Map<String, String> alienatorUserMap = null;
		Map<String, String> updateDebtMap = null;
		Map<String, String> feeMap = null;
		try {
			debtMap = assignmentDebtDao.getAssignmentDebt(conn, debtId);
			/**
			 * 转让人
			 */
			long alienatorId = Convert.strToLong(debtMap.get("alienatorId"), -1);
			/**
			 * 购买者
			 */
			maxMap = auctionDebtDao.queryAuctionMaxPriceAndCount(conn, debtId);
			// 拍卖者ID
			long auctionerId = Convert.strToLong(maxMap.get("userId"), -1);
			long investId = Convert.strToLong(debtMap.get("investId"), -1);
//			double debtSum = Convert.strToDouble(debtMap.get("debtSum"), 0.0);
			double debtPrice = Convert.strToDouble(maxMap.get("maxAuctionPrice"), 0.0);
			nowDate = new Date();
			String now = DateUtil.dateToString(nowDate);
			// 查询平台收费标准
			feeMap = platformCostService.queryPlatformCostById(IAmountConstants.CREDIT_TRANSFER_FEE_RATE);
			
			if (maxMap != null
					&& StringUtils.isNotBlank(maxMap.get("auctionCount"))
					&& !"0".equals(maxMap.get("auctionCount"))) {
				// 转让者扣除手续费
				double manageFee = debtPrice * Convert.strToDouble(feeMap.get("costFee"), 0) * 0.01;
				/* costManagerDao.getCostManagerNumberByType(conn, 7); */
				
				// 修改转让表
				updateDebtMap = new HashMap<String, String>();
				if(StringUtils.isNotBlank(debtValue)){
					updateDebtMap.put("debtSum", debtValue);
				}
				updateDebtMap.put("debtStatus", "2");
				updateDebtMap.put("manageFee", manageFee + "");
				long updateCount = assignmentDebtDao.updateAssignmentDebt(conn,
						debtId, "1", updateDebtMap);
				if (updateCount != 0) {
					result = 1;

					String basePath = WebUtil.getBasePath();

//					double maxPrice = Convert.strToDouble(maxMap
//							.get("maxAuctionPrice"), 0.0);

					// 更新投资还款记录是债权转让的状态
					investDao.updateInvestDebtStatus(conn, investId,
							auctionerId);

					// 操作投资表
					// 添加投资历史表
					investMap = investDao.getInvest(conn, investId);
					String date = DateUtil.dateToString(new Date());
					
					//whb 当债权购买人为原始投资人时不插入投资历史表
					investHistoryMap = new HashMap<String, String>();
					investHistoryMap.putAll(investMap);
					// investHistoryMap.remove("id");
					investHistoryMap.put("recivedPrincipal", investMap
							.get("hasPrincipal"));
					investHistoryMap.put("recievedInterest", investMap
							.get("hasInterest"));
					investHistoryMap.put("repayStatus", "2");
					investHistoryMap.put("recivedFI", investMap.get("hasFI"));
					investHistoryMap.put("manageFee", "0");
					investHistoryMap.put("investTime", date);
					
					investDao.addInvestHistory(conn, investHistoryMap);

					// 修改投资表
					investUpdateMap = new HashMap<String, String>();
					investUpdateMap.put("investor", auctionerId + "");
					investUpdateMap.put("hasPI", "0");

					double realAmount = (Convert.strToDouble(investMap
							.get("recivedPrincipal"), 0.0) - Convert
							.strToDouble(investMap.get("hasPrincipal"), 0.0));
					double recievedInterest = (Convert.strToDouble(investMap
							.get("recievedInterest"), 0.0) - Convert
							.strToDouble(investMap.get("hasInterest"), 0.0));
					//whb 更新投资时间和金额
					investUpdateMap.put("investTime", date);
					investUpdateMap.put("investAmount", debtPrice + "");
					investUpdateMap.put("realAmount", debtPrice + "");
					investUpdateMap.put("recivedPrincipal", realAmount + "");
					investUpdateMap.put("recievedInterest", recievedInterest
							+ "");
					investUpdateMap.put("hasPrincipal", "0");
					investUpdateMap.put("hasInterest", "0");
					investUpdateMap.put("isDebt", "2");
					investUpdateMap.put("manageFee", "0");
					investUpdateMap.put("recivedFI", (Convert.strToDouble(
							investMap.get("recivedFI"), 0.0) - Convert
							.strToDouble(investMap.get("hasFI"), 0.0))
							+ "");
					investUpdateMap.put("hasFI", "0");

					investDao.updateInvest(conn, investId, investUpdateMap);
					
					// 修改债权购买表
					debtUpdateMap = new HashMap<String, String>();
					debtUpdateMap.put("createTime", date);
					auctionDebtDao.updateAuctionDebt(conn, Convert.strToLong(String.valueOf(maxMap.get("id")), -1), debtUpdateMap);
					

					// 扣除竞拍者金额
					auctionerUserMap = auctionDebtDao.getUserById(conn,
							auctionerId);

					userMap = new HashMap<String, String>();
					double usableSum = Convert.strToDouble(auctionerUserMap
							.get("usableSum"), 0.0);
					double freezeSum = Convert.strToDouble(auctionerUserMap
							.get("freezeSum"), 0.0);
					userMap.put("freezeSum", (freezeSum - debtPrice) + "");
					userDao.updateUser(conn, auctionerId, userMap);

					// 竞拍者竞拍成功资金记录表
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							auctionerId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					String borrowTitle = assignmentDebtDao.getBorrowTitle(conn,
							debtId);

					String remark = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle + "</a>]购买成功扣除";
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", auctionerId + "");
					fundRecordMap.put("fundMode", "债权转让购买成功扣除");
					fundRecordMap.put("handleSum", debtPrice + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum",
							(Convert.strToDouble(userAmountMap.get("forPI"),
									0.0) - (realAmount + recievedInterest))
									+ "");
					fundRecordMap.put("trader", alienatorId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("dueoutSum","0");
					fundRecordMap.put("remarks", remark);
					fundRecordMap.put("spending", debtPrice+"");
					fundRecordMap.put("operateType", 726 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);

					String remarks = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle
							+ "</a>]成功,待收金额增加";
					// 债权转让成功待收增加
					auctionerUserMap = auctionDebtDao.getUserById(conn,
							auctionerId);
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", auctionerId + "");
					fundRecordMap.put("fundMode", "待收金额增加");
					fundRecordMap.put("handleSum",
							(realAmount + recievedInterest) + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
					fundRecordMap.put("trader", alienatorId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("dueoutSum","0");
					fundRecordMap.put("remarks", remarks);
					fundRecordMap.put("income", (realAmount + recievedInterest)+"");
					fundRecordMap.put("operateType", 1005 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);

					// 发送通知，通过通知模板
					Map<String, Object> informTemplateMap = getInformTemplate();

					noticeMap = new HashMap<String, String>();

					// 竞拍者消息提醒
					// 消息模版
					// 站内信
					String informTemplate = informTemplateMap.get(
							IInformTemplateConstants.SUCCESS_BID).toString();
					informTemplate = informTemplate.replace("date", DateUtil
							.dateToString((new Date())));
					informTemplate = informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + " target=_blank>" + borrowTitle
									+ "</a>");
					informTemplate = informTemplate.replace("money", debtPrice
							+ "");
					noticeMap.put("mail", informTemplate);

					// 邮件
					String e_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.E_SUCCESS_BID).toString();
					e_informTemplate = e_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					e_informTemplate = e_informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + ">" + borrowTitle + "</a>");
					e_informTemplate = e_informTemplate.replace("money",
							debtPrice + "");
					noticeMap.put("email", e_informTemplate);

					// 短信
					String s_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.S_SUCCESS_BID).toString();
					s_informTemplate = s_informTemplate.replace("userName",
							assignmentDebtDao.queryUserNameById(conn,
									auctionerId));
					s_informTemplate = s_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					s_informTemplate = s_informTemplate.replace("describe",
							borrowTitle);
					s_informTemplate = s_informTemplate.replace("money",
							debtPrice + "");
					noticeMap.put("note", e_informTemplate);

					selectedService.sendNoticeMSG(conn, auctionerId,
							"债权转让报告", noticeMap, IConstants.NOTICE_MODE_5);

					// 转让者待收金额减少资金记录
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							alienatorId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}

					String remark1 = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle
							+ "</a>]转让成功,待收金额减少";
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", alienatorId + "");
					fundRecordMap.put("fundMode", "待收金额减少");
					fundRecordMap.put("handleSum",
							(realAmount + recievedInterest) + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
					fundRecordMap.put("trader", auctionerId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("dueoutSum", "0");
					fundRecordMap.put("spending", (realAmount + recievedInterest) + "");
					fundRecordMap.put("remarks", remark1);
					fundRecordMap.put("operateType", 1003 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);

					// 添加转让者金额
					alienatorUserMap = auctionDebtDao.getUserById(conn,
							alienatorId);
					userMap = new HashMap<String, String>();
					usableSum = Convert.strToDouble(alienatorUserMap
							.get("usableSum"), 0.0);
					userMap.put("usableSum", (usableSum + debtPrice) + "");
					userDao.updateUser(conn, alienatorId, userMap);

					// 转让者转让成功资金记录
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							alienatorId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					String remark2 = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle + "</a>]转让成功收入";
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", alienatorId + "");
					fundRecordMap.put("fundMode", "债权转让成功");
					fundRecordMap.put("handleSum", debtPrice + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
					fundRecordMap.put("trader", auctionerId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("remarks", remark2);
					fundRecordMap.put("dueoutSum", "0");
					fundRecordMap.put("income", debtPrice + "");
					fundRecordMap.put("operateType", 201 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);

					alienatorUserMap = auctionDebtDao.getUserById(conn,
							alienatorId);
					userMap = new HashMap<String, String>();
					usableSum = Convert.strToDouble(alienatorUserMap
							.get("usableSum"), 0.0);
					userMap.put("usableSum", (usableSum - manageFee) + "");
					userDao.updateUser(conn, alienatorId, userMap);

					fundRecordMap = new HashMap<String, String>();
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							alienatorId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					if (manageFee > 0) {
						String remark3 = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle
							+ "</a>]转让手续费扣除";
						fundRecordMap.put("userId", alienatorId + "");
						fundRecordMap.put("fundMode", "债权转让手续费扣除");
						fundRecordMap.put("handleSum", manageFee + "");
						fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
						fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
						fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
						fundRecordMap.put("trader", "-1");
						fundRecordMap.put("recordTime", now);
						fundRecordMap.put("remarks", remark3);
						fundRecordMap.put("dueoutSum", "0");
						fundRecordMap.put("spending", manageFee + "");
						fundRecordMap.put("operateType", 701 + "");
						fundRecordDao.addFundRecord(conn, fundRecordMap);
					}

					// 转让者消息提醒
					noticeMap = new HashMap<String, String>();
					// 消息模版
					// 站内信
					informTemplate = informTemplateMap.get(
							IInformTemplateConstants.SUCCESS_CREDIT).toString();
					informTemplate = informTemplate.replace("date", DateUtil
							.dateToString((new Date())));
					informTemplate = informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + " target=_blank>" + borrowTitle
									+ "</a>");
					informTemplate = informTemplate.replace("money", debtPrice
							+ "");
					noticeMap.put("mail", informTemplate);

					// 邮件
					e_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.E_SUCCESS_CREDIT)
							.toString();
					e_informTemplate = e_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					e_informTemplate = e_informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + ">" + borrowTitle + "</a>");
					e_informTemplate = e_informTemplate.replace("money",
							debtPrice + "");
					noticeMap.put("email", e_informTemplate);

					// 短信
					s_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.S_SUCCESS_CREDIT)
							.toString();
					s_informTemplate = s_informTemplate.replace("userName",
							assignmentDebtDao.queryUserNameById(conn,
									alienatorId));
					s_informTemplate = s_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					s_informTemplate = s_informTemplate.replace("describe",
							borrowTitle);
					s_informTemplate = s_informTemplate.replace("money",
							debtPrice + "");
					noticeMap.put("note", e_informTemplate);

					selectedService.sendNoticeMSG(conn, alienatorId, "债权转让报告",
							noticeMap, IConstants.NOTICE_MODE_5);

					// 添加风险保障金
					Map<String, String> map = riskManageDao
							.queryRiskBalance(conn);
					String riskBalance = map.get("riskBalance");
					double riskBalanceDouble = Convert.strToDouble(
							riskBalance, 0);
					String riskType = "收入";
					String resource = "债权转让手续费";
					String remark_ = "债权手续费转让";
					riskManageDao.addRisk(conn, manageFee, -1, remark_,
							riskBalanceDouble, nowDate, riskType, resource);
					informTemplateMap = null;
				}
			} else {
				// 修改转让表
				updateDebtMap = new HashMap<String, String>();
				updateDebtMap.put("debtStatus", "4");
				long updateCount = assignmentDebtDao.updateAssignmentDebt(conn,
						debtId, "1", updateDebtMap);
				if (updateCount != 0) {
					result = 1;
				}
			}
			return result;
		} finally {
			debtMap = null;
			nowDate = null;
			maxMap = null;
			auctionerUserMap = null;
			userMap = null;
			fundRecordMap = null;
			noticeMap = null;
			investMap = null;
			investHistoryMap = null;
			investUpdateMap = null;
			userAmountMap = null;
			alienatorUserMap = null;
			updateDebtMap = null;
			System.gc();
		}
	}
	
	
	
	
	
	
	
	/**
	 * 结束债权转让(原来平台方法，已停用)
	 * 
	 * @throws Exception
	 */
	private long auctDebtSuccessOld(Connection conn, long debtId, String debtValue) throws Exception {
		long result = -1;
		Map<String, String> debtMap = null;
		Date nowDate = new Date();
		Map<String, String> maxMap = null;
		Map<String, String> auctionerUserMap = null;
		Map<String, String> userMap = null;
		Map<String, String> fundRecordMap = null;
		StringBuffer notice = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String, String> noticeMap = null;
		Map<String, String> investMap = null;
		Map<String, String> investHistoryMap = null;
		Map<String, String> investUpdateMap = null;
		List<Map<String, Object>> auctionList = null;
		Map<String, String> userAmountMap = null;
		Map<String, String> alienatorUserMap = null;
		Map<String, String> riskMap = null;
		Map<String, String> updateDebtMap = null;
		try {
			debtMap = assignmentDebtDao.getAssignmentDebt(conn, debtId);
			/**
			 * 转让人
			 */
			long alienatorId = Convert.strToLong(debtMap.get("alienatorId"), -1);
			long borrowId = Convert.strToLong(debtMap.get("borrowId"), -1);
			/**
			 * 购买者
			 */
			maxMap = auctionDebtDao.queryAuctionMaxPriceAndCount(conn, debtId);
			long auctionerId = Convert.strToLong(maxMap.get("userId"), -1);
			long investId = Convert.strToLong(debtMap.get("investId"), -1);
			double debtSum = Convert.strToDouble(debtMap.get("debtSum"), 0.0);
			double debtPrice = Convert.strToDouble(maxMap.get("maxAuctionPrice"), 0.0);
			nowDate = new Date();
			String now = DateUtil.dateToString(nowDate);
			// -- 7 - 9
			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> maps = borrowManageDao.queryBorrowInfo(conn,
					borrowId);
			// 得到收费标准的json代码
			String feelog = Convert.strToStr(maps.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject
					.toBean(JSONObject.fromObject(feelog), HashMap.class);
			// 得到收费标准的说明信息
			String feestate = Convert.strToStr(maps.get("feestate"), "");
			Map<String, String> feestateMap = (Map<String, String>) JSONObject
					.toBean(JSONObject.fromObject(feestate), HashMap.class);
			// --end
			if (maxMap != null
					&& StringUtils.isNotBlank(maxMap.get("auctionCount"))
					&& !"0".equals(maxMap.get("auctionCount"))) {
				// 转让者扣除手续费
				double manageFee = debtPrice
						* Convert.strToDouble(feeMap.get(IAmountConstants.CREDIT_TRANSFER_FEE_RATE)	+ "", 0);
				/* costManagerDao.getCostManagerNumberByType(conn, 7); */
				// 修改转让表
				updateDebtMap = new HashMap<String, String>();
				if(StringUtils.isNotBlank(debtValue)){
					updateDebtMap.put("debtSum", debtValue);
				}
				updateDebtMap.put("debtStatus", "2");
				updateDebtMap.put("manageFee", manageFee + "");
				long updateCount = assignmentDebtDao.updateAssignmentDebt(conn,
						debtId, "1", updateDebtMap);
				if (updateCount != 0) {
					result = 1;
					
					String basePath = WebUtil.getBasePath();
					
					long maxAuctionId = Convert.strToLong(maxMap.get("id"), -1);
					double maxPrice = Convert.strToDouble(maxMap
							.get("maxAuctionPrice"), 0.0);
					
					// 更新投资还款记录是债权转让的状态
					investDao.updateInvestDebtStatus(conn, investId,
							auctionerId);
					
					// 操作投资表
					// 添加投资历史表
					investMap = investDao.getInvest(conn, investId);
					investHistoryMap = new HashMap<String, String>();
					investHistoryMap.putAll(investMap);
					// investHistoryMap.remove("id");
					investHistoryMap.put("recivedPrincipal", investMap
							.get("hasPrincipal"));
					investHistoryMap.put("recievedInterest", investMap
							.get("hasInterest"));
					investHistoryMap.put("repayStatus", "2");
					investHistoryMap.put("recivedFI", investMap.get("hasFI"));
					investHistoryMap.put("manageFee", "0");
					
					investDao.addInvestHistory(conn, investHistoryMap);
					
					// 修改投资表
					investUpdateMap = new HashMap<String, String>();
					investUpdateMap.put("investor", auctionerId + "");
					investUpdateMap.put("hasPI", "0");
					
					double realAmount = (Convert.strToDouble(investMap
							.get("recivedPrincipal"), 0.0) - Convert
							.strToDouble(investMap.get("hasPrincipal"), 0.0));
					double recievedInterest = (Convert.strToDouble(investMap
							.get("recievedInterest"), 0.0) - Convert
							.strToDouble(investMap.get("hasInterest"), 0.0));
					investUpdateMap.put("realAmount", realAmount + "");
					investUpdateMap.put("recivedPrincipal", realAmount + "");
					investUpdateMap.put("recievedInterest", recievedInterest
							+ "");
					investUpdateMap.put("hasPrincipal", "0");
					investUpdateMap.put("hasInterest", "0");
					investUpdateMap.put("isDebt", "2");
					investUpdateMap.put("manageFee", "0");
					investUpdateMap.put("recivedFI", (Convert.strToDouble(
							investMap.get("recivedFI"), 0.0) - Convert
							.strToDouble(investMap.get("hasFI"), 0.0))
							+ "");
					investUpdateMap.put("hasFI", "0");
					
					investDao.updateInvest(conn, investId, investUpdateMap);
					
					// 扣除竞拍者金额
					auctionerUserMap = auctionDebtDao.getUserById(conn,
							auctionerId);
					
					userMap = new HashMap<String, String>();
					double usableSum = Convert.strToDouble(auctionerUserMap
							.get("usableSum"), 0.0);
					double freezeSum = Convert.strToDouble(auctionerUserMap
							.get("freezeSum"), 0.0);
					//cp修改
//					double dueinSum = Convert.strToDouble(auctionerUserMap
//							.get("dueinSum"), 0.0);
//					userMap.put("dueinSum", (dueinSum + debtSum) + "");
					userMap.put("freezeSum", (freezeSum - maxPrice) + "");
					userDao.updateUser(conn, auctionerId, userMap);
					
					// 竞拍者竞拍成功资金记录表
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							auctionerId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					String borrowTitle = assignmentDebtDao.getBorrowTitle(conn,
							debtId);
					
					String remark = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle + "</a>]购买成功扣除";
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", auctionerId + "");
					fundRecordMap.put("fundMode", "债权转让购买成功扣除");
					fundRecordMap.put("handleSum", maxPrice + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum",
							(Convert.strToDouble(userAmountMap.get("forPI"),
									0.0) - (realAmount + recievedInterest))
									+ "");
					fundRecordMap.put("trader", alienatorId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("dueoutSum","0");
					fundRecordMap.put("remarks", remark);
					fundRecordMap.put("spending", maxPrice+"");
					fundRecordMap.put("operateType", 726 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);
					
					String remarks = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle
							+ "</a>]成功,待收金额增加";
					// 债权转让成功待收增加
					auctionerUserMap = auctionDebtDao.getUserById(conn,
							auctionerId);
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", auctionerId + "");
					fundRecordMap.put("fundMode", "待收金额增加");
					fundRecordMap.put("handleSum",
							(realAmount + recievedInterest) + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
					fundRecordMap.put("trader", alienatorId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("dueoutSum","0");
					fundRecordMap.put("remarks", remarks);
					fundRecordMap.put("income", debtSum+"");
					fundRecordMap.put("operateType", 1005 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);
					
					// 发送通知，通过通知模板
					Map<String, Object> informTemplateMap = getInformTemplate();
					
					noticeMap = new HashMap<String, String>();
					
					// 竞拍者消息提醒
					// 消息模版
					// 站内信
					String informTemplate = informTemplateMap.get(
							IInformTemplateConstants.SUCCESS_BID).toString();
					informTemplate = informTemplate.replace("date", DateUtil
							.dateToString((new Date())));
					informTemplate = informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + " target=_blank>" + borrowTitle
									+ "</a>");
					informTemplate = informTemplate.replace("money", maxPrice
							+ "");
					noticeMap.put("mail", informTemplate);
					
					// 邮件
					String e_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.E_SUCCESS_BID).toString();
					e_informTemplate = e_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					e_informTemplate = e_informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + ">" + borrowTitle + "</a>");
					e_informTemplate = e_informTemplate.replace("money",
							maxPrice + "");
					noticeMap.put("email", e_informTemplate);
					
					// 短信
					String s_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.S_SUCCESS_BID).toString();
					s_informTemplate = s_informTemplate.replace("userName",
							assignmentDebtDao.queryUserNameById(conn,
									auctionerId));
					s_informTemplate = s_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					s_informTemplate = s_informTemplate.replace("describe",
							borrowTitle);
					s_informTemplate = s_informTemplate.replace("money",
							maxPrice + "");
					noticeMap.put("note", e_informTemplate);
					
					selectedService.sendNoticeMSG(conn, auctionerId,
							"债权转让报告", noticeMap, IConstants.NOTICE_MODE_5);
					
					/**
					 *  解除其它未中拍的竞拍者资金
					auctionList = auctionDebtDao.queryAuctionDebtByDebtId(conn,
							debtId);
					Map<Long, Double> frezeMap = new HashMap<Long, Double>(); // 记录解冻的资金
					for (Map<String, Object> map : auctionList) {
						long id = Convert.strToLong(map.get("id") + "", -1);
						long userId = Convert.strToLong(map.get("userId") + "",
								-1);
						if (id != maxAuctionId && userId != auctionerId) {
							auctionerUserMap = auctionDebtDao.getUserById(conn,
									userId);
							userMap = new HashMap<String, String>();
							usableSum = Convert.strToDouble(auctionerUserMap
									.get("usableSum"), 0.0);
							freezeSum = Convert.strToDouble(auctionerUserMap
									.get("freezeSum"), 0.0);
							double auctionPrice = Convert.strToDouble(map
									.get("auctionPrice")
									+ "", 0.0);
							// 防止解冻重复解冻金额
							if (frezeMap.containsKey(userId)) {
								double oldAcutionPrice = frezeMap.get(userId);
								if (oldAcutionPrice >= auctionPrice) {
									continue;
								} else {
									frezeMap.put(userId, auctionPrice);// 记录该用户最大竞拍值
									auctionPrice = auctionPrice
											- oldAcutionPrice;
								}

							} else {
								frezeMap.put(userId, auctionPrice);// 记录该用户竞拍
							}
							userMap.put("usableSum", (usableSum + auctionPrice)
									+ "");
							userMap.put("freezeSum", (freezeSum - auctionPrice)
									+ "");
							if (userDao.updateUser(conn, userId, userMap) > 0) {
								// 解冻资金操作记录
								userAmountMap = financeDao
										.queryUserAmountAfterHander(conn,
												userId);
								if (userAmountMap == null) {
									userAmountMap = new HashMap<String, String>();
								}
								String rms = "债权转让[<a href=" + basePath
										+ "/queryDebtDetail.do?id=" + debtId
										+ " target=_blank>" + borrowTitle
										+ "</a>]竞拍失败解冻金额";
								double usableSumAfter = Convert.strToDouble(
										userAmountMap.get("usableSum") + "", 0);
								double freezeSumAfter = Convert.strToDouble(
										userAmountMap.get("freezeSum") + "", 0);
								double forPI = Convert.strToDouble(
										userAmountMap.get("forPI") + "", 0);
								fundRecordDao.addFundRecord(conn, userId,
										"债权转让竞拍解冻", auctionPrice,
										usableSumAfter, freezeSumAfter, forPI,
										-1, rms, auctionPrice, 0.0, borrowId,
										-1, 202,0.0);

								// 竞拍者消息提醒
								notice = new StringBuffer();

								noticeMap = new HashMap<String, String>();

								// 消息模版
								// 站内信
								informTemplate = informTemplateMap.get(
										IInformTemplateConstants.FAIL_BID)
										.toString();
								informTemplate = informTemplate.replace("date",
										DateUtil.dateToString((new Date())));
								informTemplate = informTemplate.replace(
										"describe", "<a href=" + basePath
												+ "/queryDebtDetail.do?id="
												+ debtId + " target=_blank>"
												+ borrowTitle + "</a>");
								informTemplate = informTemplate.replace(
										"money", auctionPrice + "");
								noticeMap.put("mail", informTemplate);

								// 邮件
								e_informTemplate = informTemplateMap.get(
										IInformTemplateConstants.E_FAIL_BID)
										.toString();
								e_informTemplate = e_informTemplate.replace(
										"date", DateUtil
												.dateToString((new Date())));
								e_informTemplate = e_informTemplate.replace(
										"describe", "<a href='" + basePath
												+ "/queryDebtDetail.do?id="
												+ debtId + "' target='_blank'>"
												+ borrowTitle + "</a>");
								e_informTemplate = e_informTemplate.replace(
										"money", auctionPrice + "");
								noticeMap.put("email", e_informTemplate);

								// 短信
								s_informTemplate = informTemplateMap.get(
										IInformTemplateConstants.S_FAIL_BID)
										.toString();
								s_informTemplate = s_informTemplate.replace(
										"userName",
										assignmentDebtDao.queryUserNameById(
												conn, userId));
								s_informTemplate = s_informTemplate.replace(
										"date", DateUtil
												.dateToString((new Date())));
								s_informTemplate = s_informTemplate.replace(
										"describe", borrowTitle);
								s_informTemplate = s_informTemplate.replace(
										"money", auctionPrice + "");
								noticeMap.put("note", e_informTemplate);

								selectedService.sendNoticeMSG(conn, userId,
										"债权转让报告", noticeMap,
										IConstants.NOTICE_MODE_5);
							}
						}
						// 回收对象
						map = null;
					}
					 */
					
					// 转让者待收金额减少资金记录
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							alienatorId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					
					String remark1 = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle
							+ "</a>]转让成功,待收金额减少";
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", alienatorId + "");
					fundRecordMap.put("fundMode", "待收金额减少");
					fundRecordMap.put("handleSum",
							(realAmount + recievedInterest) + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
					fundRecordMap.put("trader", auctionerId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("dueoutSum", "0");
					fundRecordMap.put("remarks", remark1);
					fundRecordMap.put("operateType", 1003 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);
					
					// 添加转让者金额
					alienatorUserMap = auctionDebtDao.getUserById(conn,
							alienatorId);
					userMap = new HashMap<String, String>();
					usableSum = Convert.strToDouble(alienatorUserMap
							.get("usableSum"), 0.0);
					userMap.put("usableSum", (usableSum + maxPrice) + "");
					//cp修改
//					dueinSum = Convert.strToDouble(alienatorUserMap
//							.get("dueinSum"), 0.0);
//					userMap.put("dueinSum", (dueinSum - debtSum) + "");
					userDao.updateUser(conn, alienatorId, userMap);
					
					// 转让者转让成功资金记录
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							alienatorId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					String remark2 = "债权转让[<a href=" + basePath
							+ "/queryDebtDetail.do?id=" + debtId
							+ " target='_blank'>" + borrowTitle + "</a>]转让成功收入";
					fundRecordMap = new HashMap<String, String>();
					fundRecordMap.put("userId", alienatorId + "");
					fundRecordMap.put("fundMode", "债权转让成功");
					fundRecordMap.put("handleSum", maxPrice + "");
					fundRecordMap.put("usableSum", userAmountMap
							.get("usableSum"));
					fundRecordMap.put("freezeSum", userAmountMap
							.get("freezeSum"));
					fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
					fundRecordMap.put("trader", auctionerId + "");
					fundRecordMap.put("recordTime", now);
					fundRecordMap.put("remarks", remark2);
					fundRecordMap.put("dueoutSum", "0");
					fundRecordMap.put("income", maxPrice + "");
					fundRecordMap.put("operateType", 201 + "");
					fundRecordDao.addFundRecord(conn, fundRecordMap);
					
					alienatorUserMap = auctionDebtDao.getUserById(conn,
							alienatorId);
					userMap = new HashMap<String, String>();
					usableSum = Convert.strToDouble(alienatorUserMap
							.get("usableSum"), 0.0);
					userMap.put("usableSum", (usableSum - manageFee) + "");
					userDao.updateUser(conn, alienatorId, userMap);
					
					fundRecordMap = new HashMap<String, String>();
					userAmountMap = financeDao.queryUserAmountAfterHander(conn,
							alienatorId);
					if (userAmountMap == null) {
						userAmountMap = new HashMap<String, String>();
					}
					if (manageFee > 0) {
						String remark3 = "债权转让[<a href=" + basePath
								+ "/queryDebtDetail.do?id=" + debtId
								+ " target='_blank'>" + borrowTitle
								+ "</a>]转让手续费扣除";
						fundRecordMap.put("userId", alienatorId + "");
						fundRecordMap.put("fundMode", "债权转让手续费扣除");
						fundRecordMap.put("handleSum", manageFee + "");
						fundRecordMap.put("usableSum", userAmountMap
								.get("usableSum"));
						fundRecordMap.put("freezeSum", userAmountMap
								.get("freezeSum"));
						fundRecordMap.put("dueinSum", userAmountMap.get("forPI"));
						fundRecordMap.put("trader", "-1");
						fundRecordMap.put("recordTime", now);
						fundRecordMap.put("remarks", remark3);
						fundRecordMap.put("dueoutSum", "0");
						fundRecordMap.put("spending", manageFee + "");
						fundRecordMap.put("operateType", 701 + "");
						fundRecordDao.addFundRecord(conn, fundRecordMap);
					}
					
					// 转让者消息提醒
					noticeMap = new HashMap<String, String>();
					// 消息模版
					// 站内信
					informTemplate = informTemplateMap.get(
							IInformTemplateConstants.SUCCESS_CREDIT).toString();
					informTemplate = informTemplate.replace("date", DateUtil
							.dateToString((new Date())));
					informTemplate = informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + " target=_blank>" + borrowTitle
									+ "</a>");
					informTemplate = informTemplate.replace("money", maxPrice
							+ "");
					noticeMap.put("mail", informTemplate);
					
					// 邮件
					e_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.E_SUCCESS_CREDIT)
							.toString();
					e_informTemplate = e_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					e_informTemplate = e_informTemplate.replace("describe",
							"<a href=" + basePath + "/queryDebtDetail.do?id="
									+ debtId + ">" + borrowTitle + "</a>");
					e_informTemplate = e_informTemplate.replace("money",
							maxPrice + "");
					noticeMap.put("email", e_informTemplate);
					
					// 短信
					s_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.S_SUCCESS_CREDIT)
							.toString();
					s_informTemplate = s_informTemplate.replace("userName",
							assignmentDebtDao.queryUserNameById(conn,
									alienatorId));
					s_informTemplate = s_informTemplate.replace("date",
							DateUtil.dateToString((new Date())));
					s_informTemplate = s_informTemplate.replace("describe",
							borrowTitle);
					s_informTemplate = s_informTemplate.replace("money",
							maxPrice + "");
					noticeMap.put("note", e_informTemplate);
					
					selectedService.sendNoticeMSG(conn, alienatorId, "债权转让报告",
							noticeMap, IConstants.NOTICE_MODE_5);
					
					// 添加风险保障金
					Map<String, String> map = riskManageDao
							.queryRiskBalance(conn);
					String riskBalance = map.get("riskBalance");
					double riskBalanceDouble = Convert.strToDouble(
							riskBalance, 0);
					String riskType = "收入";
					String resource = "债权转让手续费";
					String remark_ = "债权手续费转让";
					riskManageDao.addRisk(conn, manageFee, -1, remark_,
							riskBalanceDouble, nowDate, riskType, resource);
					//
					// // 团队长，经纪人奖励提成
					// // 转让人
					// BigDecimal money = new BigDecimal(realAmount);
					// awardService.updateMoney(conn, alienatorId, money,
					// IConstants.MONEY_TYPE_1, investId);
					// // 竞拍者
					// awardService.updateMoney(conn, auctionerId, money,
					// IConstants.MONEY_TYPE_1, investId);
					//				
					informTemplateMap = null;
				}
			} else {
				// 修改转让表
				updateDebtMap = new HashMap<String, String>();
				updateDebtMap.put("debtStatus", "4");
				long updateCount = assignmentDebtDao.updateAssignmentDebt(conn,
						debtId, "1", updateDebtMap);
				if (updateCount != 0) {
					result = 1;
				}
			}
			return result;
		} finally {
			debtMap = null;
			nowDate = null;
			maxMap = null;
			auctionerUserMap = null;
			userMap = null;
			fundRecordMap = null;
			notice = null;
			sf = null;
			noticeMap = null;
			investMap = null;
			investHistoryMap = null;
			investUpdateMap = null;
			auctionList = null;
			userAmountMap = null;
			alienatorUserMap = null;
			riskMap = null;
			updateDebtMap = null;
			System.gc();
		}
	}

	public void setRiskManageDao(RiskManageDao riskManageDao) {
		this.riskManageDao = riskManageDao;
	}

	/**
	 * 提前还款时处理正在竞拍中的债权
	 * 
	 * @param repayId
	 *            还款Id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long preRepayment(Connection conn, long repayId) throws Exception {
		long result = -1;
		try {
			long borrowId = auctionDebtDao
					.queryBorrowIdByRepayId(conn, repayId);
			List<Map<String, Object>> debtList = assignmentDebtDao
					.queryAssignmentDebtIds(conn, borrowId, "1,2");
			if (debtList != null) {
				for (Map<String, Object> map : debtList) {
					long id = Convert.strToLong(map.get("id") + "", -1);
					assignmentDebt(conn, id, "1", 1);
					List<Map<String,Object>> list = this.getAllauctionerId(id);
					if(list!=null){
						for(Map<String,Object> m:list){
							long uid = Convert.strToLong(m.get("userId")+ "", -1);
							userService.updateSign(conn,uid);
						}
					}
				}
			}

			result = 1;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

		return result;
	}

	/**
	 * 撤销转让
	 * 
	 * @param debtId
	 * @param debtStatus
	 *            5：撤销,7：提前还款
	 * @throws SQLException
	 */
	public long cancelAssignmentDebt(long debtId, int debtStatus, long userId,
			int type) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1;
		try {
			if (assignmentDebtDao.isDebtInStatus(conn, debtId, "1")) {
				long dealResult = assignmentDebt(conn, debtId, "1",
						debtStatus);
				if (dealResult == 1) {
					if (type == 1) {
						userMap = userDao.queryUserById(conn, userId);
						operationLogDao.addOperationLog(conn,
								"t_assignment_debt", Convert.strToStr(userMap
										.get("username"), ""),
								IConstants.UPDATE, Convert.strToStr(userMap
										.get("lastIP"), ""), 0, "用户取消债权转让", 1);
					} else {
						userMap = adminDao.queryAdminById(conn, userId);
						operationLogDao.addOperationLog(conn,
								"t_assignment_debt", Convert.strToStr(userMap
										.get("userName"), ""),
								IConstants.UPDATE, Convert.strToStr(userMap
										.get("lastIP"), ""), 0, "管理员取消债权转让", 1);
					}
					conn.commit();
					result = 1;
				} else {
					conn.rollback();
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return result;
	}
	
	/**
	 * 通过
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long goManageDebt(long id)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1;		
		try {
			m = assignmentDebtDao.goManageDebt(conn,id);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return  m;
	}

	/**
	 * 债权转让失败操作
	 * 
	 * @param conn
	 * @param debtId
	 * @param debtStatus
	 *            5：撤销,7：提前还款
	 * @throws DataException
	 * @throws SQLException
	 */
	private long assignmentDebt(Connection conn, long debtId,
			String preDebtStatus, int debtStatus) throws Exception {
		long result = -1;
		List<Map<String, Object>> auctionList = auctionDebtDao
				.queryAuctionDebtByDebtId(conn, debtId);
		String borrowTitle = assignmentDebtDao.getBorrowTitle(conn, debtId);
		String basePath = WebUtil.getBasePath();

		Map<String, String> updateDebtMap = new HashMap<String, String>();
		updateDebtMap.put("debtStatus", debtStatus + "");
		long updateCount = assignmentDebtDao.updateAssignmentDebt(conn, debtId,
				preDebtStatus, updateDebtMap);
		if (updateCount != 0) {
			result = 1;
			if (auctionList != null) {
				Map<Long, Double> frezeMap = new HashMap<Long, Double>(); // 记录解冻的资金
				for (Map<String, Object> map : auctionList) {
					long userId = Convert.strToLong(map.get("auctionerId") + "", -1);
					Map<String, String> auctionerUserMap = auctionDebtDao
							.getUserById(conn, userId);
					Map<String, String> userMap = new HashMap<String, String>();
					double usableSum = Convert.strToDouble(auctionerUserMap
							.get("usableSum"), 0.0);
					double freezeSum = Convert.strToDouble(auctionerUserMap
							.get("freezeSum"), 0.0);
					double auctionPrice = Convert.strToDouble(map
							.get("debtPrice")
							+ "", 0.0);
					// 防止解冻重复解冻金额

					if (frezeMap.containsKey(userId)) {
						double oldAcutionPrice = frezeMap.get(userId);
						if (oldAcutionPrice >= auctionPrice) {
							continue;
						} else {
							frezeMap.put(userId, auctionPrice);// 记录该用户最大竞拍值
							auctionPrice = auctionPrice - oldAcutionPrice;
						}
					} else {
						frezeMap.put(userId, auctionPrice);// 记录该用户竞拍
					}
					userMap.put("usableSum", (usableSum + auctionPrice) + "");
					userMap.put("freezeSum", (freezeSum - auctionPrice) + "");

					if (userDao.updateUser(conn, userId, userMap) > 0) {
						// 解冻资金操作记录
						Map<String, String> userAmountMap = financeDao
								.queryUserAmountAfterHander(conn, userId);
						if (userAmountMap == null) {
							userAmountMap = new HashMap<String, String>();
						}
						double usableSumAfter = Convert.strToDouble(
								userAmountMap.get("usableSum") + "", 0);
						double freezeSumAfter = Convert.strToDouble(
								userAmountMap.get("freezeSum") + "", 0);
						double forPI = Convert.strToDouble(userAmountMap
								.get("forPI")
								+ "", 0);
						String remark = "[<a href=" + basePath
								+ "/queryDebtDetail.do?id=" + debtId
								+ " target=_blank>" + borrowTitle
								+ "</a>]竞拍失败解冻";
						fundRecordDao.addFundRecord(conn, userId, "债权转让竞拍解冻",
								auctionPrice, usableSumAfter, freezeSumAfter,
								forPI, -1, remark, auctionPrice, 0.0, -1, -1,
								202, 0.0);
						// 竞拍者消息提醒
						// 发送通知，通过通知模板
						Map<String, Object> informTemplateMap = getInformTemplate();

						Map<String, String> noticeMap = new HashMap<String, String>();

						// 消息模版
						// 站内信
						String informTemplate = informTemplateMap.get(
								IInformTemplateConstants.FAIL_BID).toString();
						if (informTemplate == null) {
							conn.rollback();
							return -1L;
						}
						informTemplate = informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						informTemplate = informTemplate.replace("describe",
								"<a href=" + basePath
										+ "/queryDebtDetail.do?id=" + debtId
										+ " target=_blank>" + borrowTitle
										+ "</a>");
						informTemplate = informTemplate.replace("money",
								auctionPrice + "");
						noticeMap.put("mail", informTemplate);

						// 邮件
						String e_informTemplate = informTemplateMap.get(
								IInformTemplateConstants.E_FAIL_BID).toString();
						if (e_informTemplate == null) {
							conn.rollback();
							return -1L;
						}
						e_informTemplate = e_informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						e_informTemplate = e_informTemplate.replace("describe",
								"<a href=" + basePath
										+ "/queryDebtDetail.do?id=" + debtId
										+ ">" + borrowTitle + "</a>");
						e_informTemplate = e_informTemplate.replace("money",
								auctionPrice + "");
						noticeMap.put("email", e_informTemplate);

						// 短信
						String s_informTemplate = informTemplateMap.get(
								IInformTemplateConstants.S_FAIL_BID).toString();
						if (s_informTemplate == null) {
							conn.rollback();
							return -1L;
						}
						s_informTemplate = s_informTemplate.replace("userName",
								assignmentDebtDao.queryUserNameById(conn,
										userId));
						s_informTemplate = s_informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						s_informTemplate = s_informTemplate.replace("describe",
								borrowTitle);
						s_informTemplate = s_informTemplate.replace("money",
								auctionPrice + "");
						noticeMap.put("note", e_informTemplate);

						selectedService.sendNoticeMSG(conn, userId, "债权转让报告",
								noticeMap, IConstants.NOTICE_MODE_5);
					}
				}
			}
		}

		return result;
	}

	/**
	 * 检查债权是否过期并设置过期参数
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean checkDueDebt() throws Exception {
		Connection conn = MySQL.getConnection();
		boolean result = false;
		try {
			List<Map<String, Object>> debtList = assignmentDebtDao
					.queryDueDebt(conn);
			if (debtList != null) {
				for (Map<String, Object> map : debtList) {
					if ("2".equals(map.get("debtStatus") + "")) {
						Long id = Convert.strToLong(map.get("id") + "", -1);
						auctDebtSuccess(conn, id, "");
						Map<String, String> m = getAssignmentDebt(id);
						if(m != null){
							long alienatorId = Convert.strToLong(m.get("alienatorId"), -1);//转让人
							userService.updateSign(conn, alienatorId);//更换校验码
						}
						List<Map<String, Object>> auctionList = auctionDebtDao.queryAuctionDebtByDebtId(conn, id);
						for (Map<String, Object> auctionMap : auctionList) {
							long userId = Convert.strToLong(auctionMap.get("userId") + "",-1);
							userService.updateSign(conn, userId);//更换校验码
						}
					}
				}
			}
			conn.commit();
			result = true;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 查询竞拍成功的用户和转让者
	 * 
	 * @param aid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryDebtUserName(long aid) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = assignmentDebtDao.queryDebtUserName(conn, aid);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	public Map<String, String> queryApplyDebtDetail() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = assignmentDebtDao.queryApplyDebtDetail(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryApplySuccessDebtDetail() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = assignmentDebtDao.queryApplySuccessDebtDetail(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return map;
	}

	public Map<String, String> queryApplyFailDebtDetail() throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = assignmentDebtDao.queryApplyFailDebtDetail(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return map;
	}
	public void changeDateToStr(PageBean<Map<String, Object>> pageBean){
		List<Map<String,Object>> list = pageBean.getPage();
		if(list != null){
			Date nowDate = new Date();
			for(Map<String,Object> map : list){
				Date date = (Date)map.get("remainAuctionTime");
				map.put("remainDays", DateUtil.remainDateToString(nowDate, date));
			}
		}
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	/**
	 * 查询债权所有竞拍者
	 * @param debtId
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> queryAuctioner(long debtId) throws SQLException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = assignmentDebtDao.queryAuctioner(conn, debtId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return list;
	}
	
	
	public long addAuctingDebt(long debtId,long userId,String pwd,double auctionPrice,String basePath,String debtValue,int isUseHb_6_24){
		long res = -9999;
		try {
			Map<String, String> debtMap = assignmentDebtService
					.getAssignmentDebt(debtId);
			Map<String, String> userMap = auctionDebtService
					.getUserById(userId);
			
			if (debtMap != null && userMap != null) {
				if (debtMap.get("alienatorId").equals(userId + "")) {// 不能投自己转让的的债权
					return -2;
				}
				//借款id
				long borrowId = Convert.strToLong(debtMap.get("borrowId"), -1);
				//投资id
				long investId = Convert.strToLong(debtMap.get("investId"), -1);
			
				if (!pwd.equals(userMap.get("dealpwd"))) {// 交易密码不对
					return -3;
				}
				Map<String,String> aucctionMap = auctionDebtService.getAuctionDebt(debtId,userId);
				double oldAuctionPrice = 0.0;
				if(aucctionMap != null){
					oldAuctionPrice = Convert.strToDouble(aucctionMap.get("auctionPrice"),0.0);
				}
				
				double usableSum = Convert.strToDouble(userMap.get("usableSum"), 0.0);
				if (usableSum < (auctionPrice-oldAuctionPrice)) {// 可用余额不足
					return -4;
				}
				
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				if(borrowerId==userId){// 借款者不能竞拍该债权
					return -9;
				}
				
				if(!"1".equals(debtMap.get("debtStatus"))){ //竞拍失败
					return -7;
				}
				
				Map<String,String> pro_map = auctionDebtService.procedure_Debts(borrowId, investId, debtId, userId, auctionPrice, pwd, basePath,isUseHb_6_24);
				
				long result = -1;
				result =Convert.strToLong( pro_map.get("ret"),-1);
				if(result == 1){
					//结束债权，不用审核
					assignmentDebtService.auctingDebtSuccess(debtId,userId,1,debtValue);  //区分前后台用户
					res = 1;
					// 更新转让利率和转让时间
					Map<String, Object> debtPriceMap = coseDebtPrice(borrowId, investId, "", "");
					double debtValue1 = Convert.strToDouble(String.valueOf(debtPriceMap.get("debtValue")), -1f); 
					double debtPrice = Convert.strToDouble(String.valueOf(debtPriceMap.get("debtPrice")),-1f);
					int remainingDays = Convert.strToInt(String.valueOf(debtPriceMap.get("remainingDays")),-1);
					double annualRateDebtBDDouble =Convert.strToDouble(String.valueOf(debtPriceMap.get("annualRateDebtBDDouble")),-1f);
					assignmentDebtService.updateDebtEnd(debtId, debtValue1, debtPrice, remainingDays, annualRateDebtBDDouble);
					
					//购买人
					log.info("购买者ID:"+userId);
				}else{
					res = result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 更新债权满标时的状态
	 * @param id
	 * @param debtValue
	 * @param debtPrice
	 * @param remainingDays
	 * @param annualRateDebtBDDouble
	 * @return
	 * @throws Exception
	 */
	public long updateDebtEnd(long id ,double debtValue,double debtPrice,int remainingDays,double annualRateDebtBDDouble)throws Exception{
		long result = 0;
		Connection conn = MySQL.getConnection();
		try {
			result = assignmentDebtDao.updateDebtEnd(conn, id, debtValue, debtPrice, remainingDays,annualRateDebtBDDouble);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	/**
	 * 添加债权投资申请
	 * @param debtId
	 * @param userId
	 * @param pwd
	 * @param auctionPrice
	 * @param basePath
	 * @param debtValue
	 * @return
	 */
	public long addInvestDebtApply(long debtId,long userId,String pwd,double auctionPrice,String basePath,String debtValue) throws Exception{
		long result = 0;
		Connection conn = MySQL.getConnection();
		try {
			result = assignmentDebtDao.addInvestDebtApply(conn, debtId, userId, pwd, auctionPrice, basePath, debtValue);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public long updateInvestDebtApplyStatus(long id,int status){
		long result = 0;
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			result = assignmentDebtDao.updateInvestDebtApplyStatus(conn, id, status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	/**
	 * 查询机构用户的债权
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryInvestDebtApply() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = assignmentDebtDao.queryInvestDebtApply(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询机构用户的债权
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestDebtApplyById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = assignmentDebtDao.queryInvestDebtApplyById(conn,id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	public void queryInvestDebtApply(PageBean pageBean)throws SQLException, DataException{
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "t_invest_debt_apply", "*",
					" GROUP BY debtId HAVING sum(`status`)=0 order by status asc ", " ");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 债权转让
	 * 
	 * @param：
	 * borrowId借款id;
	 * investId还款拥有者;
	 * annualRate年利率(转让后的年利率,0系统自动分配线性利率);
	 * transRatio转让系数;
	 * nextRepayDate下一还款日期
	 * @author 
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public Map<String,Object> coseDebtPrice(long borrowId, long investId, String  transRatio, String annualRate) throws Exception{
		Map<String, String> debtMap = null;
		Map<String,Object> result = null;
		//获取当前借款
		Map<String, String> map = assignmentDebtService.getOnePay(borrowId);
		
		if(null != map && map.size() >0){
			if(1 == Integer.parseInt(map.get("deadline"))){ // 活力宝
				log.info("===========暂不支持活力宝转让=============");
			}else{
				debtMap = assignmentDebtService.getDebt(borrowId,investId);
			}
		}else {
			log.info("===========查询标的信息错误【BORRROW_ID:"+borrowId+"不存在】=============");
		}
		
		try {
			result = DebtUtil.debtFormula(debtMap,annualRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 自动发布和投资一笔债权，用于后台筹集债权
	 * @author 黑暗珊瑚君
	 * @param debtMap
	 * @return
	 * @throws Exception
	 */
	public long autoAssignmentAndInvestDebt(Map<String,String> debtMap,String basePath)throws Exception{
		
		Connection conn = MySQL.getConnection();
		long result = -1;
		long userId = Convert.strToLong(debtMap.get("investor"), -1);
		
		try {
			// <----- 分配一个债权 
			double moneyFlag = checkTransMoney(userId, Convert.strToDouble(debtMap.get("debtSum"), -1));
			if (moneyFlag == 1) {
				result = addAssignmentDebt(conn,debtMap);
				if (result != -1) {
					
					// 更新INVEST表
					Map<String,String> investMap = new HashMap<String, String>();
					investMap.put("debt_type", "2");// 待转成活力宝
					investDao.updateInvest(conn, Convert.strToLong(debtMap.get("investId"), -1), investMap);
					
					operationLogService.addOperationLog("t_assignment_debt","admin", IConstants.UPDATE,"186", 0, "债权转让审核成功", 2);
				} else {
					operationLogService.addOperationLog("t_assignment_debt","admin", IConstants.UPDATE,"186", 0, "债权转让审核失败", 2);
				}
			} else if (moneyFlag > 1) {
				operationLogService.addOperationLog("t_assignment_debt","admin", IConstants.UPDATE,"186", 0, "债权转让审核失败", 2);
			}
			// 结束分配一个债权  -----> 
			
			// 提交数据，否则投资不了债权
			if(result>0){
				conn.commit();
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public long addAssignmentDebt(Connection conn ,Map<String,String> paramMap){

		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1;
		try {
			if (!assignmentDebtDao.isHaveAssignmentDebt(conn, Convert
					.strToLong(paramMap.get("investId"), -1), Convert
					.strToLong(paramMap.get("alienatorId"), -1))) {
				// 添加别名 
				Map<String,String> debtIndexMap = assignmentDebtDao.queryMMaxDebtIndex(conn,paramMap.get("publishTime"));
				String maxDebtIndexLable = "0001";
				String maxDebtIndex = "1";
				if(debtIndexMap !=null && !debtIndexMap.isEmpty()){
					maxDebtIndex = debtIndexMap.get("maxDebtIndex");
					int n = 4-maxDebtIndex.length();
					String appendZero = "";
					for(int i=0;i<n;i++){
						appendZero = "0"+appendZero;// 补零
					}
					if(appendZero!=null && appendZero.length()>0){
						maxDebtIndexLable = appendZero +maxDebtIndex;
					}
				}
				
				paramMap.put("debtIndex", maxDebtIndex);
				paramMap.put("debtIndexLable", maxDebtIndexLable);
				
				// 划分份数和相应的额度
				
				int copies = 100;//默认100份
				double share = Convert.strToDouble(paramMap.get("debtSum"), 0)/copies;// 单份额度0
				paramMap.put("copies", String.valueOf(copies));
				paramMap.put("share",String.valueOf(share));
				
				// 添加债权
				result = assignmentDebtDao.addAssignmentDebt(conn, paramMap);
				// 更新转让利率，目前只针对批量债转采取这样的措施。
				investDao.updateAnnualRateDebtBDDouble(conn,Convert.strToDouble(paramMap.get("annualRateDebtBDDouble"), -1), Convert.strToLong(paramMap.get("investId"), -1));
				
				String borrowTitle = assignmentDebtDao.getBorrowTitle(conn,
						result);
				// 添加用户动态
				String cotent = "债权转让了借款<a href=queryDebtDetail.do?id="
						+ result + " target=_blank>" + borrowTitle + "</a>";
				financeDao.addUserDynamic(conn, Convert.strToLong(paramMap
						.get("alienatorId"), -1), cotent);
				userMap = userDao.queryUserById(conn, Convert.strToLong(
						paramMap.get("alienatorId"), -1));
				operationLogDao.addOperationLog(conn, "t_assignment_debt",
						Convert.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发布债权转让", 1);
				
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			
		}

		return result;
	
	}
	
	
	public List<Map<String, Object>> queryRepayDebt(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = assignmentDebtDao.queryRepayDebt(conn, borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
}
