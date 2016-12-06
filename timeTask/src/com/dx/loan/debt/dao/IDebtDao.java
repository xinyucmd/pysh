/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IDebtDao.java
 * 包名： com.dx.loan.debt.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-6-20 下午02:55:17
 * @version V1.0
 */ 
package com.dx.loan.debt.dao;

import java.util.List;

import com.dx.loan.debt.bean.DebtBean;

/**
 * 类名： IDebtDao
 * 描述： 欠款dao
 * @author 乾之轩
 * @date 2012-6-20 下午02:55:17
 *
 *
 */
public interface IDebtDao {
	/**
	 * 
	 * 方法描述： 获得欠款实体,根据业务设置参数
	 * @param debtBean
	 * @return
	 * DebtBean
	 * @author 乾之轩
	 * @date 2012-6-20 下午02:57:22
	 */
	public DebtBean getLoanDebt (DebtBean debtBean);
	/**
	 * 
	 * 方法描述： 删除欠款实体 ,根据业务设置参数
	 * @param debtBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-20 下午02:57:25
	 */
	public void  delLoanDebt(DebtBean debtBean);
	/**
	 * 
	 * 方法描述： 保存欠款实体
	 * @param debtBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-20 下午02:57:28
	 */
	public void  saveLoanDebt(DebtBean debtBean);
	/**
	 * 
	 * 方法描述：  更新欠款实体
	 * @param debtBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-20 下午02:57:31
	 */
	public void updateLoanDebt(DebtBean debtBean);
	/**
	 * 
	 * 方法描述： 获得欠款列表
	 * @param debtBean
	 * @return
	 * List<DebtBean>
	 * @author 乾之轩
	 * @date 2012-6-20 下午04:04:21
	 */
	public List<DebtBean>  getLoanDebts(DebtBean debtBean);
	
	/**
	 * 
	 * 方法描述： 批量更新欠款 
	 * @param deBeans
	 * void
	 * @author 乾之轩
	 * @date 2012-6-22 下午12:53:23
	 */
	public   void  updateLoanDebtList(List<DebtBean> deBeans); 

	/**
	 *获得 期号小于当前期号且还没有还完款的欠款列表,现在这样查还存在问题,因为还完款的信息也还存在只是欠款金额为0
	 * @param deBeans
	 */
	public   List<DebtBean>  getLoanDebtList(DebtBean deBeans); 
	
	
	
	
	

}
