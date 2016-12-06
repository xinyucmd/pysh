<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

<table width="95%"><tr height="30" bgcolor="#e9e9e9"><td>序号</td><td>还款日期</td><td>已还本息</td><td>待还本息</td><td>已付罚息</td><td>待还罚息</td><td>状态</td></tr>
	<s:if test="%{#request.repayList !=null && #request.repayList.size()>0}">
      <s:iterator value="#request.repayList" id="bean">
      	<tr height="30"><td>${bean.repayPeriod}</td><td>${bean.repayDate}</td><td>${bean.hasPI}</td><td>${bean.stillPI}</td><td>${bean.hasFI}</td><td>${bean.lateFI}</td>
      	<td><s:if test="#bean.repayStatus == 1">
                                      未偿还
          </s:if>
          <s:elseif test="#bean.repayStatus == 2">
                                       已偿还
          </s:elseif>
          <s:elseif test="#bean.repayStatus == 3">
                                       偿还中
          </s:elseif>
          </td></tr>
      </s:iterator></s:if>
	  <s:else>
      <td colspan="7" align="center">没有数据</td>
  </s:else>
</table>