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
<script type="text/javascript" src="../../js/print/jquery.js"></script>
<script type="text/javascript" src="../../js/print/flexpaper_flash.js"></script>
<script type="text/javascript" src="../../js/print/flexpaper_flash_debug.js"></script>

<script type="text/javascript">

	function cancel(){
		openPage('/contract/listReceiptPage.action');
	}
	
	function download(){
		var id='<s:property value="id"/>';
		openPage('/contract/downloadReceipt.action?id='+id);
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
		   
	<!-- saveClose -->	       
		   	   
    <!-- cancel -->
	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="cancel()" plain="true">
			           		<s:text name="返回" />
			           </a>
	          </span>
	          
	          <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-export" onclick="download()" plain="true">
			           		<s:text name="导出Excel" />
			           </a>
	          </span>
      </div>
<!---->

<div id="feature-content">
			<h2>
		    	<s:text name="收据信息" />
		    </h2>
		</div> 
		
<!-- 表单 -->			
	<div id="feature-content">  
   					<table style="padding: 10px;" cellspacing="10" cellpadding="0"width="100%">
   					      <tr>
									<td class="td-label"><label class="record-label"><s:text
												name="合同编号"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.pact_no" />
									</label></td>						
							</tr>
							
							<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="身份证件号码"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.customer.id_no" />
									</label></td>


							</tr>
							
							<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="付款方式"></s:text>:</label></td>

									<td class="td-value"><span
										onmousemove="this.setCapture();"
										onmouseout="this.releaseCapture();" onfocus="this.blur();">
											<s:select id="sex" list="#select.queryWPD('FUNDS_FROM')"
												listKey="opCode" listValue="opCnName" name="pactInfo.fund_sources"
												value="%{pactInfo.fund_sources}"></s:select>
									</span></td>



									<td class="td-label"><label class="record-label"><s:text
												name="付款金额"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="amtCN" />
									</label></td>
							</tr>
							
							<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="收益起算日期"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="startDate" />
									</label></td>



									<td class="td-label"><label class="record-label"><s:text
												name="产品终止日期"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="endDate" />
									</label></td>
							</tr>
							
							<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="付款人帐号"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_no" />
									</label></td>



									<td class="td-label"><label class="record-label"><s:text
												name="到帐时间"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="dzDate" />
									</label></td>
							</tr>
							
							<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="付款人开户行"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.account_bank" />
									</label></td>

							 <%-- <td class="td-label">
            						<label class="record-label">
            							<s:text name="entity.pactInfo.old_pactno"></s:text>:
            						</label>            						
            				</td>
            				<td class="td-value">
            					    <input id="old_pactno" name="pactInfo.old_pactno" value="<s:property value="pactInfo.old_pactno" />" cssClass="record-value" class="easyui-textbox" style="width:250px;"></input>
            				</td> --%> 
							</tr>
						    
						    <s:if test="pactInfo.fund_sources==2||pactInfo.fund_sources==3">
						    <tr>
									<td class="td-label"><label class="record-label"><s:text
												name="原合同号"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="pactInfo.old_pactno" />
									</label></td>

							</tr>
						    </s:if>
            				
            				
            				<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="客户经理"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:if test="pactInfo.pact_no.substring(0,2).equals('HB')||pactInfo.pact_no.substring(0,2).equals('XT')"><s:property value="" /></s:if>
											<s:else>
   											 <s:property value="jlName" />
											</s:else>
											
									</label></td>



									<td class="td-label"><label class="record-label"><s:text
												name="收据制作"></s:text>:</label></td>

									<td class="td-value"><label class="record-value">
											<s:property value="loginUserName" />
									</label></td>
							</tr>
          			</table>           
        </div> 
    
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



