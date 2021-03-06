package com.jiangchuanbanking.subscription.action;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
//import com.jiangchuanbanking.test.PrintTest;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;

public class ListPactInfoAction extends BaseListAction implements Printable{

	private static final long serialVersionUID = 1L;
	private ISelectService selectService;
	private static final String CLAZZ = PactInfo.class.getSimpleName();
	private IBaseService<PactInfo> baseService;
	private String pactNo ;
	private String cifName ;
	private String idNo;
	//要打印的数据#######################
			private String pact_no ;
			private String shouJu_no;
			private String cif_name ;
			private String id_no;
			private String pay_type;
			private String pay_jine; 
			private String start_time;
			private String end_time  ;
			private String bank_no  ;
			private String op_bank ;
			//###############################
			private String[] value = null;// 所要打印的数据{ "001", "002", "003"}; 
			private int[][] position = null;// 每个数据在图片中的坐标{ { 10, 50 }, { 30, 70 }, { 50, 90 }}; 

	public String listFull()throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","1=1 order by OPEN_DATE desc");
		
//		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
//				loginUser.getScope_system(), loginUser);

		SearchResult<PactInfo> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<PactInfo> pactInfo = result.getResult().iterator();
	 	long totalRecords = result.getTotalRecords();
		getListJson(pactInfo, totalRecords, searchCondition, true);	
		return null;
	}
	public String listEndFull() throws Exception
	{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		
//		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
//				loginUser.getScope_system(), loginUser);
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","end_date != null");
		SearchResult<PactInfo> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<PactInfo> pactInfo = result.getResult().iterator();
	 	long totalRecords = result.getTotalRecords();
	 	getEndListJson(pactInfo, totalRecords, searchCondition, false);
			return null;
	}
	
	public String listPactInfo() throws Exception{
		Integer CIF_NO	=	this.getId();
		
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		
//		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
//				loginUser.getScope_system(), loginUser);
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","CIF_NO="+CIF_NO);
		SearchResult<PactInfo> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<PactInfo> pactInfo = result.getResult().iterator();
	 	long totalRecords = result.getTotalRecords();
		getCustPactJosn(pactInfo, totalRecords, searchCondition, false,CIF_NO);
		return null;
	}
	
	public void getCustPactJosn(Iterator<PactInfo> pactInfo, long totalRecords,
			SearchCondition searchCondition, boolean isList,Integer CIF_NO)  throws Exception{
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (pactInfo.hasNext()) {
			PactInfo instance = pactInfo.next();
			Integer	cif_no			=	instance.getCustomer().getId();
			String	cif_name		=	CommonUtil.fromNullToEmpty(instance.getCif_name());
			String  pact_no			=	CommonUtil.fromNullToEmpty(instance.getPact_no());
			String prdt_no	    	=	CommonUtil.fromNullToEmpty(instance.getPrdt_no());
			String prdt_name		=   CommonUtil.fromNullToEmpty(instance.getPrdt_name());
			String term_range		=   CommonUtil.fromNullToEmpty(instance.getTerm_range());
			String rate				=   CommonUtil.fromNullToEmpty(instance.getRate());
			Double pact_amt			=   instance.getPact_amt();
			Double cash_amt			=	instance.getCash_amt();
			Double income_amt		=	instance.getIncome_amt();
			String payment_type	 	=	CommonUtil.fromNullToEmpty(instance.getPayment_type());
			payment_type  =  selectService.getOpCnName("PAYMENT_TYPE", payment_type);
			Date 	start_date	= 	instance.getStart_date();
			Date 	end_date			=	instance.getEnd_date();
			String start_date1="";
			String end_date1="";
			if(start_date==null){
		    	 start_date1 = "";
		    }else{
		    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				 start_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(start_date));
				System.out.println(start_date1);
				 end_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(end_date));
		    }
			String if_continue	=	CommonUtil.fromNullToEmpty(instance.getIf_continue());
			if_continue	= selectService.getOpCnName("YES_NO", if_continue);
			String sts	= CommonUtil.fromNullToEmpty(instance.getSts());
			sts	= selectService.getOpCnName("PACT_STS", sts);	
