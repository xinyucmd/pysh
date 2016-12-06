<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${sitemap.siteName}</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script>
$(function(){
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
</script>
<script>
	function send_password(){
		var r = $("#code").val();
		if(r == ""){
			$("#s_email").html("验证码不能为空.");
		}else{
			$("#s_email").html("");
			var isDemo = '${maps.isDemo}';
			if(isDemo != 1){
				var code = '${phonecodes}';
				if(!($("#code").val() == code)){
					var id ='${maps.id}';
					var code = $("#code").val();
					var param = {};
					param["paramMap.code"] = code;
					$.post("checkPhoneCode2.do",param,function(data){
						if(data.msg){
							window.location.href="checkPhoneCode.do?id="+id+"&code="+code;
						}else{
							$("#s_email").html("验证码错误.");
						}
					});
				}
			}else{
				var id ='${maps.id}';
				var code = $("#code").val();
				window.location.href="checkPhoneCode.do?id="+id+"&code="+code;
			}
		}
	}
</script>
<script>
	var timers=60;
	var tipId;
	var flags= false;
	function clickCode5(){
		var param = {};
		param["paramMap.phone"] = $("#phone").val();
		if($("#clickCode5s").html() == "重新发送验证码" || $("#clickCode5s").html() == "点击获取验证码"){
			$.post("sendPhoneCodes.do",param,function(data){
				if(!data.msg){
					$("#clickCode5s").html("发送失败");
				}else{
					/* $("#clickCode5s").html("点击重新发"); */
					timers=180;
		          	tipId=window.setInterval("timer_()",1000);
				}
			});
		}
	}
	function timer_(){
		if(timers>=0){
			$("#clickCode5s").html("验证码获取："+timers+"秒");
		    timers--;
		    //whb 修复重新发送验证码时间显示问题
		  /*   if(timers==0){
		    	window.clearInterval(tipId);
		    	$.post("DelPhoneCode.do",null,function(){});
		    	$("#clickCode5s").html("点击重新获取");
		    } */
		 }else{
	          window.clearInterval(tipId);
	          $("#clickCode5s").html("重新发送验证码");
	          $.post("DelPhoneCode.do",null,function(){});
	     }
	}
</script>
   <jsp:include page="/include/head.jsp"></jsp:include>
      <jsp:include page="/include/common.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
    <input type="hidden" id="paramMap.id" value="${map.id}"/>
    <input type="hidden" id="phone" value="${maps.cellPhone }"/>
<div class="s_loginmain clearfix">
	<div class="s_login_box clearfix">
    	<h4>手机找回</h4>
        <div class="s_findpwmain">
            <!--
            	正在进行的li类名：class="step-doing"           
			-->
            <ul class="find-step clearfix">
            	<li>
                	<span class="step">1</span>
                    <span class="line"></span>
                    <p>输入手机号码</p>
                </li>
                <li class="step-doing">
                	<span class="step">2</span>
                    <span class="line"></span>
                    <p>验证身份</p>
                </li>
                <li>
                	<span class="step">3</span>
                    <span class="line"></span>
                    <p>重置密码</p>
                </li>
                <li class="li4">
                	<span class="step">√</span>
                    <p>完成</p>
                </li>
            </ul>
            <ul class="find-input">
            	<li><span style="color: red; float: none;" id="s_email" class="formtips">${msg }</span></li>
				<li class="name">
                	<label>用&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名</label>
                	<span id="name" class="orange">${maps.username }</span>
                </li>
				<li class="name">
                	<label>已验证手机</label>
                	<span id="phone2" class="orange">${maps.cellPhones}</span>  
                </li>
                <li class="yzm">
                	<label>手机验证码</label>
                    <input type="text" id="code"/> 
                    <a onclick="clickCode5();" id="clickCode5s" class="getmsg" style="cursor: pointer;">点击获取验证码</a>
                </li>
                 <li class="notice" style="display:none;">验证码不能为空/验证码输入错误</li>
                <li class="next step2-next">
                	<a href="javascript:void();" onclick="send_password();">下一步</a>

                </li>
            </ul>
        </div>
    </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>

<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
});
</script>



</body>
</html>
