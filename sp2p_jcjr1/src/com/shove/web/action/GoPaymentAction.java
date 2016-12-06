package com.shove.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.llpay.client.config.PartnerConfig;
import com.llpay.client.config.ServerURLConfig;
import com.llpay.client.utils.LLPayUtil;
import com.llpay.client.vo.OrderInfo;
import com.llpay.client.vo.PayDataBean;
import com.llpay.client.vo.PaymentInfo;
import com.llpay.client.vo.RetBean;
import com.shove.Convert;
import com.shove.config.GopayConfig;
import com.shove.security.Encrypt;
import com.shove.util.FormUtil;
import com.shove.web.util.GopayUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.RechargeDetailService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.system.exception.FrontHelpMessageException;
import com.sp2p.util.DateUtil;

/**
 * 国付宝在线充值
 * 
 * @author Administrator
 * 
 */
public class GoPaymentAction extends BasePageAction {

	private static Log log = LogFactory.getLog(GoPaymentAction.class);

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	private RechargeDetailService rechargeDetailService;
	private RechargeService rechargeService;

	private String urlParam = "";// 接口拼接的参数

	// 在线充值
	public String gopayPayment() throws Exception {
		User user = (User) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}
		String bankCode = request.getString("bankCode");
		String money = request.getString("money");
		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}
		BigDecimal moneyDecimal = null;
		try {
			moneyDecimal = new BigDecimal(money);
		} catch (RuntimeException e) {
			return INPUT;
		}
		int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
		if (temp < 0) {
			return INPUT;
		}
		long userId = this.getUserId();
		// 生成订单
		paramMap.put("rechargeMoney", moneyDecimal + "");
		paramMap.put("userId", userId + "");
		paramMap.put("result", "0");
		Date date = new Date();
		paramMap.put("addTime", DateUtil.dateToString(date));
		Map<String, String> result = rechargeService.addRecharge(paramMap, 3);
		long nunber = Convert.strToInt(result.get("result"), -1);

		if (nunber != -1) {

			String html = createGopayUrl("在线充值", nunber, userId, bankCode,
					DateUtil.YYYYMMDDHHMMSS.format(date), moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
			sendHtml(html);
			return null;
		} else {
			//whb修改支付失败为充值失败
			createHelpMessage("充值失败！" + result.get("description"), "返回首页",
					"index.do");
			return null;
		}

	}

	public String gopayQuery() throws Exception {
		User user = (User) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}

		String bankCode = request.getString("bankCode");
		String money = request.getString("money");
		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}
		BigDecimal moneyDecimal = null;
		try {
			moneyDecimal = new BigDecimal(money);
		} catch (RuntimeException e) {
			return INPUT;
		}
		int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
		if (temp < 0) {
			return INPUT;
		}
		long userId = this.getUserId();
		Date date = new Date();
		String html = createGopayQuery("在线充值", 1, userId, bankCode,
					DateUtil.YYYYMMDDHHMMSS.format(date), moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
		sendHtml(html);
		return null;

	}
	
	/**
	 * 跳转拦截
	 * 
	 * @param title
	 * @param msg
	 * @param url
	 * @throws FrontHelpMessageException
	 */
	public void createHelpMessage(String title, String msg, String url)
			throws FrontHelpMessageException {
		/* helpMessage.setTitle("用户不存在"); */
		helpMessage.setMsg(new String[] { "返回首页" });
		helpMessage.setUrl(new String[] { "index" });
		helpMessage.setTitle(title);
		/*
		 * helpMessage.setMsg(new String[]{msg}); helpMessage.setUrl(new
		 * String[]{url});
		 */
		throw new FrontHelpMessageException();
	}

	private String createGopayUrl(String body, long recharId, long userId,
			String bankCode, String tranDateTime, BigDecimal money)
			throws Exception {
		log.info("12");
		// 组装接口参数，并进行加密

		String version = GopayConfig.gopay_version;
		String charset = GopayConfig.gopay_input_charset;
		String language = "";
		String signType = GopayConfig.gopay_signtype;
		// 交易代码
		String tranCode = GopayConfig.gopay_tranCode;
		String merchantID = GopayConfig.gopay_merchantID;
		String merOrderNum = recharId + ""; // 订单号 ---- 支付流水号
		String tranAmt = money + "";
		String feeAmt = "0";
		String frontMerUrl = GopayConfig.gopay_frontMerUrl;
		String backgroundMerUrl = GopayConfig.gopay_backgroundMerUrl;
		String tranIP = GopayUtils.getIpAddr(request());
		String verficationCode = GopayConfig.gopay_verficationCode;
		String gopayServerTime = GopayUtils.getGopayServerTime();
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("version", version);
		map.put("charset", "2");
		map.put("language", "1");
		map.put("signType", "1");
		map.put("tranCode", tranCode);
		map.put("merchantID", merchantID);
		map.put("merOrderNum", merOrderNum);
		map.put("tranAmt", tranAmt);
		map.put("feeAmt", feeAmt);
		map.put("tranDateTime", tranDateTime);
		map.put("frontMerUrl", frontMerUrl);
		map.put("backgroundMerUrl", backgroundMerUrl);
		map.put("currencyType", "156");
		map.put("virCardNoIn", GopayConfig.gopay_virCardNoIn);
		if (!"DEFAULT".equals(bankCode)) {
			map.put("bankCode", bankCode);
			map.put("userType", "1");
		} else {
			map.put("bankCode", bankCode);
		}

		map.put("orderId", "");
		map.put("gopayOutOrderId", "");
		map.put("tranIP", tranIP);
		map.put("respCode", "");
		map.put("VerficationCode", verficationCode);
		map.put("gopayServerTime", gopayServerTime);
		map.put("merRemark1", com.shove.security.Encrypt.encryptSES(
				userId + "", GopayConfig.gopay_see_key));
		
		// 组织加密明文

		StringBuffer plain = new StringBuffer();
		plain.append("version=[");
		plain.append(version);
		plain.append("]tranCode=[");
		plain.append(tranCode);
		plain.append("]merchantID=[");
		plain.append(merchantID);
		plain.append("]merOrderNum=[");
		plain.append(merOrderNum);
		plain.append("]tranAmt=[");
		plain.append(tranAmt);
		plain.append("]feeAmt=[");
		plain.append(feeAmt);
		plain.append("]tranDateTime=[");
		plain.append(tranDateTime);
		plain.append("]frontMerUrl=[");
		plain.append(frontMerUrl);
		plain.append("]backgroundMerUrl=[");
		plain.append(backgroundMerUrl);
		plain.append("]orderId=[]gopayOutOrderId=[]tranIP=[");
		plain.append(tranIP);
		plain.append("]respCode=[]gopayServerTime=[");
		plain.append(gopayServerTime);
		plain.append("]VerficationCode=[");
		plain.append(verficationCode);
		plain.append("]");
		System.out.println("plain==>" + plain);
		String signValue = GopayUtils.md5(plain.toString());
		map.put("signValue", signValue);

		return FormUtil.buildHtmlForm(map, GopayConfig.gopay_gateway, "post");
	}
	
	private String createGopayQuery(String body, long recharId, long userId,
			String bankCode, String tranDateTime, BigDecimal money)
			throws Exception {
		// 组装接口参数，并进行加密
		String tranCode = GopayConfig.gopay_query_tranCode;
		String merchantID = GopayConfig.gopay_merchantID;
		String merOrderNum = recharId + ""; // 订单号 ---- 支付流水号
		String tranIP = GopayUtils.getIpAddr(request());
		String verficationCode = GopayConfig.gopay_verficationCode;
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("tranCode",tranCode);
		map.put("tranDateTime",tranDateTime);
		map.put("merOrderNum",merOrderNum);
		map.put("merchantID",merchantID);
		map.put("orgOrderNum",merOrderNum);
		map.put("orgtranDateTime","20131106135701");
		map.put("orgTxnType","");
		map.put("orgtranAmt","");
		map.put("orgTxnStat","");
		map.put("authID","");
		map.put("isLocked","");
		map.put("respCode","");
		map.put("tranIP", tranIP);
		map.put("msgExt","");
		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append("tranCode=["+tranCode);
		plain.append("]merchantID=["+merchantID);
		plain.append("]merOrderNum=["+merOrderNum);
		plain.append("]tranAmt=[");
		plain.append("]ticketAmt=[]tranDateTime=["+tranDateTime);
		plain.append("]currencyType=[");
		plain.append("]merURL=[");
		plain.append("]customerEMail=[");
		plain.append("]authID=[");
		plain.append("]orgOrderNum=["+merOrderNum);
		plain.append("]orgtranDateTime=["+"20131106135701");
		plain.append("]orgtranAmt=[");
		plain.append("]orgTxnType=[");
		plain.append("]orgTxnStat=[");
		plain.append("]msgExt=[]virCardNo=[]virCardNoIn=[");
		plain.append("]tranIP=[" + tranIP);
		plain.append("]isLocked=[]feeAmt=[]respCode=[");
		plain.append("]VerficationCode=[" + verficationCode + "]");
		
		String signValue = GopayUtils.sign(plain.toString());
		map.put("signValue", signValue);

		return FormUtil.buildHtmlForm(map, GopayConfig.gopay_gateway, "post");
	}

	/**
	 * 前台调用函数
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frontMerUrl() throws Exception {
		log.info("1--frontMerUrl");
		return backgroundMerUrl();
	}

	/**
	 * 国付宝回调函数
	 * 
	 * @return
	 * @throws Exception
	 */
	public String backgroundMerUrl() throws Exception {
		log.info("1-----backgroundMerUrl");

		String respCode = request.getString("respCode");// 
		log.info("2--" + respCode);
		if (!"0000".equals(respCode) && !"9999".equals(respCode)) {
			log.info("3--");
			createHelpMessage("充值失败！", "返回首页", "index.do");
		}
		if ("9999".equals(respCode)) {
			log.info("4--");
			createHelpMessage("订单处理中，请耐心等待！", "返回首页", "index.do");
		}
		String merchantID = request.getString("merchantID");// 商户号
		log.info("4--" + merchantID);
		if (!validateSign()) {
			log.info("5--validate sign fail");
			createHelpMessage("充值失败！", "返回首页", "index.do");
		}

//		// 国付宝支付编号
//		String orderId = URLDecoder.decode(request.getString("orderId"), "utf-8");
//
//		// 交易完成时间
//		String tranFinishTime = URLDecoder.decode(request.getString("tranFinishTime"),
//				"utf-8");

		String paybank = GopayConfig.bankMap.get(request.getString("bankCode"));// 
		if (StringUtils.isBlank(paybank)) {// 如果没有银行编号说明是支付宝直接支付的
			paybank = "国付宝充值";
		}

		String attach = request.getString("merRemark1");
		double money = request.getDouble("tranAmt", -1);
		String in_paynumber = request.getString("merOrderNum");
		long userId = Convert.strToLong(Encrypt.decryptSES(attach,
				GopayConfig.gopay_see_key), -1);
		Map<String, String> resultMap = rechargeService.addUseraddmoney(userId,
				money, in_paynumber, paybank);
		String result = resultMap.get("result");
		String description = resultMap.get("description");

		HttpServletResponse httpServletResponse = ServletActionContext
				.getResponse();
		httpServletResponse.setCharacterEncoding("utf-8");
		PrintWriter pw = httpServletResponse.getWriter();
		String msg = description;
		userService.updateSign(userId);//更换校验码
		if (!"0".endsWith(result)) {
			log.info("6--");
			pw.println("fail");
			createHelpMessage(msg, "返回首页", "index.do");
		}
		msg = "充值成功";
		pw.println("success");
		createHelpMessage(msg + "", "返回首页", "index.do");

		return "message";
	}
	
	public String merUrl() throws Exception {
		log.info("1-----backgroundMerUrl");

		String respCode = request.getString("respCode");// 
		log.info("2--" + respCode);
		
		createHelpMessage(respCode + "", "返回首页", "index.do");
		return "message";
	}

	private static long getDistanceTime(Date one, Date two) {// 判断相隔多少天。
		long day = 0;
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		diff = time1 - time2;
		day = diff / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 返回验证参数
	 * 
	 * @return
	 */
	public boolean validateSign() {
		StringBuffer plain = new StringBuffer();
		plain.append("version=[");
		plain.append(request.getString("version"));
		plain.append("]tranCode=[");
		plain.append(request.getString("tranCode"));
		plain.append("]merchantID=[");
		plain.append(request.getString("merchantID"));
		plain.append("]merOrderNum=[");
		plain.append(request.getString("merOrderNum"));
		plain.append("]tranAmt=[");
		plain.append(request.getString("tranAmt"));
		plain.append("]feeAmt=[");
		plain.append(request.getString("feeAmt"));
		plain.append("]tranDateTime=[");
		plain.append(request.getString("tranDateTime"));
		plain.append("]frontMerUrl=[");
		plain.append(request.getString("frontMerUrl"));
		plain.append("]backgroundMerUrl=[");
		plain.append(request.getString("backgroundMerUrl"));
		plain.append("]orderId=[");
		plain.append(request.getString("orderId"));
		plain.append("]gopayOutOrderId=[");
		plain.append(request.getString("gopayOutOrderId"));
		plain.append("]tranIP=[");
		plain.append(request.getString("tranIP"));
		plain.append("]respCode=[");
		plain.append(request.getString("respCode"));
		plain.append("]gopayServerTime=[]VerficationCode=[");
		plain.append(GopayConfig.gopay_verficationCode);
		plain.append("]");
		String sign = GopayUtils.md5(plain.toString());
		return sign.equals(request.getString("signValue"));
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRechargeDetailService(
			RechargeDetailService rechargeDetailService) {
		this.rechargeDetailService = rechargeDetailService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}
	

	
	/**连连支付开始***************************************************/
	public String lianPayment(){
		User user = (User) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}
        try {
        	
	        OrderInfo order = createOrder(request());
	        String paymod =  request().getParameter("paymod");
	        if ("plain".equals(paymod)){
	        	
	        	// 生成订单
	    		paramMap.put("rechargeMoney", order.getMoney_order() + "");
	    		paramMap.put("userId", user.getId() + "");
	    		paramMap.put("result", "0");
	    		/*Date date = new Date();
	    		paramMap.put("addTime", DateUtil.dateToString(date));*/
	    		Map<String, String> result = rechargeService.addRecharge(paramMap, 10);
	    		long nunber = Convert.strToInt(result.get("result"), -1);

	    		if (nunber != -1) {
	    			 order.setNo_order(String.valueOf(nunber));//配置订单号
	    			 plainPay(request(), order,user.getId());
	    		}else{
	    			log.info("连连支付订单提交失败");
	    		} 
	        } 
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return "success";
	}
	
	
	/**
     * 普通支付处理
     * @param req
     * @param order
     */
	private void plainPay(HttpServletRequest req, OrderInfo order,long userId)
    {
        // 构造支付请求对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setVersion(PartnerConfig.VERSION);
        paymentInfo.setOid_partner(PartnerConfig.OID_PARTNER);
        paymentInfo.setUser_id(String.valueOf(userId));
        paymentInfo.setSign_type(PartnerConfig.SIGN_TYPE);//MD5
        paymentInfo.setBusi_partner(PartnerConfig.BUSI_PARTNER);
        paymentInfo.setNo_order(order.getNo_order());
        paymentInfo.setDt_order(order.getDt_order());
        paymentInfo.setName_goods(order.getName_goods());
        paymentInfo.setInfo_order(order.getInfo_order());
        paymentInfo.setMoney_order(order.getMoney_order());
        paymentInfo.setNotify_url(PartnerConfig.NOTIFY_URL);
        paymentInfo.setUrl_return(PartnerConfig.URL_RETURN);
        paymentInfo.setUserreq_ip(LLPayUtil.getIpAddr(req));
        paymentInfo.setUrl_order("");
        paymentInfo.setValid_order("10080");// 单位分钟，可以为空，默认7天
        paymentInfo.setTimestamp(LLPayUtil.getCurrentDateTimeStr());
        paymentInfo.setRisk_item(createRiskItem());
        // 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON
                .toJSONString(paymentInfo)), PartnerConfig.TRADER_PRI_KEY,
                PartnerConfig.MD5_KEY);
        paymentInfo.setSign(sign);

        req.setAttribute("version", paymentInfo.getVersion());
        req.setAttribute("oid_partner", paymentInfo.getOid_partner());
        req.setAttribute("user_id", paymentInfo.getUser_id());
        req.setAttribute("sign_type", paymentInfo.getSign_type());
        req.setAttribute("busi_partner", paymentInfo.getBusi_partner());
        req.setAttribute("no_order", paymentInfo.getNo_order());
        req.setAttribute("dt_order", paymentInfo.getDt_order());
        req.setAttribute("name_goods", paymentInfo.getName_goods());
        req.setAttribute("info_order", paymentInfo.getInfo_order());
        req.setAttribute("money_order", paymentInfo.getMoney_order());
        req.setAttribute("notify_url", paymentInfo.getNotify_url());
        req.setAttribute("url_return", paymentInfo.getUrl_return());
        req.setAttribute("userreq_ip", paymentInfo.getUserreq_ip());
        req.setAttribute("url_order", paymentInfo.getUrl_order());
        req.setAttribute("valid_order", paymentInfo.getValid_order());
        req.setAttribute("timestamp", paymentInfo.getTimestamp());
        req.setAttribute("sign", paymentInfo.getSign());
        req.setAttribute("risk_item", paymentInfo.getRisk_item());
        req.setAttribute("req_url", ServerURLConfig.PAY_URL);

    }
	
	
	/**
     * 模拟商户创建订单
     * @param req
     * @return
     */
    private OrderInfo createOrder(HttpServletRequest req) {
        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setNo_order(LLPayUtil.getCurrentDateTimeStr());
        orderInfo.setDt_order(LLPayUtil.getCurrentDateTimeStr());
        orderInfo.setMoney_order(req.getParameter("money_order"));
        orderInfo.setName_goods("充值");
        orderInfo.setInfo_order("连连充值，" + req.getParameter("name_goods"));
        return orderInfo;
    }
    
    /**
	 * 国付宝回调函数
	 * 
	 * @return
	 * @throws Exception
	 */
	public String lianlianNotify() throws Exception {
		log.info("进入支付异步通知数据接收处理");
        RetBean retBean = new RetBean();
        
        String reqStr = LLPayUtil.readReqStr(request());
        HttpServletResponse resp = response();
        
        
        if (LLPayUtil.isnull(reqStr))
        {
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            createHelpMessage("充值失败！", "返回首页", "index.do");
        }
        log.info("接收支付异步通知数据：【" + reqStr + "】");
        try
        {
            if (!LLPayUtil.checkSign(reqStr, PartnerConfig.YT_PUB_KEY,
                    PartnerConfig.MD5_KEY))
            {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                log.info("支付异步通知验签失败");
                createHelpMessage("充值失败[支付异步通知验签失败]！", "返回首页", "index.do");
            }
        } catch (Exception e)
        {
            log.info("异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            createHelpMessage("充值失败[异步通知报文解析异常]！", "返回首页", "index.do");
        }
        retBean.setRet_code("0000");
        retBean.setRet_msg("交易成功");
        resp.getWriter().write(JSON.toJSONString(retBean));
        resp.getWriter().flush();
        log.info("支付异步通知数据接收处理成功");
        // 解析异步通知对象
        PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
        
        // 处理连连的返回结果
		String paybank =payDataBean.getBank_code();// 
		if (StringUtils.isBlank(paybank)) {// 如果没有银行编号说明是支付宝直接支付的
			paybank = "连连支付充值";
		}

		double money = Convert.strToDouble(payDataBean.getMoney_order(), 0);// 交易金额
		String in_paynumber = payDataBean.getNo_order(); // 商户订单号，商户充值时传给连连的
		Map<String,String> rechargeDetailMap = rechargeService.queryRechargeDetailById(in_paynumber);
		String userId = rechargeDetailMap.get("userId");
		Map<String, String> resultMap = rechargeService.addUseraddmoney(Convert.strToLong(userId, 0),
				money, in_paynumber, paybank);
		
		log.info("保存结果==resultMap["+resultMap+"]");
		//充值成功添加站内系统消息提示
		rechargeService.addPayMail(Long.parseLong(userId),money);
//		String result = resultMap.get("result");
//		String description = resultMap.get("description");
//
//		HttpServletResponse httpServletResponse = ServletActionContext
//				.getResponse();
//		httpServletResponse.setCharacterEncoding("utf-8");
//		PrintWriter pw = httpServletResponse.getWriter();
//		String msg = description;
//		
//		if (!"0".endsWith(result)) {
//			log.info("6--");
//			pw.println("fail");
//			createHelpMessage(msg, "返回首页", "index.do");
//		}
//		msg = "充值成功";
//		pw.println("success");
//		createHelpMessage(msg + "", "返回首页", "index.do");

		return "message";
	}
    
    
    /**
     * 根据连连支付风控部门要求的参数进行构造风控参数
     * @return
     */
    private String createRiskItem(){
        JSONObject riskItemObj = new JSONObject();
        riskItemObj.put("user_info_full_name", "hello");
        riskItemObj.put("frms_ware_category", "1999");
        return riskItemObj.toString();
    }

}
