/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� ConsumerService.java
 * ������ com.dx.loan.consumer.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-28 ����04:44:18
 * @version V1.0
 */ 
package com.dx.loan.consumer.services.impl;

import com.dx.loan.consumer.bean.ConsBean;

/**
 * ������ ConsumerService
 * ������ �ͻ�ģ�������
 * @author Ǭ֮��
 * @date 2012-5-28 ����04:44:18
 *
 *
 */
public class ConsumerService {
	
	
	public ConsBean getConsumer(String cifNo){
		// 
		ConsBean consBean = new ConsBean();
		consBean.setCardNo("121212121212");
		consBean.setCardType("1");
		consBean.setCifName("����");
		consBean.setCifNo(cifNo);
		
		return consBean;
	}
	
	
	
	

}
