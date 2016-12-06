package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.FrontMyHomeAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Operator;
import com.sp2p.entity.User;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.SelectedService;

public class MyHomeAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(FrontMyHomeAction.class);
	private static final long serialVersionUID = 1L;

	private MyHomeService myHomeService;
	private SelectedService selectedService;
	private List<Map<String, Object>> borrowDeadlineList;
	private Map<String, String> automaticBidMap;
	private List<Operator> checkList;

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午08:53:19
	 * @Return:
	 * @Descb: 我的主页初始化 // * @Throws: //
	 */
	// public String homeInit() throws SQLException, DataException, IOException
	// {
	// Map<String, Object> jsonMap = new HashMap<String, Object>();
	// User user = (User) session().getAttribute("user");
	// Map<String, String> homeMap = myHomeService.queryHomeInfo(user.getId());
	// request().setAttribute("homeMap", homeMap);
	// Map<String, String> accmountStatisMap = myHomeService
	// .queryAccountStatisInfo(user.getId());
	// request().setAttribute("accmountStatisMap", accmountStatisMap);
	// jsonMap.put("homeMap",homeMap);
	// jsonMap.put("accmountStatisMap",accmountStatisMap);
	// jsonMap.put("error", "-1");
	// jsonMap.put("msg", "初始化成功");
	// JSONUtils.printObject(jsonMap);
	// return null;
	// }

	/**
	 * @throws IOException
	 * @MethodName: homeBorrowPublishInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午08:53:29
	 * @Return:
	 * @Descb: 已经发布的借款初始化
	 * @Throws:
	 */
	public String homeBorrowPublishInit() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "初始化成功");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: loanStatisInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:57:19
	 * @Return:
	 * @Descb: 借款统计
	 * @Throws:
	 */
	public String loanStatisInit() throws Exception {
  	Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> appAuthMap = getAppAuthMap();
		String uid = appAuthMap.get("uid");
		if (StringUtils.isBlank(uid)) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "请先登陆");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		long userId = Convert.strToLong(uid, -1l);
		Map<String, String> loanStatisMap = myHomeService
				.queryLoanStatis(userId);
		jsonMap.put("loanStatisMap", loanStatisMap);
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "统计成功");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: financeStatisInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:57:31
	 * @Return:
	 * @Descb: 理财统计
	 * @Throws:
	 */
	public String financeStatisInit() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> appAuthMap = getAppAuthMap();
		String uid = appAuthMap.get("uid");
		if (StringUtils.isBlank(uid)) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "请先登陆");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		long userId = Convert.strToLong(uid, -1l);
		Map<String, String> financeStatisMap = myHomeService
				.queryFinanceStatis(userId);
		jsonMap.put("financeStatisMap", financeStatisMap);
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "统计成功");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	/**
	 * @MethodName: homeBorrowBackAcount
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 上午09:12:22
	 * @Return:
	 * @Descb: 查询借款回账
	 * @Throws:
	 */
	public String homeBorrowBackAcount() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			String title = appInfoMap.get("title") == null ? "" : appInfoMap
					.get("title");
			String publishTimeStart = appInfoMap.get("publishTimeStart") == null ? ""
					: appInfoMap.get("publishTimeStart");
			if (StringUtils.isNotBlank(publishTimeStart)) {
				publishTimeStart = publishTimeStart + " 00:00:00";
			}
			String publishTimeEnd = appInfoMap.get("publishTimeEnd") == null ? ""
					: appInfoMap.get("publishTimeEnd");
			if (StringUtils.isNotBlank(publishTimeStart)) {
				publishTimeEnd = publishTimeEnd + " 23:59:59";
			}
			Map<String, String> map = myHomeService.queryBackAcountStatis(
					userId, publishTimeStart, publishTimeEnd, title);
			String allForPIOneMonth = map.get("allForPIOneMonth") == null ? "0"
					: map.get("allForPIOneMonth");
			String allForPIThreeMonth = map.get("allForPIThreeMonth") == null ? "0"
					: map.get("allForPIThreeMonth");
			String allForPIYear = map.get("allForPIYear") == null ? "0" : map
					.get("allForPIYear");
			String allForPI = map.get("allForPI") == null ? "0" : map
					.get("allForPI");
			jsonMap.put("allForPIOneMonth", allForPIOneMonth);
			jsonMap.put("allForPIThreeMonth", allForPIThreeMonth);
			jsonMap.put("allForPIYear", allForPIYear);
			
			jsonMap.put("allForPI", allForPI);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeBorrowInvestList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:40:13
	 * @Return:
	 * @Descb: 投资借款列表
	 * @Throws:
	 */
	public String homeBorrowInvestList() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);

			pageBean.setPageNum(appInfoMap.get("curPage"));

			String type = "1";
			String borrowStatus = "";
			if ("1".equals(type)) {
				borrowStatus = IConstants.BORROW_STATUS_4 + ","
						+ IConstants.BORROW_STATUS_5;
			} else if ("2".equals(type)) {
				borrowStatus = "" + IConstants.BORROW_STATUS_2;
			}
			String title = appInfoMap.get("title");
			String publishTimeStart = appInfoMap.get("publishTimeStart");
			String publishTimeEnd = appInfoMap.get("publishTimeEnd");
			myHomeService.queryBorrowInvestByCondition(title, publishTimeStart,
					publishTimeEnd, borrowStatus, userId, pageBean, 0);
			// this.setRequestToParamMap();
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeBorrowInvestList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:40:13
	 * @Return:
	 * @Descb: 招标中的借款
	 * @Throws:
	 */
	public String homeBorrowTenderInList() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);

			pageBean.setPageNum(appInfoMap.get("curPage"));
			String borrowStatus = IConstants.BORROW_STATUS_2 + "";

			String title = appInfoMap.get("title");
			String publishTimeStart = appInfoMap.get("publishTimeStart");
			String publishTimeEnd = appInfoMap.get("publishTimeEnd");
			myHomeService.queryBorrowInvestByCondition(title, publishTimeStart,
					publishTimeEnd, borrowStatus, userId, pageBean, 1);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeBorrowRecycleList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:41:47
	 * @Return:
	 * @Descb: 待回收借款列表
	 * @Throws:
	 */
	public String homeBorrowRecycleList() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			pageBean.setPageNum(appInfoMap.get("curPage"));
			String title = appInfoMap.get("title");
			myHomeService
					.queryBorrowRecycleByConditionApp(title, userId, pageBean);
			// this.setRequestToParamMap();
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: homeBorrowRecycledList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午01:40:27
	 * @Return:
	 * @Descb: 已回收的借款
	 * @Throws:
	 */
	public String homeBorrowRecycledList() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			pageBean.setPageNum(appInfoMap.get("curPage"));
			String title = appInfoMap.get("title");
			myHomeService.queryBorrowRecycledByCondition(title, userId,
					pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: homeBorrowForpayDetail
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:53:03
	 * @Return:
	 * @Descb: 查询投资人回收中借款还款详情
	 * @Throws:
	 */
	public String homeBorrowForpayDetail() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			String id = appInfoMap.get("id") == null ? "" : appInfoMap
					.get("id");
			// add by houli
			String iid = appInfoMap.get("iid") == null ? "" : appInfoMap
					.get("iid");
			long idLong = Convert.strToLong(id, -1);
			long iidLong = Convert.strToLong(iid, -1);
			
			//String date = appInfoMap.get("date");
			List<Map<String, Object>> listMap = myHomeService
			.queryBorrowForpayById(iidLong, userId,idLong);
			
			
			  DecimalFormat df_two = new DecimalFormat("#0.00"); 
			  for(Map<String,Object> map : listMap ){
			  
				  map.put("forpayPrincipal", df_two.format(
				  map.get("forpayPrincipal"))); map.put("forpayInterest",
				  df_two.format( map.get("forpayInterest")));
				  map.put("principalBalance", df_two.format(
				  map.get("principalBalance"))); map.put("manage", df_two.format(
				  map.get("manage"))); map.put("earn", String.format("%.2f",
				  map.get("earn"))); 
			  } //
			 
			jsonMap.put("listMap", listMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: homeBorrowHaspayDetail
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:57:20
	 * @Return:
	 * @Descb: 查询投资人已回收借款还款详情
	 * @Throws:
	 */
	public String homeBorrowHaspayDetail() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			String id = appInfoMap.get("id") == null ? "" : appInfoMap
					.get("id");
			String iid = appInfoMap.get("iid") == null ? "" : appInfoMap
					.get("iid");

			long idLong = Convert.strToLong(id, -1);
			long iidLong = Convert.strToLong(iid, -1);
			List<Map<String, Object>> listMap = myHomeService
					.queryBorrowHaspayById(idLong, userId, iidLong);
			jsonMap.put("listMap", listMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeBorrowBackAcountList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:43:24
	 * @Return:
	 * @Descb: 借款回账查询列表
	 * @Throws:
	 */
	public String homeBorrowBackAcountList() throws SQLException,
			DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			Map<String, String> backAcountStatisMap = myHomeService
					.queryBackAcountStatis(userId, "", "", "");
			// 回账类型

			pageBean.setPageNum(appInfoMap.get("curPage"));
			String title = appInfoMap.get("title");
			String publishTimeStart = appInfoMap.get("publishTimeStart");
			String publishTimeEnd = appInfoMap.get("publishTimeEnd");
			myHomeService.queryBorrowBackAcountByCondition(title,
					publishTimeStart, publishTimeEnd, userId, pageBean);

			jsonMap.put("backAcountStatisMap", backAcountStatisMap);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("type", "5");
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeBorrowPublishList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午09:03:01
	 * @Return:
	 * @Descb: 审核中的借款
	 * @Throws:
	 */
	public String homeBorrowAuditList() throws SQLException, DataException,
			IOException {
		String borrowStatus = IConstants.BORROW_STATUS_1 + ","
				+ IConstants.BORROW_STATUS_3;
		return queryBrrowPublishList(borrowStatus);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: homeBorrowPublishList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午09:03:01
	 * @Return:
	 * @Descb: 招标中的借款列表
	 * @Throws:
	 */
	public String homeBorrowingList() throws SQLException, DataException,
			IOException {
		String borrowStatus = "" + IConstants.BORROW_STATUS_2;
		return queryBrrowPublishList(borrowStatus);
	}

	private String queryBrrowPublishList(String borrowStatus)
			throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);
			pageBean.setPageNum(appInfoMap.get("curPage"));
			pageBean.setPageSize(1);
			String title = appInfoMap.get("title");
			String publishTimeStart = appInfoMap.get("publishTimeStart");
			String publishTimeEnd = appInfoMap.get("publishTimeEnd");
			myHomeService.queryBorrowFinishByCondition(title, publishTimeStart,
					publishTimeEnd, borrowStatus, userId, pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException 
	 * @MethodName: automaticBidInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:53
	 * @Return:
	 * @Descb: 查询用户自动投标设置
	 * @Throws:
	 */
	public String automaticBidInit() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (automaticBidMap == null) {
				// 自动投标设置
				Map<String, String> appAuthMap = getAppAuthMap();
				String uid = appAuthMap.get("uid");
				if(StringUtils.isBlank(uid)){
					jsonMap.put("error", "2");
					jsonMap.put("msg", "请先登陆");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				long userId = Convert.strToLong(uid, -1l);	
				automaticBidMap = myHomeService.queryAutomaticBid(userId);
				checkList = new ArrayList<Operator>();				
				if(automaticBidMap !=null){
					//设置ckBoxList的选中值
					String borrowWay = automaticBidMap.get("borrowWay");
					String[] ckList = borrowWay.split(",");
					if(ckList.length > 0){
						for(String ck:ckList){
							checkList.add(new Operator(ck, ""));						
						}
					}
				}
			}
			jsonMap.put("automaticBidMap", automaticBidMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: automaticBidSet
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:33:53
	 * @Return:
	 * @Descb: 自动投标设置
	 * @Throws:
	 */
	public String automaticBidSet() throws IOException, SQLException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String bidStatus = appInfoMap.get("bidStatus") == null ? "1" : appInfoMap.get("bidStatus");
			long bidStatusLong = Convert.strToLong(bidStatus, 1);
			JSONObject obj = new JSONObject();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if(StringUtils.isBlank(uid)){
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);	
			long returnId = -1;
			returnId = myHomeService.automaticBidSet(bidStatusLong, userId);
	
			if (returnId <= 0) {
				obj.put("msg", "未保存自动投标设置");
				jsonMap.put("error", "1");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
		

	/**
	 * @throws DataException
	 * @MethodName: automaticBidModify
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:04:24
	 * @Return:
	 * @Descb: 修改自动投标内容
	 * @Throws:
	 */
	public String automaticBidModify() throws SQLException, IOException,
			DataException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			if(StringUtils.isBlank(uid)){
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请先登陆");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(uid, -1l);	
			long returnId = -1;
			String bidAmount = appInfoMap.get("bidAmount") == null ? "" : appInfoMap
					.get("bidAmount");
			String rateStart = appInfoMap.get("rateStart") == null ? "" : appInfoMap
					.get("rateStart");
			String rateEnd = appInfoMap.get("rateEnd") == null ? "" : appInfoMap
					.get("rateEnd");
			String deadlineStart = appInfoMap.get("deadlineStart") == null ? ""
					: appInfoMap.get("deadlineStart");
			String deadlineEnd = appInfoMap.get("deadlineEnd") == null ? ""
					: appInfoMap.get("deadlineEnd");
			String creditStart = appInfoMap.get("creditStart") == null ? ""
					: appInfoMap.get("creditStart");
			String creditEnd = appInfoMap.get("creditEnd") == null ? "" : appInfoMap
					.get("creditEnd");
			String remandAmount = appInfoMap.get("remandAmount") == null ? ""
					: appInfoMap.get("remandAmount");
			String borrowWay = appInfoMap.get("borrowWay") == null ? ""
					: appInfoMap.get("borrowWay");
			Double bidAmountDouble = Convert.strToDouble(bidAmount, 0);
			Double rateStartDouble = Convert.strToDouble(rateStart, 0);
			Double rateEndDouble = Convert.strToDouble(rateEnd, 0);
			Double deadlineStartDouble = Convert.strToDouble(deadlineStart, 0);
			Double deadlineEndDouble = Convert.strToDouble(deadlineEnd, 0);
			Double creditStartDouble = Convert.strToDouble(creditStart, 0);
			Double creditEndDouble = Convert.strToDouble(creditEnd, 0);
			Double remandAmountDouble = Convert.strToDouble(remandAmount, 0);
			if (StringUtils.isBlank(bidAmount)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "每次投标金额不可为空");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (bidAmountDouble == 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "每次投标金额格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}else if(bidAmountDouble < 50){
				jsonMap.put("error", "3");
				jsonMap.put("msg", "每次投标金额不能低于50元");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (StringUtils.isBlank(rateStart)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "利率范围开始不可为空");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (rateStartDouble == 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "利率范围开始格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (rateStartDouble < 0.1 || rateStartDouble > 24) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "利率范围0.1%~24%");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (StringUtils.isBlank(rateEnd)) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "利率范围结束不可为空");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (rateEndDouble == 0) {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "利率范围结束格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (rateEndDouble < 0.1 || rateEndDouble > 24) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "利率范围0.1%~24%");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (StringUtils.isBlank(remandAmount)) {
				jsonMap.put("error", "10");
				jsonMap.put("msgjsonMap","账户保留金额不可为空");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (remandAmountDouble == 0) {
				jsonMap.put("error", "11");
				jsonMap.put("msg", "账户保留金额格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (StringUtils.isBlank(borrowWay)) {
				jsonMap.put("error", "12");
				jsonMap.put("msg", "请勾选借款类型");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			returnId = myHomeService.automaticBidModify(bidAmountDouble,
					rateStartDouble, rateEndDouble, deadlineStartDouble,
					deadlineEndDouble, creditStartDouble, creditEndDouble,
					remandAmountDouble, userId,borrowWay);
	
			if (returnId <= 0) {
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				jsonMap.put("error", "13");
			} else {
				jsonMap.put("msg", IConstants.ACTION_SUCCESS);
				jsonMap.put("error", "-1");
			}
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "14");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		if (borrowDeadlineList == null) {
			// 借款期限列表
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public Map<String, String> getAutomaticBidMap() throws Exception {
		if (automaticBidMap == null) {
			// 自动投标设置
			User user = (User) session().getAttribute("user");
			automaticBidMap = myHomeService.queryAutomaticBid(user.getId());
			checkList = new ArrayList<Operator>();
			if (automaticBidMap != null) {
				// 设置ckBoxList的选中值
				String borrowWay = automaticBidMap.get("borrowWay");
				String[] ckList = borrowWay.split(",");
				if (ckList.length > 0) {
					for (String ck : ckList) {
						checkList.add(new Operator(ck, ""));
					}
				}
			}
		}
		return automaticBidMap;
	}
	
	public List<Operator> getCheckList() {
		return checkList;
	}
	
	public String bespeakInvest() throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, String> appAuthMap = getAppAuthMap();
		long userId = Convert.strToLong(appAuthMap.get("uid"), -1);
		if(userId==-1){
			obj.put("msg", "您未登录");
			JSONUtils.printObject(obj);
			return null;
		}
		try {
			List<Map<String, Object>> list = myHomeService.queryAppointInvestRecond(userId);
			pageBean.setPage(list);
			obj.put("pageBean", pageBean);
		 
			Map<String, String> m = new LinkedHashMap<String, String>();
			m.put("3", "7");
			m.put("6", "8.5");
			m.put("12", "10");
			obj.put("RATE", m);
			
			//标的类型
			Map<String, String> typeMap = new LinkedHashMap<String, String>();
			typeMap.put("定息宝", "4");
			obj.put("TYPE",typeMap);
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtils.printObject(obj);
		return null;
	}
	
	
	
	/**
	 * 预约投标首页
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String bespeakInvest2_0() throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, String> appAuthMap = getAppAuthMap();
		long userId = Convert.strToLong(appAuthMap.get("uid"), -1);
		if(userId==-1){
			obj.put("msg", "您未登录");
			JSONUtils.printObject(obj);
			return null;
		}
		try {
			List<Map<String, Object>> list = myHomeService.queryAppointInvestRecond(userId);
			pageBean.setPage(list);
			obj.put("pageBean", pageBean);
			
			 //还款方式
			Map<String, String> repayMothedsMap = new LinkedHashMap<String, String>();
			repayMothedsMap.put("repayMotheds_1", "1");
			repayMothedsMap.put("repayMotheds_2", "2");
			obj.put("repayMothedsMap", repayMothedsMap);
			
			//a类标期限对应利率--还款方式：先息后本
			Map<String, String> aMap = new LinkedHashMap<String, String>();
			aMap.put("2", "6.5");
			aMap.put("3", "7");
			aMap.put("6", "8.5");
			aMap.put("12", "10");
			aMap.put("18", "10.5");
			aMap.put("24", "11");
			aMap.put("36", "12");
			obj.put("aMap", aMap);
			
			//b类标期限对应利率--还款方式：利随本清
			Map<String, String> bMap = new LinkedHashMap<String, String>();
			bMap.put("2", "6.6");
			bMap.put("3", "7.5");
			bMap.put("6", "9");
			bMap.put("12", "10.5");
			bMap.put("18", "11");
			bMap.put("24", "12");
			bMap.put("36", "14");
			obj.put("bMap", bMap);

			
			//标的类型
			Map<String, String> typeMap = new LinkedHashMap<String, String>();
			typeMap.put("定息宝", "4");
			obj.put("TYPE",typeMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtils.printObject(obj);
		return null;
	}
	
	/**
	 * 添加自动投标
	 * @return
	 */
	public String addAppointInvest()throws Exception{
		
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> appAuthMap = getAppAuthMap();
		long userId = Convert.strToLong(appAuthMap.get("uid"), -1);
		if(userId==-1){
			obj.put("msg", "您未登录");
			JSONUtils.printObject(obj);
			return null;
		}
		
		int type = 4;
				//Convert.strToInt(appInfoMap.get("type"), 0);//4-定息宝
		int state = 0;//未投资
		int limits = Convert.strToInt(appInfoMap.get("limits"), 0);//期限
		double rate = Convert.strToDouble(appInfoMap.get("rate"), 0);//年化收益率
		String moeny = appInfoMap.get("amount");
		double amount = Convert.strToDouble(appInfoMap.get("amount"), 0);//预约金额
		double usableSum = Convert.strToDouble(appInfoMap.get("usableSum"), 0);//用户可用余额
		boolean flag = moeny.matches("^[1-9][0-9]*$");
		if(!flag){
			obj.put("msg", "预约金额必须是整数");
			JSONUtils.printObject(obj);
			return null;
		}
		
		if(amount<1000 || amount>100000){
			obj.put("msg", "预约金额在1000~100000元");
			JSONUtils.printObject(obj);
			return null;
		}
		
		
		if(amount-usableSum>0){
			obj.put("msg", "预约金额不能大于可用余额");
			JSONUtils.printObject(obj);
			return null;
		}
		
		long m = -1;
		try {
			Map<String, String> map = myHomeService.queryAppointInvest(userId);
			if(map!=null && map.size()>0){
				obj.put("msg", "您已经预约过了，请等待系统投标");
				JSONUtils.printObject(obj);
				return null;
			}
			
			m = myHomeService.addAppointInvest(type, limits, rate, amount, state, userId);
			if (m > 0) {
				obj.put("m", "1");
				obj.put("msg", "预约成功");
			} else {
				obj.put("msg", "预约失败");
			}
			
		JSONUtils.printObject(obj);
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除预约投标
	 * @return
	 * @throws Exception
	 */
	public String removeAppointInvest()throws Exception{
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> appAuthMap = getAppAuthMap();
		long userId = Convert.strToLong(appAuthMap.get("uid"), -1);
		if(userId==-1){
			obj.put("msg", "您未登录");
			JSONUtils.printObject(obj);
			return null;
		}
		long id = Convert.strToLong(appInfoMap.get("id"), 0);//期限
		try {
		   long m = myHomeService.removeAppointInvest(id);
		   if(m>0){
			   obj.put("m", "1");
			   obj.put("msg", "取消成功");
		   }else{
			   obj.put("msg", "取消失败");
		   }
		} catch (Exception e) {
			 e.printStackTrace();
		}
		JSONUtils.printObject(obj);
		return null;
	}
	

}
