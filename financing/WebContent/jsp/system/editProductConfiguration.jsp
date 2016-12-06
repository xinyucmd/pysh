<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

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
        baseSave("ProductConfiguration");      
	 }
	 function saveClose() {
		baseSaveClose("ProductConfiguration");
		}
	 function cancel() {
			baseCancel("ProductConfiguration");
		}
		function cancel() {
			baseCancel("ProductConfiguration");
		}

</script>
</head>

<body>
  <div id="page-wrap">
    
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
    
      <div id="shortcuts" class="headerList">
    <!-- save -->
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
    <!-- cancel -->
	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-cancel" 
			           onclick="cancel()" plain="true">
			           		<s:text name="button.cancel" />
			           </a>
	          </span>
      </div>
<!--添加产品配置 -->
		<div id="feature-content">
			<h2>
		    	<s:text name="menu.ProductConfiguration.save" />
		    </h2>
		</div> 
<!-- 表单 -->			
	 <div id="feature-content">
	 		 <s:form id="addObjectForm" validate="true"  namespace="/jsp/system" method="post">
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
            				<tr>
            				
            				<!-- productNo --> 
            					    <td class="td-mass-update">
            					    	  <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
              							 	 value="productNo" />
              						</td>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.PADT_NO"></s:text>:
            							</label>            						
            					    </td>
            					     <td class="td-value">
            					     		<s:textfield name="prdt.productNo" cssClass="record-value" />
            					     </td>
            				<!--productName  -->
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="productName" />
               						 </td>
	            					 <td class="td-label">
	            							<label class="record-label">
	            								<s:text name="menu.ProductConfiguration.PADT_NAME"></s:text>:
	            							</label>	            							
	            					 </td>
	            					  <td class="td-value">
            					     		<s:textfield name="prdt.productName" cssClass="record-value" />
            					      </td>
            				</tr>            					 	
            				<tr>
            				<!-- standardAMT -->	
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="standardAMT" />
               						 </td>      		
            					     <td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.STANDARD_AMT"></s:text>:
            							</label>            								
            					     </td>
            					     <td class="td-value">
            					     		<s:textfield name="prdt.standardAMT" cssClass="record-value" />
            					     </td>            					    
            				
            				<!--  advanceRedeem  -->
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="advanceRedeem" />
               						 </td> 
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.ADVANCE_REDEEM"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<s:textfield name="prdt.advanceRedeem" cssClass="record-value" />
            					    </td>  
            				</tr>
            				<tr>
            				<!-- prymentType -->
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="prymentType" />
               						 </td>
            					    <td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.PRYMENT_TYPE"></s:text>:
            							</label>            							
            					    </td>
            					    <td class="td-value">
            					     		<s:textfield name="prdt.prymentType" cssClass="record-value" />
            					    </td>
            				
            				<!-- stageDays -->
            						 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="stageDays" />
               						 </td>
            						 <td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.STAGE_DAYS"></s:text>:
            							</label>            							
            					    </td>
            					    <td class="td-value">
            					     		<s:textfield name="prdt.stageDays" cssClass="record-value" />
            					    </td>
            				</tr>
            				<tr>
            				<!-- returnRate -->
            				 		 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="returnRate" />
               						 </td>
            					   	<td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.RETURN_RATE"></s:text>:
            							</label>            							
            					    </td>
            					     <td class="td-value">
            					     		<s:textfield name="prdt.returnRate" cssClass="record-value" />
            					    </td>  		
            				<!-- redeemDays -->
            				 		 <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="redeemDays" />
               						 </td>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.REDEEM_DAYS"></s:text>:
            							</label>            						
            					    </td>
            					    <td class="td-value">
            					     		<s:textfield name="prdt.redeemDays" cssClass="record-value" />
            					    </td>
            				</tr>
            				<tr>		    
            					    <!-- IS_REDEEM_BEFORE -->
            					    <td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="isRedeemBefore" />
               						 </td>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.IS_REDEEM_BEFORE"></s:text>:
            							</label>            						
            					    </td>
            					     <td class="td-value">
            					     		<s:textfield name="prdt.isRedeemBefore" cssClass="record-value" />
            					    </td>
            				<!--rates  -->
            						<td class="td-mass-update">
            									 <input id="massUpdate" name="massUpdate" type="checkbox" class="massUpdate"
               											 value="rates" />
               						 </td>
            						<td class="td-label">
            							<label class="record-label">
            								<s:text name="menu.ProductConfiguration.RATES"></s:text>:
            							</label>            						
            					    </td>
            					     <td class="td-value">
            					     		<s:textfield name="prdt.rates" cssClass="record-value" />
            					    </td>
            				</tr>
          			</table>
          	</s:form>
    </div>
    
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



