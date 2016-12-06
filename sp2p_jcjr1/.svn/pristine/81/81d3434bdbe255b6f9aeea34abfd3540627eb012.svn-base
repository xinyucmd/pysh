package com.sp2p.action.app;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.PublicFunction;
import com.sp2p.util.isKeywords;


public class HomeAppAction extends BaseAppAction {
	private static final long serialVersionUID = -8705141732645392945L;
	public static Log log = LogFactory.getLog(HomeAppAction.class);

	private MyHomeService myHomeService;
	private HomeInfoSettingService homeInfoSettingService;
    private FundManagementService fundManagementService;
    private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}
	
	/**
	 * 获取APP版本号
	 * @return
	 * @throws IOException
	 */
	public String getAppVersion() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			
			// 判断系统编号
			String sysType = Convert.strToStr(infoMap.get("sys_type"), "");
			String verNum = Convert.strToStr(infoMap.get("verNum"), "");
			String curNum = Convert.strToStr(PublicFunction.GetOption("app_version"), "");
			if(sysType.equals("ios")){
				curNum= Convert.strToStr(PublicFunction.GetOption("app_version_ios"), "");
			}
			if(verNum.equals(curNum)){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "当前版本已经是最新版本");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = PublicFunction.GetOption("app_upcontent");
			String appForce = PublicFunction.GetOption("app_force");
			String url = IConstants.APP_URL;
			int app_state = Convert.strToInt(myHomeService.queryOptions(317).get("app_state"), 0);
			jsonMap.put("app_state", String.valueOf(app_state));
			jsonMap.put("url", url);
			jsonMap.put("updMessage", content);
			jsonMap.put("curNum", curNum+"");
			jsonMap.put("appForce", appForce);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	
	public String queryHome() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> homeMap = myHomeService.queryHomeInfo(userId);
			Map<String, String> accmountStatisMap = myHomeService
					.queryAccountStatisInfo(userId);
			jsonMap.putAll(homeMap);
			jsonMap.putAll(accmountStatisMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public String queryBank() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			List<Map<String, Object>> lists = homeInfoSettingService
					.queryBankInfoList(userId);
			String card = "";
			for(Map<String, Object> map:lists){
				card = String.valueOf(map.get("cardNo"));
				//银行卡长度
				if(card.length() > 8){
					map.put("cardNoAll",card);
					map.put("cardNo", card.substring(0, 4) + "*********" + card.substring(card.length()-4));
				}
				card = String.valueOf(map.get("modifiedCardNo"));
				//银行卡长度
				if(card.length() > 8){
					map.put("modifiedCardNoAll",card);
					map.put("modifiedCardNo", card.substring(0, 4) + "*********" + card.substring(card.length()-4));
				}
			}
			jsonMap.put("lists", lists);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public String addBank() throws IOException {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			Map<String, String> appInfoMap = this.getAppInfoMap();
			
//			String code = appInfoMap.get("code");
//			if(StringUtils.isBlank(code)){
//				jsonMap.put("error", "1");
//				jsonMap.put("msg", "验证码不能为空");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			String randomCode = appInfoMap.get("randomCode") + "";
//			randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
////			 String phone = session().getAttribute("phone") +"";
//			if (!randomCode.equals(code)) {
//				jsonMap.put("error", "2");
//				jsonMap.put("msg", "验证码不正确");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			String recivePhone = appInfoMap.get("recivePhone") + "";
//			recivePhone = Encrypt.decryptSES(recivePhone, AlipayConfig.ses_key);
			Map<String, String> mapPerson = homeInfoSettingService.getRealNameByUserId(userId);
			String cardUserName = mapPerson.get("realName");
			if (StringUtils.isBlank(cardUserName)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请先完善个人信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			//区分安卓
			String flag = appInfoMap.get("flag");
			String bankName = appInfoMap.get("bankName");
			String subBankName = appInfoMap.get("subBankName");
			//ios不解密
			if("android".equals(flag)){
				bankName = com.shove.security.Encrypt.decrypt3DES(bankName,	IConstants.PASS_KEY);
				subBankName = com.shove.security.Encrypt.decrypt3DES(subBankName, IConstants.PASS_KEY);
			}else {
				if(bankName.startsWith("U")){
					bankName = bankName.replace("U", "\\u");
					bankName = isKeywords.decodeUnicode(bankName);
				}
				if(subBankName.startsWith("U")){
					subBankName = bankName.replace("U", "\\u");
					subBankName = isKeywords.decodeUnicode(subBankName);
				}
			}
			if (StringUtils.isBlank(bankName)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "银行名称不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if (StringUtils.isBlank(subBankName)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "银行开户行不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			String bankCard = appInfoMap.get("bankCard");
			if (StringUtils.isBlank(bankCard)) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "银行卡号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String dealpwd = appInfoMap.get("dealpwd");
			//dealpwd = com.shove.security.Encrypt.decrypt3DES(dealpwd, IConstants.PASS_KEY);
			
			if(StringUtils.isBlank(dealpwd)){
				jsonMap.put("error", "5");
				jsonMap.put("msg", "请输入交易密码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if ("1".equals(IConstants.ENABLED_PASS)){
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
			}else{
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()+IConstants.PASS_KEY);
			}
			//dealpwd = com.shove.security.Encrypt.MD5(dealpwd + IConstants.PASS_KEY);
			if(!dealpwd.equals(userService.queryUserById(userId).get("dealpwd"))){
				jsonMap.put("error", "6");
				jsonMap.put("msg", "交易密码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			long bankId = Convert.strToLong(appInfoMap.get("bankId"), -1);

			Map<String, String> map = homeInfoSettingService
					.queryCardStatus(userId);
			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);

			if (bindingCardNum >= 1) {// 已经绑定两张银行卡，不能再绑定了
				jsonMap.put("error", "7");
				jsonMap.put("msg", "最多绑定一张银行卡");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 银行卡验证
			/*
			 * Pattern p = Pattern.compile("[0-9]{19}"); Matcher m =
			 * p.matcher(bankCard); if(!m.matches()){ JSONUtils.printStr("1");
			 * return null; }
			 */

			// 新添加的提现银行卡信息状态为2，表示申请中
			long result = -1;
			if (bankId == -1) {
				result = homeInfoSettingService.addBankCardInfo(userId,
						cardUserName, bankName, subBankName, bankCard,
						IConstants.BANK_SUCCESS,"","");
			}else{
				result = fundManagementService.updateChangeBank(bankId, bankName, subBankName, bankCard, IConstants.BANK_CHECK, new Date(), true);
			}
			if(result > 0){
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
			}else{
				jsonMap.put("error", "8");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	public String updateBank() throws IOException {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			Map<String, String> appInfoMap = this.getAppInfoMap();
			
//			String code = appInfoMap.get("code");
//			if(StringUtils.isBlank(code)){
//				jsonMap.put("error", "1");
//				jsonMap.put("msg", "验证码不能为空");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			String randomCode = appInfoMap.get("randomCode") + "";
//			randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
//			 String phone = session().getAttribute("phone") +"";
//			if (!randomCode.equals(code)) {
//				jsonMap.put("error", "2");
//				jsonMap.put("msg", "验证码不正确");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			String recivePhone = appInfoMap.get("recivePhone") + "";
//			recivePhone = Encrypt.decryptSES(recivePhone, AlipayConfig.ses_key);
//			String cardUserName = appInfoMap.get("cardUserName");
			String bankName = appInfoMap.get("mBankName");
			if (StringUtils.isBlank(bankName)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "银行名称不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if(bankName.startsWith("U")){
				bankName = bankName.replace("U", "\\u");
				bankName = isKeywords.decodeUnicode(bankName);
			}
			String subBankName = appInfoMap.get("mSubBankName");
			if (StringUtils.isBlank(subBankName)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "银行开户行不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if(subBankName.startsWith("U")){
				subBankName = bankName.replace("U", "\\u");
				subBankName = isKeywords.decodeUnicode(subBankName);
			}
			String bankCard = appInfoMap.get("mBankCard");
			if (StringUtils.isBlank(bankCard)) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "银行卡号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String dealpwd = appInfoMap.get("dealpwd");
			//dealpwd = com.shove.security.Encrypt.decrypt3DES(dealpwd, IConstants.PASS_KEY);
			if(StringUtils.isBlank(dealpwd)){
				jsonMap.put("error", "5");
				jsonMap.put("msg", "请输入交易密码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if ("1".equals(IConstants.ENABLED_PASS)){
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
			}else{
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()+IConstants.PASS_KEY);
			}
			if(!dealpwd.equals(userService.queryUserById(userId).get("dealpwd"))){
				jsonMap.put("error", "6");
				jsonMap.put("msg", "交易密码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long bankId = Convert.strToLong(appInfoMap.get("bankId"), -1);

//			Map<String, String> map = homeInfoSettingService
//					.queryCardStatus(userId);
//			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			// 银行卡验证
			/*
			 * Pattern p = Pattern.compile("[0-9]{19}"); Matcher m =
			 * p.matcher(bankCard); if(!m.matches()){ JSONUtils.printStr("1");
			 * return null; }
			 */

			// 新添加的提现银行卡信息状态为2，表示申请中
			long result = -1;
				result = fundManagementService.updateChangeBank(bankId, bankName, subBankName, bankCard, IConstants.BANK_CHECK, new Date(), true);
		
			if(result > 0){
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "更变申请已提交");
				JSONUtils.printObject(jsonMap);
			}else{
				jsonMap.put("error", "8");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}
	
	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

}
