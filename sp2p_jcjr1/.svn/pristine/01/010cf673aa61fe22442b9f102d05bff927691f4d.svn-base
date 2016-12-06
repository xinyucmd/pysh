package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: FinanceDao.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:15:29
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 理财数据处理层
 */
public class FinanceDao {
	
	/**
	 * @MethodName: queryBorrowAll
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 查询全部借款
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowAll(Connection conn, String borrow_t,String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order) throws Exception {
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
		
		String resultFeilds = " id,minTenderedSum,countPer,borrowShow,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus,paymentMode,"
				+ "add_interest as add_interest_src,IF(hasPWD=1,0,add_interest) as add_interest ";
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1 and sorts!= 0 ");
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
		Dao.Views.v_t_borrow_list borrowList = new Dao().new Views().new v_t_borrow_list();
		DataSet dataSet = borrowList.open(conn, resultFeilds, condition.toString() + " order by sorts desc,schedules asc ,id desc", "",-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowList
	 * @Param: 列表类型：type=success/new（success为成交标的列表，new为新增标的列表）
	 * @Author: whb
	 * @Return: List<Map<String, Object>>
	 * @Descb: 查询借款列表(米袋360)
	 * @Throws: SQLException, DataException
	 */
	public List<Map<String, Object>> queryBorrowList(Connection conn, String type, String date, String dateNext)
			throws SQLException, DataException {
		type = Utility.filteSqlInfusion(type);
		date = Utility.filteSqlInfusion(date);
		dateNext = Utility.filteSqlInfusion(dateNext);
		
		StringBuffer condition = new StringBuffer(" 1=1");
		if("success".equals(type)){
			condition.append(" and time_2 = '" + date + "'");
		}
		if("new".equals(type)){
			condition.append(" and time_0 >= '" + date + "'");
			condition.append(" and time_0 < '" + dateNext + "'");
			condition.append(" and time_2 >= '" + dateNext + "'");
		}
		Dao.Views.v_t_borrow_list_medai borrowList = new Dao().new Views().new v_t_borrow_list_medai();
		DataSet dataSet = borrowList.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:15
	 * @Return:
	 * @Descb: 根据ID查询借款的详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_detail borrowDetail = new Dao().new Views().new v_t_borrow_detail();
		DataSet dataSet = borrowDetail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
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
	public Map<String, String> queryInvest(Connection conn, long userId,long borrowId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select SUM(a.investAmount) as investAmount  from t_invest a where a.investor = "+userId+" and  a.borrowId = "+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 单次最大投标金额
	 * @param conn
	 * @param userId
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBorrow(Connection conn,long borrowId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.maxTenderedSum as maxTenderedSum  from t_borrow a where a.id ="+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * @MethodName: queryInvestPerson
	 * @Param: FinanceDao
	 * @Author: whb
	 * @Return:
	 * @Descb: 根据ID查询借款的详情
	 * @Throws:
	 */
	public Map<String, String> queryInvestPerson(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DataSet dataSet = t_invest.open(conn, " COUNT(id) AS countPer ", " borrowId=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
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
	public Map<String, String> queryUserInfoById(Connection conn, long id)
			throws SQLException, DataException {
		
		Dao.Views.v_t_borrow_user_info v_t_borrow_user_info = new Dao().new Views().new v_t_borrow_user_info();
		DataSet dataSet = v_t_borrow_user_info.open(conn, "id , userId , f_formatting_username( username) as username , username as username_2 ,vipStatus ,rating , personalHead ,address , credit , creditrating , createTime ,DATE_FORMAT(lastDate,'%Y-%m-%d') as lastDate  , creditLimit ,  vip , age ,maritalStatus ,  workPro , workCity , companyLine , companyScale , job ,school ,highestEdu ,eduStartDay ,  hasHourse , hasCar ,  hasHousrseLoan , hasCarLoan , auditperson ,  auditwork ,nativePlaceCity,nativePlacePro,nativePlace,sex", " id=" + id,
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
    /**
     * 根据ID查询借款信息发布者个人信息(后台发布的借款)
     * queryUserInfoByIdAdmin
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException
     * @autthor linww
     * 2014-6-5 下午05:22:02
     */
	public Map<String, String> queryUserInfoByIdAdmin(Connection conn, long id)
	throws SQLException, DataException {

	Dao.Views.v_t_borrow_user_info_byadmin v_t_borrow_user_info_byadmin = new Dao().new Views().new v_t_borrow_user_info_byadmin();
	DataSet dataSet = v_t_borrow_user_info_byadmin.open(conn, "id , userId ,f_formatting_username(username) as username , username as username_2 ,vipStatus ,rating , personalHead ,address , credit , creditrating , createTime ,DATE_FORMAT(lastDate,'%Y-%m-%d') as lastDate  , creditLimit ,  vip , age ,maritalStatus ,  workPro , workCity , companyLine , companyScale , job ,school ,highestEdu ,eduStartDay ,  hasHourse , hasCar ,  hasHousrseLoan , hasCarLoan , auditperson ,  auditwork ,nativePlaceCity,nativePlacePro,nativePlace,sex", " id=" + id,
			"", 0, 1);
	return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据ID查询借款信息发布者企业人信息(后台发布的借款)
	 * queryEnterpriseUserInfoById
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-10 上午09:32:48
	 */
	public Map<String, String> queryEnterpriseUserInfoById(Connection conn, long id)
	throws SQLException, DataException {

	Dao.Views.v_t_borrow_enterpriseuser_info v_t_borrow_enterpriseuser_info = new Dao().new Views().new v_t_borrow_enterpriseuser_info();
	DataSet dataSet = v_t_borrow_enterpriseuser_info.open(conn, "id,userId,f_formatting_username(username) as username, username as username_2 ,vipStatus,credit,creditrating,createTime,lastDate,creditLimit,companyName,companyAddress,companyPhone,legalPerson,registeredTime,registeredCapital,businessCode,borrowCause", " id=" + id,
			"", 0, 1);
	return BeanMapUtils.dataSetToMap(dataSet);
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
	public List<Map<String, Object>> queryUserIdentifiedByid(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materialsauth user_materialsauth = new Dao().new Views().new v_t_borrow_user_materialsauth();
		DataSet dataSet = user_materialsauth.open(conn, " * ,sum(case when visiable = 1 then 1 else 0 end) visiable_2 ", " id=" + id+" group by materAuthTypeId ", " ",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据ID查询用户认证信息（后台发布的借款）
	 * queryUserIdentifiedByidAdmin
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-5 下午05:28:46
	 */
	public List<Map<String, Object>> queryUserIdentifiedByidAdmin(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materialsauth_byadmin v_t_borrow_user_materialsauth_byadmin = new Dao().new Views().new v_t_borrow_user_materialsauth_byadmin();
		DataSet dataSet = v_t_borrow_user_materialsauth_byadmin.open(conn, " * , sum(case when visiable = 1 then 1 else 0 end) visiable_2 ", " id=" + id+" group by materAuthTypeId ", " ",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryRePayByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_repayment v_t_borrow_repayment = new Dao().new Views().new v_t_borrow_repayment();
		DataSet dataSet = v_t_borrow_repayment.open(conn, " * ", " borrowId=" + id, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryCollectionByid(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_collection collection = new Dao().new Views().new v_t_borrow_collection();
		DataSet dataSet = collection.open(conn, " * ", " borrowId=" + id, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryInvestByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_investrecord investrecord = new Dao().new Views().new v_t_borrow_investrecord();
		DataSet dataSet = investrecord.open(conn, "  id ,borrowId , f_formatting_username(username) as username , investAmount , investTime , investor , creditedStatus ,user_name ", " borrowId=" + id,
				//" id desc", -1, -1);//modify by houli 按照时间的正序排
				" investTime asc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String, String> queryGroupById(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " select a.groupName,b.userId  from t_group a ,t_group_user b WHERE a.id = b.groupId and a.groupName = '机构客户' and b.userId ="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet); 
	}
	
	/**
	 * @Author: whb
	 * @Return:
	 * @Descb: 添加超级用户显示名称变换
	 * @Throws:
	 */
	public Long updateChangeUsername(Connection conn, long investId,String userName) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.user_name_as.setValue(userName);
		return t_invest.update(conn, " id= "+investId);
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
	public Map<String, String> queryDebtById(Connection conn, long borrowId, long investId)
			throws SQLException, DataException {
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if(borrowId > 0){
			condition.append(" and borrow_id = ");
			condition.append(borrowId);
		}
		if(investId > 0){
			condition.append(" and invest_id = ");
			condition.append(investId);
		}
		condition.append(" and repayStatus = 1 ");
		DataSet dataSet = t_invest_repayment.open(conn, " MAX(ownerlist) AS ownerlist ", condition.toString(), "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: getInvestStatus
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:47:23
	 * @Return:
	 * @Descb: 获取借款投标的状态,条件是正在招标中
	 * @Throws:
	 */
	public Map<String, String> getInvestStatus(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow_invest = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow_invest.open(conn, " isDayThe,minTenderedSum,borrowTitle,borrowWay,deadline,id,hasPWD ,nid_log,hasCirculationNumber,circulationNumber,smallestFlowUnit ",
				" borrowStatus =2 and id=" + id, " id desc", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * @MethodName: getInvestToFbaba
	 * @Return:
	 * @Descb: 获取借款投标的信息
	 * @Throws:
	 */
	public Map<String, String> getInvestToFbaba(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow.open(conn, " * ", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * @MethodName: getFbabaUser
	 * @Param: FinanceDao
	 * @Author: whb
	 * @Return:
	 * @Descb: 获取借款的投标状态,条件是正在招标中
	 * @Throws:
	 */
	public Map<String, String> getFbabaUser(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_parteners_user t_parteners_user = new Dao().new Tables().new t_parteners_user();
		DataSet dataSet = t_parteners_user.open(conn, " (TO_DAYS(NOW())-TO_DAYS(reg_time)) AS day ",
				" user_id =" + id, " id desc", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询用户红包金额
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserHbMoney(Connection conn, long userId,int m)
			throws SQLException, DataException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currDate =  df.format(new Date())+" 00:00:00";
		StringBuffer sql = new StringBuffer();
		sql.append( " select sum(a.bonus_avaliable) as bonus_avaliable from t_bonus_list a where a.user_id ="+userId+" and a.status=1 and a.end_time>='"+currDate+"'");
		if(m==1){
			sql.append(" and a.bonus_type=1 ");	
		}
		if(m==2){
			sql.append(" and a.bonus_type=2 ");	
		}
		
		if(m==3){
			sql.append(" and a.bonus_type=3 ");	
		}
		if(m==12){
			sql.append(" and a.bonus_type in (1,2) ");	
		}
		if(m==13){
			sql.append(" and a.bonus_type in (1,3) ");	
		}
		if(m==23){
			sql.append(" and a.bonus_type in (2,3) ");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrowInvest
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:52:43
	 * @Return:
	 * @Descb: 根据ID获取借款投标中的借款内容
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInvest(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_invest v_t_borrow_invest = new Dao().new Views().new v_t_borrow_invest();
		DataSet dataSet = v_t_borrow_invest.open(conn, " * ", " id=" + id, "",
				0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryUserMonney
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午08:47:41
	 * @Return:
	 * @Descb: 查询用户的金额
	 * @Throws:
	 */
	public Map<String, String> queryUserMonney(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet dataSet = t_user.open(conn,
				" (usableSum+freezeSum) AS totalSum,usableSum ", " id="
						+ userId, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询红包的配置
	 * @return
	 */
	public Map<String,String> queryBonusConfig(Connection conn)throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT t_bonus_config.*,DATE_FORMAT(t_bonus_config.start_time,'%Y-%m-%d') as startTime from t_bonus_config");
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: addBrowseCount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:51:36
	 * @Return:
	 * @Descb: 更新浏览量
	 * @Throws:
	 */
	public long addBrowseCount(Connection conn, Long id) throws SQLException {
		long returnId = -1L;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		StringBuffer condition = new StringBuffer();
		condition.append("UPDATE t_borrow  SET remainTimeStart = '"
				+ sf.format(new Date()));
		condition.append("' WHERE remainTimeEnd IS NOT NULL AND ");
		condition.append(" remainTimeStart <remainTimeEnd AND id =" + id);
		returnId = MySQL.executeNonQuery(conn,
				" update t_borrow set visitors = visitors+1 where id = " + id);
		MySQL.executeNonQuery(conn, condition.toString());
		condition=null;
		return returnId;
	}

	/**
	 * @MethodName: valideInvest
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:06:58
	 * @Return:
	 * @Descb: 验证投资人是否符合本次投标
	 * @Throws:
	 */
	public String valideInvest(Connection conn, long id, long userId,
			double amount) throws SQLException, DataException {
		int returnId = 0;
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();

		DataSet investAmountDataSet = t_borrow.open(conn, " id ", " id=" + id
				+ " and (borrowAmount-hasInvestAmount) >=" + amount, "", 0, 1);
		returnId = investAmountDataSet.tables.get(0).rows.getCount();
		if (returnId == 0) {
			return "您的投标金额超过本轮剩余投标金额";
		} else {
			DataSet maxTenderedSumDataSet = t_borrow.open(conn,
					" maxTenderedSum ", " id=" + id, "", 0, 1);
			returnId = maxTenderedSumDataSet.tables.get(0).rows.getCount();
			DataRow dr = maxTenderedSumDataSet.tables.get(0).rows.get(0);
			BigDecimal maxTenderedSum = (BigDecimal) dr.get("maxTenderedSum");
			if (maxTenderedSum != null) {
				if (returnId == 0) {
					return "您的投标金额超过本轮最多投标金额";
				} else {
					DataSet usableSumDataSet = t_user.open(conn, " id ", " id="
							+ userId + " and usableSum >" + amount, "", 0, 1);
					returnId = usableSumDataSet.tables.get(0).rows.getCount();
					if (returnId == 0) {
						return "您的可用余额不够进行本轮投标";
					}
				}
			} else {
				DataSet usableSumDataSet = t_user.open(conn, " id ", " id="
						+ userId + " and usableSum >" + amount, "", 0, 1);
				returnId = usableSumDataSet.tables.get(0).rows.getCount();
				if (returnId == 0) {
					return "您的可用余额不够进行本轮投标";
				}
			}
		}
		return "";
	}

	/**
	 * @MethodName: valideTradePassWord
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:23
	 * @Return:
	 * @Descb: 验证交易密码
	 * @Throws:
	 */
	public String valideTradePassWord(Connection conn, long userId, String pwd)
			throws SQLException, DataException {
		String passWord = Encrypt.MD5(pwd.trim());
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		passWord=com.shove.web.Utility.filteSqlInfusion(passWord);
		DataSet dataSet = t_user.open(conn, " id ", " id=" + userId
				+ " and dealpwd ='" + StringEscapeUtils.escapeSql(passWord) + "'", "", 0, 1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		if (map == null || map.size() == 0) {
			return "交易密码错误";
		}
		return "";
	}

	/**
	 * @MethodName: addBorrowInvest
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午05:46:28
	 * @Return:
	 * @Descb: 添加借款投资
	 * @Throws:
	 */
	public Long addBorrowInvest(Connection conn, long id, long userId,
			double borrowSum, double annualRate,double deadlineDouble) throws SQLException {
		long returnId = -1;
		// 添加投资记录
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(borrowSum);
		t_invest.realAmount.setValue(borrowSum);
		t_invest.monthRate.setValue(annualRate/12);
		t_invest.investor.setValue(userId);
		t_invest.oriInvestor.setValue(userId);
		t_invest.investTime.setValue(new Date());
		t_invest.borrowId.setValue(id);
		t_invest.deadline.setValue(deadlineDouble);
		returnId = t_invest.insert(conn);
		// 更新借款信息中的已投资总额和数量
		returnId = MySQL.executeNonQuery(conn,
				" update t_borrow set hasInvestAmount = hasInvestAmount+"
						+ borrowSum + ",investNum=investNum+1" + " where id = "
						+ id);
		// 更新投资人的资金信息
		returnId = MySQL.executeNonQuery(conn,
				" update t_user set usableSum = usableSum-" + borrowSum
						+ ",freezeSum=freezeSum+" + borrowSum + " where id = "
						+ userId);
		return returnId;
	}

	/**
	 * @MethodName: isFullSale
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午12:44:07
	 * @Return:
	 * @Descb: 判断是否符合满标的条件投资金额已经达到借款金额
	 * @Throws:
	 */
	public Map<String, String> isFullSale(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append(" borrowStatus =" + IConstants.BORROW_STATUS_2);
		condition.append(" and borrowAmount = hasInvestAmount");
		condition.append(" and remainTimeStart < remainTimeEnd");
		condition.append(" and id=" + id);
		Dao.Tables.t_borrow t_borrow_invest = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow_invest.open(conn, " id ", condition
				.toString(), " id desc", -1, -1);
		condition = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateBorrowFullSale
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午12:57:53
	 * @Return:
	 * @Descb: 更新借款的状态为满标
	 * @Throws:
	 */
	public long updateBorrowFullSale(Connection conn, long id,int sorts)
			throws SQLException, DataException {
		long returnId = -1L;
		StringBuffer condition = new StringBuffer();
		condition.append("update t_borrow set ");
		condition.append(" sort = "+sorts);
		condition.append(", borrowStatus =" + IConstants.BORROW_STATUS_3);
		condition.append(",remainTimeStart= remainTimeEnd");
		condition.append(" where id =" + id);
		returnId = MySQL.executeNonQuery(conn, condition.toString());
		condition = null;
		return returnId;
	}

	/**
	 * @MethodName: addBorrowMSG
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午08:15:42
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public Long addBorrowMSG(Connection conn, long id, long userId,
			String msgContent) throws SQLException {
		Dao.Tables.t_msgboard t_msgboard = new Dao().new Tables().new t_msgboard();
		t_msgboard.msgContent.setValue(msgContent);
		t_msgboard.modeId.setValue(id);
		// 借款留言类型
		t_msgboard.msgboardType.setValue(1);
		t_msgboard.msger.setValue(userId);
		t_msgboard.msgTime.setValue(new Date());
		return t_msgboard.insert(conn);
	}

	/**
	 * @MethodName: addFocusOn
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午08:58:05
	 * @Return:
	 * @Descb: 我的关注
	 * @Throws:
	 */
	public Long addFocusOn(Connection conn, long id, long userId, int moduleType)
			throws SQLException {
		Dao.Tables.t_concern t_concern = new Dao().new Tables().new t_concern();
		t_concern.moduleId.setValue(id);
		t_concern.userId.setValue(userId);
		t_concern.moduleType.setValue(moduleType);
		return t_concern.insert(conn);
	}

	/**
	 * @MethodName: hasFocusOn
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午11:02:03
	 * @Return:
	 * @Descb: 查询用户是否已经有关注
	 * @Throws:
	 */
	public Map<String, String> hasFocusOn(Connection conn, long id,
			long userId, int moduleType) throws SQLException, DataException {
		String condition = "moduleId = " + id + " and userId = " + userId
				+ " and moduleType=" + moduleType;
		Dao.Tables.t_concern t_concern = new Dao().new Tables().new t_concern();
		DataSet dataSet = t_concern.open(conn, " id ", condition, "", 0, 1);
		condition =  null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: addUserMail
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午09:59:59
	 * @Return:
	 * @Descb: 添加用户站内信
	 * @Throws:
	 */
	public Long addUserMail(Connection conn, long reciver, Long userId,
			String title, String content, int mailType) throws SQLException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.reciver.setValue(reciver);
		t_mail.sender.setValue(userId);
		t_mail.mailTitle.setValue(title);
		t_mail.mailContent.setValue(content);
		t_mail.mailType.setValue(mailType);
		t_mail.mailMode.setValue(1);
		t_mail.sendTime.setValue(new Date());
		return t_mail.insert(conn);
	}

	/**
	 * @MethodName: addUserReport
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:13:31
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public Long addUserReport(Connection conn, long reporter, Long userId,
			String title, String content) throws SQLException {
		Dao.Tables.t_report t_report = new Dao().new Tables().new t_report();
		t_report.reporter.setValue(reporter);
		t_report.user.setValue(userId);
		t_report.reportTitle.setValue(title);
		t_report.reportContent.setValue(content);
		t_report.reportTime.setValue(new Date());
		return t_report.insert(conn);
	}
	
	
	/**
	 *  pc 新版 首页    ----已经发的标但是时间未到
	 */
	public List<Map<String, Object>> queryLastestBorrowWay(Connection conn,int borrowWay)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_type_index v_t_borrow_type_index = new Dao().new Views().new v_t_borrow_type_index();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" sorts != 0 and borrowStatus =2 and investCount=0  and borrowWay="+borrowWay);
		DataSet dataSet = v_t_borrow_type_index.open(conn, " * ", buffer.toString(), " publishTime desc  ", 0,1);
		 
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 *  投资风云榜
	 */
	public List<Map<String, Object>> queryUserInvestRecode(Connection conn)
			throws SQLException, DataException {   
		    StringBuffer buffer = new StringBuffer();
		    buffer.append(" SELECT DATE_FORMAT(a.investTime,'%m-%d %h:%i:%s') as investTime, ROUND(a.investAmount,0) as investAmount, concat(left(b.username,1),'***') as username   from t_invest a ,t_user b where a.investor = b.id ORDER BY a.investTime desc  LIMIT 0,20 ");
			DataSet dataSet = MySQL.executeQuery(conn, buffer.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLastBorrow
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:25:24
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrow(Connection conn)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_index v_t_borrow_index = new Dao().new Views().new v_t_borrow_index();
		DataSet dataSet = v_t_borrow_index.open(conn, " * ", " sorts != 0 and borrowStatus in (2,3,4,5) ", " schedules asc, publishTime desc, sorts desc ", 0,
				200);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String, Object>> queryBorrowIndex(Connection conn,int borrowWay)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_index v_t_borrow_index = new Dao().new Views().new v_t_borrow_index();
		DataSet dataSet = v_t_borrow_index.open(conn, " * ", " sorts != 0 and borrowStatus in (2,3,4,5) and borrowWay="+borrowWay, " schedules asc, publishTime desc, sorts desc ", 0,
				1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * @MethodName: queryLastestBorrow
	 * @Param: FinanceService
	 * @Author: whb
	 * @Return:
	 * @Descb: 首页只推送定息宝（暂时）
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrowAppByBorrowWay(Connection conn)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_index v_t_borrow_index = new Dao().new Views().new v_t_borrow_index();
//		DataSet dataSet = v_t_borrow_index.open(conn, " * ", " sorts != 0 and borrowStatus in (2,3,4,5) and borrowWay = 4", " sorts desc , schedules asc  ", 0,
//				12);
		DataSet dataSet = v_t_borrow_index.open(conn, " * ", " sorts != 0 and borrowStatus in (2,3,4,5) and borrowWay = 4", " applyTime DESC  ", 0,
		12);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLastBorrow
	 * @Param: FinanceDao
	 * @Author: whb
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrowApp(Connection conn)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_index v_t_borrow_index = new Dao().new Views().new v_t_borrow_index();
		DataSet dataSet = v_t_borrow_index.open(conn, " * ", " sorts != 0 and borrowStatus in (2,3,4,5) and schedules < 100 and borrowWay = 4", " ", 0,1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	
	/**   
	 * @MethodName: investRank  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:10:04
	 * @Return:    
	 * @Descb: 投资排名前8条记录
	 * @Throws:
	*/
	public List<Map<String, Object>> investRank(Connection conn,String starTime,String endTime)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM(t.borrowSum) AS borrowSum,f_formatting_username(b.username) as username,f_rating(b.rating) AS rating FROM(");
		sql.append(" SELECT SUM(realAmount) AS borrowSum,investor,investTime FROM t_invest ");
		starTime=com.shove.web.Utility.filteSqlInfusion(starTime);
		endTime=com.shove.web.Utility.filteSqlInfusion(endTime);
		if (StringUtils.isNotBlank(starTime)) {
			sql.append(" where investTime >= '"+StringEscapeUtils.escapeSql(starTime)+"'") ;
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append("  and investTime <='"+StringEscapeUtils.escapeSql(endTime)+"'") ;
		}
		sql.append(" GROUP BY investor");
		sql.append(" UNION ALL SELECT SUM(realAmount) AS borrowSum,investor,investTime FROM t_invest_history ");
		if (StringUtils.isNotBlank(starTime)) {
			sql.append(" where investTime >= '"+StringEscapeUtils.escapeSql(starTime)+"'") ;
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append("  and investTime <='"+StringEscapeUtils.escapeSql(endTime)+"'") ;
		}
		sql.append("GROUP BY investor");
		sql.append(" )t LEFT JOIN t_user b ON t.investor = b.id GROUP BY t.investor ORDER BY borrowSum DESC LIMIT 0,8");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**   
	 * @MethodName: queryTotalRisk  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:33:42
	 * @Return:    
	 * @Descb: 查询风险保障金总额
	 * @Throws:
	*/
	public Map<String,String> queryTotalRisk(Connection conn) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "SELECT ((SUM(riskInCome)-SUM(riskSpending))) AS total FROM t_risk_detail limit 0,1");
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @MethodName: queryCurrentRisk  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:33:46
	 * @Return:    
	 * @Descb: 查询当日风险保障金收支金额
	 * @Throws:
	*/
	public Map<String,String> queryCurrentRisk(Connection conn) throws SQLException, DataException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(new Date());
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		DataSet dataSet = t_risk_detail.open(conn, " sum(riskInCome) as riskInCome,sum(riskSpending) as riskSpending ", " riskDate= '" +date+"'",
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: getInvestPWD  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:38:27
	 * @Return:    
	 * @Descb: 获取投标密码是否正确
	 * @Throws:
	*/
	public Map<String, String> getInvestPWD(Connection conn,long idLong, String investPWD) throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		investPWD=com.shove.web.Utility.filteSqlInfusion(investPWD);
		DataSet dataSet = t_borrow.open(conn, " id ", " id="+idLong+" and investPWD='"+StringEscapeUtils.escapeSql(investPWD.trim())+"'",
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查找投资人信息
	 * add by houli
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryInvestorById(Connection conn,long investorId,int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DataSet dataSet = t_invest.open(conn, " investor ", 
				" investor="+investorId,"",0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据借款id和投资人查找投资id
	 * add by whb
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryInvestId(Connection conn,long borrowId, long investor) throws SQLException, DataException{
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		StringBuffer command = new StringBuffer();
		if(investor > 0){
			command.append(" investor = " + investor);
		}
		if(borrowId > 0){
			command.append(" and borrowId = " + borrowId);
		}
		DataSet dataSet = t_invest.open(conn, " max(id) as investId", command.toString(),"",0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: queryInvestIdByFlag  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午05:38:44
	 * @Return:    
	 * @Descb: 查询投资的id
	 * @Throws:
	*/
	public Map<String, String> queryInvestIdByFlag(Connection conn, String flag) throws SQLException, DataException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DataSet dataSet = t_invest.open(conn, " id ", 
				" flag="+flag,"",0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryUserImageByid  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:21:25
	 * @Return:    
	 * @Descb: 查询用户认证通过的图片
	 * @Throws:
	*/
	public List<Map<String, Object>> queryUserImageByid(Connection conn,
			long typeId, long userId) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materauth_img user_materialsauth_img = new Dao().new Views().new v_t_borrow_user_materauth_img();
		DataSet dataSet = user_materialsauth_img.open(conn, " * ", " materAuthTypeId=" + typeId+" and userId="+userId + " and visiable='1' ", "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 项目详情查看抵押物
	 * @param conn
	 * @param typeId
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUserImageByidDyw(Connection conn,
			long typeId, long userId) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materauth_img user_materialsauth_img = new Dao().new Views().new v_t_borrow_user_materauth_img();
		DataSet dataSet = user_materialsauth_img.open(conn, " * ", " materAuthTypeId=" + typeId+" and userId="+userId, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**   
	 * @MethodName: queBorrowInfo  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午01:56:10
	 * @Return:    
	 * @Descb: 查询借款信息
	 * @Throws:
	*/
	public Map<String, String> queBorrowInfo(Connection conn, long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" select a.borrowWay as borrowWay,a.auditTime as auditTime, a.excitationType as excitationType,a.excitationSum as excitationSum,a.circulationNumber as circulationNumber, a.version,a.annualRate,(a.annualRate/12) monthRate");
		command.append(",a.borrowAmount,a.deadline,a.borrowTitle,a.publisher,a.isDayThe,b.username as borrowerName from");
		command.append(" t_borrow a left join t_user b on a.publisher=b.id where a.id="+ id);
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}
	

	/**   
	 * @MethodName: addInvest  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午02:04:59
	 * @Return:    
	 * @Descb: 添加投资记录
	 * @Throws:
	*/
	public long addInvest(Connection conn, double investAmount,
			double realAmount, double monthRate, long investor,long oriInvestor, long id, int deadline,int isAutoBid) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(investAmount);
		t_invest.realAmount.setValue(realAmount);
		t_invest.monthRate.setValue(monthRate);
		t_invest.investor.setValue(investor);
		t_invest.oriInvestor.setValue(oriInvestor);
		t_invest.borrowId.setValue(id);
		t_invest.deadline.setValue(deadline);
		t_invest.investTime.setValue(new Date());
		t_invest.isAutoBid.setValue(isAutoBid);
		return t_invest.insert(conn);
	}
	
	/**
	 * @MethodName: updateBorrowStatus
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午04:47:06
	 * @Return:
	 * @Descb: 更新借款状态
	 * @Throws:
	 */
	public long updateBorrowStatus(Connection conn, double investAmount,
			 long copies,long id,int version) throws SQLException {
		long returnId = -1;
		StringBuffer command = new StringBuffer();
		command.append("UPDATE t_borrow SET hasInvestAmount = hasInvestAmount+ "+investAmount);
		command.append(",hasCirculationNumber=hasCirculationNumber+"+copies);
		command.append(",investNum=investNum+1,version=version+1 WHERE id =" + id);
		command.append(" and hasInvestAmount <borrowAmount and version="+version);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		return returnId;
	}
	
	

	/**   
	 * @MethodName: freezeUserAmount  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午04:48:11
	 * @Return:    
	 * @Descb: 投资人投资成功资金冻结
	 * @Throws:
	*/
	public long freezeUserAmount(Connection conn, double investAmount,
			long userId) throws SQLException {
		long returnId = -1;
		StringBuffer command = new StringBuffer();
		command.append("UPDATE t_user SET usableSum = usableSum-"+investAmount+", freezeSum=freezeSum+"+investAmount+" WHERE id ="+userId);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return returnId;
	}
	
	
	/**   
	 * @MethodName: updateFullBorrow  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午04:51:11
	 * @Return:    
	 * @Descb: 更新满标的借款
	 * @Throws:
	*/
	public long updateFullBorrow(Connection conn,long id) throws SQLException{
		long returnId = -1;
		StringBuffer command = new StringBuffer();
		command.append("update t_borrow set borrowStatus =3,sort = 5,remainTimeStart= remainTimeEnd where borrowAmount=hasInvestAmount AND borrowStatus = 2 AND id = "+id);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null; 
		return returnId;
	}

	
	/**   
	 * @MethodName: queryUserAmountAfterHander  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午05:06:05
	 * @Return:    
	 * @Descb: 查询用户操作后的资金记录
	 * @Throws:
	*/
	public Map<String, String> queryUserAmountAfterHander(Connection conn,
			long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select ifnull(a.usableSum,0) usableSum,ifnull(a.freezeSum,0) freezeSum,ifnull(sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest),0.0) forPI ,a.lastIP as lastIP ");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id="+userId+" group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询待还金额
	 */
	public Map<String,String> queryUserRepayMount(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append("select ifnull(sum((a.stillPrincipal+a.stillInterest-a.hasPI+a.lateFI-a.hasFI)),0) as forpaySum ");
		command.append("from t_repayment a left join t_borrow b on a.borrowId = b.id where a.repayStatus = 1 and b.publisher= "+userId+" GROUP BY b.publisher");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**   
	 * @MethodName: addUserDynamic  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 上午10:28:50
	 * @Return:    
	 * @Descb: 添加用户动态
	 * @Throws:
	*/
	public long addUserDynamic(Connection conn, long userId, String url) throws SQLException {
		Dao.Tables.t_user_recorelist t_user_recorelist = new Dao().new Tables().new t_user_recorelist();
		t_user_recorelist.userId.setValue(userId);
		t_user_recorelist.url.setValue(url);
		t_user_recorelist.time.setValue(new Date());
		return t_user_recorelist.insert(conn);
	}

	/**   
	 * @MethodName: updateFullScall  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:01:50
	 * @Return:    
	 * @Descb: 更新满标的状态
	 * @Throws:
	*/
	public void updateFullScall(Connection conn, long id) throws SQLException {
        StringBuffer command = new StringBuffer();
        command.append("update t_borrow set borrowStatus =3,remainTimeStart= remainTimeEnd where borrowAmount=hasInvestAmount AND borrowStatus = 2 and id ="+id);
        MySQL.executeNonQuery(conn, command.toString());
        command = null;
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryBorrowTenderIn  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:03:40
	 * @Return:    
	 * @Descb: 查询招标中的借款
	 * @Throws:
	*/
	public Map<String, String> queryBorrowTenderIn(Connection conn, long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id,version,smallestFlowUnit,(circulationNumber-hasCirculationNumber) as remainCirculationNumber FROM t_borrow WHERE "
				+ "borrowAmount>hasInvestAmount AND borrowStatus = 2 AND id ="
				+ id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryInvestAmount  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:06:31
	 * @Return:    
	 * @Descb: 本轮剩余投标金额
	 * @Throws:
	*/
	public Map<String, String> queryInvestAmount(Connection conn, long id,double investAmount) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE (borrowAmount-hasInvestAmount) >="+investAmount+"  AND id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryMinTenderedSum  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:19:15
	 * @Return:    
	 * @Descb: 查询最小投标金额
	 * @Throws:
	*/
	public Map<String, String> queryMinTenderedSum(Connection conn, long id,
			double investAmount) throws SQLException, DataException {  
	    StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE minTenderedSum > (borrowAmount-hasInvestAmount) and id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryUserUsableSum  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:19:25
	 * @Return:    
	 * @Descb: 查询用户可用金额
	 * @Throws:
	*/
	public Map<String, String> queryUserUsableSum(Connection conn, long userId,double investAmount) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id,usableSum  FROM t_user WHERE usableSum < "+investAmount+" and id ="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryMaxTenderedSum  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:24:07
	 * @Return:    
	 * @Descb: 查询最大投标金额
	 * @Throws:
	*/
	public Map<String, String> queryMaxTenderedSum(Connection conn, long id,
			double investAmount) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE maxTenderedSum < "+investAmount+" and maxTenderedSum is not null and id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryMinTenderedSumMaps  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:26:49
	 * @Return:    
	 * @Descb: 查询本轮最小投标金额
	 * @Throws:
	*/
	public Map<String, String> queryMinTenderedSumMaps(Connection conn,
			long id, double investAmount) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE minTenderedSum > "+investAmount+" and minTenderedSum is not null and id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
public List<Map<String, Object>> queryBorrowAllTime(Connection conn,String borrowWay)
			throws SQLException, DataException {
		 
		Dao.Views.v_t_borrow_time borrowListTime = new Dao().new Views().new v_t_borrow_time();
		DataSet dataSet = borrowListTime.open(conn, "*", " borrowWay in "+borrowWay, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**   
	 * @MethodName: addAutoBidRecord  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-27 上午11:47:20
	 * @Return:    
	 * @Descb: 添加自动投标用户投标记录
	 * @Throws:
	*/
	public long addAutoBidRecord(Connection conn, long userId, int borrowId) throws SQLException {
		Dao.Tables.t_automaticbid_user t_automaticbid_user = new Dao().new Tables().new t_automaticbid_user();
		t_automaticbid_user.userId.setValue(userId);
		t_automaticbid_user.borrowId.setValue(borrowId);
		return t_automaticbid_user.insert(conn);
	}

	/**   
	 * @MethodName: queryHasInvest  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-14 上午10:54:46
	 * @Return:    
	 * @Descb: 查询已投资总额是否小于等于借款总额
	 * @Throws:
	*/
	public Map<String, String> queryHasInvest(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select id from t_borrow where hasInvestAmount <=borrowAmount and id ="+id);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**   
	 * @MethodName: reBackBorrowStatus  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-14 下午04:56:11
	 * @Return:    
	 * @Descb: 回退借款信息中的已投资总额和数量
	 * @Throws:
	*/
	public long reBackBorrowStatus(Connection conn, double bidAmount,
			int borrowId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("UPDATE t_borrow SET hasInvestAmount = hasInvestAmount- "+bidAmount+",investNum=investNum-1 WHERE id ="+borrowId);
		long result= Database.executeNonQuery(conn, command.toString());
		command = null;
		return result;
	}
	
	/**
	 * @MethodName: updateRepo
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午09:05:09
	 * @Return:
	 * @Descb: 更新回购中的状态
	 * @Throws:
	 */
	public void updateRepo(Connection conn, Long id) throws SQLException {
		String command = "update t_borrow set borrowStatus =4 where borrowAmount=hasInvestAmount AND borrowStatus = 2 and id ="
						+ id;
		MySQL.executeNonQuery(conn, command.toString());
		command = null;
	}

	public long addCirculatioinInvest(Connection conn, double investAmount,
			double realAmount, double monthRate, long investor,
			long oriInvestor, long id, int deadline,
			int circulationForpayStatus, double circulationInterest,Date repayDate,
			double excutation,String reason,int isAutoBid)
			throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(investAmount);
		t_invest.realAmount.setValue(realAmount);
		t_invest.monthRate.setValue(monthRate);
		t_invest.investor.setValue(investor);
		t_invest.oriInvestor.setValue(oriInvestor);
		t_invest.borrowId.setValue(id);
		t_invest.deadline.setValue(deadline);
		t_invest.investTime.setValue(new Date());
		if(repayDate !=null){
			t_invest.repayDate.setValue(repayDate);			
		}
		t_invest.circulationForpayStatus.setValue(circulationForpayStatus);
		t_invest.circulationInterest.setValue(circulationInterest);
		t_invest.recivedPrincipal.setValue(investAmount);
		t_invest.recievedInterest.setValue(circulationInterest);
		t_invest.reward.setValue(excutation);
		t_invest.reason.setValue(reason);
		t_invest.isAutoBid.setValue(isAutoBid);
		
		return t_invest.insert(conn);
	}
	/**
	 * @throws SQLException
	 * @MethodName: addUserAmount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午11:04:23
	 * @Return:
	 * @Descb: 添加用户金额
	 * @Throws:
	 */
	public long addUserAmount(Connection conn, double investAmount, long userId)
			throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum =usableSum+" + investAmount
				+ " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}
	/**
	 * @MethodName: deductedUserAmount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午11:04:14
	 * @Return:
	 * @Descb: 扣除用户金额
	 * @Throws:
	 */
	public long deductedUserAmount(Connection conn, double investAmount,
			long userId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum =usableSum-" + investAmount
				+ " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}
	
	public List<Map<String, Object>> queryInvestTotal(Connection conn)
	throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT SUM(a.investAmount) as totalAmount,b.borrowWay as borrowWay,count(1) as count from t_invest a INNER JOIN t_borrow b on a.borrowId = b.id ");
		command.append(" and (b.borrowWay = 6 or b.borrowWay = 4) and (b.borrowStatus =4 or b.borrowStatus =5 or b.borrowStatus =2 or b.borrowStatus =3) GROUP BY b.borrowWay ");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long addHbRecode(Connection conn,long userId,String fundMode, double handleSum,double usableSum,
			double spending,String remarks) throws SQLException {
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		t_fundrecord.userId.setValue(userId);
		t_fundrecord.fundMode.setValue(fundMode);
		t_fundrecord.handleSum.setValue(handleSum);
		t_fundrecord.freezeSum.setValue(0);
		t_fundrecord.dueinSum.setValue(0);
		t_fundrecord.usableSum.setValue(usableSum);
		t_fundrecord.spending.setValue(spending);
		t_fundrecord.remarks.setValue(remarks);
		t_fundrecord.recordTime.setValue(new Date());
		t_fundrecord.trader.setValue(-1);
		return t_fundrecord.insert(conn);
	}
	
	public long add51Recode(Connection conn,long userId,long trader,String fundMode, double handleSum,double usableSum,
			double spending,String remarks,int temp,long borrow_id) throws SQLException {
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		t_fundrecord.userId.setValue(userId);
		t_fundrecord.fundMode.setValue(fundMode);
		t_fundrecord.handleSum.setValue(handleSum);
		t_fundrecord.freezeSum.setValue(0);
		t_fundrecord.dueinSum.setValue(0);
		t_fundrecord.usableSum.setValue(usableSum);
		if(temp==1){
			t_fundrecord.spending.setValue(spending);
		}
		if(temp==2){
			t_fundrecord.income.setValue(spending);
		}
		t_fundrecord.remarks.setValue(remarks);
		t_fundrecord.recordTime.setValue(new Date());
		t_fundrecord.trader.setValue(trader);
		t_fundrecord.borrow_id.setValue(borrow_id);
		return t_fundrecord.insert(conn);
	}
	
	public Map<String, String> query51ActivtyComfig(Connection conn, String code) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select a.*,date_format(a.start_time,'%Y-%m-%d') as start_time_f ,date_format(a.end_time,'%Y-%m-%d') as  end_time_f  from t_setting_activity a where a.code ='"+code+"'");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	//请注意，未用上此方法哦 
	public Map<String, String> queryIsCurrUserJiuHua(Connection conn, long user_id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append( " select * from  t_recommend_user  t where t.userId = "+user_id+" and t.recommendUserId = (select id from t_user where username = 'any')" );
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryIsQQUser(Connection conn, long user_id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append( " select * from  t_recommend_user  t where t.userId = "+user_id+" and t.recommendUserId in (select b.userId from t_group a,t_group_user b where a.id = b.groupId and a.groupName = 'QQ群主')" );
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询QQ群主推荐投资人进行投资投资获得佣金，其中投资人在5.1活动时间范围之内的投资
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> query51QQMainRecomment(Connection conn,long repay_id,String start_time,String end_time)
			throws SQLException, DataException {
				StringBuffer command = new StringBuffer();
				command.append(" select a.borrowId,a.stillInterest,b.recivedInterest,b.hasInterest ,b.owner,d.recommendUserId ,c.investTime  "
						+ " from t_repayment a,t_invest_repayment b ,t_invest c,t_recommend_user d   ");
				command.append(" where a.id = b.repayId and b.invest_id = c.id and b.owner = d.userId  ");
				command.append(" and d.recommendUserId in (select n.userId from t_group m,t_group_user n where m.id = n.groupId and m.groupName = 'QQ群主') ");
				command.append(" and a.id = "+repay_id+" and  ((c.investTime >= '"+start_time+"' and DATE_FORMAT(c.investTime,'%Y-%m-%d') <= '"+end_time+"'"+") or (c.investTime >= '2015-07-06' and c.investTime<='2015-10-11'))");
				DataSet dataSet = MySQL.executeQuery(conn, command.toString());
				command = null;
				dataSet.tables.get(0).rows.genRowsMap();
				return dataSet.tables.get(0).rows.rowsMap;
			}
	
	/**
	 * 根据还款Id 查询标的Id
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryRepayment(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select * from t_repayment t where t.id = "+id);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryBorrowById(Connection conn, long borrowId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select * from t_borrow t where t.id = "+borrowId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long addTyjInvest(Connection conn,long userId,long borrowId, double getLx,String repayDate) throws SQLException {
		Dao.Tables.t_invest_new t_invest_new = new Dao().new Tables().new t_invest_new();
		t_invest_new.userId.setValue(userId);
		t_invest_new.borrowId.setValue(borrowId);
		t_invest_new.getLx.setValue(getLx);
		t_invest_new.repayDate.setValue(repayDate);
		t_invest_new.createTime.setValue(new Date());
		return t_invest_new.insert(conn);
	}
	
	
	public List<Map<String, Object>> queryTyjRepay(Connection conn,String currentDate)throws SQLException, DataException {
		        StringBuffer command = new StringBuffer();
				command.append(" SELECT z.username,z.mobilePhone, t.* from t_invest_new t,t_user z where t.userId = z.id and t.state = 0 and DATE_FORMAT(t.repayDate,'%Y-%m-%d') ='"+currentDate+"'" );
				DataSet dataSet = MySQL.executeQuery(conn, command.toString());
				command = null;
				dataSet.tables.get(0).rows.genRowsMap();
				return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long updateTyjRepay(Connection conn,long id) throws SQLException {
		Dao.Tables.t_invest_new t_invest_new = new Dao().new Tables().new t_invest_new();
		t_invest_new.state.setValue(1);
		return t_invest_new.update(conn, " id="+id);
	}
	
	public List<Map<String, Object>> queryUserSendTyj(Connection conn, long userId,String currDate) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select * from t_send_tyj t where t.state=0 and t.userId = "+userId+" and DATE_FORMAT(t.endTime,'%Y-%m-%d')>='"+currDate+"'");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long updateSendTyjState(Connection conn,long userId) throws SQLException {
		Dao.Tables.t_send_tyj t_send_tyj = new Dao().new Tables().new t_send_tyj();
		t_send_tyj.state.setValue(1);
		return t_send_tyj.update(conn, " userId="+userId);
	}
	
	public long updateSendTyjStateById(Connection conn,long id) throws SQLException {
		Dao.Tables.t_send_tyj t_send_tyj = new Dao().new Tables().new t_send_tyj();
		t_send_tyj.state.setValue(1);
		return t_send_tyj.update(conn, " id="+id);
	}
	
	public long updateBorrowAmountAble(Connection conn,long id,double moeny) throws SQLException {
		Dao.Tables.t_borrow_new t_borrow_new = new Dao().new Tables().new t_borrow_new();
		t_borrow_new.amount_able.setValue(moeny);
		return t_borrow_new.update(conn, " id="+id);
	}
	
	public Map<String, String> queryInvestNew(Connection conn, long userId) throws SQLException, DataException {
		String time = "2015-08-26";
		String time1 = "2015-09-15";
		StringBuffer command = new StringBuffer();
		command.append(" SELECT t.*,DATE_FORMAT(t.repayDate,'%Y-%m-%d') as repayDate_f ,"
				+ "(IF(DATE_FORMAT(t.repayDate,'%Y-%m-%d')<='"+time+"',1,0)) as proc_1,"
				+ "(IF(DATE_FORMAT(t.repayDate,'%Y-%m-%d')>='"+time1+"',1,0)) as proc_2 "
				+ " FROM t_invest_new t where t.state=1"
				+ " and  t.userId = "+userId+" "
				+ " and (DATE_FORMAT(t.repayDate,'%Y-%m-%d')<='"+time+"'"
				+ " or DATE_FORMAT(t.repayDate,'%Y-%m-%d')>='"+time1+"')");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryInvest(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" SELECT sum(t.investAmount) as investAmount  FROM t_invest t where t.investor = "+userId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 2016-06-29
	 * @param conn
	 */
	public Map<String, String> searchInvest(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" SELECT sum(t.investAmount) as investAmount , DATE_FORMAT(u.createTime,'%Y-%m-%d') as time FROM t_invest t,t_user u  where  t.investor=u.id and t.investor = "+userId +" and u.createTime<'2016-07-01'");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 更新到期 未 使用 的体验金---过期
	 * 
	 * @param conn
	 * @param end_time
	 * @return
	 * @throws SQLException
	 */
	public long updateUserTyj(Connection conn,String end_time) throws SQLException {
		Dao.Tables.t_send_tyj t_send_tyj = new Dao().new Tables().new t_send_tyj();
		t_send_tyj.state.setValue(2);
		return t_send_tyj.update(conn, " DATE_FORMAT(endTime,'%Y-%m-%d') ='"+end_time+"' and state = 0 ");
	}
	
	/***
	 * 判断投资人是否有还款完毕的投资
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryInvestRepayStatus(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" SELECT * from t_invest t where t.repayStatus = 2 and  t.investor = "+userId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 查询标投资总金额
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryInvestSumAmount(Connection conn, long borrowId) throws Exception{
		StringBuffer command = new StringBuffer();
		command.append(" SELECT SUM(t.investAmount) as investAmountSum from   t_invest t where t.borrowId ="+borrowId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryRowCount(Connection conn) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" SELECT ROW_COUNT() as rowCount ");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询 【到期】 【年化】 【本金】
	 * @param conn
	 * @param investor
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserInvestSumForBorrEnd(Connection conn, long investor)
				throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( "  SELECT SUM(hasPrincipal*(t_borrow.deadline/12)) as investAmountSum  FROM t_repayment ");
		sql.append(" LEFT JOIN t_invest_repayment on t_invest_repayment.repayId = t_repayment.id ");
		sql.append(" LEFT JOIN t_borrow on t_repayment.borrowId = t_borrow.id ");
		sql.append(" where  t_repayment.repayStatus = 2 AND t_borrow.borrowWay = 4 AND hasPrincipal >0 ");
		sql.append(" and t_invest_repayment.`owner` =  "+investor);
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet); 
	 }
	
	public Map<String, String> queryEmployeeConfigByUserId(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select * from t_employee_config t where t.userId = "+userId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long updateEmployeeBorrowAbleAount(Connection conn,long id,int amount_able) throws SQLException {
		Dao.Tables.t_employee_borrow t_employee_borrow = new Dao().new Tables().new t_employee_borrow();
		t_employee_borrow.amount_able.setValue(amount_able);
		return t_employee_borrow.update(conn, " id="+id);
	}
	
	public long addEmployeeInvest(Connection conn,long userId,long borrowId, int amount) throws Exception {
		Dao.Tables.t_employee_invest t_employee_invest = new Dao().new Tables().new t_employee_invest();
		t_employee_invest.user_id.setValue(userId);
		t_employee_invest.borrow_id.setValue(borrowId);
		t_employee_invest.amount.setValue(amount);
		t_employee_invest.create_time.setValue(new Date());
		t_employee_invest.user_name.setValue(DateUtil.getChangeUserName());
		return t_employee_invest.insert(conn);
	}
	
	public List<Map<String, Object>> queryEmployeeRepayment(Connection conn)throws SQLException, DataException {
        StringBuffer command = new StringBuffer();
		command.append(" SELECT  a.*,b.username,b.mobilePhone  from  t_employee_repayment a LEFT JOIN t_user b on a.userId = b.id " );
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
   }
	
	public List<Map<String, Object>> queryOctoberInvestAmount(Connection conn,String start_time,String end_time)throws SQLException, DataException {
        StringBuffer command = new StringBuffer();
		command.append(" SELECT  t.investor as userId,ROUND(SUM(t.investAmount*t.deadline/12),2) as investAmountSum  from  t_invest t "
				+ " where t.investor NOT in (SELECT  userId  from t_group_user m where  m.groupId in(5,6,7,8)) "
				+ " and  DATE_FORMAT(t.investTime,'%Y-%m-%d')>='"+start_time+"'"
				+ " and DATE_FORMAT(t.investTime,'%Y-%m-%d')<='"+end_time+"'"
				+ " GROUP BY t.investor  HAVING investAmountSum>=100000 " );
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
   }
	
	
	public List<Map<String, Object>> checkctoberInvestAmount(Connection conn)throws SQLException, DataException {
        StringBuffer command = new StringBuffer();
		command.append("SELECT  *  from t_fundrecord t where  t.fundMode = '年化投资额满10W返现千分之五'");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
   }
	public List<Map<String, Object>> queryNovemberInvestAmount(Connection conn)throws SQLException, DataException {
	        StringBuffer command = new StringBuffer();
			command.append(" SELECT  t.investor as userId,SUM(if(t.isDebt=1,t.investAmount*t.deadline/12, t.investAmount*(TO_DAYS(date_sub(b.auditTime, interval -b.deadline month)) -  TO_DAYS(DATE_FORMAT(t.investTime,'%Y-%m-%d'))  )/360)) as investAmountSum  from  t_invest t,t_borrow b where t.borrowId=b.id "
					+ " and  t.investor NOT in (SELECT  userId  from t_group_user m where  m.groupId in(5,6,7,8)) "
					+ " and  DATE_FORMAT(t.investTime,'%Y-%m-%d')>='2015-11-13' "
					+ " and DATE_FORMAT(t.investTime,'%Y-%m-%d')<='2015-11-30' "
					+ " GROUP BY t.investor HAVING investAmountSum>=30000 " );
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			command = null;
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	   }	



		public List<Map<String, Object>> queryDecemberInvestAmount(Connection conn)throws SQLException, DataException {
		    StringBuffer command = new StringBuffer();
			command.append(" SELECT  t.investor as userId,SUM(t.investAmount) as investAmountSum  from  t_invest t "
					+ " where  t.investor NOT in (SELECT  userId  from t_group_user m where  m.groupId in(5,6,7,8)) "
					+ " and  DATE_FORMAT(t.investTime,'%Y-%m-%d')>='2015-12-07' "
					+ " and DATE_FORMAT(t.investTime,'%Y-%m-%d')<='2015-12-31' "
					+ " GROUP BY t.investor HAVING investAmountSum>=50000 ORDER BY investAmountSum desc " );
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			command = null;
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}
		
		public List<Map<String, Object>> queryDecemberInvestRepayOver(Connection conn,long userId,String curSysDate)throws SQLException, DataException {
		    StringBuffer command = new StringBuffer();
			command.append(" SELECT  DISTINCT(a.id),a.investor,a.investAmount,a.deadline  from t_invest a ,t_repayment b where a.borrowId = b.borrowId  "
					+ " and  DATE_FORMAT(a.investTime,'%Y-%m-%d')>='2015-12-07' "
					+ " and DATE_FORMAT(a.investTime,'%Y-%m-%d')<='2015-12-31' "
					+ " and a.repayStatus = 2 and a.isDebt = 1 and  b.realRepayDate= '"+curSysDate+"'"
					+ " and  a.investor="+userId);
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			command = null;
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}
		
		public long addActivityci(Connection conn,double amount,double rate,String endTime,long userId,long borrowId) throws Exception {
			Dao.Tables.t_activity_ci t_activity_ci = new Dao().new Tables().new t_activity_ci();
			t_activity_ci.amount.setValue(amount);
			t_activity_ci.rate.setValue(rate);
			t_activity_ci.end_time.setValue(endTime);
			t_activity_ci.user_id.setValue(userId);
			t_activity_ci.borrow_id.setValue(borrowId);
			t_activity_ci.status.setValue(0);
			t_activity_ci.create_time.setValue(new Date());
			return t_activity_ci.insert(conn);
		}
}
