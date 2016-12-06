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
					addObjectForm.action = 'saveTemplate.action';
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
					<s:text name="上传合同模板文件" />
				</h2>

			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" namespace="/jsp/contract" method="post">
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						<s:hidden id="id" value="entity.id"></s:hidden>
						<tr>
							<!-- prdt_no -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.PADT_NO"></s:text>:
							</label></td>
							<td class="td-value"><label class="record-label"> <s:property value="entity.prdtNo"/></label></td>
							<td class="td-label"><label class="record-label"> <s:text
										name="模板包名称"></s:text>:
							</label></td>
							<td class="td-value"><label class="record-label"> <s:property value="entity.name"/></label></td>
						</tr>
						
						
					</table>
				</s:form>


			</div>
		</div>
		<s:include value="../footer.jsp" />
	</div>
</body>
</html>



