package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.BonusDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RecommendUserCountDao;
import com.sp2p.dao.RepayMentDao;
import com.sp2p.dao.SettingActivityDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: FinanceService.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:14:21
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 理财业务处理层
 */
public class FinanceService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private FinanceDao financeDao;
	private AwardService awardService;
	private SelectedService selectedService;
	private UserDao userDao;
	private OperationLogDao operationLogDao;
	private RepayMentDao repayMentDao;
	private InvestDao investDao;
	private BorrowDao borrowDao;
	private BonusDao bonusDao;
	
	private RecommendUserCountDao recommendUserCountDao;
	private SettingActivityDao settingActivityDao;

	public RecommendUserCountDao getRecommendUserCountDao() {
		return recommendUserCountDao;
	}

	public void setRecommendUserCountDao(RecommendUserCountDao recommendUserCountDao) {
		this.recommendUserCountDao = recommendUserCountDao;
	}

	public SettingActivityDao getSettingActivityDao() {
		return settingActivityDao;
	}

	public void setSettingActivityDao(SettingActivityDao settingActivityDao) {
		this.settingActivityDao = settingActivityDao;
	}

	private SMSInterfaceService sMsService;
	
	/**
	 * @MethodName: queryBorrowByCondition
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午05:01:31
	 * @Return:
	 * @Descb: 根据条件查询借款信息
	 * @Throws:
	 */
	public void queryBorrowByCondition(String borrow_t,String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order,
			PageBean pageBean) throws Exception {
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		title = Utility.filteSqlInfusion(title);
		paymentMode = Utility.filteSqlInfusion(paymentMode);
		purpose = Utility.filteSqlInfusion(purpose);
		deadline = Utility.filteSqlInfusion(deadline);
		reward = Utility.filteSqlInfusion(reward);
		arStart = Utility.filteSqlInfusion(arStart);
		arEnd = Utility.filteSqlInfusion(arEnd);
		order = Utility.filteSqlInfusion(order);
		
		String resultFeilds = " id,minTenderedSum,remainAmount,countPer,borrowShow,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus,paymentMode,"
				+ "add_interest as add_interest_src,IF(hasPWD=1,0,add_interest) as add_interest";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 ");
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in"
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in"
					+ StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(paymentMode)
				&& StringUtils.isNumericSpace(paymentMode)) {
			condition.append(" and paymentMode ="
					+ StringEscapeUtils.escapeSql(paymentMode));
		}
		if (StringUtils.isNotBlank(purpose)
				&& StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose ="
					+ StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline)
				&& StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline ="
					+ StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward)
				&& StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType ="
						+ StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart)
				&& StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount >= "
					+ StringEscapeUtils.escapeSql(arStart));
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <"
					+ StringEscapeUtils.escapeSql(arEnd));
		}
		if (StringUtils.isNotBlank(borrow_t)) {
			condition.append(" and borrowType ="
					+ StringEscapeUtils.escapeSql(borrow_t));
		}
		
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds,
					" order by sorts desc,schedules asc ,id desc ", condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
