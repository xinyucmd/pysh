package com.lianpay.trust.enums;

/**
 * @name   BusiCode
 * @author Eric
 * @email  mataojs@lianlian.com
 * @date   2015年2月13日 下午1:44:52
 * @update #update time#
 * @modify #modify user#

 */
public enum BusiCode {
	
	/**
	 * 业务编码规则
	 * P1XXXX ~ P2XXXX 为个人服务类型
	 * E3XXXX ~ E4XXXX 为企业服务类型
	 * O5XXXX ~ O6XXXX 为其它业务类型
	 * Q7XXXX ~ Q8XXXX 为查询服务类型
	 */
	
	//基础服务
	BUSI1000("P10000","P10000-个人实名认证");
	
	//查询服务
	
	
	public String busiCode;
	public String busiName;
	
	public String getBusiCode(){
		return busiCode;
	}
	
	public String getBusiName(){
		return busiName;
	}
	
	BusiCode(String busiCode,String busiName){
		this.busiCode = busiCode;
		this.busiName = busiName;
	}
	
	
	
    /**
     * 根据返回代码，确认返回内容［返回码必须唯一］
     */
	public static Boolean checkBusi(String retCode){
		
		for(BusiCode enums : BusiCode.values()){
			if(enums.busiCode.equals(retCode))
				return true;
		}
		
		return false;
	}
	
	
	
}
