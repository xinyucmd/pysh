/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IPutOutServices.java
 * ������ com.dx.loan.putout.services
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-16 ����10:21:28
 * @version V1.0
 */ 
package com.dx.loan.putout.services;


import com.dx.common.bean.PageBean;
import com.dx.loan.putout.bean.PactBean;



/**
 * ������ IPutOutServices
 * ������  �ſ�ҵ��ӿ�
 * @author Ǭ֮��
 * @date 2012-5-16 ����10:21:28
 *
 *
 */
public interface IPutOutServices {
	/**
	 * 
	 * ���������� ��ú�ͬ��¼�б�
	 * @return
	 * List<PactBean>
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����04:40:59
	 */
	public String  getAdditiList(PageBean pageBean);
	
	/**
	 * 
	 * ���������� ���ݺ�ͬ�Ż�ú�ͬʵ��
	 * @return
	 * PactBean
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����03:57:09
	 */
	public PactBean getPactById(String pactNo);
	
	/**
	 * 
	 * ���������� ���º�ͬʵ��
	 * @param pactBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����07:53:01
	 */
	public void updatePactBean(PactBean pactBean);
	
	

}
