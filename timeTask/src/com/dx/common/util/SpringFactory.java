/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SpringFactory.java
 * 包名： com.dx.common.util
 * 说明：
 * @author 乾之轩
 * @date 2012-5-17 上午09:38:46
 * @version V1.0
 */ 
package com.dx.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类名： SpringFactory
 * 描述： 获得spring的工厂方便在非spring管理的bean中调用spring管理的bean
 * @author 乾之轩
 * @date 2012-5-17 上午09:38:46
 *
 *
 */
public class SpringFactory implements ApplicationContextAware   {
	private static ApplicationContext context ;
	/**
	 * spring 启动后将ApplicationContext进行初始化
	 */
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
			this.context =context;  
			 System.out.println("加载 SpringFactory 工厂类");
	}
	/**
	 * 
	 * 方法描述：根据名称 获得spring管理的bean
	 * @param <T>
	 * @param name
	 * @return
	 * T
	 * @author 乾之轩
	 * @date 2012-5-17 上午10:20:46
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		return (T)context.getBean(name);
	}
}
