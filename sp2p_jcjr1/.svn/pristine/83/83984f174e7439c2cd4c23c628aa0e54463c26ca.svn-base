<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>
<link rel="shortcut icon" href="favicon.ico">
<script type="text/javascript">

//初始化
function switchCode_top(){
	var timenow = new Date();
		$("#codeNum_top").attr("src","/${sitemap.adminUrl}/imageCode.do?pageId=userlogin&d="+timenow);
	};
//===验证数据
$(document).ready(function(){
		//===========input失去焦点
		     $("form :input").blur(function(){
		       //email
	                 if($(this).is("#email")){   
		            if(this.value==""){   
		                $("#s_email").attr("class", "formtips onError");
		                $("#s_email").html("*请输入用户名或邮箱地址");
		            }else if(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)){ //判断用户输入的是否是邮箱地址
		            	checkRegister('email');
		            }else if((/^1[3,5,8]\d{9}$/.test(this.value))){
		                checkRegister('cellphone');
		            }else{ 
		            	checkRegister('userName');
		            	  $("#s_email").attr("class", "formtips");
		            }   
		        }  
		     //password
		   	   if($(this).attr("id")=="password"){
		   	    pwd=this.value; 
		     if(this.value==""){
		      	$("#s_password").attr("class", "formtips onError");
		        $("#s_password").html("*请輸入您的密码");  
		     }else if(this.value.length<6){ 
		      	$("#s_password").attr("class", "formtips onError");
		      $("#s_password").html("*密码长度为6-20个字符"); 
		     }else{
		     $("#s_password").html(""); 
		      	$("#s_password").attr("class", "formtips");
		     }
		   }
		   
		   	  //验证码
		   	   if($(this).attr("id")=="code_top"){
		     if(this.value==""){
		     $("#s_code").attr("class", "formtips onError");
		      $("#s_code").html("*请输入验证码"); 
		     }else{   
		        		$("#s_code").attr("class", "formtips");
		                $("#s_code").html(""); 
		            } 
		   }
		       
		      });
    });



function checkRegister(str){
   var param = {};
   	if(str == "userName"){
			param["paramMap.email"]  = "";
			param["paramMap.userName"] = $("#email").val();
		}else if(str=="email"){
			param["paramMap.email"] = $("#email").val();
			param["paramMap.userName"] = "";
		}else{
			param["paramMap.email"] = "";
			param["paramMap.userName"] = "";
			param["paramMap.cellphone"] = $("#email").val();
		}
		$.post("ajaxCheckLog.do",param,function(data){
		    $("#email_").hide();
           if(data == 2 && str == "userName"){
              $("#s_email").html("*无效用户！");
           }else if(data == 3 && str == "userName"){
              $("#s_email").html("*该用户还没激活！");
              //add by houli
              $("#email_").show();
           }else if(data == 4&& str == "userName"){
           	$("#s_email").attr("class", "formtips");
         	$("#s_email").html("");
           }
           if(data == 0 && str == "email"){
              $("#s_email").html("*无效邮箱！");
           }else if(data == 1 && str == "email"){
              $("#s_email").html("*该邮箱用户还没激活！");
              //add by houli
              $("#email_").show();
           }else if(data == 4&& str == "email"){
           	$("#s_email").attr("class", "formtips");
         	$("#s_email").html("");
           } 
	          if(data == 5 && str == "cellphone"){
              $("#s_email").html("*用户不存在！");
           }else if(data == 4 && str == "cellphone"){
             $("#s_email").html("");
           }
		});
		
}

function login(){
	 $(this).attr('disabled',true);
	 $('#btn_login').attr('value','登录中...');
       var param = {};
		param["paramMap.pageId"] = "userlogin";
		param["paramMap.email"] = $("#email").val();
		param["paramMap.password"] = $("#password").val();
		param["paramMap.code"] = $("#code").val();
		param["paramMap.afterLoginUrl"]="${afterLoginUrl}";
      $.post("logining.do",param,function(data){
     		if(data == 1){
				   window.location.href='index.jsp';
			}else if (data == 2) {
			     $('#btn_login').attr('value','登录');
				alert("验证码错误!");
               switchCode();
                $("#btn_login").attr('disabled',false); 
			} else if (data == 3) {
				 $('#btn_login').attr('value','登录');
               alert("用户名或密码错误!");
                switchCode();
                $("#btn_login").attr('disabled',false); 
			} else if (data == 4) {
				$('#btn_login').attr('value','登录');
				$("#s_email").attr("class", "formtips onError");
				alert("该用户已被禁用!");
				 switchCode();
                $("#btn_login").attr('disabled',false); 
			} else if (data == 5) {
				 $('#btn_login').attr('value','登录');
					$("#s_email").attr("class", "formtips onError");
					 switchCode();
	                $("#s_email").html("*该用户已被限制登录，请于三小时以后登录！"); 
	                 $("#btn_login").attr('disabled',false); 
			}else if (data == 7) {
				$('#btn_login').attr('value','登录');
				$("#s_email").attr("class", "formtips onError");
				alert("*该用户账号出现异常，请速与管理员联系！");
               switchCode(); 
               $("#btn_login").attr('disabled',false); 
			}
      });

	};

	$("#code").bind('keyup', function(event){
		   if (event.keyCode=="13"){
		      login();  
		   }
		});	
	$("#email").focus(function(){
		$("#email").val("");
	});
	$("#code").focus(function(){
		$("#code").val("");
	});
	
	function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=userlogin&d="+timenow);
		}
	
</script>
<style>
.ui-nav-item{ font-size:15px; color:#ccc;  float:left;}
.icon_app{ background:url(/images/app/ip.png) no-repeat; width:15px; height:15px; display:inline-block; position:relative; top:1px;}
.fl{ float:left;}
</style>
<!--头部 begin-->
<div class="p_headerbox index-main">
    <div class="p_topbox">
        <div class="p_top clearfix">
            <div class="p_topleft"> <span>客服电话：<em class="f18">400-001-6007</em> （9:00-21:00）</span> </div>
            <div class="p_topright"> 
            	<div class="fl" style="margin-top:1px; margin-right:14px;">
            	<a href="/page/app/download.html" class="ui-nav-item" target="_blank">
            	<i class="icon_app"></i>移动客户端</a></div> 
            <s:if	 test="#session.user !=null">
             <input type="hidden" id="islogin" value="ok"/>
				     <img src="images/p_ico003.png" /><a href="home.do" style="color:#f90; margin-right:10px;">我的微信贷</a><span></span> <a href="logout.do">退出</a>
				  </s:if>
				  <s:else>
				  <span><a href="cellPhonereginit.do" style="color:#ff6960;"><img src="images/p_ico003.png" /> 我要注册</a></span> 
				  <span><a href="login.do"   style="color:#F90;"><img src="images/p_ico004.png" /> 立即登录</a></span>
				   </s:else>
             </div>
        </div>
    </div>
    <div class="p_header clearfix">
        <div class="p_logo"><a href="index.jsp"><img src="images/logo.png"></a></div>
        <div class="p_nav"><a class="hover" href="index.jsp">首页</a><a href="finance.do?m=1&type=4" id='我要投资'>我要投资</a><a href="borrow.do" id='我要借款'>我要借款</a><a href="capitalEnsure.do" id='安全中心'>安全中心</a><a href="getMessageBytypeId.do?typeId=4" id='关于我们'>关于我们</a></div>
    </div>
</div>
<!--------------------------头部 end--------------------------->
