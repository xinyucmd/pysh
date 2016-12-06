package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.SettingDebtService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.DebtUtil;

/**
 * 债权转让
 */
public class HomeDebtAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(HomeDebtAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;

	private SelectedService selectedService;

	private List<Map<String, Object>> debtLimitList;
	private UserService userService;
	
	private MyHomeService myHomeService;
	private SettingDebtService settingDebtService;
	
	public SettingDebtService getSettingDebtService() {
		return settingDebtService;
	}

	public void setSettingDebtService(SettingDebtService settingDebtService) {
		this.settingDebtService = settingDebtService;
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

	/**
	 * 债权转让
	 * 
	 * @param：
	 * borrowId借款id;
	 * investId还款拥有者;
	 * annualRate年利率(转让后的年利率,0系统自动分配线性利率);
	 * transRatio转让系数;
	 * nextRepayDate下一还款日期
	 * @author 
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public Map<String,Object> coseDebtPrice(long borrowId, long investId, String  transRatio, String annualRate) throws Exception{
		
		Map<String, String> debtMap = null;
		Map<String,Object> result = null;
		//获取当前借款
		Map<String, String> map = assignmentDebtService.getOnePay(borrowId);
		
		if(null != map && map.size() >0){
			if(1 == Integer.parseInt(map.get("deadline"))){ // 活力宝
				log.info("===========暂不支持活力宝转让=============");
			}else{
				debtMap = assignmentDebtService.getDebt(borrowId,investId);
			}
		}else {
			log.info("===========查询标的信息错误【BORRROW_ID:"+borrowId+"不存在】=============");
		}
		
		try {
			result = DebtUtil.debtFormula(debtMap,annualRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return result;
	}
	
	public Map<String,Object> coseDebtPrice(long investId, String annualRate) throws Exception{
		
		Map<String, String> debtMap = null;
		Map<String,Object> result = null;
		
		debtMap = assignmentDebtService.queryDebtCoseInvestedMap(investId);
		result = DebtUtil.debtFormula(debtMap,annualRate);
		result.put("awaitPI", 0.00);
		return result;
	}
	
	
	/**
	 * 可以转让的债权
	 * @author 黑暗珊瑚君
	 * @return
	 */
	public String queryCanAssignmentDebt() throws Exception {
		String borrowTitle = request.getString("borrowTitle");
		String borrowerName = request.getString("borrowerName");
		pageBean.setPageNum(request.getString("curPage"));
		long userId = this.getUserId();
		Map<String, String> mapSettingDebt = null;
		try {
			
			//判断用户是否为超级投资人-既机构用户和线下理财人
			Map<String, String> groupUserMap = settingDebtService.queryGroupAndLcUser(userId);
			int status = 0;
			if(groupUserMap!=null && groupUserMap.size()>0){
				long id = Convert.strToLong(groupUserMap.get("userId"), 0);
				if(id==userId && id>0){
					status = 1;
				}
			}
			request().setAttribute("status", status);

			//可转让的标的类型
			mapSettingDebt = settingDebtService.querySettingDebtById(1);
			
			if(mapSettingDebt == null){
				request().setAttribute("title", "管理员尚未进行债权参数!");
				return "message";
			}
			
			String borrowTypes = mapSettingDebt.get("borrow_types");
			borrowTypes = borrowTypes.substring(0, borrowTypes.length()-1);
			assignmentDebtService.queryCanAssignmentDebt(userId, borrowTitle,
					borrowerName, borrowTypes, pageBean,status);
			// 获得债权价值
			List<Map<String, Object>> lists = pageBean.getPage();
			Map<String,Object> result = null;
			String borrowId = "";
			String investId = "";
			String annualRate = "";
			if (lists != null && lists.size() > 0) {
				for (Map<String, Object> map : lists) {
					borrowId = String.valueOf(map.get("borrowId"));
					investId = String.valueOf(map.get("investId"));
					annualRate = String.valueOf(map.get("annualRate"));
//					nextRepayDate = String.valueOf(map.get("nextRepayDate"));
					result = coseDebtPrice(Long.parseLong(borrowId), Long.parseLong(investId), "", annualRate);
					map.putAll(result);
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 结束竞拍
	 * 
	 * @return
	 */
	public String auctingDebtEnd() throws Exception {
		long debtId = request.getLong("debtId", -1);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		try {
			assignmentDebtService.auctingDebtSuccess(debtId, user.getId(), 1, "");
			Map<String, String> map = assignmentDebtService
					.getAssignmentDebt(debtId);
			if (map != null) {
				long alienatorId = Convert
						.strToLong(map.get("alienatorId"), -1);// 转让人
				userService.updateSign(alienatorId);// 更换校验码
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询竞拍中的债权
	 * 
	 * @return
	 */
	public String queryAuctingDebt() throws Exception {
		String borrowTitle = request.getString("borrowTitle");
		String borrowerName = request.getString("borrowerName");
		pageBean.setPageNum(request.getString("curPage"));

		long userId = this.getUserId();
		// 区分1：已转入
		int flag = 0;
		try {
			assignmentDebtService.queryAuctingDebt(flag, userId, borrowTitle,
					borrowerName, "1", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();

			if (list != null) {
				Date nowDate = new Date();
				Map<String,Object> result = null;
				String borrowId = "";
				String investId = "";
//				String annualRate = "";
				String transRatio = "";
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("remainAuctionTime");
					map.put("remainDays",
							DateUtil.remainDateToString(nowDate, date));
					borrowId = String.valueOf(map.get("borrowId"));
					investId = String.valueOf(map.get("investId"));
//					annualRate = String.valueOf(map.get("annualRate"));
//					nextRepayDate = String.valueOf(map.get("nextRepayDate"));
					transRatio = String.valueOf(map.get("transRatio"));
					result = coseDebtPrice(Long.parseLong(borrowId), Long.parseLong(investId), transRatio, "");
					map.putAll(result);
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询竞拍结束的债权
	 * 
	 * @return
	 */
	public String queryAuctedDebt() throws Exception {
		User user = (User) session().getAttribute("user");
		Map<String, String> accmountStatisMap = myHomeService
			.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		
		String borrowTitle = request.getString("borrowTitle");
		String borrowerName = request.getString("borrowerName");
		pageBean.setPageNum(request.getString("curPage"));

		long userId = this.getUserId();
		// 区分1：已转入
		int flag = 0;
		try {
			assignmentDebtService.queryAuctingDebt(flag, userId, borrowTitle,
					borrowerName, "2", pageBean);
			DesSecurityUtil des = new DesSecurityUtil();
			List<Map<String, Object>> lists = pageBean.getPage();

			if (lists != null) {
				Map<String, Object> result = null;
				for (Map<String, Object> map : lists) {
					String borrowId = map.get("borrowId") + "";
					String encBorrowId = des.encrypt(borrowId);
					map.put("encBorrowId", encBorrowId);

					String investId = map.get("investId") + "";
					String encInvestId = des.encrypt(investId);
					map.put("encInvestId", encInvestId);

					String debtId = map.get("debtId") + "";
					String encDebtId = des.encrypt(debtId);
					map.put("encDebtId", encDebtId);

					String typeId = "24";
					String encTypeId = des.encrypt(typeId);
					map.put("encTypeId", encTypeId);
					
					//whb 一次性还款剩余期数
					String paymentMode = map.get("paymentMode") + "";
					if(4 == Integer.parseInt(paymentMode)){
//						result = coseDebtPrice(Long.parseLong(borrowId), Long.parseLong(investId), "", "", "");
						result = coseDebtPrice(Long.parseLong(investId),"");
						map.put("remainPeriod", result.get("remainPeriod"));
					}
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 添加债权转让
	 * 
	 * @return
	 */
	public String addAssignmentDebt() throws Exception {
		long userId = this.getUserId();
		paramMap.put("publishTime", DateUtil.dateToString(new Date()));
		paramMap.put("alienatorId", userId + "");
		paramMap.put("applyTime", DateUtil.dateToString(new Date()));
		paramMap.put("debtStatus", "1");
		
		long borrowId = Convert.strToLong(paramMap.get("borrowId"), 0);
		long investId = Long.parseLong(paramMap.get("investId"));
		String transRatio = paramMap.get("transRatio");
		Map<String,Object> result = coseDebtPrice(borrowId, investId, transRatio, "");
		String debtPrice = String.valueOf(result.get("debtPrice"));
		paramMap.put("debtSum", debtPrice);
		
		// 本次申请金额
		long reslut = -1;
		double moneyFlag = -1;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			// whb添加用户组每月债转限额
			moneyFlag = assignmentDebtService
					.checkTransMoney(userId, Convert.strToDouble(debtPrice, -1));
			if (moneyFlag == 1) {
				// whb 债权不用审核
				reslut = assignmentDebtService.addAssignmentDebt(paramMap);
				if (reslut != -1) {
					JSONUtils.printStr("1");
					operationLogService.addOperationLog("t_assignment_debt",
							admin.getUserName(), IConstants.UPDATE,
							admin.getLastIP(), 0, "债权转让审核成功", 2);
				} else {
					JSONUtils.printStr("-1");
					operationLogService.addOperationLog("t_assignment_debt",
							admin.getUserName(), IConstants.UPDATE,
							admin.getLastIP(), 0, "债权转让审核失败", 2);
				}
			} else if (moneyFlag > 1) {
				JSONUtils.printStr(String.valueOf(moneyFlag));
				operationLogService.addOperationLog("t_assignment_debt",
						admin.getUserName(), IConstants.UPDATE,
						admin.getLastIP(), 0, "债权转让审核失败", 2);
			}

		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 查询失败的债权（已转入）
	 * 
	 * @return
	 */
	public String queryInDebt() throws Exception {
		String borrowTitle = "";
		String borrowerName = "";
		pageBean.setPageNum(request.getString("curPage"));

		long userId = this.getUserId();
		// 区分1：已转入
		int flag = 1;
		try {
			assignmentDebtService.queryAuctingDebt(flag, userId, borrowTitle,
					borrowerName, "2", pageBean);
			List<Map<String, Object>> lists = pageBean.getPage();
			DesSecurityUtil des = new DesSecurityUtil();
			if (lists != null) {
				Map<String,Object> result = null;
				for (Map<String, Object> map : lists) {
					String borrowId = map.get("borrowId") + "";
					String encBorrowId = des.encrypt(borrowId);
					map.put("encBorrowId", encBorrowId);

					String investId = map.get("investId") + "";
					String encInvestId = des.encrypt(investId);
					map.put("encInvestId", encInvestId);

					String debtId = map.get("debtId") + "";
					String encDebtId = des.encrypt(debtId);
					map.put("encDebtId", encDebtId);

					String typeId = "24";
					String encTypeId = des.encrypt(typeId);
					map.put("encTypeId", encTypeId);
					
					//whb 一次性还款剩余期数
					String paymentMode = map.get("paymentMode") + "";
					if(4 == Integer.parseInt(paymentMode)){
						result = coseDebtPrice(Long.parseLong(borrowId), Long.parseLong(investId), "", "");
						map.put("remainPeriod", result.get("remainPeriod"));
					}
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 取消申请债权转让
	 * 
	 * @return
	 */
	public String cancelApplyDebt() throws Exception {
		long debtId = request.getLong("debtId", -1);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		try {
			assignmentDebtService.cancelAssignmentDebt(debtId, 4, user.getId(),
					1);
			Map<String, String> map = assignmentDebtService
					.getAssignmentDebt(debtId);
			if (map != null) {
				long alienatorId = Convert
						.strToLong(map.get("alienatorId"), -1);// 转让人
				userService.updateSign(alienatorId);// 更换校验码
			}
			// whb去掉竞拍
			// List<Map<String, Object>> auctionerList =
			// assignmentDebtService.queryAuctioner(debtId);
			// if(auctionerList != null){
			// for(Map<String, Object> auctioner: auctionerList){
			// long auctionerId = Convert.strToLong(auctioner.get("userId")+"",
			// -1);//竞拍者
			// userService.updateSign(auctionerId);//更换校验码
			// }
			// }
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 参与竞拍的债权
	 * 
	 * @return
	 */
	public String queryBuyingDebt() throws Exception {
		String borrowTitle = request.getString("borrowTitle");
		String startTime = request.getString("startTime");
		String endTime = request.getString("endTime");
		long userId = this.getUserId();
		pageBean.setPageNum(request.getString("curPage"));

		try {
			auctionDebtService.queryAuctionDebt(borrowTitle, startTime,
					endTime, userId, "", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();

			if (list != null) {
				Date nowDate = new Date();
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("remainAuctionTime");
					map.put("remainDays",
							DateUtil.remainDateToString(nowDate, date));
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 成功竞拍的债权
	 * 
	 * @return
	 */
	public String querySucessBuyedDebt() throws Exception {
		String borrowTitle = request.getString("borrowTitle");
		String startTime = request.getString("startTime");
		String endTime = request.getString("endTime");
		long userId = this.getUserId();
		pageBean.setPageNum(request.getString("curPage"));
		try {
			auctionDebtService.querySuccessAuctionDebt(borrowTitle, startTime,
					endTime, userId, pageBean);

			DesSecurityUtil des = new DesSecurityUtil();
			List<Map<String, Object>> lists = pageBean.getPage();

			if (lists != null) {
				for (Map<String, Object> map : lists) {
					String debtId = map.get("id") + "";
					String encDebtId = des.encrypt(debtId);
					map.put("encDebtId", encDebtId);

					String typeId = "24";
					String encTypeId = des.encrypt(typeId);
					map.put("encTypeId", encTypeId);
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		this.setRequestToParamMap();
		return SUCCESS;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public List<Map<String, Object>> getDebtLimitList() {
		if (debtLimitList == null) {
			debtLimitList = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("selectId", -100);
			mp.put("selectValue", "---请选择---");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 1);
			mp.put("selectValue", "1");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 2);
			mp.put("selectValue", "2");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 3);
			mp.put("selectValue", "3");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 4);
			mp.put("selectValue", "4");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 5);
			mp.put("selectValue", "5");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 6);
			mp.put("selectValue", "6");
			debtLimitList.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("selectId", 7);
			mp.put("selectValue", "7");
			debtLimitList.add(mp);
		}
		return debtLimitList;
	}

	public void setDebtLimitList(List<Map<String, Object>> debtLimitList) {
		this.debtLimitList = debtLimitList;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

}
