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
    	  function getShouju(){
    		var pactNo = document.getElementById('pactNo').value;
    		var cifName = document.getElementById('cifName').value;
    		var idNo   = document.getElementById('idNo').value;
    	   
    		var params = {
    				 pactNo  :  pactNo,
    				 cifName :  cifName,
    				 idNo 	 : idNo
    		 };
				 $.ajax({
					    type: "POST",
					    url: "getShouju.action",
					    data: params,
					    dataType:"text", 
					    success: function(json){  
					    	
					    	
					    	var jsonObj=eval("("+json+")"); 
					    	$.each(jsonObj, function (i, item) {
					    		
					    		if(pactNo =item.htNo )
					    		{
					    				document.getElementById('pactNo').value = item.htNo;
							    		document.getElementById('cifName').value = item.khName;
							    		document.getElementById('idNo').value  = item.idNo;
							    		document.getElementById('fukuanType').value = item.fkType;
							    		document.getElementById('fukuanJine').value = item.fkJe;
							    		document.getElementById('shouyiqisuanDate').value = item.qsDate;
							    		document.getElementById('chanpinzhongzhiDate').value = item.zzDate;
							    		document.getElementById('fuKuanRenBankNo').value = item.bankNo;
							    		document.getElementById('opBank').value = item.opBank;	
					    		}
					    		else
					    		{
					    			alert("无此合同编号");
					    		}
					    		
					    		
					    		
					    		
					    	}); 
					    },
					    error: function(json){
					      alert("合同编码错误");
					      return false;
					    }
					}); 
    	}
    	function printShouju(){
	    	var pact_no  =	document.getElementById('pactNo').value ;
	    	var shouJu_no = document.getElementById('shouJuNo').value;
	    	var id_no = document.getElementById('idNo').value;
	    	var cif_name =	document.getElementById('cifName').value ;
	    	var pay_type =  document.getElementById('fukuanType').value;
	    	var pay_jine = 	document.getElementById('fukuanJine').value ;
	    	var start_time = document.getElementById('shouyiqisuanDate').value ;
	    	var end_time = 	document.getElementById('chanpinzhongzhiDate').value;
	    	var bank_no  = 	document.getElementById('fuKuanRenBankNo').value;
	    	var op_bank  =	document.getElementById('opBank').value;
	    	
	    	var params = {
	    			pact_no  :  pact_no,
	    			shouJu_no : shouJu_no,
	    			cif_name :  cif_name,
	    			id_no 	 :  id_no,
	    			pay_type : pay_type,
	    			pay_jine  :  pay_jine,
	    			start_time:  start_time,
	    			end_time  : end_time,
	    			bank_no  :  bank_no,
	    			op_bank  :  op_bank
   		 };
	    	
	    	 $.ajax({
				    type: "POST",
				    url: "printShouJu.action",
				    data: params,
				    dataType:"text", 
				    success: function(json){  
				    	var jsonObj=eval("("+json+")"); 
				    	$.each(jsonObj, function (i, item) {
				    		
				    	}); 
				    },
				}); 
    	}
    	
</script>
</head>

<body>

  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="feature-title">
        <h2>
          <s:text name="收据打印" />
        </h2>
      </div>
    </div>
	<div id="p" class="easyui-panel" title="收据展示"
  				  style="width:700px;height:300px;padding:10px;background:#fafafa;"
  				  data-options="iconCls:'icon-save',closable:true,
   					 collapsible:true,minimizable:true,maximizable:true">
	
	<!--startprint1-->
	<table>
		<tr>
				<td
						class="td-label"  
						style="text-align:left"
				>
					合同编号
				</td>
				<td>
					<input   id= "pactNo" name = "pactNo"/><font color="red">*</font>
				</td>
				<td
					class="td-label"  
					style="text-align:right">
						          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp 收据编号
				</td>
				<td>
						 	  <input id ="shouJuNo"  name = "shouJuNo"  value = "1410268" />
				</td>
				
		</tr>
	</table>	
	<table border="1" style="padding: 10px;" cellspacing="0" cellpadding="0">
             			<tr>
            				 <td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="客户姓名/名称"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				 	   colspan="3"
            				 	  
            				  >
            				 			<input id="cifName" name = "cifName" /><font color="red">*</font>
            				 </td>
           				</tr>
           				<tr>
            				<td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="身份证件号码"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				 	   colspan="3"
            				  >
            				 			<input id="idNo" name = "idNo" /><font color="red">*</font>
            				 </td>	
           				</tr>
                        <tr>
            				<td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="付款方式"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				  >
            				 			<input id="fukuanType" name = "fukuanType" />
            				 </td>
            				 <td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="付款金额"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				  >
            				 			<input id="fukuanJine" name = "fukuanJine" />
            				 </td>		 
                        </tr>
                        <tr>
                        	<td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="收益起算日期"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				  >
            				 			<input id="shouyiqisuanDate" name = "shouyiqisuanDate" />
            				 </td>
            				 
            				 <td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="产品终止日期"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				  >
            				 			<input id="chanpinzhongzhiDate" name = "chanpinzhongzhiDate" />
            				 </td>
                        </tr>
                         <tr>
                         		<td class="td-label" 
            					 style="text-align:center"
            					 >
            							<label class="record-label">
            								<s:text name="付款人账号"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				  >
            				 			<input id="fuKuanRenBankNo" name = "fuKuanRenBankNo" />
            				 </td>
            				 
            				 <td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="到账时间"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				  >
            				 			<input id="daoZhangDate" name = "daoZhangDate" value = "2015-12-12" />
            				 </td>
                        </tr>
                         <tr>
                         	<td class="td-label" 
            					 style="text-align:center"
            				 >
            							<label class="record-label">
            								<s:text name="付款人开户行"></s:text>
            							</label>            							
            				 </td>	
            				  <td class="td-label"
            				 	  style="text-align:center"
            				 	   colspan="3"
            				  >
            				 			<input id="opBank" name = "opBank" />
            				 </td>
                        </tr>
                        
                       <!--  <tr>
	                         <td
	                          class="td-label"
	                          rowspan="4" 
	                          colspan="4"
	                          style="text-align:right"
	                         >
	                         		江川财富投资管理（北京）有限公司（盖章）_____ 年____月____日 
	                         </td>
                        </tr>
                        
                         <tr>
	                         <td
	                         		class="td-label"
		                          rowspan="4" 
		                          colspan="4"
	                         >
	                         	 备注:(1) 资金到账以银行账单为准，收益起算日为到账之次日;
	                         	 	(2) 如对本收据内容有异议,请于本收据出具之日起10日内提出,过期视为无异议,
	                         	 	            以本收据记载信息为准。
	                         </td>
                        </tr> -->
    </table>
    <!--endprint1-->
    <a href="#" class="easyui-linkbutton" onclick="getShouju()">生成收据</a>
    <a href="#" class="easyui-linkbutton" onclick="printShouju()">打印收据</a>
	</div>
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



