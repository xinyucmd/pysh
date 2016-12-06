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
    	
     var message='<s:property value="message"/>';
     if (message!=null&&message!='') {
      	 jQuery.messager.alert('提示:',message);
  	 }


	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listReceipt.action', 
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="entity.id.label" />','<s:text name="合同编号"/>',
				  		'<s:text name="客户姓名" />',
				  		'<s:text name="文件名称" />',
				  		'<s:text name="创建时间" />',
				  		'<s:text name="是否已导出" />',
				  		'<s:text name="操作1" />',
				  		'<s:text name="操作2" />'	
			  		],
		   	colModel:[
                {name:'id',index:'id', width:120, key: true,sorttype:"int",resizable:true, hidden:true},
                {name:'pactNo',index:'PACT_NO', width:120, resizable:true},
                {name:'cifName',index:'cif_name', width:120, resizable:true},
		   		{name:'path',index:'PATH', width:150, resizable:true},
		   		{name:'createdate',index:'CREATE_TIME', width:100, resizable:true},
		   		{name:'if_export',index:'IF_EXPORT', width:100, resizable:true,stype:'select', 
			   		editoptions:{value:":全部;1:未导出;2:已导出"}},
		   		{name:'op', width:100, resizable:true, align:"center",formatter:urlFmatter},
		   		{name:'op2', width:100, resizable:true, align:"center",formatter:urlFmatter2}	
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	rowNumber:true,
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   //	multiselect: true, 
		   	caption: "<s:text name='pactInfo.liebiao'/>"
		});
		function urlFmatter (cellvalue, options, rowObject)
		{  	  
		     new_format_value = "<a href='receiptInfo.action?id=" + rowObject[0] + "&pactNo=" + rowObject[1] + "'><s:text name="查看收据信息"/></a>";
		   return new_format_value
		};	
		
		function urlFmatter2 (cellvalue, options, rowObject)
		{  	  
		     new_format_value = "<a href='downloadReceipt.action?id=" + rowObject[0] + "'><s:text name="导出Excel"/></a>";
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
    <s:text name="合同列表" />
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