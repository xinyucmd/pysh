package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.SettingActivityDao;

/**
 * 活动配置
 * @author Administrator
 */
public class SettingActivityService extends BaseService  {
	public static Log log = LogFactory.getLog(SettingActivityService.class);

	private SettingActivityDao settingActivityDao;

	public Map<String, String> getSettingActivity(String code) throws SQLException, DataException{
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = settingActivityDao.getSettingActivity(conn, code);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 分页查询活动设置信息
	 * 
	 * @author guojingchao
	 * @date 2014-12-08
	 * @param pageBean
	 * @throws Exception
	 */
	public void querySetActivityPage(PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "t_setting_activity", "*",
					"order by  code  desc ", "");

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 根据ID查询活动设置条记录
	 * 
	 * @author guojingchao
	 * @date 2014-12-09
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> querySettingActivityById(Long id) throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = null;
		try {
			map =settingActivityDao.querySettingActivityById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return map;
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
	public Long updateSettingActivity(Long id,String startTime,String endTime,String userId,String process) throws Exception{
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = settingActivityDao.updateSettingActivity(conn, id, startTime, endTime, userId,process);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	
	
	public SettingActivityDao getSettingActivityDao() {
		return settingActivityDao;
	}

	public void setSettingActivityDao(SettingActivityDao settingActivityDao) {
		this.settingActivityDao = settingActivityDao;
	}
	
	
}
