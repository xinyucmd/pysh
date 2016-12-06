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
					<th style="width: 150px;" scope="col">
						奖励总额
					</th>
					<th style="width: 150px;" scope="col">
						推荐人
					</th>
					<th style="width: 150px;" scope="col">
						推荐人类型
					</th>
					<th style="width: 150px;" scope="col">
						查看
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
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
						    ${amount}
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
						<td>
						    <a href="queryRewardDetail.do?flag=1&id=${bean.id }" id="detail" target="_blank">奖励明细</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
				
				<tr>
					<td colspan="2"><strong>合计项</strong></td>
					<td colspan="5">投资总额：${sumAmount}元</td>
				</tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>