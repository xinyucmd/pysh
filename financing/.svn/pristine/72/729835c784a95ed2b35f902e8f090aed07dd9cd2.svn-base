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
		  many_deleterow("/system/deleteProductConfiguration.action?seleteIDs=");
	  });
 
	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/system/editProductConfiguration.action?seleteIDs=");
	  });
	  
	  $("#export").click(function() {	
		  many_exportrow("/system/exportProductConfiguration.action?seleteIDs=");
	  });		  
	  
	  $("#copy").click(function() {	
		  many_copyrow("/system/copyProductConfiguration.action?seleteIDs=");
	  });
	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listProductConfigurationFull.action', 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="menu.ProductConfiguration.id" />','<s:text name="menu.ProductConfiguration.PADT_NO" />',
		  		 	  '<s:text name="menu.ProductConfiguration.PADT_NAME" />','<s:text name="menu.ProductConfiguration.STANDARD_AMT" />',
		  		   	  '<s:text name="menu.ProductConfiguration.ADVANCE_REDEEM" />','<s:text name="menu.ProductConfiguration.PRYMENT_TYPE" />',
		  		      '<s:text name="menu.ProductConfiguration.STAGE_DAYS" />','<s:text name="menu.ProductConfiguration.RETURN_RATE" />',
		  			  '<s:text name="menu.ProductConfiguration.REDEEM_DAYS" />',
		  	    	  '<s:text name="menu.ProductConfiguration.IS_REDEEM_BEFORE" />','<s:text name="menu.ProductConfiguration.RATES" />'],
		   	colModel:[
		   		{name:'id',index:'id', width:90, key: true,sorttype:"int",resizable:true, hidden:true},
		   		{name:'padt_no',index:'padt_no', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'padt_name',index:'padt_name', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'standard_amt',index:'standard_amt', width:100, resizable:true,sorttype:"int", formatter:urlFmatter},
		   		{name:'advance_redeem',index:'advance_redeem', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'pryment_type',index:'pryment_type', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'stage_days',index:'stage_days', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'return_rate',index:'return_rate', width:100, resizable:true, formatter:urlFmatter},
		   		{name:'redeem_days',index:'redeem_days', width:100, resizable:true, formatter:urlFmatter} ,
				{name:'is_redeem_before',index:'is_redeem_before', width:100, resizable:true, formatter:urlFmatter},
				{name:'rates',index:'rates', width:100, resizable:true, formatter:urlFmatter}
		   		
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
		   if (par == 1){
		     new_format_value = "<a href='editProductConfiguration.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
		   }else {
			 new_format_value = cellvalue;
		   }			
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
            name="title.action" />:&nbsp;&nbsp;</b> <span> <s:if
            test="#session.loginUser.create_system == 1">
            <span style="white-space: nowrap;"> <a
              href="editProductConfiguration.action" class="easyui-linkbutton"
              iconCls="icon-add" plain="true"><s:text
                  name="action.createUser" /></a>
            </span>
          </s:if> <s:if test="#session.loginUser.delete_system == 1">
            <span style="white-space: nowrap;"> <a id="delete"
              href="#" class="easyui-linkbutton" iconCls="icon-remove"
              plain="true"><s:text name="action.deleteUser" /></a>
            </span>
          </s:if> <span style="white-space: nowrap;"><a
            href="javascript:void(0)" id="mtmt"
            class="easyui-menubutton"
            data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                name='menu.toolbar.more.title' /></a>
            <div id="mtm1" style="width: 150px;">
            
            <!-- 导入 -->
              <s:if
       			 test="#session.loginUser.create_account == 1 || #session.loginUser.update_account == 1">
       			 <div data-options="iconCls:'icon-import'"
        			 onClick="openwindow('/system/upload.jsp?entityName=ProductConfiguration&namespace=system&title=' + '<s:text name="menu.ProductConfiguration" />')">
                  <s:text name='menu.item.import.title' />
        	</div> </s:if>
              
              
              <s:if test="#session.loginUser.view_system == 1">
                <div data-options="iconCls:'icon-export'" id="export">
                  <s:text name='menu.item.export.title' />
                </div>
              </s:if>
              <s:if test="#session.loginUser.update_system == 1">
                <div data-options="iconCls:'icon-update'"
                  id="massUpdate">
                  <s:text name='menu.item.massupdate.title' />
                </div>
              </s:if>
              <s:if test="#session.loginUser.create_system == 1">
                <div data-options="iconCls:'icon-copy'" id="copy">
                  <s:text name='menu.item.copy.title' />
                </div>
              </s:if>
            </div> </span>
        </span>
      </div>
      <div id="feature-title">
        <h2>
          <s:text name="menu.ProductConfiguration" />
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



 	
