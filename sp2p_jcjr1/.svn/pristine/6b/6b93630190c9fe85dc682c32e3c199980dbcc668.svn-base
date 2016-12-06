

function MUser(_param){
	JcBanking.call(this,_param);
}

MUser.prototype = new JcBanking(); 

/**
 * 手机注册
 * @param mobileNo
 * @param password
 * @param mCode
 */
MUser.prototype.regester=function(mobileNo,password,mCode,randomCode,recivePhone,refferee,complete,recommend_src){
	_param={};
	if(!MUser.prototype.isNotBank(refferee)){
		refferee = "";
	}
	_param["info"] = '{"name":"'+mobileNo+
						'","pwd":"'+password+
						'","cellPhone":"'+mobileNo+
						'","randomCode":"'+randomCode+
						'","recivePhone":"'+recivePhone+
						'","code":"'+mCode+
						'","refferee":"'+refferee+
						'","recommend_src":"'+recommend_src+
						'"}';
	MUser.prototype.httpPost(
			'/app/register.do',
			_param,
			complete,
			function(msg){
				alert(msg)
			}
	);
}

MUser.prototype.regesterAny=function(mobileNo,password,mCode,randomCode,recivePhone,refferee,complete,recommend_src,point,pic_mCode){
	_param={};
	if(!MUser.prototype.isNotBank(refferee)){
		refferee = "";
	}

	
	if(!MUser.prototype.isNotBank(mobileNo)){
		alert("手机号不能为空！");
		return;
	}
	
	if(!MUser.prototype.isNotBank(password)){
		alert("密码不能为空！");
		return;
	}
	
	if( password.length<6){
		alert("密码长度不能小于6位！");
		return;
	}
	if(!MUser.prototype.isNotBank(mCode)){
		alert("手机验证码不能为空！");
		return;
	}
	if(!MUser.prototype.isNotBank(pic_mCode)){
		alert("图片验证码不能为空！");
		return;
	}
	
	$("#btn_register").attr("value","注册中");
	$("#btn_register").attr("disabled","disabled");
	$("#btn_register").attr("class","big_btn_disable");
	
	_param["info"] = '{"name":"'+mobileNo+
	'","pwd":"'+password+
	'","cellPhone":"'+mobileNo+
	'","randomCode":"'+randomCode+
	'","recivePhone":"'+recivePhone+
	'","code":"'+mCode+
	'","refferee":"'+refferee+
	'","recommend_src":"'+recommend_src+
	'","point":"'+point+
	'","pic_mCode":"'+pic_mCode+
	'","pageId":"'+'userappreg'+
	'"}';
	MUser.prototype.httpPost(
		'/app/registerForM.do',
		_param,
		complete,
		function(msg){
			$("#btn_register").attr("value","立即注册");
			$("#btn_register").removeAttr("disabled");
			$("#btn_register").attr("class","big_btn");
			alert(msg)
		}
	);
	
}

MUser.prototype.login=function(username,password,complete){
	_param={};
	_param["info"] = '{"name":"'+username+'","pwd":"'+password+'"}';
	MUser.prototype.httpPost(
			'/app/login.do',
			_param,
			function(data){
				MUser.prototype.setCookie("userId",data.id);
				MUser.prototype.setCookie("userName",data.username);
				MUser.prototype.setCookie("realName",data.realName);
				complete(data);
			},
			function(msg){
				alert(msg);
			}
	);
}

/**
 * 好友实名认证
 */
MUser.prototype.authentication=function(userId,realName,idNo,complete){
	_param={};
	_param["info"] = '{"userId":"'+userId+
					'","realName":"'+realName+
					'","idNo":"'+idNo+
					'"}';
	MUser.prototype.httpPost(
			'/app/authentication.do',
			_param,
			function(data){
				complete(data);
			},
			function(msg){
				alert(msg)
			}
	);
}


MUser.prototype.flushImgCode = function(obj_img_code){
	var timenow = new Date();
	obj_img_code.attr("src","/shoveeims/imageCode.do?pageId=userlogin&d="+timenow);
}

