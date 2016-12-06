<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzQuery_zh_CN.js"></script>
		<title><s:text name="pzQueryTitle"></s:text>
		</title>
	</head>
	<body>
		<s:form name="form01" action="pzQuery_toPzQueryList.action" theme="simple">
			<!--
			 -->
			<s:i18n name="com.dx.finance.voucher.voucher">
				<s:text name="日期"></s:text>
				<s:textfield id="dateLeft" name="dateBetween[0]"></s:textfield>
				<s:text name="到"></s:text>
				<s:textfield id="dateRight" name="dateBetween[1]"></s:textfield>
				<br />
				<s:text name="凭证字"></s:text>
				<s:select list="pzZiList" name="pzZi"></s:select>
				<br />
				<s:text name="凭证号"></s:text>
				<s:textfield id="pzPidLeft" name="noteno[0]"></s:textfield>
				<s:text name="到"></s:text>
				<s:textfield id="pzPidRight" name="noteno[1]"></s:textfield>
				<br />
				<s:text name="状态"></s:text>
				<s:select list="flagList" name="flag"></s:select>
				<br />
				<s:text name="科目"></s:text>
				<s:textfield name="km" id="km"></s:textfield>
				<br />
				<s:text name="辅助核算"></s:text>
				<s:select list="itemsList" name="items"></s:select>
				<br />
				<s:text name="员工"></s:text>
				<s:textfield id="tel" name="tel"></s:textfield>
				<br />
				<s:text name="金额"></s:text>
				<s:textfield id="amtLeft" name="amt[0]"></s:textfield>
				<s:text name="到"></s:text>
				<s:textfield id="amtRight" name="amt[1]"></s:textfield>
				<br />
				<s:submit value="%{getText('ok')}" onclick="ok()"></s:submit>
				<s:reset value="%{getText('reset')}" onclick="reset()"></s:reset>
			</s:i18n>
		</s:form>
	</body>
</html>