<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
      <form id="editForm" >
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
		
		<%--   <tr height="36">
		    <th align="right"><strong style="color: red;">*</strong>竞拍方式：&nbsp;&nbsp;</th>
		    <td>
		    <s:radio list="#{1:'明拍',2:'暗拍'}" theme="simple" name="paramMap.auctionMode"></s:radio>
		     <strong style="color: red;"><s:fielderror fieldName="paramMap.auctionMode" /></strong></td>
		  </tr> --%>
		  <tr height="36">
		    <th align="right"><strong style="color: red;">*</strong>债权价值：&nbsp;&nbsp;</th>
		    <td><span id="span_debtSum">${debtValue}</span>元
		    	<s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden>
		    </td>
		  </tr>
		  <tr height="36">
		    <th align="right"><strong style="color: red;">*</strong>转让价格：&nbsp;&nbsp;</th>
		    <td><span id="span_debtPrice">${debtPrice}</span>元
		    	<s:hidden id="investId" name="paramMap.investId"></s:hidden>
		    	<!--<s:hidden id="debtPrice" name="paramMap.debtPrice"></s:hidden>-->
		    	<s:hidden id="term" name="paramMap.term"></s:hidden>
		    	<s:hidden id="interest" name="paramMap.interest"></s:hidden>
		    	<s:hidden id="principalBalance" name="paramMap.principalBalance"></s:hidden>
		    </td>
		  </tr>
		  <tr height="36">
		    <th align="right"><strong style="color: red;">*</strong>剩余期限：&nbsp;&nbsp;</th>
		    <td><span id="span_remainPeriod">${remainPeriod}</span>个月
		    	<input id="remainPeriod" name="remainPeriod" value="${paramMap.remainPeriod}" type="hidden" />
		    </td>
		  </tr>
		   <tr height="36">
		    <th width="26%" align="right"><strong style="color: red;">*</strong>转让期限：&nbsp;&nbsp;</th>
		    <td width="74%">
		    	<s:select id="debtLimitList" list="debtLimitList" name="paramMap.debtLimit" listKey="selectId" listValue="selectValue" value="-100">
		    	</s:select>
		    	&nbsp;天
		    	<%-- <strong style="color: red;"><s:fielderror fieldName="paramMap.debtLimit" /></strong> --%>
		    </td>
		  </tr> 
		 <%-- <tr height="36">
		    <th align="right"><strong style="color: red;">*</strong>竞拍底价：&nbsp;&nbsp;</th>
		  //whb去掉竞拍  <td><input type="text" class="inp90" name="paramMap.auctionBasePrice" value="${ paramMap.auctionBasePrice}" />
		    <strong style="color: red;"><s:fielderror fieldName="paramMap.auctionBasePrice" /></strong>
		    </td> 
		</tr>--%>
		   <input id="borrowId" name="paramMap.borrowId" value="${paramMap.borrowId }" type="hidden" />
		<!-- 
		   <tr height="36">
		    <th align="right"><strong style="color: red;">*</strong>转让系数：&nbsp;&nbsp;</th>
		    <td><input id="transRatio" class="inp90" name="paramMap.transRatio"/>
		    <strong style="color: red;">&nbsp;&nbsp;转让系数需在0-1之间</strong>
		   <%--  <strong style="color: red;"><s:fielderror fieldName="paramMap.transRatio" /></strong>  --%>
		    </td>
		  </tr>
		   -->
		   <input id="transRatio"  type="hidden" name="paramMap.transRatio" value="1"/>
		  <tr height="158">
		    <th align="right" valign="top"><strong style="color: red;">*</strong>转让描述：&nbsp;&nbsp;</th>
		    <td><textarea id="details" class="txt380" rows="6" cols="30" name="paramMap.details">${paramMap.details }</textarea><br />
		    <%-- <strong style="color: red;"> <s:fielderror fieldName="paramMap.details" /></strong> --%></td>
		  </tr>
		  <tr height="36">
		    <td>&nbsp;</td>
		    <td style="padding-top:20px;"><!--
		    <a href="javascript:void(0);" id="debt_ok" class="scbtn">确认</a> 
		    --><input id="debt_ok" class="scbtn" type="button" value="确认"/>
		    <a href="javascript:void(0);" id="debt_cancel" class="scbtn" id="qxtck">取消</a></td>
		  </tr>
		</table>
 </form>

<script>
var flag = true;
   $(function(){
		  $("#transRatio").blur(function(){
			  // 不用债权系数了
			  // debtPrice();
		  });
	   
	   
   		$("#debt_cancel").click(function(){
   			$("#zrzq_div").attr("style","display:none");
   			$("#editForm")[0].reset();
   		});
   		
   		$("#debt_ok").click(function(){
   			//添加校验
   			if($("#debtLimitList").val() == "" || $("#debtLimitList").val() == -100){
   				alert("转让期限不能为空");
   			 	flag = true;
   			 	return;
   			}
   			if($("#transRatio").val() == ""){
   				alert("转让系数不能为空");
   			 	flag = true;
   			 	return;
   			}
   			if($("#transRatio").val() > 1 || $("#transRatio").val() < 0){
   				alert("转让系数需在0-1之间");
   			 	flag = true;
   			 	return;
   			}
   			if($("#details").val() == ""){
   				alert("转让描述不能为空");
   			 	flag = true;
   				 return;
   			}
             if(flag){
	   			var para = $("#editForm").serialize();
	   			
              flag = false;
   			$.shovePost("addAssignmentDebt.do",para,function(data){
   				if(data == 1){
   					alert("债权转让申请成功");
   					$("#debt_cancel").click();
   					$("#btn_search").click();
   				}else if(data > 1){
   					alert("本月累计申请金额超出可转让限额"+data+"元");
   					$("#debt_cancel").click();
   				}
   				else if(data == -1){
   					alert("债权转让申请失败");
   					$("#debt_cancel").click();
   				}else{
   				 flag = true;
   					$("#debt_add").html(data);
   				}
   			});
   			}
   		});
   });
   
   function debtPrice(){
		if($("#transRatio").val() == ""){
			alert("转让系数不能为空");
		 	flag = true;
		 	return;
		}else{
			var term = $("#term").val();
			var interest = parseFloat($("#interest").val());
			var principalBalance = parseFloat($("#principalBalance").val());
			var transRatio = parseFloat($("#transRatio").val());
			var debtPrice = 0.00;
			if(-1 == term){
				debtPrice = principalBalance * transRatio + interest;
				$("#span_debtPrice").html(debtPrice.toFixed(2));
				$("#debtSum").val(debtPrice);
			}else{
				debtPrice = principalBalance * transRatio - interest;
				$("#span_debtPrice").html(debtPrice.toFixed(2));
				$("#debtSum").val(debtPrice);
			}
			
		}
   }	
   
   
</script>

