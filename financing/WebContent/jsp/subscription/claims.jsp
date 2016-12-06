<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<s:action name="select" id="select" namespace="/jsp/select"></s:action>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<meta http-equiv="X-UA-Compatible" content="IE=9,IE=8">
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
	function countChar(textareaName, spanName) {
		document.getElementById(spanName).innerHTML = 200 - document
				.getElementById(textareaName).value.length;
	}

	function save() {
		if ($("#addObjectForm").form("validate")) {

			$.messager.confirm("保存确认", "您确认保存当前信息吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'saveSubscription.action';
					addObjectForm.submit();

					baseCancel("subscrip");

				}
			});

		}

	}

	function submit() {
		collectData() ;
		if ($("#addObjectForm").form("validate")) {

			$.messager.confirm("提交确认", "您确认提交吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'submitClaims.action';
					addObjectForm.submit();

				}
			});
		}

	}

	function fail() {
		$.messager.confirm("提交确认", "匹配失败流程就会关闭，是否继续？", function(saveAction) {
			if (saveAction) {
				var addObjectForm = document.getElementById('addObjectForm');
				addObjectForm.action = 'failClaims.action';
				addObjectForm.submit();

			}
		});

	}

	function selectQS() {
		var g = $('#proID').combogrid('grid'); // 获取表格控件对象
		var r = g.datagrid('getSelected'); //获取表格当前选中行 
		var qishu = $('#prodcpid').val();

		var params = {
			prodNo : r.prdtNo,
			qishu : qishu
		};

		$.ajax({
			type : "POST",
			url : "getRate.action",
			data : params,
			dataType : "text",
			success : function(json) {
				var jsonObj = eval("(" + json + ")");
				$.each(jsonObj, function(i, item) {
					$("#rate").val(item.rate);
				});
			},
			error : function(json) {
				alert("json=" + json);
				return false;
			}
		});
	}
	
	function cancel() {
		baseCancel("Claims");
	}

	function keydown() {
		$("#pact_amt").val($("#cash_amt").val());

	}

	$(document)
			.ready(
					function() {

						var merPactNo = '<s:property value="pactInfo.pact_no"/>';

						$("#pactInfo")
								.datagrid(
										{
											url : 'listMerPact.action?merPactNo='
													+ merPactNo,
											isField : "id",
											pagination : true,
											border : false,
											pagesize : 5,
											pageList : [ 5, 10, 15 ],
											title : "到期续购合同信息",
											rownumbers : true,
											singleSelect : false,
											columns : [ [

													{
														field : 'pactNo',
														title : '<s:text name="customer.pactNo" />',
														width : 100
													},
													{
														field : 'cifName',
														title : '<s:text name="customer.cifName.label" />',
														width : 100
													},
													{
														field : 'idNo',
														title : '<s:text name="customer.idNo.label" />',
														width : 120
													},
													{
														field : 'prot',
														title : '<s:text name="entity.prod.name" />',
														width : 100
													},
													{
														field : 'pact_amt',
														title : '<s:text name="pactInfo.pactAmt" />',
														width : 100
													},
													{
														field : 'term_range',
														title : '<s:text name="pactInfo.term_range" />',
														width : 100
													},
													{
														field : 'rate',
														title : '<s:text name="pactInfo.rate" />',
														width : 100
													},
													{
														field : 'incomeAmt',
														title : '<s:text name="pactInfo.incomeAmt" />',
														width : 100
													},
													{
														field : 'redemAmt',
														title : '<s:text name="RedeemEntity.redemType" />',
														width : 100
													},
													{
														field : 'continueAmt',
														title : '<s:text name="redeemEntity.surplusAmt" />',
														width : 100
													},
													{
														field : 'startDate',
														title : '<s:text name="pactInfo.startDate" />',
														width : 100
													},
													{
														field : 'endDate',
														title : '<s:text name="pactInfo.endDate" />',
														width : 100
													}

											] ]

										});

						$('#cusID').combogrid('setValue',
								'<s:property value="cusID"/>');

						$('#cusID').combogrid('setText',
								'<s:property value="cusName"/>');

						$("#cusID").combogrid({

							onSelect : function(n, o) {

								var g = $('#cusID').combogrid('grid'); // 获取表格控件对象
								var r = g.datagrid('getSelected'); //获取表格当前选中行 

								$("#cosid").val(r.id);

								$("#phone").val(r.contactPhone);
								$("#idNo").val(r.idNo);
								$("#emal").val(r.mail);
								$("#cifType").val(r.cifType);
								$("#idType").val(r.idType);
								$("#contact").val(r.contact);
								$("#sex").val(r.sex);
								$("#cifName").val(r.cifName);
								$("#birth").val(r.birth);

								$("#contactTel").val(r.contactTel);
								$("#addr").val(r.addr);

								$("#postcode").val(r.postcode);
								$("#openDate").val(r.openDate);
								$("#openOp").val(r.openOp);
								$("#cmt").val(r.cmt);

								// $(function(){ 

								//把min改为20,原来的值是0 

								//   $("#num").numberbox({ 

								//       min:20 ,
								//     missingMessage: r.name	
								//  }); 

								// });

							}

						});

						$('#proID').combogrid('setValue',
								'<s:property value="prdtNo"/>');

						$('#proID').combogrid('setText',
								'<s:property value="prdtName"/>');

						$("#proID")
								.combogrid(
										{

											onSelect : function(n, o) {

												var g = $('#proID').combogrid(
														'grid'); // 获取表格控件对象
												var r = g
														.datagrid('getSelected'); //获取表格当前选中行 

												$("#prdt_no").val(r.prdtNo);
												$("#prdtName").val(r.prdtName);

												var params = {
													prodNo : r.prdtNo
												};

												$
														.ajax({
															type : "POST",
															url : "getInstallments.action",
															data : params,
															dataType : "text",
															success : function(
																	json) {
																jQuery(
																		"#prodcpid")
																		.empty();
																jQuery(
																		"#prodcpid")
																		.append(
																				"<option value=0>请选择</option>");
																var jsonObj = eval("("
																		+ json
																		+ ")");

																$
																		.each(
																				jsonObj,
																				function(
																						i,
																						item) {
																					jQuery(
																							"#prodcpid")
																							.append(
																									"<option value="+ item.stageMax+">"
																											+ item.stageMax
																											+ "</option>");
																				});

															},
															error : function(
																	json) {
																alert("json="
																		+ json);
																return false;
															}
														});

												$(function() {

													$("#pact_amt")
															.numberbox(
																	{

																		min : parseInt(r.standardAmt),
																		missingMessage : "起购金额"
																				+ r.standardAmt
																	});

													$("#cash_amt")
															.numberbox(
																	{

																		min : parseInt(r.standardAmt),
																		missingMessage : "起购金额"
																				+ r.standardAmt
																	});

												});

											}

										});

						var params = {
							prodNo : '<s:property value="prdtNo"/>'
						};

						$
								.ajax({
									type : "POST",
									url : "getInstallments.action",
									data : params,
									dataType : "text",
									success : function(json) {
										jQuery("#prodcpid").empty();
										jQuery("#prodcpid").append(
												"<option value=0>请选择</option>");
										var jsonObj = eval("(" + json + ")");

										$
												.each(
														jsonObj,
														function(i, item) {
															if (item.stageMax == '<s:property value="pactInfo.term_range"/>') {
																jQuery(
																		"#prodcpid")
																		.append(
																				"<option value="+ item.stageMax+"  selected='true'>"
																						+ item.stageMax
																						+ "</option>");
															} else {
																jQuery(
																		"#prodcpid")
																		.append(
																				"<option value="+ item.stageMax+">"
																						+ item.stageMax
																						+ "</option>");
															}

														});

									},
									error : function(json) {
										alert("json=" + json);
										return false;
									}
								});

					});
	
	
	var k = 1;
	function addFeeRate() {
	k++;
	r = feerateTable.insertRow();
	r.setAttribute("id", "fR" + k);
	c = r.insertCell();
	c.innerHTML ="<label class='record-label' style='float:right;'>  <s:text	name='pactInfo.claimsPactNo'></s:text>:	</label>";
	
	c = r.insertCell();
	c.innerHTML ="<input name='claims_no'	class='easyui-validatebox' required='true' missingMessage='不能为空'/>";
	
	c = r.insertCell();
	c.innerHTML ="<label class='record-label' style='float:right;'>  <s:text	name='债务人姓名/名称'></s:text>:		</label>";
	
	c = r.insertCell();
	c.innerHTML = "<input name='claims_name'	class='easyui-validatebox' required='true' missingMessage='不能为空'/>";
	
	
	c = r.insertCell();
	c.innerHTML ="<label class='record-label' style='float:right;'> <s:text	name='单项债权本金金额'></s:text>:		</label>";
	
	c = r.insertCell();
	c.innerHTML = "<input name='claims_amt'	class='easyui-validatebox' required='true' missingMessage='不能为空' />";
	
	c = r.insertCell();
	c.innerHTML = "<a href='#' class='easyui-linkbutton' iconCls='icon-remove' onclick='removeFeeRate(this)' plain='true' ><s:text	name='删除'/></a>";

	}
	function removeFeeRate(r) {
	feerateTable.deleteRow(r.parentNode.parentNode.rowIndex);
	}
	function collectData() {
		
		var c = "";
		for (index = 0; index < document.getElementsByName("claims_no").length; index++) {
			c +=document.getElementsByName("claims_no")[index].value + "#"
				+document.getElementsByName("claims_name")[index].value + "#"
					+document.getElementsByName("claims_amt")[index].value + "|";
		}
		document.getElementById("multiClaims").value = c;
		
		
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
					onclick="submit()" plain="true"> <s:text name="匹配成功" />
				</a>
				</span>
				<!-- saveClose -->
				<span style="white-space: nowrap;"> <a id="save_go_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-save-go"
					onclick="fail()" plain="true"> <s:text name="匹配失败" />
				</a>
				</span>
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
					<s:text name="债权匹配" />
				</h2>
			</div>

			<!-- 表单 -->
			<div id="feature-content">
				<s:form id="addObjectForm" validate="true"
					namespace="/jsp/subscription" method="post">
<input type="hidden" name="multiClaims" id="multiClaims" />
					<s:hidden id="cifName" name="customer.cif_name"
						value="%{customer.cif_name}" />
					<s:hidden id="birth" name="customer.birth"
						value="%{customer.birth}" />
					<s:hidden id="contactTel" name="customer.contact_tel"
						value="%{customer.contact_tel}" />
					<s:hidden id="addr" name="customer.addr" value="%{customer.addr}" />
					<s:hidden id="postcode" name="customer.postcode"
						value="%{customer.postcode}" />
					<s:hidden id="openDate" name="customer.open_date"
						value="%{customer.open_date}" />
					<s:hidden id="openOp" name="customer.open_op"
						value="%{customer.open_op}" />
					<s:hidden id="cmt" name="cmt" value="%{claims_cmt}" />

					<s:hidden id="cosid" name="customer.id" value="%{customer.id}" />

					<s:hidden id="prdtName" name="pactInfo.prdt_name"
						value="%{pactInfo.prdt_name}" />
					<s:hidden id="" name="pactInfo.pact_no" value="%{pactInfo.pact_no}" />
					
				
					<s:hidden id="" name="pactInfo.id" value="%{pactInfo.id}" />
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" id="feerateTable" name="feerateTable">

						<tr>

							<td ><label class="record-label" style="float:right;">  <s:text
										name="pactInfo.claimsPactNo"></s:text>:
							</label></td>
							<td ><input name="claims_no"
								class="easyui-validatebox" required="true" missingMessage="不能为空" />
							</td>

							<td ><label class="record-label" style="float:right;">  <s:text
										name="债务人姓名/名称"></s:text>:
							</label></td>
							<td ><input name="claims_name"
								class="easyui-validatebox" required="true" missingMessage="不能为空" />
							</td>
							<td ><label class="record-label" style="float:right;"> <s:text
										name="单项债权本金金额"></s:text>:
							</label></td>
							<td ><input name="claims_amt"
								class="easyui-validatebox" required="true" missingMessage="不能为空" />
							</td>
							<td><a href="javascript:void(0)" onclick="addFeeRate()" 
								>增加</a></td>
						</tr>
					</table>

					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						<tr>
							<td class="td-label"  ><label class="record-label" style="float:right;"> <s:text name="customer.cmt.label"></s:text>：
							</label></td>
							<td class="td-value"  ><s:textarea id="claimCmt"
									name="claims_cmt" rows="5" cssStyle="width:350px;"
									cssClass="record-value" maxlength="200"
									onkeydown='countChar("claimCmt","counter");'
									onkeyup='countChar("claimCmt","counter");' /> <br />最多输入200字，还可以输入<span
								id="counter">200</span>字</td>
							<td class="td-mass-update"></td>

							<td class="td-label"></td>
							<td class="td-value"></td>

						</tr>
					</table>


					<div id="tt" class="easyui-tabs">
						<div title="<s:text name='menu.customer.infomation'/>"
							style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="menu.base.information" /></span>
							</div>
							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="customer.cifName.label"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="customer.cif_name" />
									</label></td>



									<td class="td-label"><label class="record-label">
											<s:text name="customer.sex.label"></s:text>:
									</label></td>
									<td class="td-value"><span
										onmousemove="this.setCapture();"
										onmouseout="this.releaseCapture();" onfocus="this.blur();">
											<s:select id="sex" list="#select.queryWPD('sex')"
												listKey="opCode" listValue="opCnName" name="customer.sex"
												value="%{customer.sex}"></s:select>
									</span></td>


								</tr>

								<tr>
									<!--productName  -->

									<td class="td-label"><label class="record-label">
											<s:text name="customer.idType.label"></s:text>:
									</label></td>
									<td class="td-value"><span
										onmousemove="this.setCapture();"
										onmouseout="this.releaseCapture();" onfocus="this.blur();">
											<s:select id="idType" list="#select.queryWPD('ID_TYPE')"
												listKey="opCode" listValue="opCnName"
												name="customer.id_type" value="%{customer.id_type}"></s:select>
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
									<td class="td-value"><span
										onmousemove="this.setCapture();"
										onmouseout="this.releaseCapture();" onfocus="this.blur();">
											<s:select id="cifType" list="#select.queryWPD('CUS_TYPE')"
												listKey="opCode" listValue="opCnName"
												name="customer.cif_type" value="%{customer.cif_type}"></s:select>
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
						<span><s:text name="customer.address.label" /></span>
					</div>

					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" readonly="true">
						<tr>
							<td class="td-label"><label class="record-label"> <s:text
										name="customer.address.label"></s:text>:
							</label></td>
							<td class="td-value"><label class="record-value"> <s:property
										value="customer.addr" />
							</label></td>

							<td class="td-label"></td>
							<td class="td-value"></td>

						</tr>




					</table>

					<div class="section-header">
						<span><s:text name="pactInfo.meAdult" /></span>
					</div>

					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" readonly="true">
						<tr>
							<td class="td-label"><label class="record-label"> <s:text
										name="pactInfo.meAdult"></s:text>:
							</label></td>
							<td class="td-value"><label class="record-value"> <s:property
										value="pactInfo.me_adult" />
							</label></td>

							<td class="td-label"></td>
							<td class="td-value"></td>

						</tr>




					</table>
			</div>

			<div title="<s:text name='customer.pactInformation'/>"
				style="padding: 10px;">
				<div class="section-header">
					<span><s:text name="entity.prod.information" /></span>
				</div>

				<table style="padding: 10px;" cellspacing="10" cellpadding="0"
					width="100%">
					<tr>


						<td class="td-label"><label class="record-label"> <s:text
									name="entity.prod.name"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.prdt_name" />
						</label></td>


						<td class="td-label"><label class="record-label"> <s:text
									name="entity.prod.code"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.prdt_no" />
						</label></td>
					</tr>

					<tr>
						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.term_range2"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.term_range" /> <label class="record-label">
									<s:text name="期"></s:text>
							</label>

						</label></td>

						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.rate"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.rate" /> <label class="record-label">
									% </label>
						</label></td>
					</tr>
				</table>

				<div class="section-header">
					<span><s:text name="合同信息" /></span>
				</div>

				<table style="padding: 10px;" cellspacing="10" cellpadding="0"
					width="100%">
					<tr>
						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.pactAmt"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.pact_amt" />元
						</label></td>

						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.cashAmt"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.cash_amt" />元
						</label></td>
					</tr>

					<tr>
						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.income_amt"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.income_amt" />元
						</label></td>

						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.fundSources"></s:text>:
						</label></td>
						<td class="td-value"><span onmousemove="this.setCapture();"
							onmouseout="this.releaseCapture();" onfocus="this.blur();">
								<s:select id="cifType" list="#select.queryWPD('FUNDS_FROM')"
									listKey="opCode" listValue="opCnName"
									name="pactInfo.fund_sources"></s:select>
						</span></td>
					</tr>

					<tr>
						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.paymentType"></s:text>:
						</label></td>
						<td class="td-value"><span onmousemove="this.setCapture();"
							onmouseout="this.releaseCapture();" onfocus="this.blur();">
								<s:select id="" list="#select.queryWPD('PAYMENT_TYPE')"
									listKey="opCode" listValue="opCnName"
									name="pactInfo.payment_type"></s:select>
						</span></td>
						<!-- 新加的 -->
						<s:if test="pactInfo.fund_sources==2||pactInfo.fund_sources==3">
					<td class="td-label"  >
            						<label class="record-label">
            							<s:text name="entity.pactInfo.old_pactno"></s:text>:
            						</label>            						
            				</td>
            				<td  class="td-value" >
            					    <label class="record-value"> <s:property
									value="pactInfo.old_pactno" />
									</label>
            				</td>
</s:if>
					</tr>


					<tr>
						<td class="td-label"><label class="record-label"> <s:text
									name="pactInfo.pact.if_continue"></s:text>:
						</label></td>
						<td class="td-value"><span onmousemove="this.setCapture();"
							onmouseout="this.releaseCapture();" onfocus="this.blur();">
								<s:select list="#select.queryWPD('YES_NO')" listKey="opCode"
									name="pactInfo.if_continue" value="pactInfo.if_continue"
									listValue="opCnName"></s:select>
						</span></td>

						<td class="td-label"><label class="record-label"> <s:text
									name="entity.financing.if_wxd"></s:text>:
						</label></td>
						<td class="td-value"><span onmousemove="this.setCapture();"
							onmouseout="this.releaseCapture();" onfocus="this.blur();">
								<s:select list="#select.queryWPD('YES_NO')" listKey="opCode"
									name="pactInfo.if_wxd" value="pactInfo.if_wxd"
									listValue="opCnName"></s:select>
						</span></td>
					</tr>

				</table>


				<div class="section-header">
					<span><s:text name="银行账户信息" /></span>
				</div>

				<table style="padding: 10px;" cellspacing="10" cellpadding="0"
					width="100%">
					<tr>
						<td class="td-label"><label class="record-label"> <s:text
									name="银行账户名"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.account_name" />
						</label></td>

						<td class="td-label"><label class="record-label"> <s:text
									name="银行账号"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.account_no" />
						</label></td>
					</tr>

					<tr>
						<td class="td-label" rowspan=”2″><label class="record-label">
								<s:text name="开户行"></s:text>:
						</label></td>
						<td class="td-value"><label class="record-value"> <s:property
									value="pactInfo.account_bank" />
						</label></td>

						<td class="td-label"><label class="record-label"> </label></td>
						<td class="td-value"></td>
					</tr>



				</table>
			</div>
			<s:if test="pactInfo.pact_type==2||pactInfo.pact_type==3">
				<div title="<s:text name='续购合同信息'/>" style="padding: 10px;">
					<div class="section-header">
						<span><s:text name="续购合同信息" /></span>
					</div>
					<table id="pactInfo" style="padding: 10px;" cellspacing="10"
						cellpadding="0" width="100%">
					</table>

				</div>
			</s:if>
		</div>

		</s:form>
	</div>

	<s:include value="../footer.jsp" />
	</div>
</body>
</html>



