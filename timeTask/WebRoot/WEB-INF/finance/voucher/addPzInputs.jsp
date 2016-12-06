<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../common.jsp"%>
		<script src="<%=path%>/js/finance/voucher/addPzZi_zh_CN.js"></script>
		<title><s:i18n name="com.dx.finance.voucher.voucher">
				<s:text name="addPzInputsTitle"></s:text>
			</s:i18n></title>
	</head>
	<body>
		<s:form name="form01" action="pzInputs_savePzInputs.action"
			theme="simple">
			<s:i18n name="com.dx.finance.voucher.voucher">

			</s:i18n>
			<s:text name="ptitle">
			</s:text>
			<br />
			<s:select list="pzQzList" name="pzQz" keyList="key" valueList="value"></s:select>
			<s:text name="凭证字号"></s:text>
			<s:select list="noteNoList" label="凭证字号" name="noteNo"></s:select>
			<s:text name="附单据"></s:text>
			<s:select list="fuDanJuList" label="附单据" name="fuDanJu"></s:select>
			<s:text name="日期"></s:text>
			<s:textfield label="日期" name="txtime"></s:textfield>
			<table id="table1">
				<td>
					<s:text name="操作"></s:text>
				</td>
				<td>
					<s:text name="摘要"></s:text>
				</td>
				<td>
					<s:text name="会计科目"></s:text>
				</td>
				<td>
					<s:text name="借方金额"></s:text>
				</td>
				<td>
					<s:text name="贷方金额"></s:text>
				</td>

				</th>
				<tr>
					<td>
						<s:a onclick="alert('增加一行')">
						加
						</s:a>
						<s:a onclick="alert('减少一行')">
						减
						</s:a>
					</td>
					<!--
						 -->
					<td>
						<s:textfield name="remark"></s:textfield>
					</td>

					<td>
						<s:textfield name="kjKm" ></s:textfield>
					</td>

					<td>
						<s:textfield name="debit" ></s:textfield>
					</td>
					<td>
						<s:textfield name="credit"></s:textfield>
					</td>

				</tr>
				<tr>
					<td>
						<s:a onclick="alert('增加一行')">
						加
						</s:a>
						<s:a onclick="alert('减少一行')">
						减
						</s:a>
					</td>
					<!--
						 -->
					<td>
						<s:textfield name="remark"></s:textfield>
					</td>

					<td>
						<s:textfield name="kjKm"></s:textfield>
					</td>

					<td>
						<s:textfield name="debit"></s:textfield>
					</td>
					<td>
						<s:textfield name="credit" ></s:textfield>
					</td>

				</tr>
			</table>
			<s:text name="会计主管"></s:text>
			<s:textfield name="kjZg" id="kjZg"></s:textfield>
			<s:text name="记账"></s:text>
			<s:textfield name="jz" id="jz"></s:textfield>
			<s:text name="出纳"></s:text>
			<s:textfield name="cn" id="cn"></s:textfield>
			<s:text name="审核"></s:text>
			<s:textfield name="sh" id="sh"></s:textfield>
			<s:text name="制单"></s:text>
			<s:textfield name="zd" id="zd"></s:textfield>
			<s:text name="经办"></s:text>
			<s:textfield name="jb" id="jb"></s:textfield>
			<br />
			<s:submit onclick=""></s:submit>
		</s:form>
	</body>
</html>