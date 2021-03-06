package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BeVipDao;
import com.sp2p.dao.VidateDao;
import com.sp2p.dao.admin.CountWorkStatusDao;
import com.sp2p.service.admin.SendmsgService;

/**
 * 用户验证基本资料
 * 
 * @author Administrator
 * 
 */
public class ValidateService extends BaseService {
	private VidateDao vidateDao;

	private UserService userService;
	private SendmsgService sendmsgService;
	private CountWorkStatusDao countWorkStatusDao;
	private BeVipDao beVipDao;
	public static Log log = LogFactory.getLog(ValidateService.class);

	/**
	 * 查询订单的用户的充值记录
	 * 
	 * @param status
	 * @param userName
	 * @param startDate
	 * @param endDate
	 * @return void
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryOrderRechargeRecords(
			PageBean<Map<String, Object>> pageBean, Integer status,
			String userName, String startDate, String endDate) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		startDate = Utility.filteSqlInfusion(startDate);
		endDate = Utility.filteSqlInfusion(endDate);
		
		// 只要订单类型(orderType)是3的就是充值记录，不管是否成功都展示出来
		StringBuffer condition = new StringBuffer();
		condition.append(" AND orderType = 3");
		if (status != null && status >= -1) {
			condition.append(" AND status = " + status);
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" AND addDate >= DATE('"
					+ StringEscapeUtils.escapeSql(startDate) + "')");
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" AND addDate <= DATE('"
					+ StringEscapeUtils.escapeSql(endDate) + "')");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_order_recharge_records ", " * ",
					" order by id" + IConstants.SORT_TYPE_DESC, condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询用户的基本基料认证
	 * 
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param autiStatus
	 * @param certificateName
	 * @throws SQLException
	 */
	public void queryBaseValidata(PageBean<Map<String, Object>> pageBean,
			String username, String realName, Integer autiStatus,
			String certificateName, String serviceManName) throws Exception {
		username = Utility.filteSqlInfusion(username);
		realName = Utility.filteSqlInfusion(realName);
		certificateName = Utility.filteSqlInfusion(certificateName);
		serviceManName = Utility.filteSqlInfusion(serviceManName);
		
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realname = '"
					+ StringEscapeUtils.escapeSql(realName) + "'");
		}
		if (null != autiStatus && autiStatus >= -1) {
			// condition.append(" AND auditStatus = "+autiStatus);
		}

		if (StringUtils.isNotBlank(certificateName)) {
			// condition.append(" AND certificateName ="+certificateName);
		}
		if (StringUtils.isNotBlank(serviceManName)) {
			condition.append(" AND findandcheck ="
					+ StringEscapeUtils.escapeSql(serviceManName));
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_base_check", "*", " order by id "
					+ IConstants.SORT_TYPE_DESC, condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 后台-> 个人信息审核列表
	 * 
	 * @param pageBean
	 * @param username
	 *            用户名称
	 * @param realName
	 *            真实姓名
	 * @param autiStatus
	 *            状态值
	 * @param certificateName
	 * @param serviceManName
	 *            审核人名称
	 * @throws SQLException
	 */
	public void queryPersonInfo(PageBean<Map<String, Object>> pageBean,
			String username, String realName, Integer autiStatus,
			Integer certificateName, String serviceManName) throws Exception {
		username = Utility.filteSqlInfusion(username);
		realName = Utility.filteSqlInfusion(realName);
		serviceManName = Utility.filteSqlInfusion(serviceManName);
		
		StringBuffer condition = new StringBuffer();
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "','%')");
		}

		if (null != autiStatus && autiStatus > -1 && autiStatus == 1// 联系信息
				&& null != certificateName && certificateName > -1) {
			if (certificateName != 4) {
				condition.append(" AND workauditStatus = " + certificateName);
			} else {

				condition.append(" AND workauditStatus is null");
			}
		}
		if (null != autiStatus && autiStatus > -1 && autiStatus == 2// 个人信息
				&& null != certificateName && certificateName > -1) {
			if (certificateName != 4) {
				condition.append(" AND personauditStatus = " + certificateName);
			} else {
				condition.append(" AND personauditStatus is null");

			}
		}
		if (null != autiStatus && autiStatus > -1 && autiStatus == 3// 联系信息
				&& null != certificateName && certificateName > -1) {
			if (certificateName != 4) {
				if (certificateName == 3) {// 联系信息成功的
					condition.append(" AND cccc = " + 9);
				} else if (certificateName == 1) {// 联系信息待审核的
					condition.append(" AND cccc = " + 3);
				} else if (certificateName == 2) {// 联系信息待失败的
					condition
							.append(" AND directedStatus = 2 or otherStatus = 2 or moredStatus = 2 ");
				}
			} else {
				condition.append(" AND directedStatus is null");
			}
		}

		if (StringUtils.isNotBlank(serviceManName)) {
			condition.append(" AND service  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(serviceManName.trim())
					+ "','%')");
		}
		// ------------
		if (autiStatus <= -1 && certificateName > -1 && certificateName != 4) {
			condition.append(" AND ( workauditStatus = " + certificateName
					+ " or directedStatus = " + certificateName
					+ " or otherStatus  = " + certificateName
					+ " or moredStatus = " + certificateName
					+ " or personauditStatus = " + certificateName + ")  ");
		}
		if (autiStatus <= -1 && certificateName == 4) {
			condition.append(" AND directedStatus is null");
		}
		// --------------
		try {
			dataPage(conn, pageBean, "v_t_personcheck", "*", " order by id "
					+ IConstants.SORT_TYPE_DESC, condition.toString());

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 查询用户申请信用额度类表
	 * 
	 * @param pageBean
	 * @param username
	 * @param realName
	 * @param autiStatus
	 * @param certificateName
	 * @param serviceManName
	 * @throws SQLException
	 */
	public void querycreditLimitApply(PageBean<Map<String, Object>> pageBean,
			String username, Integer autiStatus, String starttime,
			String endTime) throws Exception {
		username = Utility.filteSqlInfusion(username);
		endTime = Utility.filteSqlInfusion(endTime);
		
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND uername  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(starttime)) {
			condition.append(" and applyTime >= '"
					+ StringEscapeUtils.escapeSql(starttime) + "'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and applyTime <= '"
					+ StringEscapeUtils.escapeSql(endTime) + "'");
		}
		if (autiStatus != null && autiStatus != -1) {
			condition.append(" AND applystatus = " + autiStatus);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_user_creditlimit_apply", "*",
					" ORDER BY  tcid  DESC", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 查询用户的信用情况
	 * 
	 * @param pageBean
	 * @param username
	 * @param realName
	 * @param autiStatus
	 * @param certificateName
	 * @param serviceManName
	 * @throws SQLException
	 */
	public void queryUserCredit(PageBean<Map<String, Object>> pageBean,
			String username, String realName, Integer auditStatus,
			String serviceManName, Integer certificateName) throws Exception {
		username = Utility.filteSqlInfusion(username);
		realName = Utility.filteSqlInfusion(realName);
		serviceManName = Utility.filteSqlInfusion(serviceManName);
		
		StringBuffer condition = new StringBuffer();
		Connection conn = MySQL.getConnection();

		/*
		 * if(userId!=null&&userId >=-1){ condition.append(" AND id = "+userId); }
		 */
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND usrename  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(serviceManName)) {
			condition.append(" AND serviceManName  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(serviceManName.trim())
					+ "','%')");
		}
		if (null != auditStatus && auditStatus > -1) {
			// ========

			if (certificateName != null && certificateName > -1
					&& certificateName == 1) {
				condition.append(" AND tmIdentityauditStatus = " + auditStatus);
			}
			// ========

			if (certificateName != null && certificateName > -1
					&& certificateName == 2) {
				condition.append(" AND tmworkauditStatus = " + auditStatus);
			}

			// ===========

			if (certificateName != null && certificateName > -1
					&& certificateName == 3) {
				condition.append(" AND tmaddressauditStatus = " + auditStatus);
			}
			// =============================

			if (certificateName != null && certificateName > -1
					&& certificateName == 4) {
				condition.append(" AND tmresponseauditStatus = " + auditStatus);
			}
			// ============================

			if (certificateName != null && certificateName > -1
					&& certificateName == 5) {
				condition.append(" AND tmincomeeauditStatus = " + auditStatus);
			}
			// ===========================
			if (certificateName == -1) {
				condition.append(" and tmIdentityauditStatus = " + auditStatus
						+ " or  tmworkauditStatus = " + auditStatus
						+ " or tmaddressauditStatus = " + auditStatus
						+ "  or tmresponseauditStatus  = " + auditStatus
						+ " or tmincomeeauditStatus = " + auditStatus + " ");
			}
		}

		try {
			dataPage(conn, pageBean, "v_t_user_auth",
					"*", " order by id "
					+ IConstants.SORT_TYPE_DESC, condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * //后台统计待审核的用户数量为 X 个。总的待审核的认证数量为XX个
	 * 
	 * @param conn
	 * @param username
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param auditStatus
	 *            认证状态
	 * @param serviceManName
	 *            跟踪人
	 * @param certificateName
	 *            认证类型
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUserCreditCount(String username,
			String realName, Integer auditStatus, String serviceManName,
			Integer certificateName) throws Exception {
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		Connection conn = MySQL.getConnection();
		try {
			map = vidateDao.queryUserCreditCount(conn, username, realName,
					auditStatus, serviceManName, certificateName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	// 根据名字统计总的待审核的认证数量为XX个 基本认证资料
	public Map<String, String> queryByNameCount(Vector<Long> lists)
			throws Exception {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		condition.append(" select ");
		condition.append(" COUNT(*) cc ");
		condition.append(" from  ");
		condition
				.append(" (select tm.auditStatus as auditStatus ,tm.materAuthTypeId as materAuthTypeId from t_materialsauth tm  where tm.userId = ");
		if (lists != null && lists.size() > 0) {
			Connection conn = MySQL.getConnection();
			for (int i = 0; i < lists.size(); i++) {
				if (i == 0) {
					condition.append(lists.get(i));
				} else {
					condition.append(" or tm.userId = " + lists.get(i));
				}
			}
			condition
					.append(" ) t where t.auditStatus = 1 and t.materAuthTypeId <=5 ");
			try {
				DataSet dataSet = MySQL
						.executeQuery(conn, condition.toString());
				map = BeanMapUtils.dataSetToMap(dataSet);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		}
		return map;
	}

	/**
	 * 后台统计待审核的用户数量为 X 个
	 * 
	 * @param lists
	 * @return
	 * @throws SQLException
	 */
	public int queryByNameUserCount() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		int i = 0;
		int alli = 0;// 返回的数字为+1
		try {
			StringBuffer condition = new StringBuffer();
			condition.append(" select ");
			condition.append(" COUNT(*) cc ");
			condition.append(" from  ");
			condition
					.append(" t_materialsauth tm  where tm.auditStatus = 1  and tm.materAuthTypeId <=5  and userId = ");
			DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
			map = BeanMapUtils.dataSetToMap(dataSet);
			if (map != null && map.size() > 0) {
				i = Convert.strToInt(map.get("cc"), 0);
				if (i > 0) {
					alli += 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return alli;
	}

	// 根据名字统计总的待审核的认证数量为XX个可选认证资料
	public Map<String, String> queryByNameselectCount(Vector<Long> lists)
			throws Exception {

		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		condition.append(" select ");
		condition.append(" COUNT(*) cc ");
		condition.append(" from  ");
		condition
				.append(" (select tm.auditStatus as auditStatus ,tm.materAuthTypeId as materAuthTypeId from t_materialsauth tm  where tm.userId = ");
		if (lists != null && lists.size() > 0) {
			Connection conn = MySQL.getConnection();
			for (int i = 0; i < lists.size(); i++) {
				if (i == 0) {
					condition.append(lists.get(i));
				} else {
					condition.append(" or tm.userId = " + lists.get(i));
				}
			}
			condition
					.append(" ) t where t.auditStatus = 1 and t.materAuthTypeId > 5 ");
			try {
				DataSet dataSet = MySQL
						.executeQuery(conn, condition.toString());
				map = BeanMapUtils.dataSetToMap(dataSet);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		}
		return map;
	}

	/**
	 * 后台统计待审核的用户数量为 X 个 可选
	 * 
	 * @param lists
	 * @return
	 * @throws SQLException
	 */
	public int queryByNameUserSelectCount(Vector<Long> lists) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		int i = 0;
		int alli = 0;// 返回的数字为+1
		try {
			for (Long list : lists) {
				StringBuffer condition = new StringBuffer();
				condition.append(" select ");
				condition.append(" COUNT(*) cc ");
				condition.append(" from  ");
				condition
						.append(" t_materialsauth tm  where tm.auditStatus = 1  and tm.materAuthTypeId > 5  and userId = "
								+ list);
				DataSet dataSet = MySQL
						.executeQuery(conn, condition.toString());
				map = BeanMapUtils.dataSetToMap(dataSet);
				if (map != null && map.size() > 0) {
					i = Convert.strToInt(map.get("cc"), 0);
					if (i > 0) {
						alli += 1;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return alli;
	}

	public Map<String, String> queryPerUserCredit(Long userId) throws Exception {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND id = " + userId);
		}

		Connection conn = MySQL.getConnection();
		try {
			map = vidateDao.queryPerUserCredit(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Map<String, String> queryPerAdminCredit(Long userId_admin) throws Exception {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId_admin != null && userId_admin >= -1) {
			condition.append(" AND id = " + userId_admin);
		}

		Connection conn = MySQL.getConnection();
		try {
			map = vidateDao.queryPerAdminCredit(conn, userId_admin);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查看可选资料的
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryselectpicture(Long userId) throws Exception {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND id = " + userId);
		}

		Connection conn = MySQL.getConnection();
		try {
			// dataPage(conn, pageBean, "v_t_personcheck", "*", " order by id
			// "+IConstants.SORT_TYPE_DESC, condition.toString());
			map = vidateDao.queryselectpicture(conn, userId);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 统计通过基本资料的数量
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryTotaPass(Long userId) throws Exception {
		Map<String, String> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = vidateDao.queryTotaPass(conn, userId);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 手机变更分页查询
	 * 
	 * @param userId
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */

	public void querytelphonePage(Long userId, PageBean pageBean,
			String username, String starttime, String endtime) throws Exception {
		username = Utility.filteSqlInfusion(username);
		starttime = Utility.filteSqlInfusion(starttime);
		endtime = Utility.filteSqlInfusion(endtime);
		// String resultFeilds = "
		// id,creditingName,applyAmount,applyDetail,status";
		StringBuffer condition = new StringBuffer();
		condition.append(" AND auditStatus = 3 ");
		// condition.append(" and applyer =" + userId);
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(username)) {
			/*
			 * condition.append(" AND username LIKE CONCAT('%','" +
			 * StringEscapeUtils.escapeSql(username.trim()) + "','%')");
			 */
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(starttime)) {
			condition.append(" and requsetTime >= '"
					+ StringEscapeUtils.escapeSql(starttime) + "'");
		}
		if (StringUtils.isNotBlank(endtime)) {
			condition.append(" and requsetTime <= '"
					+ StringEscapeUtils.escapeSql(endtime) + "'");
		}
		// ===============

		// StringBuffer sqlresult = new StringBuffer();
		// sqlresult
		// .append(" tpbi.id as tpbiid, tuser.id as id, tuser.username as
		// username, a.co as amountall,tp.realName as realName, ");
		// sqlresult
		// .append(" tp.cellPhone as cellPhone,tpbi.requsetTime as requsetTime
		// ");

		// ============
		// StringBuffer sql = new StringBuffer();
		// sql.append(" t_phone_binding_info tpbi ");
		// sql.append(" left join t_user tuser on tuser.id = tpbi.userId ");
		// sql.append(" left join t_person tp on tuser.id = tp.userId ");
		// sql.append(" left join ");
		// sql
		// .append(" (select SUM(investAmount) as co, ti.investor as tiv from
		// t_invest ti GROUP BY ti.investor) a ");
		// sql.append(" on a.tiv = tuser.id ");

		/*
		 * StringBuffer sql = new StringBuffer(); sql.append(" t_person tp ");
		 * sql.append(" left join t_user tuser on tuser.id = tp.userId ");
		 * sql.append(" left join t_phone_binding_info tpbi on tp.userId =
		 * tpbi.userId "); sql.append(" left join "); sql .append(" (select
		 * SUM(investAmount) as co, ti.investor as tiv from t_invest ti GROUP BY
		 * ti.investor) a "); sql.append(" on a.tiv = tp.userId "); sql.append("
		 * GROUP BY tp.userId ");
		 */

		// ===================
		try {
			/*
			 * dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
			 * "", condition.toString());
			 */
			dataPage(conn, pageBean, "v_t_phone_banding_review", "*",
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
	 * 手机分页查询 变更2
	 * 
	 * @param userId
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void querytelphonePage2(Long userId, PageBean pageBean,
			String username, String starttime, String endtime, Integer statuss)
			throws Exception {
		username = Utility.filteSqlInfusion(username);
		starttime = Utility.filteSqlInfusion(starttime);
		endtime = Utility.filteSqlInfusion(endtime);
		// String resultFeilds = "
		// id,creditingName,applyAmount,applyDetail,status";
		StringBuffer condition = new StringBuffer();
		// condition.append(" and applyer =" + userId);
		Connection conn = MySQL.getConnection();
		condition.append(" AND 1 = 1 ");
		// ===============
		if (StringUtils.isNotBlank(username)) {
			condition.append(" and  username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(starttime)) {
			condition.append(" and requsetTime >= '"
					+ StringEscapeUtils.escapeSql(starttime) + "'");
		}
		if (StringUtils.isNotBlank(endtime)) {
			condition.append(" and requsetTime <= '"
					+ StringEscapeUtils.escapeSql(endtime) + "'");
		}
		if (statuss != null && statuss != -1) {

			condition.append(" and tpbi.status  = " + statuss);

		}
		condition.append(" and tuser.id is not null ");
		condition.append(" and tpbi.type = 2 ");
		// ===============

		StringBuffer sqlresult = new StringBuffer();

		sqlresult
				.append(" tpbi.id as tpbiid, tuser.id as id, tuser.username as username, a.co as amountall,tp.realName as realName, ");
		sqlresult
				.append(" tpbi.oldPhone as cellPhone,tpbi.requsetTime as requsetTime, ");
		sqlresult.append(" tpbi.mobilePhone as mobilePhone, ");
		sqlresult.append(" tpbi.status as tpStatus ");

		// ============
		StringBuffer sql = new StringBuffer();
		sql.append(" t_phone_binding_info tpbi ");
		sql.append(" left join  t_user tuser   on tuser.id = tpbi.userId ");
		sql.append(" left join t_person tp on tuser.id = tp.userId ");
		sql.append(" left join ");
		sql
				.append(" (select SUM(investAmount) as co, ti.investor as tiv from t_invest ti GROUP BY ti.investor) a  ");
		sql.append(" on  a.tiv = tuser.id  ");
		System.out.println(sql.toString());
		System.out.println(sqlresult.toString());
		System.out.println(condition.toString());
		// ===================
		try {
			/*
			 * dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
			 * "", condition.toString());
			 */
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(), "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> querytelphone(Long userId)
			throws SQLException, Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = vidateDao.querytelphone1(conn);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> querytelphone2(Long userId)
			throws SQLException, Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = vidateDao.querytelphone2(conn);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 统计可选认证的总通过数量
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> querySelectPassTotal(Long userId)
			throws Exception {
		Map<String, String> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = vidateDao.querySelectPassTotal(conn, userId);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryUserTelMsg(Long userId, Long tpiid)
			throws Exception {
		Map<String, String> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = vidateDao.queryUserTelMsg(conn, userId, tpiid);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询用户的用户名
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserNameById(Long userId) throws Exception {
		Map<String, String> map = null;
		/*
		 * StringBuffer condition = new StringBuffer(); if(userId!=null&&userId
		 * >=-1){ condition.append(" AND id = "+userId); }
		 */

		Connection conn = MySQL.getConnection();
		try {
			// dataPage(conn, pageBean, "v_t_personcheck", "*", " order by id
			// "+IConstants.SORT_TYPE_DESC, condition.toString());
			map = vidateDao.queryUserNameById(conn, userId);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询个人的单个证件的信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryPerPictruMsgCallBack(long id,
			Integer materAuthTypeId, Long tmtId) throws Exception {
		Map<String, String> map = null;
		/*
		 * StringBuffer condition = new StringBuffer(); if(userId!=null&&userId
		 * >=-1){ condition.append(" AND id = "+userId); }
		 */

		Connection conn = MySQL.getConnection();
		try {
			// dataPage(conn, pageBean, "v_t_personcheck", "*", " order by id
			// "+IConstants.SORT_TYPE_DESC, condition.toString());
			map = vidateDao.queryPerPictruMsgCallBack(conn, id,
					materAuthTypeId, tmtId);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 删除单个证件
	 * 
	 * @param tmdid
	 *            证件id
	 */
	public void deletecertificate(Long tmdid) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			vidateDao.deletecertificate(conn, tmdid);
			
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
	 * 查询个人的图片情况 （5大证件）
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryPerUserPicturMsg(
			PageBean<Map<String, Object>> pageBean, Long userId,
			Integer materAuthTypeId) throws Exception {
		List<Map<String, Object>> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND vtb.id = " + userId);
		}
		if (materAuthTypeId != null) {
			condition.append(" AND vtb.materAuthTypeId = " + materAuthTypeId);
		}
		Connection conn = MySQL.getConnection();
		StringBuffer sqlresult = new StringBuffer();
		sqlresult
				.append(" vtb.tmid  as tmid, tmd.imagePath as imgPath,tmd.id as tmdid,tmd.`option` as tmoption,vtb.username as username,vtb.id as id,vtb.realName as realName,vtb.checkperson as checkperson,vtb.tmoption as alltmoption,tmd.uploadingTime as passTime,tmd.checktime as checktime,vtb.materAuthTypeId as materAuthTypeId,tmd.auditStatus as auditStatus,vtb.tmtname as tmtname ");
		System.out.println(sqlresult);
		StringBuffer sql = new StringBuffer();
		sql.append(" t_materialimagedetal tmd ");
		sql
				.append(" left join v_t_user_picture_base vtb on tmd.materialsauthid = vtb.tmid ");
		System.out.println(sql);
		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
					" order by tmd.id", condition.toString());

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查看可选认证资料显示
	 * 
	 * @param pageBean
	 * @param userId
	 *            用户id
	 * @param materAuthTypeId
	 *            证件类型
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querySelectPictureDate(
			PageBean<Map<String, Object>> pageBean, Long userId,
			Integer materAuthTypeId) throws Exception {
		List<Map<String, Object>> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND vtb.id = " + userId);
		}
		if (materAuthTypeId != null) {
			condition.append(" AND vtb.materAuthTypeId = " + materAuthTypeId);
		}
		Connection conn = MySQL.getConnection();
		StringBuffer sqlresult = new StringBuffer();
		sqlresult
				.append(" vtb.tmid  as tmid,tmd.imagePath as imgPath,tmd.id as tmdid,tmd.`option` as tmoption,vtb.username as username,vtb.id as id,vtb.realName as realName,vtb.checkperson as checkperson,vtb.tmoption as alltmoption,tmd.uploadingTime as passTime,tmd.checktime as checktime,vtb.materAuthTypeId as materAuthTypeId,tmd.auditStatus as auditStatus,vtb.tmtname as tmtname ");
		System.out.println(sqlresult);
		StringBuffer sql = new StringBuffer();
		sql.append(" t_materialimagedetal tmd ");
		sql
				.append(" left join v_t_user_picture_select vtb on tmd.materialsauthid = vtb.tmid ");
		System.out.println(sql);
		try {
			// dataPage(conn, pageBean, "v_t_personcheck", "*", " order by id
			// "+IConstants.SORT_TYPE_DESC, condition.toString());
			// map = vidateDao.queryPerUserPicturMsg(conn,
			// userId,materAuthTypeId );
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
					" order by tmd.id", condition.toString());

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查看所有用户的图片资料
	 * 
	 * @param userId
	 *            用户的id
	 * @param materAuthTypeId
	 *            用户的证件类型
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryPictureDateCount(
			PageBean<Map<String, Object>> pageBean, Long userId,
			Integer materAuthTypeId, String username, String realName)
			throws Exception {
		username = Utility.filteSqlInfusion(username);
		realName = Utility.filteSqlInfusion(realName);
		
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND 1 = 1 ");
		if (userId != null && userId > 0) {
			condition.append(" AND id = " + userId);
		}
		if (StringUtils.isNotBlank(username)) {
			condition.append(" and  username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (materAuthTypeId != null && materAuthTypeId > 0) {
			condition.append(" AND materAuthTypeId = " + materAuthTypeId);
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and  realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_user_picture", "*", " order by id "
					+ IConstants.SORT_TYPE_DESC, condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询用户的可选资料
	 * 
	 * @param pageBean
	 * @param userId
	 * @param materAuthTypeId
	 * @param username
	 * @param realName
	 * @return
	 * @throws SQLException
	 */
	/*
	 * public List<Map<String,Object>> queryselect( PageBean<Map<String,Object>>
	 * pageBean,Integer materAuthTypeId,String username,String realName, String
	 * tausername) throws SQLException{ List<Map<String,Object>> map = null;
	 * Connection conn = MySQL.getConnection(); StringBuffer condition = new
	 * StringBuffer(); condition.append(" AND 1 = 1 ");
	 * if(userId!=null&&userId>0){ condition.append(" AND id = "+userId); }
	 * if(StringUtils.isNotBlank(username)){ condition.append(" AND username
	 * LIKE
	 * CONCAT('%','"+StringEscapeUtils.escapeSql(username.trim())+"','%')"); }
	 * if(StringUtils.isNotBlank(tausername)){ condition.append(" AND tausername
	 * LIKE
	 * CONCAT('%','"+StringEscapeUtils.escapeSql(tausername.trim())+"','%')"); }
	 * if(materAuthTypeId!=null&&materAuthTypeId>0){ condition.append(" AND
	 * materAuthTypeId = "+materAuthTypeId); }
	 * if(materAuthTypeId!=null&&materAuthTypeId>0){ condition.append(" AND
	 * materAuthTypeId = "+materAuthTypeId); }
	 * if(StringUtils.isNotBlank(realName)){ condition.append(" AND realName
	 * LIKE
	 * CONCAT('%','"+StringEscapeUtils.escapeSql(realName.trim())+"','%')"); }
	 * 
	 * try { //dataPage(conn, pageBean, "v_t_personcheck", "*", " order by id
	 * "+IConstants.SORT_TYPE_DESC, condition.toString()); dataPage(conn,
	 * pageBean, "v_t_user_picture_select_3", "*", " order by id
	 * "+IConstants.SORT_TYPE_DESC, condition.toString()); } catch (SQLException
	 * e) { log.error(e); e.printStackTrace(); } catch (Exception e) {
	 * log.error(e); e.printStackTrace(); }finally{ conn.close(); } return map; }
	 */

	/**
	 * 可选认证
	 */
	public List<Map<String, Object>> queryselect(
			PageBean<Map<String, Object>> pageBean, Integer materAuthTypeId,
			String username, String realName, String tausername,
			Integer typeStatus) throws Exception {
		username = Utility.filteSqlInfusion(username);
		realName = Utility.filteSqlInfusion(realName);
		tausername = Utility.filteSqlInfusion(tausername);
		
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND 1 = 1 ");

		if (StringUtils.isNotBlank(username)) {
			condition.append(" and tuser.username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(tausername)) {
			condition.append(" and ta.userName  like '%"
					+ StringEscapeUtils.escapeSql(tausername.trim()) + "%' ");
		}
		if (materAuthTypeId != null && materAuthTypeId > 0) {
			condition.append(" AND materAuthTypeId = " + materAuthTypeId);
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and tp.realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (typeStatus != null && typeStatus != -1) {
			if (typeStatus == 1) {
				condition.append(" AND temp1.noshangchuan > 0 ");
			}
			if (typeStatus == 2) {
				condition.append(" AND f.fail  > 0 ");
			}
			if (typeStatus == 3) {
				condition.append(" AND s.success > 0 ");
			}
			if (typeStatus == 4) {
				condition.append(" AND w.wait > 0 ");
			}
		}
		// =============================
		StringBuffer sqlresult = new StringBuffer();
		sqlresult.append(" tuser.id as id, ");
		sqlresult.append(" tuser.username  as username, ");
		sqlresult.append(" tp.realName as realName, ");
		sqlresult.append(" IFNULL(temp1.noshangchuan,0) as nosh, ");
		sqlresult.append(" IFNULL(w.wait,0) as wait, ");
		sqlresult.append(" IFNULL(f.fail,0) as fail, ");
		sqlresult.append(" IFNULL(s.success,0) as success,");
		sqlresult.append(" IFNULL(p.pass,0) as pass ,");
		sqlresult.append(" ta.userName as tausername ");
		System.out.println(sqlresult.toString());
		// ==========================
		StringBuffer sql = new StringBuffer();
		sql.append(" t_user tuser ");
		sql.append(" left join t_person tp  on tuser.id = tp.userId ");
		sql.append(" left join t_admin ta on tuser.adminId = ta.id ");
		sql
				.append(" left join v_t_noshangchuan  temp1 on tuser.id = temp1.id ");
		sql
				.append(" left join ( select tuser.id,count(*) as wait,tuser.username from t_materialsauth tm , t_user tuser where tm.auditStatus = 1 AND tuser.id = tm.userId  AND tm.materAuthTypeId >5 GROUP BY tuser.username) w on tuser.id = w.id  ");
		sql
				.append(" left join (select tuser.id,count(*) as fail,tuser.username from t_materialsauth tm , t_user tuser where tm.auditStatus = 2 AND tuser.id = tm.userId AND tm.materAuthTypeId >5 GROUP BY tuser.username) f on tuser.id = f.id   ");
		sql
				.append(" left join (select tuser.id,count(*) as success,tuser.username from t_materialsauth tm , t_user tuser where tm.auditStatus = 3 AND tuser.id = tm.userId AND tm.materAuthTypeId >5 GROUP BY tuser.username ) s on tuser.id = s.id  ");
		sql.append(" left join v_t_pasttime p on tuser.id = p.id ");
		// ================================

		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 统计根据搜索结果所得的集合
	 * 
	 * @param materAuthTypeId
	 * @param username
	 * @param realName
	 * @param tausername
	 * @param typeStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryselect1(Integer materAuthTypeId,
			String username, String realName, String tausername,
			Integer typeStatus) throws Exception {
		username = Utility.filteSqlInfusion(username);
		realName = Utility.filteSqlInfusion(realName);
		tausername = Utility.filteSqlInfusion(tausername);
		
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer selectSelect = new StringBuffer();
		selectSelect.append(" select ");
		selectSelect
				.append(" tuser.id as id,tuser.username  as username,tp.realName as realName,IFNULL(temp1.noshangchuan,0) as nosh,IFNULL(w.wait,0) as wait, ");
		selectSelect
				.append(" IFNULL(f.fail,0) as fail, IFNULL(s.success,0) as success,IFNULL(p.pass,0) as pass ,ta.userName as tausername");
		selectSelect.append(" from ");
		selectSelect.append(" t_user tuser ");
		selectSelect.append(" left join t_person tp  on tuser.id = tp.userId ");
		selectSelect.append(" left join t_admin ta on tuser.adminId = ta.id ");
		selectSelect
				.append(" left join v_t_noshangchuan  temp1 on tuser.id = temp1.id ");
		selectSelect
				.append(" left join ( select tuser.id,count(*) as wait,tuser.username from t_materialsauth tm , t_user tuser where tm.auditStatus = 1 AND tuser.id = tm.userId  AND tm.materAuthTypeId >5 GROUP BY tuser.username) w on tuser.id = w.id  ");
		selectSelect
				.append(" left join (select tuser.id,count(*) as fail,tuser.username from t_materialsauth tm , t_user tuser where tm.auditStatus = 2 AND tuser.id = tm.userId AND tm.materAuthTypeId >5 GROUP BY tuser.username) f on tuser.id = f.id  ");
		selectSelect
				.append(" left join (select tuser.id,count(*) as success,tuser.username from t_materialsauth tm , t_user tuser where tm.auditStatus = 3 AND tuser.id = tm.userId AND tm.materAuthTypeId >5 GROUP BY tuser.username ) s on tuser.id = s.id  ");
		selectSelect.append(" left join v_t_pasttime p on tuser.id = p.id ");
		selectSelect.append(" where 1 = 1 ");
		if (StringUtils.isNotBlank(username)) {
			selectSelect.append(" and tuser.username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(tausername)) {
			selectSelect.append(" and ta.userName  like '%"
					+ StringEscapeUtils.escapeSql(tausername.trim()) + "%' ");
		}
		if (materAuthTypeId != null && materAuthTypeId > 0) {
			selectSelect.append(" AND materAuthTypeId = " + materAuthTypeId);
		}
		if (StringUtils.isNotBlank(realName)) {
			selectSelect.append(" and tp.realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (typeStatus != null && typeStatus != -1) {
			if (typeStatus == 1) {
				selectSelect.append(" AND temp1.noshangchuan > 0 ");
			}
			if (typeStatus == 2) {
				selectSelect.append(" AND f.fail  > 0 ");
			}
			if (typeStatus == 3) {
				selectSelect.append(" AND s.success > 0 ");
			}
			if (typeStatus == 4) {
				selectSelect.append(" AND w.wait > 0 ");
			}
		}
		try {
			DataSet dataSet = MySQL.executeQuery(conn, selectSelect.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			map = dataSet.tables.get(0).rows.rowsMap;
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
	 * 查询用户的的待审记录
	 * 
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param autiStatus
	 * @param certificateName
	 * @throws SQLException
	 */
	public void querynewUserCheck(PageBean<Map<String, Object>> pageBean,
			String username) throws Exception {
		username = Utility.filteSqlInfusion(username);
		
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_newusercheck", "*", " order by id "
					+ IConstants.SORT_TYPE_DESC, condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 查找图片资料验证情况
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querymaterialsauth(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.querymaterialsauth(conn, id);
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
	 * 统计新用户未分配的的个数
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> querydistribute() throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = null;
		try {

			map = vidateDao.querydistribute(conn);
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
	 * 根据类型查看单个证件情况
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> querymaterialsauth(Long id, Integer type)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.querymaterialsauth(conn, id, type);
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
	 * 用户申请信用额度详情
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryrequestCredit(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = vidateDao.queryrequestCredit(conn, id);
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
	 * 查询可选资料的信用积分情况
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> querySelectCledit(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.querySelectCledit(conn, id);
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
	 * 查找客服标的所有客服的集合
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryServiceName() throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.queryServiceName(conn);
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
	 * 查找资料的情况
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBaseDataById(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.queryBaseDataById(conn, id);
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
	 * 查找资料的情况
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryWorkDataById(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.queryWorkDataById(conn, id);
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
	 * 查询用户的工作资料的认证情况
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryAllWorkStatus(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {

			map = vidateDao.queryAllWorkStatus(conn, id);
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
	 * @param id
	 * @return List<Map<String,Object>>
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryMeterAuthTypeListByIds(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = vidateDao.queryMeterAuthTypeListByIds(conn, id);
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
	 * 查找可选资料的认证数据
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querySelectCleditList(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = vidateDao.querySelectCleditList(conn, id);
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
	 * 查询客服表
	 * 
	 * @param id
	 * @return List<Map<String,Object>>
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryServiceNameByI() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = vidateDao.queryServiceNameByI(conn);
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
	 * 查询针对于某个用户的列表查询
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAdminCheckList(Long userId,
			Integer materAuthTypeIdStr) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = vidateDao.queryAdminCheckList(conn, userId,
					materAuthTypeIdStr);
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
	 * 查询申请信用额度列表
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	/*
	 * public List<Map<String,Object>> querycreditLimitApply() throws
	 * Exception{ Connection conn = MySQL.getConnection(); List<Map<String,
	 * Object>> list = new ArrayList<Map<String,Object>>(); try { list =
	 * vidateDao.querycreditLimitApply(conn); } catch (Exception e) {
	 * log.error(e); e.printStackTrace(); throw e; } catch (Exception e) {
	 * log.error(e); e.printStackTrace(); throw e; }finally{ conn.close(); }
	 * return list; }
	 */

	/**
	 * 更新审核状态
	 * 
	 * @param userId
	 * @param personId
	 * @param auditStatus
	 * @return
	 * @throws SQLException
	 */
	public Long updatePersonauditStatus(Long userId, Long personId,
			Integer auditStatus) throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;

		try {
			resultId = vidateDao.updatePersonauditStatus(conn, userId,
					personId, auditStatus);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		
		return resultId;
	}

	/**
	 * 审核更换手机状态
	 * 
	 * @param userId
	 * @param personId
	 * @param auditStatus
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserPhoneService(Long userId, Integer auditStatus,
			String option, String newTelNumber, Long tpiid,
			String personCellPhone) throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		try {
			// 如果状态为成功那么要更新t_person表中的手机号码
			if (auditStatus == 5)// 1 的时候表示绑定
			{
				resultId = vidateDao
						.updateUserPhone(conn, userId, newTelNumber);
				if (resultId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			resultId = beVipDao.delectPhone(conn, tpiid, auditStatus, option);
			if (resultId <= 0) {
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
		
		return resultId;
	}

	/**
	 * 
	 * 用户的额度申请处理
	 * 
	 * @param userId
	 *            用户id
	 * @param Creditstatus
	 *            审核状态
	 * @param applyAmount
	 *            审核同意金额
	 * @param checkMsg
	 *            审核意见
	 * @param adminId
	 *            审核管理员id
	 * @param ti
	 *            审核申请表id
	 * @return Long
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserCreditLimit(Long userId, Integer Creditstatus,
			BigDecimal applyAmount, String checkMsg, Long adminId, Long ti)
			throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer msg = new StringBuffer();
		;
		Long resultId = -1L;
		try {
			resultId = vidateDao.updateUserCreditLimit(conn, userId,
					Creditstatus, applyAmount, checkMsg, adminId, ti);
			if (resultId > 0) {
				// 更改申请额度表中的审核记录和状态
				resultId = vidateDao.upTCREDITING(conn, userId, Creditstatus,
						applyAmount, checkMsg, adminId, ti);
				if (resultId <= 0) {
					conn.rollback();
					return -1L;
				}
				String m = "";
				if (Creditstatus == 2) {
					m = "不通过";
				} else if (Creditstatus == 3) {
					m = "通过";
				}
				msg.append("您的申请额度的审核状况:");
				msg.append(m);
				if (applyAmount.compareTo(new BigDecimal("0")) != 0) {
					msg.append("金额为" + applyAmount.toString());
				}
				// 发站内信
				resultId = sendmsgService.sendCheckMail(conn,userId, " 信用额度审核通知",
						msg.toString(), 2, adminId);// 2管理员信件
				if (resultId <= 0) {
					conn.rollback();
					return -1L;
				}
			} else {
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
		
		return resultId;
	}

	/**
	 * 更新信用积分和插入审核列表
	 * 
	 * @param userId
	 *            用户id
	 * @param alloption
	 *            总的审核观念 发站内信给用户
	 * @param creditrating
	 *            信用积分
	 * @param adminId
	 *            审核员的id
	 * @return
	 * @throws Exception
	 */
	public Long Updatecreditrating(Long userId, String alloption,
			Integer creditrating, Long adminId, Integer mterType,
			Integer checkStatus) throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		Long resultId1 = -1L;

		try {
			resultId = vidateDao.Updatecreditrating(conn, userId, alloption,
					creditrating, adminId, mterType, checkStatus);// 更新user表的积分
			if (resultId <= 0) {
				conn.rollback();
				return -1L;
			}
			/*
			 * resultId1 = vidateDao.addCheckRecord(conn, creditrating, adminId,
			 * userId, mterType);// 添加审核记录 if (resultId1 <= 0) {
			 * conn.rollback(); return -1L; }
			 */
			// 发送站内信
			StringBuffer msg = new StringBuffer();
			if (mterType == 1) {
				msg.append("您的身份证认证审核状况:");
			}
			if (mterType == 2) {
				msg.append("您的工作认证审核状况:");
			}
			if (mterType == 3) {
				msg.append("您的居住地认证审核状况:");
			}
			if (mterType == 4) {
				msg.append("您的信用报告审核状况:");
			}
			if (mterType == 5) {
				msg.append("您的收入认证审核状况:");
			}
			if (mterType == 6) {
				msg.append("您的房产认证的审核状况:");
			}
			if (mterType == 7) {
				msg.append("您的购车认证的审核状况:");
			}
			if (mterType == 8) {
				msg.append("您的结婚认证的审核状况:");
			}
			if (mterType == 9) {
				msg.append("您的学历认证的审核状况:");
			}
			if (mterType == 10) {
				msg.append("您的技术认证的审核状况:");
			}
			if (mterType == 11) {
				msg.append("您的手机认证的审核状况:");
			}
			if (mterType == 12) {
				msg.append("您的微博认证的审核状况:");
			}
			if (mterType == 13) {
				msg.append("您的现场认证的审核状况:");
			}
			if (mterType == 14) {
				msg.append("您的抵押认证的审核状况:");
			}
			if (mterType == 15) {
				msg.append("您的担保认证的审核状况:");
			}
			if (mterType == 16) {
				msg.append("您的其他认证的审核状况:");
			}
			msg.append("通过信用积分为:" + creditrating);
			resultId = sendmsgService.sendCheckMail(conn,userId, " 基本资料审核通知", msg
					.toString(), 2, adminId);// 2管理员信件
			if (resultId <= 0) {
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

		return resultId;
	}
	public Long Updatecreditrating1(Long userId, Integer auditStatus,Integer creditrating, Integer mterType) throws Exception{
		Connection conn = MySQL.getConnection();
		long result=-1l;
		try {
			result = vidateDao.Updatecreditrating1(conn, userId,auditStatus,
					creditrating,  mterType);
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
	public Long addeducationcost(Long userId, String educationFree)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		try {
			resultId = vidateDao.addeducationcost(conn, userId, educationFree);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return resultId;
	}

	/**
	 * 添加审核记录
	 * 
	 * @param userId
	 *            用户id
	 * @param alloption
	 *            总审核意见
	 * @param creditrating
	 *            添加的信用积分
	 * @param adminId
	 *            管理员id
	 * @param mterType
	 *            证件类型id
	 * @return
	 * @throws Exception
	 */
	public Long addCheckRecord(Long userId, String alloption,
			Integer creditrating, Long adminId, Integer mterType,
			Integer cCreditration) throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		try {
			resultId = vidateDao.addCheckRecord(conn, creditrating, adminId, userId,
					mterType, cCreditration);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return resultId;
	}

	/**
	 * 统计证件类型明细表被审核的个数
	 * 
	 * @param tmid
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryCheckCount(Long tmid) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		condition.append(" select ");
		condition.append(" IFNULL(COUNT(*),0) as cccc  ");
		condition.append(" from t_materialimagedetal tmd ");
		condition.append(" where tmd.materialsauthid =  " + tmid);
		condition.append("  and tmd.auditStatus = 1 ");
		try {
			DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
			map = BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}

	/**
	 * 审核用户的证件
	 * 
	 * @param userId
	 *            用户id
	 * @param materAuthTypeId
	 *            证件种类
	 * @param authTime
	 *            审核时间
	 * @param option
	 *            审核观点
	 * @param auditStatus
	 *            审核状态
	 * @return Long
	 * @throws SQLException
	 * @throws ParseException
	 * @throws DataException
	 */
	public Long Updatematerialsauth(Long mtdId, Long userId,
			Long materAuthTypeId, String option, Integer auditStatus,
			Long materaldetalId, Integer visiable) throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;

		try {
			resultId = vidateDao.Updatematerialsauth(conn, mtdId, userId,materAuthTypeId, option, auditStatus, materaldetalId,visiable);
			if (resultId <= 0) {
				conn.rollback();
				return -1L;
			}
			
			conn.commit();
			// 设置失效时间

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return resultId;

	}

	/**
	 * 更新用户的工作审核状态
	 * 
	 * @param userId
	 * @param workauthId
	 * @param auditStatus
	 * @param directedStatus
	 * @param otherStatus
	 * @param moredStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateworkStatus(Long userId, Long workauthId,
			Integer auditStatus, Integer directedStatus, Integer otherStatus,
			Integer moredStatus, Long adminId) throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		try {
			resultId = vidateDao.updateworkStatus(conn, userId, workauthId,
					auditStatus, directedStatus, otherStatus, moredStatus);
			if (resultId <= 0) {
				conn.rollback();
				return -1L;
			}
			// 发站内信
			boolean flag1 = countWorkStatusDao.queryWorkStatus(conn, userId);
			if (flag1) {// 表示审核通过了 要发送站内信
				StringBuffer msg = new StringBuffer();
				msg.append("您工作信息的审核状况: 通过");
				// 发站内信
				resultId = sendmsgService.sendCheckMail(conn,userId, " 工作信息的审核通知",
						msg.toString(), 2, adminId);// 2管理员信件
				if (resultId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			boolean flag = countWorkStatusDao.isFailWorkstatus(conn, userId);
			if (flag) {// 审核失败 要发送站内信
				StringBuffer msg = new StringBuffer();
				msg.append("您工作信息的审核状况: 审核失败");
				// 发站内信
				resultId = sendmsgService.sendCheckMail(conn,userId, " 工作信息的审核通知",
						msg.toString(), 2, adminId);// 2管理员信件
				if (resultId <= 0) {
					conn.rollback();
					return -1L;
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
		
		return resultId;
	}

	/**
	 * 更新用户的跟踪人
	 * 
	 * @param userId
	 * @param servicePersonId
	 *            跟踪人id
	 * @return
	 * @throws SQLException
	 */
	public Long updataUserServiceMan(Long userId, Integer servicePersonId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;
		try {
			resultId = vidateDao.updataUserServiceMan(conn, userId,
					servicePersonId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return resultId;
	}

	public Long updataUserServiceMans(String ids, String admins)
			throws Exception {
		Connection conn = Database.getConnection();
		Long resultId = -1L;
		try {
			resultId = vidateDao.updataUserServiceMans(conn, ids, admins);
			if (resultId < 0) {
				conn.rollback();
				return resultId;
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
		
		return resultId;
	}

	public void changeNumToStr(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if(list!=null){
			for (Map<String, Object> map : list) {
				if (map.get("tpStatus").toString().equals(
						"1")) {
					map.put("tpStatus", "成功");
				} else if (map.get("tpStatus").toString().equals(
						"2")) {
					map.put("tpStatus", "审核中");
				} else if (map.get("tpStatus").toString().equals(
						"3")) {
					map.put("tpStatus", "取消");
				} else if (map.get("tpStatus").toString().equals(
						"4")) {
					map.put("tpStatus","失败");
				}

			
			}
		}
	}
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setVidateDao(VidateDao vidateDao) {
		this.vidateDao = vidateDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSendmsgService(SendmsgService sendmsgService) {
		this.sendmsgService = sendmsgService;
	}

	public void setCountWorkStatusDao(CountWorkStatusDao countWorkStatusDao) {
		this.countWorkStatusDao = countWorkStatusDao;
	}

	public void setBeVipDao(BeVipDao beVipDao) {
		this.beVipDao = beVipDao;
	}

}
