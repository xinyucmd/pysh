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


	 function countChar(textareaName,spanName){  
			document.getElementById(spanName).innerHTML = 200 - document.getElementById(textareaName).value.length;
	 }



	 function save() {
		 if($("#addObjectForm").form("validate")){
			 
			 $.messager.confirm("保存确认", "您确认保存当前信息吗？", function (saveAction) {
	              if (saveAction) {
			 		var addObjectForm = document.getElementById('addObjectForm');
			 		addObjectForm.action='saveSubscription.action';
		    		addObjectForm.submit();
	
		     		baseCancel("Continue");
		     		
		     		
	              }
             });
			
			 
		 } 
		 
		 
		 
	}
	 
	 function submit() {
		 
		 if($("#addObjectForm").form("validate")){
			 
			var items = $("input[name='PD']:checked");
			 
			var pactNos = '';
			
			var amt=0;
			
			$.each(items, function (index, item) {
				 var str=item.value;
				 var strs=new Array();
				 strs=str.split(",");
				 pactNos=pactNos+"'"+strs[0]+"',";
				 amt=Number(amt)+Number(strs[1]);
          
            });
 			
 			pactNos=pactNos.substring(0,pactNos.length-1);
 			//alert(pactNos);
 			//alert(amt);
 			$("#pactnos").val(pactNos); 
 			$("#amt").val(amt);
 		
			if (pactNos=='') {
				$.messager.show({  
					 title: '提示信息',  
					msg: '请至少选择一个续购的合同！',  
					 showType: 'slide'  
				});  
			}else{
				$.messager.confirm("提交确认", "您确认提交到下一环节吗？", function (saveAction) {
					if (saveAction) {					 
				 		var addObjectForm = document.getElementById('addObjectForm');
				 		addObjectForm.action='submitContinue.action';
				 		
			     		addObjectForm.submit();
			     	
			     		//baseCancel("Continue");
			     	}
	         	});
			}
		 }  
		 
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
			    		$("#payment_type").val(item.type);
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
		 
		 
		 if ('<s:property value="pactInfo.if_continue" />'=='') {
			 $('#if_continue').val('2');
		 }
		 
		 $('#cusID').combogrid('setValue',
			'<s:property value="cusID"/>');
		 
		 $('#cusID').combogrid('setText',
			'<s:property value="cusName"/>');
		 
		
		 

		 $("#cusID").combogrid({

			 	onSelect: function (n,o) {
			 			
	            	var g = $('#cusID').combogrid('grid');	// 获取表格控件对象
	                var r = g.datagrid('getSelected');	//获取表格当前选中行 
	         
	                $("#cosid").val(r.id);
	                if (r.contactPhone!=null) {
	                	$("#phone").val(r.contactPhone);
					}
	                
	                $("#idNo").val(r.idNo);
	                	           
	                if (r.mail!=='null') {
	                	$("#emal").val(r.mail);
					}else{
						$("#emal").val('');
					}
	              
	                $("#cifType").val(r.cifType);
	                $("#idType").val(r.idType);
	                
	                if (r.contact!=='null') {
	                	 $("#contact").val(r.contact);
					}else{
						$("#contact").val('');
					}
	                
	                if (r.sex=='男') {
	                	$("#sex").val('1');
					}else{
						$("#sex").val('2');
					}
	                
	                  
	                $("#cifName").val(r.cifName);
	      
	                if(r.birth!=='null'){
	                	$('#birth').datebox('setValue',r.birth);
	                }else{
						$("#birth").datebox('setValue','') ;
					}
	                
	                if (r.contactTel!=='null') {
	                	$("#tel").val(r.contactTel);
					}else{
						$("#tel").val('');
					}
	                
	                if (r.contactPhone!=='null') {
	                	$("#phone").val(r.contactPhone);
					}else{
						$("#phone").val('');
					}
	                
	                if (r.addr!=='null') {
	                	$("#addr").val(r.addr);
					}else{
						$("#addr").val('');
					}
	               
	                document.getElementById("counter2").innerHTML = 200 - document.getElementById("addr").value.length;
	                
	                if (r.postcode!=='null') {
	                	$("#postcode").val(r.postcode);
					}else{
						$("#postcode").val('');
					}
	                
	                $("#open_id").val(r.openId);
	                $("#open_op").val(r.openOp);
	                $("#open_date").val(r.openDate);
	                
	                var cosid = r.id;
	                
	                $("#pactCountine").datagrid({
	                	url:'listPact.action?id='+cosid ,
	                	isField : "id",
	                	pagination : true,
	                	border:false,
	                	pagesize : 5,
	                	pageList : [5,10,15],
	                	title : "请选择参加续购的合同",
	                	rownumbers: true,              
	                	columns : [[
	         
	                	            {field:'ck', title: '<input id=\"checkbox\" type=\"checkbox\"  >',width:30,
	                	            	formatter: function (value, rec, rowIndex) {
	                                        return "<input type=\"checkbox\"  name=\"PD\"  value=\"" +rec.pactNo+","+rec.continueAmt + "\" >";

	                                    }
	                	            },  

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

	                	]],
	                	
	                	onLoadSuccess: function () {
	                		$("#checkbox").click(function () {
	                			if ($(this).attr('checked') == 'checked') {
	                                $("input[name='PD']").attr("checked", 'checked');
	                            } else {
	                                $("input[name='PD']").removeAttr("checked");
	                            }	
	                			
	                			 var items = $("input[name='PD']:checked");
	                			 var amt=0;
	                			 $.each(items, function (index, item) {
	                				 var str=item.value;
	                				 var strs=new Array();
	                				 strs=str.split(",");
	                				 amt=Number(amt)+Number(strs[1]);
	                           
	                             });

	                			 $("#continue_amt").val(amt);
	                			
	                			
	                		});
	                		
	                		
	                		 $("input[name='PD']").unbind().bind("click", function () {
	                			 var items = $("input[name='PD']:checked");
	                			 var amt=0;
	                			 $.each(items, function (index, item) {
	                				 var str=item.value;
	                				 var strs=new Array();
	                				 strs=str.split(",");
	                				 amt=Number(amt)+Number(strs[1]);
	                           
	                             });

	                			 $("#continue_amt").val(amt);

	                         });


	                	},
	                	
	                	/*
	                    onSelect: function (){
	                        var amt=0;
		   	                var checkedItems = $('#pactCountine').datagrid('getChecked');
		   	                $.each(checkedItems, function(index, item){            				
	         				   amt=Number(amt)+Number(item.continueAmt);
	         				   
	         			    }); 
		   	                $("#continue_amt").val(amt);
	                    },
	                
	                    onUnselect: function (){
	                    	var amt=0;
		   	                var checkedItems = $('#pactCountine').datagrid('getChecked');
		   	                if (checkedItems==null) {
		   	                 	$("#continue_amt").val(0);
							}else{
			   	                $.each(checkedItems, function(index, item){            				
		         				   amt=Number(amt)+Number(item.surplusAmt);
		         				   
		         			    }); 
			   	                $("#continue_amt").val(amt);
							}
	                    }
	                   
	                   
	                	/*
	                	toolbar : ["-", {
	                		id:'getcheckbutton',
	                		text:'选择',
	                		iconCls:'icon-update',
	                		handler: function () {
	                			var checkedItems = $('#pactInfo').datagrid('getChecked');
	                			var pactNos = '';
	                			$.each(checkedItems, function(index, item){            				
	                				pactNos=pactNos+item.pactNo+',';
	                			});    
	                	
	                			var params = {
	                					pactNos : pactNos
	        							 };
	                			 $.ajax({
	         					    type: "POST",
	         					    url: "getIncomeAmt.action?",
	         					    data: params,
	         					    dataType:"text", 
	         					    success: function(json){        					
	         					    	var jsonObj=eval("("+json+")");	         					    	
	         					    	$.each(jsonObj, function (i, item) { 	
	         					    		$("#incomeAmt").val(item.incomeAmt);
	         					    	}); 
	         					    	
	         					    },
	         					    error: function(json){
	         					      alert("json=" + json);
	         					      return false;
	         					    }
	         					  });
	                		}
	                	}]
	                	 */  
	               
	                });
	             
	                
	                
                    //查询银行卡信息
	                
	                var params = {
	                		cusID : r.id
							 };
	             
	                $.ajax({
					    type: "POST",
					    url: "getBankAcc.action",
					    data: params,
					    dataType:"text", 
					    success: function(json){  
					    	if (json!=null) {
					    		var jsonObj=eval("("+json+")");
						    	
						    	$.each(jsonObj, function (i, item) { 	
						    		$("#account_name").val(item.bank_account_name);
						    		$("#account_no").val(item.bank_account_no);
						    		$("#account_bank").val(item.bank_account_addr);
						    	}); 
							}
	
					    },
					    error: function(json){
					      alert("json=" + json);
					      return false;
					    }
					  });
	              
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
	                
	                
	                
	              /*  
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
*/
	                
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
    <!-- save
		        <span style="white-space: nowrap;">
		           <a id="save_accept_btn" href="#" class="easyui-linkbutton" iconCls="icon-save-accept" onclick="save()" plain="true">
		           		<s:text name="button.save" />
		           </a>
		        </span> 
		  -->
	<!-- saveClose -->	       
		   	    <span style="white-space: nowrap;"> 
		     	   <a id="save_go_btn" href="#" class="easyui-linkbutton" iconCls="icon-save-go" onclick="submit()" plain="true">
		     	   		<s:text name="button.submit" />
		     	   </a>
		        </span> 
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
		    	<s:text name="发起续购" />
		    </h2>
		</div> 
		
<!-- 表单 -->			
	 <div id="feature-content">
	 		 <s:form id="addObjectForm" validate="true"  namespace="/jsp/subscription" method="post">
	 		 
	 		 <s:hidden id="cifName" name="customer.cif_name" value="%{customer.cif_name}" />
	 		
	 		   <s:hidden id="open_id" name="customer.open_id" value="%{customer.open_id}" />
	 		  <s:hidden id="open_op" name="customer.open_op" value="%{customer.open_op}" />
	 		  <s:hidden id="open_date" name="customer.open_date" value="%{customer.open_date}" />
	 		  <s:hidden id="cmt" name="customer.cmt" value="%{customer.cmt}" />
	 		  
	 		 
	 		  <s:hidden id="cosid" name="customer.id" value="%{customer.id}" />
	 		  <s:hidden id="pactnos" name="pactNos"  />
	 		  
	 		  <s:hidden id="amt" name="pactInfo.income_amt"  />
	 		 
	 		  <s:hidden id="prdtName" name="pactInfo.prdt_name" value="%{pactInfo.prdt_name}" />
	 		  
	 		  
	 		  <s:hidden id="" name="pactInfo.id" value="%{pactInfo.id}" />
	 		 
	 		 <div class="section-header">
                <span><s:text name="customer.infomation" /></span>
             </div>
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
   					      <tr> 
            				<td class="td-label"><label class="record-label"><s:text
                                name="action.selectCif"></s:text>：</label></td>
            					
              				<td class="td-value"><select id="cusID"  required="true"  
                					class="easyui-combogrid record-value" name="cifName" 
                					style="width: 180px;"
                					data-options="  
					            		panelWidth:520,  
					            		idField:'id',  
					            		textField:'cifName',  
					            		url:'<s:url action="listInvestor" namespace="/jsp/investor"/>',
		                        		loadMsg: '<s:text name="datagrid.loading" />',
		                        		pagination : true,
		                        		pageSize: 10,
		                        		pageList: [10,30,50],	
				                		fit: true,
					            		mode:'remote',
					            		columns:[[  
					                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'cifName',title:'<s:text name="entity.name.label" />',width:100},  
					                {field:'sex',title:'<s:text name="customer.sex.label" />',width:120},    
					                {field:'birth',title:'<s:text name="customer.birth.label" />',width:100}, 
					                {field:'cifType',title:'<s:text name="customer.cifType.label" />',width:100},  
					                {field:'idType',title:'<s:text name="customer.idType.label" />',width:100}, 
					                {field:'idNo',title:'<s:text name="customer.idNo.label" />',width:100},
					                {field:'contact',title:'<s:text name="customer.contact.label" />',width:100},
					                {field:'contactTel',title:'<s:text name="customer.contactTel.label" />',width:100},
					                {field:'contactPhone',title:'<s:text name="customer.contactPhone.label" />',width:100},			                
					                {field:'mail',title:'<s:text name="customer.email" />',width:100},
					                {field:'addr',title:'<s:text name="customer.address.label" />',width:100},
					                {field:'postcode',title:'<s:text name="customer.postcode.label" />',width:100},
					                {field:'openDate',title:'<s:text name="customer.openDate.label" />',width:100}, 
					                {field:'openOp',title:'<s:text name="customer.openOp.label" />',width:100}, 
					                {field:'cmt',title:'<s:text name="customer.cmt.label" />',width:100}, 
					                {field:'openId',title:'<s:text name="customer.openId.lable" />',width:100}     
					                
					            ]]  
					        ">
              			</select>
              
              
              
              			 <td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.meAdult"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input name="pactInfo.me_adult" value="<s:property value="pactInfo.me_adult" />" class="easyui-validatebox" cssClass="record-value" required="true" missingMessage="不能为空"/>
            				</td>
          			           					 	
            				
            				
            				
            				
          			</table>
          			
          	 <div id="tt" class="easyui-tabs" >
          	 		<div title="<s:text name='pactInfo.daoqiPactInformation'/>"  style="padding: 10px;" >
          	 		<table id="pactCountine" style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
                         
          	 		</table>
          	      </div>
          	 	
          	 	
          	    <div title="<s:text name='pactInfo.inflomation'/>"  style="padding: 10px;" >
          			<div class="section-header">
                      <span><s:text name="entity.prod.information" /></span>
                    </div>
            		
            		<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
   					      <tr>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.prod.name"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <select id="proID"  required="true"  
                					class="easyui-combogrid record-value" name="" 
                					style="width: 180px;"
                					data-options="  
					            		panelWidth:520,  
					            		idField:'prdtNo',  
					            		textField:'prdtName',  
					            		url:'<s:url action="listPro" namespace="/jsp/prod"/>',
		                        		loadMsg: '<s:text name="datagrid.loading" />',
		                        		pagination : true,
		                        		pageSize: 10,
		                        		pageList: [10,30,50],	
				                		fit: true,
					            		mode:'remote',
					            		columns:[[    	
					                {field:'prdtNo',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'prdtName',title:'<s:text name="entity.name.label" />',width:100},  
					                {field:'standardAmt',title:'<s:text name="entity.title.label" />',width:120},  
					                {field:'advanceRedeem',title:'<s:text name="entity.department.label" />',width:100},
					                {field:'delFlg',title:'<s:text name="entity.status.label" />',width:100},  
					                {field:'sts',title:'<s:text name="entity.status.label" />',width:100}, 
					                {field:'startDate',title:'<s:text name="entity.status.label" />',width:100}, 
					                {field:'endDate',title:'<s:text name="entity.status.label" />',width:100}, 
					                {field:'createOp',title:'<s:text name="entity.status.label" />',width:100}, 
					                {field:'createDate',title:'<s:text name="entity.status.label" />',width:100},  
					                {field:'cmt',title:'<s:text name="entity.status.label" />',width:100} 
					                
					            ]]  
					        ">
              			</select>
              			
              			
              			
            				</td>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.prod.code"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input name="pactInfo.prdt_no" value="<s:property value="pactInfo.prdt_no" />"  cssClass="record-value" id="prdt_no" name="pactInfo.prdt_no" readonly="true"/>
            				</td>
            			</tr>	
            			
            			 <tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.term_range2"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					     <s:select id="prodcpid"  onchange="selectQS()" name="pactInfo.term_range" 
                      list="#{'0'}" 
                      cssClass="record-value" cssStyle="width:140px;"/>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.rate"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input id="rate" name="pactInfo.rate" value="<s:property value="pactInfo.rate" />"  cssClass="record-value" readonly="true"/>%
            				</td>
            			</tr>
            				<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.paymentType"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value" >
            				<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();"> 
            					   <s:select id="payment_type" list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode" listValue="opCnName" name="pactInfo.payment_type" ></s:select>
            			    </span>
            				</td>
            				
            			
            			</tr>
            	    </table>
            	    
            	    <div class="section-header">
                      <span><s:text name="pactInfo.inflomation" /></span>
                    </div>
                    <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
            			<tr>
      				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.cashAmt"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input id="cash_amt" name="pactInfo.cash_amt" value="<s:property value="pactInfo.cash_amt" />" cssClass="record-value" class="easyui-numberbox" data-options="required:false" precision="2"></input>
            					    <label class="record-value">元</label> 
            				</td>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="continue.continue_amt"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <label class="record-value">
            					    	<input id="continue_amt" readonly="true" ></input>
            					    </label> 
            				</td>
            			</tr>	
            			
            				<tr>
            				
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.fundSources"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <s:select id="cifType" list="#select.queryWPD('FUNDS_FROM')" listKey="opCode" listValue="opCnName" name="pactInfo.fund_sources" ></s:select>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="pactInfo.pact.if_continue"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					
                           
                           <s:select id="if_continue" list="#select.queryWPD('YES_NO')" listKey="opCode"  name="pactInfo.if_continue" value="pactInfo.if_continue" 
                           listValue="opCnName" ></s:select>
                           
            				</td>
            			</tr>
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.financing.if_wxd"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
                                
                                <s:select list="#select.queryWPD('YES_NO')" listKey="opCode"  name="pactInfo.if_wxd" value="pactInfo.if_wxd"
                           listValue="opCnName" ></s:select>
            					   
            		
            				</td>
            			
            			</tr>
            		</table>
            		<div class="section-header">
                      <span><s:text name="main.account.bankinformation" /></span>
                    </div>
                    <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">		
            			
            			<tr>
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="menu.bankAccount.name"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input id="account_name" name="pactInfo.account_name"  value="<s:property value="pactInfo.account_name" />"  cssClass="record-value" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
            				</td>
            				
            				<td class="td-label">
            						<label class="record-label">
            							<s:text name="menu.bankAccount.no"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input id="account_no" name="pactInfo.account_no" value="<s:property value="pactInfo.account_no" />" cssClass="record-value" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
            				</td>
            			</tr>
            			
            			<tr>
            				<td class="td-label" valign="top">
            						<label class="record-label">
            							<s:text name="menu.bankAccount.op_bank"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value"  >
            					    <textarea id="account_bank" rows=5  cssClass="record-value" style="width:250px;"  name="pactInfo.account_bank"  class="textarea easyui-validatebox"  required="true" missingMessage="不能为空" maxlength=200 onkeydown='countChar("account_bank","counter");' onkeyup='countChar("account_bank","counter");'><s:property value="pactInfo.account_bank" /></textarea>
            					    <br/>最多输入200字，还可以输入<span id="counter">200</span>字
            				</td>
            				
            				
            			</tr>
            			
            			
            		
            			
            			
            			
            				
          			</table>
          			 </div>
          			 
          		<div title="<s:text name='menu.customer.xiangxiinfomation'/>"  style="padding: 10px;">
          		<div class="section-header">
                      <span><s:text name="menu.base.information" /></span>
                    </div>
                    <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
                     <tr> 
            				
              					 
              					    
              				  
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
            					     		
            					     		 <s:select id="idType" list="#select.queryWPD('ID_TYPE')" listKey="opCode" listValue="opCnName" name="customer.id_type" value="%{customer.id_type}"></s:select>
            					      </td>
            				<!-- standardAMT -->
            				
            					     <td class="td-label">
	            							<label class="record-label">
	            								<s:text  name="customer.idNo.label"></s:text>:
	            							</label>	            							
	            					 </td>    		
	            					 
            					    
            					     <td class="td-value">
            					     		<input type="text" id="idNo" class="easyui-validatebox"  required="true" missingMessage="不能为空"  name="customer.id_no" value="<s:property value="customer.id_no" />"/>
            					     </td>  
            					     
            					                					    
            				</tr>
            				<tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.cifType.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		 <s:select id="cifType" list="#select.queryWPD('CUS_TYPE')" listKey="opCode" listValue="opCnName" name="customer.cif_type" value="%{customer.cif_type}"></s:select>
            					    </td>
            					   
            			    </tr>
            				
                   </table> 
          		
          		
          		
          	      <div class="section-header">
                      <span><s:text name="customer.contact.information" /></span>
                    </div>
                    <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
                      <tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.contact.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input id="contact" name="customer.contact" value="<s:property value="customer.contact" />"  class="easyui-validatebox"  cssClass="record-value"    required="true" missingMessage="不能为空"/>
            					    </td>
            					    
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="contact.tel"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input type="text" id='tel' name="customer.contact_tel" value="<s:property value="customer.contact_tel" />"   class="easyui-validatebox""  missingMessage="不能为空"/>
            					    </td>
            				
            				</tr>
            				<tr>
            				
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.mail.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input id="emal" name="customer.mail" value="<s:property value="customer.mail" />"  cssClass="record-value" class="easyui-validatebox" missingMessage="邮件必须填写" validType="email" invalidMessage="请填写正确的邮件格式"/>
            					    </td>
            					    
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.contactPhone.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input id="phone" name="customer.contact_phone" value="<s:property value="customer.contact_phone" />"  cssClass="record-value" class="easyui-validatebox" missingMessage="不能为空" />
            					    </td>
            				
            				</tr>
                   </table> 
                   
                   <div class="section-header">
                      <span><s:text name="customer.selfInformation" /></span>
                    </div>
                    <table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
                      <tr>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.birth.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input id="birth" name="customer.birth" value="<s:property value="customer.birth" />"  class="easyui-datebox"  cssClass="record-value"  />
            					    </td>
            					    
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="customer.postcode.label"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<input type="text" id='postcode' name="customer.postcode" value="<s:property value="customer.postcode" />"   class="easyui-validatebox""  missingMessage="不能为空"/>
            					    </td>
            				
            				</tr>
            				<tr>
		            				<td class="td-label" valign="top">
		                    		 	<label class="record-label"> <s:text name="customer.address.label" ></s:text>：</label></td>
		                            <td class="td-value" valign="top">             
		                  	         	<s:textarea  id="addr"
		                      			name="customer.addr" rows="5" cssStyle="width:350px;" cssClass="record-value" maxlength="200" onkeydown='countChar("addr","counter2");' onkeyup='countChar("addr","counter2");'/>
		                      			<br/>最多输入200字，还可以输入<span id="counter2">200</span>字</td>
		                      		<td class="td-mass-update">
                   			</td>
            						
            				
            				</tr>
                   </table>            		
          	</div>
          
          	
          
          	</div>
          </s:form>
            
    </div>
    
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



