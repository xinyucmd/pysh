<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="s" uri="/struts-tags"%> 
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
		if ($("#payAmt").val()>parseFloat('<s:property value="%{redeemPayView.redem_amount}"/>')) {
			 $.messager.show({  
				  title: '提示信息',  
				  msg: '实际支付金额不能大于应支付金额！',  
				  showType: 'slide'  
			});
		}else if($("#payAmt").val()<parseFloat('<s:property value="%{redeemPayView.redem_amount}"/>')){
			if ($("#addObjectForm").form("validate")) {
			
				$.messager.confirm("提交确认", "实际支付金额小于应支付金额,您确认提交吗？", function(saveAction) {
					if (saveAction) {
						
						var addObjectForm = document
								.getElementById('addObjectForm');
						
						addObjectForm.action = 'redemPay.action';
						addObjectForm.submit();
					}
				});
			}
			
		}else{

			if ($("#addObjectForm").form("validate")) {
				
				$.messager.confirm("提交确认", "您确认提交吗？", function(saveAction) {
					if (saveAction) {
						
						var addObjectForm = document.getElementById('addObjectForm');
						
						addObjectForm.action = 'redemPay.action';
						
						addObjectForm.submit();
					}
				});
			}
		}
	}
	
	
	function cancelApply() {
		if ($("#addObjectForm").form("validate")) {
			$.messager.confirm("撤销确认", "您确认撤销此申请吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					if($("#")){
						
					}
					addObjectForm.action = 'UndoRedem.action';
					addObjectForm.submit();

					baseCancel("RedemPay");

				}
			});
		}

	}
	 function cancel(){
		 baseCancel("RedemPay");
	 }

	 $(document).ready(function () {
		 $("#payHis").datagrid({
         	url:'listPayHis.action?pactNo=<s:property value="pactInfo.pact_no" />' ,
         	pagination : true,
         	border:false,
         	pagesize : 5,
         	pageList : [5,10,15],
         	title : "回款历史信息",
         	rownumbers: true,              
         	columns : [[
         	                {field:'pactNo',title:'<s:text name="合同号" />',width:150},
			                {field:'term',title:'<s:text name="期数" />',width:50},  
			                {field:'settleDate',title:'<s:text name="回款时间" />',width:120},  
			                {field:'reAmt',title:'<s:text name="回款金额" />',width:100}
         	]]
		 });
		 
		 
	 });
	
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
					onclick="submit()" plain="true"> <s:text name="button.save.commit_ok" />
				</a>
				</span>
				<!-- saveClose -->

				<!-- cancel -->
				<span style="white-space: nowrap;"> <a id="cancel_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="cancel()" plain="true"> <s:text name="button.quxiao" />
				</a>
				</span>
			</div>
			<!---->

			<div id="feature-content">
				<h2>
					<s:text name="赎回支付" />
				</h2>
			</div>

			<!-- 表单 -->
			<div id="feature-content">
				<s:form id="addObjectForm" validate="true"
					namespace="/jsp/redeem" method="post">
					<s:hidden id="prdtName" name="pactInfo.prdt_name"
						value="%{pactInfo.prdt_name}" />
					<s:hidden id="pact_no" name="pactInfo.pact_no" value="%{pactInfo.pact_no}" />
					<s:hidden id="id" name="pactInfo.id" value="%{pactInfo.id}" />
					<s:hidden id="pact_amt" name="pactInfo.pact_amt" value="%{pactInfo.pact_amt}" />
					<s:hidden id="rate" name="pactInfo.rate" value="%{pactInfo.rate}" />
					<s:hidden id="term_range" name="pactInfo.term_range" value="%{pactInfo.term_range}" />
					<s:hidden id="redeemId" name="id" value="%{id}" />
					
					<s:hidden id="" name="planBean.id" value="%{planBean.id}" />
			
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						<s:if test="redeemPayView.redem_type!=2">
						<tr>

							<td class="td-label" valign="top"><label
								class="record-label"> <s:text name="应赎回本金"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="%{redeemPayView.redem_capital}"/>元</label></td>
                            <td class="td-label"> <label
								class="record-label"><s:text name="应赎回收益"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="%{redeemPayView.redem_interest}"/>元</label></td>
									
						</tr>
						</s:if>
						
						<tr>

							<td class="td-label" valign="top"><label
								class="record-label"> <s:text name="advance.redeem.jine"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="redeemPayView.redem_amount"/>元</label></td>
                            <td class="td-label"> <label
								class="record-label"><s:text name="advance.redeem.type"></s:text>：

							</label></td>
							<td class="td-value"><span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
									<s:select id="sex"
											list="#select.queryWPD('REDEM_TYPE')" listKey="opCode"
											listValue="opCnName" name="redeemPayView.redem_type"
											></s:select>
									</span></td>
						</tr>
						
						<tr>

							<td class="td-label" valign="top"><label
								class="record-label"> <s:text name="advance.redeem.date"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="redeemPayView.redem_Date"/></label></td>
                           <s:if test="pactInfo.payment_type==2">
                           <td class="td-label" valign="top"><label
								class="record-label"> <s:text name="回款期数"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="planBean.term"/></label></td>
							</s:if>
						</tr>
						
						<tr>

						 <td class="td-label" valign="top"><label
								class="record-label"> <s:text name="支付金额"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><input id="payAmt" name="financingDetails.flow_amt" class="easyui-validatebox"  required="true" missingMessage="不能为空"/>元</label></td>
                           <td class="td-label"> <label

								class="record-label"><s:text name="advance.redeem.come_amt_date"></s:text>：

							</label></td>
							<td class="td-value">
								 <input class="easyui-datebox"  name="financingDetails.flow_date" value="<s:property value="financingDetails.flow_date" />" required="true" missingMessage="不能为空"/>
							</td>
						</tr>
						
					</table>


					<div id="tt" class="easyui-tabs">
						<div title="<s:text name='menu.customer.infomation'/>" style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="menu.base.information" /></span>
							</div>
							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="customer.cifName.label"></s:text>：</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="customer.cif_name" />
									</label></td>



									<td class="td-label"><label class="record-label">
											<s:text name="customer.otherContact.sex"></s:text>:
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
											<s:text name="customer.contactTel.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.contact_phone" />
									</label></td>

								</tr>
								<tr>

									<td class="td-label"><label class="record-label">
											<s:text name="customer.email"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.mail" />
									</label></td>

								</tr>



								<tr>

								</tr>



							</table>

							
							<div class="section-header">
								<span><s:text name="customer.address.label" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%" readonly="true">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="customer.address.label"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="customer.mail" />
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

						<div title="<s:text name='pactInfo.information'/>" style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="pactInfo.pactNo" /></span>
							</div>
						<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>


									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.pactNo"></s:text>:
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
											<s:text name="pactInfo.termRange"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.term_range" /> </label><label
											class="record-label"> <s:text name="期"></s:text>
										

									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="pactInfo.rate"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.rate" /> </label><label
											class="record-label"> % </label>
									</td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="pactInfo.inflomation" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="entity.pactInfo.jine"></s:text>:
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
											<s:text name="pactInfo.income_Amt"></s:text>:
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
											<s:text name="pactInfo.type_fuxi"></s:text>:
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
											<s:text name="是否续购"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select
											list="#select.queryWPD('YES_NO')" listKey="opCode"
											name="pactInfo.if_continue" value="pactInfo.if_continue"
											listValue="opCnName"></s:select></span></td>

									<td class="td-label"><label class="record-label">
											<s:text name="entity.financing.if_wxd"></s:text>:
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
											<s:text name="entity.start_time"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value"><s:property value="startDate" /></label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="entity.end_time"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value"><s:property value="endDate" /></label></td>
								</tr>

							</table>


							<div class="section-header">
								<span><s:text name="entity.bankAccount.label" /></span>
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
						
						<s:if test="pactInfo.payment_type==2">
						   <div title="<s:text name='回款历史'/>" style="padding: 10px;">
								<div class="section-header">
									<span><s:text name="回款历史" /></span>
								</div>
						     	<table id="payHis" style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
						     		
						     	
						     	</table>
						   </div>

						</s:if>

					</div>
				</s:form>
			</div>

			<s:include value="../footer.jsp" />
		</div>
	</div>
</body>
</html>



