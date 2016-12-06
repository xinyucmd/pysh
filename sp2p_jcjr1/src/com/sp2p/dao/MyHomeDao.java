package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.util.DateUtil;

public class MyHomeDao {


	/**
	 * @MethodName: queryBorrowRepaymentById
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:51:39
	 * @Return:
	 * @Descb: 查询投资人回收中借款的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowForpayById(Connection conn,
			long minId, long userId,long investId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.repayPeriod as  repayPeriod ,a.repayStatus as  repayStatus ,DATE_FORMAT(a. repayDate,'%Y-%m-%d')  as  repayDate,round(a.recivedPrincipal,2) AS  forpayPrincipal , " );
		command.append("round(a.recivedInterest,2) AS  forpayInterest , round(a.principalBalance,2) AS  principalBalance , round(a.iManageFee,2)   AS  manage , " );
		command.append("a.isLate as  isLate ,a.lateDay as  lateDay ,round(a.recivedFI,2) AS  forFI , c.borrowTitle as borrowTitle ," );
		
		command.append("round((a.recivedInterest/c.annualRate)*c.add_interest,2) as add_interest_value,");
		command.append("round((a.recivedInterest -a.iManageFee+a.recivedFI +  round((a.recivedInterest/c.annualRate)*c.add_interest,2)  ),2) AS earn ,");
		command.append("d.username as  username ,a.isWebRepay as  isWebRepay  from t_invest_repayment a LEFT JOIN " );
		command.append("t_repayment b on a.repayId=b.id LEFT JOIN t_borrow c on b.borrowId=c.id LEFT JOIN t_user d on c.publisher=d.id where 1=1");
		if(minId > 0){
			command.append(" and a.id >= "+minId);
		}
		command.append(" and a.invest_id="+investId+" and a.owner ="+userId+" AND a.isWebRepay=1 ORDER BY a.repayDate ");
        
		
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}


	
	/**   
	 * @MethodName: queryBorrowHaspayById  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:56:47
	 * @Return:    
	 * @Descb: 查询已回收的借款还款详情
	 * @Throws:
	*/
	public List<Map<String, Object>> queryBorrowHaspayById(Connection conn,
			long borrowId, long userId,long investId) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append("SELECT a.repayPeriod as  repayPeriod ,a.repayDate as  repayDate  ,");
			command.append("round(a.hasPrincipal,2) AS  forpayPrincipal , round(a.hasInterest,2) AS  forpayInterest , round(a.principalBalance,2) AS  principalBalance , " );
			command.append(" round(a.iManageFee,2) AS  manage , a.isLate as  isLate ,a.lateDay as  lateDay ,round(a.recivedFI,2) AS  forFI , " );
			
