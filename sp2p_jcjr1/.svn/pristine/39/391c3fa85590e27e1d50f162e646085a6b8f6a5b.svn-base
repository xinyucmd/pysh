/**
 * 
 */
package com.sp2p.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import net.sf.json.JSONObject;

import com.lianpay.trust.enums.SignType;
import com.lianpay.trust.util.FuncUtils;
import com.lianpay.trust.util.HttpRequestSimple;
import com.lianpay.trust.util.security.Md5Algorithm;
import com.lianpay.trust.util.security.RSAUtil;
import com.shove.util.HttpClient;
import com.sp2p.constants.IConstants;
import com.udcredit.demo.config.DemoConfig;
import com.udcredit.demo.pojo.AntifraudRequest;
import com.udcredit.demo.util.ParameterFactory;
import com.udcredit.demo.util.SignatureHelper;

/**
 * @author David‎-RYE
 *
 */
public class AuthInfomationUtils {
	
	public static String CHECK_ID5_STATUS = "-1";// -1使用 ，0不使用
	
	public static Map<String,String> lainCheckId(String realName,String idNo){
		Map<String,String> jsonMap = new HashMap<String, String>();
		/**姓名和身份证一一对应*/
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		JSONObject jsons = new JSONObject();
		jsons.put("oid_partner", "201504231000296502");        	//您的商户号
		jsons.put("busi_type", "P01");				//业务类型，固定P01
		jsons.put("notify_type", "SYNC");			//通知方式，目前只支持SYNC同步返回
		jsons.put("sign_type", "MD5");		    //加密类型，MD5或者RSA，根据您商户站的相关设置为主
		jsons.put("no_order",String.valueOf(date.getTime()));				//订单号，由商户生成的数字或者字母或者数字字母组合的订单号
		jsons.put("dt_order", df.format(date));		  	//订单时间,YYYYMMDDH24MISS
		jsons.put("api_version", "1.0");				//接口版本,默认1.0
		jsons.put("name_user",realName);			//认证人姓名
		jsons.put("id_no", idNo);		  		//认证人身份证件号
		jsons.put("sign", LianSign.addSign(LianSign.genSignData(jsons),"MD5","","201504231000296502jcbanking"));  //加密串
		//jsons.put("sign", addSign(genSignData(json),"MD5","",IConstants.PASS_KEY));  //加密串
		
		HttpRequestSimple http = new HttpRequestSimple();
		String outbuffer = http.postSendHttp("https://yintong.com.cn/tradeauthapi/auth.htm", jsons.toString());
		System.out.println(outbuffer);
		Map<String, Object> map  = HttpClient.parseJSON2Map(outbuffer);
		String ret_code = (String) map.get("ret_code");
		String ret_msg = (String) map.get("ret_msg");
		String result_order = (String) map.get("result_order");
		
		jsonMap.put("ret_code", ret_code);
		jsonMap.put("ret_msg", ret_msg);
		jsonMap.put("result_order", result_order);
		
		return jsonMap;
	}
	
    public static class LianSign {

    	/**
         * 生成待签名串
         * @param paramMap
         * @return
         */
        public static String genSignData(JSONObject jsonObject){
            StringBuffer content = new StringBuffer();

            // 按照key做首字母升序排列
            List<String> keys = new ArrayList<String>(jsonObject.keySet());
            Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
            for (int i = 0; i < keys.size(); i++)
            {
                String key = (String) keys.get(i);
                // sign 和ip_client 不参与签名
                if ("sign".equals(key))
                {
                    continue;
                }
                String value = (String) jsonObject.getString(key);
                // 空串不参与签名
                if (FuncUtils.isNull(value))
                {
                    continue;
                }
                content.append((i == 0 ? "" : "&") + key + "=" + value);

            }
            String signSrc = content.toString();
            if (signSrc.startsWith("&"))
            {
                signSrc = signSrc.replaceFirst("&", "");
            }
            return signSrc;
        }
    	
        /**
         * 加签
         * @param reqObj
         * @param rsa_private
         * @param md5_key
         * @return
         */
        public static String addSign(String sign_src, String sign_type,String rsa_private, String md5_key) {
                if (sign_src == null || sign_type == null) {
                        return "";
                }
                if (SignType.MD5.getSignTypeCode().equals(sign_type)) {
                        return addSignMD5(sign_src, md5_key);
                } else {
                        return addSignRSA(sign_src, rsa_private);
                }
        }
        
        
        
        /**
         * MD5加签名
         * 
         * @param reqObj
         * @param md5_key
         * @return
         */
        public static String addSignMD5(String sign_src, String md5_key) {
                if (sign_src == null) {
                        return "";
                }
                
                sign_src += "&key=" + md5_key;
                            
                try {
                        return Md5Algorithm.getInstance().md5Digest(
                                        sign_src.getBytes("utf-8"));
                } catch (Exception e) {
                        return "";
                }
        }
        

        /**
         * RSA加签名
         * 
         * @param reqObj
         * @param rsa_private
         * @return
         */
        public static String addSignRSA(String sign_src, String rsa_private) {
                if (sign_src == null) {
                        return "";
                }
                
                
                try {
                        return RSAUtil.sign(rsa_private, sign_src);
                } catch (Exception e) {
                        return "";
                }
        }
    }
    
    /**
     * 校验实名认证
     * @param realName
     * @param idNo
     * @param orderId
     * @return
     */
    public static Map<String,String> cloudxCheckId5(String realName,String idNo,String orderId ){
    	Map<String,String> jsonMap = new HashMap<String, String>();
    	Map<String, String> param = ParameterFactory.getShimingParameterMap(realName,idNo,orderId);
        AntifraudRequest request = new AntifraudRequest();

        request.setPartnerCode(DemoConfig.PARTNER_CODE);
        request.setNonceStr(RandomStringUtils.randomNumeric(32));
        request.setStrategyCode(DemoConfig.SHIMING_STRATEGY_CODE);
        request.setScenarioCode(DemoConfig.SHIMING_SENARIO_CODE);
        request.setPackageStr(SignatureHelper.generateSignedPackageStr(param));
        request.setSignature(SignatureHelper.generateSignatureFromRequest(DemoConfig.SECRET_KEY, request));

        System.out.println(request);

        com.udcredit.demo.util.HttpRequestSimple httpRequestSimple = new com.udcredit.demo.util.HttpRequestSimple();
		String response = httpRequestSimple.postSendHttp(DemoConfig.SERVICE_URL,request.toString());
		System.out.println(response);
		Map<String, Object> map  = HttpClient.parseJSON2Map(response);
		 
		String ret_code = String.valueOf(map.get("retCode"));
		String ret_msg = String.valueOf(map.get("retMsg"));
		String result_order = String.valueOf(map.get("score"));
		
		if(IConstants.CHECK_ID.equals("1")){
			jsonMap.put("ret_code", ret_code);
			jsonMap.put("ret_msg", ret_msg);
			jsonMap.put("result_order", result_order);
		}else{
			jsonMap.put("ret_code", "0000");
			jsonMap.put("ret_msg", "通过");
			jsonMap.put("result_order","1");
		}
		
		return jsonMap;
	}
	
}
