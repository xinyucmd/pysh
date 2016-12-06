/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SystemDateBean.java
 * 包名： com.dx.back.systemdate.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-6-12 上午10:26:45
 * @version V1.0
 */ 
package com.dx.back.systemdate.bean;
/**
 * 类名： SystemDateBean
 * 描述：
 * @author 乾之轩
 * @date 2012-6-12 上午10:26:45
 *
 *
 */
public class SystemDateBean {
  // 上个交易日期 	
  private  String lastDate ; 	
  // 当前交易日期
  private  String nowDate ;
public String getLastDate() {
	return lastDate;
}
public void setLastDate(String lastDate) {
	this.lastDate = lastDate;
}
public String getNowDate() {
	return nowDate;
}
public void setNowDate(String nowDate) {
	this.nowDate = nowDate;
} 	

}
