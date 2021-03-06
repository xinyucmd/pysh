package com.jiangchuanbanking.contract.vo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.redeem.domain.RedeemEntity;
import com.jiangchuanbanking.redeem.service.IRedeemService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.jiangchuanbanking.util.DateUtil;
import com.jiangchuanbanking.util.MoneyUtils;

import hirondelle.date4j.DateTime;


public class GenerateContracts {
	private IRedeemService redeemService;
	//private CifBaseBo cifBaseBo = (CifBaseBo) SourceTemplate.getSpringContextInstance().getBean("cifBaseBo");
	DecimalFormat df = new DecimalFormat("#####0.00");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 导出借款合同
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void generateJkht(String pactNo, String cifNo, String path, String packagePath, String contractName, String classify_name) throws Exception {
		Map<String, Comparable> variables = new HashMap<String, Comparable>();
		
		// 生成合同
		String filename = "";

		if ("gsjkht".equals(classify_name)) {
			filename = "gsjkht_" + pactNo;
		} else if ("grzgedyjkzyd".equals(classify_name)) {
			filename = "grzgedyjkzyd_" + pactNo;
		} else if ("hktzs".equals(classify_name)) {
			filename = "hktzs_" + pactNo;
		} else if ("fktzs".equals(classify_name)) {
			filename = "fktzs_" + pactNo;
		} else if ("jkhtzgedy".equals(classify_name)) {
			filename = "jkhtzgedy_" + pactNo;
		} else if ("wtsq".equals(classify_name)) {
			filename = "wtsq_" + pactNo;
		} else if ("csht".equals(classify_name)) {
			filename = "csht_" + pactNo;
		}

		path = path + "\\" + filename + ".doc";
		List<String> fileList = new ArrayList<String>();
		fileList.add(filename);
		GenerateContractsUtils.generateJkht(variables, path, packagePath, contractName, classify_name);
		ServletActionContext.getRequest().setAttribute("filename", fileList);
	}
	
	
	/**
	 * 生成合同
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateht(PactInfo pactInfo, String path, String packagePath,String classify_name) throws Exception {
		Map<String, Comparable> variables = new HashMap<String, Comparable>();
		String startDate = DateTimeUtil.getDateString(pactInfo.getStart_date());
		//startDate=DateUtil.addByMonDay(startDate, 0, -1, "yyyy-MM-dd") ;
		
		DateTime dTime = new DateTime(startDate);
		DateTime tmpDte = dTime. plusDays(-1);// 
		
		startDate=tmpDte.toString();
		variables.put("pactAmt", pactInfo.getPact_amt());
		
		double num=pactInfo.getPact_amt()/10000;	
		//修改合同中的万元信息保留到分也就是六位 并四舍五入20151112谷新玉
		DecimalFormat formater = new DecimalFormat();  
	    //保留几位小数  
	       formater.setMaximumFractionDigits(6);  
	       //模式  四舍五入  
	       formater.setRoundingMode(RoundingMode.UP);  
		variables.put("pactAmtWan", formater.format(num));
		variables.put("pactAmtChinese", MoneyUtils.toChinese(pactInfo.getPact_amt()+""));
		variables.put("pactNo", pactInfo.getPact_no());
		
		variables.put("dzyear", DateUtil.getYear(startDate)+"");
		variables.put("dzmonth", DateUtil.getMonth(startDate)+"");
		variables.put("dzday", DateUtil.getDay(startDate)+"");
		
		variables.put("cifName", pactInfo.getCustomer().getCif_name());
		variables.put("cifIdNo", pactInfo.getCustomer().getId_no());
		variables.put("cifAddr", pactInfo.getCustomer().getAddr());
		variables.put("cifPostcode", pactInfo.getCustomer().getPostcode());
		variables.put("cifPhone", pactInfo.getCustomer().getContact_phone());
		
		DateTime dTime1 = new DateTime(startDate);
		DateTime tmpDteThree = dTime1. plusDays(3);// 
		String dateThree = tmpDteThree.toString();
		variables.put("year3", DateUtil.getYear(dateThree)+"");
		variables.put("month3", DateUtil.getMonth(dateThree)+"");
		variables.put("day3", DateUtil.getDay(dateThree)+"");
		
		variables.put("accBank", pactInfo.getAccount_bank());
		variables.put("accBankName",pactInfo.getAccount_name());
		variables.put("accBankNo", pactInfo.getAccount_no());
		
		
		variables.put("pactContext", MoneyUtils.toChinese(pactInfo.getPact_amt().toString())+"\n"+"(￥"+MoneyUtils.moneyFormat(pactInfo.getPact_amt())+")");
		
		variables.put("bxhj", MoneyUtils.toChinese(MoneyUtils.getbxhj(pactInfo, pactInfo.getPact_amt().toString()))+"\n"+"(￥"+MoneyUtils.moneyFormat(Double.parseDouble(MoneyUtils.getbxhj(pactInfo, pactInfo.getPact_amt().toString())))+")");
		// 生成合同
		String filename = "";
		if ("lcht".equals(classify_name)) {
			filename = "lcht_" + pactInfo.getPact_no();
		}
		if ("sj".equals(classify_name)) {
			filename = "sj_" + pactInfo.getPact_no();
		}
		path = path + "\\" + filename + ".doc";
		//List<String> fileList = new ArrayList<String>();
		//fileList.add(filename);
		GenerateContractsUtils.generatedy(variables, path, packagePath, classify_name);
		//ServletActionContext.getRequest().setAttribute("filename", fileList);
		return path;
	}
	/**
	 * 生成回购申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateredeem(PactInfo pactInfo,String path, String packagePath,String classify_name,RedeemEntity redeemEntity) throws Exception {
		Map<String, Comparable> variables = new HashMap<String, Comparable>();
		String startDate = DateTimeUtil.getDateString(pactInfo.getStart_date());
		String endDate = DateTimeUtil.getDateString(pactInfo.getEnd_date());
		//startDate=DateUtil.addByMonDay(startDate, 0, -1, "yyyy-MM-dd") ;
		
		DateTime dTime = new DateTime(startDate);
		DateTime tmpDte = dTime. plusDays(-1);// 
		startDate=tmpDte.toString();
		variables.put("pactAmt", pactInfo.getPact_amt());
		
		double num=pactInfo.getPact_amt()/10000;	
		//修改合同中的万元信息保留到分也就是六位 并四舍五入20151112谷新玉
		DecimalFormat formater = new DecimalFormat();  
	    //保留几位小数  
	       formater.setMaximumFractionDigits(6);  
	       //模式  四舍五入  
	       formater.setRoundingMode(RoundingMode.UP);
	       
		variables.put("pactNo", pactInfo.getPact_no());
		
		variables.put("dzyear", DateUtil.getYear(startDate)+"");
		variables.put("dzmonth", DateUtil.getMonth(startDate)+"");
		variables.put("dzday", DateUtil.getDay(startDate)+"");
		
		variables.put("year7", DateUtil.getYear(endDate)+"");
		variables.put("month7", DateUtil.getMonth(endDate)+"");
		variables.put("day7", DateUtil.getDay(endDate)+"");
		
		
		variables.put("cifName", pactInfo.getCustomer().getCif_name());
		variables.put("cifIdNo", pactInfo.getCustomer().getId_no());
		variables.put("cifAddr", pactInfo.getCustomer().getAddr());
		variables.put("cifPhone", pactInfo.getCustomer().getContact_phone());
		
		variables.put("accBank", pactInfo.getAccount_bank());
		variables.put("accBankNo", pactInfo.getAccount_no());
		DateTime dTime3 = new DateTime(redeemEntity.getRedem_Date());
		DateTime tmpDte3 = dTime3. plusDays(-1); 
		String redemDate=tmpDte3.toString();
		variables.put("year8", DateUtil.getYear(redemDate)+"");
		variables.put("month8", DateUtil.getMonth(redemDate)+"");
		variables.put("day8", DateUtil.getDay(redemDate)+"");
		
		variables.put("sy", redeemEntity.getRedem_interest());
		//判断收益属于哪档，申请赎回日redeem.redem_date-计息日pact_info.start_date
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
		Date rated1=simpleDateFormat.parse(redemDate);
		Date rated2=simpleDateFormat.parse(startDate);
		long diff =rated1.getTime() - rated2.getTime();//这样得到的差值是微秒级别 
	    long days = diff / (1000 * 60 * 60 * 24); 
		if(days>=90&&days<180){
				variables.put("rate1","√");
				variables.put("rate2","");
				variables.put("rate3","");
		}else if(days>=180&&days<270){
			variables.put("rate1","");
			variables.put("rate2","√");
			variables.put("rate3","");
		}else if(days>=270&&days<365){
			variables.put("rate1","");
			variables.put("rate2","");
			variables.put("rate3","√");
		}
		SimpleDateFormat s=new SimpleDateFormat("yyyyMMdd");
		String s_date=s.format(pactInfo.getStart_date());
		if(Integer.parseInt(s_date.toString())>=Integer.parseInt("20150901".toString())&&Integer.parseInt(s_date.toString())<=Integer.parseInt("20160430".toString())){
			variables.put("drate1","5");
			variables.put("drate2","6");
			variables.put("drate3","8");
		}else{
			variables.put("drate1","4");
			variables.put("drate2","5");
			variables.put("drate3","5");
		}
		if("JC2000".equals(pactInfo.getPrdt_no())){
			variables.put("ratef","9.5");
		}else if("JC2010".equals(pactInfo.getPrdt_no())){
			variables.put("ratef","8");
		}
		// 生成合同
		String filename = "";
		if ("hg".equals(classify_name)) {
			filename = "hg_" + pactInfo.getPact_no();
		}
		path = path + "\\" + filename + ".doc";
		GenerateContractsUtils.generatedy(variables, path, packagePath, classify_name);
		return path;
	}


	public IRedeemService getRedeemService() {
		return redeemService;
	}


	public void setRedeemService(IRedeemService redeemService) {
		this.redeemService = redeemService;
	}


}
