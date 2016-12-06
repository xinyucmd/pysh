<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="s" uri="/struts-tags"%> 
<%@ page import="com.jiangchuanbanking.util.DateTimeUtil"%>
<%@ page import="com.jiangchuanbanking.system.domain.User"%>
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


	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listUndoApply.action', 
			mtype: 'POST',
			height: "auto",
			colNames:[  '<s:text name="entity.id.label" />',
			            '<s:text name="customer.cifName.label" />',
				  		'<s:text name="customer.pactNo" />',
				  		'<s:text name="redem.type" />',
				  		'<s:text name="redem.amt" />',
				  		'<s:text name="redem.date" />' 			  		 
				  		],
			   	colModel:[
	                {name:'id',index:'id', width:120, key: true,sorttype:"int",resizable:true, hidden:true},
			   		{name:'cif_name',index:'cif_name', width:120, resizable:true, formatter:urlFmatter},
			   		{name:'pact_no',index:'pact_no', width:120, resizable:true, formatter:urlFmatter},
			   		{name:'redem_type',index:'redem_type', width:150, resizable:true, formatter:urlFmatter,stype:'select', 
				   		editoptions:{value:":全部;4:撤销理财"}},
			   		{name:'redem_amount',index:'redem_amount', width:150, resizable:true, formatter:urlFmatter},
			   		{name:'redem_Date',index:'redem_Date', width:150, resizable:true, formatter:urlFmatter}   	
			   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "赎回列表"
		});
		function urlFmatter (cellvalue, options, rowObject)
		{   
		   var par='<%=((User)session.getAttribute("loginUser")).getUpdate_account()%>';
		     new_format_value = "<a href='editUndoConfirm.action?id=" + rowObject[0] + "&pact_no="+ rowObject[2] +"'>" + cellvalue + "</a>";
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
				mygrid[0].clearToolbar();
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
  
  </div>
  <div id="feature-title">
   <h2>
    <s:text name='undo.wealth.confirm' />
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
   <table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
   <div id="pager" class="scroll"></div>
   <div id="filter" style="margin-left: 30%; display: none">
    <s:text name="title.listAccount" />
   </div>
  </div>
 </div>
 <s:include value="../footer.jsp" />
 </div>
</body>
</html>