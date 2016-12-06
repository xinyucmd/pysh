package com.jiangchuanbanking.account.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.account.service.ISubAccountService;
import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.select.service.impl.SelectService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;

/**
 * 投资人列表
 * 
 */
public class ListSubAccountAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;
	private IBaseService baseService;
	private ISubAccountService subAccountService;
	private ISelectService selectService;
	private SubAccount subAccount;
	private String account_no;
	private String search;
	private Integer id;
	private Customer cust;
	private String sub_no;
	private static final String CLAZZ = SubAccount.class.getSimpleName();
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
	
	public String getSub(){
		return SUCCESS;
	}
	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String listFull() throws Exception {
		UserUtil.permissionCheck("view_account");

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("account_no", account_no);
		User loginUser = UserUtil.getLoginUser();
		
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,""," account_no="+account_no);
		SearchResult<SubAccount> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<SubAccount> subIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(subIterator, totalRecords, searchCondition, true);
		return null;
	}

	
	public String listCustSubAccount() throws Exception {
		
		String accountNo = getAccountNo(id);
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("account_no", accountNo);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,""," account_no="+accountNo);
		SearchResult<SubAccount> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<SubAccount> subIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getCusSubListJson(subIterator, totalRecords, searchCondition, false);
		return null;
	}
	public String listDetailsAccount() throws Exception {
		
	
//		String accountNo = getAccountNo();
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("account_no", sub_no);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,""," sub_no="+sub_no);
		SearchResult<FinancingDetails> result = baseService.getPaginationObjects(FinancingDetails.class.getSimpleName(),
				searchCondition);
		Iterator<FinancingDetails> detailsIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getDetailsSubListJson(detailsIterator, totalRecords, searchCondition, false);
		return null;
	}
	private void getDetailsSubListJson(
			Iterator<FinancingDetails> detailsIterator, long totalRecords,
			SearchCondition searchCondition, boolean isList) throws IOException {
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (detailsIterator.hasNext()) {
			FinancingDetails details = detailsIterator.next();
			Integer flow_no =details.getFlow_no();
			String flow_type=details.getFlow_type();
			flow_type = selectService.getOpCnName("WATER_TYPE", flow_type);
			String flow_abstract=CommonUtil.fromNullToEmpty(details.getFlow_abstract());
			String flow_amt=details.getFlow_amt();
			String flow_date=details.getFlow_date();
			String op_no=details.getOp_no();
			String cmt=CommonUtil.fromNullToEmpty(details.getCmt());
			jsonBuilder
			.append("{\"flow_no\":\"").append(flow_no)
			.append("\",\"flow_type\":\"").append(flow_type)
			.append("\",\"flow_abstract\":\"").append(flow_abstract)
			.append("\",\"flow_amt\":\"").append(flow_amt)
			.append("\",\"flow_date\":\"").append(flow_date)
			.append("\",\"op_no\":\"").append(op_no)
			.append("\",\"cmt\":\"").append(cmt)
			.append("\"}");
			if (detailsIterator.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		while(",".equals(jsonBuilder.subSequence(jsonBuilder.length()-1, jsonBuilder.length()))){
		    jsonBuilder.setLength(jsonBuilder.length()-1);			//字符集最后是","就去除
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	
	}

	private void getCusSubListJson(Iterator<SubAccount> subAccount,
			long totalRecords, SearchCondition searchCondition, boolean isList) throws Exception {
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (subAccount.hasNext()) {
			SubAccount instance = subAccount.next();
			String account_no = CommonUtil.fromNullToEmpty(instance.getAccount_no());
			String sub_no=CommonUtil.fromNullToEmpty(instance.getSub_no());
			String pact_no=CommonUtil.fromNullToEmpty(instance.getPact_no());
			String cif_no = CommonUtil.fromNullToEmpty(String.valueOf(instance.getCif_no()));
			String cif_name = CommonUtil.fromNullToEmpty(instance.getCif_name());
			String cash_amt = CommonUtil.fromNullToEmpty(instance.getCash_amt());
			String renew_amt = CommonUtil.fromNullToEmpty(instance.getRenew_amt());
			String rate=CommonUtil.fromNullToEmpty(instance.getRate());
			String prdt_no=CommonUtil.fromNullToEmpty(instance.getPrdt_no());
			String prdt_name=CommonUtil.fromNullToEmpty(instance.getPrdt_name());
			String term=CommonUtil.fromNullToEmpty(instance.getTerm());
			String payment_type=CommonUtil.fromNullToEmpty(instance.getPayment_type());
			payment_type = selectService.getOpCnName("PAYMENT_TYPE", payment_type);
			String income_amt=CommonUtil.fromNullToEmpty(instance.getIncome_amt());
			String redeem_amt=CommonUtil.fromNullToEmpty(instance.getRedeem_amt());
			String if_wxd=CommonUtil.fromNullToEmpty(instance.getIf_wxd());
				
			if_wxd = selectService.getOpCnName("YES_NO", if_wxd);

			String end_date=CommonUtil.fromNullToEmpty(instance.getEnd_date());
			String start_date=CommonUtil.fromNullToEmpty(instance.getStart_date());
			String sts = CommonUtil.fromNullToEmpty(instance .getSts());
			if(sts.equals("11"))
			{
				sts = "正常";
			}
			else if(sts.equals("21"))
			{
				sts = "正常关闭";
			}else
			{
				sts = "提前";
			}
			
			String open_date = CommonUtil.fromNullToEmpty(instance .getOpen_date());
			String open_op = CommonUtil.fromNullToEmpty(instance .getOpen_op());
			String close_date = CommonUtil.fromNullToEmpty(instance .getClose_date());
			String close_op=CommonUtil.fromNullToEmpty(instance .getClose_op());
			String cmt=CommonUtil.fromNullToEmpty(instance .getCmt());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").
				append(account_no).append("\",\"")
				.append(sub_no).append("\",\"").append(pact_no)
				.append("\",\"").append(cif_no).append("\",\"")
				.append(cif_name).append("\",\"")
				.append(cash_amt).append("\",\"").append(renew_amt)
				.append("\",\"").append(rate).append("\",\"")
				.append(prdt_no).append("\",\"").append(prdt_name)
				.append("\",\"").append(term).append("\",\"")
				.append(payment_type).append("\",\"")
				.append(income_amt).append("\",\"").append(redeem_amt)
				.append("\",\"").append(if_wxd).append("\",\"")
				.append(end_date).append("\",\"")
				.append(start_date).append("\",\"")
				.append(sts).append("\",\"")
				.append(open_date).append("\",\"")
				.append(open_op).append("\",\"")
				.append(close_date).append("\",\"")
				.append(close_op).append("\",\"")
				.append(cmt).append("\"]}");
			} else {
				jsonBuilder
						.append("{\"sub_no\":\"").append(sub_no)
						.append("\",\"pact_no\":\"").append(pact_no)
						.append("\",\"cash_amt\":\"").append(cash_amt)
						.append("\",\"renew_amt\":\"").append(renew_amt)
						.append("\",\"rate\":\"").append(rate)
						.append("\",\"prdt_no\":\"").append(prdt_no)
						.append("\",\"prdt_name\":\"").append(prdt_name)
						.append("\",\"term\":\"").append(term)
						.append("\",\"payment_type\":\"").append(payment_type)
						.append("\",\"income_amt\":\"").append(income_amt)
						.append("\",\"redeem_amt\":\"").append(redeem_amt)
						.append("\",\"if_wxd\":\"").append(if_wxd)
						.append("\",\"end_date\":\"").append(end_date)
						.append("\",\"start_date\":\"").append(start_date)
						.append("\",\"sts\":\"").append(sts)
						.append("\",\"open_date\":\"").append(open_date)
						.append("\",\"close_date\":\"")	.append(close_date)
						.append("\",\"close_op\":\"") .append(close_op)
						.append("\",\"cmt\":\"").append(cmt)
						.append("\"}");
			}
			if (subAccount.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		while(",".equals(jsonBuilder.subSequence(jsonBuilder.length()-1, jsonBuilder.length()))){
		    jsonBuilder.setLength(jsonBuilder.length()-1);			//字符集最后是","就去除
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	
	}
	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public static void getListJson(Iterator<SubAccount> subAccount,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (subAccount.hasNext()) {
			SubAccount instance = subAccount.next();
			String account_no = CommonUtil.fromNullToEmpty(instance.getAccount_no());
			String sub_no=CommonUtil.fromNullToEmpty(instance.getSub_no());
			String pact_no=CommonUtil.fromNullToEmpty(instance.getPact_no());
			String cif_no = CommonUtil.fromNullToEmpty(String.valueOf(instance.getCif_no()));
			String cif_name = CommonUtil.fromNullToEmpty(instance.getCif_name());
			String cash_amt = CommonUtil.fromNullToEmpty(instance.getCash_amt());
			String renew_amt = CommonUtil.fromNullToEmpty(instance.getRenew_amt());
			String rate=CommonUtil.fromNullToEmpty(instance.getRate());
			String prdt_no=CommonUtil.fromNullToEmpty(instance.getPrdt_no());
			String prdt_name=CommonUtil.fromNullToEmpty(instance.getPrdt_name());
			String term=CommonUtil.fromNullToEmpty(instance.getTerm());
			String payment_type=CommonUtil.fromNullToEmpty(instance.getPayment_type());
			String income_amt=CommonUtil.fromNullToEmpty(instance.getIncome_amt());
			String redeem_amt=CommonUtil.fromNullToEmpty(instance.getRedeem_amt());
			String if_wxd=CommonUtil.fromNullToEmpty(instance.getIf_wxd());
			String end_date=CommonUtil.fromNullToEmpty(instance.getEnd_date());
			String start_date=CommonUtil.fromNullToEmpty(instance.getStart_date());
			String sts = CommonUtil.fromNullToEmpty(instance .getSts());
			if(sts.equals("11"))
			{
				sts = "正常";
			}
			else if(sts.equals("21"))
			{
				sts = "正常关闭";
			}else
			{
				sts = "提前";
			}
			String open_date = CommonUtil.fromNullToEmpty(instance .getOpen_date());
			String open_op = CommonUtil.fromNullToEmpty(instance .getOpen_op());
			String close_date = CommonUtil.fromNullToEmpty(instance .getClose_date());
			String close_op=CommonUtil.fromNullToEmpty(instance .getClose_op());
			String cmt=CommonUtil.fromNullToEmpty(instance .getCmt());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").
				append(account_no).append("\",\"")
				.append(sub_no).append("\",\"").append(pact_no)
				.append("\",\"").append(cif_no).append("\",\"")
				.append(cif_name).append("\",\"")
				.append(cash_amt).append("\",\"").append(renew_amt)
				.append("\",\"").append(rate).append("\",\"")
				.append(prdt_no).append("\",\"").append(prdt_name)
				.append("\",\"").append(term).append("\",\"")
				.append(payment_type).append("\",\"")
				.append(income_amt).append("\",\"").append(redeem_amt)
				.append("\",\"").append(if_wxd).append("\",\"")
				.append(end_date).append("\",\"")
				.append(start_date).append("\",\"")
				.append(sts).append("\",\"")
				.append(open_date).append("\",\"")
				.append(open_op).append("\",\"")
				.append(close_date).append("\",\"")
				.append(close_op).append("\",\"")
				.append(cmt).append("\"]}");
			} else {
				jsonBuilder
						.append("\",\"account_no\":\"").append(account_no)
						.append("\",\"sub_no\":\"").append(sub_no)
						.append("\",\"pact_no\":\"").append(pact_no)
						.append("\",\"cif_no\":\"").append(cif_no)
						.append("\",\"cif_name\":\"").append(cif_name)
						.append("\",\"cash_amt\":\"").append(cash_amt)
						.append("\",\"renew_amt\":\"").append(renew_amt)
						.append("\",\"rate\":\"").append(rate)
						.append("\",\"prdt_no\":\"").append(prdt_no)
						.append("\",\"prdt_name\":\"").append(prdt_name)
						.append("\",\"term\":\"").append(term)
						.append("\",\"payment_type\":\"").append(payment_type)
						.append("\",\"income_amt\":\"").append(income_amt)
						.append("\",\"redeem_amt\":\"").append(redeem_amt)
						.append("\",\"if_wxd\":\"").append(if_wxd)
						.append("\",\"end_date\":\"").append(end_date)
						.append("\",\"start_date\":\"").append(start_date)
						.append("\",\"sts\":\"").append(sts)
						.append("\",\"open_date\":\"").append(open_date)
						.append("\",\"open_op\":\"").append(open_op)
						.append("\",\"close_date\":\"")	.append(close_date)
						.append("\",\"close_op\":\"") .append(close_op)
						.append("\",\"cmt\":\"").append(cmt).append("\"}");
			}
			if (subAccount.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	public String getAccountNo(Integer id) throws Exception
	{
		String accountNo = null;
		Integer CIF_NO	=	id;
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser.getScope_system(), loginUser);
		SearchResult<MainAccount> result = baseService
				.getPaginationObjects(MainAccount.class.getSimpleName(), searchCondition);
		Iterator<MainAccount> mainAccount = result.getResult().iterator();
		while(mainAccount.hasNext())
		{
			MainAccount instance = mainAccount.next();
			Integer cif_no2 = instance.getCif_no();
			if(!cif_no2.equals(CIF_NO))
			{
				continue;
			}
			 accountNo = CommonUtil.fromNullToEmpty(instance.getAccount_no());
		}
		return accountNo;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public SubAccount getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(SubAccount subAccount) {
		this.subAccount = subAccount;
	}

	public ISubAccountService getSubAccountService() {
		return subAccountService;
	}

	public void setSubAccountService(ISubAccountService subAccountService) {
		this.subAccountService = subAccountService;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}


	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public String getSub_no() {
		return sub_no;
	}

	public void setSub_no(String sub_no) {
		this.sub_no = sub_no;
	}

	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

}
