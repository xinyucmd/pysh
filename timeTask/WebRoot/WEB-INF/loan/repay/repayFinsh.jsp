<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="../../common.jsp"%>
    <title>已还信息 </title>
    <script >
    // 跳转到还款列表
    fuction  toRepayList(){
    	window.location.href="${root}/repay_toRepayList.action";
    }
    
    </script>
  </head>
  
  <body>
  <table>
	<tr>
		<td>客户号:</td>
		<td>
			<s:property  value="#repayBean.cifNo"/>
		</td>
		<td>客户名称:</td>
		<td>
			<s:property  value="#repayBean.cifName"/>
		</td>
	</tr>  
	
	<tr>
		<td>开始日期:</td>
		<td><s:property  value="#repayBean.begDate"/></td>
		<td>结束日期:</td>
		<td><s:property  value="#repayBean.endDate"/></td>
	</tr>  
	
	<tr>
		<td>本金:</td>
		<td><s:property  value="#repayBean.returnCapital"/></td>
		<td>利息:</td>
		<td><s:property  value="#repayBean.returnInterest"/></td>
	</tr>  
	
	<tr>
		<td>账户管理费:</td>
		<td><s:property  value="#repayBean.otherFee"/></td>
		<td>罚息:</td>
		<td>${repayBean.overInterest+repayBean.cmpdInterest}</td>
	</tr>  
  </table>
  
  <input   type="button"  value="确定"  onclick="toRepayList()"/>
  
  
  </body>
</html>
