package com.sp2p.action.front;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.admin.BorrowfaService;

public class FrontBorrowfaAction extends BaseFrontAction{

	/**
	 * 
	 */
	public static Log log = LogFactory.getLog(FrontBorrowfaAction.class);
	private static final long serialVersionUID = 1L;
	
	private BorrowfaService borrowfaService;
	
	
	public String addApplyInit() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 申请后台发标
	 * addApply
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 上午10:11:43
	 */
	public String addApply() throws Exception{
		JSONObject json = new JSONObject();
		
	/*String companyname = paramMap.get("companyname");// 企业名称
	if(StringUtils.isBlank(companyname)) {
		json.put("msg", "请填写企业名称");
		JSONUtils.printObject(json);
		return null;
	}else if ("-1".equals(companyname)) {
		json.put("msg", "请填写企业名称");
		JSONUtils.printObject(json);
		return null;
	}*/
	
	/*String registnumber = paramMap.get("registnumber");// 注册号
	if (StringUtils.isBlank(registnumber)) {
		json.put("msg", "请正确填写注册号");
		JSONUtils.printObject(json);
		return null;
	}*/

	String tname = paramMap.get("tname");// 联系人
	if (StringUtils.isBlank(tname)) {
		json.put("msg", "请填写姓名");
		JSONUtils.printObject(json);
		return null;
	} else if (2 > tname.length() || 20 < tname.length()) {
		json.put("msg", "姓名的长度为不小于2和大于20");
		JSONUtils.printObject(json);
		return null;
	}

	String telphone = paramMap.get("telphone");// 居住电话
		if (StringUtils.isBlank(telphone)) {
			json.put("msg", "请正确填写手机号码");
			JSONUtils.printObject(json);
			return null;
		} else if (telphone.length() < 9 || telphone.length() > 15) {
			json.put("msg", "手机号码长度不对");
			JSONUtils.printObject(json);
			return null;
		}
	
	String cityaddress = paramMap.get("cityaddress");// 所在地
	if (StringUtils.isBlank(cityaddress)) {
		
		json.put("msg", "请填写联系地址");
		JSONUtils.printObject(json);
		return null;
	}
	 String borrowAmount =paramMap.get("borrowAmount");
		if (StringUtils.isBlank(borrowAmount)) {

			json.put("msg", "请填写借款金额");
			JSONUtils.printObject(json);
			return null;
		}else if (!borrowAmount
				.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")){
			json.put("msg", "借款金额格式不正确");
			JSONUtils.printObject(json);
			return null;
		} 
	
		String deadline = paramMap.get("deadline");// 借款期限
		if (StringUtils.isBlank(deadline)) {
			json.put("msg", "请填写借款期限");
			JSONUtils.printObject(json);
			return null;
		}else if (!deadline
				.matches("^\\+?[1-9][0-9]*$")){
			json.put("msg", "借款期限格式不正确");
			JSONUtils.printObject(json);
			return null;
		} 
	
		String borrowPurpose =paramMap.get("borrowPurpose");// 借款用途
		if (StringUtils.isBlank(borrowPurpose)) {

			json.put("msg", "请填写借款用途");
			JSONUtils.printObject(json);
			return null;
		}
	//验证码
	String vilidataNum = paramMap.get("code");
    if(StringUtils.isBlank(vilidataNum)){
    	json.put("msg","请填写验证码");
    	JSONUtils.printObject(json);
		return null;
    }
    String randomCode=null;
	Object objec=session().getAttribute("apply_checkCode");
	if(objec!=null){
		randomCode=objec.toString();
	}else{
		json.put("msg","请输入正确的验证码");
    	JSONUtils.printObject(json);
		return null;
	}
	if(randomCode!=null){
		//不区分大小写
		if(!randomCode.trim().equalsIgnoreCase(vilidataNum.trim())){
			json.put("msg","请输入正确的验证码");
        	JSONUtils.printObject(json);
			return null;
		}
	}
	long result = -1L;
	result = borrowfaService.addApply("","",tname,telphone,cityaddress,
			borrowAmount,deadline,borrowPurpose);
	if(result>0){
		session().removeAttribute("apply_checkCode");
		json.put("msg", "1");
		JSONUtils.printObject(json);
		return null;
	}else{
		json.put("msg", "申请失败");
		JSONUtils.printObject(json);
		return null;
	}
}
		// 成功
	

	public BorrowfaService getBorrowfaService() {
		return borrowfaService;
	}

	public void setBorrowfaService(BorrowfaService borrowfaService) {
		this.borrowfaService = borrowfaService;
	} 
	
	
}



