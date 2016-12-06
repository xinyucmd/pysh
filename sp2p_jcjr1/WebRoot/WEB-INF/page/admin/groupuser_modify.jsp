<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<style>
.close_btn{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; left:600px; cursor:pointer; top:10px; border:none;}
.close_btn1{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; left:600px; cursor:pointer; top:10px; border:none;}
.s_login_txt{ width:150px; text-align:right; display:inline-block; font-size:12px; margin-right:10px;}
.input_a{ width:160px; height:26px;border:none; border:solid 1px #c7c7c7; }
.input_b{ padding:0 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 1px #c7c7c7;; margin-left:10px;}
.input_c{ padding:2px 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 2px #c7c7c7;; margin-left:10px; cursor:pointer;}
.input_d{ padding:0 10px; background:#c7c7c7; border:none; line-height:26px; border-radius:3px;  margin-left:5px; cursor:pointer;}
.zh_table th {background-color: #f9f9f9;border: 1px solid #ddd;font-weight: normal;text-align: center; width:14%;}
.zh_table td {border: 1px solid #ddd;text-align: center; width:14%;}
#ul_input li{ line-height:40px;}
#ul_div_check li{ line-height:30px;}
</style>
		<title>管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" language="javascript">
	    	$(function(){
	    	    
	    	    //-----add by houli 加载页面的时候将用户列表显示
	    	    $("#span_usernames").html('${paramMap.users}');
	    	    $("#userId").val('${paramMap.userIds}');
	    	    //-----------
	    	    
				//提交表单
				$("#btn_save").click(function(){
					$(this).hide();
					$("#editForm").submit();
					return false;
				});
				$("#add_users").click(function(){
				    
					var result = window.showModalDialog("queryGroupUserInit.do",window,"dialogWidth=800px;dialogHeight=600px");
					if(result != null && result != undefined){
						//判断不允许重复添加
						var prepareAddnameStr = result.names;
						var alreadyAddnameStr = $("#span_usernames").text();
						var prepareAddnameList = prepareAddnameStr.split(',');
						var alreadyAddnameList = alreadyAddnameStr.split(',');
						if(isRepeatRecord(prepareAddnameList,alreadyAddnameList)){
				        	alert("不允许重复添加！");
				        	return ;
						}
						changeUserIdAndName(result.ids+",",result.names+",");
					}
				});
				$("#add_user").click(function(){
					var praData = {};
					praData["paramMap.userName"] = $("#userName").val();
					//判断不允许重复添加
					var prepareAddnameStr = $("#userName").val();
					var alreadyAddnameStr = $("#span_usernames").text();
					var prepareAddnameList = prepareAddnameStr.split(',');
					var alreadyAddnameList = alreadyAddnameStr.split(',');
			        if(isRepeatRecord(prepareAddnameList,alreadyAddnameList)){
			        	alert("不允许重复添加！");
			        	return ;
					}
					$.shovePost("queryUserIdByUserName.do",praData,function(data){
						if(data ==''){
							alert("请正确添加用户!");
							return false;
							}
						if(data != null && data != undefined){
							var ids = "";
							var names = ""; 
						
							for(var i = 0; i < data.length; i++){
								ids += (data[i].id+",");
								names += (data[i].username+",");
							}
							changeUserIdAndName(ids,names);
						}else{
							alert("请正确添加用户!");
							return false;
							}
					});
				});
				//changeActionUrl("addGroup.do","modifyGroup.do");
			});
			//判断两个数组是否有重复记录
			function isRepeatRecord(array1,array2){
				for (var i=0,iLen=array1.length; i<iLen; i++)
		        {
		            for (var j=0,jLen=array2.length; j<jLen; j++)
		            {
		                if (array1[i]==array2[j]&&array1[i]!=''&&array2[j]!='')
		                {
		                    return true;
		                }
		            }
		        }
		        return false;
			}
			function changeUserIdAndName(ids,names){
				var userId = $("#userId").val();
				if(userId == ""){
					$("#userId").val(ids);
				}else{
					$("#userId").val(userId+ids);
				}
				var usernames = $("#span_usernames").text();
				if(usernames == ""){
					$("#span_usernames").html(names);
				}else{
					$("#span_usernames").html(usernames+names);
				}
			}
			//修改或增加的URL
			/*function changeActionUrl(addAction,upateAction){
				var id = "${paramMap.id}";
				alert("id = " + id);
				if(id != null && id != ""){
					$("#editForm").attr("action",upateAction);
				}else{
					$("#editForm").attr("action",addAction);
				}
			}*/
		</script>

	</head>
	<body>
	<form id="editForm"  method="post" action="modifyGroup.do" >
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td width="100" height="28"  class="main_alll_h2">
									<a href="#">修改用户组</a>
								</td>
								<td>
									&nbsp;
								</td>
						</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									用户组名称:<s:hidden name="paramMap.groupId"></s:hidden>
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.groupName" theme="simple" readonly="true" 
										cssClass="in_text_2" cssStyle="width: 150px" > </s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap.groupName" />
									</span>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									备注:
								</td>
								<td align="left" class="f66">
									<s:textarea name="paramMap.groupRemark" theme="simple" rows="3" cols="60"></s:textarea>
									<span style="color: red;"><s:fielderror
											fieldName="paramMap.groupRemark" />
									</span>
									<s:hidden name="paramMap.id" ></s:hidden>
								</td>
							</tr>
							<!--add by houli 添加一个提现状态复选框 -->
							<tr>
							
								<td height="36" align="right" class="blue12">
									提现状态：
								</td>
								<td align="left" class="f66" >
								   <s:if test="paramMap.cashStatus == 1">
								      <input type="checkbox" id="w_status" checked="checked" name="paramMap.cashStatus"/>启用
								   </s:if>
								   <s:else>
								     <input type="checkbox" id="w_status" name="paramMap.cashStatus"/>启用
								   </s:else>
								</td>
							</tr>
							<!-- end -->
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									添加成员:
								</td>
								<td align="left" class="f66">
									<s:textfield name="userName" id="userName" theme="simple"  
										cssClass="in_text_2" cssStyle="width: 150px" > </s:textfield>
									<%--<input type="button" id="add_user" value="添加" /> 
									--%><input type="button" id="add_users" value="批量添加" />
									    <input type="button" value="注册" class="theme-login"/>
									<input id="userId" type="hidden" name="paramMap.userId" />
									<br/>
									<span id="span_usernames" style="display: block;width: 550px;word-wrap:break-word;"></span>
								</td>
							</tr>
							<tr>
								<td height="25">
								</td>
								<td align="left" class="f66" style="color: Red;">
									<s:fielderror fieldName="paramMap.allError" />
								</td>
							</tr>
							
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
                                <button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"  ></button>
                                &nbsp;<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp;
                                &nbsp;
                                <span style="color: red;">
                             	  <s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror>
                                </span>
                            </td>
							</tr>
						</table>
              </div>  
			<br />
			</div>
		</div>
		
		     
		    <ul id="ul_input" style="position:absolute; display:none; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:20%; top:50px;">
                 	
                    <li>
                    <input type="hidden" id="groupIds" value="${paramMap.groupId}"/>
                    <span class="s_login_txt">用户名：</span>
                    <input name="paramMap.userName"  id="userNameReg" type="text" class="input_a"/>
                    <span  style="color: red" id="s_userName" class="formtips"></span>
                    </li>
                    
                    <li>
                    <span class="s_login_txt">密码：</span>
                    <input name="paramMap.password" id="password" type="password" class="input_a" />
                    <span  style="color: red" id="s_password" class="formtips"></span>
                    </li>
                    
                    <li>
                       <span class="s_login_txt">确认密码：</span>
                       <input name="paramMap.confirmPassword" id="confirmPassword" type="password"  class="input_a"/>
                       <span  style="color: red;" id="s_confirmPassword" class="formtips"></span>
                    </li>
                    
                    <li>
                 	    <span class="s_login_txt">手机号：</span>
                 	    <input type="text" name="paramMap.cellphone" id="cellphone" class="input_a"/>
                 	    <span style="color: red" id="s_cellphone" class="formtips"></span>
                 	</li>
                 	 
                    <li>
                       <span class="s_login_txt">验证码：</span>
                       <input type="text" name="paramMap.code" id="code" class="input_a"/> 
                       <img src="${sitemap.adminUrl}/imageCode.do?pageId=cellphone" title="点击更换验证码" 
                       style="cursor: pointer;" id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
					   <span  style="color: red" id="pic_code" class="formtips"></span>
					</li>
                     
                	<li>
                	<span class="s_login_txt">&nbsp;</span>
                	<input id="btn_cellregs" type="button" value="注      册 "  onclick="regUerButon()"/>
                	</li>
                    <input type="button" class="close_btn" value="" />
                 </ul>
             
		</form>
<script type="text/javascript">
		jQuery(document).ready(function($) {
			$('.theme-login').click(function(){
				$('#ul_input').slideDown(200);
			}) 
			$('.close_btn').click(function(){
				$('#ul_input').slideUp(200);
			})
		})
  		//初始化验证码
		function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=cellphone&d="+timenow);
		}
		
		//注册
		function regUerButon(){
			   var states = "1";
			   if($("#userNameReg").val()==""){
			     	 $("#s_userName").attr("class", "formtips onError");
					 $("#s_userName").html("请填写用户名"); 
					 states = "0";
			   }else{
			        $("#s_userName").attr("class", "formtips");
					$("#s_userName").html(""); 
			   }
			   if($("#password").val()==""){
			     	 $("#s_password").attr("class", "formtips onError");
					 $("#s_password").html("请输入密码"); 
					 states = "0";
			   }else if($("#password")[0].value.length<6 || $("#password")[0].value.length>20){
				     $("#s_password").attr("class", "formtips onError");
					 $("#s_password").html("密码长度6到20个字符"); 
					 states = "0";
			   }else{
				    $("#s_password").attr("class", "formtips");
					$("#s_password").html(""); 
			   }
			   if( $("#confirmPassword").val()==""){
			     	 $("#s_confirmPassword").attr("class", "formtips onError");
					 $("#s_confirmPassword").html("请输入确认密码"); 
					 states = "0";
			   }else{
			        $("#s_confirmPassword").attr("class", "formtips");
					$("#s_confirmPassword").html(""); 
			   }
			   
			   if($("#cellphone").val()==""){
			         $("#s_cellphone").attr("class", "formtips onError");
					 $("#s_cellphone").html("请输入手机号码"); 
					 states = "0";
			   }else if((!/^1[3,5,8]\d{9}$/.test($("#cellphone").val()))){
			         $("#s_cellphone").attr("class", "formtips onError");
					 $("#s_cellphone").html("手机号码格式错误"); 
					 $("#cellphone").val("");
					 states = "0";
			   }else{
			         $("#s_cellphone").attr("class", "formtips");
					 $("#s_cellphone").html(""); 
			   }
			   if($("#code").val()==""){
			     	 $("#pic_code").attr("class", "formtips onError");
					 $("#pic_code").html("请填写验证码"); 
					 states = "0";
			   }else{
			        $("#pic_code").attr("class", "formtips");
					$("#pic_code").html(""); 
			   }
			    
			   if( states == "0"){
				   return;
			   }
			   
			   var param = {};
		       param["paramMap.cellphone"]= $("#cellphone").val();//手机号码
		       param["paramMap.code"] = $("#code").val();//图片验证码
		       param["paramMap.userName"] = $("#userNameReg").val();//用户名
		       param["paramMap.password"] = $("#password").val();//密码
		       param["paramMap.confirmPassword"] = $("#confirmPassword").val();//确认密码
		       param["groupId"] = $("#groupIds").val();//用户组主键
		        
		       $.shovePost("userGroupRegUser.do",param,function(data){
		    	 switchCode();
		         if(data.mailAddress=='1'){
		         
		            $("#s_confirmPassword").html("两次输入的密码不一致"); 
		          
		         }
		         
		         else if(data.mailAddress=='2'){
		        	 
					 $("#s_userName").html("用户名已存在"); 
		             
		          }
		         
		         else if(data.mailAddress=='14'){
		          
		            $("#s_password").html("请设置您的密码"); 
		            
		         }
		         else if(data.mailAddress=='15'){
		            
		           $("#s_confirmPassword").html("请再次输入密码确认"); 
		         
		         }
		         else if(data.mailAddress=='18'){
		           
		          $("#s_userName").html("用户名长度为2-20个字符"); 
		          
		         }
		         else if(data.mailAddress=='20'){
		         $("#s_userName").html("用户名不能含有特殊字符"); 
		           
		         }
		          else if(data.mailAddress=='21'){
		          $("#s_userName").html("用户名第一个字符不能是下划线"); 
		         }else if(data.mailAddress=='手机已存在'){
		           $("#s_cellphone").html("该手机号码已经注册了"); 
		           $.post("removecellCode.do","",function(data){});//删除手机验证码  
		         }
		         else if(data.mailAddress=='请填写手机校验码'){
		           $("#s_cellcode").html("请填写手机校验码");
		            
		         }
		         else if(data.mailAddress=='请发送手机校验码'){
		             $("#s_cellcode").html("请发送手机校验码");
		              
		           }
		         else if(data.mailAddress=='请输入正确的手机校验码'){
		             $("#s_cellcode").html("请输入正确的手机校验码");
		              
		           }
		         else if(data.mailAddress=='与获取验证码手机号不一致'){
		             $("#s_cellcode").html("与获取验证码手机号不一致");
		              
		           }
		         else if(data.mailAddress=='验证码输入错误'){
		             $("#pic_code").html("验证码输入错误");
		          }
		         else if(data.states=='1'){
		        	 alert("您好,已经存在高级用户！");
		          }
		         else if(data.mailAddress=='16'){
		             alert("注册失败");
		            }
		         else if(data.mailAddress=='注册成功'){
		          $.post("adminUserRemoveSessionCode.do","",function(data){
		        	    
		        	 
		        	  alert("恭喜你!注册成功");
		        	  window.location.reload();
		        	  $('#ul_input').slideUp(400);
		        	 
		          });//删除手机验证码
		         }
		       });
			  
		   }
</script>
	</body>
</html>