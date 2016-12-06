<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzFlowSetting_zh_CN.js"></script>
		<title><s:i18n name="com.dx.finance.voucher.voucher">
				<s:text name="pzFlowSettingTitle"></s:text>
			</s:i18n></title>
	</head>
	<body>
		<div>
			<s:form name="form01" action="pzFlow_savePzFlow.action">
				<s:i18n name="com.dx.finance.voucher.voucher">
					<div style="width: 800px">
						<s:text name="pzFlowSettingTitle"></s:text>
					</div>
					<div style="width: 800px">
						<!--

					 -->
						<input type="checkbox" checked disabled="disabled" />
						<s:text name="zhi_dan"></s:text>
						<input type="checkbox" id="c1" />
						<s:text name="shen_he"></s:text>
						<input type="checkbox" id="c2" />
						<s:text name="chu_na"></s:text>
						<input type="checkbox" id="c3" />
						<s:text name="kuai_ji"></s:text>
						<input type="checkbox" checked disabled="disabled" />
						<s:text name="ji_zhang"></s:text>
						<input type="checkbox" checked disabled="disabled" />
						<s:text name="guo_zhang"></s:text>
						<input type="checkbox" checked disabled="disabled" />
						<s:text name="jie_zhang"></s:text>
						<input type="hidden" value="${flownosss}" id="flowno" name="flowno" />
					</div>
					<div style="width: 800px">
						<input type="button" value="<s:text name="save"></s:text>"
							onclick="save()">
						<input type="button" value="<s:text name="cancel"></s:text>"
							onclick="cancel();" />
					</div>
				</s:i18n>
			</s:form>
		</div>
	</body>
</html>
