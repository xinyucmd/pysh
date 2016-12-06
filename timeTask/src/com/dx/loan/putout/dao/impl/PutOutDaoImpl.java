/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PutOutDaoImpl.java
 * ������ com.dx.loan.putout.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-15 ����06:40:43
 * @version V1.0
 */ 
package com.dx.loan.putout.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.common.bean.PageBean;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.putout.dao.IPutOutDao;

/**
 * ������ PutOutDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-5-15 ����06:40:43
 *
 *
 */
public class PutOutDaoImpl extends SqlMapClientDaoSupport  implements IPutOutDao {

	
	/**
	 * 
	 * ���������� �����ͬʵ��
	 * @param pactBean
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����03:00:19
	 */
	public  void savePactBean(PactBean pactBean){
	 	this.getSqlMapClientTemplate().insert("savePactBean",pactBean);
	}
	
	/**
	 * 
	 * ���������� ���ݺ�ͬ�Ż�ú�ͬʵ�� 
	 * @param pactNo
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����09:35:24
	 */
	public PactBean getPactById(String pactNo) {
		return (PactBean)this.getSqlMapClientTemplate().queryForObject("getPactById",pactNo);
	}
	

	/**
	 * 
	 * ���������� ��ú�ͬ��¼�б�
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����10:23:52
	 */
		@SuppressWarnings("unchecked")
		public List<PactBean> getAdditiList(PageBean pageBean) {
			// �����ܼ�¼����
			List<PactBean> pactBeans = (List<PactBean>)this.getSqlMapClientTemplate().queryForList("getAdditiList",pageBean);
			pageBean.setTotalSize(String.valueOf(this.getSqlMapClientTemplate().queryForObject("getAdditiListSize")));
			return pactBeans;
		}
		

	/**
	 * 
	 * ���������� ��ô�ȷ�Ϻ�ͬ�б�
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����10:10:56
	 */
	@SuppressWarnings("unchecked")
	public List<PactBean> getConfList() {
		return this.getSqlMapClientTemplate().queryForList("getConfList");
	}
/**
 * 
 * ���������� ���º�ͬʵ��
 * @param pactBean
 * @author Ǭ֮��
 * @date 2012-5-29 ����07:33:33
 */
	public void updatePactBean(PactBean pactBean) {
		 this.getSqlMapClientTemplate().update("updatePactBean",pactBean);
	}
}
