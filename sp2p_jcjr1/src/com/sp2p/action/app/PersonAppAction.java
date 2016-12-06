package com.sp2p.action.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lianpay.trust.enums.SignType;
import com.lianpay.trust.util.FuncUtils;
import com.lianpay.trust.util.HttpRequestSimple;
import com.lianpay.trust.util.security.Md5Algorithm;
import com.lianpay.trust.util.security.RSAUtil;
import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.security.Encrypt;
import com.shove.util.HttpClient;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BeVipService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.UserService;
import com.sp2p.util.AuthInfomationUtils;
import com.sp2p.util.isKeywords;

public class PersonAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(PersonAppAction.class);
	private static final long serialVersionUID = 7226324035784433720L;

	private BeVipService beVipService;
	private UserService userService;

	public String addOrUpdatePerson() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> pMap = null;
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		Map<String, String> userMap = null;
		try {
			userMap = userService.queryUserById(userId);
			Map<String, String> appInfoMap = getAppInfoMap();
			String realName = String.valueOf(appInfoMap.get("realName"));// 真实姓名
			//区分安卓
			String flag = appInfoMap.get("flag");
			//ios不解密
			if("android".equals(flag)){
				realName = com.shove.security.Encrypt.decrypt3DES(realName,
						IConstants.PASS_KEY);
			}else {
				realName = realName.replace("U", "\\u");
				realName = isKeywords.decodeUnicode(realName);
			}
			if (StringUtils.isBlank(realName)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请正确填写真实名字");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (2 > realName.length() || 20 < realName.length()) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "真实姓名的长度为不小于2和大于20");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String idNo = appInfoMap.get("idNo");// 身份证号码
			if (StringUtils.isBlank(idNo)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请输入正确的身份证号码");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (15 != idNo.length() && 18 != idNo.length()) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "请输入正确的身份证号码");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 验证身份证
			int sortCode = 0;
			int MAN_SEX = 0;
			if (idNo.length() == 15) {
				sortCode = Integer.parseInt(idNo.substring(12, 15));
			} else {
				sortCode = Integer.parseInt(idNo.substring(14, 17));
			}
			if (sortCode % 2 == 0) {
				MAN_SEX = 1;// 女性身份证
			} else if (sortCode % 2 != 0) {
				MAN_SEX = 2;// 男性身份证
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "身份证不合法");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String iDresutl = "";
			iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
			if (iDresutl != "") {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "身份证不合法");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (IConstants.ISDEMO.equals("1")) {
				
			} else {
				//判断身份证的唯一性
				if (!StringUtils.isBlank(idNo)) {
					Map<String, String> idNoMap = beVipService.queryIDCard(idNo);
					if (idNoMap != null && !idNoMap.isEmpty()) {
						
						if(Convert.strToLong(idNoMap.get("userId"), -1) != userId){
							jsonMap.put("error", "6");
							jsonMap.put("msg", "身份证已注册");
							JSONUtils.printObject(jsonMap);
							return null;
						}
					}
				}

			}

			// 手机号码
			String cellPhone = userMap.get("mobilePhone");
//			String cellPhone = appInfoMap.get("cellPhone");// 手机号码
//			if (StringUtils.isBlank(cellPhone)) {
//				jsonMap.put("error", "7");
//				jsonMap.put("msg", "请正确填写手机号码");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			} else if (cellPhone.length() < 9 || cellPhone.length() > 12) {
//				jsonMap.put("error", "8");
//				jsonMap.put("msg", "手机号码长度不对");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}

			/**
			 * 判定用户是否已存在记录
			 */
			if (userId == -1) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> personMap = userService.queryPersonById(userId);
			if (personMap != null){
				if(personMap.get("auditStatus").equals("3")){
					jsonMap.put("error", "30");
					jsonMap.put("msg", "您的信息已通过审核");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			// 验证手机的唯一性
//			Map<String, String> phonemap = beVipService.queryIsPhone(cellPhone);
//			pMap = beVipService.queryPUser(userId);
//			if (pMap == null) {
//				if (phonemap != null) {
//					jsonMap.put("error", "10");
//					jsonMap.put("msg", "手机号已存在");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//			} else if (phonemap != null
//					&& !cellPhone.trim().equals(pMap.get("cellphone"))) {
//				jsonMap.put("error", "10");
//				jsonMap.put("msg", "手机号已存在");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			if (IConstants.ISDEMO.equals("2")) {
//				String phonecode = appInfoMap.get("recivePhone");
//				if (StringUtils.isBlank(phonecode)) {
//					jsonMap.put("error", "12");
//					jsonMap.put("msg", "你还没有获取手机验证码");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//				phonecode = Encrypt.decryptSES(phonecode, AlipayConfig.ses_key);
//				if (StringUtils.isNotBlank(phonecode)) {
//					if (!phonecode.trim().equals(cellPhone.trim())) {
//						jsonMap.put("error", "12");
//						jsonMap.put("msg", "与获取验证码手机号不一致");
//						JSONUtils.printObject(jsonMap);
//						return null;
//					}
//
//				}
//				// 验证码
//				String code = appInfoMap.get("code");
//				if (StringUtils.isBlank(code)) {
//					jsonMap.put("error", "13");
//					jsonMap.put("msg", "请填写验证码");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//
//				String randomCode = appInfoMap.get("randomCode");
//				if (StringUtils.isBlank(randomCode)) {
//					jsonMap.put("error", "14");
//					jsonMap.put("msg", "请输入正确的验证码");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//				randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
//				if (!code.trim().equals(randomCode)) {
//					jsonMap.put("error", "14");
//					jsonMap.put("msg", "请输入正确的验证码");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//			}
//			
//			
//
//			String sex = appInfoMap.get("sex");// 性别(男 女)
//			if (StringUtils.isNotBlank(sex)) {
//				if (sex.equals("男") && MAN_SEX == 1) {
//					jsonMap.put("error", "12");
//					jsonMap.put("msg", "请正确填写性别");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				} else if (sex.equals("女") && MAN_SEX == 2) {
//					jsonMap.put("error", "12");
//					jsonMap.put("msg", "请正确填写性别");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//			}
//
//			String birthday = appInfoMap.get("birthday");// 出生日期
//
//			 if (StringUtils.isBlank(birthday)) {
//			 jsonMap.put("error", "13");
//			 jsonMap.put("msg", "请正确填写出生日期");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String highestEdu = appInfoMap.get("highestEdu");// 最高学历
//			 if (StringUtils.isBlank(highestEdu)) {
//			 jsonMap.put("error", "14");
//			 jsonMap.put("msg", "请正确填写最高学历");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String eduStartDay = appInfoMap.get("eduStartDay");// 入学年份
//			 if (StringUtils.isBlank(eduStartDay)) {
//			 jsonMap.put("error", "15");
//			 jsonMap.put("msg", "请正确填写入学年份");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String school = appInfoMap.get("school");// 毕业院校
//			 if (StringUtils.isBlank(school)) {
//			 jsonMap.put("error", "16");
//			 jsonMap.put("msg", "请正确填写入毕业院校");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String maritalStatus = appInfoMap.get("maritalStatus");// 婚姻状况(已婚
//			// 未婚)
//			 if (StringUtils.isBlank(maritalStatus)) {
//			 jsonMap.put("error", "17");
//			 jsonMap.put("msg", "请正确填写入婚姻状况");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String hasChild = appInfoMap.get("hasChild");// 有无子女(有 无)
//
//			 if (StringUtils.isBlank(hasChild)) {
//			 jsonMap.put("error", "18");
//			 jsonMap.put("msg", "请正确填写入有无子女");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//			String hasHourse = appInfoMap.get("hasHourse");// 是否有房(有 无)
//			 if (StringUtils.isBlank(hasHourse)) {
//			 jsonMap.put("error", "19");
//			 jsonMap.put("msg", "请正确填写入是否有房");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String hasHousrseLoan = appInfoMap.get("hasHousrseLoan");// 有无房贷(有
//			// 无)
//			 if (StringUtils.isBlank(hasHousrseLoan)) {
//			 jsonMap.put("error", "19");
//			 jsonMap.put("msg", "请正确填写入有无房贷");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String hasCar = appInfoMap.get("hasCar");// 是否有车 (有 无)
//			 if (StringUtils.isBlank(hasCar)) {
//			 jsonMap.put("error", "20");
//			 jsonMap.put("msg", "请正确填写入是否有车");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String hasCarLoan = appInfoMap.get("hasCarLoan");// 有无车贷 (有 无)
//			 if (StringUtils.isBlank(hasCarLoan)) {
//			 jsonMap.put("error", "21");
//			 jsonMap.put("msg", "请正确填写入有无车贷");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//			Long nativePlacePro = Convert.strToLong(appInfoMap
//					.get("nativePlacePro"), -1);// 籍贯省份(默认为-1)
//			 if (StringUtils.isBlank(nativePlacePro.toString())) {
//			 jsonMap.put("error", "22");
//			 jsonMap.put("msg", "请正确填写入籍贯省份");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//			Long nativePlaceCity = Convert.strToLong(appInfoMap
//					.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
//			 if (StringUtils.isBlank(nativePlaceCity.toString())) {
//			 jsonMap.put("error", "23");
//			 jsonMap.put("msg", "请正确填写入籍贯城市");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			Long registedPlacePro = Convert.strToLong(appInfoMap
//					.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
//			 if (StringUtils.isBlank(registedPlacePro.toString())) {
//			 jsonMap.put("error", "24");
//			 jsonMap.put("msg", "请正确填写入户口所在地省份");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			Long registedPlaceCity = Convert.strToLong(appInfoMap
//					.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)
//
//			 if (StringUtils.isBlank(registedPlaceCity.toString())) {
//			 jsonMap.put("error", "25");
//			 jsonMap.put("msg", "请正确填写入户口所在地城市");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String address = appInfoMap.get("address");// 所在地
//			 if (StringUtils.isBlank(address)) {
//			 jsonMap.put("error", "26");
//			 jsonMap.put("msg", "请正确填写居住地址");
//			 JSONUtils.printObject(jsonMap);
//			 return null;
//			 }
//
//			String telephone = appInfoMap.get("telephone");// 居住电话
//			if (StringUtils.isNotBlank(telephone)) {
//				
//				if (!telephone.contains("-")) {
//					jsonMap.put("error", "15");
//					jsonMap.put("msg", "你的居住电话格式不正确");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//				if (telephone.trim().length() < 7
//						|| telephone.trim().length() > 13) {
//					jsonMap.put("error", "16");
//					jsonMap.put("msg", "你的居住电话输入长度不对");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//			}
//
//			/* 用户头像 */
//			String personalHead = appInfoMap.get("personalHead");// 个人头像
//			// (默认系统头像)
//			// if (StringUtils.isBlank(personalHead)) {
//			// jsonMap.put("error", "30");
//			// jsonMap.put("msg", "请正确上传你的个人头像");
//			// JSONUtils.printObject(jsonMap);
//			// return null;
//			// }
			//如果认证超过一天超过3次，不允许在认证。
			List<Map<String,Object>> list = userService.searchLianlianOrderRz(String.valueOf(userId));
			if(list!=null && list.size()>=3){
				jsonMap.put("msg", "认证已超过三次");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if(IConstants.ISDEMO.equals("2")){
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
			jsons.put("sign", addSign(genSignData(jsons),"MD5","","201504231000296502jcbanking"));  //加密串
//			jsons.put("sign", addSign(genSignData(json),"MD5","",IConstants.PASS_KEY));  //加密串
//			HttpRequestSimple http = new HttpRequestSimple();
//			String outbuffer = http.postSendHttp("https://yintong.com.cn/tradeauthapi/auth.htm", jsons.toString());
//			System.out.println(outbuffer);
//			Map<String, Object> map  = HttpClient.parseJSON2Map(outbuffer);
			/**更换 有盾进行实名验证 */
			Map<String,String> map=AuthInfomationUtils.cloudxCheckId5(realName,idNo,String.valueOf(date.getTime()));
			String ret_code = (String) map.get("ret_code");
			String ret_msg = (String) map.get("ret_msg");
			String result_order = (String) map.get("result_order");
//			if(!"0000".equals(code)){
//				jsonMap.put("error", "1001");
//				jsonMap.put("msg", msg);
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			
//			if("0000".equals(code) && !"1".equals(result_order)){
//				jsonMap.put("error", "1002");
//				jsonMap.put("msg", "请填写您的真实姓名和身份证号码！");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			
//			if("0000".equals(code) && "1".equals(result_order)){
//				userService.addLianlianOrderRz(date.getTime(), date,userId);
//			}
			if(!"0000".equals(ret_code)){
				jsonMap.put("error", "0");
				jsonMap.put("msg", ret_msg);
				
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if("0000".equals(ret_code) && !"1".equals(result_order)){
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "请填写您的真实姓名和身份证号码！");
				
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			if("0000".equals(ret_code) && "1".equals(result_order)){
				userService.addLianlianOrderRz(date.getTime(), date,userId,flag+"-APP-"+idNo);
			}
		}
			long personId = -1L;
            int lian_state = 1;
			personId = userService.updateUserBaseData(realName, cellPhone, "",
					"", "", "", "", "",
					"", "", "", "", "",
					personId, personId, personId,
					personId, "", "", "",
					userId, idNo,lian_state);
//			personId = userService.updateUserBaseData(realName, cellPhone, sex,
//					birthday, highestEdu, eduStartDay, school, maritalStatus,
//					hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
//					nativePlacePro, nativePlaceCity, registedPlacePro,
//					registedPlaceCity, address, telephone, personalHead,
//					userId, idNo);
			if (personId > 0) {
				session().removeAttribute("randomCode");
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);

				// 成功
			} else {
				// 失败
				jsonMap.put("error", "17");
				jsonMap.put("msg", "保存失败");

				JSONUtils.printObject(jsonMap);

			}
			
		} catch (Exception e) {
			log.error(e);
			System.out.println("未知异常:==================================="+e);
			e.printStackTrace();
			jsonMap.put("error", "18");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}

		return null;
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
	
	
	

	public String queryPersonByUserId() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> map = userService.queryPersonById(userId);
			if (map == null || map.isEmpty()) {
				jsonMap.put("error", "-2");
				jsonMap.put("msg", "用户没有填写个人基本信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			jsonMap.put("error", "-1");
			jsonMap.put("msg", "获取成功");
			jsonMap.putAll(map);
			JSONUtils.printObject(jsonMap);

		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
