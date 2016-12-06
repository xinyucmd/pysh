<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<s:action name="select" id="select"></s:action>
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
<script type="text/javascript" src="../../js/jquery.edatagrid.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
		function save() {
			collectData();
			collectData2();
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'saveProd.action';
			addObjectForm.submit();
			//baseCancel("Prod");
		}
		function collectData() {
		
			var c = "";
			for (index = 0; index < document.getElementsByName("stageMax1").length; index++) {
				c +=document.getElementsByName("paymentType")[index].value + ","
					+document.getElementsByName("stageMax1")[index].value + ","
						+ document.getElementsByName("returnRate")[index].value + "|";
			}
			document.getElementById("multiReturnRate").value = c;
		
			
		}
		function collectData2() {
			var a = "";
			for (index = 0; index < document.getElementsByName("stageMax").length; index++) {
				a += document.getElementsByName("stageMin")[index].value + ","
						+ document.getElementsByName("stageMax")[index].value + ","
						+ document.getElementsByName("ifRedeem")[index].value + ","
						+ document.getElementsByName("rate")[index].value +  "|";
			
			}
			document.getElementById("multiRate").value = a;
			
		}
		function saveClose() {
			collectData();
			collectData2();
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'saveSubmitProd.action';
			addObjectForm.submit();
			//baseCancel("Prod");
		}
		function cancel() {
		baseCancel("Prod");
		}
		
		var k = 1;
		function addFeeRate1() {
		k++;
		r = feerateTable1.insertRow();
		r.setAttribute("id", "fR" + k);
		c = r.insertCell();
		c.innerHTML ="<td align='right'><label class='record-label' style='float:right;'> 支付方式:</label></td>";
		
		c = r.insertCell();
		c.innerHTML ="<select name='paymentType' class='record-value'  class='class java.util.HashMap'>    <option value='10005'>利随本清</option>    <option value='10006'>先息后本</option>  <option value='10007'>等额本金</option> ></select>";
		
		c = r.insertCell();
		c.innerHTML ="<label class='record-label' style='float:right;'> 理财期限:</label>";
		
		c = r.insertCell();
		c.innerHTML = "<input name='stageMax1' cssClass='record-value' /><label class='record-label'> 期 </label></td>";
		
		
		c = r.insertCell();
		c.innerHTML ="<label class='record-label' style='float:right;'> 年化收益费率:</label>";
		
		c = r.insertCell();
		c.innerHTML = "<input name='returnRate' cssClass='record-value' /> <label class='record-label'> % </label>";
		
		c = r.insertCell();
		c.innerHTML = "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' onclick='removeFeeRate1(this)' plain='true'>删除</a>";
	
		}
		function removeFeeRate1(r) {
		feerateTable1.deleteRow(r.parentNode.parentNode.rowIndex);
		}
		var m = 1;
		function addFeeRate2() {
		m++;
		r = feerateTable2.insertRow();
		r.setAttribute("id", "fR" + m);
		c = r.insertCell();
		c.innerHTML="<label class='record-label' style='float:right;'> 赎回期限:</label>";
		
		
		c = r.insertCell();
		c.innerHTML = "<td ><input name='stageMin' cssClass='record-value' /> - <input name='stageMax' cssClass='record-value' /><label class='record-label'>期 </label>";
	
		
		c = r.insertCell();
		c.innerHTML="<label class='record-label' style='float:right;'> 是否可赎回:</label>";
		
		c = r.insertCell();
		c.innerHTML = "<td ><select name='ifRedeem' class='record-value'  class='class java.util.HashMap'>    <option value='10003'>是</option>    <option value='10004'>否</option></select>";
	
		c = r.insertCell();
		c.innerHTML="<label class='record-label' style='float:right;'> 年化收益费率:</label>";
		
		c = r.insertCell();
		c.innerHTML = "<td ><td class='td-value'><input name='rate' cssClass='record-value' /> <label class='record-label'>% </label></td>";
	
		
		c = r.insertCell();	
		c.innerHTML = "<td ><a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-add\" onclick=\"removeFeeRate2(this)\" plain=\"true\">删除</a>";

		}
		function removeFeeRate2(r) {
		feerateTable2.deleteRow(r.parentNode.parentNode.rowIndex);
		}
	</script>
