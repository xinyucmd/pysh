<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
 <s:action name="select" id="select" namespace="/jsp/select"></s:action>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
  href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
	
	
	 function cancel() {
		baseCancel("BankAccount");
	 }
	 
	 function getSelected(){
		 var row = $("#bankcus").datagrid("getSelected");
		 var bank_account_id = row.bank_account_id;
		 var bank_account_sts = row.sts;
		if(bank_account_sts=="停用"){
			 $('#bankcus').datagrid({  
			 	  url:"listBankAccountSts.action?id=" + bank_account_id
			   }) ;
		}else{
			alert("此账户启用中");
		}
	 }
	 
	 $(document).ready(function() {	 
		
		 $('#customerId').combogrid('setValue',
							'<s:property value="cif_id"/>');
		 $('#customerId').combogrid('setText',
							'<s:property value="cif_name"/>');
		
		 $("#customerId").combogrid({
			 	onSelect: function (n,o) {
			 		  var grid=$("#customerId").combogrid("grid");//获取表格对象
					  var row = grid.datagrid('getSelected');//获取行数据
					 
					  var id = row.id;	  
					  $('#bankcus').datagrid({  
					 	  url:"listOddBankAccount.action?id=" + id
					   }) ;
			 	}
	    });
	   /*  $("#bankcus").datagrid({  
	         onSelect:function(n,o) {     	
	              //var row = $("#bankcus").datagrid("getSelected");
	              //var row = $("#bankcus").datagrid("getSelected");
	     		  //alert(row.bank_account_name); 
	             
	          }
	    
		}); */
		 
	 	var id='<s:property value="cif_id"/>';
		if(id!=""){
				$('#bankcus').datagrid({  
			 	  url:"listOddBankAccount.action?id=" + id  
				}) ;
		}
	 }
	 )
</script>
</head>

<body>
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <div id="shortcuts" class="headerList">
  
    <!-- cancel -->
	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">
			           		<s:text name="button.close" />
			           </a>
	          </span>
      </div>
		
<!-- 表单 -->			
	 <div id="feature-content">
	 		 <s:form id="addObjectForm" validate="true"  namespace="/jsp/financing" method="post">
   					<s:hidden id="bankAccountId" name="bankAccount.bank_account_id" value="%{bankAccount.bank_account_id}" />
        			  <s:hidden id="saveFlag" name="saveFlag" />
          				<s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />
          			
          		
          	<table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
		           <!-- customer -->
		            <tr>	
		             <td class="td-mass-update">
		            	<input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate" value="customer" />
              		 </td>           			                			     
			         <td class="td-label">
			             	 <label class="record-label">
			             				 <!-- 客户 -->
			             	 	<s:text name="customer"></s:text>：
			             	 </label>
			         </td>
			         <td class="td-value">
				              <select  id="customerId"  required="true"
				              class="easyui-combogrid record-value" name="bankAccount.customer.id"  style="width: 180px;"
				                data-options="  
									            panelWidth:520,  
									            idField:'id',  
									            textField:'cif_name',  
									            url:'<s:url action="listCustomer" namespace="/jsp/investor"/>',
						                        loadMsg: '<s:text name="datagrid.loading" />',
						                        pagination : true,
						                        pageSize: 10,
						                        pageList: [10,30,50],
								                fit: true,
									            mode:'remote',
									            columns:[[  
									                {field:'id',title:'<s:text name="customer.id.label" />',width:60 },  
									                {field:'cif_name',title:'<s:text name="customer.cifName.label" />',width:120}           
									            ]]  
									        ">
				              	</select>
			            </td>
			            
			            <td class="td-mass-update">
		            	<input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate" value="customer" />
              		 </td>           			                			     
			         
		            </tr>
          </table>	
          		
        <div id="tt" class="easyui-tabs">
     
		<div id="oddBank" title="<s:text name='menu.oddBankAccount.information'/>" style="padding: 10px;"  >
			      <table>
			        <tr>
			       		 <table id="bankcus" class="easyui-datagrid" title="<s:text name='menu.oddBankAccount.lable'/>" style="width:700px; height:250px" 
			       		 data-options="singleSelect:true,collapsible:true,method:'post'">
									 <thead>
									 <tr>
									 		<th data-options="field:'bank_account_id',width:80"><s:text name="menu.bankAccount.id" />	</th>
										    <th data-options="field:'cif_name',width:100"><s:text name="customer.cifName.label" />	</th>
											<th data-options="field:'bank_account_name',width:100"><s:text name="menu.bankAccount.name" /></th>
											<th data-options="field:'bank_account_no',width:80,align:'right'"><s:text name="menu.bankAccount.no" /></th>
											<th data-options="field:'bank_account_addr',width:80,align:'right'"><s:text name="menu.bankAccount.address" /></th>
											<th data-options="field:'sts',width:80,align:'right'"><s:text name="menu.bankAccount.STS" /></th>
											<th data-options="field:'op_no',width:80,align:'right'"><s:text name="menu.bankAccount.opno" /></th>
									</tr>
								</thead>
							</table>
						</td>  	
					</tr>
					</table>
					<a href="#" class="easyui-linkbutton" onclick="getSelected()">启用</a>
			</div>  
			</div>							
          	</s:form>
    </div>
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



