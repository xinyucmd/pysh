/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PutOutDaoImpl.java
 * 包名： com.dx.loan.putout.dao.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-15 下午06:40:43
 * @version V1.0
 */ 
package com.dx.loan.putout.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.common.bean.PageBean;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.putout.dao.IPutOutDao;

/**
 * 类名： PutOutDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-5-15 下午06:40:43
 *
 *
 */
public class PutOutDaoImpl extends SqlMapClientDaoSupport  implements IPutOutDao {

	
	/**
	 * 
	 * 方法描述： 保存合同实体
	 * @param pactBean
	 * @author 乾之轩
	 * @date 2012-5-28 下午03:00:19
	 */
	public  void savePactBean(PactBean pactBean){
	 	this.getSqlMapClientTemplate().insert("savePactBean",pactBean);
	}
	
	/**
	 * 
	 * 方法描述： 根据合同号获得合同实体 
	 * @param pactNo
	 * @return
	 * @author 乾之轩
	 * @date 2012-5-24 上午09:35:24
	 */
	public PactBean getPactById(String pactNo) {
		return (PactBean)this.getSqlMapClientTemplate().queryForObject("getPactById",pactNo);
	}
	

	/**
	 * 
	 * 方法描述： 获得合同补录列表
	 * @return
	 * @author 乾之轩
	 * @date 2012-5-28 上午10:23:52
	 */
		@SuppressWarnings("unchecked")
		public List<PactBean> getAdditiList(PageBean pageBean) {
			// 设置总记录条数
			List<PactBean> pactBeans = (List<PactBean>)this.getSqlMapClientTemplate().queryForList("getAdditiList",pageBean);
			pageBean.setTotalSize(String.valueOf(this.getSqlMapClientTemplate().queryForObject("getAdditiListSize")));
			return pactBeans;
		}
		

	/**
	 * 
	 * 方法描述： 获得待确认合同列表
	 * @return
	 * @author 乾之轩
	 * @date 2012-5-28 上午10:10:56
	 */
	@SuppressWarnings("unchecked")
	public List<PactBean> getConfList() {
		return this.getSqlMapClientTemplate().queryForList("getConfList");
	}
/**
 * 
 * 方法描述： 更新合同实体
 * @param pactBean
 * @author 乾之轩
 * @date 2012-5-29 下午07:33:33
 */
	public void updatePactBean(PactBean pactBean) {
		 this.getSqlMapClientTemplate().update("updatePactBean",pactBean);
	}
}
