package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_admin;
import com.sp2p.database.Dao.Tables.t_apply;
import com.sp2p.database.Dao.Tables.t_help_type;

/**
 * 企业融资申请表
 * @author lenovo
 *
 */
public class BorrowfaDao {
	
	
	public Long addApply(Connection conn,String companyname,String registnumber,
			String tname,String telphone,String cityaddress,
			String borrowAmount,String deadline,String borrowPurpose
			) throws SQLException {
		
		Dao.Tables.t_apply apply = new Dao().new Tables().new t_apply();
		apply.companyname.setValue(companyname);
		apply.registnumber.setValue(registnumber);
		apply.tname.setValue(tname);
		apply.telephone.setValue(telphone);
		apply.cityaddress.setValue(cityaddress);
		apply.borrowAmount.setValue(borrowAmount);
		apply.deadline.setValue(deadline);
		apply.borrowPurpose.setValue(borrowPurpose);
		return apply.insert(conn);
	}
	
	public Long addApplyAdmin(Connection conn,String companyname,String registnumber,
			String tname,String telphone,String cityaddress,
			String borrowAmount,String deadline,String borrowPurpose
			) throws SQLException {
		
		Dao.Tables.t_apply apply = new Dao().new Tables().new t_apply();
		apply.companyname.setValue(companyname);
		apply.registnumber.setValue(registnumber);
		apply.tname.setValue(tname);
		apply.telephone.setValue(telphone);
		apply.cityaddress.setValue(cityaddress);
		apply.borrowAmount.setValue(borrowAmount);
		apply.deadline.setValue(deadline);
		apply.borrowPurpose.setValue(borrowPurpose);
		apply.state.setValue(1);
		return apply.insert(conn);
	}

	
	public Map<String, String> queryApplyById(Connection conn, long id)
				throws SQLException, DataException {
		
		Dao.Tables.t_apply apply = new Dao().new Tables().new t_apply();
		DataSet dataSet = apply.open(conn, "*", " id=" + id, " id desc ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}


	public Map<String, String> queryfabiaoInfo(Connection conn, long id) throws SQLException, DataException {
		DataSet dataSet = null;
		dataSet = MySQL.executeQuery(conn, "select id,companyname,registnumber,tname,telephone,cityaddress," +
		"borrowPurpose,borrowAmount,deadline,state from t_apply where id="+id );
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	


	public long updateApplystate(Connection conn, long id, String state) throws SQLException {
			Dao.Tables.t_apply apply = new Dao().new Tables().new t_apply();		
			apply.state.setValue(state);
			return apply.update(conn, " id = "+id);
	
	}
	/**
	 * 删除对应id的问题类型信息
	 * @param conn
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public long deleteBorrowfaId(Connection conn,long id) throws SQLException{
		Dao.Tables.t_apply apply = new Dao().new Tables().new t_apply();	
		return apply.delete(conn, " id="+id );
	}
	
	/**
	 * 删除企业融资申请记录
	 * 
	 * @param ids企业融资申请记录编号拼接起来的字符串
	 * @return
	 * @throws SQLException
	 */
	public void deleteBorrowfabiao(Connection conn, String ids)
			throws SQLException {
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_apply t_apply = new Dao().new Tables().new t_apply();
		t_apply.delete(conn, " id in (" + idSQL
				+ ")");
	}
	}

