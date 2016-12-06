<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzStepctl_zh_CN.js"></script>
		<title><s:i18n name="com.dx.finance.voucher.voucher">
				<s:text name="pzStepctlTitle"></s:text>
			</s:i18n></title>
	</head>
	<body>
		<s:form name="form01" action="pzStepctl_savePzStepctl.action">
			<s:i18n name="com.dx.finance.voucher.voucher">
				<div style="width: 800px">
					<s:select name="stepSelect" id="stepSelect" list="steps" label="岗位" onchange="selectChange()">
					</s:select>
					<s:textfield name="tels" id="tels" label="审批人员"></s:textfield>
				</div>

				<div style="width: 800px">
					<s:submit value="%{getText('save')}" onclick="save()"></s:submit>
					<s:reset value="%{getText('cancel')}" onclick="cancel()"></s:reset>
					<!-- 
					<input type="button" value="<s:text name="save"></s:text>" onclick="save()"/>
					<input type="button" value="<s:text name="cancel"></s:text>" onclick="cancel()"/>
				 	-->
				</div>
			</s:i18n>
			<input type="hidden" name="temp" id="temp" value="${tempV}" />
		</s:form>
	</body>
</html>
