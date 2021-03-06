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

	
	 function save() {
         var phone = /^[1]{1}[\d]{10}$/;
         var idNo  = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
         var cif_name = document.getElementById('cif_name1111').value;
		 var id_no =  document.getElementById("id_no1111").value;
		 var contact = document.getElementById("contact1111").value;
		 var contact_phone = document.getElementById("contact_phone1111").value;
		
		  if(cif_name!=""){
						     var params = {
								 	id_no : id_no
									  };
							 $.ajax({
								    type: "POST",
								    url: "chactCustomerIdNo.action",
								    data: params,
								    dataType:"text", 
								    success: function(json){  
								    	var jsonObj=eval("("+json+")"); 
								    	$.each(jsonObj, function (i, item) {
								    		if(item.data == "Yes"){
								    			 if(contact !=""){
								 					if(phone.test(contact_phone)){
								 						 baseSave("Customer");	
								 					}else{
								 						alert("手机号码输入有误");
								 					}
								 				 }else{
								 					alert("联系人不能为空");
								 				 }
								    		}else{
								    			alert("身份证号码已存在");
								    		}
								    	}); 
								    },
								    error: function(json){
								      alert("json=" + json);
								      return false;
								    }
								}); 
				  
		  }else{
			  alert("用户名为空");
		  } 
		}
		 
	 function saveClose() {
		baseSaveClose("Customer");
		}
	 function cancel() {
		baseCancel("Customer");
		}
	 function getSelected(){
		 var row = $("#mainAccount").datagrid("getSelected");
		 var main_accounr = row.account_no;
			 $('#subAccount').datagrid({
				 url:"listCustSubAccountFull.action?account_no="+main_accounr
			 }); 
	 }
	 $(document).ready(function() {	 
		 $('#createBrith').datebox('setValue', '<s:property value="cust.birth"/>');
		
		 $('#ownerID').combogrid('setText',
			'<s:property value="ownerName"/>');
		 
		/*  $("#ownerID").combogrid({
				onSelect : function(n, o) {
					var grid = $("#ownerID").combogrid("grid");//获取表格对象
					var row = grid.datagrid('getSelected');//获取行数据

					alert(row.id+"  :  "+row.name);
					
					var open_op = '<s:property value="cust.getOpen_op()"/>';;
					alert(open_op);
				}
			});		 */ 
		 
	    var id='<s:text  name="cust.id"/>';
		if(id != "cust.id"){
		 $('#custPactInfo').datagrid({  
		 	  url:"listCustPactInfoFull.action?id="+id
		   }) ;
		 $('#mainAccount').datagrid({
			 url:"listCustMainAccountFull.action?id="+id
		 });
		  $('#subAccount').datagrid({
			 url:"listCustSubAccountFull.action?id="+id
		 }); 
		} 
	 })
</script>
</head>

<body>
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    
    <div id="feature">
      
      <div id="shortcuts" class="headerList">
    <!-- save -->
     <s:if test="#session.loginUser.role == 'R02'">
		        <span style="white-space: nowrap;">
		           <a id="save_accept_btn" href="#" class="easyui-linkbutton" iconCls="icon-save-accept" onclick="save()" plain="true">
		           		<s:text name="button.save" />
		           </a>
		        </span> 
	<!-- saveClose -->
		   	    <span style="white-space: nowrap;"> 
		     	   <a id="save_go_btn" href="#" class="easyui-linkbutton" iconCls="icon-save-go" onclick="saveClose()" plain="true">
		     	   		<s:text name="button.saveClose" />
		     	   </a>
		        </span> 
		       </s:if>
    <!-- cancel -->
	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">
			           		<s:text name="button.cancel" />
			           </a>
	          </span>
      </div>
      
		<div id="feature-title">
        <s:if test="cust!=null && cust.id!=null">
			       <script type="text/javascript">
			       $(document).ready(function() {
			    	  	 document.getElementById("cif_name1111").setAttribute("readonly","readonly");
			    	  	 document.getElementById("id_no1111").setAttribute("readonly","readonly");
			       })
			       </script>
			        <s:if test="#session.loginUser.role == 'R02'">
		          <h2>
		            <s:text name="title.updateCustomer"/>
		          </h2>
		          </s:if>
		          <s:else>
		          <h2>
		            <s:text name="menu.customer.infomation"/>
		          </h2>
		          
		          </s:else>
        </s:if>
        <s:else>
			            <h2>
			              <s:text name="title.addCustomer" />
			            </h2>
        </s:else>
      </div>
