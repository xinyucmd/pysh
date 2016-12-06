package com.sp2p.action.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.service.admin.CompanyBondingService;
import com.sp2p.service.admin.CompanyLoanBondingService;
import com.sp2p.service.admin.CompanyLoanService;

/**
 * 小额公司和担保公司 关系
 * @author Administrator
 *
 */
@SuppressWarnings( { "serial", "rawtypes"})
public class CompnayLoanBondingAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(CompanyLoanAction.class);

	private CompanyLoanService companyLoanService;
	private CompanyBondingService companyBondingService;
	private CompanyLoanBondingService companyLoanBondingService;
	public CompanyLoanService getCompanyLoanService() {
		return companyLoanService;
	}
	 
	
	/**
	 * 新增小额公司和贷款公司 关系信息
	 */
	public void addCompnayLoanBonding(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		JSONObject jo = new JSONObject();
		 
		try {
			 
			long loan_comp_id = Convert.strToLong(request().getParameter("loan_comp_id"), 0);
			long bonding_comp_id = Convert.strToLong(request().getParameter("bonding_comp_id"), 0);
			Date start_time = Convert.strToDate(request().getParameter("start_time"), new Date());
			Date end_time = Convert.strToDate(request().getParameter("end_time"), new Date());
			Date create_time = sdf.parse(sdf.format(new Date()));
			double credit_limit = Convert.strToDouble(request().getParameter("credit_limit"), 0);
			int model_type  = Convert.strToInt(request().getParameter("model_type"), 0);
			String desc = request().getParameter("desc");
			String bonding_letter_path = request().getParameter("bonding_letter_path");
			long id = companyLoanBondingService.addCompanyLoanBonding(loan_comp_id, bonding_comp_id, start_time, end_time, credit_limit, model_type, create_time, desc,bonding_letter_path);
		
		    if(id>0){
		    	jo.put("status", 1);
				jo.put("desc", "新增成功");
				jo.put("id", id);
		    }
		} catch (Exception e) {
			jo.put("status", 0);
			jo.put("desc", "新增失败");
			e.printStackTrace();
		}
		 
		printJson(jo.toString());
	 
	}
	
	/**
	 * 分页查询小贷公司和担保公司关系
	 */
	public void queryCompnayLoanBondingPage(){
		//分页参数
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		//条件查询参数
		String loanCompanyNames = request().getParameter("loanCompanyNames").trim();
		String bondingCompanyNames = request().getParameter("bondingCompanyNames").trim();
		
		JSONObject jo = new JSONObject();
		try {
			companyLoanBondingService.queryCompnayLoanBondingPage(pageBean,loanCompanyNames,bondingCompanyNames);
			List list = pageBean.getPage();  
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		printJson(jo.toString());
	}
	
	
	
	

	
	
	
	public void setCompanyLoanService(CompanyLoanService companyLoanService) {
		this.companyLoanService = companyLoanService;
	}
	public CompanyBondingService getCompanyBondingService() {
		return companyBondingService;
	}
	public void setCompanyBondingService(CompanyBondingService companyBondingService) {
		this.companyBondingService = companyBondingService;
	}
	public CompanyLoanBondingService getCompanyLoanBondingService() {
		return companyLoanBondingService;
	}
	public void setCompanyLoanBondingService(
			CompanyLoanBondingService companyLoanBondingService) {
		this.companyLoanBondingService = companyLoanBondingService;
	}

}
