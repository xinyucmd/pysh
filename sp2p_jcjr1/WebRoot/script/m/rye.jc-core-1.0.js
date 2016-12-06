

function JcBanking(_param){
	BaseRye.call(this,_param);
}

JcBanking.prototype = new BaseRye();

JcBanking.prototype.timersConstant= 120;
JcBanking.prototype.timers = 120;
JcBanking.prototype.tipId;// 定时器对象，获取定时器
JcBanking.prototype.flags = false;//定时器状态
JcBanking.prototype.iptMobileNo;// 手机号码
JcBanking.prototype.btnCodeId; // 获取验证码的按钮
JcBanking.prototype.iptRandomCodeId; // 后台返回的验证码
JcBanking.prototype.iptRecivePhoneId; // 后台返回的验证码手机号

/**
 * 发送手机验证码
 * @param mobileNo
 */
JcBanking.prototype.requestMcode=function(mobileNo,complete){
	_param={};
	_param["info"] = '{"cellPhone":"'+mobileNo+'","isExist":"2","pageId":"userappreg"}';
	var result = 1;
	if(JcBanking.prototype.validMobile(mobileNo)){
		JcBanking.prototype.httpPost('/app/sendSMSMP.do',_param,
			complete,
			function(msg){
				alert(msg)
			}
		);
	}else{
		result = -2;
		alert('手机号不合法！');
	}
	
	return result;
}

/**
 * 发送手机验证码
 * @param mobileNo
 */
JcBanking.prototype.requestMcodeRe=function(mobileNo,complete){
	var pic_mCode = $("#pic_mCode").val();
	_param={};
	_param["info"] = '{"cellPhone":"'+mobileNo+'","isExist":"2","pageId":"userappreg","code":"'+pic_mCode+'"}';
	var result = 1;
	if(JcBanking.prototype.validMobile(mobileNo)){
		JcBanking.prototype.httpPost('/app/sendSMSMP.do',_param,
			complete,
			function(msg){
				alert(msg)
			}
		);
	}else{
		result = -2;
		alert('手机号不合法！');
	}
	
	return result;
}


JcBanking.prototype.requestMcodeWithEffec=function(_iptMobileNo,_btnCodeId,_iptRandomCodeId,_iptRecivePhoneId,pic_mCode){
	JcBanking.prototype.iptMobileNo = _iptMobileNo;
	var mobileNo = $("#"+_iptMobileNo).val();
	_param={};
	_param["info"] = '{"cellPhone":"'+mobileNo+'","isExist":"2","pageId":"userappreg","code":"'+pic_mCode+'"}';
	var result = 1;
	if(JcBanking.prototype.validMobile(mobileNo)){
		JcBanking.prototype.log(_iptRandomCodeId+','+_iptRandomCodeId+','+_iptRecivePhoneId);
		JcBanking.prototype.btnCodeId=_btnCodeId;
		JcBanking.prototype.iptRandomCodeId= _iptRandomCodeId;
		JcBanking.prototype.iptRecivePhoneId = _iptRecivePhoneId;
		
		btnValue = $("#"+JcBanking.prototype.btnCodeId).val();
		if(btnValue=="重新发送验证码") { 
			reSendMsg();
			return;
 		}else if(btnValue=="点击获取验证码"){
 			$("#"+JcBanking.prototype.btnCodeId).attr("disabled","disabled");
 			$("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn_disable");
 			JcBanking.prototype.httpPost('/app/sendSMSMP.do',_param,
 				function (data){
 					if(data.error ==-1){
 						JcBanking.prototype.timers = JcBanking.prototype.timersConstant;
 		 	 	        tipId=window.setInterval("_timer()",1000);
 	 					$("#"+JcBanking.prototype.iptRandomCodeId).attr("value",data.randomCode);
 	 					$("#"+JcBanking.prototype.iptRecivePhoneId).attr("value",data.recivePhone);	
 					}else{
 						$("#"+JcBanking.prototype.btnCodeId).removeAttr("disabled");
 	 					$("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn");
 	 					alert(data.msg);
 					}
 				},
 				function(msg){
 					$("#"+JcBanking.prototype.btnCodeId).removeAttr("disabled");
 					$("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn");
 					alert(msg)
 				}
 			);
 		}else{
 			alert("请刷新页面重新填写！");
 		}
	}else{
		result = -2;
		alert('手机号不合法！');
	}
	
	return result;
}

