<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						真实姓名
					</th>
					<th style="width: 200px;" scope="col">
						推荐人
					</th>
					<th style="width: 150px;" scope="col">
						推荐人类型
					</th>
				<!-- 	<th style="width: 150px;" scope="col">
						推荐总人数
					</th>
					<th style="width: 150px;" scope="col">
						推荐认证人数
					</th>
					<th style="width: 150px;" scope="col">
						推荐投资人数
					</th>
					<th style="width: 150px;" scope="col">
						推荐投资总额
					</th> -->
					<th style="width: 170px;" scope="col">
						查看
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${username}
						</td>
						<td>
							${realName}
						</td>
						<td>
						    ${recommendUser}
						</td>
						<td>
						    <s:if test="%{#bean.type == 1}">QQ群主</s:if>
							<s:elseif test="%{#bean.type == 2}">九华</s:elseif>
							<s:elseif test="%{#bean.type == 3}">普通用户</s:elseif>
							<s:elseif test="%{#bean.type == 0}">无</s:elseif>
							<s:else></s:else>
						</td>
				<%-- 		<td>
						    ${recomendSum}
						</td>
						<td>
						    ${recomendRzSum}
						</td>
						<td>
						    ${recomendTzSum}
						</td>
						<td>
						    ${recomendInvestAmount}
						</td> --%>
						<td>
						    <a href="queryRecommendDetail.do?id=${bean.id }" id="recomDetail" target="_blank">推荐明细</a> |
						    <a href="queryInvestDetail.do?id=${bean.id }" id="investDetail" target="_blank">投资明细</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
					
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>