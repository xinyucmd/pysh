package com.sp2p.action.front;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SMSUtil;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.ExcelUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.FinanceService;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.SendSMSService;
import com.sp2p.service.brokerage.BrokerageIDRecognition;
import com.sp2p.util.DateUtil;

public class FrontMyPaymentAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;
	private FrontMyPaymentService frontpayService;
	private UserService userService;
	private MyHomeService myHomeService;
	private BrokerageIDRecognition brokerageIDRecognition;
	private FinanceService financeService;
	private SendSMSService sendSMSService;
	
	public SendSMSService getSendSMSService() {
		return sendSMSService;
	}

	public void setSendSMSService(SendSMSService sendSMSService) {
		this.sendSMSService = sendSMSService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public BrokerageIDRecognition getBrokerageIDRecognition() {
		return brokerageIDRecognition;
	}

	public void setBrokerageIDRecognition(
			BrokerageIDRecognition brokerageIDRecognition) {
		this.brokerageIDRecognition = brokerageIDRecognition;
	}

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public FrontMyPaymentService getFrontpayService() {
		return frontpayService;
	}

	public void setFrontpayService(FrontMyPaymentService frontpayService) {
		this.frontpayService = frontpayService;
	}
	
	
	/**
	 * 员工投资
	 * @return
	 */
	 public String addEmployeeInvest(){
	    	User user = (User) session().getAttribute("user");
			long userId=user.getId();
			JSONObject json = new JSONObject();
			long borrowId = Convert.strToLong(request().getParameter("borrowId"), 0);
			int fs = Convert.strToInt(request().getParameter("fs"), 0);//份数
			
			 String imgCode = Convert.strToStr(request("code"), "");
		     String pageId = Convert.strToStr(request("pageId"), ""); // 验证码
		     String code = (String) session().getAttribute(pageId + "_checkCode");
			 try {
				if(userId<0){
					json.put("msg", "未登陆");
					JSONUtils.printObject(json);
					return null;
				}
				
				if("".equals(imgCode)){
					json.put("msg", "请输入图片验证码");
					JSONUtils.printObject(json);
					return null;
				}
				
				if(!code.equals(imgCode)){
					json.put("msg", "图片验不正确");
					JSONUtils.printObject(json);
					return null;
				}
				
				 
				Map<String,String> map  = financeService.addEmployeeInvest(userId, borrowId, fs);
				json.put("msg", map.get("msg")); 
				json.put("status", map.get("status")); 
			    JSONUtils.printObject(json);
			    
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	    
	
	/**
	 * 体验金投资
	 * @return
	 */
	 public String addTyjInvest(){
	    	User user = (User) session().getAttribute("user");
			long userId=user.getId();
			JSONObject json = new JSONObject();
			long borrowId = Convert.strToLong(request().getParameter("borrowId"), 0);
			
			 String imgCode = Convert.strToStr(request("code"), "");
		     String pageId = Convert.strToStr(request("pageId"), ""); // 验证码
		     String code = (String) session().getAttribute(pageId + "_checkCode");
			 try {
				if(userId<0){
					json.put("msg", "未登陆");
					JSONUtils.printObject(json);
					return null;
				}
				
				if("".equals(imgCode)){
					json.put("msg", "请输入图片验证码");
					JSONUtils.printObject(json);
					return null;
				}
				
				if(!code.equals(imgCode)){
					json.put("msg", "图片验不正确");
					JSONUtils.printObject(json);
					return null;
				}
				
				 
				Map<String,String> map  = financeService.addTyjInvest(userId, borrowId);
				json.put("msg", map.get("msg")); 
			    JSONUtils.printObject(json);
			    
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	    

	/**
	 * 成功的借款 
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMySuccessBorrowList() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		Map<String, String> accmountStatisMap = myHomeService
		.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		pageBean.setPageNum(request.getString("curPage"));
		String startTime = Convert.strToStr(request.getString("startTime"), null);
		String endTime = Convert.strToStr(request.getString("endTime"), null);
		String title = Convert.strToStr(request.getString("title"), null);
		
		int borrowStatus = request.getString("borrowStatus") == null ? -1 : request.getInt("borrowStatus", -1);
		endTime = changeEndTime(endTime);

		

		if (borrowStatus == -1) {
			frontpayService.queryMySuccessBorrowList(pageBean, userId,
					startTime, endTime, title, -1);
		} else {// 还款中的借款 已还完的借款
			frontpayService.queryMySuccessBorrowList(pageBean, userId,
					startTime, endTime, title, borrowStatus);
		}
		DesSecurityUtil des = new DesSecurityUtil();
		List<Map<String, Object>> lists = pageBean.getPage();

		if (lists != null) {
			for (Map<String, Object> map : lists) {
				if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				}else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}

				if (map.get("borrowStatus").toString().equals(
						IConstants.BORROW_STATUS_4 + "")) {
					map.put("borrowStatus", IConstants.BORROW_STATUS_4_STR);
				} else if (map.get("borrowStatus").toString().equals(
						IConstants.BORROW_STATUS_5 + "")) {
					map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
				}
				String borrowId = map.get("id")+"";
				String encBorrowId = des.encrypt(borrowId);
				map.put("encBorrowId", encBorrowId);
				String typeId = des.encrypt("15");
				map.put("encTypeId", typeId);
			}
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}
	
	/**
	 * 导出成功借款,正在还款的借款，已还借款 的数据excel
	 * @return
	 */
	public String exportSuccessBorrow(){
		
		Long userId = this.getUserId();// 获得用户编号
		Integer status = request.getInt("status", -1);
		pageBean.pageNum = 1;
		pageBean.setPageSize(5000);
		try {               
			String startTime=Convert.strToStr(request.getString("startTime"),null);
			String endTime=Convert.strToStr(request.getString("endTime"),null);
			endTime = changeEndTime(endTime);
			String title =Convert.strToStr(request.getString("title"),null);
			//中文乱码转换
			if(StringUtils.isNotBlank(title)){
				title = URLDecoder.decode(title,"UTF-8");
			}
			//成功借款
			frontpayService.queryMySuccessBorrowList(pageBean, userId,
					startTime, endTime, title, status);
			if(pageBean.getPage()==null)
			{
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return  null;
			}
			if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
			{
			getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
			return  null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			if (list != null) {
				for (Map<String, Object> map : list) {
					if (map.get("borrowWay").toString().equals(
							IConstants.BORROW_TYPE_NET_VALUE)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
					} else if (map.get("borrowWay").toString().equals(
							IConstants.BORROW_TYPE_SECONDS)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
					} else if (map.get("borrowWay").toString().equals(
							IConstants.BORROW_TYPE_GENERAL)) {
						map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
					} else if (map.get("borrowWay").toString().equals(
							IConstants.BORROW_TYPE_FIELD_VISIT)) {
						map
								.put("borrowWay",
										IConstants.BORROW_TYPE_FIELD_VISIT_STR);
					} else if (map.get("borrowWay").toString().equals(
							IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
						map.put("borrowWay",
								IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
					}

					if (map.get("borrowStatus").toString().equals(
							IConstants.BORROW_STATUS_4 + "")) {
						map.put("borrowStatus", IConstants.BORROW_STATUS_4_STR);
					} else if (map.get("borrowStatus").toString().equals(
							IConstants.BORROW_STATUS_5 + "")) {
						map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
					}
				}
			}
			HSSFWorkbook wb=null;
			if(status==-1){
				wb = ExcelUtils.exportExcel("成功借款", pageBean.getPage(),
						new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间",
						          "偿还本息(￥)","已还本息(￥)","未还本息(￥)",
								"状态" }, new String[] { "borrowTitle", "borrowWay",
								"borrowAmount", "annualRate", "deadline",
								"publishTime", "stillTotalSum","hasPI","hasSum","borrowStatus" });
			}else if(status==4){
				wb = ExcelUtils.exportExcel("正在还款的借款", pageBean.getPage(),
						new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间",
						          "偿还本息(￥)","已还本息(￥)","未还本息(￥)"
								 }, new String[] { "borrowTitle", "borrowWay",
								"borrowAmount", "annualRate", "deadline",
								"publishTime", "stillTotalSum","hasPI","hasSum" });
			}else if(status==5){
				
				wb = ExcelUtils.exportExcel("已还完的借款", pageBean.getPage(),
						new String[] { "标题", "借款类型", "借款金额(￥)", "年利率(%)", "还款期限(月)", "借款时间",
						          "偿还本息(￥)","已还本息(￥)","已还逾期罚息(￥)"
								 }, new String[] { "borrowTitle", "borrowWay",
								"borrowAmount", "annualRate", "deadline",
								"publishTime", "stillTotalSum","stillTotalSum","hasFI" });
			}
			
			this.export(wb, new Date().getTime() + ".xls");
			
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
	 * 正在还款的借款
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMyPayingBorrowList() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		Map<String, String> accmountStatisMap = myHomeService
		.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		pageBean.setPageNum(request.getString("curPage"));
		String startTime = Convert.strToStr(request.getString("startTime"), null);
		String endTime = Convert.strToStr(request.getString("endTime"), null);
		String title = Convert.strToStr(request.getString("title"), null);
		/*int borrowStatus = paramMap.get("borrowStatus") == null ? -1 : Convert
				.strToInt(paramMap.get("borrowStatus"), -1);*/
		
		int borrowStatus = IConstants.BORROW_STATUS_4;
		endTime = changeEndTime(endTime);

		if (borrowStatus == -1) {
			frontpayService.queryMySuccessBorrowList(pageBean, userId,
					startTime, endTime, title, -1);
		} else {// 还款中的借款 已还完的借款
			frontpayService.queryMySuccessBorrowList(pageBean, userId,
					startTime, endTime, title, borrowStatus);
		}

		List<Map<String, Object>> lists = pageBean.getPage();
		DesSecurityUtil des = new DesSecurityUtil();
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				}else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}

				String borrowId = map.get("id")+"";
				String encBorrowId = des.encrypt(borrowId);
				map.put("encBorrowId", encBorrowId);
				String typeId = des.encrypt("15");
				map.put("encTypeId", typeId);
				
			}
		}
		
		this.setRequestToParamMap();
		return SUCCESS;
	}
	
	/**
	 * 已还完借款
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMyPayoffBorrowList() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(request.getString("curPage"));
		String startTime = request.getString("startTime");
		String endTime = request.getString("endTime");
		String title = request.getString("title");
		int borrowStatus = IConstants.BORROW_STATUS_5;
		endTime = changeEndTime(endTime);
		Map<String, String> accmountStatisMap = myHomeService
		.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		frontpayService.queryMySuccessBorrowList(pageBean, userId,
					startTime, endTime, title, borrowStatus);
		
		List<Map<String, Object>> lists = pageBean.getPage();
		DesSecurityUtil des = new DesSecurityUtil();
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map
							.put("borrowWay",
									IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay",
							IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				}else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay",
							IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}

				map.put("borrowStatus", IConstants.BORROW_STATUS_5_STR);
				String borrowId = map.get("id")+"";
				String encBorrowId = des.encrypt(borrowId);
				map.put("encBorrowId", encBorrowId);
				String typeId = des.encrypt("15");
				map.put("encTypeId", typeId);
			}
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 正在还款的借款详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryPayingDetails() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		Long borrowId = paramMap.get("borrowId") == null ? -1 : Convert.strToLong(paramMap.get("borrowId"), -1);// 
		
		int status = -1;
		if(paramMap.get("status") != null){
			status = Convert.strToInt(paramMap.get("status"), -1);
		}
		// 获得统计信息
		Map<String, String> map = null;

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);

		if(borrowId == -1){
			return null;
		}
		frontpayService.queryPayingDetails(pageBean, borrowId,status);
		map = frontpayService.queryOneBorrowInfo(userId,
				borrowId);
		
		List<Map<String, Object>> lists = pageBean.getPage();
		if (lists != null) {
			for (Map<String, Object> mp : lists) {
				if (Convert.strToInt(mp.get("repayStatus").toString(), -1) == IConstants.PAYING_STATUS_NON) {
					mp.put("repayStatus", IConstants.PAYING_STATUS_NON_STR);
				} else if (Convert.strToInt(mp.get("repayStatus").toString(),
						-1) == IConstants.PAYING_STATUS_PAYING) {
					mp.put("repayStatus", IConstants.PAYING_STATUS_PAYING_STR);
				}else if(Convert.strToInt(mp.get("repayStatus").toString(),
						-1) == IConstants.PAYING_STATUS_SUCCESS){//已偿还完
					mp.put("repayStatus", IConstants.PAYING_STATUS_SUCCESS_STR);
				}
			}
		}

		// map 首次加载时，为Null
		if(map != null){
			request().setAttribute("borrowTitle", map.get("borrowTitle"));
			request().setAttribute("borrowAmount", map.get("borrowAmount"));
			request().setAttribute("annualRate", map.get("annualRate"));
			request().setAttribute("deadline", map.get("deadline"));
			request().setAttribute("isDayThe", map.get("isDayThe"));
			request().setAttribute("paymentMode", map.get("paymentMode"));
			request().setAttribute("publishTime", map.get("publishTime"));
			request().setAttribute("auditTime", map.get("auditTime").split("\\s+")[0]);
		}
		
		//当前服务器时间
		String currDate = DateUtil.dateToStringYYMMDD(new Date());
		request().setAttribute("currDate", currDate);
		
		return SUCCESS;
	}

	/**
	 * 还款明细账
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryAllDetails() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		Map<String, String> accmountStatisMap = myHomeService
		.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		pageBean.setPageNum(request.getString("curPage"));
		String startTime = Convert.strToStr(request.getString("startTime"), null);
		String endTime = Convert.strToStr(request.getString("endTime"), null);
		String title = Convert.strToStr(request.getString("title"), null);
		endTime = changeEndTime(endTime);

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);

		frontpayService.queryAllDetails(pageBean, userId, startTime, endTime,
				title);
		this.setRequestToParamMap();
		
		//当前系统时间
		String currDate = DateUtil.dateToStringYYMMDD(new Date());
		request().setAttribute("currDate", currDate);
		return SUCCESS;
	}
	
	/**
	 * 还款明细账，的数据导出excel文件
	 * @return
	 */
	public String exportrepayment(){
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.pageNum = 1;
		pageBean.setPageSize(5000);
		try {
			//还款明细账
			String startTime=Convert.strToStr(request.getString("startTime"),null);
			String endTime=Convert.strToStr(request.getString("endTime"),null);
			endTime = changeEndTime(endTime);
			String title =Convert.strToStr(request.getString("title"),null);
			//中文乱码转换
			if(StringUtils.isNotBlank(title)){
				title = URLDecoder.decode(title,"UTF-8");
			}
			frontpayService.queryAllDetails(pageBean, userId, startTime, endTime,
					title);
			if(pageBean.getPage()==null)
			{
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return  null;
			}
			if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
			{
			getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
			return  null;
			}
			List<Map<String, Object>> list = pageBean.getPage();
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			if (list != null) {
				for (Map<String, Object> map : list) {		

					if (map.get("repayStatus").toString().equals(
							1+ "")) {
						map.put("repayStatus","未偿还");
					} else  {
						map.put("repayStatus","已偿还");
					}
				}
			}
			
			
			HSSFWorkbook wb= ExcelUtils.exportExcel("还款明细账", pageBean.getPage(),
						new String[] { "标题", "第几期", "应还款日期", "实际还款日期", "本期应还本息(￥)", "利息(￥)",
						          "逾期罚款(￥)","逾期天数(天)","还款状态"
								 }, new String[] { "borrowTitle", "repayPeriod",
								"repayDate", "realRepayDate", "forPI",
								"stillInterest", "lateFI","lateDay","repayStatus" });
			
			
			this.export(wb, new Date().getTime() + ".xls");
			
		} catch (Exception e) {
		
			e.printStackTrace();
		} 
		return null;
	}
	

	/**
	 * 借款明细账
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryBorrowInvestorInfo() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		pageBean.setPageNum(request.getString("curPage"));
		String investor = request.getString("investor");
		Map<String, String> accmountStatisMap = myHomeService
		.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		frontpayService.queryBorrowInvestorInfo(pageBean, userId, investor);
		this.setRequestToParamMap();
		return SUCCESS;
	}
	
	/**
	 * 还款数据显示
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryMyPayData() throws Exception{
		long payId = request.getString("payId") == null?-1:request.getLong("payId", -1);
		String borrowId = request.getString("borrowId") == null?"":request.getString("borrowId");
		Map<String,String> payMap = frontpayService.queryMyPayData(payId);
		payMap.put("borrowId", borrowId);
		request().setAttribute("payMap", payMap);
		return SUCCESS;
	}
	
	/**
	 * 提交还款记录
	 * @return
	 * @throws Exception 
	 */
	public String submitPay() throws Exception{
		Thread.sleep(100);
		User user = (User)session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("invest_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap
				.get("code");
		String id = paramMap.get("id") == null ? "" : paramMap
				.get("id");
		long idLong = Convert.strToLong(id, -1L);
		//whb 添加借款id，删除转让中的债权使用
		long borrowId = paramMap.get("borrowId") == null ? -1 : Convert.strToLong(paramMap.get("borrowId"), -1);
		String pwd = paramMap.get("pwd") == null ? "" : paramMap
				.get("pwd");
		if (StringUtils.isBlank(pwd.trim())) {
			obj.put("msg", "密码不能为空");
			JSONUtils.printObject(obj);
			return null;
		}
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
		boolean re = userService.checkSign(user.getId());
		if(!re){
			obj.put("msg", "*您的账号出现异常，请速与管理员联系!");
			JSONUtils.printObject(obj);
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut().print(
			"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		Map<String, String> map =frontpayService.submitPay(idLong,user.getId(),pwd,getBasePath(),user.getUserName(),1);
		String result =Convert.strToStr( map.get("ret_desc"),"");
		userService.updateSign(user.getId());//更换校验码
		 //更新session中的可用余额
		Map<String,String> userMap = userService.queryUserById(user.getId());
		if(userMap!=null && userMap.size()>0){
			user.setUsableSum(userMap.get("usableSum"));
			session().setAttribute("user", user);
		}
		
		// 更新推荐人佣金
		if(Integer.parseInt(map.get("ret"))>0){
			Map<String,String> payment = frontpayService.queryMyPayData(idLong);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", IConstants.RECOMMEND_BROKERAGE_TYPE_INVEST);
			param.put("annualRate", payment.get("annualRate"));
			param.put("pid",idLong);
			brokerageIDRecognition.updateBrokerage(null,0,param);
		}
		
		/**5.1活动奖励-推荐人佣金  = QQ群主特殊处理*/
		if(Integer.parseInt(map.get("ret"))>0){
			 
			long t_repayment_id = Convert.strToLong(paramMap.get("id") == null ? "" : paramMap.get("id"), 0);
			//QQ群主获取1%年利率推广返现
			Map<String,String> activtyComfig = financeService.query51ActivtyComfig();
			if(activtyComfig!=null && activtyComfig.size()>0){
				String start_time =  activtyComfig.get("start_time_f");
				String end_time = activtyComfig.get("end_time_f");
				financeService.update51QQMainRecomment(t_repayment_id, start_time, end_time);
			}
		}
		

		/**判断用户是否为小贷公司用户*/
		Map<String,String> user_map = frontpayService.queryAdminUserPublisher(user.getId());
		if(user_map!=null && user_map.size()>0){//说明是小贷公司用户，则进行授信金额的释放哦
			/**查询待还本金*/
			Map<String,String> repayment_map =  frontpayService.queryRepayment(idLong);
			double stillPrincipal =Convert.strToDouble(repayment_map.get("stillPrincipal"), 0);
			if(stillPrincipal>0){
				/**查询小贷公司*/
				Map<String,String> loan_map = frontpayService.queryLona(Convert.strToLong(user_map.get("adminId"), 0));
				long loan_id = Convert.strToLong(loan_map.get("id"), 0);
				double available_total_amount =Convert.strToDouble(loan_map.get("available_total_amount"), 0);
				double has_total_amount =Convert.strToDouble(loan_map.get("has_total_amount"), 0);
				double available_total_amounts = available_total_amount+stillPrincipal;
				double has_total_amounts = has_total_amount - stillPrincipal;
				/**释放金额*/
				frontpayService.updateLoanCompanyMoney(loan_id, available_total_amounts, has_total_amounts);
			}
		}
		
		
		//给投资人发短信-投资还款
		if(IConstants.ISDEMO.equals("2")){
			List<Map<String, Object>> repaymentList = frontpayService.queryInvestRepmentByRepId(idLong);
			if(repaymentList!=null && repaymentList.size()>0){
				Map<String,String> SMSInface =frontpayService.getSMSById(1);
				for(Map<String, Object> repaymentMap : repaymentList){
					if(repaymentMap!=null){
						String user_name = String.valueOf(repaymentMap.get("username"));
						String cell = String.valueOf(repaymentMap.get("mobilePhone"));
						String borrow_title = String.valueOf(repaymentMap.get("borrowTitle"));
						String repayPeriod = String.valueOf(repaymentMap.get("repayPeriod"));
						
						double hasPrincipal =  Double.parseDouble(String.valueOf(repaymentMap.get("hasPrincipal_f")));//已收本金
						double hasInterest = Double.parseDouble(String.valueOf(repaymentMap.get("hasInterest_f"))) ;//已经收利息
						double sum_money = Double.parseDouble(String.valueOf(repaymentMap.get("sum_money"))) ;//已经zong
						//double sum = hasPrincipal+hasInterest;
						//sum = AmountUtil.getRepaymentMoney(sum);
						 
						StringBuffer buffer = new StringBuffer();
						buffer.append("【微信贷】尊敬的["+user_name+"]:您投资借款["+borrow_title+"],第["+repayPeriod+"]期还款已完成.");
						buffer.append("本期应得总额：￥["+sum_money+"],其中本金部分：["+hasPrincipal+"]元,利息部分：["+hasInterest+"]元。");
						if(!"18765027928".equals(cell) 
							&& !"13321152594".equals(cell) 
							&& !"13718853545".equals(cell) 
							&& !"03718853545".equals(cell) 
							&& !"03321152594".equals(cell)){
							if(sum_money>0){
								SMSUtil.sendMSM(SMSInface.get("Account"), SMSInface.get("Password"), buffer.toString(), cell); 
							}
						}
						
						sendSMSService.insertSmsRecord(cell, buffer.toString(),"",request().getRemoteAddr());
						
					}
				}
			}
		}
		
		 
		
		obj.put("msg", result);
		JSONUtils.printObject(obj);
		return null;
	}

	public static String changeEndTime(String endTime) {
		if (endTime != null && !endTime.equals("")) {
			String[] strs = endTime.split("-");
			// 结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert
					.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1),
					0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		}
		return null;
	}
	
	public static Date changeStrToDate(String dateTime){
		if(dateTime != null){
			String[] strs = dateTime.split("-");
			int ind = strs[2].indexOf(" ");
			if(ind >= 0){
				strs[2] = strs[2].substring(0,ind+1);
			}
			Date date = new Date();// 取时间
			long time = date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert
					.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2].trim(), -1),
					0, 0, 0);
			date.setTime(time);
			return date;
		}
		return null;
	}
	
	
	
}
