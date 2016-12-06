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
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">

	function submit() {
		if ($("#addObjectForm").form("validate")) {
			$.messager.confirm("提交确认", "您确认提交到下一环节吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'redemConfirm.action';
					addObjectForm.submit();

					//baseCancel("RedemConfirm");

				}
			});
		}
	}
	function cancelApply() {
		if ($("#addObjectForm").form("validate")) {
			$.messager.confirm("撤销确认", "您确认撤销此申请吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'undoRedem.action';
					addObjectForm.submit();

					//baseCancel("RedemConfirm");

				}
			});
		}

	}
	 function cancel(){
		 baseCancel("RedemConfirm");
	 }
</script>
</head>

<body>
	<div id="page-wrap">

		<s:include value="../header.jsp" />
		<s:include value="../menu.jsp" />
		<div id="feature">

			<div id="shortcuts" class="headerList">

				<!-- saveClose -->
				<span style="white-space: nowrap;"> <a id="save_go_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-save-go"
					onclick="submit()" plain="true"> <s:text name="confirm.submit" />
				</a>
				</span>
				<!-- saveClose -->

				<!-- cancel
				<span style="white-space: nowrap;"> <a id="cancel_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="cancelApply()" plain="true"> <s:text name="cancel.apply" />
				</a>
				</span> -->
				<!-- cancel -->
				<span style="white-space: nowrap;"> <a id="cancel_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="cancel()" plain="true"> <s:text name="button.cancel" />
				</a>
				</span>
			</div>
			<!---->

			<div id="feature-content">
				<h2>
					<s:text name="entity.redemConfirm.label" />
				</h2>
			</div>

			<!-- 表单 -->
			<div id="feature-content">
				<s:form id="addObjectForm" validate="true"
					namespace="/jsp/redeem" method="post">
					<s:hidden id="prdtName" name="pactInfo.prdt_name"
						value="%{pactInfo.prdt_name}" />
					<s:hidden id="pact_no" name="pactInfo.pact_no" value="%{pactInfo.pact_no}" />
					<s:hidden id="id" name="pactInfo.id" value="%{pactInfo.id}" />
					<s:hidden id="pact_amt" name="pactInfo.pact_amt" value="%{pactInfo.pact_amt}" />
					<s:hidden id="rate" name="pactInfo.rate" value="%{pactInfo.rate}" />
					<s:hidden id="term_range" name="pactInfo.term_range" value="%{pactInfo.term_range}" />
					<s:hidden id="redeemId" name="id" value="%{id}" />
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						<tr>

							<td class="td-label" ><label
								class="record-label"> <s:text name="redem.amt"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-label"><s:property value="%{redeemEntity.redem_amount}"/></label></td>

							<td class="td-label"><label
								class="record-label"> <s:text name="redem.date"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-label"><s:property value="%{redeemEntity.redem_Date}"/></label></td>
							</tr>
							<tr  >	
							<td class="td-label"><label
								class="record-label"> <s:text name="should.get.amt"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-label"><s:property value="%{redeemEntity.pay_amt}"/></label></td>
							<td class="td-label"></td>
							<td class="td-value"></td>
						</tr>
						<tr  >
							<td class="td-label"><label class="record-label"><s:text name="accept.account_name"></s:text>：</label></td>
							<td class="td-value"><label class="record-label"><input name="account_name" id="account_name" value="<s:property value="%{account_name}"/>"/></label></td>
							<td class="td-label"><label class="record-label"><s:text name="accept.account_no"></s:text>：</label></td>
							<td class="td-value"><label class="record-label"><input name="account_no" id="account_no" value="<s:property value="%{account_no}"/>" /></label></td>
							</tr>
						<tr  >
							<td class="td-label"><label class="record-label"><s:text name="accept.account_bank"></s:text>：</label></td>
							<td class="td-value"><label class="record-label"><input name="account_bank" id="account_bank" value="<s:property value="%{account_bank}"/>"/></label></td>
							<td class="td-label"></td>
							<td class="td-value"></td>
						</tr>
					</table>


					<div id="tt" class="easyui-tabs">
	     <div title="<s:text name='entity.customer.label'/>"  style="padding: 10px;">
	 		 <div class="section-header">
                <span><s:text name="menu.base.information" /></span>
             </div>
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">
   					      <tr> 
            				<td class="td-label"><label class="record-label"><s:text
                                name="customer.otherContact.custname"></s:text>：</label></td>
            					
              				<td class="td-value">
              					<label class="record-value"> <s:property value="customer.cif_name" /></label>
              
              
              			</td>
              					 
              					    
              				  
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.sex.label"></s:text>:
            							</label>            							
            					    </td>
            					    <td class="td-value">
            					    	 <s:select id="sex" list="#select.queryWPD('sex')" listKey="opCode" listValue="opCnName" name="customer.sex" value="%{customer.sex}"></s:select>	
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
            								<s:text name="customer.contactPhone.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">

            					     		<label class="record-value"> <s:property value="customer.contact_phone" /></label>
            					    </td>
            				
            				</tr>
            				<tr>
            				
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.email"></s:text>:
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
            					     	<label class="record-value"> <s:property value="customer.mail" /></label>
            					    </td>
            					    
            					    <td class="td-label">
            							       						
            					    </td>
            					    <td class="td-value">
            					     		
            					    </td>
            				
            				</tr>
            				
            				
            				
           		
          			</table>
          			
          	<div class="section-header">
                <span><s:text name="menu.customer.mangerName" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            						<label class="record-label">
            							<s:text name="menu.customer.mangerName"></s:text>:
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
          			
          			<div title="<s:text name='customer.pactInformation'/>"  style="padding: 10px;">
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
            							<s:text name="entity.pactInfo.qishu"></s:text>:
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
            							<s:text name="menu.ProductConfiguration.RETURN_RATE"></s:text>:
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
                      <span><s:text name="customer.pactInformation" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
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
            							<s:text name="entity.pactInfo.xianjinzhuanzhang"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    
            					     <label class="record-value"> <s:property value="pactInfo.cash_amt" /></label>
            				</td>
            			</tr>	
            			
            				<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.pactInfo.licaishouyijine"></s:text>:
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
            							<s:text name="合同号"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.pact_no" /></label>
            				</td>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.paymentType"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
                            <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
            					   <s:select id="" list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode" listValue="opCnName" name="pactInfo.payment_type" ></s:select>
            				</span>
            			
            				</td>
            				
            			
            			</tr>
            			
            			<tr>
            			<td class="td-label">
            						<label class="record-label">
            							<s:text name="起始日期"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.start_date" /></label>
            				</td>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="到期日期"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.end_date" /></label>
            				</td>
            				
            			
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
            							<s:text name="entity.financing.if_wxd"></s:text>:
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
            							<s:text name="main.account.name"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.account_name" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.accountNo"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.account_no" /></label>
            				</td>
            			</tr>
            			
            			<tr>
            				<td class="td-label" rowspan=”2″>
            						<label class="record-label">
            							<s:text name="pactInfo.accountBank"></s:text>:
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
          			<div class="section-header">
                      <span><s:text name="entity.pactInfo.zhanquan_pipei" /></span>
                    </div>
          		
          		     <table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
                    	<tr>

                    		<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.pactInfo.zhanquan_pactInfoNo"></s:text>:
            						</label>            						
            				</td> 
                    		<td class="td-value">
                    		    <label class="record-value"> <s:property value="pactInfo.claims_pact_no" /></label>
                    		</td>
                    		
                    		 <td class="td-label">
            						          						
            				</td> 
                    		<td class="td-value">
                    		   
                    		</td>
                    		
                    	</tr>
                    	
                    	<tr>

                    		<td class="td-label" valign="top">
                    		 	<label class="record-label"> <s:text name="entity.cmt.value" ></s:text>：</label></td>
                            <td class="td-value" valign="top">             
                  	         	<s:textarea  readonly="true"
                      			name="cmt" value="%{pactInfo.cmt}" rows="5" cssStyle="width:350px;" cssClass="record-value" /></td>
                      		<td class="td-mass-update">
                   			</td>
                    		
                    		 <td class="td-label">
            						          						
            				</td> 
                    		<td class="td-value">
                    		   
                    		</td>
                    		
                    	</tr>
                    	
                    	
                    </table>
          		</div>
          		
          		<div title="<s:text name='accept.come_acmInformation'/>"  style="padding: 10px;"> 
          			<div class="section-header">
                      <span><s:text name="accept.come_acmInformation" /></span>
                    </div>
          		
          		     <table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
                    	<tr>

                 		
                    		<td class="td-label">
            						<label class="record-label">
            							<s:text name="accept.come_acmInformation_true"></s:text>:
            						</label>            						
            				</td> 
                    		<td class="td-value">
                    		    <label class="record-value"> <s:property value="pactInfo.cash_amt" /></label>
                    		    <label class="record-label">
            							<s:text name="元"></s:text>
            						</label> 
                    		    
                    		   
                    		</td>
                    		
                    		 <td class="td-label">
            						 <label class="record-label">
            							<s:text name="续购金额"></s:text>:
            						</label>      						
            				</td> 
                    		<td class="td-value">
                    		        <label class="record-value"> <s:property value="pactInfo.income_amt" /></label>
                    		    <label class="record-label">
            							<s:text name="元"></s:text>
            						</label> 
                    		</td>
                    		
                    	</tr>
                    	
                    	<tr>
                    	<td class="td-label">
            						<label class="record-label">
            							<s:text name="accept.come_acm_date"></s:text>:
            						</label>            						
            				</td> 
                    		<td class="td-value">
                    		  <s:if test="pactInfo.cash_amt!=0">
                    		   <label class="record-value"> <s:property value="financingDetails.flow_date" /></label>
                    		   </s:if>
                    		</td>
                    		
                    		 <td class="td-label">
                    		 		<label class="record-label">
            							<s:text name="accept.come_acm"></s:text>:
            						</label> 
            						          						
            				</td> 
                    		<td class="td-value">
                    		   <label class="record-value"> <s:property value="pactInfo.cash_amt" /></label>
                    		   <label class="record-label">
            							<s:text name="元"></s:text>
            						</label> 
                    		</td>
							
							
                    		
                    		
                    	</tr>
                    	
                    	
                    	
                    	
                    	<tr>
                    	<td class="td-label">
            						<label class="record-label">
            							<s:text name="转账人"></s:text>:
            						</label>            						
            				</td> 
                    		<td class="td-value">
                    		   <label class="record-value"> <s:property value="financingDetails.transfer_op" /></label>
                    		</td>
                    		
                    		 <td class="td-label">
                    		 		<label class="record-label">
            							<s:text name="转账账号"></s:text>:
            						</label> 
            						          						
            				</td> 
                    		<td class="td-value">
                    		   <label class="record-value"> <s:property value="financingDetails.transfer_acc" /></label>
                    		</td>

                    		
                    		
                    	</tr>
                    	
                    	
                    </table>
          		</div>
          		<s:if test="pactInfo.pact_type==2">
          		<div title="<s:text name='entity.pactInfo.information'/>"  style="padding: 10px;">
          		    <div class="section-header">
                      <span><s:text name="entity.pactInfo.information" /></span>
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



