/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� DebtDaoImpl.java
 * ������ com.dx.loan.debt.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-20 ����02:54:39
 * @version V1.0
 */ 
package com.dx.loan.debt.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.dao.IDebtDao;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ������ DebtDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-6-20 ����02:54:39
 *
 *
 */
public class DebtDaoImpl extends SqlMapClientDaoSupport implements IDebtDao {
/**
 * 
 * ���������� ɾ��Ƿ��ʵ��
 * @param debtBean
 * @author Ǭ֮��
 * @date 2012-6-20 ����03:25:34
 */
	public void delLoanDebt(DebtBean debtBean) {
		this.getSqlMapClientTemplate().delete("delLoanDebt", debtBean);
		
	}
/**
 * 
 * ���������� ���Ƿ��,��������ҵ��������
 * @param debtBean
 * @return
 * @author Ǭ֮��
 * @date 2012-6-20 ����03:25:48
 */
	public DebtBean getLoanDebt(DebtBean debtBean) {
		return	(DebtBean) this.getSqlMapClientTemplate().queryForObject("getLoanDebt",debtBean);
	}
	/**
	 * 
	 * ���������� ���Ƿ���б�
	 * @param debtBean
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����04:05:47
	 */
	@SuppressWarnings("unchecked")
	public List<DebtBean> getLoanDebts(DebtBean debtBean) {
		return	 this.getSqlMapClientTemplate().queryForList("getLoanDebt",debtBean);
	}

/**
 * 
 * ���������� ����Ƿ��ʵ��
 * @param debtBean
 * @author Ǭ֮��
 * @date 2012-6-20 ����03:25:54
 */
	public void saveLoanDebt(DebtBean debtBean) {
		this.getSqlMapClientTemplate().insert("saveLoanDebt",debtBean);
		
	}

/**
 * 
 * ���������� ����Ƿ��ʵ��
 * @param debtBean
 * @author Ǭ֮��
 * @date 2012-6-20 ����03:26:00
 */
	public void updateLoanDebt(DebtBean debtBean) {
		this.getSqlMapClientTemplate().update("updateLoanDebt",debtBean);
	}
	
/**
 * 
 * ���������� ��������Ƿ��
 * @param deBeans
 * @author Ǭ֮��
 * @date 2012-6-22 ����12:54:13
 */
public void updateLoanDebtList(List<DebtBean> deBeans) {
	try {
		this.getSqlMapClient().startBatch();
		int i = 0;
		for (DebtBean debtBean : deBeans) {
			// ÿ500������Ϊһ��
			i++;
			if (i == 500) {
				this.getSqlMapClient().executeBatch();
				this.getSqlMapClient().startBatch();
				i = 0;
			}
			this.getSqlMapClient().update("updateLoanDebt",	debtBean);
		}
		this.getSqlMapClient().executeBatch();
	} catch (SQLException e) {
		e.printStackTrace();
	}
   }


/**
 * ��� �ں�С�ڵ�ǰ�ں��һ�û�л�����Ƿ���б�,���������黹��������,��Ϊ��������ϢҲ������ֻ��Ƿ����Ϊ0
 */
public List<DebtBean> getLoanDebtList(DebtBean debtBean) {
	return this.getSqlMapClientTemplate().queryForList("getLoanDebtList", debtBean);
}




}
