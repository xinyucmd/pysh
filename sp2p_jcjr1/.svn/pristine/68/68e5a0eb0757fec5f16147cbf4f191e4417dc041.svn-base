<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link type="text/css" rel="stylesheet" href="/new_css/css.css"/>
</head>
<body>
<div class="nymain" style="width:800px;" >
  <div class="wdzh"  style="width:800px;">
    <div class="r_main"  style="width:800px;">
<div class="box" style="border:none;">
  <div class="boxmain2">
         <div class="biaoge">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th style="width: 40px;">期数</th>
    <th style="width: 100px;">还款时间</th>
    <th style="width: 90px;">应收本金</th>
    <th style="width: 90px;">应收利息</th>
    <th style="width: 100px;">加息收益</th>
    <th style="width: 80px;">剩余本金</th>
    <th style="width: 90px;">利息管理费</th>
    <th style="width: 85px;">还款状态</th>
    <th style="width: 80px;">是否逾期</th>
    <th style="width: 80px;">逾期天数</th>
    <th style="width: 70px;">逾期罚息</th>
    <th style="width: 40px;">收益</th>
    <th style="width: 80px;">还款人</th>
    </tr>
  	<s:if test="request.listMap==null || request.listMap.size==0">
	   <tr align="center">
		  <td colspan="11">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="#request.listMap" var="bean">
	 <tr>
	   <td align="center">${bean.repayPeriod}</td>
	   <td align="center">${bean.repayDate}</td>
	   <td align="center">${bean.forpayPrincipal}</td>
	   <td align="center">
	   
	  ${bean.forpayInterest}
	   </td>
	   <td align="center">
	   
	   <s:if test="%{#bean.add_interest_value > 0}">
	    <font color="#ff0000">${bean.add_interest_value}</font>
	    </s:if>
	    <s:else>
	    	0
	    </s:else>
	    </td>
	   <td align="center">${bean.principalBalance}</td>
	   <td align="center">${bean.manage}</td>
	   <td align="center"><s:if test="%{#bean.repayStatus == 1}">未还</s:if><s:else>已还</s:else></td>
	   <td align="center"><s:if test="%{#bean.isLate == 1}">否</s:if><s:else>是</s:else></td>
	   <td align="center">${bean.lateDay}</td>
	   <td align="center">${bean.forFI}</td>
	   <td align="center"><fmt:formatNumber value="${bean.earn}" pattern="#0.00"/> </td>
	   
	   	<s:if test="#request.jk_state==0">
	       <td align="center"><s:if test="%{#bean.isWebRepay == 2}">网站垫付</s:if><s:else>${bean.username}</s:else></td>
	   </s:if>
	  	<s:if test="#request.jk_state==1">
	     <td align="center">${request.jk_user_name}</td>
	   </s:if>
	 
       </tr>
     </s:iterator>
	</s:else>
</table>
          </div>
    </div>
</div>
    </div>
  </div>
</div>
</body>
</html>