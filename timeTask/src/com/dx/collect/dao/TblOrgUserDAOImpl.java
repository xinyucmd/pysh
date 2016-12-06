package com.dx.collect.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.TblOrgUser;

/**
 * πÒ‘±dao
 * 
 * @author leopard
 * @date 2010-11-08
 * 
 */
public class TblOrgUserDAOImpl extends SqlMapClientDaoSupport implements
		TblOrgUserDAO {

	public TblOrgUser getUserByBrNO(TblOrgUser user) {
		TblOrgUser tou = (TblOrgUser) getSqlMapClientTemplate().queryForObject(
				"TblOrgUser.getUserByBrNO", user);
		return tou;
	}

}
