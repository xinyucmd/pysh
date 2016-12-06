package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.admin.GroupDao;
import com.sp2p.dao.admin.SettingDebtDao;
import com.sp2p.dao.admin.ShoveBorrowTypeDao;

/**
 * 债权转让参数设置
 */
public class SettingDebtService extends BaseService{
	public static Log log = LogFactory.getLog(SettingDebtService.class);
	private SettingDebtDao settingDebtDao;
	private ShoveBorrowTypeDao shoveBorrowTypeDao;//标种类型
	//whb添加用户组每月债转限额
	private GroupDao groupDao;
		

	/**
	 * 查询标种类型
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryBorrowType(String id) throws Exception{
		List<Map<String, Object>> typeList = null;
		Connection conn = MySQL.getConnection();
		try {
			typeList = shoveBorrowTypeDao.queryBorrowType(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.commit();
			throw e;
		} finally {
			conn.close();
		}
		
		return typeList;
	}
	
	/**
	 * 查询用户组
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAllGroup() throws Exception{
		List<Map<String, Object>> groupList = null;//用户组
		Connection conn = MySQL.getConnection();
		try {
			groupList = groupDao.queryAllGroup(conn);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.commit();
			throw e;
		} finally {
			conn.close();
		}
		
		return groupList;
	}
	
	/**
	 * 查询转债
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> querySettingDebtById(long id) throws Exception{
		Map<String, String> result = null;
		Connection conn = MySQL.getConnection();
		try {
			result = settingDebtDao.querySettingDebtById(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.commit();
			throw e;
		} finally {
			conn.close();
		}
		
		return result;
	}
	
	public Long addSettingDebt(int holdShortDays,int rangeExpiryDays,String transmoneyMonth,
			String borrowTypes,String borrowTypesLable,double transRatio)throws Exception{
		Long id = -1L;
		Connection conn = MySQL.getConnection();
		try {
			id = addSettingDebtInfo(conn, holdShortDays, rangeExpiryDays, transmoneyMonth, borrowTypes, borrowTypesLable, transRatio);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.commit();
			throw e;
		} finally {
			conn.close();
		}
		
		return id;
	}
	
	public Long updateSettingDebt(long id,int holdShortDays,int rangeExpiryDays,String transmoneyMonth,
			String borrowTypes,String borrowTypesLable,double transRatio)throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			id = updateSettingDebtInfo(conn,id, holdShortDays, rangeExpiryDays, transmoneyMonth, borrowTypes, borrowTypesLable, transRatio);
			//whb添加用户组每月债转限额
			String[] transmoney = transmoneyMonth.split(",");
			String[] transGroup = null;
			for(int i=0;i<transmoney.length;i++){
				transGroup = transmoney[i].split(":");
				groupDao.updateGroup(conn, Convert.strToLong(transGroup[0], -1), -1, "","", -1, Convert.strToDouble(transGroup[1], 0));
			}
			
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		
		return id;
	}
	
	/***
	 * 判断用户是否是超级投资人-如是可债转定息宝，否则不可债转定息宝
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryGroupUser(long userId) throws Exception{
		Map<String, String> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = settingDebtDao.queryGroupUser(conn, userId);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.commit();
			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}
	
	/***
	 * 判断用户是否是超级投资人(机构用户和理财用户)-如是可债转定息宝，否则不可债转定息宝
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryGroupAndLcUser(long userId) throws Exception{
		Map<String, String> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = settingDebtDao.queryGroupAndLcUser(conn, userId);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.commit();
			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}
	
	
	private Long addSettingDebtInfo(Connection conn,int holdShortDays,int rangeExpiryDays,String transmoneyMonth,
			String borrowTypes,String borrowTypesLable,double transRatio)throws Exception{
		return settingDebtDao.addSettingDebt(conn, holdShortDays, rangeExpiryDays, transmoneyMonth, borrowTypes, borrowTypesLable, transRatio);
	}
	
	private Long updateSettingDebtInfo(Connection conn,long id,int holdShortDays,int rangeExpiryDays,String transmoneyMonth,
			String borrowTypes,String borrowTypesLable,double transRatio)throws Exception{
		return settingDebtDao.updateSettingDebt(conn, id, holdShortDays, rangeExpiryDays, transmoneyMonth, borrowTypes, borrowTypesLable, transRatio);
	}

	public SettingDebtDao getSettingDebtDao() {
		return settingDebtDao;
	}

	public void setSettingDebtDao(SettingDebtDao settingDebtDao) {
		this.settingDebtDao = settingDebtDao;
	}

	public ShoveBorrowTypeDao getShoveBorrowTypeDao() {
		return shoveBorrowTypeDao;
	}

	public void setShoveBorrowTypeDao(ShoveBorrowTypeDao shoveBorrowTypeDao) {
		this.shoveBorrowTypeDao = shoveBorrowTypeDao;
	}
	
	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
}