public List<Map<String, Object>> queryBorrowAllTime(String borrowWay) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryBorrowAllTime(conn, borrowWay);
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
	 * @MethodName: queryBorrowAll
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 查询全部借款
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowAll(String borrow_t,String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryBorrowAll(conn, borrow_t, borrowStatus, borrowWay,
					title, paymentMode, purpose, deadline, reward, arStart, arEnd, order);
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
	 * 投资成功发送站内信
	 * @param userId
	 * @param amount
	 * @param borrowTitle
	 * @throws Exception
	 */
	public void addInvestMail(Long userId, double amount,String borrowTitle) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			// 发送通知，通过通知模板  写入service层 在action层调用
			Map<String, Object> informTemplateMap =(Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);;

			Map<String,String> noticeMap = new HashMap<String, String>();

			// 竞拍者消息提醒
			// 消息模版
			// 站内信
			String informTemplate = informTemplateMap.get(
					IInformTemplateConstants.TENDER).toString();
			informTemplate = informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			informTemplate = informTemplate.replace("voluntarily","手动");
			
			informTemplate = informTemplate.replace("title",borrowTitle);
			informTemplate = informTemplate.replace("investAmount", amount+ "");
			noticeMap.put("mail", informTemplate);

			selectedService.sendNoticeMSG(conn, userId,
					"投资成功", noticeMap, IConstants.NOTICE_MODE_5);
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
	
	
	
	public Map<String, String> queryBorrowById( long borrowId) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowById(conn, borrowId);
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
	 * @MethodName: queryBorrowList
	 * @Param: 列表类型：type=success/new（success为成交标的列表，new为新增标的列表）
	 * @Author: whb
	 * @Return: List<Map<String, Object>>
	 * @Descb: 查询借款列表(米袋360)
	 * @Throws: SQLException, DataException
	 */
	public List<Map<String, Object>> queryBorrowList(String type, String date, String dateNext) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = financeDao.queryBorrowList(conn, type, date, dateNext);
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
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:18:19
	 * @Return:
	 * @Descb: 根据ID查询借款详细信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowDetailById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Map<String, String> queryBorrowDebtDetailById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowDetailById(conn, id);
			String borrowTitle = String.valueOf(map.get("borrowTitle"));
			borrowTitle = "交易宝"+borrowTitle.substring(3, borrowTitle.length());
			map.put("borrowTitle", borrowTitle);
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
	 * 查询单一投资人对单一标的投资总额
	 * @param conn
	 * @param userId
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryInvest(long userId,long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryInvest(conn, userId,borrowId);
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
	 * 2016-06-29
	 * @param liugang
	 * 七月份活动
	 */
	public Map<String, String> searchInvest(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.searchInvest(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	public Map<String, String> queryBorrow(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrow(conn,borrowId);
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
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 根据ID查询借款详细信息
	 * @Throws:
	 */
	public Map<String, String> queryInvestPerson(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryInvestPerson(conn, id);
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
	 * @MethodName: queryUserInfoById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:54
	 * @Return:
	 * @Descb: 根据ID查询借款信息发布者个人信息
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoById(conn, id);
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
	 * 根据ID查询借款信息发布者个人信息(后台发布的借款)
	 * queryUserInfoByIdAdmin
	 * @param id
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-5 下午05:21:04
	 */
	public Map<String, String> queryUserInfoByIdAdmin(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoByIdAdmin(conn, id);
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
	 * 根据ID查询借款信息发布者企业信息(后台发布的借款)
	 * queryEnterpriseUserInfoById
	 * @param id
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-10 上午09:35:11
	 */
	public Map<String, String> queryEnterpriseUserInfoById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryEnterpriseUserInfoById(conn, id);
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
	 * @MethodName: queryUserIdentifiedByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByid(conn, id);
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
	 * 根据ID查询用户认证信息(后台发布的借款)
	 * queryUserIdentifiedByidAdmin
	 * @param id
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-5 下午05:29:30
	 */
	public List<Map<String, Object>> queryUserIdentifiedByidAdmin(long id)
	throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByidAdmin(conn, id);
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
	 * @MethodName: queryUserImageByid
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:01:28
	 * @Return:
	 * @Descb: 查询用户认证图片
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserImageByid(long typeId, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserImageByid(conn, typeId, userId);
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
	 * 项目详情查看抵押物
	 * @param typeId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryUserImageByidDyw(long typeId, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserImageByidDyw(conn, typeId, userId);
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
	 * @MethodName: queryPaymentByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:03:01
	 * @Return:
	 * @Descb: 根据ID查询本期还款信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRePayByid(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryRePayByid(conn, id);
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
	 * @MethodName: queryCollectionByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:04:28
	 * @Return:
	 * @Descb: 根据ID查询本期催收信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCollectionByid(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryCollectionByid(conn, id);
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
	 * @MethodName: queryInvestByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:06:00
	 * @Return:
	 * @Descb: 根据ID查询投资记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryInvestByid(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryInvestByid(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	public Map<String, String> queryGroupById(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = financeDao.queryGroupById(conn,userId);
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
	 * @MethodName: queryInvestByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:06:00
	 * @Return:
	 * @Descb: 根据ID查询投资记录 whb
	 * @Throws:
	 */
	public Map<String, String> queryDebtById(long borrowId, long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = financeDao.queryDebtById(conn, borrowId, investId);
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
	 * @MethodName: queryBorrowMSGBord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:30:26
	 * @Return:
	 * @Descb: 根据ID查询留言板信息
	 * @Throws:
	 */
	public void queryBorrowMSGBord(long id, PageBean pageBean) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_msgbord", " * ",
					" order by id desc ", " and modeId=" + id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @Author: whb
	 * @Return:
	 * @Descb: 添加超级用户显示名称变换
	 * @Throws:
	 */
	public Long updateChangeUsername(long investId, String userName) throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1L;
		try {
			returnId = financeDao.updateChangeUsername(conn, investId, userName);
			if(returnId > 0){
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return returnId;
	}
	
	/**
	 * @MethodName: addBrowseCount
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:54:02
	 * @Return:
	 * @Descb: 添加浏览量处理
	 * @Throws:
	 */
	public void addBrowseCount(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1L;
		try {
			returnId = financeDao.addBrowseCount(conn, id);
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
	 * @throws DataException
	 * @MethodName: addBorrowMSG
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午08:16:45
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public long addBorrowMSG(long id, long userId, String msgContent)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long returnId = -1;
		try {
			returnId = financeDao.addBorrowMSG(conn, id, userId, msgContent);
			if (returnId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_msgboard", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "添加借款留言", 1);
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

	/**
	 * @MethodName: getInvestStatus
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:46:17
	 * @Return:
	 * @Descb: 获取借款的投标状态,条件是正在招标中
	 * @Throws:
	 */
	public Map<String, String> getInvestStatus(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.getInvestStatus(conn, id);
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
	 * @MethodName: getInvestToFbaba
	 * @Return:
	 * @Descb: 获取借款投标的信息
	 * @Throws:
	 */
	public Map<String, String> getInvestToFbaba(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.getInvestToFbaba(conn, id);
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
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 获取推荐的用户注册后多久投资
	 * @Throws:
	 */
	public Map<String, String> getFbabaUser(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.getFbabaUser(conn, id);
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
	 * @MethodName: valideInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:59
	 * @Return:
	 * @Descb: 验证投资人是否符合本次投标
	 * @Throws:
	 */
	public String valideInvest(long id, long userId, double amount)
			throws Exception {
		Connection conn = MySQL.getConnection();
		String result = "";
		try {
			result = financeDao.valideInvest(conn, id, userId, amount);
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
	 * @MethodName: valideTradePassWord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:43
	 * @Return:
	 * @Descb: 验证交易密码
	 * @Throws:
	 */
	public String valideTradePassWord(long userId, String pwd) throws Exception {
		Connection conn = MySQL.getConnection();
		String result = "";
		try {
			result = financeDao.valideTradePassWord(conn, userId, pwd);
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
	 * @MethodName: queryBorrowInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午07:30:26
	 * @Return:
	 * @Descb: 根据ID获取投资的借款信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInvest(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowInvest(conn, id);
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
	 * @Param: FinanceService
	 * @Author: whb
	 * @Date: 2015-1-7 下午15:20:26
	 * @Return:
	 * @Descb: 根据借款id和投资人查找投资id
	 * @Throws:
	 */
	public String queryInvestId(long borrowId, long investor) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		String investId = "";
		try {
			map = financeDao.queryInvestId(conn, borrowId, investor);
			if(map != null && map.size() > 0){
				investId = map.get("investId");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		return investId;
	}

	/**
	 * @MethodName: queryUserMonney
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午08:48:43
	 * @Return:
	 * @Descb: 查询用户的金额
	 * @Throws:
	 */
	public Map<String, String> queryUserMonney(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserMonney(conn, userId);
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
	 * @throws Exception
	 * @MethodName: addBorrowInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午05:48:20
	 * @Return:
	 * @Descb: 添加借款投资记录
	 * @Throws:
	 */
	public Map<String, String> addBorrowInvest(long id, long userId,
			String dealPWD, double investAmount, String basePath,
			String username, int status, int num,int isUseHb,double bonus_avaliable,int bonus_type,int isUseHb_6_24) throws Exception {
		dealPWD = Utility.filteSqlInfusion(dealPWD);
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		
		Connection conn = MySQL.getConnection();
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outParameterValues, id, userId,
					basePath, new BigDecimal(investAmount), new Date(), status,
					num, 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			
				
			if (ret <= 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_invest", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "用户投标借款", 1);
				// 得到当前用户最新的投资ID
				Map<String, String> maps = investDao.queryInvestId(conn, id,
						userId);
				// 得到借款当前借款信息
				Map<String, String> borrowMap = borrowDao.queryBorroeById(conn,
						id);
				if (borrowMap != null) {
					long borrowWay = Convert.strToLong(borrowMap
							.get("borrowWay"), -1);
					if (borrowWay == 6) {
						// 提成奖励
						ret = awardService.updateMoneyNew(conn, userId,
								new BigDecimal(investAmount),
								IConstants.MONEY_TYPE_1, Convert.strToLong(maps
										.get("investId"), -1));
					}
				}
				
				/***红包开始*/
				long rerult = -1;
				List<Map<String,Object>> list = null;
				long borrowWay1 = Convert.strToLong(borrowMap.get("borrowWay"), -1);
				//针对定息宝开展的活动
				 if(isUseHb==1&&borrowWay1==4){//投资时候已经选择使用红包
					String startTime = "2016-07-01";
					String andTime = "2016-07-31";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					Date d1 = sdf.parse(startTime);
					Date d2 = sdf.parse(andTime);
					/**活动时间：2016年7月1日-7月31日  七月份红包**/
					if (d1.getTime() <= date.getTime() && date.getTime() <= d2.getTime()) {
					//查询用户可用余额
					Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
					double usableSum = Double.parseDouble(useMap.get("usableSum"));//可用金额
					//查询红包配置
					Map<String,String> hbConfig = bonusDao.queryBonusConfig(conn);
					//查询超级账户可用金额
					long super_id = Long.parseLong(hbConfig.get("super_id"));//超级账户id
					Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
					double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
					double invest_proportion = Double.parseDouble(hbConfig.get("invest_proportion"));//配置比例
					double n = investAmount*invest_proportion;//投资抵用红包
					double m = Math.floor(n);
					if(m>=bonus_avaliable){//红包被全部用完或者红包不够用的情况
						double fee = usableSum + bonus_avaliable;
						double super_fee = superUsableSum - bonus_avaliable;
						rerult = borrowDao.updateUserMoney(conn, userId, fee);//更新用户可用金
						borrowDao.updateUserMoney(conn, super_id, super_fee);//更新超级用户可用金
					    list = bonusDao.queryUserBonusY(conn, userId, bonus_type);
					    if(list!=null && list.size()>0){
					    	for(int i=0;i<list.size();i++){
					    		Map<String, Object> userBonusMap = list.get(i);
					    		long ids = (Long) userBonusMap.get("id");
					    		double bonus_moneys = (Double) userBonusMap.get("bonus_money");
					    		double bonus_avaliables = 0;
					    		double bouns_used = bonus_moneys;
					    		bonusDao.updateUserBonus(conn, ids, bonus_moneys, bonus_avaliables, bouns_used, 2);
					    	}
					    	bonusDao.addBonusHis(conn, userId, bonus_avaliable, id);//添加记录   id=借款id
					    	financeDao.addHbRecode(conn,super_id, "红包活动", bonus_avaliable, superUsableSum - bonus_avaliable, bonus_avaliable, "");
					    }
					}else{//红包有剩余的情况
						 rerult = borrowDao.updateUserMoney(conn, userId, usableSum+m);//更新用户可用金
						 borrowDao.updateUserMoney(conn, super_id, superUsableSum-m);//更新超级用户可用金 
						    list = bonusDao.queryUserBonusN(conn, userId, bonus_type);
						    if(list!=null && list.size()>0){
						    	 double bonus_avaliable_sum = 0;
						    	 int temp = 0;//临时变量
						    	 long lastId = 0;//最后一条数据id
						    	 double list_bonus_avaliable_n = 0;//最后一条数据可用余额红包
						    	 double list_bouns_used_n = 0;//最后一条数据已用红包
						    	 
								 for(int i=0;i<list.size();i++){
									 long  id_n = (Long) list.get(i).get("id");
									 double bonus_money_n = (Double) list.get(i).get("bonus_money");
									 double  bonus_avaliable_n = (Double) list.get(i).get("bonus_avaliable");
									 bonusDao.updateUserBonus(conn, id_n, bonus_money_n, 0, bonus_money_n, 2);
									 
									 bonus_avaliable_sum = bonus_avaliable_sum + bonus_avaliable_n; 
									 if(bonus_avaliable_sum==m){
										 bonusDao.addBonusHis(conn, userId, bonus_avaliable_sum,id);//添加红包使用记录//   id=借款id
										 financeDao.addHbRecode(conn,super_id, "红包活动", bonus_avaliable_sum, superUsableSum-bonus_avaliable_sum, bonus_avaliable_sum, "");
										 break;
									 }
                                     if(bonus_avaliable_sum>m){
                                    	 lastId = id_n;
                                    	 list_bonus_avaliable_n = bonus_avaliable_sum - m ;
                                    	 list_bouns_used_n = bonus_money_n - list_bonus_avaliable_n;
                                    	 temp = 1;
                                    	 bonusDao.addBonusHis(conn, userId, bonus_avaliable_sum-list_bonus_avaliable_n,id);//   id=借款id
                                    	 financeDao.addHbRecode(conn,super_id, "红包活动", bonus_avaliable_sum-list_bonus_avaliable_n, superUsableSum-(bonus_avaliable_sum-list_bonus_avaliable_n), bonus_avaliable_sum-list_bonus_avaliable_n, "");
                                    	 break;
									 }
								  }
								 if(temp==1){
									 //更新上述更新数据的最后一条；
									 bonusDao.updateUserBonus(conn, lastId,list_bonus_avaliable_n+list_bouns_used_n, list_bonus_avaliable_n, list_bouns_used_n, 1);
								 }
								
						    }
						   
					
					}
					 
				}
			}
				/***红包结束 */
				
				//迎5.1活动
				
				/**线下理财人，线下借款人和机构客户以及其他不获取投资返现*/
				boolean flags = true;
				Map<String,String> downLineMap = userDao.queryGroupById(conn, userId);
				if(downLineMap!=null && downLineMap.size()>0){
					flags = false;
				}
				
				/**获取编号为1003的活动配置活动配置信息*/
				if(flags){
					Map<String,String>  activtyComfig = financeDao.query51ActivtyComfig(conn, "1003");
					if(activtyComfig!=null && activtyComfig.size()>0){
						String start_time =  activtyComfig.get("start_time_f");
						String end_time = activtyComfig.get("end_time_f");
						String currDate = DateUtil.dateToStringYYMMDD(new Date());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date d1 = sdf.parse(start_time);
						Date d2 = sdf.parse(currDate);
						Date d3 = sdf.parse(end_time);
						long m = d2.getTime()-d1.getTime();
						long n = d3.getTime()-d2.getTime();
						if(m>=0 && n>=0){//在活动时间范围内
							    //获取标的信息
							    String borrowWay = borrowMap.get("borrowWay");//标种 4——定息宝
							    int deadline = Integer.parseInt(borrowMap.get("deadline"));//投标期限
							    //当前投资用户
								Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
								double usableSum = Double.parseDouble(useMap.get("usableSum"));//可用金额
								//超级用户
								long super_id = Long.parseLong(activtyComfig.get("user_id"));
								Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
								double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
							    
								//查询当前投资用户是否qq群投资用户?
								Map<String,String> isQQUser = financeDao.queryIsQQUser(conn, userId);
								boolean flag = false;
								if(isQQUser!=null && isQQUser.size()>0){
									/**按照qq群投资用户奖励规则*/
									if("4".equals(borrowWay)){
										double rewardSum = investAmount*0.02*deadline/12;
										borrowDao.updateUserMoney(conn, userId, usableSum+rewardSum);//更新用户可用金
										borrowDao.updateUserMoney(conn, super_id, superUsableSum-rewardSum);//更新超级用户可用金
										financeDao.add51Recode(conn,userId, super_id,"5.1活动奖励-投资", rewardSum, usableSum+rewardSum, rewardSum, "投资返现",2,id);
										financeDao.add51Recode(conn,super_id, userId,"5.1活动奖励-投资", rewardSum, superUsableSum-rewardSum, rewardSum, "投资返现",1,id);
									}
									
									flag = true;
								} 
								if(!flag){
									/**按照普通用户和九华用户投资奖励规则*/
									if("4".equals(borrowWay)){
										double rewardSum = investAmount*0.01*deadline/12;
										borrowDao.updateUserMoney(conn, userId, usableSum+rewardSum);//更新用户可用金
										borrowDao.updateUserMoney(conn, super_id, superUsableSum-rewardSum);//更新超级用户可用金
										financeDao.add51Recode(conn,userId,super_id, "5.1活动奖励-投资", rewardSum, usableSum+rewardSum, rewardSum, "投资返现",2,id);
										financeDao.add51Recode(conn,super_id,userId, "5.1活动奖励-投资", rewardSum, superUsableSum-rewardSum, rewardSum, "投资返现",1,id);
									}
								}
						}
					}
					
					//感恩十一月丰收季--转盘红包使用
					Map<String,String>  activty_comfig = financeDao.query51ActivtyComfig(conn, "1004");
					if(activty_comfig!=null && activty_comfig.size()>0){
							//String borrowWay = borrowMap.get("borrowWay");//标种 4——定息宝
							//int deadline = Integer.parseInt(borrowMap.get("deadline"));							
							if(1==isUseHb_6_24){
							
								List<Map<String,Object>> listBonus = bonusDao.queryBonus_6_24(conn, userId);
								if(listBonus!=null && listBonus.size()>0){//有6.24红包
									
									Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
									double usableSum = Double.parseDouble(useMap.get("usableSum"));//可用金额
									long super_id = Long.parseLong(activty_comfig.get("user_id"));//超级账户id
									Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
									double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
									
									double b = investAmount/50f;//投资抵用红包
									double a = Math.floor(b);
									double bonus_avaliable_6_24 = Double.parseDouble(bonusDao.queryBonus_6_24_sum(conn, userId).get("bonus_able"));
									if(a>=bonus_avaliable_6_24){//6.24红包被全部用完或者红包不够用的情况
										double fee = usableSum + bonus_avaliable_6_24;
										double super_fee = superUsableSum - bonus_avaliable_6_24;
										rerult = borrowDao.updateUserMoney(conn, userId, fee);//更新用户可用金
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
											rerult = borrowDao.updateUserMoney(conn, userId, fee);//更新用户可用金
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
					

						
						
					//复投返现--单次年华投资额的千分之二（8月份千分之一）
				
					Map<String,String>  comfig_1006 = financeDao.query51ActivtyComfig(conn, "1006");
					
					if(comfig_1006!=null && comfig_1006.size()>0){
						String start_time_cfig =  comfig_1006.get("start_time_f"); 
						String end_time_cfig = comfig_1006.get("end_time_f"); 
						String currDate_cfig = DateUtil.dateToStringYYMMDD(new Date()); 
						SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd"); 
						Date date1 = sdformat.parse(start_time_cfig); 
						Date date2 = sdformat.parse(currDate_cfig); 
						Date date3 = sdformat.parse(end_time_cfig); 
						long mResult = date2.getTime()-date1.getTime(); 
						long nResult = date3.getTime()-date2.getTime(); 
						if(mResult>=0 && nResult>=0){//在活动时间范围内
							Map<String,String> investRepayMap = financeDao.queryInvestRepayStatus(conn, userId);
							if(investRepayMap!=null && investRepayMap.size()>0){// ① 判断投资人是否有到期的投资
								String borrowWay = borrowMap.get("borrowWay");//标种 4——定息宝
								
								// 查询该用户是否满足年化复投金额大于10000
								// TODO: 如果不计算利息的年化也可以配合 ① 的思路，直接查询投资表，或得到期投资本金
								Map<String,String> investMap =financeDao.queryUserInvestSumForBorrEnd(conn, userId);
								
								if(investMap!=null && investMap.size()>0){
								   double investAmountSum = Convert.strToDouble(investMap.get("investAmountSum"), 0);
								   if(investAmountSum>=10000){
										if("4".equals(borrowWay)){
											int deadline = Integer.parseInt(borrowMap.get("deadline"));//投标期限
											double reAmount = (investAmount*deadline/12f)*(5/1000f);//返现金额（ 8月份千分之一）
											
											if(reAmount>1){
												
												/*//当前投资用户
												Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
												double usableSum = Double.parseDouble(useMap.get("usableSum"));//可用金额
												//超级用户
												long super_id = Long.parseLong(comfig_1006.get("user_id"));
												Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
												double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
												
												//更新用户可用余额
												borrowDao.updateUserMoney(conn, userId, usableSum+reAmount);//更新用户可用金
												borrowDao.updateUserMoney(conn, super_id, superUsableSum-reAmount);//更新超级用户可用金
												//添加用户资金记录
												financeDao.add51Recode(conn,userId, super_id,"复投返现", reAmount, usableSum+reAmount, reAmount, "201601月份复投返现奖励",2,id);
												financeDao.add51Recode(conn,super_id, userId,"复投返现", reAmount, superUsableSum-reAmount, reAmount, "201601月份复投返现奖励",1,id);
											*/
												financeDao.addActivityci(conn, reAmount, 0.005, DateUtil.getNextMonthParm(deadline), userId, id);
											
											}else{
												log.info("利息小于1元，不予发放！");
											}
										}
								   }
								}
							}
						}
					}
					/**复投返现结束*/
					
			   }
			}
			conn.commit();
			//whb 添加投资id
			String investId = queryInvestId(id, userId);
			map.put("ret_id", investId);
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
	
	
	public Map<String, String> addBorrowInvests(long id, long userId,
			String dealPWD, double investAmount, String basePath,
			String username, int status, int num) throws Exception {
		dealPWD = Utility.filteSqlInfusion(dealPWD);
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		
		Connection conn = MySQL.getConnection();
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outParameterValues, id, userId,
					basePath, new BigDecimal(investAmount), new Date(), status,
					num, 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			
				
			if (ret <= 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_invest", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "用户投标借款", 1);
				// 得到当前用户最新的投资ID
				Map<String, String> maps = investDao.queryInvestId(conn, id,
						userId);
				// 得到借款当前借款信息
				Map<String, String> borrowMap = borrowDao.queryBorroeById(conn,
						id);
				if (borrowMap != null) {
					long borrowWay = Convert.strToLong(borrowMap
							.get("borrowWay"), -1);
					if (borrowWay == 6) {
						// 提成奖励
						ret = awardService.updateMoneyNew(conn, userId,
								new BigDecimal(investAmount),
								IConstants.MONEY_TYPE_1, Convert.strToLong(maps
										.get("investId"), -1));
					}
				}
				
				// 更新推荐机会
				//brokerageIDRecognition.processInvestReward(conn,userId, investAmount,investId);
			}
			conn.commit();
			//whb 添加投资id
			String investId = queryInvestId(id, userId);
			map.put("ret_id", investId);
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
	 * 投标
	 * 
	 * @param conn
	 * @param investAmount
	 * @param id
	 * @param userId
	 * @param basePath
	 * @param username
	 * @param status
	 * @param num
	 * @return
	 */
	private Map<String, String> validateInvest(Connection conn,
			double investAmount, long id, long userId, String basePath,
			String username, int status, int num) throws Exception {
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outParameterValues, id, userId,
					basePath, new BigDecimal(investAmount), new Date(), status,
					num, 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			//whb 添加投资id
			String investId = queryInvestId(id, userId);
			map.put("ret_id", investId);
			
			if (ret <= 0) {
				conn.rollback();
				return map;
			}
			// 添加操作日志
			userMap = userDao.queryUserById(conn, userId);
			operationLogDao.addOperationLog(conn, "t_invest", Convert.strToStr(
					userMap.get("username"), ""), IConstants.INSERT, Convert
					.strToStr(userMap.get("lastIP"), ""), 0, "用户投标借款", 1);
			// 得到当前用户最新的投资ID
			Map<String, String> maps = investDao
					.queryInvestId(conn, id, userId);
			// 得到借款当前借款信息
			Map<String, String> borrowMap = borrowDao.queryBorroeById(conn, id);
			if (borrowMap != null) {
				long borrowWay = Convert.strToLong(borrowMap.get("borrowWay"),
						-1);
				if (borrowWay == 6) {
					// 提成奖励
					ret = awardService.updateMoneyNew(conn, userId,
							new BigDecimal(investAmount),
							IConstants.MONEY_TYPE_1, Convert.strToLong(maps
									.get("investId"), -1));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		}

		return map;
	}

	/**
	 * @throws DataException
	 * @MethodName: addFocusOn
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:00:49
	 * @Return:
	 * @Descb: 添加关注
	 * @Throws:
	 */
	public Long addFocusOn(long id, long userId, int modeType) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addFocusOn(conn, id, userId, modeType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				if (modeType == 1) {
					operationLogDao.addOperationLog(conn, "t_concern", Convert
							.strToStr(userMap.get("username"), ""),
							IConstants.INSERT, Convert.strToStr(userMap
									.get("lastIP"), ""), 0, "添加关注用户", 1);
				} else {
					operationLogDao.addOperationLog(conn, "t_concern", Convert
							.strToStr(userMap.get("username"), ""),
							IConstants.INSERT, Convert.strToStr(userMap
									.get("lastIP"), ""), 0, "添加关注借款", 1);
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

	/**
	 * @throws DataException
	 * @MethodName: hasFocusOn
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午11:04:13
	 * @Return:
	 * @Descb: 查询用户是否已经有关注
	 * @Throws:
	 */
	public Map<String, String> hasFocusOn(long id, long userId, int moduleType)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.hasFocusOn(conn, id, userId, moduleType);
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
	 * @MethodName: addUserMail
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:13:57
	 * @Return:
	 * @Descb: 添加用户站内信
	 * @Throws:
	 */
/*	public long addUserMail(Long userId, Long adminId, double money,
			String title, Date date, String fundMode, String addIP,
			String userName, String remarks) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			// 发送通知，通过通知模板
			Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader
					.getCurrentWebApplicationContext()
					.getServletContext()
					.getAttribute(
							IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
			// 消息模版
			// 站内信
			Map<String, String> noticeMap = new HashMap<String, String>();
			String informTemplate = informTemplateMap.get(IInformTemplateConstants.TENDER)+"";
			informTemplate = informTemplate.replace("title", title+"");
			informTemplate = informTemplate.replace("[voluntarily]", "手动");
			informTemplate = informTemplate.replace("date",DateUtil.dateToString((new Date())));
			informTemplate = informTemplate.replace("investAmount", money+"");
			noticeMap.put("mail", informTemplate);
			selectedService.sendNoticeMSG(conn, userId, "投资成功", noticeMap,
					IConstants.NOTICE_MODE_5);
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
	}*/

	public long addUserMail(long reciver, Long userId, String title,
			String content, int mailType) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addUserMail(conn, reciver, userId, title,
					content, mailType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_concern", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发送站内信", 1);
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
	 * @MethodName: addUserReport
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:15:05
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public long addUserReport(long reporter, Long userId, String title,
			String content) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addUserReport(conn, reporter, userId, title,
					content);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_report", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "添加用户举报", 1);
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
	 * @MethodName: queryLastestBorrow
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:28:00
	 * @Return:
	 * @Descb: 查询最新的借款前10条记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrow() throws Exception,
			DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryLastestBorrow(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	public List<Map<String, Object>> queryBorrowIndex(int borrowWay) throws Exception,DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryBorrowIndex(conn, borrowWay);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
public List<Map<String, Object>> queryLastestBorrowWay(int borrowWay) throws Exception,DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryLastestBorrowWay(conn,borrowWay);
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
	 * 
	 * 投资风云榜
	 */
	public List<Map<String, Object>> queryUserInvestRecode() throws Exception,DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserInvestRecode(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
   }	/**
	 * @MethodName: queryLastestBorrow
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 首页只推送定息宝（暂时）
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrowAppByBorrowWay() throws Exception,
	DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryLastestBorrowAppByBorrowWay(conn);
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
	 * @MethodName: queryLastestBorrow
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 查询最新的借款前1条记录app
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrowApp() throws Exception,
	DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryLastestBorrowApp(conn);
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
	 * @MethodName: investRank
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:11:37
	 * @Return:
	 * @Descb: 投资排名前20条记录
	 * @Throws:
	 */
	public List<Map<String, Object>> investRank(int type, int count)
			throws Exception {
		Connection conn = MySQL.getConnection();
		// List<Map<String, Object>> list = new ArrayList<Map<String,
		// Object>>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			// list = financeDao.investRank(conn, starTime, endTime);
			Procedures.p_get_topinvestment(conn, ds, outParameterValues, type,
					count);

			conn.commit();

			ds.tables.get(0).rows.genRowsMap();

			return ds.tables.get(0).rows.rowsMap;
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
	 * @MethodName: queryTotalRisk
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:36:01
	 * @Return:
	 * @Descb: 查询风险保障金总额
	 * @Throws:
	 */
	public Map<String, String> queryTotalRisk() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryTotalRisk(conn);
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
	 * @MethodName: queryCurrentRisk
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:36:14
	 * @Return:
	 * @Descb: 查询当日风险保障金收支金额
	 * @Throws:
	 */
	public Map<String, String> queryCurrentRisk() throws Exception,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryCurrentRisk(conn);
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
	 * @throws Exception
	 * @MethodName: queryBorrowRecord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午11:03:17
	 * @Return:
	 * @Descb: 查询借款记录统计
	 * @Throws:
	 */
	public Map<String, String> queryBorrowRecord(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBorrowRecord(conn, ds, outParameterValues, id,
					new Date());
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
	 * @MethodName: subscribeSubmit
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午10:30:15
	 * @Return:
	 * @Descb: 认购提交
	 * @Throws:
	 */
	public Map<String,String> subscribeSubmit(long id, int copies, Long userId,
			String basePath, String username,
			Map<String, Object> platformCostMap,int isUseHb_6_24) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = new HashMap<String, String>();
		String msg = "";
		//whb 投资id
		String investId = "";
		long returnId = -1;
		double investAmount = 0;
		try {
			Map<String, String> borrowTenderInMap = financeDao
					.queryBorrowTenderIn(conn, id);
			// 认购中的借款
			if (borrowTenderInMap != null && borrowTenderInMap.size() > 0) {
				long remainCirculationNumber = Convert.strToLong(
						borrowTenderInMap.get("remainCirculationNumber") + "",
						0);
				double smallestFlowUnit = Convert.strToDouble(borrowTenderInMap
						.get("smallestFlowUnit")
						+ "", 0);
				if (copies > remainCirculationNumber) {
					// 校验认购份数是否满足
					msg = "只剩下【" + remainCirculationNumber + "】份可认购,请重新选择!";
				} else {
					// 提交的认购总金额
					investAmount = smallestFlowUnit * copies;
					// 查询账户上的金额是否满足认购的份数
					Map<String, String> usableSumMap = financeDao
							.queryUserUsableSum(conn, userId, investAmount);
					if (usableSumMap != null && usableSumMap.size() > 0) {
						double usableSum = Convert.strToDouble(usableSumMap
								.get("usableSum")
								+ "", 0);
						long minCirculationNumber = 0;
						double needSum = 0;
						if (usableSum < smallestFlowUnit) {
							msg = "您的可用余额少于￥" + smallestFlowUnit + "元，认购失败!";
						} else {
							// 计算向下取数满足最小的认购份数
							for (long n = remainCirculationNumber; n > 0; n--) {
								needSum = smallestFlowUnit * n;
								if (usableSum >= needSum) {
									minCirculationNumber = n;
									break;
								}
							}
							msg = "您的可用余额可认购【" + minCirculationNumber
									+ "】份,请重新选择!";
						}
					} else {
						Map<String, String> map = validateInvest(conn,
								investAmount, id, userId, basePath, username,
								1, copies);
						returnId = Convert.strToLong(map.get("ret"), -1);
						if (returnId <= 0) {
							conn.rollback();
							msg = Convert.strToStr(map.get("ret_desc"), "");
						} else {
							msg = "1";
							investId = Convert.strToStr(map.get("ret_id"), "");
						}
					}
				}
			} else {
				// 认购已满,更新状态为回购中
				financeDao.updateRepo(conn, id);
				msg = "无效借款投标";
			}
			
			
			boolean flags = true;
			Map<String,String> downLineMap = userDao.queryGroupById(conn, userId);
			if(downLineMap!=null && downLineMap.size()>0){
				flags = false;
			}
			
			/**获取编号为1003的活动配置活动配置信息*/
			if(flags){
			
			//感恩十一月丰收季--转盘红包使用
				if("1".equals(msg) && 1==isUseHb_6_24){
					Map<String,String>  activty_comfig = financeDao.query51ActivtyComfig(conn, "1004");
					if(activty_comfig!=null && activty_comfig.size()>0){
						List<Map<String,Object>> listBonus = bonusDao.queryBonus_6_24(conn, userId);
						if(listBonus!=null && listBonus.size()>0){//有6.24红包
							Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
							double usableSum = Double.parseDouble(useMap.get("usableSum"));//可用金额
							long super_id = Long.parseLong(activty_comfig.get("user_id"));//超级账户id
							Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
							double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
							
							double b = investAmount/50f;//投资抵用红包
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
			conn.commit();
			String invest_id = queryInvestId(id, userId);
			result.put("msg", msg);
			result.put("investId", invest_id);
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

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	/**
	 * @throws Exception
	 * @MethodName: getInvestPWD
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:35:00
	 * @Return:
	 * @Descb: 获取投标密码是否正确
	 * @Throws:
	 */
	public Map<String, String> getInvestPWD(Long idLong, String investPWD)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			investPWD = Encrypt.MD5(investPWD);
			map = financeDao.getInvestPWD(conn, idLong, investPWD);
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
	 * 根据借款Id查询还款记录
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRepayment(long borrowId)
			throws Exception {

		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = repayMentDao.queryHasPIAndStillPi(conn, borrowId);
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
	 * 查找投资人信息 add by houli
	 * 
	 * @return
	 * @throws DataException
	 * @throws Exception
	 */
	public Map<String, String> queryInvestorById(long investorId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryInvestorById(conn, investorId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Map<String, String> queryUserById(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserById(conn,userId);
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
	 * 判断发标人是否问高级用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAdminUserPublisher(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryAdminUserPublisher(conn,userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	public Map<String, String> queryBonus_6_24_sum(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = bonusDao.queryBonus_6_24_sum(conn, userId);
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
	 * 查询合作机构信息
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryLoanCompany(Long adminId) throws Exception {
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
	public Map<String, String> queryBondingCompany(long loan_comp_id,long bonding_comp_id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryBondingCompany(conn,loan_comp_id,bonding_comp_id);
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
	 * 查询逐笔审批的担保函
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryFinanceById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryFinanceById(conn,id);
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
	 * 查询合作机构的基本资料
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryLoanCompanyBasePic(Long id) throws Exception,
	DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.queryLoanCompanyBasePic(conn,id);
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
	 * 查询合作机构的抵押图片
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryLoanCompanyMortgPic(Long id) throws Exception,
	DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.queryLoanCompanyMortgPic(conn,id);
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
	 * 查询用户红包金额
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryUserHbMoney(Long userId,int bonus_type) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserHbMoney(conn,userId,bonus_type);
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
	 * 
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryInvestTotal() throws Exception,
	DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryInvestTotal(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	
	public Map<String, String> query51ActivtyComfig() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.query51ActivtyComfig(conn, "1003");
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
	  * 给QQ群主返佣金
	  * @param borrowId
	  * @param start_time
	  * @param end_time
	  * @return
	  * @throws Exception
	  * @throws DataException
	  */
	public long update51QQMainRecomment(long t_repayment_id,String start_time,String end_time) throws Exception,DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		long m = -1;
		try {
			Map<String,String> payMap = financeDao.queryRepayment(conn, t_repayment_id);
			long borrowId = Long.parseLong(payMap.get("borrowId"));
			Map<String,String> bMao = financeDao.queryBorrowById(conn, borrowId);
			double annual_Rate = Double.parseDouble(bMao.get("annualRate"));//年化收益率
			list = financeDao.query51QQMainRecomment(conn, t_repayment_id, start_time, end_time);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					long recommendUserId = Long.parseLong(String.valueOf(list.get(i).get("recommendUserId")));//群主Id
					double hasInterest = Double.parseDouble(String.valueOf(list.get(i).get("hasInterest")));//已还利息
					double yjlv = 0.01;//佣金参数
					/**获取返回给群主的佣金*/
					double annualRate = annual_Rate/100;
					double flee = hasInterest*yjlv/annualRate;
					Map<String,String> superUseMap = borrowDao.queryUserAmountByName(conn, "weixindai1503");//扣款-佣金大账户
					double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级用户可用金额
					long super_id = Long.parseLong(superUseMap.get("id"));//超级用户Id
					double superUsableSumFlee = superUsableSum - flee;
					
					Map<String,String> QQUseMap = borrowDao.queryUserAmount(conn,recommendUserId);//QQ群主
					double  QQusableSum = Double.parseDouble(QQUseMap.get("usableSum"));//群主可用金额
					double QQusableSumFlee = QQusableSum + flee;
					
					borrowDao.updateUserMoney(conn, recommendUserId,QQusableSumFlee);//更新用户可用金
					borrowDao.updateUserMoney(conn, super_id, superUsableSumFlee);//更新超级用户可用金
					String names = "5.1活动奖励-推荐";
					String investTime = String.valueOf(list.get(i).get("investTime"));
					int results = investTime.compareTo(end_time);
					if(results>0){
						names = "推荐返佣金-群主";
					}				
					financeDao.add51Recode(conn,recommendUserId,super_id, names, flee, QQusableSumFlee, flee, "QQ群主获得佣金",2,borrowId);
					financeDao.add51Recode(conn,super_id, recommendUserId,names, flee, superUsableSumFlee, flee, "支付QQ群主佣金",1,borrowId);
				}
				conn.commit();
				m=1;
			}else{
				m=0;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return m;
	}
	
	/***
	 * 判断是否在发送体验金时间范围内
	 * @return
	 * @throws Exception
	 */
	public long  queryActivtySetComfig() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		long result = 0;
		try {
			map = userDao.queryActivtySetComfig(conn, "1005");
			if(map!=null && map.size()>0){
				String start_time =  map.get("start_time_f");
				String end_time = map.get("end_time_f");
				String currDate = DateUtil.dateToStringYYMMDD(new Date());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = sdf.parse(start_time);
				Date d2 = sdf.parse(currDate);
				Date d3 = sdf.parse(end_time);
				long m = d2.getTime()-d1.getTime();
				long n = d3.getTime()-d2.getTime();
				if(m>=0 && n>=0){
					result = 1;
				}
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
	 * 添加体验金投资
	 * @return
	 */
	public Map<String,String> addTyjInvest(long userId,long borrowId) throws Exception{
		Map<String,String> retMap = new HashMap<String,String>();
		Connection conn = MySQL.getConnection();
		try {
			 
			
			String currDate = DateUtil.dateToStringYYMMDD(new Date());
			List<Map<String, Object>> list = financeDao.queryUserSendTyj(conn, userId,currDate);
			if(list==null || list.size()==0){
				retMap.put("msg", "您没有体验金了"); 
				return retMap;
			}
			
			Map<String,String> map = borrowDao.queryBorrowNew(conn);
			
			int parm = Convert.strToInt(map.get("version"), 0);
			borrowDao.updateBorrowNewVersion(conn, borrowId, parm);
			Map<String,String> rowCountMap = financeDao.queryRowCount(conn);
			int rowCount = Convert.strToInt(rowCountMap.get("rowCount"), -9999);
		    log.info("####################################################"+rowCount+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			if(rowCount<=0){
				retMap.put("msg", "系统繁忙"); 
				return retMap;
			}
			
			double rate = Convert.strToDouble(map.get("rate"), 0);
			int day = Convert.strToInt(map.get("day"), 0);
			double getLx = (8888*rate/100f)*(day/365f); //年华收益率8%，
			String repayDate = DateUtil.getCurrDateLateDay(day);//投资day天后还款哦
			double amount_able = Convert.strToDouble(map.get("amount_able"), 0);
			double amount_sum = Convert.strToDouble(map.get("amount_sum"), 0);
			int m = (int) ((amount_sum-amount_able)/8888);//体验金可投资份数
			int n = list.size();//用户可投份数
			
			if(m<=0){
				retMap.put("msg", "标的已满"); 
				return retMap;
			}
			
			if(m-n>=0){
				financeDao.updateBorrowAmountAble(conn, borrowId,amount_able+8888*n);
				for(int i=0;i<n;i++){
					financeDao.addTyjInvest(conn, userId, borrowId, getLx, repayDate);
				
				}
				financeDao.updateSendTyjState(conn, userId);
				//retMap.put("msg", n+"份体验金已投标成功，剩余0份"); 
				retMap.put("msg", "体验金已投标成功"); 
			} 
			
			if(n-m>0){
				financeDao.updateBorrowAmountAble(conn, borrowId,amount_able+8888*m);
				Map<String, Object> maps = null;
				for(int i=0;i<m;i++){
					maps  = list.get(i);
					long id = Convert.strToLong(String.valueOf(maps.get("id")), 0);
					financeDao.addTyjInvest(conn, userId, borrowId, getLx, repayDate);
					financeDao.updateSendTyjStateById(conn, id);
				}
				
				retMap.put("msg", m+"份体验金已投标成功，剩余"+(n-m)+"份"); 
			}
			
			conn.commit();
		} catch (Exception e) {
			retMap.put("msg", "投标失败"); 
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return retMap;
		
	}
	
   /**
    * 查询用户是否有体验金
    * @param userId
    * @return
    * @throws Exception
    */
	public List<Map<String, Object>> queryUserSendTyj(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
	
		List<Map<String, Object>> list = null;
		try {
			String currDate = DateUtil.dateToStringYYMMDD(new Date());
			list = financeDao.queryUserSendTyj(conn, userId,currDate);
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
	 * 查询体验标的
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryBorrowNew() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowNew(conn);
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
	 * 查询体检金标的详情
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryTyjBorrowInvestDatail(PageBean pageBean,long borrowId) throws Exception,DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String resultFeilds = " username,createTime ";
			StringBuffer condition = new StringBuffer();
			condition.append(" and borrowId ="+borrowId);
		 
			dataPage(conn, pageBean, " v_t_tyjBorrowInvestDatail", resultFeilds,
					" order by createTime desc", condition.toString());
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
	 * 查询员工投资详情
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryEmployeeBorrowInvestDatail(PageBean pageBean,long borrowId) throws Exception,DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String resultFeilds = " amount, date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time,user_name ";
			StringBuffer condition = new StringBuffer();
			condition.append(" and borrow_id ="+borrowId);
		 
			dataPage(conn, pageBean, " t_employee_invest", resultFeilds,
					" order by create_time desc", condition.toString());
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
	 * 查询标的投资总额
	 * 
	 * @param boorowId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryInvestSumAmount(long boorowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryInvestSumAmount(conn, boorowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	public Map<String, String> queryEmployeeBorrow() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryEmployeeBorrow(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Map<String, String> queryEmployeeConfig(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryEmployeeConfig(conn,userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	public Map<String,String> addEmployeeInvest(long userId,long borrowId,int fs) throws Exception{
		Map<String,String> retMap = new HashMap<String,String>();
		Connection conn = MySQL.getConnection();
		try {
			retMap.put("status", "0"); 
			if(fs<=0){
				retMap.put("msg", "参数错误"); 
				return retMap;
			}
			 
			//用户是否有体验金
			Map<String, String> employeeConfigMap = financeDao.queryEmployeeConfigByUserId(conn, userId);
			if(employeeConfigMap==null || employeeConfigMap.size()==0){
				retMap.put("msg", "您没有体验金"); 
				return retMap;
			}
			
			//防止并发访问
			Map<String,String> map = borrowDao.queryEmployeeBorrow(conn);
			int parm = Convert.strToInt(map.get("version"), 0);
			borrowDao.updateEmployeeBorrowVersion(conn, borrowId, parm);
			Map<String,String> rowCountMap = financeDao.queryRowCount(conn);
			int rowCount = Convert.strToInt(rowCountMap.get("rowCount"), -9999);
			if(rowCount<=0){
				retMap.put("msg", "系统繁忙"); 
				return retMap;
			}
			
			int amount_sum = Convert.strToInt(map.get("amount_sum"), 0);//总份数
			int amount_able = Convert.strToInt(map.get("amount_able"), 0);//已投份数
			int m = amount_sum - amount_able;
			int n = Convert.strToInt(employeeConfigMap.get("amount"), 0);//用户可投份数
			if(m<=0){
				retMap.put("msg", "标的已满"); 
				return retMap;
			}
			
			//用户每月已经投资多少个满标
			String currDate = DateUtil.dateToStringYYMMDD(new Date());
			List<Map<String, Object>> countsList = borrowDao.queryEmployeeInvestCounts(conn, userId, currDate);
		    int counts = 0;
			if(countsList!=null && countsList.size()>0){
				counts = countsList.size();
		    }
			
			//用户每月-可投标的已经使用了多少份体验金
			Map<String, String> sumsList = borrowDao.queryEmployeeInvestSums(conn, userId, currDate);
		    int sums = 0;
			if(sumsList!=null && sumsList.size()>0){
				sums = Convert.strToInt(sumsList.get("amount"), 0);
		    }
			
			int n4 = 0;
			if(counts<3){
				n4 = (int) Math.floor(n/4f)-sums;
			}else{
				n4 = ((int) Math.floor(n/4f))+(n%4)-sums;
			}
			
			if(n4==0){
				retMap.put("msg", "您没有体验金了"); 
				return retMap;
			}
			
			if(fs>n4){
				retMap.put("msg", "该标您最多可投"+n4+"份"); 
				return retMap;
			}
			
			//更新员工体验标可投份数
			financeDao.updateEmployeeBorrowAbleAount(conn, borrowId,amount_able+fs);
			//添加员工投标记录
			financeDao.addEmployeeInvest(conn, userId, borrowId,fs);
			if(n4-fs==0){
				retMap.put("msg", fs+"投资成功"); 
			}else{
				retMap.put("msg", "投标成功"+fs+"份"+"，剩余"+(n4-fs)+"份"); 
			}
			retMap.put("status", "1"); 
			
			conn.commit();
		} catch (Exception e) {
			retMap.put("msg", "投标失败"); 
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return retMap;
		
	}
	

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
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

	public RepayMentDao getRepayMentDao() {
		return repayMentDao;
	}

	public void setRepayMentDao(RepayMentDao repayMentDao) {
		this.repayMentDao = repayMentDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}
	
	public SMSInterfaceService getsMsService() {
		return sMsService;
	}

	public void setsMsService(SMSInterfaceService sMsService) {
		this.sMsService = sMsService;
	}

	public BonusDao getBonusDao() {
		return bonusDao;
	}

	public void setBonusDao(BonusDao bonusDao) {
		this.bonusDao = bonusDao;
	}
	
	

}
