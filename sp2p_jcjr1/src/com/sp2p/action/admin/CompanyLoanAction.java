package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.service.BeVipService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.CompanyBondingService;
import com.sp2p.service.admin.CompanyLoanService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.util.DateUtil;

@SuppressWarnings( { "serial", "rawtypes"})
public class CompanyLoanAction extends BasePageAction {

	public static Log log = LogFactory.getLog(CompanyLoanAction.class);

	private CompanyLoanService companyLoanService;
	private CompanyBondingService companyBondingService;
	private List companyBondingList = new ArrayList();
	private UserService userService;
	private RelationService relationService;
	private HomeInfoSettingService homeInfoSettingService;
	private RecommendUserService recommendUserService;
	private CellPhoneService cellPhoneService;
	private BeVipService beVipService;
	//private BBSRegisterService bbsRegisterService;
	private AdminService adminService;
	private SMSInterfaceService sMsService; 
	
	
	/**
	 * 添加小贷超级管理员，判断唯一性
	 * @return
	 */
	public String findLoanCompanyAdminUserByLoanId(){
		
		JSONObject jo = new JSONObject();
		long id = Convert.strToLong(request().getParameter("loan_bonding_loanid"), 0);
		 
		try {
			Map<String,String> map = companyLoanService.findLoanCompanyAdminUserByLoanId(id);
			if(map!=null && map.size()>0){
			  jo.put("state", 1);
			}else{
			  jo.put("state", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	}
	
	/**
	 * 根据user_id查询小贷公司超级管理员
	 * @return
	 */
	public String findLoanCompanyAdminUserById(){
		JSONObject jo = new JSONObject();
		long id = Convert.strToLong(request().getParameter("userId"), 0);
		try {
			paramMap = companyLoanService.findLoanCompanyAdminUserById(id);
			jo.put("paramMap", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	}
	
    /**
     * 通过id查找小贷公司
     * @return
     */
	public String queryLoanCompanyById(){
		JSONObject jo = new JSONObject();
		long id = Convert.strToLong(request().getParameter("id"), 0);
		try {
		paramMap = companyLoanService.findLoanCompanyById(id);
		jo.put("loanCompany", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	}
	
	
	/**
	 * 更新小贷公司数据
	 * @return
	 */
	public String updateCompnayLoanData(){
        JSONObject jo = new JSONObject();
		
		try {
			
			long id  = Convert.strToLong(request().getParameter("id"), 0);
			String name = request().getParameter("loan_name");
			double total_amount = Convert.strToDouble(request().getParameter("total_amount"), 0);
			double available_total_amount =  Convert.strToDouble(request().getParameter("available_total_amount"), 0);
			double has_total_amount=  Convert.strToDouble(request().getParameter("has_total_amount"), 0);
			double insolvency_reserves_scale=  Convert.strToDouble(request().getParameter("insolvency_reserves_scale"), 0);
			Date start_time= Convert.strToDate(request().getParameter("start_time"),new Date());
			Date end_time= Convert.strToDate(request().getParameter("end_time"),new Date());
			String service_letter_path= request().getParameter("service_letter_path");
			int bonding_required= Convert.strToInt(request().getParameter("bonding_required"),0);
			int status= Convert.strToInt(request().getParameter("status"),0);
			String desc= request().getParameter("desc");
			int level= Convert.strToInt(request().getParameter("level"),0);
			double channel_cost= Convert.strToDouble(request().getParameter("channel_cost"),0);
			String loanLogo= request().getParameter("loan_logo_path_update");
			
			//whb 添加图片
			int num= Convert.strToInt(request().getParameter("num"),0);
			String data_path = "";
			String data_desc = "";
			//添加抵押物
			String mortg_path = "";
			String mortg_desc = "";
			if(num == 1){
				data_path = request().getParameter("data_path");
				data_desc = request().getParameter("data_desc");
				
				mortg_path = request().getParameter("mortg_path");
				mortg_desc = request().getParameter("mortg_desc");
			}
			//修改图片
			int up= Convert.strToInt(request().getParameter("up"),0);
			String data_update_path = "";
			String data_update_desc = "";
			String ids = "";
			//修改抵押物
			String mortg_update_path = "";
			String mortg_update_desc = "";
			String idsMortg = "";
			if(up == 1){
				data_update_path = request().getParameter("data_update_path");
				data_update_desc = request().getParameter("data_update_desc");
				ids = request().getParameter("ids");

				mortg_update_path = request().getParameter("mortg_update_path");
				mortg_update_desc = request().getParameter("mortg_update_desc");
				idsMortg = request().getParameter("idsMortg");
			}
			
			long m = companyLoanService.updateCompnayLoanData(id, name, total_amount, available_total_amount, has_total_amount, 
					insolvency_reserves_scale, start_time, end_time, service_letter_path, bonding_required, status, desc,level,channel_cost,
					mortg_path,mortg_desc,mortg_update_path,mortg_update_desc,idsMortg,
					data_path,data_desc,data_update_path,data_update_desc,ids,loanLogo);
			if(m>0){
				jo.put("state", "1");
			}else{
				jo.put("state", "0");
			}
		} catch (Exception e) {
			jo.put("status", "更新失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
	}


	/**
	 * 删除图片数据
	 * @author whb
	 * @return
	 */
	public String deleteCompnayImgData(){
		
        JSONObject jo = new JSONObject();
        //图片id
        long id= Convert.strToLong(request().getParameter("id"),0);
        
		try {
			long m = companyLoanService.deleteCompanyImg(id);
			
			if(m>0){
				jo.put("state", "1");
			}else{
				jo.put("state", "0");
			}
		} catch (Exception e) {
			jo.put("status", "删除失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
	}

	/***
	 * 初始化查询所有担保公司 显示下拉列表中
	 * @return
	 */
	public String toCompanyLoanList(){
		paramMap.put("enable", 1 + "");
		Long id = 0L;
		try {
			companyBondingList = companyBondingService.queryCompanyBondingAll(id);
			request().setAttribute("companyBondingList", companyBondingList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String toCopanyAdd(){
		return SUCCESS;
	}
	
	/***
	 * 查询小贷公司列表分页
	 * @return
	 */
	public void queryAllCompanyLoan(){
		//分页参数
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		//条件查询参数
		String loanCompanyName = request().getParameter("loanCompanyName");
		JSONObject jo = new JSONObject();
		try {
		    companyLoanService.queryCompanyLoanByConditions(pageBean, loanCompanyName);
			List<Map<String, Object>> list  = pageBean.getPage();
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("result", list); 
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "查询失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
	}
	
	/**
	 * 新增小贷公司
	 * @return
	 */
	public String addCompnayLoan(){
		JSONObject jo = new JSONObject();
		
		try {
			String name = request().getParameter("loan_name");
			double total_amount = Convert.strToDouble(request().getParameter("total_amount"), 0);
			double available_total_amount =  Convert.strToDouble(request().getParameter("available_total_amount"), 0);
			double has_total_amount=  Convert.strToDouble(request().getParameter("has_total_amount"), 0);
			double insolvency_reserves_scale=  Convert.strToDouble(request().getParameter("insolvency_reserves_scale"), 0);
			Date start_time= Convert.strToDate(request().getParameter("start_time"),new Date());
			Date end_time= Convert.strToDate(request().getParameter("end_time"),new Date());
			String service_letter_path= request().getParameter("service_letter_path");
			int bonding_required= Convert.strToInt(request().getParameter("bonding_required"),0);
			int status= Convert.strToInt(request().getParameter("status"),0);
			String desc= request().getParameter("desc");
			String loanLogo = request().getParameter("loan_logo_path");
			int level= Convert.strToInt(request().getParameter("level"),0);
			double channel_cost= Convert.strToDouble(request().getParameter("channel_cost"),0);
			
			
			//whb 添加图片资料
			int num= Convert.strToInt(request().getParameter("num"),0);
			String data_path = "";
			String data_desc = "";
			//抵押物图片路径和描述
			String mortg_path= request().getParameter("mortg_path");
			String mortg_desc= request().getParameter("mortg_desc");
			if(num >= 1){
				data_path = request().getParameter("data_path");
				data_desc = request().getParameter("data_desc");
			}
			
		
			long id = companyLoanService.addCompnayLoan(name, total_amount, available_total_amount, 
					has_total_amount, insolvency_reserves_scale, start_time, end_time, 
					service_letter_path, bonding_required, status, desc,
					level,channel_cost,mortg_path,mortg_desc,data_path,data_desc,loanLogo);
			if(id>0){
				jo.put("status", 1);
				jo.put("desc", "新增成功");
				jo.put("id", id);
			}
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "新增失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
	}
	
	/***
	 * 加载小贷公司抵押图片
	 * @return
	 */
	public String loadMortgPic(){

		JSONObject jo = new JSONObject();
		long id = Convert.strToLong(request().getParameter("id"), 0);
		try {
			List<Map<String, Object>> list = companyLoanService.loadMortgPic(id);
		if(list!=null && list.size()>0){
			jo.put("result", list);
			jo.put("state", 1);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	
	}
	
	
	/**
	 * 小贷公司超级管理员进入注册初始化页面
	 * @return
	 */
	public String toAdminUserPhoneRegInit(){
		String refferee=request().getParameter("refferee");
		String src = request().getParameter("src");
		String activity = request().getParameter("activity");
		
		if(StringUtils.isNotBlank(refferee) && session().getAttribute("refferee")==null){
			session().setAttribute("refferee", refferee);
			session().setAttribute("src", src);
			session().setAttribute("activity", activity);
		}
		session().setAttribute("DEMO", IConstants.ISDEMO);
		return SUCCESS;
	}
	
    /**
    * 小贷公司管理员发送手机短信验证
    */
    public String adminUsersendSMSNew() throws SQLException, DataException {

        try {

            // 清空验证码
            session().removeAttribute("randomCode");
            session().removeAttribute("phone");
            String phone = SqlInfusion.FilteSqlInfusion(paramMap.get("phone"));
            phone = phone.substring(10, phone.length());
            phone = Encrypt.decryptSES(phone, IConstants.PASS_KEY);
            String[] t = phone.split("/");
            phone = t[0].toString();
             
            // 随机产生4位数字
            int intCount = 0;
            intCount = (new Random()).nextInt(9999);// 最大值位9999
            if (intCount < 1000)
            intCount += 1000; // 最小值位1001
            String randomCode = intCount + "";
            // 测试--跳过验证
            if (IConstants.ISDEMO.equals("1")) {

                JSONUtils.printStr("1");
                session().setAttribute("randomCode", randomCode);
                session().setAttribute("phone", phone);

                 
            }
            // 发送短信
            Map<String, String> map = sMsService.getSMSById(1);
            String content = "尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME + ",手机验证码为:";
            
            String retCode = SMSUtil.sendMSM(map.get("Account"), map.get("Password"), content, phone, randomCode);
            if ("Sucess".equals(retCode)) {
                JSONUtils.printStr("1");
                session().setAttribute("randomCode", randomCode);
                session().setAttribute("phone", phone);
            return null;
            } else {
            JSONUtils.printStr("2");
                return null;
            }

        } catch (Exception e) {

            log.error(e);

        e.printStackTrace();
        }
        return null;

    }
	
	
	  /**
	   * 加密手机号码
	   * @return
	   * @throws Exception
	   */
	  public String adminUderphoneCheck() throws Exception {

	        JSONObject obj = new JSONObject();
	        obj.put("ret", -1 + "");
	        String phone = Convert.strToStr(request("phone"), "");
	        String code = Convert.strToStr(request("code"), "123");
	        String uid = "-1";
	        User user = (User) session().getAttribute(IConstants.SESSION_USER);
	        if (user != null) {

	            uid = user.getId() + "";

	        }
	        if (StringUtils.isBlank(phone)) {
	            JSONUtils.printObject(obj);
	            return null;

	        }
	        // 手机号码验证
	        Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$");
	        Matcher m = p.matcher(phone);
	        if (!m.matches()) {

	            obj.put("msg", "请输入正确的手机号码进行查询");
	            JSONUtils.printObject(obj);
	            return null;

	        }
	        String time = DateUtil.getTimeCurS("yyyy:MM:dd HH:mm:ss", new Date());
	        try {

	            phone = Encrypt.encryptSES(phone + "/" + time + "/" + code + "/" + uid, IConstants.PASS_KEY);
	            phone = Encrypt.MD5(phone + IConstants.PASS_KEY).substring(0, 10) + phone;
	            obj.put("ret", 1 + "");

	        obj.put("phone", phone);
	        } catch (Exception e) {
	        e.printStackTrace();
	        }
	        JSONUtils.printObject(obj);
	        return null;

	    }
	
	
	
	/**
	 * 校验手机号码
	 * @return
	 * @throws Exception
	 */
	public void adminUsercellPhoneregsinit() throws Exception{
		String cellphone  = paramMap.get("cellphone");
		String pageId = paramMap.get("pageId"); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
//		if (code == null || !_code.equals(code)) {
//			JSONUtils.printStr("2");//2为验证码错误
//		}
//		if(StringUtils.isBlank(cellphone)){
//			JSONUtils.printStr("3");//3为手机验证码为空
//		}
		try {
			Map<String,String> 	phonemap = beVipService.queryIsPhoneonUser(cellphone);
			Map<String,String> cellMap = cellPhoneService.queryCellPhone(cellphone);
			if(phonemap!= null || cellMap !=null){   //判断手机号码是都否存在
				JSONUtils.printStr("0");//0为 手机号码已存在
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		request().setAttribute("cellphone", cellphone);
		session().setAttribute("cellphone", cellphone);
		session().setAttribute("code", code);
		JSONUtils.printStr("1");//1通过校验
	}
	
	
	/***	
	 * 小额公司超级管理员  注册贷款账户.
	 * 
	 *@author 郭井超
	 *@date 2014-12-12
	 * @return
	 * @throws Exception
	 */
	public String loanAdminUserCellreginfo() throws Exception{
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		//判断手机验证码是否正确 start
		/**
		 * 判定用户是否已存在记录
		 */
		//验证手机的唯一性
		String cellphone  =  paramMap.get("cellphone");
		Map<String,String> phonemap = null;
		Map<String,String> cellMap = null;
		try{
			phonemap = beVipService.queryIsPhoneonUser(cellphone);
			cellMap = cellPhoneService.queryCellPhone(cellphone);
			
			if(phonemap!=null || cellMap!=null ){
					obj.put("mailAddress", "手机已存在");
				JSONUtils.printObject(obj);
				return null;
			}	
			
			//图片验证码
			String code = (String) session().getAttribute("cellphone_checkCode");
			String _code = paramMap.get("code").toString().trim();
			if (code == null || !_code.equals(code)) {
				obj.put("mailAddress", "图片验证码输入错误");
				JSONUtils.printObject(obj);
				return null;
			}
			
			String userName = paramMap.get("userName"); // 用户名
			if(userName.length()<2||userName.length()>20){
				obj.put("mailAddress", "18");
				JSONUtils.printObject(obj);
				return null;
			}
			if(StringUtils.isBlank(userName)){
				obj.put("mailAddress", "13");
				JSONUtils.printObject(obj);
				return null;
			}
			//验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
	       if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length()!=0) {
	    		obj.put("mailAddress", "20");
			   JSONUtils.printObject(obj);
				return null;
	        }
	          //判断第一个字符串不能使以下划线开头的
	           String fristChar = userName.substring(0,1);
	           if(fristChar.equals("_")){
	       	   obj.put("mailAddress", "21");
	 		   JSONUtils.printObject(obj);
	 			return null;
	           }
			String password = paramMap.get("password"); // 用户密码
			String md5Password =password;
			if(StringUtils.isBlank(password)){
				obj.put("mailAddress", "14");
				JSONUtils.printObject(obj);
				return null;
			}
			String confirmPassword = paramMap.get("confirmPassword"); // 用户密码
			if(StringUtils.isBlank(confirmPassword)){
				obj.put("mailAddress", "15");
				JSONUtils.printObject(obj);
				return null;
			}
		 
			// 判断密码是否一致
			if (!password.equals(confirmPassword)) {
				  obj.put("mailAddress", "1");
				  JSONUtils.printObject(obj);
					return null;
			}
			Long userId = -1L;
			Long result = userService.isExistEmailORUserName(null, userName);
			boolean isExist = adminService.isExistUserName(userName);
			if (result > 0 || isExist) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
			

//			//手机校验码
//			String vilidataNum = paramMap.get("cellcode");
//		    if(StringUtils.isBlank(vilidataNum)){
//		        obj.put("mailAddress","请填写手机校验码");
//		        JSONUtils.printObject(obj);
//		        return null;
//		    }
//		        
//		    String randomCode=null;
//			Object objec=session().getAttribute("randomCode");
//			if(objec!=null){
//					randomCode=objec.toString();
//					if(!randomCode.trim().equals(vilidataNum.trim())){
//						obj.put("mailAddress","请输入正确的手机校验码");
//			        	JSONUtils.printObject(obj);
//			        	return null;
//					}
//			}else{
//				obj.put("mailAddress","请发送手机校验码");
//		        JSONUtils.printObject(obj);
//		        return null;
//			}
			
			
			if(phonemap==null){	
					String phonecode=null;
					try {
//						Object obje=session().getAttribute("phone");
//						if(obje!=null){
//							phonecode=obje.toString();
//						}else{
//							if ("2".equals(IConstants.ISDEMO)) {
//								obj.put("mailAddress", "请输入正确的验证码");
//								JSONUtils.printObject(obj);
//								return null;
//							}
//						}
				} catch (Exception e) {
								e.printStackTrace();
				}
				
//				if(phonecode!=null){
//					if(!phonecode.trim().equals(cellphone.trim())){
//						obj.put("mailAddress", "与获取验证码手机号不一致");
//						JSONUtils.printObject(obj);
//						return null;
//					}
//								
//				}
			}
			 
			
			int isFlag =  Convert.strToInt(request().getParameter("isFlag"), 0);
//			if(isFlag==1){
//				Map<String,String> loanAdminMap = companyLoanService.findAdminUserByAdminId(admin.getId());
//				if(loanAdminMap!=null && loanAdminMap.size()>0){
//					obj.put("states", "1");
//					JSONUtils.printObject(obj);
//					return null;
//				}
//			}
			
			
		    int typelen = -1;
			Map<String,String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount();	//查询证件类型主表有多少种类型
			if(lenMap!=null&&lenMap.size()>0){
				typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
			// 调用service
				if(typelen!=-1){
					//判断是否使用了加密字符串
					if ("1".equals(IConstants.ENABLED_PASS)){
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim());
					}else{
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()+IConstants.PASS_KEY);
					}
					 
			        userId = cellPhoneService.loanCompanyAdminUserCellRegister(admin.getId(),cellphone, userName, md5Password,"","","",null,typelen,isFlag);//注册用户 和  初始化图片资料
				}
			}
			if (userId < 0) { // 注册失败
				  obj.put("mailAddress", "4");
				  JSONUtils.printObject(obj);
				  return null;
			} else {
				userService.updateSign(userId);//修改校验码
				//添加通知默认方法
				homeInfoSettingService.addNotes(userId, true, false, false);
				homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
				  //====
				obj.put("mailAddress", "注册成功");//注册成功
				JSONUtils.printObject(obj);
				 
			}
			
			//  将用户同步到BBS系统中
//			User user = new User();
//			user.setUserName(userName);
//			user.setPassword(password);
//			user.setEmail("default@163.com");
			//bbsRegisterService.doRegisterByAsynchronousMode(user);
		}catch (Exception e) {
			obj.put("mailAddress", "16");
			JSONUtils.printObject(obj);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
	/***
	 * 初始化营销账户页面
	 * @return
	 */
	public String queryYxUserRegInit(){
		return SUCCESS;
	}
	
	/**
	 * 注册营销账户
	 * @return
	 */
	public String regYxUser() throws Exception{

		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		 
		String cellphone  =  paramMap.get("cellphone");
		Map<String,String> phonemap = null;
		Map<String,String> cellMap = null;
		try{
			phonemap = beVipService.queryIsPhoneonUser(cellphone);
			cellMap = cellPhoneService.queryCellPhone(cellphone);
			
			if(phonemap!=null || cellMap!=null ){
					obj.put("mailAddress", "手机已存在");
				JSONUtils.printObject(obj);
				return null;
			}	
			
			//图片验证码
			String code = (String) session().getAttribute("cellphone_checkCode");
			String _code = paramMap.get("code").toString().trim();
			if (code == null || !_code.equals(code)) {
				obj.put("mailAddress", "图片验证码输入错误");
				JSONUtils.printObject(obj);
				return null;
			}
			
			String userName = paramMap.get("userName"); // 用户名
			if(userName.length()<2||userName.length()>20){
				obj.put("mailAddress", "18");
				JSONUtils.printObject(obj);
				return null;
			}
			if(StringUtils.isBlank(userName)){
				obj.put("mailAddress", "13");
				JSONUtils.printObject(obj);
				return null;
			}
			//验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
	       if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length()!=0) {
	    		obj.put("mailAddress", "20");
			   JSONUtils.printObject(obj);
				return null;
	        }
	          //判断第一个字符串不能使以下划线开头的
	           String fristChar = userName.substring(0,1);
	           if(fristChar.equals("_")){
	       	   obj.put("mailAddress", "21");
	 		   JSONUtils.printObject(obj);
	 			return null;
	           }
			String password = paramMap.get("password"); // 用户密码
			String md5Password =password;
			if(StringUtils.isBlank(password)){
				obj.put("mailAddress", "14");
				JSONUtils.printObject(obj);
				return null;
			}
			String confirmPassword = paramMap.get("confirmPassword"); // 用户密码
			if(StringUtils.isBlank(confirmPassword)){
				obj.put("mailAddress", "15");
				JSONUtils.printObject(obj);
				return null;
			}
		 
			// 判断密码是否一致
			if (!password.equals(confirmPassword)) {
				  obj.put("mailAddress", "1");
				  JSONUtils.printObject(obj);
					return null;
			}
			Long userId = -1L;
			Long result = userService.isExistEmailORUserName(null, userName);
			boolean isExist = adminService.isExistUserName(userName);
			if (result > 0 || isExist) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
	  
			int isFlag =  Convert.strToInt(request().getParameter("isFlag"), 0);
 			  
		    int typelen = -1;
			Map<String,String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount();	//查询证件类型主表有多少种类型
			if(lenMap!=null&&lenMap.size()>0){
				typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
			// 调用service
				if(typelen!=-1){
					//判断是否使用了加密字符串
					if ("1".equals(IConstants.ENABLED_PASS)){
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim());
					}else{
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()+IConstants.PASS_KEY);
					}
					 
			        userId = cellPhoneService.regYxUser(admin.getId(),cellphone, userName, md5Password,"","","",null,typelen,isFlag);//注册用户 和  初始化图片资料
				}
			}
			if (userId < 0) { // 注册失败
				  obj.put("mailAddress", "4");
				  JSONUtils.printObject(obj);
				  return null;
			} else {
				userService.updateSign(userId);//修改校验码
				//添加通知默认方法
				homeInfoSettingService.addNotes(userId, true, false, false);
				homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
				  //====
				obj.put("mailAddress", "注册成功");//注册成功
				JSONUtils.printObject(obj);
			}
			
		//  将用户同步到BBS系统中
//					User user = new User();
//					user.setUserName(userName);
//					user.setPassword(password);
//					user.setEmail("default@163.com");
//					bbsRegisterService.doRegisterByAsynchronousMode(user);
		}catch (Exception e) {
			obj.put("mailAddress", "16");
			JSONUtils.printObject(obj);
			e.printStackTrace();
			throw e;
		}
		return null;
	
	}
	
	
	/**
	 * 清空session中的验证码
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void adminUserRemoveSessionCode() throws SQLException, DataException {
		session().removeAttribute("randomCode");
	}
	
	
	/***
	 * 小额公司户管理员查询其所有贷款账户信息
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryAdminUserPage()throws SQLException, DataException {
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject json = new JSONObject();
		String cellphone = request().getParameter("cellphone");
		String userName = request().getParameter("username");
		try {
			companyLoanService.queryAdminUserPage(pageBean,admin.getId(), cellphone.trim(), userName.trim());
			List list = pageBean.getPage();  
			json.put("totalNum", pageBean.getTotalNum());
			json.put("result", list);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		printJson(json.toString());
	}
	 
	/**
	 * 更改小贷公司前台用户状态--是否为前台超级用户
	 */
	public void  updateAdminUser(){
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long admin_user_id = Convert.strToLong(request().getParameter("admin_user_id"), 0);
		int isFlag = Convert.strToInt(request().getParameter("isFlag"), -1);
		
		JSONObject json = new JSONObject();
		try {
			long m = companyLoanService.updateAdminUser(admin_user_id,admin.getId(), isFlag);
			json.put("status", 1);
			if(m>0){
				json.put("status", 1);
			}else{
				json.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(json.toString());
	}
	
	
	/**
	 * 变更合作机构及其从属所有用户状态
	 * @return
	 */
	public String deleteCompanyLoan(){
		
		long id = Convert.strToLong(request().getParameter("id"), -1);//小贷公司id
		int temp = Convert.strToInt(request().getParameter("temp"), -1);
		JSONObject json = new JSONObject();
		try {
			long z = companyLoanService.deleteCompanyLoan(id,temp);
			if(z>0){
				json.put("state", 1);
			}else{
				json.put("state", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(json.toString());
		return null;
	}

	/**
	 * 查询渠道费用金额初始化页面
	 * @return
	 */
	public String queryChannelCostInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询渠道费用金额列表
	 * @return
	 */
	public String findChannelCostRecodeByContions(){
		//分页参数
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		JSONObject jo = new JSONObject();
		try {
		    companyLoanService.findChannelCostRecodeByContions(pageBean);
			List<Map<String, Object>> list  = pageBean.getPage();
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
	
	/***
	 * 系统已经存在的用户t_user变更为小贷公司旗下用户
	 * @return
	 */
	public String updateAdminUserCheck(){
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String userName = request().getParameter("userName_check"); 
		String password = request().getParameter("password_check");
		String isFlag = request().getParameter("isFlag_check");
		 
		JSONObject jo = new JSONObject();
		try {
			
			if(userName==null || "".equals(userName.trim())){
				jo.put("status", "5");
	    		printJson(jo.toString());
	    		return null;
			}
			if(password==null || "".equals(password.trim())){
				jo.put("status", "6");
	    		printJson(jo.toString());
	    		return null;
			}
			
		    password = com.shove.security.Encrypt.MD5(password.trim());
		    Map userMap = companyLoanService.findUser(userName, password);
		    if(userMap!=null && userMap.size()>0){
		      long userId = Convert.strToLong((String) userMap.get("id"), 0);
//		      if("1".equals(isFlag)){
//		    	Map adminUserMap =  companyLoanService.findAdminUserByAdminId(admin.getId());
//		    	if(adminUserMap!=null && adminUserMap.size()>0){
//		    		jo.put("status", "2");//超级用户已经存在
//		    		printJson(jo.toString());
//		    		return null;
//		    	}
//		      }
		      
		      Map admin_UserMap =  companyLoanService.findLoanCompanyAdmnUser(userId);
	    		if(admin_UserMap!=null && admin_UserMap.size()>0){
	    			jo.put("status", "3");//该用户已经是小贷公司用户
		    		printJson(jo.toString());
		    		return null;
	    		}
	    		
	    	  long m = companyLoanService.addCompanyAdmnUser(admin.getId(), userId, Integer.parseInt(isFlag));
	    	  if(m>0){
	    		  jo.put("status", "4");//提交成功
	    	  }
		    }else{
		    	jo.put("status", "1");//账户不存在
		    }
		} catch (Exception e) {
			jo.put("status", "0");
			jo.put("desc", "设置失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		return null;
	}
	
	/***
	 * 查询营销账户列表
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserYxPage()throws SQLException, DataException {
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(pageNum); 
		pageBean.setPageSize(Convert.strToInt(pageSize, 10));
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject json = new JSONObject();
		String cellphone = request().getParameter("cellphone");
		String userName = request().getParameter("username");
		try {
			companyLoanService.queryUserYxPage(pageBean,cellphone.trim(), userName.trim());
			List list = pageBean.getPage();  
			json.put("totalNum", pageBean.getTotalNum());
			json.put("result", list);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		printJson(json.toString());
	}
	
	/***
	 * 更改营销账户手机号码
	 */
	public String  updateUserYxTell(){
		 long userId = Convert.strToLong(request().getParameter("userId"), 0);;
		 long id = Convert.strToLong(request().getParameter("id"), 0);;
		 String tell = request().getParameter("tell");
		
		JSONObject json = new JSONObject();
		try {
			Map<String,String> phonemap = beVipService.queryIsPhoneonUser(tell);//t_user
			Map<String,String> cellMap = cellPhoneService.queryCellPhone(tell);//_t_person
			if(phonemap!=null && phonemap.size()>0){
				json.put("status", 2);
				printJson(json.toString());
				return null;
			}
			if(cellMap!=null && cellMap.size()>0){
				json.put("status", 2);
				printJson(json.toString());
				return null;
			}
			long m = companyLoanService.updateUserYxTell(userId,id,tell);
			if(m>0){
				json.put("status", 1);
			}else{
				json.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJson(json.toString());
		return null;
	}
	
	/**
	 * 用户组注册用户
	 * @return
	 * @throws Exception
	 */
	public String userGroupRegUser() throws Exception{
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		
		//验证手机的唯一性
		String cellphone  =  paramMap.get("cellphone");
		Map<String,String> phonemap = null;
		Map<String,String> cellMap = null;
		try{
			phonemap = beVipService.queryIsPhoneonUser(cellphone);
			cellMap = cellPhoneService.queryCellPhone(cellphone);
			
			if(phonemap!=null || cellMap!=null ){
					obj.put("mailAddress", "手机已存在");
				JSONUtils.printObject(obj);
				return null;
			}	
			
			//图片验证码
			String code = (String) session().getAttribute("cellphone_checkCode");
			String _code = paramMap.get("code").toString().trim();
			if (code == null || !_code.equals(code)) {
				obj.put("mailAddress", "验证码输入错误");
				JSONUtils.printObject(obj);
				return null;
			}
			
			String userName = paramMap.get("userName"); // 用户名
			if(userName.length()<2||userName.length()>20){
				obj.put("mailAddress", "18");
				JSONUtils.printObject(obj);
				return null;
			}
			if(StringUtils.isBlank(userName)){
				obj.put("mailAddress", "13");
				JSONUtils.printObject(obj);
				return null;
			}
			//验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
	       if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length()!=0) {
	    		obj.put("mailAddress", "20");
			   JSONUtils.printObject(obj);
				return null;
	        }
	          //判断第一个字符串不能使以下划线开头的
	        String fristChar = userName.substring(0,1);
	           if(fristChar.equals("_")){
	       	   obj.put("mailAddress", "21");
	 		   JSONUtils.printObject(obj);
	 			return null;
	        }
			String password = paramMap.get("password"); // 用户密码
			String md5Password =password;
			if(StringUtils.isBlank(password)){
				obj.put("mailAddress", "14");
				JSONUtils.printObject(obj);
				return null;
			}
			String confirmPassword = paramMap.get("confirmPassword"); // 用户密码
			if(StringUtils.isBlank(confirmPassword)){
				obj.put("mailAddress", "15");
				JSONUtils.printObject(obj);
				return null;
			}
		 
			// 判断密码是否一致
			if (!password.equals(confirmPassword)) {
				  obj.put("mailAddress", "1");
				  JSONUtils.printObject(obj);
					return null;
			}
			Long userId = -1L;
			Long result = userService.isExistEmailORUserName(null, userName);
			boolean isExist = adminService.isExistUserName(userName);
			if (result > 0 || isExist) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
	
			int groupId =  Convert.strToInt(request().getParameter("groupId"), 0);
		    int typelen = -1;
			Map<String,String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount();	//查询证件类型主表有多少种类型
			if(lenMap!=null&&lenMap.size()>0){
				typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
			// 调用service
				if(typelen!=-1){
					//判断是否使用了加密字符串
					if ("1".equals(IConstants.ENABLED_PASS)){
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim());
					}else{
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()+IConstants.PASS_KEY);
					}
					 
			        userId = cellPhoneService.userGroupRegUser(admin.getId(),cellphone, userName, md5Password,"","","",null,typelen,groupId);//注册用户 和  初始化图片资料
				}
			}
			if (userId < 0) { // 注册失败
				  obj.put("mailAddress", "4");
				  JSONUtils.printObject(obj);
				  return null;
			} else {
				userService.updateSign(userId);//修改校验码
				//添加通知默认方法
				homeInfoSettingService.addNotes(userId, true, false, false);
				homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
				  //====
				obj.put("mailAddress", "注册成功");//注册成功
				JSONUtils.printObject(obj);
				 
			}
		}catch (Exception e) {
			obj.put("mailAddress", "16");
			JSONUtils.printObject(obj);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
	public CompanyLoanService getCompanyLoanService() {
		return companyLoanService;
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

	public List getCompanyBondingList() {
		return companyBondingList;
	}

	public void setCompanyBondingList(List companyBondingList) {
		this.companyBondingList = companyBondingList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RelationService getRelationService() {
		return relationService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public CellPhoneService getCellPhoneService() {
		return cellPhoneService;
	}

	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}

	public BeVipService getBeVipService() {
		return beVipService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

//	public BBSRegisterService getBbsRegisterService() {
//		return bbsRegisterService;
//	}
//
//	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
//		this.bbsRegisterService = bbsRegisterService;
//	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public SMSInterfaceService getsMsService() {
		return sMsService;
	}

	public void setsMsService(SMSInterfaceService sMsService) {
		this.sMsService = sMsService;
	}
	
	
}
