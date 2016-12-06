<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>更新内容</title>
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
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script>
			$(function(){
				$("#btn_save").click(function(){
							$("#updateTeam").submit();
					});
		    });
		</script>
	</head>
	<body>
	<s:hidden id="sort_" name="paramMap.sort"></s:hidden>
		<form id="updateTeam" name="updateTeam" action="updateSettingActivity.do" method="post">
			
			<s:hidden name="paramMap.id" value="%{#request.paramMap.id}"/>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									编码：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.code" cssClass="in_text_250" theme="simple" value="%{#request.paramMap.code}">
									</s:textfield>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									名称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.name" cssClass="in_text_250" theme="simple" value="%{#request.paramMap.name}">
									</s:textfield>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									描述：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_sort" name="paramMap.desc" cssClass="in_text_250" theme="simple" value="%{#request.paramMap.desc}">
									</s:textfield>
								</td>
							</tr>
					
					        <tr>
							    <td style="width: 100px; height: 25px;" align="right" class="blue12">
									总共金额：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_visist" name="paramMap.fee_total" cssClass="in_text_250" theme="simple" value="%{#request.paramMap.fee_total}"></s:textfield>
								</td>
							</tr>
							<tr>
							    <td style="width: 100px; height: 25px;" align="right" class="blue12">
									初始金额：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_visist" name="paramMap.fee_used" cssClass="in_text_250" theme="simple" value="%{#request.paramMap.fee_used}"></s:textfield>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									开始时间：
								</td>
								<td align="left" class="f66">
								    <input id="tb_Time" class="in_text_250" type="text"  value="${paramMap.start_time}" name="paramMap.start_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									结束时间：
								</td>
								<td align="left" class="f66">
								    <input id="tb_Time" class="in_text_250" type="text"  value="${paramMap.end_time}" name="paramMap.end_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
								</td>
							</tr>
							
							<tr>
							    <td style="width: 100px; height: 25px;" align="right" class="blue12">
									user_id：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_visist" name="paramMap.user_id" cssClass="in_text_250" theme="simple" value="%{#request.paramMap.user_id}">
									</s:textfield>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									活动阶段：
								</td>
								<td align="left" class="f66">
								    <input id="tb_process" class="in_text_250" type="text"  value="${paramMap.process}" name="paramMap.process" />
								</td>
							</tr>
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<input id="btn_save" type="button"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></input>
									&nbsp;
									<button id="previewButton" style="background-image: url('../images/admin/btn_yulan.jpg');width: 70px;border: 0;height: 26px" ></button>
                                   <span id="messageInfo" class="blue12"></span>
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
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
		</form>
	</body>
</html>
