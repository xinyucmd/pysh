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
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
	 
	 
	 function submit() {
		 
		 if($("#addObjectForm").form("validate")){
			 			
				 $.messager.confirm("提交确认", "您确认生成合同吗？", function (saveAction) {
					if (saveAction) {
				 	var addObjectForm = document.getElementById('addObjectForm');
				 	addObjectForm.action='contractGen.action';
			     	addObjectForm.submit();

			     	}
	         	});
			 	 
		 }  
		 
	}
	 

	
	 
	 
	 function cancel(){
		 baseCancel("ContractGen");
	 }
	 
	 
	
	 $(document).ready(function () {
		 
 		var merPactNo='<s:property value="pactInfo.pact_no"/>';
		 
		 $("#pactInfo").datagrid({
         	url:'listMerPact.action?merPactNo='+merPactNo,
         	isField : "id",
         	pagination : true,
         	border:false,
         	pagesize : 5,
         	pageList : [5,10,15],
         	title : "pactInfo.xugou_pactinfo_information",
         	rownumbers : true,
         	singleSelect : false,
         	columns : [[ 
         	           {field:'pactNo',title:'<s:text name="pactInfo.pactNo" />',width:100},
		                {field:'cifName',title:'<s:text name="pactInfo.cifName" />',width:100},  
		                {field:'idNo',title:'<s:text name="customer.idNo.label" />',width:120},  
		                {field:'prot',title:'<s:text name="pactInfo.prdtName" />',width:100},  
		                {field:'pact_amt',title:'<s:text name="pactInfo.pactAmt" />',width:100}, 
		                {field:'term_range',title:'<s:text name="pactInfo.term_range" />',width:100}, 
		                {field:'rate',title:'<s:text name="pactInfo.rate" />',width:100},
		                {field:'incomeAmt',title:'<s:text name="pactInfo.incomeAmt" />',width:100},
		                {field:'redemAmt',title:'<s:text name="redeemEntity.redem_amount" />',width:100},
		                {field:'continueAmt',title:'<s:text name="redeemEntity.surplusAmt" />',width:100} ,
		                {field:'startDate',title:'<s:text name="pactInfo.startDate" />',width:100} ,
		                {field:'endDate',title:'<s:text name="pactInfo.endDate" />',width:100}       

         	]]
 
         });
		 
		 
		 $("#ClaimsInfo").datagrid({
	         	url:'listClaimsInfo.action?merPactNo='+merPactNo,
	         	isField : "id",
	         	pagination : true,
	         	border:false,
	         	pagesize : 5,
	         	pageList : [5,10,15],
	         	title : "债权信息",
	         	rownumbers : true,
	         	singleSelect : false,
	         	columns : [[ 
	         	           {field:'claimsNo',title:'<s:text name="债权合同号" />',width:150},
			                {field:'name',title:'<s:text name="债权姓名" />',width:100},  
			                {field:'amt',title:'<s:text name="债权金额" />',width:120}, 
			                {field:'cmt',title:'<s:text name="备注" />',width:120}

	         	]]
	 
	         });
		 
		 
		 
		 
		
		  

	});
	 
	 
	
	 
	

	 
</script>
</head>

<body>
  <div id="page-wrap">
    
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
    
      <div id="shortcuts" class="headerList">
		      
	<!-- saveClose -->	       
		   	    <span style="white-space: nowrap;"> 
		     	   <a id="save_go_btn" href="#" class="easyui-linkbutton" iconCls="icon-save-go" onclick="submit()" plain="true">
		     	   		<s:text name="生成合同" />
		     	   </a>
		        </span> 
	<!-- saveClose -->	       
		   	   
    <!-- cancel -->
	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">
			           		<s:text name="button.cancel" />
			           </a>
	          </span>
      </div>
<!---->

<div id="feature-content">
			<h2>
		    	<s:text name="合同生成" />
		    </h2>
		</div> 
		
