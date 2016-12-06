/**
 * 
 */
package com.sp2p.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 *  JSON工具类
 * @author David‎-RYE
 */
public class JSONUtil {
	
	public static Map<String,String> strToMap(String str){
		Map<String,String> result = new HashMap<String, String>();
		
		if(StringUtils.isNotBlank(str)){
			JSONObject jo = JSONObject.fromObject(str);
			for(Object key:jo.keySet()){
				result.put(String.valueOf(key), String.valueOf(jo.get(key)));
			}
		}
		return result;
	}
	
	public static String objectToJosn(Object o){
		if(o!=null){
			return JSONObject.fromObject(o).toString();
		}
		return null;
	}
}
