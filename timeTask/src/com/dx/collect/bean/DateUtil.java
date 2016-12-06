package com.dx.collect.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;


/**
 * 功能描述：日期处理工具类（基于Calendar） 主要功能：日期校验；获取系统当前日期（可自定义系统日期）；判断闰年；获取连个日期之间的天数，月数；
 * 判定日期的前后；将字符串转换为Date或Calendar等...
 * 日期格式默认：yyyyMMdd
 * @author WangShanfang
 * @date 2008-11-21
 * @see null
 * @修改日志：1.0 
 */
public class DateUtil extends Object {

	/**
	 * 当前操作系统日期 Calendar
	 */
	private static Calendar calendar = new GregorianCalendar(TimeZone
			.getDefault());
	/**
	 * 日期格式 默认：yyyyMMdd
	 */
	private static String pattern = "yyyyMMdd";
	/**
	 * 时间格式 默认：HH:mm:ss
	 */
	private static String timePattern = "HH:mm:ss";

	/**
	 * 年
	 */
	private static int year = 0;
	/**
	 * 月
	 */
	private static int month = 0;
	/**
	 * 日
	 */
	private static int day = 0;
	/**
	 * 时
	 */
	private static int hour = 0;
	/**
	 * 分
	 */
	private static int minute = 0;
	/**
	 * 秒
	 */
	private static int second = 0;

	/**
	 * 静态初始化（默认系统当前日期和时间）
	 */
	static {
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
	}

	/**
	 * 构造方法
	 */
	private DateUtil() {
		// Do Nothing
	}

