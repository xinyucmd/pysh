package com.lianpay.trust.enums;

/**
 * @name   CallBack
 * @author Eric
 * @email  mataojs@lianlian.com
 * @date   2015年2月11日 下午2:07:21
 * @update #update time#
 * @modify #modify user#

 */
public enum CallBack {
	
	//后端返回错误
	CALLBACK_01("000000","[连连征信] 认证成功"),
	CALLBACK_02("100000","[连连征信] 认证失败"),
	CALLBACK_03("990001","[连连征信] 参数%s为空"),
	CALLBACK_04("990002","[连连征信] 当前请求渠道异常"),
	CALLBACK_05("990003","[连连征信] 后端渠道故障"),
	CALLBACK_06("100001","[连连征信] 业务已被暂停"),
	CALLBACK_07("100002","[连连征信] 无此业务权限");
	
	public String retCode;
	public String retMsg;
	
	public String getRetCode(){
		return retCode;
	}
	
	public String getRetMsg(){
		return retMsg;
	}
	
	CallBack(String retCode,String retMsg){
		this.retCode = retCode;
		this.retMsg  = retMsg;
	}
	
	
}
