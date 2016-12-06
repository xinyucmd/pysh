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
		 baseCancel("PactInfo");
		}
	 $(document).ready(function() {	 
		 $('#createStartDate').datebox('setValue', '<s:property value="pactInfo.start_date"/>');
		 $('#createEndDate').datebox('setValue','<s:property value="pactInfo.end_date"/>');
		 
		 $("#payHis").datagrid({
	         	url:'listPactPayHis.action?pactNo=<s:property value="pactInfo.pact_no" />' ,
	         	pagination : true,
	         	border:false,
	         	pagesize : 5,
	         	pageList : [5,10,15],
	         	title : "回款历史信息",
	         	rownumbers: true,              
	         	columns : [[
	         	                {field:'pactNo',title:'<s:text name="合同号" />',width:150},
				                {field:'term',title:'<s:text name="期数" />',width:50},  
				                {field:'settleDate',title:'<s:text name="回款时间" />',width:120},  
				                {field:'reAmt',title:'<s:text name="回款金额" />',width:100}
	         	]]
			 });
		 
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
	 
	         });
		 
	 })
	 
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
			           		<s:text name="button.save.close" />
			           </a>
	          </span>
      </div>
      <div id="feature-title">
        <s:if test="pactInfo!=null && pactInfo.id!=null">
		          <h2>
		            <s:text name="pactInfo.selectPact"/>
		          </h2>
        </s:if>
      </div>
      
