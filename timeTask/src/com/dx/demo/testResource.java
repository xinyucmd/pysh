/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� testResource.java
 * @���� com.dx.demo
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-7 ����01:51:48
 * @�汾 V1.0
 */ 
package com.dx.demo;

import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @���� testResource
 * @���� TODO(��;˵��)
 * @���� luanhaowei
 * @���� 2012-5-7 ����01:51:48
 * ========�޸���־=======
 *
 */
public class testResource {
	
	public static void main(String[] args) throws Throwable {    
        // �� jdbc.properties ��λ����·���µ��ļ�
        Properties props = PropertiesLoaderUtils.loadAllProperties("resources/mysql.properties"); 
        System.out.println(props.getProperty("jdbc.driverClassName")); 
    } 

	

}
