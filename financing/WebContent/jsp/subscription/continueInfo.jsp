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

	function countChar(textareaName,spanName)
	{  
			document.getElementById(spanName).innerHTML = 200 - document.getElementById(textareaName).value.length;
	} 


	 function save() {
		 if($("#addObjectForm").form("validate")){
			 
			 $.messager.confirm("保存确认", "您确认保存当前信息吗？", function (saveAction) {
	              if (saveAction) {
			 		var addObjectForm = document.getElementById('addObjectForm');
			 		addObjectForm.action='saveSubscription.action';
		    		addObjectForm.submit();
	
		     		baseCancel("subscrip");
		     		
		     		
	              }
             });
			
			 
		 } 
		 
		 
		 
	}
	 
	 function submit() {
		 
		 if($("#addObjectForm").form("validate")){
			 
			 $.messager.confirm("提交确认", "您确认提交吗？", function (saveAction) {
				 if (saveAction) {
			 	var addObjectForm = document.getElementById('addObjectForm');
			 	addObjectForm.action='submitClaims.action';
		     	addObjectForm.submit();
		     
		     	}
         	});
		 }  
		 
	}
	 
	 function fail(){		 
			 $.messager.confirm("提交确认", "匹配失败流程就会关闭，是否继续？", function (saveAction) {
				 if (saveAction) {
			 	var addObjectForm = document.getElementById('addObjectForm');
			 	addObjectForm.action='failClaims.action';
		     	addObjectForm.submit();
		     
		     	}
         	});
		  
		 
		 
	 }
	 
	 function selectQS(){
		 var g = $('#proID').combogrid('grid');	// 获取表格控件对象
         var r = g.datagrid('getSelected');	//获取表格当前选中行 
         var qishu=$('#prodcpid').val();
         
         var params = {
        		 prodNo : r.prdtNo,
         		qishu: qishu
					 };
 
		 
		 $.ajax({
			    type: "POST",
			    url: "getRate.action",
			    data: params,
			    dataType:"text", 
			    success: function(json){  
			    	var jsonObj=eval("("+json+")"); 
			    	$.each(jsonObj, function (i, item) { 	
			    		$("#rate").val(item.rate);		
			    	}); 
			    },
			    error: function(json){
			      alert("json=" + json);
			      return false;
			    }
			  });	  
	 }
	 
	 
	 function cancel(){
		 baseCancel("Continue");
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
         	title : "到期续购合同信息",
         	rownumbers : true,
         	singleSelect : false,
         	columns : [[ 
         	             
	                	            {field:'pactNo',title:'<s:text name="合同号" />',width:100},
					                {field:'cifName',title:'<s:text name="客户姓名" />',width:100},  
					                {field:'idNo',title:'<s:text name="身份证号" />',width:120},  
					                {field:'prot',title:'<s:text name="产品名称" />',width:100},  
					                {field:'pact_amt',title:'<s:text name="合同金额" />',width:100}, 
					                {field:'term_range',title:'<s:text name="认购期数" />',width:100}, 
					                {field:'rate',title:'<s:text name="年利率" />',width:100},
					                {field:'incomeAmt',title:'<s:text name="收益金额" />',width:100},
					                {field:'redemAmt',title:'<s:text name="赎回金额" />',width:100},
					                {field:'continueAmt',title:'<s:text name="续购金额" />',width:100} ,
					                {field:'startDate',title:'<s:text name="开始时间" />',width:100} ,
					                {field:'endDate',title:'<s:text name="结束时间" />',width:100}     
         	]]
 
         });
		 
		 
		 
		 
		 $('#cusID').combogrid('setValue',
			'<s:property value="cusID"/>');
		 
		 $('#cusID').combogrid('setText',
			'<s:property value="cusName"/>');

		 $("#cusID").combogrid({

			 	onSelect: function (n,o) {
			 			
	            	var g = $('#cusID').combogrid('grid');	// 获取表格控件对象
	                var r = g.datagrid('getSelected');	//获取表格当前选中行 
	         
	                $("#cosid").val(r.id);
	                
	                $("#phone").val(r.contactPhone);
	                $("#idNo").val(r.idNo);
	                $("#emal").val(r.mail);
	                $("#cifType").val(r.cifType);
	                $("#idType").val(r.idType);
	                $("#contact").val(r.contact);
	                $("#sex").val(r.sex);
	                $("#cifName").val(r.cifName);
	                $("#birth").val(r.birth);
	                
	                $("#contactTel").val(r.contactTel);
	                $("#addr").val(r.addr);
	                
	                $("#postcode").val(r.postcode);
	                $("#openDate").val(r.openDate);
	                $("#openOp").val(r.openOp);
	                $("#cmt").val(r.cmt);
	                
	                
	             
	              
	             // $(function(){ 

	                  //把min改为20,原来的值是0 

	               //   $("#num").numberbox({ 

	               //       min:20 ,
	                 //     missingMessage: r.name	
	                //  }); 

	             // });

			 	}
		 
		 

			 });
		 
		 
		 
		 $('#proID').combogrid('setValue',
			'<s:property value="prdtNo"/>');
		 
		 $('#proID').combogrid('setText',
			'<s:property value="prdtName"/>');
		 
		 $("#proID").combogrid({

			 	onSelect: function (n,o) {
			 			
	            	var g = $('#proID').combogrid('grid');	// 获取表格控件对象
	                var r = g.datagrid('getSelected');	//获取表格当前选中行 
	         
	                
	                $("#prdt_no").val(r.prdtNo);
	                $("#prdtName").val(r.prdtName);
	                
	                var params = {
	                		prodNo : r.prdtNo
							 };
	             
	                $.ajax({
					    type: "POST",
					    url: "getInstallments.action",
					    data: params,
					    dataType:"text", 
					    success: function(json){  
					    	jQuery("#prodcpid").empty();    	
					    	jQuery("#prodcpid").append("<option value=0>请选择</option>");
					    	var jsonObj=eval("("+json+")");
					    	
					    	$.each(jsonObj, function (i, item) { 	
					    		jQuery("#prodcpid").append("<option value="+ item.stageMax+">"+ item.stageMax+"</option>"); 
					    	}); 
					    	
					    },
					    error: function(json){
					      alert("json=" + json);
					      return false;
					    }
					  });
	                
	                
	                
	                
	               $(function(){ 

	                 

	                  $("#pact_amt").numberbox({ 

	                      min: parseInt(r.standardAmt),
	                      missingMessage: "起购金额"+r.standardAmt	
	                  }); 
	                  
	                  $("#cash_amt").numberbox({ 

	                      min: parseInt(r.standardAmt),
	                      missingMessage: "起购金额"+r.standardAmt	
	                  }); 
	                  
	                  

	              });

	                
			 	}

			 });
		 
		 
		 var params = {
         		prodNo : '<s:property value="prdtNo"/>'
					 };
		 
		 $.ajax({
			    type: "POST",
			    url: "getInstallments.action",
			    data: params,
			    dataType:"text", 
			    success: function(json){  
			    	jQuery("#prodcpid").empty();    	
			    	jQuery("#prodcpid").append("<option value=0>请选择</option>");
			    	var jsonObj=eval("("+json+")");
			    	
			    	$.each(jsonObj, function (i, item) { 
			    		if(item.stageMax == '<s:property value="pactInfo.term_range"/>'){
			    			jQuery("#prodcpid").append("<option value="+ item.stageMax+"  selected='true'>"+ item.stageMax+"</option>");
			    		}else{
			    		    jQuery("#prodcpid").append("<option value="+ item.stageMax+">"+ item.stageMax+"</option>"); 
			    		}
			    		
			    	}); 
			    	
			    },
			    error: function(json){
			      alert("json=" + json);
			      return false;
			    }
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

	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="cancel()" plain="true">
			           		<s:text name="返回" />
			           </a>
	          </span>
      </div>
      
<!---->

<div id="feature-content">
			<h2>
		    	<s:text name="续购合同信息" />
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
	 		 
	 		  <s:hidden id="cosid" name="customer.id" value="%{customer.id}" />
	 		 
	 		  <s:hidden id="prdtName" name="pactInfo.prdt_name" value="%{pactInfo.prdt_name}" />
	 		  <s:hidden id="" name="pactInfo.pact_no" value="%{pactInfo.pact_no}" />
	 		  
	 		  
	 		  <s:hidden id="" name="pactInfo.id" value="%{pactInfo.id}" />
	 		  
	 		  
	 		  
	 		  
	 <div id="tt" class="easyui-tabs">
	     <div title="<s:text name='客户信息'/>"  style="padding: 10px;">
	 		 <div class="section-header">
                <span><s:text name="基本信息" /></span>
             </div>
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%" readonly="true">
   					      <tr> 
            				<td class="td-label"><label class="record-label"><s:text
                                name="客户姓名"></s:text>:</label></td>
            					
              				<td class="td-value">
              					<label class="record-value"> <s:property value="customer.cif_name" /></label>
              
              
              			</td>
              					 
              					    
              				  
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="性别"></s:text>:
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
          			
          	
            	
            	<div title="<s:text name='合同信息'/>" style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="合同号" /></span>
							</div>
						   <table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>


									<td class="td-label"><label class="record-label">
											<s:text name="合同号"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.pact_no" />
									</label></td>


									<td class="td-label"><label class="record-label">
											<s:text name="债权合同号"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.claims_pact_no" />
									</label></td>

								</tr>
						</table>
						
						
							<div class="section-header">
								<span><s:text name="产品信息" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>


									<td class="td-label"><label class="record-label">
											<s:text name="产品名称"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.prdt_name" />
									</label></td>


									<td class="td-label"><label class="record-label">
											<s:text name="产品编码"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.prdt_no" />
									</label></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="合同期数"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.term_range" /> <label
											class="record-label"> <s:text name="期"></s:text>
										</label>

									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="年化收益率"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.rate" /> <label
											class="record-label"> % </label>
									</label></td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="合同信息" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="合同金额"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.pact_amt" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="现金转账金额"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.cash_amt" />
									</label></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="理财收益金额"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.income_amt" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="资金来源"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select id="cifType"
											list="#select.queryWPD('FUNDS_FROM')" listKey="opCode"
											listValue="opCnName" name="pactInfo.fund_sources"></s:select></span>
									</td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="付息方式"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select id=""
											list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode"
											listValue="opCnName" name="pactInfo.payment_type"></s:select></span>
									</td>


								</tr>


								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="到期续存"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select
											list="#select.queryWPD('YES_NO')" listKey="opCode"
											name="pactInfo.if_continue" value="pactInfo.if_continue"
											listValue="opCnName"></s:select></span></td>

									<td class="td-label"><label class="record-label">
											<s:text name="是否转入微信贷"></s:text>:
									</label></td>
									<td class="td-value">
									<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
									<s:select
											list="#select.queryWPD('YES_NO')" listKey="opCode"
											name="pactInfo.if_wxd" value="pactInfo.if_wxd"
											listValue="opCnName"></s:select></span></td>
								</tr>
								
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="开始时间"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value"><s:property value="pactInfo.start_date" /></label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="结束时间"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value"><s:property value="pactInfo.end_date" /></label></td>
								</tr>

							</table>


							<div class="section-header">
								<span><s:text name="银行账户信息" /></span>
							</div>

							<table style="padding: 10px;" cellspacing="10" cellpadding="0"
								width="100%">
								<tr>
									<td class="td-label"><label class="record-label">
											<s:text name="银行账户名"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_name" />
									</label></td>

									<td class="td-label"><label class="record-label">
											<s:text name="银行账号"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_no" />
									</label></td>
								</tr>

								<tr>
									<td class="td-label" rowspan=”2″><label
										class="record-label"> <s:text name="开户行"></s:text>:
									</label></td>
									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_bank" />
									</label></td>

									<td class="td-label"><label class="record-label">

									</label></td>
									<td class="td-value"></td>
								</tr>



							</table>


				</div>
          		<s:if test="pactInfo.pact_type==2||pactInfo.pact_type==3">
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



