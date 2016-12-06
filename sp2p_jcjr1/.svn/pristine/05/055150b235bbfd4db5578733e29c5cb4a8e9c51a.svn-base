package com.lianpay.trust.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lianpay.trust.enums.SignType;
import com.lianpay.trust.util.FuncUtils;
import com.lianpay.trust.util.HttpRequestSimple;
import com.lianpay.trust.util.security.Md5Algorithm;
import com.lianpay.trust.util.security.RSAUtil;
import com.shove.util.HttpClient;


/**
 * @name   demo
 * @author Eric
 * @email  mataojs@lianlian.com
 * @date   2015年3月13日 下午3:15:35
 * @update #update time#
 * @modify #modify user#

 */
public class Demo{

	public static void main(String[] args) {
		new Demo().aaa();
	}
	
	@Test
	public void aaa(){
		
		
		/**
		 * 订单时间生成规则
		 */
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date nowDate = new Date();
    	String date = df.format(nowDate);
		
    	
    	
    	/**
    	 * 交易发起
    	 */
		JSONObject json = new JSONObject();
		json.put("oid_partner", "201504231000296502");        	//您的商户号
		json.put("busi_type", "P01");				//业务类型，固定P01
		json.put("notify_type", "SYNC");			//通知方式，目前只支持SYNC同步返回
		json.put("sign_type", "MD5");		    //加密类型，MD5或者RSA，根据您商户站的相关设置为主
		json.put("no_order","20150365");				//订单号，由商户生成的数字或者字母或者数字字母组合的订单号
		json.put("dt_order", "201505061049");		  	//订单时间,YYYYMMDDH24MISS
		json.put("api_version", "1.0");				//接口版本,默认1.0
		json.put("name_user","王二");			//认证人姓名
		json.put("id_no", "34162118990236451");		  		//认证人身份证件号
		json.put("sign", addSign(genSignData(json),"MD5","","8TE552Z95T659Y7K"));  //加密串
		
		System.out.println(json.toString());
		HttpRequestSimple http = new HttpRequestSimple();
		String outbuffer = http.postSendHttp("https://yintong.com.cn/tradeauthapi/auth.htm", json.toString());
			
		Map<String, Object> map  = HttpClient.parseJSON2Map(outbuffer);
		System.out.println(map.get("ret_msg"));
		System.out.println(outbuffer);
		
		
	}
	
	
	
	
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
