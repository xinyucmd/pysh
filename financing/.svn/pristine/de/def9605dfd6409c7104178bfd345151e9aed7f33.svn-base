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
  	 /*  $("#delete").click(function() {	
		  many_deleterow("/investor/deleteCustomer.action?seleteIDs=");
	  });
  */
	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/investor/editCustomer.action?seleteIDs=");
	  });
	  
	  $("#export").click(function() {	
		  many_exportrow("/investor/exportCustomer.action?seleteIDs=");
	  });		  
	  
	  $("#copy").click(function() {	
		  many_copyrow("/investor/copyCustomer.action?seleteIDs=");
	  });
	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listCustomerFull.action', 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="customer.id.label" />',
		   	          '<s:text name="customer.cifName.label" />', '<s:text name="customer.sex.label" />',
		   	          '<s:text name="customer.birth.label" />','<s:text name="customer.cifType.label" />',
		   	          '<s:text name="customer.idType.label" />', '<s:text name="customer.idNo.label" />',
		   	          '<s:text name="customer.contact.label" />','<s:text name="customer.contactTel.label" />',
		   	          '<s:text name="customer.contactPhone.label" />','<s:text name="customer.mail.label" />',
		   	          '<s:text name="customer.address.label" />',
		   	          '<s:text name="customer.postcode.label" />','<s:text name="customer.openDate.label" />',
		   	          '<s:text name="customer.openOp.label" />','<s:text name="customer.cmt.label" />'],
		   	colModel:[
				{name:'id',index:'id', width:80, key: true, sorttype:"int",resizable:true, hidden:true},
				
		   		{name:'CIF_NAME',index:'CIF_NAME', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'SEX',index:'SEX', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;1:男;2:女"}},
		   		{name:'BIRTH',index:'BIRTH', width:90, resizable:true,formatter:urlFmatter},
		   		{name:'CIF_TYPE',index:'CIF_TYPE', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;1:个人;2:公司"}},
		   		{name:'ID_TYPE',index:'ID_TYPE', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
				   	editoptions:{value:":全部;1:身份证;2:组织机构代码证"}},
		   		{name:'ID_NO',index:'ID_NO', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'CONTACT',index:'CONTACT', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'CONTACT_TEL',CONTACT_TEL:'contactTel', width:90, resizable:true, formatter:urlFmatter} ,
				{name:'CONTACT_PHONE',index:'CONTACT_PHONE', width:90, resizable:true, formatter:urlFmatter},
				{name:'MAIL',index:'MAIL', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'ADDR',index:'ADDR', width:90, resizable:true, formatter:urlFmatter} ,
				{name:'POSTCODE',index:'POSTCODE', width:90, resizable:true, formatter:urlFmatter},
				{name:'OPEN_DATE',index:'OPEN_DATE', width:90, resizable:true, formatter:urlFmatter},
				{name:'OPEN_OP',index:'OPEN_OP', width:90, resizable:true, formatter:urlFmatter},
				{name:'CMT',index:'CMT', width:90, resizable:true, formatter:urlFmatter}
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
		     new_format_value = "<a href='editCustomer.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
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
      <s:if test="#session.loginUser.role == 'R02'">
      <div id="shortcuts" class="headerList">
      
        <b style="white-space: nowrap; color: #444;"><s:text
            name="title.action" />:&nbsp;&nbsp;</b> <span> 
            <span style="white-space: nowrap;"> <a
              href="editCustomer.action" class="easyui-linkbutton"
              iconCls="icon-add" plain="true"><s:text
                  name="action.createUser" /></a>
            </span>
            <%-- <span style="white-space: nowrap;"> 
            <a id="delete"　href="#" class="easyui-linkbutton" iconCls="icon-remove"
              plain="true"><s:text name="action.deleteUser" /></a>
            </span> --%>
        </span>
      </div>
      </s:if>
      <div id="feature-title">
        <h2>
          <s:text name="customer.title.label" />
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



 	