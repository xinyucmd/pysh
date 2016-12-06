<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${sitemap.siteName}</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<jsp:include page="/include/common.jsp"></jsp:include>
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
          $("#s_email").html("邮箱不能为空");
        }else if(data.mailAddress=='1'){
          $("#s_email").html("该邮箱不存在");
        }else{
          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+"><font color='red'> 登录</font></a>到你的邮箱验证");
        }
       });
    });
  
  
  });


</script>
   <jsp:include page="/include/head.jsp"></jsp:include>
   <style>
     
     .fog002{ padding:50px 80px;} 
	.h_01{ float:left; width:400px; text-align:center;} 
	.h_02{ border-left:1px solid #ccc;} 
	.h_btn{ width:140px; height:36px; line-height:36px; text-align:center; color:#fff; background:#f39b41; display:inline-block; cursor:pointer; font-size:16px; margin-top:40px; border-radius:4px;} 
	.h_btn1{background:#008bcf;}
   
   </style>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
<!--内页主体 开始-->
<div class="s_loginmain clearfix">
	<div class="s_login_box clearfix">
    	<h4>找回密码</h4>
        <div class="s_findpw clearfix">
        	<dl class="dl1">
				<dt><img src="images/s_pic44.png" /></dt>
				<dd><a href="forgetpasswordphone.do">手机找回</a></dd>
			</dl>
			<dl class="dl2">
				<dt><img src="images/s_pic45.png" /></dt>
				<dd><a href="forgetpasswordemail.do">邮箱找回</a></dd>
			</dl>
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
