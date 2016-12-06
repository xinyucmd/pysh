package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.web.action.CommonAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.service.BonusService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.RecommendBrokerageListService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.service.admin.SendSMSService;
import com.sp2p.service.brokerage.BrokerageIDRecognition;
import com.sp2p.util.DateUtil;
import com.sp2p.util.CheckRegFrom;
import com.sp2p.util.isKeywords;

public class UserAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(UserAppAction.class);
	private static final long serialVersionUID = -9011135946028616456L;

	private UserService userService;
	private UserIntegralService userIntegralService;
	private HomeInfoSettingService homeInfoSettingService;
	private SMSInterfaceService sMSInterfaceService;
	
	private BrokerageIDRecognition brokerageIDRecognition;
	
	private RecommendBrokerageListService recommendBrokerageListService;
	private BonusService bonusService;
	private RecommendUserService recommendUserService;

	private SendSMSService sendSMSService;
	
	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public RecommendBrokerageListService getRecommendBrokerageListService() {
		return recommendBrokerageListService;
	}

	public void setRecommendBrokerageListService(
			RecommendBrokerageListService recommendBrokerageListService) {
		this.recommendBrokerageListService = recommendBrokerageListService;
	}

	public BrokerageIDRecognition getBrokerageIDRecognition() {
		return brokerageIDRecognition;
	}

	public void setBrokerageIDRecognition(
			BrokerageIDRecognition brokerageIDRecognition) {
		this.brokerageIDRecognition = brokerageIDRecognition;
	}

	public String login() throws IOException {

		// 验证签名
		Map<String, String> jsonMap = new HashMap<String, String>();
		String auth = request("auth");
		String info = request("info");
		String sign = request("sign");
		String tmp = Encrypt.MD5(auth + info + IConstants.PASS_KEY, "gb2312")
				.toUpperCase();
		log.info("sign:"+sign);
		log.info("tmp:"+tmp);
//		if (!tmp.equals(sign)) {
//			jsonMap.put("error", "-2");
//			jsonMap.put("msg", "签名不正确");
//			JSONUtils.printObject(jsonMap);
//			return null;
//		}

//		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		// Map<String, String> jsonMap = new HashMap<String, String>();
//		User user = new User();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = String.valueOf(appInfoMap.get("name"));
			String pwd = appInfoMap.get("pwd");
			//区分安卓
			String flag = appInfoMap.get("flag");
//whb去掉解密
			//ios不解密
			if("android".equals(flag)){
				name = com.shove.security.Encrypt.decrypt3DES(name,
						IConstants.PASS_KEY);
			}else {
				if(name.startsWith("U")){
					name = name.replace("U", "\\u");
					name = isKeywords.decodeUnicode(name);
				}
			}
//			pwd = com.shove.security.Encrypt.decrypt3DES(pwd,
//					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)){
				pwd = com.shove.security.Encrypt.MD5(pwd.trim());
			}else{
				pwd = com.shove.security.Encrypt.MD5(pwd.trim()+IConstants.PASS_KEY);
			}

			// 查找数据库对象中的enable属性
			if (StringUtils.isBlank(name)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户名或手机号为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (StringUtils.isBlank(pwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			Thread.sleep(500);
			Map<String, String> userMap = userService.queryUserByUserAndPwd(
					name, pwd);

			if (null == userMap || userMap.isEmpty()) {

				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户名或密码错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (userMap.get("isLoginLimit").equals("2")) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "该用户已被限制登录，请于三小时以后登录！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!userService
					.checkSign(Convert.strToLong(userMap.get("id"), -1))) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "*该用户账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				userMap = null;
			}
			long userId = Convert.strToLong(userMap.get("id"), -1L);
			// 令牌
			jsonMap.put("token", creatToken(userId + ""));

			// 刷新登录计数
			userService.loginCountReFresh(userId);

			// 用户登录日志插入
			if (userId > 0) {
				userIntegralService.addUserLoginLog(userId);
			}

			jsonMap.putAll(userMap);

			// 用户登录分数
			Map<String, String> logmap = null;
			Map<String, String> usermap = null;
			Integer preScore = null;
			int LongCount = 1;
			int score = 1;
			if (userId > 0) {
				logmap = userIntegralService.queryUserLoginLong(userId);
				usermap = userService.queryUserById(userId);
				if (logmap.size() > 0 && logmap != null) {
					preScore = Convert.strToInt(usermap.get("rating"), 0);
					LongCount = Convert.strToInt(logmap.get("cl"), 0);
					userIntegralService.UpdateLoginRating(userId, score,
							preScore, LongCount);

				}
			}
			jsonMap.put("realName", "");
			Map<String, String> personMap = userService.queryPersonById(userId);
			if (personMap != null && personMap.size() > 0) {
				jsonMap.put("realName", personMap.get("realName"));
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "登录成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}
		return null;
	}

	public String registerForM()throws IOException{
		
		log.info("移动端注册开始....");
		Map<String, String> jsonMap = new HashMap<String, String>();
		String auth = request("auth");
		String info = request("info");
		String sign = request("sign");
		String tmp = Encrypt.MD5(auth + info + IConstants.PASS_KEY, "gb2312")
				.toUpperCase();
		log.info("sign:"+sign);
		log.info("tmp:"+tmp);

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = String.valueOf(appInfoMap.get("name"));
			String pwd = appInfoMap.get("pwd");
			String refferee = appInfoMap.get("refferee");
			String recommend_src = appInfoMap.get("recommend_src");
			int point = Convert.strToInt(appInfoMap.get("point"), 0);
			 
			//区分安卓
			String flag = appInfoMap.get("flag");
			//ios不解密
			if("android".equals(flag)){
				name = com.shove.security.Encrypt.decrypt3DES(name,
						IConstants.PASS_KEY).trim();
			}else {
				name = name.toLowerCase().replace("u", "\\u");
				name = isKeywords.decodeUnicode(name);
			}

			if (StringUtils.isBlank(name)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户名不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			String cellPhone = appInfoMap.get("cellPhone");
			//ios不解密
			if("android".equals(flag)){
				cellPhone = com.shove.security.Encrypt.decrypt3DES(cellPhone,
						IConstants.PASS_KEY).trim();
			}
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "手机号码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			 
			if(!cellPhone.matches("^[1][3|4|5|7|8|][0-9]{9}$")){
				jsonMap.put("error", "3");
				jsonMap.put("msg", "手机号码格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (StringUtils.isBlank(pwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			long str = userService.queryUserIdByPhone(cellPhone);
			if (str > 0) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "该手机号已注册！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			 String code = appInfoMap.get("code");
			 if(StringUtils.isBlank(code)){
				 jsonMap.put("error", "4");
				 jsonMap.put("msg", "验证码不能为空");
				 JSONUtils.printObject(jsonMap);
				 return null;
			 }

			String pageId = appInfoMap.get("pageId");
			String pic_mCode = appInfoMap.get("pic_mCode");
			String pageId_code = (String) session().getAttribute(pageId + "_checkCode");
			if(!pageId_code.toLowerCase().equals(pic_mCode.toLowerCase())){
				 jsonMap.put("msg", "图片验证码错误");
				 JSONUtils.printObject(jsonMap);
				 return null;
			}
			 
			 if(IConstants.ISDEMO.equals("2")){
				 String randomCode = appInfoMap.get("randomCode");
				 if(StringUtils.isBlank(randomCode)){
					 jsonMap.put("error", "4");
					 jsonMap.put("msg", "你还没有获取手机验证码");
					 JSONUtils.printObject(jsonMap);
					 return null;
				 }
				 randomCode = Encrypt.decryptSES(randomCode,AlipayConfig.ses_key);
				 if(!code.equals(randomCode)){
					 jsonMap.put("error", "5");
					 jsonMap.put("msg", "手机验证码不正确");
					 JSONUtils.printObject(jsonMap);
					 return null;
				 }
				 String recivePhone =
					 Encrypt.decryptSES(appInfoMap.get("recivePhone"),AlipayConfig.ses_key);
					 if(!cellPhone.equals(recivePhone)){
						 jsonMap.put("error", "6");
						 jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
						 JSONUtils.printObject(jsonMap);
						 return null;
					 }
			 }
			
			 
			// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
			if (name.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "用户名包含特殊字符");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if ("1".equals(IConstants.ENABLED_PASS)){
				pwd = com.shove.security.Encrypt.MD5(pwd.trim());
			}else{
				pwd = com.shove.security.Encrypt.MD5(pwd.trim()+IConstants.PASS_KEY);
			}

			Long userId = -1L;

			Long result = userService.isExistEmailORUserName(null, name);
			if (result > 0) { // 用户名重复
				jsonMap.put("error", "6");
				jsonMap.put("msg", "用户名已经存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			
			long recommendUserId = -1;
			if(StringUtils.isNotBlank(refferee)){
				Map<String,String> userIdMap = userService.queryIdByUser(refferee);//根据用户查询用户明细
				if(userIdMap != null){
					recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
				}
				
				if(userIdMap==null){
					jsonMap.put("error", "7");
					jsonMap.put("msg", "推荐人不存在！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			int typelen = -1;
			Map<String, String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount(); // 查询证件类型主表有多少种类型
			if (lenMap != null && lenMap.size() > 0) {
				typelen = Convert.strToInt(lenMap.get("cccc"), -1);
				// 调用service
				if (typelen != -1) {
					userId = userService.userAppRegister(null, name, pwd, "","app","",
							null, typelen, cellPhone, recommendUserId);// 注册用户 和 初始化图片资料
				}
			}
			userService.updateSign(userId);// 设置校验码
			if (userId < 0) { // 注册失败
				jsonMap.put("error", "7");
				jsonMap.put("msg", "注册失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加通知默认方法
				homeInfoSettingService.addNotes(userId, false, true, false);
				homeInfoSettingService.addNotesSetting(userId, false, false,
						false, false, false, true, true, true, true, true,
						false, false, false, false, false);

				jsonMap.put("error", "-1");
				jsonMap.put("msg", "注册成功");
				jsonMap.put("id", userId + "");
				
				//修改之前的推荐
				try {
					if(recommendUserId>0){//判断是否为空
						List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
						if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
							return null;
						}
						if(StringUtils.isBlank(recommend_src)){
							recommend_src = "2";//二维码扫码
						}
						recommendUserService.addRecommendUser(userId, recommendUserId,Convert.strToInt(recommend_src, -1),point);
					}
				} catch (Exception e) {
					log.error(e);
					e.printStackTrace();
				}
				
				// 幸运大转盘获取红包 11-20
	
//				if(DateUtil.dateToStringYYMM(new Date()).equals("2015-11")){
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
//				}
	
				// 幸运大转盘获取红包 11-20
				
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}
	
	public String register() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		
		String auth = request("auth");
		String info = request("info");
		String sign = request("sign");
		String tmp = Encrypt.MD5(auth + info + IConstants.PASS_KEY, "gb2312")
				.toUpperCase();
		log.info("sign:"+sign);
		log.info("tmp:"+tmp);
//		if (!tmp.equals(sign)) {
//			jsonMap.put("error", "-2");
//			jsonMap.put("msg", "签名不正确");
//			JSONUtils.printObject(jsonMap);
//			return null;
//		}

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = String.valueOf(appInfoMap.get("name"));
			String pwd = appInfoMap.get("pwd");
			String refferee = appInfoMap.get("refferee");
			String recommend_src = appInfoMap.get("recommend_src");
			int point = Convert.strToInt(appInfoMap.get("point"), 0);
			 
			//区分安卓
			String flag = appInfoMap.get("flag");
			//ios不解密
			if("android".equals(flag)){
				name = com.shove.security.Encrypt.decrypt3DES(name,
						IConstants.PASS_KEY).trim();
			}else {
				name = name.toLowerCase().replace("u", "\\u");
				name = isKeywords.decodeUnicode(name);
			}
//			pwd = com.shove.security.Encrypt.decrypt3DES(appInfoMap
//					.get("pwd"), IConstants.PASS_KEY);

			if (StringUtils.isBlank(name)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户名不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			String cellPhone = appInfoMap.get("cellPhone");
			//ios不解密
			if("android".equals(flag)){
				cellPhone = com.shove.security.Encrypt.decrypt3DES(cellPhone,
						IConstants.PASS_KEY).trim();
			}
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "手机号码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			 
			if(!cellPhone.matches("^[1][3|4|5|7|8|][0-9]{9}$")){
				jsonMap.put("error", "3");
				jsonMap.put("msg", "手机号码格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (StringUtils.isBlank(pwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			long str = userService.queryUserIdByPhone(cellPhone);
			if (str > 0) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "该手机号已注册！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			 String code = appInfoMap.get("code");
			 if(StringUtils.isBlank(code)){
				 jsonMap.put("error", "4");
				 jsonMap.put("msg", "验证码不能为空");
				 JSONUtils.printObject(jsonMap);
				 return null;
			 }

			 if(IConstants.ISDEMO.equals("2")){
				 String randomCode = appInfoMap.get("randomCode");
				 if(StringUtils.isBlank(randomCode)){
					 jsonMap.put("error", "4");
					 jsonMap.put("msg", "你还没有获取手机验证码");
					 JSONUtils.printObject(jsonMap);
					 return null;
				 }
				 randomCode = Encrypt.decryptSES(randomCode,AlipayConfig.ses_key);
				 if(!code.equals(randomCode)){
					 jsonMap.put("error", "5");
					 jsonMap.put("msg", "手机验证码不正确");
					 JSONUtils.printObject(jsonMap);
					 return null;
				 }
				 String recivePhone =
					 Encrypt.decryptSES(appInfoMap.get("recivePhone"),AlipayConfig.ses_key);
					 if(!cellPhone.equals(recivePhone)){
						 jsonMap.put("error", "6");
						 jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
						 JSONUtils.printObject(jsonMap);
						 return null;
					 }
			 }
			
			 
			// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$

			if (name.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "用户名包含特殊字符");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if ("1".equals(IConstants.ENABLED_PASS)){
				pwd = com.shove.security.Encrypt.MD5(pwd.trim());
			}else{
				pwd = com.shove.security.Encrypt.MD5(pwd.trim()+IConstants.PASS_KEY);
			}

			Long userId = -1L;

			Long result = userService.isExistEmailORUserName(null, name);
			if (result > 0) { // 用户名重复
				jsonMap.put("error", "6");
				jsonMap.put("msg", "用户名已经存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			
			long recommendUserId = -1;
			if(StringUtils.isNotBlank(refferee)){
				Map<String,String> userIdMap = userService.queryIdByUser(refferee);//根据用户查询用户明细
				if(userIdMap != null){
					recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
				}
				
				if(userIdMap==null){
					jsonMap.put("error", "7");
					jsonMap.put("msg", "推荐人不存在！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				
			}
			int regSrc;
			        //判断是否为移动端访问  
			        if("android".equals(flag)){  
			            regSrc=0;
			        } else {  
			        	regSrc=1;
			        } 
			int typelen = -1;
			Map<String, String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount(); // 查询证件类型主表有多少种类型
			if (lenMap != null && lenMap.size() > 0) {
				typelen = Convert.strToInt(lenMap.get("cccc"), -1);
				// 调用service
				if (typelen != -1) {
					userId = userService.userAppRegisters(null, name, pwd, "","app","",
							null, typelen, cellPhone, recommendUserId,regSrc);// 注册用户 和 初始化图片资料
				}
			}
			userService.updateSign(userId);// 设置校验码
			if (userId < 0) { // 注册失败
				jsonMap.put("error", "7");
				jsonMap.put("msg", "注册失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加论坛用户
				/*
				 * User user = new User(); user.setUserName(userName);
				 * user.setPassword(password); user.setEmail(email);
				 * bbsRegisterService.doRegisterByAsynchronousMode(user);
				 */

				// 添加通知默认方法
				homeInfoSettingService.addNotes(userId, false, true, false);
				homeInfoSettingService.addNotesSetting(userId, false, false,
						false, false, false, true, true, true, true, true,
						false, false, false, false, false);

				jsonMap.put("error", "-1");
				jsonMap.put("msg", "注册成功");
				jsonMap.put("id", userId + "");
				
				
				//修改之前的推荐
				try {
					if(recommendUserId>0){//判断是否为空
						List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
						if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
							return null;
						}
						if(StringUtils.isBlank(recommend_src)){
							recommend_src = "2";//二维码扫码
						}
						recommendUserService.addRecommendUser(userId, recommendUserId,Convert.strToInt(recommend_src, -1),point);
					}
				} catch (Exception e) {
					log.error(e);
					e.printStackTrace();
				}
				
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public String authentication() throws IOException{
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> usermap = null;
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String userId = appInfoMap.get("userId");
			String realName = appInfoMap.get("realName");// 真实姓名
			String idNo = appInfoMap.get("idNo");
			long ret = -1;
			if(StringUtils.isNotBlank(userId)){
				long longUserId = Convert.strToLong(userId, 0);
				usermap = userService.queryUserById(longUserId);
				if(usermap != null){
					Map<String,String> map = userService.queryPersonById(longUserId);
					if(StringUtils.isNotBlank(map.get("idNo"))){
						jsonMap.put("error", "-4");
						jsonMap.put("msg", "已经实名认证！");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					
					if (StringUtils.isBlank(realName)) {
						jsonMap.put("error", "-5");
						jsonMap.put("msg", "请正确填写真实名字");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (2 > realName.length() || 20 < realName.length()) {
						jsonMap.put("error", "-6");
						jsonMap.put("msg", "真实姓名的长度为不小于2和大于20");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					//身份证校验
					long len = idNo.length();
					if (StringUtils.isBlank(idNo)) {
						jsonMap.put("error","-7");
						jsonMap.put("msg", "请正确身份证号码");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (15 != len) {
						if (18 == len) {
						} else {
							jsonMap.put("error","-7");
							jsonMap.put("msg", "请正确身份证号码");
							JSONUtils.printObject(jsonMap);
							return null;
						}
					}
					// 验证身份证
					int sortCode = 0;
					int MAN_SEX = 0;
					if (len == 15) {
						sortCode = Integer.parseInt(idNo.substring(12, 15));
					} else {
						sortCode = Integer.parseInt(idNo.substring(14, 17));
					}
					if (sortCode % 2 == 0) {
						MAN_SEX = 1;// 男性身份证
					} else if (sortCode % 2 != 0) {
						MAN_SEX = 2;// 女性身份证
					} else {
						jsonMap.put("error","-7");
						jsonMap.put("msg", "身份证不合法");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					String iDresutl = "";
					iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
					if (iDresutl != "") {
						jsonMap.put("error","-7");
						jsonMap.put("msg", "身份证不合法");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					
					Map<String,String> person = userService.queryPersonByIdNo(idNo);
					if(person!=null){
						jsonMap.put("error","-9");
						jsonMap.put("msg", "身份证已经被实名认证");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					
					person = userService.queryPersonById(longUserId);
					if(!StringUtils.isNotBlank(person.get("idNo"))){
						ret = userService.updateUserBaseDataOne(realName,idNo,longUserId);
					}else{
						jsonMap.put("error","-7");
						jsonMap.put("msg", "该用户已经实名认证！");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					
					if(ret>0){
						jsonMap.put("error","-1");
						jsonMap.put("msg", "设置成功");
						JSONUtils.printObject(jsonMap);
						// 更新注册奖励
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("type", IConstants.RECOMMEND_BROKERAGE_TYPE_REG);
						brokerageIDRecognition.updateBrokerage(longUserId,IConstants.ACTIVITY_RECOMMEND_REG_AMOUNT, param);
						
						// 幸运大转盘获取红包 11-20
						if(DateUtil.dateToStringYYMM(new Date()).equals("2015-11")){
							String bonusAble = null;
							if(session().getAttribute("bonus_able")!=null){
								bonusAble = (String)session().getAttribute("bonus_able");
								if(bonusAble != null && !bonusAble.equals("")){
									long bres = bonusService.addBonus_6_24(Convert.strToLong(userId, -1),Convert.strToDouble(bonusAble, -1),2);
									if(bres>0){
										log.info("幸运大转盘红包发放成功！");
										session().setAttribute("bonus_able", "");
									}
								}
							}
						}
						// 幸运大转盘获取红包 11-20
						
						return null;
					}else{
						jsonMap.put("error","-8");
						jsonMap.put("msg", "设置失败");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				}
			}else{
				jsonMap.put("error","-9");
				jsonMap.put("msg", "用户未登录");
				JSONUtils.printObject(jsonMap);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	
	
	public String queryReconmmendInfo() throws IOException{
		Map<String, String> jsonMap = new HashMap<String, String>();
		JSONObject jo = new JSONObject();
		String type = null;
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			type = appInfoMap.get("type");
			long userId = Convert.strToLong(appInfoMap.get("userId"), 0);
			Map<String,String> user = userService.queryUserById(userId);
			Map<String, String> personMap = userService.queryPersonById(userId);
			if (personMap != null && personMap.size() > 0) {
				jsonMap.put("realName", personMap.get("realName"));
			}
			
			if(userId<=0){
				jo.put("error", "-2");
				jo.put("msg", "未登录");
				printJson(jo.toString());
				return null;
			}
			
			// 1、生成邀请链接
			StringBuffer reconmendUrl = new StringBuffer();
//			reconmendUrl.append(IConstants.WEB_URL);
			reconmendUrl.append("http://m.jcbanking.com/");
			reconmendUrl.append("/page/m/home.html");
			reconmendUrl.append("?ref="+com.shove.security.Encrypt.encrypt3DES(user.get("username"),IConstants.PASS_KEY));
			if(jsonMap.containsKey("realName")){
				reconmendUrl.append("&rn="+com.shove.security.Encrypt.encrypt3DES(jsonMap.get("realName"),IConstants.PASS_KEY));
			}
			jo.put("reconmmend_url", reconmendUrl.toString());
			
			//2 、查询推广活动期间注册用户和投资金额
			if(StringUtils.isNotBlank(type) && type.length()>0){
				recommendSingleDetail(jo,userId,Integer.parseInt(type));
			}else{
				recommendSingleDetail(jo,userId,-1);
			}
			printJson(jo.toString());
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	
	/**
	 * 查询推广活动期间单个推荐人，注册用户和投资金额
	 * @param jo
	 * @param userId
	 */
	private void recommendSingleDetail(JSONObject jo,Long userId,Integer type){
		try {
			List<Map<String,Object>> result = recommendBrokerageListService.queryRecommendUserInvstTotalByConditions(userId,type);
			jo.put("result", result);
			
			Map<String,String> recommendBase = recommendBrokerageListService.queryRecommendBase(userId);
			jo.put("recommendBase", recommendBase);
			
			Map<String,String> recommendSummary = recommendBrokerageListService.queryRecommendSummary(userId);
			jo.put("recommnedSummary", recommendSummary);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送短信
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException
	 */
	public String sendSMSMP() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			String userAgent = request().getHeader("user-agent");
			Map<String, String> appInfoMap = getAppInfoMap();
			String phone = appInfoMap.get("cellPhone");
			
			String pageId = appInfoMap.get("pageId"); // 验证码
			String code = (String) session().getAttribute(pageId + "_checkCode");
			String _code = appInfoMap.get("code").toString().trim();
			//移动端图形验证码不区分大小写
			if (code == null || !_code.toLowerCase().equals(code.toLowerCase())) {
				jsonMap.put("error", "-9999");
				jsonMap.put("msg", "图片验证码错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			
			
			List<Map<String,Object>> list = sendSMSService.getNowDaySmsRecordList(phone);
			if(list!=null && list.size()>=10){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "当日获取验证码次数太多");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if (StringUtils.isBlank(phone)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String isExist = appInfoMap.get("isExist");
			if(StringUtils.isNotBlank(isExist)){
				long str = userService.queryUserIdByPhone(phone);
				if (str > 0 && isExist.equals("2")) {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "该手机号码已注册！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				
				if (str <= 0 && isExist.equals("1")) {
					jsonMap.put("error", "6");
					jsonMap.put("msg", "该手机号码不存在！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			
			// 随机产生4位数字
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);// 最大值位9999
			if (intCount < 1000)
				intCount += 1000; // 最小值位1001

			String randomCode = intCount + "";
			// 发送短信
			Map<String, String> map = sMSInterfaceService.getSMSById(1);
			log.info("本次验证码为:"+randomCode);
			
			String content = "本次验证码为:"+randomCode;
			String retCode = SMSUtil.sendMSM(map.get("Account"), map.get("Password"), content, phone, "");

			// 发送短信
			if ("Sucess".equals(retCode)) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "发送成功");
				jsonMap.put("randomCode", Encrypt.encryptSES(randomCode,
						AlipayConfig.ses_key));
				jsonMap.put("recivePhone", Encrypt.encryptSES(phone,
						AlipayConfig.ses_key));
				sendSMSService.insertSmsRecord(phone, content,"UserAppAction.sendSMSMP:[User-Agent:"+userAgent+"]",request().getRemoteAddr());
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "发送失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * 发送短信
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException
	 */
	public String sendSMS() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			String userAgent = request().getHeader("user-agent");
			Map<String, String> appInfoMap = getAppInfoMap();
			String phone = appInfoMap.get("cellPhone");
			if(getUserAgent() != 1){
				String code4 = appInfoMap.get("code4"); // 验证码
				String _code = appInfoMap.get("code");
				code4 = com.shove.security.Encrypt.decrypt3DES(code4,
						IConstants.PASS_KEY).split(CommonAction.CHECK_CODE_SPLIT)[1];
				log.info("==================验证码进入【code4："+code4+",_code:"+_code+"】===================");
				if (code4 == null || _code==null || !_code.equals(code4)) {
					jsonMap.put("error", "-9999");
					jsonMap.put("msg", "验证码输入错误");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			
			List<Map<String,Object>> list = sendSMSService.getNowDaySmsRecordList(phone);
			if(list!=null && list.size()>=5){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "发送失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if (StringUtils.isBlank(phone)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String isExist = appInfoMap.get("isExist");
			if(StringUtils.isNotBlank(isExist)){
				long str = userService.queryUserIdByPhone(phone);
				if (str > 0 && isExist.equals("2")) {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "该手机号码已注册！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				
				if (str <= 0 && isExist.equals("1")) {
					jsonMap.put("error", "6");
					jsonMap.put("msg", "该手机号码不存在！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			
			// 随机产生4位数字
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);// 最大值位9999
			if (intCount < 1000)
				intCount += 1000; // 最小值位1001

			String randomCode = intCount + "";
			// 发送短信
			Map<String, String> map = sMSInterfaceService.getSMSById(1);
			log.info("本次验证码为:"+randomCode);
			
			String content = "本次验证码为:"+randomCode;
			String retCode = SMSUtil.sendMSM(map.get("Account"), map
					.get("Password"), content, phone, "");

			// 发送短信
			if ("Sucess".equals(retCode)) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "发送成功");
				jsonMap.put("randomCode", Encrypt.encryptSES(randomCode,
						AlipayConfig.ses_key));
				jsonMap.put("recivePhone", Encrypt.encryptSES(phone,
						AlipayConfig.ses_key));
				sendSMSService.insertSmsRecord(phone, content,"UserAppAction.sendSMS:[User-Agent:"+userAgent+"]",request().getRemoteAddr());
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "发送失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}
	
	public String getSessionUserId(){
		Map<String, String> jsonMap = new HashMap<String, String>();
		String userId = "-9";
		User user = (User)session().getAttribute("user");
		if(user != null){
			userId = String.valueOf(user.getId());
		}
		try {
			jsonMap.put("userId", userId);
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return null;
	}

	// public static void main(String[] args) {
	// System.out.println(Encrypt.MD5("test123"));
	// }
	/**
	 * 重置密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetLoginPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		
		String auth = request("auth");
		String info = request("info");
		String sign = request("sign");
		String tmp = Encrypt.MD5(auth + info + IConstants.PASS_KEY, "gb2312")
				.toUpperCase();
		log.info("sign:"+sign);
		log.info("tmp:"+tmp);
		if (!tmp.equals(sign)) {
			jsonMap.put("error", "-2");
			jsonMap.put("msg", "签名不正确");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			password = com.shove.security.Encrypt.decrypt3DES(password,
					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			
			String cellPhone = appInfoMap.get("cellPhone");
			
			if (IConstants.ISDEMO.equals("2")) {
				String code = appInfoMap.get("code");
				if (StringUtils.isBlank(code)) {
					jsonMap.put("error", "2");
					jsonMap.put("msg", "验证码不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				String randomCode = appInfoMap.get("randomCode");
				if (StringUtils.isBlank(randomCode)||randomCode.equals("null")) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "你还没有获取手机验证码");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
				// String phone = session().getAttribute("phone") +"";
				if (!randomCode.equals(code)) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "验证码不正确");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				
				if (!randomCode.equals(code)) {
					jsonMap.put("error", "4");
					jsonMap.put("msg", "验证码不正确");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				String recivePhone = appInfoMap.get("recivePhone");
				if (StringUtils.isBlank(recivePhone)) {
					jsonMap.put("error", "7");
					jsonMap.put("msg", "接收验证码手机号不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				recivePhone = Encrypt.decryptSES(recivePhone, AlipayConfig.ses_key);
				if (StringUtils.isNotBlank(cellPhone)
						&& !cellPhone.equals(recivePhone)) {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			
			
			long userId = userService.queryUserIdByPhone(cellPhone);
			
			if (userId == -1) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "手机号与绑定的手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long result = -1L;

			result = userService.updateLoginPwd(userId, password);

			if (result > 0) {
				// userService.queryUserById(userId);
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateLoginPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			password = com.shove.security.Encrypt.decrypt3DES(password,
					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			String oldPwd = appInfoMap.get("oldPwd").toLowerCase();

			if (StringUtils.isBlank(oldPwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "旧密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			oldPwd = com.shove.security.Encrypt.decrypt3DES(oldPwd,
					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				oldPwd = com.shove.security.Encrypt.MD5(oldPwd.trim());
			} else {
				oldPwd = com.shove.security.Encrypt.MD5(oldPwd.trim()
						+ IConstants.PASS_KEY);
			}
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if(password.equals(userMap.get("dealpwd"))){
				jsonMap.put("error", "14");
				jsonMap.put("msg", "登录密码不能和交易密码一样！");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			boolean re = userService.checkSign(userId);
			if (!re) {
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*修改失败！你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (!oldPwd.toLowerCase().equalsIgnoreCase(userMap.get("password"))) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "旧密码输入错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long result = -1L;

			// password = Encrypt.MD5(password);
			result = userService.updateLoginPwd(userId, password.toLowerCase());

			if (result > 0) {
				userService.queryUserById(userId);
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改银行卡
	 * @return
	 * @throws IOException
	 */
	public String sendMsgByUserName()throws IOException{
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String userName = appInfoMap.get("userName");
			jsonMap = sendMsgCodeByUPM(userName);
			JSONUtils.printObject(jsonMap);
			return null;
		}catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 忘记登录密码，通过手机找回
	 * @return
	 * @throws IOException
	 */
	public String modifyPwdByPhoneMsg()throws IOException{
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String userName = appInfoMap.get("userName");
			String password = appInfoMap.get("newPwd");
			String phoneCode = appInfoMap.get("phoneCode");
			String randomCode = appInfoMap.get("randomCode");
			String phoneCodeEncode = com.shove.security.Encrypt.MD5(phoneCode+IConstants.PASS_KEY);
			if(randomCode ==null){// 兼容之前的session存储验证码版本
				String sessionPhoneCode = (String)session().getAttribute("phoneCode");
				if(sessionPhoneCode != null){
					if(!sessionPhoneCode.equals(phoneCode)){
						jsonMap.put("error", "-9");
						jsonMap.put("msg", "手机验证码不正确，请重新输入！");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				}
			}else{
				if(!randomCode.equals(phoneCodeEncode)){
					jsonMap.put("error", "-9");
					jsonMap.put("msg", "手机验证码不正确，请重新输入！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			
			Map<String, String> userMap = userService.queryUserByName(userName);
			
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "该用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(userMap.get("id"), -1L);
			
			if(password.equals(userMap.get("dealpwd"))){
				jsonMap.put("error", "14");
				jsonMap.put("msg", "登录密码不能和交易密码一样！");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Long result = -1L;
			// password = Encrypt.MD5(password);
			result = userService.updateLoginPwd(userId, password.toLowerCase());

			if (result > 0) {
				userService.queryUserById(userId);
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	
	}
	
	/**
	 * 修改交易密码初始化
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateDeal() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
//			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			boolean re = userService.checkSign(userId);
			if (!re) {
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if (userMap.get("isApplyPro").equals("1")) {
				jsonMap.put("error", "-4");
				jsonMap.put("msg", "请先设置安全问题");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if (userMap.get("isApplyPro").equals("2")) {
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "请先回答安全问题");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateDealPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
//			password = com.shove.security.Encrypt.decrypt3DES(password,
//					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			
			String oldPwd = appInfoMap.get("oldPwd");
//			oldPwd = com.shove.security.Encrypt.decrypt3DES(oldPwd,
//					IConstants.PASS_KEY);
//			
			 if (StringUtils.isBlank(oldPwd)) {
			 jsonMap.put("error", "2");
			 jsonMap.put("msg", "旧密码不能为空");
			 JSONUtils.printObject(jsonMap);
			 return null;
			 }
//			 
			if ("1".equals(IConstants.ENABLED_PASS)) {
				oldPwd = com.shove.security.Encrypt.MD5(oldPwd.trim());
			} else {
				oldPwd = com.shove.security.Encrypt.MD5(oldPwd.trim()
						+ IConstants.PASS_KEY);
			}
			
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			Map<String, String> userMap = userService.queryUserById(userId);
			if(!oldPwd.equals(userMap.get("dealpwd"))){
				jsonMap.put("error", "13");
				jsonMap.put("msg", "旧密码输入错误！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			boolean re = userService.checkSign(userId);
			if (!re) {
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*修改失败！你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 设置交易密码
			Long result = -1L;
			if (Encrypt.MD5("-2").equalsIgnoreCase(oldPwd)) {

				result = userService.updateDealPwd(userId, password);
				if (result > 0) {
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "设置成功");
					JSONUtils.printObject(jsonMap);
				} else {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "设置失败");
					JSONUtils.printObject(jsonMap);
				}
				return null;
			}
			
//			if (!oldPwd.equalsIgnoreCase(userMap.get("dealpwd"))) {
//				jsonMap.put("error", "4");
//				jsonMap.put("msg", "输入旧密码错误");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			
			if(password.equals(userMap.get("password"))){
				jsonMap.put("error", "15");
				jsonMap.put("msg", "交易密码不能和登录密码一样！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			result = userService.updateDealPwd(userId, password);
			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 忘记并重置交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetDealPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd").toLowerCase();

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String cellPhone = appInfoMap.get("cellPhone");
			userService.queryUserIdByPhone(cellPhone);
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			String code = appInfoMap.get("code");
			if (StringUtils.isBlank(code)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			String randomCode = appInfoMap.get("randomCode");
			if (StringUtils.isBlank(randomCode)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "你还没有获取手机验证码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
			// String phone = session().getAttribute("phone") +"";
			if (!randomCode.equals(code)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String recivePhone = appInfoMap.get("recivePhone");
			if (StringUtils.isBlank(recivePhone)) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "接收验证码手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			recivePhone = Encrypt.decryptSES(recivePhone, AlipayConfig.ses_key);
			if (StringUtils.isNotBlank(cellPhone)
					&& !cellPhone.equals(recivePhone)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = userService.queryUserIdByPhone(cellPhone);
			
			if (userId == -1) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "手机号与绑定的手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			Long uId = userService.queryUserIdByPhone(cellPhone);

			long userid = Convert.strToLong(authMap.get("uid"), -1L);
			if (userid == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userid);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (uId != userid) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "输入手机号错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			password = com.shove.security.Encrypt.decrypt3DES(password,
					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			Long result1 = userService.updateDealPwd(userid, password);
			if (result1 > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 审核基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserBaseDataCheck() throws SQLException, IOException,
			DataException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long personId = -1L;
			int auditStatus = 1;// 默认不通过审核
			long userId = Convert.strToLong(authMap.get("uid"), -1l);
			long flag = -1L;
			if (StringUtils.isNotBlank(appInfoMap.get("flag"))) {
				flag = Long.parseLong(appInfoMap.get("flag"));
			}
			if (flag == 3) {
				auditStatus = 3;// 通过审核
			} else if (flag == 2) {
				auditStatus = 2;// 审核不通过
			} else {
				auditStatus = 1;// 等待审核
			}
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			long adminId = admin.getId();
			if (admin != null) {
				personId = userService.updateUserBaseDataCheck(userId,
						auditStatus, adminId);
			}
			// 测试---跳过
			if (IConstants.ISDEMO.equals("1")) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (personId > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
				return null;
				// 成功
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "保存失败");
				// 失败
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加认证图片
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addImage() throws Exception {
		Map<String, String> Apcmap = null;// 五大基本资料的计数存放map
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long materAuthTypeId = Convert.strToLong(appInfoMap.get(
					"materAuthTypeId").toString(), -1L);
			String imgPath = appInfoMap.get("userHeadImg");

			if (StringUtils.isBlank(imgPath)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "上传失败，请上传认证资料");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long imageId = -1L;
			long userId = Convert.strToLong(authMap.get("uid"), -1l);
			User user = userService.jumpToWorkData(userId);
			// 认证状态
			if (null != user) {
				userId = user.getId();

				imageId = userService
						.addImage(materAuthTypeId, imgPath, userId);
				String id = userService.queryPitcturId(userId, materAuthTypeId)
						.get("id");
				String msg = addpastPicturdate(Convert.strToLong(id, -1l),
						materAuthTypeId, user, imgPath);
				if (msg == null) {
					jsonMap.put("error", "-1");// 不给跳转
					jsonMap.put("msg", "保存成功");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				jsonMap.put("error", "11");// 不给跳转
				jsonMap.put("msg", msg);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (imageId < 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "保存失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				Integer allcount = 0;
				Apcmap = userService.queryPicturStatuCount(userId);
				if (Apcmap != null && Apcmap.size() > 0) {
					allcount = Convert.strToInt(Apcmap.get("ccc"), 0);
				}
				if (allcount != 0 && allcount >= 5) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "请先填写基本信息和工作信息");
				} else {
					jsonMap.put("error", "-1");// 不给跳转
					jsonMap.put("msg", "成功");
				}
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	// 用户提交图片审核
	public String addpastPicturdate(Long tmid, Long materAuthTypeId, User user,
			String imgPath) throws Exception {
//		Map<String, String> jsonMap = new HashMap<String, String>();
		// ------add by houli
//		String btype = request("btype");
		// -----------
		List<Map<String, Object>> userPicDate = null;
//		Map<String, String> typmap = null;
		long userId = user.getId();
		int len = 1;// 上传图片个数
		if (tmid > 0) {
			request().setAttribute("tmid", tmid);
			userPicDate = userService.queryPerTyhpePicture(tmid);
			len = userPicDate.size() + 1;
		}

		Integer Listlen = 0;// 数据库的图片个数
		// Long tmids = Convert.strToLong(appInfoMap.get("tmidStr"), -1L);
		Long result = -1L;
		if (Listlen == -1) {
			return "没有图片";
		}
		if (len == -1) {
			return "没有图片";
		}
		Integer allPicturecount = len + Listlen;// 用户将要上传的图片和数据库图片的个数的总和
		if (materAuthTypeId == 1) {
			if (5 < allPicturecount) {
				return "身份证审核图片最多5张";
			}// 身份证
		}
		if (materAuthTypeId == 2) {
			if (10 < allPicturecount) {
				return "工作认证审核图片最多10张";
			}// 工作认证
		}
		if (materAuthTypeId == 3) {
			if (5 < allPicturecount) {
				return "居住认证审核图片最多5张";
			}// 居住认证
		}

		if (materAuthTypeId == 4) {
			if (30 < allPicturecount) {
				return "收入认证审核图片最多5张";
			}// 收入认证
		}
		if (materAuthTypeId == 5) {
			if (10 < allPicturecount) {
				return "信用报告审核图片最多10张";
			}// 信用报告
		}
		if (materAuthTypeId == 6) {
			if (10 < allPicturecount) {
				return "房产证审核图片最多10张";
			}// 房产
		}
		if (materAuthTypeId == 7) {
			if (10 < allPicturecount) {
				return "购车证审核图片最多10张";
			}// 购车
		}
		if (materAuthTypeId == 8) {
			if (5 < allPicturecount) {
				return "结婚证审核图片最多5张";
			}// 结婚
		}
		if (materAuthTypeId == 9) {
			if (5 < allPicturecount) {
				return "学历认证审核图片最多5张";
			}// 学历
		}
		if (materAuthTypeId == 10) {
			if (5 < allPicturecount) {
				return "技术认证审核图片最多5张";
			}// 技术
		}
		if (materAuthTypeId == 11) {
			if (5 < allPicturecount) {
				return "手机认证审核图片最多5张";
			}// 手机
		}
		if (materAuthTypeId == 12) {
			if (5 < allPicturecount) {
				return "微博认证审核图片最多5张";
			}// 微博
		}
		/*
		 * if(materAuthTypeId==13){ //视频
		 * if(5<allPicturecount){JSONUtils.printStr("13");return null;}//视频 }
		 */

		if (materAuthTypeId == 13) {
			if (10 < allPicturecount) {
				return "现场认证审核图片最多10张";
			}// 现场认证
		}
		if (materAuthTypeId == 14) {
			if (10 < allPicturecount) {
				return "抵押认证审核图片最多10张";
			}// 抵押认证
		}

		if (materAuthTypeId == 15) {
			if (10 < allPicturecount) {
				return "机构担保审核图片最多10张";
			}// 机构担保
		}

		if (materAuthTypeId == 16) {
			if (30 < allPicturecount) {
				return "其他资料审核图片最多30张";
			}// 其他资料
		}

		List<Long> lists = new ArrayList<Long>();// 已经上传的图片设置他们的可见性
		if (Listlen != -1 && user != null) {
			for (int i = 1; i <= Listlen; i++) {
				if (Convert.strToInt(paramMap.get("id" + i), -1) != -1) {
					lists.add(Convert.strToLong(paramMap.get("id" + i), -1));
				}
			}
		}

		List<String> imglistsy = new ArrayList<String>();
		List<String> imgListsn = new ArrayList<String>();
		if (len != -1 && user != null) {
			for (int i = 1; i <= len; i++) {// 将要上传图片图片先保存在一个数组里面
				imgListsn.add(imgPath);
				break;
			}
		}
		// Map<String, String> authMap = this.getAppAuthMap();
		// long userId = Convert.strToLong(authMap.get("uid"), -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String uploadingTime = format.format(new Date());// 当前时间上传图片时间
		if (user != null && tmid != -1L && materAuthTypeId != -1L) {
			result = userService.addUserImage(1, uploadingTime, lists,
					imglistsy, imgListsn, tmid, userId, materAuthTypeId, -1l,
					len);// 遍历将image查到数据库中 1 表示向t_materialsauth 插入图片类型 表示等待审核
			if (result > 0) {
				// 更新User的状态
				try {

					Map<String, String> newstatusmap = null;
					newstatusmap = userService.querynewStatus(userId);// 查询放到session中去
					if (newstatusmap != null && newstatusmap.size() > 0) {
						user.setAuthStep(Convert.strToInt(newstatusmap
								.get("authStep"), -1));

						user.setEmail(Convert.strToStr(newstatusmap
								.get("email"), null));
						user.setPassword(Convert.strToStr(newstatusmap
								.get("password"), null));
						user.setId(Convert.strToLong(newstatusmap.get("id"),
								-1L));
						user.setRealName(Convert.strToStr(newstatusmap
								.get("realName"), null));
						user.setKefuname(Convert.strToStr(newstatusmap
								.get("kefuname"), null));
						user.setUserName(Convert.strToStr(newstatusmap
								.get("username"), null));
						user.setVipStatus(Convert.strToInt(newstatusmap
								.get("vipStatus"), -1));
						user.setKefuid(Convert.strToInt(newstatusmap
								.get("tukid"), -1));
						// session().setAttribute("user",
						// user);//跟新session中的user
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return "失败";
			}

		}
		return null;
	}

	/**
	 * 跳转到上传页面
	 * 
	 * @throws Exception
	 */
	public String jumpPassDatapage() throws Exception {

		List<Map<String, Object>> basepictur = null;
		List<Map<String, Object>> selectpictur = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {

			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			User user = userService.jumpToWorkData(userId);
			String from = appInfoMap.get("from");
			String btype = appInfoMap.get("btype");
			// -------------
			if (user != null) {
				if (from == null || from.equals("")) {
					// 获取用户认证进行的步骤
					if (user.getAuthStep() == 1) {
						// 个人信息认证步骤
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先填写个人信息");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (user.getAuthStep() == 2) {
						// 工作信息认证步骤
						jsonMap.put("error", "2");
						jsonMap.put("msg", "请先填写工作信息");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (user.getAuthStep() == 3) {
						// VIP申请认证步骤
						jsonMap.put("error", "3");
						jsonMap.put("msg", "请先成为VIP");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					// ---------add by houli
					else if (user.getAuthStep() == 5
							&& (btype != null && !btype.equals(""))) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				} else {// 净值借款跟秒还借款操作步骤
					// 获取用户认证进行的步骤
					if (user.getAuthStep() == 1) {
						// 个人信息认证步骤
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先填写个人信息");
						JSONUtils.printObject(jsonMap);
						return null;
					}

					if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "请先成为VIP");
						JSONUtils.printObject(jsonMap);
						return null;
					}

					// -------add by houli
					// return jumpToBorrow(btype);
					if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						return null;
					} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					// -----------------
				}

				basepictur = userService.queryBasePicture(userId);// 五大基本
				selectpictur = userService.querySelectPicture(userId);// 可选
				jsonMap.put("basepictur", basepictur);
				jsonMap.put("selectpictur", selectpictur);
				jsonMap.put("error", "-1");// 不给跳转
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 所有密码安全提问的内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAllQuestionList() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<Map<String, Object>> list = userService.queryAllQuestionList();
		if (null != list) {
			jsonMap.put("list", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "获取所有密保提问成功");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		jsonMap.put("error", "1");
		jsonMap.put("msg", "获取所有密保提问失败");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	/**
	 * 检查用户回答密保问题是否正确
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkUserAnswer() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);
		String answerOne = appInfoMap.get("answerOne");
		String answerTwo = appInfoMap.get("answerTwo");
		String answerThree = appInfoMap.get("answerThree");
		Map<String, String> map = homeInfoSettingService
				.queryUserALLAnswer(userId);
		if (null != map) {
			if (!answerOne.equals(map.get("answerOne"))) {
				jsonMap.put("error", "1");// 问题一回答不正确
				jsonMap.put("msg", "问题一回答不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!answerTwo.equals(map.get("answerTwo"))) {
				jsonMap.put("error", "2");// 问题二回答不正确
				jsonMap.put("msg", "问题二回答不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!answerThree.equals(map.get("answerThree"))) {
				jsonMap.put("error", "3");// 问题三回答不正确
				jsonMap.put("msg", "问题三回答不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			jsonMap.put("error", "-1");// 问题回答都正确
			jsonMap.put("msg", "问题回答都正确");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		return null;
	}

	/**
	 * 获取用户设置的密保问题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserQuestion() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);
		int isApplyPro = Convert.strToInt(userService.queryUserById(userId)
				.get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);

		try {
			Map<String, String> map = homeInfoSettingService
					.queryUserALLAnswer(userId);
			if (null != map) {

				jsonMap.put("questionOne", map.get("questionOne"));
				jsonMap.put("questionTwo", map.get("questionTwo"));
				jsonMap.put("questionThree", map.get("questionThree"));
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "获取成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 保存或修改用户密保答案及提问
	 * 
	 * @return
	 * @throws Exception
	 */

	public String saveOrUpdatePwdAnswer() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);

		int isApplyPro = Convert.strToInt(userService.queryUserById(userId)
				.get("isApplyPro"), 1);

		String questionOne = appInfoMap.get("questionOne");
		String questionTwo = appInfoMap.get("questionTwo");
		String questionThree = appInfoMap.get("questionThree");
		String answerOne = appInfoMap.get("answerOne");
		String answerTwo = appInfoMap.get("answerTwo");
		String answerThree = appInfoMap.get("answerThree");
		long result = -1;
		// 判定用户是否已经申请密保，没有申请就表示保存，有申请就表示修改
		if (isApplyPro == 1) {
			result = homeInfoSettingService.savePwdAnswer(userId, questionOne,
					questionTwo, questionThree, answerOne, answerTwo,
					answerThree);
			if (result < 0) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "设置密保失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				userService.updatePwdProState(userId);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "设置密保成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		}
		result = homeInfoSettingService.updatePwdAnswer(userId, questionOne,
				questionTwo, questionThree, answerOne, answerTwo, answerThree);
		if (result < 0) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "修改密保失败");
			JSONUtils.printObject(jsonMap);
			return null;
		} else {
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "修改密保成功");
			JSONUtils.printObject(jsonMap);
			return null;
		}
	}
	
	/**
	 * 用户意见反馈
	 * @author whb
	 * @return
	 * @throws IOException
	 */
	public String addOptions() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String title = appInfoMap.get("title");
			String options = appInfoMap.get("options");
			
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			
			if (userId == -1L) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			long result = homeInfoSettingService.addOptions(userId, title, options);
			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "反馈成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "反馈失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 帮助中心
	 * @author whb
	 * @return
	 * @throws IOException
	 */
	public String callcenter() throws IOException {
		try {
			int type = Convert.strToInt(request.getString("typeId"), -1);
			//页面选择 1：我要投资；2：账户管理；3：安全保障
			if(1 == type){
				return "wytz";
			}else if(2 == type){
				return "zhgl";
			}else if(3 == type){
				return "aqbz";
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送短信息通过 用户名 电话 Email
	 * @param upm 用户名 \电话 \Email
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> sendMsgCodeByUPM(String upm) throws Exception{
		Map<String,String> resultMap = new HashMap<String,String>();
		Map<String,String> userMap = null;
		if(org.apache.commons.lang.StringUtils.isNotBlank(upm)){
			userMap = userService.queryUserByName(upm);
			if(userMap == null){
				resultMap.put("error", "2");
				resultMap.put("msg", "输入的手机号不是您绑定的手机号");
				return resultMap;
			}
		}else{
			resultMap.put("error", "-9");
			resultMap.put("msg", "用户 名不能为空");
			return resultMap;
		}
		
		String phone = userMap.get("mobilePhone");
		Map<String,String> map = null;
		if(phone != null && phone.length() > 0){
			//手机正则
			if(!phone.matches("^[1][3|4|5|7|8|][0-9]{9}$")){
				resultMap.put("error", "1");
				resultMap.put("msg", "手机号码格式错误");
				return resultMap;
			}
			map = userService.queryUserByNameAndPhone(null, phone);
			if(map!=null && map.size() > 0){
				int intCount = 0;
				intCount = (new Random()).nextInt(9999);
				if (intCount < 1000)
					intCount += 1000; 
				
				String randomCode = intCount + "";
				Map<String, String> maps = sMSInterfaceService.getSMSById(1);
				System.out.println(randomCode);
				String content = "本次验证码为:"+ randomCode;
				String retCode = SMSUtil.sendMSM(maps.get("Account"), maps
						.get("Password"), content, phone, "");
				
				if("Sucess".equals(retCode)){
					session().setAttribute("phoneCode", randomCode);
					resultMap.put("code", com.shove.security.Encrypt.MD5(randomCode+IConstants.PASS_KEY));
					resultMap.put("error", "-1");
					resultMap.put("msg", "发送成功");
					return resultMap;
				}else{
					resultMap.put("error", "3");
					resultMap.put("msg", "发送失败");
					return resultMap;
				}
			}
		}else{
			resultMap.put("error", "0");
			resultMap.put("msg", "手机号不能为空");
		}
		return null; 
	
	}
	
	/**
	 * 我的体验金
	 * @return
	 */
	public String myTyj(){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Map<String, String> authMap = getAppAuthMap();
		Map<String, String> appInfoMap = getAppInfoMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);
		
		try {
			if (userId == -1L) {
				jsonMap.put("msg", "您未登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		 
			pageBean.setPageNum(appInfoMap.get("curPage"));
			pageBean.setPageSize(15);
			bonusService.queryMyTyj(pageBean,userId);
			jsonMap.put("pageBean", pageBean);
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setSMSInterfaceService(SMSInterfaceService interfaceService) {
		sMSInterfaceService = interfaceService;
	}

	public SendSMSService getSendSMSService() {
		return sendSMSService;
	}

	public void setSendSMSService(SendSMSService sendSMSService) {
		this.sendSMSService = sendSMSService;
	}

	public BonusService getBonusService() {
		return bonusService;
	}

	public void setBonusService(BonusService bonusService) {
		this.bonusService = bonusService;
	}

}