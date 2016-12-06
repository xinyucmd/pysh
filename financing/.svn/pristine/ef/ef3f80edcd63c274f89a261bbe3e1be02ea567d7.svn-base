package com.jiangchuanbanking.prod.dao.impl;

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
import com.jiangchuanbanking.prod.dao.IProdChargePolicyDao;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;

/**
 * Call service
 */
public class ProdChargePolicyDao extends BaseDao<ProdChargePolicy> implements IProdChargePolicyDao {

	public List<ProdChargePolicy> findScheduleProdBaseEntitys()
			throws Exception {
		List<ProdChargePolicy> prods =this.findByHQL(" from ProdChargePolicy");
		/*this.findByParam(
				"from wealth_prdt_base  "				);*/
//		for (ProdBaseEntity prod : prods) {
//			Hibernate.initialize(call.getContacts());
//			Hibernate.initialize(call.getLeads());
//			Hibernate.initialize(call.getUsers());
//		}

		return prods;
	}

	public List<ProdChargePolicy> findPolicy(ProdChargePolicy prodChargePolicy) {
		return this.findByHQL("from ProdChargePolicy a where a.prodBaseEntity.prdtNo = '"+prodChargePolicy.getPrdtNo()+
				"' and a.type='"+prodChargePolicy.getType()	+"'");
	}

}
