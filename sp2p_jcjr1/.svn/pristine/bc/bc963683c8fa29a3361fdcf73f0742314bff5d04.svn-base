package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.action.BasePageAction;
import com.sp2p.service.admin.CompanyBondingService;

@SuppressWarnings( { "serial", "rawtypes"})
public class CompanyBondingAction extends BasePageAction {

	public static Log log = LogFactory.getLog(CompanyBondingAction.class);

	private CompanyBondingService companyBondingService;
	private List list = new ArrayList();
	
	//findAllBondingCompanyByLoadId
	
	/**
	 * 根据小贷公司id查询所有担保公司信息
	 * @return
	 */
	public String findAllBondingCompanyByLoadId(){
		JSONObject jo = new JSONObject();
		long id = Convert.strToLong(request().getParameter("id"), 0);
		try {
			List list = companyBondingService.findAllBondingCompanyByLoadId(id);
			jo.put("result", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	}
	
	/**
	 * 更新担保公司和中间表信息
	 * @return
	 */
	public String updateBondingCompanyData(){
		JSONObject jo = new JSONObject();
		try {
			//担保公司参数
			long id = Convert.strToLong(request().getParameter("id"), 0);
			String name = request().getParameter("bonding_name");
			Date in_time = Convert.strToDate(request().getParameter("in_time"),new Date());
			int status= Convert.strToInt(request().getParameter("status_bonding"),0);
			String desc= request().getParameter("desc_bonding");
			int level= Convert.strToInt(request().getParameter("bonding_level"),0);
			
			//担保公司和小贷公司关系参数
			long loan_bonding_id = Convert.strToLong(request().getParameter("loan_bonding_id"), 0);
			Date start_time = Convert.strToDate(request().getParameter("start_time"), new Date());
			Date end_time = Convert.strToDate(request().getParameter("end_time"), new Date());
			double credit_limit = Convert.strToDouble(request().getParameter("credit_limit"), 0);
			int model_type  = Convert.strToInt(request().getParameter("model_type"), 0);
			String loan_bonding_desc = request().getParameter("desc");
			String bonding_letter_path = request().getParameter("bonding_letter_path");
				
			long m = companyBondingService.updateBondingCompanyData(id, name, in_time, status, desc, loan_bonding_id, start_time, end_time, credit_limit, model_type, loan_bonding_desc, bonding_letter_path,level);
			if(m>0){
				jo.put("state",1);
			}else{
				jo.put("state",0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			jo.put("error", e.getMessage());
		}
		printJson(jo.toString());
		return null;
	}
	
	/**
	 * 查询担保公司行列表分页
	 */
	public String queryAllCompanyBonding(){
		//分页参数
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		//条件查询参数
		String bondingCompanyName = request().getParameter("bondingCompanyName");
		
		JSONObject jo = new JSONObject();
		try {
			companyBondingService.queryCompanyBondingByConditions(pageBean, bondingCompanyName.trim());
			List list =  pageBean.getPage();
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list);
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "查询失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
	}
	
	/**
	 * 添加担保公司
	 */
	public String addCompnayBonding(){
		JSONObject jo = new JSONObject();
		try {
			//担保公司参数
			String bondingid = request().getParameter("loan_bonding_bondingid");
			String name = request().getParameter("bonding_name");
			Date create_time = Convert.strToDate(request().getParameter("create_time"),new Date());
			int status= Convert.strToInt(request().getParameter("status_bonding"),0);
			String desc= request().getParameter("desc_bonding");
			int level= Convert.strToInt(request().getParameter("bonding_level"),0);
			
			//担保公司和小贷公司关系参数
			long loan_comp_id = Convert.strToLong(request().getParameter("loan_comp_id"), 0);
			Date start_time = Convert.strToDate(request().getParameter("start_time"), new Date());
			Date end_time = Convert.strToDate(request().getParameter("end_time"), new Date());
			double credit_limit = Convert.strToDouble(request().getParameter("credit_limit"), 0);
			int model_type  = Convert.strToInt(request().getParameter("model_type"), 0);
			String loan_bonding_desc = request().getParameter("desc");
			String bonding_letter_path = request().getParameter("bonding_letter_path");
			
			long id = companyBondingService.addCompnayBonding(name, new Date(), create_time, status, desc, loan_comp_id, start_time, end_time, credit_limit, model_type, loan_bonding_desc, bonding_letter_path,level,bondingid);
			if(id>0){
				jo.put("status", 1);
				jo.put("desc", "新增成功");
				jo.put("id", id);
			}
		} catch (SQLException e) {
			jo.put("status", "0");
			jo.put("desc", "新增失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
	}
	
	/**
	 * 选择下拉列表中的担保公司=反选赋值
	 */
	public void ajaxQueryCompanyBondingById(){
		JSONObject jo = new JSONObject();
		try {
			String id = request().getParameter("bondingId");
			List<Map<String, Object>> list = companyBondingService.queryCompanyBondingAll(Long.parseLong(id));
			jo.put("result", list.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(jo.toString());
	}
	 
	public CompanyBondingService getCompanyBondingService() {
		return companyBondingService;
	}

	public void setCompanyBondingService(CompanyBondingService companyBondingService) {
		this.companyBondingService = companyBondingService;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
}
