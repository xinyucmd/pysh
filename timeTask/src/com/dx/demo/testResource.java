/**
 * Copyright (C) DXHM 版权所有
 * @文件名 testResource.java
 * @包名 com.dx.demo
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-7 下午01:51:48
 * @版本 V1.0
 */ 
package com.dx.demo;

import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @类名 testResource
 * @描述 TODO(用途说明)
 * @作者 luanhaowei
 * @日期 2012-5-7 下午01:51:48
 * ========修改日志=======
 *
 */
public class testResource {
	
	public static void main(String[] args) throws Throwable {    
        // ① jdbc.properties 是位于类路径下的文件
        Properties props = PropertiesLoaderUtils.loadAllProperties("resources/mysql.properties"); 
        System.out.println(props.getProperty("jdbc.driverClassName")); 
    } 

	

}
