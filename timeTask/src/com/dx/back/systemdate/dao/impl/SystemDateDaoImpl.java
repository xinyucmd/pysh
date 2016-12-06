/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SystemDateDaoImpl.java
 * 包名： com.dx.back.systemdate.dao.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-12 上午10:12:49
 * @version V1.0
 */ 
package com.dx.back.systemdate.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.dao.ISystemDateDao;
import com.dx.common.util.DateUtil;

/**
 * 类名： SystemDateDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-6-12 上午10:12:49
 *
 *
 */
public class SystemDateDaoImpl extends SqlMapClientDaoSupport implements ISystemDateDao {
/**
 * 
 * 方法描述： 获得系统的当前日期
 * @return
 * @author 乾之轩
 * @date 2012-6-12 上午10:17:08
 */
	public SystemDateBean getSytstemDate() {
		return (SystemDateBean) this.getSqlMapClientTemplate().queryForObject("getSytstemDate");
	}
/**
 * 
 * 方法描述：更新系统日期 
 * @param date
 * @author 乾之轩
 * @date 2012-6-12 上午10:23:58
 */
	
	public void updateSystemDate(SystemDateBean systemDateBean) {
		try{
			this.getSqlMapClientTemplate().update("updateSystemDate", systemDateBean);
		}catch(Exception e){
			e.printStackTrace();	
		}
		
	}
	
	

	
	
}
