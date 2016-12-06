<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page language="java" import="com.jiangchuanbanking.system.domain.User"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><s:text name="info.about" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<link rel="stylesheet" type="text/css" href="../../css/portal.css" />
<style type="text/css">
.title {
	font-size: 16px;
	font-weight: bold;
	padding: 20px 10px;
	background: #eee;
	overflow: hidden;
	border-bottom: 1px solid #ccc;
}

.t-list {
	padding: 5px;
}
</style>
<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript" src="../../js/jquery.portal.js"></script>
<script>
		$(function(){
			$('#pp').portal({
				border:false,
				fit:true
			});
			
			  $('#myAccountGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					width:1150,
					height:350,
					pagination:true,
					idField:'id', 
					url:'listEndPactInfo.action',
					columns:[[
						{field:'cif_name',title:'<s:text name="pactInfo.cifName" />',width:100,align:'center',sortable:'true'},
						
						{field:'id_no',title:'<s:text name="customer.idNo.label" />',width:150,align:'center',sortable:'true'},
						{field:'addr',title:'<s:text name="customer.address.label" />',width:140,align:'center',sortable:'true'},
						{field:'contact',title:'<s:text name="customer.contact.label" />',width:100,align:'center',sortable:'true'},
						{field:'contact_tel',title:'<s:text name="customer.contactTel.label" />',width:140,align:'center',sortable:'true'},
						
						{field:'pact_no',title:'<s:text name="pactInfo.pactNo" />',width:130,align:'center',sortable:'true'},
						{field:'prdt_name',title:'<s:text name="pactInfo.prdtName" />',width:130,align:'center',sortable:'true'},
						{field:'claims_pact_no',title:'<s:text name="pactInfo.claimsPactNo" />',width:100,align:'center',sortable:'true'},
						
						{field:'end_date',title:'<s:text name="pactInfo.endDate" />',width:130,align:'center',sortable:'true'}
						
					]]
				  });			
			
			 <%--  $('#myOpportunityGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					width:620,
					height:350,
					pagination:true,
					idField:'id', 
					url:'listOpportunity.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
								var updateOpportunity='<%=((User)session.getAttribute("loginUser")).getUpdate_opportunity()%>';
								if (updateOpportunity == 1){   
									new_format_value = "<a href='editOpportunity.action?id=" + row.id + "'>" + value + "</a>";
								} else{
							      new_format_value = value;
								} 							  
							   return new_format_value 
			             }  
			            },
						{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'center',sortable:'true'},
						{field:'sales_stage.name',title:'<s:text name="menu.salesStage.title" />',width:80,align:'right',sortable:'true'},
						{field:'opportunity_amount',title:'<s:text name="opportunity.opportunity_amount.label" />',width:80,align:'center',sortable:'true'}		
					]]
				  });			  
			 --%>
			
			
			
		});
	</script>
</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="feature-content">
        <br></br>
        <div region="center" border="false">
          <div id="pp" style="position: relative">
            <div style="width: 100%;">
                <div title="<s:text name='entity.pactInfo.endTime'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myAccountGrid"></table>
                </div>
            </div>
            <%-- <div style="width: 50%;">
                <div title="<s:text name='title.grid.myOpportunities'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myOpportunityGrid"></table>
                </div>
            </div> --%>
          </div>
        </div>
      </div>
    </div>
    <s:include value="../footer.jsp" />

  </div>
</body>
</html>
