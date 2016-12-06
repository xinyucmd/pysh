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
       var param = {};
       param["paramMap.email"] = $("#email").val();
       //alert(param["paramMap.email"]);
       $.post("forgetpasswordsenEml.do",param,function(data){
        if(data.mailAddress=='0'){
          $("#s_email").html("邮箱或用户名不能为空");
        }else if(data.mailAddress=='1'){
          $("#s_email").html("该邮箱或用户名不存在");
        }else{
          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+"><font color='red'> 登录</font></a>到你的邮箱验证");
        }
       });
    });
  
  
  });


</script>
   <jsp:include page="/include/head.jsp"></jsp:include>
   <jsp:include page="/include/common.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="s_loginmain clearfix" id ="ok">
	<div class="s_login_box clearfix">
    	<h4>邮箱找回</h4>
        <div class="s_findpwmain">
            <!--
            	正在进行的li类名：class="step-doing"           
			-->
            <ul class="find-step clearfix">
            	<li class="step-doing">
                	<span class="step">1</span>
                    <span class="line"></span>
                    <p>输入邮箱地址</p>
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
            	<li><span style="color: red; float: none;" id="s_email" class="formtips">${msg }</span></li>
            	<li class="name">
                	<label>邮箱地址</label>
                    <input type="text"  id="email"/>
                </li>
                <li  class="notice" style="display:none;">登录账号不能为空/登录账号不存在</li>
                <li  class="next">
                	<a href="javascript:void();" id="send_password">下一步</a>
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
