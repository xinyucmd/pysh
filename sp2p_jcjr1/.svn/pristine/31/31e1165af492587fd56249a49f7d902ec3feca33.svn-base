package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Param;
import org.json.simple.JSONArray;

import com.sp2p.service.SendMailService;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
//import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.BecomeToFinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;

/**
 * 我的帐户 个人设置
 * 
 * @author Administrator
 * 
 */
public class HomeInfoSettingAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(HomeInfoSettingAppAction.class);
	private static final long serialVersionUID = 1L;

	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;
	private BecomeToFinanceService becomeFinanceService;
	private AdminService adminService;
	private FundManagementService fundManagementService;

	private BeVipService beVipService;
	//private BBSRegisterService bbsRegisterService;
	private SendMailService sendMailService;
	private OperationLogService operationLogService;
	private RechargeService rechargeService;
	
	
	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public String homeInfoSettingInit() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 获取用户的信息
		Map<String, String> appAuthMap = getAppAuthMap();
		// 加载用户真实姓名
		String uid = appAuthMap.get("uid");
		Long id = Convert.strToLong(uid, -1l);// 获得用户编号
		try {
			Map<String, String> map = homeInfoSettingService.getRealNameByUserId(id);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", map.get("realName"));
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "14");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: renewalVIPInit
	 * @Param: HomeInfoSettingAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:37:37
	 * @Return:
	 * @Descb: 会员续费初始化
	 * @Throws:
	 */
	public String renewalVIPInit() throws Exception {
		User user = (User) session().getAttribute("user");
		try {
			Map<String, String> renewalVIPMap = homeInfoSettingService
					.queryRenewalVIP(user.getId());
			request().setAttribute("renewalVIPMap", renewalVIPMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: renewalVIPSubmit
	 * @Param: HomeInfoSettingAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午10:51:50
	 * @Return:
	 * @Descb: 提交会员续费
	 * @Throws:
	 */
	public String renewalVIPSubmit() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("code_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> renewalVIPMap = homeInfoSettingService
				.renewalVIPSubmit(user.getId(), getPlatformCost());
		String result = renewalVIPMap.get("result") == null ? ""
				: renewalVIPMap.get("result");
		userService.updateSign(user.getId());//更换校验码
		// 续费成功
		if ("1".equals(result)) {
			user.setVipStatus(IConstants.VIP_STATUS);
			session().setAttribute(IConstants.SESSION_USER, user);
			obj.put("msg", "VIP续费成功");
			JSONUtils.printObject(obj);
			return null;
		}
		obj.put("msg", result);
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * 修改个人头像的时候判断是否填写过个人信息
	 * 
	 * @return
	 */
	public String queryHeadImg() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (user.getRealName() == null || "".equals(user.getRealName())) {
			JSONUtils.printStr("1");
			return null;
		}
		Map<String, String> map = homeInfoSettingService.queryHeadImg(user
				.getRealName());
		if (map != null) {
			JSONUtils.printStr("2");
			return null;
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: updatePersonImg
	 * @Param: HomeInfoSettingAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:08:12
	 * @Return:
	 * @Descb: 修改个人头像
	 * @Throws:
	 */
	public String updatePersonImg() throws Exception {
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
//		String imgPath = paramMap.get("imgPath");
		String imgPath = request.getString("imgPath");
		JSONObject obj = new JSONObject();
		long returnId = -1;
		try {
			returnId = homeInfoSettingService.updatePersonImg(imgPath, user
					.getId());

			if (returnId <= 0) {
				obj.put("msg", IConstants.ACTION_FAILURE);
			} else {
				obj.put("msg", IConstants.ACTION_SUCCESS);
			}
			user.setPersonalHead(imgPath);
			session().setAttribute(IConstants.SESSION_USER, user);
			JSONUtils.printObject(obj);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 修改用户登录密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateLoginPass() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 获取用户的信息
		//User user = (User) session().getAttribute(IConstants.SESSION_USER);

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String oldPass = appInfoMap.get("oldPass") == null ? "" : appInfoMap.get("oldPass");
			String newPass = appInfoMap.get("newPass") == null ? "" : appInfoMap.get("newPass");
			String confirmPass = appInfoMap.get("confirmPass") == null ? "" : appInfoMap.get("confirmPass");
			String type = appInfoMap.get("type") == null ? "" : appInfoMap.get("type");// 用来标志修改的是登录密码还是交易密码
			// add by lw 判断交易面的长度 6 - 20
			if (newPass.length() < 6 || newPass.length() > 20) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "密码长度必须为6-20个字符");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// end
			if (!newPass.endsWith(confirmPass)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "两次密码不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String uid = appAuthMap.get("uid");
			Long id = Convert.strToLong(uid, -1l);// 获得用户编号
			Map<String, String> map = homeInfoSettingService.getDealPwd(id);
			boolean re = userService.checkSign(id);
			if(!re){
				jsonMap.put("error", "5");//账号异常 
				jsonMap.put("msg", "*修改失败！你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String password = null;
			if (type.endsWith("login")) {
				password = map.get("password");
				if(com.shove.security.Encrypt.MD5(newPass
						+ IConstants.PASS_KEY).equals(map.get("dealpwd"))){
					jsonMap.put("error", "6");//登录密码不能和交易密码一样 
					jsonMap.put("msg", "登录密码不能和交易密码一样！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} else {
				
				// 获得交易密码
				password = map.get("dealpwd");// 交易密码默认为登录密码
				if (password == null || password.equals("")) {
					password = map.get("password");
				}
				if(com.shove.security.Encrypt.MD5(newPass
						+ IConstants.PASS_KEY).equals(map.get("password"))){
					jsonMap.put("error", "7");//交易密码不能和登录密码一样
					jsonMap.put("msg", "交易密码不能和登录密码一样！");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				
			}
			if ("1".equals(IConstants.ENABLED_PASS)) {
				oldPass = com.shove.security.Encrypt.MD5(oldPass);
			} else {
				oldPass = com.shove.security.Encrypt.MD5(oldPass
						+ IConstants.PASS_KEY);
			}
			if (!oldPass.endsWith(password)) {// 旧密码输入错误
				jsonMap.put("error", "2");
				jsonMap.put("msg", "旧密码输入错误！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long result = homeInfoSettingService.updateUserPassword(id,
					newPass, type);

			if (result < 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "新密码修改失败");
			} else {
				//bbsRegisterService.doUpdatePwdByAsynchronousMode(map.get("username"), newPass, oldPass, 1);
//				user.setEncodeP(
//						Encrypt.encryptSES(newPass, IConstants.PWD_SES_KEY));
//				User user1 = (User)session().getAttribute("user");
//				user1.setPassword(Encrypt.MD5(newPass.trim()+IConstants.PASS_KEY)+"");
//				session().setAttribute("user", user1);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改密码成功");
			}
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "14");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询银行卡信息，以表格显示
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 * @throws IOException
	 */
	public String queryBankInfoInit() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 获取用户的信息
		Map<String, String> appAuthMap = getAppAuthMap();
		// 加载用户真实姓名
		String uid = appAuthMap.get("uid");
		Long id = Convert.strToLong(uid, -1l);// 获得用户编号
		try {
			List<Map<String, Object>> lists = homeInfoSettingService
					.queryBankInfoList(id);
			jsonMap.put("bankLists", lists);
		} catch (Exception e) {
			jsonMap.put("error", "14");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 添加提现银行信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addBankInfo() throws Exception {
		String cardUserName = Convert.strToStr(paramMap.get("cardUserName"),
				null);
		String bankName = Convert.strToStr(paramMap.get("bankName"), null);
		String subBankName = Convert
				.strToStr(paramMap.get("subBankName"), null);
		String bankCard = Convert.strToStr(paramMap.get("bankCard"), null);
		String dealpwd = Convert.strToStr(paramMap.get("dealpwd"), null);
		

		if(bankName==null||subBankName==null||bankCard==null||cardUserName==null||dealpwd==null){
			JSONUtils.printStr("3");
			return null;
		}
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String pwd=Convert.strToStr(userService.queryUserById(user.getId()).get("dealpwd"), "");
		if(!dealpwd.equals(pwd)){
			JSONUtils.printStr("5");
			return null;
		}
		Long id = user.getId();// 获得用户编号
		try {
			Map<String, String> map = homeInfoSettingService
					.queryCardStatus(id);
			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			if (bindingCardNum >= 2) {// 已经绑定两张银行卡，不能再绑定了
				JSONUtils.printStr("2");
				return null;
			}
			// 新添加的提现银行卡信息状态为2，表示申请中
			long result = homeInfoSettingService.addBankCardInfo(id,
					cardUserName, bankName, subBankName, bankCard,
					IConstants.BANK_SUCCESS,"","");
			operationLogService.addOperationLog("t_bankcard", user
					.getUserName(), IConstants.INSERT, user.getLastIP(), 0,
					"添加提现银行信息", 1);

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 删除提现银行卡信息（这里删除未绑定的银行卡）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteBankInfo() throws Exception {
		Long id = request.getLong("bankId", -1L);
		User user = (User) session().getAttribute("user");
		try {
			long result = homeInfoSettingService.deleteBankInfo(id);
			// 添加系统操作日志
			if(result>0){
			operationLogService.addOperationLog("t_bankcard", user
					.getUserName(), IConstants.DELETE, user.getLastIP(), 0,
					"删除未绑定的银行卡信息", 1);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 手机绑定页面加载
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bindingMobileInit() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		try {
			// 查询成功绑定的手机信息
			// Map<String,String> map = homeInfoSettingService.
			// querySucessBindingInfoByUserId(user.getId(),1);
			Map<String, String> map = homeInfoSettingService
					.querySucessBindingInfoByUserId(user.getId());
			JSONObject object = new JSONObject();
			if (map == null) {
				object.put("map", "");
			} else {
				object.put("map", map);
			}
			JSONUtils.printObject(object);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 添加手机号码绑定信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long addBindingMobile() throws Exception {
		// 为空在jsp页面已经验证
		String mobile = Convert.strToStr(paramMap.get("mobile"), null);
		String code = Convert.strToStr(paramMap.get("code"), null);
		String content = Convert.strToStr(paramMap.get("content"), "");

		// 手机号码验证 whb
//		Pattern p = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//		Matcher m = p.matcher(mobile);
		if (!mobile.matches("^[1][3|4|5|7|8|][0-9]{9}$")) {// 手机号码无效
			JSONUtils.printStr("1");
			return null;
		}

		// ..............................................
		// 手机号码与验证码号码匹配
		Object objcet = session().getAttribute("phone");
		if (objcet != null) {
			String phonecode = objcet.toString();
			if (!phonecode.trim().equals(mobile.trim())) {

				JSONUtils.printStr("10");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// 验证码

		if (StringUtils.isBlank(code)) {

			JSONUtils.printStr("12");
			return null;
		}
		Object obje = session().getAttribute("randomCode");
		if (obje != null) {
			String randomCode = obje.toString();
			if (!randomCode.trim().equals(code.trim())) {

				JSONUtils.printStr("13");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// ..........................................................

		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		try {
			// 首先查看该用户有没有设置手机绑定
			Map<String, String> mp = homeInfoSettingService
					.queryBindingInfoByUserId(id);
			if (mp != null) {// 如果该用户已经绑定了手机号码信息
				String status = Convert.strToStr(mp.get("status"), null);// 查看手机状态
				if (status != null) {
					if (status.equals(IConstants.PHONE_BINDING_ON + "")) {// 手机号码已经绑定，需要申请更换手机
						JSONUtils.printStr("7");
						return null;
					} else if (status.equals(IConstants.PHONE_BINDING_CHECK
							+ "")) {// 手机号码正在审核，请等待

						JSONUtils.printStr("8");
						return null;
					} else if (status.equals(IConstants.PHONE_BINDING_UNPASS
							+ "")) {// 手机审核不通过
						JSONUtils.printStr("9");
						return null;
					}
				}
			}

			// 查看填写的手机号码是不是已经被别人绑定或者在申请绑定
			Map<String, String> map = homeInfoSettingService
					.queryBindingMobile(mobile);

			if (map != null) {
				String status = Convert.strToStr(map.get("status"), null);
				if (status != null) {
					if (status.equals(IConstants.PHONE_BINDING_ON + "")) {// 手机号码已经绑定，需要申请更换手机
						JSONUtils.printStr("3");
						return null;
					} else if (status.equals(IConstants.PHONE_BINDING_CHECK
							+ "")) {// 手机号码正在审核，请等待
						session().removeAttribute("randomCode");
						JSONUtils.printStr("4");
						return null;
					}
				}
			}

			// add by lw 查询已经绑定的手机号码
			Map<String, String> phoneMap = null;
			String oldPhone = null;
			phoneMap = beVipService.queryPUser(id);
			if (phoneMap.size() > 0 && phoneMap != null) {
				oldPhone = phoneMap.get("cellphone");
			}
			// end

			// 添加手机绑定信息，手机绑定状态位2.2代表正在审核
			Long result = homeInfoSettingService.addBindingMobile(mobile, id,
					IConstants.PHONE_BINDING_CHECK, content, oldPhone);
			if (result < 0) {// 手机绑定失败
				JSONUtils.printStr("5");
				return null;
			}

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 手机变更
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public Long addChangeBindingMobile() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		Map<String, String> appInfoMap = getAppInfoMap();
		
		long id = Convert.strToLong(authMap.get("uid"), -1);// 获得用户编号
		String code = appInfoMap.get("code");
		String reason = appInfoMap.get("reason");
		String phone = appInfoMap.get("phone");
		// 测试--跳过验证码
		if (IConstants.ISDEMO.equals("2")) {
			// 手机号码与验证码号码匹配
			String phones = (String) session("phone");
			if(!phone.equals(phones)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号码与获取验证码手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 验证码
			String codes = (String) session("phonecodes");
			//校验不通过
			if(!code.equals(codes)){
				jsonMap.put("error", "3");
				jsonMap.put("msg", "验证码输入错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		}
				
		// ..........................................................

		try {
			// 获取用户的信息
			List<Map<String, Object>> mp = homeInfoSettingService.queryBindingsByUserId(id);//首先查看该用户有没有设置手机绑定
			if (mp == null) {// 如果该用户没有绑定了手机号码信息
				jsonMap.put("error", "4");
				jsonMap.put("msg", "您还没有进行手机绑定，请先申请手机绑定");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {// 查看用户手机的状态 是否已经申请了变更
				for (Map<String, Object> mpp : mp) {
					String status = Convert.strToStr(mpp.get("status")
							.toString(), null);
					if (status != null) {
						if (status.equals(IConstants.PHONE_BINDING_CHECK + "")) {// 绑定手机还在审核中，不能变更
							jsonMap.put("error", "5");
							jsonMap.put("msg", "您已经申请的手机信息还在审核，请等待后台审核");
							JSONUtils.printObject(jsonMap);
							return null;
						} else if (status
								.equals(IConstants.PHONE_BINDING_UNPASS + "")) {// 手机审核不通过
							jsonMap.put("error", "6");
							jsonMap.put("msg", "您的手机变更不通过，请联系客服人员");
							JSONUtils.printObject(jsonMap);
							return null;
						}
					}
				}
			}
			// 查看变更的手机号码是否别人绑定了
//			Map<String, String> map = homeInfoSettingService
//					.queryBindingMobile(phone);
//
//			if (map != null) {
//				String status = Convert.strToStr(map.get("status"), null);
//				if (status != null) {
//					if (status.equals(IConstants.PHONE_BINDING_ON + "")) {// 手机号码已经被别人绑定，需要申请更换手机
//						JSONUtils.printStr("6");
//						return null;
//					} else if (status.equals(IConstants.PHONE_BINDING_CHECK
//							+ "")) {// 手机号码正在审核，请等待
//						JSONUtils.printStr("7");
//						return null;
//					} else if (status.equals(IConstants.PHONE_BINDING_UNPASS
//							+ "")) {// 手机审核不通过
//						JSONUtils.printStr("9");
//						return null;
//					}
//				}
//			}

			// add by lw 查询已经绑定的手机号码
			Map<String, String> phoneMap = null;
			String oldPhone = null;
			phoneMap = beVipService.queryPUser(id);
			if (phoneMap.size() > 0 && phoneMap != null) {
				oldPhone = phoneMap.get("cellphone");
			}
			// end
			// 进行手机变更（状态为正在审核）
			Long result = homeInfoSettingService.addBindingMobile(phone, id,
					IConstants.PHONE_BINDING_CHECK, reason, oldPhone);
			if (result < 0) {// 手机变更失败
				jsonMap.put("error", "7");
				jsonMap.put("msg", "变更失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			operationLogService.addOperationLog("t_phone_binding_info", phoneMap.get("userId"), IConstants.INSERT, "", 0,
					"发布手机变更请求", 1);
			//session().removeAttribute("randomCode");
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "您的变更申请已提交，请等待审核，如有问题请联系客服");
			JSONUtils.printObject(jsonMap);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 通知设置加载，加载的时候从数据库中读取已经设置的数据 查询两个地方
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryNotesSettingInit() throws Exception {
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号
		try {
			Map<String, String> notes = homeInfoSettingService
					.queryNotesList(id);
			List<Map<String, Object>> lists = homeInfoSettingService
					.queryNotesSettingList(id);

			if (lists == null) {
				JSONUtils.printStr("1");
				return null;
			} else {
				if (notes == null && lists.size() <= 0) {// 没有值
					JSONUtils.printStr("1");
					return null;
				}
			}

			List<Map<String, Object>> values = changeList2List(notes, lists);
			String jsonStr = JSONArray.toJSONString(values);
			JSONUtils.printStr(jsonStr);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return null;
	}

	private List<Map<String, Object>> changeList2List(
			Map<String, String> notes, List<Map<String, Object>> lists) {
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		boolean message = false, mail = false, note = false;
		if (notes.get("mailNoticeEnable").equals(IConstants.NOTICE_ON + "")) {// 2为开启状态
			message = true;
		}
		if (notes.get("emailNoticeEnable").equals(IConstants.NOTICE_ON + "")) {
			mail = true;
		}
		if (notes.get("noteNoticeEnable").equals(IConstants.NOTICE_ON + "")) {
			note = true;
		}
		Map<String, Object> val = null;
		if (lists != null && lists.size() > 0) {
			for (Map<String, Object> o : lists) {
				val = add(message, mail, note, o.get("noticeMode"), o
						.get("reciveRepayEnable"), o.get("showSucEnable"), o
						.get("loanSucEnable"), o.get("rechargeSucEnable"), o
						.get("capitalChangeEnable"));
				values.add(val);
			}
		}
		return values;
	}

	private Map<String, Object> add(boolean message, boolean mail,
			boolean note, Object noticeMode, Object reciveRepayEnable,
			Object showSucEnable, Object loanSucEnable,
			Object rechargeSucEnable, Object capitalChangeEnable) {
		Map<String, Object> mg = new HashMap<String, Object>();
		mg.put("message", message);
		mg.put("mail", mail);
		mg.put("note", note);
		mg.put("noticeMode", noticeMode);// 通知方式(1 邮件 2 站内信 3 短信)
		mg.put("reciveRepayEnable", reciveRepayEnable);
		mg.put("showSucEnable", showSucEnable);
		mg.put("loanSucEnable", loanSucEnable);
		mg.put("rechargeSucEnable", rechargeSucEnable);
		mg.put("capitalChangeEnable", capitalChangeEnable);
		return mg;
	}

	/**
	 * 添加通知设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long addNotesSetting() throws Exception {
		// 站内信

		boolean message = paramMap.get("message") == null ? false : true;// Convert.strToBoolean(paramMap.get("message"),false);//站内信总复选框
		boolean messageReceive = paramMap.get("messageReceive") == null ? false
				: true;
		boolean messageDeposit = paramMap.get("messageDeposit") == null ? false
				: true;
		boolean messageBorrow = paramMap.get("messageBorrow") == null ? false
				: true;
		boolean messageRecharge = paramMap.get("messageRecharge") == null ? false
				: true;
		boolean messageChange = paramMap.get("messageChange") == null ? false
				: true;
		// 邮件
		boolean mail = paramMap.get("mail") == null ? false : true;
		boolean mailReceive = paramMap.get("mailReceive") == null ? false
				: true;
		boolean mailDeposit = paramMap.get("mailDeposit") == null ? false
				: true;
		boolean mailBorrow = paramMap.get("mailBorrow") == null ? false : true;
		boolean mailRecharge = paramMap.get("mailRecharge") == null ? false
				: true;
		boolean mailChange = paramMap.get("mailChange") == null ? false : true;
		// 短信
		boolean notes = paramMap.get("note") == null ? false : true;
		boolean noteReceive = paramMap.get("noteReceive") == null ? false
				: true;
		boolean noteDeposit = paramMap.get("noteDeposit") == null ? false
				: true;
		boolean noteBorrow = paramMap.get("noteBorrow") == null ? false : true;
		boolean noteRecharge = paramMap.get("noteRecharge") == null ? false
				: true;
		boolean noteChange = paramMap.get("noteChange") == null ? false : true;

		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号
		Map<String, String> map = userService.queryUserById(id);
		if (mail) {
			String ismail = Convert.strToStr(map.get("email"), "");
			if (StringUtils.isBlank(ismail)) {
				JSONUtils.printStr("3");
				return null;
			}
		}
		try {
			long result = homeInfoSettingService.addNotesSetting(id,
					messageReceive, messageDeposit, messageBorrow,
					messageRecharge, messageChange, mailReceive, mailDeposit,
					mailBorrow, mailRecharge, mailChange, noteReceive,
					noteDeposit, noteBorrow, noteRecharge, noteChange);

			long result2 = homeInfoSettingService.addNotes(id, message, mail,
					notes);
			// 添加操作日志
			long result3 = operationLogService.addOperationLog("t_noticecon",
					user.getUserName(), IConstants.UPDATE, user.getLastIP(), 0,
					"修改通知设置", 1);
			if (result < 0 || result2 < 0 || result3 < 0) {// 设置失败
				JSONUtils.printStr("1");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public String mailNoticeInit() throws SQLException, DataException {
		// 加载邮件信息
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		request().setAttribute("userName", user.getUserName());
		return SUCCESS;
	}

	/**
	 * 判断收件人是否有效
	 * 
	 * @return
	 * @throws Exception
	 */
	public String judgeUserName() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String receiver = paramMap.get("receiver"); // 收件人
		try {
			// 检查用户名是否存在 t_user
			long result = homeInfoSettingService.getConcernList(user.getId(),
					receiver);
			if (result < 0) { // 用户名不存在
				// 到t_admin表中检查用户名
				List<Map<String, Object>> lists = adminService.queryAdminList(
						receiver, 1);
				if (lists == null || lists.size() <= 0) {
					JSONUtils.printStr("1");
					return null;
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
	 * 添加邮件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addMail() throws Exception {
		String receiver = Convert.strToStr(paramMap.get("receiver"), null);
		String title = Convert.strToStr(paramMap.get("title"), null);
		String content = Convert.strToStr(paramMap.get("content"), null);
		String pageId = paramMap.get("pageId"); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		if (code == null || !_code.equals(code)) {
			JSONUtils.printStr(IConstants.USER_REGISTER_CODE_ERROR);
			return null;
		}
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Integer enable = user.getEnable();
		if (enable == 3) {
			JSONUtils.printStr("8");
			return null;
		}
		Long id = user.getId();// 获得用户编号
		try {
			// 前台页面进行了判断，这里名称不可能为空
			Map<String, String> map = userService.queryIdByUser(receiver);
			Long receiverId = -2L;
			if (map == null || map.size() < 0) {// 到t_admin表中查数据
				List<Map<String, Object>> lists = adminService.queryAdminList(
						receiver, 1);
				receiverId = Convert.strToLong(lists.get(0).get("id")
						.toString(), -1L);
			} else {
				receiverId = Convert.strToLong(map.get("id"), -1L);
			}

			long result = -1;
			/**
			 * 如果是发给admin，系统管理员，则该邮件为系统邮件(如果发件人或者收件人为admin,则为系统消息)
			 */
			if (receiver.equalsIgnoreCase(IConstants.MAIL_SYS)) {// 新发送的邮件默认为未读
																	// IConstants.MAIL_UN_READ
				result = homeInfoSettingService.addMail(id, receiverId, title,
						content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_SYS);
			} else if (user.getUserName().equalsIgnoreCase(IConstants.MAIL_SYS)) {
				result = homeInfoSettingService.addMail(id, receiverId, title,
						content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_SYS);
			} else {
				result = homeInfoSettingService.addMail(id, receiverId, title,
						content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_COMMON);
			}
			if (result < 0) {
				JSONUtils.printStr("1");
				return null;
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 获得用户的收件箱信息(一般信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryReciveMails() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		int mailStatus = paramMap.get("mailStatus") == null ? -1 : Convert
				.strToInt(paramMap.get("mailStatus"), -1);
		try {

			homeInfoSettingService.queryReceiveMails(pageBean, id,
					IConstants.MALL_TYPE_COMMON, "", mailStatus);
			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null)
				changeLists2Lists(lists, "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String querySendMailsInit() {
		return SUCCESS;
	}

	public String queryReceiveMailsInit() {
		return SUCCESS;
	}

	public String querySysMailsInit() {
		return SUCCESS;
	}

	/**
	 * 获得用户的发件箱信息(一般信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String querySendMails() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		try {
			homeInfoSettingService.querySendMails(pageBean, id);
			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null)
				changeLists2Lists2(lists);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 获得用户系统信息
	 * 
	 * @throws Exception
	 */
	public String querySysMails() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int mailStatus = paramMap.get("mailStatus") == null ? -1 : Convert
				.strToInt(paramMap.get("mailStatus"), -1);
		try {
			homeInfoSettingService.queryReceiveMails(pageBean, id,
					IConstants.MALL_TYPE_SYS, "sys", mailStatus);

			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null) {
				changeLists2Lists(lists, "sys");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 更改lists里面的一些信息。这样前台直接显示。 将用户id改成用户名，信息状态更改中文显示
	 * 
	 * @throws Exception
	 */
	private void changeLists2Lists(List<Map<String, Object>> lists, String type)
			throws Exception {
		String username = "";
		Map<String, String> mp = null;
		int status = -1;
		try {
			for (Map<String, Object> map : lists) {
				if (type.equalsIgnoreCase("sys")) {
					mp = adminService.queryAdminById(Convert.strToLong(map.get(
							"sender").toString(), -1));
					if (mp != null && mp.size() > 0) {
						username = Convert.strToStr(mp.get("userName"), "");
						map.put("sender", username);
					}
				} else {
					mp = userService.queryUserById(Convert.strToLong(map.get(
							"sender").toString(), -1));
					if (mp != null && mp.size() > 0) {
						username = Convert.strToStr(mp.get("username"), "");
						map.put("sender", username);
					}
				}
				status = Convert.strToInt(map.get("mailStatus").toString(), -1);
				if (status == IConstants.MAIL_READED) {
					map.put("mailStatus", "已读");
				} else if (status == IConstants.MAIL_UN_READ) {
					map.put("mailStatus", "未读");
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	private void changeLists2Lists2(List<Map<String, Object>> lists)
			throws Exception {
		String username = "";
		for (Map<String, Object> map : lists) {
			username = this.getUserNameById(Convert.strToLong(map
					.get("reciver").toString(), -1));
			if (username.equals("")) {
				username = this.getAdminNameById(Convert.strToLong(map.get(
						"reciver").toString(), -1));
			}
			map.put("reciver", username);
		}
	}

	public String deleteMails() throws Exception {
		String ids = request.getString("ids");
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String[] allIds = ids.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		homeInfoSettingService.deleteMails(ids, user.getId());
		return SUCCESS;
	}

	/**
	 * 更新邮件状态
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMail() throws Exception {
		String ids = Convert.strToStr(paramMap.get("ids"), "");
		String type = Convert.strToStr(paramMap.get("type"), "");
		String[] allIds = ids.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		try {
			long result = -1;
			// 站内信状态(1 默认未读 2 删除 3 已读)
			if (type.equals("readed")) {// 标记为已读
				result = homeInfoSettingService.updateMails(ids,
						IConstants.MAIL_READED);
			} else if (type.equals("unread")) {// 标记为未读
				result = homeInfoSettingService.updateMails(ids,
						IConstants.MAIL_UN_READ);
			}
			if (result < 0)
				return null;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 根据用户id获得用户名
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private String getUserNameById(long userId) throws Exception {
		try {
			Map<String, String> mp = userService.queryUserById(userId);
			if (mp != null) {
				return Convert.strToStr(mp.get("username"), "");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "";
	}

	private String getAdminNameById(long adminId) throws Exception {
		try {
			Map<String, String> mp = adminService.queryAdminById(adminId);
			if (mp != null) {
				return Convert.strToStr(mp.get("userName"), "");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "";
	}

	/**
	 * 查询邮件内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryEmailById() throws Exception {
		Long mailId = request.getLong("mailId", -1);
		int type = request.getInt("type", 0);
		int curPage = request.getString("curPage") == null ? 1 : request.getInt("curPage", 1);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		try {
			Map<String, String> map = homeInfoSettingService
					.queryEmailById(mailId);
			if (map == null) {
				return null;
			}
			Long userId = user.getId();
			@SuppressWarnings("unused")
			long result = -1;
			if (type == 1) {// 如果是未读信息，则更新数据库，将状态改为已读
				// add by houli
				if (user.getVirtual() != 1) {// virtual=1 是后台虚拟用户登录，不用改变邮件状态
					result = homeInfoSettingService.updateMails(mailId + "",
							IConstants.MAIL_READED);
				}
			}
			String sender = "", receiver = "", title = "", date = "", content = "";

			int mt = 0;// 发件箱
			if (map.get("sender").equals(userId + "")) {
				sender = user.getUserName();
				mt = 100;// 标记发件箱
			} else {
				if (map.get("mailType").equals(IConstants.MAIL_SYS_ + "")) {
					sender = getAdminNameById(Convert.strToLong(map
							.get("sender"), -1));
				} else {
					sender = getUserNameById(Convert.strToLong(map
							.get("sender"), -1));

				}
			}

			if (map.get("reciver").equals(userId + "")) {
				receiver = user.getUserName();
			} else {
				if (map.get("mailType").equals(IConstants.MAIL_SYS_ + "")) {
					receiver = getAdminNameById(Convert.strToLong(map
							.get("reciver"), -1));
				} else {
					receiver = getUserNameById(Convert.strToLong(map
							.get("reciver"), -1));
				}
			}
			// 操作日志
			operationLogService.addOperationLog("t_mail", user.getUserName(),
					IConstants.UPDATE, user.getLastIP(), 0, "查看站内信", 1);
			title = map.get("mailTitle");
			date = map.get("sendTime");
			content = map.get("mailContent").replace("-1.00", "0");
			request().setAttribute("sender", sender);
			request().setAttribute("receiver", receiver);
			request().setAttribute("title", title);
			request().setAttribute("date", date);
			request().setAttribute("content", content);
			request().setAttribute("curPage", curPage);
			if (mt == 100) {
				request().setAttribute("mType", 100);
			} else {
				request().setAttribute("mType",
						Convert.strToInt(map.get("mailType") + "", 0));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 成为理财人页面初始化
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String become2FinanceInit() throws Exception {
		// 成为理财人必须是在会员登录以后
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		try {
			Map<String, String> map = becomeFinanceService
					.queryFinancer(userId);
			if (map == null) {// 没有记录，非理财人
				return INPUT;
			} else {
				int status = Convert.strToInt(map.get("status"), 1);
				if (status == IConstants.FINANCE_NON) {// 如果已经是填写了理财人的信息，
					return "waiting";
				} else {
					return SUCCESS;
				}
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	public String queryOneBankInfo() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);

		Long bankId = request.getLong("bankId", -100);
		try {
			Map<String, String> map = fundManagementService
					.queryOneBank(bankId);
			request().setAttribute("bankCard", map.get("cardNo"));
			request().setAttribute("bankId", map.get("id"));
			request().setAttribute("realName", user.getRealName());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String updateBankInfo() throws Exception {
		User user = (User) session().getAttribute("user");
		Long bankId = paramMap.get("bankId") == null ? -100 : Convert
				.strToLong(paramMap.get("bankId"), -100);
		String mBankName = paramMap.get("mBankName") == null ? null : Convert
				.strToStr(paramMap.get("mBankName"), null);
		String mSubBankName = paramMap.get("mSubBankName") == null ? null
				: Convert.strToStr(paramMap.get("mSubBankName"), null);
		String mBankCard = paramMap.get("mBankCard") == null ? null : Convert
				.strToStr(paramMap.get("mBankCard"), null);
		String dealpwd = paramMap.get("dealpwd") == null ? null : Convert
				.strToStr(paramMap.get("dealpwd"), null);
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		 
		if(!dealpwd.equals(user.getDealpwd())){
			JSONUtils.printStr("2");
			return null;
		}

		try {
			Long result = fundManagementService.updateChangeBank(bankId,
					mBankName, mSubBankName, mBankCard, IConstants.BANK_CHECK,
					new Date(), true);
			if (result < 0) {
				JSONUtils.printStr("1");
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
	 * 取消银行卡变更
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bankChangeCancel() throws Exception {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> appAuthMap = getAppAuthMap();
		Long bankId = Convert.strToLong(appInfoMap.get("bankId"), -100);
		String uid = appAuthMap.get("uid");
		Long userId = Convert.strToLong(uid, -1l);// 获得用户编号
		try {
			Map<String, String> map = homeInfoSettingService.getDealPwd(userId);
			Long result = fundManagementService.updateChangeBank(bankId, "",
					"", "", IConstants.BANK_SUCCESS, null, false);
			result = operationLogService.addOperationLog("t_bankcard", map.get("username")
					, IConstants.UPDATE, map.get("lastIP"), 0,
					"取消银行卡变更", 1);
			
			if (result < 0) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "取消变更失败");
			}else{
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "取消变更成功");
			}
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "14");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public String financerWaiting() throws SQLException, DataException {
		return SUCCESS;
	}
	/**
	 * 检查用户回答密保问题是否正确
	 * @return
	 * @throws Exception
	 */
	public String checkUserAnswer() throws Exception{
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		
		String answerOne = request.getString("answerOne");
		String answerTwo = request.getString("answerTwo");
		String answerThree = request.getString("answerThree");
		Map<String, String> map = homeInfoSettingService
		.queryUserALLAnswer(userId);
		if (null!=map ){
			request().setAttribute("questionOne", map.get("questionOne"));
			request().setAttribute("questionTwo", map.get("questionTwo"));
			request().setAttribute("questionThree", map.get("questionThree"));
			if (IConstants.ISDEMO.equals("1")) {
				return "success";
			}
			if((!answerOne.equals(map.get("answerOne")))||(!answerTwo.equals(map.get("answerTwo")))
					||(!answerThree.equals(map.get("answerThree")))){
				this.addFieldError("paramMap.code", "答案错误！");
				return INPUT;
			}else{
					return "success";
			}
		}
		return "404";
	}
	/**
	 * 检查用户回答密保问题是否正确
	 * @return
	 * @throws Exception
	 */
	public String checkUserAnswera() throws Exception{
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		List<Map<String, Object>> list = null;
		list = userService.queryAllQuestionList();
		if (list != null && list.size() > 0) {
			request().setAttribute("list", list);
		}
		if (IConstants.ISDEMO.equals("1")) {
			return "success";
		}
		String answerOne = request.getString("answerOne");
		String answerTwo = request.getString("answerTwo");
		String answerThree = request.getString("answerThree");
		Map<String, String> map = homeInfoSettingService
		.queryUserALLAnswer(userId);
		if (null != map ) {
			request().setAttribute("questionOne", map.get("questionOne"));
			request().setAttribute("questionTwo", map.get("questionTwo"));
			request().setAttribute("questionThree", map.get("questionThree"));
			if((!answerOne.equals(map.get("answerOne")))||(!answerTwo.equals(map.get("answerTwo")))
					||(!answerThree.equals(map.get("answerThree")))){
				this.addFieldError("paramMap.code", "答案错误！");
				return INPUT;
			}else{
				return "success";
			}
		}
		return "404";
	}
	
	
	public String setPwd() throws Exception{
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		String flag = request.getString("flag");
		request().setAttribute("flag", flag);
		return "success";
	}
	
	/**
	 * 获取用户设置的密保问题
	 * @return
	 * @throws Exception
	 */
	public String getUserQuestion() throws Exception{
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		if(isApplyPro == 1){
			return "index";
		}
		try {
			Map<String, String> map = homeInfoSettingService
					.queryUserALLAnswer(userId);
			if(null!=map){
				request().setAttribute("questionOne", map.get("questionOne"));
				request().setAttribute("questionTwo", map.get("questionTwo"));
				request().setAttribute("questionThree", map.get("questionThree"));
			}
			String bindingPhone = null;
			int status = -1;
			Map<String, String> m = userService.queryUserBindphone(userId);
			if (m != null && m.size() > 0) {
				if (m.get("bindingPhone") != null) {
					bindingPhone = m.get("bindingPhone").toString();
				}
				if (m.get("status") != null) {
					status = Convert.strToInt(m.get("status")
							.toString(), -1);
				}

				// 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
				if (bindingPhone == null
						|| status != IConstants.PHONE_BINDING_ON || bindingPhone=="") {
					bindingPhone = m.get("cellPhone") + "";
				}
				if (bindingPhone == null || bindingPhone=="" ) {
					bindingPhone = m.get("mobilePhone").toString();
				}
			}
			request().setAttribute("bindingPhone", bindingPhone);
			request().setAttribute("ISDEMO", IConstants.ISDEMO);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "success";
	}
	/** 保存或修改用户密保答案及提问
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String saveOrUpdatePwdAnswer() throws Exception{
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		
		String questionOne = paramMap.get("questionOne").trim();
		String questionTwo = paramMap.get("questionTwo").trim();
		String questionThree = paramMap.get("questionThree").trim();
		String answerOne = paramMap.get("answerOne").trim();
		String answerTwo = paramMap.get("answerTwo").trim();
		String answerThree = paramMap.get("answerThree").trim();
		long result=-1;
		//判定用户是否已经申请密保，没有申请就表示保存，有申请就表示修改
		if(isApplyPro==1){
			 result = homeInfoSettingService.savePwdAnswer(userId, questionOne, questionTwo, 
					questionThree, answerOne, answerTwo, answerThree);
			 if (result < 0) {
					JSONUtils.printStr("1");
				     return null;
				}else{
					JSONUtils.printStr("2");
					userService.updatePwdProState(userId);
				     return null;
				}
		}
		 result = homeInfoSettingService.updatePwdAnswer(userId, questionOne, questionTwo,
				 questionThree, answerOne, answerTwo, answerThree);
		 if (result < 0) {
				JSONUtils.printStr("3");
			     return null;
			}else{
				JSONUtils.printStr("4");
				userService.updatePwdProState(userId);
			     return null;
			}
	}
	
	
	public String addBecomeFinance() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		String realName = paramMap.get("realName");
		String cellPhone = paramMap.get("cellPhone");
		String idNo = paramMap.get("idNo");
		String code = paramMap.get("send_phoneCode");

		// 手机号码验证 whb
//		Pattern p = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//		Matcher m = p.matcher(mobile);
		if (!cellPhone.matches("^[1][3|4|5|7|8|][0-9]{9}$")) {// 手机号码无效
			JSONUtils.printStr("7");
			return null;
		}
		if (StringUtils.isBlank(cellPhone)) {

			JSONUtils.printStr("8"); // 手机号为空
			return null;
		}

		// ..............................................
		// 手机号码与验证码号码匹配
		Object objcet = session().getAttribute("phone");
		if (objcet != null) {
			String phonecode = objcet.toString();
			if (!phonecode.trim().equals(cellPhone.trim())) {

				JSONUtils.printStr("10");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// 验证码

		if (StringUtils.isBlank(code)) {

			JSONUtils.printStr("12");
			return null;
		}
		Object obje = session().getAttribute("randomCode");
		if (obje != null) {
			String randomCode = obje.toString();
			if (!randomCode.trim().equals(code.trim())) {

				JSONUtils.printStr("13");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// ..........................................................

		try {
			long result = becomeFinanceService.addBecomeFinancer(userId,
					realName, cellPhone, idNo, IConstants.FINANCE_NON);

			if (result < 0) {
				JSONUtils.printStr("1");
			}
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 邮件回复
	 * 
	 * @return
	 * @throws Exception
	 */
	public String replayMail() throws Exception {

		Long mailId = request.getLong("id", -1L);
		int type = request.getInt("type", 0);

		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();

		try {
			Map<String, String> map = homeInfoSettingService
					.queryEmailById(mailId);
			if (map == null) {
				return null;
			}
			long result = -1;
			if (type == 1) {// 如果是未读信息，则更新数据库，将状态改为已读
				result = homeInfoSettingService.updateMails(mailId + "",
						IConstants.MAIL_READED);
			}
			String sender = "", receiver = "", title = "", date = "", content = "";

			if (map.get("sender").equals(userId + "")) {
				sender = user.getUserName();
			} else {
				sender = getUserNameById(Convert.strToLong(map.get("sender"),
						-1));
			}

			if (map.get("reciver").equals(userId + "")) {
				receiver = user.getUserName();
			} else {
				receiver = getUserNameById(Convert.strToLong(
						map.get("reciver"), -1));
			}

			title = map.get("mailTitle");
			date = map.get("sendTime");
			content = map.get("mailContent");
			request().setAttribute("sender", sender);
			request().setAttribute("receiver", receiver);
			request().setAttribute("title", title);
			request().setAttribute("date", date);
			request().setAttribute("content", content);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 邮箱管理模块
	 * 
	 * @return
	 * @throws Exception
	 */
	public String emailManagerInit() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		paramMap = userService.queryUserById(user.getId());
		String email = paramMap.get("email") + "";
		String flag = "1";
		if (email.equals("")) {
			flag = "1";
		} else {
			flag = "2";
		}
		paramMap.put("flag", flag);
		session().setAttribute("DEMO", IConstants.ISDEMO);
		return SUCCESS;
	}

	/**
	 * 账户设置 邮箱设定
	 * 
	 * @return
	 * @throws Exception
	 */
	public String SendUserEmailSet() throws Exception {
		JSONObject obj = new JSONObject();
		String email = paramMap.get("email");
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		}
		long result1 = userService.isExistEmailORUserName(email, null);
		if (result1 > 0) { // email重复
			obj.put("mailAddress", "4");
			JSONUtils.printObject(obj);
			return null;
		}
		// ===截取emal后面地址
		int dd = email.indexOf("@");
		String mailAddress = null;
		if (dd >= 0) {
			mailAddress = "mail." + email.substring(dd + 1);
		}
		
		if (user != null) {
			DesSecurityUtil des = new DesSecurityUtil();
			String key1 = des.encrypt(user.getId() + "");
			String key2 = des.encrypt(new Date().getTime() + "");
			String key3 = email;
			String Name = user.getUserName();
			String url =  request().getRequestURI();
			String VerificationUrl = url + "bangdingemail.do?key=" + key1 + "-"
					+ key2 + "-" + key3;
			
			sendMailService.SendUserEmailSetInUser(VerificationUrl, Name, email);
			
			obj.put("mailAddress", mailAddress);
			JSONUtils.printObject(obj);
			return null;
		}
		return null;

	}
	
	/**
	 * 修改邮箱
	 * @return
	 * @throws IOException 
	 */
	public String updattingUserEmail() throws Exception{
		JSONObject obj = new JSONObject();
		String email = paramMap.get("email");
		/*
		 * flag =2 旧邮箱发送修改信息
		 * flag =3 新邮箱发送激活信息
		 */
		String flag = paramMap.get("flag");
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String mailAddress = null;
		if(StringUtils.isNotBlank(flag)){
			if(flag.equals("2")){
				email = user.getEmail();
			}
			
			if (StringUtils.isBlank(email)) {
				obj.put("mailAddress", "0");
				JSONUtils.printObject(obj);
				return null;
			}
			
			if(flag.equals("3")){
				long result1 = userService.isExistEmailORUserName(email, null);
				if (result1 > 0) { // email重复
					obj.put("mailAddress", "4");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			
			// ===截取emal后面地址
			int dd = email.indexOf("@");
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
		}
		
		if (user != null) {
			DesSecurityUtil des = new DesSecurityUtil();
			String key1 = des.encrypt(user.getId() + "");
			String key2 = des.encrypt(new Date().getTime() + "");
			String key3 = email;
			String Name = user.getUserName();
			String url = request().getRequestURI();//getPath();  request().getRequestURI();
			String VerificationUrl = "";
			
			if(flag.equals("2")){
				VerificationUrl = url + "toUpdattingEmail.do?key=" + key1 + "-"
						+ key2 + "-" + key3+"-2";
			}else if(flag.equals("3")){
				VerificationUrl = url + "bangdingemail.do?key=" + key1 + "-"
						+ key2 + "-" + key3;
			}
			// 修改邮箱
			sendMailService.toSendUserNewEmailUpdattingSetInUser(VerificationUrl, Name, email);
			
			obj.put("mailAddress", mailAddress);
			JSONUtils.printObject(obj);
			return null;
		}
		return null;
	}
	
	
	/**
	 * 修改邮箱
	 * @return
	 */
	public String toUpdattingEmail(){
		String key = request.getString("key").trim();
		String msg = "参数传递有误！";
		String[] keys = key.split("-");
		if (4 == keys.length) {
			String flag = keys[3];
			if(flag.equals("2")){
				paramMap.put("msg", "验证邮箱成功！");
				paramMap.put("flag", "3");
			}else{
				paramMap.put("param", msg);
			}
		}
		return SUCCESS;
	}

	/**
	 * 邮箱绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bangdingemail() throws Exception {
		String key = request.getString("key").trim();
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (3 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			Long userId = Convert
					.strToLong(des.decrypt(keys[0].toString()), -1);
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			String emial = keys[2].toString();
			Pattern p = Pattern
					.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
			Matcher matcher = p.matcher(emial);
			if (!matcher.matches()) {
				paramMap.put("msg", "邮箱格式错误");
			} else {
				// 校验邮箱的唯一性
				long result1 = userService.isExistEmailORUserName(emial, null);
				if (result1 > 0) { // email重复
					paramMap.put("msg", "该邮箱已被绑定,请重新输入");
				} else {
					// 当用户点击注册时间小于10分钟
					if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
						long result = userService.updateEmalByid(userId, emial);
						if (result < 0) {
							paramMap.put("msg", "邮箱绑定失败");
						} else {
							paramMap.put("msg", "邮箱绑定成功");
							//Map<String, String> map = userService
									//.queEmailUser(userId);
							//String username = map.get("username") + "";
							//bbsRegisterService.doUpdateEmailByAsynchronousMode(
									//username, emial);
						}
						return SUCCESS;
					} else {
						msg = "连接失效,<strong>请从新绑定</a></strong>";
						paramMap.put("msg", msg);
					}
				}
			}
		}
		return SUCCESS;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BecomeToFinanceService getBecomeFinanceService() {
		return becomeFinanceService;
	}

	public void setBecomeFinanceService(
			BecomeToFinanceService becomeFinanceService) {
		this.becomeFinanceService = becomeFinanceService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

//	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
//		this.bbsRegisterService = bbsRegisterService;
//	}

	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

}
