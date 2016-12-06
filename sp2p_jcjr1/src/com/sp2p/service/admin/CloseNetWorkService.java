package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.CloseNetWorkDao;
import com.sp2p.database.Dao.Procedures;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;

/**
 * 推荐好友奖励设置
 * 
 * @author Administrator
 * 
 */
public class CloseNetWorkService extends BaseService {
	public static Log log = LogFactory.getLog(CloseNetWorkService.class);

	private CloseNetWorkDao closeNetWorkDao;

	private ConnectionManager connectionManager;

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public CloseNetWorkDao getCloseNetWorkDao() {
		return closeNetWorkDao;
	}

	public void setCloseNetWorkDao(CloseNetWorkDao closeNetWorkDao) {
		this.closeNetWorkDao = closeNetWorkDao;
	}

	/**
	 * 更新网站状态（开启或禁用）
	 * 
	 * @param conn
	 * @param id
	 * @param sort
	 * @param columName
	 * @param content
	 * @param publishTimee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateNetWork(Integer status, String content)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = closeNetWorkDao.updateNetWork(conn, status, content);
			conn.commit();
		} catch (SQLException e) {
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
	 * 获取网站信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getNetWorkById() throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = closeNetWorkDao.getNetWorkById(conn);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Long initMysql() throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
//			Procedures.p_init(conn, ds, outParameterValues,-1,"");
//			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
//			map.put("ret", result + "");
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

}