			command.append("round((a.recivedInterest/c.annualRate)*c.add_interest,2) as add_interest_value,");
			command.append("round((a.recivedInterest -a.iManageFee+a.recivedFI +  round((a.recivedInterest/c.annualRate)*c.add_interest,2)  ),2) AS earn ,");
			command.append("d.username as  username ,a.isWebRepay as  isWebRepay  " );
			command.append("from t_invest_repayment a LEFT JOIN t_borrow c on a.borrow_id=c.id LEFT JOIN t_user d " );
			command.append("on c.publisher=d.id  " );
			command.append("where a.isDebt = 1 and a.invest_id="+investId+" and a.interestOwner ="+userId+" and (a.repayStatus=2 OR a.isWebRepay=2) " );
			command.append("ORDER BY a.repayDate ");
        DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}


	
	/**   
	 * @MethodName: queryAutomaticBid  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:20
	 * @Return:    
	 * @Descb: 查询用户的自动投标设置
	 * @Throws:
	*/
	public Map<String, String> queryAutomaticBid(Connection conn, Long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.usableSum,b.* FROM t_user a LEFT JOIN t_automaticbid b ON b.userId = a.id");
		command.append(" where a.id = "+id + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}


	
	/**   
	 * @MethodName: automaticBidSet  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:29:20
	 * @Return:    
	 * @Descb: 修改用户自动投标状态
	 * @Throws:
	*/
	public Long automaticBidSet(Connection conn, long status,long userId) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		if(status == 1){
			t_automaticbid.bidStatus.setValue(2);
			t_automaticbid.bidSetTime.setValue(new Date());
		}else{
			t_automaticbid.bidStatus.setValue(1);						
		}
		return t_automaticbid.update(conn, " userId = "+userId);
	}
	
	
	/**   
	 * @MethodName: queryAutomaticBid  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:06:47
	 * @Return:    
	 * @Descb: 查询用户是否已经创建自动投标内容
	 * @Throws:
	*/
	public Map<String,String> hasAutomaticBid(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" select id from t_automaticbid");
		command.append(" where userId = "+userId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @throws SQLException 
	 * @MethodName: automaticBidAdd  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:57:42
	 * @Return:    
	 * @Descb: 添加自动投标内容
	 * @Throws:
	*/
	public Long automaticBidAdd(Connection conn, Double bidAmount,
			Double rateStart, Double rateEnd, Double deadlineStart,
			Double deadlineEnd, Double creditStart, Double creditEnd,
			Double remandAmount, Long id,String borrowWay) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidAmount.setValue(bidAmount);
		t_automaticbid.rateStart.setValue(rateStart);
		t_automaticbid.rateEnd.setValue(rateEnd);
		t_automaticbid.deadlineStart.setValue(deadlineStart);
		t_automaticbid.deadlineEnd.setValue(deadlineEnd);
		t_automaticbid.creditStart.setValue(creditStart);
		t_automaticbid.creditEnd.setValue(creditEnd);
		t_automaticbid.remandAmount.setValue(remandAmount);
		t_automaticbid.userId.setValue(id);
		t_automaticbid.borrowWay.setValue(borrowWay);
		return t_automaticbid.insert(conn);
	}
	
	/**   
	 * @throws SQLException 
	 * @MethodName: automaticBidUpdate  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:57:28
	 * @Return:    
	 * @Descb: 更新自动投标内容
	 * @Throws:
	*/
	public Long automaticBidUpdate(Connection conn, Double bidAmount,
			Double rateStart, Double rateEnd, Double deadlineStart,
			Double deadlineEnd, Double creditStart, Double creditEnd,
			Double remandAmount, Long id,String borrowWay) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidAmount.setValue(bidAmount);
		t_automaticbid.rateStart.setValue(rateStart);
		t_automaticbid.rateEnd.setValue(rateEnd);
		t_automaticbid.deadlineStart.setValue(deadlineStart);
		t_automaticbid.deadlineEnd.setValue(deadlineEnd);
		t_automaticbid.creditStart.setValue(creditStart);
		t_automaticbid.creditEnd.setValue(creditEnd);
		t_automaticbid.remandAmount.setValue(remandAmount);
		t_automaticbid.borrowWay.setValue(borrowWay);
		return t_automaticbid.update(conn, " userId = "+id);
	}
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryRepaymentByOwner  
	 * @Param: MyHomeDao  
	 * @Author: 吴海彬 2014-11-14 update
	 * @Date: 
	 * @Return:    
	 * @Descb: 查询最近还款日及金额
	 * @Throws:
	*/
	
	public Map<String, String> queryRepaymentByOwner(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		
		command.append("SELECT SUM(t.recivedPrincipal + t.recivedInterest) AS totalSum FROM t_invest_repayment t ");
		command.append("WHERE repaystatus = '1' AND MONTH(repayDate) = IF (MONTH(NOW()) + 1 = 13,1,MONTH(NOW()) + 1) AND YEAR(repayDate) = IF (MONTH(NOW()) + 1 = 13,YEAR(NOW()) + 1,YEAR(NOW()))");
		command.append("AND t.`owner` = " + userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 添加预约投标
	 * @param conn
	 * @param type
	 * @param limits
	 * @param rate
	 * @param amount
	 * @param state
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long addAppointInvest(Connection conn,int type,int limits,double rate,double amount,int state,long userId) throws SQLException {
		Dao.Tables.t_appointment_invest t_appointment_invest = new Dao().new Tables().new t_appointment_invest();
		t_appointment_invest.type.setValue(type);
		t_appointment_invest.limits.setValue(limits);
		t_appointment_invest.rate.setValue(rate);
		t_appointment_invest.amount.setValue(amount);
		t_appointment_invest.state.setValue(state);
		t_appointment_invest.userId.setValue(userId);
		t_appointment_invest.create_Time.setValue(new Date());
		return t_appointment_invest.insert(conn);
	}
	
	public Map<String, String> queryAppointInvest(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" SELECT * from t_appointment_invest t where t.state = 0 and t.userId = "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String, Object>> queryAppointInvestRecond(Connection conn, long userId) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append(" SELECT DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') as createTime_f,  t.* from t_appointment_invest t where t.userId = "+userId+" ORDER BY t.state asc  ");
        DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Long removeAppointInvest(Connection conn,long id) throws SQLException {
		Dao.Tables.t_appointment_invest t_appointment_invest = new Dao().new Tables().new t_appointment_invest();
		return t_appointment_invest.delete(conn, " id="+id);
	}
	
	public Map<String, String> queryOptions(Connection conn,long id) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append("SELECT * from t_options t where t.id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询用户投资加息标的额外收益
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAddInerestAmount(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append("SELECT round(SUM(b.investAmount*a.add_interest/100*a.deadline/12),2) as add_interest_amount from t_borrow a ,t_invest b where a.id = b.borrowId and b.investor = "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 还给用户的额外收益
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryRepayUserInerestAmount(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" SELECT SUM(t.handleSum) as repayInerestAmount FROM t_fundrecord t where  t.fundMode='7.6加息奖励' and   t.userId="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询用户投资加息标的额外下一个月的收益
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryNextInerestAmount(Connection conn,long userId) throws Exception{
		String nextMonth = DateUtil.getNextMonth(1);
		StringBuffer command = new StringBuffer();
		command.append("SELECT SUM(c.investAmount*a.add_interest/100*a.deadline/12/a.deadline) as next_month_interest_amount from t_borrow a,t_invest_repayment b ,t_invest c WHERE a.id = b.borrow_id and b.invest_id = c.id   and b.realRepayDate is NULL  and DATE_FORMAT(b.repayDate,'%Y-%m') = '"+nextMonth+"'"+" and c.investor="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryUserName(Connection conn, Long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT  username from  t_borrow m,t_user b where m.publisher = b.id and m.id in "
				+ " (SELECT old_borrow_id from  t_borrow a  where  a.id = "+id
				+ " ) ");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
}
