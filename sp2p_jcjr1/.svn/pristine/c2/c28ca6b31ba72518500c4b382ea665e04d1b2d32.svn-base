package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.DesSecurityUtil;
import com.shove.util.ServletUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.service.FinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.service.admin.RechargebankService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;

public class RechargeAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;
	private RechargeService rechargeService;
	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;
	private FinanceService financeService;
	private AdminService adminService;
	private RechargebankService rechargebankService;
	private FundManagementService fundManagementService;
	private MyHomeService myHomeService;
	

	//whb充值状态
	private List<Map<String, Object>> results;
		
		
	public List<Map<String, Object>> getResults() {
		if (results == null) {
			results = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("resultId", -100);
			mp.put("resultValue", "--请选择--");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 1);
			mp.put("resultValue", "充值成功");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 0);
			mp.put("resultValue", "充值失败");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 3);
			mp.put("resultValue", "审核中");
			results.add(mp);
		}
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}
		
		
	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	private List<Map<String, Object>> potypeList;
	private List<Map<String, Object>> banksList;

	public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	/**
	 * 充值提现页面初始化
	 * 
	 * @return
	 */
	public String rechargePageInit() {
		return SUCCESS;
	}

	public String rechargeInit() throws Exception {
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Map<String, String> m = new HashMap<String, String>();
		m = userService.queryUserById(user.getId());
		if (null != m) {
			if (m.get("password").equals(m.get("dealpwd"))) {
				getOut()
				.print(
						"<script>alert('*您还没有修改交易密码，为了您的帐号安全，请您先修改交易密码! ');window.location.href='setPwd.do';</script>");
				return null;
			}
			int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
			/*if (isApplyPro == 1) {
				getOut()
						.print(
								"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
				return null;

			}*/
		}

		boolean re = userService.checkSign(user.getId());
		if (!re) {
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut()
					.print(
							"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		request().setAttribute("realName", user.getRealName());
		request().setAttribute("email", user.getUserName());
		fundManagementService.queryAccountPaymentPage(pageBean);
		List<Map<String, Object>> list = pageBean.getPage();
		for (Map<String, Object> map : list) {
			if (map.get("nid").equals("gopay")) {
				request().setAttribute("gopay", map.get("status"));
			} else if (map.get("nid").equals("IPS")) {
				request().setAttribute("IPS", map.get("status"));
			}

		}
		
		//查询提现手续费
		Map<String, String> cost =  fundManagementService.queryPlatformCost();
		if(cost != null){
			request().setAttribute("cost", cost);
		}
		
		return SUCCESS;
	}

	/**
	 * 提现信息加载
	 * 
	 * @return
	 * @throws Exception
	 */
	public String withdrawLoad() throws Exception {
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号

		try {

			Map<String, String> m = new HashMap<String, String>();
			m = userService.queryUserById(user.getId());
			if (null != m) {
				if (m.get("password").equals(m.get("dealpwd"))) {
					getOut()
					.print(
							"<script>alert('*您还没有修改交易密码，为了您的帐号安全，请您先修改交易密码! ');window.location.href='setPwd.do';</script>");
					return null;
				}
				int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
				/*if (isApplyPro == 1) {
					getOut()
							.print(
									"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
					return null;
				}*/
			}

			boolean re = userService.checkSign(userId);
			if (!re) {
				request().getSession().removeAttribute("user");
				request().getSession().invalidate();
				getOut()
						.print(
								"<script>alert('*你的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
				return null;
			}
			int authStep = user.getAuthStep();// 需要填写个人资料之后才能申请提现
			if (authStep < 2) {
				getOut()
						.print(
								"<script>alert(' 您还没有进行实名认证，请前去认证! ');window.location.href='updateUserBaseDataJbxxrz.do';</script>");
				// return "personal";
				return null;
			}
			List<Map<String, Object>> lists = rechargeService
					.withdrawLoad(userId);
			// 需要加载信息 真实姓名 手机号码 帐户余额 可用余额 冻结总额 提现银行
			String realName = user.getRealName();
			String bindingPhone = null;
			int status = -1;
			String[] vals = moneyVal(userId);

			request().setAttribute("handleSum", vals[0]);
			request().setAttribute("usableSum", vals[1]);
			request().setAttribute("freezeSum", vals[2]);
			request().setAttribute("max_withdraw", IConstants.WITHDRAW_MAX);// 最大充值金额，超过之后要收取手续费
			if (lists != null && lists.size() > 0) {
				if (lists.get(0).get("bindingPhone") != null) {
					bindingPhone = lists.get(0).get("bindingPhone").toString();
				}
				if (lists.get(0).get("status") != null) {
					status = Convert.strToInt(lists.get(0).get("status")
							.toString(), -1);
				}

				// 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
				if (bindingPhone == null
						|| status != IConstants.PHONE_BINDING_ON) {
					bindingPhone = lists.get(0).get("cellPhone") + "";
				}
				// 绑定的银行卡信息单独查询
				List<Map<String, Object>> banks = homeInfoSettingService
						.querySuccessBankInfoList(userId);
				request().setAttribute("banks", banks);
				request().setAttribute("realName", realName);
				Map<String, Object> bankMap= null;
				int temp = 0;
				for(int i=0;i<banks.size();i++){
					bankMap = banks.get(i);
					String unique_mark = String.valueOf(bankMap.get("unique_mark"));
					if("1".equals(unique_mark)){
						temp = 1;
						break;
					}
				}
				request().setAttribute("temp", temp);
				
				//判断用户是否用营销账户
				Map<String,String> userYxMap = homeInfoSettingService.queryUserYxById(userId);
				if(userYxMap!=null && userYxMap.size()>0){//说明当前账户是营销账户
					String tell = userYxMap.get("tell");
					request().setAttribute("bindingPhone", tell);
				}else{
				    request().setAttribute("bindingPhone", bindingPhone);
				}
				
				
			}
			request().setAttribute("ISDEMO", IConstants.ISDEMO);
			 
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 加载提现记录信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryWithdrawList() throws Exception {
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);

		Long userId = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		try {
			rechargeService.queryWithdrawList(pageBean, userId, 0, null);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String addRechargeoutline() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> recharMap = new HashMap<String, String>();
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userid = user.getId();
		String rechargeMoneystr = paramMap.get("money") + "";
		String realname = paramMap.get("realname");
		Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$");
		Matcher m = pattern.matcher(rechargeMoneystr);
		if (StringUtils.isBlank(realname)
				|| !realname.equals(user.getRealName())) {
			jsonMap.put("msg", " 请输入正确的真实姓名！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		if (!m.matches()) {
			jsonMap.put("msg", " 请输入正确的充值金额！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		double rechargeMoney = Convert.strToDouble(rechargeMoneystr, 0);
		if (rechargeMoney <= 0) {
			jsonMap.put("msg", "充值金额不能小于零！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		String bankName = paramMap.get("bankName");
		if (StringUtils.isBlank(bankName)) {
			jsonMap.put("msg", "请选定充值帐户！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		String rechargeNumber = paramMap.get("rechargeNumber") + "";
		String remark = paramMap.get("remark") + "";
		if (StringUtils.isBlank(rechargeNumber)) {
			jsonMap.put("msg", " 交易编号不能为空！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		if (StringUtils.isBlank(remark)) {
			jsonMap.put("msg", "线下充值备注不能为空！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		recharMap.put("rechargeMoney", rechargeMoney + "");
		recharMap.put("userId", userid + "");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recharMap.put("rechargeTime", df.format(new Date()) + "");
		recharMap.put("rechargeType", 4 + "");
		recharMap.put("result", 3 + "");// 3为审核中
		recharMap.put("rechargeNumber", rechargeNumber);
		recharMap.put("remark", remark);
		recharMap.put("bankName", bankName);
		long result = -1;
		result = rechargeService.addRechargeoutline(recharMap);

		if (result < 0) {
			jsonMap.put("msg", "提交失败");
			JSONUtils.printObject(jsonMap);
			return null;
		}

		jsonMap.put("msg", "提交成功");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	public String addWithdraw() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Map<String, String> m = new HashMap<String, String>();
		m = userService.queryUserById(user.getId());
		if (null != m) {
			if (m.get("password").equals(m.get("dealpwd"))) {
				getOut()
				.print(
						"<script>alert('*您还没有修改交易密码，为了您的帐号安全，请您先修改交易密码! ');window.location.href='setPwd.do';</script>");
				return null;
			}
			int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
			/*if (isApplyPro == 1) {
				getOut()
						.print(
								"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
				return null;
			}*/
		}
		JSONObject obj = new JSONObject();
		// ip地址
		String ipAddress = ServletUtils.getRemortIp();
		String dealpwd = paramMap.get("dealpwd");
		String money = paramMap.get("money");
		double moneyD = Convert.strToDouble(money, 0);
		String code = paramMap.get("code");
		String bankId = paramMap.get("bankId");
		long bankIdL = Convert.strToLong(bankId, -1);
		String type = paramMap.get("type");
		long userId = user.getId();// 获得用户编号
		boolean re = userService.checkSign(userId);
		if (!re) {
			obj.put("msg", "*你的账号出现异常，请速与管理员联系！");
			JSONUtils.printObject(obj);
			return null;
		}
		// 验证码
		if (!IConstants.ISDEMO.equals("1")) {
			if (StringUtils.isBlank(code)) {
				obj.put("msg", "请填写验证码");
				JSONUtils.printObject(obj);
				return null;
			}
			Object obje = session().getAttribute("randomCode");
			if (obje != null) {
				String randomCode = obje.toString();
				//验证码不区分大小写
				if (!randomCode.toLowerCase().equals(code.toLowerCase())) {
					obj.put("msg", "验证码错误");
					JSONUtils.printObject(obj);
					return null;
				}
			} else {
				obj.put("msg", "验证码无效");
				JSONUtils.printObject(obj);
				return null;
			}
		}
		if (StringUtils.isBlank(bankId)) {
			obj.put("msg", "请先选择或者设置银行卡信息");
			JSONUtils.printObject(obj);
			return null;
		}
		if (moneyD <= 0) {
			obj.put("msg", "错误金额格式");
			JSONUtils.printObject(obj);
			return null;
		}
		
		Map<String,String> map1 = rechargeService.queryInvestNew(userId);
		Map<String,String> map2 = rechargeService.queryInvest(userId);
		String state = "0";
		String temp = "0";
		if(map1!=null && map1.size()>0){//用过体验金投资
			state = "1";//1-已还款啦
		}
		
		if(state.equals("1")){
			if(map2!=null && map2.size()>0){
				double investAmount = Convert.strToDouble(map2.get("investAmount"), 0);
				String proc_1 = map1.get("proc_1");
				String proc_2 = map1.get("proc_2");
				
				// 活动第一阶段
				if(proc_1 != null && proc_1.equals("1")){
					if(investAmount>=100){
						temp = "1";//存在真实投资记录
					}
				}
				
				// 活动第二阶段
				if(temp.equals("0")){
					if(proc_2 != null && proc_2.equals("1")){
						if(investAmount>=200){
							temp = "1";//存在真实投资记录
						}
					}
				}
				
			}
		}
		
		if("1".equals(state) && "0".equals(temp)){
			double usableSum = Convert.strToDouble(rechargeService.queryUserAmount(userId).get("usableSum"),0);
			double moeny = usableSum - moneyD;
			moeny = AmountUtil.getRepaymentMoney(moeny);
			if(moeny<=15.58){
				obj.put("msg", "您尚未投资或者投资不符合活动规则，体验金收益不能提现！");
				JSONUtils.printObject(obj);
				return null;
			}
		}
		
		if (StringUtils.isBlank(dealpwd)) {
			obj.put("msg", "请填写交易密码");
			JSONUtils.printObject(obj);
			return null;
		}
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		Map<String, String> retMap = rechargeService.withdrawApply(userId,
				moneyD, dealpwd, bankIdL, type, ipAddress);
		userService.updateSign(userId);// 更换校验码
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	public static String getNeedTime(Date currDate) {
		try {
			String currDateStr = DateUtil.YYYY_MM_DD.format(currDate);
			currDate = DateUtil.YYYY_MM_DD.parse(currDateStr);
			long currTime = currDate.parse(currDate.toString());
			long delTime = IConstants.WITHDRAW_TIME * 24 * 60 * 60 * 1000;
			long needTime = currTime - delTime;
			Date needDate = new Date();
			needDate.setTime(needTime);
			String needDateStr = DateUtil.YYYY_MM_DD.format(needDate);
			return needDateStr;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除提现记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteWithdraw() throws Exception {
		JSONObject obj = new JSONObject();
		long wid = Convert.strToLong(paramMap.get("wId"), -1);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		try {
			// 删除提现记录
			Map<String, String> resultMap = rechargeService.deleteWithdraw(
					userId, wid);
			long result = Convert.strToLong(resultMap.get("ret") + "", -1);

			userService.updateSign(userId);// 更换校验码
			if (result <= 0) {
				obj.put("msg", resultMap.get("ret_desc") + "");
				JSONUtils.printObject(obj);
				return null;
			} else {
				obj.put("msg", "1");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	public String queryFundrecordInit() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		String[] vals = fundVal(userId);

		request().setAttribute("handleSum", vals[0]);
		request().setAttribute("usableSum", vals[1]);
		request().setAttribute("freezeSum", vals[2]);
		
		Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(userId);
		request().setAttribute("accmountStatisMap", accmountStatisMap);

		return SUCCESS;
	}

	private String[] fundVal(long userId) throws Exception {
		String[] val = new String[3];
		for (int i = 0; i < val.length; i++) {
			val[i] = "0";
		}
		try {
			Map<String, String> map = rechargeService.queryFund(userId);
			if (map != null) {
				double usableSum = Convert.strToDouble(map.get("usableSum"), 0);
				double freezeSum = Convert.strToDouble(map.get("freezeSum"), 0);
				double dueinSum = Convert.strToDouble(map.get("forPI"), 0);
				double handleSum = usableSum + freezeSum + dueinSum;
				val[0] = String.format("%.2f", handleSum);
				val[1] = String.format("%.2f", usableSum);
				val[2] = String.format("%.2f", freezeSum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return val;
	}

	public String[] moneyVal(long userId) throws Exception {
		String[] val = new String[3];
		for (int i = 0; i < val.length; i++) {
			val[i] = "0";
		}
		try {
			Map<String, String> map = userService.queryUserById(userId);
			if (map != null) {
				double usableSum = Convert.strToDouble(map.get("usableSum"), 0);
				double freezeSum = Convert.strToDouble(map.get("freezeSum"), 0);
				double handleSum = usableSum + freezeSum;
				val[0] = String.format("%.2f", handleSum);// df.format(handleSum);
				val[1] = String.format("%.2f", usableSum);// df.format(usableSum);
				val[2] = String.format("%.2f", freezeSum);// df.format(freezeSum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return val;
	}

	public String queryFundrecordList() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号

		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = Convert.strToStr(paramMap.get("endTime"), null);
		String momeyType = Convert.strToStr(paramMap.get("momeyType"), "");
		if (endTime != null) {
			String[] strs = endTime.split("-");
			// 结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert
					.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1),
					0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			endTime = formatter.format(date);
		}

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);

		rechargeService.queryFundrecordList(pageBean, userId, startTime,
				endTime, momeyType);
		paramMap = rechargeService.queryFundrecordSum(userId, startTime,
				endTime, momeyType);
		String[] val = moneyVal(userId);
		paramMap.put("SumusableSum", val[1]);
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return SUCCESS;
	}

	/**
	 * 添加充值记录
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */

	public String queryRechargeInfo() throws Exception {
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		List<Map<String, Object>> lists = null;
		int type = -1, status = -1;
		try {

			pageBean.setPageSize(IConstants.PAGE_SIZE_6);

			rechargeService.queryRechargeInfo(pageBean, userId);
			lists = pageBean.getPage();
			if (lists != null) {
				for (Map<String, Object> map : lists) {
					map.put("userId", user.getUserName());
					type = Convert.strToInt(map.get("rechargeType").toString(),
							-1);
					status = Convert.strToInt(map.get("result").toString(), -1);
					if (status == IConstants.RECHARGE_SUCCESS) {
						map.put("status", IConstants.RECHARGE_SUCCESS_STR);
					} else {
						map.put("status", IConstants.RECHARGE_CHECKING_STR);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 发送手机验证码
	 * 
	 * @return
	 */
	public String sendPhoneCode() {
		Random rand = new Random();
		int num = rand.nextInt(9999);
		while (num < 1000) {
			num = rand.nextInt(9999);
		}
		System.out.println("num====>" + num);
		String type = request.getString("type");
		session().setAttribute(type + "_phoneCode", num);
		try {
			JSONUtils.printStr(num + "");
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String setUniqueBankCardMark()  throws Exception{
		JSONObject obj = new JSONObject();
		long id = Convert.strToLong(paramMap.get("bank_id"), -1);
		 
		try {
			 
			long result = userService.updateUniqueBankCardMark(id);
			if (result <= 0) {
				obj.put("msg", "0");
				JSONUtils.printObject(obj);
				return null;
			} else {
				obj.put("msg", "1");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 线下充值审核
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateRechargeDetailStatusById() throws Exception {

		String award = paramMap.get("award");
		//whb
		String checkremark = paramMap.get("checkremark") == null ? "" : paramMap.get("checkremark");

		if (StringUtils.isBlank(award)) {
			award = "";
		} else {
			Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$");
			Matcher m = pattern.matcher(award);
			if (!m.matches()) {
				this.addFieldError("paramMap.allerror", "输入奖励比例错误");
				return "input";
			}
		}
		long id = Convert.strToLong(new DesSecurityUtil().decrypt(paramMap.get("ids")), -1);
		long userid = Convert.strToLong(new DesSecurityUtil().decrypt(paramMap.get("userIds")), -1);
		if (id == -1) {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		if (userid == -1) {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		String typeStr = paramMap.get("type");
		long type = -1;
		if (typeStr.equals("a")) {// 审核成功
			type = 1;
		} else if (typeStr.equals("b")) {
			type = 0;
		} else {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		double rechargeMoney = Convert.strToDouble(paramMap
				.get("rechargeMoney")
				+ "", 0);
		if (rechargeMoney == 0) {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		long result = rechargeService.updateRechargeDetailStatusById(id,
				userid, type, rechargeMoney, award, checkremark);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_recharge_detail", admin
				.getUserName(), IConstants.UPDATE, admin.getLastIP(),
				rechargeMoney, "线下审核", 2);
		userService.updateSign(userid);// 更换校验码
		if (result <= 0) {
			getOut()
					.print(
							"<script>alert('审核失败');window.location.href='queryxxRechargeInit.do';</script>");
			return null;
		}
		return SUCCESS;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public List<Map<String, Object>> getPotypeList() throws Exception {
		if (potypeList == null) {
			potypeList = rechargeService.queryTypeFund();
		}
		return potypeList;
	}

	public void setPotypeList(List<Map<String, Object>> potypeList) {
		this.potypeList = potypeList;
	}

	public List<Map<String, Object>> getBanksList() throws Exception {
		try {
			banksList = rechargebankService.queryrechargeBanklist();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return banksList;
	}

	public void setBanksList(List<Map<String, Object>> banksList) {
		this.banksList = banksList;
	}

	public RechargebankService getRechargebankService() {
		return rechargebankService;
	}

	public void setRechargebankService(RechargebankService rechargebankService) {
		this.rechargebankService = rechargebankService;
	}

}