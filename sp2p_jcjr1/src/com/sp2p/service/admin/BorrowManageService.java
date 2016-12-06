package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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
import org.springframework.web.context.ContextLoader;

import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.NoticeTaskDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RepaymentRecordDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.UserDao;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.RepamentDao;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.AwardService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.HttpClientHelper;

/**
 * @ClassName: BorrowManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-10 下午10:07:28
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 后台借款管理业务处理
 */
public class BorrowManageService extends BaseService {

	public static Log log = LogFactory.getLog(BorrowManageService.class);

	private BorrowManageDao borrowManageDao;
	private SelectedService selectedService;
	private AwardService awardService;
	private InvestDao investDao;
	private AccountUsersDao accountUsersDao;
	private RepamentDao repamentDao;
	private UserDao userDao;
	private FundRecordDao fundRecordDao;
	private RepaymentRecordDao repaymentRecordDao;
	private AdminDao adminDao;
	private BorrowDao borrowDao;
	private PlatformCostService platformCostService;
	private NoticeTaskDao noticeTaskDao;
	private OperationLogDao operationLogDao;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @MethodName: queryAllCirculationRepayRecordByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-23 下午11:24:18
	 * @Return:
	 * @Descb: 根据条件查询流转标还款记录
	 * @Throws:
	 */
	public void queryAllCirculationRepayRecordByCondition(String userName,
			int borrowStatus, String borrowTitle,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		
		String resultFeilds = " a.*";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and a.username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and a.borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(borrowTitle.trim())
					+ "','%')");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and borrowStatus =" + borrowStatus);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_circulation_repayrecord a",
					resultFeilds, " order by a.id asc", condition.toString());
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
	 * 获取希财网access_token
	 * @author whb
	 * 
	 */
	public String getCsaiAccessToken() {
		try {
			String url = "http://api.csai.cn/oauth2/access_token2";
			Map<String, String> params = new HashMap<String, String>();
			params.put("client_id", "bb60453f97334f1ab5db8e4934dbd9e8");
			params.put("client_secret", "8fe63b1471ae4f1fb7080e2ac131a7fb");
			Map<String, String> result = HttpClientHelper.postMap(url, params);
			if(!result.isEmpty()){
				log.info("希财网access_token获取成功!");
				return result.get("access_token");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据借款id查询借款信息（希财网）
	 * @author whb
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> dataToCsai(long borrowId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.dataToCsai(conn, borrowId);
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
	
	/**
	 * 根据借款id查询借款信息（希财网）
	 * @author whb
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> updateCsai()throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = borrowManageDao.updateCsai(conn);
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
	 * @MethodName: updateBorrowCirculationStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午03:21:21
	 * @Return:
	 * @Descb: 更新流转标状态
	 * @Throws:
	 */
	public long updateBorrowCirculationStatus(long borrowId, long reciverId,
			long statusLong, String auditOpinion, Long adminId,
			String basePath, Map<String, Object> platformCostMap)
			throws Exception {
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		basePath = Utility.filteSqlInfusion(basePath);
		
		Connection conn = MySQL.getConnection();
		int circulationStatus = -1;
		double borrowSum = 0;
		double yearRate = 0;
		int deadline = 0;
		int isDayThe = 1;
		Long result = -1L;
		try {
			Map<String, String> borrowMap = borrowManageDao.queryBorrowInfo(
					conn, borrowId);
			if (borrowMap == null)
				borrowMap = new HashMap<String, String>();
			borrowSum = Convert.strToDouble(borrowMap.get("borrowAmount") + "",
					0);
			yearRate = Convert.strToDouble(borrowMap.get("annualRate") + "", 0);
			deadline = Convert.strToInt(borrowMap.get("deadline") + "", 0);
			isDayThe = Convert.strToInt(borrowMap.get("isDayThe") + "", 1);
			// 处理流转标
			result = borrowManageDao.updateBorrowCirculationStatus(conn,
					borrowId, statusLong, auditOpinion, circulationStatus, 10);
			if (result > 0) {
				// 审核通过添加还款记录
				if (statusLong == 2) {
					AmountUtil au = new AmountUtil();
					List<Map<String, Object>> rateCalculateOneList = au
							.rateCalculateOne(borrowSum, yearRate, deadline,
									isDayThe);
					for (Map<String, Object> oneMap : rateCalculateOneList) {
						String repayPeriod = oneMap.get("repayPeriod") + "";
						String repayDate = oneMap.get("repayDate") + "";
						// 添加还款记录,还款金额和利息在投资时进行累加
						result = borrowManageDao.addRepayRecord(conn,
								repayPeriod, 0, 0, borrowId, 0, 0, repayDate);
					}
				}
			}
			if (result <= 0) {
				conn.rollback();
				return -1L;
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
	 * @MethodName: queryBorrowCirculationDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午02:52:55
	 * @Return:
	 * @Descb: 查询流转标详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowCirculationDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowCirculationDetailById(conn, id);
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

	/**
	 * @MethodName: queryCirculationRepayRecord
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午07:37:39
	 * @Return:
	 * @Descb: 查询流转标还款记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCirculationRepayRecord(long borrowId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = borrowManageDao.queryCirculationRepayRecord(conn, borrowId);
			conn.commit();
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
	 * @MethodName: queryAllCirculationByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:37:27
	 * @Return:
	 * @Descb: 查询流转标借款
	 * @Throws:
	 */
	public void queryAllCirculationByCondition(String userName, int borrowWay,
			int borrowStatus, String undertaker,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		undertaker = Utility.filteSqlInfusion(undertaker);
		
		String resultFeilds = " a.*,b.userid,b.counts,c.userName as undertakerName ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and a.username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(undertaker)) {
			condition
					.append(" and c.userName  LIKE CONCAT('%','"
							+ StringEscapeUtils.escapeSql(undertaker.trim())
							+ "','%')");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and a.borrowStatus =" + borrowStatus);
		}
		condition.append(" and a.borrowShow=2");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_circulation a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid"
							+ " left join t_admin c on a.undertaker=c.id",
					resultFeilds, " order by a.borrowStatus asc,a.id desc",
					condition.toString());
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
	 * @MethodName: queryBorrowFistAuditByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的初审借款记录
	 * @Throws:
	 */
	public void queryBorrowFistAuditByCondition(String userName, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}

		String filed = " a.*,b.counts";

		String table = "(select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userId=b.userid "
				+ " INNER JOIN v_t_user_approve_lists c on a.userId = c.uid where a.borrowWay >2  "
				+ "UNION ALL SELECT  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth "
				+ "where auditStatus = 3  GROUP BY userid) b ON a.userId=b.userid   "
				+ "INNER JOIN v_t_user_base_approve_lists d  on a.userId=d.uuid where a.borrowWay <3 and d.auditStatus=3) t ";
		// ----
		Connection conn = MySQL.getConnection();
		try {
			// 秒还净值个人资料通过审核即可 其它借款需要个人资料+工作认证+5项基本认证
			dataPage(conn, pageBean, table,
			// ---
					resultFeilds, " order by id desc ", condition.toString());
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
	 * add by houli 查询等待资料审核的数据
	 * 
	 * @param userName
	 * @param borrowWay
	 * @param pageBean
	 * @throws Exception
	 * @throws DataException
	 */
	public void queryBorrowWaitingAuditByCondition(String userName,
			int borrowWay, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		String filed = "a.id,a.userId,a.username,a.realName,b.counts as counts,a.borrowWay,a.borrowTitle,"
				+ "a.borrowAmount,a.annualRate,a.deadline,a.raiseTerm,a.isDayThe ,a.borrowShow";
		String table = " (select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userId=b.userid "

				+ " inner join v_t_user_un_approve_lists c on a.userid = c.uid where a.borrowway >2 "
				+ "union all select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a left join (select userid,count(1) as counts from t_materialsauth "
				+ "where auditstatus = 3  group by userid) b on a.userid=b.userid   "
				+ "inner join v_t_user_base_approve_lists d  on a.userid=d.uuid where a.borrowway <3 and d.auditstatus!=3) t";

		Connection conn = MySQL.getConnection();
		try {
			// 秒还净值个人资料通过审核即可 其它借款需要个人资料+工作认证+5项基本认证
			dataPage(conn, pageBean, table, resultFeilds, "order by  id desc ",
					condition.toString());
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
	 * @MethodName: queryBorrowFistAuditDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款初审中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFistAuditDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFistAuditDetailById(conn, id);
			map.put("mailContent", "");
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

	/**
	 * @MethodName: queryBorrowTenderInByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的招标中借款记录
	 * @Throws:
	 */
	public void queryBorrowTenderInByCondition(String userName, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_tenderin a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowTenderInDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款招标中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTenderInDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapNotick = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTenderInDetailById(conn, id);
			if (map != null) {
				long userId = Convert.strToLong(map.get("userId"), -1L);
				mapNotick = noticeTaskDao.queryNoticeTask(conn, userId, id);
				if (mapNotick == null) {
					Map<String, String> maps = noticeTaskDao
							.queryNoticeTasklog(conn, userId, id);
					if(maps!=null){
						map.put("mailContent", Convert.strToStr(maps
								.get("mail_info")
								+ "", ""));
					}
				} else {
					map.put("mailContent", Convert.strToStr(mapNotick
							.get("mail_info")
							+ "", ""));
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
		return map;
	}

	/**
	 * @MethodName: queryBorrowFullScaleByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的满标借款记录
	 * @Throws:
	 */
	public void queryBorrowFullScaleByCondition(String userName, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_fullscale a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowFullScaleDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款满标的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFullScaleDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapNotick = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFullScaleDetailById(conn, id);
			if (map != null) {
				long userId = Convert.strToLong(map.get("userId"), -1L);
				mapNotick = noticeTaskDao.queryNoticeTask(conn, userId, id);
				if (mapNotick == null) {
					Map<String, String> maps = noticeTaskDao
							.queryNoticeTasklog(conn, userId, id);
					if(maps != null){
						map.put("mailContent", Convert.strToStr(maps
								.get("mail_info")
								+ "", ""));
					}
				} else {
					map.put("mailContent", Convert.strToStr(mapNotick
							.get("mail_info")
							+ "", ""));
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
		return map;
	}

	/**
	 * @MethodName: queryBorrowFlowMarkByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的流标借款记录
	 * @Throws:
	 */
	public void queryBorrowFlowMarkByCondition(String userName, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_flowmark a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * add by houli 获得所有等待资料审核的借款
	 * 
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllWaitingBorrow() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		try {
			returnMap = borrowManageDao.queryAllWaitingBorrow(conn);
			conn.commit();
			return returnMap;
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
	 * @MethodName: queryBorrowFlowMarkDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款流标的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFlowMarkDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFlowMarkDetailById(conn, id);
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

	/**
	 * @MethodName: queryBorrowAllByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的借款记录
	 * @Throws:
	 */
	public void queryBorrowAllByCondition(String userName, int borrowWay,
			int borrowStatus, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and borrowStatus =" + borrowStatus);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowAllDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowAllDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowAllDetailById(conn, id);
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

	/**
	 * @throws DataException
	 * @MethodName: updateBorrowFistAuditStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:19:48
	 * @Return:
	 * @Descb: 更新初审中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowFistAuditStatus(long id, long reciver, int status,
			String msg, String auditOpinion, long sender, String basePath, String applyTime)
			throws Exception {
		msg = Utility.filteSqlInfusion(msg);
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		basePath = Utility.filteSqlInfusion(basePath);
		
		Long result = -1L;
		long userId = -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();
		//whb 预约发标时间
		Date applyDate = new Date();
		if(StringUtils.isNotBlank(applyTime)){
			applyDate = DateUtil.strToDate(applyTime);
		}
		// 得到管理员信息
		adminMap = adminDao.queryAdminById(conn, sender);

			try {
				if (status == IConstants.BORROW_STATUS_2) {
					Procedures.p_borrow_audit(conn, ds, outParameterValues, id,
							sender, status, msg, auditOpinion, basePath, applyDate,
							new Date(), 0, "");
					map_ret.put("out_ret", outParameterValues.get(0) + "");
					map_ret.put("out_desc", outParameterValues.get(1) + "");
					result = Convert.strToLong(outParameterValues.get(0) + "", -1);
					map = borrowManageDao.queryBorrowerById(conn, id);
					if(map != null){
						userId = Convert.strToLong(map.get("publisher"), -1);
						userService.updateSign(conn, userId);//更换校验码
					}
					if (result < 1) {
						conn.rollback();
						return -1L;
					}else{
					// 添加操作日志
					operationLogDao.addOperationLog(conn, "t_borrow", Convert
							.strToStr(adminMap.get("userName"), ""),
							IConstants.UPDATE, Convert.strToStr(adminMap
									.get("lastIP"), ""), 0, "初审通过", 2);
					}
				} else {
					//conn.close();
					// 撤消初审中的借款
					result = reBackBorrowFistAudit(conn,id, sender, basePath, msg,
							auditOpinion);
					// Procedures.p_borrow_cancel(conn, ds, outParameterValues, id,
					// sender, status, auditOpinion, basePath, 0, "");
					// map_ret.put("out_ret", outParameterValues.get(0)+"");
					// map_ret.put("out_desc", outParameterValues.get(1)+"");
					// 添加操作日志
					// operationLogDao.addOperationLog(conn, "t_borrow",
					// Convert.strToStr(adminMap.get("userName"), ""),
					// IConstants.UPDATE, Convert.strToStr(adminMap.get("lastIP"), ""),
					// 0, "管理员撤销借款", 2);
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
	 * @throws DataException
	 * @MethodName: updateBorrowTenderInStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:20:27
	 * @Return:
	 * @Descb: 更新招标中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowTenderInStatus(long id, String auditOpinion)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = borrowManageDao.updateBorrowTenderInStatus(conn, id,
					auditOpinion);
			if (result <= 0) {
				conn.rollback();
				return -1L;
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
	 * @throws Exception
	 * @throws DataException
	 * @throws Exception
	 * @MethodName: reBackBorrowTenderIn
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午08:40:22
	 * @Return:
	 * @Descb: 撤消借款
	 * @Throws:
	 */
	public long reBackBorrow(Connection conn ,long id, long aId, String basePath)
			throws Exception {
		basePath = Utility.filteSqlInfusion(basePath);
		
		long returnId = -1;
		ContextLoader.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(
						IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, id, aId,
					6, "", basePath, -1, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			returnId = Convert.strToLong(outParameterValues.get(0) + "", -1);
			
			borrowUserMap = borrowManageDao.queryBorrowerById(conn, id);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowManageDao.queryInvesterById(conn, id);
			if(investorList != null){
				for(Map<String, Object> map : investorList){
					long userId = Convert.strToLong(map.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					map = null;
				}
			}
			
			// 添加操作日志
			adminMap = adminDao.queryAdminById(conn, aId);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
					adminMap.get("userName"), ""), IConstants.UPDATE, Convert
					.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
		return returnId;
	}
	
	public long reBackBorrow(long id, long aId, String basePath)
	throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		try {
			returnId = reBackBorrow(conn ,id, aId, basePath);
			if (returnId < 1) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			return -1L;
		} finally {
			conn.close();
		}
		return returnId;
	}
	
	/**
	 * @throws Exception
	 * @MethodName: updateBorrowFullScaleStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:21:09
	 * @Return:
	 * @Descb: 更新满标的借款状态
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> updateBorrowFullScaleStatus(long id,
			long status, String auditOpinion, long adminId, String basePath)
			throws Exception {
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		basePath = Utility.filteSqlInfusion(basePath);
		
		Connection conn = MySQL.getConnection();
		double investFeeRate = 0;// 投资管理费
		double borrowFeeRateOne = 0;// 秒还借款管理费
		double borrowInmonthFeeRateOne = 0;// 净值借款2个月内管理费率
		double borrowOutmonthFeeRateOne = 0;// 净值借款2个月外管理费率
		double borrowDayFeeRateOne = 0;// 净值借款天标管理费率
		double borrowInmonthFeeRateTwo = 0;// 信用借款2个月内管理费率
		double borrowOutmonthFeeRateTwo = 0;// 信用借款2个月外管理费率
		double borrowDayFeeRateTwo = 0;// 信用借款天标管理费率
		double borrowInmonthFeeRateThree = 0;// 机构担保借款2个月内管理费率
		double borrowOutmonthFeeRateThree = 0;// 机构担保借款2个月外管理费率
		double borrowDayFeeRateThree = 0;// 机构担保借款天标管理费率
		double borrowInmonthFeeRateFour = 0;// 实地考察借款2个月内管理费率
		double borrowOutmonthFeeRateFour = 0;// 实地考察借款2个月外管理费率
		double borrowDayFeeRateFour = 0;// 实地考察借款天标管理费率
		String identify = id + "_" + System.currentTimeMillis() + "";
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		List<Object> outParameters = new ArrayList<Object>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
		
		try {
			// 满标审核前判断处理
			Procedures.p_borrow_auth_fullscale(conn, ds, outParameterValues,
					id, status, -1, "", new BigDecimal(0.00), new BigDecimal(
							0.00), 0, 0, 0);
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret <= 0) {
				map.put("ret", ret + "");
				map.put("ret_desc", outParameterValues.get(1) + "");
				conn.rollback();
				return map;
			}
			// 审核通过才生成还款记录
			if (ret == 4) {
				double borrowAmount = Convert.strToDouble(outParameterValues
						.get(2)
						+ "", 0);
				double annualRate = Convert.strToDouble(outParameterValues
						.get(3)
						+ "", 0);
				int deadline = Convert.strToInt(outParameterValues.get(4) + "",
						0);
				int isDayThe = Convert.strToInt(outParameterValues.get(5) + "",
						1);
				int paymentMode = Convert.strToInt(outParameterValues.get(6)
						+ "", 1);

				// 生成还款记录
				List<Map<String, Object>> repayMapList = null;
				AmountUtil au = new AmountUtil();
				if (paymentMode == 1) {
					// 按月等额还款
					repayMapList = au.rateCalculateMonth(borrowAmount,
							annualRate, deadline, isDayThe);
				} else if (paymentMode == 2) {
					// 先息后本还款
					repayMapList = au.rateCalculateSum(borrowAmount,
							annualRate, deadline, isDayThe);
				} else if (paymentMode == 3) {
					// 秒还还款
					repayMapList = au.rateSecondsSum(borrowAmount, annualRate,
							deadline);
				}// add by c_j 13.07.25增加一次性还款
				else if (paymentMode == 4) {
					repayMapList = au.rateCalculateOne(borrowAmount,
							annualRate, deadline, isDayThe);
				}
				String repayPeriod = ""; // 还款期数
				double stillPrincipal = 0; // 应还本金
				double stillInterest = 0; // 应还利息
				double principalBalance = 0; // 剩余本金
				double interestBalance = 0; // 剩余利息
				double totalSum = 0; // 本息余额
				double totalAmount = 0; // 还款总额
				double mRate = 0; // 月利率
				String repayDate = "";
				int count = 1;
				for (Map<String, Object> paymentMap : repayMapList) {
					repayPeriod = paymentMap.get("repayPeriod") + "";
					stillPrincipal = Convert.strToDouble(paymentMap
							.get("stillPrincipal")
							+ "", 0);
					stillInterest = Convert.strToDouble(paymentMap
							.get("stillInterest")
							+ "", 0);
					principalBalance = Convert.strToDouble(paymentMap
							.get("principalBalance")
							+ "", 0);
					interestBalance = Convert.strToDouble(paymentMap
							.get("interestBalance")
							+ "", 0);
					totalSum = Convert.strToDouble(paymentMap.get("totalSum")
							+ "", 0);
					totalAmount = Convert.strToDouble(paymentMap
							.get("totalAmount")
							+ "", 0);
					repayDate = paymentMap.get("repayDate") + "";
					mRate = Convert
							.strToDouble(paymentMap.get("mRate") + "", 0);
					// 添加预还款记录
					ret = repamentDao.addPreRepament(conn, id, identify,
							repayPeriod, stillPrincipal, stillInterest,
							principalBalance, interestBalance, totalSum,
							totalAmount, mRate, repayDate, count);
					count++;
					if (ret <= 0) {
						break;
					}
				}

				if (ret <= 0) {
					map.put("ret", ret + "");
					map.put("ret_desc", "执行失败");
					conn.rollback();
					return map;
				}
				// 查询借款信息得到借款时插入的平台收费标准
				Map<String, String> mapacc = borrowDao
						.queryBorrowCost(conn, id);
				String feelog = Convert.strToStr(mapacc.get("feelog"), "");
				Map<String, Double> feeMap = (Map<String, Double>) JSONObject
						.toBean(JSONObject.fromObject(feelog), HashMap.class);
				investFeeRate = Convert.strToDouble(feeMap
						.get(IAmountConstants.INVEST_FEE_RATE)
						+ "", 0);
				borrowFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_FEE_RATE_1)
						+ "", 0);
				borrowInmonthFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_1)
						+ "", 0);
				borrowOutmonthFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_1)
						+ "", 0);
				borrowDayFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_1)
						+ "", 0);
				borrowInmonthFeeRateTwo = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_2)
						+ "", 0);
				borrowOutmonthFeeRateTwo = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_2)
						+ "", 0);
				borrowDayFeeRateTwo = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_2)
						+ "", 0);
				borrowInmonthFeeRateThree = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_3)
						+ "", 0);
				borrowOutmonthFeeRateThree = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_3)
						+ "", 0);
				borrowDayFeeRateThree = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_3)
						+ "", 0);
				borrowInmonthFeeRateFour = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_4)
						+ "", 0);
				borrowOutmonthFeeRateFour = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_4)
						+ "", 0);
				borrowDayFeeRateFour = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_4)
						+ "", 0);
			}

			// 满标审核处理
			Procedures.p_borrow_deal_fullscale(conn, ds, outParameters, id,
					adminId, status, new Date(), auditOpinion, identify,
					basePath, new BigDecimal(investFeeRate), new BigDecimal(
							borrowFeeRateOne), new BigDecimal(
							borrowInmonthFeeRateOne), new BigDecimal(
							borrowOutmonthFeeRateOne), new BigDecimal(
							borrowDayFeeRateOne), new BigDecimal(
							borrowInmonthFeeRateTwo), new BigDecimal(
							borrowOutmonthFeeRateTwo), new BigDecimal(
							borrowDayFeeRateTwo), new BigDecimal(
							borrowInmonthFeeRateThree), new BigDecimal(
							borrowOutmonthFeeRateThree), new BigDecimal(
							borrowDayFeeRateThree), new BigDecimal(
							borrowInmonthFeeRateFour), new BigDecimal(
							borrowOutmonthFeeRateFour), new BigDecimal(
							borrowDayFeeRateFour), -1, "");
			ret = Convert.strToLong(outParameters.get(0) + "", -1);
			if (ret > 0 && status == 4) {
				// 添加系统操作日志
				adminMap = adminDao.queryAdminById(conn, adminId);
				operationLogDao.addOperationLog(conn, "t_borrow", Convert
						.strToStr(adminMap.get("userName"), ""),
						IConstants.UPDATE, Convert.strToStr(adminMap
								.get("lastIP"), ""), 0, "满标复审通过", 2);
				// 提成奖励
				DataSet ds1 = MySQL.executeQuery(conn," select DISTINCT a.id as id,a.investor as userId,a.realAmount as realAmount,c.publisher as publisher from t_invest a left join t_repayment b on a.borrowId = b.borrowId  left join t_borrow c on a.borrowId = c.id where c.id ="
										+ id);
				ds1.tables.get(0).rows.genRowsMap();
				List<Map<String, Object>> list = ds1.tables.get(0).rows.rowsMap;
				for (Map<String, Object> map2 : list) {
					long uId = Convert.strToLong(map2.get("userId") + "", -1);
					long investId = Convert.strToLong(map2.get("id") + "", -1);
					Object obj = map2.get("realAmount");
					BigDecimal amounts = BigDecimal.ZERO;
					if (obj != null) {
						amounts = new BigDecimal(obj + "");
					}
					ret = awardService.updateMoneyNew(conn, uId, amounts,
							IConstants.MONEY_TYPE_1, investId);
				}

			}
			map.put("ret", ret + "");
			map.put("ret_desc", outParameters.get(1) + "");
			borrowUserMap = borrowManageDao.queryBorrowerById(conn, id);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowManageDao.queryInvesterById(conn, id);
			if(investorList != null){
				for(Map<String, Object> investorMap : investorList){
					long userId = Convert.strToLong(investorMap.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
				}
			}
			if (ret <= 0) {
				conn.rollback();
			} else {
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
		return map;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public long reBackBorrowFistAudit(Connection conn,long idLong, Long id, String basePath,
			String msg, String auditOpinion) throws Exception {
		basePath = Utility.filteSqlInfusion(basePath);
		msg = Utility.filteSqlInfusion(msg);
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		
		//Connection conn = MySQL.getConnection();
		long returnId = -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
		
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, idLong,
					id, 6, auditOpinion, basePath, -1, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			returnId = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (returnId <= 0) {
				conn.rollback();
				return Convert.strToLong(map_ret.get("out_ret"), -1);
			}
			
			borrowUserMap = borrowManageDao.queryBorrowerById(conn, idLong);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowManageDao.queryInvesterById(conn, idLong);
			if(investorList != null){
				for(Map<String, Object> map : investorList){
					long userId = Convert.strToLong(map.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					map = null;
				}
			}
			// 添加操作日志
			adminMap = adminDao.queryAdminById(conn, id);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
					adminMap.get("userName"), ""), IConstants.UPDATE, Convert
					.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
			
		
		return Convert.strToLong(map_ret.get("out_ret"), 1);
	}
	/**
	 * 重载上面方法
	 * @param idLong
	 * @param id
	 * @param basePath
	 * @param msg
	 * @param auditOpinion
	 * @return
	 * @throws Exception
	 */
	public long reBackBorrowFistAudit(long idLong, Long id, String basePath,
			String msg, String auditOpinion) throws Exception {
		long result=-1;
		Connection conn = MySQL.getConnection();
		try {
			result = reBackBorrowFistAudit(conn, idLong, id, basePath, msg, auditOpinion);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	/**
	 * @MethodName: addCirculationRepay
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午04:32:03
	 * @Return:
	 * @Descb: 添加流转标还款记录
	 * @Throws:
	 */
	public long addCirculationRepay(long repayId, double amountDouble, Long id,
			String remark) throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		try {
			returnId = repaymentRecordDao.addRepayMentRecord(conn, repayId,
					amountDouble, id, remark);
			if (returnId <= 0) {
				conn.rollback();
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
		return returnId;
	}
	
	public long addBorrowPushHis(long borrowId,int status) throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		try {
			returnId = borrowManageDao.addBorrowPushHis(conn, borrowId,status);
			if (returnId <= 0) {
				conn.rollback();
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
		return returnId;
	}
	
	public long updateBorrowPushHis(long id,int status) throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		try {
			returnId = borrowManageDao.updateBorrowPushHis(conn, id,status);
			if (returnId <= 0) {
				conn.rollback();
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
		return returnId;
	}

	public Map<String, String> queryBorrowInfo(long idLong) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowInfo(conn, idLong);
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

	/**
	 * 查看借款协议中的内容
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowMany(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryBorrowMany(conn, borrowId);
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
	
	/**
	 * 根据所借款判断借款人是否为小贷公司下的借款人
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> queryUsreType(long borrowId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryUsreType(conn, borrowId);
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
	
	
	/**
	 * 查询合作机构信息
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findLoanCompany(Long adminId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryLoanCompany(conn,adminId);
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
	 * 查询担保机构信息
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findBondingCompany(long loanId,long bondingId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryBondingCompany(conn,loanId,bondingId);
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
	 * 查询用户基本信息
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findUserBase(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.findUserBase(conn,userId);
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
	 * 查询用户可用余额
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findUserAbleMoney(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.findUserAbleMoney(conn,userId);
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
	 * 更新用户可用余额--扣除渠道费比例金额后的
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public long updateUserAbleMoney(Long userId,double userFinallyAbleMoney,long borrowId,String borrowName,long loanId,String loanName,double channelCost,Date createTime) throws Exception {
		Connection conn = MySQL.getConnection();
		long m = -1;
		long n = -1;
		try {
			m = userDao.updateUserAbleMoney(conn,userId,userFinallyAbleMoney);
			n = userDao.addChannelCostRecond(conn, borrowId,borrowName, loanId,loanName, channelCost, createTime);
			if(m>0 && n>0){
				conn.commit();
			}
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
	
	/***
	 * 根据用户名查询配置好的的存放渠道费金额的用户
	 * @param conn
	 * @param uName
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> findUserByuName(String uName) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.findUserByuName(conn,uName);
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
			userDao.updateLoanCompanyMoney(conn,id,availableTotalAmountSum,hasTotalAmountSum);
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
	
	/**
	 * 更新存渠道费金额的用户的可用余额
	 * @param userId
	 * @param userFinallyAbleMoney
	 * @param borrowId
	 * @param borrowName
	 * @param loanId
	 * @param loanName
	 * @param channelCost
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	public  void updateUserMoney(Long uId,double money) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			userDao.updateUserAbleMoney(conn,uId,money);
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
	/**
	 * 添加合作机构偿债
	 * @param loan_id
	 * @param borrow_id
	 * @param pay_debt_money
	 * @param creat_time
	 * @return
	 * @throws Exception
	 */
	 public long addPayDebtMoney(long loan_id,long borrow_id,double pay_debt_money,Date creat_time)throws Exception {
		 Connection conn = MySQL.getConnection();
		 try {
				Map<String,String> payDebtMap = userDao.findPayDebt(conn, loan_id);
				if(payDebtMap!=null && payDebtMap.size()>0){
					long debtId = Long.parseLong(payDebtMap.get("id"));
					double pay_debt_money_sum = Double.parseDouble(payDebtMap.get("pay_debt_money_sum"))+pay_debt_money; 
					userDao.updatePayDebt(conn, pay_debt_money_sum, debtId);
					userDao.addPayDebtRecode(conn, pay_debt_money, borrow_id, debtId, creat_time);
				}else{
					long id = userDao.addPayDebt(conn, pay_debt_money, loan_id, creat_time);
					userDao.addPayDebtRecode(conn, pay_debt_money, borrow_id, id, creat_time);
				}
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				
				throw e;
			} finally {
				conn.close();
			}
		 return 0;
	 }
	 
	 /***
	  * 添加渠道费借款人的资金记录
	  * @param userId
	  * @param fundMode
	  * @param handleSum
	  * @param usableSum
	  * @param remarks
	  * @param oprateType
	  * @param speeding
	  * @throws Exception
	  */
	 public void saveChanncelCostFundrecode(long userId,String fundMode,double handleSum,double usableSum,String remarks,double speeding)throws Exception {
		    Connection conn = MySQL.getConnection();
			try {
				userDao.saveChanncelCostFundrecode(conn, userId, fundMode, handleSum, usableSum, remarks, speeding);
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
	 
	 public void saveChanncelCostFundrecode1(long userId,String fundMode,double handleSum,double usableSum,String remarks,double income)throws Exception {
		    Connection conn = MySQL.getConnection();
			try {
				userDao.saveChanncelCostFundrecode1(conn, userId, fundMode, handleSum, usableSum, remarks, income);
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
	  * 添加偿债金比例借款人的资金记录
	  * @param userId
	  * @param fundMode
	  * @param handleSum
	  * @param usableSum
	  * @param remarks
	  * @param oprateType
	  * @param speeding
	  * @throws Exception
	  */
	 public void savePayDebtFundrecode(long userId,String fundMode,double handleSum,double usableSum,String remarks,double speeding)throws Exception {
		    Connection conn = MySQL.getConnection();
			try {
				userDao.saveChanncelCostFundrecode(conn, userId, fundMode, handleSum, usableSum, remarks, speeding);
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				conn.close();
			}
	 }

	/**
	 * 根据借款id 和投资id 查询
	 * 
	 * @param borrowId
	 * @param invest_id
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryInvestMomey(long borrowId,
			long invest_id, long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = null;
		try {
			map = investDao.queryInvestMomey(conn, invest_id, borrowId, userId);
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

	/**
	 * 查询所有投资人信息
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUsername(long userId, long invest_id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = investDao.queryUsername(conn, userId, invest_id);
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
	
	/**
	 * 查询投资人信息whb
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String,String> queryUsernameNow(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = investDao.queryUsernameNow(conn, userId);
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

	/**
	 * 根据借款查询借款应还的金额
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowSumMomeny(long borrowId,
			long invest_id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryBorrowSumMomeny(conn, borrowId, invest_id);
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

	// 借款管理模块中，查询各个报表的总额
	public Map<String, String> queryBorrowTotalFistAuditDetail()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalFistAuditDetail(conn);
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

	public Map<String, String> queryBorrowTotalWait() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalWait(conn);
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

	public Map<String, String> queryBorrowTotalTenderDetail() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalTenderDetail(conn);
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

	public Map<String, String> queryBorrowFlowMarkDetail() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFlowMarkDetail(conn);
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

	public Map<String, String> queryBorrowTotalFullScaleDetail()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalFullScaleDetail(conn);
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

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public BorrowManageDao getBorrowManageDao() {
		return borrowManageDao;
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public RepamentDao getRepamentDao() {
		return repamentDao;
	}

	public void setRepamentDao(RepamentDao repamentDao) {
		this.repamentDao = repamentDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setRepaymentRecordDao(RepaymentRecordDao repaymentRecordDao) {
		this.repaymentRecordDao = repaymentRecordDao;
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public PlatformCostService getPlatformCostService() {
		return platformCostService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public RepaymentRecordDao getRepaymentRecordDao() {
		return repaymentRecordDao;
	}

	public NoticeTaskDao getNoticeTaskDao() {
		return noticeTaskDao;
	}

	public void setNoticeTaskDao(NoticeTaskDao noticeTaskDao) {
		this.noticeTaskDao = noticeTaskDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public Map<String, String> queryAuthProtocol(long borrowId, long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryAuthProtocol(conn,borrowId,investId);
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

	/**
	 * 后台发布借款
	 * addBorrowByAdmin
	 * @param title
	 * @param imgPath
	 * @param borrowWay
	 * @param purpose
	 * @param deadLine
	 * @param paymentMode
	 * @param amount
	 * @param annualRate
	 * @param minTenderedSum
	 * @param maxTenderedSum
	 * @param raiseTerm
	 * @param excitationType
	 * @param sum
	 * @param detail
	 * @param excitationMode
	 * @param investPWD
	 * @param hasPWD
	 * @param remoteIP
	 * @param publisher
	 * @param fee
	 * @param daythe
	 * @param basePath
	 * @param username
	 * @param smallestFlowUnit
	 * @param circulationNumber
	 * @param hasCirculationNumber
	 * @param subscribe_status
	 * @param nid_log
	 * @param frozen_margin
	 * @param json
	 * @param jsonState
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-5 上午08:59:34
	 */
	public Map<String,String> addBorrowByAdmin(String title, String imgPath, int borrowWay,
			int purpose, int deadLine, int paymentMode, double amount,
			double annualRate, double minTenderedSum, double maxTenderedSum,
			int raiseTerm, int excitationType, double sum, String detail,
			int excitationMode, String investPWD, int hasPWD, String remoteIP,
			long publisher, double fee, int daythe, String basePath,
			String username, double smallestFlowUnit, int circulationNumber,
			int hasCirculationNumber, int subscribe_status, String nid_log,
			double frozen_margin, String json, String jsonState,int borrowType,Date publishTime,long applyId,long userId)
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
			Procedures.p_borrow_initialization_admin(conn, ds, outParameterValues,
					publisher, title, imgPath, borrowWay, "", deadLine,
					paymentMode, new BigDecimal(amount), new BigDecimal(
							annualRate), new BigDecimal(minTenderedSum),
					new BigDecimal(maxTenderedSum), new BigDecimal(raiseTerm),
					detail, 1, publisher, excitationType, new BigDecimal(sum),
					excitationMode, purpose, hasPWD, investPWD, publishTime,
					remoteIP, daythe, new BigDecimal(smallestFlowUnit),
					circulationNumber, nid_log, new BigDecimal(frozen_margin),
					"", basePath, new BigDecimal(fee), json, jsonState, "", "",
					"", "", "", borrowShow,borrowType,applyId,userId, 0, "");
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
	 * 
	 * queryPersonageApplyList
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 上午09:15:41
	 */
	public void queryPersonageApplyList(PageBean<Map<String, Object>> pageBean,
			String name, String telephone,double borrowAmount) throws Exception {
		name = Utility.filteSqlInfusion(name);
		telephone = Utility.filteSqlInfusion(telephone);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(name)) {
				condition.append(" and tname  like '%"
						+ StringEscapeUtils.escapeSql(name.trim()) + "%' ");
			}
			
			if (StringUtils.isNotBlank(telephone)) {
				condition.append(" and telephone like '%"
						+ StringEscapeUtils.escapeSql(telephone.trim()) + "%' ");
			}

			if( borrowAmount > 0){
				condition.append(" and borrowAmount = " + borrowAmount);
			}
			
			dataPage(conn, pageBean, "v_t_personage_apply", "*",
					" order by id ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryEnterpriseApplyList(PageBean<Map<String, Object>> pageBean,
			String name, String telephone,double borrowAmount) throws Exception {
		name = Utility.filteSqlInfusion(name);
		telephone = Utility.filteSqlInfusion(telephone);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(name)) {
				condition.append(" and tname  like '%"
						+ StringEscapeUtils.escapeSql(name.trim()) + "%' ");
			}
			
			if (StringUtils.isNotBlank(telephone)) {
				condition.append(" and telephone like '%"
						+ StringEscapeUtils.escapeSql(telephone.trim()) + "%' ");
			}

			if( borrowAmount > 0){
				condition.append(" and borrowAmount = " + borrowAmount);
			}
			
			dataPage(conn, pageBean, "v_t_enterprise_apply", "*",
					" order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryApplyList(PageBean<Map<String, Object>> pageBean,
			String name, String telephone,double borrowAmount) throws Exception {
		name = Utility.filteSqlInfusion(name);
		telephone = Utility.filteSqlInfusion(telephone);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(name)) {
				condition.append(" and tname  like '%"
						+ StringEscapeUtils.escapeSql(name.trim()) + "%' ");
			}
			
			if (StringUtils.isNotBlank(telephone)) {
				condition.append(" and telephone like '%"
						+ StringEscapeUtils.escapeSql(telephone.trim()) + "%' ");
			}

			if( borrowAmount > 0){
				condition.append(" and borrowAmount = " + borrowAmount);
			}
			
			dataPage(conn, pageBean, "t_apply", "*",
					" order by id ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	public Map<String, String> queryUserBaseInfo(long applyId)
	throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryUserBaseInfo(conn,applyId);
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
	
	/**
	 * 获取用户表的下一个ID
	 * getUserId
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 下午02:43:13
	 */
	public long getUserId() throws Exception{
		Connection conn = MySQL.getConnection();
		long userId = -1L;
		try {
			userId = MySQL.executeNonQuery(conn, "insert into t_user (username) values ('')");
			MySQL.executeNonQuery(conn, "delete from t_user where id=" + userId);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		return userId;
	}
	
	/**
	 * 根据ID删除申请记录
	 * deleteApplyById
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-10 下午01:25:42
	 */
	public long deleteApplyById(long id)
	throws Exception {
			Connection conn = MySQL.getConnection();
			long result = -1L;
			try {
				result = borrowManageDao.deleteApplyById(conn,id);
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
	public Map<String, String> queryBorrowFistAuditDetailById2(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFistAuditDetailById2(conn, id);
			map.put("mailContent", "");
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
}
