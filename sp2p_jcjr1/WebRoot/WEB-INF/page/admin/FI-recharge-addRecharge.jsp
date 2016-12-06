<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>还款管理-充值管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
			   $("#tb_money").attr("value",'${paramMap.totalSum }');
			   $("#tb_realName").attr("value",'${paramMap.realName }');
			   $("#tb_userId").attr("value",'${paramMap.userId }');
			   
			   //提交表单
				$("#btn_save").click(function(){
					param['paramMap.type'] = '';
					initListInfo(param);
					return false;
				});
			});
			
			function initListInfo(praData){
				/* praData["paramMap.userName"] = $("#tb_userName").val();
				praData["paramMap.userId"] = $("#tb_userId").val();
				praData["paramMap.dealMoney"] = $("#tb_money").val();
				praData["paramMap.remark"] = $("#tb_remark").val(); */
		 		//$.shovePost("addRecharge.do",praData,initCallBack);
		 		window.location.href="addRecharge.do?paramMap.userName="+$("#tb_userName").val()+"&&paramMap.userId="+$("#tb_userId").val()+"&&paramMap.dealMoney="+$("#tb_money").val()+"&&paramMap.remark="+$("#tb_remark").val();
		 	}
			
			function cancel(){
			  $("#tb_remark").attr("value","");
			  $("#tb_money").attr("value","");
			  $("#remark_tip").html("");
			  $("#money_tip").html("");
			}
			
									
		</script>
	</head>
	<body>
	<form id="addRecharge" name="addRecharge" action="addRecharge.do" method="post">
	
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 120px; height: 35px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_userName" name="paramMap.userName"
										cssClass="in_text_2" cssStyle="width: 150px;height:20px;line-height: 20px;" theme="simple" readonly="true" ></s:textfield>
									<s:textfield id="tb_userId" name="paramMap.userId"
										cssClass="in_text_2" cssStyle="width: 150px;height:20px;display:none" 
										theme="simple" readonly="true" ></s:textfield>
										 	 
								</td>
							</tr>
							
							<tr>
								<td style="width: 120px; height: 35px;" align="right"
									class="blue12">
									真实姓名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_realName" name="paramMap.realName"
										cssClass="in_text_2" cssStyle="width: 150px;height:20px;line-height: 20px;" theme="simple" readonly="true" ></s:textfield>
								</td>
							</tr>
							
							<tr>
								<td style="width: 120px; height: 35px;" align="right" class="blue12">
									充值金额：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_money" name="paramMap.dealMoney"
										cssClass="in_text_2" cssStyle="width: 150px;height:20px;line-height: 20px;" theme="simple"></s:textfield>
									<span id="money_tip" class="require-field">*<s:fielderror fieldName="paramMap['dealMoney']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 35px;" align="right" class="blue12">
									充值类型：
								</td>
								<td align="left" class="f66">
								<input type="radio" name="paramMap.recharType" value="5" style="vertical-align:text-bottom; margin-bottom:-2px;" checked="checked"/>手工充值
							</tr>
							<tr>
								 <td  style="width: 120px; height: 35px;" align="right" class="blue12">
								    充值备注：
								    
										</td>
										<td>
										&nbsp;
										</td>
								 
							</tr>
							<tr>
							<td colspan="2" class="f66">
								 <s:textarea id="tb_remark" cssStyle="margin-left:80px;" name="paramMap.remark" value="" cols="30" rows="5"></s:textarea>
										<span id="remark_tip" class="require-field">*<s:fielderror fieldName="paramMap['remark']"></s:fielderror></span>
								 </td>
							</tr>
							
							<tr>
							<td colspan="2">
									<button id="btn_save" 
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									
									<button id="btn_cancel" onclick="cancel();"
										style="background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
							</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			</div>
			</form>
	</body>
</html>
