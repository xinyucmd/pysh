package com.jiangchuanbanking.subscription.dao;


import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;

public interface ISubscripDao extends IBaseDao{
      
	public SearchResult<ListSubscrip> getSubscripObjects(final String clazz,
            final String columns, final SearchCondition searchCondition);
	
	public String getPactNo();
		
	
}
