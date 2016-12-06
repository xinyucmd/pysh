package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_bonus_amount;
import com.sp2p.database.Dao.Tables.t_company_loan;
import com.sp2p.database.Dao.Views.v_t_borrow_csai;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class PartenersDao {

	public Long addPareners(Connection conn,String pname,String alias,String url,Date create_time) throws SQLException{
		Dao.Tables.t_parteners parteners = new Dao().new Tables().new t_parteners();
		
		parteners.pname.setValue(pname);
		parteners.alias.setValue(alias);
		parteners.url.setValue(url);
		parteners.status.setValue(1);
		parteners.create_time.setValue(create_time);
		
		return parteners.insert(conn);
	}
	
	public Long updatePareners(Connection conn,long id,String pname,String alias,String url,int status,Date create_time) throws SQLException{
		if(id<=0){
			return -1L;
		}
		
		Dao.Tables.t_parteners parteners = new Dao().new Tables().new t_parteners();
		if(StringUtils.isNotBlank(pname)){
			parteners.pname.setValue(pname);
		}
		if(StringUtils.isNotBlank(alias)){
			parteners.alias.setValue(alias);
		}
		if(StringUtils.isNotBlank(url)){
			parteners.url.setValue(url);
		}
		if(status>=0){
			parteners.status.setValue(status);
		}
		
		return parteners.update(conn, "id = "+id);
	}
	
	public Map<String,String> queryPartenersByConditions(Connection conn,long id)throws SQLException,DataException{
		Dao.Tables.t_parteners parteners = new Dao().new Tables().new t_parteners();
		StringBuffer conditions = new StringBuffer();
		conditions.append("1=1");
		if(id>0){
			conditions.append(" and id = "+id);
		}
		
		conditions.append(" and status = 1");
		DataSet dataSet=parteners.open(conn, " * ", conditions.toString(), "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 判断客户是否是广告商推荐过来的
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryPartenersUser(Connection conn,long userId)throws SQLException,DataException{
		Dao.Tables.t_parteners_user t_parteners_user = new Dao().new Tables().new t_parteners_user();
		DataSet dataSet=t_parteners_user.open(conn, " * ", " user_id ="+userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Long addParenersUser(Connection conn,long pid, String uid, long user_id,Date create_time,Date reg_time) throws SQLException{
		Dao.Tables.t_parteners_user t_parteners_user = new Dao().new Tables().new t_parteners_user();
		
		t_parteners_user.pid.setValue(pid);
		t_parteners_user.uid.setValue(uid);
		t_parteners_user.user_id.setValue(user_id);
		t_parteners_user.create_time.setValue(create_time);
		t_parteners_user.reg_time.setValue(reg_time);
		
		return t_parteners_user.insert(conn);
	}
	
	public Long updateParenersUser(Connection conn,long id,long pid, String uid, long user_id,Date reg_time) throws SQLException{
		if(id<=0){
			return -1L;
		}
		
		Dao.Tables.t_parteners_user t_parteners_user = new Dao().new Tables().new t_parteners_user();
		if(pid>0){
			t_parteners_user.pid.setValue(pid);
		}
		if(StringUtils.isNotBlank(uid)){
			t_parteners_user.uid.setValue(uid);
		}
		if(user_id>0){
			t_parteners_user.user_id.setValue(user_id);
		}
		if(reg_time!=null){
			t_parteners_user.reg_time.setValue(reg_time);
		}
		
		return t_parteners_user.update(conn, "id = "+id);
	}
	
	public List<Map<String, Object>> queryPartenersUserByConditions(Connection conn,long userId)throws SQLException,DataException{
		Dao.Views.v_parteners_user v_parteners_user = new Dao().new Views().new v_parteners_user();
		StringBuffer conditions = new StringBuffer("1=1");
		if(userId>0){
			conditions.append(" and user_id = "+userId);
		}
		conditions.append(" and status = 1");
		DataSet dataSet=v_parteners_user.open(conn, " * ", conditions.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 寻金会查看标的数据
	 * @param flag 1:进行中的标的 2:满标
	 * @param date 满标日期
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getborrowData(Connection conn, int flag, String date)throws SQLException,DataException{
		Dao.Views.v_t_borrow_list_xunjinhui v_t_borrow_list_xunjinhui = new Dao().new Views().new v_t_borrow_list_xunjinhui();
		date=com.shove.web.Utility.filteSqlInfusion(date);
		StringBuffer conditions = new StringBuffer("1=1");
		if(1 == flag){
			conditions.append(" and hasInvestAmount < amount");
		}else if(2 ==flag && StringUtils.isNotBlank(date)){
			conditions.append(" and successTime like '%"+StringEscapeUtils.escapeSql(date)+"%'") ;
		}else {
			conditions.append(" and 1 = 1");
		}
		DataSet dataSet=v_t_borrow_list_xunjinhui.open(conn, " * ", conditions.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 未来网贷查看标的数据
	 * @param flag 1:进行中的标的 2:满标
	 * @param date 满标日期
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getborrowToWeilaiwd(Connection conn, int flag, String date)throws SQLException,DataException{
		Dao.Views.v_t_borrow_list_wlwd v_t_borrow_list_wlwd = new Dao().new Views().new v_t_borrow_list_wlwd();
		date=com.shove.web.Utility.filteSqlInfusion(date);
		StringBuffer conditions = new StringBuffer("1=1");
		if(1 == flag){
			conditions.append(" and hasInvestAmount < amount");
		}else if(2 ==flag && StringUtils.isNotBlank(date)){
			conditions.append(" and successtime like '%"+StringEscapeUtils.escapeSql(date)+"%'") ;
		}else {
			conditions.append(" and 1 = 1");
		}
		DataSet dataSet=v_t_borrow_list_wlwd.open(conn, " * ", conditions.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 融360查看单个项目信息
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getborrowDataTo360(Connection conn, long product_id)throws SQLException,DataException{
		Dao.Views.v_t_borrow_rong360 v_t_borrow_rong360 = new Dao().new Views().new v_t_borrow_rong360();
		StringBuffer conditions = new StringBuffer("1=1");
		if(product_id > 0){
			conditions.append(" and id = " + product_id);
		}
		DataSet dataSet=v_t_borrow_rong360.open(conn, " * ", conditions.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * crLinshi
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> crLinshi(Connection conn)throws SQLException,DataException{
		Dao.Tables.cr_linshi cr_linshi = new Dao().new Tables().new cr_linshi();
		DataSet dataSet=cr_linshi.open(conn, " * ", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * crLinshi更新
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateLinshi(Connection conn, int id, String realName)throws SQLException {
		Dao.Tables.cr_linshi cr_linshi = new Dao().new Tables().new cr_linshi();
		cr_linshi.realName.setValue(realName);
		return cr_linshi.update(conn, " id = " + id);
	}
	
	/**
	 * 寻金会查看单个标的投标列表
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getInvestList(Connection conn, long borrowId)throws SQLException,DataException{
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		StringBuffer conditions = new StringBuffer(" 1=1");
		if(borrowId > 0){
			conditions.append(" and borrowId = " + borrowId);
		}
		DataSet dataSet=t_invest.open(conn, " CAST(MD5(investor) AS CHAR) AS subscribeUserName,investAmount AS amount,investAmount AS validAmount, date_format(investTime,'%Y-%m-%d %H:%i:%s') AS `addDate`, 1 AS`status`, IF(isAutoBid=1,0,1) AS type ", conditions.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryInvestInfoById  
	 * @Return:    
	 * @Descb: 未来网贷根据借款id查询投资信息
	 * @Throws:
	*/
	public List<Map<String, Object>> queryInvestInfoById(Connection conn,
			long borrowId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT b.username as investorid,ROUND(a.investAmount,2) as amount,date_format(a.investTime,'%Y-%m-%d %T') AS addtime");
		command.append(" from t_invest a LEFT JOIN t_user b on a.investor = b.id where a.borrowId="+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 添加广告商记录
	 * 
	 * @author 郭井超
	 * @date 2015-04-14
	 * @param conn
	 * @param type
	 * @param remark
	 * @param url
	 * @param user_id
	 * @param pid
	 * @param amount
	 * @param commission
	 * @param operateTime
	 * @param createTime
	 * @return
	 * @throws SQLException
	 */
	public Long addParenersMessage(Connection conn,int type,String remark,String url,
			                       long user_id,int pid,double amount,double commission,
			                       String operateTime,Date createTime) throws SQLException{
		Dao.Tables.t_parteners_message t_parteners_message = new Dao().new Tables().new t_parteners_message();
		t_parteners_message.type.setValue(type);
		t_parteners_message.remark.setValue(remark);
		t_parteners_message.url.setValue(url);
		t_parteners_message.user_id.setValue(user_id);
		t_parteners_message.pid.setValue(pid);
		t_parteners_message.amount.setValue(amount);
		t_parteners_message.commission.setValue(commission);
		t_parteners_message.operateTime.setValue(new Date());
		t_parteners_message.createTime.setValue(createTime);
		return t_parteners_message.insert(conn);
	}
	
	/**
	 * 添加广告商记录
	 * 
	 * @author 郭井超
	 * @date 2015-04-14
	 * @param conn
	 * @param pageBean
	 * @param pid
	 * @param operateTime
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryParenersMessage(Connection conn,PageBean<Map<String, Object>> pageBean,int pid,String operateTime) throws SQLException, DataException{
		Dao.Tables.t_parteners_message t_parteners_message = new Dao().new Tables().new t_parteners_message();
		StringBuffer condition = new StringBuffer();
		 //拼接查询条件
		  
		long c=t_parteners_message.getCount(conn, condition.toString()); 
		boolean  result=pageBean.setTotalNum(c); 
		if(result)
		{
			DataSet ds= t_parteners_message.open(conn, " DATE_FORMAT(operateTime,'%Y-%m-%d %H:%i:%s') as operateTime_f,DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s')as createTime_f,t_parteners_message.*  ", condition.toString(), " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
		}
	}
	
	
}