MUser.prototype.login_pc_validate = function (obj_input,username,processFuc){
	var _value = obj_input.attr("value");
   //email
   if(obj_input.attr("id")=="email"){   
        if(this.value==""){
            alert("请输入用户名或邮箱地址");
            if(processFuc != null){
            	processFuc("请输入用户名或邮箱地址");
            }
        }else if(/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(_value)){ //判断用户输入的是否是邮箱地址
        	MUser.prototype.checkRegister('email',username);
        }else if((/^1[3,5,8]\d{9}$/.test(_value))){
            checkRegister('cellphone',username);
        }else{ 
        	MUser.prototype.checkRegister('userName',username);
        }   
    }  
	//password
	if(obj_input.attr("id")=="password"){
		if(_value==""){
	      	alert("请輸入您的密码");
	      	if(processFuc != null){
            	processFuc("请輸入您的密码");
            }
		}else if(_value.length<6){ 
	      	alert("密码长度为6-20个字符"); 
	      	if(processFuc != null){
            	processFuc("密码长度为6-20个字符");
            }
		}else{
			
		}
	}
		   
	//验证码
	if(obj_input.attr("id")=="code"){
	    if(this.value==""){
	    	alert("请输入验证码"); 
	    }else{   
    		
		} 
	}

}

/**
 * 检查用户名是否合法
 * @param flag
 * @param username
 */
MUser.prototype.checkRegister = function(flag,username){
	var param = {};
  	if(flag == "userName"){
		param["paramMap.email"]  = "";
		param["paramMap.userName"] = username;
	}else if(flag=="email"){
		param["paramMap.email"] = username;
		param["paramMap.userName"] = "";
	}else{
		param["paramMap.email"] = "";
		param["paramMap.userName"] = "";
		param["paramMap.cellphone"] = username;
	}
	$.post("ajaxCheckLog.do",param,function(data){
	    $("#email_").hide();
         if(data == 2 && flag == "userName"){
             alert("无效用户！");
         }else if(data == 3 && flag == "userName"){
        	 alert("该用户还没激活！");
         }else if(data == 4&& flag == "userName"){
          	
         }
         if(data == 0 && flag == "email"){
        	 alert("无效邮箱！");
         }else if(data == 1 && flag == "email"){
        	 alert("该邮箱用户还没激活！");
         }else if(data == 4&& flag == "email"){
        	  
         } 
         if(data == 5 && flag == "cellphone"){
        	 alert("用户不存在！");
         }else if(data == 4 && flag == "cellphone"){
        	  
         }
	});
}

/**
 * 判断是否登录
 */
MUser.prototype.login_pc_check=function(processFunc){
	var _param = {}
	MUser.prototype.httpPost(
			'/queryLogin.do',
			_param,
			function(data){
				processFunc(data);
			},
			function(msg){
				alert(msg)
			}
	);
}

/**
 * PC端Ajax登录
 * @param username
 * @param password
 * @param code
 * @param obj_btn_login
 * @param obj_img_code
 */
MUser.prototype.login_pc = function (username,password,code,obj_btn_login,obj_img_code,processFunc,processError){
    var param = {};
    
    obj_btn_login.attr('disabled',true);
    if(username==""){
        alert("请输用户名或邮箱地址");
    }
    if(password==""){
    	alert("请输入密码");
    }  
    obj_btn_login.attr('value','登录中...');
    
	param["paramMap.pageId"] = "userlogin";
	param["paramMap.email"] = username;
	param["paramMap.password"] = password;
	param["paramMap.code"] = code
	$.post("/logining.do",param,function(data){
		if(data == 1){
			processFunc();
			obj_btn_login.attr("disabled",false);
		}else if (data == 2) {
			obj_btn_login.attr('value','');
			alert("验证码错误！"); 
			MUser.prototype.flushImgCode(obj_img_code);
			obj_btn_login.attr("disabled",false); 
		}else if (data == 3) {
			obj_btn_login.attr('value','');
			alert("用户名或密码错误！"); 
			MUser.prototype.flushImgCode(obj_img_code);
            obj_btn_login.attr('disabled',false); 
            processError(data);
		}else if (data == 4) {
			obj_btn_login.attr('value','');
			MUser.prototype.flushImgCode(obj_img_code);
			alert("该用户已被禁用！"); 
	        obj_btn_login.attr('disabled',false); 
	        processError(data);
		}else if (data == 5) {
			obj_btn_login.attr('value','');
			MUser.prototype.flushImgCode(obj_img_code);
			alert("该用户已被限制登录，请于三小时以后登录！"); 
            obj_btn_login.attr('disabled',false); 
            processError(data);
		}else if (data == 7) {
			obj_btn_login.attr('value','');
			MUser.prototype.flushImgCode(obj_img_code);
			alert("该用户账号出现异常，请速与管理员联系！"); 
            obj_btn_login.attr('disabled',false); 
            processError(data);
		}else{
			obj_btn_login.attr('value','');
			MUser.prototype.flushImgCode(obj_img_code);
			alert("输入错误，请重新输入，只有3次机会！"); 
            obj_btn_login.attr('disabled',false); 
            processError(data);
		}
	});
}













