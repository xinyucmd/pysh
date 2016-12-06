package com.sp2p.action.front;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.json.simple.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.service.FinanceToolsService;

/**
 * @ClassName: FinanceToolsAction.java
 * @Author: li.hou
 * @Descrb: 我要理财，工具箱
 */
public class FinanceToolsAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;

	private FinanceToolsService financeToolsService;

	/**
	 * 天标计算
	 */
	public String toolsCalculateDay() throws Exception {

		double borrowSum = Convert.strToDouble(SqlInfusion
				.FilteSqlInfusion(paramMap.get("borrowSum")), -1);
		double yearRate = Convert.strToFloat(SqlInfusion
				.FilteSqlInfusion(paramMap.get("yearRate")), -1);// 接收百分比
		int borrowTime = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap
				.get("borrowTime")), -1);// 接收的数字是月

		if (yearRate < 0.00001) {
			JSONUtils.printStr("2");
			return null;
		}

		try {
			double yearRateVal = yearRate * 1.0f / 100;

			Map<String, Object> map = financeToolsService.rateCalculateDay(
					borrowSum, yearRateVal, borrowTime);

			if (map == null) {
				JSONUtils.printStr("1");
				return null;
			}

			JSONObject object = new JSONObject();
			object.put("map", map);

			JSONUtils.printObject(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 工具箱，利息计算器
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toolsCalculate() throws Exception {

		double borrowSum = Convert.strToDouble(SqlInfusion
				.FilteSqlInfusion(paramMap.get("borrowSum")), -1);
		double yearRate = Convert.strToFloat(SqlInfusion
				.FilteSqlInfusion(paramMap.get("yearRate")), -1);// 接收百分比
		int borrowTime = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap
				.get("borrowTime")), -1);// 接收的数字是月
		int repayWay = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap
				.get("repayWay")), -1);// 还款方式

		if (yearRate < 0.00001) {
			JSONUtils.printStr("2");
			return null;
		}

		double yearRateVal = yearRate * 1.0f / 100;

		List<Map<String, Object>> lists = null;
		if (repayWay == 0) {// 按月还款 (下拉框的下标从0开始)
			lists = financeToolsService.rateCalculate2Month(borrowSum,
					yearRateVal, borrowTime);
		} else if (repayWay == 2) {// 一次性 (下拉框的下标从0开始)
			lists = financeToolsService.rateCalculate2SumOne_new(borrowSum,
					yearRateVal, borrowTime);
		} else {// 先息后本
			lists = financeToolsService.rateCalculate2Sum(borrowSum,
					yearRateVal, borrowTime);
		}
		if (lists == null) {
			JSONUtils.printStr("1");
			return null;
		}
		String jsonStr = JSONArray.toJSONString(lists);
		JSONUtils.printStr(jsonStr);

		return null;
	}

	/**
	 * 收益计算器
	 * 
	 * @return
	 * @throws Exception
	 */
	public String incomeCalculate() throws Exception {

		double borrowSum = Convert.strToDouble(SqlInfusion
				.FilteSqlInfusion(paramMap.get("borrowSum")), -1);
		double yearRate = Convert.strToDouble(SqlInfusion
				.FilteSqlInfusion(paramMap.get("yearRate")), -1);// 接收百分比
		int borrowTime = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap
				.get("borrowTime")), -1);// 接收的数字是月
		int repayWay = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap
				.get("repayWay")), -1);// 还款方式
		double bidReward = Convert.strToDouble(SqlInfusion
				.FilteSqlInfusion(paramMap.get("bidReward")), 0);
		double bidRewardMoney = Convert.strToDouble(SqlInfusion
				.FilteSqlInfusion(paramMap.get("bidRewardMoney")), 0);
		if (yearRate < 0.00001) {
			JSONUtils.printStr("2");
			return null;
		}

		double yearRateVal = yearRate * 1.0f / 100;

		List<Map<String, Object>> lists = null;
		if (repayWay == 0) {// 按月还款 (下拉框的下标从0开始)
			lists = financeToolsService.rateIncome2Month(borrowSum,
					yearRateVal, borrowTime, bidReward, bidRewardMoney);
		} else if (repayWay == 1) {// 先息后本
			lists = financeToolsService.rateIncome2Sum(borrowSum, yearRateVal,
					borrowTime, bidReward, bidRewardMoney);
		}else if (repayWay == 2){//一次还本付息
			lists = financeToolsService.rateIncome2Sum_new(borrowSum, yearRateVal,
					borrowTime, bidReward, bidRewardMoney);
		}
		if (lists == null) {
			JSONUtils.printStr("1");
			return null;
		}
		String jsonStr = JSONArray.toJSONString(lists);
		JSONUtils.printStr(jsonStr);

		return null;
	}

	public String queryPhoneInfo() throws Exception {
		String address = "";
		try {
		String mobile = Convert.strToStr(paramMap.get("phoneNum"), null);
		if (mobile == null) {
		getOut().print("请输入手机号码进行查询");
		return null;
		}
		// 手机号码验证
		Pattern p = Pattern.compile("^((13[0-9])| (17[0-9])| (15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
		getOut().print("请输入正确的手机号码进行查询");
		return null;
		}
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		String key = "08d1e5b008381ad003c8dae75b9f084a";
		String url = "http://apis.juhe.cn/mobile/get?phone=" + mobile + "&key=" + key;
		address = getLocation2(url);
		} catch (Exception e) {
		address = "未知";
		System.out.println("手机所属地查询失败====================");
		}
		getOut().print(address);
		return null;
		}
		/**
		* 解析url返回的文件，获得值
		* 
		* @param url
		* @return
		* @throws Exception
		*/
		private String getLocation2(String url) throws Exception {
		String address = "";
		URLConnection connection = (URLConnection) new URL(url).openConnection();
		connection.setDoOutput(true);
		InputStream os = connection.getInputStream();
		Thread.sleep(100);
		int length = os.available();
		byte[] buff = new byte[length];
		os.read(buff);
		String s = new String(buff, "utf-8");
		// 返回数据fYodaoCallBack(1,
		// {‘product’:'ip’,'ip’:’192.168.1.1′,’location’:'局域网 对方和您在同一内部网’} , ”);
		String addre[] = s.split("province");
		String addre2[] = addre[1].split(":");
		String addre3 = addre2[1].substring(1, 3);
		String addre4 = addre2[2].substring(1, 3);
		String addre5 = addre2[5].substring(3, 5);
		address = addre3 + addre4 + addre5;
		s = null;
		buff = null;
		os.close();
		connection = null;
		return address;
	}

	/**
	 * ip地址查询
	 */
	public String queryIPInfo() throws Exception {
		String address = "";
		try {
			String ipAddr = Convert.strToStr(SqlInfusion
					.FilteSqlInfusion(paramMap.get("ipAddress")), null);
			if (ipAddr == null) {
				getOut().print("请输入IP进行查询");
				return null;
			}
			// 手机号码验证
			Pattern p = Pattern
					.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
							+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
							+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher m = p.matcher(ipAddr);
			if (!m.matches()) {
				getOut().print("请输入正确的IP地址进行查询");
				return null;
			}
			address = getLocationIp(ipAddr);
		} catch (Exception e) {
			address = "未知";
			System.out.println("IP地址查询失败====================");
		}
		getOut().print(address);
		return null;
	}

	/**
	 * 解析url返回的文件，获得值
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getLocationIp(String ip) throws Exception {
		String address = "";
		String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="
				+ ip;
		URLConnection connection = (URLConnection) new URL(url)
				.openConnection();
		connection.setDoOutput(true);
		InputStream os = connection.getInputStream();
		Thread.sleep(100);
		int length = os.available();
		byte[] buff = new byte[length];
		os.read(buff);
		String s = new String(buff, "gbk");
		Map<String, String> map2 = (Map<String, String>) JSONObject.toBean(
				JSONObject.fromObject(s.substring(21, s.length() - 1)),
				HashMap.class);
		// Map<String, String> map2 = (Map<String, String>) JSONObject.toBean(
		// JSONObject.fromObject(map.get("data")), HashMap.class);
		address = map2.get("country") + map2.get("province") + map2.get("city");
		s = null;
		buff = null;
		os.close();
		connection = null;
		return address;
	}

	public String howToFinanceInit() throws Exception {
		/**
		 * 初始化 从bt_config表中加载静态内容
		 */
		List<Map<String, Object>> map = financeToolsService.queryConfigList();
		if (map != null) {
			request().setAttribute("name", map.get(0).get("name"));
			request().setAttribute("var", map.get(0).get("var"));
		}
		return SUCCESS;
	}

	public FinanceToolsService getFinanceToolsService() {
		return financeToolsService;
	}

	public void setFinanceToolsService(FinanceToolsService financeToolsService) {
		this.financeToolsService = financeToolsService;
	}

}
