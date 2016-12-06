/**
 * Copyright (C) DXHM 版权所有

 * 文件名 DateUtil.java
 * 包名 com.dx.common.demoUtil
 * 说明 处理日期的函数

 * 作者 rjq
 * 时间 May 7, 2012 9:54:15 AM
 * 版本 V1.0
 */
package com.dx.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;


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
	 * 日期格式 默认：yyyyMMdd
	 */
	private static String pattern = "yyyyMMdd";
	/**
	 * 当前操作系统日期 Calendar
	 */
	private static Calendar calendar = new GregorianCalendar(TimeZone
			.getDefault());
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
	public static synchronized String addByDay(String datestr, int days, String formatStr) {
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
	 * @return
	 */
	public static int[] getMonthsAndDays(String d1,String d2) {
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
		String begin = "2013-12-01";
		int i=0;
		for(i=1;i<32;++i){
			begin = addByDay(begin,1,DateUtil.DATE_FORMAT_);
			System.out.println(begin);
		}
	}
	/**
	 * 
	 * 功能描述：获得给定日期与系统当前日期之间的天数
	 * 
	 * @param strdate
	 *            给定的日期字符串
	 * @return long 天数
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static long getDays(String strdate) {
		Calendar cal = parseCalendar(strdate);
		Calendar cal1 = parseCalendar(getDate());
		long millis = Math.abs(cal.getTimeInMillis() - cal1.getTimeInMillis());
		return millis / (24 * 60 * 60 * 1000);
	}
	/**
	 * 功能描述：把字符串转换为Calendar
	 * 
	 * @param strdate
	 *            预转换的字符串
	 * @return Calendar
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static Calendar parseCalendar(String strdate) {
		if (isDateStr(strdate)) {
			int year = Integer.parseInt(strdate.substring(0, 4));
			int month = Integer.parseInt(strdate.substring(4, 6)) - 1;
			int day = Integer.parseInt(strdate.substring(6, 8));
			return new GregorianCalendar(year, month, day);
		} else {
			return null;
		}
	}
	/**
	 * 功能描述：获取系统当前日期---年月日 （格式：yyyymmdd）
	 * 
	 * @return String 年月日
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = calendar.getTime();
		return format.format(date);
	}
	/**
	 * 功能描述：判断字符串是否可以转换为日期型 是：true，否：false
	 * 
	 * @param strdate
	 *            预转换字符串
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static boolean isDateStr(String strdate) {
		if (strdate.length() != 8) {
			return false;
		}

		String reg = "^(\\d{4})((0([1-9]{1}))|(1[012]))((0[1-9]{1})|([1-2]([0-9]{1}))|(3[0|1]))$";

		if (Pattern.matches(reg, strdate)) {
			return getDaysOfMonth(strdate) >= Integer.parseInt(strdate
					.substring(6, 8));
		} else {
			return false;
		}
	}
	/**
	 * 功能描述：获取某一月份的天数
	 * 
	 * @param strdate
	 *            日期 格式：yyyymmdd 或 yyyymm
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static int getDaysOfMonth(String strdate) {
		int m = Integer.parseInt(strdate.substring(4, 6));
		switch (m) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (isLeapYear(strdate)) {
				return 29;
			} else {
				return 28;
			}
		default:
			return 0;
		}
	}
	/**
	 * 
	 * 功能描述：获得给定的两个日期之间相差的天数（日期不分前后）
	 * 
	 * @param fromdate
	 *            日期字符串 格式：yyyymmdd
	 * @param todate
	 *            日期字符串 格式：yyyymmdd
	 * @return long
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static long getDaysBetween(String fromdate, String todate) {
		Calendar from = parseCalendar(fromdate);
		Calendar to = parseCalendar(todate);
		long millis = Math.abs(from.getTimeInMillis() - to.getTimeInMillis());
		return millis / (24 * 60 * 60 * 1000);
	}
	/**
	 * 
	 * 功能描述：计算在当前系统日期增加或减少 n 天后的日期
	 * 
	 * @param days
	 *            增加或减少的天数，正数增加，反之减少
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @修改日志：
	 */
	public static String addByDay(int days) {
		calendar.add(Calendar.DATE, days);
		return getDate();
	}
	/**
	 * 
	 * 功能描述：计算在给定的日期加上或减去 n 天后的日期
	 * 
	 * @param datestr
	 *            给定的日期
	 * @param days
	 *            正数增加，反之减少
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @修改日志：
	 */
	public static String addByDay(String datestr, int days) {
		Calendar cal = parseCalendar(datestr);
		cal.add(Calendar.DATE, days);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = cal.getTime();
		return format.format(date);
	}
}
