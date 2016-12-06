package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BeVipService;
import com.sp2p.service.BonusService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.PartenersService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.util.CookieUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.CheckRegFrom;

import net.sf.json.JSONObject;
/**
 * 跳转到手机注册页面
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class CellPhoneAction extends BaseFrontAction{
	public static Log log = LogFactory.getLog(BaseFrontAction.class);
	private UserService userService;
	private RelationService relationService;
	private HomeInfoSettingService homeInfoSettingService;
	private RecommendUserService recommendUserService;
	private CellPhoneService cellPhoneService;
	private BeVipService beVipService;
	//private BBSRegisterService bbsRegisterService;
	private AdminService adminService;
	
	//广告商
	private PartenersService partenersService;
	private PartenersAction partenersAction;
	
	private BonusService bonusService;
	
	public BonusService getBonusService() {
		return bonusService;
	}

	public void setBonusService(BonusService bonusService) {
		this.bonusService = bonusService;
	}

	public PartenersAction getPartenersAction() {
		return partenersAction;
	}

	public void setPartenersAction(PartenersAction partenersAction) {
		this.partenersAction = partenersAction;
	}

	public PartenersService getPartenersService() {
		return partenersService;
	}

	public void setPartenersService(PartenersService partenersService) {
		this.partenersService = partenersService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String cellPhoneinit(){
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
	
	public String cellPhonereginit(){
		String refferee = (String)session().getAttribute("refferee");
		if(StringUtils.isNotBlank(refferee)){
			paramMap.put("refferee", refferee);
		}
		return SUCCESS;
	}
	public String cellPhoneregsinit() throws Exception{
		String cellphone  = paramMap.get("cellphone");
		String pageId = paramMap.get("pageId"); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		if (code == null || !_code.equals(code)) {
			JSONUtils.printStr("2");//2为验证码错误
			return null;
		}
		if(StringUtils.isBlank(cellphone)){
			JSONUtils.printStr("3");//3为手机验证码为空
			return null;
		}
		try {
			Map<String,String> 	phonemap = beVipService.queryIsPhoneonUser(cellphone);
			Map<String,String> cellMap = cellPhoneService.queryCellPhone(cellphone);
			if(phonemap!= null || cellMap !=null){   //判断手机号码是都否存在
				JSONUtils.printStr("5");//5为 手机号码已存在
				return null;
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("2");//
			return null;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("2");//
			return null;
		}
		request().setAttribute("cellphone", cellphone);
		session().setAttribute("cellphone", cellphone);
		JSONUtils.printStr("1");//1通过校验
		return null;
	}
	
	
	/***	
	 * 手机注册
	 * @return
	 * @throws Exception
	 */
	public String cellreginfo() throws Exception{
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
			
			if(phonemap!=null &&cellMap!=null ){
					obj.put("mailAddress", "手机已存在");
				JSONUtils.printObject(obj);
					return null;
			}	
			 if(phonemap==null){	
				String phonecode=null;
				try {
					Object obje=session().getAttribute("phone");
					if(obje!=null){
						phonecode=obje.toString();
					}else{
						if ("2".equals(IConstants.ISDEMO)) {
							obj.put("mailAddress", "请输入正确的验证码");
							JSONUtils.printObject(obj);
							return null;
						}
					}
			} catch (Exception e) {
							e.printStackTrace();
			}
			if(phonecode!=null){
				if(!phonecode.trim().equals(cellphone.trim())){
					obj.put("mailAddress", "与获取验证码手机号不一致");
					JSONUtils.printObject(obj);
					return null;
				}
							
			}
			if (!"1".equals(IConstants.ISDEMO)) {
				//验证码
				String vilidataNum = paramMap.get("cellcode");
		        if(StringUtils.isBlank(vilidataNum)){
		        	obj.put("mailAddress","请填写验证码");
		        	JSONUtils.printObject(obj);
					return null;
		        }
		        
		        String randomCode=null;
				Object objec=session().getAttribute("randomCode");
				if(objec!=null){
					randomCode=objec.toString();
				}else{
					obj.put("mailAddress","请输入正确的验证码");
		        	JSONUtils.printObject(obj);
					return null;
				}
				if(randomCode!=null){
					if(!randomCode.trim().equals(vilidataNum.trim())){
						
						obj.put("mailAddress","请输入正确的验证码");
			        	JSONUtils.printObject(obj);
						return null;
					}
					
				}
			}
				
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
			String refferee = paramMap.get("refferee");
			String src=paramMap.get("src");
			String activity = paramMap.get("activity");
			String recommendSrc = paramMap.get("recommend_src");// 推荐来源 1邀请链接、2二维码扫码、3填写推荐人
			//设备来源 
			boolean isFromMobile=CheckRegFrom.check(request()); 
			int regSrc;
			        //判断是否为移动端访问  
			        if(isFromMobile){  
			            regSrc=4;
			        } else {  
			        	regSrc=2;
			        } 
			 
			if(!StringUtils.isNotBlank(src) && session().getAttribute("src")!=null){
				src = (String)session().getAttribute("src");
			}
			
			if(!StringUtils.isNotBlank(activity) && session().getAttribute("activity")!=null){
				activity = (String)session().getAttribute("activity");
			}
			
			@SuppressWarnings("unused")
			String param = paramMap.get("param"); //邀请好友链接携带的参数
			Map<String,Object> map = null;
			long recommendUserId = -1;
			if(StringUtils.isNotBlank(refferee)){
				if(!StringUtils.isNotBlank(recommendSrc)){
					recommendSrc = "3";// 如果不填则为填写推荐人
				}
				
				Map<String,String> userIdMap = userService.queryIdByUser(refferee);//根据用户查询用户明细
				if(userIdMap != null){
					recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
				}
				
				map = relationService.isPromoter(refferee);
				if(map==null){
					refferee = null;
				}
				
				if(userIdMap==null&&map==null){
					obj.put("mailAddress", "5");
					JSONUtils.printObject(obj);
					return null;
				}
				
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
					//添加体验金
			        userId = cellPhoneService.usercellRegister(cellphone, userName, md5Password,
					refferee,src,activity,map,typelen,recommendUserId,regSrc);//注册用户 和  初始化图片资料
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
			
			//修改之前的推荐
			try {
				if(recommendUserId>0){//判断是否为空
//					List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
//					if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
//						return null;
//					}
					int point = 0;
					recommendUserService.addRecommendUser(userId, recommendUserId,Convert.strToInt(recommendSrc, -1),point);
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
			
			// 判断用户是否是广告联盟推荐过来的
			String pid = CookieUtil.getCookieValue(request(), "patener_id");
			//富爸爸
			if("1".equals(pid)){
				String uid = CookieUtil.getCookieValue(request(), "patener_uid");
				if(StringUtils.isNotBlank(uid)){
					long ret = partenersService.addParenersUser(userId, Convert.strToLong(pid, -1), uid);
					if(ret >0){
						log.info("SUCC-富爸爸推广用户成功注册："+userId);
						partenersService.addParenersMessage(5, "富爸爸推广用户成功注册", "", userId, 1, 0, 0, "", new Date());
						//清空cookie，避免单台PC重复注册
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_id"));
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_uid"));
					}else{
						log.info("ERR-富爸爸推广用户注册失败："+userId);
						partenersService.addParenersMessage(4, "富爸爸推广用户注册失败", "", userId, 1, 0, 0, "", new Date());
					}
				}
			}
			//GEO
			else if("2".equals(pid)){
				String executeid = CookieUtil.getCookieValue(request(), "patener_executeid");
				String pidGeo = CookieUtil.getCookieValue(request(), "patener_pidGeo");
				if(StringUtils.isNotBlank(executeid) && StringUtils.isNotBlank(pidGeo)){
					long ret = partenersService.addParenersUser(userId, Convert.strToLong(pid, -1), "-1");
					if(ret >0){
						log.info("SUCC-GEO推广用户成功注册："+userId);
						partenersService.addParenersMessage(5, "GEO推广用户成功注册", "", userId, 2, 0, 0, "", new Date());
						//清空cookie，避免单台PC重复注册
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_id"));
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_executeid"));
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_pidGeo"));
						//返回url给GEO
						String resultGeo = partenersAction.spliceUrl(executeid, pidGeo, userId);
						if("success".equals(resultGeo)){
							log.info("来自GEO的推广用户成功注册后返回成功："+userId);
							partenersService.addParenersMessage(6, "来自GEO的推广用户成功注册后返回成功", "", userId, 2, 0, 0, "", new Date());
						}else if("error".equals(resultGeo)){
							log.info("来自GEO的推广成功注册后返回失败："+userId);
							partenersService.addParenersMessage(6, "来自GEO的推广成功注册后返回失败", "", userId, 2, 0, 0, "", new Date());
							return null;
						}
					}else{
						log.info("ERR-GEO推广用户注册失败："+userId);
						partenersService.addParenersMessage(4, "GEO推广用户注册失败", "", userId, 2, 0, 0, "", new Date());
					}
				}
			}
			//黄金投资网
			else if("4".equals(pid)){
				String uid = CookieUtil.getCookieValue(request(), "patener_uid");
				if(StringUtils.isNotBlank(uid) && "1001".equals(uid)){
					long ret = partenersService.addParenersUser(userId, Convert.strToLong(pid, -1), "-1");
					if(ret >0){
						log.info("黄金投资网推广用户成功注册："+userId);
						partenersService.addParenersMessage(5, "黄金投资网推广用户成功注册", "", userId, 4, 0, 0, "", new Date());
						//清空cookie，避免单台PC重复注册
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_id"));
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_uid"));
					}else{
						log.info("黄金投资网推广用户注册失败："+userId);
						partenersService.addParenersMessage(4, "黄金投资网推广用户注册失败", "", userId, 4, 0, 0, "", new Date());
					}
				}
			}
			//希财网
			else if("5".equals(pid)){
				String uid = CookieUtil.getCookieValue(request(), "patener_uid");
				if(StringUtils.isNotBlank(uid) && "1002".equals(uid)){
					long ret = partenersService.addParenersUser(userId, Convert.strToLong(pid, -1), "-1");
					if(ret >0){
						log.info("希财网推广用户成功注册："+userId);
						partenersService.addParenersMessage(5, "希财网推广用户成功注册", "", userId, 5, 0, 0, "", new Date());
						//清空cookie，避免单台PC重复注册
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_id"));
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_uid"));
					}else{
						log.info("希财网推广用户注册失败："+userId);
						partenersService.addParenersMessage(4, "希财网推广用户注册失败", "", userId, 5, 0, 0, "", new Date());
					}
				}
			}
			//棕榈树
			else if("9".equals(pid)){
					long ret = partenersService.addParenersUser(userId, Convert.strToLong(pid, -1), "-1");
					if(ret >0){
						log.info("棕榈树推广用户成功注册："+userId);
						partenersService.addParenersMessage(5, "棕榈树推广用户成功注册", "", userId, 9, 0, 0, "", new Date());
						//清空cookie，避免单台PC重复注册
						CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_id"));
						 
					}else{
						log.info("棕榈树推广用户注册失败："+userId);
						partenersService.addParenersMessage(4, "棕榈树推广用户注册失败", "", userId, 9, 0, 0, "", new Date());
					}
			}
			
			//九盟互动
			else if("10".equals(pid)){
				String uid = CookieUtil.getCookieValue(request(), "patener_uid");
				long ret = partenersService.addParenersUser(userId, Convert.strToLong(pid, -1), uid);
				if(ret >0){
					log.info("/九盟互动推广用户成功注册："+userId);
					partenersService.addParenersMessage(5, "九盟互动推广用户成功注册", "", userId, 10, 0, 0, "", new Date());
					//清空cookie，避免单台PC重复注册
					CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_id"));
					CookieUtil.removeCookie(response(), CookieUtil.getCookie(request(), "patener_uid"));
				}else{
					log.info("/九盟互动推广用户注册失败："+userId);
					partenersService.addParenersMessage(4, "九盟互动推广用户注册失败", "", userId, 10, 0, 0, "", new Date());
				}
		    }
			
			else {
				log.info("用户注册成功"+userId);
				log.info("$$$$$$$$$$$$$$$$$$第三方平台唯一标识pid:{"+pid+"}$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}
			
			
			// 幸运大转盘获取红包 11-20
			
			if(DateUtil.dateToStringYYMM(new Date()).equals("2015-11")){
				String bonusAble = null;
				if(session().getAttribute("bonus_able")!=null){
					bonusAble = (String)session().getAttribute("bonus_able");
					if(bonusAble != null && !bonusAble.equals("")){
						long bres = bonusService.addBonus_6_24(userId,Convert.strToDouble(bonusAble, -1),1);
						if(bres>0){
							log.info("幸运大转盘红包发放成功！");
							session().setAttribute("bonus_able", "");
						}
					}
				}
			}

			// 幸运大转盘获取红包 11-20
			
		}catch (Exception e) {
			obj.put("mailAddress", "16");
			JSONUtils.printObject(obj);
			e.printStackTrace();
			throw e;
		}
		
		return null;
	}
	
	public String cellphoneforgetinit() throws Exception{
		String cellphone = SqlInfusion.FilteSqlInfusion(request().getParameter("cp"));
		request().setAttribute("cellphone",cellphone );
		String key = Encrypt.encryptSES(cellphone+"-"+new Date().getTime()+"",IConstants.BBS_SES_KEY);
		String sign = Encrypt.MD5(key+IConstants.BBS_SES_KEY).substring(0,10)+key;
		request().setAttribute("sign",sign);
		return SUCCESS;
	}
	/**
	 * 通过手机更改用户登录密码
	 * @return
	 * @throws IOException
	 */
	public String cellphoneforgetinfo() throws Exception{
		JSONObject obj = new JSONObject();
		String sign  = paramMap.get("cellphone");
		String mdKey = sign.substring(0,10);
		String mdValue = sign.substring(10,sign.length());
		String mdCompare = Encrypt.MD5(mdValue+IConstants.BBS_SES_KEY).substring(0,10);
		String valAll = Encrypt.decryptSES(mdValue, IConstants.BBS_SES_KEY);
		if(!mdKey.equals(mdCompare)){
		JSONUtils.printStr("签名错误");
		return null;
		}
		String[] keys = valAll.split("-");
		String cellphone = keys[0].toString();
		String dateTime = keys[1].toString();
		long curTime = new Date().getTime();
		// 当用户点击注册时间大于于1分钟
		if (curTime - Long.valueOf(dateTime) >= 60 * 1000) {
		obj.put("mailAddress", "已超时");
		JSONUtils.printObject(obj);
		return null;
		}
		
		String phonecode=null;
		try {
			Object obje=session().getAttribute("phone");
			if(obje!=null){
				phonecode=obje.toString();
			}else{
				obj.put("mailAddress", "请输入正确的验证码");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		 
		if(phonecode!=null){
			if(!phonecode.trim().equals(cellphone.trim())){
				obj.put("mailAddress", "与获取验证码手机号不一致");
				JSONUtils.printObject(obj);
				return null;
			}
			
		}
		//验证码
		String vilidataNum = paramMap.get("cellcode");
        if(StringUtils.isBlank(vilidataNum)){
        	obj.put("mailAddress","请填写验证码");
        	JSONUtils.printObject(obj);
			return null;
        }
        
        String randomCode=null;
		Object objec=session().getAttribute("randomCode");
		if(objec!=null){
			randomCode=objec.toString();
		}else{
			obj.put("mailAddress","请输入正确的验证码");
        	JSONUtils.printObject(obj);
			return null;
		}
		if(randomCode!=null){
			if(!randomCode.trim().equals(vilidataNum.trim())){
				
				obj.put("mailAddress","请输入正确的验证码");
	        	JSONUtils.printObject(obj);
				return null;
			}
			
		}
		String password = paramMap.get("password"); // 用户密码
		if(StringUtils.isBlank(password)){
			obj.put("mailAddress", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		//控制长度
		if(password.length()<6||password.length()>20){
			obj.put("mailAddress", "2");
			JSONUtils.printObject(obj);
			return null;
		}
		String confirmPassword = paramMap.get("confirmPassword"); // 用户密码
		if(StringUtils.isBlank(confirmPassword)){
			obj.put("mailAddress", "3");
			JSONUtils.printObject(obj);
			return null;
		}
		//检查用户是否存在通过手机号码]
		Map<String,String>	phonemap = null;
		
	try {
		phonemap = beVipService.queryIsPhoneonUser(cellphone);
	} catch (SQLException e1) {
		e1.printStackTrace();
	} catch (DataException e1) {
		e1.printStackTrace();
	}
   if(phonemap==null){
		obj.put("mailAddress", "6");
		JSONUtils.printObject(obj);
		return null;
   }		
		Long resutl = -1L;
		try {
			resutl = cellPhoneService.updatepasswordBycellphone(cellphone, password);
			if(resutl<=0){
				obj.put("mailAddress", "4");
				JSONUtils.printObject(obj);
				return null;
			}else{
				obj.put("mailAddress", "5");
				JSONUtils.printObject(obj);
				//User user = this.getUser();
				//bbsRegisterService.doUpdatePwdByAsynchronousMode(user.getUserName(),password, password,2);
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
//	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
//		this.bbsRegisterService = bbsRegisterService;
//	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	
	
	
	
}
