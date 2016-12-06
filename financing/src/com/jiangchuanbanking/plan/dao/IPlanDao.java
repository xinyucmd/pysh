package com.jiangchuanbanking.plan.dao;

/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IPlanDao.java
 * 包名： com.dx.loan.plan.dao
 * 说明：
 * @version V1.0
 */ 


import java.sql.SQLException;
import java.util.List;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.plan.domain.PlanBean;


/**
 * 类名： IPlanDao
 */
public interface IPlanDao extends IBaseDao<PlanBean>  {

	void savePlanList(List<PlanBean> planBeans) throws SQLException;
	
	
}
