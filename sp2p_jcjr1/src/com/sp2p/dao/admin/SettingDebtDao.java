package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 债权转让参数设置
 */
public class SettingDebtDao {

	/**
	 * 添加债权转让设置
	 * @param conn
	 * @param holdShortDays
	 * @param rangeExpiryDays
	 * @param transmoneyMonth
	 * @param borrowTypes
	 * @param borrowTypesLable
	 * @param transRatio
	 * @return
	 * @throws SQLException
	 */
	public Long addSettingDebt(Connection conn,int holdShortDays,int rangeExpiryDays,String transmoneyMonth,
							String borrowTypes,String borrowTypesLable,double transRatio)
			throws SQLException {
		borrowTypes=com.shove.web.Utility.filteSqlInfusion(borrowTypes);
		borrowTypesLable=com.shove.web.Utility.filteSqlInfusion(borrowTypesLable);
		
		Dao.Tables.t_setting_debt t_setting_debt = new Dao().new Tables().new t_setting_debt();
		t_setting_debt.hold_short_days.setValue(holdShortDays);
		t_setting_debt.range_expiry_days.setValue(rangeExpiryDays);
		t_setting_debt.transmoney_month.setValue(transmoneyMonth);
		t_setting_debt.borrow_types.setValue(borrowTypes);
		t_setting_debt.borrow_types_lable.setValue(borrowTypesLable);
		t_setting_debt.trans_ratio.setValue(transRatio);
		
		return  t_setting_debt.insert(conn);
	}
	
	/**
	 * 更新债权转让设置
	 * @param conn
	 * @param id
	 * @param holdShortDays
	 * @param rangeExpiryDays
	 * @param transmoneyMonth
	 * @param borrowTypes
	 * @param borrowTypesLable
	 * @param transRatio
	 * @return
	 * @throws SQLException
	 */
	public Long updateSettingDebt(Connection conn,long id,int holdShortDays,int rangeExpiryDays,String transmoneyMonth,
			String borrowTypes,String borrowTypesLable,double transRatio) throws SQLException{
		if(StringUtils.isNotBlank(borrowTypes)){
			borrowTypes=com.shove.web.Utility.filteSqlInfusion(borrowTypes);
		}
		if(StringUtils.isNotBlank(borrowTypesLable)){
			borrowTypesLable=com.shove.web.Utility.filteSqlInfusion(borrowTypesLable);
		}
		
		Dao.Tables.t_setting_debt t_setting_debt = new Dao().new Tables().new t_setting_debt();
		if(holdShortDays>0){
			t_setting_debt.hold_short_days.setValue(holdShortDays);
		}
		if(rangeExpiryDays>0){
			t_setting_debt.range_expiry_days.setValue(rangeExpiryDays);
		}
		if(transmoneyMonth.length() > 0){
			t_setting_debt.transmoney_month.setValue(transmoneyMonth);
		}
		if(StringUtils.isNotBlank(borrowTypes)){
			t_setting_debt.borrow_types.setValue(borrowTypes);
		}
		if(StringUtils.isNotBlank(borrowTypesLable)){
			t_setting_debt.borrow_types_lable.setValue(borrowTypesLable);
		}
		t_setting_debt.trans_ratio.setValue(transRatio);
		
		return  t_setting_debt.update(conn, "id = "+id);
	}
	
	
	/**
	 * 根据Id查询权转让设置
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySettingDebtById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_setting_debt t_setting_debt = new Dao().new Tables().new t_setting_debt();// 获得债权转让设置对象
		DataSet ds = t_setting_debt.open(conn, "*", "", "", -1, -1);// 对数据库进行访问，查询数据，DateSet装载数据结果
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String, String> queryGroupUser(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT  *  from  t_group_user where  groupId = 7 and userId ="+userId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryGroupAndLcUser(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT  *  from  t_group_user where  groupId in (6,7) and userId ="+userId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
}
