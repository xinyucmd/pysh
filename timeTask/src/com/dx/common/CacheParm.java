/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CacheParm.java
 * 包名： com.dx.common
 * 说明：
 * @author 乾之轩
 * @date 2012-6-5 下午07:25:10
 * @version V1.0
 */ 
package com.dx.common;

import java.util.HashMap;

/**
 * 类名： CacheParm
 * 描述：缓存参数使用
 * @author 乾之轩
 * @date 2012-6-5 下午07:25:10
 *
 *
 */
public class CacheParm {
	// 保存临时缓存的参数
	private static HashMap<String, Object>  cachePool= new HashMap<String, Object>();
	/**
	 * 
	 * 方法描述： 将需要缓存的对象放入缓存池中
	 * @param key
	 * @param object
	 * void
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:30:42
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
	 * 方法描述： 根据key从缓存池中移除一个对象 
	 * @param key
	 * void
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:32:37
	 */
	public static void remove(String key){
		cachePool.remove(key);
	}
	
/**
 * 	
 * 方法描述： 根据  key从缓存池中取出数据
 * @param <T>
 * @param key
 * @return
 * T
 * @author 乾之轩
 * @date 2012-6-5 下午07:39:12
 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T) cachePool.get(key);
	}
	

}
