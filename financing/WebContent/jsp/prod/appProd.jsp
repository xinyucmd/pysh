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
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'saveAppProd.action';
			addObjectForm.submit();
			//baseCancel("ProdApp");
		}
		
		function saveOn() {
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'saveOnProd.action';
			addObjectForm.submit();
			//baseCancel("ProdApp");
		}
		
		function refApp() {
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'refAppProd.action';
			addObjectForm.submit();
			//baseCancel("ProdApp");
		}
		
		function off() {
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'offProd.action';
			addObjectForm.submit();
			//baseCancel("ProdApp");
		}
		
		function on() {
			var addObjectForm = document.getElementById('addObjectForm');
			addObjectForm.action = 'onProd.action';
			addObjectForm.submit();
			//baseCancel("ProdApp");
		}
	
		
		function cancel() {
			baseCancel("ProdApp");
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
            name="title.action" />:&nbsp;&nbsp;</b> 
           <s:if test="prodBase.sts=='10'">
            
            <span
          style="white-space: nowrap;"> <a id="save_accept_btn"
            href="#" class="easyui-linkbutton"
            iconCls="icon-save-accept" onclick="save()" plain="true"><s:text
                name="menu.appProd.shenpiok" /></a>
        </span> <span> <span style="white-space: nowrap;"> <a
              id="save_go_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-save-go" onclick="saveOn()" plain="true"><s:text
                  name="menu.appProd.tg_qiyong" /></a>
          </span> <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="refApp()" plain="true"><s:text
                  name="menu.appProd.close" /></a>
          </span>
          		
          </s:if>
          
          
	      <s:if test="prodBase.sts=='15' && prodBase.delFlg==1" >
	      		<span style="white-space: nowrap;"> <a
              id="save_go_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-save-go" onclick="on()" plain="true"><s:text
                  name="menu.appProd.qiyong" /></a>
          </span> <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="refApp()" plain="true"><s:text
                  name="menu.appProd.close" /></a>
	          	
          </s:if>
	       
	         
	         <s:if test="prodBase.sts=='15' && prodBase.delFlg==0" >
	         
	          	 <span style="white-space: nowrap;"> <a
	              id="save_go_btn" href="#" class="easyui-linkbutton"
	              iconCls="icon-save-go" onclick="off()" plain="true"><s:text
	                  name="menu.appProd.stop" /></a>
	          </span>
	       
	         </s:if>
         
          <span style="white-space: nowrap;"> <a id="cancel_btn"
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

      <div id="feature-title">
        
          <h2>
            <s:text name="menu.appProd.base_information" />
          </h2>
       
      </div>

      <div id="feature-content">
       <s:form id="addObjectForm"   namespace="/jsp/prod"
					method="post">
					<input type="hidden" name="multiReturnRate" id="multiReturnRate" />
					<input type="hidden" name="multiRate" id="multiRate" />
					<input type="hidden" name="prodBase.prdtNo" value="<s:property value="prodBase.prdtNo" />"/>
					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						
						<tr>
							<!-- prdt_no -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.PADT_NO"></s:text>:
							</label></td>
							<td class="td-value">
						<label class="record-value"> <s:property value="prodBase.prdtNo" />
							</label>
							     
							
							</td>
							<!-- prdt_name -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.PADT_NAME"></s:text>:
							</label></td>
							<td class="td-value">
								<label class="record-value"><s:property value="prodBase.prdtName" /></label></td>
							<td class="td-label"></td>
						</tr>
						<tr>
							<!-- standardAMT -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.STANDARD_AMT"></s:text>:
							</label></td>
							<td class="td-value">
									<label class="record-value"> <s:property value="prodBase.standardAmt" /></label>
								</td>
							<!--  advanceRedeem  -->

							<td class="td-label"><label class="record-label"> <s:text
										name="menu.ProductConfiguration.ADVANCE_REDEEM"></s:text>:
							</label></td>
							<td class="td-value">
							<s:if test="prodBase.advanceRedeem==1">
								<label class="record-value"><s:text name="是"></s:text></label>
							</s:if>
   							<s:else>
   								<label class="record-value"><s:text name="否"></s:text></label>
   							</s:else>
							</td>
							
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
							<td >
								<s:if test="#aa.paymentType==1">
									<label class="record-value"><s:text name="menu.appProd.lisuibenqing"></s:text></label>
								</s:if>	
								<s:elseif test="#aa.paymentType==2">
								    <label class="record-value"><s:text name="menu.appProd.xianxihouben"></s:text></label>
								</s:elseif>	
								<s:elseif test="#aa.paymentType==3">
								    <label class="record-value"><s:text name="menu.appProd.dengebenjin"></s:text></label>
								</s:elseif>	
									
							</td>
						<td ><label class="record-label" style="float:right;"> <s:text
										name="menu.ProductConfiguration.STAGE_DAYS"></s:text>:
							</label></td>
							
							<td >
								<label class="record-value"> <s:property value="#aa.stageMax" /></label>
								<label class="record-label">期 </label>
							</td>
							<td ><label class="record-label" style="float:right;"><s:text
										name="menu.ProductConfiguration.RETURN_RATE"></s:text>: </label></td>
							
							<td > 
								<label class="record-value"> <s:property value="#aa.returnRate" /></label>
								<label class="record-label">% </label>
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
							<td >
								<label class="record-value"> <s:property value="#bb.stageMin" /></label>
								<label class="record-label"> -</label> 
								<label class="record-value"> <s:property value="#bb.stageMax" /></label>
								<label class="record-label">期 </label>
							</td>
							<td ><label class="record-label" style="float:right;"> <s:text
										name="menu.ProductConfiguration.IS_REDEEM_BEFORE"></s:text>:
							</label></td>
							<td >
								<s:if test="#bb.ifRedeem==1">
									<label class="record-value"><s:text name="是"></s:text></label>
								</s:if>
	   							<s:else>
	   								<label class="record-value"><s:text name="否"></s:text></label>
	   							</s:else>
									
							</td>
							
							<td ><label class="record-label" style="float:right;"><s:text
										name="menu.ProductConfiguration.RETURN_RATE"></s:text>: </label></td>
							<td>
							
								<label class="record-value"> <s:property value="#bb.rate" /></label>
								<label class="record-label">% </label>
							</td>
							
						</tr>
						
					</s:iterator>
				</table>             
              
              
                                          
              

              
            </div>
            
            
            <div title="<s:text name='menu.appProd.shenpiyijian'/>"
              style="padding: 10px;" >
              <div class="section-header">
                <span><s:text name="menu.appProd.shenpiyijian" /></span>
              </div>
              <table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%" id="feerateTable2" name="feerateTable2">
						 <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="menu.appProd.shenpiyijian"></s:text>：</label></td>
                  <td class="td-value" valign="top">             
                  	  <s:textarea 
                      name="prodBase.approveIdea" rows="5" cssStyle="width:350px;" cssClass="record-value" /></td>
                      <td class="td-mass-update">
                   </td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>      
					
				</table>             
              
              
                                       
              

              
            </div>

            </s:form>  

          </div>

      
      </div>
    </div>
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



