<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>

<html>
  <head>
  	 <%@ include file="../../common.jsp"%>
    <title><s:text name="repayDetailTitle"></s:text></title>
     <script  src="${root}/js/loan/repay/repayDetail_zh_CN.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  	<!-- 隐藏域参数 -->
  	<div id="hiddenvalue"  name="hiddenvalue">
  	    <form  name="repayForm"  id="repayForm">
  	    	<!-- 应还本金 -->
	  		<input id="repayBean.returnCapital" name="repayBean.returnCapital" type="hidden"  value="${ratedayBean.capital}"/>
	  		<!-- 应还利息 -->
	  		<input id="repayBean.returnInterest" name="repayBean.returnInterest" type="hidden"  value="${ratedayBean.interest}"/>
	  		<!-- 借据号 -->
	  		<input id="repayBean.dueNo" name="repayBean.dueNo" type="hidden"  value="${ratedayBean.dueNo}"/>
	  		<!-- 还款计划结束日期 -->
	  		<input id="repayBean.endDate" name="repayBean.endDate" type="hidden"  value="${endDate}"/>
	  		<!-- 还款计划开始日期 -->
	  		<input id="repayBean.begDate" name="repayBean.begDate" type="hidden"  value="${begDate}"/>
	  		
	  		
	  		<!-- 逾期利息 -->
	  		<input id="repayBean.overInterest" name="repayBean.overInterest" type="hidden"  value="${ratedayBean.overInterest}"/>
	  		<!-- 复利利息 -->
	  		<input id="repayBean.cmpdInterest" name="repayBean.cmpdInterest" type="hidden"  value="${ratedayBean.cmpdInterest}"/>
	  		<!-- 其他费用 -->
	  		<input id="repayBean.otherFee" name="repayBean.otherFee" type="hidden"  value="${otherFee}"/>
			<!-- 客户号 -->
	  		<input id="repayBean.cifNo" name="repayBean.cifNo" type="hidden"  value="${cifNo}"/>
  			<!-- 期号 -->
	  		<input id="repayBean.termNo" name="repayBean.termNo" type="hidden"  value="${termNo}"/>
			<!-- 借据金额 -->
	  		<input id="repayBean.dueAmt" name="repayBean.dueAmt" type="hidden"  value="${dueAmt}"/>
	  		<!-- 客户名称 -->
	  		<input id="repayBean.cifName" name="repayBean.cifName" type="hidden"  value="${cifName}"/>
	  		<!-- 提前还款本金 -->
	  		<input id="repayBean.advaceAmt" name="repayBean.advaceAmt" type="hidden"  value="${advaceAmt}"/>
	  		
	  		
  			
  		</form>
  			<!-- 利息 逾期利息 复利利息的和 -->
	  		<input id="totalInterest" name="totalInterest" type="hidden"  value="${totalInterest}"/>
	  		<!-- 判断是不是最后一天还款  -->
	  		<input id="isLastDay" name="isLastDay" type="hidden"  value="${isLastDay}"/>
  	</div>
  	
  	<fieldset>
  		 <legend>借据信息:</legend>
  		 <table>
			<tr>
				<td>客户号:</td>
				<td><s:property value="#acLnMstBean.cifNo"/></td>
				<td>客户名称:</td>
				<td>${cifName}</td>
			</tr>  
			
			<tr>
				<td>借据金额:</td>
				<td><s:property value="#acLnMstBean.dueAmt"/></td>
				<td>借据余额:</td>
				<td><s:property value="#acLnMstBean.dueBal"/></td>
			</tr>
			
			
			<tr>
				<td>借据开始日期:</td>
				<td><s:property value="#acLnMstBean.dueBegDate"/></td>
				<td>借据结束日期:</td>
				<td><s:property value="#acLnMstBean.dueEndDate"/></td>
			</tr>		 
  		 </table>
  	</fieldset>
  	
  	
  	
  	<table>
  		<tr>
  			<td>应还本金:</td>
  			<td>
  				<s:property  value="#ratedayBean.capital"/>
  			</td>
  			
  			<td>应还利息:</td>
  			<td>
  				<s:property  value="#ratedayBean.interest"/>
  			</td>
  		</tr>
  		
  		<tr>
  			<td>逾期利息:</td>
  			<td>
  				<s:property  value="#ratedayBean.overInterest"/>
  			</td>
  			
  			<td>复利利息:</td>
  			<td>
  				<s:property  value="#ratedayBean.cmpdInterest"/>
  			</td>
  		</tr>
  		
  		<tr>
  			<td>欠款金额</td>
  			<td>${debtMoney}</td>
  		</tr>
		
		<tr>
			<td>还款类型:</td>
			<td>
				<select id="payType"  name="payType"  onchage="setBank()">
					<option value="1">银行转账</option>
					<option value="0">现金</option>
				</select>
			</td>
			
			<td>银行名称:</td>
			<td>
				<select id="bankNo"  name="bankNo">
					<option value="">请选择</option>
					<option value="11">中国银行</option>
					<option value="12">工商银行</option>
					<option value="13">农业银行</option>
					<option value="14">交通银行</option>
					<option value="15">建设银行</option>
				</select>
			</td>
		</tr>
		  		
  		<tr>
  			<td>
  			还款金额:(是否可以编辑)
  			</td>
  			<td>
  				<input id="returnAmt" name="returnAmt"  maxlength=10  value=${totalInterest}> </input>
  			</td>
  			
  			<s:if test="#is_privilege==1">
  			<td>
  			优惠金额:
  			</td>
  			
  			<td>
  				<input id="privilege" name="privilege" value="0.00"  maxlength=10 onBlur="compare()"> </input>
  			</td>
  			</s:if>
  		</tr>
  		
  		
  		<s:if test="#account_fee==1">
  			<tr>
	  			<td>
	  			账户管理费:
	  			</td>
	  			<td>
	  				<s:property  value="#otherFee"/>
	  			</td>
	  			<td>
	  			还款日期:
	  			</td>
	  			
	  			<td>
	  				<input class="Wdate" type="text" id="occDate" name="occDate" value="${systemDate}"  onClick="WdatePicker({isShowClear:false,readOnly:true})"/>
	  			</td>
  		</tr>
  		
  		</s:if>
  		
  		<tr>
  			<td>是否保证金充值:</td>
	  		<td>
	  			<select  id="isPerfAmount"   name="isPerfAmount" >
	  				<option value="1">是</option>
	  				<option value="0">否</option>
	  			</select>
	  		</td>
  		
  			<td>保证金充值:</td>
	  			<td>
	  				 <input id="perfAmount" name="perfAmount" value="${perfAmount}"> </input>
	  			</td>
  		</tr>
  		
  		
  		<s:if test="#advaceAmt>0">
  			<tr>
  				<td>提前还款本金:</td>
  				<td>
  				<s:property  value="#advaceAmt"/>
  				</td>
  			</tr>
  		</s:if>
  	</table>
  
  
    
       <input type="button"  onclick="repay()" id="repay" name="repay"  value="还款"/>
    
   
       
  </body>
</html>
