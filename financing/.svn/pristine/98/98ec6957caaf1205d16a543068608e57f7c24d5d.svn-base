package com.jiangchuanbanking.investor.action;



import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;


@SuppressWarnings("serial")
public class ListCusAction extends BaseListAction{
	
	
	private IBaseService<Customer> baseService;
	
	private ISelectService selectService;
	
	private static final String CLAZZ = Customer.class.getSimpleName();
	public IBaseService<Customer> getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService<Customer> baseService) {
		this.baseService = baseService;
	}
	@Override
	public String list() throws Exception {
     
		SearchCondition searchCondition = getSearchCondition("cif_name",UserUtil.getLoginUser(),"OPEN_ID","");
		
		SearchResult<Customer> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
	
		Iterator<Customer> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, null, false);
		return null;
	}
	
	
	public  void getListJson(Iterator<Customer> accounts,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (accounts.hasNext()) {
			Customer instance = accounts.next();
			int id = instance.getId();

			String cifName=CommonUtil.fromNullToEmpty(instance.getCif_name());
			String sex=CommonUtil.fromNullToEmpty(instance.getSex());
			sex=selectService.getOpCnName("sex", sex);
			String birth=CommonUtil.fromNullToEmpty(instance.getBirth());
			String cifType=CommonUtil.fromNullToEmpty(instance.getCif_type());
			String idType=CommonUtil.fromNullToEmpty(instance.getId_type());
			String idNo=CommonUtil.fromNullToEmpty(instance.getId_no());
			String contact=CommonUtil.fromNullToEmpty(instance.getContact());
			String contactTel=CommonUtil.fromNullToEmpty(instance.getContact_tel());
			String contactPhone=CommonUtil.fromNullToEmpty(instance.getContact_phone());
			String mail=CommonUtil.fromNullToEmpty(instance.getMail());
			String otherContact=CommonUtil.fromNullToEmpty(instance.getOther_contact());
			String addr=CommonUtil.fromNullToEmpty(instance.getAddr());
			String postcode=CommonUtil.fromNullToEmpty(instance.getPostcode());
			String openDate=CommonUtil.fromNullToEmpty(instance.getOpen_date());
			String openOp=CommonUtil.fromNullToEmpty(instance.getOpen_op());
			String cmt=CommonUtil.fromNullToEmpty(instance.getCmt());
			int openId=instance.getOpen_id();
			;
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
						.append(cifName).append("\",\"").append(sex)
						.append("\",\"").append(birth).append("\"")
				       .append(",\"").append(cifType).append("\",\"")
						.append(idType).append("\",\"").append(idNo)
						.append("\",\"").append(contact).append("\",\"")
						.append(contactTel).append("\",\"")
						.append(contactPhone).append("\",\"").append(mail)
						.append("\",\"").append(otherContact).append("\",\"")
						.append(addr).append("\",\"").append(postcode)
						.append("\",\"").append(openDate).append("\",\"")
						.append(openOp).append("\",\"")
						.append(cmt).append("\"]}");
			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
						.append("\",\"cifName\":\"").append(cifName)
						.append("\",\"sex\":\"").append(sex)
						.append("\",\"birth\":\"").append(birth)
						.append("\",\"cifType\":\"").append(cifType)
						.append("\",\"idType\":\"").append(idType)
						.append("\",\"idNo\":\"").append(idNo)
						.append("\",\"contact\":\"").append(contact)
						.append("\",\"contactTel\":\"").append(contactTel)
						.append("\",\"contactPhone\":\"").append(contactPhone)
						.append("\",\"mail\":\"").append(mail)		
						.append("\",\"addr\":\"").append(addr)
						.append("\",\"postcode\":\"").append(postcode)
						.append("\",\"openDate\":\"").append(openDate)
						.append("\",\"openOp\":\"").append(openOp)
						.append("\",\"cmt\":\"").append(cmt)
						.append("\",\"openId\":\"").append(openId)
						.append("\"}");
			}
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
	
	
	
	
	
	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}
	public ISelectService getSelectService() {
		return selectService;
	}
	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}
	
	
	
	
	
	
	
	

}
