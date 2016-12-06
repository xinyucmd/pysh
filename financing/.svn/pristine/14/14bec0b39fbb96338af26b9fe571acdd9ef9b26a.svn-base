/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ��� PlanDaoImpl.java
 * ���� com.dx.loan.plan.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-26 ����10:08:35
 * @version V1.0
 */ 
package com.jiangchuanbanking.plan.dao.impl;


import java.sql.SQLException;
import java.util.List;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.plan.dao.IPlanDao;
import com.jiangchuanbanking.plan.domain.PlanBean;
public class PlanDaoImpl   extends BaseDao<PlanBean>  implements IPlanDao {

	public void savePlanList(List<PlanBean> planBeans) throws SQLException {
		for (PlanBean planBean : planBeans) {
			this.makePersistent(planBean);
		}		
	}
	
	
}
