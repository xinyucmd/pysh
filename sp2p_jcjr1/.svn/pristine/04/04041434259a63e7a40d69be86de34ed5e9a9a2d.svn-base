<%@page import="org.apache.commons.lang.text.StrMatcher"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>



<div>
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">
					序号
				</th>
				<th scope="col">
					债转金额
				</th>
				<th scope="col">
					状态
				</th>
				<th style="width: 90px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">

						<td align="center">
							<s:property value="#s.count+#counts" />
						</td>
						<td align="center">
							${auctionPrice}
						</td>
						<td align="center">
							<s:if test="#bean.status==0">
								待审核
							</s:if>
							<s:if test="#bean.status==1">
								审核通过
							</s:if>
							<s:if test="#bean.status==2">
								已撤销
							</s:if>
						</td>
						<td>
						    <s:if test="#bean.status==0">
								<a  id="chengjiao" onclick="pass(${id});" href="javascript:void(0)">通过</a>
								<a  id="chehui" onclick="cancel(${id});" href="javascript:void(0)">撤回</a>
							</s:if>
							<a href="../queryDebtDetail.do?id=${debtId}" target="_blank">查看</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<script type="text/javascript">
  
  function cancel(param){
     if(!confirm("确认撤回该债权投资吗？")){
         return ;
     }else{
      window.location.href="cancelInvestDebtApply.do?id="+param;
     }
  }
  
  function pass(param){
	     if(!confirm("确认审核通过该债权投资吗？")){
	         return ;
	     }else{
	    	 window.location.href="auditInvestDebtApply.do?id="+param;
	     }
 }
</script>

<%
	Object msg = request.getAttribute("msg");
	String strMsg = "";
	if(msg != null){
		strMsg = (String)msg;
	}
%>

<script type="text/javascript">
	var strMsg = "<%=strMsg%>";
	if(strMsg!=""){
		alert(strMsg);
		parent.location.href=parent.location.href;
	}
	
</script>


<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
