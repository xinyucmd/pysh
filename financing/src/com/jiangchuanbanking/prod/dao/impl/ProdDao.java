package com.jiangchuanbanking.prod.dao.impl;

import java.util.List;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service
 */
public class ProdDao extends BaseDao<ProdBaseEntity> implements IProdDao {


	public List<ProdBaseEntity> findScheduleProdBaseEntitys() throws Exception {
		List<ProdBaseEntity> prods = this.findByHQL(" from ProdBaseEntity");
		return prods;
	}

	

	public void deleteEntity(String prdtNo) {
		//new HibernateUtil().executeUpdate("update ProdBaseEntity a set a.delFlg = ? where a.prdtNo = ?", "1", prdtNo);
//		this.executeUpdate(
//				"update ProdBaseEntity a set a.delFlg = ? where a.prdtNo = ?",
//				"1", prdtNo);
		//this.update("update ProdBaseEntity a set a.delFlg = ? where a.prdtNo = ?", "0",prdtNo);
	}



	public ProdBaseEntity getProdByNo(String prdtNo) {
		return (ProdBaseEntity) this.findByParam("from ProdBaseEntity where prdtNo=? ", prdtNo).get(0);
	}

}
