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
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">

	function save() {
		var bankAccount_name = document.getElementById("bank_account_name1111").value;
		var bankAccount_no = document.getElementById("bank_account_no1111").value;
		var bankAccount_addr = document.getElementById("bankAccount_addr1111").value;
		if (bankAccount_name != "") {
			if (bankAccount_no != "") {
				if (bankAccount_addr != "") {
					var grid = $("#customerId").combogrid("grid");//获取表格对象
					var row = grid.datagrid('getSelected');//获取行数据
					if ($("#addObjectForm").form("validate")) {
						$.messager
								.confirm(
										"保存确认",
										"您确认保存当前信息吗？",
										function(saveAction) {
											if (saveAction) {
												//$.get("listOddDelectNewBankAccount.action?id="+row.id);

												var addObjectForm = document
														.getElementById('addObjectForm');
												addObjectForm.action = 'saveBankAccount.action';
												addObjectForm.submit();  
												setTimeout(addObjectForm
														.submit(), 100);
											}
										});
					}
				} else {
					alert("请输入开户行地址");
				}
			} else {
				alert("请输入银行卡号");
			}
		} else {
			alert("请输入银行账户名");
		}
	}

	function cancel() {
		baseCancel("BankAccount");
	}

	function getSelected() {
		var row = $("#bankcus").datagrid("getSelected");
		var bank_account_id = row.bank_account_id;
		$('#bankcus').datagrid({

			url : "listBankAccountSts.action?id=" + bank_account_id
		});
	}

	
	$(document).ready(
			function() {

				$('#customerId').combogrid('setValue',
						'<s:property value="cif_id"/>');
				$('#customerId').combogrid('setText',
						'<s:property value="cif_name"/>');

				$("#customerId").combogrid({
					onSelect : function(n, o) {
						var grid = $("#customerId").combogrid("grid");//获取表格对象
						var row = grid.datagrid('getSelected');//获取行数据

						var id = row.id;
						$('#bankcus').datagrid({
							url : "listOddBankAccount.action?id=" + id
						});
					}
				});
				/*  $("#bankcus").datagrid({  
				      onSelect:function(n,o) {     	
				           //var row = $("#bankcus").datagrid("getSelected");
				           //var row = $("#bankcus").datagrid("getSelected");
				  		  //alert(row.bank_account_name); 
				          
				       }
				 
				}); */

				var id = '<s:property value="cif_id"/>';
				if (id != "") {
					$('#bankcus').datagrid({
						url : "listOddBankAccount.action?id=" + id
					});
				}
			})

	
