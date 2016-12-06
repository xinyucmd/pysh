/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� CacheParm.java
 * ������ com.dx.common
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-5 ����07:25:10
 * @version V1.0
 */ 
package com.dx.common;

import java.util.HashMap;

/**
 * ������ CacheParm
 * �������������ʹ��
 * @author Ǭ֮��
 * @date 2012-6-5 ����07:25:10
 *
 *
 */
public class CacheParm {
	// ������ʱ����Ĳ���
	private static HashMap<String, Object>  cachePool= new HashMap<String, Object>();
	/**
	 * 
	 * ���������� ����Ҫ����Ķ�����뻺�����
	 * @param key
	 * @param object
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:30:42
	 */
	public static void push(String key,Object object){
		if(cachePool.containsKey(key)){
			remove(key);
			cachePool.put(key, object);
		}else{
			cachePool.put(key, object);
		}
	}

	/**
	 * 
	 * ���������� ����key�ӻ�������Ƴ�һ������ 
	 * @param key
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:32:37
	 */
	public static void remove(String key){
		cachePool.remove(key);
	}
	
/**
 * 	
 * ���������� ����  key�ӻ������ȡ������
 * @param <T>
 * @param key
 * @return
 * T
 * @author Ǭ֮��
 * @date 2012-6-5 ����07:39:12
 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T) cachePool.get(key);
	}
	

}
