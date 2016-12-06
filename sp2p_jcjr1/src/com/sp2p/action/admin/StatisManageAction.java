package com.sp2p.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.UtilDate;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.admin.StatisManageService;
import com.sp2p.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: StatisManageAction.java
 * @Author: gang.lv
 * @Date: 2013-4-4 上午09:16:19
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb:统计管理控制层
 */
public class StatisManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(StatisManageAction.class);
	private static final long serialVersionUID = 1L;

	private StatisManageService statisManageService;
	private SelectedService selectedService;
	private List<Map<String, Object>> userGroupList;
	private AssignmentDebtService assignmentDebtService;
	
	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public StatisManageService getStatisManageService() {
		return statisManageService;
	}

	public void setStatisManageService(StatisManageService statisManageService) {
		this.statisManageService = statisManageService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getUserGroupList() throws Exception {
		if (userGroupList == null) {
			userGroupList = selectedService.userGroup();
		}
		return userGroupList;
	}

	/**
	 * @throws Exception
	 * @MethodName: webStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:16:36
	 * @Return:
	 * @Descb: 网站统计初始化
	 * @Throws:
	 */
	public String webStatisInit() throws Exception {
		Map<String, String> webMap = statisManageService.queryWebStatis();
		request().setAttribute("webMap", webMap);
		return "success";
	}

	/**
	 * 导出网站统计 excel
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String exportwebStatis() throws Exception {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 投标排名查询
			Map<String, String> webMap = statisManageService.queryWebStatis();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("covarianceName", "网站会员总金额");
			map1.put("covarianceNum", webMap.get("webUserAmount"));
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("covarianceName", "网站会员总冻结金额");
			map2.put("covarianceNum", webMap.get("webUserFreezeAmount"));
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("covarianceName", "网站会员总待收金额");
			map3.put("covarianceNum", webMap.get("webUserForPI"));
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("covarianceName", "网站收入总金额");
			map4.put("covarianceNum", webMap.get("webComeInAmount"));
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("covarianceName", "网站总VIP金额");
			map5.put("covarianceNum", webMap.get("webVIPAmount"));
			Map<String, Object> map7 = new HashMap<String, Object>();
			map7.put("covarianceName", "网站总学历认证金额");
			map7.put("covarianceNum", webMap.get("webXLAmount"));
			Map<String, Object> map8 = new HashMap<String, Object>();
			map8.put("covarianceName", "网站总借款管理费金额");
			map8.put("covarianceNum", webMap.get("borrowManageFee"));
			Map<String, Object> map9 = new HashMap<String, Object>();
			map9.put("covarianceName", "网站总借款逾期罚息金额");
			map9.put("covarianceNum", webMap.get("borrowFI"));
			Map<String, Object> map11 = new HashMap<String, Object>();
			map11.put("covarianceName", "后台手动添加费用");
			map11.put("covarianceNum", webMap.get("backAddAmount"));
			Map<String, Object> map12 = new HashMap<String, Object>();
			map12.put("covarianceName", "后台手动扣除费用");
			map12.put("covarianceNum", webMap.get("backDelAmount"));
			Map<String, Object> map13 = new HashMap<String, Object>();
			map13.put("covarianceName", "网站成功充值总额");
			map13.put("covarianceNum", webMap.get("webSucPrepaid"));
			Map<String, Object> map14 = new HashMap<String, Object>();
			map14.put("covarianceName", "网站线上充值总额");
			map14.put("covarianceNum", webMap.get("onlinePrepaid"));
			Map<String, Object> map15 = new HashMap<String, Object>();
			map15.put("covarianceName", "网站线下充值总额");
			map15.put("covarianceNum", webMap.get("downlinePrepaid"));
			Map<String, Object> map16 = new HashMap<String, Object>();
			map16.put("covarianceName", "网站提现总额");
			map16.put("covarianceNum", webMap.get("cashWith"));
			Map<String, Object> map17 = new HashMap<String, Object>();
			map17.put("covarianceName", "网站提现手续费总额");
			map17.put("covarianceNum", webMap.get("cashWithFee"));
			Map<String, Object> map22 = new HashMap<String, Object>();
			map22.put("covarianceName", "所有借款未还总额");
			map22.put("covarianceNum", webMap.get("borrowForPI"));
			Map<String, Object> map23 = new HashMap<String, Object>();
			map23.put("covarianceName", "所有逾期网站垫付未还款金额");
			map23.put("covarianceNum", webMap.get("webAdvinceForP"));
			Map<String, Object> map24 = new HashMap<String, Object>();
			map24.put("covarianceName", "借款逾期网站未垫付未还款金额");
			map24.put("covarianceNum", webMap.get("borrowForAmount"));
			Map<String, Object> map25 = new HashMap<String, Object>();
			map25.put("covarianceName", "所有借款已还款总额");
			map25.put("covarianceNum", webMap.get("borrowHasAmount"));
			Map<String, Object> map26 = new HashMap<String, Object>();
			map26.put("covarianceName", "所有借款正常还款总额");
			map26.put("covarianceNum", webMap.get("borrowNomalRepayAmount"));
			Map<String, Object> map27 = new HashMap<String, Object>();
			map27.put("covarianceName", "借款逾期网站垫付后已还款总额");
			map27.put("covarianceNum", webMap.get("webAdvinceHasP"));
			Map<String, Object> map28 = new HashMap<String, Object>();
			map28.put("covarianceName", "借款逾期的网站未垫付已还款总额");
			map28.put("covarianceNum", webMap.get("webNoAdvinceHasP"));
			Map<String, Object> map29 = new HashMap<String, Object>();
			map29.put("covarianceName", "借款逾期网站垫付总额");
			map29.put("covarianceNum", webMap.get("webAdviceAmount"));

			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			list.add(map5);
			list.add(map7);
			list.add(map8);
			list.add(map9);
			list.add(map11);
			list.add(map12);
			list.add(map13);
			list.add(map14);
			list.add(map15);
			list.add(map16);
			list.add(map17);
			list.add(map22);
			list.add(map23);
			list.add(map24);
			list.add(map25);
			list.add(map26);
			list.add(map27);
			list.add(map28);
			list.add(map29);

			HSSFWorkbook wb = ExcelUtils.exportExcel("网站统计", list,
					new String[] { "统计项", "金额" }, new String[] {
							"covarianceName", "covarianceNum" });
			this.export(wb, new Date().getTime() + ".xls");

			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("pr_getWebStatis", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出网站统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: loginStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:45:13
	 * @Return:
	 * @Descb: 登录统计列表
	 * @Throws:
	 */
	public String loginStatisList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String realName = paramMap.get("realName") == null ? "" : paramMap
				.get("realName");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String count = paramMap.get("count") == null ? "" : paramMap
				.get("count");
		int countInt = Convert.strToInt(count, -1);
		statisManageService.queryLoginStatisByCondition(userName, realName,
				timeStart, timeEnd, countInt, pageBean);

		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 推荐明细初始化
	 * @Throws:
	 */
	public String queryRecommendDetail() throws Exception {
		String userId = request.getString("id");
		request().setAttribute("userId", userId);
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 推荐明细列表
	 * @Throws:
	 */
	public String queryRecommendDetailList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String realName = paramMap.get("realName") == null ? "" : paramMap
				.get("realName");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String type = paramMap.get("type") == null ? "" : paramMap
				.get("type");
		String recommendUserId = paramMap.get("userId") == null ? "" : paramMap
				.get("userId");
		int typeInt = Convert.strToInt(type, -1);
		long userId = Convert.strToLong(recommendUserId, -1);
		//查询推荐人数
		Map<String, String> map = statisManageService.queryRecommendDetailSum(userId);
		
		statisManageService.queryRecommendDetailList(userId, userName, realName, timeStart, timeEnd, typeInt, pageBean);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("map", map);
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 投资明细初始化
	 * @Throws:
	 */
	public String queryInvestDetail() throws Exception {
		String userId = request.getString("id");
		request().setAttribute("userId", userId);
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 投资明细列表
	 * @Throws:
	 */
	public String queryInvestDetailList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String amountDown = paramMap.get("amountDown") == null ? "" : paramMap
				.get("amountDown");
		String amountUp = paramMap.get("amountUp") == null ? "" : paramMap
				.get("amountUp");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String recommendUserId = paramMap.get("userId") == null ? "" : paramMap
				.get("userId");
		long userId = Convert.strToLong(recommendUserId, -1);
		double investAmountDown = Convert.strToDouble(amountDown, 0.00);
		double investAmountUp = Convert.strToDouble(amountUp, 0.00);
		statisManageService.queryInvestDetailList(userId, userName, investAmountDown, investAmountUp, timeStart, timeEnd, pageBean);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}
	
	/**
	 * 查询好友关系统计初始化
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryRecomendRationInit()throws SQLException, DataException {
		//whb flag:1 通用 2:五一活动
		request().setAttribute("flag", 1);
		return SUCCESS;
	}
	
	/**
	 * 查询好友关系(五一)统计初始化
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryRecomendActivityInit()throws SQLException, DataException {
		//whb flag:1 通用 2:五一活动
		request().setAttribute("flag", 2);
		return SUCCESS;
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 好友统计列表
	 * @Throws:
	 */
	public String queryRecomendRationList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String recommendUser = paramMap.get("recommendUser") == null ? "" : paramMap
				.get("recommendUser");
		String type = paramMap.get("type") == null ? "" : paramMap
				.get("type");
		String flag = paramMap.get("flag") == null ? "" : paramMap
				.get("flag");
		int typeInt = Convert.strToInt(type, -1);
		int flagInt = Convert.strToInt(flag, -1);
		statisManageService.queryRecomendRationList(userName, recommendUser, typeInt, flagInt, pageBean);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	/**
	 * @Author: whb
	 * @Return:
	 * @Descb: 投资人奖励统计初始化5.1
	 * @Throws:
	 */
	public String queryRewardActivityInit() {
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 投资人奖励统计列表5.1
	 * @Throws:
	 */
	public String queryRewardList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String recommendUser = paramMap.get("recommendUser") == null ? "" : paramMap
				.get("recommendUser");
		String amountDown = paramMap.get("amountDown") == null ? "" : paramMap
				.get("amountDown");
		String amountUp = paramMap.get("amountUp") == null ? "" : paramMap
				.get("amountUp");
		String type = paramMap.get("type") == null ? "" : paramMap
				.get("type");
		int typeInt = Convert.strToInt(type, -1);
		double rewardAmountDown = Convert.strToDouble(amountDown, 0.00);
		double rewardAmountUp = Convert.strToDouble(amountUp, 0.00);
		//查询奖励总额
		Map<String, String> map = statisManageService.queryRewardListSum(userName, recommendUser, rewardAmountDown, rewardAmountUp, typeInt);
		statisManageService.queryRewardList(userName, recommendUser, rewardAmountDown, rewardAmountUp, typeInt, pageBean);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("sumAmount", map.get("sumAmount"));
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 奖励明细初始化
	 * @Throws:
	 */
	public String queryRewardDetail() throws Exception {
		String userId = request.getString("id");
		String flag = request.getString("flag");
		request().setAttribute("userId", userId);
		request().setAttribute("flag", flag);
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 奖励明细列表
	 * @Throws:
	 */
	public String queryRewardDetailList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String amountDown = paramMap.get("amountDown") == null ? "" : paramMap
				.get("amountDown");
		String amountUp = paramMap.get("amountUp") == null ? "" : paramMap
				.get("amountUp");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String recommendUserId = paramMap.get("userId") == null ? "" : paramMap
				.get("userId");
		String flag = paramMap.get("flag") == null ? "" : paramMap
				.get("flag");
		int flagInt = Convert.strToInt(flag, -1);
		long userId = Convert.strToLong(recommendUserId, -1);
		double rewardAmountDown = Convert.strToDouble(amountDown, 0.00);
		double rewardAmountUp = Convert.strToDouble(amountUp, 0.00);
		statisManageService.queryRewardDetailList(userId, userName, rewardAmountDown, rewardAmountUp, timeStart, timeEnd, flagInt, pageBean);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}
	
	
	/**
	 * @Author: whb
	 * @Return:
	 * @Descb: 推荐人奖励统计初始化5.1
	 * @Throws:
	 */
	public String queryRecommendInit() {
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @Param: StatisManageAction
	 * @Author: whb
	 * @Return:
	 * @Descb: 推荐人奖励统计列表5.1
	 * @Throws:
	 */
	public String queryRecommendList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String amountDown = paramMap.get("amountDown") == null ? "" : paramMap
				.get("amountDown");
		String amountUp = paramMap.get("amountUp") == null ? "" : paramMap
				.get("amountUp");
		String issuedAmountDown = paramMap.get("issuedAmountDown") == null ? "" : paramMap
				.get("issuedAmountDown");
		String issuedAmountUp = paramMap.get("issuedAmountUp") == null ? "" : paramMap
				.get("issuedAmountUp");
		String type = paramMap.get("type") == null ? "" : paramMap
				.get("type");
		int typeInt = Convert.strToInt(type, -1);
		double rewardAmountDown = Convert.strToDouble(amountDown, 0.00);
		double rewardAmountUp = Convert.strToDouble(amountUp, 0.00);
		double rewardIssuedAmountDown = Convert.strToDouble(issuedAmountDown, 0.00);
		double rewardIssuedAmountUp = Convert.strToDouble(issuedAmountUp, 0.00);
		statisManageService.queryRecommendList(userName, rewardAmountDown, rewardAmountUp, rewardIssuedAmountDown, rewardIssuedAmountUp, typeInt, pageBean);

		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}
	
	/**
	 * @MethodName: loginStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:16:44
	 * @Return:
	 * @Descb: 登录统计初始化
	 * @Throws:
	 */
	public String loginStatisInit() {
		return "success";
	}

	/**
	 * @MethodName: financeStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:16:57
	 * @Return:
	 * @Descb: 投资统计初始化
	 * @Throws:
	 */
	public String financeStatisInit() {
		return "success";
	}
	/**
	 * @throws Exception
	 * @MethodName: financeStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午02:51:14
	 * @Return:
	 * @Descb: 投资统计列表
	 * @Throws:
	 */
	public String financeStatisList() throws Exception {
		String radio = paramMap.get("radio") == null ? "" : paramMap
				.get("radio");
		int radioInt = Convert.strToInt(radio, -1);
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		SimpleDateFormat sfYear = new SimpleDateFormat(UtilDate.year);
		Date date = new Date();
		if (radioInt == -1) {
			// 没有日期限制
			timeStart = "";
			timeEnd = "";
		} else if (radioInt == 1) {
			// d当日
			timeStart = sf.format(date) + " 00:00:00";
			timeEnd = sf.format(date) + " 23:59:59";
		} else if (radioInt == 2) {
			// 当月
			timeStart = UtilDate.getMonthFirstDay();
			timeEnd = UtilDate.getMonthLastDay();
		} else if (radioInt == 3) {
			// 当年
			timeStart = sfYear.format(date) + "-01-01 00:00:00";
			timeEnd = sfYear.format(date) + "-12-31 23:59:59";
		}
		Map<String, String> financeEarnMap = statisManageService
				.queryFinanceEarnStatis(timeStart, timeEnd);
		request().setAttribute("financeEarnMap", financeEarnMap);
		return "success";
	}

	/**
	 * 导出投资统计列表 excel
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String exportfinanceStatis() throws Exception {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String radio = request.getString("radio") == null ? "" : request.getString("radio");
		int radioInt = Convert.strToInt(radio, -1);
		String timeStart = request.getString("timeStart") == null ? ""
				: request.getString("timeStart");
		String timeEnd = request.getString("timeEnd") == null ? "" : request.getString("timeEnd");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		SimpleDateFormat sfYear = new SimpleDateFormat(UtilDate.year);
		Date date = new Date();
		if (radioInt == -1) {
			// 没有日期限制
			timeStart = "";
			timeEnd = "";
		} else if (radioInt == 1) {
			// d当日
			timeStart = sf.format(date) + " 00:00:00";
			timeEnd = sf.format(date) + " 23:59:59";
		} else if (radioInt == 2) {
			// 当月
			timeStart = UtilDate.getMonthFirstDay();
			timeEnd = UtilDate.getMonthLastDay();
		} else if (radioInt == 3) {
			// 当年
			timeStart = sfYear.format(date) + "-01-01 00:00:00";
			timeEnd = sfYear.format(date) + "-12-31 23:59:59";
		}

		try {

			// 投标排名查询
			Map<String, String> financeEarnMap = statisManageService
					.queryFinanceEarnStatis(timeStart, timeEnd);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("covarianceName", "投资成功待收金额");
			map1.put("covarianceNum", financeEarnMap.get("investForAmount"));
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("covarianceName", "投资奖励金额");
			map2.put("covarianceNum", financeEarnMap.get("investRewardAmount"));
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("covarianceName", "借款人逾期罚金金额");
			map3.put("covarianceNum", financeEarnMap.get("borrowLateFAmount"));
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("covarianceName", "用户邀请好友金额");
			map4.put("covarianceNum", financeEarnMap.get("inviteReward"));
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("covarianceName", "借款成功总额");
			map5.put("covarianceNum", financeEarnMap.get("borrowAmount"));
			Map<String, Object> map6 = new HashMap<String, Object>();
			map6.put("covarianceName", "借款管理费总额");
			map6.put("covarianceNum", financeEarnMap.get("borrowManageFee"));
			Map<String, Object> map7 = new HashMap<String, Object>();
			map7.put("covarianceName", "借款利息总额");
			map7.put("covarianceNum", financeEarnMap
					.get("borrowInterestAmount"));
//			Map<String, Object> map8 = new HashMap<String, Object>();
//			map8.put("covarianceName", "借款奖励总额");
//			map8.put("covarianceNum", financeEarnMap.get("borrowRewardAmount"));
			Map<String, Object> map9 = new HashMap<String, Object>();
			map9.put("covarianceName", "借款逾期罚息总额");
			map9.put("covarianceNum", financeEarnMap.get("borrowLateFI"));

			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			list.add(map5);
			list.add(map6);
			list.add(map7);
//			list.add(map8);
			list.add(map9);

			HSSFWorkbook wb = ExcelUtils.exportExcel("投资盈利统计", list,
					new String[] { "统计项", "金额" }, new String[] {
							"covarianceName", "covarianceNum" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("pr_getFinanceEarnStatis",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投资盈利统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws SQLException
	 * @MethodName: investStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:17:07
	 * @Return:
	 * @Descb: 投标统计初始化
	 * @Throws:
	 */
	public String investStatisInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: investStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午09:13:15
	 * @Return:
	 * @Descb: 投标统计列表
	 * @Throws:
	 */
	public String investStatisList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String realName = paramMap.get("realName") == null ? "" : paramMap
				.get("realName");
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String amountDown = paramMap.get("amountDown") == null ? "" : paramMap
				.get("amountDown");
		double sumAmountDown = Convert.strToDouble(amountDown, -1);
		String amountUp = paramMap.get("amountUp") == null ? ""
				: paramMap.get("amountUp");
		double sumAmountUp = Convert.strToDouble(amountUp, -1);
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		int groupInt = Convert.strToInt(group, -1);
		//投标统计sum
		Map<String, String> map = statisManageService.queryInvestSum(investor, realName,
				borrowWayInt, sumAmountDown, sumAmountUp, groupInt, timeStart, timeEnd);
		statisManageService.queryInvestStatisByCondition(investor, realName,
				borrowWayInt, sumAmountDown, sumAmountUp, groupInt, 
				timeStart, timeEnd, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("sumAmount", map.get("sumAmount"));
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("totalNum", pageBean.getTotalNum());
		return "success";
	}

	/**
	 * 导出投标统计列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportinvestStatis() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 得到页面传来的值
			// 真实姓名
			String realName = paramMap.get("realName") == null ? "" : paramMap
					.get("realName");
			realName = URLDecoder.decode(realName, "UTF-8");
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 借款类型
			int borrowWay = Convert.strToInt(request
					.getString("borrowWay"), -1);
			// 投资总额(年化)
			double amountDown = Convert.strToDouble(request.getString("amountDown"), -1);
			double amountUp = Convert.strToDouble(request.getString("amountUp"), -1);
			String timeStart = request.getString("timeStart") == null ? "" : request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? "" : request.getString("timeEnd");
			// 用户组
			int group = Convert.strToInt(request.getString("group"), -1);

			// 已还款记录列表
			statisManageService.queryInvestStatisByCondition(investor, realName,
					borrowWay, amountDown, amountUp, group, timeStart, timeEnd, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("投标记录",
					pageBean.getPage(),
					new String[] { "用户名", "真实姓名","用户组","年化投资总额(￥)", "标的类型",
							}, new String[] {
							"investor", "realName","groupName","realAmount", 
							"borrowWay"});
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投标统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @MethodName: investDetailInit
	 * @Author: whb
	 * @Return:
	 * @Descb: 投标详情初始化
	 * @Throws:
	 */
	public String investDetailInit() {
		// 用户名
		String investor = request.getString("userId") == null ? ""
				: request.getString("userId");
		request().setAttribute("investor", investor);
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @MethodName: investDetailList
	 * @Author: whb
	 * @Return:
	 * @Descb: 投标详情列表
	 * @Throws:
	 */
	public String investDetailList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String flag = paramMap.get("flag") == null ? "" : paramMap
				.get("flag");
		String bTitle = paramMap.get("bTitle") == null ? "" : paramMap
				.get("bTitle");
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String isAutoBid = paramMap.get("isAutoBid") == null ? "" : paramMap
				.get("isAutoBid");
		int isAutoBidInt = Convert.strToInt(isAutoBid, -1);
		String deadline = paramMap.get("deadline") == null ? ""
				: paramMap.get("deadline");
		int deadlineInt = Convert.strToInt(deadline, 0);
 
		String amountDown = paramMap.get("amountDown") == null ? "" : paramMap
				.get("amountDown");
		double sumAmountDown = Convert.strToDouble(amountDown, -1);
		String amountUp = paramMap.get("amountUp") == null ? ""
				: paramMap.get("amountUp");
		double sumAmountUp = Convert.strToDouble(amountUp, -1);
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		int groupInt = Convert.strToInt(group, -1);
		//投标记录sum
		Map<String, String> map = statisManageService.queryInvestSum(flag,bTitle, investor,
				timeStart, timeEnd, borrowWayInt, isAutoBidInt, deadlineInt,
				sumAmountDown, sumAmountUp, groupInt);
		statisManageService.queryInvestDetailByCondition(flag,bTitle, investor,
				timeStart, timeEnd, borrowWayInt, isAutoBidInt, deadlineInt,
				sumAmountDown, sumAmountUp, groupInt, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("sumAmount", map.get("sumAmount"));
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("totalNum", pageBean.getTotalNum());
		return "success";
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 投资详情记录
	 */
	public String investDetailLists() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		Map<String, String> map = statisManageService.queryInvestSums(
				timeStart, timeEnd);
		statisManageService.queryInvestDetailByConditions(
				timeStart, timeEnd, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("sumAmount", map.get("sumAmount"));
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("totalNum", pageBean.getTotalNum());
		return "success";
	}
	
	
	public String searchMs()throws Exception {
		return "success";
	}
	public String searchIdentify()throws Exception {
		return "success";
	}
	public String searchRegSrc()throws Exception {
		return "success";
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 短信message使用量统计
	 */
    public String queryMsList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String status = "";
		String timeStart = "";
		String endTimeStr = "";
		if(StringUtils.isNotBlank(paramMap.get("selectName"))){
			status=paramMap.get("selectName");
		}
	
		if(StringUtils.isNotBlank(paramMap.get("timeStart"))){
			timeStart=paramMap.get("timeStart");
		}
		if(StringUtils.isNotBlank(paramMap.get("timeEnd"))){
			endTimeStr=paramMap.get("timeEnd");
		}
		String timeEnd = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
				Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
				try {
					timeEnd = DateUtil.getParamDateNext(parm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, String> map = statisManageService.queryMs(timeStart, timeEnd,status);
			statisManageService.queryMsDetailByConditions(timeStart, timeEnd, status, pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("status", status);
			request().setAttribute("timeStart", timeStart);
			request().setAttribute("timeEnd", endTimeStr);
			if(map!=null){
				request().setAttribute("sumAmount", map.get("sumAmount"));
			}else{
				request().setAttribute("sumAmount", 0);
			}
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
			return "success";
		}
    /**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 注册来源统计重点有设备和时间
	 */
    public String queryRegSrcList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String status = "";
		String timeStart = "";
		String endTimeStr = "";
		String regSrc="";
		if(StringUtils.isNotBlank(paramMap.get("selectName"))){
			status=paramMap.get("selectName");
		}
	
		if(StringUtils.isNotBlank(paramMap.get("timeStart"))){
			timeStart=paramMap.get("timeStart");
		}
		if(StringUtils.isNotBlank(paramMap.get("timeEnd"))){
			endTimeStr=paramMap.get("timeEnd");
		}
		if(StringUtils.isNotBlank(paramMap.get("regSrc"))){
			regSrc=paramMap.get("regSrc");
		}
		String timeEnd = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
				Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
				try {
					timeEnd = DateUtil.getParamDateNext(parm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String, String> map = statisManageService.queryRegSrc(timeStart, timeEnd,status,Integer.parseInt(regSrc));
			statisManageService.queryRegSrcDetailByConditions(timeStart, timeEnd, status, Integer.parseInt(regSrc),pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("status", status);
			request().setAttribute("timeStart", timeStart);
			request().setAttribute("timeEnd", endTimeStr);
			request().setAttribute("regSrc", regSrc);
			if(map!=null){
				request().setAttribute("sumAmount", map.get("sumAmount"));
			}else{
				request().setAttribute("sumAmount", 0);
			}
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
			return "success";
		}
    /**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 实名认证统计
	 */
		public String queryIdentifyList() throws Exception {
			pageBean.setPageSize(IConstants.PAGE_SIZE_10);
			String status = "";
			String timeStart = "";
			String endTimeStr = "";
			if(StringUtils.isNotBlank(paramMap.get("selectName"))){
				status=paramMap.get("selectName");
			}
		
			if(StringUtils.isNotBlank(paramMap.get("timeStart"))){
				timeStart=paramMap.get("timeStart");
			}
			if(StringUtils.isNotBlank(paramMap.get("timeEnd"))){
				endTimeStr=paramMap.get("timeEnd");
			}
			String timeEnd = "";
			if(endTimeStr!=null && !"".equals(endTimeStr)){
					Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
					try {
						timeEnd = DateUtil.getParamDateNext(parm);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			Map<String, String> map = statisManageService.queryIdentify(
					timeStart, timeEnd,status);
			statisManageService.queryIdentifyDetailByConditions(
					timeStart, timeEnd, status, pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("status", status);
			request().setAttribute("timeStart", timeStart);
			request().setAttribute("timeEnd", endTimeStr);
			if(map!=null){
				request().setAttribute("sumAmount", map.get("sumAmount"));
			}else{
				request().setAttribute("sumAmount", 0);
			}
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
			return "success";
	}
	
	/**
	 * 导出投标详情列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportinvestDetail() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		
		try {
			
			// 得到页面传来的值
			String flag = request.getString("flag") == null ? ""
					: request.getString("flag");
			// 借款标题
			String bTitle = request.getString("bTitle") == null ? ""
					: request.getString("bTitle");
			bTitle = URLDecoder.decode(bTitle, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 借款类型
			int borrowWay = Convert.strToInt(request
					.getString("borrowWay"), -1);
			// 是否自动投标
			int isAutoBid = Convert.strToInt(request.getString("isAutoBid"), -1);
			// 期限
			int deadline = Convert.strToInt(request.getString("deadline"), -1);
			
			// 投资金额
			double sumAmountDown = Convert.strToInt(request.getString("amountDown"), -1);
			double sumAmountUp = Convert.strToInt(request.getString("amountUp"), -1);
			// 用户组
			int group = Convert.strToInt(request.getString("group"), -1);
			
			// 查询详情
			statisManageService.queryInvestDetailByCondition(flag,bTitle, investor,
					timeStart, timeEnd, borrowWay, isAutoBid, deadline,
					sumAmountDown, sumAmountUp, group, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
				.print(
						"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
				.print(
						"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("投标记录",
					pageBean.getPage(),
					new String[] { "用户名", "投资金额(￥)", "投资项目", "借款类型",
				"是否自动投标", "投资期限", "投标时间" }, new String[] {
				"investor", "realAmount", "borrowTitle", "borrowWay", "isAutoBid",
				"deadline", "investTime" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投标详情列表", 2);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

public String exportinvestDetails() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		
		try {
			
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 查询详情
			statisManageService.queryInvestDetailByConditions(timeStart, timeEnd, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
				.print(
						"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
				.print(
						"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("投资记录",
					pageBean.getPage(),
					new String[] { "用户名", "真实姓名", "联系方式", "投资项目",
				"投资金额(￥)", "投资期限",  "推荐人", "推荐人手机号","投标时间","推荐人用户名" }, new 

					String[] {
				"username", "realName", "mobilePhone", "borrowTitle", "investAmount",
				"deadline", "rrealName","rmobilePhone","investTime","rusername" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投标详情列表", 2);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: investStatisRankInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:34:18
	 * @Return:
	 * @Descb: 投资排名
	 * @Throws:
	 */
	public String investStatisRankInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: investStatisRankList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:34:26
	 * @Return:
	 * @Descb: 投表排名查询
	 * @Throws:
	 */
	public String investStatisRankList() throws Exception {

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		int groupInt = Convert.strToInt(group, -1);
		
		
		//投标排名sum
		Map<String, String> map = statisManageService.queryInvestSum(investor,
				timeStart, timeEnd, groupInt);
		statisManageService.queryInvestStatisRankByCondition(investor,
				timeStart, timeEnd, groupInt, pageBean);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("totalNum", pageBean.getTotalNum());
		request().setAttribute("sumInvestAmount", map.get("sumInvestAmount"));
		request().setAttribute("sumRealAmount", map.get("sumRealAmount"));
		request().setAttribute("sumYearAmount", map.get("sumYearAmount"));
		return "success";
	}

	/**
	 * 导出投表排名查询 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportinvestStatisRank() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 用户组
			int group = Convert.strToInt(request.getString("group"), -1);
			// 投标排名查询
			statisManageService.queryInvestStatisRankByCondition(investor,
					timeStart, timeEnd, group, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			Long num = 1L;
			if (list != null) {
				for (Map<String, Object> map : list) {
					map.put("count", num);
					num += 1L;

				}
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("投标排名",
					pageBean.getPage(), new String[] { "排名", "用户名", "用户组","真实姓名",
							"期间成功投标金额", "期间投标金额总计", "账号总额型", "可以金额", "待收金额",
							"会员积分", "信用积分" }, new String[] { "count",
							"investor", "groupName","realName", "realAmount", "realSum",
							"totalSum", "usableSum", "forPI", "rating",
							"creditrating" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投标排名列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: borrowStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:17:15
	 * @Return:
	 * @Descb: 借款统计初始化
	 * @Throws:
	 */
	public String borrowStatisInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午10:25:18
	 * @Return:
	 * @Descb: 借款投资统计
	 * @Throws:
	 */
	public String borrowStatisList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String borrowTitle = paramMap.get("borrowTitle") == null ? ""
				: paramMap.get("borrowTitle");
		String borrower = paramMap.get("borrower") == null ? "" : paramMap
				.get("borrower");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		statisManageService.queryBorrowStatisByCondition(borrowTitle, borrower,
				timeStart, timeEnd, borrowWayInt, pageBean);
		// 借款管理费统计总额
		Map<String, String> feeMap = statisManageService
				.queryBorrowStatisAmount(borrowTitle, borrower, timeStart,
						timeEnd, borrowWayInt);

		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		request().setAttribute("feeMap", feeMap);
		return "success";
	}

	/**
	 * 导出债权转让费用统计 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportdebtMangrFee() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			String debtStatus = "2";
			// 转让人
			String alienatorName = request.getString("alienatorName") == null ? ""
					: request.getString("alienatorName");
			alienatorName = URLDecoder.decode(alienatorName, "UTF-8");
			// 购买人
			String auctionName = request.getString("auctionName") == null ? ""
					: request.getString("auctionName");
			auctionName = URLDecoder.decode(auctionName, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			
			assignmentDebtService.queryAssignmentDebt(auctionName, timeStart, timeEnd, "",alienatorName,debtStatus,pageBean);
			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			statisManageService.changeNumToStr(pageBean);

			HSSFWorkbook wb = ExcelUtils.exportExcel("债权转让费统计", pageBean
					.getPage(), new String[] { "借款标题", "借款人",
					"转让人", "购买人", "剩余期数", "年利率", "债权价值", "转出价格", "手续费", "成交时间" }, new String[] {
				    "borrowTitle", "borrowerName",  "alienatorName", "auctionName","remainPeriod", "annualRate", "debtSum",
					"debtPrice", "manageFee", "createTime", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_auction_assignmentdebt",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出债权转让费统计列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出借款投资统计 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportborrowStatis() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 用户名
			String investor = request.getString("borrower") == null ? ""
					: request.getString("borrower");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 标题
			String borrowTitle = request.getString("borrowTitle") == null ? ""
					: request.getString("borrowTitle");
			borrowTitle = URLDecoder.decode(borrowTitle, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 借款类型
			int borroWer = Convert.strToInt(
					request.getString("borrowWay"), -1);
			statisManageService.queryBorrowStatisByCondition(borrowTitle,
					investor, timeStart, timeEnd, borroWer, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			statisManageService.changeNumToStr(pageBean);

			HSSFWorkbook wb = ExcelUtils.exportExcel("借款管理费统计", pageBean
					.getPage(), new String[] { "借款用户名", "借款标题", "借款金额(￥)",
					"借款类型", "借款期限", "借款管理费", "复审成功时间" }, new String[] {
					"borrower", "borrowTitle", "borrowAmount", "borrowWay",
					"deadline", "manageFee", "auditTime", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_borrow",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出借款管理费统计列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: borrowStatisInterestInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:36:46
	 * @Return:
	 * @Descb: 投资利息统计初始化
	 * @Throws:
	 */
	public String borrowStatisInterestInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowStatisInterestList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:36:57
	 * @Return:
	 * @Descb: 投资利息查询
	 * @Throws:
	 */
	public String borrowStatisInterestList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		statisManageService.queryborrowStatisInterestByCondition(investor,
				timeStart, timeEnd, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * 导出借款投资统计 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportborrowStatisInterest() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 得到页面传来的值
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 借款投资统计
			statisManageService.queryborrowStatisInterestByCondition(investor,
					timeStart, timeEnd, pageBean);

			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}

			HSSFWorkbook wb = ExcelUtils.exportExcel("投资利息管理费统计", pageBean
					.getPage(), new String[] { "用户名", "真实姓名", "期间净赚利息总额",
					"期间收到还款总额", "已赚利息总额 ","利息管理费", "待收利息总额", "待收总额" }, new String[] {
					"investor", "realName", "manageFI", "hasPI", "hasInterest","manageFee",
					"forInterest", "forPI", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投资利息管理费统计", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户组统计查询初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryborrowStatisUserGroupInit() throws SQLException,
			DataException {
		return SUCCESS;
	}

	/**
	 * 用户组统计查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryborrowStatisUserGroupPage() throws Exception {
		String groupName = paramMap.get("groupName") == null ? "" : paramMap
				.get("groupName");
		try {
			statisManageService.queryborrowStatisUserGroupByCondition(
					groupName, pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return SUCCESS;

	}

	/**
	 * 导出用户组统计
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public String exportUserGroup() throws SQLException, DataException {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			String groupName = request.getString("groupName") == null ? ""
					: request.getString("groupName");
			groupName = URLDecoder.decode(groupName, "UTF-8");
			// 导出
			statisManageService.queryborrowStatisUserGroupByCondition(
					groupName, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户组统计", pageBean
					.getPage(), new String[] { "序号", "组名", "总金额(元)", "冻结金额(元)",
					"待收金额(元) ", "借款管理费金额", "待收利息总额(元)", "VIP总金额", "已还款总额",
					"投资总额" }, new String[] { "groupId", "groupName",
					"totalSum", "freezeSum", "forPI", "manageFee",
					"forInterest", "vipFee", "hasPI", "realAmount" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_group_user_amount", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出用户组统计", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * 明细导出
	 * @return
	 */
	public String sysBaseStatisExcelDetail(){
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		request().setAttribute("status", status);
		request().setAttribute("startTime", startTime);
		request().setAttribute("endTime", endTimeStr);
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		pageBean.setPageNum(1); 
		pageBean.setPageSize(100000);
		 
		try {
			statisManageService.sysBaseStatisDetail(pageBean, status, startTime, endTime);
			if (pageBean.getPage() == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			 
			HSSFWorkbook wb = ExcelUtils.exportExcel("明细统计", pageBean.getPage(), 
				    new String[] {
		    		"日期", "注册用户（个）", "投资用户（个）", "充值金额（元）","提现金额（元） ", "投资金额（元）", "机构投资（元）", "线上投资（元）", "线下理财（元）",},
				    new String[] {
				    "s_date_f", "s_reg_user_sum","s_invest_user_sum", 
				    "s_cz_money", "s_tx_money", "s_tz_money",
					"s_org_moey", "s_upline_moeny", "s_downline_money"
					});
			this.export(wb, new Date().getTime() + ".xls"); 
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 统计导出
	 * @return
	 */
	public String sysBaseStatisExcel(){
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		request().setAttribute("status", status);
		request().setAttribute("startTime", startTime);
		request().setAttribute("endTime", endTimeStr);
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		Map<String, Object> map4 = new HashMap<String, Object>();
		Map<String, Object> map5 = new HashMap<String, Object>();
		 
		try {

			
			//累计用户注册
			Map<String,String> allUserSumMap = statisManageService.queryAllRegiestUser();
			String allUuerSum = "0";
			if(allUserSumMap!=null){
				allUuerSum = allUserSumMap.get("allUuerSum");
			}
			request().setAttribute("allUuerSum", allUuerSum);
			
			//累计用户投资
			Map<String,String> allInvestUserMap = statisManageService.queryAllInvestUser();
			String allInvestUser = "0";
			if(allInvestUserMap!=null){
				allInvestUser = allInvestUserMap.get("investUserCount");
			}
			request().setAttribute("allInvestUser", allInvestUser);
			
			//投资占比
			 
			double m = Convert.strToLong(allInvestUser, 0)*100;
			double n = Convert.strToLong(allUuerSum, 0);
			double investUserBL = m/n;
		    BigDecimal bg = new BigDecimal(investUserBL);
		    investUserBL = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			request().setAttribute("investUserBL", investUserBL);
			
			//累计充值金额
			Map<String,String> queryAllFundUserMap = statisManageService.queryAllFundUser();
			String allFundUser = "0.00";
			if(queryAllFundUserMap!=null){
				allFundUser = queryAllFundUserMap.get("handleSum");
			}
			request().setAttribute("allFundUser", allFundUser);
			map1.put("name1", "累计用户注册");
			map1.put("value1", allUuerSum);
			map1.put("name2", "累计用户投资");
			map1.put("value2", allInvestUser);
			map1.put("name3", "投资占比");
			map1.put("value3", investUserBL+"%");
			map1.put("name4", "累计充值金额");
			map1.put("value4", allFundUser);
			
			//累计成交金额
			Map<String,String> queryAllUserUpAndDownMap = statisManageService.queryAllUserUplineAndDownlineSum();
			String allUserUpAndDown = "0.00";
			if(queryAllUserUpAndDownMap!=null){
				allUserUpAndDown = queryAllUserUpAndDownMap.get("investAmountUpAndDown");
			}
			request().setAttribute("allUserUpAndDown", allUserUpAndDown);
			
			//累计线下理财
			Map<String,String> queryAllUserDownMap = statisManageService.queryAllUserDownlineSum();
			String allUserDown = "0.00";
			if(queryAllUserDownMap!=null){
				allUserDown = queryAllUserDownMap.get("investAmountDown");
			}
			double allUserDown_bf = Convert.strToDouble(allUserDown, 0.00)*100/Convert.strToDouble(allUserUpAndDown, 0.00);
			bg = new BigDecimal(allUserDown_bf);
		    allUserDown_bf = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			request().setAttribute("allUserDown", allUserDown);
			request().setAttribute("allUserDown_bf", allUserDown_bf);
			
			//累计线上投资
			Map<String,String> queryAllUserUpMap = statisManageService.queryAllUserUplineSum();
			String allUserUp = "0.00";
			if(queryAllUserUpMap!=null){
				allUserUp = queryAllUserUpMap.get("investAmountUp");
			}
			double allUserUp_bf = Convert.strToDouble(allUserUp, 0.00)*100/Convert.strToDouble(allUserUpAndDown, 0.00);
			bg = new BigDecimal(allUserUp_bf);
			allUserUp_bf = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			request().setAttribute("allUserUp", allUserUp);
			request().setAttribute("allUserUp_bf", allUserUp_bf);
			
			//累计机构投资
			Map<String,String> queryAllUserOrgMap = statisManageService.queryAllUserOrgSum();
			String allUserOrg = "0.00";
			if(queryAllUserOrgMap!=null){
				allUserOrg = queryAllUserOrgMap.get("investAmountOrg");
			}
			double allUserOrg_bf = Convert.strToDouble(allUserOrg, 0.00)*100/Convert.strToDouble(allUserUpAndDown, 0.00);
			bg = new BigDecimal(allUserOrg_bf);
			allUserOrg_bf = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			request().setAttribute("allUserOrg", allUserOrg);
			request().setAttribute("allUserOrg_bf", allUserOrg_bf);
			
			map2.put("name1", "累计成交金额");
			map2.put("value1", allUserUpAndDown);
			map2.put("name2", "累计机构投资");
			map2.put("value2", allUserOrg );
			map2.put("name3", "累计线上投资");
			map2.put("value3", allUserUp);
			map2.put("name4", "累计线下理财");
			map2.put("value4", allUserDown);
			//用户可用余额
			Map<String,String> queryAllUserAbleSumMap = statisManageService.queryAllUserAbleSum();
			String usableSum = "0.00";
			if(queryAllUserAbleSumMap!=null){
				usableSum = queryAllUserAbleSumMap.get("usableSum");
			}
			request().setAttribute("usableSum", usableSum);
			map3.put("name1", "用户可用余额");
			map3.put("value1", usableSum);
			map3.put("name2", "机构投资占比");
			map3.put("value2", allUserOrg_bf+"%" );
			map3.put("name3", "线上投资占比");
			map3.put("value3", allUserUp_bf+"%");
			map3.put("name4", "线下理财占比");
			map3.put("value4", allUserDown_bf+"%");
			
			//新增注册用户
			String newRegUser = "0";
			Map<String,String> queryNewRegUserMap = statisManageService.queryNewRegUser(status, startTime, endTime);
			if(queryNewRegUserMap!=null){
				newRegUser = queryNewRegUserMap.get("newRegUsers");
			}
			request().setAttribute("newRegUser", newRegUser);
			
			//新增投资用户
			String newInvestUser = "0";
			Map<String,String> queryNewInvestUserMap = statisManageService.queryNewInvestUser(status, startTime, endTime);
			if(queryNewInvestUserMap!=null){
				newInvestUser = queryNewInvestUserMap.get("investUserCount");
			}
			request().setAttribute("newInvestUser", newInvestUser);
			
			//新增投资占比
			double newInvestUser_bf = Convert.strToDouble(newInvestUser, 0.00)*100/Convert.strToDouble(newRegUser, 0.00);
			if(newInvestUser_bf>0){
				bg = new BigDecimal(newInvestUser_bf);
				newInvestUser_bf = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}else{
				newInvestUser_bf = 0;
			}
			request().setAttribute("newInvestUser_bf", newInvestUser_bf);
			
			//新增投资金额
			String newInvestMoney = "0";
			Map<String,String> queryNewInvestMoneyMap = statisManageService.queryNewInvestMoney(status, startTime, endTime);
			if(queryNewInvestMoneyMap!=null){
				newInvestMoney = queryNewInvestMoneyMap.get("investAmountSum");
			}
			request().setAttribute("newInvestMoney", newInvestMoney);
			map4.put("name1", "新增注册用户");
			map4.put("value1", newRegUser);
			map4.put("name2", "新增投资用户");
			map4.put("value2", newInvestUser);
			map4.put("name3", "新增投资占比");
			map4.put("value3", newInvestUser_bf+"%");
			map4.put("name4", "新增投资金额");
			map4.put("value4", newInvestMoney);
			
			//新增成交金额
			String handleSum = "0.00";
			Map<String,String> queryTxMoneyMap = statisManageService.queryTxMoney(status, startTime, endTime);
			if(queryTxMoneyMap!=null){
			 handleSum = queryTxMoneyMap.get("handleSum");
			}
			double cjSumDouble =  Convert.strToDouble(newInvestMoney, 0)-Convert.strToDouble(handleSum, 0);
			request().setAttribute("cjSumDouble", cjSumDouble);
			
			//新增机构投资
			String investAmountNewOrg = "0.00";
			Map<String,String> queryAllUserNewOrgSumMap = statisManageService.queryAllUserNewOrgSum(status, startTime, endTime);
			if(queryAllUserNewOrgSumMap!=null){
				investAmountNewOrg = queryAllUserNewOrgSumMap.get("investAmountNewOrg");
			}
			request().setAttribute("investAmountNewOrg", investAmountNewOrg);
			
			//新增线上投资
			String investAmountNewUpLine = "0.00";
			Map<String,String> queryAllUserNewUpLineSumMap = statisManageService.queryAllUserNewUpLineSum(status, startTime, endTime);
			if(queryAllUserNewUpLineSumMap!=null){
				investAmountNewUpLine = queryAllUserNewUpLineSumMap.get("investAmountNewUpLine");
			}
			request().setAttribute("investAmountNewUpLine", investAmountNewUpLine);
			
			//新增线下理财
			String investAmountNewDownLine = "0.00";
			Map<String,String> queryAllUserNewDownLineSumMap = statisManageService.queryAllUserNewDownLineSum(status, startTime, endTime);
			if(queryAllUserNewDownLineSumMap!=null){
				investAmountNewDownLine = queryAllUserNewDownLineSumMap.get("investAmountNewDownLine");
			}
			request().setAttribute("investAmountNewDownLine", investAmountNewDownLine);
			map5.put("name1", "新增成交金额");
			map5.put("value1", cjSumDouble);
			map5.put("name2", "新增机构投资");
			map5.put("value2", investAmountNewOrg);
			map5.put("name3", "新增线上投资");
			map5.put("value3", investAmountNewUpLine);
			map5.put("name4", "新增线下理财");
			map5.put("value4", investAmountNewDownLine);
		 
			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			list.add(map5);
			 
			HSSFWorkbook wb = ExcelUtils.exportExcel("数据统计", list, 
				    new String[] {"","", "","", "","", "","",},
				    new String[] {"name1","value1" ,"name2","value2" ,"name3","value3" ,"name4","value4" 
					});
			this.export(wb, new Date().getTime() + ".xls"); 
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 投资统计
	 * @return
	 */
	public String investAll(){
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		request().setAttribute("status", status);
		request().setAttribute("startTime", startTime);
		request().setAttribute("endTime", endTimeStr);
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			
			//累计成交金额
			Map<String,String> queryAllUserUpAndDownMap = statisManageService.queryAllUserUplineAndDownlineSum();
			String allUserUpAndDown = "0.00";
			String allUserUpAndDownf = "0.00";
			if(queryAllUserUpAndDownMap!=null){
				allUserUpAndDown = queryAllUserUpAndDownMap.get("investAmount");
				allUserUpAndDownf = queryAllUserUpAndDownMap.get("investAmountF");
			}
			request().setAttribute("allUserUpAndDown", allUserUpAndDown);
			request().setAttribute("allUserUpAndDownf", allUserUpAndDownf);
			//累计线下理财
			Map<String,String> queryAllUserDownMap = statisManageService.queryAllUserDownlineSum();
			String allUserDown = "0.00";
			String allUserDownf = "0.00";
			if(queryAllUserDownMap!=null){
				allUserDown = queryAllUserDownMap.get("investX");
				allUserDownf = queryAllUserDownMap.get("investXF");
			}
			request().setAttribute("allUserDown", allUserDown);
			request().setAttribute("allUserDownf", allUserDownf);
			
			//累计线上投资
			Map<String,String> queryAllUserUpMap = statisManageService.queryAllUserUplineSum();
			String allUserUp = "0.00";
			String allUserUpf = "0.00";
			if(queryAllUserUpMap!=null){
				allUserUp = queryAllUserUpMap.get("investS");
				allUserUpf = queryAllUserUpMap.get("investSF");
			}
			request().setAttribute("allUserUp", allUserUp);
			request().setAttribute("allUserUpf", allUserUpf);
			
			//累计机构投资
			Map<String,String> queryAllUserOrgMap = statisManageService.queryAllUserOrgSum();
			String allUserOrg = "0.00";
			String allUserOrgf = "0.00";
			if(queryAllUserOrgMap!=null){
				allUserOrg = queryAllUserOrgMap.get("investJ");
				allUserOrgf = queryAllUserOrgMap.get("investJF");
			}
			request().setAttribute("allUserOrg", allUserOrg);
			request().setAttribute("allUserOrgf", allUserOrgf);
			
			//新增投资金额
			String newInvestMoney = "0";
			String newInvestMoneyf = "0";
			Map<String,String> queryNewInvestMoneyMap = statisManageService.queryNewInvestMoney(status, startTime, endTime);
			if(queryNewInvestMoneyMap!=null){
				newInvestMoney = queryNewInvestMoneyMap.get("investNewAmount");
				newInvestMoneyf = queryNewInvestMoneyMap.get("investNewAmountF");
			}
			request().setAttribute("newInvestMoney", newInvestMoney);
			request().setAttribute("newInvestMoneyf", newInvestMoneyf);
			//新增机构投资
			String investAmountNewOrg = "0.00";
			String investAmountNewOrgf = "0.00";
			Map<String,String> queryAllUserNewOrgSumMap = statisManageService.queryAllUserNewOrgSum(status, startTime, endTime);
			if(queryAllUserNewOrgSumMap!=null){
				investAmountNewOrg = queryAllUserNewOrgSumMap.get("investNewAmountJ");
				investAmountNewOrgf = queryAllUserNewOrgSumMap.get("investNewAmountJF");
			}
			request().setAttribute("investAmountNewOrg", investAmountNewOrg);
			request().setAttribute("investAmountNewOrgf", investAmountNewOrgf);
			//新增线上投资
			String investAmountNewUpLine = "0.00";
			String investAmountNewUpLinef = "0.00";
			Map<String,String> queryAllUserNewUpLineSumMap = statisManageService.queryAllUserNewUpLineSum(status, startTime, endTime);
			if(queryAllUserNewUpLineSumMap!=null){
				investAmountNewUpLine = queryAllUserNewUpLineSumMap.get("investNewAmountS");
				investAmountNewUpLinef = queryAllUserNewUpLineSumMap.get("investNewAmountSF");
			}
			request().setAttribute("investAmountNewUpLine", investAmountNewUpLine);
			request().setAttribute("investAmountNewUpLinef", investAmountNewUpLinef);
			//新增线下理财
			String investAmountNewDownLine = "0.00";
			String investAmountNewDownLinef = "0.00";
			Map<String,String> queryAllUserNewDownLineSumMap = statisManageService.queryAllUserNewDownLineSum(status, startTime, endTime);
			if(queryAllUserNewDownLineSumMap!=null){
				investAmountNewDownLine = queryAllUserNewDownLineSumMap.get("investNewAmountX");
				investAmountNewDownLinef = queryAllUserNewDownLineSumMap.get("investNewAmountXF");
			}
			request().setAttribute("investAmountNewDownLine", investAmountNewDownLine);
			request().setAttribute("investAmountNewDownLinef", investAmountNewDownLinef);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 数据统计
	 * @return
	 */
	public String sysBaseStatisInit(){
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		request().setAttribute("status", status);
		request().setAttribute("startTime", startTime);
		request().setAttribute("endTime", endTimeStr);
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			
			//累计用户注册
			Map<String,String> allUserSumMap = statisManageService.queryAllRegiestUser();
			String allUuerSum = "0";
			if(allUserSumMap!=null){
				allUuerSum = allUserSumMap.get("allUuerSum");
			}
			request().setAttribute("allUuerSum", allUuerSum);
			//累计安卓用户注册
			Map<String,String> allUserSumMap1 = statisManageService.queryAllARegiestUser();
			String allUuerSum1 = "0";
			if(allUserSumMap1!=null){
				allUuerSum1 = allUserSumMap1.get("allUuerSum");
			}
			request().setAttribute("allAuerSum", allUuerSum1);
			//累计苹果用户注册
			Map<String,String> allUserSumMap2 = statisManageService.queryAllIRegiestUser();
			String allUuerSum2 = "0";
			if(allUserSumMap2!=null){
				allUuerSum2 = allUserSumMap2.get("allUuerSum");
			}
			request().setAttribute("allIuerSum", allUuerSum2);
			//累计PC用户注册
			Map<String,String> allUserSumMap3 = statisManageService.queryAllPRegiestUser();
			String allUuerSum3 = "0";
			if(allUserSumMap3!=null){
				allUuerSum3 = allUserSumMap3.get("allUuerSum");
			}
			request().setAttribute("allPuerSum", allUuerSum3);
			//累计用户投资
			Map<String,String> allInvestUserMap = statisManageService.queryAllInvestUser();
			String allInvestUser = "0";
			if(allInvestUserMap!=null){
				allInvestUser = allInvestUserMap.get("investUserCount");
			}
			request().setAttribute("allInvestUser", allInvestUser);
			
			
			
			//新增注册用户
			String newRegUser = "0";
			Map<String,String> queryNewRegUserMap = statisManageService.queryNewRegUser(status, startTime, endTime);
			if(queryNewRegUserMap!=null){
				newRegUser = queryNewRegUserMap.get("newRegUsers");
			}
			request().setAttribute("newRegUser", newRegUser);
			//新增安卓注册用户
			String newRegUser1 = "0";
			Map<String,String> queryNewRegUserMap1 = statisManageService.queryNewARegUser(status, startTime, endTime);
			if(queryNewRegUserMap1!=null){
				newRegUser1 = queryNewRegUserMap1.get("newRegUsers");
			}
			request().setAttribute("newARegUser", newRegUser1);
			//新增苹果注册用户
			String newRegUser2 = "0";
			Map<String,String> queryNewRegUserMap2 = statisManageService.queryNewIRegUser(status, startTime, endTime);
			if(queryNewRegUserMap2!=null){
				newRegUser2 = queryNewRegUserMap2.get("newRegUsers");
			}
			request().setAttribute("newIRegUser", newRegUser2);
			//新增PC注册用户
			String newRegUser3 = "0";
			Map<String,String> queryNewRegUserMap3 = statisManageService.queryNewPRegUser(status, startTime, endTime);
			if(queryNewRegUserMap3!=null){
				newRegUser3 = queryNewRegUserMap3.get("newRegUsers");
			}
			request().setAttribute("newPRegUser", newRegUser3);
			
			//新增投资用户
			String newInvestUser = "0";
			Map<String,String> queryNewInvestUserMap = statisManageService.queryNewInvestUser(status, startTime, endTime);
			if(queryNewInvestUserMap!=null){
				newInvestUser = queryNewInvestUserMap.get("investUserCount");
			}
			request().setAttribute("newInvestUser", newInvestUser);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 提现统计
	 * @return
	 */
	public String withdrawAll(){
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		request().setAttribute("status", status);
		request().setAttribute("startTime", startTime);
		request().setAttribute("endTime", endTimeStr);
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			//累计总提现金额
			Map<String,String> queryAllWithdrawUserMap = statisManageService.queryAllWithdrawUser();
			String allWithdrawUser = "0.00";
			if(queryAllWithdrawUserMap!=null){
				allWithdrawUser = queryAllWithdrawUserMap.get("withdrawSum");
			}
			request().setAttribute("allWithdrawUser", allWithdrawUser);
			
			//新增总提现金额
			Map<String,String> queryNewWithdrawMap = statisManageService.queryNewWithdraw(status, startTime, endTime);
			String allNewWithdraw = "0.00";
			if(queryNewWithdrawMap!=null){
				allNewWithdraw = queryNewWithdrawMap.get("newWithdraw");
			}
			request().setAttribute("allNewWithdraw", allNewWithdraw);
			//累计真实提现金额
			Map<String,String> queryAllWithdrawUserMap1 = statisManageService.queryAllWithdrawUser1();
			String allWithdrawUser1 = "0.00";
			if(queryAllWithdrawUserMap1!=null){
				allWithdrawUser1 = queryAllWithdrawUserMap1.get("withdrawSum1");
			}
			request().setAttribute("allWithdrawUser1", allWithdrawUser1);
			
			//新增真实提现金额
			Map<String,String> queryNewWithdrawMap2 = statisManageService.queryNewWithdraw1(status, startTime, endTime);
			String allNewWithdraw1 = "0.00";
			if(queryNewWithdrawMap2!=null){
				allNewWithdraw1 = queryNewWithdrawMap2.get("newWithdraw3");
			}
			request().setAttribute("allNewWithdraw1", allNewWithdraw1);
			//累计机构提现金额
			Map<String,String> queryAllWithdrawUserMap3 = statisManageService.queryAllWithdrawUser2();
			String allWithdrawUser3 = "0.00";
			if(queryAllWithdrawUserMap3!=null){
				allWithdrawUser3 = queryAllWithdrawUserMap3.get("withdrawSum2");
			}
			request().setAttribute("allWithdrawUser2", allWithdrawUser3);
			
			//新增机构提现金额
			Map<String,String> queryNewWithdrawMap4 = statisManageService.queryNewWithdraw2(status, startTime, endTime);
			String allNewWithdraw4 = "0.00";
			if(queryNewWithdrawMap4!=null){
				allNewWithdraw4 = queryNewWithdrawMap4.get("newWithdraw1");
			}
			request().setAttribute("allNewWithdraw2", allNewWithdraw4);
			//累计线下理财人提现金额
			Map<String,String> queryAllWithdrawUserMap5 = statisManageService.queryAllWithdrawUser3();
			String allWithdrawUser5 = "0.00";
			if(queryAllWithdrawUserMap5!=null){
				allWithdrawUser5 = queryAllWithdrawUserMap5.get("withdrawSum3");
			}
			request().setAttribute("allWithdrawUser3", allWithdrawUser5);
			
			//新增线下理财人提现金额
			Map<String,String> queryNewWithdrawMap6 = statisManageService.queryNewWithdraw3(status, startTime, endTime);
			String allNewWithdraw6 = "0.00";
			if(queryNewWithdrawMap6!=null){
				allNewWithdraw6 = queryNewWithdrawMap6.get("newWithdraw2");
			}
			request().setAttribute("allNewWithdraw3", allNewWithdraw6);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 总投资图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String createChart() throws IOException{
		return SUCCESS;
	}
	/**
	 * 总投资图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String createCharts() throws Exception{
		List dataChart=statisManageService.chart();
		JSONArray result = JSONArray.fromObject(dataChart);
		printJson(result.toString());
		return null;
	}
	/**
	 * 标的浪费图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String borrowChart() throws IOException{
		return SUCCESS;
	}
	/**
	 * 标的浪费图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String borrowCharts() throws Exception{
		List dataChart=statisManageService.borrowChart();
		JSONArray result = JSONArray.fromObject(dataChart);
		printJson(result.toString());
		return null;
	}
	/**
	 * 产品交易量图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String borrowChart1() throws IOException{
		return SUCCESS;
	}
	/**
	 * 产品交易量图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String borrowCharts1() throws Exception{
		List dataChart=statisManageService.borrowChart1();
		List List1=statisManageService.borrowChart11();
		List List2=statisManageService.borrowChart2();
		List List3=statisManageService.borrowChart3();
		List List4=statisManageService.borrowChart4();
		List List5=statisManageService.borrowChart5();
		List List6=statisManageService.borrowChart6();
		 JSONArray json = new JSONArray();
	        JSONObject jo = new JSONObject();
	        jo.put("data1", List1);//活利宝
	        jo.put("data2", List2);//定息宝
	        jo.put("data3", List3);//定息宝2
	        jo.put("data4", List4);//定息宝3个月
	        jo.put("data5", List5);//定息宝6个月
	        jo.put("data6", List6);//定息宝12个月
	        jo.put("cmonth", dataChart);
	        json.add(jo);
		printJson(json.toString());
		return null;
	}
	/**
	 * 当月每天投资额图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String investChart() throws IOException{
		return SUCCESS;
	}
	/**
	 * 当月每天投资额图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String investCharts() throws Exception{
		List dataChart=statisManageService.investChart();
		JSONArray result = JSONArray.fromObject(dataChart);
		printJson(result.toString());
		return null;
	}
	/**
	 * 总注册用户图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String regChart() throws IOException{
		return SUCCESS;
	}
	/**
	 * 总注册用户图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String regCharts() throws Exception{
		List dataChart=statisManageService.regChart();
		JSONArray result = JSONArray.fromObject(dataChart);
		printJson(result.toString());
		return null;
	}
	/**
	 * 当月每日注册图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String regChart1() throws IOException{
		return SUCCESS;
	}
	/**
	 * 当月每日注册图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String regChart1s() throws Exception{
		List dataChart=statisManageService.regChart1();
		JSONArray result = JSONArray.fromObject(dataChart);
		printJson(result.toString());
		return null;
	}
	/**
	 * 注册来源图表
	 * @author guxinyu
	 * @return
	 * @throws IOException 
	 */
	public String regSourceChart() throws IOException{
		return SUCCESS;
	}
	/**
	 * 注册来源图表1
	 * @author guxinyu
	 * @return
	 * @throws Exception 
	 */
	public String regSourceCharts() throws Exception{
		List dataChart=statisManageService.regSourceChart();
		JSONArray result = JSONArray.fromObject(dataChart);
		printJson(result.toString());
		return null;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 待收统计
	 */
	public String RepayMentList() throws Exception {
		//whb 添加还款日期查询
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		String repayStatus = request().getParameter("repayStatus");
		int ri=Integer.parseInt(repayStatus);
		request().setAttribute("status", status);
		request().setAttribute("startTime", startTime);
		request().setAttribute("endTime", endTimeStr);
		request().setAttribute("repayStatus", ri);
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			//累计总提现金额
			Map<String,String> queryAllRepayMap = statisManageService.queryAllRepay(ri);
			String allRepay = "0.00";
			if(queryAllRepayMap!=null){
				allRepay = queryAllRepayMap.get("d1");
			}
			request().setAttribute("allRepay", allRepay);
			
			//新增总提现金额
			Map<String,String> queryNewWithdrawMap = statisManageService.queryNewRepay(status, startTime, endTime,ri);
			String allRepay1 = "0.00";
			if(queryNewWithdrawMap!=null){
				allRepay1 = queryNewWithdrawMap.get("d2");
			}
			request().setAttribute("allRepay1", allRepay1);
			//累计真实提现金额
			Map<String,String> queryAllRepayMap1 = statisManageService.queryAllRepay1(ri);
			String allRepay2 = "0.00";
			if(queryAllRepayMap1!=null){
				allRepay2 = queryAllRepayMap1.get("d3");
			}
			request().setAttribute("allRepay2", allRepay2);
			
			//新增真实提现金额
			Map<String,String> queryAllRepayMap2 = statisManageService.queryNewRepay1(status, startTime, endTime,ri);
			String allRepay3 = "0.00";
			if(queryAllRepayMap2!=null){
				allRepay3 = queryAllRepayMap2.get("d4");
			}
			request().setAttribute("allRepay3", allRepay3);
			//累计机构提现金额
			Map<String,String> queryAllRepayMap3 = statisManageService.queryAllRepay2(ri);
			String allRepay4 = "0.00";
			if(queryAllRepayMap3!=null){
				allRepay4 = queryAllRepayMap3.get("d5");
			}
			request().setAttribute("allRepay4", allRepay4);
			
			//新增机构提现金额
			Map<String,String> queryAllRepayMap4 = statisManageService.queryNewRepay2(status, startTime, endTime,ri);
			String allRepay5 = "0.00";
			if(queryAllRepayMap4!=null){
				allRepay5 = queryAllRepayMap4.get("d6");
			}
			request().setAttribute("allRepay5", allRepay5);
			//累计线下理财人提现金额
			Map<String,String> queryAllRepayMap5 = statisManageService.queryAllRepay3(ri);
			String allRepay6 = "0.00";
			if(queryAllRepayMap5!=null){
				allRepay6 = queryAllRepayMap5.get("d7");
			}
			request().setAttribute("allRepay6", allRepay6);
			
			//新增线下理财人提现金额
			Map<String,String> queryAllRepayMap6 = statisManageService.queryNewRepay3(status, startTime, endTime,ri);
			String allRepay8 = "0.00";
			if(queryAllRepayMap6!=null){
				allRepay8 = queryAllRepayMap6.get("d8");
			}
			request().setAttribute("allRepay8", allRepay8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 发标统计
	 */
	public String giveBiao(){
		try{
			String status = request().getParameter("selectName");
			String startTime  = request().getParameter("startTime");
			String endTimeStr = request().getParameter("endTime");
/*			String group = request().getParameter("group")==null?"":request().getParameter("group");
*/			String borrowWay = request().getParameter("borrowWay")==null?"":request().getParameter("borrowWay");
			String deadline = request().getParameter("deadline")==null?"":request().getParameter("deadline");
/*			int groupInt = Convert.strToInt(group, -1);
*/			int borrowWayInt = Convert.strToInt(borrowWay, -1);
			int deadlineInt=Convert.strToInt(deadline, -1);
			request().setAttribute("status", status);
			request().setAttribute("startTime", startTime);
			request().setAttribute("endTime", endTimeStr);
/*			request().setAttribute("group", groupInt);
*/			request().setAttribute("borrowWay", borrowWayInt);
			request().setAttribute("deadline", deadlineInt);
			String endTime = "";
			if(endTimeStr!=null && !"".equals(endTimeStr)){
				Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
				try {
					endTime = DateUtil.getParamDateNext(parm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String,String> queryAllGiveSumMap = statisManageService.queryAllGiveSum();
			String allGive = "0.00";
			if(queryAllGiveSumMap!=null){
				allGive = queryAllGiveSumMap.get("borrowAmounts");
			}
			request().setAttribute("allGive", allGive);
			Map<String, String> feeMap = statisManageService
					.queryGiveBiao(status, borrowWayInt, startTime,
							endTime,deadlineInt);
			request().setAttribute("feeMap", feeMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author gxy
	 * @Descb 投标统计
	 */
		public String touBiao(){
			try{
				String status = request().getParameter("selectName");
				String startTime  = request().getParameter("startTime");
				String endTimeStr = request().getParameter("endTime");
				String group = request().getParameter("group")==null?"":request().getParameter("group");
				String borrowWay = request().getParameter("borrowWay")==null?"":request().getParameter("borrowWay");
				String deadline = request().getParameter("deadline")==null?"":request().getParameter("deadline");
				int groupInt = Convert.strToInt(group, -1);
				int borrowWayInt = Convert.strToInt(borrowWay, -1);
				int deadlineInt=Convert.strToInt(deadline, -1);
				request().setAttribute("status", status);
				request().setAttribute("startTime", startTime);
				request().setAttribute("endTime", endTimeStr);
				request().setAttribute("group", groupInt);
				request().setAttribute("borrowWay", borrowWayInt);
				request().setAttribute("deadline", deadlineInt);
				String endTime = "";
				if(endTimeStr!=null && !"".equals(endTimeStr)){
					Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
					try {
						endTime = DateUtil.getParamDateNext(parm);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Map<String,String> queryAllTouSumMap = statisManageService.queryAllTouSum();
				String allTou = "0.00";
				if(queryAllTouSumMap!=null){
					allTou = queryAllTouSumMap.get("touAmounts");
				}
				request().setAttribute("allTou", allTou);
				Map<String, String> feeMap = statisManageService
						.querytouBiao(status, borrowWayInt, startTime,
								endTime,deadlineInt,groupInt);
				request().setAttribute("feeMap", feeMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		}
	public String sysBaseStatisDetail(){
		//分页参数
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		//查询条件
		String status = request().getParameter("selectName");
		String startTime  = request().getParameter("startTime");
		String endTimeStr = request().getParameter("endTime");
		String endTime = "";
		if(endTimeStr!=null && !"".equals(endTimeStr)){
			Date parm =DateUtil.strToYYMMDDDate(endTimeStr);
			try {
				endTime = DateUtil.getParamDateNext(parm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JSONObject jo = new JSONObject();
		try {
			statisManageService.sysBaseStatisDetail(pageBean, status, startTime, endTime);
			List<Map<String, Object>> list  = pageBean.getPage();
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list); 
		} catch (Exception e){
			jo.put("state", -1);
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	}
	
	public String queryRecomendRelationPage()throws SQLException, DataException {
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		JSONObject json = new JSONObject();
		String userName = request().getParameter("username");
		String reallyName = request().getParameter("reallyName");
		String mobilePhone = request().getParameter("mobilePhone");
		
		try {
			statisManageService.queryRecomendRationInit(pageBean,userName,reallyName,mobilePhone);
			List list = pageBean.getPage();  
			Map<String,String> map = statisManageService.queryRecomendRationHelp(userName, reallyName, mobilePhone);
			if(map!=null && map.size()>0){
				int m = Convert.strToInt(map.get("totalNum"), 0);
				if(m>0){
					json.put("totalNum", m);
				}else{
					json.put("totalNum", 1);
				}
				
			}else{
				json.put("totalNum", 1);
			}
			
			json.put("result", list);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		printJson(json.toString());
		return null;
	}
	
	/**
	 * 好友推荐详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryRecomendRationDetail()throws SQLException, DataException {
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		JSONObject json = new JSONObject();
		long id = Convert.strToLong(request().getParameter("id"), 0);
		String userName = request().getParameter("username");
		String reallyName = request().getParameter("reallyName");
		String mobilePhone = request().getParameter("mobilePhone");
		String src = request().getParameter("src");
		String time1 = request().getParameter("time1");
		String time2 = request().getParameter("time2");
		
		try {
			statisManageService.queryRecomendRationDetail(pageBean,id,userName,reallyName,mobilePhone,src,time1,time2);
			List list = pageBean.getPage();  
			json.put("totalNum", pageBean.getTotalNum());
			json.put("result", list);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		printJson(json.toString());
		return null;
	}

	/**
	 * 奖励发放统计-现金
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMoneySendInit()throws SQLException, DataException {
		try {
			List<Map<String, Object>> list = null;
			list = statisManageService.queryMoneySendAll();
			if(list!=null && list.size()>0){
				double all = 0;
				for(int i=0;i<list.size();i++){
					int reward_type = (Integer) list.get(i).get("reward_type");
					String  reward_typs = String.valueOf(reward_type);
					double reward_amount = (Double) list.get(i).get("reward_amount");
					all= all + reward_amount;
					request().setAttribute("money"+reward_typs, reward_amount);
					request().setAttribute("all", all);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/***
	 * 统计推广返现-现金
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMoneySendPage()throws SQLException, DataException {
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(Convert.strToInt(pageNum, 1)); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		JSONObject json = new JSONObject();
		String username = request().getParameter("username");
		String reallyName = request().getParameter("reallyName");
		String type = request().getParameter("type");
		String time1 = request().getParameter("time1");
		String time2 = request().getParameter("time2");
		
		try {
			statisManageService.queryMoneySendPage(pageBean, username, reallyName, type, time1, time2);
			List list = pageBean.getPage();  
			json.put("totalNum", pageBean.getTotalNum());
			json.put("result", list);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		printJson(json.toString());
		return null;
	}
	
	
	/**
	 * 体检卡初始化
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryActivtyAnyInit()throws SQLException, DataException {
		return SUCCESS;
	} 
	
	/**
	 * 体检卡查询
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryActivtyAny()throws SQLException, DataException {
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(Convert.strToInt(pageNum, 1)); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		JSONObject json = new JSONObject();
		String check_code = request().getParameter("check_code");
		String state = request().getParameter("state");
		String end_time = request().getParameter("end_time");
		String start_time = request().getParameter("start_time");
		
		try {
			statisManageService.queryActivtyAny(pageBean, check_code, state, start_time,end_time);
			List list = pageBean.getPage();  
			json.put("totalNum", pageBean.getTotalNum());
			json.put("result", list);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		printJson(json.toString());
		return null;
	}
	
	/**
	 * 生成体检卡
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String createActivtyAny()throws SQLException, DataException {
		JSONObject json = new JSONObject();
		try {
			//面额
			double values = Convert.strToDouble(request().getParameter("values"), 0);
			if(values<=0){
				json.put("desc", 1);
				printJson(json.toString());
				return null;
			}
	        //个数
			int  temp = Convert.strToInt(request().getParameter("temp"), 0);
			if(temp<=0){
				json.put("desc", 2);
				printJson(json.toString());
				return null;
			}
			//天数
			int  day = Convert.strToInt(request().getParameter("day"), 0);
			if(day<=0){
				json.put("desc", 3);
				printJson(json.toString());
				return null;
			}
			long m = statisManageService.createActivtyAny(values, temp,day);
			if(m==1){
				json.put("state", 1);
			}else{
				json.put("state", 0);
			}
		} catch (Exception e) {
			 json.put("state", -1);
			 e.printStackTrace();
		}
		printJson(json.toString());
		return null;
	}
	
	/**
	 * 编辑体检卡
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateActivtyAny()throws SQLException, DataException {
		JSONObject json = new JSONObject();
		try {
			long id = Convert.strToLong(request().getParameter("id"), 0);
			long m = statisManageService.updateActivtyAny(id);
			if(m>0){
				json.put("state", 1);
			}else{
				json.put("state", 0);
			}
		} catch (Exception e) {
			json.put("state", -1);
			e.printStackTrace();
		}
		
		printJson(json.toString());
		return null;
	}
	
	/**
	 * 导出体检卡
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryActivtyAnyExcel()throws SQLException, DataException {
		String check_code = request().getParameter("check_code"); 
		String state = request().getParameter("state"); 
		String start_time = request().getParameter("start_time"); 
		String end_time = request().getParameter("end_time"); 
		try {
			List<Map<String, Object>> list = statisManageService.queryActivtyAnyExcel(check_code, state, start_time, end_time);
		
			if (list== null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			 
			HSSFWorkbook wb = ExcelUtils.exportExcel("明细统计", list, 
				    new String[] {
		    		"用户名", "手机号", "体检码", "面值金额（元）","有效天数（天） ", "生成时间", "失效时间", "状态",},
				    new String[] {
				    "username", "mobilePhone","check_code", 
				    "values", "day", "start_time_f",
					"end_time_f", "states",
					});
			this.export(wb, new Date().getTime() + ".xls"); 
		} catch (Exception e){
			e.printStackTrace();
		}
		 return null;
	}

}