</script>
</head>
<body>
	<s:include value="../header.jsp" />
	<s:include value="../menu.jsp" />
	<div id="feature">
		<div id="shortcuts" class="headerList">
			<!-- save -->

			<span style="white-space: nowrap;"> <a id="save_accept_btn"
				href="#" class="easyui-linkbutton" iconCls="icon-save-accept"
				onclick="save()" plain="true"> <s:text name="button.save" />
			</a>
			</span>
			<!-- cancel -->
			<span style="white-space: nowrap;"> <a id="cancel_btn"
				href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="cancel()" plain="true"> <s:text name="button.close" />
			</a>
			</span>
		</div>
		<div id="feature-title">
			<s:if test="bankAccount!=null && bankAccount.bank_account_id!=null">
				<h2>
					<s:text name="menu.bankAccount.updata" />
				</h2>
			</s:if>
			<s:else>
				<s:if test="seleteIDs!=null && seleteIDs!= ''">
					<h2>
						<s:text name="menu.bankAccount.massupdata" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="menu.bankAccount.add" />
					</h2>
				</s:else>
			</s:else>
		</div>
		<!-- 表单 -->
		<div id="feature-content">
			<s:form id="addObjectForm" validate="true" namespace="/jsp/financing"
				method="post">
				<s:hidden id="bankAccountId" name="bankAccount.bank_account_id"
					value="%{bankAccount.bank_account_id}" />
				<s:hidden id="saveFlag" name="saveFlag" />
				<s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />
				<table style="padding: 10px;" cellspacing="10" cellpadding="0"
					width="100%">
					<!-- customer -->
					<tr>
						<td class="td-mass-update"><input id="massUpdate"
							name="massUpdate" type="checkbox" class="massUpdate"
							value="customer" /></td>
						<td class="td-label"><label class="record-label"> <!-- 客户 -->
								<s:text name="customer"></s:text>：
						</label></td>
						<td class="td-value"><select id="customerId" required="true"
							class="easyui-combogrid record-value"
							name="bankAccount.customer.id" style="width: 180px;"
							data-options="  
									            panelWidth:520,  
									            idField:'id',  
									            textField:'cif_name',  
									            url:'<s:url action="listCustomer" namespace="/jsp/investor"/>',
						                        loadMsg: '<s:text name="datagrid.loading" />',
						                        pagination : true,
						                        pageSize: 10,
						                        pageList: [10,30,50],
								                fit: true,
									            mode:'remote',
									            columns:[[  
									                {field:'id',title:'<s:text name="customer.id.label" />',width:60 },  
									                {field:'cif_name',title:'<s:text name="customer.cifName.label" />',width:120}           
									            ]]  
									        ">
						</select></td>

						<td class="td-mass-update"><input id="massUpdate"
							name="massUpdate" type="checkbox" class="massUpdate"
							value="customer" /></td>

					</tr>
				</table>

				<div id="tt" class="easyui-tabs">

					<div title="<s:text name='menu.NewBankAccount.information'/>"
						style="padding: 10px;">
						<table style="padding: 10px;" cellspacing="10" cellpadding="0"
							width="100%">
							<tr>
								<!-- bank_account_name -->
								<td class="td-mass-update"><input id="massUpdate"
									name="massUpdate" type="checkbox" class="massUpdate"
									value="bank_account_name" /></td>
								<td class="td-label"><label class="record-label"> <s:text
											name="menu.bankAccount.name"></s:text>:
								</label></td>
								<td class="td-value"><input id="bank_account_name1111"
									name="bankAccount.bank_account_name" class="easyui-validatebox"
									required="true" /></td>
								<!-- bank_account_no  -->
								<td class="td-mass-update"><input id="massUpdate"
									name="massUpdate" type="checkbox" class="massUpdate"
									value="bank_account_no" /></td>
								<td class="td-label"><label class="record-label"> <s:text
											name="menu.bankAccount.no"></s:text>:
								</label></td>
								<td class="td-value"><input id="bank_account_no1111"
									name="bankAccount.bank_account_no" class="easyui-validatebox"
									required="true" /></td>
							</tr>
							<tr>
								<!-- bank_account_addr -->
								<td class="td-mass-update"><input id="massUpdate"
									name="massUpdate" type="checkbox" class="massUpdate"
									value="bank_account_addr" /></td>
								<td class="td-label"><label class="record-label"> <s:text
											name="menu.bankAccount.address"></s:text>:
								</label></td>
								<td class="td-value"><input id="bankAccount_addr1111"
									name="bankAccount.bank_account_addr" cssClass="record-value"
									class="easyui-validatebox" required="true" />
								</td>
								<!-- sts -->
								<td class="td-mass-update"><input id="massUpdate"
									name="massUpdate" type="checkbox" class="massUpdate"
									value="sts" /></td>
								<td class="td-label"><label class="record-label"> <s:text
											name="menu.bankAccount.STS"></s:text>:
								</label></td>
								<td class="td-value"><s:select name="bankAccount.sts"
										list="#select.queryWPD('ENABLE')" listKey="opCode"
										listValue="opCnName" /></td>
							</tr>
						</table>
					</div>
					<div id="oddBank"
						title="<s:text name='menu.oddBankAccount.information'/>"
						style="padding: 10px;">
						<table>
							<tr>
								<table id="bankcus" class="easyui-datagrid"
									style="width: 700px; height: 250px"
									data-options="singleSelect:true,collapsible:true,method:'post'" 
									>
								
									<thead>
										<tr>
											<th data-options="field:'bank_account_id',width:80"><s:text
													name="menu.bankAccount.id" /></th>
											<th data-options="field:'cif_name',width:100"><s:text
													name="customer.cifName.label" /></th>
											<th data-options="field:'bank_account_name',width:100"><s:text
													name="menu.bankAccount.name" /></th>
											<th
												data-options="field:'bank_account_no',width:80,align:'right'"><s:text
													name="menu.bankAccount.no" /></th>
											<th
												data-options="field:'bank_account_addr',width:80,align:'right'"><s:text
													name="menu.bankAccount.address" /></th>
											<th data-options="field:'sts',width:80,align:'right'"><s:text
													name="menu.bankAccount.STS" /></th>
											<th data-options="field:'op_no',width:80,align:'right'"><s:text
													name="menu.bankAccount.opno" /></th>
										</tr>
									</thead>
								</table>
								</td>
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