</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
   
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="shortcuts" class="headerList">
        <b style="white-space: nowrap; color: #444;"><s:text
            name="title.action" />:&nbsp;&nbsp;</b> <span> <span
          style="white-space: nowrap;"> <a id="save_accept_btn"
            href="#" class="easyui-linkbutton"
            iconCls="icon-save-accept" onclick="save()" plain="true"><s:text
                name="button.save" /></a>
        </span> <span> <span style="white-space: nowrap;"> <a
              id="save_go_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-save-go" onclick="saveClose()" plain="true"><s:text
                  name="button.save.commit" /></a>
          </span> <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
          </span> <s:if test="account!=null && account.id!=null">
              <span style="white-space: nowrap;"><a
                href="javascript:void(0)" id="mtmt"
                class="easyui-menubutton"
                data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                    name='menu.toolbar.more.title' /></a>
                <div id="mtm1" style="width: 150px;">
                  <div data-options="iconCls:'icon-import'"
                    onClick="openwindow2('/financing/showChangeLogPage.action?entity=Account&recordID=' + '<s:property value="account.id" />',750,500)">
                    <s:text name='menu.item.changeLog.title' />
                  </div>
                </div> </span>
            </s:if>
        </span>
      </div>

     
      <div id="feature-content">
       <s:form id="addObjectForm"   namespace="/jsp/prod"
					method="post">
					<input type="hidden" name="multiReturnRate" id="multiReturnRate" />
					<input type="hidden" name="multiRate" id="multiRate" />
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						
						<tr>
							<!-- prdt_no -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.PADT_NO"></s:text>:
							</label></td>
							<td class="td-value">
						<label class="record-value"> <s:property value="prodBase.prdtNo" /></label>
							     
							
							</td>
							<!-- prdt_name -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.PADT_NAME"></s:text>:
							</label></td>
							<td class="td-value"><input name="prodBase.prdtName" value="<s:property value="prodBase.prdtName" />"
								class="record-value" /></td>
							<td class="td-label"></td>
						</tr>
						<tr>
							<!-- standardAMT -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.STANDARD_AMT"></s:text>:
							</label></td>
							<td class="td-value"><input name="prodBase.standardAmt" value="<s:property value="prodBase.standardAmt" />"
								class="record-value" /></td>
							<!--  advanceRedeem  -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.ADVANCE_REDEEM"></s:text>:
							</label></td>
							<td class="td-value"><s:select
									class="easyui-combogrid record-value" style="width: 50px;"
									list="#select.queryWPD('YES_NO')" listKey="opCode"
									listValue="opCnName" name="prodBase.advanceRedeem" value="%{prodBase.advanceRedeem}"></s:select></td>
							<td class="td-value"></td>
							<td class="td-label"></td>
						</tr>
							
					</table>
		
          <div id="tt" class="easyui-tabs">
            <div title="<s:text name='menu.appProd.dingjia_information'/>"
              style="padding: 10px;">
              <div class="section-header">
                <span><s:text name="menu.appProd.chanpindingjia_information" /></span>
              </div>
              <table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" id="feerateTable1" name="feerateTable1">
					
					<s:iterator id="aa" value="listA" status="i">
						<tr>
							<td  ><label class="record-label" style="float:right;"> <s:text
										name="menu.appProd.zhifu_type"></s:text>:</label></td>
							<td ><s:select
									list="#select.queryWPD('PAYMENT_TYPE')" listKey="opCode"
									listValue="opCnName" name="paymentType"
									cssClass="record-value" value="#aa.paymentType" ></s:select></td>
						<td ><label class="record-label" style="float:right;"> <s:text
										name="menu.ProductConfiguration.STAGE_DAYS"></s:text>:
							</label></td>
							
							<td ><input name="stageMax1"
								cssClass="record-value" value="<s:property value="#aa.stageMax"/>"/><label class="record-label">
									期 </label></td>
							<td ><label class="record-label" style="float:right;"><s:text
										name="menu.ProductConfiguration.RETURN_RATE"></s:text>: </label></td>
							
							<td ><input name="returnRate"
								cssClass="record-value" value="<s:property value="#aa.returnRate"/>"/> <label class="record-label">
									% </label></td>
							<td>
							<s:if test="#i.index == 0">
							<a href="javascript:void(0)"   onclick="addFeeRate1()"
            plain="true">增加</a>
                            </s:if>
                            <s:else>
                            <a href="javascript:void(0)"   onclick="removeFeeRate1(this)"
            plain="true">删除</a>
                            </s:else>
							</td>
						</tr>
				   </s:iterator>
					</table>
        

            </div>
            
            <div title="<s:text name='menu.appProd.tiqianshuhui'/>"
              style="padding: 10px;" >
              <div class="section-header">
                <span><s:text name="menu.appProd.tiqianshuhui" /></span>
              </div>
              <table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" id="feerateTable2" name="feerateTable2">
						
					<s:iterator id="bb" value="listB" status="i">
						<tr>
							<td ><label class="record-label" style="float:right;"><s:text
										name="menu.ProductConfiguration.REDEEM_DAYS"></s:text>: </label></td>
							<td ><input name="stageMin"  value="<s:property value="#bb.stageMin"/>"
								cssClass="record-value" /> - <input name="stageMax"  value="<s:property value="#bb.stageMax"/>"
								cssClass="record-value" /><label class="record-label">
									期 </label></td>
							<td ><label class="record-label" style="float:right;"> <s:text
										name="menu.ProductConfiguration.IS_REDEEM_BEFORE"></s:text>:
							</label></td>
							<td ><s:select
									class="record-value" 
									list="#select.queryWPD('YES_NO')" listKey="opCode"
									listValue="opCnName" name="ifRedeem" value="#bb.ifRedeem"></s:select></td>
							
							<td ><label class="record-label" style="float:right;"><s:text
										name="menu.ProductConfiguration.RETURN_RATE"></s:text>: </label></td>
							<td><input name="rate" value="<s:property value="#bb.rate"/>"
								/> <label class="record-label">
									% </label></td>
							<td><s:if test="#i.index == 0">
							<a href="javascript:void(0)"   onclick="addFeeRate2()"
            plain="true">增加</a>
                            </s:if>
                            <s:else>
                            <a href="javascript:void(0)"   onclick="removeFeeRate2(this)"
            plain="true">删除</a>
                            </s:else></td>
						</tr>
						
					</s:iterator>
				</table>      
              
                                          
              

              
            </div>
            
            <s:if test="prodBase.sts=='20'">
            
                <div title="<s:text name='审批意见'/>"
              style="padding: 10px;" >
              <div class="section-header">
                <span><s:text name="审批意见" /></span>
              </div>
              <table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" id="feerateTable2" name="feerateTable2">
						 <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="审批意见"></s:text>：</label></td>
                  <td class="td-value" valign="top">             
                  	  <s:textarea 
                      name="prodBase.approveIdea" rows="5" cssStyle="width:350px;" cssClass="record-value" readonly="true"/></td>
                      <td class="td-mass-update">
                   </td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>      
					
				</table>             
              
              
            </div>
            
            
            
            </s:if>
            
          </div>

      </s:form>
      </div>
    </div>
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