<!-- 表单 -->			
	 <div id="feature-content">
	 		 <s:form id="addObjectForm" validate="true"  namespace="/jsp/subscription" method="post">
	 		 
	 		 <s:hidden id="cifName" name="customer.cif_name" value="%{customer.cif_name}" />
	 		  <s:hidden id="birth" name="customer.birth" value="%{customer.birth}" />
	 		  <s:hidden id="contactTel" name="customer.contact_tel" value="%{customer.contact_tel}" />
	 		  <s:hidden id="addr" name="customer.addr" value="%{customer.addr}" />
	 		  <s:hidden id="postcode" name="customer.postcode" value="%{customer.postcode}" />
	 		  <s:hidden id="openDate" name="customer.open_date" value="%{customer.open_date}" />
	 		  <s:hidden id="openOp" name="customer.open_op" value="%{customer.open_op}" />
	 		  <s:hidden id="cmt" name="customer.cmt" value="%{customer.cmt}" />
	 		  <s:hidden id="cmt" name="pactInfo.term_range" value="%{pactInfo.term_range}" />
	 		  <s:hidden id="cosid" name="customer.id" value="%{customer.id}" />
	 		  
	 		  <s:hidden id="pactnos" name="pactNos"  />
	 		  
	 		  <s:hidden id="" name="pactInfo.pact_type" value="%{pactInfo.pact_type}" />
	 		 
	 		  <s:hidden id="prdtName" name="pactInfo.prdt_name" value="%{pactInfo.prdt_name}" />
	 		  <s:hidden id="" name="pactInfo.pact_no" value="%{pactInfo.pact_no}" />
	 	
	 		  
	 		  <s:hidden id="" name="pactInfo.id" value="%{pactInfo.id}" />
	 		  
	 		  
	 		  
	 <div id="tt" class="easyui-tabs">
	     <div title="<s:text name='customer.infomation'/>"  style="padding: 10px;">
	 		 <div class="section-header">
                <span><s:text name="menu.base.information" /></span>
             </div>
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">
   					      <tr> 
            				<td class="td-label"><label class="record-label"><s:text
                                name="pactInfo.cifName"></s:text>:</label></td>
            					
              				<td class="td-value">
              					<label class="record-value"> <s:property value="customer.cif_name" /></label>
              
              
              			</td>
              					 
              					    
              				  
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.sex.label"></s:text>:
            							</label>            							
            					    </td>
            					    <td class="td-value">
            					    <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
            					    	 <s:select id="sex" list="#select.queryWPD('sex')" listKey="opCode" listValue="opCnName" name="customer.sex" value="%{customer.sex}"></s:select>
            					    </span>	
            					    </td>
            					  
          
          					</tr> 
          			           					 	
            				<tr>
            				<!--productName  -->
            						
	            					 <td class="td-label">
	            							<label class="record-label">
	            								<s:text name="customer.idType.label"></s:text>:
	            							</label>	            							
	            					 </td>
	            					  <td class="td-value">
            					     		<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
            					     		 <s:select id="idType" list="#select.queryWPD('ID_TYPE')" listKey="opCode" listValue="opCnName" name="customer.id_type" value="%{customer.id_type}"></s:select>
            					     		</span>
            					      </td>
            				<!-- standardAMT -->
            				
            					     <td class="td-label">
	            							<label class="record-label">
	            								<s:text  name="customer.idNo.label"></s:text>:
	            							</label>	            							
	            					 </td>    		
	            					 
            					    
            					     <td class="td-value">
            					     		<label class="record-value"> <s:property value="customer.id_no" /></label>
            					     </td>  
            					     
            					                					    
            				</tr>
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.cifType.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					    <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
            					     		 <s:select id="cifType" list="#select.queryWPD('CUS_TYPE')" listKey="opCode" listValue="opCnName" name="customer.cif_type" value="%{customer.cif_type}" ></s:select>
            					    </span> 		 
            					    </td>
            			    </tr>
            			</table>    
            <div class="section-header">
                <span><s:text name="customer.contact.label" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.contact.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            		
            					     		<label class="record-value"> <s:property value="customer.contact" /></label>
            					    </td>
            					    
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="contact.tel"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">

            					     		<label class="record-value"> <s:property value="customer.contact_phone" /></label>
            					    </td>
            				
            				</tr>
            				<tr>
            				
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.otherContact.emil"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<label class="record-value"> <s:property value="customer.mail" /></label>
            					    </td>
            				
            				</tr>
            								
			
		
            				<tr>
            				
            				</tr>
            				
            				
           		
          			</table>
          			
          			 			</table>    
            <div class="section-header">
                <span><s:text name="customer.address.label" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.address.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     	<label class="record-value"> <s:property value="customer.addr" /></label>
            					    </td>
            					    
            					    <td class="td-label">
            							       						
            					    </td>
            					    <td class="td-value">
            					     		
            					    </td>
            				
            				</tr>
            				
            				
            				
           		
          			</table>
          			
          	<div class="section-header">
                <span><s:text name="pactInfo.tichengren" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.tichengren"></s:text>:
            						</label>            						
            						</td>
            						<td class="td-value">
            					    <label class="record-value"> <s:property value="pactInfo.me_adult" /></label>
            						</td>
            					    
            					    <td class="td-label">
            							       						
            					    </td>
            					    <td class="td-value">
            					     		
            					    </td>
            				
            				</tr>
            				
            				
            				
           		
          			</table>
          			</div>
          			
          			<div title="<s:text name='entity.pactInfo.label'/>"  style="padding: 10px;">
          			<div class="section-header">
                      <span><s:text name="entity.prod.information" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
   					      <tr>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.prod.name"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    
              						 <label class="record-value"> <s:property value="pactInfo.prdt_name" /></label>
              			
              			
            				</td>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.prod.code"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            		
            					     <label class="record-value"> <s:property value="pactInfo.prdt_no" /></label>
            				</td>
            			</tr>	
            			
            			 <tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.term_range2"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					 
                       <label class="record-value"> <s:property value="pactInfo.term_range" />
                       
            						<label class="record-label">
            							<s:text name="期"></s:text>
            						</label>            						

                       </label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.rate"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.rate" />
            					     		<label class="record-label">
            								%
            								</label> 
            					     </label>
            				</td>
            			</tr>
            	</table>		
            	
            	<div class="section-header">
                      <span><s:text name="entity.pactInfo.label" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
            		
            		  <tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="合同号" ></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            
            					    <label class="record-value"> <s:property value="pactInfo.pact_no" /></label>
            				</td>
            				
            				<td class="td-label">
            						          						
            				</td>
            				<td class="td-value">
            					    
            				</td>
            			</tr>	
            		
            		
            		
            		
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.pactInfo.jine" ></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            
            					        <label class="record-value"> <s:property value="pactInfo.pact_amt" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.cashAmt"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    
            					     <label class="record-value"> <s:property value="pactInfo.cash_amt" /></label>
            				</td>
            			</tr>	
            			
            				<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.incomeAmt"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.income_amt" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.income_Amt"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            				<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
            					    <s:select id="cifType" list="#select.queryWPD('FUNDS_FROM')" listKey="opCode" listValue="opCnName" name="pactInfo.fund_sources" ></s:select>
            				</span>
            				</td>
            			</tr>
            			
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.type_fuxi"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            				<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
            					   <s:select id="" list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode" listValue="opCnName" name="pactInfo.payment_type" ></s:select>
            				</span>
            				</td>
            				<s:if test="pactInfo.fund_sources==2||pactInfo.fund_sources==3">
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.pactInfo.old_pactno"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					 <label class="record-value">
											<s:property value="pactInfo.old_pactno" />
									</label></td>
            			</s:if>
            				
            			
            			</tr>
            			
            			
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.pactInfo.daoqi_xucun"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					
                           <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
                           <s:select list="#select.queryWPD('YES_NO')" listKey="opCode"  name="pactInfo.if_continue" value="pactInfo.if_continue"
                           listValue="opCnName" ></s:select>
                           </span>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.ifWxd"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
                             <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">   
                                <s:select list="#select.queryWPD('YES_NO')" listKey="opCode"  name="pactInfo.if_wxd" value="pactInfo.if_wxd"
                           listValue="opCnName" ></s:select>
            					   
            		        </span>
            				</td>
            			</tr>
            				
            			</table>
            			
            			
            		<div class="section-header">
                      <span><s:text name="main.account.bankinformation" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="menu.bankAccount.name"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.account_name" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="menu.bankAccount.no"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.account_no" /></label>
            				</td>
            			</tr>
            			
            			<tr>
            				<td class="td-label" rowspan=”2″>
            						<label class="record-label">
            							<s:text name="menu.bankAccount.op_bank"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value"  >
            					     <label class="record-value"> <s:property value="pactInfo.account_bank" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							
            						</label>            						
            				</td>
            				<td class="td-value">
                                
                           
            					   
            		
            				</td>
            			</tr>
            			
            			
            				
          			</table>
          			
          			
          		</div>
          		<div title="<s:text name='entity.pactInfo.zhanquan_pipei'/>"  style="padding: 10px;"> 
          			
          		    <table id="ClaimsInfo"  style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
                    </table>
          		     <%-- <table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">

                       <tr>

                    		<td  valign="top">


                    		 	<label class="record-label"> <s:text name="customer.cmt.label" ></s:text>：</label>
							</td>
                            <td  valign="top" >             
                                <label class="record-value">
                  	         	<s:textarea  readonly="true"
                      			name="cmt" rows="5" cssStyle="width:350px;" cssClass="record-value" />
                      			</label>
                   			</td>
                    	</tr>	
                    		
                    	
                    	
                    </table> --%>
          		</div>
          		<s:if test="pactInfo.pact_type==2">
          		<div title="<s:text name='pactInfo.xugou_pactinfo_information'/>"  style="padding: 10px;">
          		    <div class="section-header">
                      <span><s:text name="pactInfo.xugou_pactinfo_information" /></span>
                    </div>
                    <table id="pactInfo"  style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
                    </table>
          		      
          		</div>	
          		</s:if>
          		
          		
              </div>     
          			
          	</s:form>
    </div>
    
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



