/**
 * 
 */
package com.sp2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.shove.Convert;

/**
 * 时间工具类
 * @author Administrator
 *
 */
public class DateUtil {
		
	public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public final static DateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");
	public final static DateFormat YY_MM = new SimpleDateFormat("yyMM");
	
	public final static DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

	public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return YYYY_MM_DD_MM_HH_SS.format(date);
	}
	
	public static String dateToStringYYMMDD(Date date){
		return YYYY_MM_DD.format(date);
	}
	
	public static String dateToStringYYMM(Date date){
		return YYYY_MM.format(date);
	}
	
	public static String dateToStringYYMM2(Date date){
		return YY_MM.format(date);
	}
	
	public static Date strToDate(String dateString){
		Date date = null;
		try {
			date = YYYY_MM_DD_MM_HH_SS.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date strToYYMMDDDate(String dateString){
		Date date = null;
		try {
			date = YYYY_MM_DD.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date strToDateYYMMDD(String dateString){
		Date date = null;
		try {
			date = YYYYMMDD.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 计算两个时间之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDays(Date startDate,Date endDate){
		long days = 0;
		long start = startDate.getTime();
		long end = endDate.getTime();
		//一天的毫秒数1000 * 60 * 60 * 24=86400000
		days = (end - start) / 86400000;
		return days;
	}
	
	/**
	 * 计算2个时间的大小，大于0，则startDate>endDate 否则亦然,等于0  说明2个时间相等
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDate(Date startDate,Date endDate){
		long start = startDate.getTime();
		long end = endDate.getTime();
		
		return end - start;
	}
	
	/**
	 * 日期加上月数的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddMonth(Date date,int month){
		return add(date,Calendar.MONTH,month);
	}
	
	/**
	 * 日期加上天数的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddDay(Date date,int day){
		return add(date,Calendar.DAY_OF_YEAR,day);
	}
	
	/**
	 * 日期加上年数的时间
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date,int year){
		return add(date,Calendar.YEAR,year);
	}
	
	 /** 
     * 计算剩余时间 (多少天多少时多少分)
     * @param startDateStr 
     * @param endDateStr 
     * @return 
     */  
    public static String remainDateToString(Date startDate, Date endDate){  
    	StringBuilder result = new StringBuilder();
    	if(endDate == null ){
    		return "过期";
    	}
    	long times = endDate.getTime() - startDate.getTime();
    	if(times < -1){
    		result.append("过期");
    	}else{
    		long temp = 1000 * 60 * 60 *24;
    		//天数
    		long d = times / temp;

    		//小时数
    		times %= temp;
    		temp  /= 24;
    		long m = times /temp;
    		//分钟数
    		times %= temp;
    		temp  /= 60;
    		long s = times /temp;
    		
    		result.append(d);
    		result.append("天");
    		result.append(m);
    		result.append("小时");
    		result.append(s);
    		result.append("分");
    	}
    	return result.toString();
    }  
    
	private static Date add(Date date,int type,int value){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}
	
	
	/**   
	 * @MethodName: getLinkUrl  
	 * @Param: DateUtil  
	 * flag ： true 转换  false 不转换
	 * @Author: gang.lv
	 * @Date: 2013-5-8 下午02:52:44
	 * @Return:    
	 * @Descb: 
	 * @Throws:
	*/
	public static String getLinkUrl(boolean flag,String content,String id){
		if(flag){
			content = "<a href='finance.do?id="+id+"'>"+content+"</a>";
		}
		return content;
	}
	
	/**
	 * 时间转换为时间戳
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format,String date) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return  sf.parse(sf.format(date)).getTime();
	}
	/**
	 * 时间转换为时间戳
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format,Date date) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return  sf.parse(sf.format(date)).getTime();
	}
	
	/**
	 * 将时间戳转为字符串 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) { 
	 String re_StrTime = null; 
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss"); 
	 long lcc_time = Long.valueOf(cc_time); 
	 re_StrTime = sdf.format(new Date(lcc_time * 1000L)); 
	 return re_StrTime; 
	 } 
	
	/**
	* 时间转换为时间戳
	*
	* @param format
	* @param date
	* @return
	* @throws ParseException
	*/
	public static String getTimeCurS(String format, Date date) throws ParseException {
	        SimpleDateFormat sf = new SimpleDateFormat(format);
	return Convert.strToStr(sf.parse(sf.format(date)).getTime() + "", "");
	}
	
	/**
	 * 获取传递时间时间的下一天
	 * @author guojingchao
	 * @return
	 * @throws Exception
	 */
	public static String getParamDateNext(Date parms) throws Exception{
		long m = parms.getTime();
		long n =24*60*60*1000;
		Date date = new Date(m+n);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date)+ " 00:00:00";
	}
	
	/**
	 * 获取当前时间的前一天的开始时间
	 * @author guojingchao
	 * @return
	 * @throws Exception
	 */
	public static String getCurrDateAgo() throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date)+ " 00:00:00";
	}
	
	/**
	 * 获取当前时间的前一天的结束时间
	 * @author guojingchao
	 * @return
	 * @throws Exception
	 */
	public static String getCurrDateAgoEnd() throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date)+ " 00:00:00";
	}
	
	/**
	 * 获取当前星期的第一天
	 * @author guojingchao
	 * @return
	 * @throws Exception
	 */
	public static String getCurrWeekFirstDay() throws Exception{
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
		mondayPlus = 0;
		} else {
		mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();


		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday + " 00:00:00";
	}
	
	/**
	 * 获取当前月第一天
	 * @author guojingchao
	 * @throws Exception
	 */
	public static String getCurrMonthFirstDay() throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return format.format(c.getTime())+ " 00:00:00";
        
	}
	
	/**
	 * 获取当前时间前后n天的开始时间
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public static String getCurrDateLateDay(int day) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date)+ " 00:00:00";
	}
	
	public static String getChangeUserName() throws Exception{
		String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
                "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",  
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",  
                "w", "x", "y", "z"};
        List<String> list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        String result = afterShuffle.substring(1,2);  
        return result+"****";
	}
	
	public static String getCurrDateAgoParmsDay(int parm) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, parm);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date)+ " 23:59:59";
	}
	
	/**
	 * 计算到期日期
	 * @return
	 * @throws Exception
	 */
	public static String getRepayDay(String date,int month) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d= df.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MONTH, month);
		return df.format(calendar.getTime());
	}
	
	/**
	 *当前月份的下month个月
	 *
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String getNextMonth(int month) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}
	
	
	/**
	 * 郭井超
	 * 2016-1-11
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public static String getNextMonthParm(int parm) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, parm);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
}
