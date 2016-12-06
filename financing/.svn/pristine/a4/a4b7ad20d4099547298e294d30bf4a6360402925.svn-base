<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.jiangchuanbanking.util.DateTimeUtil"%>
<%@ page language="java"
	import="com.jiangchuanbanking.system.domain.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../../css/redmond/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../../css/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../../css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="../../js/datagrid-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="../../js/ui.multiselect.js"></script>
<script type="text/javascript" src="../../js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../js/i18n/grid.locale-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		var id = '<s:property value="id"/>';
		$('#subAccount').datagrid({
			url : "listCustSubAccount.action?id=" + id,
			onClickRow : function(rowIndex, rowData) {
				$('#financingDetails').datagrid({
					url : "listDetailsAccount.action?sub_no=" + rowData.sub_no
				});
			}

		});
	});
	function cancel(){
		baseCancel("TurnMainAccount");
	}
</script>
</head>
<body>
	<div id="page-wrap">
		<s:include value="../header.jsp" />
		<s:include value="../menu.jsp" />
		<div id="feature">
			<s:include value="../navigation.jsp" />
			<div id="shortcuts" class="headerList">
				<b style="white-space: nowrap; color: #444;"><s:text
						name="title.action" />:&nbsp;&nbsp;</b> <span> <span
					style="white-space: nowrap;"><a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
				</span></span>
			</div>
			<div id="feature-title">
				<h2>
					<s:text name="menu.moneyaccount.title" />
				</h2>
			</div>
			<div id="bb" title="<s:text name='main.account.mainInformation'/>"
				style="padding: 10px;">
				<table id="subAccount" class="easyui-datagrid"
					title="<s:text name='main.account.subInformation'/>"
					style="width: 1280px; height: 250px"
					data-options="
					       		pagination : true,
					       		pagesize : 5, 
                      			pageList : [5,10,15],
					       		singleSelect:true,
					       		collapsible:true,
					       		method:'post'">
					<thead>
						<tr>
							<th data-options="field:'sub_no',width:60,align:'right'"><s:text
									name="main.account.subAccount_no" /></th>
							<th data-options="field:'pact_no',width:60,align:'right'"><s:text
									name="pactInfo.pactNo" /></th>
							<th data-options="field:'cash_amt',width:60"><s:text
									name="pactInfo.cashAmt" /></th>
							<th data-options="field:'renew_amt',width:60,align:'right'"><s:text
									name="subaccount.renewAmt" /></th>
							<th data-options="field:'rate',width:60"><s:text
									name="pactInfo.rate" /></th>
							<th data-options="field:'prdt_no',width:60,align:'right'"><s:text
									name="pactInfo.prdtNo" /></th>
							<th data-options="field:'prdt_name',width:60"><s:text
									name="pactInfo.prdtName" /></th>
							<th data-options="field:'term',width:60"><s:text
									name="pactInfo.termRange" /></th>
							<th data-options="field:'payment_type',width:60,align:'right'"><s:text
									name="pactInfo.paymentType" /></th>
							<th data-options="field:'income_amt',width:60"><s:text
									name="pactInfo.incomeAmt" /></th>
							<th data-options="field:'redeem_amt',width:60"><s:text
									name="subaccount.redeemAmt" /></th>
							<th data-options="field:'if_wxd',width:60"><s:text
									name="pactInfo.ifWxd" /></th>
							<th data-options="field:'end_date',width:60"><s:text
									name="entity.end_date.label" /></th>
							<th data-options="field:'start_date',width:60"><s:text
									name="entity.start_date.label" /></th>
							<th data-options="field:'sts',width:60"><s:text
									name="pactInfo.sts" /></th>
							<th data-options="field:'open_date',width:60"><s:text
									name="main.account.open_date" /></th>
							<th data-options="field:'close_date',width:60"><s:text
									name="main.account.close_date" /></th>
							<th data-options="field:'close_op',width:60"><s:text
									name="main.account.close_op" /></th>
							<th data-options="field:'cmt',width:60"><s:text
									name="customer.cmt.label" /></th>
						</tr>
					</thead>
				</table>
				<table id="financingDetails" class="easyui-datagrid"
					title="<s:text name='流水信息'/>" style="width: 1280px; height: 250px"
					data-options="
					       		pagination : true,
					       		pagesize : 5, 
                      			pageList : [5,10,15],
					       		singleSelect:true,
					       		collapsible:true,
					       		method:'post'">
					<thead>
						<tr>
							<th data-options="field:'flow_no',width:60,align:'right'"><s:text
									name="details.flow_no" /></th>
							<th data-options="field:'flow_type',width:60,align:'right'"><s:text
									name="details.flow_type" /></th>
							<th data-options="field:'flow_abstract',width:60"><s:text
									name="details.flow_abstract" /></th>
							<th data-options="field:'flow_amt',width:60,align:'right'"><s:text
									name="details.flow_amt" /></th>
							<th data-options="field:'flow_date',width:60"><s:text
									name="details.flow_date" /></th>
							<th data-options="field:'op_no',width:60,align:'right'"><s:text
									name="details.op_no" /></th>
							<th data-options="field:'cmt',width:60"><s:text
									name="customer.cmt.label" /></th>

						</tr>
					</thead>
				</table>
			</div>
		</div>

		<s:include value="../footer.jsp" />

	</div>
</body>
</html>



