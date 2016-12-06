package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 活动设置类
 * @author 
 */
public class SettingActivityDao {

	public  Map<String, String> getSettingActivity(Connection conn,String code) throws SQLException, DataException{
		StringBuffer condition = new StringBuffer();
		condition.append("code=");
		condition.append(code);
		Dao.Views.v_setting_activity v_setting_activity = new Dao().new Views().new v_setting_activity();
		DataSet ds = v_setting_activity.open(conn, "*", condition.toString(),"",-1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 根据id查询活动设置一条记录.
	 * 
	 * @author guojingchao
	 * @date 2014-12-09
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> querySettingActivityById(Connection conn,Long id) throws SQLException,DataException{
		Dao.Tables.t_setting_activity  t_setting_activity  = new Dao().new Tables().new t_setting_activity();
		DataSet ds  = t_setting_activity.open(conn, "*", "id="+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 根据ID更新活动设置条记录
	 * 
	 * @author guojingchao
	 * @date 2014-12-09
	 * @param conn
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateSettingActivity(Connection conn,Long id,String startTime,String endTime,String userId,String process) throws SQLException, DataException {
		Dao.Tables.t_setting_activity  t_setting_activity  = new Dao().new Tables().new t_setting_activity();
		
		if(StringUtils.isNotBlank(startTime)){
			t_setting_activity.start_time.setValue(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			t_setting_activity.end_time.setValue(endTime);
		}
		if(StringUtils.isNotBlank(userId)){
			t_setting_activity.user_id.setValue(userId);
		}
		if(StringUtils.isNotBlank(process)){
			t_setting_activity.process.setValue(process);
		}
		
		return t_setting_activity.update(conn, "id=" + id);
	}
}
