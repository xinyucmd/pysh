package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_company_loan_bonding;

/**
 * 小贷公司超级管理员及其下的贷款账户关系表
 * @author Administrator
 *
 */
public class AdminUserDao {

	/**
	 * 小贷公司超级管理员及其下的贷款账户
	 * 
	 * @author guojingchao
	 * @date 2014-12-12
	 * @param conn
	 * @param adminId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long addAdminUser(Connection conn,long adminId,long userId,int isFlag) throws SQLException{
		Dao.Tables.t_admin_user t_admin_user = new Dao().new Tables().new t_admin_user();
		t_admin_user.adminId.setValue(adminId);
		t_admin_user.userId.setValue(userId);
		t_admin_user.isFlag.setValue(isFlag);
		return t_admin_user.insert(conn);
	}
	
	/***
	 * 更改小贷公司前台超级用户状态
	 * 
	 * @param conn
	 * @param admin_user_id
	 * @param isFlag
	 * @return
	 * @throws SQLException
	 */
	public long updateAdminUser(Connection conn,long admin_user_id,int isFlag) throws SQLException{
		Dao.Tables.t_admin_user t_admin_user = new Dao().new Tables().new t_admin_user();
		int temp = -1; 
		if(0==isFlag){
			temp = 1;
		}
		if(1==isFlag){
			temp = 0;
		}
		t_admin_user.isFlag.setValue(temp);
		return t_admin_user.update(conn, "id="+admin_user_id);
	}
	
	/***
	 * 更改营销账户手机号
	 * @param conn
	 * @param id
	 * @param tell
	 * @return
	 * @throws SQLException
	 */
	public long updateUserYxTell(Connection conn,long id,String tell) throws SQLException{
		Dao.Tables.t_user_yx t_user_yx = new Dao().new Tables().new t_user_yx();
		t_user_yx.tell.setValue(tell);
		return t_user_yx.update(conn, "id="+id);
	}
	
	public long updateT_User(Connection conn,long id,String tell) throws SQLException{
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		t_user.mobilePhone.setValue(tell);
		return t_user.update(conn, "id="+id);
	}
	
	public long updateT_Person(Connection conn,long id,String tell) throws SQLException{
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		t_person.cellPhone.setValue(tell);
		return t_person.update(conn, "userId="+id);
	}
	
	public long updateT_Phone_Binding_Info(Connection conn,long id,String tell) throws SQLException{
		Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();
		t_phone_binding_info.mobilePhone.setValue(tell);
		return t_phone_binding_info.update(conn, "userId="+id);
	}
	
	/**
	 * 判断小贷公司是否存在前台超级用户
	 * @param conn
	 * @param admin_user_id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>>  queryAdminUserByConnections(Connection conn,long adminId)throws SQLException,DataException{
		Dao.Tables.t_admin_user t_admin_user = new Dao().new Tables().new t_admin_user();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		condition.append(" and adminId='"+adminId+"'");
		condition.append(" and isFlag= 1 ");
		DataSet dataSet = t_admin_user.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	 
	public long updateAdminUserBatch(Connection conn,long id) throws SQLException{
		Dao.Tables.t_admin_user t_admin_user = new Dao().new Tables().new t_admin_user();
		t_admin_user.isFlag.setValue(0);
		return t_admin_user.update(conn, "id="+id);
	}
	
	
	public Map<String,String> findLoanCompanyAdminUserById(Connection conn,long id) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_admin where id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> findLoanCompanyAdmnUser(Connection conn,long uerId) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_admin_user where userId = "+uerId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> findUser(Connection conn,String userName,String password) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_user where username = "+"'"+userName+"'"+" and password = "+"'"+password+"'");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long addUserGroup(Connection conn,long groupId,long userId) throws SQLException{
		Dao.Tables.t_group_user t_group_user = new Dao().new Tables().new t_group_user();
		t_group_user.groupId.setValue(groupId);
		t_group_user.userId.setValue(userId);
		return t_group_user.insert(conn);
	}
}
