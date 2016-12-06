package com.dx.collect.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;


/**
 * �������������ڴ������ࣨ����Calendar�� ��Ҫ���ܣ�����У�飻��ȡϵͳ��ǰ���ڣ����Զ���ϵͳ���ڣ����ж����ꣻ��ȡ��������֮���������������
 * �ж����ڵ�ǰ�󣻽��ַ���ת��ΪDate��Calendar��...
 * ���ڸ�ʽĬ�ϣ�yyyyMMdd
 * @author WangShanfang
 * @date 2008-11-21
 * @see null
 * @�޸���־��1.0 
 */
public class DateUtil extends Object {

	/**
	 * ��ǰ����ϵͳ���� Calendar
	 */
	private static Calendar calendar = new GregorianCalendar(TimeZone
			.getDefault());
	/**
	 * ���ڸ�ʽ Ĭ�ϣ�yyyyMMdd
	 */
	private static String pattern = "yyyyMMdd";
	/**
	 * ʱ���ʽ Ĭ�ϣ�HH:mm:ss
	 */
	private static String timePattern = "HH:mm:ss";

	/**
	 * ��
	 */
	private static int year = 0;
	/**
	 * ��
	 */
	private static int month = 0;
	/**
	 * ��
	 */
	private static int day = 0;
	/**
	 * ʱ
	 */
	private static int hour = 0;
	/**
	 * ��
	 */
	private static int minute = 0;
	/**
	 * ��
	 */
	private static int second = 0;

	/**
	 * ��̬��ʼ����Ĭ��ϵͳ��ǰ���ں�ʱ�䣩
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
	 * ���췽��
	 */
	private DateUtil() {
		// Do Nothing
	}

