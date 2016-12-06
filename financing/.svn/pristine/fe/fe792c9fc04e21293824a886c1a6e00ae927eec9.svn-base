package com.jiangchuanbanking.flowdetails.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.financing.service.ICallService;
import com.jiangchuanbanking.flowdetails.dao.IFinancingDetailsDao;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service
 */
public class FinancingDetailsDao extends BaseDao<FinancingDetails> implements IFinancingDetailsDao {

	public List<FinancingDetails> findScheduleEntitys(FinancingDetails details)
			throws Exception {
		String sqlString = " from FinancingDetails a  where 1=1 ";
		
		if (details.getAccount_no()!=null) {
			sqlString+= " and a.account_no='"+details.getAccount_no()+"'";
		}
		if (details.getSub_no()!=null) {
			sqlString+= " and a.sub_no='"+details.getSub_no()+"'";
		}
		if (details.getFlow_type()!=null) {
			sqlString+= " and a.flow_type='"+details.getFlow_type()+"'";
		}
		List<FinancingDetails> details1 = this.findByHQL(sqlString);
		return details1;}


}
