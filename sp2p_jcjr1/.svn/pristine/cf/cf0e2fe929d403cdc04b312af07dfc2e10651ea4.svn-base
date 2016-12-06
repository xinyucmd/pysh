package com.sp2p.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;

public class CacheRecommendUtils {

	public static Log log = LogFactory.getLog(CacheRecommendUtils.class);
	private static CacheRecommendUtils cru = null;
	
	
	private List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
	
	private Map<String,Map<String,Object>> sortMap = new HashMap<String, Map<String,Object>>();
	public Map<String, Map<String, Object>> getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map<String, Map<String, Object>> sortMap) {
		this.sortMap = sortMap;
	}

	public List<Map<String, Object>> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Map<String, Object>> orderList) {
		this.orderList = orderList;
	}
	
	public static CacheRecommendUtils getInstance(){
		if(cru == null){
			cru = new CacheRecommendUtils();
		}
		
		return cru;
	}
	
	public void add(String key,Map<String,Object> value){
		if(value != null){
			AmountUtil au = new AmountUtil();
			value.put("amountTotal",au.getDf_two().format(
					Convert.strToDouble(String.valueOf(value.get("amountTotal")),0)
					));
			value.put("myAmountTotal",au.getDf_two().format(
					Convert.strToDouble(String.valueOf(value.get("myAmountTotal")),0)
					));
			value.put("friendAmountTotal",au.getDf_two().format(
					Convert.strToDouble(String.valueOf(value.get("friendAmountTotal")),0)
					));
			sortMap.put(key,value);
			
			// 排序
			orderMap(null);
		}
	}
	
	/**
	 * 添加所有数据至MAP
	 * @param list
	 * @return
	 */
	public Map<String,Map<String,Object>> addAll(List<Map<String,Object>> list){
		if(list != null && list.size()>0){
			clearCache();
			for(Map<String,Object> map:list){
				AmountUtil au = new AmountUtil();
				map.put("amountTotal",au.getDf_two().format(
						Convert.strToDouble(String.valueOf(map.get("amountTotal")),0)
						));
				map.put("myAmountTotal",au.getDf_two().format(
						Convert.strToDouble(String.valueOf(map.get("myAmountTotal")),0)
						));
				map.put("friendAmountTotal",au.getDf_two().format(
						Convert.strToDouble(String.valueOf(map.get("friendAmountTotal")),0)
						));
				sortMap.put(String.valueOf(map.get("userId")), map);
			}
		}
		
		return sortMap;
	}
	
	
	public int modifyAmount(String key,String modifykey, double value){
		if(sortMap != null && sortMap.size()>0){
			if(!sortMap.containsKey(key)){
				return -1;// 没有该记录
			}else{
				Map<String,Object> temMap = sortMap.get(key);
				// 修改单项总额
				double newAmount = Convert.strToDouble(String.valueOf(temMap.get(modifykey)), 0)+value;
				temMap.put(modifykey,newAmount);
				
				// 修改总额
				double newAmountTotal  = Convert.strToDouble(String.valueOf(temMap.get("amountTotal")), 0)+value;
				temMap.put("amountTotal", newAmountTotal);
				
				AmountUtil au = new AmountUtil();
				temMap.put("amountTotal",au.getDf_two().format(
						Convert.strToDouble(String.valueOf(temMap.get("amountTotal")),0)
						));
				temMap.put("myAmountTotal",au.getDf_two().format(
						Convert.strToDouble(String.valueOf(temMap.get("myAmountTotal")),0)
						));
				temMap.put("friendAmountTotal",au.getDf_two().format(
						Convert.strToDouble(String.valueOf(temMap.get("friendAmountTotal")),0)
						));
				
				
				// 排序
				orderMap(null);
			}
		}else{
			log.info("没有修改数据");
			return -2;
		}
		
		return 1;
	}
	
	public List<Map<String,Object>> orderMap(List<Map<String,Object>> list){
		if(list == null){
			list = orderList;
		}
		if(sortMap==null || sortMap.size()<=0){
			// 添加
			sortMap = addAll(list);
		}
		
		if(sortMap != null){
			orderList = new ArrayList<Map<String,Object>>(); 
			for(String key:sortMap.keySet()){
				orderList.add(sortMap.get(key));
			}
			
			// 排序
			for(int i=0;i<orderList.size();i++){
				for(int j=0;j<orderList.size()-i-1;j++){
					Map<String,Object> map = orderList.get(j);
					Map<String,Object> mapBack = orderList.get(j+1);
					double temp = getAmountTotal(map);
					double tempj = getAmountTotal(mapBack);
					String iLastDate = String.valueOf(map.get("lastTime"));
					String jLastDate = String.valueOf(mapBack.get("lastTime"));
					if(temp<tempj){
						orderList.set(j+1, map);
						orderList.set(j, mapBack);
					}else if(temp==tempj){
						if(DateUtil.diffDate(DateUtil.strToDate(iLastDate), DateUtil.strToDate(jLastDate))<0){
							orderList.set(j+1, map);
							orderList.set(j, mapBack);
						}
					}
				}
			}
			
			// 追加名次
			int ranking = 1;
			for(Map<String,Object> map :orderList){ 
				map.put("ranking", ranking);
				ranking ++;
			}
		}else{
			log.info("暂无数据");
		}
		
		return orderList;
	}
	
	public Map<String,Object> getMap(String key){
		if(sortMap != null && sortMap.get(key)!= null){
			return sortMap.get(key);
		}else{
			log.info("没有数据");
		}
		
		return null;
	}
	
	public Map<String,Object> getOrderMap(String key){
		if(orderList != null && orderList.size()>0){
			for(Map<String,Object> map :orderList){
				log.debug(key+"==="+String.valueOf(map.get("userId")));
				if(key.equals(String.valueOf(map.get("userId")))){
					return map;
				}
			}
		}else{
			log.info("没有数据");
		}
		
		return null;
	}
	
	private double getAmountTotal(Map<String,Object> map){
		if(map != null){
			return Convert.strToDouble(String.valueOf(map.get("amountTotal")), 0);
		}
		return 0;
	}
	
	/**
	 * 清空缓存
	 */
	public void clearCache(){
		if(orderList != null && orderList.size()>0){
			orderList = new ArrayList<Map<String,Object>>();
		}
		if(sortMap != null && sortMap.size()>0){
			sortMap = new HashMap<String, Map<String,Object>>();
		}
	}
	
	
	public static void main(String[] args) {
		
		 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		 Map<String,Object> sortMap1 = new HashMap<String, Object>();
		 sortMap1.put("amountTotal", "200");
		 sortMap1.put("myAmountTotal", "100");
		 sortMap1.put("friendAmountTotal", "100");
		 sortMap1.put("userId", "1");
		 sortMap1.put("lastTime", "2014-12-03  19:05:30");
		 
		 Map<String,Object> sortMap2 = new HashMap<String, Object>();
		 sortMap2.put("amountTotal", "100");
		 sortMap2.put("myAmountTotal", "50");
		 sortMap2.put("friendAmountTotal", "50");
		 sortMap2.put("userId", "2");
		 sortMap2.put("lastTime", "2014-12-04  19:05:30");
		 
		 Map<String,Object> sortMap3 = new HashMap<String, Object>();
		 sortMap3.put("amountTotal", "100");
		 sortMap3.put("myAmountTotal", "200");
		 sortMap3.put("friendAmountTotal", "100");
		 sortMap3.put("userId", "3");
		 sortMap3.put("lastTime", "2014-12-03  18:05:30");
		 
		 Map<String,Object> sortMap4 = new HashMap<String, Object>();
		 sortMap4.put("amountTotal", "100");
		 sortMap4.put("myAmountTotal", "25");
		 sortMap4.put("friendAmountTotal", "25");
		 sortMap4.put("userId", "4");
		 sortMap4.put("lastTime", "2014-12-03  19:05:30");
		 
		 Map<String,Object> sortMap5 = new HashMap<String, Object>();
		 sortMap5.put("amountTotal", "200");
		 sortMap5.put("myAmountTotal", "25");
		 sortMap5.put("friendAmountTotal", "25");
		 sortMap5.put("userId", "5");
		 sortMap5.put("lastTime", "2014-12-03  10:05:30");
		 
		 Map<String,Object> sortMap6 = new HashMap<String, Object>();
		 sortMap6.put("amountTotal", "200");
		 sortMap6.put("myAmountTotal", "25");
		 sortMap6.put("friendAmountTotal", "25");
		 sortMap6.put("userId", "6");
		 sortMap6.put("lastTime", "2014-12-03  11:05:30");
		 
		 list.add(sortMap1);
		 list.add(sortMap2);
		 list.add(sortMap3);
		 list.add(sortMap4);
		 list.add(sortMap5);
		 list.add(sortMap6);
		 
		 
		 List<Map<String,Object>> orderList = CacheRecommendUtils.getInstance().orderMap(list);
		
		 
		 for(Map<String,Object> map :orderList){
			 System.out.println("ranking:"+map.get("ranking")+","+map.get("userId")+","+map.get("amountTotal")+","+map.get("lastTime"));
		 }
		 
	}
	
}
