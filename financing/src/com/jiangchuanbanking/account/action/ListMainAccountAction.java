package com.jiangchuanbanking.account.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.service.IMainAccountService;
import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.select.service.impl.SelectService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.jiangchuanbanking.util.DateTimeUtil;

/**
 * 投资人列表
 * 
 */
public class ListMainAccountAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;
	private IBaseService  baseService;
	private IMainAccountService mainAccountService;
	private MainAccount mainAccount;
	private String account_no;
	private  ISelectService selectService;
    private static final String CLAZZ = MainAccount.class.getSimpleName();
    
    public  void insertData() throws ClassNotFoundException, SQLException {
		InputStream is = null;
		try {
			File target = new File("C:\\Users\\Administrator\\Desktop\\aa.xls");
			// 开始读Excel
			is = new FileInputStream(target);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet sh = rwb.getSheet(0);
			int Rows = sh.getRows();
			int Cols = sh.getColumns();
			int startRow = 1;// 从第几行开始读
			int startCol = 1;
			
			String excel_value = "";
			for (int i = startRow; i < Rows; i++) {
				Customer customer = new Customer();
				PactInfo paInfo=new PactInfo();
				BankAccount account = new BankAccount();
				String cif_name =""; 
				String pact_no="";
				String id_no="";
				String accbankname="";
				String accbankno="";
				String accbank="";
				String phone="";
				for (int j = startCol - 1; j < Cols; j++) {
					excel_value = sh.getCell(j, i).getContents();
					
					
					switch (j) {
					case 0://pact_no
						System.out.print(i + "<=======>" + excel_value);
						pact_no=excel_value.trim();
						break;
					case 1://claim_no
						System.out.print(i + "<=======>" + excel_value);
						break;
					case 2://cif_name
						System.out.print(i + "<=======>" + excel_value);
						cif_name=excel_value.trim();
						break;
					case 3://id_no
						System.out.print(i + "<=======>" + excel_value);
						id_no=excel_value.trim();
						break;
					case 4://accbankname
						System.out.print(i + "<=======>" + excel_value);
						accbankname=excel_value.trim();
						break;
					case 5://accbankno
						System.out.print(i + "<=======>" + excel_value);
						accbankno=excel_value.trim();
						break;
					case 6://accbank
						System.out.print(i + "<=======>" + excel_value);
						accbank=excel_value.trim();
						break;
					case 7://phone
						System.out.print(i + "<=======>" + excel_value);
						phone=excel_value.trim();
						break;
					}
					
				}
				List<Customer> list1=baseService.findByHQL(" from Customer where cif_name='"+cif_name+"'");
				if (list1.size()>0) {
					customer=(Customer) list1.get(0);
					customer.setId_no(id_no);
					customer.setContact_phone(phone);
					customer=(Customer) baseService.makePersistent(customer);
					account.setCustomer(customer);
					account.setBank_account_name(accbankname);
					account.setBank_account_no(accbankno);
					account.setBank_account_addr(accbank);
					account.setSts("0");
					account.setOp_no("admin");
					account.setOpen_id(3);
					account.setUpdata_time(DateTimeUtil.getNowDateString());
					baseService.makePersistent(account);
				}
				List<PactInfo>list2 = baseService.findByHQL(" from PactInfo where pact_no='"+pact_no+"'");
				if (list2.size()>0) {
					paInfo=(PactInfo) list2.get(0);
					paInfo.setAccount_name(accbankname);
					paInfo.setAccount_bank(accbank);
					paInfo.setAccount_no(accbankno);
					paInfo=(PactInfo) baseService.makePersistent(paInfo);
				}
				
				
			}
			System.out.println("信息导入完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}
	
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
	
	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String listFull() throws Exception {
		//this.insertData();
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","");
		SearchResult<MainAccount> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<MainAccount> mainIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(mainIterator, totalRecords, searchCondition, true);
		return null;
	}
	/**
	 * 在客户信息维护界面，显示客户的主账户信息
	 * @return
	 * @throws Exception
	 */
	public String listCustMainAccount() throws Exception 
	{
		String accountNo = null;
		Integer CIF_NO	=	this.getId();
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				1, loginUser);

		SearchResult<MainAccount> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<MainAccount> mainAccount = result.getResult().iterator();
		
	 	long totalRecords = result.getTotalRecords();
	 	getCustMainAccountJosn(mainAccount, totalRecords, searchCondition, false,CIF_NO);
		
	 	return null;
	}
	
	private void getCustMainAccountJosn(Iterator<MainAccount> mainAccount2,
			long totalRecords, SearchCondition searchCondition, boolean isList,
			Integer CIF_NO) throws IOException {
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (mainAccount2.hasNext()) {
			MainAccount instance = mainAccount2.next();
			
			String account_no 	 = CommonUtil.fromNullToEmpty(instance.getAccount_no());
			Integer cif_no 		 = instance.getCif_no();
			
			String cif_name		 = CommonUtil.fromNullToEmpty(instance.getCif_name());
		//	String account_type  = CommonUtil.fromNullToEmpty(instance.getAccount_type());
			String status 		 = CommonUtil.fromNullToEmpty(instance .getStatus());
			status = selectService.getOpCnName("ACCOUNT_STATUS", status);
			
			String open_date     = CommonUtil.fromNullToEmpty(instance .getOpen_date());
			String open_op 		 = CommonUtil.fromNullToEmpty(instance .getOpen_op());
			String close_date 	 = CommonUtil.fromNullToEmpty(instance .getClose_date());
			String close_op		 = CommonUtil.fromNullToEmpty(instance .getClose_op());
			String cmt			 = CommonUtil.fromNullToEmpty(instance .getCmt());
			
			if(!cif_no.equals(CIF_NO)){
				continue;
			}
			
			jsonBuilder.append("{\"account_no\":\"").append(account_no)
				.append("\",\"cif_name\":\"").append(cif_name)	
				//.append("\",\"account_type\":\"").append(account_type)
				.append("\",\"status\":\"").append(status)
				.append("\",\"open_date\":\"").append(open_date)
				.append("\",\"open_op\":\"").append(open_op)
				.append("\",\"close_date\":\"").append(close_date)
				.append("\",\"close_op\":\"").append(close_op)
				.append("\",\"cmt\":\"").append(cmt)
				
				.append("\"}");
			if (mainAccount2.hasNext()) {
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
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public  void getListJson(Iterator<MainAccount> mainAccount,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		String assignedTo = null;
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (mainAccount.hasNext()) {
			MainAccount instance = mainAccount.next();
			String account_no = CommonUtil.fromNullToEmpty(instance.getAccount_no());

		


			Integer cif_no = instance.getCif_no();
			String cif_name = CommonUtil.fromNullToEmpty(instance.getCif_name());
			//String account_type = CommonUtil.fromNullToEmpty(instance.getAccount_type());
			String status = CommonUtil.fromNullToEmpty(instance .getStatus());
				status = selectService.getOpCnName("ACCOUNT_STATUS", status);
			
			
			String open_date = CommonUtil.fromNullToEmpty(instance .getOpen_date());
			String open_op = CommonUtil.fromNullToEmpty(instance .getOpen_op());
			String close_date = CommonUtil.fromNullToEmpty(instance .getClose_date());
			String close_op=CommonUtil.fromNullToEmpty(instance .getClose_op());
			String cmt=CommonUtil.fromNullToEmpty(instance .getCmt());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(cif_no).append("\",\"")
						.append(account_no).append("\",\"").append(cif_name).append("\"") 
						.append(",\"")
						.append(status).append("\",\"").append(open_date)
						.append("\",\"").append(open_op).append("\",\"")
						.append(close_date).append("\",\"")
						.append(close_op)
						.append("\",\"").append(cmt) .append("\"]}");
			} else {
				jsonBuilder
						.append("\",\"account_no\":\"").append(account_no)
						.append("\",\"cif_no\":\"").append(cif_no)
						.append("\",\"cif_name\":\"").append(cif_name)
					//	.append("\",\"account_type\":\"").append(account_type)
						.append("\",\"status\":\"").append(status)
						.append("\",\"open_date\":\"").append(open_date)
						.append("\",\"open_op\":\"").append(open_op)
						.append("\",\"close_date\":\"")	.append(close_date)
						.append("\",\"close_op\":\"") .append(close_op)
						.append("\",\"cmt\":\"").append(cmt).append("\"}");
			}
			if (mainAccount.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}

	public MainAccount getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(MainAccount mainAccount) {
		this.mainAccount = mainAccount;
	}

	public IMainAccountService getMainAccountService() {
		return mainAccountService;
	}

	public void setMainAccountService(IMainAccountService mainAccountService) {
		this.mainAccountService = mainAccountService;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

}
