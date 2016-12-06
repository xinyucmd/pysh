package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.BorrowService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.SettingDebtService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 债权转让
 */
public class DebtManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(DebtManageAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;
	private FinanceService financeService;
	private UserService userService;
	
	private SettingDebtService settingDebtService;
	
	private BorrowService borrowService;
	private BorrowManageService borrowManageService;
	private PlatformCostService platformCostService;
	private ShoveBorrowStyleService shoveBorrowStyleService;
	
	public ShoveBorrowStyleService getShoveBorrowStyleService() {
		return shoveBorrowStyleService;
	}

	public void setShoveBorrowStyleService(ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public PlatformCostService getPlatformCostService() {
		return platformCostService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}


	public SettingDebtService getSettingDebtService() {
		return settingDebtService;
	}

	public void setSettingDebtService(SettingDebtService settingDebtService) {
		this.settingDebtService = settingDebtService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 申请中的债权转让初始化
	 * @return
	 */
	public String queryApplyDebtInit(){
		return SUCCESS;
	}
	
	public String auditRaiseIndex(){
		return SUCCESS;
	}
	
	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public String settingDebtInit(){
		Map<String,String> result = null;
		//whb
		List<Map<String, Object>> typeList = null;//标种类型
		List<Map<String, Object>> groupList = null;//用户组
		try {
			result = settingDebtService.querySettingDebtById(1);
			typeList = settingDebtService.queryBorrowType("1");
			groupList = settingDebtService.queryAllGroup();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request().setAttribute("settingDebt", result);
		request().setAttribute("list", typeList);//标种类型
		request().setAttribute("groupList", groupList);//用户组
		return SUCCESS;
	}
	
	public String updateSettingDebt(){
		JSONObject jo = new JSONObject();
		String id = request.getString(("setId"));
		int holdShortDays = 0;
		if(StringUtils.isNotBlank("holdShortDays")){
			holdShortDays = Convert.strToInt(paramMap.get("holdShortDays"), -1);
		}
		
		int rangeExpiryDays = 0;
		if(StringUtils.isNotBlank(paramMap.get("rangeExpiryDays"))){		
			rangeExpiryDays = Convert.strToInt(paramMap.get("rangeExpiryDays"), -1);
		}
		//whb获取每月债转金额    
		String transmoneyMonth = paramMap.get("transmoneyMonth");
		
		String borrowTypes = paramMap.get("borrowTypes");
		
		String borrowTypesLable = paramMap.get("borrowTypesLable");
		
		double transRatio = Convert.strToDouble(paramMap.get("transRatio"),-1f);
		
		try {
			if(StringUtils.isNotBlank(id) && id.length()>0){
				settingDebtService.updateSettingDebt(Convert.strToLong(id, -1), holdShortDays, rangeExpiryDays, transmoneyMonth, borrowTypes, borrowTypesLable, transRatio);
			}else{
				settingDebtService.addSettingDebt(holdShortDays, rangeExpiryDays, transmoneyMonth, borrowTypes, borrowTypesLable, transRatio);
			}
			jo.put("status", IConstants.UPDATE_SUCCESS_STATUS);
			jo.put("msg", IConstants.UPDATE_SUCCESS_STATUS_LABLE);
			jo.put("borrowTypes", borrowTypes);
		} catch (Exception e) {
			log.error(e);
			jo.put("status", IConstants.UPDATE_FAILE_STATUS);
			jo.put("msg", IConstants.UPDATE_FAILE_STATUS_LABLE);
			e.printStackTrace();
		}finally{
			try {
				JSONUtils.printObject(jo);
			} catch (IOException e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 申请中的债权转让
	 * @throws DataException 
	 */
	public String queryApplyDebtInfo() throws Exception{
		String debtStatus = "1";
		String borrowerName = paramMap.get("borrowerName");
		String alienatorName = paramMap.get("alienatorName");
		try {
			assignmentDebtService.queryApplyDebt(borrowerName,alienatorName,debtStatus,pageBean);
			Map<String, String> repaymentMap = assignmentDebtService.queryApplyDebtDetail();
	         request().setAttribute("repaymentMap",repaymentMap);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> payList = pageBean.getPage();
			if (payList!=null){
				for (Map<String, Object> map : payList) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
	
	/**
	 * 债权转让审核
	 * @return
	 * @throws Exception 
	 */
	public String auditDebt() throws Exception {
		String auditStatus = paramMap.get("auditStatus");
		long id = Convert.strToLong(paramMap.get("id"), -1);
		Map<String,String> map = new HashMap<String, String>();
		map.put("publishTime", DateUtil.dateToString(new Date()));
		Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if("1".equals(auditStatus)){ 
			map.put("debtStatus", "2");
		}else{
			map.put("debtStatus", "6");
		}
		try {
			assignmentDebtService.updateAssignmentDebt(id,1, map);
			if("1".equals(auditStatus)){
				operationLogService.addOperationLog("t_assignment_debt", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "债权转让审核成功", 2);
			}else{
				operationLogService.addOperationLog("t_assignment_debt", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "债权转让审核失败", 2);
			}
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
		
	}
	
	/**
	 * 跳转到审核页面
	 * @return
	 */
	public String queryApplyDebtAuditDetail() throws Exception{
		long id = request.getLong("id", -1);
		try {
			paramMap.putAll(assignmentDebtService.getAssignmentDebt(id));
			Map<String ,String> borrow = new HashMap<String, String>();
			long borrowId = Convert.strToLong(paramMap.get("borrowId"), -1);
			long userId = Convert.strToLong(paramMap.get("alienatorId"), -1);
			long investId = Convert.strToLong(paramMap.get("investId"), -1);
			borrow = financeService.queryBorrowDetailById(borrowId);
			if(borrow != null && borrow.size() > 0){
				paramMap.put("borrowTitle", borrow.get("borrowTitle"));
			}
			List<Map<String,Object>> list = assignmentDebtService.queryDebtBacking(borrowId,userId,investId);
			request().setAttribute("list", list);
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
	 * 查询正在转让中的债权初始化
	 * @return
	 */
	public String queryAuctingAssignmentDebtInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询正在转让中的债权
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryAuctingAssignmentDebtInfo() throws Exception{
		String debtStatus = "1";
		String borrowerName = paramMap.get("borrowerName");
		String alienatorName = paramMap.get("alienatorName");
		try {
			assignmentDebtService.queryAssignmentDebt("","","",borrowerName,alienatorName,debtStatus,pageBean);
			assignmentDebtService.changeDateToStr(pageBean);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	/**
	 * 债权投资审核
	 * @return
	 */
	public String queryInvestDebtApplyInit(){
		return SUCCESS;
	}
	/**
	 * 查询正在转让中的债权初始化
	 * @return
	 */
	public String querySuccessAssignmentDebtInit(){
		return SUCCESS;
	}
	
	public String queryInvestDebtApplyInfo(){
		try {
			assignmentDebtService.queryInvestDebtApply(pageBean);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> debtApply = pageBean.getPage();
			if (debtApply!=null){
				for (Map<String, Object> map : debtApply) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}
	
	public String auditInvestDebtApply(){
		long id = request.getLong("id", -1);
		Map<String, String> map;
		try {
			map = assignmentDebtService.queryInvestDebtApplyById(id);
		
			long debtId = Convert.strToLong(map.get("debtId"), -1);
			long userId = Convert.strToLong(map.get("userId"), -1);
			String pwd = map.get("pwd");
			double auctionPrice = Convert.strToDouble(map.get("auctionPrice"), -1);
			String basePath = map.get("basePath");
			String debtValue = map.get("debtValue");
			
			long result = assignmentDebtService.addAuctingDebt(debtId, userId, pwd, auctionPrice, basePath, debtValue,0);
			if(result >0){
				// 修改状态
				result = assignmentDebtService.updateInvestDebtApplyStatus(id,1);
				if(result>0){
					request().setAttribute("msg", "操作成功！");
				}else{
					request().setAttribute("msg", "操作失败[更新状态失败，请联系技术人员]！");
				}
				return SUCCESS;
			}else{
				log.info("错误状态【"+result+"】");
				request().setAttribute("msg", "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 撤销投资成功
	 * @return
	 */
	public String cancelInvestDebtApply(){
		long id = request.getLong("id", -1);
		// 修改状态
		long result = assignmentDebtService.updateInvestDebtApplyStatus(id,2);
		if(result>0){
			request().setAttribute("msg", "操作成功！");
		}else{
			request().setAttribute("msg", "操作失败！");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 查询转让成功的债权
	 * @return
	 * @throws DataException 
	 */
	public String querySuccessAssignmentDebtInfo()  throws Exception{
		String debtStatus = "2";
		String borrowerName = paramMap.get("borrowerName");
		String alienatorName = paramMap.get("alienatorName");
		String auctionName = paramMap.get("auctionName");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		try {
			assignmentDebtService.queryAssignmentDebt(auctionName,timeStart,timeEnd,borrowerName,alienatorName,debtStatus,pageBean);
			Map<String, String> repaymentMap = assignmentDebtService.queryApplySuccessDebtDetail();
	         request().setAttribute("repaymentMap",repaymentMap);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> payList = pageBean.getPage();
			if (payList!=null){
				for (Map<String, Object> map : payList) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}
	
	/**
	 * 查询失败的债权初始化
	 * @return
	 */
	public String queryFailDebtInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询失败的债权
	 * @return
	 * @throws DataException 
	 */
	public String queryFailDebtInfo()  throws Exception{
		String debtStatus = "3,4";
		String borrowerName = paramMap.get("borrowerName");
		String alienatorName = paramMap.get("alienatorName");
		try {
			assignmentDebtService.queryAssignmentDebt("","","",borrowerName,alienatorName,debtStatus,pageBean);
			Map<String, String> repaymentMap = assignmentDebtService.queryApplyFailDebtDetail();
	         request().setAttribute("repaymentMap",repaymentMap);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> payList = pageBean.getPage();
			if (payList!=null){
				for (Map<String, Object> map : payList) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	/**
	 * 结束竞拍
	 * @return
	 * @throws Exception 
	 */
	public String debtEndSuccess() throws Exception {
		long id = request.getLong("id", -1);
		Admin   admin =  (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			assignmentDebtService.auctingDebtSuccess(id,admin.getId(),2,"");  //区分前后台用户
			
			Map<String, String> m = assignmentDebtService.getAssignmentDebt(id);
			if(m!=null){
				long alienatorId = Convert.strToLong(m.get("alienatorId"), -1);//转让人
				userService.updateSign(alienatorId);//更换校验码
			}
			List<Map<String, Object>> auctionList = assignmentDebtService.getAllauctionerId(id);
			for (Map<String, Object> map : auctionList) {
				long userId = Convert.strToLong(map.get("userId") + "",-1);
				System.out.println("投资者ID:"+userId);
				userService.updateSign(userId);//更换校验码
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
       
	}
	
	/**
	 * 撤回债权转让
	 * @return
	 */
	public String cancelManageDebt() throws Exception{
		
		long id = request.getLong("id", -1);
		Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			assignmentDebtService.cancelAssignmentDebt(id, 4,admin.getId(),2);  //区分前后台用户whb还款

			Map<String, String> m = assignmentDebtService.getAssignmentDebt(id);
			if(m!=null){
				long alienatorId = Convert.strToLong(m.get("alienatorId"), -1);//转让人
				userService.updateSign(alienatorId);//更换校验码
			}
			List<Map<String, Object>> auctionList = assignmentDebtService.getAllauctionerId(id);
			for (Map<String, Object> map : auctionList) {
				long userId = Convert.strToLong(map.get("userId") + "",-1);
				System.out.println("投资者ID:"+userId);
				userService.updateSign(userId);//更换校验码
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
        
	}
	
	/**
	 * 批量添加债权
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addAuctingDebt(){
		try {
			Thread.sleep(10);
			JSONObject jo = new JSONObject();
			long result =  0;
			
			String jsonParam = request.getString("debtArr");
			String alieneeName = request.getString("alieneeName"); // 受让人用户名
			String alieneePwd = request.getString("alieneePwd");
			String eqsDeadline = request.getString("eqsDeadline");
//			String alieneeId = ""; // 受让人ID
//			Map<String,String> userMap = userService.queryUserName(alieneeName);
//			if(userMap != null){
//				alieneeId = userMap.get("id");
//			}
			
			
			String basePath = this.getBasePath();
			JSONArray debtArr = JSONArray.fromObject(jsonParam);
			if(debtArr != null && debtArr.size()>0){
				beak_falg:
				for(int i = 0;i<debtArr.size();i++){
					Object obj = JSONObject.toBean((JSONObject)debtArr.get(i),HashMap.class);
					Map<String,String> itemMap = (Map<String,String>)obj;
					Map<String,String> debtMap = new HashMap<String, String>();
					debtMap.put("alieneeName", alieneeName);
					debtMap.put("alieneePwd", alieneePwd);
					debtMap.put("debtSum", itemMap.get("debtPrice"));
					debtMap.put("publishTime", DateUtil.dateToString(new Date()));
					debtMap.put("applyTime", DateUtil.dateToString(new Date()));
					debtMap.put("debtStatus", "1");
					debtMap.put("transRatio", "1");
					debtMap.put("debtLimit", "1");
					debtMap.put("details", "批量债转");
					debtMap.put("borrowId", itemMap.get("borrowId"));
					debtMap.put("investId", itemMap.get("investId"));
					debtMap.put("alienatorId", itemMap.get("investor"));
					debtMap.put("debtValue", itemMap.get("debtValue"));
					
					debtMap.put("annualRateDebtBDDouble", itemMap.get("annualRateDebtBDDouble"));
					debtMap.put("endTime", itemMap.get("lastRepayment"));
					
					// 活力宝债转
					if(StringUtils.isNotBlank("eqsDeadline") && eqsDeadline.equals("0")){						
						debtMap.put("type", "1");
					}else{
						debtMap.put("investPwd", IConstants.DEBT_INVEST_PWD);
					}
					
					result = assignmentDebtService.autoAssignmentAndInvestDebt(debtMap,basePath);
					
					// 添加borrow start
					
					// 标的总额
					double amountDouble = Convert.strToDouble(itemMap.get("debtPrice"), -1);
					
					amountDouble = Math.floor(amountDouble/100)*100;
					
					// 处理标题
					Map<String,String> maxHLMap = borrowService.queryMaxHLId();
					
					String title = maxHLMap.get("borrowTitle");
					String title2 = "活利宝-"+DateUtil.dateToStringYYMM2(new Date());
					
					String title2_1 = title.substring(8, 12);
				
					if(title2_1.toCharArray()[0]=='0'){
						title2_1 = "1000";
					}else{
						title2_1 = String.valueOf(Integer.parseInt(title2_1)+1);
					}
					
					System.out.println(title2_1);
					title2 += title2_1;
					title2 += "期";
					
					title = title2;
					// 处理标题
					
					String imgPath = "/";
					
					int borrowWay = 6;
					
					int purposeInt = 1;
					
					int deadLineInt = 1;
					
					// 还款方式 一次性还本付息
					int paymentMode = 4;
					
					double annualRateDouble = 6;
					
					
					String remoteIP = "0.0.0.0";
					
					long userId = Convert.strToLong(itemMap.get("investor"), -1);
					
					String username = "";
					Map<String,String> userMap = userService.queryUserById(userId);
					if(userMap!=null && userMap.size()>0){
						username = userMap.get("username");
					}
					
					String businessDetail = "资金流转";
					
					String assets = "资产良好";
					
					String moneyPurposes = "采购货物";
					
					// 奖励类型(1 不设置奖励 2 按固定比例金额分摊 3 按投标金额比例)
					int excitationTypeInt = 1;
					
					
					// 最小流转单位
					int smallestFlowUnitDouble = 100;
					
					// 总份数
					int circulationNumber = Convert.strToInt(String.valueOf(amountDouble), -1)/smallestFlowUnitDouble;
					// 奖励金额
					double sumInt = 0;
					
					// 冻结保证金
					double frozenMargin = 0;
					
					int isExclus = 0;
					
					int isLimitMaxMoney = 1;//是否限制最大投标金额  0-不限制，1-限制
					
					double maxMoneyValue = smallestFlowUnitDouble*10;
					
					double bigestFlowUnit = smallestFlowUnitDouble*10;
				
					long old_borrow_id = Convert.strToLong(itemMap.get("borrowId"), -1)  ;
					
					long old_invest_id = Convert.strToLong(itemMap.get("investId"), -1)  ;
					
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
					
					Map<String, String> instiList = shoveBorrowStyleService
							.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 2);
					String agent = instiList.get("selectName");
					Map<String, String> counterList = shoveBorrowStyleService
							.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 3);
					String counterAgent = counterList.get("selectName");
					
					result = borrowService.addCirculationBorrow(bigestFlowUnit,title, imgPath, borrowWay,
							purposeInt, deadLineInt, paymentMode, amountDouble,
							annualRateDouble, remoteIP, circulationNumber,
							smallestFlowUnitDouble, userId, businessDetail, assets,
							moneyPurposes, IConstants.DAY_THE_1, getBasePath(), 
							username, excitationTypeInt, sumInt, json,
							jsonState, "flow_6_15", agent,
							counterAgent, frozenMargin,"",isExclus,
							isLimitMaxMoney,maxMoneyValue,0,old_borrow_id,old_invest_id);
				
						Map<String, String> map = borrowManageService.queryBorrowFistAuditDetailById2(userId);
						result = -1L;
						result = borrowManageService.updateBorrowFistAuditStatus(Convert.strToLong(map.get("id"), -1L),
								Convert.strToLong(map.get("userId"), -1L), 2, "= =!", "= =", -1,
								getBasePath(), "");
					//  添加borrow end 
					
					if(result < 0){
						jo.put("status", "-1");
						jo.put("msg", "债转失败！");
						break beak_falg;
					}
				}
			}
			
			if(result > 0){
				jo.put("status", "1");
				jo.put("msg", "债转成功！");
			}
			
			JSONUtils.printObject(jo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/***
	 * 通过
	 * @return
	 * @throws Exception
	 */
    public String goManageDebt() throws Exception{
		long id = request.getLong("id", -1);
		try {
			assignmentDebtService.goManageDebt(id);   
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 债权转让详情
	 * @return
	 */
	public String queryManageDebtDetail(){
		long id = request.getLong("id", -1);
		try {
			paramMap.putAll(assignmentDebtService.getAssignmentDebt(id));
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String auditRaiseIndexInfo() {
		// 转让人
		String userNames = paramMap.get("alienatorName");
		
		// 转让利率
		String debtAnnualRate = paramMap.get("debtAnnualRate");
		
		// 转让期限
		String debtDeadline = paramMap.get("debtDeadline");
		
		// 固定期限
		String eqsDeadline = paramMap.get("eqsDeadline");
		
		try {
			if (StringUtils.isNotBlank(userNames)) {
				List<Map<String,Object>> debtList = assignmentDebtService.queryCanAssignmentDebt(userNames,debtAnnualRate,debtDeadline,eqsDeadline);
				
				request().setAttribute("debtList", debtList);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	
	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

}