	/**
	 * �����������Զ���ϵͳʱ�䡣������ʹ�ã�
	 * 
	 * @param strdate
	 *            �Զ��������ַ�������ʽ��yyyymmdd
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * ���������� ��ʼ��ϵͳ����(��ǰϵͳ����)����setSysDate()����õ��η������³�ʼ��ϵͳ����ʱ��
	 * 			Ϊ��ǰ����ʱ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * ������������ȡϵͳ��ǰ����---��
	 * 
	 * @return int ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static int getYear() {
		return year;
	}

	/**
	 * ������������ȡϵͳ��ǰ����---��
	 * 
	 * @return String ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getStrYear() {
		return String.valueOf(year);
	}

	/**
	 * ������������ȡϵͳ��ǰ����---��
	 * 
	 * @return int ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static int getMonth() {
		return month;
	}

	/**
	 * ������������ȡϵͳ��ǰ����---��
	 * 
	 * @return String ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getStrMonth() {
		return month >= 10 ? String.valueOf(month) : "0"
				+ String.valueOf(month);
	}

	/**
	 * ������������ȡϵͳ��ǰ����---��
	 * 
	 * @return int ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static int getDay() {
		return day;
	}

	/**
	 * ������������ȡϵͳ��ǰ����---��
	 * 
	 * @return String ��
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getStrDay() {
		return day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
	}
	/**
	 * 
	 * ������������ȡϵͳʱ��--Сʱ
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @�޸���־��1.0
	 */
	public static int getHour(){
		return hour;
	}
	/**
	 * 
	 * ������������ȡϵͳʱ��--����
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @�޸���־��
	 */
	public static int getMinute(){
		return minute;
	}
	/**
	 * 
	 * ������������ȡϵͳʱ��--��
	 * @return int
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @�޸���־��
	 */
	public static int getSecond(){
		return second;
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
	 * ������������ȡϵͳ��ǰ����---������ ����ʽ��yyyymmdd��
	 * 
	 * @return String ʱ����
	 * @author haoxiaofeng
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat(timePattern);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * ��������������Ԥ����ʽȡϵͳ��ǰ����---������
	 * 
	 * @param ptn
	 *            ���ڸ�ʽ
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getDate(String ptn) {
		SimpleDateFormat format = new SimpleDateFormat(ptn);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * ������������ȡϵͳʱ�� ��ʽ��yyyymmdd hh:mm:ss
	 * 
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static String getDateTime() {
		SimpleDateFormat format = new SimpleDateFormat(pattern + " "
				+ timePattern);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * 
	 * ������������ȡԤ�����ʽ��ϵͳʱ��
	 * 
	 * @param datePtn
	 *            ���ڸ�ʽ
	 * @param timePtn
	 *            ʱ���ʽ
	 * @return String
	 * @author wangshanfang
	 * @date 2008-11-24
	 * @�޸���־��1.0
	 */
	public static String getDateTime(String datePtn, String timePtn) {
		SimpleDateFormat format = new SimpleDateFormat(datePtn + " " + timePtn);
		Date date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * �����������жϸ������ڣ���ʽyyyymmdd���Ƿ���ϵͳ����֮ǰ���ǣ�����ڣ���true����false
	 * 
	 * @param strdate
	 *            ��������
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
	 */
	public static boolean isBefore(String strdate) {
		Calendar cal = parseCalendar(strdate);
		return cal.before(calendar);
	}
	/**
	 * 
	 * �����������жϸ������������ڵ�ǰ��strdate1��strdate2֮ǰ����ͬһ�죩������true����֮��false
	 * @param strdate1	����1
	 * @param strdate2	����2
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-25
	 * @�޸���־��1.0
	 */
	public static boolean isBefore(String strdate1, String strdate2){
		Calendar cal1 = parseCalendar(strdate1);
		Calendar cal2 = parseCalendar(strdate2);
		return cal1.before(cal2);
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
	 * ������������ø�������������֮�������������и�����
	 * 
	 * @param fromdate
	 *            �����ַ��� ��ʽ��yyyymmdd
	 * @param todate
	 *            �����ַ��� ��ʽ��yyyymmdd
	 * @return long
	 * @author huochuanxi
	 * @date 2011-11-15
	 * @�޸���־��
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
	 * ������������ø���������ϵͳ��ǰ����֮�����������������
	 * 
	 * @param strdate
	 *            �����������ַ���
	 * @return long ����
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־������
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
	 * ���������������������֮����²�������������
	 * @param strdate1
	 * @param strdate2
	 * @return long
	 * @author wangshanfang
	 * @date 2008-11-25
	 * @�޸���־������
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
	 * ������������ø���������ϵͳ��ǰ����֮�������������
	 * 
	 * @param strdate
	 *            �����������ַ���
	 * @return long[] �±�0������1����
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־������
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
	 * ������������ø�����������֮�������������
	 * @param strdate1
	 * @param strdate2
	 * @return long[] �±�0������1����
	 * @author wangshanfang
	 * @date 2008-11-25
	 * @�޸���־��
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
	 * �����������ж��Ƿ������꣨����1000--9999���ǣ�true����false
	 * 
	 * @param strdate
	 *            Ԥ�ж��� ��ʽyyyymmdd �� yyyy
	 * @return boolean
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * �������������ַ���ת��ΪDate������ ���ڸ�ʽyyyymmdd
	 * 
	 * @param strdate
	 *            Ԥת�����ַ���
	 * @return Date
	 * @author wangshanfang
	 * @date 2008-11-21
	 * @�޸���־��1.0
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
	 * �������������ؿͻ������ڵ���һ��
	 * 
	 * @param pigdate
	 *            �ͻ���������
	 * @return lastTerm
	 * 			  �ͻ��������һ��
	 * @author sanhuachen
	 * @date 2009-10-16
	 * @�޸���־��
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
//	 * ȡϵͳӪҵ����
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
////        	System.out.println("����");
//        } else {
//            df = new SimpleDateFormat(pattern);
//            returnValue = df.format(aDate);
//        }
//
//        return (returnValue);
//    }
	/**
	 * ��������ȡ�����ڼ�
	 * @param DateStr
	 * @return
	 */
	public static String getWeekDay(String DateStr){ 
		SimpleDateFormat formatYMD=new SimpleDateFormat("yyyyMMdd");//formatYMD��ʾ����yyyyMMdd��ʽ
		SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"��ʾ"day in week" 
		Date d=null;
		String weekDay=""; 
		try{ d=formatYMD.parse(DateStr);//��String ת��Ϊ���ϸ�ʽ������
		weekDay=formatD.format(d); }
		catch 
		(Exception e)
		{ e.printStackTrace(); 
		} 
//		System.out.println("����:"+DateStr+" �� "+weekDay);
		return weekDay; 
		} 
	/**
	 * ����ÿ�µ�һ�����ڼ���һ������ȷ������
	 * @param week
	 * @param days
	 * @return
	 */
	public static int line(String week,int days){
		int line=0;
		if (week.equals("������")) {
			if(days>=29){
				line=5;
			}
			else {
				line=4;
			}
		}
		else if (week.equals("����һ")) {
			line=5;
		}
		else if (week.equals("���ڶ�")) {
			line=5;
		}
		else if (week.equals("������")) {
			line=5;
		}
		else if (week.equals("������")) {
			line=5;
		}
		else if (week.equals("������")) {
			if(days>=31){
				line=6;
			}
			else {
				line=5;
			}
		}
		else if (week.equals("������")) {
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
	 *���ݲ���busidate������һ�·ݵ�6λ����(YYYYMM��ʽ)
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
	 * �������ھ��������º������ *20100302
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
	 * ������λ�����ִ� *
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
