<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
	<script>
	   $(function(){
	     $("#friendAward").click(function(){
	       
	        $.post("showCostType.do","type=3",function(data){
	             $("#showcontent").html("");
	            $("#feind").attr("bgcolor","#CC0000");
	            $("#showcontent").html(data);	        
	        });
	      
	     });
	    
	   });
	 
	
	</script>	

		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td  width="18%" height="28"  class="main_alll_h2">
									<a href="emailAndMessageindex.do">邮件设置</a>
								</td>
								<td width="2px">
								</td>
								<td width="18%" height="28" class="xxk_all_a">
									<a href="findSMSList.do">短信接口设置</a>
								</td>
								<td width="80">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
						
						<table width="50%" border="0" cellspacing="1" cellpadding="3" align="center">
							<tr style="height: 20px;"></tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									SMTP服务器：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_host" name="paramMap.host"
										 theme="simple" value="%{#request.mailMap.host}"></s:textfield>
									<span class="require-field" id="e_host">*</span>(如：smtp.126.com)
								</td>
							</tr>	
								<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									端口号:
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_port" name="paramMap.port"
										 theme="simple" value="%{#request.mailMap.port}"></s:textfield>
									<span class="require-field" id="e_port">*</span>
								</td>
							</tr>	
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									邮箱地址：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_mailaddress" name="paramMap.maildress"
										 theme="simple" value="%{#request.mailMap.mailaddress}"></s:textfield>
									<span class="require-field" id="e_mailaddress">*</span>(如：flyyeh@126.com)
								</td>
							</tr>	
							<tr>
                                 <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									邮箱密码：
								</td>
								<td align="left" class="f66">
								<!--  
									<s:password  id="tb_password" name="paramMap.password"
										 theme="simple" value="%{mailMap.mailpassword}" ></s:password>-->
										 <input type="password" name="paramMap.password" id ="tb_password" value="${mailMap.mailpassword }"/>
									<span class="require-field" id="e_password">*</span>
								</td> 							
							
							</tr>
							<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发件人Email：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_sendEmail" name="paramMap.sendEmail"
										 theme="simple" value="%{#request.mailMap.sendmail}"></s:textfield>
									<span class="require-field" id="e_sendEmail">*</span>(如：flyyeh@126.com)
								</td> 	
							
							</tr>
							
								<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发件人昵称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_username" name="paramMap.username"
										 theme="simple"  value="%{#request.mailMap.sendname}"></s:textfield>
									<span class="require-field" id="e_username">*</span>
								</td> 	
							
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<button id="btn_tijiao"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						
						
						<br />
					</div>
				</div>
			</div>
		<script>
		$(function(){
		  	 
		  	 $('#btn_tijiao').click(function(){
		  	  $("#btn_tijiao").attr("disabled",true);
			  	 if($("#tb_host").val()==""){
			  		$("#e_host").html("服务器地址不能为空!");
			  		 $("#btn_tijiao").attr("disabled",false);
					return false;
				  	}else{
				  		$("#e_host").html("*");
					  }
			  	 if($("#tb_port").val()==""){
				  		$("#e_port").html("端口号不能为空!");
				  		 $("#btn_tijiao").attr("disabled",false);
						return false;
					  	}<%--else if (isNaN($("#tb_port").val())){
					  		$("#e_port").html("端口号只能为数字!");
					  		return false;
						  }--%>
						 else
							  {
							  $("#e_port").html("*");
							  }
			  	 if($("#tb_password").val()==""){
				  		$("#e_password").html("密码不能为空!");
				  		 $("#btn_tijiao").attr("disabled",false);
						return false;
					  	}else{
					  		$("#e_password").html("*");
						  }
		  		if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#tb_mailaddress").val())){
					$("#e_mailaddress").html("请正确填写邮箱地址!");
					 $("#btn_tijiao").attr("disabled",false);
					return false;
				}else{
					$("#e_mailaddress").html("*");
				}
		  		if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#tb_sendEmail").val())){
					$("#e_sendEmail").html("请正确填写发件人邮箱!");
					 $("#btn_tijiao").attr("disabled",false);
					return false;
				}else{
					$("#e_sendEmail").html("*");
				}
		  	 	 if($("#tb_username").val()==""){
				  		$("#e_username").html("发件人昵称不能为空!");
				  		 $("#btn_tijiao").attr("disabled",false);
						return false;
					  	}else{
					  		$("#e_username").html("*");
						  }
		  		
		  	   if (!confirm("你确认更改邮件参数设置吗?")){
				return false;
		  }
		   var param ={};
		  param["paramMap.maildress"] = $("#tb_mailaddress").val();
		  param["paramMap.password"] = $("#tb_password").val();
		  param["paramMap.sendEmail"] = $("#tb_sendEmail").val();
          param["paramMap.username"] = $("#tb_username").val();
          param["paramMap.port"] = $("#tb_port").val();
          param["paramMap.host"] = $("#tb_host").val();
          $.post("updateMailSet.do",param,function(data){
               var callBack = data.msg;  
                if(callBack == undefined || callBack == ''){
                 $('#showcontent').html(data);
                }else{
                  if(callBack == 1){
		          alert("操作成功!");
		          window.location.reload();
		          return false;
		          }
		          //alert(callBack);    
                }    
          });
		  	 

          });
		
	    
		});
		
		
		</script>
	</body>
</html>
