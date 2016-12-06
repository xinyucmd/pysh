/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DebtDaoImpl.java
 * 包名： com.dx.loan.debt.dao.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-20 下午02:54:39
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
 * 类名： DebtDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-6-20 下午02:54:39
 *
 *
 */
public class DebtDaoImpl extends SqlMapClientDaoSupport implements IDebtDao {
/**
 * 
 * 方法描述： 删除欠款实体
 * @param debtBean
 * @author 乾之轩
 * @date 2012-6-20 下午03:25:34
 */
	public void delLoanDebt(DebtBean debtBean) {
		this.getSqlMapClientTemplate().delete("delLoanDebt", debtBean);
		
	}
/**
 * 
 * 方法描述： 获得欠款,参数根据业进行设置
 * @param debtBean
 * @return
 * @author 乾之轩
 * @date 2012-6-20 下午03:25:48
 */
	public DebtBean getLoanDebt(DebtBean debtBean) {
		return	(DebtBean) this.getSqlMapClientTemplate().queryForObject("getLoanDebt",debtBean);
	}
	/**
	 * 
	 * 方法描述： 获得欠款列表
	 * @param debtBean
	 * @return
	 * @author 乾之轩
	 * @date 2012-6-20 下午04:05:47
	 */
	@SuppressWarnings("unchecked")
	public List<DebtBean> getLoanDebts(DebtBean debtBean) {
		return	 this.getSqlMapClientTemplate().queryForList("getLoanDebt",debtBean);
	}

/**
 * 
 * 方法描述： 保存欠款实体
 * @param debtBean
 * @author 乾之轩
 * @date 2012-6-20 下午03:25:54
 */
	public void saveLoanDebt(DebtBean debtBean) {
		this.getSqlMapClientTemplate().insert("saveLoanDebt",debtBean);
		
	}

/**
 * 
 * 方法描述： 更新欠款实体
 * @param debtBean
 * @author 乾之轩
 * @date 2012-6-20 下午03:26:00
 */
	public void updateLoanDebt(DebtBean debtBean) {
		this.getSqlMapClientTemplate().update("updateLoanDebt",debtBean);
	}
	
/**
 * 
 * 方法描述： 批量更新欠款
 * @param deBeans
 * @author 乾之轩
 * @date 2012-6-22 下午12:54:13
 */
public void updateLoanDebtList(List<DebtBean> deBeans) {
	try {
		this.getSqlMapClient().startBatch();
		int i = 0;
		for (DebtBean debtBean : deBeans) {
			// 每500条数据为一批
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
 * 获得 期号小于当前期号且还没有还完款的欠款列表,现在这样查还存在问题,因为还完款的信息也还存在只是欠款金额为0
 */
public List<DebtBean> getLoanDebtList(DebtBean debtBean) {
	return this.getSqlMapClientTemplate().queryForList("getLoanDebtList", debtBean);
}




}
