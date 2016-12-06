package com.jiangchuanbanking.financing.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.financing.domain.BankAccount;

import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;

public class ListBankAccountAction  extends BaseListAction{
	 
	private static final long serialVersionUID = 1L;
	private static final String CLAZZ = BankAccount.class.getSimpleName();
	private IBaseService<BankAccount> baseService;
	private ISelectService selectService;
	private BankAccount bankAccount;
	public String listFull() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","sts = 0");
		
		SearchResult<BankAccount> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<BankAccount> bkAccount = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		//String STS= "启用";
		getYListJson(bkAccount, totalRecords, searchCondition, true);	
		return null;
	}
	/**
	 * 查询历史银行卡
	 * @return
	 * @throws Exception
	 */
	public String listOddFull() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","");
		/*
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser.getScope_system(), loginUser);*/
		SearchResult<BankAccount> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<BankAccount> bkAccount = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();

		getNListJson(bkAccount, totalRecords, searchCondition, false);
		return null;
	}
	/**
	 * 改变客户银行卡状态
	 * @return
	 * @throws Exception
	 */
	public  String  listStsFull() throws Exception{
	    Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","");
		
		SearchResult<BankAccount> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<BankAccount> bkAccount = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		Integer bankAccountId=this.getId();//预启用银行卡Id
		
	    BankAccount	originalBankAccount = baseService.getEntityById(BankAccount.class,bankAccountId);
	
	    Integer cif_id =  originalBankAccount.getCustomer().getId();//预启用银行卡客户Id
	   
		changeBankSts(bkAccount, totalRecords, searchCondition,cif_id, bankAccountId);
		
		getOddListJsonSts(result.getResult().iterator(), totalRecords, searchCondition, false, cif_id);
		return null;
	}
	private void getOddListJsonSts(Iterator<BankAccount> bkAccount, long totalRecords,
			SearchCondition searchCondition, boolean isList,Integer cif_id) throws Exception{
			StringBuilder jsonBuilder = new StringBuilder("");
			
			jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));//待处理...
			while (bkAccount.hasNext()) {
				BankAccount instance = bkAccount.next();
				
				Integer bank_account_id = instance.getBank_account_id();
				
				String cif_name = CommonUtil.fromNullToEmpty(instance.getCustomer().getCif_name());		
				
				String bank_account_name = CommonUtil.fromNullToEmpty(instance.getBank_account_name());
				
				String bank_account_no = CommonUtil.fromNullToEmpty(instance.getBank_account_no());
				
				String bank_account_addr = CommonUtil.fromNullToEmpty(instance.getBank_account_addr());
				
				String sts = CommonUtil.fromNullToEmpty(instance.getSts());
				sts = selectService.getOpCnName("ENABLE", sts);
				
				String updata_time = CommonUtil.fromNullToEmpty(instance.getUpdata_time());
				
				String op_no = CommonUtil.fromNullToEmpty(instance.getOp_no());
				
			 if (isList) {
					jsonBuilder
					.append("{\"cell\":[\"").append(bank_account_id)				
					.append("\",\"").append(cif_name)				
					.append("\",\"").append(bank_account_name)
					.append("\",\"").append(bank_account_no)
					.append("\",\"").append(bank_account_addr)
					.append("\",\"").append(sts)
					.append("\",\"").append(updata_time)
					.append("\",\"").append(op_no)
					.append("\"]}");
				} else {
			
					if(!cif_id.equals(instance.getCustomer().getId())){
							continue;
					}
						jsonBuilder.append("{\"bank_account_id\":\"").append(bank_account_id)
						.append("\",\"cif_name\":\"").append(cif_name)
						.append("\",\"bank_account_name\":\"").append(bank_account_name)
						.append("\",\"bank_account_no\":\"").append(bank_account_no)
						.append("\",\"bank_account_addr\":\"").append(bank_account_addr)
						.append("\",\"sts\":\"").append(sts)
						.append("\",\"updata_time\":\"").append(updata_time)
						.append("\",\"op_no\":\"").append(op_no)
						.append("\"}");
				}
				if (bkAccount.hasNext()) {
					jsonBuilder.append(",");
				}
			}
			
		    while(",".equals(jsonBuilder.subSequence(jsonBuilder.length()-1, jsonBuilder.length()))){
		    	jsonBuilder.setLength(jsonBuilder.length()-1);			//字符集最后是","就去除
		    }
			jsonBuilder.append("]}");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(jsonBuilder.toString());
		}
	private void changeBankSts(Iterator<BankAccount> bkAccount, long totalRecords,
			SearchCondition searchCondition, Integer cif_id ,Integer bankAccountId) throws Exception{
			
		while (bkAccount.hasNext()) {
				BankAccount instance = bkAccount.next();
				Integer cif_id2 = instance.getCustomer().getId();
				String bank_account_sts = CommonUtil.fromNullToEmpty(instance.getSts());
				Integer bankAccountId2 = instance.getBank_account_id();
				if(!cif_id.equals(cif_id2)){
					continue;
				}
				if(bank_account_sts.equals("0")){
					instance.setSts("1");
					getBaseService().makePersistent(instance);
				}
				if(bankAccountId2.equals(bankAccountId)){
					instance.setSts("0");
					getBaseService().makePersistent(instance);
				}
			}
	}
	/**
	 * 添加新银行卡业务
	 * @return
	 * @throws Exception
	 */
	public String listOddDeletNewBank() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser.getScope_system(), loginUser);
		
		SearchResult<BankAccount> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<BankAccount> bkAccount = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();	
		getOddBankAndDelectUseBankAccount(bkAccount, totalRecords, searchCondition);	
		return null;
	}
	
	/**
	 * 1.客户现有有效银行卡账户状态设置为无效
	 * 
	 */
	private void getOddBankAndDelectUseBankAccount(Iterator<BankAccount> bkAccount, long totalRecords,
			SearchCondition searchCondition) throws Exception{
		
		while (bkAccount.hasNext()) {//客户现有有效银行卡账户状态设置为无效
			BankAccount instance = bkAccount.next();
			Integer cif_id = instance.getCustomer().getId();
			String bank_account_sts = CommonUtil.fromNullToEmpty(instance.getSts());
			
			if(!this.getId().equals(cif_id)){
				continue;
			}
			if(bank_account_sts.equals("0")){
				instance.setSts("1");
				getBaseService().makePersistent(instance);
			}
		}
	}

	/**
	 * 1.查询所有用户有效的银行卡账户
	 * 2.查询某一用户历史银行卡账户
	 * @param bkAccount
	 * @param totalRecords
	 * @param searchCondition
	 * @param isList
	 * @param STS
	 * @throws Exception
	 */
	private void getYListJson(Iterator<BankAccount> bkAccount, long totalRecords,
		SearchCondition searchCondition, boolean isList) throws Exception{
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));//待处理...
		while (bkAccount.hasNext()) {
			BankAccount instance = bkAccount.next();
			
			Integer bank_account_id = instance.getBank_account_id();
			
			String cif_name = CommonUtil.fromNullToEmpty(instance.getCustomer().getCif_name());		
			
			String bank_account_name = CommonUtil.fromNullToEmpty(instance.getBank_account_name());
			
			String bank_account_no = CommonUtil.fromNullToEmpty(instance.getBank_account_no());
			
			String bank_account_addr = CommonUtil.fromNullToEmpty(instance.getBank_account_addr());
			
			String sts = CommonUtil.fromNullToEmpty(instance.getSts());
			sts	= selectService.getOpCnName("ENABLE", sts);	
			
			String updata_time = CommonUtil.fromNullToEmpty(instance.getUpdata_time());
			
			String op_no = CommonUtil.fromNullToEmpty(instance.getOp_no());
			
		 if (isList) {
				jsonBuilder
				.append("{\"cell\":[\"").append(bank_account_id)				
				.append("\",\"").append(cif_name)				
				.append("\",\"").append(bank_account_name)
				.append("\",\"").append(bank_account_no)
				.append("\",\"").append(bank_account_addr)
				.append("\",\"").append(sts)
				.append("\",\"").append(updata_time)
				.append("\",\"").append(op_no)
				.append("\"]}");
			} else {
				if(!this.getId().equals(instance.getCustomer().getId())){
						continue;
				}
					jsonBuilder.append("{\"bank_account_id\":\"").append(bank_account_id)
					.append("\",\"cif_name\":\"").append(cif_name)
					.append("\",\"bank_account_name\":\"").append(bank_account_name)
					.append("\",\"bank_account_no\":\"").append(bank_account_no)
					.append("\",\"bank_account_addr\":\"").append(bank_account_addr)
					.append("\",\"sts\":\"").append(sts)
					.append("\",\"updata_time\":\"").append(updata_time)
					.append("\",\"op_no\":\"").append(op_no)
					.append("\"}");
			}
			if (bkAccount.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		
	    while(",".equals(jsonBuilder.subSequence(jsonBuilder.length()-1, jsonBuilder.length()))){
	    	jsonBuilder.setLength(jsonBuilder.length()-1);			//字符集最后是","就去除
	    }
	 
		jsonBuilder.append("]}");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	private void getNListJson(Iterator<BankAccount> bkAccount, long totalRecords,
			SearchCondition searchCondition, boolean isList) throws Exception{
			StringBuilder jsonBuilder = new StringBuilder("");
			jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));//待处理...
			while (bkAccount.hasNext()) {
				BankAccount instance = bkAccount.next();
				
				Integer bank_account_id = instance.getBank_account_id();
				
				String cif_name = CommonUtil.fromNullToEmpty(instance.getCustomer().getCif_name());		
				
				String bank_account_name = CommonUtil.fromNullToEmpty(instance.getBank_account_name());
				
				String bank_account_no = CommonUtil.fromNullToEmpty(instance.getBank_account_no());
				
				String bank_account_addr = CommonUtil.fromNullToEmpty(instance.getBank_account_addr());
				
				String sts = CommonUtil.fromNullToEmpty(instance.getSts());
				sts	= selectService.getOpCnName("ENABLE", sts);	
				
				String updata_time = CommonUtil.fromNullToEmpty(instance.getUpdata_time());
				String op_no = CommonUtil.fromNullToEmpty(instance.getOp_no());
				
			 if (isList) {
					jsonBuilder
					.append("{\"cell\":[\"").append(bank_account_id)				
					.append("\",\"").append(cif_name)				
					.append("\",\"").append(bank_account_name)
					.append("\",\"").append(bank_account_no)
					.append("\",\"").append(bank_account_addr)
					.append("\",\"").append(sts)
					.append("\",\"").append(updata_time)
					.append("\",\"").append(op_no)
					.append("\"]}");
				} else {
					if(!this.getId().equals(instance.getCustomer().getId())){
							continue;
					}
						jsonBuilder.append("{\"bank_account_id\":\"").append(bank_account_id)
						.append("\",\"cif_name\":\"").append(cif_name)
						.append("\",\"bank_account_name\":\"").append(bank_account_name)
						.append("\",\"bank_account_no\":\"").append(bank_account_no)
						.append("\",\"bank_account_addr\":\"").append(bank_account_addr)
						.append("\",\"sts\":\"").append(sts)
						.append("\",\"updata_time\":\"").append(updata_time)
						.append("\",\"op_no\":\"").append(op_no)
						.append("\"}");
				}
				if (bkAccount.hasNext()) {
					jsonBuilder.append(",");
				}
			}
			
		    while(",".equals(jsonBuilder.subSequence(jsonBuilder.length()-1, jsonBuilder.length()))){
		    	jsonBuilder.setLength(jsonBuilder.length()-1);			//字符集最后是","就去除
		    }
		 
			jsonBuilder.append("]}");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(jsonBuilder.toString());
		}
	
	/**
	 * delete BankAccount information
	 * @return
	 * @throws Exception
	 */
	 public String delete() throws Exception {
	        UserUtil.permissionCheck("delete_system");
	        Integer id=Integer.parseInt(this.getSeleteIDs());
	        bankAccount = baseService.getEntityById(BankAccount.class,id); 
	        bankAccount.setSts ("1");
	        bankAccount = getBaseService().makePersistent(bankAccount);
	        return SUCCESS;
	    }
	
	@Override
	public String list() throws Exception {
		SearchCondition searchCondition = getSearchCondition("bank_account_name",UserUtil.getLoginUser(),"OPEN_ID","");
//		 SearchCondition searchCondition = getSearchCondition();
	        SearchResult<BankAccount> result = baseService.getPaginationObjects(CLAZZ,
	                searchCondition);
	        Iterator<BankAccount> bankAccounts = result.getResult().iterator();
	        long totalRecords = result.getTotalRecords();
	      //  String STS="";
	        getYListJson(bankAccounts, totalRecords, null, false);
	        return null;
	}
	@Override
	protected String getEntityName() {
	
		return BankAccount.class.getName();
	}
	
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	public IBaseService<BankAccount> getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService<BankAccount> baseService) {
		this.baseService = baseService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getClazz() {
		return CLAZZ;
	}
	public ISelectService getSelectService() {
		return selectService;
	}
	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

}
