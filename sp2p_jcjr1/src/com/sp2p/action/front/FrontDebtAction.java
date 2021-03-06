package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class FrontDebtAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontDebtAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;

	private UserService userService;
	//富爸爸调用
	private FrontMyFinanceAction frontMyFinanceAction;

	private FinanceService financeService;
	
	
	public FrontMyFinanceAction getFrontMyFinanceAction() {
		return frontMyFinanceAction;
	}

	public void setFrontMyFinanceAction(FrontMyFinanceAction frontMyFinanceAction) {
		this.frontMyFinanceAction = frontMyFinanceAction;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 查询前台的债权转让
	 * 
	 * @return
	 */
	public String queryFrontAllDebt()  throws Exception{
		pageBean.setPageNum(request.getInt("curPage", -1));
		long debtSum = request.getLong("debtSum", -1);
		//whb去掉竞拍
		//long auctionBasePrice = request.getLong("auctionBasePrice", -1);
		//long auctionMode = request.getLong("auctionMode", -1);
		long isLate = request.getLong("isLate", -1);
		long publishDays = request.getLong("publishDays", -1);
		String borrowTitle = paramMap.get("borrowTitle");
		try {

			assignmentDebtService.queryAllDebt(borrowTitle,debtSum,
					isLate, publishDays, "1,2", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();
			Date nowDate = new Date();
			if (list != null) {
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("actionTime");
					String remainDays = DateUtil.remainDateToString(nowDate,
							date);
					map.put("remainDays", remainDays);
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		//将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询前台的债权转让
	 * 
	 * @return
	 */
	public String queryFrontSuccessDebt() throws Exception {
		pageBean.setPageNum(request.getInt("curPage", -1));
		long debtSum = request.getLong("debtSum", -1);
		//whb去掉竞拍
		//long auctionBasePrice = request.getLong("auctionBasePrice", -1);
		//long auctionMode = request.getLong("auctionMode", -1);
		long isLate = request.getLong("isLate", -1);
		long publishDays = request.getLong("publishDays", -1);
		String borrowTitle = request.getString("borrowTitle");
		
		try {
			assignmentDebtService.queryAllDebt(borrowTitle,debtSum,
					isLate, publishDays, "2", pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		//将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询债权详情
	 * 
	 * @return
	 */
	public String queryDebtDetail() throws Exception {
		long id = request.getLong("id", -1);
		String  annualRateDebtBDDouble = request.getString("annualRateDebtBDDouble");
		String remainingDays =  request.getString("remainingDays");
		try {
			User user = (User) session().getAttribute("user");
			if(user!=null){
				Long userId = user.getId();
				Map<String, String> userMap = financeService.queryUserById(userId);
				if(userMap!=null){
					String usableSum = userMap.get("usableSum");
					request().setAttribute("usableSum", usableSum);
				}
				
			}else{
				return "noLogin";
			}
			Map<String, String> map = assignmentDebtService
					.getAssignmentDebt(id);
			
			// 设置新标题
			String debtBorrowTile = map.get("debtBorrowTile");
			paramMap.put("debtBorrowTile", debtBorrowTile+"");
			if (map != null) {
				long viewCount = Convert.strToLong(map.get("viewCount"), 0);
				viewCount++;
				paramMap.putAll(map);
				long borrowId = Convert.strToLong(map.get("borrowId"), -1);
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				Map<String, String> mapth = auctionDebtService.queryBorrowerImgpath(borrowId);
				String imgPath = mapth.get("imgPath");
				paramMap.put("imgPath", imgPath+"");
				paramMap.put("borrowerId", borrowerId+"");
				paramMap.put("viewCount", viewCount + "");
				long deptStatus = Convert.strToLong(map.get("debtStatus"),-1);
				map = new HashMap<String, String>();
				map.put("viewCount", viewCount + "");
				assignmentDebtService.updateAssignmentDebt(id,deptStatus, map);
				String publishTime = paramMap.get("publishTime");
				//whb去掉竞拍
				long debtLimit = Convert.strToLong(paramMap
						.get("debtLimit"), 0);
				if(StringUtils.isNotBlank(publishTime)){
					String remainDays = DateUtil.remainDateToString(new Date(),
							DateUtil.dateAddDay(DateUtil.strToDate(publishTime),
									(int) debtLimit));
					paramMap.put("remainDays", remainDays);
				}
				long debtId = Convert.strToLong(paramMap.get("id"), -1);
				paramMap.put("debtId", paramMap.get("id"));
				paramMap.putAll(auctionDebtService
						.queryAuctionMaxPriceAndCount(debtId));
				long alienatorId = Convert.strToLong(paramMap
						.get("alienatorId"), -1);
				
				
				
				Map<String, String> userMap = auctionDebtService
						.getUserAddressById(alienatorId);
				request().setAttribute("userMap", userMap);
				
				paramMap.put("annualRateDebtBDDouble", annualRateDebtBDDouble);
				paramMap.put("remainingDays", remainingDays);
			}

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
	 * 
	 * 添加留言
	 * @throws DataException 
	 * 
	 */
	public String addDebtMSG() throws Exception {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("msg_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String msgContent = paramMap.get("msg") == null ? "" : paramMap
				.get("msg");
		long returnId = -1;
		returnId = assignmentDebtService.addDebtMsg(idLong, user.getId(),
				msgContent);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 留言初始化
	 * 
	 */
	public String debtMSGInit() throws Exception{
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String pageNum = paramMap.get("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (idLong == -1) {
			return "404";
		}
		assignmentDebtService.queryDebtMSGBord(idLong, pageBean);
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * 竞拍初始化
	 * 
	 * @return
	 */
	public String auctingDebtInit() throws Exception {
		//可用金额
		String usableSum = request.getString("usableSum") == null ? "0.00" : request.getString("usableSum");
		request().setAttribute("usableSum", usableSum);
		//投资id
		String investId = request.getString("investId") == null ? "" : request.getString("investId");
		//转让价格
		String debtPrice = request.getString("debtPrice") == null ? "" : request.getString("debtPrice");
		//债权价值
		String debtValue = request.getString("debtValue") == null ? "" : request.getString("debtValue");
		
		// 红包标记
		String isUseHb_6_24 = request.getString("isUseHb_6_24") == null ? "" : request.getString("isUseHb_6_24");
		
		request().setAttribute("debtPrice", debtPrice);
		request().setAttribute("debtValue", debtValue);
		request().setAttribute("isUseHb_6_24", isUseHb_6_24);
		long userId = this.getUserId();
		try {
			paramMap.put("debtId", request.getString("debtId"));
			paramMap.put("investId", investId);
			Map<String, String> map = auctionDebtService.getUserById(userId);
			if (map != null) {
				paramMap.put("usableSum", map.get("usableSum"));
				paramMap.put("totalSum", String.format("%.2f", Convert
						.strToDouble(map.get("freezeSum"), 0.0)
						+ Convert.strToDouble(map.get("usableSum"), 0.0)));
			}
			Map<String,String> debtMap =  assignmentDebtService.getAssignmentDebt(request.getLong("debtId", -1));
			if(debtMap != null){
				paramMap.putAll(debtMap);
			}
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
	 * 参与竞拍
	 * @return
	 */
	public String addAuctingDebt() throws Exception {
		long debtId = Convert.strToLong(paramMap.get("debtId"), -1);
		long userId = this.getUserId();
		
		try {
			//转让价格
			String auctionprice = paramMap.get("debtPrice");
			//债权价值
			String debtValue = paramMap.get("debtValue");
			if("".equals(auctionprice)){
				JSONUtils.printStr("-12"); //竞拍金额为空
				return null;
			}else{
				if(!auctionprice.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")){
					JSONUtils.printStr("-13"); //竞拍金额格式不正确
					return null;
				}
			}
			String pwd = paramMap.get("pwd");
			if ("1".equals(IConstants.ENABLED_PASS)){ //1为默认启用
				pwd =  Encrypt.MD5(pwd.trim());
			}else{
				pwd =  Encrypt.MD5(pwd.trim()+IConstants.PASS_KEY);
			}
			
			
			
			double debtPrice = Convert.strToDouble(paramMap.get("debtPrice"), 0.0);
			String code = paramMap.get("code");
			String sessionCode = (String) session().getAttribute(
					"auction_checkCode");
			if (sessionCode == null || !sessionCode.equals(code)) {
				JSONUtils.printStr("-1"); // 验证码错误
				return null;
			}
			
			long result = -9999;
			String basePath = this.getBasePath();
			
			Map<String,String> resUserMap = userService.queryGroupUser(userId);
			Map<String,String> user_map = assignmentDebtService.findUserById(userId);
			if(user_map!=null  && user_map.size()>0){
				paramMap.put("applyState", "1");
			}
			
			Map<String, String> debtMap = assignmentDebtService
					.getAssignmentDebt(debtId);
			
			if(debtMap.get("investPwd")!=null && debtMap.get("investPwd").trim().length()>0 && !String.valueOf(debtMap.get("investPwd")).trim().toLowerCase().equals("null")){
				String investPwd = String.valueOf(paramMap.get("investPwd"));
				String investPwd2 = String.valueOf(debtMap.get("investPwd"));
				
				log.info("投资债转密码：====>"+investPwd+","+investPwd2);
				if(investPwd!= null && !investPwd.equals(investPwd2)){
					JSONUtils.printStr("-333"); //投资密码错误
					return null;
				}
			}else{
				log.info("投资债转密码：普通投资，无需密码");
			}
			
			if((resUserMap !=null && resUserMap.size()>0)||
					(user_map!=null && user_map.size()>0)
					){// 判断是否为机构/线下理财人用户
				Map<String, String> userMap = auctionDebtService
						.getUserById(userId);
				if (!pwd.equals(userMap.get("dealpwd"))) {// 交易密码不对
					JSONUtils.printStr("-3"); 
					return null;
				}
				
				if (debtMap.get("alienatorId").equals(userId + "")) {// 不能投自己转让的的债权
					JSONUtils.printStr("-2"); 
					return null;
				}
				//借款id
				long borrowId = Convert.strToLong(debtMap.get("borrowId"), -1);
			
				if (!pwd.equals(userMap.get("dealpwd"))) {// 交易密码不对
					JSONUtils.printStr("-3"); 
					return null;
				}
				Map<String,String> aucctionMap = auctionDebtService.getAuctionDebt(debtId,userId);
				double oldAuctionPrice = 0.0;
				if(aucctionMap != null){
					oldAuctionPrice = Convert.strToDouble(aucctionMap.get("auctionPrice"),0.0);
				}
				
				double usableSum = Convert.strToDouble(userMap.get("usableSum"), 0.0);
				if (usableSum < (debtPrice-oldAuctionPrice)) {// 可用余额不足
					JSONUtils.printStr("-4"); 
					return null;
				}
				
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				if(borrowerId==userId){// 借款者不能竞拍该债权
					JSONUtils.printStr("-9"); 
					return null;
				}
				
				if(!"1".equals(debtMap.get("debtStatus"))){ //竞拍失败
					JSONUtils.printStr("-7");
					return null;
				}
				
				result = assignmentDebtService.addInvestDebtApply(debtId, userId, pwd, debtPrice, basePath, debtValue);
				if(result >0){// 申请转债投资成功
					JSONUtils.printStr("9988"); 
					return null;
				}
			}else{
				
				String isUseHb_6_24Str = paramMap.get("isUseHb_6_24");
				if(StringUtils.isNotBlank(isUseHb_6_24Str) && isUseHb_6_24Str.trim().equals("1")){
					result = assignmentDebtService.addAuctingDebt(debtId, userId, pwd, debtPrice,this.getBasePath(), debtValue,1);
				}else{
					result = assignmentDebtService.addAuctingDebt(debtId, userId, pwd, debtPrice,this.getBasePath(), debtValue,0);
				}
			}
			
			// 返回结果
			JSONUtils.printStr(result+""); 
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询竞拍记录
	 * 
	 * @return
	 */
	public String queryAcutionRecordInfo() throws Exception {
		long id = Convert.strToLong(paramMap.get("id"), -1);
		try {
			List<Map<String, Object>> list = auctionDebtService
					.queryAuctionDebtByDebtId(id);
			request().setAttribute("list", list);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

}
