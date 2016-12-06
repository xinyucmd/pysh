/**
 * 
 */
package com.sp2p.service;

import java.math.BigDecimal;
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
import com.shove.util.ServletUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.AssignmentDebtDao;
import com.sp2p.dao.AuctionDebtDao;
import com.sp2p.dao.BonusDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.DateUtil;

/**
 * 竞拍债权
 * 
 * 
 */
public class AuctionDebtService extends BaseService {
	public static Log log = LogFactory.getLog(AuctionDebtService.class);

	private AuctionDebtDao auctionDebtDao;
	private AssignmentDebtDao assignmentDebtDao;
	private SelectedService selectedService;
	private FundRecordDao fundRecordDao;
	private AccountUsersDao accountUsersDao;
	private OperationLogDao operationLogDao;
	private BorrowDao borrowDao;
	private BonusDao bonusDao;
	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public BonusDao getBonusDao() {
		return bonusDao;
	}

	public void setBonusDao(BonusDao bonusDao) {
		this.bonusDao = bonusDao;
	}


	private UserDao userDao;

	private FinanceDao financeDao;

	public void setAuctionDebtDao(AuctionDebtDao auctionDebtDao) {
		this.auctionDebtDao = auctionDebtDao;
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

	/**
	 * 添加竞拍债权
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAuctionDebt(Map<String, String> paramMap,
			Map<String, String> userMap) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			long userId = Convert.strToLong(paramMap.get("userId"), -1);

			if (userMap != null) {
				double useMoney = Convert.strToDouble(userMap.get("usableSum"),
						0);
				double freezeSum = Convert.strToDouble(
						userMap.get("freezeSum"), 0);
				double djMoney = Convert.strToDouble(paramMap
						.get("auctionPrice"), 0);
				if (useMoney >= djMoney) {
					userMap = new HashMap<String, String>();
					userMap.put("usableSum", (useMoney - djMoney) + "");
					userMap.put("freezeSum", (freezeSum + djMoney) + "");
					String borrowTitle = assignmentDebtDao.getBorrowTitle(conn,
							Convert.strToLong(paramMap.get("debtId"), -1));
					// 冻结资金
					if (userDao.updateUser(conn, userId, userMap) > 0) {
						// 查询竞拍后的账户金额
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
						String remark = "竞拍[<a href="
								+ paramMap.get("basePath")
								+ "/queryDebtDetail.do?id="
								+ Convert.strToLong(paramMap.get("debtId"), -1)
								+ " target=_blank>" + borrowTitle + "</a>]资金冻结";

						fundRecordDao.addFundRecord(conn, userId, "债权转让竞拍冻结",
								djMoney, usableSumAfter, freezeSumAfter, forPI,
								-1, remark, 0.0, djMoney, -1, -1, 702, 0.0);

						// 发送通知，通过通知模板
						Map<String, Object> informTemplateMap = getInformTemplate();

						Map<String, String> noticeMap = new HashMap<String, String>();

						// 消息模版
						// 站内信
						String informTemplate = informTemplateMap.get(
								IInformTemplateConstants.FREEZE_BID).toString();
						if (informTemplate == null) {
							conn.rollback();
							return -1L;
						}
						informTemplate = informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						informTemplate = informTemplate.replace("describe",
								"<a href=" + paramMap.get("basePath")
										+ "/queryDebtDetail.do?id="
										+ paramMap.get("debtId")
										+ " target=_blank>" + borrowTitle
										+ "</a>");
						informTemplate = informTemplate.replace("money",
								paramMap.get("auctionPrice"));
						noticeMap.put("mail", informTemplate);

						// 邮件
						String e_informTemplate = informTemplateMap.get(
								IInformTemplateConstants.E_FREEZE_BID)
								.toString();
						if (e_informTemplate == null) {
							conn.rollback();
							return -1L;
						}
						e_informTemplate = e_informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						e_informTemplate = e_informTemplate.replace("describe",
								"<a href=" + paramMap.get("basePath")
										+ "/queryDebtDetail.do?id="
										+ paramMap.get("debtId") + ">"
										+ borrowTitle + "</a>");
						e_informTemplate = e_informTemplate.replace("money",
								paramMap.get("auctionPrice"));
						noticeMap.put("email", e_informTemplate);

						// 短信
						String s_informTemplate = informTemplateMap.get(
								IInformTemplateConstants.S_FREEZE_BID)
								.toString();
						if (s_informTemplate == null) {
							conn.rollback();
							return -1L;
						}
						s_informTemplate = s_informTemplate.replace("userName",
								paramMap.get("userName"));
						s_informTemplate = s_informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						s_informTemplate = s_informTemplate.replace("describe",
								borrowTitle);
						s_informTemplate = s_informTemplate.replace("money",
								paramMap.get("auctionPrice"));
						noticeMap.put("note", e_informTemplate);

						selectedService.sendNoticeMSG(conn, userId, "债权转让竞拍报告",
								noticeMap, IConstants.NOTICE_MODE_5);

						result = auctionDebtDao.addAuctionDebt(conn, paramMap);
						long debtId = Convert.strToLong(paramMap.get("debtId"),
								-1);
						Map<String, String> debtMap = auctionDebtDao
								.queryAuctionMaxPriceAndCount(conn, debtId);
						debtMap.put("auctionHighPrice", debtMap
								.get("maxAuctionPrice"));
						debtMap.put("auctionerId", debtMap.get("userId"));
						debtMap.remove("id");
						assignmentDebtDao.updateAssignmentDebt(conn, debtId,
								"1", debtMap);//whb还款

					}
				}

			}

			conn.commit();
			result = 1;
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
	 * 添加竞拍债权
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateAuctionDebt(long id, Map<String, String> paramMap)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = auctionDebtDao.updateAuctionDebt(conn, id, paramMap);

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
	 * 删除竞拍债权，可删除多个
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteAuctionDebt(String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = auctionDebtDao.deleteAuctionDebt(conn, ids);

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
	 * 根据ID获取竞拍债权信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getAuctionDebt(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.getAuctionDebt(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据债权转让Id查询竞拍记录
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryAuctionDebtByDebtId(long debtId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = auctionDebtDao.queryAuctionDebtByDebtId(conn, debtId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 查询条数和最大竞拍金额数
	 * 
	 * @param debtId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryAuctionMaxPriceAndCount(long debtId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.queryAuctionMaxPriceAndCount(conn, debtId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据用户Id获取用户信息
	 * 
	 * @param alienatorId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getUserAddressById(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.getUserAddressById(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据用户Id获取用户信息
	 * 
	 * @param alienatorId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getUserById(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.getUserById(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 查询参与竞拍的债权
	 * 
	 * @param borrowTitle
	 * @param startTime
	 * @param endTime
	 * @param userId
	 * @param pageBean
	 * @throws SQLException
	 */
	public void queryAuctionDebt(String borrowTitle, String startTime,
			String endTime, long userId, String debtStatus, PageBean pageBean)
			throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		debtStatus = Utility.filteSqlInfusion(debtStatus);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (userId > 0) {
			condition.append(" and userId=");
			condition.append(userId);
			condition.append(" ");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and borrowTitle like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowTitle));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" and createTime >= '");
			condition.append(StringEscapeUtils.escapeSql(startTime));
			condition.append("' ");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and createTime <= '");
			condition.append(StringEscapeUtils.escapeSql(endTime));
			condition.append("' ");
		}

		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + debtStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" and debtStatus in(");
			condition.append(idSQL);
			condition.append(") ");
		}

		try {
			dataPage(conn, pageBean, " v_debt_aucting_borrow ", "*", "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询成功的竞拍债权
	 * 
	 * @param borrowTitle
	 * @param startTime
	 * @param endTime
	 * @param userId
	 * @param pageBean
	 * @throws SQLException
	 */
	public void querySuccessAuctionDebt(String borrowTitle, String startTime,
			String endTime, long userId, PageBean pageBean) throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (userId > 0) {
			condition.append(" and auctionerId=");
			condition.append(userId);
			condition.append(" ");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and borrowTitle like '%");
			condition.append(StringEscapeUtils.escapeSql(borrowTitle));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" and createTime >= '");
			condition.append(StringEscapeUtils.escapeSql(startTime));
			condition.append("' ");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and createTime <= '");
			condition.append(StringEscapeUtils.escapeSql(endTime));
			condition.append("' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_assignment_debt_success", "*", "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询用户的竞拍次数
	 * 
	 * @param debtId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long queryAuctionUserTimes(long debtId, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long result = 0;
		try {
			result = auctionDebtDao.queryAuctionUserTimes(conn, debtId, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 查找借款人Id
	 * 
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 */
	public long queryBorrowerByBorrowId(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = 0;
		try {
			result = auctionDebtDao.queryBorrowerByBorrowId(conn, borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}
	
	/**
	 * 查找借款人图片
	 * 
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryBorrowerImgpath(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.queryBorrowerImgpath(conn, borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 统计债务信息
	 * 
	 * @param debtId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAuctionDebt(long debtId, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.getAuctionDebt(conn, debtId, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 用户参与竞拍，债权信息的更新
	 * 
	 * @param paramMap
	 * @param id
	 * @param userMap
	 * @return
	 * @throws SQLException
	 */
	public long upadteAuctionDebt(Map<String, String> paramMap, long id,
			Map<String, String> userMap) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			long userId = Convert.strToLong(paramMap.get("userId"), -1);

			if (userMap != null) {
				double useMoney = Convert.strToDouble(userMap.get("usableSum"),
						0);
				double freezeSum = Convert.strToDouble(
						userMap.get("freezeSum"), 0);
				double djMoney = Convert.strToDouble(paramMap
						.get("auctionPrice"), 0)
						- Convert.strToDouble(paramMap.get("oldAuctionPrice"),
								0);
				if (useMoney >= djMoney) {
					userMap = new HashMap<String, String>();
					userMap.put("usableSum", (useMoney - djMoney) + "");
					userMap.put("freezeSum", (freezeSum + djMoney) + "");
					String borrowTitle = assignmentDebtDao.getBorrowTitle(conn,
							Convert.strToLong(paramMap.get("debtId"), -1));
					// 冻结资金
					if (userDao.updateUser(conn, userId, userMap) > 0) {
						paramMap.put("autiontimes", "2");
						auctionDebtDao.addAuctionDebt(conn, paramMap);
						// 查询竞拍后的账户金额
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
						String remark = "您第二次竞拍了债权转让[<a href="
								+ paramMap.get("basePath")
								+ "/queryDebtDetail.do?id="
								+ Convert.strToLong(paramMap.get("debtId"), -1)
								+ " target=_blank>" + borrowTitle + "</a>]资金冻结";

						fundRecordDao.addFundRecord(conn, userId, "债权转让竞拍冻结",
								djMoney, usableSumAfter, freezeSumAfter, forPI,
								-1, remark, 0.0, djMoney, -1, -1, 702, 0.0);

						// 发送通知，通过通知模板
						Map<String, Object> informTemplateMap = getInformTemplate();

						Map<String, String> noticeMap = new HashMap<String, String>();

						// 消息模版
						// 站内信
						String informTemplate = informTemplateMap.get(
								IInformTemplateConstants.FREEZE_BID).toString();
						informTemplate = informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						informTemplate = informTemplate.replace("describe",
								"<a href=" + paramMap.get("basePath")
										+ "/queryDebtDetail.do?id="
										+ paramMap.get("debtId")
										+ " target=_blank>" + borrowTitle
										+ "</a>");
						informTemplate = informTemplate.replace("money",
								paramMap.get("auctionPrice"));
						noticeMap.put("mail", informTemplate);

						// 邮件
						String e_informTemplate = informTemplateMap.get(
								IInformTemplateConstants.E_FREEZE_BID)
								.toString();
						e_informTemplate = e_informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						e_informTemplate = e_informTemplate.replace("describe",
								"<a href=" + paramMap.get("basePath")
										+ "/queryDebtDetail.do?id="
										+ paramMap.get("debtId") + ">"
										+ borrowTitle + "</a>");
						e_informTemplate = e_informTemplate.replace("money",
								paramMap.get("auctionPrice"));
						noticeMap.put("email", e_informTemplate);

						// 短信
						String s_informTemplate = informTemplateMap.get(
								IInformTemplateConstants.S_FREEZE_BID)
								.toString();
						s_informTemplate = s_informTemplate.replace("userName",
								paramMap.get("userName"));
						s_informTemplate = s_informTemplate.replace("date",
								DateUtil.dateToString((new Date())));
						s_informTemplate = s_informTemplate.replace("describe",
								borrowTitle);
						s_informTemplate = s_informTemplate.replace("money",
								paramMap.get("auctionPrice"));
						noticeMap.put("note", e_informTemplate);

						selectedService.sendNoticeMSG(conn, userId, "债权转让竞拍报告",
								noticeMap, IConstants.NOTICE_MODE_5);

						long debtId = Convert.strToLong(paramMap.get("debtId"),
								-1);
						Map<String, String> debtMap = auctionDebtDao
								.queryAuctionMaxPriceAndCount(conn, debtId);
						debtMap.put("auctionHighPrice", debtMap
								.get("maxAuctionPrice"));
						debtMap.put("auctionerId", debtMap.get("userId"));
						debtMap.remove("id");

						assignmentDebtDao.updateAssignmentDebt(conn, debtId,
								"1", debtMap);//whb还款

					}
				}
			}
			conn.commit();
			result = 1;
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
	 * 根据借款ID查询未还款期数
	 * @author whb
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryTermByBorrowId(long borrowId, long investId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = auctionDebtDao.queryTermByBorrowId(conn, borrowId, investId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}
	
	/**
	 * 存储过程 处理债权转让
	 * 
	 * @param debtId
	 * @param userId
	 * @param aucionPrice
	 * @param debtPwd
	 * @param basePath
	 * @return
	 * @author C_J
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> procedure_Debts(long borrowId, long investId, long debtId, long userId,
			double aucionPrice, String debtPwd, String basePath,int isUseHb_6_24)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long ret = -1L;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			//whb 添加未还款期数
			Map<String, String> result = queryTermByBorrowId(borrowId, investId);
			long term = Convert.strToLong(result.get("term"),-1);
			long id = Convert.strToLong(result.get("id"),-1);
			
			Procedures.p_borrow_debt_add(conn, ds, outParameterValues, id, term, debtId,
					userId, new BigDecimal(aucionPrice), debtPwd, basePath, 0,
					"");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", outParameterValues.get(0) + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				conn.rollback();
				return map;
			}
			// 添加操作日志
			userMap = userDao.queryUserById(conn, userId);
			operationLogDao.addOperationLog(conn, "t_invest_debt", Convert
					.strToStr(userMap.get("username"), ""), IConstants.INSERT,
					Convert.strToStr(userMap.get("lastIP"), ""), 0, "债权竞拍", 1);
			
			
			// 使用红包 START
			
			boolean flags = true;
			Map<String,String> downLineMap = userDao.queryGroupById(conn, userId);
			if(downLineMap!=null && downLineMap.size()>0){
				flags = false;
			}
			
			//感恩十一月丰收季--转盘红包使用
			if(flags){
				Map<String,String>  activty_comfig = financeDao.query51ActivtyComfig(conn, "1004");
				if(activty_comfig!=null && activty_comfig.size()>0){
					 Map<String, String> borrowMap = borrowDao.queryBorroeById(conn, borrowId);
					
					int deadline = Integer.parseInt(borrowMap.get("deadline"));//投标期限
					if(isUseHb_6_24==1){
						
						List<Map<String,Object>> listBonus = bonusDao.queryBonus_6_24(conn, userId);
						if(listBonus!=null && listBonus.size()>0){//有6.24红包
							
							Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
							double usableSum = Double.parseDouble(useMap.get("usableSum"));//可用金额
							long super_id = Long.parseLong(activty_comfig.get("user_id"));//超级账户id
							Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
							double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
							
//							double b = aucionPrice*deadline/12/100f;//投资抵用红包
							double b = aucionPrice/50f;//投资抵用红包
							double a = Math.floor(b);
							double bonus_avaliable_6_24 = Double.parseDouble(bonusDao.queryBonus_6_24_sum(conn, userId).get("bonus_able"));
							if(a>=bonus_avaliable_6_24){//6.24红包被全部用完或者红包不够用的情况
								double fee = usableSum + bonus_avaliable_6_24;
								double super_fee = superUsableSum - bonus_avaliable_6_24;
								borrowDao.updateUserMoney(conn, userId, fee);//更新用户可用金
								borrowDao.updateUserMoney(conn, super_id, super_fee);//更新超级用户可用金
							    if(listBonus!=null && listBonus.size()>0){
							    	double user_able_moeny = usableSum;
							    	for(int i=0;i<listBonus.size();i++){
							    		Map<String, Object> userBonusMap = listBonus.get(i);
							    		long ids = Long.parseLong(String.valueOf(userBonusMap.get("id")));
							    		double bonus_able = Double.parseDouble(String.valueOf(userBonusMap.get("bonus_able")));
							    		double bonus_sum = Double.parseDouble(String.valueOf(userBonusMap.get("bonus_sum")));
							    		//double bonus_already = bonus_sum-bonus_able;
							    		bonusDao.updateUserBonus6_24(conn, ids, 0, bonus_sum, 2);
							    		user_able_moeny = user_able_moeny+bonus_able;
							    	financeDao.add51Recode(conn,userId, super_id,"感恩十一月丰收季", bonus_able, user_able_moeny, bonus_able, "投资返现",2,id);
									financeDao.add51Recode(conn,super_id, userId,"感恩十一月丰收季", bonus_able, superUsableSum-bonus_able, bonus_able, "投资返现",1,id);
							    	}
							     }
							   }else{//6.24红包有剩余情况下
								    double fee = usableSum + a;
									double super_fee = superUsableSum - a;
									borrowDao.updateUserMoney(conn, userId, fee);//更新用户可用金
									borrowDao.updateUserMoney(conn, super_id, super_fee);//更新超级用户可用金
									double bonus_able_sum = 0;
									int temp = 0;
									double bonus_dy = 0;
									long id_last = 0;
									double bonus_sum_s = 0;
									for(int i = 0;i<listBonus.size();i++){
										Map<String,Object> userBonusMap = listBonus.get(i);
										long ids = Long.parseLong(String.valueOf(userBonusMap.get("id")));
										double bonus_able = Double.parseDouble(String.valueOf(userBonusMap.get("bonus_able")));
//										double bonus_already = Double.parseDouble(String.valueOf(userBonusMap.get("bonus_already")));
										bonus_sum_s = Double.parseDouble(String.valueOf(userBonusMap.get("bonus_sum")));
										
										bonusDao.updateUserBonus6_24(conn, ids, 0, bonus_sum_s, 2);
										bonus_able_sum = bonus_able_sum+bonus_able;
										if(bonus_able_sum == a){
											financeDao.add51Recode(conn,userId, super_id,"感恩十一月丰收季", bonus_able_sum, usableSum+bonus_able_sum, bonus_able_sum, "投资返现",2,id);
											financeDao.add51Recode(conn,super_id, userId,"感恩十一月丰收季", bonus_able_sum, superUsableSum-bonus_able_sum, bonus_able_sum, "投资返现",1,id);
										    break;
										}
										
										if(bonus_able_sum > a){
											id_last = ids;
											bonus_dy = bonus_able_sum-a;//多余的红包
											temp = 1;
											financeDao.add51Recode(conn,userId, super_id,"感恩十一月丰收季", bonus_able_sum-bonus_dy, usableSum+(bonus_able_sum-bonus_dy), bonus_able_sum-bonus_dy, "投资返现",2,id);
											financeDao.add51Recode(conn,super_id, userId,"感恩十一月丰收季", bonus_able_sum-bonus_dy, superUsableSum-(bonus_able_sum-bonus_dy), bonus_able_sum-bonus_dy, "投资返现",1,id);
										    
											break;
										}
									}
									if(temp==1){
										 //更新上述更新数据的最后一条；
										 bonusDao.updateUserBonus6_24(conn, id_last, bonus_dy, bonus_sum_s-bonus_dy, 1);
									 }
							   }
				            }
						}
					}
				}
			
			// 使用红包END
			
			
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

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
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

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public AuctionDebtDao getAuctionDebtDao() {
		return auctionDebtDao;
	}

	public AssignmentDebtDao getAssignmentDebtDao() {
		return assignmentDebtDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}
}
