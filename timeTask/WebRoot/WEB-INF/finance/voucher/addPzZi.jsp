<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/addPzZi_zh_CN.js"></script>
		<title><s:i18n name="com.dx.finance.voucher.voucher">
				<s:text name="pzZiTitle"></s:text>
			</s:i18n></title>
	</head>
	<body>
		<s:form name="form01" action="pzZi_savePzZi.action">
			<s:i18n name="com.dx.finance.voucher.voucher">
				<div style="width: 800px">
					<!--
				 -->
					<s:textfield name="ptitle" label="凭证打印标题"></s:textfield>
					<s:textfield name="pzQz" label="编码前缀"></s:textfield>
					<s:radio name="isAuto" list="#{1:'是',2:'否'}" label="是否默认"></s:radio>
					<!-- 
					<s:hidden name="isAuto" value=""></s:hidden>
					<s:hidden name="codeType" value=""></s:hidden>
					 -->
					<s:textfield name="traceNo" label="起始流水号"></s:textfield>


					<s:radio name="codeType" list="#{1:'流水号',2:'年编号',3:'月编号',4:'日编号'}"
						label="是否默认"></s:radio>

					<s:select name="xzType" id="xzTypeSelect" list="#{1:'请选择',2:'借方必有',3:'贷方必有',4:'借贷'}"  label="限制类型" onchange="" value="1">
					</s:select>
					<s:textfield name="xzKm" id="xzKm" label="限制科目"></s:textfield>
					<s:div label="预览" id="yu_lan"></s:div>
				</div>

				<div style="width: 800px">
					<s:submit value="%{getText('save')}" onclick="save()"></s:submit>
					<s:reset value="%{getText('cancel')}" onclick="cancel()"></s:reset>
				</div>
			</s:i18n>
			<input type="hidden" name="pid" value="${pidd }" />
		</s:form>
	</body>
</html>