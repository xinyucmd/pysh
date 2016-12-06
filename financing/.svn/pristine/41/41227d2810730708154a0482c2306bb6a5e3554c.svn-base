<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<s:action name="select" id="select" namespace="/jsp/select"></s:action>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">

	function submit() {
			
		if ($("#addObjectForm").form("validate")) {
			$.messager.confirm("提交确认", "您确认撤销此理财吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'undo.action';
					addObjectForm.submit();
					baseCancel("Undo");
				}
			});
		}
	}
	 function cancel(){
		 baseCancel("Undo");
	 }

	 
</script>
</head>

<body>
	<div id="page-wrap">

		<s:include value="../header.jsp" />
		<s:include value="../menu.jsp" />
		<div id="feature">

			<div id="shortcuts" class="headerList">

				<!-- saveClose -->
				<span style="white-space: nowrap;"> <a id="save_go_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-save-go"
					onclick="submit()" plain="true"> <s:text name="confirm.submit" />
				</a>
				</span>
				<!-- saveClose -->

				<!-- cancel -->
				<span style="white-space: nowrap;"> <a id="cancel_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="cancel()" plain="true"> <s:text name="button.cancel" />
				</a>
				</span>
			</div>
			<!---->

			<div id="feature-content">
				<h2>
					<s:text name="entity.chexiaoLicai" />
				</h2>
			</div>

			<!-- 表单 -->
			<div id="feature-content">
				<s:form id="addObjectForm" validate="true"
					namespace="/jsp/subscription" method="post">
					<s:hidden id="prdtName" name="pactInfo.prdt_name"
						value="%{pactInfo.prdt_name}" />
					<s:hidden id="pact_no" name="pactInfo.pact_no" value="%{pactInfo.pact_no}" />
					<s:hidden id="id" name="pactInfo.id" value="%{pactInfo.id}" />
					<s:hidden id="pact_amt" name="pactInfo.pact_amt" value="%{pactInfo.pact_amt}" />
					<s:hidden id="rate" name="pactInfo.rate" value="%{pactInfo.rate}" />
					<s:hidden id="term_range" name="pactInfo.term_range" value="%{pactInfo.term_range}" />
					


					<div id="tt" class="easyui-tabs">
						<div title="<s:text name='menu.customer.infomation'/>" style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="menu.base.information" /></span>
							</div>
							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="customer.otherContact.custname"></s:text>：</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="customer.cif_name" />
									</label></td>



									<td class="td-label"><label class="record-label">
											<s:text name="customer.sex.label"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
									<s:select id="sex"
											list="#select.queryWPD('sex')" listKey="opCode"
											listValue="opCnName" name="customer.sex"
											value="%{customer.sex}"></s:select>
									</span>
											</td>
                                  

								</tr>

								<tr>
									<!--productName  -->

									<td class="td-label"><label class="record-label">
											<s:text name="customer.idType.label"></s:text>:
									</label></td>
									<td class="td-value">
									 <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select id="idType"
											list="#select.queryWPD('ID_TYPE')" listKey="opCode"
											listValue="opCnName" name="customer.id_type"
											value="%{customer.idType}"></s:select>
											</span></td>
									<!-- standardAMT -->

									<td class="td-label"><label class="record-label">
											<s:text name="customer.idNo.label"></s:text>:
									</label></td>


									<td class="td-value"><label class="record-value">
											<s:property value="customer.id_no" />
									</label></td>


								</tr>
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="customer.cifType.label"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select id="cifType"
											list="#select.queryWPD('CUS_TYPE')" listKey="opCode"
											listValue="opCnName" name="customer.cifType"
											value="%{customer.cifType}"></s:select>
											</span></td>
								</tr>
							</table>
							<div class="section-header">
								<span><s:text name="customer.contact.label" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="customer.contact.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.contact" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="customer.contactPhone.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.contact_phone" />
									</label></td>

								</tr>
								<tr>

									<td class="td-label"><label class="record-label">
											<s:text name="customer.mail.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.mail" />
									</label></td>

								</tr>



								<tr>

								</tr>



							</table>

							</table>
							<div class="section-header">
								<span><s:text name="地址" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="customer.address.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.addr" />
									</label></td>

									<td class="td-label"></td>
									<td class="td-value"></td>

								</tr>




							</table>

							<div class="section-header">
								<span><s:text name="提成人" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.meAdult"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.me_adult" />
									</label></td>

									<td class="td-label"></td>
									<td class="td-value"></td>

								</tr>




							</table>
						</div>

						<div title="<s:text name='pactInfo.inflomation'/>" style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="pactInfo.cifNo" /></span>
							</div>
						<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>


									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.cifNo"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.pact_no" />
									</label></td>


									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.claimsPactNo"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.claims_pact_no" />
									</label></td>

								</tr>
						</table>
						
						
						
							<div class="section-header">
								<span><s:text name="entity.prod.information" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>


									<td class="td-label"><label class="record-label">
											<s:text name="entity.prod.name"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.prdt_name" />
									</label></td>


									<td class="td-label"><label class="record-label">
											<s:text name="entity.prod.code"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.prdt_no" />
									</label></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.term_range2"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.term_range" /> <label
											class="record-label"> <s:text name="期"></s:text>
										</label>

									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.rate"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.rate" /> <label
											class="record-label"> % </label>
									</label></td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="entity.pactInfo.label" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.pactAmt"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.pact_amt" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.cashAmt"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.cash_amt" />
									</label></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.incomeAmt"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.income_amt" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.fundSources"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select id="cifType"
											list="#select.queryWPD('FUNDS_FROM')" listKey="opCode"
											listValue="opCnName" name="pactInfo.fund_sources"></s:select></span>
									</td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.paymentType"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select id=""
											list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode"
											listValue="opCnName" name="pactInfo.payment_type"></s:select></span>
									</td>


								</tr>


								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.ifContinue"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select
											list="#select.queryWPD('YES_NO')" listKey="opCode"
											name="pactInfo.if_continue" value="pactInfo.if_continue"
											listValue="opCnName"></s:select></span></td>

									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.ifWxd"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select
											list="#select.queryWPD('YES_NO')" listKey="opCode"
											name="pactInfo.if_wxd" value="pactInfo.if_wxd"
											listValue="opCnName"></s:select></span></td>
								</tr>
								
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="entity.start_date.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value"><s:property value="pactInfo.start_date" /></label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="entity.end_date.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value"><s:property value="pactInfo.end_date" /></label></td>
								</tr>

							</table>


							<div class="section-header">
								<span><s:text name="main.account.bankinformation" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="menu.bankAccount.name"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_name" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="menu.bankAccount.no"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_no" />
									</label></td>
								</tr>

								<tr>
									<td class="td-label" rowspan=”2″><label
										class="record-label"> <s:text name="menu.bankAccount.op_bank"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_bank" />
									</label></td>

									<td class="td-label"><label class="record-label">

									</label></td>
									<td class="td-value"></td>
								</tr>



							</table>


						</div>
						

						


					</div>

				</s:form>
			</div>

			<s:include value="../footer.jsp" />
		</div>
</body>
</html>



