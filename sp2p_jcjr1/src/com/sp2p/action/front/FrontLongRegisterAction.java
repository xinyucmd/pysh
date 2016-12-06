package com.sp2p.action.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.service.admin.SendSMSService;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
//import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MailSendService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.RelationService;
import com.shove.Convert;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.util.UtilDate;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;

/**
 * 用户注册
 * 
 * @author
 * 
 */
public class FrontLongRegisterAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(FrontLongRegisterAction.class);
	private static final long serialVersionUID = 1L;
	/**
	 */
	protected UserService userService;
	protected SendMailService sendMailService;
	private RecommendUserService recommendUserService;
	private RelationService relationService;
	private HomeInfoSettingService homeInfoSettingService;
	private UserIntegralService userIntegralService;
	private MailSendService mailSendService;
	//private BBSRegisterService bbsRegisterService;
	private AdminService adminService;
	private SMSInterfaceService phonemsg;
	private CellPhoneService cellPhoneService;
	private BeVipService beVipService;
	private SendSMSService sendSMSService;
	
	public void setMailSendService(MailSendService mailSendService) {
		this.mailSendService = mailSendService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	public String regInit() throws Exception {
		User user = (User) session().getAttribute("user");
		if (user != null) {
			// return "login";
		}
		String param = request.getString("param");
		if (StringUtils.isNotBlank(param)) {
			DesSecurityUtil des = new DesSecurityUtil();
			Long userId = Convert.strToLong(des.decrypt(param), -1);
			String userName;
			Map<String, String> map = new HashMap<String, String>();
			try {
				map = userService.queryUserById(userId);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
			userName = map.get("username");
			paramMap.put("refferee", userName);
		}
		paramMap.put("param", param);
		return SUCCESS;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	public String forget() {
		return SUCCESS;
	}

	public String forgetSendEMl() throws Exception {
		JSONObject obj = new JSONObject();
		Map<String, String> map = null;
		String username = null;
		Long userId = null;
		String email = paramMap.get("email");
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		} else {
			// ===截取emal后面地址
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}else{//输入的是用户名
				map = userService.queryUserByName(email);
				if(map != null && map.size() > 0){
					email = map.get("email")+"";
					dd = email.indexOf("@");
					if (dd >= 0) {
						mailAddress = "mail." + email.substring(dd + 1);
					}
				}else{
					obj.put("mailAddress", "1");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			// ====
			map = userService.queryPassword(email);
			if (map != null && map.size() > 0) {
				username = map.get("username");
				userId = Convert.strToLong(map.get("id"), -1L);
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");
				String url = getPath(); // request().getRequestURI();
				String VerificationUrl = url + "changePassword.do?key=" + key1
						+ "-" + key2;

				sendMailService.sendRegisterVerificationEmailPassWordindex(
						VerificationUrl, username, email);
				obj.put("mailAddress", mailAddress);
				JSONUtils.printObject(obj);
				return null;

			} else {
				obj.put("mailAddress", "1");
				JSONUtils.printObject(obj);
				return null;
			}
		}
	}

	/**
	 * 点击邮箱连接后
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changePasswordfor() throws Exception {
		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
		    String userId = Encrypt.MD5(key+IConstants.BBS_SES_KEY).substring(0,10)+key;
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				ServletActionContext.getRequest()
						.setAttribute("userId", userId);
				return SUCCESS;
			} else {
				msg = "连接失效,<strong>请从新填写你的注册邮箱</a></strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
				return "index";
			}

		} else {
			return "index";
		}

	}

	/**
	 * 修改论坛密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatechangePasswordfor() throws Exception {
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("newPassword"));
		String confirmpassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmpassword"));
		String key = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		Long userId = -1l;
		
		String mdKey = key.substring(0,10);
		String mdValue = key.substring(10,key.length());
		String mdCompare = Encrypt.MD5(mdValue+IConstants.BBS_SES_KEY).substring(0,10);
		if(!mdKey.equals(mdCompare)){
			JSONUtils.printStr("4");
			return null;
		}
		String[] keys = mdValue.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			userId = Convert.strToLong(des.decrypt(keys[0].toString()), -1);
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) >= 10 * 60 * 1000) {
				JSONUtils.printStr("4");
				return null;
			}
		} else {
			JSONUtils.printStr("4");
			return null;
		}
		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr("3");
			return null;
		}

		if (!confirmpassword.equals(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		// 验证密码的长度
		if (password.length() < 6 || password.length() > 20) {
			JSONUtils.printStr("6");
			return null;
		}
		if (userId < 0) {
			JSONUtils.printStr("4");
			return null;
		}
		Long result = -1L;
		if (password != null && password.trim() != "" && userId > 0) {
			result = userService.updateUserPassword(userId, password);
		}
		if (result > 0) {
			Map<String, String> userMap = userService.queryUserById(userId);
			if(userMap == null){
				userMap = new HashMap<String, String>();
			}
			String userName = userMap.get("username") + "";
			// 修改论坛的密码
			//bbsRegisterService.doUpdatePwdByAsynchronousMode(userName,password, confirmpassword, 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}

	}

	// -------add by houli 将查询推荐人方法单独出来，注册填写推荐人的时候，用户要求失去焦点进行提示推荐人填写正确与否
	public String queryValidRecommer() throws Exception {
		String refferee = request.getString("refferee");
		try {
			if (refferee == null) {
				JSONUtils.printStr("1");
				return null;
			}
			Map<String, String> userIdMap = userService.queryIdByUser(refferee);// 根据用户查询用户明细
			Map<String, Object> map = relationService.isPromoter(refferee);
			if (userIdMap == null && map == null) {
				JSONUtils.printStr("1");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	// ------------end by houli

	/**
	 * @throws Exception
	 *             用户注册
	 * @throws SQLException
	 * @return String
	 * @throws IOException
	 * @throws MessagingException
	 * @throws
	 */
	public String register() throws Exception {
		JSONObject obj = new JSONObject();
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")) ; // 验证码
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email")); // 电子邮箱
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "12");
			JSONUtils.printObject(obj);
			return null;
		}
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
		if (userName.length() < 2 || userName.length() > 20) {
			obj.put("mailAddress", "18");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(userName)) {
			obj.put("mailAddress", "13");
			JSONUtils.printObject(obj);
			return null;
		}
		// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$

		if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
			obj.put("mailAddress", "20");
			JSONUtils.printObject(obj);
			return null;
		}
		// 判断第一个字符串不能使以下划线开头的
		String fristChar = userName.substring(0, 1);
		if (fristChar.equals("_")) {
			obj.put("mailAddress", "21");
			JSONUtils.printObject(obj);
			return null;
		}

		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password")); // 用户密码
		String md5Password = password;
		if (StringUtils.isBlank(password)) {
			obj.put("mailAddress", "14");
			JSONUtils.printObject(obj);
			return null;
		}
		String confirmPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword")); // 用户确认密码
		if (StringUtils.isBlank(confirmPassword)) {
			obj.put("mailAddress", "15");
			JSONUtils.printObject(obj);
			return null;
		}
		
		String refferee = SqlInfusion.FilteSqlInfusion(paramMap.get("refferee"));
		@SuppressWarnings("unused")
		String param = SqlInfusion.FilteSqlInfusion(paramMap.get("param")); // 邀请好友链接携带的参数
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		if (code == null || !_code.equals(code)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, Object> map = null;
		long recommendUserId = -1;
		if (StringUtils.isNotBlank(refferee)) {
			Map<String, String> userIdMap = userService.queryIdByUser(refferee);// 根据用户查询用户明细
			if (userIdMap != null) {
				recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
			}
			map = relationService.isPromoter(refferee);
			if (map == null) {
				refferee = null;
			}
			if (userIdMap == null && map == null) {
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
		try {
			Long result = userService.isExistEmailORUserName(null, userName);
			if (result > 0) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
			result = userService.isExistEmailORUserName(email, null);
			if (result > 0) { // email重复
				obj.put("mailAddress", "3");
				JSONUtils.printObject(obj);
				return null;
			}
			Thread.sleep(100);
			int typelen = -1;
			if ("1".equals(IConstants.ENABLED_PASS)) {
				md5Password = com.shove.security.Encrypt
						.MD5(md5Password.trim());
			} else {
				md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
						+ IConstants.PASS_KEY);
			}
			// 调用service
			userId = userService.userRegister1(email, userName, md5Password,
					refferee, map, typelen, null);// 注册用户 和初始化图片资料
			// Map<String, String> lenMap = null;
			// lenMap = userService.querymaterialsauthtypeCount(); //
			// 查询证件类型主表有多少种类型
			// if (lenMap != null && lenMap.size() > 0) {
			// typelen = Convert.strToInt(lenMap.get("cccc"), -1);
			// if (typelen != -1) {
			// //判断是否使用了加密字符串
			// if ("1".equals(IConstants.ENABLED_PASS)){
			// md5Password = com.shove.security.Encrypt.MD5(md5Password.trim());
			// }else{
			// md5Password =
			// com.shove.security.Encrypt.MD5(md5Password.trim()+IConstants.PASS_KEY);
			// }
			//					
			// userId = userService.userRegister(email, userName,
			// md5Password, refferee, map, typelen, null);// 注册用户 和
			// // 初始化图片资料
			// }
			// }
			if (userId < 0) { // 注册失败
				obj.put("mailAddress", "4");
				JSONUtils.printObject(obj);
				return null;
			} else {
				userService.updateSign(userId);//修改校验码
				// 添加论坛用户
//				User user = new User();
//				user.setUserName(userName);
//				user.setPassword(password);
//				user.setEmail(email);
//				bbsRegisterService.doRegisterByAsynchronousMode(user);

				// 组合加密验证链接
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");

				String url = getPath(); // request().getRequestURI();
				String VerificationUrl = url + "verificationEmial.do?key="
						+ key1 + "-" + key2;
				// 发送验证邮件
				mailSendService.sendRegEmail(userName, email, VerificationUrl);
				// ===截取emal后面地址
				int dd = email.indexOf("@");
				String mailAddress = null;
				if (dd >= 0) {
					mailAddress = "mail." + email.substring(dd + 1);
				}
				session().setAttribute("mail", mailAddress);
				session().setAttribute("register_email", email);
				// 添加通知默认方法
				homeInfoSettingService.addNotes(userId, true, true, false);
				homeInfoSettingService.addNotesSetting(userId, true, true,
						true, true, true, true, true, false, false, false,
						false, false, false, false, false);
				// ====
				obj.put("mailAddress", mailAddress);
				JSONUtils.printObject(obj);

			}
		} catch (SQLException e) {
			obj.put("mailAddress", "16");
			JSONUtils.printObject(obj);
			e.printStackTrace();
			throw e;

		}
		JSONUtils.printStr(IConstants.USER_REGISTER_OK);
		// 注册成功后判断是否是推广注册的。
		// 修改之前的推荐
		try {
			if (recommendUserId > 0) {// 判断是否为空

				List<Map<String, Object>> list = recommendUserService
						.queryRecommendUser(null, userId, null);// 查询用户是否已经存在关系了。
				if (list != null && list.size() > 0) {// 判断之前是否已经有关系了。
					return null;
				}
				int point = 0 ;
				recommendUserService.addRecommendUser(userId, recommendUserId,3,point);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (IConstants.ISDEMO.equals("1")) {
			obj.put("mailAddress", "99");
			JSONUtils.printObject(obj);
			return null;
		}
		return null;
	}

	private long r_userId;

	public long getR_userId() {
		return r_userId;
	}

	public void setR_userId(long id) {
		r_userId = id;
	}

	// add by houli
	public String reActivateEmail() throws Exception {
		String email = request.getString("email") == null ? null : Convert.strToStr(
				SqlInfusion.FilteSqlInfusion(request.getString("email")), null);
		try {
			if (email == null) {
				JSONUtils.printStr("1");
				return INPUT;
			}
			Map<String, String> userm = userService.queryUserByName(email);
			if(userm == null){
				return INPUT;
			}
			email = userm.get("email");
			
			Pattern p = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
			Matcher matcher = p.matcher(email);
			if (!matcher.matches()) {
				return INPUT;
			}
			long id = -100;
			// 根据邮件查询用户信息
			Map<String, String> userMap = userService.queryPassword(email);
			if (userMap == null || userMap.isEmpty()) {
				// 按照用户名查找
				userMap = userService.queryIdByUser(email);
				if (userMap == null || userMap.isEmpty()) {
					JSONUtils.printStr("2");
					return INPUT;
				} else {
					id = userMap.get("id") == null ? -100 : Convert.strToLong(
							userMap.get("id"), -100);
				}
			} else {
				id = userMap.get("id") == null ? -100 : Convert.strToLong(
						userMap.get("id"), -100);
			}

			setR_userId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 登录BBS
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginBBS() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String referer = this.request.getString("referer");
		if (referer == null) {
			referer = "";
		}
		if (referer.contains("tid")) {
			referer += "&highlight=";
		}
		if (user == null) {
			this.response().sendRedirect(IConstants.BBS_URL + referer);
			return null;
		}

		// 虚拟用户不能登录论坛
		if (user.getVirtual() == 1) {
			this.response().sendRedirect(IConstants.BBS_URL);
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();

		map.put("username", user.getUserName());
		map.put("password", user.getPassword());

		map.put("cookietime", "2592000");
		map.put("answer", "");
		map.put("formHash", "6a36c78f");
		map.put("loginfield", "username");
		map.put("loginmode", "");
		map.put("loginsubmit", "true");

		map.put("questionid", "0");

		map.put("referer", referer);
		map.put("styleid", "");

		map.put("k", Encrypt.encryptSES(IConstants.BBS_KEY,
				IConstants.BBS_SES_KEY));

		String strURL = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL
				+ "logging.jsp?action=login" : IConstants.BBS_URL
				+ "/logging.jsp?action=login";
		String html = buildForm(map, strURL, "post", "登录");
		this.response().setContentType("text/html");
		response().setCharacterEncoding("utf-8");
		PrintWriter out = response().getWriter();
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
		out.println(" <BODY>");
		out.print(html);
		out.println(" </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		return null;
	}

	private String buildForm(Map<String, String> sParaTemp, String gateway,
			String strMethod, String strButtonName) {
		log.info("BBS==gateway========>" + gateway);
		// 待请求参数数组
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"loginForm\" name=\"loginForm\" action=\""
				+ gateway + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sParaTemp.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name
					+ "\" value=\"" + value + "\"/>");
			log.info(name + "=============" + value);
		}

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName
				+ "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['loginForm'].submit();</script>");

		return sbHtml.toString();
	}

	/**
	 * 登录初始化
	 * 
	 * @return String
	 * @throws
	 */
	public String loginInit() {
		User user = (User) session().getAttribute("user");
		if (user != null) {
			return "zhanghao";
		}
		
		return SUCCESS;
	}

	/**
	 * 验证用户名和邮箱的唯一性
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String ajaxCheckRegister() throws Exception {
		try {
			String email = paramMap.get("email"); // 电子邮箱
			String userName = paramMap.get("userName"); // 用户名
			String flag = paramMap.get("flag");

			// 判断邮箱是否唯一
			Long result = -1L;
			if (StringUtils.isNotBlank(email) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(email, null);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_EMAIL);
				} else {
					JSONUtils.printStr("0");
				}
				return null;
			}

			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, userName);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_NAME);
				} else {
					// ---add by houli 首先检查用户表中是否有重复的名字，如果没有则去t_admin表中
					Map<String, String> map = adminService
							.queryIdByUser(userName);
					if (map == null || map.size() <= 0) {
						JSONUtils.printStr("0");
					} else {
						JSONUtils
								.printStr(IConstants.USER_REGISTER_REPEAT_NAME);
					}
				}
				return null;
			}

			// 判断邮箱与用户名是否对应存在
			if (StringUtils.isNotBlank(flag) && StringUtils.isNotBlank(email)
					&& StringUtils.isNotBlank(userName)) {
				result = userService.isExistEmailORUserName(email, userName);
				if (result > 0) {
					JSONUtils.printStr("1");

				} else {
					JSONUtils.printStr("0");
				}
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return null;
	}

	/**
	 * 用户登录时候的用户名和邮箱验证是否已将激活
	 * 
	 * @throws Exception
	 */
	public String ajaxChecklogin() throws Exception {
		try {
			String email = paramMap.get("email"); // 电子邮箱
			String userName = paramMap.get("userName"); // 用户名
			String flag = paramMap.get("flag");
			String cellphone = paramMap.get("cellphone"); // 用户名
			// 判断邮箱是否唯一
			Long result = -1L;
			Long vidResult = -1L;
			if (StringUtils.isNotBlank(email) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				result = userService.isUEjihuo(email, null);
				// 不检测enable 检测有没这个账号
				vidResult = userService.isUEjihuo_(email, null);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("0");
					return null;
					// 有邮箱 但是没有激活
				} else if (result > 0) {
					JSONUtils.printStr("1");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}

			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				result = userService.isUEjihuo(null, userName);
				// 不检测enable 检测有没这个账号
				vidResult = userService.isUEjihuo_(null, userName);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("2");
					return null;
					// 有号 但是没有激活
				} else if (result > 0) {
					JSONUtils.printStr("3");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}

			if (StringUtils.isNotBlank(cellphone) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				// 不检测enable 检测有没这个账号
				vidResult = userService.isPhoneExist(cellphone);
				long vidResult2 = userService.isUEjihuo_(null, cellphone);
				if (vidResult < 0&&vidResult2<0) {
					// 没有这个账号
					JSONUtils.printStr("5");
					return null;
				}
				JSONUtils.printStr("4");
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
	 * 用户登录
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String login() throws Exception {
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String pageId = paramMap.get("pageId"); // 验证码
		String email = paramMap.get("email");
		String password = paramMap.get("password");
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		User user = (User) session().getAttribute("user");
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code");
		//验证码不区分大小写
		if (code == null || !_code.toLowerCase().equals(code.toLowerCase())) {
			JSONUtils.printStr("2");
			return null;
		}
		try {
			Thread.sleep(500);
			user = userService.userLogin1(email, password, lastIP, lastTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		Map<String,String> mapUser = userService.queryUserName(email);
		if(mapUser!=null && mapUser.size()>0){
			String i = mapUser.get("loginErrorCount");
			if("1".equals(i)){
				JSONUtils.printStr("11");
				return null;
			}
			if("2".equals(i)){
				JSONUtils.printStr("22");
				return null;
			}
			
			if("3".equals(i)){
				JSONUtils.printStr("33");
				return null;
			}
		}
		
		if (user == null) {
			JSONUtils.printStr("3");
			return null;
		}

		// 查找数据库对象中的enable属性
		if (user.getEnable() == 2) {
			JSONUtils.printStr("4");
			return null;
		}
		if (user.getIsLoginLimit() == 2) {
			JSONUtils.printStr("5");
			return null;
		}	
		boolean re = userService.checkSign(user.getId());
		if(!re){
			JSONUtils.printStr("7");
			return null;
		}
		
		// 解除强制登陆之后的跳转链接
		if(session().getAttribute("forceAfterLoginUrl")!=null){
			session().removeAttribute("forceAfterLoginUrl");
		}
		
		int lineDownLicai = 0;
		Map<String,String> userGroupMap = userService.queryGroupById(user.getId());//是否为线下理财人
		if(userGroupMap!=null && userGroupMap.size()>0){
			lineDownLicai = 1;
		}
		session().setAttribute("lineDownLicai", lineDownLicai);
		session().setAttribute("user", user);
		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		JSONUtils.printStr("1");
		return null;
	}
	
	/**
	 * 用户登录
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String afterRegLogin() throws Exception {
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String email = paramMap.get("email");
		String password = paramMap.get("password");
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		User user = (User) session().getAttribute("user");
		try {
			Thread.sleep(500);
			user = userService.userLogin1(email, password, lastIP, lastTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (user == null) {
			JSONUtils.printStr("3");
			return null;
		}

		// 查找数据库对象中的enable属性
		if (user.getEnable() == 2) {
			JSONUtils.printStr("4");
			return null;
		}
		if (user.getIsLoginLimit() == 2) {
			JSONUtils.printStr("5");
			return null;
		}	
		boolean re = userService.checkSign(user.getId());
		if(!re){
			JSONUtils.printStr("7");
			return null;
		}
		session().setAttribute("user", user);
		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		JSONUtils.printStr("1");
		return null;
	}
	

	/**
	 * 验证邮箱
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public String verificationEmial() throws Exception {
		String key = request.getString("key").trim();
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			Long userId = Convert
					.strToLong(des.decrypt(keys[0].toString()), -1);

			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				// 修改用户状态
				Long result = userService.frontVerificationEmial(userId);
				if (result > 0) {
					msg = "恭喜您帐号激活成功！请点击<a href='login.do'>登录</a>";
					ServletActionContext.getRequest().setAttribute("msg", msg);
				} else {
					msg = "注册失败";
					// 这里还要写一个用户删除账号和密码
					ServletActionContext.getRequest().setAttribute("msg", msg);
				}
			} else {
				msg = "连接失效,<strong><a href='reSend.do?id=" + userId
						+ "'>点击重新发送邮件</a></strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
			}
		}
		return SUCCESS;

	}

	/**
	 * 重新发送邮件
	 * 
	 * @throws Exception
	 */
	public String reSendEmail() throws Exception {
		DesSecurityUtil des = new DesSecurityUtil();
		String key1 = des.encrypt(ServletActionContext.getRequest().getParameter("id"));
	//	String key1=request.getString("id");		
		String key2 = des.encrypt(new Date().getTime() + "");
		String url = getPath(); // request().getRequestURI();
		String VerificationUrl = url + "verificationEmial.do?key=" + key1 + "-"
				+ key2;
		//long userId = Convert.strToLong(ServletActionContext.getRequest().getParameter("id"), -1);
		long userId=request.getLong("id", -1);		
		// 获取用户email地址 和 userName
		Map<String, String> reMap = null;

		reMap = userService.queryUserById(userId);

		if (null != reMap && reMap.size() > 0) {
			String userName = reMap.get("username");
			String email = reMap.get("email");
			// 发送验证邮件
			sendMailService.sendRegisterVerificationEmail(VerificationUrl,
					userName, email);
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
			session().setAttribute("mail", mailAddress);
		}
		return SUCCESS;
	}

	/**
	 * 邮箱提示信息跳转
	 */
	public String tip() {
		return SUCCESS;
	}

	/**
	 * 用户登录后的页面
	 * 
	 * @return
	 */
	public String jumpUser() {
		return SUCCESS;
	}

	/**
	 * 虚拟用户登录时没有权限跳转页面
	 * 
	 * @return
	 */
	public String noPermission() {
		return SUCCESS;
	}

	/**
	 * @MethodName: logout
	 * @Param: FrontLongRegisterAction
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午11:04:19
	 * @Return:
	 * @Descb: 退出系统
	 * @Throws:
	 */
	public void logout() throws Exception {
		request().getSession().removeAttribute("user");
		request().getSession().invalidate();
//		session().removeAttribute("bbs");
//		bbsRegisterService.doExitByAsynchronousMode();
		getOut().print("<script>parent.location.href='login.do';</script>");
	}
	
	
	public String forgetpasswordphones() throws Exception{
		String phone = request("phone");
		Map<String,String> map = null;
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		String isDemo = pf.read("isDemo");
		if(phone==null){
			return SUCCESS;
		}
		if(phone.matches("^[0-9]*$")){
			map = userService.queryUserByNameAndPhone(null, phone);
			map.put("isDemo", isDemo);
			map.put("cellPhones", map.get("cellPhone").replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
			session().setAttribute("maps", map);
		}else{
			map = userService.queryUserByNameAndPhone(phone, null);
			map.put("isDemo", isDemo);
			map.put("cellPhones", map.get("cellPhone").replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
			session().setAttribute("maps", map);
		}
		return SUCCESS;
	}
 
	public String CheackCode() throws IOException{
		String code = paramMap.get("code");
		JSONObject obj = new JSONObject();
		//图形验证码不区分大小写
		if(!(code.toLowerCase().equals((String)session("userregister_checkCode").toString().toLowerCase()))){
			obj.put("msg", false);
			JSONUtils.printObject(obj);
		}else{
			obj.put("msg", true);
			JSONUtils.printObject(obj);
		}
		return null;
	}
	
	/**
	 * 发送短信验证码
	 * @return
	 * @throws Exception
	 */
	public String sendPhoneCode() throws Exception{
		String phone = paramMap.get("phone");
		Map<String,String> map = null;
		JSONObject obj = new JSONObject();
		if(phone != null){
			map = userService.queryUserByNameAndPhone(null, phone);
			if(map!=null){
				int intCount = 0;
				intCount = (new Random()).nextInt(9999);
				if (intCount < 1000)
					intCount += 1000; 

				String randomCode = intCount + "";
				Map<String, String> maps = phonemsg.getSMSById(1);
				System.out.println(randomCode);
//				String content = "尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME
//						+ ",手机验证码为:[" + randomCode + "]";
				String content = "本次验证码为:"+ randomCode;
				String retCode = SMSUtil.sendMSM(maps.get("Account"), maps
						.get("Password"), content, phone, "");
				
				if("Sucess".equals(retCode)){
					obj.put("msg", true);
					session().setAttribute("phonecodes", randomCode);
					JSONUtils.printObject(obj);
				}else{
					obj.put("msg", false);
					JSONUtils.printObject(obj);
				}
			}
		}else{
			obj.put("msg", false);
			JSONUtils.printObject(obj);
		}
		return null;
	}
	
	/**
	 * 发送短信验证码（注册）
	 * @return
	 * @throws Exception
	 */
	public String sendPhoneCodeReg() throws Exception{
		String phone = paramMap.get("phone");
		JSONObject obj = new JSONObject();
		String pageId = paramMap.get("pageId"); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		
		Map<String,String> 	phonemap = userService.queryIsPhoneonUser(phone);
		
		//验证码不区分大小写
		if (code == null || !_code.toLowerCase().equals(code.toLowerCase())) {
			obj.put("msg", "-9999");
			JSONUtils.printObject(obj);
			return null;
		}
		
		if(phonemap!= null){   //判断手机号码是都否存在
			obj.put("msg", "1111");
			JSONUtils.printObject(obj);
			return null;
		}
			
		List<Map<String,Object>> list = sendSMSService.getNowDaySmsRecordList(phone);
		if(list!=null && list.size()>=10){
				obj.put("msg", false);
				JSONUtils.printObject(obj);
				return null;
			}

		if(phone != null){
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);
			if (intCount < 1000)
				intCount += 1000; 
			String randomCode = intCount + "";
			Map<String, String> maps = phonemsg.getSMSById(1);
			System.out.println(randomCode);
			String content = "本次验证码为:"+ randomCode;
			//发送验证码
			String retCode = SMSUtil.sendMSM(maps.get("Account"), maps
					.get("Password"), content, phone, "");
			
			if("Sucess".equals(retCode)){
				obj.put("msg", true);
				session().setAttribute("randomCode", randomCode);
				session().setAttribute("phone", phone);
				sendSMSService.insertSmsRecord(phone, content,"FrontLongRegisterAction.sendPhoneCodeReg",request().getRemoteAddr());
				JSONUtils.printObject(obj);
			}else{
				obj.put("msg", false);
				JSONUtils.printObject(obj);
			}
		}else{
			obj.put("msg", false);
			JSONUtils.printObject(obj);
		}
		return null;
	}
	
	public String checkPhoneCode() throws IOException{
		String code = request("code");
		String userId = request("id");
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		String isDemo = pf.read("isDemo");
		if("1".endsWith(isDemo)){
			session().setAttribute("userId", userId);
			return SUCCESS;
		}
		if(code!=null){
			String codes = (String) session("phonecodes");
			if(code.equals(codes)){
			session().setAttribute("userId", userId);
			return SUCCESS;
		}
	}
		return SUCCESS;
	}
	public String checkPhoneCode2() throws IOException{
		String code = paramMap.get("code");
		JSONObject obj = new JSONObject();
		if(code!=null){
			String codes = (String) session("phonecodes");
			if(code.equals(codes)){
				obj.put("msg", true);
				JSONUtils.printObject(obj);
				return null;
			}else{
				obj.put("msg", false);
				JSONUtils.printObject(obj);
				return null;
			}
		}else{
			obj.put("msg", false);
			JSONUtils.printObject(obj);
			return null;
		}
	}
	public String reUserPasswd() throws Exception{
		String id = paramMap.get("id");
		String passwd = paramMap.get("passwd");
		String confirmpassword = paramMap.get("confirmpassword");
		JSONObject obj = new JSONObject();
		if(passwd==null || confirmpassword==null){
			obj.put("msg", false);
			JSONUtils.printObject(obj);
			return null;
		}
		if(!confirmpassword.equals(passwd)){
			obj.put("msg", false);
			JSONUtils.printObject(obj);
			return null;
		}
		long l = userService.updateUserPassword(Convert.strToLong(id, -1L), passwd);
		if(l>0){
			obj.put("msg", true);
			JSONUtils.printObject(obj);
			return null;
		}else{
			obj.put("msg", false);
			JSONUtils.printObject(obj);
			return null;
		}
	}
	
	/**
	 * 交易密码重置
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public String reUserDealpwd() throws Exception {
		long id = Convert.strToLong(paramMap.get("id"),-1L);
		String passwd = paramMap.get("passwd");
		String confirmpassword = paramMap.get("confirmpassword");
		try {
			//新密码不能为空
			if(passwd == null || "".equals(passwd)){
				JSONUtils.printStr("1");
				return null;
			}
			//两次密码不相同
			if(!passwd.equals(confirmpassword)){
				JSONUtils.printStr("2");
				return null;
			}
			//密码长度为6-20个字符
			if (passwd.length() < 6 || passwd.length() > 20) {
				JSONUtils.printStr("3");
				return null;
			}
			//获得用户信息
			Map<String, String> map = homeInfoSettingService.getDealPwd(id);
			//交易密码不能和登录密码一样
			if(com.shove.security.Encrypt.MD5(passwd + IConstants.PASS_KEY).equals(map.get("password"))){
				JSONUtils.printStr("4");
				return null;
			}
			//账号异常
			boolean re = userService.checkSign(id);
			if(!re){
				JSONUtils.printStr("5");
				return null;
			}
			
			long result = homeInfoSettingService.updateUserPassword(id,	passwd, "deal");
			if(result > 0){
				JSONUtils.printStr("-1");
				return null;
			}else{
				JSONUtils.printStr("0");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	public String queryPhonePs() throws Exception{
		String cellphone  = paramMap.get("phone");
		JSONObject obj = new JSONObject();
		if(StringUtils.isBlank(cellphone)){
			JSONUtils.printObject(new JSONObject().put("msg", 0));
		}else{
			Map<String,String> user = null;
			if(cellphone.matches("^[0-9]*$")){
				user = userService.queryUserByNameAndPhone(null,cellphone);
			}else{
				user = userService.queryUserByNameAndPhone(cellphone,null);
			}
			if(user!=null){
				String phone = user.get("cellPhone");
				if(StringUtils.isNotBlank(phone)){
						obj.put("msg", 1);
						JSONUtils.printObject(obj);
						return null;
				}else{
					obj.put("msg", 2);
					JSONUtils.printObject(obj);
					return null;
				}
			}else{
				obj.put("msg", 0);
				JSONUtils.printObject(obj);
				return null;
			}
		}
		return null;
		
	}
	public String DelPhoneCode(){
		session().removeAttribute("phonecodes");
		return null;
	}
	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(
			RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
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

//	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
//		this.bbsRegisterService = bbsRegisterService;
//	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public SMSInterfaceService getPhonemsg() {
		return phonemsg;
	}

	public void setPhonemsg(SMSInterfaceService phonemsg) {
		this.phonemsg = phonemsg;
	}
	
	public  SendSMSService getSendSMSService() {
		return sendSMSService;
	}

	public  void setSendSMSService(SendSMSService sendSMSService) {
		this.sendSMSService = sendSMSService;
	}

}
