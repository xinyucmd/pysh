package com.jiangchuanbanking.select.dao.impl;

import org.hibernate.SQLQuery;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.select.dao.ISelectDao;

public class SelectDao extends BaseDao implements ISelectDao{

	@Override
	public String getSubParentId() {
		String sql="select dictparent_sequence.nextval from dual";
		SQLQuery query = this.getSession().createSQLQuery(sql);  	
		return query.uniqueResult().toString();
	}
     
}
