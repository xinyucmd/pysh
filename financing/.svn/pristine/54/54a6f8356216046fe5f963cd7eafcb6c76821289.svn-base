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
    $(document).ready(function(){
    	$("#delete").click(function() {	
  		  many_deleterow("/contract/deleteTemplate.action?seleteIDs=");
  	  });
    		 var a = document.getElementById("add");
    		 a.href="addTemplate.action?id="+$("#id").val();
    	
	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listTemplateFull.action?id='+$("#id").val(), 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="id" />',
		   	          '<s:text name="id" />',
		   	          '<s:text name="模板文件名称" />',
		  		 	  '<s:text name="模板包类型" />',
		  		 	 	
		  		 	  '<s:text name="是否有效" />',
		  		 	'<s:text name="创建人" />',
		  		 	'<s:text name="下载查看" />'
		  	    	   ],
		   	colModel:[
				{name:'packageId',index:'packageId', width:100, resizable:true, hidden:true,formatter:urlFmatter},
				{name:'id',index:'id', width:100, resizable:true, key:true,hidden:true,formatter:urlFmatter},
		   		{name:'name',index:'name', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'type',index:'type', width:100, resizable:true, formatter:urlFmatter},
		   		
		   		{name:'status',index:'status', width:100, resizable:true, formatter:urlFmatter,stype:'select', 
				   		editoptions:{value:":全部;0:启用;1:停用"}},
				 {name:'op',index:'op', width:100, resizable:true, formatter:urlFmatter },
				 {name:'down',index:'down', width:100, resizable:true, formatter:urlFmatter1 }
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "<s:text name='title.grid.users'/>"
		});
		function urlFmatter (cellvalue, options, rowObject)
		{  
		   var par='<%=((User) session.getAttribute("loginUser"))
					.getUpdate_system()%>';
							if (par == 1) {
								new_format_value = "<a href='modifyTemplate.action?templateId="
										+ rowObject[1]
										+ "&id="
										+ rowObject[0]
										+ "'>" + cellvalue + "</a>";
							} else {
								new_format_value = cellvalue;
							}
							return new_format_value
						}
						;
						function urlFmatter1(cellvalue, options, rowObject) {
							new_format_value = "<a href='templateDetail.action?templateId="
									+ rowObject[1] + "'>" + cellvalue + "</a>";
							return new_format_value
						}
						;
						jQuery("#grid").jqGrid('navGrid', '#pager', {
							del : false,
							add : false,
							edit : false,
							refresh : false,
							search : false
						});
						jQuery("#grid")
								.jqGrid(
										'navButtonAdd',
										"#pager",
										{
											caption : "",
											title : "<s:text name='grid.button.toggle.title'/>",
											buttonicon : 'ui-icon-pin-s',
											onClickButton : function() {
												mygrid[0].toggleToolbar();
											}
										});
						jQuery("#grid")
								.jqGrid(
										'navButtonAdd',
										"#pager",
										{
											caption : "",
											title : "<s:text name='grid.button.advancedSearch.title'/>",
											buttonicon : 'ui-icon-search',
											onClickButton : function() {
												jQuery("#grid")
														.jqGrid(
																'searchGrid',
																{
																	multipleSearch : true
																});
											}
										});
						jQuery("#grid")
								.jqGrid(
										'navButtonAdd',
										"#pager",
										{
											caption : "",
											title : "<s:text name='grid.button.clear.title'/>",
											buttonicon : 'ui-icon-refresh',
											onClickButton : function() {
												var postdata = jQuery("#grid")
														.jqGrid('getGridParam',
																'postData');
												postdata.filters = "";
												mygrid[0].clearToolbar()
											}
										});
						jQuery("#grid")
								.jqGrid(
										'navButtonAdd',
										'#pager',
										{
											caption : "",
											title : "<s:text name='grid.button.reorderColumn.title'/>",
											onClickButton : function() {
												jQuery("#grid").jqGrid(
														'columnChooser');
											}
										});
						jQuery("#grid").jqGrid('filterToolbar');

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
						name="title.action" />:&nbsp;&nbsp;</b> <span
					style="white-space: nowrap;"> <a id="add"
					class="easyui-linkbutton" iconCls="icon-add" plain="true"><s:text
							name="button.create" /></a>
				</span> <span style="white-space: nowrap;"> <a id="delete" href="#"
					class="easyui-linkbutton" iconCls="icon-remove" plain="true"><s:text
							name="删除" /></a>
				</span> </span> <span style="white-space: nowrap;"> <a id="back"
					href="listTemplatePackage.action" class="easyui-linkbutton"
					iconCls="icon-back" plain="true"><s:text
							name="button.save.close" /></a>
				</span>
			</div>

		</div>
		<div id="feature-title">
			<h2>
				<s:text name="合同模板" />
			</h2>
		</div>
		<div id="feature-content">
			<table style="" cellspacing="10" cellpadding="0" width="100%">
				<s:if test="hasActionErrors()">
					<tr>
						<td align="left" colspan="4"><font color="red"><s:actionerror /></font></td>
					</tr>
				</s:if>
			</table>
			<%-- <div>
					<label class="record-label"> <s:text
								name="menu.ProductConfiguration.PADT_NO"></s:text>:
					</label>
					<label class="record-label"> <s:property
								value="prdtNo" /></label>
					<label class="record-label"> <s:text
								name="模板包名称"></s:text>:
					</label>
					<label class="record-label"> <s:property
								value="entity.name" /></label>
				 </div> --%>
			<table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
			
			<input id="id" type="hidden" value="<s:property value="id"/>">
			<div id="pager" class="scroll"></div>
		</div>
	</div>

	<s:include value="../footer.jsp" />

	</div>
</body>
</html>



