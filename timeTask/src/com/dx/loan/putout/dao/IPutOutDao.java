/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IPutOutDao.java
 * ������ com.dx.loan.putout.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-15 ����04:54:50
 * @version V1.0
 */ 
package com.dx.loan.putout.dao;


import java.util.List;

import com.dx.common.bean.PageBean;
import com.dx.loan.putout.bean.PactBean;


/**
 * ������ IPutOutDao
 * ������ �ſ�ӿ�
 * @author Ǭ֮��
 * @date 2012-5-15 ����04:54:50
 *
 *
 */
public interface IPutOutDao {
	/**
	 * 
	 * ���������� �����ͬʵ��
	 * @param pactBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����02:59:50
	 */
	public  void savePactBean(PactBean pactBean);
	
	/**
	 * 
	 * ��������  ���ݺ�ͬ�Ż�ú�ͬʵ��
	 * @param pactNo
	 * @return
	 * PactBean
	 * @author sll
	 * @date 2012-5-10 11:26:33
	 */
	public PactBean getPactById(String pactNo);
	/**
	 * 
	 * ���������� �����Ҫ��¼�ĺ�ͬ�б� 
	 * @return
	 * List<PactBean>
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����10:20:13
	 */
	public List<PactBean> getAdditiList(PageBean PageBean);
	
	
	/**
	 * 
	 * ���������� ��ô�ȷ�Ϻ�ͬ�б�
	 * @return
	 * List<PactBean>
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����10:05:48
	 */
	public List<PactBean> getConfList();
	
	/**
	 * 
	 * ���������� ���º�ͬʵ��
	 * @param pactBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����07:32:16
	 */
	public void updatePactBean(PactBean pactBean);
	
	

}
