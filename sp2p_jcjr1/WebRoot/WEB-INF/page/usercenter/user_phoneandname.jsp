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
  $(function(){
  
    $("#send_password").click(function(){
    	var phone = $("#email").val();
      	if(phone==""){
      		$("#s_email").html("手机或用户名不能为空");
      	}else{
      		$("#s_email").html("");
      		var param = {};
      		param["paramMap.code"] = $("#code").val();
      		$.post("CheackCode.do",param,function(data){
      		if(!data.msg){
      			$("#s_email").html("验证码不匹配.");
      			switchCode_userregister();
      		}else{
      			var phone = $("#email").val();
				if(phone != "" || phone != null){
				var param = {};
				param["paramMap.phone"] = phone;
				$.post("queryPhonePs.do",param,function(data){
				if(data.msg == 1){
					$("#s_email").html("");
				}else if(data.msg == 2){
					$("#s_email").html("此用户未绑定手机号码.");
					return;
				}else if(data.msg == 0){
					$("#s_email").html("用户名或手机号不存在.");
					return;
				}
				$("#s_email").html("");
      			window.location.href="forgetpasswordphones.do?phone="+phone;
			});
      			
      		}
      	}});
      	}
    });
  
  
  });


</script>
<script>
		//初始化
		function switchCode_userregister(){
			var timenow = new Date();
			$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=userregister&d="+timenow);
		}
</script>
<script>
	function cheakPhone(){
		var phone = $("#email").val();
		if(phone != "" || phone != null){
			var param = {};
			param["paramMap.phone"] = phone;
			$.post("queryPhonePs.do",param,function(data){
				if(data.msg == 1){
					$("#s_email").html("");
				}else if(data.msg == 0){
					$("#s_email").html("用户名或手机号不存在.");
					return;
				}
			});
		}
	}
</script>
   <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="s_loginmain clearfix">
	<div class="s_login_box clearfix">
    	<h4>手机找回</h4>
        <div class="s_findpwmain">
            <!--
            	正在进行的li类名：class="step-doing"           
			-->
            <ul class="find-step clearfix">
            	<li class="step-doing">
                	<span class="step">1</span>
                    <span class="line"></span>
                    <p>输入手机号码</p>
                </li>
                <li>
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
            	<li><span style="color: red; float: none;" id="s_email" class="formtips"></span></li>
            	<li class="name">
                	<label>手机号码</label>
                    <input type="text"  id="email" onblur="cheakPhone();"/>
                </li>
                <li  class="notice" style="display:none;">登录账号不能为空/登录账号不存在</li>
                <li class="yzm">
                	<label>验&nbsp;&nbsp;证&nbsp;码</label>
                    <input type="text" class="inp100"  name="paramMap.code" id="code"/>
    <img src="${sitemap.adminUrl}/imageCode.do?pageId=userregister" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="100" height="30" onclick="javascript:switchCode_userregister()" />
	 <a href="javascript:void()" onclick="switchCode_userregister()" style="color: blue;">换一换?</a>	
                </li>
                 <li class="notice" style="display:none;">验证码不能为空/验证码输入错误</li>
                <li  class="next">
                	
                	<a href="javascript:void()" id="send_password">下一步</a>
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