<!-- 表单 -->	
<div id="feature-content">
	 		 <s:form id="addObjectForm" validate="true"  namespace="/jsp/investor" method="post" onsubmit="return chackForm()">
	 		   <s:hidden id="customerId" name="cust.id" value="%{cust.id}" />
					<s:hidden id="saveFlag" name="saveFlag" />
          				<s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />
	<table style="padding: 10px;" cellspacing="10" cellpadding="0"
            width="100%">
            <tr>
              <!--cif_name  -->
            						 <td class="td-mass-update">
            						 			 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="productName" />
               						 </td>
	            					 <td class="td-label">
	            							<label class="record-label">
	            								<s:text name="customer.cifName.label"></s:text>:
	            							</label>	            							
	            					 </td>
	            					  <td class="td-value">
	            					  		<input id="cif_name1111" name="cust.cif_name"
	            					  			value="<s:property value="cust.cif_name" />"
	            					  			
	            					  			class="easyui-validatebox textbox" 
	            					  			
	            					  			data-options="required:true"
	            					  			
	            					  			
	            					  		  />
            					      </td>
                
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="owner" /></td>
              <td class="td-label"><%-- <label class="record-label"><s:text
                    name="menu.customer.mangerName"></s:text>：</label> --%></td>
            
              <td class="td-value"><%-- <select id="ownerID"
                class="easyui-combogrid record-value" name="id"
                style="width: 180px;"
                data-options="  
					            panelWidth:520,  
					            idField:'id',  
					            textField:'name',  
					            url:'<s:url action="listUser" namespace="/jsp/system"/>',
		                        loadMsg: '<s:text name="datagrid.loading" />',
		                        pagination : true,
		                        pageSize: 10,
		                        pageList: [10,30,50],
				                fit: true,
					            mode:'remote',
					            columns:[[  
					                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'name',title:'<s:text name="entity.name.label" />',width:100},  
					                {field:'title',title:'<s:text name="entity.title.label" />',width:120},  
					                {field:'department',title:'<s:text name="entity.department.label" />',width:100},
					                {field:'status.name',title:'<s:text name="entity.status.label" />',width:100}   
					            ]]  
					        ">
              </select> --%></td>
              <%-- 
                <td class="td-value">
              		<input readonly="readonly"
	            			value="<s:property value="name" />"
	            				class="easyui-validatebox"  
	            	  /> 
              </td>--%>
            </tr>
          </table>	
           <div id="tt" class="easyui-tabs">
             	 <div title="<s:text name='menu.customer.infomation'/>" style="padding: 10px;">
				 		<div class="section-header">
       <!-- 基本信息 -->        				 <span><s:text name="span.basic" /></span>
       					</div>
				 		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
             			<tr>
          
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="prymentType" />
               						 </td>
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.cifType.label"></s:text>:
            							</label>            							
            					    </td>
            					    <td class="td-value">
            					     		<s:select  name="cust.cif_type" list="#select.queryWPD('CUS_TYPE')" listKey="opCode" listValue="opCnName"/>
            					    </td>
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="standardAMT" />			
               						 </td>      		
            					     <td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.sex.label"></s:text>:
            							</label>            								
            					     </td>
            					     <td class="td-value">
            					     		  <s:select name="cust.sex" list="#select.queryWPD('sex')" listKey="opCode" listValue="opCnName"/>               					     	            					           
            					     </td> 
            				</tr>
             				 <tr>
     
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="stageDays" />
               						 </td>
            						 <td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.idType.label"></s:text>:
            							</label>            							
            					    </td>
            					    <td class="td-value">            					     
            					     		<s:select name="cust.id_type" list="#select.queryWPD('ID_TYPE')" listKey="opCode" listValue="opCnName"  required="true" missingMessage="不能为空"/>
            					    </td>
           
            				 		 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="returnRate" />
               						 </td>
            					   	<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.idNo.label"></s:text>:
            							</label>            							
            					    </td>
            					     <td class="td-value">
            					     		<input 
            					     			id = "id_no1111"
            					     			name="cust.id_no"
	            					  			value="<s:property value="cust.id_no" />"
	            					  			class="easyui-validatebox"  
	            					  			required="true" 
	            					  			data-options="required:true,validType:'length[15,18]'"
	            					  		    class="record-value"
	            					  		   
	            					  		    />
            					    </td>  	
            				</tr>
                          <tr>
          
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="advanceRedeem" />
               						 </td> 
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.birth.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
           								 <input name="cust.birth"
           									    value="<s:property value="cust.birth" />"
	            					  			class="easyui-datebox record-value"  
	            					  			required="true" 
	            					  			missingMessage="<s:text name="menu.massage.notnull"/>"
	            					  		  />
            					    </td>  
       
         							<td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="rates" />
               						 </td>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.address.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input name="cust.addr"
           									    value="<s:property value="cust.addr" />"
	            					  			class="easyui-validatebox"  
	            					  			required="true" 
	            					  			missingMessage="<s:text name="menu.massage.notnull"/>"
	            					  		  />
            					    </td>		
                          </tr>
          			</table> 
          			 <div class="section-header">
   <!-- 联系方式 -->			   <span><s:text name="customer.relationInformation" /></span>
             		 </div>
             		 <div class="easyui-accordion" style="width:1400px;height:200px;">
             		  		<div title="<s:text name="menu.contacts.self"/>" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
									<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
             		 		<tr>
		             		 		<td class="td-label">
		            							<label class="record-label">
		            								<s:text name="customer.contact.name"></s:text>:
		            							</label>            						
		            				</td>
		            				 <td class="td-value">
		            					     		<input 
		            					     			id = "contact1111"
		            					     		 	name="cust.contact"
		           									    value="<s:property value="cust.contact" />"
			            					  			class="easyui-validatebox"  
			            					  			required="true" 
			            					  			missingMessage="<s:text name="menu.massage.notnull"/>"
			            					  		  />
		            				</td>
             		 		</tr>
             		 		<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.contactTel.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input name="cust.contact_tel"
           									    value="<s:property value="cust.contact_tel" />"
	            					  			class="easyui-validatebox"  
	            					  			required="true" 
	            					  			missingMessage="<s:text name="menu.massage.notnull"/>"
	            					  		  />
            					    </td>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.contactPhone.label"></s:text>:
            							</label>            						
            					    </td>
            					     <td class="td-value">
            					     		<input
            					     			id = "contact_phone1111" 
            					     		 	name="cust.contact_phone"
           									    value="<s:property value="cust.contact_phone" />"
	            					  			class="easyui-validatebox"  
	            					  			required="true" 
	            					  			missingMessage="<s:text name="menu.massage.notnull"/>"
	            					  		  />
            					    </td>
            					    </tr>
            					    <tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.mail.label"></s:text>:
            							</label>            						
            					    </td>
            					     <td class="td-value">
            					     		<input name = "cust.mail" 
            					     		 value = "<s:property value = "cust.mail"/>"
            					     		 class="easyui-validatebox"
            					     		 validType="email" 
            					     		 invalidMessage="<s:text name="menu.massage.emil"/>"
            					     		/>
            					    </td>   
            					    
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.customer.postcode"></s:text>:
            							</label>            						
            					    </td>
            					     <td class="td-value">
            					     		<input name = "cust.postcode" 
            					     		 value = "<s:property value = "cust.postcode"/>"
            					     		 class="easyui-validatebox"
            					     		/>
            					    </td>  
            					    
             				 </tr>
             		 </table>
							</div>
							<div title="<s:text name="customer.otherContact.people"/>" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
								 <table  id="feerateTable1" name="feerateTable1"  width="100%">
								 <tr>				
											<td class="td-label">
													<label class="record-label">
													<s:text name="customer.otherContact.name"/>:
													</label>
											</td><td>
											<input   name = "contacts.contact_name" 
													 value="<s:property value="contacts.contact_name" />"												
													 class="easyui-validatebox textbox" 
													 data-options="required:true,validType:'length[2,10]',novalidate:true"/>
											</td>
									 		<td class="td-label">
									 				<label class="record-label">
															<s:text name="customer.otherContact.sex"/>:
													</label>
									 		</td>
									 		<td>
									 			<s:select name="contacts.contact_sex" list="#select.queryWPD('sex')" listKey="opCode" listValue="opCnName"/>  
									 		</td>
								</tr>
								<tr>
									 		<td class="td-label">
									 				<label class="record-label">
															<s:text name = "customer.otherContact.guanxi"/>：
													</label>
									 		</td><td>
									 				<input	name = "contacts.yuliu_1" 
									 						 value="<s:property value="contacts.yuliu_1" />"	
									 						 class="easyui-validatebox textbox" 
									 						 data-options="required:true,novalidate:true"/>
									 		</td>
								
									 		<td class="td-label">
										 		<label class="record-label">
																<s:text name="customer.otherContact.contactPhone"/>:
														</label>
									 		</td><td>
									 		<input  name="contacts.contact_phone" 
									 		 					value="<s:property value="contacts.contact_phone" />"	
									 							class="easyui-validatebox textbox" 
									 							data-options="required:true,novalidate:true"/></td>
								 </tr>
											
											
								 <tr>
								 		<td class="td-label">
									 		<label class="record-label">
															<s:text name = "customer.otherContact.emil"/>：
													</label>
									 		</td><td>
									 		<input name = "contacts.contact_mail" 
														 		value="<s:property value="contacts.contact_mail" />"	
									 							class="easyui-validatebox textbox" 
									 							data-options="required:true,validType:'email',novalidate:true"/></td>
									 		<td class="td-label">
									 		
									 		
									 		<label class="record-label">
															<s:text name = "customer.address.label"/>:
													</label>
									 		</td><td>
									 		<input	name = "contacts.contact_addr"
									 							 value="<s:property value="contacts.contact_addr" />"	
									 							class="easyui-validatebox textbox" 
									 							data-options="required:true,novalidate:true"/></td>
								 </tr>
								 </table>	
							</div>
             		 </div>
				 </div>
             	 <div  id="bb" title="<s:text name='customer.pactInformation'/>" style="padding: 10px;">
					     <table id="custPactInfo" class="easyui-datagrid" title="<s:text name='customer.custPactInformation'/>" style="width:1280px; height:250px" 
					       		data-options="
					       		pagination : true,
					       		pagesize : 5, 
                      			pageList : [5,10,15],
					       		singleSelect:true,
					       		collapsible:true,
					       		method:'post'">
											 <thead>
											 <tr>
													<th data-options="field:'cif_no',width:80,align:'right'"><s:text name="pactInfo.cifNo" /></th>
													<th data-options="field:'cif_name',width:80,align:'right'"><s:text name="pactInfo.cifName" /></th>
													<th data-options="field:'pact_no',width:80"><s:text name="pactInfo.pactNo" />	</th>
													<th data-options="field:'prdt_no',width:80,align:'right'"><s:text name="pactInfo.prdtNo" /></th>
													<th data-options="field:'prdt_name',width:80"><s:text name="pactInfo.prdtName" />	</th>
													<th data-options="field:'term_range',width:80,align:'right'"><s:text name="pactInfo.termRange" /></th>
													<th data-options="field:'rate',width:80"><s:text name="pactInfo.rate" />	</th>
													<th data-options="field:'pact_amt',width:80,align:'right'"><s:text name="pactInfo.pactAmt" /></th>
													<th data-options="field:'cash_amt',width:80"><s:text name="pactInfo.cashAmt" />	</th>
													<th data-options="field:'income_amt',width:80,align:'right'"><s:text name="pactInfo.incomeAmt" /></th>
													<th data-options="field:'payment_type',width:80"><s:text name="pactInfo.paymentType" /></th>
													<th data-options="field:'start_date',width:80"><s:text name="pactInfo.startDate" />	</th>
													<th data-options="field:'end_date',width:80,align:'right'"><s:text name="pactInfo.endDate" /></th>
													<th data-options="field:'if_continue',width:80"><s:text name="pactInfo.ifContinue" />	</th>
													<th data-options="field:'sts',width:80"><s:text name="pactInfo.sts" />	</th>
											</tr>
										</thead>
						</table>
             	 </div>
             	 
             	 <div  id="bb" title="<s:text name='main.account.information'/>" style="padding: 10px;">
					     <table id="mainAccount" class="easyui-datagrid" title="<s:text name='main.account.mainInformation'/>" style="width:1280px; height:250px" 
					       		data-options="
					       		pagination : true,
					       		pagesize : 5, 
                      			pageList : [5,10,15],
					       		singleSelect:true,
					       		collapsible:true,
					       		method:'post'">
											 <thead>
											 <tr>
													<th data-options="field:'account_no',width:80,align:'right'"><s:text name="main.account.account_no" /></th>
													<th data-options="field:'cif_name',width:80,align:'right'"><s:text name="main.account.cif_name" /></th>
													<th data-options="field:'open_date',width:80"><s:text name="main.account.open_date" />	</th>
													<th data-options="field:'open_op',width:80,align:'right'"><s:text name="main.account.open_op" /></th>
													<th data-options="field:'status',width:80"><s:text name="main.account.status" /></th>
											</tr>
										</thead>
						</table>
						<table id="subAccount" class="easyui-datagrid" title="<s:text name='main.account.subInformation'/>" style="width:1200px; height:250px" 
					       		data-options="
					       		pagination : true,
					       		pagesize : 5, 
                      			pageList : [5,10,15],
					       		singleSelect:true,
					       		collapsible:true,
					       		method:'post'">
											 <thead>
											 <tr>
													<th data-options="field:'sub_no',width:60,align:'right'"><s:text name="main.account.subAccount_no" /></th>
													<th data-options="field:'pact_no',width:60,align:'right'"><s:text name="pactInfo.pactNo" /></th>
													<th data-options="field:'cash_amt',width:60"><s:text name="main.account.cash_amt" />	</th>
													<th data-options="field:'renew_amt',width:60,align:'right'"><s:text name="main.account.remew_amt" /></th>
													<th data-options="field:'rate',width:60"><s:text name="pactInfo.rate" />	</th>
													<th data-options="field:'prdt_no',width:60,align:'right'"><s:text name="pactInfo.prdtNo" /></th>
													<th data-options="field:'prdt_name',width:60"><s:text name="pactInfo.prdtName" /></th>	
													<th data-options="field:'term',width:60"><s:text name="pactInfo.termRange" />	</th>
													<th data-options="field:'payment_type',width:60,align:'right'"><s:text name="pactInfo.paymentType" /></th>
													<th data-options="field:'income_amt',width:60"><s:text name="pactInfo.incomeAmt" /></th>	
													<th data-options="field:'redeem_amt',width:60"><s:text name="subaccount.redeemAmt" /></th>	
													<th data-options="field:'if_wxd',width:40"><s:text name="pactInfo.ifWxd" /></th>	
													<th data-options="field:'end_date',width:100"><s:text name="entity.end_date.label" /></th>	
													<th data-options="field:'start_date',width:100"><s:text name="entity.start_date.label" /></th>	
													<th data-options="field:'sts',width:60"><s:text name="pactInfo.sts" /></th>	
													<th data-options="field:'open_date',width:100"><s:text name="main.account.open_date" /></th>	
													<th data-options="field:'close_date',width:60"><s:text name="main.account.close_date" /></th>	
													<th data-options="field:'close_op',width:60"><s:text name="main.account.close_op" /></th>	
													<th data-options="field:'cmt',width:60"><s:text name="customer.cmt.label" /></th>	
											</tr>
										</thead>
						</table>
             	 </div>
          			 
           </div>	
          </s:form>
    </div>
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



