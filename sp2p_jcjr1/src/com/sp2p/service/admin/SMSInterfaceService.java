package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.util.SMSUtil;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.SMSInterfaceDao;

public class SMSInterfaceService extends BaseService {
	public static Log log = LogFactory.getLog(SMSInterfaceService.class);

	private SMSInterfaceDao sMSInterfaceDao;

	private ConnectionManager connectionManager;
	
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public SMSInterfaceDao getSMSInterfaceDao() {
		return sMSInterfaceDao;
	}

	public void setSMSInterfaceDao(SMSInterfaceDao interfaceDao) {
		sMSInterfaceDao = interfaceDao;
	}

	public long sendSMSByConditions(long userId,String content) throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			Map<String,String> userMap = userDao.queryUserById(conn,userId);
			Map<String, String> map = getSMSById(1);
	        String retCode = SMSUtil.sendMSM(map.get("Account"), map.get("Password"), content.toString(), userMap.get("mobilePhone"), null);
	        conn.commit();
	        if ("Sucess".equals(retCode)) {
	        	return 1;
	        }else{
	        	return -1;
	        }
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 更新短信接口信息
	 * 
	 * @param conn
	 * @param id
	 * @param UserID
	 * @param Account
	 * @param Password
	 * @param status
	 * @param url
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateSMS(Integer id, String UserID, String Account,
			String Password, String status, String url) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = sMSInterfaceDao.updateSMS(conn, id, UserID, Account,
					Password, status, url);
			if(result < 0){
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/** 
	* 根据Id获取短信接口信息详情 
	* 
	* @param conn 
	* @param id 
	* @return 
	* @throws SQLException 
	* @throws DataException 
	*/ 
	public Map<String, String> getSMSById(Connection conn ,Integer id) throws Exception { 
		Map<String, String> map = null; 
		map = sMSInterfaceDao.getSMSById(conn, id); 
		return map; 
		} 
	/** 
	* 根据Id获取短信接口信息详情 (重载方法)
	* 
	* @param conn 
	* @param id 
	* @return 
	* @throws SQLException 
	* @throws DataException 
	*/ 
	public Map<String, String> getSMSById(Integer id) throws Exception { 
		Connection conn = MySQL.getConnection(); 
		Map<String, String> map = null; 
		try { 
		map = getSMSById(conn,id); 
	
		} catch (Exception e) { 
		log.error(e); 
		e.printStackTrace(); 
	
		throw e; 
		} finally { 
		conn.close(); 
		} 
		return map; 
		}
	/**
	 * 获取短信接口列表
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> findBySMS() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = sMSInterfaceDao.findBySMS(conn);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}

}
