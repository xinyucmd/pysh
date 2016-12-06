package com.jiangchuanbanking.account.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.account.dao.IMainAccountDao;
import com.jiangchuanbanking.account.domain.MainAccount;
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
public class MainAccountDao extends BaseDao<MainAccount> implements IMainAccountDao {

	public List<MainAccount> findScheduleEntitys()
			throws Exception {
		List<MainAccount> main =this.findByHQL(" from MainAccount");
		/*this.findByParam(
				"from wealth_prdt_base  "				);*/
//		for (ProdBaseEntity prod : prods) {
//			Hibernate.initialize(call.getContacts());
//			Hibernate.initialize(call.getLeads());
//			Hibernate.initialize(call.getUsers());
//		}

		return main;
	}

	@Override
	public String getAccNo() {
		String sql="select MAINACC_SEQUENCE.nextval from dual";
		SQLQuery query = this.getSession().createSQLQuery(sql);  	
		return query.uniqueResult().toString();
	}


}
