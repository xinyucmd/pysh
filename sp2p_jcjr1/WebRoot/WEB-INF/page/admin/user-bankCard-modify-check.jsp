<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>帮助中心-内容维护-修改</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			
			$(function(){
				//提交表单
				$("#btn_save").click(function(){
					 
					var remark = $('#remark').val();
					if(remark=='' || remark==null){
						$('#remarkDiv').html('请填写审核意见！');
						return false;
					}else{
						$("#updateUserModifyBank").submit();
					}
					 
				});
			});
			
		</script>
		
	</head>
	<body>
		<!--  <form id="updateHelp" name="updateHelp" action="updateHelp.do" method="post"> -->
		<form id="updateUserModifyBank" name="updateUserModifyBank" action="updateUserModifyBank.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="120" height="28" class="xxk_all_a">
								<a href="queryUserBankInit.do">用户银行卡管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="120" class="main_alll_h2">
									<a href="queryModifyBankInit.do">用户银行卡变更</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="2">
									&nbsp;
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
								<td style="width: 120px; height: 25px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_username" name="paramMap.username"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									真实姓名：
								</td>
								<td align="left" class="f66">
								<s:textfield id="tb_realName" name="paramMap.realName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								 <td style="width: 120px; height: 25px;" align="right" class="blue12">
								    审核人：
										</td>
								 <td align="left" class="f66">
								 <s:select id="checkUser" list="checkers" name="paramMap.userName" listKey="id" listValue="userName"  />
										<span class="require-field">*<s:fielderror fieldName="paramMap['userName']"></s:fielderror></span>
								 
								 </td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									身份证：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_idNo" name="paramMap.idNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
										</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									原始银行卡号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_cardNo" name="paramMap.cardNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield></td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 修改后的开户行：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedBankName"></s:property>-->
									<s:textfield id="tb_modifiedBankName" name="paramMap.modifiedBankName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 修改后的开户支行：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedBranchBankName"></s:property>-->
									<s:textfield id="tb_modifiedBranchBankName" name="paramMap.modifiedBranchBankName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 修改后的银行卡号：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedCardNo"></s:property></td>-->
									<s:textfield id="tb_modifiedCardNo" name="paramMap.modifiedCardNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
								  <s:hidden  id="tb_modifiedTime" name="paramMap.modifiedTime"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										
										<s:hidden  id="tb_id" name="paramMap.bankId"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										</td>
							</tr>
							
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									银行卡变更证明：
								</td>
								<td align="left" class="f66">
								      <a href="../${bankCarApply.bank_card_path}" target="_blank" title="点击查看图片">
								        <img src="../${bankCarApply.bank_card_path}" alt="" height="100" width="300"/>
								      </a>
								</td>
							</tr>
							
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									申请人身份证件：
								</td>
								<td align="left" class="f66">
								   <a href="../${bankCarApply.identify_path}" target="_blank" title="点击查看图片">
								     <img src="../${bankCarApply.identify_path}" alt=""  height="100" width="300"/>
								   </a>
								</td>
							</tr>
							
							
							<tr>
								 <td style="width: 120px; height: 25px;" align="right" class="blue12">
								审核状态：
								</td>
								<td align="left" class="f66">
							       <s:radio id="radio1" name="paramMap.status" list="#{1:'审核通过',3:'审核不通过'}" value="3"></s:radio>
								 <span class="require-field">*<s:fielderror fieldName="paramMap['status']"></s:fielderror></span>
								 
								 </td>
								</tr>
								<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
								  审核意见：
								 </td>
								 <td align="left" class="f66">
								      <s:textarea id="remark" cssStyle="margin-left:8px;" name="paramMap.remark" value="" cols="30" rows="5"></s:textarea>
								<span id="remarkDiv" class="require-field">*<s:fielderror fieldName="paramMap['remark']"></s:fielderror></span>
								 
								</td>
								</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
