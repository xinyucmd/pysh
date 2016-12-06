package com.sp2p.action.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.service.UserService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.AwardService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.AwardDetailService;

public class AwardAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Log log = LogFactory.getLog(AwardAction.class);
	private AwardService awardService;
	private AdminService adminService;
	private AwardDetailService awardDetailService;
	private UserService userService;

	private long uid;

	private List<Map<String, Object>> monthList;
	private String[] monthStr = new String[] { "", "一", "二", "三", "四", "五",
			"六", "七", "八", "九", "十", "十一", "十二" };

	/**
	 * 投资人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel23Init() throws Exception {
		request().setAttribute("level", IConstants.RELATION_LEVEL3);
		Long level2userId = request.getLong("level2userId", -1);
		// paramMap = awardService.queryLevel2Moneys(null, level2userId,null,
		// IConstants.RELATION_LEVEL3,null);
		request().setAttribute("level2userId", level2userId);
		return SUCCESS;
	}

	/**
	 * 理财人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel24Init() throws Exception {
		request().setAttribute("level", IConstants.RELATION_LEVEL4);
		Long level2userId = request.getLong("level2userId", -1);
		paramMap = awardService.queryLevel2Moneys(null, level2userId, null,
				IConstants.RELATION_LEVEL4, null);
		request().setAttribute("level2userId", level2userId);
		return SUCCESS;
	}

	/**
	 * 经济人提成列表
	 * 
	 * @return
	 */
	public String queryAwardLevel2Info() {
		String level2userName = request.getString("level2userName");
		String userName = request.getString("userName");
		String realName = request.getString("realName");
		int level = request.getInt("level", -1);
		long level2userId = request.getLong("level2Id", -1);
		request().setAttribute("level", level);
		try {
			awardService.queryAwardLevel2(null, level2userId, level2userName,
					userName, realName, level, pageBean);
			// 所有投资人奖励统计
			paramMap = awardService.querySumAwardLevel2(null, level2userId,
					level2userName, userName, realName, level);
			// 得到当前也的金额统计
			List<Map<String, Object>> list = pageBean.getPage();
			double coumtMomeys = 0;
			if (list != null) {
				for (Map<String, Object> map : list) {
					coumtMomeys = coumtMomeys
							+ Convert.strToDouble(map.get("level2moneys") + "",
									0);
				}
			}
			request().setAttribute("coumtMomeys", coumtMomeys);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 第一部分提成奖励,投资人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel23mxType1Init() throws Exception {
		long userId = request.getLong("userId", -1);
		long level2userId = request.getLong("level2userId", -1);
		request().setAttribute("userId", userId);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("level", IConstants.RELATION_LEVEL3);
		request().setAttribute("mxType", IConstants.MX_TYPE_MAX);
		paramMap = awardService.queryLevel2Moneys(null, level2userId, userId,
				IConstants.RELATION_LEVEL3, IConstants.MX_TYPE_MAX);
		return SUCCESS;
	}

	/**
	 * 第二部分提成奖励，投资人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel23mxType2Init() throws Exception {
		long userId = request.getLong("userId", -1);
		long level2userId = request.getLong("level2userId", -1);
		request().setAttribute("userId", userId);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("level", IConstants.RELATION_LEVEL3);
		request().setAttribute("mxType", IConstants.MX_TYPE_MIN);
		paramMap = awardService.queryLevel2Moneys(null, level2userId, userId,
				IConstants.RELATION_LEVEL3, IConstants.MX_TYPE_MIN);
		return SUCCESS;
	}

	/**
	 * 第一部分提成奖励,理财人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel24mxType1Init() throws Exception {
		long userId = request.getLong("userId", -1);
		long level2userId = request.getLong("level2userId", -1);
		request().setAttribute("userId", userId);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("level", IConstants.RELATION_LEVEL4);
		request().setAttribute("mxType", IConstants.MX_TYPE_MAX);
		paramMap = awardService.queryLevel2Moneys(null, level2userId, userId,
				IConstants.RELATION_LEVEL4, IConstants.MX_TYPE_MAX);
		return SUCCESS;
	}

	/**
	 * 第二部分提成奖励，理财人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel24mxType2Init() throws Exception {
		long userId = request.getLong("userId", -1);
		long level2userId = request.getLong("level2userId", -1);
		request().setAttribute("userId", userId);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("level", IConstants.RELATION_LEVEL4);
		request().setAttribute("mxType", IConstants.MX_TYPE_MIN);
		paramMap = awardService.queryLevel2Moneys(null, level2userId, userId,
				IConstants.RELATION_LEVEL4, IConstants.MX_TYPE_MIN);
		return SUCCESS;
	}

	public String queryAwardLevel2mxTypeInfo() {
		long level2userId = request.getLong("level2userId", -1);
		long userId = request.getLong("userId", -1);
		int month = request.getInt("month", -1);
		String startDate = request.getString("startDate");
		String endDate = request.getString("endDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int level = request.getInt("level", -1);
		int mxType = request.getInt("mxType", -1);
		request().setAttribute("level", level);
		request().setAttribute("mxType", mxType);
		if (StringUtils.isNotBlank(startDate)) {
			try {
				sdf.parse(startDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				startDate = null;
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			try {
				sdf.parse(endDate);
			} catch (Exception e) {
				log.info(e);
				e.printStackTrace();
				endDate = null;
			}
		}
		try {
			if (mxType == 2) {
				pageBean.setPageSize(12);
			} else {
				pageBean.setPageSize(10);
			}
			awardService.queryAwardLevel2mxType(level2userId, userId, mxType,
					startDate, endDate, month, level, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryIoRInit() throws Exception {
		long level2userId = request.getLong("level2userId", -1);
		long userId = request.getLong("userId", -1);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("userId", userId);
		request().setAttribute("level", request("level"));
		request().setAttribute("userName", request("userName"));
		// 通过id查询投资人姓名
		Map<String, String> userMap = userService.queryUserById(userId);
		String InvestorName = userMap.get("username");
		String t = request.getString("t");
		paramMap = awardService.queryIorMoneys(level2userId, userId,t);
		paramMap.put("InvestorName", InvestorName);
		request().setAttribute("level2userId", level2userId);		
		if(t.equals("2")){
		return "repay";
		}else{
			return SUCCESS;
		}
	}

	public String queryIoRInfo() {
		long userId = request.getLong("userId", -1);
		long level2userId = request.getLong("level2userId", -1);
		try {
			awardService.queryIoRInfo(userId, level2userId, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String queryIoRRepayInfo() {
		long userId = request.getLong("userId", -1);
		long level2userId = request.getLong("level2userId", -1);
		try {
			awardService.queryIoRRepayInfo(userId, level2userId, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 团队长统计
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardLevel1Init() throws Exception {
		long level1userId = request.getLong("level1userId", -1);
		// paramMap = awardService.queryLevel2Moneys(level1userId, null, null,
		// null, null);
		request().setAttribute("level1userId", level1userId);
		return SUCCESS;
	}

	/**
	 * 团队长 提成统计
	 * 
	 * @return
	 */
	public String queryAwardLevel1Info() {
		long level1 = request.getLong("level1Id", -1L);
		String level1userName = request.getString("level1userName");
		String level2userName = request.getString("level2userName");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			awardService.queryAwardLevel1(level1, level1userName,
					level2userName, pageBean, admin.getRoleId(), admin
							.getUserName());
			// 得到提成总计
			paramMap = awardService.queryAwardLevel1Sum(level1, level1userName,
					level2userName, admin.getRoleId(), admin.getUserName());
			// 得到当前页提成统计
			List<Map<String, Object>> list = pageBean.getPage();
			double summoney = 0;
			for (Map<String, Object> map : list) {
				summoney = summoney
						+ Convert.strToDouble(map.get("level1money3") + "", 0)
						+ Convert.strToDouble(map.get("level1money4") + "", 0);
			}
			request().setAttribute("summoney", summoney);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryAwardTinit() {
		return SUCCESS;
	}

	/**
	 *团队长
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAwardT() throws Exception {
		String level1userName = Convert.strToStr(
				paramMap.get("level1userName"), "");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long leve1userId = -1L;
		if (admin.getRoleId() == 1) {
			leve1userId = admin.getId();
		}
		try {
			awardService.queryAwardT(leve1userId, level1userName, pageBean);
			// 得到总合计
			paramMap = awardService.queryAwardTSum(leve1userId, level1userName);
			// 得到当前页合计
			List<Map<String, Object>> list = pageBean.getPage();
			double leve1moeney = 0;
			if (list != null) {
				for (Map<String, Object> map : list) {
					leve1moeney = leve1moeney
							+ Convert.strToDouble(map.get("level1money") + "",
									0);
				}
			}
			request().setAttribute("leve1moeneySum", leve1moeney);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 团队长统计 经纪人提成奖励
	 * 
	 * @param awardService
	 * @throws Exception
	 */

	public String queryLeve2AwardInit() throws Exception {
		long parentId = request.getLong("parentId", -1);
		// paramMap = awardService.queryLevel2AwardMoneys(parentId);
		request().setAttribute("parentId", parentId);
		return SUCCESS;
	}

	public String queryLeve2AwardInfo() {
		Integer enable = request.getInt("enable", -1);
		Long parentId = request.getLong("parentId", -1);
		try {
			awardService.queryLevel2Award(parentId, enable, pageBean);
			// 得到所有奖励合计
			paramMap = awardService.queryLevel2AwardSum(parentId, enable);
			// 得到当前页的合计
			double countMonmey = 0;
			List<Map<String, Object>> list = pageBean.getPage();
			if (list != null) {
				for (Map<String, Object> map : list) {
					countMonmey = countMonmey
							+ Convert.strToDouble(map.get("level2moneys") + "",
									0);
				}
			}
			request().setAttribute("countMonmey", countMonmey);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryLevel1level3Init() throws Exception {
		long level1userId = request.getLong("level1userId", -1);
		request().setAttribute("level1userId", level1userId);
		request().setAttribute("level", IConstants.RELATION_LEVEL3);
		// paramMap = awardService.queryLevel2Moneys(level1userId, null, null,
		// IConstants.RELATION_LEVEL3, null);
		return SUCCESS;
	}

	public String queryLevel1level4Init() throws Exception {
		long level1userId = request.getLong("level1userId", -1);
		request().setAttribute("level1userId", level1userId);
		request().setAttribute("level", IConstants.RELATION_LEVEL4);
		paramMap = awardService.queryLevel2Moneys(level1userId, null, null,
				IConstants.RELATION_LEVEL4, null);
		return SUCCESS;
	}

	@SuppressWarnings( { "deprecation", "deprecation", "deprecation",
			"deprecation" })
	public String queryLevel1level4Info() {
		long level1userId = request.getLong("level1userId", -1);
		String level2userName = request.getString("level2userName");
		Integer level = request.getInt("level", -1);
		Integer dateStatus = request.getInt("dateStatus", -1);
		String startDateStr = null;
		String endDateStr = null;
		if (dateStatus == 1) {// 有效期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endDate = new Date();
			endDateStr = sdf.format(endDate);
			endDate.setYear(endDate.getYear() - 1);
			startDateStr = sdf.format(endDate);
		} else if (dateStatus == 2) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endDate = new Date();
			endDate.setYear(endDate.getYear() - 1);
			endDateStr = sdf.format(endDate);
		}
		try {
			awardService.queryLevel1level34(level1userId, level2userName,
					level, startDateStr, endDateStr, pageBean);
			// 得合计项
			paramMap = awardService.queryLevel1level34Sum(level1userId,
					level2userName, level, startDateStr, endDateStr);
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null || list.size() <= 0) {
				return SUCCESS;
			}
			double countMoney = awardService.changeDateToStr(pageBean);
			request().setAttribute("countMoney", countMoney);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public List<Map<String, Object>> getMonthList() {
		if (monthList == null) {
			monthList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i <= 12; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (i == 0) {
					map.put("id", -1);
					map.put("name", "--请选择--");
				} else {
					map.put("id", i);
					map.put("name", "第" + monthStr[i] + "个月");
				}
				monthList.add(map);
			}
		}
		return monthList;
	}

	/**
	 * 查找所有的团队长
	 * 
	 * @return
	 */
	public String queryAllLevel1Init() {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (admin.getRoleId() == IConstants.RELATION_LEVEL1 // 团队长
				|| admin.getRoleId() == IConstants.RELATION_LEVEL2) {// 经纪人
			setUid(admin.getId());
			return "forwardTo";
		}
		return SUCCESS;
	}

	public String queryAllLevel1Info() {
		String userName = paramMap.get("userName");
		String realName = paramMap.get("realName");
		try {
			awardService.queryAllLevel1Info(userName, realName, pageBean,
					IConstants.RELATION_LEVEL1);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addAwardDetailInit() throws SQLException, DataException {
		Long userId = request.getString("userId") == null ? -100 : 
				request.getLong("userId", -100);
		try {
			Map<String, String> map = adminService.queryAdminById(userId);
			if (map != null && !map.isEmpty()) {
				paramMap.put("userId", userId + "");
				paramMap.put("userName", map.get("userName") + "("
						+ map.get("realName") + ")");
				paramMap.put("realName", map.get("realName"));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}

	public String addAwardDetail() throws Exception {
		Long userId = paramMap.get("userId") == null ? -100 : Convert
				.strToLong(paramMap.get("userId"), -100);
		double handleSum = paramMap.get("handleSum") == null ? 0 : Convert
				.strToDouble(paramMap.get("handleSum"), 0);
		Long checkId = paramMap.get("checkId") == null ? -100 : Convert
				.strToLong(paramMap.get("checkId"), -100);
		String remark = paramMap.get("remark");
		if (handleSum <= 0) {
			JSONUtils.printStr("3");
			return null;
		}

		long result = -1;
		try {
			Map<String, String> map = awardService.queryOneLevelInfo(userId);
			if (map == null || map.isEmpty()) {
				if (result <= 0) {// 添加失败
					JSONUtils.printStr("1");
					return null;
				}
			}
			double forPaySum = Convert.strToDouble(map.get("forPaySum"), 0);
			if (handleSum > forPaySum) {// 操作金额大于未结算金额
				JSONUtils.printStr("2");
				return null;
			}
			result = awardDetailService.addAwardDetail(userId, handleSum,
					checkId, new Date(), remark);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_award_detail", admin
					.getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
					"添加团队长或经纪人提成奖励", 2);

			if (result <= 0) {// 添加失败
				JSONUtils.printStr("1");
				return null;
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return null;
	}

	public String queryAwardDetailByUserIdInit() {
		Long userId = request.getString("userId") == null ? null : request.getLong("userId", -100);
		Map<String, String> map;
		try {
			map = awardService.queryOneLevelInfo(userId);
			request().setAttribute("map", map);
			request().setAttribute("userId", userId);

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryAwardDetailByUserId() {
		Long userId = request.getString("userId") == null ? null : request.getLong("userId", -100);
		String startTime = request("startTime");
		String endTime = request("endTime");
		endTime = FrontMyPaymentAction.changeEndTime(endTime);
		try {

			awardService.queryAwardDetailByUserId(userId, startTime, endTime,
					pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryAllLevel2Init() {
		return SUCCESS;
	}

	public String queryAllLevel2Info() {
		String userName = paramMap.get("userName");
		String realName = paramMap.get("realName");
		try {
			awardService.queryAllLevel1Info(userName, realName, pageBean,
					IConstants.RELATION_LEVEL2);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 团队长统计 经纪人提成明细
	 * 
	 * @param awardService
	 * @throws Exception
	 */

	public String queryLeve1AwardInit() throws Exception {
		long level2userId = request.getLong("level2userId", -1);
		long level1userId = request.getLong("level1userId", -1);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("level1userId", level1userId);
		return SUCCESS;
	}

	/**
	 * 提成明细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryLeve1AwardInfo() throws Exception {
		long level2userId = request.getLong("level2userId", -1);
		long level1userId = request.getLong("level1userId", -1);
		String username = request.getString("luserName");
		paramMap = awardService.queryLevel1Sum(level1userId, level2userId,
				username);
		awardService.queryLevel1AwardMoneys(level1userId, level2userId,
				username, pageBean);
		List<Map<String, Object>> list = pageBean.getPage();
		double sumMoney = 0;
		if (list != null) {
			for (Map<String, Object> map : list) {
				sumMoney = sumMoney
						+ Convert.strToDouble(map.get("level1money") + "", 0);
			}
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("sumMoney", sumMoney);
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("level2userId", level2userId);
		request().setAttribute("level1userId", level1userId);
		return SUCCESS;
	}

	/**
	 * 经济人奖励合计
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryLeve2SumCount() throws Exception {
		String leve2Name = Convert.strToStr(paramMap.get("level2userName"), "");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long leve2userId = -1L;
		if (admin.getRoleId() == 2) {
			leve2userId = admin.getId();
		}
		try {
			awardService.queryLeve2SumCount(leve2userId, leve2Name, pageBean);
			// 得到统计结果
			paramMap = awardService.queryLeve2CountToMap(leve2userId, leve2Name);
			// 得到当前页统计结果
			List<Map<String, Object>> leve2list = pageBean.getPage();
			double sumLeve2M = 0;
			if (leve2list != null) {
				for (Map<String, Object> map : leve2list) {
					sumLeve2M = sumLeve2M
							+ Convert.strToDouble(map.get("level2money") + "",
									0.0);
				}
			}
			request().setAttribute("sumLeve2M", sumLeve2M);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 经济人奖励初始化
	 * 
	 * @return
	 */
	public String queryLeve2SumInit() {
		return SUCCESS;
	}

	public String[] getMonthStr() {
		return monthStr;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public AwardDetailService getAwardDetailService() {
		return awardDetailService;
	}

	public void setAwardDetailService(AwardDetailService awardDetailService) {
		this.awardDetailService = awardDetailService;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setMonthList(List<Map<String, Object>> monthList) {
		this.monthList = monthList;
	}

	public void setMonthStr(String[] monthStr) {
		this.monthStr = monthStr;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
