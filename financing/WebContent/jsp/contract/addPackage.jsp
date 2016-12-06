<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<s:action name="select" id="select"></s:action>
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
<script type="text/javascript" src="../../js/jquery.edatagrid.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/datagrid-<%=(String) session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
	function save() {
		if ($("#addObjectForm").form("validate")) {
			$.messager.confirm("保存确认", "您确认保存当前信息吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'savePackage.action';
					addObjectForm.submit();
				}
			});

		}

	}

	function cancel() {
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = 'listTemplatePackage.action';
		addObjectForm.submit();
	}
	$(document).ready(function() {
		$('#proID').combogrid('setValue', '<s:property value="prdtNo"/>');

		$('#proID').combogrid('setText', '<s:property value="prdtName"/>');

		$("#proID").combogrid({

			onSelect : function(n, o) {

				var g = $('#proID').combogrid('grid'); // 获取表格控件对象
				var r = g.datagrid('getSelected'); //获取表格当前选中行 

				$("#prdtNo").val(r.prdtNo);
				$("#name").val(r.prdtName);

			}

		});
	});
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
					style="white-space: nowrap;"> <a id="save_accept_btn"
						href="#" class="easyui-linkbutton" iconCls="icon-save-accept"
						onclick="save()" plain="true"><s:text name="button.save" /></a>
				</span> <span> <span style="white-space: nowrap;"> <a
							id="cancel_btn" href="#" class="easyui-linkbutton"
							iconCls="icon-cancel" onclick="cancel()" plain="true"><s:text
									name="button.cancel" /></a>
					</span>
				</span>
			</div>

			<div id="feature-title">

				<h2>
					<s:text name="合同模板包" />
				</h2>

			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" namespace="/jsp/contract" method="post">
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						<tr>
							<td class="td-label"><label class="record-label"> <s:text
										name="请选择"></s:text>:
							</label></td>
							<td class="td-value"><select id="proID" required="true"
								class="easyui-combogrid record-value" name=""
								style="width: 180px;"
								data-options="  
					            		panelWidth:520,  
					            		idField:'prdtNo',  
					            		textField:'prdtName',  
					            		url:'<s:url action="listPro" namespace="/jsp/prod"/>',
		                        		loadMsg: '<s:text name="datagrid.loading" />',
		                        		pagination : true,
		                        		pageSize: 10,
		                        		pageList: [10,30,50],	
				                		fit: true,
					            		mode:'remote',
					            		columns:[[    	
					                {field:'prdtNo',title:'<s:text name="entity.prod.code" />',width:60},  
					                {field:'prdtName',title:'<s:text name="entity.prod.name" />',width:100},  
					                {field:'standardAmt',title:'<s:text name="entity.prod.qigou" />',width:120}  
					                
					                
					            ]]  
					        ">
							</select></td>
							<td class="td-label"></td>
							<td class="td-value"></td>
						</tr>
						<tr>
							<!-- prdt_no -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.PADT_NO"></s:text>:
							</label></td>
							<td class="td-value"><input name="prdtNo" id="prdtNo"
								class="easyui-validatebox" required="true" missingMessage="不能为空"
								class="record-value" /></td>
							<td class="td-label"><label class="record-label"> <s:text
										name="模板包名称"></s:text>:
							</label></td>
							<td class="td-value"><input name="name" id="name"
								class="easyui-validatebox" required="true" missingMessage="不能为空"
								class="record-value" /></td>
						</tr>

					</table>
				</s:form>


			</div>
		</div>
		<s:include value="../footer.jsp" />
	</div>
</body>
</html>



