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
			url:'listAppProd.action', 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="menu.ProductConfiguration.PADT_NO" />',
		  		 	  '<s:text name="menu.ProductConfiguration.PADT_NAME" />',
		  		 	  '<s:text name="menu.ProductConfiguration.STANDARD_AMT" />',
		  		   	  '<s:text name="menu.ProductConfiguration.ADVANCE_REDEEM" />',
		  		   	  '<s:text name="entity.able.value" />',
		  		      '<s:text name="entity.status.label" />',
		  		      '<s:text name="menu.configuration.START_DATE" />',
		  			  '<s:text name="menu.configuration.END_DATE" />',
		  	    	  '<s:text name="entity.createdBy.label" />',
		  	    	  '<s:text name="entity.createdOn.label" />',
		  	    	  '<s:text name="entity.cmt.value" />'],
		   	colModel:[
		   		{name:'prdtNo',index:'prdtNo', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'prdtName',index:'prdtName', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'standardAmt',index:'standardAmt', width:100, resizable:true,formatter:urlFmatter},
		   		{name:'advanceRedeem',index:'advanceRedeem', width:100, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;1:是;2:否"}},
		   		{name:'delFlg',index:'delFlg', width:100, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;0:启用;1:停用"}},
		   		{name:'sts',index:'sts', width:100, resizable:true, formatter:urlFmatter,stype:'select', 
				   		editoptions:{value:":全部;10:待审核;15:审核通过"}},
		   		{name:'startDate',index:'startDate', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'endDate',index:'endDate', width:100, resizable:true, formatter:urlFmatter} ,
				{name:'createOp',index:'createOp', width:100, resizable:true, formatter:urlFmatter},
				{name:'createDate',index:'createDate', width:100, resizable:true, formatter:urlFmatter},
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
		   
		     new_format_value = "<a href='appProd.action?prdtNo=" + rowObject[0] + "'>" + cellvalue + "</a>";
		   			
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
          <s:text name="menu.appProd.prod_shenpi" />
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



 	