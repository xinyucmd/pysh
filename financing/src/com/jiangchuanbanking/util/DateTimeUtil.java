package com.jiangchuanbanking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Date Time util
 */
public class DateTimeUtil {
	public static final int DATE_NONE = 0;
	public static final int DATE_TODAY = 1;
	public static final int DATE_YESTERDAY = 2;
	public static final int DATE_TOMORROW = 3;
	public static final int DATE_THIS_WEEK = 4;
	public static final int DATE_LAST_WEEK = 5;
	public static final int DATE_NEXT_WEEK = 6;
	public static final int DATE_THIS_MONTH = 7;
	public static final int DATE_LAST_MONTH = 8;
	public static final int DATE_NEXT_MONTH = 9;
	public static final int DATE_THIS_YEAR = 10;
	public static final int DATE_LAST_YEAR = 11;
	public static final int DATE_NEXT_YEAR = 12;
	private static String options = null;

	public static String getSelectOptions() {
		if (options == null) {
			StringBuilder optionBuider = new StringBuilder();
			ResourceBundle rb = CommonUtil.getResourceBundle();
			optionBuider.append(DateTimeUtil.DATE_NONE).append(":;")
					.append(DateTimeUtil.DATE_TODAY).append(":")
					.append(rb.getString("date.today")).append(";")
					.append(DateTimeUtil.DATE_YESTERDAY).append(":")
					.append(rb.getString("date.yesterday")).append(";")
					.append(DateTimeUtil.DATE_TOMORROW).append(":")
					.append(rb.getString("date.tomorrow")).append(";")
					.append(DateTimeUtil.DATE_THIS_WEEK).append(":")
					.append(rb.getString("date.thisWeek")).append(";")
					.append(DateTimeUtil.DATE_LAST_WEEK).append(":")
					.append(rb.getString("date.lastWeek")).append(";")
					.append(DateTimeUtil.DATE_NEXT_WEEK).append(":")
					.append(rb.getString("date.nextWeek")).append(";")
					.append(DateTimeUtil.DATE_THIS_MONTH).append(":")
					.append(rb.getString("date.thisMonth")).append(";")
					.append(DateTimeUtil.DATE_LAST_MONTH).append(":")
					.append(rb.getString("date.lastMonth")).append(";")
					.append(DateTimeUtil.DATE_NEXT_MONTH).append(":")
					.append(rb.getString("date.nextMonth")).append(";")
					.append(DateTimeUtil.DATE_THIS_YEAR).append(":")
					.append(rb.getString("date.thisYear")).append(";")
					.append(DateTimeUtil.DATE_LAST_YEAR).append(":")
					.append(rb.getString("date.lastYear")).append(";")
					.append(DateTimeUtil.DATE_NEXT_YEAR).append(":")
					.append(rb.getString("date.nextYear"));
			options = optionBuider.toString();
		}
		return options;
	}

