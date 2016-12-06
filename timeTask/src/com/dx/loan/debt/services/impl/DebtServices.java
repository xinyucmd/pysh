/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� DebtServices.java
 * ������ com.dx.loan.debt.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-20 ����03:31:30
 * @version V1.0
 */ 
package com.dx.loan.debt.services.impl;

import java.util.List;

import com.dx.common.util.BigNumberUtil;
import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.dao.IDebtDao;

/**
 * ������ DebtServices
 * ������ Ƿ�������
 * @author Ǭ֮��
 * @date 2012-6-20 ����03:31:30
 *
 *
 */
public class DebtServices {
	public void setDebtDao(IDebtDao debtDao) {
		this.debtDao = debtDao;
	}

	private IDebtDao debtDao;
	/**
	 * 
	 * ���������� ����Ƿ��ʵ��
	 * @param debtBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����03:43:20
	 */
	public void  saveDebtBean(DebtBean debtBean){
		debtDao.saveLoanDebt(debtBean);
	}
	/**
	 * 
	 * ���������� ���Ƿ��ʵ��
	 * @param debtBean
	 * @return
	 * DebtBean
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����03:44:35
	 */
	public  DebtBean getDebtBean(DebtBean debtBean){
		return debtDao.getLoanDebt(debtBean);
	}
	
	/**
	 * 
	 * ���������� ���Ƿ���б�
	 * @param debtBean
	 * @return
	 * List<DebtBean>
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����04:02:49
	 */
	public  List<DebtBean> getDebtBeans(DebtBean debtBean){
		return debtDao.getLoanDebts(debtBean);
	}
	
/**
 * 	
 * ������������������Ƿ�� 
 * @param deBeans
 * void
 * @author Ǭ֮��
 * @date 2012-6-22 ����03:01:42
 */
	public void updateLoanDebtList(List<DebtBean> deBeans){
		debtDao.updateLoanDebtList(deBeans);
	}
	/**
	 * ��� �ں�С�ڵ�ǰ�ں��һ�û�л�����Ƿ���б�,���������黹��������,��Ϊ��������ϢҲ������ֻ��Ƿ����Ϊ0
	 * @param debtBean
	 */
	
	public List<DebtBean> getLoanDebtList(DebtBean debtBean){
		return debtDao.getLoanDebtList(debtBean);
	}
	/**
	 * ���ݽ�ֺŻ��Ƿ����
	 * @param dueNo
	 */
	public String  getDebtByDueNo(String dueNo){
		String debtMoney = "0.00";
		DebtBean parmDebtBean = new DebtBean();
		parmDebtBean.setDueNo(dueNo);
		List<DebtBean> debtBeans = getLoanDebtList(parmDebtBean);
		for(DebtBean debtBean:debtBeans){
			debtMoney = BigNumberUtil.Add(debtMoney,debtBean.getDebAccFee(),debtBean.getDebCapital(),debtBean.getDebCmpdintst(),debtBean.getDebIntst(),debtBean.getDebOverintst()); 
		}
		return debtMoney;
	}
	
	
	

}
