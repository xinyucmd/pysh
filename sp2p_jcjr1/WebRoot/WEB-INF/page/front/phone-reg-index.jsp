<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>${sitemap.siteName}-手机注册</title>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="css/css.css" rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include> 
<style>
  	ul,li{margin:0;padding:0}
     #scrollDiv{height:182px;overflow:hidden}
	.s_loginmain_1{ width:960px; border-radius:10px; height:500px; background:#f9f9f9; border:solid 1px #dadada; padding-left:20px; margin:20px auto;}
	.test_1{ width:292px; height:34px; border:solid 1px #757575; background:#f9f9f9; padding-left:10px; line-height:34px;}
	.test_2{ width:139px; height:34px; border:solid 1px #757575; background:#f9f9f9;padding-left:10px; line-height:34px;}
	.s_login-left{ width:400px; float:left; position:relative; top:80px;}
	.s_login-left ul{}
	.s_login-left ul li{ }
	.s_login-left label{ width:60px; text-align:right; display:inline-block; color:#505050; font-weight:bold; font-size:14px;}
	.mt{ margin-left:35px;}
	.forget_pw{ position:absolute; right:40px;}
	.change {background:url(images/img/pic91.jpg) no-repeat;display: inline-block;height: 25px;line-height: 39px;margin-left: 10px;vertical-align: middle;width: 25px;}
	.s_login_btn1{ width:185px; height:55px; border-radius:5px; background:#fc4343; font-size:24px; color:#fff; border:none; font-weight:bold; cursor:pointer;}
	.check_box{ font-size:14px; margin-left:100px; margin-bottom:25px; color:#585858;}
	.check_box a{ color:#1eb3d3;  font-weight:bold;}
	.big_img{ width:475px; position:relative; left:50px; float:left; }
</style>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<!--内页主体 开始-->
<form>
<div class="s_loginmain_1">
	<p style="margin:35px 0 45px 0;"><img src="images/img/bg2.jpg" width="931" height="24" /></p>
   	<div class="s_login-left">
         <ul>
            	<li><label>手机号</label><input type="text" class="test_1 mt"  name="paramMap.cellphone" id="cellphone"/></li>
            	<li class="error-notice" style=" margin:10px 0 10px 100px;"><span class="s_login_txt"></span><span style="color: red" id="s_cellphone" class="formtips"></span></li>
               <li><label>验证码</label><input type="text" class="test_2 mt"  name="paramMap.code" id="code"/> 
               <img src="${sitemap.adminUrl}/imageCode.do?pageId=cellphone" title="点击更换验证码" style="cursor: pointer;"
				id="codeNum" width="100" height="30"  onclick="javascript:switchCode()" />
				 <a href="javascript:void()" onclick="switchCode()" class = "change"></a></li>
				<li class="error-notice" style="margin-left:100px;"><span class="s_login_txt"></span><span style="color: red;" id="s_code" class="formtips"> </span></li>
		 </ul>
		 <p class="check_box"><input id="agre" type="checkbox" checked="checked" style="margin-right:5px;" />我已经阅读并同意<a onclick="fff()">《会员注册及服务协议》</a></p>
         <p style="margin-left:90px;"><input id="btn_cellreg" type="button" value="免费注册" class="s_login_btn1" /></p>
    </div>
     <div class="big_img"><img src="images/img/img2.jpg"  /></div>
</div>
</form>
<!--内页主体 结束-->

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
  <script type="text/javascript" src="css/popom.js"></script>
  <script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
  <script>
  //弹出隐私条款和使用条款
 function fff(){
		    	  jQuery.jBox.open("post:querytips.do", "使用条款", 600,400,{ buttons: { } });
		    }
		    function ffff(){
		      ClosePop();
		    }
</script>
<script>
 function ffftip(){
	 jQuery.jBox.open("post:querytip.do", "隐私条款", 600,400,{ buttons: { } });
		    }
</script>
  <script>
//回车登录
document.onkeydown=function(e){
  if(!e)e=window.event;
  if((e.keyCode||e.which)==13){
   cellreg();
  }
}
</script>
  <script>
  $(function(){
 $("form :input").blur(function(){ 
 //手机号码
 if($(this).attr("id")=="cellphone"){
   if((!/^1[3,4,5,7,8]\d{9}$/.test($("#cellphone").val()))){
         $("#s_cellphone").attr("class", "formtips onError");
		 $("#s_cellphone").html("手机号码格式错误"); 
		 $(this).val("");
   }else{
         $("#s_cellphone").attr("class", "formtips");
		 $("#s_cellphone").html(""); 
   }
 }
 //验证码
 if($(this).attr("id")=="code"){
   if($(this).val()==""){
     	 $("#s_code").attr("class", "formtips onError");
		 $("#s_code").html("验证码不正确"); 
   }else{
        $("#s_code").attr("class", "formtips");
		 $("#s_code").html(""); 
   }
 }
 });  
  });
  </script>
  <script>
  		//初始化验证码
		function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=cellphone&d="+timenow);
		}
  </script>
  <script>
   var falg = true;
  function cellreg(){
   var errornum=$("form .onError").length;
              if(falg){
               falg = false;
               if($("#cellphone").val()==""){
                 $("#s_cellphone").html("手机号码格式错误");
                 falg = true;
                 switchCode();
                 return false;
               }
               if($("#code").val()==""){
                 $("#s_code").html("验证码不正确");
                 falg = true;
                 switchCode();
                 return false;
               }
               if(!$("#agre").attr("checked")){
		            alert("请勾选我已阅读并同意《会员注册及服务协议》");
		            falg = true;
		            return false;
		       }
               var param = {};
               param["paramMap.cellphone"]= $("#cellphone").val();
               param["paramMap.pageId"] = "cellphone";
               param["paramMap.code"]= $("#code").val();
               $.post("cellPhoneregsinit.do",param,function(da){
               if(da==1){
                 var cellph = $("#cellphone").val();
                 if('${DEMO}'!=1){
	                 /**发送手机验证码**/
	                 var phone = $("#cellphone").val();
                     var code = $("#code").val();//只有手机注册页面需要code
                     $.post("phoneCheck.do","phone="+phone+"&code="+code,function(datas){
                   	 	if(datas.ret != 1){
                    	    alert(datas.msg);
                    	    return;
                   	    }
                    	   /*  phone = datas.phone;
                    	    var test={};
                    	    test["paramMap.phone"] = phone;
                    	    test["paramMap.pageId"] = "cellphone";//只有手机注册页面需要pageId
               	            $.post("sendSMSNew.do",test,function(data){
               	                if(data==1){
               	                	window.location.href='cellPhonereginit.do';
               	                }else{
               	                	alert("手机验证码发送失败");
               	                }
               	            }); */
                    	});
                  }
                 	//else{
                  window.location.href='cellPhonereginit.do';
                  // }
               }else if(da==2){
               $("#s_code").html("验证码不正确");
                 falg = true;
                 switchCode();
               }else if(da==3){
               $("#s_cellphone").html("手机号码格式错误");
                 falg = true;
                 switchCode();
               }else if(da==5){
            	   $("#s_cellphone").html("手机号已存在!");
                   falg = true;
                   switchCode();
               }
               });
              }
  }
  </script>
  <script>
  //提交
  $(function(){
     $("#btn_cellreg").click(function(){
     //加入百度按钮跟踪的代码
 	 _hmt.push(['_trackEvent','免费注册','click','免费注册']);
     cellreg();
     });
  });
  </script>
</body>
</html>
