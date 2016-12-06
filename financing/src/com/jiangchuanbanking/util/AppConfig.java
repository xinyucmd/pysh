package com.jiangchuanbanking.util;

/**
 * <p>Title: AppConfig.java</p>
 * <p>Description: to load the Application system config file</p>
 * @author xiongxiaoming
 * @version 1.0
 * @created Mar 17, 2011 6:36:15 PM
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AppConfig {
	/** 文件名称* */
	private static String filename = "config.properties";
	/**属性对象**/
	private static Properties prop = new Properties();
	/**是否已经加载**/
	private static AtomicBoolean loadFlag = new AtomicBoolean(false);

	/**
	 * 初始化
	 */
	public static synchronized void initiate() {
		try {
			InputStream is = AppConfig.class.getClassLoader()
					.getResourceAsStream(filename);
			prop.load(is);
			loadFlag.set(true);

		} catch (IOException e) {
			System.err.println("应用程序参数初始化失败, 错误原因: " + e.getMessage());
		} 
	}

	/**
	 * 应用程序URL
	 * 
	 * @return
	 */
	public static String getAppContextURL() {
		if (!loadFlag.get()) {
			initiate();
		}
		return (String) prop.getProperty("app.context.url").trim();
	}

	/**
	 * 润乾报表地址
	 * 
	 * @return
	 */
	public static String getReportURL() {
		if (!loadFlag.get()) {
			initiate();
		}
		return (String) prop.getProperty("report.url").trim();
	}
	/**
	 * 功能描述：返回菜单权限状态
	 * @return
	 */
	public static String getMenuType(){
		if(!loadFlag.get()){
			initiate();
		}
		return (String) prop.getProperty("menu.type").trim();
	}
	/**
	 * 
	 * @return
	 */
	public static String getRightType(){
		if(!loadFlag.get()){
			initiate();
		}
		return (String) prop.getProperty("right.type").trim();		
	}
}