<!-- 表单 -->	
<div id="feature-content">
 <s:form id="addObjectForm" validate="true"  namespace="/jsp/subscription" method="post">
			<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
             			<tr>
            			<!-- 客户名 -->		 
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.cifName"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            							<%-- <s:property value="pactInfo.cif_name"/> --%>
            					     		 <input id="cif_name" value="<s:property value="pactInfo.cif_name"/>" readonly="readonly" /> 
            					</td>  
            			</tr>
          	</table>
			<div id="tt" class="easyui-tabs">
				 		<div title="<s:text name='pactInfo.inflomation'/>" style="padding: 10px;">
				 		<div class="section-header">
   <!-- 账户信息 -->			   <span><s:text name="menu.bankAccount.infomation" /></span>
             		 	</div>
             			 <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
             		 		<tr>
    							<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.accountNo"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            							<s:property value="pactInfo.account_no" />
            					     		<%-- <input id="account_no" value="<s:property value="pactInfo.account_no" />" readonly="readonly" /> --%>
            					</td> 
            					
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.accountName"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            							<s:property value="pactInfo.account_name" />
            					     		<%-- <input id="account_name" value="<s:property value="pactInfo.account_name" /> "readonly="readonly" /> --%>
            					</td>   
            						
            			</tr>
                         <tr>
          						<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.accountBank"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            							<s:property value="pactInfo.account_bank"/>
            					     		<%-- <input id="account_bank" value="<s:property value="pactInfo.account_bank"/>" readonly="readonly" /> --%>
            					</td> 
            					
            					 
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.subNo"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
            					     		<s:property value="sub_no"/>
            					     		<%-- <input id="sub_no" value="<s:property value="sub_no"/>" readonly="readonly" /> --%>
            					</td>  
                         </tr>
             			 </table>
          	   <div class="section-header">
   <!-- 合同信息 -->	 <span><s:text name="pactInfo.inflomation" /></span>
             		 </div>
             		 	<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
		             		 <tr>	    
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.pactNo"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            				<s:property value="pactInfo.pact_no"/>
	            				<%-- <input id="pact_no" value="<s:property value="pactInfo.pact_no"/>" readonly="readonly" /> --%>
            					</td>  
		            		</tr>
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.prdtNo"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            					<s:property value="pactInfo.prdt_no"/>
	            					<%-- <input id="prdt_no" value="<s:property value="pactInfo.prdt_no"/>" readonly="readonly" /> --%>
            					</td>  
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.prdtName" ></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
            					     		<s:property value="pactInfo.prdt_name"/>
            					     		<%-- <input id="prdt_name" value="<s:property value="pactInfo.prdt_name"/>" readonly="readonly" /> --%>
            					</td>  		
		            		</tr>
		            		
		            		
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text  name="pactInfo.termRange"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            				<s:property value="pactInfo.term_range"/>
	            				
	            						<label
											class="record-label"> <s:text name="期"></s:text>
										</label>
            					     		<%-- <input id="term_range" value="<s:property value="pactInfo.term_range"/>" readonly="readonly" /> --%>
            					</td>  
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.rate"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
            					     	<s:property value="pactInfo.rate"/>
            					     	<label
											class="record-label"> <s:text name="%"></s:text>
										</label>
            					     	<%-- 	<input id="rate" value="<s:property value="pactInfo.rate"/>" readonly="readonly" /> --%>
            					</td>  		
		            		</tr>
		            		
		            		
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.pactAmt"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            						<s:property value="pactInfo.pact_amt"/>
	            						<label
											class="record-label"> <s:text name="元"></s:text>
										</label>
            					     		<%-- <input id="pact_amt" value="<s:property value="pactInfo.pact_amt"/>" readonly="readonly" /> --%>
            					</td>  
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.cashAmt"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            						<s:property value="pactInfo.cash_amt"/>
	            						<label
											class="record-label"> <s:text name="元"></s:text>
										</label>
            					     		<%-- <input id="cash_amt" value="<s:property value="pactInfo.cash_amt"/>" readonly="readonly" /> --%>
            					</td>  		
		            		</tr>
		            		
		            		
		            		
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.incomeAmt"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            				<s:property value="pactInfo.income_amt"/>
	            						<label
											class="record-label"> <s:text name="元"></s:text>
										</label>
	            					<%-- <input id="income_amt" value="<s:property value="pactInfo.income_amt"/>" readonly="readonly" />
            					 --%></td>  
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.fundSources"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            				
	            						<%-- <s:property value="fund_sources"/>--%>
            					     		<s:select name="pactInfo.fund_sources" list="#select.queryWPD('FUNDS_FROM')" listKey="opCode" listValue="opCnName"  /> 
            					</td>  		
		            		</tr>
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.paymentType"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            					<%-- <s:property value="payment_type"/>--%>
	            					<s:select name="pactInfo.payment_type" list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode" listValue="opCnName"/> 
            					</td>  
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.startDate"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            						<s:property value="start_date1"/>
	            		 			<!-- <input id="createStartDate" name="createStartDate" type="text"  class="easyui-datebox record-value" /> -->		
            					     		<%-- <s:textfield id="start_date" name="pactInfo.start_date" cssClass="record-value" /> --%>
            					</td>  		
		            		</tr>
		            		
		            		
		            		
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.endDate"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	             					<s:property value="end_date1"/>
	            				 <!-- <input id="createEndDate" name="createEndDate" type="text"  class="easyui-datebox record-value" /> -->
            					     		<%-- <s:textfield id="end_date" name="pactInfo.end_date" cssClass="record-value" /> --%>
            					</td>  
            					
            					<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.ifContinue"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            				<s:property value="if_continue"/>
            					     		<%-- <s:select name="pactInfo.if_continue" list="#select.queryWPD('YES_NO')" listKey="opCode" listValue="opCnName"  /> --%>
            					
            					</td>  
		            		</tr>
		            		
		            		<tr>
		            			<td class="td-label">
	            							<label class="record-label">
	            								<s:text name="pactInfo.sts"></s:text>:
	            							</label>	            							
	            				 </td>
	            				<td class="td-value">
	            							<s:property value="sts"/>
            					     		<%-- <s:select name="pactInfo.sts" list="#select.queryWPD('PACT_STS')" listKey="opCode" listValue="opCnName"  /> --%>
            					</td>  
		            		</tr>
             		 	</table>
          			</div>
          			
          			<div title="<s:text name='entity.pactInfo.zhanquan_pipei'/>"  style="padding: 10px;"> 
          			
          		    <table id="ClaimsInfo"  style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" >
                    </table>
          		     
          		</div>
          			
          			<div title="<s:text name='回款历史'/>" style="padding: 10px;">
								<div class="section-header">
									<span><s:text name="回款历史" /></span>
								</div>
						     	<table id="payHis" style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
						     		
						     	
						     	</table>
						   </div>
					 </div>
			 </s:form>
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



