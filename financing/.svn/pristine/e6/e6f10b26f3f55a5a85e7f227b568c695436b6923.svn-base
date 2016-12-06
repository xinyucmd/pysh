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
		  many_deleterow("/investor/deleteCustomer.action?seleteIDs=");
	  });
 
	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/investor/editCustomer.action?seleteIDs=");
	  });
	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listPactInfoFull.action', 			
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="pactInfo.id" />',  '<s:text name="pactInfo.pactNo" />',
		   	          /*  '<s:text name="pactInfo.subNo" />', */
		   	          /* '<s:text name="pactInfo.cifNo" />',  */ '<s:text name="pactInfo.cifName" />', 
		   	          '<s:text name="pactInfo.accountName" />','<s:text name="pactInfo.accountNo" />',
		   	          '<s:text name="pactInfo.accountBank" />', '<s:text name="pactInfo.ifWxd" />',
		   	          '<s:text name="pactInfo.prdtNo" />',  '<s:text name="pactInfo.prdtName" />',
		   	          '<s:text name="pactInfo.termRange" />','<s:text name="pactInfo.rate" />',
		   	          '<s:text name="pactInfo.pactAmt" />', '<s:text name="pactInfo.cashAmt" />',
		   	          '<s:text name="pactInfo.incomeAmt" />', '<s:text name="pactInfo.fundSources" />',
		   	          '<s:text name="pactInfo.paymentType" />', '<s:text name="pactInfo.startDate" />',
		   	          '<s:text name="pactInfo.endDate" />','<s:text name="pactInfo.ifContinue" />',
		   	          '<s:text name="pactInfo.sts" />', '<s:text name="pactInfo.pactType" />',
		   	          '<s:text name="是否已打印合同" />', '<s:text name="是否已打印收据" />'
		   	          ],
		   	colModel:[
				{name:'pactInfo_Id',index:'pactInfo_Id', width:80, key: true, sorttype:"int",resizable:true, hidden:true},
		   		{name:'pact_no',index:'pact_no', width:90, resizable:true, formatter:urlFmatter},
		   		/* {name:'sub_no',index:'sub_no', width:90, resizable:true,formatter:urlFmatter}, 
		   		{name:'cif_no',index:'cif_no', width:90, resizable:true, formatter:urlFmatter},*/
		   		{name:'cif_name',index:'cif_name', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'account_name',index:'account_name', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'account_no',index:'account_no', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'account_bank',account_bank:'contactTel', width:90, resizable:true, formatter:urlFmatter} ,
				{name:'if_wxd',index:'if_wxd', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;0:是;1:否"}},
				{name:'prdt_no',index:'prdt_no', width:90, resizable:true, formatter:urlFmatter},
				{name:'prdt_name',index:'prdt_name', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'term_range',index:'term_range', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'rate',index:'rate', width:90, resizable:true,formatter:urlFmatter},
		   		{name:'pact_amt',index:'pact_amt', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'cash_amt',index:'cash_amt', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'income_amt',index:'income_amt', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'fund_sources',index:'fund_sources', width:90, resizable:true, formatter:urlFmatter},
		   		{name:'payment_type',payment_type:'contactTel', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;1:利随本清;2:先息后本;3:等额本金"}} ,
				{name:'start_date',index:'start_date', width:90, resizable:true, formatter:urlFmatter},
				{name:'end_date',index:'end_date', width:90, resizable:true, formatter:urlFmatter},
				{name:'if_continue',index:'if_continue', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
			   		editoptions:{value:":全部;1:是;2:否"}},
				{name:'sts',index:'sts', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
				   		editoptions:{value:":全部;1:未提交;2:债权匹配;3:合同签订;4:到账确认;5:到账复核;6:流程结束;7:已撤销;81:提前赎回申请;82:提前赎回;83：到期赎回申请;84:到期赎回;85:赎回撤销;9:关闭"}},
				{name:'pact_type',index:'pact_type', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
					   		editoptions:{value:":全部;1:认购;2:续购"}},
				{name:'if_export_ht',index:'if_export_ht', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
						   	editoptions:{value:":全部;1:未导出;2:已导出"}},
				{name:'if_export_sj',index:'if_export_sj', width:90, resizable:true, formatter:urlFmatter,stype:'select', 
							editoptions:{value:":全部;1:未导出;2:已导出"}}	
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "<s:text name="pactInfo.Information"/>"
		});
		function urlFmatter (cellvalue, options, rowObject)
		{  
		   var par='<%=((User)session.getAttribute("loginUser")).getUpdate_system()%>';
		     new_format_value = "<a href='editPactInfo.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
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
      <div id="feature-title">
        <h2>
          <s:text name="pactInfo.Information" />
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



 	