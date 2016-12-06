package com.dx.collect.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.repay.bean.LnDue;

public class LnDueDAOImpl extends SqlMapClientDaoSupport implements LnDueDAO {

	public LnDue getByApplyPactNo(String pact_no) {
		LnDue lndue = (LnDue) getSqlMapClientTemplate().queryForObject(
				"LnDueByApplyNo.getByPactId", pact_no);
		return lndue;
	}

}