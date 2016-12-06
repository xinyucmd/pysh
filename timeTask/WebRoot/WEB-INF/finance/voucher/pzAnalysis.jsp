<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/pzAnalysis_zh_CN.js"></script>
		<title><s:text name="pzQueryTitle"></s:text></title>
	</head>
	<body>
		<s:form name="form01" action="pzCheck_pzAnalysis.action"
			theme="simple">

			<!--
			 -->
			<s:i18n name="com.dx.finance.voucher.voucher">
				<s:div>
					<s:text name="重整凭证编号向导"></s:text>
					<input type="button" value="上一步" onclick="pre()" />
					<input type="button" value="分析" onclick="analysis()" />
					<input type="button" value="下一步" onclick="next()" />
					<input type="button" value="取消" onclick="cancel()" />
				</s:div>
				<br />
				<s:text name="1.检查断号"></s:text>
				<s:text name="→"></s:text>
				<s:text name="2.整理分析"></s:text>
				<s:text name="→"></s:text>
				<s:text name="3.整理"></s:text>
				<br />
				<s:div>
					<s:text name="整理方式"></s:text>
					<br />
					<s:radio list="sortTypeList" name="sortType"></s:radio>
					<br />
				</s:div>
				<div>
					<s:text name="分析结果："></s:text>
					<table>
						<tr>
							<td>
								原凭证
							</td>
							<td>
								整理后的凭证号
							</td>
						</tr>
						<tr>
							<td>
								<s:text name="old"></s:text>
							</td>
							<td>
								<s:text name="new"></s:text>
							</td>
						</tr>
					</table>
					<div>
			</s:i18n>
		</s:form>
	</body>
</html>