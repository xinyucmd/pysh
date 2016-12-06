package com.jiangchuanbanking.alert.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.alert.dao.IAlertItemDao;
import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.financing.service.ICallService;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service
 */
public class AlertItemDao extends BaseDao<AlertItem> implements IAlertItemDao {

	public List<AlertItem> findScheduleEntitys()
			throws Exception {
		List<AlertItem> alert =this.findByHQL(" from AlertItem");
		/*this.findByParam(
				"from wealth_prdt_base  "				);*/
//		for (ProdBaseEntity prod : prods) {
//			Hibernate.initialize(call.getContacts());
//			Hibernate.initialize(call.getLeads());
//			Hibernate.initialize(call.getUsers());
//		}

		return alert;
	}

	public List<AlertItem> findItem(AlertItem alert) {
		String sqlString = " from AlertItem a  where 1=1 ";
		
		if (alert.getAlert_method()!=null) {
			sqlString+= " and a.alert_method='"+alert.getAlert_method()+"'";
		}
		if (alert.getAlert_type()!=null) {
			sqlString+= " and a.alert_type='"+alert.getAlert_type()+"'";
		}
		if (alert.getAdv_days()!=null) {
			sqlString+= " and a.adv_days='"+alert.getAdv_days()+"'";
		}
		if (alert.getDel_flg()!=null) {
			sqlString+= " and a.del_flg='"+alert.getDel_flg()+"'";
		}
		List<AlertItem> alert1 = this.findByHQL(sqlString);
		return alert1;
	}

	public AlertItem getItem(AlertItem alert) {

		String sqlString = " from AlertItem a  where 1=1 ";
		
		if (alert.getAlert_method()!=null) {
			sqlString+= " and a.alert_method='"+alert.getAlert_method()+"'";
		}
		if (alert.getAlert_type()!=null) {
			sqlString+= " and a.alert_type='"+alert.getAlert_type()+"'";
		}
		if (alert.getAdv_days()!=null) {
			sqlString+= " and a.adv_days='"+alert.getAdv_days()+"'";
		}
		if (alert.getDel_flg()!=null) {
			sqlString+= " and a.del_flg='"+alert.getDel_flg()+"'";
		}
		AlertItem alert1 = this.findByHQL(sqlString).get(0);
		return alert1;
	
	}

}
