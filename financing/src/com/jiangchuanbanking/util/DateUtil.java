/**
 * Copyright (C) DXHM 版权所有

 * 文件名 DateUtil.java
 * 包名 com.dx.common.demoUtil
 * 说明 处理日期的函数

 * 作者 rjq
 * 时间 May 7, 2012 9:54:15 AM
 * 版本 V1.0
 */
package com.jiangchuanbanking.util;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;

/**
 * 类名 DateUtil
 * 描述 处理日期的函数

 * 作者 rjq
 * 日期 May 7, 2012 9:54:15 AM ========修改日志=======
 *
 */
public class DateUtil {

	public static String DATE_FORMAT = "YYYYMMDD";
	public static String DATE_FORMAT_ = "YYYY-MM-DD";
	public static String DATE_TIME_FORMAT_ = "YYYY-MM-DD hh:mm:ss";
	public static String TIME_FORMAT = "hh:mm:ss";
	public static DayOverflow monBottom = DayOverflow.LastDay;

	/**
	 *
	 * 名称 getCurtDateTime
	 * 描述 获取当前时间
	 * 作者 rjq
	 * 时间 May 7, 2012 10:02:51 AM
	 * @param formatStr  日期格式化串
	 * @return
	 */
	public static String getCurtDateTime(String formatStr) {
		DateTime now = DateTime.now(TimeZone.getDefault());
		String result = now.format(formatStr);
		return result;
	}

	
	/**
	 *
	 * 名称 addByDay
	 * 描述  指定日期加 days 天之后的日期
	 * 作者 rjq
	 * 时间 May 7, 2012 10:44:39 AM
	 * @param datestr 指定日期   格式：YYYY-MM-DD
	 * @param days  增加的天数

	 * @param formatStr  日期格式
	 * @return
	 */
	public static String addByDay(String datestr, int days, String formatStr) {
		DateTime dTime = new DateTime(datestr);
		DateTime tmpDte = dTime.plusDays(days);
		return tmpDte.format(formatStr);
	}
	/**
	 *
	 * 名称 addByMonDay
	 * 描述 日期加mons 个月，加days天后的日期

	 * 作者 rjq
	 * 时间 May 7, 2012 11:21:32 AM
	 * @param datestr  指定日期
	 * @param mons  增加月

	 * @param days  增加日

	 * @param formatStr  日期格式
	 * @return
	 */
	public static String addByMonDay(String datestr, int mons, int days, String formatStr) {
		DateTime dTime = new DateTime(datestr);
		DateTime tmpDte = dTime.plus(0, mons, days, 0, 0, 0, monBottom);// 如果是月份的最后一天就取最后一天

		return tmpDte.format(formatStr);
	}
	/**
	 * 
	 * 方法描述： 获得一个日期减去若干天后的日期
	 * @param date 日期
	 * @param days 要减去的天数
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-1 下午02:57:14
	 */
	public static String  subtDays(String date ,String days){
		int tempDay = Integer.parseInt(days);
		DateTime dTime = new DateTime(date);
		dTime = dTime.minusDays(tempDay);
		return dTime.toString() ;
	}
	
	public static String subtMonths(String date ,String months, String formatStr){
		Integer tempM = Integer.parseInt(months);
		DateTime dTime = new DateTime(date);
		dTime = dTime.minus(0, tempM, 0, 0, 0, 0, monBottom);
		return dTime.format(formatStr);
	}
	
	/**
	 *
	 * 名称 getBetweenDays
	 * 描述 获取两个日期之间（d2-d1）的天数
	 * 作者 rjq
	 * 时间 May 7, 2012 11:04:43 AM
	 * @param d1 日期   格式：YYYY-MM-DD
	 * @param d2 日期   格式：YYYY-MM-DD
	 * @return
	 */
	public static int getBetweenDays(String d1, String d2) {
	
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		int result = dt1.numDaysFrom(dt2);
		return result;
	}

