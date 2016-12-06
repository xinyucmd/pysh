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
$(document).ready(function () {
	 
	var merPactNo='<s:property value="pactInfo.pact_no"/>';
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
            {field:'amt',title:'<s:text name="债权金额" />',width:120} 
     

]]

});  });
	function submit() {
		if ($("#addObjectForm").form("validate")) {
			$.messager.confirm("提交确认", "您确认撤销此提前赎回申请吗？", function(saveAction) {
				if (saveAction) {
					var addObjectForm = document
							.getElementById('addObjectForm');
					addObjectForm.action = 'cancelAdvRedem.action?pact_no='+$("#pact_no").val();
					addObjectForm.submit();

					

				}
			});
		}
	}
	
	 function cancel(){
		 baseCancel("CancelAdvanceRedem");
	 }

	function showAmt(select) {
		var if_continue = $("#if_continue").val();
		var traget =  document.getElementById("amtTr");
		var pact_amt =  $("#pact_amt").val();
		var term_range=$("#term_range").val();
		var rate=$("#rate").val();
		if (if_continue == '10003') {//是
			traget.style.display = "block";
		}else{
			alert("none");
			traget.style.display == "none"
		}
		var bxhj ;
		var monthRate=rate/1200;
		bxhj=pact_amt*(1 +monthRate*term_range) ;
		$("#bxhj").val(bxhj) ;
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
					onclick="submit()" plain="true"> <s:text name="确认撤销" />
				</a>
				</span>
				<!-- saveClose -->

				<!-- cancel -->
				<span style="white-space: nowrap;"> <a id="cancel_btn"
					href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="cancel()" plain="true"> <s:text name="取消" />
				</a>
				</span>
			</div>
			<!---->

			<div id="feature-content">
				<h2>
					<s:text name="撤销提前赎回申请 " />
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

							<td class="td-label" valign="top"><label
								class="record-label"> <s:text name="赎回金额"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="%{redeemEntity.redem_amount}"/>元</label></td>
                            <td class="td-label"> <label
								class="record-label"><s:text name="赎回类型"></s:text>：
							</label></td>
							<td class="td-value"><span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
									<s:select id="sex"
											list="#select.queryWPD('REDEM_TYPE')" listKey="opCode"
											listValue="opCnName" name="RedeemEntity.redem_type"
											></s:select>
									</span></td>
						</tr>
						
						<tr>

							<td class="td-label" valign="top"><label
								class="record-label"> <s:text name="赎回日期"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-value"><s:property value="redeemEntity.redem_Date"/></label></td>
                            <td class="td-label"> <label
								class="record-label"></td>
							<td class="td-value"></td>
						</tr>
						
					</table>


					<div id="tt" class="easyui-tabs">
	     <div title="<s:text name='客户信息'/>"  style="padding: 10px;">
	 		 <div class="section-header">
                <span><s:text name="基本信息" /></span>
             </div>
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">
   					      <tr> 
            				<td class="td-label"><label class="record-label"><s:text
                                name="客户姓名"></s:text>：</label></td>
            					
              				<td class="td-value">
              					<label class="record-value"> <s:property value="customer.cif_name" /></label>
              
              
              			</td>
              					 
              					    
              				  
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="性别"></s:text>:
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
	            								<s:text name="证件类型"></s:text>:
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
	            								<s:text  name="证件号码"></s:text>:
	            							</label>	            							
	            					 </td>    		
	            					 
            					    
            					     <td class="td-value">
            					     		<label class="record-value"> <s:property value="customer.id_no" /></label>
            					     </td>  
            					     
            					                					    
            				</tr>
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="客户类型"></s:text>:
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
                <span><s:text name="联系人" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="联系人"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            		
            					     		<label class="record-value"> <s:property value="customer.contact" /></label>
            					    </td>
            					    
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="电话"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">

            					     		<label class="record-value"> <s:property value="customer.contact_phone" /></label>
            					    </td>
            				
            				</tr>
            				<tr>
            				
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="电子邮箱"></s:text>:
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
                <span><s:text name="地址" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="地址"></s:text>:
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
                <span><s:text name="提成人" /></span>
             </div>    
            			    
            			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">  
            				<tr>
            						<td class="td-label">
            						<label class="record-label">
            							<s:text name="提成人"></s:text>:
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
          			
          			<div title="<s:text name='合同信息'/>"  style="padding: 10px;">
          			<div class="section-header">
                      <span><s:text name="产品信息" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
   					      <tr>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="产品名称"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    
              						 <label class="record-value"> <s:property value="pactInfo.prdt_name" /></label>
              			
              			
            				</td>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="产品编码"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            		
            					     <label class="record-value"> <s:property value="pactInfo.prdt_no" /></label>
            				</td>
            			</tr>	
            			
            			 <tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="合同期数"></s:text>:
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
            							<s:text name="年化收益率"></s:text>:
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
                      <span><s:text name="合同信息" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="合同金额" ></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            
            					        <label class="record-value"> <s:property value="pactInfo.pact_amt" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="现金转账金额"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    
            					     <label class="record-value"> <s:property value="pactInfo.cash_amt" /></label>
            				</td>
            			</tr>	
            			
            				<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="理财收益金额"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.income_amt" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="资金来源"></s:text>:
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
            							<s:text name="付息方式"></s:text>:
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
            							<s:text name="到期续存"></s:text>:
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
            							<s:text name="是否转入微信贷"></s:text>:
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
                      <span><s:text name="银行账户信息" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="银行账户名"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.account_name" /></label>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="银行账号"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <label class="record-value"> <s:property value="pactInfo.account_no" /></label>
            				</td>
            			</tr>
            			
            			<tr>
            				<td class="td-label" rowspan=”2″>
            						<label class="record-label">
            							<s:text name="开户行"></s:text>:
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
          		     <table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">

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
                    		
                    	
                    	
                    </table>
          		</div>
          		
          		<div title="<s:text name='到账信息'/>"  style="padding: 10px;"> 
          			<div class="section-header">
                      <span><s:text name="到账信息" /></span>
                    </div>
          		
          		     <table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
                    	<tr>

                 		
                    		<td class="td-label">
            						<label class="record-label">
            							<s:text name="应到账金额"></s:text>:
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
            							<s:text name="到账日期"></s:text>:
            						</label>            						
            				</td> 
                    		<td class="td-value">
                    		  <s:if test="pactInfo.cash_amt!=0">
                    		   <label class="record-value"> <s:property value="financingDetails.flow_date" /></label>
                    		   </s:if>
                    		</td>
                    		
                    		 <td class="td-label">
                    		 		<label class="record-label">
            							<s:text name="到账金额"></s:text>:
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
          		<div title="<s:text name='续购合同信息'/>"  style="padding: 10px;">
          		    <div class="section-header">
                      <span><s:text name="续购合同信息" /></span>
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



