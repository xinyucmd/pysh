package com.sp2p.action.app;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.HttpClient;
import com.shove.util.SMSUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.BeVipService;
import com.sp2p.service.BonusService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.service.admin.RewardService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.service.admin.SendSMSService;
import com.sp2p.util.isKeywords;

public class MyInformationAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(MyInformationAppAction.class);
	private static final long serialVersionUID = 1L;

	private UserService userService;

	private RegionService regionService;
	private ValidateService validateService;
	private BeVipService beVipService;
	private MyHomeService myHomeService;
	private BonusService bonusService;
	private SendSMSService sendSMSService;  

	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> areaList;
	private List<Map<String, Object>> regcityList;
	private Map<String, Object> jsonMap;//app
	private SMSInterfaceService phonemsg;//发送短信
	private HomeInfoSettingService homeInfoSettingService;
	private RewardService rewardService;
	private RecommendUserService recommendUserService;
	private RechargeService rechargeService;

	private long workPro = -1L;// 初始化省份默认值
	private long cityId = -1L;// 初始化话默认城市
	private long regPro = -1L;// 户口区域默认值
	private long regCity = -1L;// 户口区域默认值

	/**
	 * 查询个详细信息
	 * 
	 * @return
	 * @throws Exception
     */
	public String queryBaseData() throws Exception {
		//app
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		//former
		Map<String, String> map = new HashMap<String, String>();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		Map<String,String> u = userService.queryUserById(userId);
		if(u != null && u.size() > 0){
			jsonMap.put("email", u.get("email"));
//			int isApplyPro=Convert.strToInt(u.get("isApplyPro"), 1);
//			jsonMap.put("isApplyPro", isApplyPro);
//			int authStep = Convert.strToInt(u.get("authStep"), 1);
//			if(authStep < 2){
//				jsonMap.put("person", "0");
//			}else{
//				jsonMap.put("person", "1");
//			}
		}
//		String  birth = null;
//		String rxedate = null;
		if (userId > -1) {
			map = userService.queryPersonById(userId);
			if (map != null && map.size() > 0) {
//				workPro = Convert.strToLong(map.get("nativePlacePro")
//						.toString(), -1L);
//				cityId = Convert.strToLong(map.get("nativePlaceCity")
//						.toString(), -1L);
//				regPro = Convert.strToLong(map.get("registedPlacePro")
//						.toString(), -1L);
//				regCity =Convert.strToLong(map.get("registedPlaceCity")
//						.toString(), -1L);
//				String birthd = map.get("birthday"); 
//				birth = Convert.strToStr(map.get("birthday"), null);
//				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
//				if(birth!=null){
//					birth = birth.substring(0, 10);
//				}
//				if(rxedate!=null){
//					rxedate = rxedate.substring(0, 10);
//				}
				//移形换位
				jsonMap.put("realName", map.get("realName"));
				//jsonMap.put("pass", map.get("auditStatus"));
			}
		}else{
			jsonMap.put("error", "1");
			jsonMap.put("msg", "没有登录");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		//判断用户是否已经填写了基本信息
		String flag = "";
        if(map!=null&&map.size()>0){//用户基本资料有数据但是不一定是已经填写了基本资料信息 还有可能是上传了个人头像
	    	if(!StringUtils.isBlank(map.get("realName"))){//不为空
	    		flag = "1";
	    	}else{
	    		flag = "2";
	    	}
        }else{
        	 flag = "2";
        }
        jsonMap.put("flag", flag);
        jsonMap.putAll(map);
        jsonMap.put("error", "-1");
		jsonMap.put("msg", "成功");
		JSONUtils.printObject(jsonMap);
		return null; //返回个人资料详情
		//provinceList = regionService.queryRegionList(-1L, 1L, 1);
		//cityList = regionService.queryRegionList(-1L, workPro, 2);
		//regcityList = regionService.queryRegionList(-1L, regPro, 2);
		//jsonMap.put("provinceList", provinceList);
		//jsonMap.put("cityList", cityList);
		//jsonMap.put("regcityList", regcityList);
//		jsonMap.put("birth", birth);
//		jsonMap.put("rxedate", rxedate);

		//whb 暂时不用
//		String tab_type = infoMap.get("tab_type")==null?null:infoMap.get("tab_type");
//		if(tab_type != null){
//			jsonMap.put("tab_type", tab_type);
//		}
//		String yy = infoMap.get("yy")==null?null:infoMap.get("yy");
//		if(yy != null){
//			jsonMap.put("yy", yy);
//		}
		
//		jsonMap.put("ISDEMO", IConstants.ISDEMO);
		
//		Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(userId);
//		jsonMap.put("accmountStatisMap", accmountStatisMap);
		
	}
	
	/**
	 * 我的好友列表
	 * @return
	 * @throws Exception
	 */
	public String toRecommendInfo() throws Exception{
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String,String> recommendUserSummary = recommendUserService.queryRecommendUserSummary(userId);
			List<Map<String, Object>> recommendUserInfo = recommendUserService.queryRecommendUserInfo(userId,-1,-1);
			
			jsonMap.put("recommendUserSummary", recommendUserSummary);
			jsonMap.put("recommendUserInfo", recommendUserInfo);
			JSONUtils.printObject(jsonMap);
		} catch (DataException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 连连支付跳转页
	 * @return
	 * @throws Exception
	 */
	public String queryLianlianPay() throws Exception{
		
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		Map<String, String> recharMap = new HashMap<String, String>();//充值明细表数据
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> recharPageList;//充值页面数据
		try {
			recharPageList = rechargeService.queryRecharPageById(userId);
			if(null != recharPageList && !recharPageList.isEmpty()){
				for (Map<String,Object> map:recharPageList) {
					if(StringUtils.isBlank(String.valueOf(map.get("realname"))) || StringUtils.isBlank(String.valueOf(map.get("idNo")))){
						jsonMap.put("error", "1");
						jsonMap.put("msg", "您没有实名认证，请先实名认证");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				}
			}
			//获取充值明细表中最大id
			Map<String, String> recharId = rechargeService.queryLianlianPaySave();
			int id = Integer.parseInt(recharId.get("id")) + 1;
			//往充值表中添加数据
			recharMap.put("userId", userId + "");
			recharMap.put("rechargeTime", df.format(new Date()) + "");
			recharMap.put("rechargeType", "10");
			recharMap.put("result", "5");// 5为连连支付跳转页
			recharMap.put("remark", "连连支付跳转页");
			recharMap.put("paynumber", id + "");
			long result = -1;
			result = rechargeService.addRechargeoutline(recharMap);
			if(result > 0){
				jsonMap.put("paynumber", id);
				jsonMap.put("recharPageList", recharPageList);
				JSONUtils.printObject(jsonMap);
			}else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "操作失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 连连支付提交（银行卡绑定）
	 * @return
	 * @throws Exception
	 */
	public String queryLianlianPaySave() throws Exception{
		jsonMap = new HashMap<String, Object>();
//		String userAgent = request().getHeader("user-agent");
//		if(userAgent.indexOf("iPhone")!=-1){
//			jsonMap.put("error", "-3");
//			jsonMap.put("msg", "审核中，暂未开放。");
//			JSONUtils.printObject(jsonMap);
//			return null;
//		}
		Map<String, String> infoMap = getAppInfoMap();
		Map<String, String> updateMap = new HashMap<String, String>();//充值明细表数据
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long cardNo =  Long.parseLong(infoMap.get("paynumber"));//充值流水号
		long id =  Long.parseLong(infoMap.get("id"));//充值绑定银行卡号id
		try {
			String name = infoMap.get("bankName");
			if(name.startsWith("U")){
				name = name.replace("U", "\\u");
				name = isKeywords.decodeUnicode(name);
			}
			//更新用户绑定银行卡
			userService.updateUniqueBankCardMark(id);
			updateMap.put("rechargeTime", df.format(new Date()) + "");
			updateMap.put("bankName", name);
			updateMap.put("paynumber", infoMap.get("paynumber"));
			updateMap.put("remark", "连连支付绑定银行卡");
			// 更新充值明细表中的状态
           long result = -1L;
           result = rechargeService.updateRechargeoutline(cardNo, 1, updateMap);
           if(result > 0){
				jsonMap.put("paynumber", cardNo);
				JSONUtils.printObject(jsonMap);
			}else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "银行卡绑定失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
		} catch (DataException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 连连支付结果回调
	 * @return
	 * @throws Exception
	 */
	public String queryLianlianPayResult() throws Exception{
		jsonMap = new HashMap<String, Object>();
		Map<String, Object> recharResult;//充值页面数据
		Map<String, String> recharMap = new HashMap<String, String>();//充值明细表数据
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//解析流
		InputStream in = request().getInputStream();
		String info = HttpClient.inputStreamToJson(in);
		log.info("连连支付返回流解析字符串为" + info);
		recharResult = HttpClient.parseJSON2Map(info);
		//往充值表中添加数据
		recharMap.put("rechargeTime", df.format(new Date()) + "");
		if("SUCCESS".equals(String.valueOf(recharResult.get("result_pay")))){
			recharMap.put("result", "1");// 6为连连支付成功
			recharMap.put("remark", "连连支付充值成功");
		}else {
			recharMap.put("result", "0");
			recharMap.put("remark", "连连支付充值失败");
		}
		recharMap.put("checkremark", String.valueOf(recharResult.get("dt_order")));
		recharMap.put("rechargeNumber", String.valueOf(recharResult.get("oid_paybill")));
		recharMap.put("rechargeMoney", String.valueOf(recharResult.get("money_order")));
		long cardNo = Convert.strToLong(String.valueOf(recharResult.get("no_order")), -1);
		long result = -1;
		//更新余额
		result = rechargeService.updateRechargeoutline(cardNo, 2, recharMap);
		if(result > 0){
			log.info("连连支付充值成功");
			jsonMap.put("ret_code", "0000");
			jsonMap.put("ret_msg", "交易成功");
			JSONUtils.printObject(jsonMap);
		}else {
			log.info("连连支付充值失败");
		}
		
		return null;
	}
	
	/**
	 * 我的奖励—现金奖励列表
	 * @return
	 * @throws Exception
	 */
	public String toRewardInfo() throws Exception {
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		//奖励总额
		List<Map<String,Object>> list = rewardService.queryRewardRecordTotal(userId);
		Map<String, String> rewardStatisMap = new HashMap<String, String>();
		if(!list.isEmpty()){
			for(Map<String,Object> map:list){
				//1、注册奖励 2、投资奖励 3 推荐注册奖励 4 累计推荐注册奖励 5 推荐单次投资奖励 6 累计推荐单次投资奖励
				if("1".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("regReward", String.valueOf(map.get("sum_amount")));
				}else if("2".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("investReward", String.valueOf(map.get("sum_amount")));
				}else if("3".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("recomReward", String.valueOf(map.get("sum_amount")));
				}else if("4".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("sumRecomReward", String.valueOf(map.get("sum_amount")));
				}else if("5".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("onceInvestReward", String.valueOf(map.get("sum_amount")));
				}else if("6".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("sumOnceInvestReward", String.valueOf(map.get("sum_amount")));
				}else if("9".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("sumReward", String.valueOf(map.get("sum_amount")));
				}else {
					rewardStatisMap.put("error", "未知错误");
				}
			}
		}
		//奖励列表
		List<Map<String,Object>> rewardList = rewardService.rewardRecordInfoApp(userId);

		jsonMap.put("rewardStatisMap", rewardStatisMap);
		jsonMap.put("rewardList", rewardList);
		JSONUtils.printObject(jsonMap);
		return null;
	}
	
	/**
	 * 获取红包信息
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public String getBonusInfo() throws Exception{
		jsonMap = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		//区分红包bonus
		int bonus = Convert.strToInt(appInfoMap.get("bonus"), 1);
		//区分是否分页
		String flag = appInfoMap.get("flag");
		if(!"android".equals(flag)){
			pageBean.setPageNum(appInfoMap.get("curPage"));
			pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		}else if("android".equals(flag)){
			pageBean.setPageNum(1);
			pageBean.setPageSize(10000);
		}
		
		try {
			long count = bonusService.queryBonusListCount(userId,bonus);
			List<Map<String, Object>> bonusList = bonusService.queryBonusList(userId,pageBean.getStartOfPage(),pageBean.getPageSize(),bonus);
			
			pageBean.setTotalNum(count);
			jsonMap.put("list", bonusList);
			jsonMap.put("totalPageNum", pageBean.getTotalPageNum());
			JSONUtils.printObject(jsonMap);
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 手机变更（获取验证码）
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public String boundcellphone() throws Exception {
		jsonMap = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		
		String phone = appInfoMap.get("phone");
		List<Map<String,Object>> list = sendSMSService.getNowDaySmsRecordList(phone);
		if(list!=null && list.size()>=10){
			jsonMap.put("error", "3");
			jsonMap.put("msg", "发送失败");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		Map<String,String> map = null;
		if(phone != null && phone.length() > 0){
			//手机正则
			if(!phone.matches("^[1][3|4|5|7|8|][0-9]{9}$")){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号码格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			map = userService.queryUserByNameAndPhone(null, phone);
			//验证手机号是否唯一
			if(map!=null && map.size() > 0){
				jsonMap.put("error", "2");
				jsonMap.put("msg", "手机号已存在");
				JSONUtils.printObject(jsonMap);
				return null; 
			}
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);
			if (intCount < 1000)
				intCount += 1000; 

			String randomCode = intCount + "";
			Map<String, String> maps = phonemsg.getSMSById(1);
			System.out.println(randomCode);
			String content = "本次验证码为:"+ randomCode;
			String retCode = SMSUtil.sendMSM(maps.get("Account"), maps
					.get("Password"), content, phone, "");
			
			if("Sucess".equals(retCode)){
				session().setAttribute("phonecodes", randomCode);
				session().setAttribute("phone", phone);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "发送成功");
				sendSMSService.insertSmsRecord(phone, content,"MyInformationAppAction.boundcellphone",request().getRemoteAddr());
				JSONUtils.printObject(jsonMap);
			}else{
				jsonMap.put("error", "3");
				jsonMap.put("msg", "发送失败");
				JSONUtils.printObject(jsonMap);
			}
		}else{
			jsonMap.put("error", "0");
			jsonMap.put("msg", "手机号不能为空");
			JSONUtils.printObject(jsonMap);
		}
		return null; 
	}
	
	/**
	 * 手机变更
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public String updatecellphone() throws Exception {
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		Map<String, String> appInfoMap = getAppInfoMap();
		
		long id = Convert.strToLong(authMap.get("uid"), -1);
		String code = appInfoMap.get("code");
		String reason = appInfoMap.get("reason");
		String phone = appInfoMap.get("phone");
		
		try {
			//验证码校验
			if(StringUtils.isNotBlank(code)){
				String codes = (String) session("phonecodes");
				String phones = (String) session("phone");
				if(!phone.equals(phones)){
					jsonMap.put("error", "1");
					jsonMap.put("msg", "手机号码与获取验证码手机号不一致");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				//校验通过
				if(code.equals(codes)){
					//账号异常
					boolean re = userService.checkSign(id);
					if(!re){
						jsonMap.put("error", "2");
						jsonMap.put("msg", "您的账号异常，请联系管理员");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					long result = homeInfoSettingService.updateUserPassword(id,	phone, "deal");
					if(result > 0){
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "操作成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}else{
						jsonMap.put("error", "0");
						jsonMap.put("msg", "变更失败");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				}
				//校验不通过
				else{
					jsonMap.put("error", "11");
					jsonMap.put("msg", "验证码错误");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}else{
				jsonMap.put("error", "12");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 重置交易密码（获取验证码）
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public String forgetdealpwd() throws Exception {
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		Map<String, String> appInfoMap = getAppInfoMap();
		
		String id = authMap.get("uid");
		String phone = appInfoMap.get("phone");
		
		List<Map<String,Object>> list = sendSMSService.getNowDaySmsRecordList(phone);
		if(list!=null && list.size()>=10){
			jsonMap.put("error", "3");
			jsonMap.put("msg", "发送失败");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		
		Map<String,String> map = null;
		if(phone != null && phone.length() > 0){
			//手机正则
			if(!phone.matches("^[1][3|4|5|7|8|][0-9]{9}$")){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号码格式错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			map = userService.queryUserByNameAndPhone(null, phone);
			if(map!=null && map.size() > 0){
				//验证手机号是否为当前用户
				if(!id.equals(map.get("id"))){
					jsonMap.put("error", "2");
					jsonMap.put("msg", "输入的手机号不是您绑定的手机号");
					JSONUtils.printObject(jsonMap);
					return null; 
				}
				int intCount = 0;
				intCount = (new Random()).nextInt(9999);
				if (intCount < 1000)
					intCount += 1000; 
				
				String randomCode = intCount + "";
				Map<String, String> maps = phonemsg.getSMSById(1);
				System.out.println(randomCode);
				String content = "本次验证码为:"+ randomCode;
				String retCode = SMSUtil.sendMSM(maps.get("Account"), maps
						.get("Password"), content, phone, "");
				
				if("Sucess".equals(retCode)){
//					session().setAttribute("phonecodes", randomCode);
					jsonMap.put("code", com.shove.security.Encrypt.MD5(randomCode+IConstants.PASS_KEY));
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "发送成功");
					
					jsonMap.put("randomCode", Encrypt.encryptSES(randomCode,
							AlipayConfig.ses_key));
					jsonMap.put("recivePhone", Encrypt.encryptSES(phone,
							AlipayConfig.ses_key));
					sendSMSService.insertSmsRecord(phone, content,"MyInformationAppAction.forgetdealpwd",request().getRemoteAddr());
					JSONUtils.printObject(jsonMap);
				}else{
					jsonMap.put("error", "3");
					jsonMap.put("msg", "发送失败");
					JSONUtils.printObject(jsonMap);
				}
			}
		}else{
			jsonMap.put("error", "0");
			jsonMap.put("msg", "手机号不能为空");
			JSONUtils.printObject(jsonMap);
		}
		return null; 
	}

	/**
	 * 交易密码重置
	 * @author whb
	 * @return
	 * @throws Exception
	 */
	public String reUserDealpwd() throws Exception {
		jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		Map<String, String> appInfoMap = getAppInfoMap();
		
		long id = Convert.strToLong(authMap.get("uid"), -1);
		String code = appInfoMap.get("code");
		String passwd = appInfoMap.get("passwd");
		String confirmpassword = appInfoMap.get("confirmpassword");
		
		try {
			//验证码校验
			if(StringUtils.isNotBlank(code)){
//				String codes = (String) session("phonecodes");
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
						 jsonMap.put("error", "11");
						 jsonMap.put("msg", "手机验证码不正确");
						 JSONUtils.printObject(jsonMap);
						 return null;
					 }
				}
				
				//新密码不能为空
				if(passwd == null || "".equals(passwd)){
					jsonMap.put("error", "1");
					jsonMap.put("msg", "新密码不能为空");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				//两次密码不相同
				if(!passwd.equals(confirmpassword)){
					jsonMap.put("error", "2");
					jsonMap.put("msg", "两次密码不一致");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				//密码长度为6-20个字符
				if (passwd.length() < 6 || passwd.length() > 20) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "密码长度为6-20个字符");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				//获得用户信息
				Map<String, String> map = homeInfoSettingService.getDealPwd(id);
				//交易密码不能和登录密码一样
				if(com.shove.security.Encrypt.MD5(passwd + IConstants.PASS_KEY).equals(map.get("password"))){
					jsonMap.put("error", "4");
					jsonMap.put("msg", "交易密码不能和登录密码一样");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				//账号异常
				boolean re = userService.checkSign(id);
				if(!re){
					jsonMap.put("error", "5");
					jsonMap.put("msg", "您的账号异常，请联系管理员");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				long result = homeInfoSettingService.updateUserPassword(id,	passwd, "deal");
				if(result > 0){
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "操作成功");
					JSONUtils.printObject(jsonMap);
					return null;
				}else{
					jsonMap.put("error", "0");
					jsonMap.put("msg", "重置失败");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}else{
				jsonMap.put("error", "12");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//添加或更新个人信息
	public String updateUserBaseData() throws Exception {
			User user = (User) session("user");
		
		JSONObject json = new JSONObject();
		String realName = paramMap.get("realName");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}
		
		String idNo = paramMap.get("idNo");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}
		//验证身份证
		String iDresutl = "";
	//	iDresutl  =	iDCardValidateService.IDCardValidate(idNo);
		if(iDresutl!=""){
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}
		String cellPhone = paramMap.get("cellPhone");// 手机号码
		//判断是否是手机注册用户
		String iscellPhone = paramMap.get("iscellPhone");
		if (StringUtils.isBlank(iscellPhone)) {
			if (StringUtils.isBlank(cellPhone)) {
				json.put("msg", "请正确填写手机号码");
				JSONUtils.printObject(json);
				return null;
			} else if (cellPhone.length() < 9 || cellPhone.length() > 15) {
				json.put("msg", "手机号码长度不对");
				JSONUtils.printObject(json);
				return null;
			}

			/**
			 * 判定用户是否已存在记录
			 */
			Map<String,String> pMap = null;
			
			pMap = beVipService.queryPUser(user.getId());
			//验证手机的唯一性
			Map<String,String> phonemap = null;
			phonemap = beVipService.queryIsPhone(cellPhone);
			
			if(pMap==null){
			
				if(phonemap!=null){
					json.put("msg", "手机已存在");
					JSONUtils.printObject(json);
					return null;
				}	
				
				   if(phonemap==null){	
						String phonecode=null;
						try {
							Object obje=session().getAttribute("phone");
							if(obje!=null){
								phonecode=obje.toString();
							}else{
								json.put("msg", "请输入正确的验证码");
								JSONUtils.printObject(json);
								return null;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
				
						 
						if(phonecode!=null){
							if(!phonecode.trim().equals(cellPhone.trim())){
								json.put("msg", "与获取验证码手机号不一致");
								JSONUtils.printObject(json);
								return null;
							}
							
						}
						//验证码
						String vilidataNum = paramMap.get("vilidataNum");
				        if(StringUtils.isBlank(vilidataNum)){
				        	json.put("msg","请填写验证码");
				        	JSONUtils.printObject(json);
							return null;
				        }
				        
				        String randomCode=null;
						Object objec=session().getAttribute("randomCode");
						if(objec!=null){
							randomCode=objec.toString();
						}else{
							json.put("msg","请输入正确的验证码");
				        	JSONUtils.printObject(json);
							return null;
						}
						if(randomCode!=null){
							if(!randomCode.trim().equals(vilidataNum.trim())){
								
								json.put("msg","请输入正确的验证码");
					        	JSONUtils.printObject(json);
								return null;
							}
							
						}
				
			}
		
		   }
			
		}

		String sex = paramMap.get("sex");// 性别(男 女)
		if (StringUtils.isBlank(sex)) {
			json.put("msg", "请正确填写性别");
			JSONUtils.printObject(json);
			return null;
		}

		String birthday = paramMap.get("birthday");// 出生日期
		if (StringUtils.isBlank(birthday)) {
			json.put("msg", "请正确填写出生日期");
			JSONUtils.printObject(json);
			return null;
		}

		String highestEdu = paramMap.get("highestEdu");// 最高学历
		if (StringUtils.isBlank(highestEdu)) {
			json.put("msg", "请正确填写最高学历");
			JSONUtils.printObject(json);
			return null;
		}

		String eduStartDay = paramMap.get("eduStartDay");// 入学年份
		if (StringUtils.isBlank(eduStartDay)) {
			json.put("msg", "请正确填写入学年份");
			JSONUtils.printObject(json);
			return null;
		}

		String school = paramMap.get("school");// 毕业院校
		if (StringUtils.isBlank(school)) {
			json.put("msg", "请正确填写入毕业院校");
			JSONUtils.printObject(json);
			return null;
		}

		String maritalStatus = paramMap.get("maritalStatus");// 婚姻状况(已婚 未婚)
		if (StringUtils.isBlank(maritalStatus)) {
			json.put("msg", "请正确填写入婚姻状况");
			JSONUtils.printObject(json);
			return null;
		}

		String hasChild = paramMap.get("hasChild");// 有无子女(有 无)

		if (StringUtils.isBlank(hasChild)) {
			json.put("msg", "请正确填写入有无子女");
			JSONUtils.printObject(json);
			return null;
		}
		String hasHourse = paramMap.get("hasHourse");// 是否有房(有 无)
		if (StringUtils.isBlank(hasHourse)) {
			json.put("msg", "请正确填写入是否有房");
			JSONUtils.printObject(json);
			return null;
		}

		String hasHousrseLoan = paramMap.get("hasHousrseLoan");// 有无房贷(有 无)
		if (StringUtils.isBlank(hasHousrseLoan)) {
			json.put("msg", "请正确填写入有无房贷");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCar = paramMap.get("hasCar");// 是否有车 (有 无)
		if (StringUtils.isBlank(hasCar)) {
			json.put("msg", "请正确填写入是否有车");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCarLoan = paramMap.get("hasCarLoan");// 有无车贷 (有 无)
		if (StringUtils.isBlank(hasCarLoan)) {
			json.put("msg", "请正确填写入有无车贷");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlacePro = Convert.strToLong(paramMap.get("nativePlacePro"),
				-1);// 籍贯省份(默认为-1)
		if (StringUtils.isBlank(nativePlacePro.toString())) {
			json.put("msg", "请正确填写入籍贯省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlaceCity = Convert.strToLong(paramMap
				.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
		if (StringUtils.isBlank(nativePlaceCity.toString())) {
			json.put("msg", "请正确填写入籍贯城市");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlacePro = Convert.strToLong(paramMap
				.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
		if (StringUtils.isBlank(registedPlacePro.toString())) {
			json.put("msg", "请正确填写入户口所在地省份");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlaceCity = Convert.strToLong(paramMap
				.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)

		if (StringUtils.isBlank(registedPlaceCity.toString())) {
			json.put("msg", "请正确填写入户口所在地城市");
			JSONUtils.printObject(json);
			return null;
		}

		String address = paramMap.get("address");// 所在地
		if (StringUtils.isBlank(address)) {
			json.put("msg", "请正确填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}

		String telephone = paramMap.get("telephone");// 居住电话
		if (StringUtils.isBlank(telephone)) {
			json.put("msg", "请正确填写入你的家庭电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (!telephone.contains("-")) {
			json.put("msg", "你的家庭电话输入不正确");
			JSONUtils.printObject(json);
			return null;
		}
		if (telephone.trim().length()<7||telephone.trim().length()>13) {
			json.put("msg", "你的家庭电话输入长度不对");
			JSONUtils.printObject(json);
			return null;
		}
		/* 用户头像 */
		String personalHead = paramMap.get("personalHead");// 个人头像 (默认系统头像)
		if (StringUtils.isBlank(personalHead)) {
			personalHead = null;
			json.put("msg", "请正确上传你的个人头像");
			JSONUtils.printObject(json);
			return null;
		}
		if(user==null){
			json.put("msg", "超时请重新登录");
			JSONUtils.printObject(json);
			return null;
		}
		long personId = -1L;
		if(iscellPhone!=null){
			cellPhone= iscellPhone;
		}
		int lian_state = 0;
		personId = userService.updateUserBaseData(realName, cellPhone, sex,
				birthday, highestEdu, eduStartDay, school, maritalStatus,
				hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
				nativePlacePro, nativePlaceCity, registedPlacePro,
				registedPlaceCity, address, telephone, personalHead, user
						.getId(), idNo,lian_state);
		if (personId > 0) {
			if(user.getAuthStep()==1){
				user.setAuthStep(2);
			}
			session().removeAttribute("randomCode");
			user.setPersonalHead(personalHead);//将个人头像放到session里面
			json.put("msg", "保存成功");
			JSONUtils.printObject(json);
			jsonMap.put("person", "1");
			user.setRealName(realName);
			session().setAttribute("user", user);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}
	
	/**
	 *
	 * 初始化工作认证信息，
	 * @return
	 * @throws Exception
	 */
	public String queryWorkInit() throws Exception {
	
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> allworkmap = new HashMap<String, String>();
		User user = (User) session().getAttribute("user");
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		jsonMap.put("provinceList", provinceList);

		map  =  validateService.queryWorkDataById(user.getId());
		allworkmap = validateService.queryAllWorkStatus(user.getId());
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("workPro")
					.toString(), -1L);
			cityId = Convert.strToLong(map.get("workCity")
					.toString(), -1L);
		}
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		jsonMap.put("cityList", cityList);
		jsonMap.put("map", map);
		jsonMap.put("allworkmap", allworkmap);
		Map<String,String> pmap = userService.queryPersonById(user.getId());
		if(pmap != null &&  pmap.size() > 0){
			jsonMap.put("pass", pmap.get("auditStatus"));
		}
		int authStep = user.getAuthStep();
		if(authStep < 2){
			jsonMap.put("person", "0");
		}else{
			jsonMap.put("person", "1");
		}
		jsonMap.put("realName", user.getRealName());
		
		return SUCCESS;
	   }
	
	/**
	 *
	 * 初始化基本资料信息，
	 * @return
	 * @throws Exception
	 */
	public String queryBasicInit() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) session().getAttribute("user");
		String  birth = null;
		String rxedate = null;
		if (user != null) {
			map = userService.queryPersonById(user.getId());
			if (map != null && map.size() > 0) {
				workPro = Convert.strToLong(map.get("nativePlacePro")
						.toString(), -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity")
						.toString(), -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro")
						.toString(), -1L);
				regCity =Convert.strToLong(map.get("registedPlaceCity")
						.toString(), -1L);
			birth = Convert.strToStr(map.get("birthday"), null);
			rxedate = Convert.strToStr(map.get("eduStartDay"), null);
			if(birth!=null){
				birth = birth.substring(0, 10);
			}
			if(rxedate!=null){
				rxedate = rxedate.substring(0, 10);
			}
		   }
		}
		//判断用户是否已经填写了基本信息
		String flag = "";
        if(map!=null&&map.size()>0){//用户基本资料有数据但是不一定是已经填写了基本资料信息 还有可能是上传了个人头像
        	if(!StringUtils.isBlank(map.get("realName"))){//不为空
        	 flag = "1";
        	}else{
        	 flag = "2";
        	}
        }else{
        	 flag = "2";
        }
        jsonMap.put("flag", flag);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		jsonMap.put("map", map);
		jsonMap.put("provinceList", provinceList);
		jsonMap.put("cityList", cityList);
		jsonMap.put("regcityList", regcityList);
		jsonMap.put("birth", birth);
		jsonMap.put("rxedate", rxedate);
		jsonMap.put("ISDEMO", IConstants.ISDEMO);
		return SUCCESS;
	}
	
	/**
	 * 更新的工作认证资料添加
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserWorkData() throws Exception{
		JSONObject json = new JSONObject();
		User user = (User) session().getAttribute("user");
		if (user.getAuthStep() == 1) {
			// 个人信息认证步骤
			json.put("msg", "querBaseData");
			JSONUtils.printObject(json);
			return null;
		}
		
		String orgName = paramMap.get("orgName");
		if (StringUtils.isBlank(orgName)) {
			json.put("msg", "请正确填写公司名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > orgName.length() || 50 < orgName.length()) {
			json.put("msg", "公司名字长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String occStatus = paramMap.get("occStatus");
		if (StringUtils.isBlank(occStatus)) {
			json.put("msg", "请填写职业状态");
			JSONUtils.printObject(json);
			return null;
		}
		Long workPro = Convert.strToLong(paramMap.get("workPro"), -1L);
		if (workPro==null||workPro==-1L) {
			json.put("msg", "请填写工作城市省份");
			JSONUtils.printObject(json);
			return null;
		}	
		Long workCity = Convert.strToLong(paramMap.get("workCity"), -1L);
		if (workCity==null||workCity==-1L) {
			json.put("msg", "请填写工作城市");
			JSONUtils.printObject(json);
			return null;
		}
		String companyType = paramMap.get("companyType");
		if (StringUtils.isBlank(companyType)) {
			json.put("msg", "请填写公司类别");
			JSONUtils.printObject(json);
			return null;
		}
		String companyLine = paramMap.get("companyLine");
		if (StringUtils.isBlank(companyLine)) {
			json.put("msg", "请填写公司行业");
			JSONUtils.printObject(json);
			return null;
		}
		String companyScale = paramMap.get("companyScale");
		if (StringUtils.isBlank(companyScale)) {
			json.put("msg", "请填写公司规模");
			JSONUtils.printObject(json);
			return null;
		}
		String job = paramMap.get("job");
		if (StringUtils.isBlank(job)) {
			json.put("msg", "请填写职位");
			JSONUtils.printObject(json);
			return null;
		}
		String monthlyIncome = paramMap.get("monthlyIncome");
		if (StringUtils.isBlank(monthlyIncome)) {
			json.put("msg", "请填写月收入");
			JSONUtils.printObject(json);
			return null;
		}
		String workYear = paramMap.get("workYear");
		if (StringUtils.isBlank(workYear)) {
			json.put("msg", "请填写现单位工作年限");
			JSONUtils.printObject(json);
			return null;
		}
		String companyTel = paramMap.get("companyTel");
		if (StringUtils.isBlank(companyTel)) {
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		if(!StringUtils.isNumeric(companyTel)){
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		if(companyTel.trim().length()<7||companyTel.trim().length()>11){
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		
		
		String workEmail = paramMap.get("workEmail");
		if (StringUtils.isBlank(workEmail)) {
			json.put("msg", "请填写工作邮箱");
			JSONUtils.printObject(json);
			return null;
		}
		String companyAddress = paramMap.get("companyAddress");
		if (StringUtils.isBlank(companyAddress)) {
			json.put("msg", "请填写公司地址");
			JSONUtils.printObject(json);
			return null;
		}
		String directedName = paramMap.get("directedName");
		if (StringUtils.isBlank(directedName)) {
			json.put("msg", "请填写直系人姓名");
			JSONUtils.printObject(json);
			return null;
		}else if (2 > directedName.length() || 50 < directedName.length()) {
			json.put("msg", "直系人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}
		String directedRelation = paramMap.get("directedRelation");
		if (StringUtils.isBlank(directedRelation)) {
			json.put("msg", "请填写直系人关系");
			JSONUtils.printObject(json);
			return null;
		}
		String directedTel = paramMap.get("directedTel");
		if (StringUtils.isBlank(directedTel)) {
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if(!StringUtils.isNumeric(directedTel)){
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (directedTel.trim().length()!=11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		
		String otherName = paramMap.get("otherName");
		if (StringUtils.isBlank(workPro.toString())) {
			json.put("msg", "请填写其他人姓名");
			JSONUtils.printObject(json);
			return null;
		}else if (2 > otherName.length() || 50 < otherName.length()) {
			json.put("msg", "其他人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}
		
		String otherRelation = paramMap.get("otherRelation");
		if (StringUtils.isBlank(otherRelation)) {
			json.put("msg", "请填写其他人关系");
			JSONUtils.printObject(json);
			return null;
		}
		String otherTel = paramMap.get("otherTel");
		if (StringUtils.isBlank(otherTel)) {
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		
		if(!StringUtils.isNumeric(otherTel)){
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (otherTel.trim().length()!=11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		String moredName = paramMap.get("moredName");
		if (StringUtils.isBlank(moredName)) {
			json.put("msg", "morename");
			JSONUtils.printObject(json);
			return null;
		}else if (2 > moredName.length() || 50 < moredName.length()) {
			json.put("msg", "更多联系人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}
		String moredRelation = paramMap.get("moredRelation");
		if (StringUtils.isBlank(moredRelation)) {
			json.put("msg", "morereation");
			JSONUtils.printObject(json);
			return null;
		}
		String moredTel = paramMap.get("moredTel");
		if (StringUtils.isBlank(moredTel)) {
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if(!StringUtils.isNumeric(moredTel)){
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if (moredTel.trim().length()!=11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		// 用户Id
		Long userId = user.getId();
		Long result = -1L;
		//判断用户是否已经是vip
		Map<String,String> vipMap  = null;
		vipMap = beVipService.queryUserById(user.getId());
		int vipStatus = 1;//1 为非vip 2 为vip 3 代扣费vip
	    int newutostept = -1;
		if(vipMap.size()>0&&vipMap!=null){
			vipStatus = Convert.strToInt(vipMap.get("vipStatus"), 1);
			newutostept = Convert.strToInt(vipMap.get("authStep"), -1);//用户此时的认证步骤状态
		}
		if(user!=null){
		result = userService.updateUserWorkData(orgName, occStatus, workPro,
				workCity, companyType, companyLine, companyScale, job,
				monthlyIncome, workYear, companyTel, workEmail, companyAddress,
				directedName, directedRelation, directedTel, otherName,
				otherRelation, otherTel, moredName, moredRelation, moredTel,
				userId,vipStatus,newutostept);
	  }
		if (result > 0) {
			// 保存成功更新认证步骤
			if (user.getAuthStep() == 2) {
				user.setAuthStep(3);
			}
			if(vipStatus!=1){//是vip会员
				//更新用户的session步骤和是更新user表中的认证步骤
				user.setAuthStep(4);
				json.put("msg", "vip保存成功");
				JSONUtils.printObject(json);
				return null;
			}else{
				json.put("msg", "保存成功");
				JSONUtils.printObject(json);
				return null;
			}
			
		} else {
			json.put("msg", "保存失败");
			JSONUtils.printObject(json);
			return null;
		}
	}

	/**
	 * (前台)个人设置中修改密码
	 * @return
	 */
	public String updatexgmm(){
		User user = (User) session().getAttribute("user");
		try {
			Map<String,String> pmap = userService.queryPersonById(user.getId());
			if(pmap != null &&  pmap.size() > 0){
				jsonMap.put("pass", pmap.get("auditStatus"));
			}
			int authStep = user.getAuthStep();
			if(authStep < 2){
				jsonMap.put("person", "0");
			}else{
				jsonMap.put("person", "1");
			}
			jsonMap.put("emailBound", user.getEmail());
			jsonMap.put("realName", user.getRealName());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getProCity() throws IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			provinceList = regionService.queryRegionList(-1L, 1L, 1);
			for(Map<String, Object> pro : provinceList){
				cityList = regionService.queryRegionList(-1L, Convert.strToLong(pro.get("regionId").toString(), -1),2);
				pro.put("cityList", cityList);
			}
			jsonMap.put("msg", "成功");
			jsonMap.put("provinceList", provinceList);
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	public UserService getUserService() {
		return userService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public List<Map<String, Object>> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Map<String, Object>> areaList) {
		this.areaList = areaList;
	}

	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}

	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}

	public long getWorkPro() {
		return workPro;
	}

	public void setWorkPro(long workPro) {
		this.workPro = workPro;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getRegPro() {
		return regPro;
	}

	public void setRegPro(long regPro) {
		this.regPro = regPro;
	}

	public long getRegCity() {
		return regCity;
	}

	public void setRegCity(long regCity) {
		this.regCity = regCity;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}


	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public SMSInterfaceService getPhonemsg() {
		return phonemsg;
	}

	public void setPhonemsg(SMSInterfaceService phonemsg) {
		this.phonemsg = phonemsg;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public BonusService getBonusService() {
		return bonusService;
	}

	public void setBonusService(BonusService bonusService) {
		this.bonusService = bonusService;
	}

	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public SendSMSService getSendSMSService() {
		return sendSMSService;
	}

	public void setSendSMSService(SendSMSService sendSMSService) {
		this.sendSMSService = sendSMSService;
	}
	
	
}