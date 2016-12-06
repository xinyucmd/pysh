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
            	<li>
                	<span class="step">1</span>
                    <span class="line"></span>
                    <p>输入手机号码</p>
                </li>
                <li>
                	<span class="step">2</span>
                    <span class="line"></span>
                    <p>验证身份</p>
                </li>
                <li class="step-doing">
                	<span class="step">3</span>
                    <span class="line"></span>
                    <p>重置密码</p>
                </li>
                <li class="li4">
                	<span class="step">√</span>
                    <p>完成</p>
                </li>
            </ul>
            <ul class="find-input set-newpw">
            	<li><div id="s_tip" align="left" style="color: red;"></div></li>
               <li class="newpw">
                	<label>新登录密码</label>
                    <input type="password"  id="newPassword"/><span class="txt" style="color: red;">*输入您的新密码</span>
                </li>
                <li  class="notice" style="display:none;">新登录密码不能为空</li>
                 <li class="newpw">
                	<label>确认新登录密码</label>
                    <input type="password" id="confirmpassword" /><span class="txt" style="color: red;">*请再次输入您的新密码</span>
                </li>
                <li  class="notice" style="display:none;">请再次输入</li>
                <li class="next step3-next">
                	<a href="javascript:void();" id="bt_yes">下一步</a>
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
<script>
$(function(){
   $("#newPassword").blur(function(){
     if($("#newPassword").val()==""){
       $("#s_tip").html("新密码不能为空");
     }else{
       $("#s_tip").html("");
     }
   });
   $("#confirmpassword").blur(function(){
      if($("#newPassword").val()!=$("#confirmpassword").val()){
        $("#s_tip").html("两次密码不相同");
      }else{
       $("#s_tip").html("");
      }
   });

  $("#bt_yes").click(function(){
  	if($("#newPassword").val() == ""){
  		$("#s_tip").html("新密码不能为空");
  	}else if(!$("#newPassword").val() == $("#confirmpassword").val()){
  		$("#s_tip").html("两次密码不相同");
  	}else if($("#newPassword").val().length<6){
  		$("#s_tip").html("密码长度为6-20个字符");
  	}else{
  		 var param = {};
    param["paramMap.passwd"] =  $("#newPassword").val();
    param["paramMap.id"] = '${userId}';
    param["paramMap.confirmpassword"] = $("#confirmpassword").val();
    $.post("reUserPasswd.do",param,function(data){
     if(data.msg){
     	alert("修改密码成功");
     	window.location.href='login.do';
     }else {
    	 alert("修改密码失败");
     }
    });
  	
  	}
  	
  
   
  });

});

</script>


</body>
</html>
