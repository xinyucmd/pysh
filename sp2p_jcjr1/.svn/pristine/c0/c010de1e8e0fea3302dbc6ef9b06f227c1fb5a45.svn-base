/**
 * @auth:ChenglongZhao-David_Rye
 * @description:一个提供通用方法的业务核心JS类，例如：Ajax请求|前端运算|通用的表单验证|非空验证等
 * @see 
 */

var param={};

function BaseRye(_param){
	this.param = _param;
}

/**
 * 字符串是否为空
 * @param content
 * @returns {Boolean}
 */
BaseRye.prototype.isNotBank = function(content){
	if(content==null || content==undefined || content==''||content=='null' ||content=='NULL'){
		return false;
	}
	return true;
}

BaseRye.prototype.analysisUrl = function(url){
	var pos,str,para,parastr; 
	var array =[];
	str = location.href;
	parastr = str.split("?")[1]; 
	if(BaseRye.prototype.isNotBank(parastr)){
		var arr = parastr.split("&");
		for (var i=0;i<arr.length;i++){
			array[arr[i].split("=")[0]]=arr[i].split("=")[1];
		}
	}
	
	return array;
}

BaseRye.prototype.getUrlParam = function (key){
	var url = document.location.href;
	return BaseRye.prototype.analysisUrl(url)[key];
}

/**
 * AJAX-POST 请求
 */
BaseRye.prototype.httpPost=function(url,param,completeHandler,failHandler){
	url += "?nowDate="+new Date().getTime();
	$.ajax({
	    type: "post",
	    url: url,
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	completeHandler(data);
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
//	    	 0 － （未初始化）还没有调用send()方法
//	    	 1 － （载入）已调用send()方法，正在发送请求
//	    	 2 － （载入完成）send()方法执行完成，已经接收到全部响应内容
//	    	 3 － （交互）正在解析响应内容
//	    	 4 － （完成）响应内容解析完成，可以在客户端调用了
	    	var error_msg = "";
	    	if(textStatus == null){
	    		// alert('链接错误');
	    		error_msg = '链接错误！XMLHttpRequest:['+XMLHttpRequest.readyState+'] errorThrown:['+errorThrown+']';
	    	}else if(textStatus == 'timeout'){
	    		// alert('链接超时');
	    		error_msg = '链接超时！XMLHttpRequest:['+XMLHttpRequest.readyState+'] errorThrown:['+errorThrown+']';
	    	}else if(textStatus=='notmodified'){
	    		
	    	}else if(textStatus == 'parsererror'){
	    		error_msg = '类型转换错误！XMLHttpRequest:['+XMLHttpRequest.readyState+'] errorThrown:['+errorThrown+']';
	    	}else{
	    		error_msg= '未知错误！XMLHttpRequest:['+XMLHttpRequest.readyState+'] errorThrown:['+errorThrown+']';
	    	}
	    	
	    	failHandler(error_msg);
	    }
	});
}

/*
 * 检查手机号是否合法
 * @param mobileNo
 * @returns {Boolean}
 */
BaseRye.prototype.validMobile = function (mobileNo){
	if((!/^1[3,4,5,7,8]\d{9}$/.test(mobileNo))){
		return false;
	}else{
		return true;
	}
}

//保存 Cookie
BaseRye.prototype.setCookie = function( name, value ){
	expires = new Date();
	// 设置有效期
	expires.setTime(expires.getTime() + (24*60*60*30*1000));//30为天数
	document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + "; path=/";
}

//获取 Cookie
BaseRye.prototype.getCookie = function (name){
	cookie_name = name + "=";
	cookie_length = document.cookie.length;
	cookie_begin = 0;
	while (cookie_begin < cookie_length){
		value_begin = cookie_begin + cookie_name.length;
		if (document.cookie.substring(cookie_begin, value_begin) == cookie_name){
			var value_end = document.cookie.indexOf ( ";", value_begin);
			if (value_end == -1){
				value_end = cookie_length;
			}
			return unescape(document.cookie.substring(value_begin, value_end));
		}
		cookie_begin = document.cookie.indexOf ( " ", cookie_begin) + 1;
		if (cookie_begin == 0){
			break;
		}
	}
	return null;
}

// 清除 Cookie
BaseRye.prototype.delCookie=function (name){
	var expireNow = new Date();
	document.cookie = name + "=" + "; expires=Thu, 01-Jan-70 00:00:01 GMT" + "; path=/";
} 

BaseRye.prototype.replaceAll = function (src,rstr,bstr){
	if(BaseRye.prototype.isNotBank(src)){
		var result  = src.replace(eval("/"+rstr+"/gi"),bstr);        
	    return result;  
	}
} 

BaseRye.prototype.logDebug = true;
BaseRye.prototype.log = function (msg){
	if (window["console"]){
		if(BaseRye.prototype.logDebug){
			console.log(msg);
		}
	}
}

BaseRye.prototype.getDiffTime = function (type,date1,date2){
	//var date1 = new Date(2012, 4, 7, 11, 27);  //开始时间
	//var date2 = new Date(2012, 4, 7, 12, 28);     //结束时间
	var date3 = date2.getTime() - date1.getTime();   //时间差的毫秒数
	//计算相差的年数
//	var years = Math.floor(date3 / (12 * 30 * 24 * 3600 * 1000));
//	//计算相差的月数
//	var leave = date3 % (12 * 30 * 24 * 3600 * 1000);
//	var months = Math.floor(leave / (30 * 24 * 3600 * 1000));
//	//计算出相差天数
//	var leave0 = leave % (30 * 24 * 3600 * 1000);
//	var days = Math.floor(leave0 / (24 * 3600 * 1000));
//	//计算出小时数
//	var leave1 = leave0 % (24 * 3600 * 1000);     //计算天数后剩余的毫秒数
//	var hours = Math.floor(leave1 / (3600 * 1000));
//	//计算相差分钟数
//	var leave2 = leave1 % (3600 * 1000);         //计算小时数后剩余的毫秒数
//	var minutes = Math.floor(leave2 / (60 * 1000));
//	//计算相差秒数
//	var leave3 = leave2 % (60 * 1000);       //计算分钟数后剩余的毫秒数
//	var seconds = Math.round(leave3 / 1000);


	// document.write(" 相差 " + years + "年" + months + "月" + days + "天 " + hours + "小时 " + minutes + " 分钟" + seconds + " 秒")
	
//	if(type == "year"){
//		return years;
//	}else if(type == "month"){
//		return months
//	}else if(type == "day"){
//		return days;
//	}else if(type == "hour"){
//		return hours;
//	}else if(type == "minutes"){
//		return minutes;
//	}else 
		
	if(type == "seconds"){
		return Math.floor(date3/1000);
	}else{
		return null;
	}
}










