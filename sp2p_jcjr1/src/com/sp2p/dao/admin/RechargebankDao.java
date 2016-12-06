package com.sp2p.dao.admin;

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
import com.sp2p.database.Dao;

public class RechargebankDao {
	
	public Map<String,String> queryrechargeBankById(Connection conn, long id) throws DataException, SQLException{
			Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
			DataSet dataSet = t_rechargebank.open(conn, "", " id = " + id, "", -1, -1);
			return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 添加充值银行
	 * @param conn
	 * @param bankname
	 * @param Account
	 * @param accountbank
	 * @param bankimage
	 * @return
	 * @throws SQLException
	 */
	public long addRechargeBankInit(Connection conn, String bankname, String Account, String accountbank, String bankimage, String accountname, long adminId,String adminIp) throws SQLException {
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		t_rechargebank.bankname.setValue(bankname);
		t_rechargebank.Account.setValue(Account);
		t_rechargebank.accountbank.setValue(accountbank);
		t_rechargebank.bankimage.setValue(bankimage);
		t_rechargebank.accountname.setValue(accountname);
		t_rechargebank.addOperator.setValue(adminId);
		t_rechargebank.addTime.setValue(new Date());
		t_rechargebank.addIp.setValue(adminIp);
		return t_rechargebank.insert(conn);
	}
	
	/**
	 * 删除充值银行
	 * delRechargeBankById
	 * @param conn
	 * @param id
	 * @param adminId
	 * @param adminIp
	 * @return
	 * @throws SQLException
	 * @autthor linww
	 * 2014-6-30 下午01:47:22
	 */
	public long delRechargeBankById(Connection conn, long id, long adminId, String adminIp) throws SQLException {
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		t_rechargebank.delOperator.setValue(adminId);
		t_rechargebank.delTime.setValue(new Date());
		t_rechargebank.delIp.setValue(adminIp);
		t_rechargebank.isDel.setValue(1);
		return t_rechargebank.update(conn, " id = " + id);
		}
	
	
	public long updaterechargeBankById(Connection conn,long id,String bankname,String Account,String accountbank,String bankimage,String accountname) throws SQLException{
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		t_rechargebank.bankname.setValue(bankname);
		t_rechargebank.Account.setValue(Account);
		t_rechargebank.accountbank.setValue(accountbank);
		if(StringUtils.isNotBlank(bankimage)){
			t_rechargebank.bankimage.setValue(bankimage);
		}
		t_rechargebank.accountname.setValue(accountname);
		return t_rechargebank.update(conn, " id = "+id);
		
	}
	
	public Map<String,String> queryrechargeBank(Connection conn) throws DataException, SQLException{
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		DataSet dataSet = t_rechargebank.open(conn, "*", "","", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
}
	/**
	 * 查询后台充值银行
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryrechargeBanklist(Connection conn) throws SQLException, DataException{
		DataSet ds = 	MySQL.executeQuery(conn," select * from t_rechargebank where isDel = 0 ");
	  	ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 * 查询资金流向类型
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryFundRecordType(Connection conn) throws SQLException, DataException{
		DataSet ds = 	MySQL.executeQuery(conn," select DISTINCT  fundMode as  fundMode from t_fundrecord where fundMode <> '虚拟充值'");
	  	ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 统计合计金额
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryFundRecordTypeAmount(Connection conn,long userId,String startTime,
			String endTime,Map<String,String> typeMap) throws SQLException, DataException{
		String condition = "";
		endTime=com.shove.web.Utility.filteSqlInfusion(endTime);
		startTime=com.shove.web.Utility.filteSqlInfusion(startTime);
		if(startTime == null || endTime == null){//没有时间就查询所有记录
			if(typeMap==null){
				condition = "and userId="+userId;
			}else{
				condition = "and userId="+userId+typeMap.get("conditionSQL")+"";
			}
		}else{
			if(typeMap==null){
				condition = "and userId="+userId+" and recordTime<='"+StringEscapeUtils.escapeSql(endTime.trim())+"' and recordTime >= '"+StringEscapeUtils.escapeSql(startTime.trim())+"'";
			}else{
				condition = "and userId="+userId+" and recordTime<='"+StringEscapeUtils.escapeSql(endTime.trim())+"' and recordTime >= '"+StringEscapeUtils.escapeSql(startTime.trim())+typeMap.get("conditionSQL")+"";
			}
		}
		DataSet ds = MySQL.executeQuery(conn,"select sum(handleSum) as allAmount from t_fundrecord  where 1 = 1 "+condition);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	
	
	
	
	
}
