/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanDaoImpl.java
 * 包名： com.dx.loan.plan.dao.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-26 上午10:08:35
 * @version V1.0
 */ 
package com.dx.loan.plan.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.dao.IPlanDao;

/**
 * 类名： PlanDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-5-26 上午10:08:35
 *
 *
 */
public class PlanDaoImpl extends SqlMapClientDaoSupport implements IPlanDao {
	/**
	 * 
	 * 方法描述： 获得还没有还完款的还款计划(0 正常 1逾期 2 还完款)
	 * @param systemDate 系统当前时间
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-6-11 下午04:52:30
	 */
	@SuppressWarnings("unchecked")
	public List<PlanBean> getNormalPlanList(String systemDate) {
		return this.getSqlMapClientTemplate().queryForList("getNormalPlanList",systemDate);
	}

	public List<PlanBean> getOverPlanList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * 方法描述： 根据借据号和期号更新还款计划
	 * @param planBean
	 * void   int
	 * @author 乾之轩
	 * @date 2012-5-17 上午09:09:46
	 */
	public int updatePlan(PlanBean planBean){
		return this.getSqlMapClientTemplate().update("updatePlan",planBean);
	}
/**
 * 
 * 方法描述： 保存还款计划 
 * @param planBeans
 * @author 乾之轩
 * @date 2012-6-5 下午07:54:29
 */
	public void savePlanList(List<PlanBean> planBeans) {
		try {
			this.getSqlMapClient().startBatch();
			int i = 0;
			for (PlanBean planBean : planBeans) {
				// 每500条数据为一批
				i++;
				if (i == 500) {
					this.getSqlMapClient().executeBatch();
					this.getSqlMapClient().startBatch();
					i = 0;
				}
				this.getSqlMapClient().insert("savePlanList",	planBean);
			}
			this.getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
/**
 * 
 * 方法描述： 删除还款计划,参数根据业务而定
 * @param planBean
 * @author 乾之轩
 * @date 2012-6-18 上午10:42:11
 */
public void delPlan(PlanBean planBean) {
	 this.getSqlMapClientTemplate().delete("delPlan", planBean);
}

/**
 * 
 * 方法描述： 批量更新还款计划的状态和欠款标志 
 * @param state
 * @param isDebt
 * @param planBeans
 * @author 乾之轩
 * @date 2012-6-22 上午09:44:39
 */
public void updatePlanList(String state, String isDebt,String dueNo, List<PlanBean> planBeans) {
	Map<String, Object> parmMap = new HashMap<String, Object>();
	parmMap.put("dueNo", dueNo);
	parmMap.put("state", state);
	parmMap.put("isDebt", isDebt);
	parmMap.put("planBeans", planBeans);
	this.getSqlMapClientTemplate().update("updatePlanList",parmMap);
	
}


public PlanBean getPlanBean(PlanBean parmPlanBean){
	return (PlanBean) this.getSqlMapClientTemplate().queryForObject("getPlan", parmPlanBean);
}


public List<PlanBean> getPlanBeanList(PlanBean parmPlanBean){
	return  this.getSqlMapClientTemplate().queryForList("getPlan", parmPlanBean);
}

}
