/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� SpringFactory.java
 * ������ com.dx.common.util
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-17 ����09:38:46
 * @version V1.0
 */ 
package com.dx.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ������ SpringFactory
 * ������ ���spring�Ĺ��������ڷ�spring�����bean�е���spring�����bean
 * @author Ǭ֮��
 * @date 2012-5-17 ����09:38:46
 *
 *
 */
public class SpringFactory implements ApplicationContextAware   {
	private static ApplicationContext context ;
	/**
	 * spring ������ApplicationContext���г�ʼ��
	 */
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
			this.context =context;  
			 System.out.println("���� SpringFactory ������");
	}
	/**
	 * 
	 * ������������������ ���spring�����bean
	 * @param <T>
	 * @param name
	 * @return
	 * T
	 * @author Ǭ֮��
	 * @date 2012-5-17 ����10:20:46
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		return (T)context.getBean(name);
	}
}