//			if(!cif_no.equals(CIF_NO)){
//				continue;
//			}
			
			jsonBuilder.append("{\"cif_no\":\"").append(cif_no)
				.append("\",\"cif_name\":\"").append(cif_name)	
				.append("\",\"pact_no\":\"").append(pact_no)
				.append("\",\"prdt_no\":\"").append(prdt_no)
				.append("\",\"prdt_name\":\"").append(prdt_name)
				.append("\",\"term_range\":\"").append(term_range)
				.append("\",\"rate\":\"").append(rate)
				.append("\",\"pact_amt\":\"").append(pact_amt)
				.append("\",\"cash_amt\":\"").append(cash_amt)
				.append("\",\"income_amt\":\"").append(income_amt)
				.append("\",\"payment_type\":\"").append(payment_type)
				.append("\",\"start_date\":\"").append(start_date1)
				.append("\",\"end_date\":\"").append(end_date1)
				.append("\",\"if_continue\":\"").append(if_continue)
				.append("\",\"sts\":\"").append(sts)
				.append("\"}");
			if (pactInfo.hasNext()) {
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

	private void getEndListJson(Iterator<PactInfo> pactInfo, long totalRecords,
			SearchCondition searchCondition, boolean isList) throws Exception
	{
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (pactInfo.hasNext())
		{
			PactInfo instance = pactInfo.next();
			Integer pactInfo_Id		=	instance.getId();
			String  pact_no			=	 CommonUtil.fromNullToEmpty(instance.getPact_no());
			String  claims_pact_no	=	CommonUtil.fromNullToEmpty(instance.getClaims_pact_no());
			Integer sub_no			=	instance.getSub_no();
			String	cif_name		=	CommonUtil.fromNullToEmpty(instance.getCif_name());
			
			String  id_no			=	CommonUtil.fromNullToEmpty(instance.getCustomer().getId_no());
			String  addr			=   CommonUtil.fromNullToEmpty(instance.getCustomer().getAddr());
			String contact 			=   CommonUtil.fromNullToEmpty(instance.getCustomer().getContact());
			String contact_tel		=   CommonUtil.fromNullToEmpty(instance.getCustomer().getContact_tel());	
			
			String if_wxd	    	=	CommonUtil.fromNullToEmpty(instance.getIf_wxd());
			if_wxd = selectService.getOpCnName("YES_NO", if_wxd);
			String prdt_name		=   CommonUtil.fromNullToEmpty(instance.getPrdt_name());
			String term_range		=   CommonUtil.fromNullToEmpty(instance.getTerm_range());
			String rate				=   CommonUtil.fromNullToEmpty(instance.getRate());
			Double pact_amt			=   instance.getPact_amt();
			Double cash_amt			=	instance.getCash_amt();
			Double income_amt		=	instance.getIncome_amt();
			String payment_type	 	=	CommonUtil.fromNullToEmpty(instance.getPayment_type());
			payment_type = selectService.getOpCnName("PAYMENT_TYPE", payment_type);
			Date 	end_date			=	instance.getEnd_date();
          /*  if(end_date==null)
            {
            	continue;
            }*/
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(end_date);
			Long endDate = end_date.getTime();
			Long shengYuDate = getWorkdayTimeInMillis(endDate);
		
			if(shengYuDate>=30){
				continue;
			}
			jsonBuilder.append("{\"cif_name\":\"").append(cif_name)
			
			.append("\",\"id_no\":\"").append(id_no)
			.append("\",\"addr\":\"").append(addr)
			.append("\",\"contact\":\"").append(contact)
			.append("\",\"contact_tel\":\"").append(contact_tel)
			
			.append("\",\"pact_no\":\"").append(pact_no)
			.append("\",\"prdt_name\":\"").append(prdt_name)	
			.append("\",\"claims_pact_no\":\"").append(claims_pact_no)
			.append("\",\"end_date\":\"").append(dateString)	
			
			.append("\"}");
			
			if (pactInfo.hasNext()) {
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
	private Long getWorkdayTimeInMillis( long end) throws ParseException
	{
	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date nowTime = new Date();
		nowTime = sdf.parse(sdf.format(new Date()));
		Long startTime = nowTime.getTime();
		Long SYTime = (end - startTime)/(24*3600*1000);
		return SYTime ;
	}
	private void getListJson(Iterator<PactInfo> pactInfo, long totalRecords,
			SearchCondition searchCondition, boolean isList) throws Exception {
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (pactInfo.hasNext()) {
	
			PactInfo instance = pactInfo.next();
			
			Integer pactInfo_Id		=	instance.getId();
			String  pact_no			=	 CommonUtil.fromNullToEmpty(instance.getPact_no());
			String  claims_pact_no	=	CommonUtil.fromNullToEmpty(instance.getClaims_pact_no());
//			Integer sub_no			=	instance.getSub_no();
			//Integer	cif_no			=	instance.getCustomer().getId();
			String	cif_name		=	CommonUtil.fromNullToEmpty(instance.getCif_name());
			String account_name 	=	CommonUtil.fromNullToEmpty(instance.getAccount_name());
			String account_no		=	CommonUtil.fromNullToEmpty(instance.getAccount_no());
			String account_bank	 	=	CommonUtil.fromNullToEmpty(instance.getAccount_bank());
			String if_wxd	    	=	CommonUtil.fromNullToEmpty(instance.getIf_wxd());
			if_wxd = selectService.getOpCnName("YES_NO", if_wxd);
			
			String prdt_no	    	=	CommonUtil.fromNullToEmpty(instance.getPrdt_no());
			String prdt_name		=   CommonUtil.fromNullToEmpty(instance.getPrdt_name());
			String term_range		=   CommonUtil.fromNullToEmpty(instance.getTerm_range());
			String rate				=   CommonUtil.fromNullToEmpty(instance.getRate());
			Double pact_amt			=   instance.getPact_amt();
			Double cash_amt			=	instance.getCash_amt();
			Double income_amt		=	instance.getIncome_amt();
			String fund_sources		=	CommonUtil.fromNullToEmpty(instance.getFund_sources());
			fund_sources = selectService.getOpCnName("FUNDS_FROM", fund_sources);
			String payment_type	 	=	CommonUtil.fromNullToEmpty(instance.getPayment_type());
			payment_type = selectService.getOpCnName("PAYMENT_TYPE", payment_type);
			Date 	start_date			= 	instance.getStart_date();
			Date 	end_date			=	instance.getEnd_date();
			String start_date1="";
			String end_date1="";
			if(start_date==null){
		    	 start_date1 = "";
		    }else{
		    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				 start_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(start_date));
				System.out.println(start_date1);
				 end_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(end_date));
		    }
			
			String if_continue		=	CommonUtil.fromNullToEmpty(instance.getIf_continue());
			if_continue = selectService.getOpCnName("YES_NO", if_continue);
			String sts				=	CommonUtil.fromNullToEmpty(instance.getSts());
			sts	= selectService.getOpCnName("PACT_STS", sts);	
			String pact_type		=	CommonUtil.fromNullToEmpty(instance.getPact_type());
			pact_type=selectService.getOpCnName("PACT_TYPE", pact_type);
			
			String if_export_ht =selectService.getOpCnName("IF_EXPORT", instance.getIf_export_ht());
			
			String if_export_sj =selectService.getOpCnName("IF_EXPORT", instance.getIf_export_sj());
			
			if (isList) {

				jsonBuilder
				.append("{\"cell\":[\"").append(pactInfo_Id)
				.append("\",\"").append(pact_no)
			  //.append("\",\"").append(claims_pact_no)
//				.append("\",\"").append(sub_no)
			//	.append("\",\"").append(cif_no)
				.append("\",\"").append(cif_name)
				.append("\",\"").append(account_name)
				.append("\",\"").append(account_no)
				.append("\",\"").append(account_bank)
				.append("\",\"").append(if_wxd)
				.append("\",\"").append(prdt_no)
				.append("\",\"").append(prdt_name)
				.append("\",\"").append(term_range)
				.append("\",\"").append(rate)
				.append("\",\"").append(pact_amt)
				.append("\",\"").append(cash_amt)
				.append("\",\"").append(income_amt)
				.append("\",\"").append(fund_sources)
				.append("\",\"").append(payment_type)
				.append("\",\"").append(start_date1)
				.append("\",\"").append(end_date1)
				.append("\",\"").append(if_continue)
				.append("\",\"").append(sts)
				.append("\",\"").append(pact_type)
				.append("\",\"").append(if_export_ht)
				.append("\",\"").append(if_export_sj)
				.append("\"]}");

			} else {
				jsonBuilder.append("{\"pactInfo_Id\":\"").append(pactInfo_Id)
					.append("\",\"pact_no\":\"").append(pact_no)
					.append("\",\"claims_pact_no\":\"").append(claims_pact_no)
				//	.append("\",\"cif_no\":\"").append(cif_no)
					.append("\",\"cif_name\":\"").append(cif_name)	
					.append("\"}");
			}
			if (pactInfo.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}

	@Override
	public String list() throws Exception {
		return null;
	}

	@Override
	protected String getEntityName() {
		return null;
	}
	
	public IBaseService<PactInfo> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<PactInfo> baseService) {
		this.baseService = baseService;
	}

	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 生成打印数据
	 * @return
	 * @throws Exception
	 */
	public String getShouJuInfomation() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","");
		SearchResult<PactInfo> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<PactInfo> pacts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		System.out.println(totalRecords);
		getShouJuJson(pacts,  searchCondition, false);
		return null;
	}
	
	private void getShouJuJson(Iterator<PactInfo> pacts, 
			SearchCondition searchCondition, boolean isList) throws IOException{
		String htNo = "";
		String khName = "";
		String idNo = "";
		String fkType = "";
		Double fkJe = null;
		String qsDate = "";
		String zzDate = "";
		String bankNo = "";
		String opBank = "";
		String dzTime = "";
		while (pacts.hasNext()) {// 此处待解决....不能全部便利数据库问题
			PactInfo instance = pacts.next();
			String pact_no = CommonUtil.fromNullToEmpty(instance.getPact_no());// 合同编号
			String cif_name = CommonUtil
					.fromNullToEmpty(instance.getCif_name());// 客户姓名
			String id_no1 = CommonUtil.fromNullToEmpty(instance.getCustomer()
					.getId_no());
			String fund_sources = CommonUtil.fromNullToEmpty(instance
					.getFund_sources());// 付款方式
			fund_sources = selectService
					.getOpCnName("FUNDS_FROM", fund_sources);
			Double cash_amt = instance.getCash_amt();// 付款金额
			String start_date1 = "";// 收益起算日期
			String end_date1 = "";// 产品终止日期
			String account_no = CommonUtil.fromNullToEmpty(instance
					.getAccount_no());// 付款人账号
			String account_bank = CommonUtil.fromNullToEmpty(instance
					.getAccount_bank());// 开户行
			Date start_date = instance.getStart_date();
			Date end_date = instance.getEnd_date();
			if (start_date == null) {
				start_date1 = "";
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				start_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(start_date));
				System.out.println(start_date1);
				end_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(end_date));
			}
			if (!pactNo.equals(pact_no)) {
				continue;
			} else {
				htNo = pact_no;
				khName = cif_name;
				idNo = id_no1;
				fkType = fund_sources;
				fkJe = cash_amt;
				qsDate = start_date1;
				zzDate = end_date1;
				bankNo = account_no;
				opBank = account_bank;
			}
		}
		String jsonData = "[{\"htNo\":\"" + htNo + "\"," + "\"khName\":\""
				+ khName + "\"," + "\"idNo\":\"" + idNo + "\","
				+ "\"fkType\":\"" + fkType + "\"," + "\"fkJe\":\"" + fkJe
				+ "\"," + "\"qsDate\":\"" + qsDate + "\"," + "\"zzDate\":\""
				+ zzDate + "\"," + "\"bankNo\":\"" + bankNo + "\","
				+ "\"opBank\":\"" + opBank + "\"" + "}]";
		String json = JSONArray.fromObject(jsonData).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		System.out.println(json.toString());
	}
	/**
	 * 打印
	 * @throws Exception
	 */
		public void printShouJu() throws Exception
		{
			 printReport(); 
		}
		
		 public  void printReport() { 
			   PrinterJob pj = PrinterJob.getPrinterJob();//创建一个打印任务
			   PageFormat pf = PrinterJob.getPrinterJob().defaultPage(); 
			   Paper paper = pf.getPaper(); 
			   // 设置页面高和宽，A4纸为 595,842 
			   double pageWidth = 595; 
			   double pageHeight = 810;  
			   paper.setSize(pageWidth, pageHeight); 
			   paper.setImageableArea(0, 0, pageWidth, pageHeight);  
			   pf.setOrientation(PageFormat.LANDSCAPE); 
		      //设置打印方向，LANDSCAPE为横向，打印方向默认为纵向
			    pf.setPaper(paper); 
//				PrintTest printTest=new PrintTest();  
//				printTest.setValue(new String []{pact_no,shouJu_no,cif_name,id_no,pay_type,pay_jine,start_time,end_time,bank_no,op_bank});  
//				printTest.setPosition(new int [][]{{140,71 },{460,71},{148,93},{148,114},{148,136},{420,136},{148,157},{148,157},{148,179},{148,200}});  
//				pj.setPrintable(printTest, pf); 
				if (pj.printDialog()) { //弹出打印对话框，打印对话框，用户可以通过它改变各种选项，例如：设置打印副本数目，页面方向，或者目标打印机。
				try {  
					pj.print();  
				} catch (PrinterException e) { 
					e.printStackTrace(); 
				} 
				} 
			} 
	
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getCifName() {
		return cifName;
	}
	public void setCifName(String cifName) {
		this.cifName = cifName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public static String getClazz() {
		return CLAZZ;
	}
	public String getPact_no() {
		return pact_no;
	}
	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}
	public String getShouJu_no() {
		return shouJu_no;
	}
	public void setShouJu_no(String shouJu_no) {
		this.shouJu_no = shouJu_no;
	}
	public String getCif_name() {
		return cif_name;
	}
	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_jine() {
		return pay_jine;
	}
	public void setPay_jine(String pay_jine) {
		this.pay_jine = pay_jine;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getOp_bank() {
		return op_bank;
	}
	public void setOp_bank(String op_bank) {
		this.op_bank = op_bank;
	}
	public String[] getValue() {
		return value;
	}
	public void setValue(String[] value) {
		this.value = value;
	}
	public int[][] getPosition() {
		return position;
	}
	public void setPosition(int[][] position) {
		this.position = position;
	}


}
