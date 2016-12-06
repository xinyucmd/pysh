/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IDebtDao.java
 * ������ com.dx.loan.debt.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-20 ����02:55:17
 * @version V1.0
 */ 
package com.dx.loan.debt.dao;

import java.util.List;

import com.dx.loan.debt.bean.DebtBean;

/**
 * ������ IDebtDao
 * ������ Ƿ��dao
 * @author Ǭ֮��
 * @date 2012-6-20 ����02:55:17
 *
 *
 */
public interface IDebtDao {
	/**
	 * 
	 * ���������� ���Ƿ��ʵ��,����ҵ�����ò���
	 * @param debtBean
	 * @return
	 * DebtBean
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����02:57:22
	 */
	public DebtBean getLoanDebt (DebtBean debtBean);
	/**
	 * 
	 * ���������� ɾ��Ƿ��ʵ�� ,����ҵ�����ò���
	 * @param debtBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����02:57:25
	 */
	public void  delLoanDebt(DebtBean debtBean);
	/**
	 * 
	 * ���������� ����Ƿ��ʵ��
	 * @param debtBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����02:57:28
	 */
	public void  saveLoanDebt(DebtBean debtBean);
	/**
	 * 
	 * ����������  ����Ƿ��ʵ��
	 * @param debtBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����02:57:31
	 */
	public void updateLoanDebt(DebtBean debtBean);
	/**
	 * 
	 * ���������� ���Ƿ���б�
	 * @param debtBean
	 * @return
	 * List<DebtBean>
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����04:04:21
	 */
	public List<DebtBean>  getLoanDebts(DebtBean debtBean);
	
	/**
	 * 
	 * ���������� ��������Ƿ�� 
	 * @param deBeans
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-22 ����12:53:23
	 */
	public   void  updateLoanDebtList(List<DebtBean> deBeans); 

	/**
	 *��� �ں�С�ڵ�ǰ�ں��һ�û�л�����Ƿ���б�,���������黹��������,��Ϊ��������ϢҲ������ֻ��Ƿ����Ϊ0
	 * @param deBeans
	 */
	public   List<DebtBean>  getLoanDebtList(DebtBean deBeans); 
	
	
	
	
	

}
