<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzCheck_zh_CN.js"></script>
		<title><s:text name="pzQueryTitle"></s:text>
		</title>
	</head>
	<body>
		<s:form name="form01" action="pzCheck_pzCheck.action" theme="simple">
		
			<!--
			 -->
			<s:i18n name="com.dx.finance.voucher.voucher">
			<s:div>
			<s:text name="重整凭证编号向导"></s:text>
			<input type="button" value="检查断号" onclick="check()"/>
			<input type="button" value="下一步" onclick="next()"/>
			<input type="button" value="取消" onclick="cancel()"/>
			<br/>
			<s:text name="1.检查断号"></s:text>
			<s:text name="→"></s:text>
			<s:text name="2.整理分析"></s:text>
			<s:text name="→"></s:text>
			<s:text name="3.整理"></s:text>
			<br/>
			</s:div>
			<s:div>
			<s:text name="参与凭证整理的范围"></s:text>
			<s:text name="会计周期"></s:text>
			<s:textfield name="year" id="year"></s:textfield>
			<s:text name="年"></s:text>
			<s:textfield name="term" id="term"></s:textfield>
			<s:text name="期"></s:text>
			<br/>
			<s:text name="凭证字"></s:text>
			<s:select list="pzZiList" name="pzType"></s:select>
			<br/>
			<s:text name="起始凭证号"></s:text>
			<s:textfield name="noteno" id="noteno"></s:textfield>
			<br/>
			</s:div>
			<s:div>
			<s:text name="断号检查结果："></s:text>
			<s:text name="result"></s:text>
			</s:div>
			</s:i18n>
		</s:form>
	</body>
</html>