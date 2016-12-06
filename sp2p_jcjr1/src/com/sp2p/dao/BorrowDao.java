package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.sp2p.constants.IConstants;
import com.sp2p.constants.IAmountConstants;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_company_loan;
import com.sp2p.database.Dao.Tables.t_fundrecord;
import com.sp2p.database.Dao.Tables.t_invest;
import com.sp2p.database.Dao.Tables.t_sms;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: FinanceDao.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:15:29
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 借款数据处理层
 */
public class BorrowDao {

	/**
	 * @MethodName: addBorrow
	 * @Param: BorrowDao
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午04:56:52
	 * @Return:
	 * @Descb: 添加借款数据处理
	 * @Throws:
	 */
	public Long addBorrow(Connection conn, String title, String imgPath,
			int borrowWay, int purpose, int deadLine, int paymentMode,
			double amount, double annualRate, double minTenderedSum,
			double maxTenderedSum, int raiseTerm, int excitationType,
			double sum, String detail, int excitationMode, String investPWD,
			int hasPWD, String remoteIP, long publisher,int daythe,String nid_log,
			double frozen_margin,String json,String jsonState,int sorts, int isExclus) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowTitle.setValue(title);
		t_borrow.nid_log.setValue(nid_log);
		t_borrow.imgPath.setValue(imgPath);
		t_borrow.borrowWay.setValue(borrowWay);
		t_borrow.purpose.setValue(purpose);
		t_borrow.deadline.setValue(deadLine);
		t_borrow.paymentMode.setValue(paymentMode);
		t_borrow.minTenderedSum.setValue(minTenderedSum);
		if(maxTenderedSum != -1){
			t_borrow.maxTenderedSum.setValue(maxTenderedSum);			
		}
		t_borrow.raiseTerm.setValue(raiseTerm);
		t_borrow.excitationType.setValue(excitationType);
		t_borrow.detail.setValue(detail);
		t_borrow.excitationMode.setValue(excitationMode);
		t_borrow.publisher.setValue(publisher);
		t_borrow.tradeType.setValue(1);
		if (StringUtils.isNotBlank(investPWD)) {
			t_borrow.investPWD.setValue(Encrypt.MD5(investPWD));
		}
		t_borrow.hasPWD.setValue(hasPWD);
		t_borrow.publishIP.setValue(remoteIP);
		t_borrow.publishTime.setValue(new Date());
		if (amount != -1) {
			t_borrow.borrowAmount.setValue(amount);
		}
		if (annualRate != -1) {
			t_borrow.annualRate.setValue(annualRate);
		}
		if (sum != -1) {
			t_borrow.excitationSum.setValue(sum);
		}
		t_borrow.isDayThe.setValue(daythe);
		t_borrow.frozen_margin.setValue(frozen_margin);
		t_borrow.feelog.setValue(json);
		t_borrow.feestate.setValue(jsonState);
		t_borrow.sort.setValue(sorts);
		t_borrow.isExclus.setValue(isExclus);
		return t_borrow.insert(conn);
	}

	public Long addBorrow(Connection conn, String title, String imgPath,
			int borrowWay, int purpose, int deadLine, int paymentMode,
			double amount, double annualRate, double minTenderedSum,
			double maxTenderedSum, int raiseTerm, int excitationType,
			double sum, String detail, int excitationMode, String investPWD,
			int hasPWD, String remoteIP, long publisher,int daythe,String smallestFlowUnit,
			int circulationNumber,int hasCirculationNumber,String nid_log ,double frozen_margin,
			String json,String jsonState,int sorts, int isExclus) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.smallestFlowUnit.setValue(smallestFlowUnit);
		t_borrow.circulationNumber.setValue(circulationNumber);
		t_borrow.hasCirculationNumber.setValue(hasCirculationNumber);
		t_borrow.nid_log.setValue(nid_log);//标题种类 历史记录
		t_borrow.borrowTitle.setValue(title);
		t_borrow.imgPath.setValue(imgPath);
		t_borrow.borrowWay.setValue(borrowWay);
		t_borrow.purpose.setValue(purpose);
		t_borrow.deadline.setValue(deadLine);
		t_borrow.paymentMode.setValue(paymentMode);
		t_borrow.minTenderedSum.setValue(minTenderedSum);
		if(maxTenderedSum != -1){
			t_borrow.maxTenderedSum.setValue(maxTenderedSum);			
		}
		t_borrow.raiseTerm.setValue(raiseTerm);
		t_borrow.excitationType.setValue(excitationType);
		t_borrow.detail.setValue(detail);
		t_borrow.excitationMode.setValue(excitationMode);
		t_borrow.publisher.setValue(publisher);
		t_borrow.tradeType.setValue(1);
		if (StringUtils.isNotBlank(investPWD)) {
			t_borrow.investPWD.setValue(Encrypt.MD5(investPWD));
		}
		t_borrow.hasPWD.setValue(hasPWD);
		t_borrow.publishIP.setValue(remoteIP);
		t_borrow.publishTime.setValue(new Date());
		if (amount != -1) {
			t_borrow.borrowAmount.setValue(amount);
		}
		if (annualRate != -1) {
			t_borrow.annualRate.setValue(annualRate);
		}
		if (sum != -1) {
			t_borrow.excitationSum.setValue(sum);
		}
		t_borrow.isDayThe.setValue(daythe);
		t_borrow.frozen_margin.setValue(frozen_margin);
		t_borrow.feelog.setValue(json);
		t_borrow.feestate.setValue(jsonState);
		t_borrow.sort.setValue(sorts);
		t_borrow.isExclus.setValue(isExclus);
		return t_borrow.insert(conn);
	}
	/**
	 * @MethodName: addCrediting
	 * @Param: BorrowDao
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午04:27:59
	 * @Return:
	 * @Descb: 添加信用申请
	 * @Throws:
	 */
	public Long addCrediting(Connection conn, double applyAmount,
			String applyDetail, long userId) throws SQLException {
		Dao.Tables.t_crediting t_crediting = new Dao().new Tables().new t_crediting();
		t_crediting.applyAmount.setValue(applyAmount);
		t_crediting.applyDetail.setValue(applyDetail);
		t_crediting.applyer.setValue(userId);
		t_crediting.applyTime.setValue(new Date());
		return t_crediting.insert(conn);
	}

	/**
	 * @MethodName: queryCreditingApply
	 * @Param: BorrowDao
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午07:51:25
	 * @Return:
	 * @Descb: 查询能够再次申请信用额度的记录
	 * @Throws:
	 */
	public Map<String, String> queryCreditingApply(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
//		condition.append("select id,applyTime,DATE_ADD(applyTime,INTERVAL 30 DAY) AS targetTime from (select a.id,a.applyer,a.applyTime,a.reviewTime from t_crediting a");
//		condition.append("  left join (select max(reviewTime) reviewTime from t_crediting group by applyer) b on a.reviewTime= b.reviewTime ");
//		condition.append(" where b.reviewTime is not null and a.status=3) t where");
//		condition.append(" DATEDIFF('" + sf.format(new Date())+ "',applyTime)<30 and applyer=" + userId);
		condition
		.append("select applyer,max(applyTime) applyTime,DATE_ADD(applyTime,INTERVAL 30 DAY) AS targetTime from t_crediting where  DATEDIFF('"
				+ sf.format(new Date())
				+ "',applyTime)<30 and applyer="
				+ userId
				+ " GROUP BY applyer ");
		DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
		condition=null;
		sf=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrowTypeSECONDSCondition
	 * @Param: BorrowDao
	 * @Author: gang.lv
	 * @Date: 2013-3-9 上午11:50:08
	 * @Return:
	 * @Descb: 查询用户的秒还借款条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeSecondsCondition(Connection conn,
			double borrowAmount, double borrowAnnualRate, long userId,Map<String,Object> platformCostMap,double frozenMargin)
			throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#0.00");
		double costFee = Convert.strToDouble(platformCostMap
				.get("locan_fee")+"", 0);
		// 借款利息+借款手续费，其中借款利息=借款本金*借款年利率/12 + 冻结保证金
		double fee =(borrowAmount * (Double.valueOf(borrowAnnualRate * 0.01) / Double.valueOf(12)))
				+ (borrowAmount * (costFee/100)) + frozenMargin;
		condition.append(" usableSum >=" + df.format(fee) + " AND id =" + userId);
		DataSet dataSet = t_user.open(conn, " id ", condition.toString(), "",
				0, 1);
		condition=null;
		df=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询秒还标平台收费记录日志
	 * queryplatformCostLog
	 * @param conn
	 * @param identifier
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-13 上午11:25:15
	 */
	public Map<String, String> queryplatformCostLog(Connection conn,
			String  identifier) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append("SELECT locan_fee,locan_month,locan_fee_month,day_fee  FROM t_borrow_type_log ");
		condition.append("  WHERE identifier =  '"+identifier+"'");
		DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
		condition=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 *  查询用户可以资金是否小于保障金额
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String>  queryBorrowFinMoney(Connection  conn,double frozen,long userId) throws DataException, SQLException{
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		condition.append(" usableSum >=" + frozen+ " AND id =" + userId);
		DataSet dataSet = t_user.open(conn, " id ", condition.toString(), "",0, 1);
		condition=null;
		
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
//	/**   
//	 * @MethodName: refreshBorrowTime  
//	 * @Param: BorrowDao  
//	 * @Author: gang.lv
//	 * @Date: 2013-3-17 上午10:57:59
//	 * @Return:    
//	 * @Descb: 刷新借款时间
//	 * @Throws:
//	*/
//	public Long refreshBorrowTime(Connection conn) throws SQLException,
//			DataException {
//		DataSet ds = new DataSet();
//		List<Object> outParameterValues = new ArrayList<Object>();
//		long returnId = -1L;
//		returnId = Dao.Procedures.pr_refreshBorrowTime(conn, ds,
//				outParameterValues, new Date());
//		ds=null;
//		outParameterValues=null;
//		return returnId;
//	}

	
	/**   
	 * @MethodName: delBorrowConcern  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:24:24
	 * @Return:    
	 * @Descb: 删除关注的借款
	 * @Throws:
	*/
	public Long delBorrowConcern(Connection conn, long idLong, Long userId) throws SQLException {
		Dao.Tables.t_concern t_concern = new Dao().new Tables().new t_concern();
		return t_concern.delete(conn, " id="+idLong+" and userId="+userId);
	}

	
	/**   
	 * @MethodName: queryCreditLimit  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-25 下午10:09:16
	 * @Return:    
	 * @Descb: 查询可用信用额度
	 * @Throws:
	*/
	public Map<String, String> queryCreditLimit(Connection conn,
			double amountDouble, Long id) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		condition.append(" usableCreditLimit >=" + amountDouble + " AND id =" + id);
		DataSet dataSet = t_user.open(conn, " id ", condition.toString(), "",
				0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据编号查询借款信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String,String> queryBorroeById(Connection conn,long id) throws DataException, SQLException{
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow.open(conn, "", " id= "+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @MethodName: queryUserAmountRecord  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-13 上午11:12:28
	 * @Return:    
	 * @Descb: 查询用户最新资金记录
	 * @Throws:
	*/
	public Map<String, String> queryUserAmountRecord(Connection conn,
			long publisher) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append("SELECT  a . id  AS  id ,(a . usableSum  +  a . freezeSum )AS  totalSum , a.usableSum   AS  usableSum ,a . freezeSum  AS  freezeSum ,ifnull(sum(((( b . recivedPrincipal  +  b . recievedInterest )-  b . hasPrincipal )-  b . hasInterest)),0)AS  forPI ");
		condition.append("  FROM  t_user   a  LEFT JOIN  t_invest   b  ON a . id  =  b . investor  where a.id =  "+publisher);
		DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
		condition=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * houli 查询是否有未满标审核的借款标的
	 * @param conn
	 * @param userId
	 * @return 
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryBorrowStatus(Connection conn,Long userId) throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow.open(conn, " borrowStatus ", " publisher="+userId , "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	
	/**   
	 * @MethodName: updateUserCreditLimit  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午08:34:19
	 * @Return:    
	 * @Descb: 更新信用额度
	 * @Throws:
	*/
	public Long updateUserCreditLimit(Connection conn, long publisher,double amount) throws SQLException {
        StringBuffer command = new StringBuffer();
        command.append(" update t_user set usableCreditLimit = usableCreditLimit-"+amount+" where id = "+publisher);
        long result= MySQL.executeNonQuery(conn, command.toString());
        command=null;
        return result;
	}

	
	/**   
	 * @throws SQLException 
	 * @MethodName: updateUserAmount  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午08:38:44
	 * @Return:    
	 * @Descb: 更新用户金额
	 * @Throws:
	*/
	public Long updateUserAmount(Connection conn, long publisher, double fee) throws SQLException {
		 StringBuffer command = new StringBuffer();
	        command.append(" update t_user set usableSum = usableSum-"+fee+",freezeSum = freezeSum + "+fee+" where id = "+publisher);
	        Long result=MySQL.executeNonQuery(conn, command.toString());
	     return result;
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryForpayBorrow  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-29 下午05:54:27
	 * @Return:    
	 * @Descb: 查询用户待收金额
	 * @Throws:
	*/
	public Map<String, String> queryForpayBorrow(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select sum(recivedPrincipal+recievedInterest-hasPrincipal-hasInterest) forpaySum from t_invest");
		command.append("  where repayStatus = 1 and investor ="+userId+" group by investor");
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryForRepayBorrow  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-29 下午05:54:46
	 * @Return:    
	 * @Descb: 查询用户待还金额
	 * @Throws:
	*/
	public Map<String, String> queryForRepayBorrow(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select sum(a.stillPrincipal+a.stillInterest-a.hasPI) forRepaySum from t_repayment a left join t_borrow b");
		command.append("  on a.borrowId=b.id where a.repayStatus = 1 and b.publisher ="+userId+" and b.id is not null group by b.publisher");
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryUserAmount  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-29 下午05:54:57
	 * @Return:    
	 * @Descb: 查询用户金额
	 * @Throws:
	*/
	public Map<String, String> queryUserAmount(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT usableSum FROM t_user WHERE id ="+userId);
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryUserAmountByName(Connection conn, String username) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id, usableSum FROM t_user WHERE username ='"+username+"'");
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 更新用户可用
	 * @param conn
	 * @param userId
	 * @param fee
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserMoney(Connection conn, long userId, double fee) throws SQLException {
		 StringBuffer command = new StringBuffer();
	        command.append(" update t_user set usableSum ="+fee+" where id = "+userId);
	        Long result=MySQL.executeNonQuery(conn, command.toString());
	     return result;
	}

	/**   
	 * @MethodName: queryBorrow  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 下午09:58:36
	 * @Return:    
	 * @Descb: 查询借款信息
	 * @Throws:
	*/
	public List<Map<String, Object>> queryBorrow(Connection conn) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("select id from t_borrow where remainTimeStart < remainTimeEnd and borrowStatus = 2");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	
	/**   
	 * @MethodName: queryMaxTime  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 下午09:58:43
	 * @Return:    
	 * @Descb: 查询最大时间
	 * @Throws:
	*/
	public Map<String, String> queryMaxTime(Connection conn, long id,String date) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		date=com.shove.web.Utility.filteSqlInfusion(date);
		command.append("select id from t_borrow where borrowStatus = 2 and remainTimeEnd > '"+StringEscapeUtils.escapeSql(date)+"' and id = "+id);
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: queryMinTime  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 下午09:58:50
	 * @Return:    
	 * @Descb: 查询最小时间
	 * @Throws:
	*/
	public Map<String, String> queryMinTime(Connection conn, long id,String date) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		date=com.shove.web.Utility.filteSqlInfusion(date);
		command.append("select publisher,borrowTitle from t_borrow where borrowStatus = 2 and remainTimeEnd < '"+ StringEscapeUtils.escapeSql(date)+"'  and id ="+id);
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: updateTime  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 下午10:01:59
	 * @Return:    
	 * @Descb: 更新时间
	 * @Throws:
	*/
	public long updateTime(Connection conn, long id, String date) throws SQLException {
		 date=com.shove.web.Utility.filteSqlInfusion(date);
		String command = "update t_borrow set remainTimeStart = '"+StringEscapeUtils.escapeSql(date)+"' where id ="+id;
		long result=MySQL.executeNonQuery(conn, command);
		command=null;
		return result;
	}

	
	/**   
	 * @MethodName: queryBorrowState  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 下午10:32:59
	 * @Return:    
	 * @Descb: 
	 * @Throws:
	*/
	public Map<String, String> queryBorrowState(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE borrowAmount = hasInvestAmount  and borrowStatus = 2 and id ="+id);
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 认购模式时  修改已认购份数
	 * @param conn
	 * @param num
	 * @return
	 * @throws SQLException 
	 */
	public long updateBorrowRenGou(Connection  conn,int num,long id) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("   update   t_borrow  set hasCirculationNumber = hasCirculationNumber + "+num+" where id = "+id);
		long result =  MySQL.executeNonQuery(conn, command.toString());
		command=null;
		return result;
	}
	/**   
	 * @MethodName: updateBorrowState  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 下午10:58:53
	 * @Return:    
	 * @Descb: 更新借款状态
	 * @Throws:
	*/
	public long updateBorrowState(Connection conn, long id,int borrowState,int sorts) throws SQLException {
		String command = "update t_borrow set remainTimeStart = remainTimeEnd,borrowStatus = "+borrowState+" , sort = "+sorts+"  where id ="+id;
		long result =  MySQL.executeNonQuery(conn, command);
		command=null;
		return result;
	}
	
	/**
	 * add by houli 查看某用户基本资料信息审核是否通过
	 * @param conn
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBaseApprove(Connection conn, long id,int status) throws SQLException, DataException{
		Dao.Views.v_t_user_base_approve_lists v_t_user_base_approve_lists = new Dao().new Views().new v_t_user_base_approve_lists();
		DataSet dataSet = v_t_user_base_approve_lists.open(conn, "", " uuid= "+id+" and auditStatus ="+status, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * add by houli 查看某用户五项基本资料认证是否通过
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBaseFiveApprove(Connection conn, long id) throws SQLException, DataException{
		String command = "SELECT userId,SUM(auditStatus) as auditStatus from t_materialsauth  where materAuthTypeId<6 and userId = "+id;
		DataSet dataSet =MySQL.executeQuery(conn, command);
		command = null;
		
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**   
	 * @MethodName: queryCreditLimit  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-11 下午04:46:27
	 * @Return:    
	 * @Descb: 查询可用信用额度
	 * @Throws:
	*/
	public Map<String, String> queryCreditLimit(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet dataSet = t_user.open(conn, " creditLimit,usableCreditLimit", " id="+id, "",
				0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/***
	 * 查询小贷公司和担保公司的合作模式 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>>  queryModelType(Connection conn, Long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.loan_comp_id,b.bonding_comp_id,c.name from t_company_loan a, t_company_loan_bonding b ,t_company_bonding c "
				 + " where a.id = b.loan_comp_id and b.bonding_comp_id = c.id and  "
				 + " a.user_id = (SELECT m.adminId from t_admin_user m where m.userId = "+ id +" ) ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/***
	 * 查询用户是否为合作机构旗下用户
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUser(Connection conn, Long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_admin_user  a where a.userId = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 查询合作机构
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryLoan(Connection conn, Long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_company_loan a where a.user_id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/***
	 * 查询担保信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBonding(Connection conn, Long id,long bondingId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.credit_limit,a.start_time,a.end_time from t_company_loan_bonding a,t_company_bonding b where a.bonding_comp_id = b.id and a.loan_comp_id  = "+id+" and a.bonding_comp_id="+bondingId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询选择该担保公司用户的借款总额
	 * @param bondingId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryBorrowMoneySum(Connection conn,long bondingId,long loanId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("   select sum(z.borrowAmount) as borrowAmount from t_borrow z where z.borrowStatus in (1,2,3,4) and z.bonding_id = "+bondingId
				 + " and z.publisher IN (select m.userId from t_admin_user m where m.adminId =  (select a.user_id from t_company_loan a ,t_company_loan_bonding b, t_company_bonding c   "
				 + " where a.id = b.loan_comp_id and b.bonding_comp_id = c.id and a.id="+loanId
				 + " and c.id ="+bondingId
				 + "))");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 更合作机构户的授信可用金额和授信已用金额
	 * @param conn
	 * @param id
	 * @param availableTotalAmountSum
	 * @param hasTotalAmountSum
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long  updateLoanCompanyMoney(Connection conn,long id,double availableTotalAmountSum,double hasTotalAmountSum)
			throws SQLException, DataException {
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		t_company_loan.available_total_amount.setValue(availableTotalAmountSum);
		t_company_loan.has_total_amount.setValue(hasTotalAmountSum);
		return t_company_loan.update(conn, "id="+id);
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
	
	public Map<String, String> queryBorrowCost(Connection conn, long idLong) throws SQLException, DataException {
		String command = "select a.feestate,a.feelog  from t_borrow a  where a.id="+idLong;
		DataSet dataSet = Database.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryBorrowCostByPayId(Connection conn, long idLong) throws SQLException, DataException {
		String command = "select a.feestate,a.feelog from t_borrow a left join t_repayment b on b.borrowId = a.id  where b.id="+idLong;
		DataSet dataSet = Database.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @throws SQLException 
	 * @MethodName: addCirculationBorrow  
	 * @Param: BorrowDao  
	 * @param borrowTitle        借款
	 * @param imgPath            借款图片
	 * @param borrowWay          借款方式
	 * @param purpose            借款目的
	 * @param deadline           回购期限
	 * @param paymentMode        还款方式
	 * @param borrowAmount       借款金额
	 * @param annualRate         年利率
	 * @param publishIP          发布IP
	 * @param circulationNumber  流转总份数
	 * @param smallestFlowUnit   最小流转单位 
	 * @param publisher          发布者
	 * @param businessDetail     商业详情
	 * @param assets             资产情况
	 * @param moneyPurposes      资金用途
	 * @param isDayThe           是否为天标( 默认 1 否，2 是)
	 * @param circulationMode    流转标发布模式(1 前台用户 2 后台特权用户)
	 * @param undertaker         代发人
	 * @param borrowShow         借款显示类型(1 一般借款 2 流转标借款)
	 * @Author: gang.lv
	 * @Date: 2013-5-18 上午09:07:42
	 * @Return:    
	 * @Descb: 添加流转标
	 * @Throws:
	*/
	public Long addCirculationBorrow(Connection conn, String title,
			String imgPath, int borrowWay, int purposeInt, int deadLineInt,
			int paymentMode, double amountDouble, double annualRateDouble,
			String remoteIP, int circulationNumber,
			double smallestFlowUnitDouble, Long id, String businessDetail,
			String assets, String moneyPurposes, int dayThe,int circulationMode
			,long undertaker,int  excitationTypeInt,double sumInt,String json,
			String jsonState,String nid,String agent, String counterAgent,double fee) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowTitle.setValue(title);
		t_borrow.imgPath.setValue(imgPath);
		t_borrow.borrowWay.setValue(borrowWay);
		t_borrow.purpose.setValue(purposeInt);
		t_borrow.deadline.setValue(deadLineInt);
		t_borrow.paymentMode.setValue(paymentMode);
		t_borrow.borrowAmount.setValue(amountDouble);
		t_borrow.annualRate.setValue(annualRateDouble);
		t_borrow.publishIP.setValue(remoteIP);
		t_borrow.circulationNumber.setValue(circulationNumber);
		t_borrow.smallestFlowUnit.setValue(smallestFlowUnitDouble);
		t_borrow.publisher.setValue(id);
		t_borrow.businessDetail.setValue(businessDetail);
		t_borrow.assets.setValue(assets);
		t_borrow.moneyPurposes.setValue(moneyPurposes);
		t_borrow.isDayThe.setValue(dayThe);
		t_borrow.circulationMode.setValue(circulationMode);
		t_borrow.publishTime.setValue(new Date());
		t_borrow.circulationStatus.setValue(1);
		t_borrow.agent.setValue(1);
		t_borrow.counterAgent.setValue(1);
		if(undertaker != -2){
			t_borrow.undertaker.setValue(undertaker);			
		}
		//显示流转标借款
		t_borrow.borrowShow.setValue(2);
		t_borrow.frozen_margin.setValue(fee);
		//add by lw
		if (sumInt != -1) {
			t_borrow.excitationSum.setValue(sumInt);
		}
		t_borrow.feelog.setValue(json);
		t_borrow.feestate.setValue(jsonState);
		t_borrow.excitationType.setValue(excitationTypeInt==-1?1:excitationTypeInt);
		t_borrow.nid_log.setValue(nid);
		t_borrow.isExclus.setValue(0);
		//------
		return t_borrow.insert(conn);
	}

	/**   
	 * @throws SQLException 
	 * @MethodName: updateRepoBorrow  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-21 下午08:03:10
	 * @Return:    
	 * @Descb: 修改状态为回购中
	 * @Throws:
	*/
	public long updateRepoBorrow(Connection conn, long id) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_borrow set borrowStatus = 4,sort="+IConstants.SORT_5+" where hasInvestAmount =borrowAmount and borrowStatus=2 and id="+id);
		return MySQL.executeNonQuery(conn, command.toString());
	}
	
	/**   
	 * @MethodName: queryCirculationBorrow  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-22 上午12:00:20
	 * @Return:    
	 * @Descb: 查询认购中或回购中的借款
	 * @Throws:
	*/
	public List<Map<String,Object>> queryCirculationBorrow(Connection conn) throws SQLException, DataException{
		String command =" select b.id,b.borrowTitle,b.publisher,b.smallestFlowUnit,b.feestate,b.feelog,a.id repayId from t_repayment a" +
				" left join t_borrow b on a.borrowId=b.id where b.borrowShow = 2 and b.borrowStatus in(2,4,5)";
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**   
	 * @MethodName: updateHasRepoNumber  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-22 上午11:20:17
	 * @Return:    
	 * @Descb: 更新已回购份数
	 * @Throws:
	*/
	public long updateHasRepoNumber(Connection conn, long borrowId,int hasRepo) throws SQLException {
		long returnId = -1;
		String command = "UPDATE t_borrow SET hasRepoNumber = hasRepoNumber+"+hasRepo
		+" where id="+borrowId;
		returnId = MySQL.executeNonQuery(conn, command.toString());
		return returnId;
	}
	
	/**
	 * 通过Id查询借款发布人
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBorrowerById(Connection conn, long id) throws DataException, SQLException {
		String command = "select publisher from t_borrow where id = "+ id;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 通过借款Id查询投资人
	 * @param conn
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryInvesterById(Connection conn,
			long id) throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select investor from t_invest t where t.borrowId=  (SELECT r.borrowId from  t_repayment r where r.id=" + id+")");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据还款ID查询还款时将资金还给哪个投资人
	 * @author 郭井超
	 * @date 2015-06-03
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryInvestRepmentByRepId(Connection conn,
			long id) throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT  ROUND(a.hasInterest+a.hasPrincipal,2) as sum_money , ROUND(a.hasInterest,2) as hasInterest_f ,ROUND(a.hasPrincipal,2) as hasPrincipal_f, a.repayPeriod, c.username,c.mobilePhone,d.borrowTitle from t_invest_repayment a ,t_invest b ,t_user c, t_borrow d where a.invest_id = b.id and b.investor = c.id and b.borrowId = d.id and a.repayId = "+id );
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> getSMSById(Connection conn,Integer id)throws SQLException,DataException{
		Dao.Tables.t_sms sms=new Dao().new Tables().new t_sms();
		DataSet dataSet=sms.open(conn, "*", " id="+id, "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	
	public Map<String, String> queryBorrow(Connection conn,long borrowId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from t_borrow t where t.id = "+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String, Object>>  queryAppointInvest(Connection conn) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from t_appointment_invest t where t.state = 0 ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> queryMaxHLId(Connection conn)throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" select * from  t_borrow where id =(select max(id) from t_borrow where borrowWay =1 and deadline = 1 )");
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	public long  updateAppointInvest(Connection conn,long id,long borrowId)
			throws SQLException, DataException {
		Dao.Tables.t_appointment_invest t_appointment_invest = new Dao().new Tables().new t_appointment_invest();
		t_appointment_invest.state.setValue(1);
		t_appointment_invest.borrowId.setValue(borrowId);
		return t_appointment_invest.update(conn, "id="+id);
	}
	
	public Map<String, String> queryBorrowNew(Connection conn) throws SQLException, DataException {
	    StringBuffer command = new StringBuffer();
		command.append("SELECT t.*,ROUND((t.amount_able/t.amount_sum)*100,2) as amount,ROUND(t.amount_sum-t.amount_able,2) as amount_arday  from t_borrow_new t ORDER BY t.id DESC LIMIT 0,1 ");
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long  updateBorrowNewVersion(Connection conn,long id,int parm)
			throws SQLException, DataException {
		Dao.Tables.t_borrow_new t_borrow_new = new Dao().new Tables().new t_borrow_new();
		t_borrow_new.version.setValue(parm+1);
		return t_borrow_new.update(conn, "id="+id+" and version="+parm);
	}
	
	public Map<String, String> queryEmployeeBorrow(Connection conn) throws SQLException, DataException {
	    StringBuffer command = new StringBuffer();
		command.append("SELECT t.*,ROUND((t.amount_able/t.amount_sum)*100,2) as amount,ROUND(t.amount_sum-t.amount_able,2) as amount_arday  from t_employee_borrow t ORDER BY t.id DESC LIMIT 0,1 ");
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long  updateEmployeeBorrowVersion(Connection conn,long id,int parm)
			throws SQLException, DataException {
		Dao.Tables.t_employee_borrow t_employee_borrow = new Dao().new Tables().new t_employee_borrow();
		t_employee_borrow.version.setValue(parm+1);
		return t_employee_borrow.update(conn, "id="+id+" and version="+parm);
	}
	
	/***
	 * 查询用户每月投了几个满标
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>>  queryEmployeeInvestCounts(Connection conn,long userId,String currDate) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT   DISTINCT(a.id) as borrow_id  from  t_employee_borrow a,t_employee_invest b where  a.id=b.borrow_id and DATE_FORMAT(b.create_time,'%Y-%m-%d')='"+currDate+"' and a.amount_able = a.amount_sum and b.user_id ="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 用户每月可投标的已经使用了多少份体验金
	 * @param conn
	 * @param userId
	 * @param currDate
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryEmployeeInvestSums(Connection conn,long userId,String currDate) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  DISTINCT(a.id) as borrow_id ,SUM(b.amount) as amount  from   t_employee_borrow a, t_employee_invest b where  a.id = b.borrow_id and a.amount_able<a.amount_sum and DATE_FORMAT(b.create_time,'%Y-%m-%d') = '"+currDate+"' and b.user_id = "+userId);
		DataSet dataSet =MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryEmployeeConfig(Connection conn,long userId) throws SQLException, DataException {
	    StringBuffer command = new StringBuffer();
		String time = DateUtil.dateToStringYYMM(new Date());
	    command.append(" SELECT  a.amount-SUM(b.amount) as ableAmount from  t_employee_config a, t_employee_invest b  where  a.userId = b.user_id and DATE_FORMAT(b.create_time,'%Y-%m')='"+time+"' and  a.userId = "+userId);
		DataSet dataSet =MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String, Object>> queryRepayBorrow(Connection conn,long repayId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  c.*,b.borrowStatus  from  t_repayment a ,t_borrow b ,  t_activity_ci c where  a.borrowId = b.id and  b.id = c.borrow_id and b.borrowStatus = 5 and a.id = "+repayId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long addRecode(Connection conn,long userId,long trader,String fundMode, double handleSum,double usableSum,
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
	
	public Long updateActivityCi(Connection conn, long id) throws SQLException {
		Dao.Tables.t_activity_ci t_activity_ci = new Dao().new Tables().new t_activity_ci();
		t_activity_ci.status.setValue(1);
		return t_activity_ci.update(conn, " id= "+id);
	}
	
	public List<Map<String, Object>>  queryInvest(Connection conn,long repayId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  a.invest_id from  t_invest_repayment a, t_invest b where a.invest_id = b.id  and b.debt_type = 2 and a.repayId = "+repayId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Long updateInvestDebtType(Connection conn, long id) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.debt_type.setValue(1);
		return t_invest.update(conn, " id= "+id);
	}
	
}