	/**
	 *
	 * @名称 getBetweenDays
	 * @描述 获取两个日期之间（d2-d1）的天数
	 * @作者 rjq
	 * @时间 May 7, 2012 11:04:43 AM
	 * @param d1
	 *            日期 格式：YYYY-MM-DD
	 * @param d2
	 *            日期 格式：YYYY-MM-DD
	 * @return
	 */
	public static long getBetweenMonths(String d1, String d2) {
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		long months = dt2.getMonth() - dt1.getMonth();
		long years = dt2.getYear() - dt1.getYear();
		return years * 12 + months;
	}
	/**
	 *
	 * @名称 getDaysToToday
	 * @描述 获取指定日期到当期日期之间的天数
	 * @作者 rjq
	 * @时间 May 7, 2012 11:28:06 AM
	 * @param dateStr 日期   格式：YYYY-MM-DD
	 * @return
	 */
	public static int  getDaysToToday(String  dateStr)
	{
		DateTime dt1=DateTime.now(TimeZone.getDefault());
		DateTime dt2=new DateTime(dateStr);
	    int  result= dt2.numDaysFrom(dt1);
	    return result;
	}

	/**
	 *
	 * @名称 getMonthsAndDays
	 * @描述 获取指定两个日期间隔的月数和天数
	 * @作者 rjq
	 * @时间 May 7, 2012 12:00:24 PM
	 * @param d1  日期   格式：YYYY-MM-DD
	 * @param d2  日期   格式：YYYY-MM-DD
	 * @return "2012-12-30", "2013-01-30"
	 */
	
	
	public static int[] getMonthsAndDays(String d1,String d2) {
        int [] monthAndDays = new int[2]; 
		//public  static void getMonthsAndDays(String d1,String d2) {
		int days = 0;	
		int months = 0; 
		int yeras = 0;
		int smalYear = Integer.parseInt(d1.replace("-", "").substring(0,4)) ;
		String month = d1.replace("-", "").substring(4,6);
		if(month.startsWith("0")){
			month = month.replace("0", "");
		}
		int smalMonth=Integer.parseInt(month) ;
		String day = d1.replace("-", "").substring(6,8);
		if(day.startsWith("0")){
			day = day.replace("0", "");
		}
		int smalDay = Integer.parseInt(day)  ;
		
		
		
		int bigYear = Integer.parseInt(d2.replace("-", "").substring(0,4)) ;
		month = d2.replace("-", "").substring(4,6);
		if(month.startsWith("0")){
			month = month.replace("0", "");
		}
		int bigMonth = Integer.parseInt(month);
		 day = d2.replace("-", "").substring(6,8);
		if(day.startsWith("0")){
			day = day.replace("0", "");
		}
		int bigDay = Integer.parseInt(day);
		
		
		if (smalYear>bigYear) {
			int tempVal = bigYear;
			bigYear = smalYear;
			smalYear = tempVal;
			
			tempVal = bigMonth;
			bigMonth = smalMonth;
			smalMonth = tempVal;
			
			tempVal = bigDay;
			bigDay  =  smalDay;
			smalDay = tempVal;
		}
		
		 if (smalYear==bigYear &&  smalMonth>bigMonth) {
			int tempVal = bigYear;
			
			tempVal = bigMonth;
			bigMonth = smalMonth;
			smalMonth = tempVal;
			
			tempVal = bigDay;
			bigDay = smalDay;
			smalDay = tempVal;
			
		}
		 
		 
		 if(smalYear==bigYear && smalMonth==bigMonth && smalDay>bigDay){
			int tempVal = bigDay;
			bigDay = smalDay;
			smalDay = tempVal;
		} 
		
		
		
		
		
		if(bigDay<smalDay){
			int monthOfDays = 0;
			if(bigMonth==1){
				 monthOfDays = getDayOfMonth(bigYear,12);
			}else{
				 monthOfDays = getDayOfMonth(bigYear,bigMonth-1);
			}
			bigDay=bigDay+monthOfDays;
			bigMonth=bigMonth-1;
			days = bigDay-smalDay;
		}else{
			days = bigDay-smalDay;
		}
		
		if (bigMonth<smalMonth) {
			bigMonth = bigMonth+12;
			months=bigMonth-smalMonth;
			bigYear=bigYear-1;
		}else{
			months=bigMonth-smalMonth;
		}
		
		yeras = bigYear-smalYear;
		months = months+12*yeras;
		monthAndDays[0]=months;
		monthAndDays[1]= days;
		return monthAndDays;
		/**
		int[]result=new int[2];
		DateTime dt1=new DateTime(d1);
		DateTime dt2=new DateTime(d2);
		DateTime bigTime;
		DateTime smalTime;
		if(dt1.lteq(dt2))
		{
			bigTime=dt2;
			smalTime=dt1;
		}else {
			bigTime=dt1;
			smalTime=dt2;
		}

		DateTime tmpDt=smalTime;

		int monCnt=0;
		while(tmpDt.lteq(bigTime)){
			tmpDt = tmpDt.plus(0, 1, 0, 0, 0, 0, monBottom);
			
			tmpDt = new DateTime(tmpDt.format(DATE_FORMAT_));
			if(tmpDt.lteq(bigTime)){
				monCnt++;
			}else{
				tmpDt = tmpDt.minus(0, 1, 0, 0, 0, 0, monBottom);
				break;
			}
		}
		int  dayCnt=tmpDt.numDaysFrom(bigTime);
		result[0]=monCnt;
		result[1]=dayCnt;
		return  result;
		**/

	}
		
