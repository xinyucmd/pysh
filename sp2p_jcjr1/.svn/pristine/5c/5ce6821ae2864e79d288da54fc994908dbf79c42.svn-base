package com.lianpay.trust.enums;

/**
 * @name   RetMsg
 * @author Eric
 * @email  mataojs@lianlian.com
 * @date   2014年12月23日 上午9:41:38
 * @update #update time#
 * @modify #modify user#

 */
public enum RetMsg {

	SUCCESS("0000","交易成功"),
	
	
	/*返给前端错误*/
	ERR_1001("1001","[1001] 交易失败:商户请求签名错误"),
	ERR_1002("1002","[1002] 交易失败:网络繁忙"),  //unused
	ERR_1003("1003","[1003] 交易失败:商户请求IP地址错误"),
	ERR_1004("1004","[1004] 交易失败:认证渠道故障"),
	ERR_1005("1005","[1005] 交易失败:非法商户"),
	ERR_1006("1006","[1006] 交易失败:无此业务权限"),
	ERR_1007("1007","[1007] 无记录"),
	ERR_1008("1008","[1008] 业务类型有误"),
	ERR_1009("1009","[1009] 服务已被暂停"),
	ERR_1010("1010","[1010] 请求接口不存在[%s]版本"),
	ERR_9901("9901","[9901] 请求报文非法"),  //unused
	ERR_9902("9902","[9902] 请求参数缺失%s"),
	ERR_9903("9903","[9903] 请求参数错误%s"), //unused
	ERR_9904("9904","[9904] 接口调用异常"),   //unused
	ERR_9999("9999","[9999] 系统错误");	   //unused
	
	
	public final String retCode;
	public final String retMsg;
	
	RetMsg(String retCode,String retMsg){
		this.retCode = retCode;
		this.retMsg  = retMsg;
	}
	
	public String getRetCode(){
		return retCode;
	}
	
	public String getRetMsg(){
		return retMsg;
	}
	
	
	 /**
     * 根据返回代码，确认返回内容［返回码必须唯一］
     */
	public static String getCodeMsg(String retCode){
		
		for(RetMsg enums : RetMsg.values()){
			if(enums.retCode.equals(retCode))
				return enums.retMsg;
		}
		
		return null;
	}
	
	
	/**
	 * MAIN测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}
	
	
}
