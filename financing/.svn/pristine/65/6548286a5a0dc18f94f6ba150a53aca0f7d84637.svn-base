package com.jiangchuanbanking.subscription.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.account.domain.MergerInfo;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.subscription.domain.Claims;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.subscription.service.ISubscripService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.jiangchuanbanking.util.DateTimeUtil;


public class SubscripAction extends BaseListAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IBaseService baseService;

	private ISubscripService SubscripService;

	private static final String CLAZZ = ListSubscrip.class.getSimpleName();
	
	private String merPactNo;

	@Override
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, null, false);
		return null;
	}

	public String listFull() throws Exception {


		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","sts='1' and PACT_TYPE='1'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);

		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);
		return null;
	}

	public String listClaims() throws Exception {

		

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"","sts='2'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
	
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);
		return null;
	}

	public String listRenew() throws Exception {

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","   to_date(to_char(sysdate, 'YYYY/MM/DD'), 'YYYY/MM/DD') >     " +
				 		"  to_date(to_char(add_months(end_date, -1), 'YYYY/MM/DD'),  'YYYY/MM/DD')  " +
				 		 "and sts='6' and continueflg='0'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);
		return null;
	}
	
	public String listAdvanceRedem() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","   sts='6' and to_date(to_char(sysdate ,'YYYY/MM/DD'),'YYYY/MM/DD') <  end_date and continueflg!='1'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);
		return null;
	}

	public String listAccConfirm() throws Exception {

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"","sts='3'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);
		return null;
	}
	
	public String listAccConfirmed() throws Exception {

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"","sts='6'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);
		return null;
	}

	public String listAccCheck() throws Exception {

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"","sts='4'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);

		return null;

	}
	public String listAccChecked() throws Exception {

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"","sts='6'");

		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);

		return null;

	}
    
    public String listContinue() throws Exception{

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"OPEN_ID","sts<='6' and (PACT_TYPE='2' or PACT_TYPE='3') order by OPEN_DATE desc");
		
		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, searchCondition, true);

		return null;
	
    }
    
    public String listPact() throws Exception{

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"OPEN_ID","cosid='"+this.getId()+"' and IF_CONTINUE='1' and IF_ALREADY_CONTI='2' ");
		
		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getPactListJson(accounts, totalRecords, searchCondition, false);

		return null;
	
    }
    

    
    

	private void getListJson(Iterator<ListSubscrip> PactInfo,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws IOException {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (PactInfo.hasNext()) {
			ListSubscrip instance = PactInfo.next();

			
			String idNo=instance.getId_no();
			String cifName=instance.getCif_name();
			String prot=instance.getPrdt_name();
			String sex=instance.getSex();
			String contact_phone=instance.getContact_phone();
			Double pact_amt=instance.getPact_amt();
			String term_range=instance.getTerm_range();
			String rate=instance.getRate();
			String pactNo=CommonUtil.fromNullToEmpty(instance.getPact_no());
			int id=instance.getId();

			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
						.append(pactNo).append("\",\"").append(cifName)
						.append("\",\"").append(idNo).append("\",\"")
						.append(contact_phone).append("\",\"").append(prot)
						.append("\",\"").append(pact_amt).append("\",\"")
						.append(term_range).append("\",\"")
						.append(rate).append("\"]}");
			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
				        .append("\",\"pactNo\":\"").append(pactNo)
						.append("\",\"cifName\":\"").append(cifName)
						.append("\",\"idNo\":\"").append(idNo)
						.append("\",\"contact_phone\":\"")
						.append(contact_phone).append("\",\"prot\":\"")
						.append(prot).append("\",\"pact_amt\":\"")
						.append(pact_amt).append("\",\"term_range\":\"")
						.append(term_range).append("\",\"rate\":\"")
						.append(rate).append("\"}");
			}
			if (PactInfo.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}

	
	private void getPactListJson(Iterator<ListSubscrip> PactInfo,
			long totalRecords, SearchCondition searchCondition, boolean isList) throws IOException {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (PactInfo.hasNext()) {
			ListSubscrip instance = PactInfo.next();
			
			String idNo=instance.getId_no();
			String cifName=instance.getCif_name();
			String prot=instance.getPrdt_name();
			String contact_phone=instance.getContact_phone();
			Double pact_amt=instance.getPact_amt();
			String term_range=instance.getTerm_range();
			String rate=instance.getRate();
			String pactNo=CommonUtil.fromNullToEmpty(instance.getPact_no());
			String startDate=DateTimeUtil.getDateString(instance.getStart_date());
			String endDate=DateTimeUtil.getDateString(instance.getEnd_date());
			String continueAmt=instance.getContinue_amt();
			
			int id=instance.getId();
            
			List list=SubscripService.getIncomeAmt(pactNo);
						
			jsonBuilder.append("{\"id\":\"").append(id)
				        .append("\",\"pactNo\":\"").append(pactNo)
						.append("\",\"cifName\":\"").append(cifName)
						.append("\",\"idNo\":\"").append(idNo)
						.append("\",\"contact_phone\":\"").append(contact_phone)
						.append("\",\"prot\":\"").append(prot)
						.append("\",\"pact_amt\":\"").append(pact_amt)
						.append("\",\"term_range\":\"").append(term_range)
						.append("\",\"rate\":\"").append(rate)
						.append("\",\"incomeAmt\":\"").append(list.get(0))
						.append("\",\"redemAmt\":\"").append(list.get(1))
						.append("\",\"continueAmt\":\"").append(continueAmt)
						.append("\",\"startDate\":\"").append(startDate)
						.append("\",\"endDate\":\"").append(endDate)
						.append("\"}");
			
			if (PactInfo.hasNext()) {
				jsonBuilder.append(",");
				
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}

	 public String listMerPact() throws Exception{
		SubAccount sa=(SubAccount) baseService.findByHQL("from SubAccount where PACT_NO='"+this.getMerPactNo()+"'").get(0);
		List<MergerInfo> list=baseService.findByHQL("from MergerInfo where NEW_SUB_NO ='"+sa.getSub_no()+"'");
		String pactNos="";
		for (MergerInfo m : list) {
			SubAccount sub=(SubAccount) baseService.findByHQL("from SubAccount where SUB_NO='"+m.getSub_no()+"'").get(0);
			pactNos=pactNos+"'"+sub.getPact_no()+"',";
		}
		pactNos=pactNos.substring(0, pactNos.length()-1);
		
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"OPEN_ID","pact_no in ("+pactNos+")");
		
		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<ListSubscrip> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getPactListJson(accounts, totalRecords, searchCondition, false);

		
		return null;
	 }
	 
	 
	 public String listClaimsInfo() throws Exception{

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser,"","PACT_NO ='"+this.getMerPactNo()+"'");
		
		SearchResult<Claims> result = baseService.getPaginationObjects(Claims.class.getSimpleName(), searchCondition);
		Iterator<Claims> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListClaimsInfoJson(accounts, totalRecords, searchCondition, false);

		return null;
    }
 
	 
	 
	 private void getListClaimsInfoJson(Iterator<Claims> accounts,
				long totalRecords, SearchCondition searchCondition, boolean isList) throws IOException {


			StringBuilder jsonBuilder = new StringBuilder("");
			jsonBuilder
					.append(getJsonHeader(totalRecords, searchCondition, isList));
			while (accounts.hasNext()) {
				Claims instance = accounts.next();
				
				String claimsNo=instance.getClaims_no();
				String name=instance.getClaims_name();
				String amt=instance.getClaims_amt();
				String cmt=instance.getCmt();
							
				jsonBuilder.append("{\"claimsNo\":\"").append(claimsNo)
					        .append("\",\"name\":\"").append(name)
							.append("\",\"amt\":\"").append(amt)
							.append("\",\"cmt\":\"").append(cmt)
							.append("\"}");
				
				if (accounts.hasNext()) {
					jsonBuilder.append(",");
				}
			}
			jsonBuilder.append("]}");

			// Returns JSON data back to page
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(jsonBuilder.toString());
		
		 
			
		}

	public String listUndo() throws Exception{
		 Map<String, String> fieldTypeMap = new HashMap<String, String>();
		 fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		 fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		 User loginUser = UserUtil.getLoginUser();
		 SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","sts='6'");

		 SearchResult<ListSubscrip> result = baseService.getPaginationObjects(
					CLAZZ, searchCondition);

		 Iterator<ListSubscrip> accounts = result.getResult().iterator();
		 long totalRecords = result.getTotalRecords();
		 getListJson(accounts, totalRecords, searchCondition, true);
		 return null;
			 
	 }

	public IBaseService<ListSubscrip> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<ListSubscrip> baseService) {
		this.baseService = baseService;
	}

	public ISubscripService getSubscripService() {
		return SubscripService;
	}

	public void setSubscripService(ISubscripService subscripService) {
		SubscripService = subscripService;
	}
    
	

	public String getMerPactNo() {
		return merPactNo;
	}


	public void setMerPactNo(String merPactNo) {
		this.merPactNo = merPactNo;
	}

	@Override
	protected String getEntityName() {
		return PactInfo.class.getSimpleName();
	}

}
