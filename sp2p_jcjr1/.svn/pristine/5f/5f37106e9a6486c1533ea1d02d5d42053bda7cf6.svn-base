package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.DesSecurityUtil;
import com.shove.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;

@SuppressWarnings("serial")
public class UserBankManagerAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(LinksAction.class);
	private AdminService adminService;
	private List<Map<String,Object>> checkers;
	 private FundManagementService fundManagementService;
		public FundManagementService getFundManagementService() {
			return fundManagementService;
		}

		public void setFundManagementService(FundManagementService fundManagementService) {
			this.fundManagementService = fundManagementService;
		}
		
	public String queryUserBankInit() throws SQLException, DataException{
		String types = request.getString("types");
		request().setAttribute("types", types);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserBankList() throws Exception{
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String realName = Convert.strToStr(paramMap.get("realName"), null);
		//
		String checkUser = Convert.strToStr(paramMap.get("checkUser"), null);
		String moStartTime = Convert.strToStr(paramMap.get("modifiedTimeStart"), null);
		String moEndTime = Convert.strToStr(paramMap.get("modifiedTimeEnd"), null);
		String checkStartTime = Convert.strToStr(paramMap.get("checkTimeStart"), null);
		String checkTimeEnd = Convert.strToStr(paramMap.get("checkTimeEnd"), null);
		
		moEndTime = FrontMyPaymentAction.changeEndTime(moEndTime);
		checkTimeEnd = FrontMyPaymentAction.changeEndTime(checkTimeEnd);
		
		Long checkUserId = -100L;
		if(checkUser!=null){
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			//加载银行卡信息
			fundManagementService.queryBankCardInfos(pageBean,userName,realName,checkUserId,moStartTime,moEndTime,
					checkStartTime,checkTimeEnd);
			fundManagementService.changeNumToName(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			List<Map<String,Object>> list = pageBean.getPage();
			if(list!=null){
				Iterator<Map<String,Object>> iter = list.iterator();
				int i = 0;
				while(iter.hasNext()){
					Map<String,Object> map = iter.next();
					list.get(i).put("ids", new DesSecurityUtil().encrypt(map.get("id").toString()));
					i++;
				}
				pageBean.setPage(list);
			}
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserModifiyBankList() throws Exception{
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String realName = Convert.strToStr(paramMap.get("realName"), null);
		//username  需要转换成 id 去搜条件
		String checkUser = Convert.strToStr(paramMap.get("checkUser"), null);
		String cStartTime = Convert.strToStr(paramMap.get("commitTimeStart"), null);
		String cEndTime = Convert.strToStr(paramMap.get("commitTimeEnd"), null);
		int cardStatus = Convert.strToInt(paramMap.get("cardStatus"), -1);
		
		cEndTime = FrontMyPaymentAction.changeEndTime(cEndTime);
		
		Long checkUserId = -100L;
		if(checkUser!=null){
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			//加载银行卡信息
			fundManagementService.queryModifyBankCardInfos(pageBean,userName,realName,checkUserId,
					cStartTime,cEndTime,cardStatus);
			
			fundManagementService.changeNumToName(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			List<Map<String,Object>> list = pageBean.getPage();
			if(list!=null){
				Iterator<Map<String,Object>> iter = list.iterator();
				int i = 0;
				while(iter.hasNext()){
					Map<String,Object> map = iter.next();
					list.get(i).put("ids", new DesSecurityUtil().encrypt(map.get("id").toString()));
					i++;
				}
				pageBean.setPage(list);
			}
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	
	public String queryOneBankCardInfo() throws Exception{
		Long bankId = Convert.strToLong(new DesSecurityUtil().decrypt(request.getString("bankId")), -1L);
		try {
			//加载银行卡信息
			paramMap = fundManagementService.queryOneBankCardInfo( bankId);
			if(paramMap != null && paramMap.size() > 0){
				if(paramMap.get("mobilePhone") == null ||
						paramMap.get("mobilePhone").trim().equals("")){
					paramMap.put("mobilePhone", paramMap.get("cellPhone"));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
		
	}
	
	/**
	 * 银行卡变更
	 * @return
	 * @throws Exception 
	 */
	public String queryModifyBankInfo() throws Exception{
		Long bankId = Convert.strToLong(request.getString("bankId"), -1L);
		try {
			paramMap = fundManagementService.queryOneBankCardInfo( bankId);
			Map<String,String> bankCarApply = fundManagementService.queryOneBankCardInfoApply(bankId);
			request().setAttribute("bankCarApply", bankCarApply);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		request().setAttribute("bankId", bankId);
		return SUCCESS;
	}
	
	/**
	 * 银行卡审核
	 * @return
	 * @throws Exception 
	 */
	public String updateUserBankInfo() throws Exception{
		Long checkUserId = paramMap.get("userName")==null?
				null:Convert.strToLong(paramMap.get("userName"), -100);
		String remark = paramMap.get("remark")==null?null:Convert.strToStr(paramMap.get("remark"), null);
		Integer check_result = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		Long bankId = paramMap.get("bankId")==null?null:Convert.strToLong(paramMap.get("bankId"), -1);	
		Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try{

		 Long result = fundManagementService.updateBankInfo(checkUserId, bankId, remark, check_result,admin.getUserName(),admin.getLastIP());
			if(result < 0){
				JSONUtils.printStr("4");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		return SUCCESS;
	}
	
	/**
	 * 银行卡变更审核
	 * @return
	 * @throws Exception 
	 */
	public String updateUserModifyBank() throws Exception{
		Long checkUserId = paramMap.get("userName")==null?
				null:Convert.strToLong(paramMap.get("userName"), -100);
		String remark = paramMap.get("remark")==null?null:Convert.strToStr(paramMap.get("remark"), null);
		Integer check_result = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		Long bankId = paramMap.get("bankId")==null?null:Convert.strToLong(paramMap.get("bankId"), -1);		
		try{
		 Admin admin  = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			@SuppressWarnings("unused")
			Long result = -1L;
			if(check_result == 1){//审核成功
				result = fundManagementService.updateModifyBankInfo(checkUserId, 
						bankId, remark, check_result, paramMap.get("modifiedBankName"), 
						paramMap.get("modifiedBranchBankName"), paramMap.get("modifiedCardNo"),
						paramMap.get("modifiedTime"), true);
				//添加操作日志
				operationLogService.addOperationLog("t_bankcard", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "银行卡变更审核成功", 2);
			}else{//审核失败
				result = fundManagementService.updateModifyBankInfo(checkUserId, 
						bankId, remark, check_result, paramMap.get("modifiedBankName"), 
						paramMap.get("modifiedBranchBankName"), paramMap.get("modifiedCardNo"),
						paramMap.get("modifiedTime"), false);
				//添加操作日志
				operationLogService.addOperationLog("t_bankcard", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "银行卡变更审核失败", 2);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

/*	public UserBankManagerService getUserBankService() {
		return userBankService;
	}

	public void setUserBankService(UserBankManagerService userBankService) {
		this.userBankService = userBankService;
	}*/

	public List<Map<String, Object>> getCheckers() throws Exception {
		if(checkers == null){
			//加载审核人员列表
			checkers = adminService.queryAdministors(IConstants.ADMIN_ENABLE);
		}
		return checkers;
	}

	public void setCheckers(List<Map<String, Object>> checkers) {
		this.checkers = checkers;
	}
	
}
