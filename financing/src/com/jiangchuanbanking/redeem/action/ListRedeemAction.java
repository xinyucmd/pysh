package com.jiangchuanbanking.redeem.action;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.redeem.domain.RedeemEntity;
import com.jiangchuanbanking.redeem.domain.RedeemPayView;
import com.jiangchuanbanking.redeem.service.IRedeemService;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;


@SuppressWarnings("serial")
public class ListRedeemAction extends BaseListAction {

    private IBaseService  baseService;
    private IRedeemService redeemService;
    private ISelectService selectService;
    private String pactNo;
	private static final String CLAZZ = RedeemEntity.class.getSimpleName();

	@Override
	public String list() throws Exception {
		SearchCondition searchCondition = getSearchCondition();
		SearchResult<RedeemEntity> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<RedeemEntity> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, null, false);
		return null;
	}
	@Override
	protected String getEntityName() {
		return RedeemEntity.class.getSimpleName();
	}
	public String listRedemConfirm1() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","  sts='11' and redem_type in ('1','2','3') ");

		SearchResult<RedeemEntity> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<RedeemEntity> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(redemiIterator, totalRecords, searchCondition, true);
		return null;
	}
	public String listUndoApply() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","  sts='11' and redem_type='4'");

		SearchResult<RedeemEntity> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<RedeemEntity> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(redemiIterator, totalRecords, searchCondition, true);
		return null;
	}
	
	public String listRedemPay() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","");
 

		SearchResult<RedeemPayView> result = baseService.getPaginationObjects(
				RedeemPayView.class.getSimpleName(), searchCondition);
		Iterator<RedeemPayView> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListRedemPayJson(redemiIterator, totalRecords, searchCondition, true);
		return null;
	}
	public String listCancelAdvRedem() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID"," sts in('21','11') and redem_type='3' ");
 

		SearchResult<RedeemEntity> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<RedeemEntity> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(redemiIterator, totalRecords, searchCondition, true);
		return null;
	}
	
	public String listPayHis() throws Exception{
	
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","PACT_NO='"+pactNo+"' and IF_SETTLE='1' order by term");
		
 
		SearchResult<PlanBean> result = baseService.getPaginationObjects(
				PlanBean.class.getSimpleName(), searchCondition);
		Iterator<PlanBean> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListPayHisJson(redemiIterator, totalRecords, searchCondition, false);

		return null;

	}
	public String listCancelUndo() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID"," sts in('21','11') and redem_type='4' ");
 

		SearchResult<RedeemEntity> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<RedeemEntity> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(redemiIterator, totalRecords, searchCondition, true);
		return null;
	}
	
	
	
	private void getListJson(Iterator<RedeemEntity> redemiIterator,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws IOException {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (redemiIterator.hasNext()) {
			RedeemEntity instance = redemiIterator.next();
			Integer cif_no  = instance.getCif_no();
			String pact_no = instance.getPact_no();
			String cif_name = instance.getCif_name();
			String sts = instance.getSts();
			String redem_type = instance.getRedem_type();
			
			redem_type=selectService.getOpCnName("REDEM_TYPE", redem_type);
			
			String redem_amount = instance.getRedem_amount();
			String pay_amt = instance.getPay_amt();
			String create_date = instance.getCreate_date();
			String create_op = instance.getCreate_op();
			String redem_Date = instance.getRedem_Date();
			String redem_capital=instance.getRedem_capital();
			String redem_interest=instance.getRedem_interest();
			int id = instance.getId();
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
						.append(cif_name).append("\",\"").append(pact_no)
						.append("\",\"").append(redem_type)
						.append("\",\"").append(redem_amount).append("\",\"")
						.append(redem_Date).append("\",\"")
						.append(pay_amt).append("\",\"")
						.append(redem_capital).append("\",\"")
						.append(redem_interest).append("\",\"")
						.append(create_date).append("\",\"")
						.append(create_op).append("\"]}");
			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
						.append("\",\"cif_no\":\"").append(cif_no)
						.append("\",\"pact_no\":\"").append(pact_no)
						.append("\",\"cif_name\":\"")
						.append(cif_name).append("\",\"sts\":\"")
						.append(sts).append("\",\"redem_type\":\"")
						.append(redem_type).append("\",\"redem_amount\":\"")
						.append(redem_amount).append("\",\"redem_Date\":\"")
						.append(redem_Date).append("\",\"pay_amt\":\"")
						.append(pay_amt).append("\",\"redem_capital\":\"")
						.append(redem_capital).append("\",\"redem_interest\":\"")
						.append(redem_interest).append("\",\"create_date\":\"")
						.append(create_date).append("\",\"create_op\":\"")
						.append(create_op).append("\"}");
			}
			if (redemiIterator.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	private void getListRedemPayJson(Iterator<RedeemPayView> redemiIterator,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws IOException {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (redemiIterator.hasNext()) {
			RedeemPayView instance = redemiIterator.next();
			int id=instance.getId();
			String pact_no = instance.getPact_no();	
			String cif_name = instance.getCif_name();			
			String redem_type = instance.getRedem_type();	
			
			redem_type=selectService.getOpCnName("REDEM_TYPE", redem_type);
			
			String redem_amount = instance.getRedem_amount();			
			String create_date = instance.getCreate_date();
			String create_op = instance.getCreate_op();
			String redem_date = instance.getRedem_Date();
			String redem_capital=instance.getRedem_capital();
			String redem_interest=instance.getRedem_interest();
			if (isList) {

				jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"").
				         append(pact_no).append("\",\"")
				        .append(cif_name).append("\",\"")
						.append(redem_type).append("\",\"").append(redem_amount)
						.append("\",\"").append(redem_capital).append("\",\"")
						.append(redem_interest).append("\",\"")
						.append(redem_date).append("\"]}");
			} else {
				
			}
			if (redemiIterator.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	
	private void getListPayHisJson(Iterator<PlanBean> redemiIterator,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws IOException {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (redemiIterator.hasNext()) {
			PlanBean instance = redemiIterator.next();
			String pactNo=instance.getPact_no();
			String term=instance.getTerm();
			String settleDate=instance.getSettle_date();
			String endDate=instance.getEnd_date();
			String reAmt=instance.getReturn_amt();
	
			if (isList) {
				
			} else {
				jsonBuilder.append("{\"pactNo\":\"").append(pactNo)
						.append("\",\"term\":\"").append(term)
						.append("\",\"settleDate\":\"")
						.append(settleDate).append("\",\"reAmt\":\"")
						.append(reAmt).append("\"}");
			}
			if (redemiIterator.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	
	


	public ISelectService getSelectService() {
		return selectService;
	}
	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	public IRedeemService getRedeemService() {
		return redeemService;
	}
	public void setRedeemService(IRedeemService redeemService) {
		this.redeemService = redeemService;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
    

	
}
