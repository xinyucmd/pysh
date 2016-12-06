<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.jiangchuanbanking.util.DateTimeUtil"%>
<%@ page language="java" import="com.jiangchuanbanking.system.domain.User"%>
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
  		  alert(111);
		 url =  getWebPath() + "/jsp" +"/prod/deleteProd.action?prdtNo=11";
		window.open(url,"_self");
	  });
 
	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/prod/editProd.action?seleteIDs=");
	  });
	  
	  $("#export").click(function() {	
		  many_exportrow("/system/exportUser.action?seleteIDs=");
	  });		  
	  
	  $("#copy").click(function() {	
		  many_copyrow("/system/copyUser.action?seleteIDs=");
	  });
	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listMainAccountFull.action', 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="main.accountNo" />',
		   	          '<s:text name="main.accountNo" />',
		  		 	  '<s:text name="customer.cifName.label" />',
		  		   	  '<s:text name="pactInfo.sts" />',
		  		      '<s:text name="customer.openDate.label" />',
		  		      '<s:text name="customer.openOp.label" />',
		  			  '<s:text name="main.account.close_date" />',
		  	    	  '<s:text name="main.account.close_op" />',
		  	    	  '<s:text name="entity.cmt.value" />'],
		   	colModel:[
{name:'cif_no',index:'cif_no', width:100, resizable:true, hidden:true,formatter:urlFmatter},
		   		{name:'account_no',index:'account_no', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'cif_name',index:'cif_name', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'status',index:'status', width:100, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;1:正常;9:关闭"}},
		   		{name:'open_date',index:'open_date', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'open_op',index:'open_op', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'close_date',index:'close_date', width:100, resizable:true, formatter:urlFmatter} ,
				{name:'close_op',index:'close_op', width:100, resizable:true, formatter:urlFmatter},
				{name:'cmt',index:'cmt', width:100, resizable:true, formatter:urlFmatter}
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
		   var par='<%=((User)session.getAttribute("loginUser")).getUpdate_system()%>';
		     new_format_value = "<a href='javascript:openPage(&quot;/subAccount/listSubAccountPage.action?account_no="+ rowObject[1]+"&search=true&id="+ rowObject[0]+"&quot;);'>" + cellvalue+ "</a>";
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
      <div id="feature-title">
        <h2>
          <s:text name="menu.moneyaccount.title" />
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
      </div>
    </div>

    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



 	