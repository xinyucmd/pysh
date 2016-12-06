/**
 * Copyright (C) DXHM ��Ȩ����

 * �ļ��� DateUtil.java
 * ���� com.dx.common.demoUtil
 * ˵�� �������ڵĺ���

 * ���� rjq
 * ʱ�� May 7, 2012 9:54:15 AM
 * �汾 V1.0
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
 * ���� DateUtil
 * ���� �������ڵĺ���

 * ���� rjq
 * ���� May 7, 2012 9:54:15 AM ========�޸���־=======
 *
 */
public class DateUtil {

	public static String DATE_FORMAT = "YYYYMMDD";
	public static String DATE_FORMAT_ = "YYYY-MM-DD";
	public static String DATE_TIME_FORMAT_ = "YYYY-MM-DD hh:mm:ss";
	public static String TIME_FORMAT = "hh:mm:ss";
	public static DayOverflow monBottom = DayOverflow.LastDay;
	/**
	 * ���ڸ�ʽ Ĭ�ϣ�yyyyMMdd
	 */
	private static String pattern = "yyyyMMdd";
	/**
	 * ��ǰ����ϵͳ���� Calendar
	 */
	private static Calendar calendar = new GregorianCalendar(TimeZone
			.getDefault());
	/**
	 *
	 * ���� getCurtDateTime
	 * ���� ��ȡ��ǰʱ��
	 * ���� rjq
	 * ʱ�� May 7, 2012 10:02:51 AM
	 * @param formatStr  ���ڸ�ʽ����
	 * @return
	 */
	public static String getCurtDateTime(String formatStr) {
		DateTime now = DateTime.now(TimeZone.getDefault());
		String result = now.format(formatStr);
		return result;
	}

	
	/**
	 *
	 * ���� addByDay
	 * ����  ָ�����ڼ� days ��֮�������
	 * ���� rjq
	 * ʱ�� May 7, 2012 10:44:39 AM
	 * @param datestr ָ������   ��ʽ��YYYY-MM-DD
	 * @param days  ���ӵ�����

	 * @param formatStr  ���ڸ�ʽ
	 * @return
	 */
	public static synchronized String addByDay(String datestr, int days, String formatStr) {
		DateTime dTime = new DateTime(datestr);
		DateTime tmpDte = dTime.plusDays(days);
		return tmpDte.format(formatStr);
	}
	
	
	
	/**
	 *
	 * ���� addByMonDay
	 * ���� ���ڼ�mons ���£���days��������

	 * ���� rjq
	 * ʱ�� May 7, 2012 11:21:32 AM
	 * @param datestr  ָ������
	 * @param mons  ������

	 * @param days  ������

	 * @param formatStr  ���ڸ�ʽ
	 * @return
	 */
	public static String addByMonDay(String datestr, int mons, int days, String formatStr) {
		DateTime dTime = new DateTime(datestr);
		DateTime tmpDte = dTime.plus(0, mons, days, 0, 0, 0, monBottom);// ������·ݵ����һ���ȡ���һ��

		return tmpDte.format(formatStr);
	}
	/**
	 * 
	 * ���������� ���һ�����ڼ�ȥ������������
	 * @param date ����
	 * @param days Ҫ��ȥ������
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-1 ����02:57:14
	 */
	public static String  subtDays(String date ,String days){
		int tempDay = Integer.parseInt(days);
		DateTime dTime = new DateTime(date);
		dTime = dTime.minusDays(tempDay);
		return dTime.toString() ;
	}
	
	/**
	 *
	 * ���� getBetweenDays
	 * ���� ��ȡ��������֮�䣨d2-d1��������
	 * ���� rjq
	 * ʱ�� May 7, 2012 11:04:43 AM
	 * @param d1 ����   ��ʽ��YYYY-MM-DD
	 * @param d2 ����   ��ʽ��YYYY-MM-DD
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
	 * @���� getBetweenDays
	 * @���� ��ȡ��������֮�䣨d2-d1��������
	 * @���� rjq
	 * @ʱ�� May 7, 2012 11:04:43 AM
	 * @param d1
	 *            ���� ��ʽ��YYYY-MM-DD
	 * @param d2
	 *            ���� ��ʽ��YYYY-MM-DD
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
	 * @���� getDaysToToday
	 * @���� ��ȡָ�����ڵ���������֮�������
	 * @���� rjq
	 * @ʱ�� May 7, 2012 11:28:06 AM
	 * @param dateStr ����   ��ʽ��YYYY-MM-DD
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
	 * @���� getMonthsAndDays
	 * @���� ��ȡָ���������ڼ��������������
	 * @���� rjq
	 * @ʱ�� May 7, 2012 12:00:24 PM
	 * @param d1  ����   ��ʽ��YYYY-MM-DD
	 * @param d2  ����   ��ʽ��YYYY-MM-DD
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
	 * ���� isLeapYear
	 * ���� �ж��Ƿ�������

	 * ���� rjq
	 * ʱ�� May 7, 2012 2:31:18 PM
	 * @param strdate   ����   ��ʽ��YYYY-MM-DD
	 * @return
	 */
	public static boolean isLeapYear(String strdate) {
		DateTime dt1 = new DateTime(strdate);
		return dt1.isLeapYear();
	}
	/**
	 * 
	 * ���������� ����������ڵ��·�
	 * @param date
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����02:25:36
	 */
	public  static int  getMonth(String date){
		DateTime dateTime = new DateTime(date);
		return dateTime.getMonth();
	}
	
/**
 * 
 * ���������� ����������ڵ���
 * @param date
 * @return
 * int
 * @author Ǭ֮��
 * @date 2012-5-19 ����03:03:42
 */
	public static int getYear(String date){
		DateTime dateTime = new DateTime(date);
		return dateTime.getYear();
	}
	