		public static int getDayOfMonth(int year,int month){
			String tempmonth = String.valueOf(month);
			if(month<10){
				tempmonth = "0"+tempmonth;
			}
			int days = 0;
			if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12 ){
				days =31;
			}else if (month==2) {
				if(isLeapYear(year+"-"+tempmonth+"-01")){
					days = 29;
				}else{
					days = 28;
				}
			}else {
				days = 30;
			}
			return days;
		}
		

	/**
	 *
	 * 名称 isLeapYear
	 * 描述 判断是否是闰年

	 * 作者 rjq
	 * 时间 May 7, 2012 2:31:18 PM
	 * @param strdate   日期   格式：YYYY-MM-DD
	 * @return
	 */
	public static boolean isLeapYear(String strdate) {
		DateTime dt1 = new DateTime(strdate);
		return dt1.isLeapYear();
	}
	/**
	 * 
	 * 方法描述： 获得日期所在的月份
	 * @param date
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 下午02:25:36
	 */
	public  static int  getMonth(String date){
		DateTime dateTime = new DateTime(date);
		return dateTime.getMonth();
	}
	
/**
 * 
 * 方法描述： 获得日期所在的年
 * @param date
 * @return
 * int
 * @author 乾之轩
 * @date 2012-5-19 下午03:03:42
 */
	public static int getYear(String date){
		DateTime dateTime = new DateTime(date);
		return dateTime.getYear();
	}
	
	/**
	 * 
	 * 方法描述： 获得所在日期的日
	 * @param date
	 * @return
	 * int
	 * @author 乾之轩
	 * @date 2012-5-19 下午03:41:04
	 */
	public static int getDay(String date){
		DateTime dateTime = new DateTime(date);
		return dateTime.getDay();
	}
	/**
	 * 
	 * 方法描述：  判断date是否介于开始日期和结束日期之间(取头不取尾)
	 * @param date
	 * @param beginDate
	 * @param endDate
	 * @return
	 * boolean
	 * @author 乾之轩
	 * @date 2012-5-21 上午11:48:36
	 */
	public static boolean isBetween(String date,String beginDate,String endDate){
		
		DateTime dateTime = new DateTime(date);
		DateTime beginDateTime = new DateTime(beginDate);
		DateTime endDateTime = new DateTime(endDate);
		return dateTime.gteq(beginDateTime) && dateTime.lt(endDateTime);
	}
	
	/**
	 * 
	 * 方法描述：判断2个日期的大小(endDate大于等于开始日期返回true,其他情况放回false) 
	 * @param begDate
	 * @param endDate
	 * @return
	 * boolean
	 * @author 乾之轩
	 * @date 2012-5-22 下午02:47:02
	 */
	public static boolean gteq(String begDate,String endDate) {
		DateTime beginDateTime = new DateTime(begDate);
		DateTime endDateTime = new DateTime(endDate);
		return endDateTime.gteq(beginDateTime);
	}
	
	/**
	 * 
	 * 方法描述：判断2个日期的大小(endDate大于开始日期返回true,其他情况放回false) 
	 * @param begDate
	 * @param endDate
	 * @return
	 * boolean
	 * @author 乾之轩
	 * @date 2012-5-22 下午02:47:02
	 */
	public static boolean gt(String begDate,String endDate) {
		DateTime beginDateTime = new DateTime(begDate);
		DateTime endDateTime = new DateTime(endDate);
		return endDateTime.gt(beginDateTime);
	}
	

	public static void main(String[] args) throws ParseException {
		System.out.println(getDay(("2012-12-03")));
		System.out.println(getMonth(("2012-12-03")));
	}

}
