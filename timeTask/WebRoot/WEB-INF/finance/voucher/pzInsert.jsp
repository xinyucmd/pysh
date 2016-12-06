<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzInsert_zh_CN.js"></script>
		<title><s:text name="pzInsertTitle"></s:text></title>
	</head>
	<body>
		<s:form name="form01" action="pzInsert_pzAnalysis.action" theme="simple">

			<!--
			 -->
			<s:i18n name="com.dx.finance.voucher.voucher">
				<s:div>
					<s:text name="插入凭证"></s:text>
					<input type="button" value="分析" onclick="analysis()" />
					<input type="button" value="执行插入" onclick="insert()" />
					<input type="button" value="取消" onclick="cancel()" />
				</s:div>
				<br />
				<s:text name="参与分析的凭证"></s:text>
				<s:text name="会计周期："></s:text>
				<s:textfield name="year" id="year"></s:textfield>
				<s:text name="年"></s:text>
				<s:textfield name="term" id="term"></s:textfield>
				<s:text name="期"></s:text>
				<s:text name="凭证字："></s:text>
				<s:select list="pzZiList" name="pzType"></s:select>
				<s:text name="将上述期间的："></s:text>
				<s:textfield name="srcNo" id="srcNo"></s:textfield>
				<s:text name="号凭证插入到"></s:text>
				<s:textfield name="desNo" id="desNo"></s:textfield>
				<s:text name="前"></s:text>
				<br />
				<div>
					<s:text name="分析结果："></s:text>
					<s:text name="result"></s:text>
				</div>
			</s:i18n>
		</s:form>
	</body>
</html>