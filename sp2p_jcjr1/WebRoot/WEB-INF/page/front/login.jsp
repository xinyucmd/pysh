<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
<style type="text/css">
.form_ss{
 	width:226px;
 	height:36px;
 	 padding:0 10px;
 	 border:1px solid #96ccf4;
 	 border-radius:3px;
}
.form_ss:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
}
</style>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<!--内页主体 开始-->

<div class="wrap" style="background:#fff;">
    <div class="new-show">
        <!-- <div>微信贷官网全新改版，11月1日开始全面公测啦!</div> -->
    </div> 
	<div class="login">
    	<div class="s_rzsqleft" style="margin-top:140px;">
        	<p style="font-size:20px; color:#1cacff;">会员登录</p>
        	  <table cellspacing="0" cellpadding="0" border="0">
             	    <tr>
						<th>账号：</th>
						<td>
						  <input type="text" class="form_ss" name="paramMap.email" placeholder="已绑定邮箱/手机/用户名" id="email"/>
						  <span id="email_del" style="display: none;">
								<a id="retake_email_del"  href="javascript:reSendEmail();">发送激活邮件</a>
						  </span>			
						</td>
					</tr>
					
                 	<tr height="24">
                 	  <td></td>
                      <td><span style="color: red; " id="s_email" class="formtips"></span></td>
                 	</tr> 
                 	
                 	<tr>
						<th>密码：</th>
						<td><input type="password" class="form_ss" name="paramMap.password" placeholder="密码" id="password"/>
						    <a  href="forgetpassword.do" class="forget_pw">忘记密码</a>
						</td>
					</tr>
					<tr height="24">
					  <td></td>
                      <td><span style="color: red; " id="s_password" class="formtips"></span> </td>
					</tr> 
					
					 <tr>
						<th>图片验证码：</th>
						<td><input type="text" style="width:150px;"  class="form_ss"  placeholder="请输入图片验证码" name="paramMap.code" id="code"/> 
                            <img src="${sitemap.adminUrl}/imageCode.do?pageId=userlogin" title="点击更换验证码"
							style="cursor: pointer;" id="codeNum" width="100" height="30" 
							onclick="javascript:switchCode()" /> &nbsp;&nbsp; <a href="javascript:void()" onclick="switchCode()" class="change"></a>
                        </td>
					</tr>
                   <tr height="24">
                      <td></td>
                      <td><span style="color: red;" id="s_code" class="formtips"></span></td>
                   </tr>
              </table>
              <button  id="btn_login" type="button" class="btn btn-primary btn-lg" style="margin-left:120px; width:220px; height:40px;">立即登录</button>
              <p style=" color:#505050; margin-left:180px; font-size:14px; margin-top:10px;">没有账号？<a href="cellPhonereginit.do">立即注册</a></p>  
        </div> 
    </div>
</div>
<!--内页主体 结束-->

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
$(function(){
 

(4);
	$("#code").bind('keyup', function(event){
			   if (event.keyCode=="13"){
			      login();  
			   }
			});	
	$("#email").bind('keyup', function(event){
			   if (event.keyCode=="13"){
			      login();  
			   }
			});	
	$("#password").bind('keyup', function(event){
			   if (event.keyCode=="13"){
			      login();  
			   }
			});		

    //样式选中

	});
	
		//初始化
		function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=userlogin&d="+timenow);
		}
		$(document).ready(function(){
		//===========input失去焦点
		     $("form :input").blur(function(){
		       //email
	                 if($(this).is("#email")){   
		            if(this.value==""){   
		                $("#s_email").attr("class", "formtips onError");
		                $("#s_email").html("*请输入用户名或邮箱地址");
		            }else if(/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)){ //判断用户输入的是否是邮箱地址
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
		   	   if($(this).attr("id")=="code"){
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
		
		
		//===验证数据
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
       
       
       
       
</script>
<script>
function login(){
	$(this).attr('disabled',true);
    if($("#email").val()==""){   
            $("#s_email").attr("class", "formtips onError");
            $("#s_email").html("*请输用户名或邮箱地址");
        }
            if($("#password").val()==""){   
        	$("#s_password").attr("class", "formtips onError");
            $("#s_password").html("*请输入密码");   
           // $("#retake_password").hide();
        }  
        	$('#btn_login').text('登录中...');
    var param = {};
	param["paramMap.pageId"] = "userlogin";
	param["paramMap.email"] = $("#email").val();
	param["paramMap.password"] = $("#password").val();
	param["paramMap.code"] = $("#code").val();		
	param["paramMap.afterLoginUrl"]="${session.afterLoginUrl}";
	var afterLoginUrl='${session.afterLoginUrl}';
	// 活动需要强制跳转到指定页面
	var forceAfterLoginUrl = '${session.forceAfterLoginUrl}';
	
	if(forceAfterLoginUrl!=''){
		afterLoginUrl = forceAfterLoginUrl;
	}
	$.post("logining.do",param,function(data){
		if(data == 1){
			// TODO:
			if(afterLoginUrl != ''){
	   			window.location.href=afterLoginUrl;
	  	   	}else{
  	   	$('#btn_login').text('登录成功！跳转中...');
 		window.location.href='home.do';
	  	  	}
		}else if (data == 2) {
			$('#btn_login').text('立即登录');
			$("#s_code").attr("class", "formtips onError");
			$("#s_code").html("*验证码错误！"); 
			switchCode();
			$("#btn_login").attr("disabled",false); 
		} else if (data == 3) {
			$('#btn_login').text('立即登录');
			$("#s_email").attr("class", "formtips onError");
	              $("#s_email").html("*用户名或密码错误！"); 
	               switchCode();
	               $("#btn_login").attr('disabled',false); 
		} else if (data == 4) {
			$('#btn_login').text('立即登录');
			$("#s_email").attr("class", "formtips onError");
			 switchCode();
	              $("#s_email").html("*该用户已被禁用！"); 
	               $("#btn_login").attr('disabled',false); 
		}else if (data == 5) {
			$('#btn_login').text('立即登录');
				$("#s_email").attr("class", "formtips onError");
				 switchCode();
	               $("#s_email").html("*该用户已被限制登录，请于三小时以后登录！"); 
	                $("#btn_login").attr('disabled',false); 
			} else if (data == 7) {
				$('#btn_login').text('立即登录');
			$("#s_email").attr("class", "formtips onError");
			switchCode();
	              $("#s_email").html("*该用户账号出现异常，请速与管理员联系！"); 
	              $("#btn_login").attr('disabled',false); 
		}else if (data == 11) {
			$('#btn_login').text('立即登录');
			switchCode();
	        $("#btn_login").attr('disabled',false); 
	        $("#s_email").html(""); 
	        $("#s_email").html("*密码错误,还有2次登录机会！"); 
	         
		}else if (data == 22) {
			$('#btn_login').text('立即登录');
			switchCode();
	        $("#btn_login").attr('disabled',false); 
	        $("#s_email").html(""); 
	        $("#s_email").html("*密码错误，还有1次登录机会！"); 
		}else if (data == 33) {
			$('#btn_login').text('立即登录');
			switchCode();
	        $("#btn_login").attr('disabled',false); 
	        $("#s_email").html(""); 
	        $("#s_email").html("*密码错误，登录次数已到3次，该账户将被限制登录！"); 
		}
		
	});
}

function reSendEmail(){
   if($("#email").val()==""){
     alert("请输入邮箱");
     return;
   }
   window.location.href = "reActivateEmail.do?email="+$("#email").val();
}

 $("#btn_login").click(function(){
		    login();  
		  });


</script>

</body>
</html>
