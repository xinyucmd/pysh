package com.jiangchuanbanking.flowdetails.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.service.IAlertItemService;
import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.dict.domain.Currency;
import com.jiangchuanbanking.financing.domain.Account;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.flowdetails.service.IFinancingDetailsService;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;


@SuppressWarnings("serial")
public class FinancingDetailsAction extends BaseListAction {

    private IBaseService baseService;
    private IFinancingDetailsService financingDetailsService;
    private FinancingDetails details;
    private List<FinancingDetails> detailsList;
    private String sub_no;
    private Integer cif_no;
    private String pact_no;
    private Customer customer;
    private PactInfo pactInfo;
    private static final String CLAZZ = FinancingDetails.class.getSimpleName();
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String listSub(){
		return"listSubAccountPage";
	}
	public String listDetails(){
//		detailsList = baseService.findByParam("from FinancingDetails where sub_no = ?", sub_no);
		if (baseService.findByHQL(" from Customer where id='"+cif_no+"'").size()>0) {
			customer = (Customer) baseService.findByHQL(" from Customer where id='"+cif_no+"'").get(0);
		}
		if (baseService.findByHQL(" from PactInfo where pact_no='"+pact_no+"'").size()>0) {
			pactInfo = (PactInfo) baseService.findByHQL(" from PactInfo where pact_no='"+pact_no+"'").get(0);
		}
		return SUCCESS;
	}
	public String getDetailsJson() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("sub_no", sub_no);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				1, loginUser);
		if (searchCondition.getCondition()==null||"".endsWith(searchCondition.getCondition())) {
			searchCondition.setCondition(" sub_no='"+sub_no+"'");
		}else{
			searchCondition.setCondition(searchCondition.getCondition()+" and sub_no='"+sub_no+"'");
		}
		SearchResult<FinancingDetails> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		
		Iterator<FinancingDetails> financingDetails = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(financingDetails, totalRecords, null, false);
		return null;
	}
	public static void getListJson(Iterator<FinancingDetails> financingDetails,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (financingDetails.hasNext()) {
			FinancingDetails instance = financingDetails.next();
			int flow_no=instance.getFlow_no();
			String sub_no=instance.getSub_no();
			String account_no=instance.getAccount_no();
			String flow_type=instance.getFlow_type();
			String flow_abstract=instance.getFlow_abstract(); 
			String flow_amt=instance.getFlow_amt();
			String flow_date=instance.getFlow_date();
			String op_no=instance.getOp_no();
			String cmt=instance.getCmt();
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(flow_no).append("\",\"")
						.append(sub_no).append("\",\"").append(account_no)
						.append("\",\"").append(flow_type).append("\"")
				       .append(",\"").append(flow_abstract).append("\",\"")
						.append(flow_amt).append("\",\"").append(flow_date)
						.append("\",\"").append(op_no).append("\",\"")
						.append(cmt).append("\"]}");
			} else {
				jsonBuilder.append("{\"flow_no\":\"").append(flow_no)
						.append("\",\"sub_no\":\"").append(sub_no)
						.append("\",\"account_no\":\"").append(account_no)
						.append("\",\"flow_type\":\"").append(flow_type)
						.append("\",\"flow_abstract\":\"").append(flow_abstract)
						.append("\",\"flow_amt\":\"").append(flow_amt)
						.append("\",\"flow_date\":\"").append(flow_date)
						.append("\",\"op_no\":\"").append(op_no)
						.append("\",\"cmt\":\"").append(cmt).append("\"}");
			}
			if (financingDetails.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	public IFinancingDetailsService getFinancingDetailsService() {
		return financingDetailsService;
	}
	public void setFinancingDetailsService(
			IFinancingDetailsService financingDetailsService) {
		this.financingDetailsService = financingDetailsService;
	}
	public FinancingDetails getDetails() {
		return details;
	}
	public void setDetails(FinancingDetails details) {
		this.details = details;
	}
	public List<FinancingDetails> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<FinancingDetails> detailsList) {
		this.detailsList = detailsList;
	}
	public String getSub_no() {
		return sub_no;
	}
	public void setSub_no(String sub_no) {
		this.sub_no = sub_no;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public PactInfo getPactInfo() {
		return pactInfo;
	}
	public void setPactInfo(PactInfo pactInfo) {
		this.pactInfo = pactInfo;
	}
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	public Integer getCif_no() {
		return cif_no;
	}
	public void setCif_no(Integer cif_no) {
		this.cif_no = cif_no;
	}
	public String getPact_no() {
		return pact_no;
	}
	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}
}
