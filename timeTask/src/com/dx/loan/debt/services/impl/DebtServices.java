/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DebtServices.java
 * 包名： com.dx.loan.debt.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-20 下午03:31:30
 * @version V1.0
 */ 
package com.dx.loan.debt.services.impl;

import java.util.List;

import com.dx.common.util.BigNumberUtil;
import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.dao.IDebtDao;

/**
 * 类名： DebtServices
 * 描述： 欠款服务类
 * @author 乾之轩
 * @date 2012-6-20 下午03:31:30
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
	 * 方法描述： 保存欠款实体
	 * @param debtBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-20 下午03:43:20
	 */
	public void  saveDebtBean(DebtBean debtBean){
		debtDao.saveLoanDebt(debtBean);
	}
	/**
	 * 
	 * 方法描述： 获得欠款实体
	 * @param debtBean
	 * @return
	 * DebtBean
	 * @author 乾之轩
	 * @date 2012-6-20 下午03:44:35
	 */
	public  DebtBean getDebtBean(DebtBean debtBean){
		return debtDao.getLoanDebt(debtBean);
	}
	
	/**
	 * 
	 * 方法描述： 获得欠款列表
	 * @param debtBean
	 * @return
	 * List<DebtBean>
	 * @author 乾之轩
	 * @date 2012-6-20 下午04:02:49
	 */
	public  List<DebtBean> getDebtBeans(DebtBean debtBean){
		return debtDao.getLoanDebts(debtBean);
	}
	
/**
 * 	
 * 方法描述：批量更新欠款 
 * @param deBeans
 * void
 * @author 乾之轩
 * @date 2012-6-22 下午03:01:42
 */
	public void updateLoanDebtList(List<DebtBean> deBeans){
		debtDao.updateLoanDebtList(deBeans);
	}
	/**
	 * 获得 期号小于当前期号且还没有还完款的欠款列表,现在这样查还存在问题,因为还完款的信息也还存在只是欠款金额为0
	 * @param debtBean
	 */
	
	public List<DebtBean> getLoanDebtList(DebtBean debtBean){
		return debtDao.getLoanDebtList(debtBean);
	}
	/**
	 * 根据结局号获得欠款金额
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
