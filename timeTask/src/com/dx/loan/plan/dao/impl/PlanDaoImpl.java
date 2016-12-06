/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanDaoImpl.java
 * ������ com.dx.loan.plan.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-26 ����10:08:35
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
 * ������ PlanDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-5-26 ����10:08:35
 *
 *
 */
public class PlanDaoImpl extends SqlMapClientDaoSupport implements IPlanDao {
	/**
	 * 
	 * ���������� ��û�û�л����Ļ���ƻ�(0 ���� 1���� 2 �����)
	 * @param systemDate ϵͳ��ǰʱ��
	 * @return
	 * List<PlanBean>
	 * @author Ǭ֮��
	 * @date 2012-6-11 ����04:52:30
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
	 * ���������� ���ݽ�ݺź��ںŸ��»���ƻ�
	 * @param planBean
	 * void   int
	 * @author Ǭ֮��
	 * @date 2012-5-17 ����09:09:46
	 */
	public int updatePlan(PlanBean planBean){
		return this.getSqlMapClientTemplate().update("updatePlan",planBean);
	}
/**
 * 
 * ���������� ���滹��ƻ� 
 * @param planBeans
 * @author Ǭ֮��
 * @date 2012-6-5 ����07:54:29
 */
	public void savePlanList(List<PlanBean> planBeans) {
		try {
			this.getSqlMapClient().startBatch();
			int i = 0;
			for (PlanBean planBean : planBeans) {
				// ÿ500������Ϊһ��
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
 * ���������� ɾ������ƻ�,��������ҵ�����
 * @param planBean
 * @author Ǭ֮��
 * @date 2012-6-18 ����10:42:11
 */
public void delPlan(PlanBean planBean) {
	 this.getSqlMapClientTemplate().delete("delPlan", planBean);
}

/**
 * 
 * ���������� �������»���ƻ���״̬��Ƿ���־ 
 * @param state
 * @param isDebt
 * @param planBeans
 * @author Ǭ֮��
 * @date 2012-6-22 ����09:44:39
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
