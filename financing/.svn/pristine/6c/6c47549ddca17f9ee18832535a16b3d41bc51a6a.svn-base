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
	src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="../../js/ui.multiselect.js"></script>
<script type="text/javascript" src="../../js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../js/i18n/grid.locale-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
      $("#delete").click(function() {	
  		  many_deleterow("/financing/deleteBankAccount.action?seleteIDs=");
  	  });
 	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listBankAccountFull.action', 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="menu.bankAccount.id" />',
		   	          '<s:text name="customer.cifName.label" />',
		   	          '<s:text name="menu.bankAccount.name" />',
		   	          '<s:text name="menu.bankAccount.no" />',
		   	          '<s:text name="menu.bankAccount.address" />',
		   	          '<s:text name="menu.bankAccount.STS" />',
		   	          '<s:text name="menu.bankAccount.updataTime" />',
		   	          '<s:text name="menu.bankAccount.opno" />'
		   	         ],
		   	colModel:[
				{name:'bank_account_id',index:'bank_account_id', width:80, key: true, sorttype:"int",resizable:true, hidden:true},
		   		{name:'cif_name',index:'cif_name', width:90, resizable:true, formatter:urlFmatter},	
				{name:'bank_account_name',index:'bank_account_name', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'bank_account_no',index:'bank_account_no', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'bank_account_addr',index:'bank_account_addr', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'sts',index:'sts', width:100, resizable:true,formatter:urlFmatter},
		   		{name:'updata_time',index:'updata_time', width:115, resizable:true, formatter:urlFmatter},
		   		{name:'op_no',index:'op_no', width:100, resizable:true, formatter:urlFmatter}
		   		
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "<s:text name='menu.NewBankAccount.lable'/>"
		});
	  
		function urlFmatter (cellvalue, options, rowObject)
		{  
		  
		     new_format_value = "<a href='editBankAccount.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
		   return new_format_value
		};	
		
		
		jQuery("#grid").jqGrid('navGrid','#pager',{del:false,add:false,edit:false,refresh:false,search:false});
		jQuery("#grid").jqGrid('navButtonAdd',"#pager",{caption:"",title:"<s:text name='grid.button.toggle.title'/>", buttonicon :'ui-icon-pin-s',
			onClickButton:function(){
				mygrid[0].toggleToolbar();
			} 
		});		
		jQuery("#grid").jqGrid('navButtonAdd',"#pager",{caption:"",title:"<s:text name='grid.button.advancedSearch.title'/>",buttonicon :'ui-icon-search',
			onClickButton:function(){
				jQuery("#grid").jqGrid('searchGrid', {multipleSearch:true} );
			} 
		});	
		jQuery("#grid").jqGrid('navButtonAdd',"#pager",{caption:"",title:"<s:text name='grid.button.clear.title'/>",buttonicon :'ui-icon-refresh',
			onClickButton:function(){
				var postdata = jQuery("#grid").jqGrid('getGridParam','postData');
				postdata.filters = "";
				mygrid[0].clearToolbar()
			} 
		});
		jQuery("#grid").jqGrid('navButtonAdd','#pager',{caption: "",title: "<s:text name='grid.button.reorderColumn.title'/>",
		    onClickButton : function (){
		    	jQuery("#grid").jqGrid('columnChooser');
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
						name="title.action" />:&nbsp;&nbsp;</b> <span> <span
					style="white-space: nowrap;"> <a
						href="editBankAccount.action" class="easyui-linkbutton"
						iconCls="icon-add" plain="true"> <s:text
								name="menu.NewBankAccount.add" />
					</a>
				</span>
			</div>
			<div id="feature-title">
				<h2>
					<s:text name="menu.bankAccount.title" />
				</h2>
			</div>
			<div id="feature-content">
				<table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
				<div id="pager" class="scroll"></div>
			</div>
		</div>

		<s:include value="../footer.jsp" />

	</div>
</body>
</html>



