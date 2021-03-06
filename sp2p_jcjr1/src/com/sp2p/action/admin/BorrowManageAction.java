package com.sp2p.action.admin;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.ServletUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.BeVipService;
import com.sp2p.service.BorrowService;
import com.sp2p.service.DataApproveService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.PartenersService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.RegionService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.BorrowfaService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.HttpClientHelper;
import com.sp2p.util.WebUtil;
import com.sp2p.util.createImageUtil;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的借款控制层
 */
public class BorrowManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(BorrowManageAction.class);
	private static final long serialVersionUID = 1L;

	private BorrowManageService borrowManageService;
	// ---add by houli
	private DataApproveService dataApproveService;
	// ---add by C_J
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private ShoveBorrowStyleService shoveBorrowStyleService;
	private FinanceService financeService;
	private SelectedService selectedService;
	private UserService userService;
	private PlatformCostService platformCostService;
	private RegionService regionService;
	private BeVipService beVipService;
	private ValidateService validateService;
	private FrontMyPaymentService frontpayService;
	private BorrowService borrowService;
	private List<Map<String,Object>> list;
	private PublicModelService publicModelService;
	private BorrowfaService borrowfaService;
	private PartenersService partenersService;
	
	public PartenersService getPartenersService() {
		return partenersService;
	}

	public void setPartenersService(PartenersService partenersService) {
		this.partenersService = partenersService;
	}
	
	public BorrowfaService getBorrowfaService() {
		return borrowfaService;
	}

	public void setBorrowfaService(BorrowfaService borrowfaService) {
		this.borrowfaService = borrowfaService;
	} 

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}
	
	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public FrontMyPaymentService getFrontpayService() {
		return frontpayService;
	}

	public void setFrontpayService(FrontMyPaymentService frontpayService) {
		this.frontpayService = frontpayService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public BeVipService getBeVipService() {
		return beVipService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	private String from;
	private String btype;

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	private Map<String, String> borrowMFADetail;
	private Map<String, String> borrowMTenderInDetail;
	private Map<String, String> borrowMFullScaleDetail;
	private Map<String, String> borrowMFlowMarkDetail;
	private Map<String, String> borrowMAllDetail;
	private Map<String, String> borrowCirculationDetail;
	private List<Map<String, Object>> cirList;
	private Object borrowId = "";

	// 下拉列表
	private List<Map<String, Object>> borrowPurposeList;
	private List<Map<String, Object>> borrowDeadlineList;
	private List<Map<String, Object>> borrowAmountList;
	private List<Map<String, Object>> borrowRaiseTermList;
	private List<Map<String, Object>> sysImageList;
	private List<Map<String, Object>> borrowTurnlineList;

	private List<Map<String, Object>> borrowRepayWayList;
	private List<Map<String, Object>> borrowDeadlineMonthList;
	private List<Map<String, Object>> borrowDeadlineDayList;
	private List<Map<String, Object>> borrowMinAmountList;
	private List<Map<String, Object>> borrowMaxAmountList;
	
	private List<Map<String, Object>> provinceList;
	
	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public List<Map<String, Object>> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Map<String, Object>> areaList) {
		this.areaList = areaList;
	}

	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}

	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}



	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> areaList;
	private List<Map<String, Object>> regcityList;

	private long workPro = -1L;// 初始化省份默认值
	private long cityId = -1L;// 初始化话默认城市
	
	
	public long getWorkPro() {
		return workPro;
	}

	public void setWorkPro(long workPro) {
		this.workPro = workPro;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public List<Map<String, Object>> getBorrowRepayWayList() throws Exception {
		if (borrowRepayWayList == null) {
			String nid = SqlInfusion.FilteSqlInfusion(session().getAttribute("nid") + "");
			borrowRepayWayList = shoveBorrowStyleService.queryShoveBorrowStyleByTypeNid(nid);
		}
			return borrowRepayWayList;
	}

	public void setBorrowRepayWayList(List<Map<String, Object>> borrowRepayWayList) {
		this.borrowRepayWayList = borrowRepayWayList;
	}

	public List<Map<String, Object>> getBorrowDeadlineMonthList() throws Exception {
		if (borrowDeadlineMonthList == null) {
			borrowDeadlineMonthList = new ArrayList<Map<String, Object>>();
			String nid = SqlInfusion.FilteSqlInfusion(session().getAttribute("nid") + "");
			List<String> list = shoveBorrowTypeService
					.queryDeadlineMonthListByNid(nid);
		
			String month = "个月";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + month);
				borrowDeadlineMonthList.add(map);
			}
		}
			return borrowDeadlineMonthList;
	}

	public void setBorrowDeadlineMonthList(
			List<Map<String, Object>> borrowDeadlineMonthList) {
		this.borrowDeadlineMonthList = borrowDeadlineMonthList;
	}

	public List<Map<String, Object>> getBorrowDeadlineDayList() throws Exception {
		if (borrowDeadlineDayList == null) {
			borrowDeadlineDayList = new ArrayList<Map<String, Object>>();
			String nid = SqlInfusion.FilteSqlInfusion(session().getAttribute("nid") + "");
			List<String> list = shoveBorrowTypeService.queryDeadlineDayListByNid(nid);
		
			String day = "天";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + day);
				borrowDeadlineDayList.add(map);
			}
		}
		return borrowDeadlineDayList;
	}

	public void setBorrowDeadlineDayList(
			List<Map<String, Object>> borrowDeadlineDayList) {
		this.borrowDeadlineDayList = borrowDeadlineDayList;
	}

	public List<Map<String, Object>> getBorrowMinAmountList() throws Exception {
		if (borrowMinAmountList == null) {
			borrowMinAmountList = new ArrayList<Map<String, Object>>();
			String nid = SqlInfusion.FilteSqlInfusion(session().getAttribute("nid") + "");
			List<String> list = shoveBorrowTypeService
					.queryMinAmountListByNid(nid);
		
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value);
				borrowMinAmountList.add(map);
			}
		}
			return borrowMinAmountList;
	}

	public void setBorrowMinAmountList(List<Map<String, Object>> borrowMinAmountList) {
		this.borrowMinAmountList = borrowMinAmountList;
	}

	public List<Map<String, Object>> getBorrowMaxAmountList()throws Exception {
		if (borrowMaxAmountList == null) {
			borrowMaxAmountList = new ArrayList<Map<String, Object>>();
			String nid = SqlInfusion.FilteSqlInfusion(session().getAttribute("nid") + "");
			List<String> list = shoveBorrowTypeService
					.queryMaxAmountListByNid(nid);
		
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value);
				borrowMaxAmountList.add(map);
			}
		}
		return borrowMaxAmountList;
	}

	public void setBorrowMaxAmountList(List<Map<String, Object>> borrowMaxAmountList) {
		this.borrowMaxAmountList = borrowMaxAmountList;
	}

	/**
	 * @MethodName: borrowManageFistAuditInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 上午09:54:00
	 * @Return:
	 * @Descb: 后台借款管理初审初始化
	 * @Throws:
	 */
	public String borrowManageFistAuditInit() throws SQLException,
			DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:58:57
	 * @Return:
	 * @Descb: 后台借款管理初审列表
	 * @Throws:
	 */
	public String borrowManageFistAuditList() throws Exception {
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		// 暂时性的修改 用于修改显示所有初审中的借款
		// 不用做判断处理~
		// borrowManageService.queryBorrowAllByCondition(userName, borrowWayInt,
		// 1,pageBean);
		// 做了判断的处理
		borrowManageService.queryBorrowFistAuditByCondition(userName,
				borrowWayInt, pageBean);

		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowTotalFistAuditDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页应收款
		double fistAuditAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				fistAuditAmount = fistAuditAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("fistAuditAmount", fmt.format(fistAuditAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	public String borrowManageWaitingAuditList() throws Exception {
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowWaitingAuditByCondition(userName,
				borrowWayInt, pageBean);

		Map<String, String> waitTotalAmount = borrowManageService
				.queryBorrowTotalWait();
		request().setAttribute("waitTotalAmount", waitTotalAmount);
		// 统计当前页等待
		double waitingAuditAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				waitingAuditAmount = waitingAuditAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("waitingAuditAmount",
				fmt.format(waitingAuditAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理中的借款详情
	 * @Throws:
	 */
	public String borrowManageFistAuditDetail() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMFADetail == null) {
			// 初审中的借款详情
			borrowMFADetail = borrowManageService
					.queryBorrowFistAuditDetailById(idLong);
			String nid_log = borrowMFADetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
				
				String userId = borrowMFADetail.get("userId");
				DesSecurityUtil ds = new DesSecurityUtil();
				String new_userId = ds.encrypt(userId.toString());
				borrowMFADetail.put("new_userId", new_userId);

			}
		}
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws IOException
	 * @throws Exception 
	 * @MethodName: updateBorrowF
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:58:28
	 * @Return:
	 * @Descb: 审核借款中的初审记录
	 * @Throws:
	 */
	public String updateBorrowF() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String reciver = paramMap.get("reciver");
		long reciverLong = Convert.strToLong(reciver, -1);
		String status = paramMap.get("status");
		int statusLong = Convert.strToInt(status, -1);
		String msg = paramMap.get("msg");
		String auditOpinion = paramMap.get("auditOpinion");
		//whb 添加初审发标时间
		String applyTime = paramMap.get("applyTime");
		long result = -1;
        
		//TODO判断借款是否为小贷公司用户
		if("6".equals(status)){//审核不通过
			Map<String,String> userMap = borrowManageService.queryUsreType(idLong);
				if(userMap!=null && userMap.size()>0){//合作机构旗下的用户
			      double  borrow_amount=  Convert.strToDouble(userMap.get("borrowAmount"), 0);//借款总额
				  long admin_id = Convert.strToLong(userMap.get("adminId"), 0);//小贷公司后台管理员adminId
				  String borrow_way = userMap.get("borrowWay");//借款方式
				      /** 获取小贷公司信息*/
					  if("3".equals(borrow_way) || "4".equals(borrow_way)){//实地考察——4，信用标-3 ， 目的排除流转标
						 Map<String,String> loanMap = borrowManageService.findLoanCompany(admin_id);
						    long loan_id = Convert.strToLong(loanMap.get("id"),0);//合作机构loanId
							double availableTotalAmount = Convert.strToDouble(loanMap.get("available_total_amount"),0);//授信余额
							double hasTotalAmount = Convert.strToDouble(loanMap.get("has_total_amount"),0);//已用金额
							double availableTotalAmountSum = availableTotalAmount + borrow_amount;
							double hasTotalAmountSum = hasTotalAmount - borrow_amount;
							borrowManageService.updateLoanCompanyMoney(loan_id, availableTotalAmountSum, hasTotalAmountSum); 
					 }
				}
		}
		try {
			 
			result = borrowManageService.updateBorrowFistAuditStatus(idLong,
					reciverLong, statusLong, msg, auditOpinion, admin.getId(),
					getBasePath(), applyTime);
			
			List<Map<String, Object>> list = borrowService.queryAppointInvest();//查看是否有人预约投标
			if("6".equals(status)){//审核不通过
				list = null;
			}
			if(list!=null && list.size()>0){
				Map<String,String> bd = borrowService.queryBorrow(idLong); //标的
				String borrowWay = bd.get("borrowWay");
				String deadline = bd.get("deadline");
				String annualRate = bd.get("annualRate");
				double borrowAmount = Convert.strToDouble(bd.get("borrowAmount"),0);//借款金额
				double minTenderedSum = Convert.strToDouble(bd.get("minTenderedSum"),0);//最小投标金额
				double hasInvestAmount = 0;
				
				// 约标（带密码的标的） 不参与投资
				if(bd.get("hasPWD") != null && bd.get("hasPWD").equals("-1")){
					for(int i=0;i<list.size();i++){
						Map<String,Object> appoint = list.get(i);
						long appontInestId  = Convert.strToLong(String.valueOf(appoint.get("id")),0);
						String type = String.valueOf(appoint.get("type"));
						String limits = String.valueOf(appoint.get("limits"));
						String rate = String.valueOf(appoint.get("rate"));
						double amount = Convert.strToDouble(String.valueOf(appoint.get("amount")),0); 
						long userId  = Convert.strToLong(String.valueOf(appoint.get("userId")),0);
					
			            if(borrowWay.equals(type)){
			            	if(deadline.equals(limits)){
			            		if(annualRate.equals(rate)){
			            			if(amount-minTenderedSum>=0){ 
			            			 if(amount+hasInvestAmount-borrowAmount<=0){
			            				String username = financeService.queryUserById(userId).get("username");
			            				Map<String,String> resultMap = financeService.addBorrowInvest(idLong, userId, "", amount, WebUtil.getWebPath(), username, 2, 0, 0, 0, 0, 0);
			            				 String ret = resultMap.get("ret"); 
			            				 log.info("自动投标结果：ret="+ret+"...........");
			            			     if("1".equals(ret)){
			            			    	 borrowService.updateAppointInvest(appontInestId,idLong);
				            				 hasInvestAmount = hasInvestAmount+amount;
			            			     }
			            			 }
			            		   }
				            	}
			            	}
			            }
					}
				}
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result <= 0) {
			// 操作失败提示
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		
		return null;
		
	}
	
	/**
	 * 推送标的给希财网
	 * @author whb
	 * 
	 */
	public String dataToCsai() {
		long borrowId = Convert.strToLong(request.getString("borrowId"), -1);
		try {
			Map<String,String> borrowMap = borrowManageService.dataToCsai(borrowId);
			String access_token = borrowManageService.getCsaiAccessToken();
			String url = IConstants.CSAI_CREATE;
			Map<String, String> params = new HashMap<String, String>();
			params.put("access_token", access_token);
			if(null != borrowMap && !borrowMap.isEmpty()){
				for(String key:borrowMap.keySet()){
					params.put(key, borrowMap.get(key));
					if(key.equals("link_website")){
						String link_website = borrowMap.get(key).replace("&", java.net.URLEncoder.encode("&", "utf-8"));
						link_website = borrowMap.get(key).replace("=", java.net.URLEncoder.encode("=", "utf-8"));
						params.put("link_website", link_website);
					}
				}
				Map<String, String> result = HttpClientHelper.postMap(url, params);
				if(!result.isEmpty()){
					if("0".equals(result.get("code"))){
						log.info("code=" + result.get("code") + "返回信息：" + result.get("msg"));
						log.info("希财网标的数据推送成功!");
						partenersService.addParenersMessage(6, "希财网标的数据推送成功,返回信息："+result.get("msg"), url, -1L, 5, 0, 0, "", new Date());
						//往t_borrow_push_his表中添加数据
						borrowManageService.addBorrowPushHis(borrowId, 1);
						JSONUtils.printStr("-1");
					}else{
						log.info("code=" + result.get("code") + "返回信息：" + result.get("ErrorMessage"));
						log.info("希财网标的数据推送失败!");
						partenersService.addParenersMessage(6, "希财网标的数据推送失败,返回信息："+result.get("msg"), url, -1L, 5, 0, 0, "", new Date());
						if("1101".equals(result.get("code"))){
							JSONUtils.printStr("1");
						}else {
							JSONUtils.printStr("0");
						}
					}
				}else {
					log.info("希财网标的数据推送失败!");
					partenersService.addParenersMessage(6, "希财网标的数据推送失败", url, -1L, 5, 0, 0, "", new Date());
					JSONUtils.printStr("0");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @MethodName: borrowManageTenderInInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理招标中初始化
	 * @Throws:
	 */
	public String borrowManageTenderInInit() throws SQLException, DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageTenderInList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款招标中的记录
	 * @Throws:
	 */
	public String borrowManageTenderInList() throws Exception {
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowTenderInByCondition(userName,
				borrowWayInt, pageBean);

		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowTotalTenderDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页等待
		double tenderAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				tenderAmount = tenderAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("tenderAmount", fmt.format(tenderAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理招标中的借款详情
	 * @Throws:
	 */
	public String borrowManageTenderInDetail() throws Exception {
		return "success";
	}

	/**
	 * @throws Exception 
	 * @throws Exception
	 * @MethodName: updateBorrowF
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:58:28
	 * @Return:
	 * @Descb: 审核借款中的招标中记录
	 * @Throws:
	 */
	public String updateBorrowTenderIn() throws Exception  {
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String auditOpinion = paramMap.get("auditOpinion");
		long result = -1;
		result = borrowManageService.updateBorrowTenderInStatus(idLong,
				auditOpinion);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_borrow",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "审核借款中的招标中记录，操作失败", 2);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		operationLogService.addOperationLog("t_borrow", admin.getUserName(),
				IConstants.UPDATE, admin.getLastIP(), 0, "审核借款中的招标中记录，操作成功", 2);
		return null;
		
	}

	/**
	 * 初审撤销
	 * @return
	 * @throws Exception
	 */
	public String reBackBorrowFistAudit() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		long result = -1;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		
		Map<String,String> userMap = borrowManageService.queryUsreType(idLong);
		if(userMap!=null && userMap.size()>0){//合作机构旗下的用户
	      double  borrow_amount=  Convert.strToDouble(userMap.get("borrowAmount"), 0);//借款总额
		  long admin_id = Convert.strToLong(userMap.get("adminId"), 0);//小贷公司后台管理员adminId
		  String borrow_way = userMap.get("borrowWay");//借款方式
		      /** 获取小贷公司信息*/
			  if("3".equals(borrow_way) || "4".equals(borrow_way)){//实地考察——4，信用标-3 ， 目的排除流转标
				 Map<String,String> loanMap = borrowManageService.findLoanCompany(admin_id);
				    long loan_id = Convert.strToLong(loanMap.get("id"),0);//合作机构loanId
					double availableTotalAmount = Convert.strToDouble(loanMap.get("available_total_amount"),0);//授信余额
					double hasTotalAmount = Convert.strToDouble(loanMap.get("has_total_amount"),0);//已用金额
					double availableTotalAmountSum = availableTotalAmount + borrow_amount;
					double hasTotalAmountSum = hasTotalAmount - borrow_amount;
					borrowManageService.updateLoanCompanyMoney(loan_id, availableTotalAmountSum, hasTotalAmountSum); 
			 }
		}
		
		
		// 调用撤消服务
		result = borrowManageService.reBackBorrowFistAudit(idLong, admin.getId(), getBasePath(), "", "");

		if (result <= 0) {
			// 操作失败
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 操作成功
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: reBackBorrowTenderIn
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午04:00:42
	 * @Return:
	 * @Descb: 撤消招标中的借款
	 * @Throws:
	 */
	public String reBackBorrowTenderIn() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		long result = -1;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		
		Map<String,String> userMap = borrowManageService.queryUsreType(idLong);
		if(userMap!=null && userMap.size()>0){//合作机构旗下的用户
	      double  borrow_amount=  Convert.strToDouble(userMap.get("borrowAmount"), 0);//借款总额
		  long admin_id = Convert.strToLong(userMap.get("adminId"), 0);//小贷公司后台管理员adminId
		  String borrow_way = userMap.get("borrowWay");//借款方式
		      /** 获取小贷公司信息*/
			  if("3".equals(borrow_way) || "4".equals(borrow_way)){//实地考察——4，信用标-3 ， 目的排除流转标
				 Map<String,String> loanMap = borrowManageService.findLoanCompany(admin_id);
				    long loan_id = Convert.strToLong(loanMap.get("id"),0);//合作机构loanId
					double availableTotalAmount = Convert.strToDouble(loanMap.get("available_total_amount"),0);//授信余额
					double hasTotalAmount = Convert.strToDouble(loanMap.get("has_total_amount"),0);//已用金额
					double availableTotalAmountSum = availableTotalAmount + borrow_amount;
					double hasTotalAmountSum = hasTotalAmount - borrow_amount;
					borrowManageService.updateLoanCompanyMoney(loan_id, availableTotalAmountSum, hasTotalAmountSum); 
			 }
		}
		
		// 调用撤消服务
		result = borrowManageService.reBackBorrow(idLong, admin.getId(),
				getBasePath());
		if (result < 0) {
			// 操作失败
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 操作成功
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @MethodName: borrowManageFullScaleInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理满标初始化
	 * @Throws:
	 */
	public String borrowManageFullScaleInit() throws SQLException,
			DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFullScaleList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款满标的记录
	 * @Throws:
	 */
	public String borrowManageFullScaleList() throws Exception {
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowFullScaleByCondition(userName,
				borrowWayInt, pageBean);

		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowTotalFullScaleDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页应收款
		double fullScaleAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				fullScaleAmount = fullScaleAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("fistAuditAmount", fmt.format(fullScaleAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理满标的借款详情
	 * @Throws:
	 */
	public String borrowManageFullScaleDetail() throws Exception {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: updateBorrowF
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:58:28
	 * @Return:
	 * @Descb: 审核借款中的满标记录
	 * @Throws:
	 */
	public String updateBorrowFullScale() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String status = paramMap.get("status");
		long statusLong = Convert.strToLong(status, -1);
		String auditOpinion = paramMap.get("auditOpinion");
		
		//TODO判断借款是否为小贷公司用户
		Map<String,String> aMap = borrowManageService.queryUsreType(idLong);
		if(aMap!=null && aMap.size()>0){
			String uName = "qudaofei";
			Map<String,String> bMap = borrowManageService.findUserByuName(uName);	
			if(bMap==null || bMap.size()==0){
				obj.put("msg","没有渠道费管理账户,复审失败");
				JSONUtils.printObject(obj);
				return null;
			}
		}
		
		Map<String, String> retMap = borrowManageService.updateBorrowFullScaleStatus(idLong, statusLong, auditOpinion,admin.getId(), getBasePath());
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			
			
			//TODO判断借款是否为小贷公司用户
			Map<String,String> userMap = borrowManageService.queryUsreType(idLong);
			if(userMap!=null && userMap.size()>0){
				String borrowName = userMap.get("borrowTitle");//借款标题
				double  borrow_amount=  Convert.strToDouble(userMap.get("borrowAmount"), 0);//借款总额
				long admin_id = Convert.strToLong(userMap.get("adminId"), 0);//小贷公司后台管理员adminId
				long user_id = Convert.strToLong(userMap.get("userId"), 0);//借款款人
				double rateMoney = 0;
				double payDept = 0;
				double userAbleMoney1 = 0;
				/** 获取小贷公司信息*/
				Map<String,String> loanMap = borrowManageService.findLoanCompany(admin_id);
				if("4".equals(status)){//满标复审状态--审核通过
				/** 合同承诺函和回购函-开始*/
					String borrowWay = userMap.get("borrowWay");
					String path = request().getSession().getServletContext().getRealPath("/");
					String createPath = path+"/images/company/"+"srcnh"+id+".png";//命名：发标id+发布时间
					String createPath1 = path+"/images/company/"+"hgcnh"+id+".png";//命名：发标id+发布时间
					String companyPath = path+"/images/company/target.png";
					File outFile = new File(createPath);
					File outFile1 = new File(createPath1);
					String[] param = {id,borrowWay};
					createImageUtil.createImageSrcnh(param, outFile,createPath,companyPath);
					createImageUtil.createImageHgcnh(param, outFile1, createPath1, companyPath);
					System.out.println("授信承诺函执行成功");
					System.out.println("回购承诺函执行成功");
					/** 合同承诺函和回购函-结束*/
					 
					
					double  channel_cost=  Convert.strToDouble(loanMap.get("channel_cost"), 0);//获取渠道费比例
					double  pay_dept =  Convert.strToDouble(loanMap.get("insolvency_reserves_scale"), 0);//获取偿债金比例
					long  loanId=  Convert.strToLong(loanMap.get("id"), 0);
					String loanName = loanMap.get("name");
					
				    /** 获取用户基本信息t_user中的可用余额*/
					Map<String,String> userAbleMoney = borrowManageService.findUserAbleMoney(user_id);
					double  usable_sum=  Convert.strToDouble(userAbleMoney.get("usableSum"), 0);//获取用户可用余额
					rateMoney = borrow_amount*channel_cost/100;//算出扣款渠道费金额
					payDept = borrow_amount*pay_dept/100;//算出扣款偿债金额
					//double userFinallyAbleMoney = usable_sum - rateMoney-payDept;//算出用户被  扣除渠道费比例金额和偿债金额后可用余额
					userAbleMoney1 = usable_sum - rateMoney;//算出用户被  扣除渠道费比例金额后的可用金额
					
					/** 将扣除的渠道费金额放到一个统一的账户中 */
					String uName = "qudaofei";
					Map<String,String> userAccount = borrowManageService.findUserByuName(uName);
					if(userAccount!=null && userAccount.size()>0){
						long uId = Convert.strToLong(userAccount.get("id"), 0);////原存款用户id
						double money = Convert.strToDouble(userAccount.get("usableSum"), 0);//原存款用户可用金额
						money = money+rateMoney;//加渠道费用金额总和
						
						if(channel_cost>0){
							/**更新管理渠道费账户总金额*/
							borrowManageService.updateUserMoney(uId, money);
							
							/**更新用户可用余额 以及将扣除的金额插入记录*/
							borrowManageService.updateUserAbleMoney(user_id, userAbleMoney1,idLong,borrowName,loanId,loanName,rateMoney,new Date());
							
							/**添加借款人渠道费的资金记录*/
							StringBuffer buffer = new StringBuffer(); 
							buffer.append("借款[").append(borrowName).append("]复审通过,扣除渠道费[").append(rateMoney).append("]元");
							borrowManageService.saveChanncelCostFundrecode(user_id, "扣除渠道费用", rateMoney, userAbleMoney1, buffer.toString(), rateMoney);
							borrowManageService.saveChanncelCostFundrecode1(uId, "扣除渠道费用", rateMoney, money, buffer.toString(), rateMoney);
						}
						
						if(pay_dept>0){
							
							/**添加扣除偿债金额*/
							borrowManageService.addPayDebtMoney(loanId, idLong, payDept, new Date());
							
							Map<String,String> userAbleMoneyMap = borrowManageService.findUserAbleMoney(user_id);
							double  usable_sum2=  Convert.strToDouble(userAbleMoneyMap.get("usableSum"), 0);//获取用户可用余额
							double userAbleMoney2 = usable_sum2-payDept;
							/**更新借款人扣除偿债金额之后的可用金额*/
							borrowManageService.updateUserMoney(user_id, userAbleMoney2);
							/**添加借款人偿债比例金的资金记录*/
							StringBuffer buffer = new StringBuffer();
							buffer.append("借款[").append(borrowName).append("]复审通过,扣除偿债准备金费[").append(payDept).append("]元");
							borrowManageService.savePayDebtFundrecode(user_id, "扣除偿债金费用", payDept, userAbleMoney2, buffer.toString(), payDept);
						}
						
					}else{
						obj.put("msg","没有渠道费管理账户,复审失败");
						JSONUtils.printObject(obj);
						return null;
					}
				  }
				
				  if("6".equals(status)){//满标复审状态--审核不通过
					    long loan_id = Convert.strToLong(loanMap.get("id"),0);//合作机构loanId
						double availableTotalAmount = Convert.strToDouble(loanMap.get("available_total_amount"),0);//授信余额
						double hasTotalAmount = Convert.strToDouble(loanMap.get("has_total_amount"),0);//已用金额
						double availableTotalAmountSum = availableTotalAmount + borrow_amount;
						double hasTotalAmountSum = hasTotalAmount - borrow_amount;
						borrowManageService.updateLoanCompanyMoney(loan_id, availableTotalAmountSum, hasTotalAmountSum); 
				  }
			}
			
			
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	 
	}

	/**
	 * @MethodName: borrowManageFlowMarkInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理流标初始化
	 * @Throws:
	 */
	public String borrowManageFlowMarkInit() throws SQLException, DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFlowMarkList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款流标的记录
	 * @Throws:
	 */
	public String borrowManageFlowMarkList() throws Exception {
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowFlowMarkByCondition(userName,
				borrowWayInt, pageBean);
		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowFlowMarkDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页应收款
		double flowmarkAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				flowmarkAmount = flowmarkAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("flowmarkAmount", fmt.format(flowmarkAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理流标的借款详情
	 * @Throws:
	 */
	public String borrowManageFlowMarkDetail() throws Exception {
		return "success";
	}

	/**
	 * @MethodName: borrowManageAllInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理初始化
	 * @Throws:
	 */
	public String borrowManageAllInit() throws SQLException, DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageAllList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款的记录
	 * @Throws:
	 */
	public String borrowManageAllList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowStatus = paramMap.get("borrowStatus") == null ? ""
				: paramMap.get("borrowStatus");
		int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowAllByCondition(userName, borrowWayInt,
				borrowStatusInt, pageBean);
		// ----add by houli 对等待资料的借款进行标记
		List<Map<String, Object>> lists = borrowManageService
				.queryAllWaitingBorrow();
		Vector<String> ids = new Vector<String>();
		if (lists != null && lists.size() > 0) {
			for (Map<String, Object> map : lists) {
				ids.add(map.get("id").toString());
			}
		}
		List<Map<String, Object>> lls = pageBean.getPage();
		if (lls != null && lls.size() > 0) {
			for (Map<String, Object> map : lls) {
				if (ids.contains(map.get("id").toString())) {
					map.put("flag", "0");
				} else {
					map.put("flag", "1");
				}
			}
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	// 校验提交借款参数
	@SuppressWarnings("unchecked")
	public boolean isValidate(double amountDouble, String excitationType,
			double sumRateDouble, double annualRateDouble) throws Exception {
		String t = (String) session().getAttribute("t");
		// 获取借款的范围
		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		tempBorrwBidMessage = shoveBorrowTypeService
				.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		// 取得按借款金额的比例进行奖励
		double accountfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_first")
				+ "", 0);
		double accountend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "2".equals(excitationType)) {
				if (sumRateDouble < accountfirst || sumRateDouble > accountend) {
					this.addFieldError("paramMap['sum']", "固定总额奖励填写不正确");
					return false;
				}
			}
		}
		// 如果选择金额的话，则按此奖励的金额范围
		double scalefirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_first")
				+ "", 0);
		double scaleend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "3".equals(excitationType)) {
				if (sumRateDouble < scalefirst || sumRateDouble > scaleend) {
					this.addFieldError("paramMap['sumRate']", "奖励比例填写不正确");
					return false;
				}
			}
		}
		// 借款额度
		double borrowMoneyfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_first")
				+ "", 0);
		double borrowMoneyend = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_end")
				+ "", 0);
		if (borrowMoneyfirst > amountDouble || borrowMoneyend < amountDouble) {
			this.addFieldError("paramMap['amount']", "输入的借款总额不正确");
			return false;
		}
		// 借款额度倍数
		double accountmultiple = Convert.strToDouble(tempBorrwBidMessage
				.get("account_multiple")
				+ "", -1);
		if (accountmultiple != 0) {
			if (amountDouble % accountmultiple != 0) {
				this.addFieldError("paramMap['amount']", "输入的借款总额的倍数不正确");
				return false;
			}
		}
		// 年利率
		double aprfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("apr_first")
				+ "", 0);
		double aprend = Convert.strToDouble(tempBorrwBidMessage.get("apr_end")
				+ "", 0);
		if (aprfirst > annualRateDouble || aprend < annualRateDouble) {
			this.addFieldError("paramMap['annualRate']", "输入的年利率不正确");
			return false;
		}
		return true;
	}

	/**
	 * @MethodName: circulationBorrowInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:33:18
	 * @Return:
	 * @Descb: 流转标借款初始化
	 * @Throws:
	 */
	public String circulationBorrowInit() throws SQLException, DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: circulationBorrowList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:35:50
	 * @Return:
	 * @Descb: 流转标借款
	 * @Throws:
	 */
	public String circulationBorrowList() throws Exception {
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String undertaker = paramMap.get("undertaker") == null ? "" : paramMap
				.get("undertaker");
		String borrowStatus = paramMap.get("borrowStatus") == null ? ""
				: paramMap.get("borrowStatus");
		int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
		borrowManageService.queryAllCirculationByCondition(userName, -1,
				borrowStatusInt, undertaker, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowCirculationDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午01:44:03
	 * @Return:
	 * @Descb: 流转标借款详情
	 * @Throws:
	 */
	public String borrowCirculationDetail() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		if (borrowCirculationDetail == null) {
			// 初审中的借款详情
			borrowCirculationDetail = borrowManageService
					.queryBorrowCirculationDetailById(idLong);
		}
		return "success";
	}

	/**
	 * @MethodName: updateBorrowCirculation
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午03:23:42
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public String updateBorrowCirculation() throws IOException {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long borrowId = Convert.strToLong(id, -1);
		String reciver = paramMap.get("reciver");
		long reciverId = Convert.strToLong(reciver, -1);
		String status = paramMap.get("status");
		long statusLong = Convert.strToLong(status, -1);
		String auditOpinion = paramMap.get("auditOpinion");
		long result = -1;
		if (statusLong == -1) {
			obj.put("msg", "请选择审核状态");
			JSONUtils.printObject(obj);
			return null;
		}
		if (!StringUtils.isNotBlank(auditOpinion)) {
			obj.put("msg", "请填写风险控制措施");
			JSONUtils.printObject(obj);
			return null;
		} else if (auditOpinion.length() > 500) {
			obj.put("msg", "风险控制措施内容不能超过500字符");
			JSONUtils.printObject(obj);
			return null;
		}
		try {
			result = borrowManageService.updateBorrowCirculationStatus(
					borrowId, reciverId, statusLong, auditOpinion, admin
							.getId(), getBasePath(), getPlatformCost());
			operationLogService.addOperationLog("t_borrow",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "更新流转标的状态", 2);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result <= 0) {
			// 操作失败提示
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理的借款详情
	 * @Throws:
	 */
	public String borrowManageAllDetail() throws Exception {
		return "success";
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public Map<String, String> getBorrowMFADetail() throws SQLException,
			DataException {
		return borrowMFADetail;
	}

	public Map<String, String> getBorrowMTenderInDetail() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMTenderInDetail == null) {
			// 招标中的借款详情
			borrowMTenderInDetail = borrowManageService
					.queryBorrowTenderInDetailById(idLong);
			String nid_log = borrowMTenderInDetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		// ---add by houli 屏蔽链接
		String mailContent = borrowMTenderInDetail.get("mailContent");
		String newStr = changeStr2Str(mailContent);
		borrowMTenderInDetail.put("mailContent", newStr);
		String userId = borrowMTenderInDetail.get("userId");
		String userId_admin = borrowMTenderInDetail.get("userId_admin");
		DesSecurityUtil ds = new DesSecurityUtil();
		String new_userId = ds.encrypt(userId.toString());
		String new_userId_admin = ds.encrypt(userId_admin.toString());
		borrowMTenderInDetail.put("new_userId", new_userId);
		borrowMTenderInDetail.put("new_userId_admin", new_userId_admin);
		// ---------end
		return borrowMTenderInDetail;
	}

	public Map<String, String> getBorrowMFullScaleDetail() throws Exception {
		String id = request.getString("id") == null ? "" :request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMFullScaleDetail == null) {
			// 满标的借款详情
			borrowMFullScaleDetail = borrowManageService
					.queryBorrowFullScaleDetailById(idLong);
			String nid_log = borrowMFullScaleDetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		// ---add by houli 屏蔽链接
		String mailContent = borrowMFullScaleDetail.get("mailContent");
		String newStr = changeStr2Str(mailContent);
		borrowMFullScaleDetail.put("mailContent", newStr);
		
		String userId = borrowMFullScaleDetail.get("userId");
		String userId_admin = borrowMFullScaleDetail.get("userId_admin");
		DesSecurityUtil ds = new DesSecurityUtil();
		String new_userId = ds.encrypt(userId.toString());
		String new_userId_admin = ds.encrypt(userId_admin.toString());
		borrowMFullScaleDetail.put("new_userId", new_userId);
		borrowMFullScaleDetail.put("new_userId_admin", new_userId_admin);
		
		
		// ---------end
		return borrowMFullScaleDetail;
	}

	public Map<String, String> getBorrowMFlowMarkDetail() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMFlowMarkDetail == null) {
			// 流标的借款详情
			borrowMFlowMarkDetail = borrowManageService
					.queryBorrowFlowMarkDetailById(idLong);
			String nid_log = borrowMFlowMarkDetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		
		String userId = borrowMFlowMarkDetail.get("userId");
		String userId_admin = borrowMFlowMarkDetail.get("userId_admin");
		DesSecurityUtil ds = new DesSecurityUtil();
		String new_userId = ds.encrypt(userId.toString());
		String new_userId_admin = ds.encrypt(userId_admin.toString());
		borrowMFlowMarkDetail.put("new_userId", new_userId);
		borrowMFlowMarkDetail.put("new_userId_admin", new_userId_admin);
		
		return borrowMFlowMarkDetail;
	}

	public String circulationRepayRecordInit() throws SQLException,
			DataException {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: circulationBorrowList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:35:50
	 * @Return:
	 * @Descb: 流转标借款
	 * @Throws:
	 */
	public String circulationRepayRecordList() throws Exception {
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowTitle = paramMap.get("borrowTitle") == null ? ""
				: paramMap.get("borrowTitle");
		String borrowStatus = paramMap.get("borrowStatus") == null ? ""
				: paramMap.get("borrowStatus");
		int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
		borrowManageService.queryAllCirculationRepayRecordByCondition(userName,
				borrowStatusInt, borrowTitle, pageBean);
		return "success";
	}

	/**
	 * @MethodName: circulationRepayForAdd
	 * @Param: BorrowManageAction
	 * @Return:
	 * @Descb: 流转标还款详情添加初始化
	 * @Throws:
	 */
	public String circulationRepayForAdd() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: circulationRepayAdd
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午03:48:45
	 * @Return:
	 * @Descb: 添加流转标详情
	 * @Throws:
	 */
	public String circulationRepayAdd() throws Exception {
		Admin admin = (Admin) session().getAttribute("admin");
		String amount = paramMap.get("amount");
		double amountDouble = Convert.strToDouble(amount, -1);
		String remark = paramMap.get("remark");
		Object id = session().getAttribute("repayId");
		long repayId = Convert.strToLong(id + "", -1);
		if (repayId == -1) {
			this.addFieldError("paramMap['action']", "操作失败");
			return "input";
		}
		if (amountDouble == -1 || amountDouble <= 0) {
			this.addFieldError("paramMap['amount']", "金额格式错误");
			return "input";
		}
		if (remark.length() > 500) {
			this.addFieldError("paramMap['remark']", "备注不能超过500字符");
			return "input";
		}
		long returnId = -1;
		returnId = borrowManageService.addCirculationRepay(repayId,
				amountDouble, admin.getId(), remark);
		operationLogService.addOperationLog("t_borrow", admin.getUserName(),
				IConstants.INSERT, admin.getLastIP(), 0, "添加流转标还款记录", 2);

		if (returnId < 1) {
			this.addFieldError("paramMap['action']", "操作失败");
			return "input";
		}
		borrowId = session().getAttribute("borrowId");
		return "success";
	}

	public Map<String, String> getBorrowMAllDetail() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		if (borrowMAllDetail == null) {
			// 所以的借款详情
			borrowMAllDetail = borrowManageService
					.queryBorrowAllDetailById(idLong);
		}
		return borrowMAllDetail;
	}

	/**
	 * 验证用户资料信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isNotUnderCoirculationBorrow() throws Exception {
		long uId = request.getLong("i", -1);
		Map<String, String> userMap;
		try {
			userMap = userService.queryUserById(uId);
			if (Convert.strToInt(userMap.get("vipStatus"), 0) == IConstants.UNVIP_STATUS) {// 没有成为VIP
				JSONUtils.printStr("3");
				return null;
			}
			if (Convert.strToInt(userMap.get("authStep"), 0) == 1) {
				// 基本信息认证步骤
				JSONUtils.printStr("7");
				return null;
			} else if (Convert.strToInt(userMap.get("authStep"), 0) == 2) {
				// 工作信息认证步骤
				JSONUtils.printStr("4");
				return null;
			} else if (Convert.strToInt(userMap.get("authStep"), 0) == 3) {
				// VIP申请认证步骤
				JSONUtils.printStr("5");
				return null;
			} else if (Convert.strToInt(userMap.get("authStep"), 0) == 4) {
				// 上传资料认证步骤
				JSONUtils.printStr("6");
				return null;
			}
			Map<String, String> map = dataApproveService.querySauthId(uId,
					IConstants.FLOW_PHONE);
			if (map == null) {
				JSONUtils.printStr("2");// 手机认证
				return null;
			} else {
				Long sauthId = Convert.strToLong(map.get("id"), -1L);
				Long status = dataApproveService.queryApproveStatus(sauthId);
				if (status < 0) {
					JSONUtils.printStr("8");// 手机认证待审核
					return null;
				}
			}
			// 条件满足
			JSONUtils.printStr("1");
			return null;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @throws Exception
	 * @MethodName: underCirculationBorrow
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午04:38:02
	 * @Return:
	 * @Descb: 代发流转标
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String underCirculationBorrow() throws Exception {
		String uId = request.getString("i") == null ? "" : request.getString("i");
		session().setAttribute("uId", uId);

		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		try {
			tempBorrwBidMessage = shoveBorrowTypeService
					.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
			// 取得按借款金额的比例进行奖励
			paramMap.put("scalefirst", tempBorrwBidMessage
					.get("award_scale_first")
					+ "");
			paramMap.put("scaleend", tempBorrwBidMessage.get("award_scale_end")
					+ "");
			// 如果选择金额的话，则按此奖励的金额范围
			paramMap.put("accountfirst", tempBorrwBidMessage
					.get("award_account_first")
					+ "");
			paramMap.put("accountend", tempBorrwBidMessage
					.get("award_account_end")
					+ "");
			// 借款额度
			paramMap.put("borrowMoneyfirst", tempBorrwBidMessage
					.get("amount_first")
					+ "");
			paramMap.put("borrowMoneyend", tempBorrwBidMessage
					.get("amount_end")
					+ "");
			// 借款额度倍数
			paramMap.put("accountmultiple", tempBorrwBidMessage
					.get("account_multiple")
					+ "");
			// 年利率
			paramMap.put("aprfirst", tempBorrwBidMessage.get("apr_first") + "");
			paramMap.put("aprend", tempBorrwBidMessage.get("apr_end") + "");
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}

		return "success";
	}

	public DataApproveService getDataApproveService() {
		return dataApproveService;
	}

	public void setDataApproveService(DataApproveService dataApproveService) {
		this.dataApproveService = dataApproveService;
	}

	private String changeStr2Str(String mailContent) {
		if (mailContent != null && !mailContent.equals("")) {
			int ind1 = mailContent.indexOf("<");
			int ind2 = mailContent.indexOf(">");
			if (ind1 < 0 || ind2 < 0 || ind2 <= ind1) {
				return mailContent;
			}
			String newStr = mailContent.substring(0, ind1)
					+ mailContent.substring(ind2 + 1);
			// 处理<a>链接的结束标签
			newStr = newStr.replace("</a>", "");
			return newStr;
		}
		return mailContent;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public List<Map<String, Object>> getCirList() {
		return cirList;
	}

	public void setCirList(List<Map<String, Object>> cirList) {
		this.cirList = cirList;
	}

	public Map<String, String> getBorrowCirculationDetail() {
		return borrowCirculationDetail;
	}

	public void setBorrowCirculationDetail(
			Map<String, String> borrowCirculationDetail) {
		this.borrowCirculationDetail = borrowCirculationDetail;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public Object getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Object borrowId) {
		this.borrowId = borrowId;
	}

	public static Log getLog() {
		return log;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws Exception {
		if (borrowPurposeList == null) {
			// 借款目的列表
			borrowPurposeList = selectedService.borrowPurpose();
		}
		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		if (borrowDeadlineList == null) {
			// 借款期限列表
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getBorrowRaiseTermList() throws Exception {
		if (borrowRaiseTermList == null) {
			// 筹款期限列表
			String nid = session().getAttribute("nid") + "";
			borrowRaiseTermList = new ArrayList<Map<String, Object>>();
			List<String> list = shoveBorrowTypeService
					.queryRaiseTermLisByNid(nid);
			String day = "天";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + day);
				borrowRaiseTermList.add(map);
			}
		}
		return borrowRaiseTermList;
	}

	public List<Map<String, Object>> getSysImageList() throws Exception {
		if (sysImageList == null) {
			// 系统列表
			sysImageList = selectedService.sysImageList();
		}
		return sysImageList;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowAmountList() {
		return borrowAmountList;
	}

	public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
		this.borrowAmountList = borrowAmountList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ShoveBorrowTypeService getShoveBorrowTypeService() {
		return shoveBorrowTypeService;
	}

	public PlatformCostService getPlatformCostService() {
		return platformCostService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}
	
	public ShoveBorrowStyleService getShoveBorrowStyleService() {
		return shoveBorrowStyleService;
	}

	public void setShoveBorrowStyleService(
			ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public void setBorrowMFADetail(Map<String, String> borrowMFADetail) {
		this.borrowMFADetail = borrowMFADetail;
	}

	public void setBorrowMTenderInDetail(
			Map<String, String> borrowMTenderInDetail) {
		this.borrowMTenderInDetail = borrowMTenderInDetail;
	}

	public void setBorrowMFullScaleDetail(
			Map<String, String> borrowMFullScaleDetail) {
		this.borrowMFullScaleDetail = borrowMFullScaleDetail;
	}

	public void setBorrowMFlowMarkDetail(
			Map<String, String> borrowMFlowMarkDetail) {
		this.borrowMFlowMarkDetail = borrowMFlowMarkDetail;
	}

	public void setBorrowMAllDetail(Map<String, String> borrowMAllDetail) {
		this.borrowMAllDetail = borrowMAllDetail;
	}

	public void setBorrowPurposeList(List<Map<String, Object>> borrowPurposeList) {
		this.borrowPurposeList = borrowPurposeList;
	}

	public void setBorrowDeadlineList(
			List<Map<String, Object>> borrowDeadlineList) {
		this.borrowDeadlineList = borrowDeadlineList;
	}

	public void setBorrowRaiseTermList(
			List<Map<String, Object>> borrowRaiseTermList) {
		this.borrowRaiseTermList = borrowRaiseTermList;
	}

	public void setSysImageList(List<Map<String, Object>> sysImageList) {
		this.sysImageList = sysImageList;
	}

	public List<Map<String, Object>> getBorrowTurnlineList() throws Exception {
		if (borrowTurnlineList == null) {
			// 借款期限列表
			// borrowDeadlineList = selectedService.borrowDeadline();
			// 获取的到相应的map
			Map<String, String> borrowTrunlineMap;
			try {
				borrowTrunlineMap = shoveBorrowTypeService
						.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
				borrowTurnlineList = new ArrayList<Map<String, Object>>();
				if (borrowTrunlineMap != null && borrowTrunlineMap.size() > 0) {
					String trunmonth = Convert.strToStr(borrowTrunlineMap
							.get("period_month")
							+ "", "");
					String[] trunmonths = trunmonth.split(",");// 截取;符号
					// 放入String数组
					if (trunmonths != null) {
						String str = " 个月";
						for (String file : trunmonths) {// 遍历String数组
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("key", file.trim());
							map.put("value", file + str);
							borrowTurnlineList.add(map);
						}
					}

				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}

		}
		return borrowTurnlineList;
	}

	public void setBorrowTurnlineList(
			List<Map<String, Object>> borrowTurnlineList) {
		this.borrowTurnlineList = borrowTurnlineList;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
	
	
	public String publishBorrowInit() throws Exception{
        long userId =-1;// Convert.strToLong(paramMap.get("userId"), -1); //用户ID
		long applyId =Convert.strToLong(paramMap.get("applyId"), -1);
		int type= Convert.strToInt(paramMap.get("type"), -1); //用户类型
		int authStep =-1;
		//根据申请id去查询是否填写基本信息
		Map<String, String> userMap  = borrowManageService.queryUserBaseInfo(applyId);
		if(userMap!=null){
			userId = Convert.strToLong(userMap.get("userId"), -1) ;
			authStep =Convert.strToInt(userMap.get("authStep"), -1);
		}
		DesSecurityUtil ds =new DesSecurityUtil();
		paramMap.put("userId", ds.encrypt(userId+""));
		paramMap.put("applyId", applyId+"");
		paramMap.put("authStep", authStep+"");
		paramMap.put("type", type+"");
		request().setAttribute("userId", ds.encrypt(userId+""));
		request().setAttribute("applyId", applyId);
		request().setAttribute("authStep", authStep);
		request().setAttribute("type", type);
		
		return SUCCESS;
	}
	
	/**
	 * 发布借款初始化
	 * enterBorrowInit
	 * @return
	 * @throws Exception 
	 * @autthor linww
	 * 2014-6-4 上午10:11:06
	 */
	public String publishBorrow() throws Exception{
		request().setAttribute("applyId", paramMap.get("applyId"));
		request().setAttribute("userId", paramMap.get("userId"));
		request().setAttribute("authStep", paramMap.get("authStep"));
		request().setAttribute("type", paramMap.get("type"));
		
		String type = paramMap.get("type");
		
		String borrowWay = Convert.strToStr(paramMap.get("borrowWay"), "1");
		request().setAttribute("borrowWay", borrowWay);
		int tInt = Convert.strToInt(borrowWay, 1);
		String nid = "";
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
			// 净值借款
			nid = BorrowType.WORTH.getValue();
			request().setAttribute("typeName", "净值借款");
		}else if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
			// 秒还借款
			nid = BorrowType.SECONDS.getValue();
			request().setAttribute("typeName", "秒还借款");
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(borrowWay)) {
			//信用借款 
			nid = BorrowType.ORDINARY.getValue();
			request().setAttribute("typeName", "信用借款");
		} else if (IConstants.BORROW_TYPE_FIELD_VISIT.equals(borrowWay)) {
			// 实地考察借款
			nid = BorrowType.FIELD.getValue();
			request().setAttribute("typeName", "定息宝");
		}else if (IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE.equals(borrowWay)) {
			// 机构担保借款
			nid = BorrowType.INSTITUTIONS.getValue();
			request().setAttribute("typeName", "机构担保借款");
		}else if (IConstants.BORROW_TYPE_INSTITUTION_FLOW.equals(borrowWay)) {
			// 机构担保借款
			nid = BorrowType.FLOW.getValue();
			request().setAttribute("typeName", "活利宝");
		}else{
			//错误
//			json.put("msg", "未知借款类型");
//			JSONUtils.printObject(json);
			getOut().print("<script>alert('未知借款类型');</script>");
			return null;
		}
		session().setAttribute("nid", nid);
		Map<String, String> borrowTypeMap = shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
		if (borrowTypeMap == null || !"1".equals(borrowTypeMap.get("status"))) {
//			json.put("msg", "该标种还未开启");
//			JSONUtils.printObject(json);
			getOut().print("<script>alert('该标种还未开启');</script>");
			return null;
		}
		if(tInt>0){
			// 设置是否开启密码（1开启）
			request().setAttribute("password_status",borrowTypeMap.get("password_status"));
			// 设置是否开启奖励(1开启)
			request().setAttribute("award_status",borrowTypeMap.get("award_status"));
			String validate = borrowTypeMap.get("validate");
			if ("0".equals(validate)) {
				request().setAttribute("validateDay", "1");
			}else{
				
			}
			// /---------cj____判断是否开启认购模式
			int subscribe_status = Convert.strToInt(borrowTypeMap
					.get("subscribe_status"), -1);
			request().setAttribute("subscribeStatus", subscribe_status);
		}
		if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)){
			return "seconds";
		}else{
			if("1".equals(type)){
				return "success_user";
			}else{
				return SUCCESS;
			}
			
			
		}
		
	}
	
	/**
	 * 发布借款校验
	 * validateAddBorrow
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-4 下午04:02:31
	 */
	public void validateBgAddBorrow() throws Exception {
		String borrowWay = paramMap.get("borrowWay");
		request().setAttribute("award_status",request.getString("award_status"));
		request().setAttribute("password_status",request.getString("password_status"));
		request().setAttribute("validateDay", request.getString("validateDay"));
		
		request().setAttribute("applyId",paramMap.get("applyId"));
		request().setAttribute("userId",paramMap.get("userId"));
		request().setAttribute("authStep",paramMap.get("authStep"));
		request().setAttribute("type", paramMap.get("type"));
		
		

		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
		if (borrowTypeMap == null) {
			this.addFieldError("paramMap['allError']", "该借款类型已关闭");
		}
		if (!"1".equals(borrowTypeMap.get("status"))) {
			this.addFieldError("paramMap['allError']", "该借款类型已关闭");
		}
		// 金额倍数
		long accountMultiple = Convert.strToLong(borrowTypeMap
				.get("account_multiple"), 0);
		// 最小年利率
		double minRate = Convert.strToDouble(borrowTypeMap.get("apr_first"),
				0.0);
		double maxRate = Convert.strToDouble(borrowTypeMap.get("apr_end"), 0.0);

		String title = paramMap.get("title");
		if (StringUtils.isBlank(title)) {
			this.addFieldError("paramMap['title']", "借款标题不能为空");
		}
		if (StringUtils.isNotBlank(title) && title.length() >= 20) {
			this.addFieldError("paramMap['title']", "借款标题长度不得大于20个字符");
		}
		String imgPath = paramMap.get("imgPath");
		if (StringUtils.isBlank(imgPath)) {
			this.addFieldError("paramMap['imgPath']", "请上传借款图片");
		}
		String amount = paramMap.get("amount");
		if (StringUtils.isBlank(amount)) {
			this.addFieldError("paramMap['amount']", "请填写借款总额");
		}

		if (StringUtils.isNotBlank(amount)) {
			if (!amount
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['amount']", "借款总额格式不正确");
			} else {
				double aountD = Convert.strToDouble(amount, 0.0);

				double minAount = Convert.strToDouble(borrowTypeMap
						.get("amount_first"), 0.0);
				double maxAount = Convert.strToDouble(borrowTypeMap
						.get("amount_end"), 0.0);
				if (aountD < minAount || aountD > maxAount) {
					this.addFieldError("paramMap['amount']", "借款总额范围为"
							+ minAount + "元 ~ " + maxAount + "元");
				}

				if (accountMultiple != 0 && aountD % accountMultiple != 0) {
					this.addFieldError("paramMap['amount']", "借款总额应为"
							+ accountMultiple + "的整数倍");
				}
			}
		}

		String annualRate = paramMap.get("annualRate");
		if (StringUtils.isBlank(annualRate)) {
			this.addFieldError("paramMap['annualRate']", "请填写借款年利率");
		}
		log.info("-----请填写借款年利率----");
		if (StringUtils.isNotBlank(annualRate)) {
			if (!annualRate
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['annualRate']", "年利率格式不正确");
			} else {
				double annualRateD = Convert.strToDouble(annualRate, 0.0);
				if (annualRateD < minRate || annualRateD > maxRate) {
					this.addFieldError("paramMap['annualRate']", "年利率范围为"
							+ minRate + "%~" + maxRate + "%");
				}
			}
		}

		String detail = paramMap.get("detail");

		if (StringUtils.isNotBlank(detail) && detail.length() > 500) {
			this.addFieldError("paramMap['detail']", "借款详情不能超过500个字符");
		}
		
		String publishTime = paramMap.get("publishTime");

		if (StringUtils.isBlank(publishTime)) {
			this.addFieldError("paramMap['publishTime']", "请选择发布时间");
		}
		
		String excitationType = paramMap.get("excitationType");
		int excitationTypeNum = -1;
		if (StringUtils.isNotBlank(excitationType)) {
			if (!excitationType.matches("^([0-9]\\d{0,9})$")) {
				this.addFieldError("paramMap['excitationType']", "非数字");
			}
			excitationTypeNum = Convert.strToInt(excitationType, -1);
		}

		String sum = paramMap.get("sum");
		if (StringUtils.isNotBlank(sum)) {
			if (!sum
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['sum']", "金额格式不正确");
			}
			// 投标奖励
			if ("1".equals(borrowTypeMap.get("award_status"))
					&& excitationTypeNum == 2) {
				double minSum = Convert.strToDouble(borrowTypeMap
						.get("award_account_first"), 0.0);
				double maxSum = Convert.strToDouble(borrowTypeMap
						.get("award_account_end"), 0.0);
				double sumD = Convert.strToDouble(sum, 0.0);
				if (sumD < minSum || sumD > maxSum) {
					this.addFieldError("paramMap['sum']", "奖励金额范围是" + minSum
							+ "元 ~ " + maxSum + "元");
				}
			}
		}

		String sumRate = paramMap.get("sumRate");
		if (StringUtils.isNotBlank(sumRate)) {
			if (!sumRate
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['sumRate']", "金额比例格式不正确");
			} else {
				double sumRateD = Convert.strToDouble(sumRate, 0.0);
				// 投标奖励
				if ("1".equals(borrowTypeMap.get("award_status"))
						&& excitationTypeNum == 3) {
					double minSumRate = Convert.strToDouble(borrowTypeMap
							.get("award_scale_first"), 0.0);
					double maxSumRate = Convert.strToDouble(borrowTypeMap
							.get("award_scale_end"), 0.0);

					if (sumRateD < minSumRate || sumRateD > maxSumRate) {
						this.addFieldError("paramMap['sumRate']", "奖励比率范围是"
								+ minSumRate + "% ~ " + maxSumRate + "%");
					}
				}
			}
		}

		String investPWD = paramMap.get("investPWD");
		if (StringUtils.isNotBlank(investPWD) && investPWD.length() > 20) {
			this.addFieldError("paramMap['investPWD']", "投标密码长度不得大于20个字符");
		}

		String code = paramMap.get("code");
		if (StringUtils.isBlank(code)) {
			this.addFieldError("paramMap['code']", "请填写验证码");
			code = "codss";
		}

		String raiseTerm = paramMap.get("raiseTerm");
		if (StringUtils.isBlank(raiseTerm)) {
			this.addFieldError("paramMap['raiseTerm']", "请选择投标期限");
		}

		String _code = (String) session().getAttribute("bgborrow_checkCode");
		if (_code == null || _code == "") {
			_code = "code";
		}
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", "验证码错误");
		}

		String daythe = paramMap.get("daythe");
		String deadLine = paramMap.get("deadLine");
		String deadDay = paramMap.get("deadDay");
		int deadLineInt = Convert.strToInt(deadLine, -1);
		if (!"2".equals(borrowWay)) {
			if (!"true".equals(daythe)) {
				if (deadLineInt < 0) {
					this.addFieldError("paramMap['deadLine']", "请选择期限");
				}
				String paymentMode = paramMap.get("paymentMode");
				int paymentModeInt = Convert.strToInt(paymentMode, -1);
				if (paymentModeInt < 0) {
					this.addFieldError("paramMap['paymentMode']", "请选择还款方式");
				}
			} else {
				int deadDayInt = Convert.strToInt(deadDay, -1);
				if (deadDayInt < 0) {
					this.addFieldError("paramMap['deadLine']", "请选择期限");
				}
			}
		}
		// 是否启用认购模式
		int subscribe_status = request.getInt("subscribeStatus", -1);
		String subscribe = Convert.strToStr(paramMap.get("subscribe"), "");

		if (subscribe_status != 1) {
			request().setAttribute("subscribeStatus", 2);
			String minTenderedSum = paramMap.get("minTenderedSum");
			if (StringUtils.isBlank(minTenderedSum)) {
				this.addFieldError("paramMap['minTenderedSum']", "请选择最低投标金额");
			}
		} else {
			request().setAttribute("subscribeStatus", 1);
			double subscribeMomey = Convert.strToDouble(subscribe, 0.0);
			if (subscribeMomey == 0) {
				this.addFieldError("paramMap['subscribe']", "请填写最小认购金额");
			}
			if (!subscribe
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['subscribe']", "认购金额格式不正确");
			}
			// 借款总金额
			double aountDou = Convert.strToDouble(amount, 0.0);
			if (subscribeMomey != 0 && aountDou % subscribeMomey != 0) {
				this.addFieldError("paramMap['subscribe']", "认购金额应能被借款总额整除");
			}
		}
	}
	
	
	/**
	 * 后台发布借款
	 * bgAddBorrow
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-4 下午03:14:53
	 */
	public String bgAddBorrow() throws Exception {
		Thread.sleep(100);
		Admin admin = (Admin) session().getAttribute("admin");
		 long publisherId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);
		 String userName="";
		
		if (admin != null) {
			long applyId = Convert.strToLong(paramMap.get("applyId"), -1);
			String encUserId = paramMap.get("userId");
			DesSecurityUtil des = new DesSecurityUtil();
			 Long userId = -1L;
			 boolean back = false; 
			 if(!encUserId.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
				 userId = Convert.strToLong(des.decrypt(encUserId), -1);
			 }else{
				 userId = Convert.strToLong(encUserId, -1);
				 back = true; 
			 }; //用户ID
			//long userId = Convert.strToLong(paramMap.get("userId"), -1);
			 String borrowWay = paramMap.get("borrowWay");
				String publishTime = paramMap.get("publishTime");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date publishDate=sdf.parse(publishTime);
				
				//whb 添加是否专享标
				int isExclus = Convert.strToInt(paramMap.get("isExclus"), 0);
				int isLimitMaxMoney = 0;//是否限制最大投标金额  0-不限制，2-限制
				double maxMoneyValue = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);//最大投标金额
				
				int borrowType = Convert.strToInt(paramMap.get("borrowType"), -1);
				String deadLine = paramMap.get("deadLine");
				String deadDay = paramMap.get("deadDay");
				String daythe = paramMap.get("daythe");
				String paymentMode = paramMap.get("paymentMode");
				int daytheInt = IConstants.DAY_THE_1;
				int deadLineInt = Convert.strToInt(deadLine, -1);
				int deadDayInt = Convert.strToInt(deadDay, -1);
				int paymentModeInt = Convert.strToInt(paymentMode, -1);
			// 天标
			if ("true".equals(daythe)) {
				deadLineInt = deadDayInt;
				daytheInt = IConstants.DAY_THE_2;
				// 为天标时默认就是按月分期还款
				paymentModeInt = 1;
			}
			// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
			//Object object = session().getAttribute("borrowWay1");
			//if (object == null) {
			//	returnParentUrl("借款发布权限不足", "borrow.do");
			//	return null;
			//}
			//if (user.getVipStatus() != IConstants.VIP_STATUS) {
			//	getOut().print(
				//		"<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
				//				+ request().getContextPath()
				//				+ "/home.do';</script>");
				//return null;
			//}
			String title = paramMap.get("title");
			String imgPath = paramMap.get("imgPath");
			String purpose = paramMap.get("purpose");
			int purposeInt = Convert.strToInt(purpose, -1);
			String amount = paramMap.get("amount");
			double amountDouble = Convert.strToDouble(amount, 0);
			String sum = paramMap.get("sum");
			double sumInt = Convert.strToDouble(sum, 0);
			if (sumInt > amountDouble) {
				this.addFieldError("enough", " *   奖励金额不能大于借款金额!");
				return "input";
			}
//			boolean res = userService.checkSign(publisherId);
//			if (!res) {
//				request().getSession().removeAttribute("user");
//				request().getSession().invalidate();
//				getOut()
//						.print(
//								"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
//				return null;
//			}
			String sumRate = paramMap.get("sumRate");
			double sumRateDouble = Convert.strToDouble(sumRate, -1);
			String annualRate = paramMap.get("annualRate");
			double annualRateDouble = Convert.strToDouble(annualRate, 0);
			int minTenderedSumInt = 0;// 最小投标金额
			int maxTenderedSumInt = 0;// 最大投标金额
			// 是否启用认购模式
			int subscribe_status = request.getInt("subscribeStatus", 2);
			// 认购金额
			String subscribe = Convert.strToStr(paramMap.get("subscribe"), "");
			int circulationNumber = 0;
			if (subscribe_status != 1) {
				String minTenderedSum = paramMap.get("minTenderedSum");
				minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
				String maxTenderedSum = paramMap.get("maxTenderedSum");
				maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
			} else {
				// 得到认购总分份数
				circulationNumber = Integer.parseInt(amount)
						/ Integer.parseInt(subscribe);
			}
			Integer raiseTerm = Convert.strToInt(paramMap.get("raiseTerm"), -1);
			int raiseTermInt = raiseTerm;
			String excitationType = paramMap.get("excitationType");
			if (StringUtils.isNotBlank(excitationType)) {
				// 按借款金额比例奖励
				if (StringUtils.isNumericSpace(excitationType)
						&& "3".equals(excitationType)) {
					sumInt = sumRateDouble;
				}
			}
			int excitationTypeInt = Convert.strToInt(excitationType, -1);
			String detail = paramMap.get("detail");
			//String borrowWay = (String) (object == null ? "" : object);
			String remoteIP = ServletUtils.getRemortIp();
			int borrowWayInt = Convert.strToInt(borrowWay, -1);

			if (borrowWayInt <= 0) {
				this.addFieldError("enough", "无效操作!");
				return "input";

			}
			// 查询标种类型
			Map<String, String> borrowTypeMap = getBorrowTypeMap(borrowWay.toString());
			int number = Convert.strToInt(
					borrowTypeMap.get("subscribe_status"), -1);
			if (number != subscribe_status) {
				this.addFieldError("paramMap['allError']",
						"无效操作,该模式已关闭,请重新发布借款!");
				return "input";
			}
			// 冻结保证金
			double frozenMargin = 0;
			//if (user.getVipStatus() == 2) {
				// vip冻结保证金
				//frozenMargin = amountDouble
				//		* Double.parseDouble(borrowTypeMap
				//				.get("vip_frost_scale")) / 100;
			//} else {
				// 普通会员冻结保证金
				//frozenMargin = amountDouble
					//	* Double.parseDouble(borrowTypeMap
				//				.get("all_frost_scale")) / 100;
			//}
			// 当借款为净值借款时，需要验证所输入的金额小于净值的70%
			//if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
				//Map<String, String> map = borrowService
						//.queryBorrowTypeNetValueCondition(publisherId,
							//	amountDouble + frozenMargin);
				//String result = map.get("result") == null ? "" : map
						//.get("result");
				//if (!"1".equals(result)) {
				//	this.addFieldError("enough", "您发布的借款金额超过净值+保障金的70%!");
					//return "input";
				//}
			//}
			// 当借款类型为秒还借款时,需要进行验证是否满足条件
			//if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
				//Map<String, String> map = borrowService
					//	.queryBorrowTypeSecondsCondition(amountDouble,
						//		annualRateDouble, publisherId,
							//	getPlatformCost(), frozenMargin);
				//if (map == null || map.size() == 0) {
				//	this.addFieldError("enough", "您的可用金额不满足秒还借款的发布条件!");
					//return "input";
				//}
			//}
//			Map<String, String> maps = borrowService.queryBorrowFinMoney(
//					frozenMargin, publisherId);
//			if (maps == null || maps.size() == 0) {
//				this.addFieldError("enough", "您的可用金额不满足借款所需保障金的发布条件!");
//				return "input";
//			}

			Long result = -1L;
			// ----modify by houli 秒还借款调用的是另外一个方法，这里只需要判断净值借款即可(否则这个中间应该用&&)
//			if (!IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
//				// 除了秒还借款和净值借款之外，其他要验证可用信用额度是否大于发布借款金额，同时发布成功后要扣除可用信用额度
//				Map<String, String> map = borrowService.queryCreditLimit(
//						amountDouble, publisherId);
//				if (map == null || map.size() == 0) {
//					this.addFieldError("enough", "您的可用信用额度不足以发布["
//							+ amountDouble + "]元的借款!");
//					return "input";
//				}
//			}
			// 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
			//Long re = borrowService.queryBorrowStatus(user.getId());
			//if (re < 0) {
			//	getOut().print(
				//		"<script>alert('你还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='"
				//				+ request().getContextPath()
					//			+ "/borrow.do';</script>");
				//return null;
			//}
			String investPWD = paramMap.get("investPWD");
			if (StringUtils.isNotBlank(investPWD)) {
				investPWD = Encrypt.MD5(investPWD);
			}
			String hasPWD = paramMap.get("hasPWD");
			if ("true".equals(hasPWD)) {
				hasPWD = "1";
			}
			int hasPWDInt = Convert.strToInt(hasPWD, -1);
			// 得到所有平台所有收费标准
			List<Map<String, Object>> mapList = platformCostService
					.queryAllPlatformCost();

			Map<String, Object> mapfee = new HashMap<String, Object>();
			Map<String, Object> mapFeestate = new HashMap<String, Object>();
			for (Map<String, Object> platformCostMap : mapList) {
				double costFee = Convert.strToDouble(platformCostMap
						.get("costFee")
						+ "", 0);
				int costMode = Convert.strToInt(platformCostMap.get("costMode")
						+ "", 0);
				String remark = Convert.strToStr(platformCostMap.get("remark")
						+ "", "");
				if (costMode == 1) {
					mapfee.put(platformCostMap.get("alias") + "",
							costFee * 0.01);
				} else {
					mapfee.put(platformCostMap.get("alias") + "", costFee);
				}
				mapFeestate.put(platformCostMap.get("alias") + "", remark); // 费用说明
				platformCostMap = null;
			}
			String json = JSONObject.fromObject(mapfee).toString();
			String jsonState = JSONObject.fromObject(mapFeestate).toString();
			// frozenMargin 冻结保证金
			Map<String, String> returnMap = null;
			double add_interest =  Convert.strToDouble(request().getParameter("add_interest"), 0);//加息利率			
			if(!back){
				returnMap = borrowManageService.addBorrowByAdmin(title, imgPath, borrowWayInt,
						purposeInt, deadLineInt, paymentModeInt, amountDouble,
						annualRateDouble, minTenderedSumInt, maxTenderedSumInt,
						raiseTermInt, excitationTypeInt, sumInt, detail, 1,
						investPWD, hasPWDInt, remoteIP, publisherId, frozenMargin,
						daytheInt, getBasePath(), userName, Convert
								.strToDouble(subscribe, 0), circulationNumber, 0,
						subscribe_status, borrowTypeMap.get("identifier"),
						frozenMargin, json, jsonState,borrowType,publishDate,applyId,userId);
			}else{
				Map<String,String> userMap = userService.queryUserById(userId);
				returnMap = borrowService.addBorrow(title, imgPath, borrowWayInt,
						purposeInt, deadLineInt, paymentModeInt, amountDouble, annualRateDouble,
						minTenderedSumInt, maxTenderedSumInt, raiseTermInt,
						excitationTypeInt, sumInt, detail, 1, investPWD, hasPWDInt,
						remoteIP, userId, frozenMargin, daytheInt, getBasePath(),
						userMap.get("username"), Convert.strToDouble(subscribe, 0),
						circulationNumber, 0, subscribe_status, borrowTypeMap
								.get("identifier"), frozenMargin, json, jsonState,"",0,0,isExclus,
								isLimitMaxMoney,maxMoneyValue,add_interest);
				
			}
			result = Convert.strToLong(returnMap.get("ret") + "", -1);
			if (result < 0) {
				this.addFieldError("enough", returnMap.get("ret_desc") + "");
				return "input";
			}

			userService.updateSign(publisherId);// 更换校验码
			if (result < 0)
				return "fail";
			// 清空paramMap
			paramMap = null;
			if(back){
				Map<String, String> map = borrowManageService.queryBorrowFistAuditDetailById2(userId);
				result = -1L;
				result = borrowManageService.updateBorrowFistAuditStatus(Convert.strToLong(map.get("id"), -1L),
						Convert.strToLong(map.get("userId"), -1L), 2, "= =!", "= =", admin.getId(),
						getBasePath(),"");
				if(result > 0){
					//getOut().print("<script>alert('借款发布成功！');</script>");
					getOut().print(
									"<script>alert('借款发布成功！');window.location.href='"
											+ request().getContextPath()
											+ "/admin/enterpriseAddBorrowInit.do';</script>");
				}else{
					return "fail";
				}
			}else{
				//getOut().print("<script>alert('借款发布成功！');</script>");
				getOut().print(
						"<script>alert('借款发布成功！');window.location.href='"
								+ request().getContextPath()
								+ "/admin/enterpriseAddBorrowInit.do';</script>");
			}
		} else {
			return "nologin";
		}
		return null;
	}
	
	private Map<String, String> getBorrowTypeMap(String type) throws Exception {
		String nid = "";
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(type)) {
			// 净值借款
			nid = BorrowType.WORTH.getValue();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(type)) {
			// 秒还借款
			nid = BorrowType.SECONDS.getValue();
		} else if (IConstants.BORROW_TYPE_FIELD_VISIT.equals(type)) {
			// 实地考察借款
			nid = BorrowType.FIELD.getValue();
		} else if (IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE.equals(type)) {
			// 机构担保借款
			nid = BorrowType.INSTITUTIONS.getValue();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(type)) {
			// 信用借款
			nid = BorrowType.ORDINARY.getValue();
		} else if (IConstants.BORROW_TYPE_INSTITUTION_FLOW.equals(type)) {
			nid = BorrowType.FLOW.getValue();// 流转标
		}
		session().setAttribute("nid", nid);
		return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
	}
	
	
	public void validateBgAddBorrowSeconds() throws Exception {
		this.validateBgAddBorrow();
	}
	
	/**
	 * 后台发布秒还标
	 * bgAddBorrowSeconds
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-5 上午11:16:40
	 */
	public String bgAddBorrowSeconds() throws Exception {
		Thread.sleep(100);
		 long publisherId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		 String userName="";
		 long applyId = Convert.strToLong(paramMap.get("applyId"), -1);
		 String encUserId = paramMap.get("userId");
		 DesSecurityUtil des = new DesSecurityUtil();
		 Long userId = -1L;
		 boolean back = false; 
		 if(!encUserId.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
			 userId = Convert.strToLong(des.decrypt(encUserId), -1);
		 }else{
			 userId = Convert.strToLong(encUserId, -1);
			 back = true;
		 }
		  //用户ID
		 //long userId = Convert.strToLong(paramMap.get("userId"), -1);
		String publishTime = paramMap.get("publishTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date publishDate=sdf.parse(publishTime);
		
		int borrowType = Convert.strToInt(paramMap.get("borrowType"), -1);
		
		// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
		//Object object = session().getAttribute("borrowWay");
		//if (object == null) {
			//returnParentUrl("", "borrow.do");
		//	return null;
		//}
		//if (user.getVipStatus() != IConstants.VIP_STATUS) {
			//getOut().print(
				//	"<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
					//		+ request().getContextPath()
						//	+ "/home.do';</script>");
			//return null;
		//}
		boolean res = userService.checkSign(publisherId);
		if (!res) {
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut()
					.print(
							"<script>alert('*您的账号出现异常，请速与管理员联系! ');</script>");
			return null;
		}
		String title = paramMap.get("title");
		String imgPath = paramMap.get("imgPath");
		String purpose = paramMap.get("purpose");
		int purposeInt = Convert.strToInt(purpose, -1);
		String deadLine = paramMap.get("deadLine");
		int deadLineInt = Convert.strToInt(deadLine, 0);
		int paymentModeInt = IConstants.PAY_WAY_SECONDS;
		String amount = paramMap.get("amount");
		double amountDouble = Convert.strToDouble(amount, 0);
		String sum = paramMap.get("sum");
		double sumInt = Convert.strToDouble(sum, 0);
		String annualRate = paramMap.get("annualRate");
		double annualRateDouble = Convert.strToDouble(annualRate, 0);
		int minTenderedSumInt = 0;// 最小投标金额
		int maxTenderedSumInt = 0;// 最大投标金额
		// 是否启用认购模式
		int subscribe_status = request.getInt("subscribeStatus", -1);
		// 认购金额
		String subscribe = Convert.strToStr(paramMap.get("subscribe"), "");
		int circulationNumber = 0;
		if (subscribe_status != 1) {
			String minTenderedSum = paramMap.get("minTenderedSum");
			minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
			String maxTenderedSum = paramMap.get("maxTenderedSum");
			maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
		} else {
			// 得到认购总分份数
			circulationNumber = Integer.parseInt(amount)
					/ Integer.parseInt(subscribe);
		}
		String raiseTerm = paramMap.get("raiseTerm");
		int raiseTermInt = Convert.strToInt(raiseTerm, -1);
		String investPWD = paramMap.get("investPWD");
		if (StringUtils.isNotBlank(investPWD)) {
			investPWD = Encrypt.MD5(investPWD);
		}
		String hasPWD = paramMap.get("hasPWD");
		if ("true".equals(hasPWD)) {
			hasPWD = "1";
		}
		int hasPWDInt = Convert.strToInt(hasPWD, -1);
		String detail = paramMap.get("detail");
		String borrowWay = IConstants.BORROW_TYPE_SECONDS;
		String remoteIP = ServletUtils.getRemortIp();
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		
		//whb 添加是否专享标
		int isExclus = Convert.strToInt(paramMap.get("isExclus"), 0);
		int isLimitMaxMoney = 0;//是否限制最大投标金额  0-不限制，1-限制
		double maxMoneyValue = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);//最大投标金额

		// 查询标种详情
		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
		// 冻结保证金 ----------------
		double frozenMargin = 0;
		//if (user.getVipStatus() == 2) {
			// vip冻结保证金
			//frozenMargin = amountDouble
			//		* Double.parseDouble(borrowTypeMap.get("vip_frost_scale"))
				//	/ 100;
		//} else {
			// 普通会员冻结保证金
			//frozenMargin = amountDouble
			//		* Double.parseDouble(borrowTypeMap.get("all_frost_scale"))
				//	/ 100;
		//}

		// 平台收费
		//Map<String, Object> platformCostMap = getPlatformCost();
		Map<String, Object> platformCostMap = borrowService.queryplatformCostLog(borrowTypeMap.get("identifier"));
		double costFee = Convert.strToDouble(platformCostMap
				.get("locan_fee")+"", 0);
		// 秒还借款冻结借款+借款利息+借款手续费 + 冻结保证金
		double fee = (amountDouble * (annualRateDouble * 0.01 / 12))
				+ (amountDouble * (costFee/100)) + frozenMargin;
		// 当借款类型为秒还借款时,需要进行验证是否满足条件
//		if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
//			Map<String, String> map = borrowService
//					.queryBorrowTypeSecondsCondition(amountDouble,
//							annualRateDouble, publisherId, platformCostMap,
//							frozenMargin);
//			if (map == null || map.size() == 0) {
//				this.addFieldError("enough", "您的可用金额不满足秒还借款的发布条件!");
//				return "input";
//			}
//		}
		Long result = -1L;
		// ------add by houli 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
		//Long re = borrowService.queryBorrowStatus(user.getId());
		//if (re < 0) {
		//	getOut().print(
			//		"<script>alert('你还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='"
				//			+ request().getContextPath()
					//		+ "/borrow.do';</script>");
			//return null;
		//}
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = platformCostService
				.queryAllPlatformCost();

		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		for (Map<String, Object> platformCostMaps : mapList) {
			double costFees = Convert.strToDouble(platformCostMaps
					.get("costFee")
					+ "", 0);
			int costMode = Convert.strToInt(platformCostMaps.get("costMode")
					+ "", 0);
			String remark = Convert.strToStr(platformCostMaps.get("remark")
					+ "", "");
			if (costMode == 1) {
				mapfee.put(platformCostMaps.get("alias") + "", costFees * 0.01);
			} else {
				mapfee.put(platformCostMaps.get("alias") + "", costFees);
			}
			mapFeestate.put(platformCostMaps.get("alias") + "", remark); // 费用说明
			platformCostMaps = null;
		}
		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		Map<String, String> returnMap = null;
		double add_interest =  Convert.strToDouble(request().getParameter("add_interest"), 0);//加息利率		
		if(!back){
			returnMap = borrowManageService.addBorrowByAdmin(title, imgPath, borrowWayInt,
					purposeInt, deadLineInt, paymentModeInt, amountDouble,
					annualRateDouble, minTenderedSumInt, maxTenderedSumInt,
					raiseTermInt, 1, sumInt, detail, 1, investPWD, hasPWDInt,
					remoteIP, publisherId, fee, IConstants.DAY_THE_1,
					getBasePath(), userName, Convert.strToDouble(
							subscribe, 0), circulationNumber, 0, subscribe_status,
					borrowTypeMap.get("identifier"), frozenMargin, json, jsonState,borrowType,publishDate,applyId,userId);
		}else{
			Map<String,String> userMap = userService.queryUserById(userId);
			returnMap = borrowService.addBorrow(title, imgPath, borrowWayInt
					, purposeInt, deadLineInt, paymentModeInt, amountDouble, 
					annualRateDouble,minTenderedSumInt, maxTenderedSumInt, raiseTermInt,
					1, sumInt, detail, 1, investPWD, 
					hasPWDInt, remoteIP, userId, fee, IConstants.DAY_THE_1, getBasePath(), userMap.get("username"), 
					Convert.strToDouble(subscribe, 0), circulationNumber, 0, 
					subscribe_status, borrowTypeMap
					.get("identifier"), frozenMargin, json, jsonState,"",0,0,isExclus,
					isLimitMaxMoney,maxMoneyValue,add_interest);
			
		}
		
		userService.updateSign(publisherId);// 更换校验码
		result = Convert.strToLong(returnMap.get("ret") + "", -1);
		if (result < 0) {
			this.addFieldError("enough", returnMap.get("ret_desc") + "");
			return "input";
		}
		if (result < 0)
			return "fail";
		// 清空paramMap
		paramMap = null;
		// modify by houli 客户要求发布借款之后要给出提示
		if(back){
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			Map<String, String> map = borrowManageService.queryBorrowFistAuditDetailById2(userId);
			result = -1L;
			result = borrowManageService.updateBorrowFistAuditStatus(Convert.strToLong(map.get("id"), -1L),
					Convert.strToLong(map.get("userId"), -1L), 2, "ㅡ.,ㅡ", "ㅡ.,ㅡ", admin.getId(),
					getBasePath(), "");
			if(result > 0){
				//getOut().print("<script>alert('借款发布成功！');</script>");
				getOut().print(
						"<script>alert('借款发布成功！');window.location.href='"
								+ request().getContextPath()
								+ "/admin/enterpriseAddBorrowInit.do';</script>");
			}else{
				return "fail";
			}
		}else{
			//getOut().print("<script>alert('借款发布成功！');</script>");
			getOut().print(
					"<script>alert('借款发布成功！');window.location.href='"
							+ request().getContextPath()
							+ "/admin/enterpriseAddBorrowInit.do';</script>");
		}
		return null;
	}
	
	/**
	 * 个人申请后台发布借款列表初始化
	 * addBorrowInit
	 * @return
	 * @autthor linww
	 * 2014-6-6 上午08:59:51
	 */
	public String personageAddBorrowInit(){
		return SUCCESS;
	}
	
	/**
	 * 个人申请后台发布借款列表
	 * queryPersonageApplyList
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 上午09:00:00
	 */
	public String queryPersonageApplyList() throws Exception{
		String name = paramMap.get("name");
		String telephone = paramMap.get("telephone");
		double borrowAmount = Convert.strToDouble(paramMap.get("borrowAmount"), 0);
		
		borrowManageService.queryPersonageApplyList(pageBean,name,telephone,borrowAmount);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

        
		return SUCCESS;
	}
	
	/**
	 * 企业申请后台发布借款列表初始化
	 * enterpriseAddBorrowInit
	 * @return
	 * @autthor linww
	 * 2014-6-6 上午08:59:51
	 */
	public String enterpriseAddBorrowInit(){
		return SUCCESS;
	}
	
	/**
	 * 企业融资列表
	 * queryEnterpriseApplyList
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 上午09:00:00
	 */
	public String queryEnterpriseApplyList() throws Exception{
		String name = paramMap.get("name");
		String telephone = paramMap.get("telephone");
		double borrowAmount = Convert.strToDouble(paramMap.get("borrowAmount"), 0);
		
		borrowManageService.queryEnterpriseApplyList(pageBean,name,telephone,borrowAmount);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

	    
		return SUCCESS;
	}
	
	
	/**
	 * 个人填写信息初始化
	 * userBaseInfoInit
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 上午11:00:04
	 */
	public String userBaseInfoInit() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapWork = new HashMap<String, String>();
		
		long applyId = Convert.strToLong(paramMap.get("applyId"), -1); //申请ID
		long  userId= -1L;
		 int authStep =-1;
		//根据申请id去查询是否填写基本信息
		Map<String, String> userMap  = borrowManageService.queryUserBaseInfo(applyId);
		if(userMap!=null){
			userId = Convert.strToLong(userMap.get("userId"), -1) ;
			authStep =Convert.strToInt(userMap.get("authStep"), -1);
		}
		
		 
		
		String birth = null;
		String rxedate = null;
		// --------modify by houli
		String from = getFrom();
		if (from == null) {
			from = request.getString("from");
		}
		String btype = getBtype();

		if (from != null) {
			request().setAttribute("from", from);
		}
		if (btype != null) {
			request().setAttribute("btype", btype);
		}
		// -----------
			map = userService.queryPersonById(userId);
			//map1 = userService.queryPersonById(userId);
			mapWork = validateService.queryWorkDataById(userId);
			if(mapWork!=null){
				map.putAll(mapWork);
			}
			if (map != null && map.size() > 0) {
				workPro = Convert.strToLong(map.get("workPro")
						.toString(), -1L);
				cityId = Convert.strToLong(map.get("workCity")
						.toString(), -1L);
				@SuppressWarnings("unused")
				String birthd = map.get("birthday");
				birth = Convert.strToStr(map.get("birthday"), null);
				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
				if (birth != null) {
					birth = birth.substring(0, 10);
				}
				if (rxedate != null) {
					rxedate = rxedate.substring(0, 10);
				}

			}
		// 判断用户是否已经填写了基本信息
		String flag = "";
		if (map != null && map.size() > 0) {// 用户基本资料有数据但是不一定是已经填写了基本资料信息
											// 还有可能是上传了个人头像
			if (!StringUtils.isBlank(map.get("realName"))) {// 不为空
				flag = "1";
			} else {
				flag = "2";
			}
		} else {
			flag = "2";
		}
		request().setAttribute("flag", flag);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);

		request().setAttribute("ISDEMO", IConstants.ISDEMO);
		request().setAttribute("applyId",applyId);
		DesSecurityUtil des = new DesSecurityUtil();
		request().setAttribute("uId",userId);
		request().setAttribute("userId",des.encrypt(userId+"")); //加密userId
		request().setAttribute("authStep",authStep);
		

		return SUCCESS;
		
		
	}
	
	/**
	 * 个人基本信息保存
	 * updateUserBaseInfoAdmin
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 下午02:19:25
	 */
	public String updateUserBaseInfoAdmin()  throws Exception {
		String encUserId = paramMap.get("userId");
		DesSecurityUtil des = new DesSecurityUtil();
		long userId =Convert.strToLong(des.decrypt(encUserId), -1);
        
		//插入基本信息生成新的userID
		if(userId==-1){
	       userId = borrowManageService.getUserId();
	    }
		
		JSONObject json = new JSONObject();
		String realName = paramMap.get("realName");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String idNo = paramMap.get("idNo");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(idNo.substring(12, 15));
		} else {
			sortCode = Integer.parseInt(idNo.substring(14, 17));
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 1;// 男性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 2;// 女性身份证
		} else {
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}
		String iDresutl = "";
		iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
		if (iDresutl != "") {
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}

		String cellPhone = paramMap.get("cellPhone");// 手机号码
		if (StringUtils.isBlank(cellPhone)) {
			json.put("msg", "请正确填写手机号码");
			JSONUtils.printObject(json);
			return null;
		} else if (cellPhone.length() != 11) {
			json.put("msg", "手机号码长度不对");
			JSONUtils.printObject(json);
			return null;
		}
		
		Map<String, String> pMap = null;
		pMap = beVipService.queryPUser(userId);
		if (pMap == null) {
			pMap = new HashMap<String, String>();
		}
		String isno = Convert.strToStr(pMap.get("idNo"), "");
		// 验证手机的唯一性
		Map<String, String> phonemap = new HashMap<String, String>();
		// 验证手机的唯一性
		phonemap = beVipService.queryIsPhone(cellPhone);
		// 测试--跳过验证
		if (IConstants.ISDEMO.equals("1")) {

		} else {
			// add by houli 判断身份证的唯一性

			if (StringUtils.isBlank(isno)) {
				Map<String, String> idNoMap = beVipService.queryIDCard(idNo);
				if (idNoMap != null && !idNoMap.isEmpty()) {
					json.put("msg", "身份证已注册");
					JSONUtils.printObject(json);
					return null;
				}
			}

		}
		String cellp = Convert.strToStr(pMap.get("cellphone"), "");
		if (StringUtils.isBlank(isno)) {
			if (!cellp.equals(cellPhone) && phonemap != null) {
				json.put("msg", "手机已存在");
				JSONUtils.printObject(json);
				return null;
			}
		}
		
		Long applyId = Convert.strToLong(paramMap.get("applyId"), -1); //申请记录ID
		String sex = Convert.strToStr(paramMap.get("sex"), null);// 性别(男 女)
		String birthday = Convert.strToStr(paramMap.get("birthday"), null);// 出生日期
		String highestEdu = Convert.strToStr(paramMap.get("highestEdu"), null);// 最高学历
		String eduStartDay = Convert.strToStr(paramMap.get("eduStartDay"), null);// 入学年份
		String school = Convert.strToStr(paramMap.get("school"), null);// 毕业院校
		String maritalStatus = Convert.strToStr(paramMap.get("maritalStatus"),
				null);// 婚姻状况(已婚 未婚)
		String hasChild = Convert.strToStr(paramMap.get("hasChild"), null);// 有无子女(有 无)
		String hasHourse = Convert.strToStr(paramMap.get("hasHourse"), null);// 是否有房(有 无)
		String hasHousrseLoan = Convert.strToStr(
				paramMap.get("hasHousrseLoan"), null);// 有无房贷(有 无)
		String hasCar = Convert.strToStr(paramMap.get("hasCar"), null);// 是否有车(有 无)
		String hasCarLoan = Convert.strToStr(paramMap.get("hasCarLoan"), null);// 有无车贷(有 无)
		
		Long workPro = Convert.strToLong(paramMap.get("workPro"), -1); //工作省份
		Long workCity = Convert.strToLong(paramMap.get("workCity"), -1); //工作城市
		String companyType = Convert.strToStr(paramMap.get("companyType"), null); //公司类别
		String companyLine = Convert.strToStr(paramMap.get("companyLine"), null);  //公司行业 
		String companyScale = Convert.strToStr(paramMap.get("companyScale"), null); //公司规模
		String job = Convert.strToStr(paramMap.get("job"), null); //职位
		
		long personId = -1L;
		
			if (StringUtils.isBlank(sex)) {
				json.put("msg", "请正确填写性别");
				JSONUtils.printObject(json);
				return null;
			}
			if (StringUtils.isBlank(birthday)) {
				json.put("msg", "请正确填写出生日期");
				JSONUtils.printObject(json);
				return null;
			}
			// 最高学历
			if (StringUtils.isBlank(highestEdu)) {
				json.put("msg", "请正确填写最高学历");
				JSONUtils.printObject(json);
				return null;
			}
			// 入学年份
			if (StringUtils.isBlank(eduStartDay)) {
				json.put("msg", "请正确填写入学年份");
				JSONUtils.printObject(json);
				return null;
			}
			// 毕业院校
			if (StringUtils.isBlank(school)) {
				json.put("msg", "请正确填写入毕业院校");
				JSONUtils.printObject(json);
				return null;
			}
			//工作省份
			if(workPro==-1){
				json.put("msg", "请正确填写入工作省份");
				JSONUtils.printObject(json);
				return null;
			}
			//工作城市
			if(workCity==-1){
				json.put("msg", "请正确填写入工作城市");
				JSONUtils.printObject(json);
				return null;
			}
			//公司类别
			if (StringUtils.isBlank(companyType)) {
				json.put("msg", "请正确填写入公司类别");
				JSONUtils.printObject(json);
				return null;
			}
			//公司行业
			if (StringUtils.isBlank(companyLine)) {
				json.put("msg", "请正确填写入公司行业");
				JSONUtils.printObject(json);
				return null;
			}
			//公司规模
			if (StringUtils.isBlank(companyScale)) {
				json.put("msg", "请正确填写入公司规模");
				JSONUtils.printObject(json);
				return null;
			}
			
			//工作职位
			if (StringUtils.isBlank(job)) {
				json.put("msg", "请正确填写工作职位");
				JSONUtils.printObject(json);
				return null;
			}

			// 婚姻状况(已婚 未婚)
			if (StringUtils.isBlank(maritalStatus)) {
				json.put("msg", "请正确填写入婚姻状况");
				JSONUtils.printObject(json);
				return null;
			}

			// 有无子女(有 无)
			if (StringUtils.isBlank(hasChild)) {
				json.put("msg", "请正确填写入有无子女");
				JSONUtils.printObject(json);
				return null;
			}
			// 是否有房(有 无)
			if (StringUtils.isBlank(hasHourse)) {
				json.put("msg", "请正确填写入是否有房");
				JSONUtils.printObject(json);
				return null;
			}

			// 有无房贷(有 无)
			if (StringUtils.isBlank(hasHousrseLoan)) {
				json.put("msg", "请正确填写入有无房贷");
				JSONUtils.printObject(json);
				return null;
			}
			// 是否有车 (有 无)
			if (StringUtils.isBlank(hasCar)) {
				json.put("msg", "请正确填写入是否有车");
				JSONUtils.printObject(json);
				return null;
			}
			// 有无车贷 (有 无)
			if (StringUtils.isBlank(hasCarLoan)) {
				json.put("msg", "请正确填写入有无车贷");
				JSONUtils.printObject(json);
				return null;
			}
		
		Map<String, String> resultMap = userService.updateUserBaseData2(realName, cellPhone, sex,
				birthday, highestEdu, eduStartDay, school, maritalStatus,
				hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
				workPro, workCity, companyType,companyLine, companyScale, job, userId, idNo ,"",applyId);
		personId=Convert.strToLong(resultMap.get("ret") + "", -1);
		if (personId > 0) {
			// ==
			//if (user.getAuthStep() == 1) {
			//	user.setAuthStep(2);
			//}
			session().removeAttribute("randomCode");
			//user.setPersonalHead(personalHead);// 将个人头像放到session里面
			json.put("msg", "保存成功");
			//json.put("userId", userId);
			request().setAttribute("userId",des.encrypt(userId+""));
			JSONUtils.printObject(json);
			request().setAttribute("person", "1");
			//user.setRealName(realName);
			//session().setAttribute("user", user);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}
	
	
	
	/**
	 * 跳转个人上传资料
	 * userUploadInit
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-9 下午03:02:04
	 */
	public String userUploadInit() throws Exception {
		//User user = (User) session().getAttribute("user");
		long userId =-1; //= Convert.strToLong(paramMap.get("userId"), -1); //用户ID
		int type=Convert.strToInt(paramMap.get("type"), -1); //用户类型
		
		long applyId =Convert.strToLong(paramMap.get("applyId"), -1);
		int authStep =-1;
		//根据申请id去查询是否填写基本信息
		Map<String, String> userMap  = borrowManageService.queryUserBaseInfo(applyId);
		if(userMap!=null){
			userId = Convert.strToLong(userMap.get("userId"), -1) ;
			authStep =Convert.strToInt(userMap.get("authStep"), -1);
		}
		
		 
		
//		if(userId==-1){
//			getOut().print(
//					"<script>alert('请先填写基本信息!');parent.location.href='"
//							+ request().getContextPath()
//							+ "admin/userBaseInfoInit.do';</script>");
//			return null;
//		}
		//userId = 5L;
		
		// Map<String,String> pictruemap = null;
		List<Map<String, Object>> basepictur = null;
		List<Map<String, Object>> selectpictur = null;
		// -----------modify by houli
//		String from = request.getString("from");
//		String btype = request.getString("btype");
		// -------------
//			if (from == null || from.equals("")) {
//				// 获取用户认证进行的步骤
//				if (user.getAuthStep() == 1) {
//					// 个人信息认证步骤
//					return "querBaseData";
//				} else if (user.getAuthStep() == 2) {
//					// 工作信息认证步骤
//					return "querWorkData";
//				} else if (user.getAuthStep() == 3) {
//					// VIP申请认证步骤
//					return "quervipData";
//				}
//				// ---------add by houli
//				else if (user.getAuthStep() == 5
//						&& (btype != null && !btype.equals(""))) {
//					return "jumpOther";
//				}
//			} else {// 净值借款跟秒还借款操作步骤
//				// 获取用户认证进行的步骤
//				if (user.getAuthStep() == 1) {
//					// 个人信息认证步骤
//					return "querBaseData";
//				}
//
//				if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
//					return "quervipData";
//				}
//
//				// -------add by houli
//				// return jumpToBorrow(btype);
//				if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
//					return "jumpNet";
//				} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
//					return "jumpSeconds";
//				}
//				// -----------------
//			}

			//userId = user.getId();
			// 获取到图片的地址和图片的状态值
			// pictruemap = userService.queryUserPictureStatus(userId);
			// request().setAttribute("pictruemap", pictruemap);
			basepictur = userService.queryBasePictureAdmin(userId);// 五大基本
			selectpictur = userService.querySelectPictureAdmin(userId);// 可选
			request().setAttribute("basepictur", basepictur);
			request().setAttribute("selectpictur", selectpictur);
			DesSecurityUtil des = new DesSecurityUtil();
			request().setAttribute("userId",des.encrypt(userId+""));
			request().setAttribute("authStep", authStep);
			request().setAttribute("applyId", applyId);
			request().setAttribute("type", type);

			
			return SUCCESS;


	}
	
	/**
	 * 企业填写基本信息初始化
	 * enterpriseUserBaseInfoInit
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-11 下午05:54:50
	 */
	public String enterpriseUserBaseInfoInit() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		//Map<String, String> mapWork = new HashMap<String, String>();
		
		long applyId = Convert.strToLong(paramMap.get("applyId"), -1); //申请ID
		long  userId= -1L;
		 int authStep =-1;
		//根据申请id去查询是否填写基本信息
		Map<String, String> userMap  = borrowManageService.queryUserBaseInfo(applyId);
		if(userMap!=null){
			userId = Convert.strToLong(userMap.get("userId"), -1) ;
			authStep =Convert.strToInt(userMap.get("authStep"), -1);
		}
		
		 
		
		//String birth = null;
		//String rxedate = null;
		// --------modify by houli
		//String from = getFrom();
		//if (from == null) {
		//	from = request.getString("from");
		//}
		//String btype = getBtype();

		//if (from != null) {
		//	request().setAttribute("from", from);
		//}
		//if (btype != null) {
		//	request().setAttribute("btype", btype);
		//}
		// -----------
		String registeredTime =null;
			map = userService.queryEnterpriseById(userId);
			if(map!=null){
				
				registeredTime = Convert.strToStr(map.get("registeredTime"), null);
				if (registeredTime != null) {
					registeredTime = registeredTime.substring(0, 10);
				}
			}
			//map1 = userService.queryPersonById(userId);
			//mapWork = validateService.queryWorkDataById(userId);
			//if(mapWork!=null){
			//	map.putAll(mapWork);
			//}
//			if (map != null && map.size() > 0) {
//				workPro = Convert.strToLong(map.get("workPro")
//						.toString(), -1L);
//				cityId = Convert.strToLong(map.get("workCity")
//						.toString(), -1L);
//				@SuppressWarnings("unused")
//				String birthd = map.get("birthday");
//				birth = Convert.strToStr(map.get("birthday"), null);
//				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
//				if (birth != null) {
//					birth = birth.substring(0, 10);
//				}
//				if (rxedate != null) {
//					rxedate = rxedate.substring(0, 10);
//				}
//
//			}
		// 判断用户是否已经填写了基本信息
		//String flag = "";
		//if (map != null && map.size() > 0) {// 用户基本资料有数据但是不一定是已经填写了基本资料信息
		//									// 还有可能是上传了个人头像
		//	if (!StringUtils.isBlank(map.get("realName"))) {// 不为空
		//		flag = "1";
		//	} else {
		//		flag = "2";
		//	}
		//} else {
		//	flag = "2";
		//}
		//request().setAttribute("flag", flag);
		//provinceList = regionService.queryRegionList(-1L, 1L, 1);
		//cityList = regionService.queryRegionList(-1L, workPro, 2);
		request().setAttribute("map", map);
		//request().setAttribute("provinceList", provinceList);
		//request().setAttribute("cityList", cityList);
		//request().setAttribute("birth", birth);
		//request().setAttribute("rxedate", rxedate);

		//request().setAttribute("ISDEMO", IConstants.ISDEMO);
		request().setAttribute("registeredTime",registeredTime);
		request().setAttribute("applyId",applyId);
		DesSecurityUtil des = new DesSecurityUtil();
		request().setAttribute("uId",userId);
		request().setAttribute("userId",des.encrypt(userId+"")); //加密userId
		request().setAttribute("authStep",authStep);
		

		return SUCCESS;
		
		
	}
	
   /**
    * 保存企业基本信息	
    * updateEnterpriseUserBaseInfo
    * @return
    * @throws Exception
    * @autthor linww
    * 2014-6-9 下午02:36:05
    */
   public String updateEnterpriseUserBaseInfo()  throws Exception {
	    String encUserId = paramMap.get("userId");
		DesSecurityUtil des = new DesSecurityUtil();
		long userId =Convert.strToLong(des.decrypt(encUserId), -1);

		if(userId==-1){
	       userId = borrowManageService.getUserId();
	    }
		
		JSONObject json = new JSONObject();
		
		Long applyId = Convert.strToLong(paramMap.get("applyId"), -1); //申请记录ID
		String companyName =Convert.strToStr( paramMap.get("companyName"),null);
		String legalPerson =Convert.strToStr( paramMap.get("legalPerson"),null);
		String registeredTime = Convert.strToStr(paramMap.get("registeredTime"), null);
	
		double registeredCapital =Convert.strToDouble( paramMap.get("registeredCapital"),0);
		String businessCode =Convert.strToStr( paramMap.get("businessCode"),null);
		String companyAddress =Convert.strToStr( paramMap.get("companyAddress"),null);
		String companyPhone =Convert.strToStr( paramMap.get("companyPhone"),null);
		String borrowCause =Convert.strToStr( paramMap.get("borrowCause"),null);
		
		
		
		long personId = -1L;
		
			if (StringUtils.isBlank(companyName)) {
				json.put("msg", "请正确填写企业名称");
				JSONUtils.printObject(json);
				return null;
			}
		
			if (StringUtils.isBlank(legalPerson)) {
				json.put("msg", "请正确填写法定人姓名");
				JSONUtils.printObject(json);
				return null;
			} else if (2 > legalPerson.length() || 20 < legalPerson.length()) {
				json.put("msg", "法定人姓名的长度为不小于2和大于20");
				JSONUtils.printObject(json);
				return null;
			}
			
			if (registeredCapital==0) {
				json.put("msg", "请正确填写注册资金");
				JSONUtils.printObject(json);
				return null;
			}
			
			if (StringUtils.isBlank(registeredTime)) {
				json.put("msg", "请正确填写成立日期");
				JSONUtils.printObject(json);
				return null;
			}
			
			if (StringUtils.isBlank(businessCode)) {
				json.put("msg", "请正确填写营业执照号");
				JSONUtils.printObject(json);
				return null;
			}
			if (StringUtils.isBlank(companyAddress)) {
				json.put("msg", "请正确填写注册地址");
				JSONUtils.printObject(json);
				return null;
			}
			
			if (StringUtils.isBlank(companyPhone)) {
				json.put("msg", "请正确填写公司电话");
				JSONUtils.printObject(json);
				return null;
			}
			if (StringUtils.isBlank(borrowCause)) {
				json.put("msg", "请正确填写借款原因");
				JSONUtils.printObject(json);
				return null;
			}
			
		
		Map<String, String> resultMap = userService.updateEnterpriseUserBaseData(userId,companyName, legalPerson, registeredTime,
				registeredCapital, businessCode, companyAddress, companyPhone, borrowCause,applyId);
		personId=Convert.strToLong(resultMap.get("ret") + "", -1);
		if (personId > 0) {
			// ==
			//if (user.getAuthStep() == 1) {
			//	user.setAuthStep(2);
			//}
			session().removeAttribute("randomCode");
			//user.setPersonalHead(personalHead);// 将个人头像放到session里面
			json.put("msg", "保存成功");
			//json.put("userId", userId);
			request().setAttribute("userId",des.encrypt(userId+""));
			JSONUtils.printObject(json);
			request().setAttribute("person", "1");
			//user.setRealName(realName);
			//session().setAttribute("user", user);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}
   
   
   /**
    * 跳转企业上传资料
    * enterpriseUserUploadInit
    * @return
    * @throws Exception
    * @autthor linww
    * 2014-6-9 下午03:02:53
    */
	public String enterpriseUserUploadInit() throws Exception {
		//User user = (User) session().getAttribute("user");
		long userId = -1;//Convert.strToLong(paramMap.get("userId"), -1); //用户ID
		int type=Convert.strToInt(paramMap.get("type"), -1); //用户类型
		
		long applyId =Convert.strToLong(paramMap.get("applyId"), -1);
		int authStep =-1;
		//根据申请id去查询是否填写基本信息
		Map<String, String> userMap  = borrowManageService.queryUserBaseInfo(applyId);
		if(userMap!=null){
			userId = Convert.strToLong(userMap.get("userId"), -1) ;
			authStep =Convert.strToInt(userMap.get("authStep"), -1);
		}
		
		 
		
//		if(userId==-1){
//			getOut().print(
//					"<script>alert('请先填写基本信息!');parent.location.href='"
//							+ request().getContextPath()
//							+ "admin/userBaseInfoInit.do';</script>");
//			return null;
//		}
		//userId = 5L;
		
		// Map<String,String> pictruemap = null;
		List<Map<String, Object>> basepictur = null;
		//List<Map<String, Object>> selectpictur = null;
		// -----------modify by houli
//		String from = request.getString("from");
//		String btype = request.getString("btype");
		// -------------
//			if (from == null || from.equals("")) {
//				// 获取用户认证进行的步骤
//				if (user.getAuthStep() == 1) {
//					// 个人信息认证步骤
//					return "querBaseData";
//				} else if (user.getAuthStep() == 2) {
//					// 工作信息认证步骤
//					return "querWorkData";
//				} else if (user.getAuthStep() == 3) {
//					// VIP申请认证步骤
//					return "quervipData";
//				}
//				// ---------add by houli
//				else if (user.getAuthStep() == 5
//						&& (btype != null && !btype.equals(""))) {
//					return "jumpOther";
//				}
//			} else {// 净值借款跟秒还借款操作步骤
//				// 获取用户认证进行的步骤
//				if (user.getAuthStep() == 1) {
//					// 个人信息认证步骤
//					return "querBaseData";
//				}
//
//				if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
//					return "quervipData";
//				}
//
//				// -------add by houli
//				// return jumpToBorrow(btype);
//				if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
//					return "jumpNet";
//				} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
//					return "jumpSeconds";
//				}
//				// -----------------
//			}

			//userId = user.getId();
			// 获取到图片的地址和图片的状态值
			// pictruemap = userService.queryUserPictureStatus(userId);
			// request().setAttribute("pictruemap", pictruemap);
			basepictur = userService.queryEnterprisePicture(userId);// 五大基本
		//	selectpictur = userService.querySelectPictureAdmin(userId);// 可选
			
			if(basepictur.size() > 0){
				request().setAttribute("basepictur",  basepictur.subList(0, basepictur.size()-1));
				
				request().setAttribute("otherpictur", basepictur.subList(basepictur.size()-1, basepictur.size()));
			}
			
			//request().setAttribute("selectpictur", selectpictur);
			DesSecurityUtil des = new DesSecurityUtil();
			request().setAttribute("userId",des.encrypt(userId+""));
			request().setAttribute("authStep", authStep);
			request().setAttribute("applyId", applyId);
			request().setAttribute("type", type);
			
			return SUCCESS;


	}
	
	/**
	 * 导出申请列表
	 * exportApplyList
	 * @return
	 * @autthor linww
	 * 2014-6-10 上午08:53:39
	 */
	public String exportApplyList() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			
			String name = request.getString("name") == null ? ""
					: request.getString("name");
			String telephone = request.getString("telephone") == null ? ""
					: request.getString("telephone");
			double borrowAmount = Convert.strToDouble(request.getString("telephone"), 0) ;
		
			name = URLDecoder.decode(name, "UTF-8");
			
			
			borrowManageService.queryApplyList(pageBean,name,telephone,borrowAmount);
			
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
			
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户后台发标申请列表", pageBean.getPage(), new String[] { "姓名",
					"性别", "联系电话", "借款金额", "email", "通讯地址 "}, new String[] { "tname",
					"sex", "telephone", "borrowAmount", "email",
					"address"});
			this.export(wb, new Date().getTime() + ".xls");
			operationLogService.addOperationLog("t_apply", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"用户后台发标申请列表", 2);

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
	 * 根据ID删除申请记录
	 * deleteApplyById
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-10 下午01:25:42
	 */
	public String deleteApplyById() throws Exception{
		long id = Convert.strToLong(request.getString("id"), -1);
		borrowManageService.deleteApplyById(id);
		
		return SUCCESS;
	}
	
	
	/**
	 * 成功的借款初始化
	 * adminSuccessBorrowInit
	 * @return
	 * @autthor linww
	 * 2014-6-14 下午04:51:48
	 */
	public String adminSuccessBorrowInit(){
		return SUCCESS;
	}
	
	/**
	 * 正在还款的借款初始化
	 * adminPaymentInit
	 * @return
	 * @autthor linww
	 * 2014-6-12 上午09:36:34
	 */
	public String adminPaymentInit(){
		return SUCCESS;
	}
	
	
	/**
	 * 还款明细账初始化
	 * adminAllDetailsInit
	 * @return
	 * @autthor linww
	 * 2014-6-14 上午08:34:18
	 */
	public String adminAllDetailsInit(){
		return SUCCESS;
	}
	
	
	/**
	 * 借款明细账
	 * adminBorrowInvestorInit
	 * @return
	 * @autthor linww
	 * 2014-6-14 上午08:57:39
	 */
	public String adminBorrowInvestorInit(){
		return SUCCESS;
	}
	
	/**
	 * 已还完的借款
	 * adminPayoffBorrowInit
	 * @return
	 * @autthor linww
	 * 2014-6-14 上午09:06:47
	 */
	public String adminPayoffBorrowInit(){
		return SUCCESS;
	}
	
	
	/**
	 * 查询成功的借款
	 * queryAdminSuccessBorrowList
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-14 下午04:38:04
	 */
	public String queryAdminSuccessBorrowList() throws Exception {
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);
		//Long userId = user.getId();// 获得用户编号
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		
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
	 * 查询正在还款的记录
	 * queryAdminPayingBorrowList
	 * @return
	 * @throws Exception 
	 * @autthor linww
	 * 2014-6-12 上午09:37:09
	 */
	public String queryAdminPayingBorrowList() throws Exception{
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		//Long userId = user.getId();// 获得用户编号
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
	
	/**
	 * 一条借款还款明细
	 * queryAdminPayingDetails
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-14 上午08:39:45
	 */
	public String queryAdminPayingDetails() throws Exception {
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
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
		return SUCCESS;
	}
	
	/**
	 * 查询一期还款详情
	 * queryAdminPayData
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-14 上午08:39:25
	 */
	public String queryAdminPayData() throws Exception{
		long payId = request.getString("payId") == null?-1:request.getLong("payId", -1);
		Map<String,String> payMap = frontpayService.queryMyPayData(payId);
		request().setAttribute("payMap", payMap);
		return SUCCESS;
	}
	
	/**
	 * 管理员提交还款
	 * adminSubmitPay 
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-12 上午11:51:23
	 */
	public String adminSubmitPay() throws Exception{
		Thread.sleep(100);
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		String userName =  IConstants.PUBLISH_BORROW_ADMIN_NAME;
		//User user = (User)session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("invest_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap
				.get("code");
		String id = paramMap.get("id") == null ? "" : paramMap
				.get("id");
		long idLong = Convert.strToLong(id, -1L);
		String pwd ="";
		//String pwd = paramMap.get("pwd") == null ? "" : paramMap
		//		.get("pwd");
		//if (StringUtils.isBlank(pwd.trim())) {
			//obj.put("msg", "密码不能为空");
			//JSONUtils.printObject(obj);
			//return null;
		//}
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
		boolean re = userService.checkSign(userId);
		if(!re){
			obj.put("msg", "*您的账号出现异常，请速与管理员联系!");
			JSONUtils.printObject(obj);
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut().print(
			"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		Map<String, String> map =frontpayService.submitPay(idLong,userId,pwd,getBasePath(),userName,2);
		String result =Convert.strToStr( map.get("ret_desc"),"");
		userService.updateSign(userId);//更换校验码
		obj.put("msg", result);
		JSONUtils.printObject(obj);
		return null;
	}
	
	/**
	 * 还款明细账
	 * queryAdminAllDetailsList
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-14 上午08:39:06
	 */
	public String queryAdminAllDetailsList() throws Exception {
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);
		//Long userId = user.getId();// 获得用户编号
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		pageBean.setPageNum(request.getString("curPage"));
		String startTime = Convert.strToStr(request.getString("startTime"), null);
		String endTime = Convert.strToStr(request.getString("endTime"), null);
		String title = Convert.strToStr(request.getString("title"), null);
		endTime = changeEndTime(endTime);

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);

		frontpayService.queryAllDetails(pageBean, userId, startTime, endTime,
				title);
		this.setRequestToParamMap();
		return SUCCESS;
	}
	
	/**
	 * 借款明细账
	 * queryAdminBorrowInvestorList
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-14 上午08:58:37
	 */
	public String queryAdminBorrowInvestorList() throws Exception {
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);
		//Long userId = user.getId();// 获得用户编号
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		pageBean.setPageNum(request.getString("curPage"));
		String investor = request.getString("investor");

		frontpayService.queryBorrowInvestorInfo(pageBean, userId, investor);
		this.setRequestToParamMap();
		return SUCCESS;
	}
	
	
	/**
	 * 已还完的借款
	 * queryAdminPayoffBorrowList
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-14 上午09:04:06
	 */
	public String queryAdminPayoffBorrowList() throws Exception {
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);
		//Long userId = user.getId();// 获得用户编号
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
		pageBean.setPageNum(request.getString("curPage"));
		String startTime = request.getString("startTime");
		String endTime = request.getString("endTime");
		String title = request.getString("title");
		int borrowStatus = IConstants.BORROW_STATUS_5;
		endTime = changeEndTime(endTime);

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
	 * 
	 * exportSuccessBorrowAdmin
	 * @return
	 * @autthor linww
	 * 2014-6-12 下午03:40:58
	 */
   public String exportSuccessBorrowAdmin(){
		
	   long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
	   
		//Long userId = this.getUserId();// 获得用户编号
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
	 * 还款明细账，的数据导出excel文件
	 * @return
	 */
	public String exportrepaymentAdmin(){
//		User user = (User) session().getAttribute(IConstants.SESSION_USER);
//		Long userId = user.getId();// 获得用户编号
		long userId = Convert.strToLong(IConstants.PUBLISH_BORROW_ADMIN_ID, -1);  //发布借款的管理员ID
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
	 * 查询可以代发标用户
	 * querAllUser
	 * @return
	 * @throws Exception
	 * 2014-7-9 下午05:06:21
	 */
	public String querAllUser() throws Exception{
		
		String name = paramMap.get("name");
		pageBean.setPageNum(request.getString("curPage"));
		pageBean.setPageSize(10);
		userService.queryUserByNameAndPhone3(name, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
		* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		//List<Map<String, Object>> user = null;
		
	    //user = userService.queryUserByNameAndPhone3(name, pageBean);
		
		//list = user;
		list=pageBean.getPage();
//		if(list!=null){
//		
//			int i = 0;
//			Iterator<Map<String, Object>> iter = list.iterator();
//			while (iter.hasNext()) {
//				Map<String, Object> map = iter.next();
//				list.get(i).put("st", userService.queryPersonById(Convert.strToLong(map.get("id").toString(), -1L)));
//				i++;
//			}
//		}
		return SUCCESS;
	}
	
	/**
	 * 个人代发标查询基本资料
	 * persioninfo
	 * @return
	 * @throws Exception
	 * 2014-7-9 下午04:59:54
	 */
	public String  persioninfo() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		long userId = Convert.strToLong(request("id"), -1); 
		map = userService.queryPersonById(userId);
		//判断用户是否为营销账户
		Map<String,String> userYxMap = userService.queryUserYxById(userId);
		if(userYxMap!=null && userYxMap.size()>0){//说明当前账户是营销账户
			String tell = userYxMap.get("tell");
			map.put("cellPhone", tell);//替换手机号码
		}
		request().setAttribute("map", map);
		request().setAttribute("ISDEMO", IConstants.ISDEMO);
		request().setAttribute("userId",userId);
		return SUCCESS;
		
	}
	
	public String persionData() throws Exception{
		//User user = (User) session().getAttribute("user");
				long userId =  Convert.strToLong(request("id"), -1); 
				int type=Convert.strToInt(paramMap.get("type"), -1); //用户类型
				
				long applyId =Convert.strToLong(paramMap.get("applyId"), -1);
				int authStep =-1;
				//根据申请id去查询是否填写基本信息
				
				 
				
//				if(userId==-1){
//					getOut().print(
//							"<script>alert('请先填写基本信息!');parent.location.href='"
//									+ request().getContextPath()
//									+ "admin/userBaseInfoInit.do';</script>");
//					return null;
//				}
				//userId = 5L;
				
				// Map<String,String> pictruemap = null;
				List<Map<String, Object>> basepictur = null;
				List<Map<String, Object>> selectpictur = null;
				// -----------modify by houli
//				String from = request.getString("from");
//				String btype = request.getString("btype");
				// -------------
//					if (from == null || from.equals("")) {
//						// 获取用户认证进行的步骤
//						if (user.getAuthStep() == 1) {
//							// 个人信息认证步骤
//							return "querBaseData";
//						} else if (user.getAuthStep() == 2) {
//							// 工作信息认证步骤
//							return "querWorkData";
//						} else if (user.getAuthStep() == 3) {
//							// VIP申请认证步骤
//							return "quervipData";
//						}
//						// ---------add by houli
//						else if (user.getAuthStep() == 5
//								&& (btype != null && !btype.equals(""))) {
//							return "jumpOther";
//						}
//					} else {// 净值借款跟秒还借款操作步骤
//						// 获取用户认证进行的步骤
//						if (user.getAuthStep() == 1) {
//							// 个人信息认证步骤
//							return "querBaseData";
//						}
		//
//						if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
//							return "quervipData";
//						}
		//
//						// -------add by houli
//						// return jumpToBorrow(btype);
//						if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
//							return "jumpNet";
//						} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
//							return "jumpSeconds";
//						}
//						// -----------------
//					}

					//userId = user.getId();
					// 获取到图片的地址和图片的状态值
					// pictruemap = userService.queryUserPictureStatus(userId);
					// request().setAttribute("pictruemap", pictruemap);
					basepictur = userService.queryBasePictureAdmin(userId);// 五大基本
					selectpictur = userService.querySelectPictureAdmin(userId);// 可选
					request().setAttribute("basepictur", basepictur);
					request().setAttribute("selectpictur", selectpictur);
					DesSecurityUtil des = new DesSecurityUtil();
					request().setAttribute("userId",des.encrypt(userId+""));
					request().setAttribute("uid",userId);
					request().setAttribute("authStep", authStep);
					request().setAttribute("applyId", applyId);
					request().setAttribute("type", type);

					
					return SUCCESS;

	}
	
	/**
	 * 个人代发标保存基本信息
	 * updatepersoninfo
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-7-9 下午03:58:56
	 */
	public String updatepersoninfo() throws Exception{
		Long userId = Convert.strToLong(paramMap.get("id"), -1L);
		JSONObject json = new JSONObject();
		String realName = paramMap.get("realName");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String idNo = paramMap.get("idNo");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(idNo.substring(12, 15));
		} else {
			sortCode = Integer.parseInt(idNo.substring(14, 17));
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 1;// 男性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 2;// 女性身份证
		} else {
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}
		String iDresutl = "";
		iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
		if (iDresutl != "") {
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}

		String cellPhone = paramMap.get("cellPhone");// 手机号码
		if (StringUtils.isBlank(cellPhone)) {
			json.put("msg", "请正确填写手机号码");
			JSONUtils.printObject(json);
			return null;
		} else if (cellPhone.length() != 11) {
			json.put("msg", "手机号码长度不对");
			JSONUtils.printObject(json);
			return null;
		}
		
		Map<String, String> pMap = null;
		pMap = beVipService.queryPUser(userId);
		if (pMap == null) {
			pMap = new HashMap<String, String>();
		}
		String isno = Convert.strToStr(pMap.get("idNo"), "");
		// 验证手机的唯一性
		Map<String, String> phonemap = new HashMap<String, String>();
		// 验证手机的唯一性
		phonemap = beVipService.queryIsPhone(cellPhone);
		// 测试--跳过验证
		if (IConstants.ISDEMO.equals("1")) {

		} else {
			// add by houli 判断身份证的唯一性

			if (StringUtils.isBlank(isno)) {
				Map<String, String> idNoMap = beVipService.queryIDCard(idNo);
				if (idNoMap != null && !idNoMap.isEmpty()) {
					json.put("msg", "身份证已注册");
					JSONUtils.printObject(json);
					return null;
				}
			}

		}
		String cellp = Convert.strToStr(pMap.get("cellphone"), "");
		if (StringUtils.isBlank(isno)) {
			if (!cellp.equals(cellPhone) && phonemap != null) {
				json.put("msg", "手机已存在");
				JSONUtils.printObject(json);
				return null;
			}
			if (phonemap == null) {
				String phonecode = null;
				try {
					Object obje = session().getAttribute("phone");
					// 测试--跳过验证码
					if (IConstants.ISDEMO.equals("1")) {

					} else {
						if (obje != null) {
							phonecode = obje.toString();
						} else {
							json.put("msg", "请输入正确的验证码");
							JSONUtils.printObject(json);
							return null;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (phonecode != null) {
					if (!phonecode.trim().equals(cellPhone.trim())) {
						json.put("msg", "与获取验证码手机号不一致");
						JSONUtils.printObject(json);
						return null;
					}

				}
				// 验证码
				String vilidataNum = paramMap.get("vilidataNum");
				if (StringUtils.isBlank(vilidataNum)) {
				}

				String randomCode = null;
				Object objec = session().getAttribute("randomCode");
				// 测试--跳过验证码
				if (IConstants.ISDEMO.equals("1")) {

				} else {
					if (objec != null) {
						randomCode = objec.toString();
					} else {
					}
					if (randomCode != null) {
						if (!randomCode.trim().equals(vilidataNum.trim())) {

						}

					}
				}
			}
		}

		String sex = Convert.strToStr(paramMap.get("sex"), null);// 性别(男 女)
		String birthday = Convert.strToStr(paramMap.get("birthday"), null);// 出生日期
		String highestEdu = Convert.strToStr(paramMap.get("highestEdu"), null);// 最高学历
		String eduStartDay = Convert.strToStr(paramMap.get("eduStartDay"), null);// 入学年份
		String school = Convert.strToStr(paramMap.get("school"), null);// 毕业院校
		String maritalStatus = Convert.strToStr(paramMap.get("maritalStatus"),
				null);// 婚姻状况(已婚 未婚)
		String hasChild = Convert.strToStr(paramMap.get("hasChild"), null);// 有无子女(有 无)
		String hasHourse = Convert.strToStr(paramMap.get("hasHourse"), null);// 是否有房(有 无)
		String hasHousrseLoan = Convert.strToStr(
				paramMap.get("hasHousrseLoan"), null);// 有无房贷(有 无)
		String hasCar = Convert.strToStr(paramMap.get("hasCar"), null);// 是否有车(有 无)
		String hasCarLoan = Convert.strToStr(paramMap.get("hasCarLoan"), null);// 有无车贷(有 无)
		Long nativePlacePro = Convert.strToLong(paramMap.get("nativePlacePro"), -1);// 籍贯省份(默认为-1)
		Long nativePlaceCity = Convert.strToLong(paramMap.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
		Long registedPlacePro = Convert.strToLong(paramMap.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
		Long registedPlaceCity = Convert.strToLong(paramMap.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)
		String personalHead = Convert.strToStr(paramMap.get("personalHead"), null);// 个人头像 (默认系统头像)
		String address = Convert.strToStr(paramMap.get("address"), null);// 所在地
		String telephone = Convert.strToStr(paramMap.get("telephone"), null);// 居住电话
		String num = Convert.strToStr(paramMap.get("num"), null);//获取传过来的页面号码
		
		
		       //判断是否为营销账户
				Map<String,String> userYxMap = userService.queryUserYxById(userId);
				if(userYxMap!=null && userYxMap.size()>0){//说明当前账户是营销账户
					 
					cellPhone = userService.queryPersonById(userId).get("cellPhone");
				}
				
		long personId = -1L;
		//判断是否是投资人填写个人资料
		if (num.equals("1")){
			//投资人    -- - 添加个人信息
			Map<String, String> resultMap = userService.updateUserBaseData1(realName, cellPhone, sex,
					birthday, highestEdu, eduStartDay, school, maritalStatus,
					hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
					nativePlacePro, nativePlaceCity, registedPlacePro,
					registedPlaceCity, address, telephone, "", userId , idNo ,num);
			personId=Convert.strToLong(resultMap.get("ret") + "", -1);
			request().setAttribute("person", "1");
			if (personId > 0) {
				session().removeAttribute("randomCode");
//				user.setPersonalHead(personalHead);// 将个人头像放到session里面
				json.put("msg", "保存成功2");
				JSONUtils.printObject(json);
//				user.setRealName(realName);
//				session().setAttribute("user", user);
				return null;
			}
		}else{
			
			if (StringUtils.isBlank(sex)) {
				json.put("msg", "请正确填写性别");
				JSONUtils.printObject(json);
				return null;
			}
			if (StringUtils.isBlank(birthday)) {
				json.put("msg", "请正确填写出生日期");
				JSONUtils.printObject(json);
				return null;
			}
			// 最高学历
			if (StringUtils.isBlank(highestEdu)) {
				json.put("msg", "请正确填写最高学历");
				JSONUtils.printObject(json);
				return null;
			}
			// 入学年份
			if (StringUtils.isBlank(eduStartDay)) {
				json.put("msg", "请正确填写入学年份");
				JSONUtils.printObject(json);
				return null;
			}
			// 毕业院校
			if (StringUtils.isBlank(school)) {
				json.put("msg", "请正确填写入毕业院校");
				JSONUtils.printObject(json);
				return null;
			}

			// 婚姻状况(已婚 未婚)
			if (StringUtils.isBlank(maritalStatus)) {
				json.put("msg", "请正确填写入婚姻状况");
				JSONUtils.printObject(json);
				return null;
			}

			// 有无子女(有 无)
			if (StringUtils.isBlank(hasChild)) {
				json.put("msg", "请正确填写入有无子女");
				JSONUtils.printObject(json);
				return null;
			}
			// 是否有房(有 无)
			if (StringUtils.isBlank(hasHourse)) {
				json.put("msg", "请正确填写入是否有房");
				JSONUtils.printObject(json);
				return null;
			}

			// 有无房贷(有 无)
			if (StringUtils.isBlank(hasHousrseLoan)) {
				json.put("msg", "请正确填写入有无房贷");
				JSONUtils.printObject(json);
				return null;
			}
			// 是否有车 (有 无)
			if (StringUtils.isBlank(hasCar)) {
				json.put("msg", "请正确填写入是否有车");
				JSONUtils.printObject(json);
				return null;
			}
			// 有无车贷 (有 无)
			if (StringUtils.isBlank(hasCarLoan)) {
				json.put("msg", "请正确填写入有无车贷");
				JSONUtils.printObject(json);
				return null;
			}
			// 籍贯省份(默认为-1)
			if (StringUtils.isBlank(nativePlacePro.toString())) {
				json.put("msg", "请正确填写入籍贯省份");
				JSONUtils.printObject(json);
				return null;
			}
			// 籍贯城市 (默认为-1)
			if (StringUtils.isBlank(nativePlaceCity.toString())) {
				json.put("msg", "请正确填写入籍贯城市");
				JSONUtils.printObject(json);
				return null;
			}
			// 户口所在地省份(默认为-1)
			if (StringUtils.isBlank(registedPlacePro.toString())) {
				json.put("msg", "请正确填写入户口所在地省份");
				JSONUtils.printObject(json);
				return null;
			}
			// 户口所在地城市(默认为-1)
			if (StringUtils.isBlank(registedPlaceCity.toString())) {
				json.put("msg", "请正确填写入户口所在地城市");
				JSONUtils.printObject(json);
				return null;
			}
			// 所在地
			if (StringUtils.isBlank(address)) {
				json.put("msg", "请正确填写入所在地");
				JSONUtils.printObject(json);
				return null;
			}
			// 居住电话
			if (StringUtils.isBlank(telephone)) {
				json.put("msg", "请正确填写入你的家庭电话");
				JSONUtils.printObject(json);
				return null;
			}
			if (telephone.trim().length() !=12 && telephone.trim().length() !=13) {
					json.put("msg", "你的居住电话输入长度不对");
					JSONUtils.printObject(json);
					return null;
			}
			 Pattern pattern = Pattern.compile("^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$");     
			   Matcher m = pattern.matcher(telephone);     
			  if (!m.matches()) {     
				json.put("msg", "请正确填写入你的家庭电话");
				JSONUtils.printObject(json);
				return null;
			 }
			/* 用户头像 */
			if (StringUtils.isBlank(personalHead)) {
				personalHead = null;
				json.put("msg", "请正确上传你的个人头像");
				JSONUtils.printObject(json);
				return null;
			}
//			if (user == null) {
//				json.put("msg", "超时请重新登录");
//				JSONUtils.printObject(json);
//				return null;
//			}
		}
		Map<String, String> resultMap = userService.updateUserBaseData1(realName, cellPhone, sex,
				birthday, highestEdu, eduStartDay, school, maritalStatus,
				hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
				nativePlacePro, nativePlaceCity, registedPlacePro,
				registedPlaceCity, address, telephone, personalHead, userId , idNo ,num);
		personId=Convert.strToLong(resultMap.get("ret") + "", -1);
		if (personId > 0) {
			// ==
//			if (user.getAuthStep() == 1) {
//				user.setAuthStep(2);
//			}
			session().removeAttribute("randomCode");
//			user.setPersonalHead(personalHead);// 将个人头像放到session里面
			json.put("msg", "保存成功");
			JSONUtils.printObject(json);
			request().setAttribute("person", "1");
//			user.setRealName(realName);
//			session().setAttribute("user", user);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}
	
	public String personpublishBorrowInit() throws Exception{
        Long userId = Convert.strToLong(request("id"), -1L); 
		paramMap.put("userId", userId.toString());
		paramMap.put("type", "1");
		request().setAttribute("userId", userId.toString());
		request().setAttribute("type", 1);
		return SUCCESS;
	}
	
	/**
	 * 后台添加企业投标意向初始化
	 * @return
	 * @throws Exception
	 */
	public String addApplyInit() throws Exception{
		Map<String, String> map= publicModelService.getMessageByTypeId(16);
		String content = map.get("content");
		String[] citys = content.split(",");
		List list = Arrays.asList(citys);
		request().setAttribute("cityList", list);
		return SUCCESS;
	}
	
	/**
	 * 后台添加企业投标意向
	 * @return
	 * @throws Exception
	 */
	public String addApplyAction() throws Exception{
		
		JSONObject json = new JSONObject();
		
		String companyname = paramMap.get("companyname");// 企业名称
		if(StringUtils.isBlank(companyname)) {
			json.put("msg", "请填写企业名称");
			JSONUtils.printObject(json);
			return null;
		}else if ("-1".equals(companyname)) {
			json.put("msg", "请填写企业名称");
			JSONUtils.printObject(json);
			return null;
		}
		
		String registnumber = paramMap.get("registnumber");// 注册号
		if (StringUtils.isBlank(registnumber)) {
			json.put("msg", "请正确填写注册号");
			JSONUtils.printObject(json);
			return null;
		}

		String tname = paramMap.get("tname");// 联系人
		if (StringUtils.isBlank(tname)) {
			json.put("msg", "请填写联系人");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > tname.length() || 20 < tname.length()) {
			json.put("msg", "联系人的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String telphone = paramMap.get("telphone");// 居住电话
			if (StringUtils.isBlank(telphone)) {
				json.put("msg", "请正确填写手机号码");
				JSONUtils.printObject(json);
				return null;
			} else if (telphone.length() < 9 || telphone.length() > 15) {
				json.put("msg", "手机号码长度不对");
				JSONUtils.printObject(json);
				return null;
			}
		
		String cityaddress = paramMap.get("cityaddress");// 所在地
		if (StringUtils.isBlank(cityaddress)) {
			
			json.put("msg", "请填写联系地址");
			JSONUtils.printObject(json);
			return null;
		}
		 String borrowAmount =paramMap.get("borrowAmount");
			if (StringUtils.isBlank(borrowAmount)) {

				json.put("msg", "请填写借款金额");
				JSONUtils.printObject(json);
				return null;
			}else if (!borrowAmount
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")){
				json.put("msg", "借款金额格式不正确");
				JSONUtils.printObject(json);
				return null;
			} 
		
			String deadline = paramMap.get("deadline");// 借款期限
			if (StringUtils.isBlank(deadline)) {
				json.put("msg", "请填写借款期限");
				JSONUtils.printObject(json);
				return null;
			}else if (!deadline
					.matches("^\\+?[1-9][0-9]*$")){
				json.put("msg", "借款期限格式不正确");
				JSONUtils.printObject(json);
				return null;
			} 
		
			String borrowPurpose =paramMap.get("borrowPurpose");// 借款用途
			if (StringUtils.isBlank(borrowPurpose)) {

				json.put("msg", "请填写借款用途");
				JSONUtils.printObject(json);
				return null;
			}
		
		long result = -1L;
		result = borrowfaService.addApplyAdmin(companyname,registnumber,tname,telphone,cityaddress,
				borrowAmount,deadline,borrowPurpose);
		if(result>0){
			session().removeAttribute("apply_checkCode");
			json.put("msg", "1");
			JSONUtils.printObject(json);
			return null;
		}else{
			json.put("msg", "申请失败");
			JSONUtils.printObject(json);
			return null;
		}
		
	}
	
	public String addCirculationBorrow() throws Exception{
		Admin admin = (Admin) session().getAttribute("admin");
		if(admin==null){
			return "nologin";
		}
		long userId = Convert.strToLong(session().getAttribute("uId").toString(), -1);
		String remoteIP = ServletUtils.getRemortIp();
		String title = paramMap.get("title");
		String imgPath = paramMap.get("imgPath");
		String purpose = paramMap.get("purpose");
		int purposeInt = Convert.strToInt(purpose, -1);
		String deadLine = paramMap.get("deadLine");
		int deadLineInt = Convert.strToInt(deadLine, 0);
		int paymentMode = 4;
		int borrowWay = Convert.strToInt(
				IConstants.BORROW_TYPE_INSTITUTION_FLOW, 6);
		String amount = paramMap.get("amount");
		double amountDouble = Convert.strToDouble(amount, 0);
		String annualRate = paramMap.get("annualRate");
		double annualRateDouble = Convert.strToDouble(annualRate, 0);
		String smallestFlowUnit = paramMap.get("smallestFlowUnit");
		double smallestFlowUnitDouble = Convert
				.strToDouble(smallestFlowUnit, 0);
		String businessDetail = paramMap.get("businessDetail");
		String assets = paramMap.get("assets");
		String moneyPurposes = paramMap.get("moneyPurposes");
		int circulationNumber = (int) (amountDouble / smallestFlowUnitDouble);
		double maxMoney = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);
		double bigestFlowUnit = Convert.strToDouble(paramMap.get("bigestFlowUnit"), 0);
		Map<String, String> tempBorrwBidMessage = shoveBorrowTypeService
				.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		if (smallestFlowUnitDouble > amountDouble) {
			this
					.addFieldError("paramMap['smallestFlowUnit']",
							"最小流转单位不能超过借款总额");
			return "input";
		}
		if (maxMoney > amountDouble) {
			this
					.addFieldError("paramMap['maxMoneyValue']",
							"累计投标金额不能超过借款总额");
			return "input";
		}
		if (maxMoney % smallestFlowUnitDouble != 0) {
			this
					.addFieldError("paramMap['maxMoneyValue']",
							"累计投标金额必须整除最小流转单位");
			return "input";
		}
		
		if(maxMoney-smallestFlowUnitDouble<0){
			this.addFieldError("paramMap['maxMoneyValue']",
					"累计投标金额必须大于或等于最小流转单位");
			return "input";
		}
		
		
		if (bigestFlowUnit % smallestFlowUnitDouble != 0) {
			this.addFieldError("paramMap['bigestFlowUnit']",
							"单次最大投标金额必须整除最小流转单位");
			return "input";
		}
		
		if(maxMoney-bigestFlowUnit<0){ 
			this.addFieldError("paramMap['bigestFlowUnit']",
					"单次最大投标金额必须小于或等于累计投标金额");
	        return "input";
		}
		
		if(bigestFlowUnit-smallestFlowUnitDouble<0){ 
			this.addFieldError("paramMap['bigestFlowUnit']",
					"单次最大投标金额必须大于或等于最小流转单位");
	        return "input";
		}
		
		
		if (amountDouble < Convert.strToDouble(tempBorrwBidMessage
				.get("amount_first"), 0)) {
			this.addFieldError("paramMap['amount']", "借款总额必须大于等于"
					+ Convert.strToDouble(tempBorrwBidMessage
							.get("amount_first"), 0));
			return "input";
		}
		if (amountDouble > Convert.strToDouble(tempBorrwBidMessage
				.get("amount_end"), 0)) {
			this.addFieldError("paramMap['amount']", "借款总额小于等于"
					+ Convert.strToDouble(
							tempBorrwBidMessage.get("amount_end"), 0));
			return "input";
		}
		if (paymentMode == -1) {
			this.addFieldError("paramMap['paymentMode']", "请选择还款方式");
			return "input";
		}
		if (smallestFlowUnitDouble < 1) {
			this.addFieldError("paramMap['smallestFlowUnit']", "最小流转单位必须大于等于1");
			return "input";
		}
		if (smallestFlowUnitDouble > amountDouble) {
			this
					.addFieldError("paramMap['smallestFlowUnit']",
							"最小流转单位不能超过借款总额");
			return "input";
		}
		if (amountDouble % smallestFlowUnitDouble != 0) {
			this
					.addFieldError("paramMap['smallestFlowUnit']",
							"借款总额必须整除最小流转单位");
			return "input";
		}
		String excitationType = paramMap.get("excitationType");
		String sum = paramMap.get("sum");
		double sumInt = Convert.strToDouble(sum, -1);
		String sumRate = paramMap.get("sumRate");
		double sumRateDouble = Convert.strToDouble(sumRate, -1);

		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "3".equals(excitationType)) {
				sumInt = sumRateDouble;
			}
		}

		if (excitationType.equals("2")) {
			if (!isValidateForTturnBid(amountDouble, excitationType, sumInt,
					annualRateDouble)) {
				return "input";
			}
		} else if (excitationType.equals("3")) {
			if (!isValidateForTturnBid(amountDouble, excitationType,
					sumRateDouble, annualRateDouble)) {
				return "input";
			}
		} else {
			if (!isValidateForTturnBid(amountDouble, excitationType,
					sumRateDouble, annualRateDouble)) {
				return "input";
			}
		}

		int excitationTypeInt = Convert.strToInt(excitationType, 1);
		// -------------
		// 查询标种详情
		Map<String, String> borrowTypeMap = this
				.getBorrowTypeMap(IConstants.BORROW_TYPE_INSTITUTION_FLOW);
		Map<String, String> counterList = shoveBorrowStyleService
				.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 3);
		// /得到反担保方式
		String counterAgent = counterList.get("selectName");
		// 得到担保结构
		Map<String, String> instiList = shoveBorrowStyleService
				.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 2);
		String agent = instiList.get("selectName");
		// 冻结保证金 ----------------
		double frozenMargin = 0;
		Long result = -1L;
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = platformCostService
				.queryAllPlatformCost();

		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		for (Map<String, Object> platformMap : mapList) {
			double costFee = Convert.strToDouble(platformMap.get("costFee")
					+ "", 0);
			int costMode = Convert
					.strToInt(platformMap.get("costMode") + "", 0);
			String remark = Convert
					.strToStr(platformMap.get("remark") + "", "");
			if (costMode == 1) {
				mapfee.put(platformMap.get("alias") + "", costFee * 0.01);
			} else {
				mapfee.put(platformMap.get("alias") + "", costFee);
			}
			mapFeestate.put(platformMap.get("alias") + "", remark); // 费用说明
			platformMap = null;
		}

		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		Map<String,String> userMap = userService.queryUserById(userId);
		String username = "";
		if(userMap!=null && userMap.size()>0){
			username = userMap.get("username");
		}
		// --------------
		//whb 添加是否专享标
		int isExclus = Convert.strToInt(paramMap.get("isExclus"), 0);
		int isLimitMaxMoney = 1;//是否限制最大投标金额  0-不限制，2-限制
		double maxMoneyValue = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);//最大投标金额
		double add_interest =  Convert.strToDouble(request().getParameter("add_interest"), 0);//加息利率		
		result = borrowService.addCirculationBorrow(bigestFlowUnit,title, imgPath, borrowWay,
				purposeInt, deadLineInt, paymentMode, amountDouble,
				annualRateDouble, remoteIP, circulationNumber,
				smallestFlowUnitDouble, userId, businessDetail, assets,
				moneyPurposes, IConstants.DAY_THE_1, getBasePath(), 
				username, excitationTypeInt, sumInt, json,
				jsonState, borrowTypeMap.get("identifier"), agent,
				counterAgent, frozenMargin,"",isExclus,
				isLimitMaxMoney,maxMoneyValue,add_interest,-1,-1);
//		userService.updateSign(userId);// 更换校验码
		if (result < 0)
			return "fail";
		Map<String, String> map = borrowManageService.queryBorrowFistAuditDetailById2(userId);
		result = -1L;
		result = borrowManageService.updateBorrowFistAuditStatus(Convert.strToLong(map.get("id"), -1L),
				Convert.strToLong(map.get("userId"), -1L), 2, "= =!", "= =", admin.getId(),
				getBasePath(), "");
		
		if (result < 0)
			return "fail";
		
		getOut().print(
				"<script>alert('借款发布成功！');window.location.href='"
						+ request().getContextPath()
						+ "/shoveeims/borrowBackstages.do';</script>");
		//后台发布流转标
		return null;
	}
	
	// 校验提交借款参数
	@SuppressWarnings("unchecked")
	public boolean isValidateForTturnBid(double amountDouble,
			String excitationType, double sumRateDouble, double annualRateDouble)
			throws Exception {
		String t = (String) session().getAttribute("t");
		// 获取借款的范围
		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		tempBorrwBidMessage = shoveBorrowTypeService
				.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		// 取得按借款金额的比例进行奖励
		double accountfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_first")
				+ "", 0);
		double accountend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "2".equals(excitationType)) {
				if (sumRateDouble < accountfirst || sumRateDouble > accountend) {
					this.addFieldError("paramMap['sum']", "固定总额奖励填写不正确");
					return false;
				}
			}
		}
		// 如果选择金额的话，则按此奖励的金额范围
		double scalefirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_first")
				+ "", 0);
		double scaleend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "3".equals(excitationType)) {
				if (sumRateDouble < scalefirst || sumRateDouble > scaleend) {
					this.addFieldError("paramMap['sumRate']", "奖励比例填写不正确");
					return false;
				}
			}
		}
		// 借款额度
		double borrowMoneyfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_first")
				+ "", 0);
		double borrowMoneyend = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_end")
				+ "", 0);
		if (borrowMoneyfirst > amountDouble || borrowMoneyend < amountDouble) {
			this.addFieldError("paramMap['amount']", "输入的借款总额不正确");
			return false;
		}
		// 借款额度倍数
		double accountmultiple = Convert.strToDouble(tempBorrwBidMessage
				.get("account_multiple")
				+ "", -1);
		if (accountmultiple != 0) {
			if (amountDouble % accountmultiple != 0) {
				this.addFieldError("paramMap['amount']", "输入的借款总额的倍数不正确");
				return false;
			}
		}
		// 年利率
		double aprfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("apr_first")
				+ "", 0);
		double aprend = Convert.strToDouble(tempBorrwBidMessage.get("apr_end")
				+ "", 0);
		if (aprfirst > annualRateDouble || aprend < annualRateDouble) {
			this.addFieldError("paramMap['annualRate']", "输入的年利率不正确");
			return false;
		}
		return true;
	}
	
	
   public String queryBorrowTyjListInit() throws Exception{
			return "success";
	}
   
   public String queryBorrowTyjList() throws Exception{
		
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		 
		JSONObject jo = new JSONObject();
		try {
			userService.queryBorrowTyjList(pageBean);
			List<Map<String, Object>> list  = pageBean.getPage();
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list); 
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "查询失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
		
	}
   
   public String addBorrowNew() throws Exception{
		
		String name = request().getParameter("name"); 
	    double rate = Convert.strToDouble(request().getParameter("rate"), 0);
	    int day = Convert.strToInt(request().getParameter("day"), 0);
	    int parm = Convert.strToInt(request().getParameter("parm"), 0);
	    double  amount_sum = 8888*parm;
		JSONObject jo = new JSONObject();
		try { 
			Map<String,String> borrowMap = financeService.queryBorrowNew();
			if(borrowMap!=null && borrowMap.size()>0){
				 
				double amount_ables = Convert.strToDouble(borrowMap.get("amount_able"), 0);
				double amount_sums = Convert.strToDouble(borrowMap.get("amount_sum"), 0);
				if(amount_ables-amount_sums<0){
					jo.put("msg", "存在未满标的"); 
					printJson(jo.toString());
					return null;
				}
			}
			long m = userService.addBorrowNew(name, rate, day, amount_sum);
			if(m>0){
				jo.put("msg", "发标成功"); 
				printJson(jo.toString());
				return null;
			}else{
				jo.put("msg", "发标失败"); 
				printJson(jo.toString());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
   
   /***
    * 初始化员工体验金配置
    * @return
    * @throws Exception
    */
   public String queryEmployeeConfigInit() throws Exception{
		return "success";
   }
   
   /***
    * 查询员工体验金配置集合
    * @return
    * @throws Exception
    */
   public String queryEmployeeConfigList() throws Exception{
		
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		 
		JSONObject jo = new JSONObject();
		try {
			userService.queryEmployeeConfigList(pageBean);
			List<Map<String, Object>> list  = pageBean.getPage();
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list); 
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "查询失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
		
	}
   
   /***
    * 查询员工体验金配置集合
    * @return
    * @throws Exception
    */
   public String updateEmployeeConfig() throws Exception{
	    long id = Convert.strToLong(request().getParameter("id"), 0);
	    int amount = Convert.strToInt(request().getParameter("amount"), 0);
	    long userId = Convert.strToLong(request().getParameter("userId"), 0);
		JSONObject jo = new JSONObject();
		try { 
			long m = userService.updateEmployeeConfig(id, amount,userId);
			if(m>0){
				jo.put("msg", "1"); 
				printJson(jo.toString());
				return null;
			}else{
				jo.put("msg", "0"); 
				printJson(jo.toString());
				return null;
			}
		} catch (Exception e) {
			jo.put("msg","-1"); 
			e.printStackTrace();
		}
		
		return null;
		
	}
   
   
   /**
    * 初始化员工体验标
    * @return
    * @throws Exception
    */
   public String queryEmployeeBorrowInit() throws Exception{
			return "success";
	}
   
   /***
    * 查询员工体验标列表
    * @return
    * @throws Exception
    */
   public String queryEmployeeBorrowList() throws Exception{
		
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		 
		JSONObject jo = new JSONObject();
		try {
			userService.queryEmployeeBorrowList(pageBean);
			List<Map<String, Object>> list  = pageBean.getPage();
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list); 
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "查询失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
		
	}
   
   /**
    * 发员工体验标
    * @return
    * @throws Exception
    */
   public String addEmployeeBorrow() throws Exception{
		
 		String name = request().getParameter("name"); 
 	    double rate = Convert.strToDouble(request().getParameter("rate"), 0);
 	    int day = Convert.strToInt(request().getParameter("day"), 0);
 	    int parm = Convert.strToInt(request().getParameter("parm"), 0);
 	    double  amount_sum = parm;
 		JSONObject jo = new JSONObject();
 		try { 
 			Map<String,String> borrowMap = financeService.queryEmployeeBorrow();
 			if(borrowMap!=null && borrowMap.size()>0){
 				 
 				double amount_ables = Convert.strToDouble(borrowMap.get("amount_able"), 0);
 				double amount_sums = Convert.strToDouble(borrowMap.get("amount_sum"), 0);
 				if(amount_ables-amount_sums<0){
 					jo.put("msg", "未满标，禁止再次发标"); 
 					printJson(jo.toString());
 					return null;
 				}
 			}
 			long m = userService.addEmployeeBorrow(name, rate, day, amount_sum);
 			if(m>0){
 				jo.put("msg", "发标成功"); 
 				printJson(jo.toString());
 				return null;
 			}else{
 				jo.put("msg", "发标失败"); 
 				printJson(jo.toString());
 				return null;
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		return null;
 		
 	}
   
   /***
    * 导出员工体验标列表
    * @return
    * @throws Exception
    */
   public String exportEmployeeConfigListExcel() throws Exception{
		try {
			pageBean.setPageNum("1"); 
			pageBean.setPageSize(1000);
			userService.queryEmployeeConfigList(pageBean);
			List<Map<String, Object>> list  = pageBean.getPage();
			if (list == null) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			
			HSSFWorkbook wb = ExcelUtils.exportExcel("导出员工体验金列表", list, 
					new String[] { "用户名","手机号码", "真实姓名", "体验金份数", "生成时间",}, 
			        new String[] { "username","mobilePhone", "realName", "amount", "createTime"
					});
	        this.export(wb, new Date().getTime() + ".xls");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
   
   
   




   
}