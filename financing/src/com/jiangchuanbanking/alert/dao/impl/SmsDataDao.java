package com.jiangchuanbanking.alert.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.alert.dao.IAlertItemDao;
import com.jiangchuanbanking.alert.dao.ISmsDataDao;
import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.domain.SmsData;
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
public class SmsDataDao extends BaseDao<SmsData> implements ISmsDataDao {

	public void insert(SmsData smsDataBean) {
		// TODO Auto-generated method stub
		
	}
	
}