function reSendMsg(){
	BaseRye.prototype.log("flags:"+JcBanking.prototype.flags);
    if(JcBanking.prototype.flags) return ;
    var phone = $("#"+JcBanking.prototype.iptMobileNo).val();
    BaseRye.prototype.log("重新发送手机号为:"+phone);
    
    $("#"+JcBanking.prototype.btnCodeId).attr("disabled","disabled");
    $("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn_disable");
    JcBanking.prototype.requestMcodeRe(phone,function(data){
 	   if(data.error==-1){
 		   $("#"+JcBanking.prototype.iptRandomCodeId).attr("value",data.randomCode);
 	       $("#"+JcBanking.prototype.iptRecivePhoneId).attr("value",data.recivePhone);
 		   JcBanking.prototype.timers=JcBanking.prototype.timersConstant;
 		   JcBanking.prototype.flags = true;
 		   window.clearInterval(tipId);
            tipId=window.setInterval("_timer()",1000);
 	   }else{
 		   $("#"+JcBanking.prototype.btnCodeId).removeAttr("disabled");
			   $("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn");
 		   alert("手机验证码发送失败！");
 	   }
    });
}

function _regEvt(){
	var btnCode = $("#"+JcBanking.prototype.btnCodeId);
	btnCode.click(function(){
		if(btnCode.val()=="重新发送验证码") {
		   BaseRye.prototype.log("flags:"+JcBanking.prototype.flags);
	       if(JcBanking.prototype.flags) return ;
	       var phone = $("#"+JcBanking.prototype.iptMobileNo).val();
	       BaseRye.prototype.log("重新发送手机号为:"+phone);
	       
	       $("#"+JcBanking.prototype.btnCodeId).attr("disabled","disabled");
	       $("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn_disable");
	       JcBanking.prototype.requestMcodeRe(phone,function(data){
	    	   if(data.error==-1){
	    		   $("#"+JcBanking.prototype.iptRandomCodeId).attr("value",data.randomCode);
	    	       $("#"+JcBanking.prototype.iptRecivePhoneId).attr("value",data.recivePhone);
	    		   JcBanking.prototype.timers=JcBanking.prototype.timersConstant;
	    		   JcBanking.prototype.flags = true;
	    		   window.clearInterval(tipId);
	               tipId=window.setInterval("_timer()",1000);
	    	   }else{
	    		   $("#"+JcBanking.prototype.btnCodeId).removeAttr("disabled");
				   $("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn");
	    		   alert("手机验证码发送失败！");
	    	   }
	       });
		}else{
			alert("请不要重复获取！");
		}
	});
}

//定时
function _timer(){
	var btnCode = $("#"+JcBanking.prototype.btnCodeId);
	var iptRandomCode = $("#"+JcBanking.prototype.iptRandomCodeId);
	var iptRecivePhone = $("#"+JcBanking.prototype.iptRecivePhoneId);
    if(JcBanking.prototype.timers>=0){
    	btnCode.attr("value",""+JcBanking.prototype.timers+"秒");
    	JcBanking.prototype.timers--;
    }else{
		$("#"+JcBanking.prototype.btnCodeId).removeAttr("disabled");
		$("#"+JcBanking.prototype.btnCodeId).attr("class","center_btn");
       window.clearInterval(tipId);
       JcBanking.prototype.flags = false;
       btnCode.attr("value","重新发送验证码");
      // iptRandomCode.attr("value","");
      // iptRecivePhone.attr("value","");
    }
}

JcBanking.prototype.isLogin=function(afterLoginUrl,completeFunc){
	JcBanking.prototype.getUserId(function(userId){
		if(!JcBanking.prototype.isNotBank(userId)){
			alert('未登录！');
			JcBanking.prototype.setCookie("afterLoginUrl",afterLoginUrl);
			window.location.href = "login.html";
		}
		
		completeFunc(userId);
	});
}

JcBanking.prototype.getUserId = function(completeFunc){
	_param={};
	var userId = JcBanking.prototype.getCookie("userId");
	if(!JcBanking.prototype.isNotBank(userId)){
		JcBanking.prototype.httpPost('/app/getSessionUserId.do',_param,
			function (data){
				if(data.userId != '-9'){
					userId = data.userId;
					JcBanking.prototype.setCookie("userId",userId);					
				}
				userId = JcBanking.prototype.getCookie("userId");
				completeFunc(userId);
			},
			function(msg){
				alert(msg)
			}
		);
	}else{
		completeFunc(userId);
	}	
}

JcBanking.prototype.goHome=function(){
	window.location.href='http://www.jcbanking.com';
}

JcBanking.prototype.goReg=function(){
	window.location.href='http://www.jcbanking.com/page/m/reg.html';
}

JcBanking.prototype.genQCode = function(renderId,content){
	user.log("er-wei-ma==>:"+content);
	// 处理URL内容
	content = JcBanking.prototype.replaceAll(content, "&", "@@");
	user.log("er-wei-ma-trans==>:"+content);
	$("#"+renderId).empty();
	$("#"+renderId).append("<img src='/showQcode.do?content="+content+"'/>");
}
