	/**
	 * Gets HQL for date scope search
	 * 
	 * @param key
	 *            field name
	 * @value value search value
	 * @return the HQL for data scope search
	 */
	public static String getDateScope(String key, int value) {
		if (DateTimeUtil.DATE_NONE == value) {
			return "";
		}
		StringBuilder condition = new StringBuilder("");
		Date startDate = null;
		Date endDate = null;
		String start = null;
		String end = null;
		Calendar cl = Calendar.getInstance();
		switch (value) {
		case DateTimeUtil.DATE_TODAY:
			startDate = cl.getTime();
			cl.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_YESTERDAY:
			endDate = cl.getTime();
			cl.add(Calendar.DAY_OF_MONTH, -1);
			startDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_TOMORROW:
			cl.add(Calendar.DAY_OF_MONTH, 1);
			startDate = cl.getTime();
			cl.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_THIS_WEEK:
			cl.set(Calendar.DAY_OF_WEEK, 1);
			cl.add(Calendar.DAY_OF_MONTH, 1);
			startDate = cl.getTime();
			cl.set(Calendar.DAY_OF_WEEK,
					cl.getActualMaximum(Calendar.DAY_OF_WEEK));
			cl.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_LAST_WEEK:
			cl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			endDate = cl.getTime();
			cl.add(Calendar.WEEK_OF_YEAR, -1);
			startDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_NEXT_WEEK:
			cl.add(Calendar.WEEK_OF_YEAR, 1);
			cl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			startDate = cl.getTime();
			cl.add(Calendar.DAY_OF_YEAR, 7);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_THIS_MONTH:
			cl.set(Calendar.DAY_OF_MONTH, 1);
			startDate = cl.getTime();
			cl.set(Calendar.DAY_OF_MONTH,
					cl.getActualMaximum(Calendar.DAY_OF_MONTH));
			cl.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_LAST_MONTH:
			cl.add(Calendar.MONTH, -1);
			cl.set(Calendar.DAY_OF_MONTH, 1);
			startDate = cl.getTime();
			cl = Calendar.getInstance();
			cl.set(Calendar.DAY_OF_MONTH, 0);
			cl.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_NEXT_MONTH:
			cl.add(Calendar.MONTH, 1);
			cl.set(Calendar.DAY_OF_MONTH, 1);
			startDate = cl.getTime();
			cl = Calendar.getInstance();
			cl.add(Calendar.MONTH, 2);
			cl.set(Calendar.DAY_OF_MONTH, 0);
			cl.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_THIS_YEAR:
			cl.set(Calendar.DAY_OF_YEAR, 1);
			startDate = cl.getTime();
			cl = Calendar.getInstance();
			cl.add(Calendar.YEAR, 1);
			cl.set(Calendar.DAY_OF_YEAR, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_LAST_YEAR:
			cl.add(Calendar.YEAR, -1);
			cl.set(Calendar.DAY_OF_YEAR, 1);
			startDate = cl.getTime();
			cl = Calendar.getInstance();
			cl.set(Calendar.DAY_OF_YEAR, 1);
			endDate = cl.getTime();
			break;
		case DateTimeUtil.DATE_NEXT_YEAR:
			cl.add(Calendar.YEAR, 1);
			cl.set(Calendar.DAY_OF_YEAR, 1);
			startDate = cl.getTime();
			cl = Calendar.getInstance();
			cl.add(Calendar.YEAR, 2);
			cl.set(Calendar.DAY_OF_YEAR, 1);
			endDate = cl.getTime();
			break;
		}

		SimpleDateFormat dd = new SimpleDateFormat(Constant.DATE_FORMAT);
		start = dd.format(startDate);
		end = dd.format(endDate);
		condition.append(key).append(">= '").append(start).append("' AND ")
				.append(key).append("< '").append(end).append("'");
		return condition.toString();
	}
	
	public static String getNowDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());	
	}
	
	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);	
	}
	public static String getDateString1(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);	
	}
	
	public static Date getLastDate(Date date)  throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
	    int day = c.get(Calendar.DATE);
	    c.set(Calendar.DATE, day - 1);
		return c.getTime();	
	}
	//
	public static String getCnDateString(Date date) {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
	    int day = c.get(Calendar.DATE);
	    c.set(Calendar.DATE, day + 1);
		Date date2=c.getTime();
		String cnDate=sdf.format(date2);
		cnDate=cnDate.substring(0, 4)+"年"+cnDate.substring(5, 7)+"月"+cnDate.substring(8)+"日";		
		return 	cnDate;
	}
	//新增的方法20151030谷新玉以债权支付日修改为合同结束日次日
	public static String getCnDateString1(Date date) {		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
	    int day = c.get(Calendar.DATE);
	    c.set(Calendar.DATE, day + 1);
	    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
	    String s=sd.format(c.getTime());
	    s=s.substring(0, 4)+"年"+s.substring(5, 7)+"月"+s.substring(8)+"日";		
		return s;	
	}
	/**
	 * 将MM/dd/yyyy格式日期转化成yyyy-MM-dd格式
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getJDateToODate(String date) throws ParseException{
		SimpleDateFormat in = new SimpleDateFormat("MM/dd/yyyy");		
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");
		return out.format(in.parse(date));
	}
}