	/**
	 * 功能描述：自定义系统时间。（谨慎使用）
	 * 
	 * @param strdate
	 *            自定义日期字符串，格式：yyyymmdd
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static void setSysDate(String strdate) {
		if (isDateStr(strdate)) {
			calendar = parseCalendar(strdate);

			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
			day = calendar.get(Calendar.DAY_OF_MONTH);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);
		}
	}

	/**
	 * 功能描述： 初始化系统日期(当前系统日期)调用setSysDate()后会用到次方法重新初始化系统日期时间
	 * 			为当前日期时间
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static void initSys() {
		calendar = new GregorianCalendar(TimeZone.getDefault());

		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：获取系统当前日期---年
	 * 
	 * @return int 年
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static int getYear() {
		return year;
	}

	/**
	 * 功能描述：获取系统当前日期---年
	 * 
	 * @return String 年
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getStrYear() {
		return String.valueOf(year);
	}

	/**
	 * 功能描述：获取系统当前日期---月
	 * 
	 * @return int 月
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static int getMonth() {
		return month;
	}

	/**
	 * 功能描述：获取系统当前日期---月
	 * 
	 * @return String 月
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getStrMonth() {
		return month >= 10 ? String.valueOf(month) : "0"
				+ String.valueOf(month);
	}

	/**
	 * 功能描述：获取系统当前日期---日
	 * 
	 * @return int 日
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static int getDay() {
		return day;
	}

	/**
	 * 功能描述：获取系统当前日期---日
	 * 
	 * @return String 日
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getStrDay() {
		return day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
	}
	/**
	 * 
	 * 功能描述：获取系统时间--小时
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @修改日志：1.0
	 */
	public static int getHour(){
		return hour;
	}
	/**
	 * 
	 * 功能描述：获取系统时间--分钟
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @修改日志：
	 */
	public static int getMinute(){
		return minute;
	}
	/**
	 * 
	 * 功能描述：获取系统时间--秒
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @修改日志：
	 */
	public static int getSecond(){
		return second;
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
	 * 功能描述：获取系统当前日期---年月日 （格式：yyyymmdd）
	 * 
	 * @return String 时分秒
	 * @author haoxiaofeng
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * 功能描述：根据预定格式取系统当前日期---年月日
	 * 
	 * @param ptn
	 *            日期格式
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getDate(String ptn) {
		SimpleDateFormat format = new SimpleDateFormat(ptn);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * 功能描述：获取系统时间 格式：yyyymmdd hh:mm:ss
	 * 
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static String getDateTime() {
		SimpleDateFormat format = new SimpleDateFormat(pattern + " "
				+ timePattern);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * 
	 * 功能描述：获取预定义格式的系统时间
	 * 
	 * @param datePtn
	 *            日期格式
	 * @param timePtn
	 *            时间格式
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @修改日志：1.0
	 */
	public static String getDateTime(String datePtn, String timePtn) {
		SimpleDateFormat format = new SimpleDateFormat(datePtn + " " + timePtn);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * 功能描述：判断给定日期（格式yyyymmdd）是否在系统日期之前，是（或等于）：true，否：false
	 * 
	 * @param strdate
	 *            给定日期
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static boolean isBefore(String strdate) {
		Calendar cal = parseCalendar(strdate);
		return cal.before(calendar);
	}
	/**
	 * 
	 * 功能描述：判断给定的两个日期的前后。strdate1在strdate2之前（或同一天），返回true，反之，false
	 * @param strdate1	日期1
	 * @param strdate2	日期2
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-25
	 * @修改日志：1.0
	 */
	public static boolean isBefore(String strdate1, String strdate2){
		Calendar cal1 = parseCalendar(strdate1);
		Calendar cal2 = parseCalendar(strdate2);
		return cal1.before(cal2);
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
	 * 功能描述：获得给定的两个日期之间相差的天数（有负数）
	 * 
	 * @param fromdate
	 *            日期字符串 格式：yyyymmdd
	 * @param todate
	 *            日期字符串 格式：yyyymmdd
	 * @return long
	 * @author huochuanxi
	 * @date 2011-11-15
	 * @修改日志：
	 */
	public static long getDaysBetweenhuo(String fromdate, String todate) {
		Calendar from = parseCalendar(fromdate);
		Calendar to = parseCalendar(todate);
		long millis = Math.abs(from.getTimeInMillis() - to.getTimeInMillis()); //round
		if(from.getTimeInMillis() < to.getTimeInMillis()){
			return -(millis / (24 * 60 * 60 * 1000));
		}
		return millis / (24 * 60 * 60 * 1000);
	}

	/**
	 * 
	 * 功能描述：获得给定日期与系统当前日期之间的月数，不记天数
	 * 
	 * @param strdate
	 *            给定的日期字符串
	 * @return long 月数
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：待定
	 */
	private static long getMonths(String strdate) {
		long months = getMonth() - Integer.parseInt(strdate.substring(4, 6));
		long years = getYear() - Integer.parseInt(strdate.substring(0, 4));
		if (!isBefore(strdate)) {
			months = -months;
			years = -years;
		}
		if (months >= 0) {
			return years * 12 + months;
		} else {
			return (years - 1) * 12 + months + 12;
		}
	}
	/**
	 * 
	 * 功能描述：获得两个日期之间的月差数，不记天数
	 * @param strdate1
	 * @param strdate2
	 * @return long
	 * @author wangshanfang
	 * @date 2008-11-25
	 * @修改日志：待定
	 */
	private static long getMonths(String strdate1, String strdate2){
		long m = 0;
		setSysDate(strdate1);
		m = getMonths(strdate2);
		initSys();
		return m;
	}
	/**
	 * 
	 * 功能描述：获得给定日期与系统当前日期之间的月数和天数
	 * 
	 * @param strdate
	 *            给定的日期字符串
	 * @return long[] 下标0月数，1天数
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：待定
	 */
	public static long[] getMonthsAndDays(String strdate) {
		long m = getMonths(strdate);
		int d = getDay() - Integer.parseInt(strdate.substring(6, 8));
		String date = "";
		if(!isBefore(strdate)){
			d = -d;
			date = strdate;
		}else{
			date = getDate();
		}
		while(d<0){
			m--;
			d+=getDaysOfMonth(date);
		}
		long[] md = { m, d };
		return md;
	}
	/**
	 * 
	 * 功能描述：获得给定两个日期之间的月数和天数
	 * @param strdate1
	 * @param strdate2
	 * @return long[] 下标0月数，1天数
	 * @author wangshanfang
	 * @date 2008-11-25
	 * @修改日志：
	 */
	public static long[] getMonthsAndDays(String strdate1, String strdate2){
		long m = getMonths(strdate1,strdate2);
		int d = Integer.parseInt(strdate1.substring(6, 8))-Integer.parseInt(strdate2.substring(6, 8));
		String date = "";
		if(!isBefore(strdate1,strdate2)){			
			date = strdate1;
		}else{
			d = -d;
			date = strdate2;
		}
		while(d<0){
			m--;
			d+=getDaysOfMonth(date);
		}
		long[] md = { m, d };
		return md;
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
	 * 功能描述：判断是否是闰年（年限1000--9999）是：true，否：false
	 * 
	 * @param strdate
	 *            预判断年 格式yyyymmdd 或 yyyy
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static boolean isLeapYear(String strdate) {
		int y = Integer.parseInt(strdate.substring(0, 4));
		if (y <= 999) {
			return false;
		}
		if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0)) {
			return true;
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
	 * 功能描述：将字符串转换为Date型日期 日期格式yyyymmdd
	 * 
	 * @param strdate
	 *            预转换的字符串
	 * @return Date
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @修改日志：1.0
	 */
	public static Date parseDate(String strdate) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date d = null;
		try {
			d = format.parse(strdate);
		} catch (Exception pe) {
			pe.printStackTrace();
		}
		return d;
	}
	/**
	 * 功能描述：返回客户报表期的上一期
	 * 
	 * @param pigdate
	 *            客户报表日期
	 * @return lastTerm
	 * 			  客户报表的上一期
	 * @author sanhuachen
	 * @date 2009-10-16
	 * @修改日志：
	 */
	public static String GetShangTerm(String pigdate)
	{
        String lastTerm="";
		int a= Integer.parseInt(pigdate.substring(0,4));
		String b = pigdate.substring(4, 6);
		String c = pigdate.substring(4, 5);
		String d = pigdate.substring(5, 6);
		if("50".equals(pigdate.substring(6, 8))){									
			if("0".equals(c)){
				if("1".equals(d)){
					lastTerm = String.valueOf(a-1) + "1250";
				}else{
					lastTerm = String.valueOf(a) + "0" + String.valueOf(Integer.parseInt(d) - 1) + "50";
				}
			}else{
				if("0".equals(d)){
					lastTerm = String.valueOf(a) + "0950";
				}else{
					lastTerm = String.valueOf(a) + String.valueOf(Integer.parseInt(b) -1) + "50";
				}
			}
		}else if("40".equals(pigdate.substring(6, 8))){
			if("1".equals(d)){
				lastTerm = String.valueOf(a-1) + "0440";
			}else{
				lastTerm = String.valueOf(a) + "0" + String.valueOf(Integer.parseInt(d) - 1) + "40";
			}
		}else if("00".equals(pigdate.substring(6, 8))){
			lastTerm=String.valueOf(a-1)+"0000";
		}
		return lastTerm;
	}
//	/**
//	 * 取系统营业日期
//	 * @return
//	 */
//	public static String getSysDate(){
//		SysGlobalDAO dao = SourceTemplate.getSpringContextInstance().getBean("sysGlobalDAO",SysGlobalDAO.class);
//		SysGlobal sysGlobal = dao.getSysGlobal();
//		return sysGlobal.getSys_date();
//	}
//	public static final String getDateTime(Date aDate) {
//        SimpleDateFormat df = null;
//        String returnValue = "";
//       if (aDate == null) {
//           // log.error("aDate is null!");
////        	System.out.println("错误");
//        } else {
//            df = new SimpleDateFormat(pattern);
//            returnValue = df.format(aDate);
//        }
//
//        return (returnValue);
//    }
	/**
	 * 根据日期取得星期几
	 * @param DateStr
	 * @return
	 */
	public static String getWeekDay(String DateStr){ 
		SimpleDateFormat formatYMD=new SimpleDateFormat("yyyyMMdd");//formatYMD表示的是yyyyMMdd格式
		SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"表示"day in week" 
		Date d=null;
		String weekDay=""; 
		try{ d=formatYMD.parse(DateStr);//将String 转换为符合格式的日期
		weekDay=formatD.format(d); }
		catch 
		(Exception e)
		{ e.printStackTrace(); 
		} 
//		System.out.println("日期:"+DateStr+" ： "+weekDay);
		return weekDay; 
		} 
	/**
	 * 根据每月第一天星期几和一月天数确定行数
	 * @param week
	 * @param days
	 * @return
	 */
	public static int line(String week,int days){
		int line=0;
		if (week.equals("星期日")) {
			if(days>=29){
				line=5;
			}
			else {
				line=4;
			}
		}
		else if (week.equals("星期一")) {
			line=5;
		}
		else if (week.equals("星期二")) {
			line=5;
		}
		else if (week.equals("星期三")) {
			line=5;
		}
		else if (week.equals("星期四")) {
			line=5;
		}
		else if (week.equals("星期五")) {
			if(days>=31){
				line=6;
			}
			else {
				line=5;
			}
		}
		else if (week.equals("星期六")) {
			if(days>=30){
				line=6;
			}
			else {
				line=5;
			}
		}
		return line;
	}
	
	/**
	 *根据参数busidate返回上一月份的6位日期(YYYYMM格式)
	 * @param busidate
	 * @return
	 */
	public static String getLastMonth(String busidate) {
		String last_month = null;
		if(busidate == null || busidate.length() < 6 ) return null;
		int month = Integer.parseInt(busidate.substring(4));
		int year = Integer.parseInt(busidate.substring(0, 4));
		if (month == 1) {
			last_month = (year - 1) + "12";
		} else {
			month--;
			if (month > 9) {
				last_month = year + String.valueOf(month);
			} else {
				last_month = year + "0" + String.valueOf(month);
			}
		}
		return last_month;
	}
	
	/**
	 * 返回日期经过若干月后的日期 *20100302
	 * 
	 * @param dateStr
	 *            String YYYY-MM-DD
	 * @param hkm
	 *            int
	 * @return String
	 */
	public  static String getDateStr(String dateStr, int hkm) {
		String reDateStr = "";
		int yy = Integer.parseInt(dateStr.substring(0, 4), 10);
		int mm = Integer.parseInt(dateStr.substring(5, 7), 10);
		int dd = Integer.parseInt(dateStr.substring(8), 10);

		// int yy1=0,mm1=0,dd1=dd;
		int yy2 = 0, mm2 = 0, dd2 = dd;
		if ((mm + hkm) % 12 == 0) {
			yy2 = yy + (mm + hkm) / 12 - 1;
			mm2 = 12;
		} else {
			if ((mm + hkm) % 12 == 1) {
				yy2 = yy + (mm + hkm) / 12;
				mm2 = 1;
			} else {
				yy2 = yy + (mm + hkm) / 12;
				mm2 = (mm + hkm) % 12;
			}
		}
		reDateStr = String.valueOf(yy2) + "-" + bZero(mm2) + "-" + bZero(dd2);
		return reDateStr;
	}
	/**
	 * 返回两位数据字串 *
	 * 
	 * @param sz
	 *            int
	 * @return String
	 */
	public static String bZero(int sz) {
		return (sz < 10 ? ("0" + String.valueOf(sz)) : String.valueOf(sz));
	}
	
	public static String  dateFormat(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
		   Date date = null; 
		   String str1="0000-01-01";
		   try {  
		    date = format.parse(str);  
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
			  str1 = format1.format(date);  
		   } catch (ParseException e) {  
		  e.printStackTrace();  
		   }  

		 return str1;
		
	}

	public static String getStr(String begDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
