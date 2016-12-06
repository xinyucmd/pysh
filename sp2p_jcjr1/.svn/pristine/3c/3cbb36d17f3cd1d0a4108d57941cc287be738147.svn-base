package com.sp2p.service;

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

import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.MyHomeDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;

/**
 * @ClassName: MyHomeService.java
 * @Author: gang.lv
 * @Date: 2013-3-18 下午10:25:00
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的个人主页服务层
 */
public class MyHomeService extends BaseService {

	public static Log log = LogFactory.getLog(MyHomeService.class);

	private MyHomeDao myHomeDao;
	private UserDao userDao;
	private OperationLogDao operationLogDao;

	public MyHomeDao getMyHomeDao() {
		return myHomeDao;
	}

	public void setMyHomeDao(MyHomeDao myHomeDao) {
		this.myHomeDao = myHomeDao;
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

	/**
	 * @MethodName: queryBorrowFinishByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午10:26:03
	 * @Return:
	 * @Descb: 查询已发布的借款列表
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryBorrowFinishByCondition(String title,
			String publishTimeStart, String publishTimeEnd,
			String borrowStatus, long userId, PageBean pageBean)
			throws Exception {
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ " 00:00:00'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + " 23:59:59'");
		}
		if (StringUtils.isNotBlank(borrowStatus)) {
			String idStr = StringEscapeUtils
					.escapeSql("'" + borrowStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" and borrowStatus in(" + idSQL + ")");
		}
		condition.append(" and userId =" + userId);
		System.out.println(condition.toString());
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_publish", resultFeilds,
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
	 * @MethodName: queryHomeInfo
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-22 下午07:09:41
	 * @Return:
	 * @Descb: 查询统计后的个人信息
	 * @Throws:
	 */
	public Map<String, String> queryHomeInfo(long userId) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		String startTime = UtilDate.getMonthFirstDay();
		String endTime = UtilDate.getMonthLastDay();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getUserInfo(conn, ds, outParameterValues, userId,
					startTime, endTime);

			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * @MethodName: queryAccountStatisInfo
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 上午09:27:44
	 * @Return:
	 * @Descb: 查询账户统计
	 * @Throws:
	 */
	public Map<String, String> queryAccountStatisInfo(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getUserAmountSumary(conn, ds, outParameterValues,
					userId);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
			
			//已还给用户的额外收益
			Map<String,String> repayMap = myHomeDao.queryRepayUserInerestAmount(conn, userId);
			double repayInerestAmount = 0;
			if(repayMap!=null && repayMap.size()>0){
				repayInerestAmount = Convert.strToDouble(repayMap.get("repayInerestAmount"), 0);
			}
			//用户投资加息标的额外的收益
			Map<String,String> map1 =  myHomeDao.queryAddInerestAmount(conn, userId);
			if(map1!=null && map1.size()>0){
				double add_interest_amount = Convert.strToDouble(map1.get("add_interest_amount"), 0);
				double accountSum = AmountUtil.getDoubleTwo(map.get("accountSum"));
				map.put("accountSum", AmountUtil.getDoubleTwo(add_interest_amount+accountSum-repayInerestAmount));//账户总资产
				
				double earnSum = Convert.strToDouble(map.get("earnSum"), 0);
				map.put("earnSum", AmountUtil.getDoubleTwo(add_interest_amount+earnSum));//累计收益
				
				double forPaySum = Convert.strToDouble(map.get("forPaySum"), 0);
				map.put("forPaySum", AmountUtil.getDoubleTwo(add_interest_amount+forPaySum-repayInerestAmount));//待收本息
				
				double hasPaySum = Convert.strToDouble(map.get("hasPaySum"), 0);
				map.put("hasPaySum", AmountUtil.getDoubleTwo(hasPaySum+repayInerestAmount));//已收总额
				
				double hasPayInterest = Convert.strToDouble(map.get("hasPayInterest"), 0);
				map.put("hasPayInterest", AmountUtil.getDoubleTwo(hasPayInterest+repayInerestAmount));//已收利息
				
				double forPayInterest = Convert.strToDouble(map.get("forPayInterest"), 0);
				map.put("forPayInterest", AmountUtil.getDoubleTwo(forPayInterest+add_interest_amount-repayInerestAmount));//待收利息
				
				double rateEarnAmount = Convert.strToDouble(map.get("rateEarnAmount"), 0);
				map.put("rateEarnAmount", AmountUtil.getDoubleTwo(rateEarnAmount+add_interest_amount));//利息收益
			}
			 
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLoanStatis
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:58:07
	 * @Return:
	 * @Descb: 查询借款统计
	 * @Throws:
	 */
	public Map<String, String> queryLoanStatis(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBorrowStatis(conn, ds, outParameterValues, id,
					new Date());
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentByOwner
	 * @Param: MyHomeService
	 * @Author:
	 * @Date:
	 * @Return:
	 * @Descb: 查询最近还款日及还款金额
	 * @Throws:
	 */

	public Map<String, String> queryRepaymentByOwner(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryRepaymentByOwner(conn, userId);
			//用户投资加息标的额外的下一个月收益
			Map<String,String> map1 =  myHomeDao.queryNextInerestAmount(conn, userId);
			if(map1!=null && map1.size()>0){
				double next_month_interest_amount = Convert.strToDouble(map1.get("next_month_interest_amount"), 0.00);
				double totalSum = Convert.strToDouble(map.get("totalSum"), 0.00);
				map.put("totalSum", String.valueOf(next_month_interest_amount+totalSum));
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryFinanceStatis
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:58:20
	 * @Return:
	 * @Descb: 查询理财统计
	 * @Throws:
	 */
	public Map<String, String> queryFinanceStatis(Long id) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getFinanceStatis(conn, ds, outParameterValues, id);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
			
			//已还给用户的额外收益
			Map<String,String> repayMap = myHomeDao.queryRepayUserInerestAmount(conn, id);
			double repayInerestAmount = 0;
			if(repayMap!=null && repayMap.size()>0){
				repayInerestAmount = Convert.strToDouble(repayMap.get("repayInerestAmount"), 0);
			}
			//用户投资加息标的额外的收益
			Map<String,String> map1 =  myHomeDao.queryAddInerestAmount(conn, id);
			if(map1!=null && map1.size()>0){
				double add_interest_amount = Convert.strToDouble(map1.get("add_interest_amount"), 0);
				double earnInterest = Convert.strToDouble(map.get("earnInterest"), 0);
				map.put("earnInterest", String.valueOf(earnInterest+repayInerestAmount));//已赚利息
				
				double hasPI = Convert.strToDouble(map.get("hasPI"), 0);
				map.put("hasPI", String.valueOf(hasPI+repayInerestAmount));//已回收本息
				
				double forPI = Convert.strToDouble(map.get("forPI"), 0);
				map.put("forPI", String.valueOf(forPI+add_interest_amount-repayInerestAmount));//待回收本息
				
				
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryBorrowInvestByCondition
	 * @Param: MyHomeService  flag：区分查询列表，0：我的投资，1：招标中的借款
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午11:13:56
	 * @Return:
	 * @Descb: 成功投资借款记录
	 * @Throws:
	 */
	public void queryBorrowInvestByCondition(String title,
			String publishTimeStart, String publishTimeEnd,
			String borrowStatus, Long id, PageBean pageBean, int flag) throws Exception {
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		
		// modify by houli 2013-04-25 添加了b.isDayThe字段
		StringBuffer resultFeilds = new StringBuffer();
		resultFeilds.append("a.debt,a.isDebt,a.debtline,a.id,a.borrowId,b.borrower,b.borrowTitle,b.borrowWay,"
				+ "b.annualRate,b.auditTime,b.deadline,b.publishTime,b.credit,b.creditrating, b.paymentMode,b.borrowStatus,"
				+ "b.schedules,b.times,b.isDayThe ,b.borrowShow ,DATE_FORMAT(a.investTime,'%Y-%m-%d') investTime,b.borrowAmount,b.add_interest,");
		
		StringBuffer condition = new StringBuffer();
		if (!"2".equals(borrowStatus)) {
			condition.append(" and 1=1 ");
		}

		if (StringUtils.isNotBlank(title)) {
			condition.append(" and b.borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and b.auditTime >='"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and b.auditTime <='"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");
		}
		if (StringUtils.isNotBlank(borrowStatus)) {
			String idStr = StringEscapeUtils
					.escapeSql("'" + borrowStatus + "'");
			//whb 添加查询我的投资中满标情形
			String idSQL = "-2,3";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" and b.borrowStatus in(" + idSQL + ")");
		}
		//whb 去掉招标中的借款
//		if (!"2".equals(borrowStatus)) {
//			condition.append(" ) or (b.borrowShow=2) )");
//		}
		// 排除债转
		condition.append(" and a.isDebt !=2 ");
		condition.append("  and a.investor =" + id);
		resultFeilds.append("round(a.investAmount,2) as investAmount");
		if(0 == flag){
			condition.append(" order by a.investTime desc");
		}else if(1 == flag){
			//resultFeilds.append("round(sum(a.investAmount),2) as investAmount");
			//condition.append(" GROUP BY a.borrowId");
			condition.append(" order by b.auditTime desc");
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_invest_borrow a  LEFT JOIN   v_t_invest_borrow_list b  ON a.borrowId=b.id ",
					resultFeilds.toString(), "", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowRecycleByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 上午11:57:07
	 * @Return:
	 * @Descb: 查询回收中的借款
	 * @Throws:
	 */
	public void queryBorrowRecycleByCondition(String title, Long id,
			PageBean pageBean) throws Exception {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%'");
		}
		condition.append(" and investor =" + id);
		
		// 排除债转
		condition.append(" and isDebt !=2");
		Connection conn = MySQL.getConnection();
		StringBuffer comm = new StringBuffer();
		try {
			dataPage(conn, pageBean, " v_t_invest_recycling_my ", resultFeilds,
					" ORDER BY investTime desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryBorrowRecycleByConditionApp(String title,
			long userId, PageBean pageBean) throws SQLException, DataException {
		title = Utility.filteSqlInfusion(title);
		
		String resultFeilds = "a.REPAYPERIOD as \"repayPeriod\",date_format(a.REPAYDATE,'%Y-%m-%d') as \"repayDate\",round(a.RECIVEDPRINCIPAL,2) AS \"forpayPrincipal\",round(a.RECIVEDINTEREST,2) AS \"forpayInterest\", round(a.PRINCIPALBALANCE,2) AS \"principalBalance\", " +
				"round(("+IConstants.I_MANAGE+"*a.RECIVEDINTEREST),2) AS \"manage\",a.ISLATE as \"isLate\",a.LATEDAY as \"lateDay\",round(a.RECIVEDFI,2) AS \"forFI\", (a.RECIVEDINTEREST -round(a.RECIVEDINTEREST*"+IConstants.I_MANAGE+",2)+a.RECIVEDFI ) AS \"earn\"," +
				"d.USERNAME as \"username\",a.ISWEBREPAY as \"isWebRepay\",a.REPAYSTATUS as \"repayStatus\",(round(a.RECIVEDINTEREST,2)+round(a.RECIVEDPRINCIPAL,2)) as \"reciveSum\" ,a.INVEST_ID AS \"id\" ";
	
		StringBuffer condition = new StringBuffer();
//		if (StringUtils.isNotBlank(title)) {
//			condition.append(" and b.\"borrowTitle\"  LIKE '%"+StringEscapeUtils.escapeSql(title.trim())+"%'");
//		}
		condition.append(" and a.REPAYSTATUS=1 and a.OWNER =" + userId);
		Connection conn = connectionManager.getConnection();
		try {
//			dataPage(conn, pageBean, "T_INVEST_REPAYMENT a LEFT JOIN T_REPAYMENT b on a.REPAYID=b.id LEFT JOIN T_BORROW c on b.borrowId=c.id LEFT JOIN T_USER d on c.PUBLISHER=d.id",
//					resultFeilds, " order by a.REPAYDATE ", condition.toString());
			
			dataPage(conn, pageBean, "t_invest_repayment a left join t_repayment b on a.repayId=b.id left join t_borrow c on b.borrowId=c.id left join t_user d on c.publisher=d.id",
					resultFeilds, " order by a.repayDate ", condition.toString());
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
	}

	/**
	 * @MethodName: queryBorrowRecycledByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午02:09:03
	 * @Return:
	 * @Descb: 查询已回收的借款(已停用)
	 * @Throws:
	 */
	public void queryBorrowRecycledByConditionOld(String title, Long id,
			PageBean pageBean) throws Exception {
		title = Utility.filteSqlInfusion(title);
		
		String resultFeilds = "t.bid,t.investor,t.borrowId,t.borrower,t.borrowTitle,t.borrowWay,t.credit,t.creditrating,t.annualRate,t.deadline"
				+ " ,t.isDayThe " + // add by houli 区别天标还是月标
				",round(t.realAmount,2) as realAmount,round(t.forTotalSum,2) forTotalSum,t.isDebt ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and t.borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		condition.append(" and t.investor =" + id
				+ " and IFNULL(t.forTotalSum,0)>0 ");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					//"  (select *  from v_t_invest_recycled_sum_ a LEFT JOIN v_t_invest_borrow_list b ON a.borrowId = b.id where  b.borrowShow = 1 "
					//whb 添加查询已回收借款中的活利宝借款
					"  (select *  from v_t_invest_recycled_sum_ a LEFT JOIN v_t_invest_borrow_list b ON a.borrowId = b.id where  b.borrowShow in (1,2)"
							+ " union all"
							+ " select * from  v_t_invest_flow a left join v_t_invest_borrow_list c on  a.borrowId = c.id where  c.borrowShow = 2 "
							+ " ) t", resultFeilds, "", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @MethodName: queryBorrowRecycledByCondition
	 * @Param: MyHomeService
	 * @Author: whb
	 * @Date: 2015-2-3 
	 * @Return:
	 * @Descb: 查询已回收的借款
	 * @Throws:
	 */
	public void queryBorrowRecycledByCondition(String title, Long id,
			PageBean pageBean) throws Exception {
		
		title = Utility.filteSqlInfusion(title);
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and t.borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		condition.append(" and t.investor =" + id);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_already_repay t", " * ", " ORDER BY completeDate asc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowForpayById
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:55:11
	 * @Return:
	 * @Descb: 查询投资人回收中的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowForpayById(long minId,
			long userId, long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeDao.queryBorrowForpayById(conn, minId, userId, investId);
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
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryBorrowHaspayById
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:55:36
	 * @Return:
	 * @Descb: 查询投资人已回收的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowHaspayById(long borrowId,
			long userId, long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeDao.queryBorrowHaspayById(conn, borrowId, userId, investId);
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
	 * @MethodName: queryBorrowBackAcountByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 上午12:02:11
	 * @Return:
	 * @Descb: 借款回账查询
	 * @Throws:
	 */
	public void queryBorrowBackAcountByCondition(String title,
			String publishTimeStart, String publishTimeEnd, Long id,
			PageBean pageBean) throws Exception {
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		
		String resultFeilds = " a.id as investId,c.username as borrower,b.borrowTitle,b.id as borrowId,b.annualRate,b.deadline,"
				+ " round(a.realAmount,2) as realAmount,round((a.hasPrincipal+a.hasInterest),2) forHasSum,round((a.recivedPrincipal+a.recievedInterest-a.hasPrincipal-a.hasInterest),2) forTotalSum"
				+ ",b.isDayThe,b.borrowWay  ";// add by houli 添加是否为天标标志
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and b.borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and b.publishTime >'"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ " 00:00:00'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and b.publishTime <'"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim())
					+ " 23:59:59'");
		}
		condition.append(" and a.investor =" + id);
		condition
				.append(" and (a.recivedPrincipal+a.recievedInterest-a.hasPrincipal-a.hasInterest) > 0");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" t_invest a left join t_borrow b on a.borrowId = b.id left join t_user c on b.publisher= c.id  ",
					resultFeilds, "", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryAutomaticBid
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:33
	 * @Return:
	 * @Descb: 查询用户自动投标设置
	 * @Throws:
	 */
	public Map<String, String> queryAutomaticBid(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryAutomaticBid(conn, id);
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
	 * @MethodName: automaticBidSet
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:33:29
	 * @Return:
	 * @Descb: 自动投标状态设置
	 * @Throws:
	 */
	public long automaticBidSet(long status, long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = myHomeDao.automaticBidSet(conn, status, userId);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				if (status == 1) {
					operationLogDao.addOperationLog(conn, "t_automaticbid",
							Convert.strToStr(userMap.get("username"), ""),
							IConstants.UPDATE, Convert.strToStr(userMap
									.get("lastIP"), ""), 0, "开启自动投标", 1);
				} else {
					operationLogDao.addOperationLog(conn, "t_automaticbid",
							Convert.strToStr(userMap.get("username"), ""),
							IConstants.UPDATE, Convert.strToStr(userMap
									.get("lastIP"), ""), 0, "关闭自动投标", 1);
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
	 * @MethodName: automaticBidModify
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:03:37
	 * @Return:
	 * @Descb: 修改自动投标内容
	 * @Throws:
	 */
	public long automaticBidModify(Double bidAmount, Double rateStart,
			Double rateEnd, Double deadlineStart, Double deadlineEnd,
			Double creditStart, Double creditEnd, Double remandAmount, Long id,
			String borrowWay) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			Map<String, String> map = myHomeDao.hasAutomaticBid(conn, id);
			if (map != null && map.size() > 0) {
				// 更新内容
				result = myHomeDao.automaticBidUpdate(conn, bidAmount,
						rateStart, rateEnd, deadlineStart, deadlineEnd,
						creditStart, creditEnd, remandAmount, id, borrowWay);
			} else {
				// 添加内容
				result = myHomeDao.automaticBidAdd(conn, bidAmount, rateStart,
						rateEnd, deadlineStart, deadlineEnd, creditStart,
						creditEnd, remandAmount, id, borrowWay);
			}
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, id);
				operationLogDao.addOperationLog(conn, "t_automaticbid", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.UPDATE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "设置自动投标", 1);
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
	 * @MethodName: queryBackAcountStatis
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-29 下午11:47:49
	 * @Return:
	 * @Descb: 回账统计
	 * @Throws:
	 */
	public Map<String, String> queryBackAcountStatis(long userId,
			String startTime, String endTime, String title) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		title = StringEscapeUtils.escapeSql(title.trim());
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBackAcountStatis(conn, ds, outParameterValues,
					startTime, endTime, title, userId);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
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
	
	/***
	 * 添加自动投标
	 * 
	 * @param type
	 * @param limits
	 * @param rate
	 * @param amount
	 * @param state
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long addAppointInvest(int type,int limits,double rate,double amount,int state,long userId) throws Exception {

		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			myHomeDao.addAppointInvest(conn, type, limits, rate, amount, state, userId);
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
	
	/***
	 * 不能重复预约
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAppointInvest(long userId) throws Exception {

		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryAppointInvest(conn,userId);
			 
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
		
	}
	
	/**
	 * 删除预约自动投标
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long removeAppointInvest(long id) throws Exception {

		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			m = myHomeDao.removeAppointInvest(conn,id);
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
	
	
	
	
	/**
	 * 查询预约记录
	 * @param borrowId
	 * @param userId
	 * @param investId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAppointInvestRecond(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeDao.queryAppointInvestRecond(conn,userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}
	
	
	public Map<String, String> queryOptions(long id) throws SQLException,DataException {
            Connection conn = MySQL.getConnection();
            Map<String, String> map = new HashMap<String, String>();
			try {
				map = myHomeDao.queryOptions(conn, id);
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			} finally {
				conn.close();
			}

         return map;
    }
	
	/***
	 * 未来30天的还款
	 * @param userId
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryRepayPlanCondition(Long userId,
			PageBean pageBean) throws Exception {
		String currDateNext30 = DateUtil.getCurrDateLateDay(30);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		 
		condition.append(" and  realRepayDate is null ");
		condition.append(" and  repayDate>= DATE_FORMAT(NOW(),'%Y-%m-%d') and repayDate<= '"+currDateNext30+"' and investor="+userId );
	
		Connection conn = MySQL.getConnection();
		 
		try {
			dataPage(conn, pageBean, " v_t_repay_plan ", resultFeilds,
					" order by repayDate asc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	public Map<String, String> queryUserName(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryUserName(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	
	
}