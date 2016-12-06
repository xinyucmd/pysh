package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
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
import com.shove.util.DesSecurityUtil;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;

/**
 * 用户资金管理
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class UserFundRecordAction extends BaseFrontAction{

	public static Log log = LogFactory.getLog(LinksAction.class);
	private UserService userService;
	private List<Map<String,Object>> status;
	private List<Map<String,Object>> rechargeStatus;
	private List<Map<String,Object>> rechargeType;
	private AdminService adminService;
	private FundManagementService fundManagementService;
	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}
	/**
	 * 用户资金管理页面加载
	 * @return
	 */
	public String userFundInit(){
		return SUCCESS;
	}
	
	/**
	 * 查找用户资金列表信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundList() throws Exception{
		try {
			//加载银行卡信息
			String userName = Convert.strToStr(paramMap.get("userName"), null);
			fundManagementService.queryUserCashList(pageBean, userName);
			Map<String, String> map = fundManagementService.queryUserCashList(pageBean, userName);
			String dueinSumss = map.get("dueinSums").toString().substring(0, map.get("dueinSums").toString().length()-2);
			map.put("dueinSumss", dueinSumss);
			request().setAttribute("map", map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		List<Map<String,Object>> list = pageBean.getPage();
		if(list!=null){
			Iterator<Map<String,Object>> iter = list.iterator();
			int i = 0;
			while(iter.hasNext()){
				Map<String, Object> map = iter.next();
				list.get(i).put("ids", new DesSecurityUtil().encrypt(map.get("userId").toString()));
				i++;
			}
			pageBean.setPage(list);
		}
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	public String userFundWithdrawInit(){
		String userId = request.getString("userId");
		paramMap.put("userId", userId);
		return SUCCESS;
	}
	
	/**
	 * 查询提现记录
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundWithdrawList() throws Exception{
		
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("endTime"), null));
		Double sum = paramMap.get("sum")==null?-100: Convert.strToDouble(paramMap.get("sum"), -100);
		Integer status = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		
		Long userId = Convert.strToLong(new DesSecurityUtil().decrypt(paramMap.get("userId")), -100);
		try{
			fundManagementService.queryUserFundWithdrawInfo(pageBean,userName,startTime,endTime,
					sum,status,userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserFundRechargeList() throws Exception{
		
		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("endTime"), null));

		Integer status = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		
		Integer rt = paramMap.get("rechargeType")==null?-100:
			Convert.strToInt(paramMap.get("rechargeType"), -100);
		
		Long userId = Convert.strToLong(new DesSecurityUtil().decrypt(paramMap.get("userId")), -100);

		try{
			fundManagementService.queryUserFundRechargeInfo(pageBean,startTime,endTime,
					status,userId,rt);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String exportUserFundRecharge() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		//Long userId = request.getLong("userId",-1L);
		
		try {
			Long userId = Convert.strToLong(new DesSecurityUtil().decrypt(request().getParameter("userId")), -100);
			Admin admin = (Admin)session().getAttribute(IConstants.SESSION_ADMIN);
			String applyTime =request.getString("applyTime");
			String endTime=request.getString("endTime");
			if(StringUtils.isBlank(endTime) &&StringUtils.isNotBlank(applyTime))
			{
				endTime = FrontMyPaymentAction.changeEndTime(applyTime);

			}
			Integer status = request.getInt("statss", -100);
			Integer rt = request.getInt("reType", -100);
			// 充值记录
			fundManagementService.queryUserFundRechargeInfo(pageBean,applyTime,endTime,
					status,userId,rt);
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
			
			List<Map<String, Object>>  lists = pageBean.getPage();
			for (Map<String, Object> map : lists) {
				String type = String.valueOf(map.get("type"));
				if("3".equals(type)){
					map.put("type", "国付宝");
				}
				if("4".equals(type)){
					map.put("type", "线下充值");
				}
				if("5".equals(type)){
					map.put("type", "手工充值");
				}
				if("7".equals(type)){
					map.put("type", "奖励充值");
				}
				if("10".equals(type)){
					map.put("type", "连连支付-手机");
				}
				
				String result = String.valueOf(map.get("result"));
				if("1".equals(result)){
					map.put("result", "成功");
				}else{
					map.put("result", "失败");
				}
			}
			
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录", pageBean
					.getPage(), new String[] { "用户名", "充值类型", "充值金额", "手续费",
					"到账金额", "充值时间", "状态" }, new String[] { "username",
					"type", "rechargeMoney", "poundage", "realMoney","rechargeTime","result"
					});
			operationLogService.addOperationLog("v_t_user_rechargeall_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户充值记录", 2);
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
 * 导出查询提现记录
 * 
 * @return
 */
	@SuppressWarnings("unchecked")
	public String exportUserFundWithdraw() {
	pageBean.pageNum = 1;
	pageBean.setPageSize(100000);
	Long userId = request.getLong("userId",-1L);
	try {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		//String applyTime=request().getParameter("applyTime")==null ? "" :request().getParameter("applyTime");
		String applyTime=request.getString("applyTime");
		//String  endTime=request().getParameter("endTime")==null ?  "" :request().getParameter("applyTime");
		String endTime=request.getString("endTime");
		if(StringUtils.isNotBlank(applyTime)&&StringUtils.isBlank(endTime))
		{
		 endTime = FrontMyPaymentAction.changeEndTime(applyTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime = FrontMyPaymentAction.changeEndTime(endTime);
		}
		//String userName=request().getParameter("userName")==null ? "" : request().getParameter("userName");
		String userName=request.getString("userName");
		if(StringUtils.isNotBlank(userName))
		{
			userName=URLDecoder.decode(userName, "UTF-8");
		}
		double sum=request.getDouble("sum", -1);
		int statss=request.getInt("statss",-1);
		// 提现记录
		fundManagementService.queryUserFundWithdrawInfo(pageBean,userName,applyTime,endTime,
				sum,statss,userId);
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
		HSSFWorkbook wb = ExcelUtils.exportExcel("提现记录", pageBean
				.getPage(), new String[] { "用户名", "真实姓名", "提现账号", "提现银行",
				"支行", "提现总额", "到账金额(￥)","手续费","提现时间" }, new String[] { "username",
				"realName", "acount", "bankName", "branchBankName","sum","realAccount","poundage","applyTime"
				});
		this.export(wb, new Date().getTime() + ".xls");
		
		operationLogService.addOperationLog("v_t_user_fundwithdraw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户提现记录", 2);
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

	
	public String queryAllUserFundRecordInit(){
		return SUCCESS;
	}

	public String queryAllUserFundRecordList() throws Exception{
		try {
			String userName = paramMap.get("userName");
			
			fundManagementService.queryAllUserFundRecordList(pageBean, userName);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	/**
	 * 导出资金明细
	 * @return
	 */
	public String exportAllUserFundList(){
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = request.getInt("statss", -1);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String userName = request.getString("userName") == null ? ""
					: request.getString("userName");
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			//资金明细
			fundManagementService.queryAllUserFundRecordList(pageBean, userName);
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
			fundManagementService.changeTraderName(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("资金明细表", pageBean
					.getPage(), new String[] { "ID","用户名", "类型", "操作金额", "总金额",
					"可用金额", "冻结金额", "待收金额", "交易对方", "记录时间" },
					new String[] { "id", "username", "fundMode", "handleSum",
							"totalSum","usableSum", "freezeSum", "dueinSum", "traderName",
							"recordTime",});
			operationLogService.addOperationLog("v_t_user_fundrecord_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出资金明细列表", 2);
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
	 * 资金管理  资金记录
	 * @return
	 */
	public String userFundRecordInit(){
		String userName = request.getString("userName");
		String userId = request.getString("userId");
		paramMap.put("userName", userName);
		paramMap.put("userId", userId);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserFundRecordList() throws Exception{
		Long userId = paramMap.get("userId")==null?-100:
			Convert.strToLong(new DesSecurityUtil().decrypt(paramMap.get("userId")), -100);
		
//		String userName = paramMap.get("userName");
//		if(userName != null){
//			request().setAttribute("userName", new String(userName.getBytes("ISO-8859-1"),"UTF-8"));
//		}
		
		Map<String,String> userMap = userService.queryUserById(userId);
		if(userMap != null && userMap.get("username") != null){
			request().setAttribute("userName", userMap.get("username"));
		}
		try{
			fundManagementService.queryUserFundRecordList(pageBean,userId);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		return SUCCESS;
	}
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getRechargeStatus() {
		if(rechargeStatus == null){//#{0:'全部',2:'成功',5:'失败',1:'充值中'}"
			rechargeStatus = new ArrayList<Map<String,Object>>();
			Map<String,Object> mp = null;
			mp = new HashMap<String,Object>();
			mp.put("statusId",-100 );
			mp.put("statusValue", "全部");
			rechargeStatus.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",1 );
			mp.put("statusValue", "成功");
			rechargeStatus.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",0 );
			mp.put("statusValue", "失败");
			rechargeStatus.add(mp);
			
		}
		return rechargeStatus;
	}

	public void setRechargeStatus(List<Map<String, Object>> rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public List<Map<String, Object>> getRechargeType() {
		if (rechargeType == null) {
			rechargeType = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", -100);
			mp.put("typeValue", "全部");
			rechargeType.add(mp);
			
//			mp = new HashMap<String, Object>();
//			mp.put("typeId", 1);
//			mp.put("typeValue", "支付宝支付");
//			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "环迅支付");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "国付宝");
			rechargeType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "线下充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 5);
			mp.put("typeValue", "手工充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 6);
			mp.put("typeValue", "虚拟充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 7);
			mp.put("typeValue", "奖励充值");
			rechargeType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 10);
			mp.put("typeValue", "连连支付");
			rechargeType.add(mp);

		}
		return rechargeType;
	}

	public void setRechargeType(List<Map<String, Object>> rechargeType) {
		this.rechargeType = rechargeType;
	}

	public List<Map<String, Object>> getStatus() {
		if(status == null){//#{0:'全部',2:'成功',5:'失败',1:'充值中'}"
			status = new ArrayList<Map<String,Object>>();
			Map<String,Object> mp = null;
			mp = new HashMap<String,Object>();
			mp.put("statusId",0 );
			mp.put("statusValue", "全部");
			status.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",2 );
			mp.put("statusValue", "成功");
			status.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",5 );
			mp.put("statusValue", "失败");
			status.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",4 );
			mp.put("statusValue", "充值中");
			status.add(mp);
		}
		return status;
	}

	public void setStatus(List<Map<String, Object>> status) {
		this.status = status;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	
}