	/**
	 * 
	 * ���������� ����������ڵ���
	 * @param date
	 * @return
	 * int
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����03:41:04
	 */
	public static int getDay(String date){
		DateTime dateTime = new DateTime(date);
		return dateTime.getDay();
	}
	/**
	 * 
	 * ����������  �ж�date�Ƿ���ڿ�ʼ���ںͽ�������֮��(ȡͷ��ȡβ)
	 * @param date
	 * @param beginDate
	 * @param endDate
	 * @return
	 * boolean
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����11:48:36
	 */
	public static boolean isBetween(String date,String beginDate,String endDate){
		
		DateTime dateTime = new DateTime(date);
		DateTime beginDateTime = new DateTime(beginDate);
		DateTime endDateTime = new DateTime(endDate);
		return dateTime.gteq(beginDateTime) && dateTime.lt(endDateTime);
	}
	
	/**
	 * 
	 * �����������ж�2�����ڵĴ�С(endDate���ڵ��ڿ�ʼ���ڷ���true,��������Ż�false) 
	 * @param begDate
	 * @param endDate
	 * @return
	 * boolean
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����02:47:02
	 */
	public static boolean gteq(String begDate,String endDate) {
		DateTime beginDateTime = new DateTime(begDate);
		DateTime endDateTime = new DateTime(endDate);
		return endDateTime.gteq(beginDateTime);
	}
	
	/**
	 * 
	 * �����������ж�2�����ڵĴ�С(endDate���ڿ�ʼ���ڷ���true,��������Ż�false) 
	 * @param begDate
	 * @param endDate
	 * @return
	 * boolean
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����02:47:02
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
	 * ������������ø���������ϵͳ��ǰ����֮�������
	 * 
	 * @param strdate
	 *            �����������ַ���
	 * @return long ����
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static long getDays(String strdate) {
		Calendar cal = parseCalendar(strdate);
		Calendar cal1 = parseCalendar(getDate());
		long millis = Math.abs(cal.getTimeInMillis() - cal1.getTimeInMillis());
		return millis / (24 * 60 * 60 * 1000);
	}
	/**
	 * �������������ַ���ת��ΪCalendar
	 * 
	 * @param strdate
	 *            Ԥת�����ַ���
	 * @return Calendar
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * ������������ȡϵͳ��ǰ����---������ ����ʽ��yyyymmdd��
	 * 
	 * @return String ������
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = calendar.getTime();
		return format.format(date);
	}
	/**
	 * �����������ж��ַ����Ƿ����ת��Ϊ������ �ǣ�true����false
	 * 
	 * @param strdate
	 *            Ԥת���ַ���
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * ������������ȡĳһ�·ݵ�����
	 * 
	 * @param strdate
	 *            ���� ��ʽ��yyyymmdd �� yyyymm
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * ������������ø�������������֮���������������ڲ���ǰ��
	 * 
	 * @param fromdate
	 *            �����ַ��� ��ʽ��yyyymmdd
	 * @param todate
	 *            �����ַ��� ��ʽ��yyyymmdd
	 * @return long
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static long getDaysBetween(String fromdate, String todate) {
		Calendar from = parseCalendar(fromdate);
		Calendar to = parseCalendar(todate);
		long millis = Math.abs(from.getTimeInMillis() - to.getTimeInMillis());
		return millis / (24 * 60 * 60 * 1000);
	}
	/**
	 * 
	 * ���������������ڵ�ǰϵͳ�������ӻ���� n ��������
	 * 
	 * @param days
	 *            ���ӻ���ٵ��������������ӣ���֮����
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @�޸���־��
	 */
	public static String addByDay(int days) {
		calendar.add(Calendar.DATE, days);
		return getDate();
	}
	/**
	 * 
	 * ���������������ڸ��������ڼ��ϻ��ȥ n ��������
	 * 
	 * @param datestr
	 *            ����������
	 * @param days
	 *            �������ӣ���֮����
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @�޸���־��
	 */
	public static String addByDay(String datestr, int days) {
		Calendar cal = parseCalendar(datestr);
		cal.add(Calendar.DATE, days);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = cal.getTime();
		return format.format(date);
	}
}
