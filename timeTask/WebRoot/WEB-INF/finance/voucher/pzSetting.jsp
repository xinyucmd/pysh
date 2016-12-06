<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzSetting_zh_CN.js"></script>
		<title><s:i18n name="com.dx.finance.voucher.voucher">
				<s:text name="pz_setting"></s:text>
			</s:i18n>
		</title>
	</head>
	<body>
		<div>
			<s:form name="form01" action="pzSetting_savePzSetting.action" method="">
				<s:i18n name="com.dx.finance.voucher.voucher">
					<br><!--				 -->
					<div style="width:800px">
						<s:text name="auto_pz"></s:text>
						<input type="checkbox" id="a1" />
						<s:text name="allow_edit"></s:text>
						<input type="checkbox" id="a2" />
						<s:text name="allow_del"></s:text>
						<br />
						<s:text name="pz_flow_sp_setting"></s:text>
						<input type="checkbox" id="b1" />
						<s:text name="zhi_ding_ren_yuan"></s:text>
						<input type="checkbox" id="b2" />
						<s:text name="zhi_ding_gang_wei"></s:text>
					</div>
					<s:submit value="%{getText('save')}" onclick="save()"></s:submit>
					<input type="hidden" value="${stss}" id="sts" name="sts" />
				</s:i18n>
			</s:form>
		</div>
	</body>
</html>
