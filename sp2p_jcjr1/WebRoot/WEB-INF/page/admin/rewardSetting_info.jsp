<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">序号</th>
					<th scope="col">奖励来源</th>
					<th scope="col">用户类型</th>
					<th scope="col">奖励层级</th>
					<th scope="col">奖励发放方式</th>
					<th scope="col">最小投资额度</th>
					<th scope="col">最大投资额度</th>
					<th scope="col">第一层返现</th>
					<th scope="col">第二层返现</th>
					<th scope="col">是否启用</th>
					<th scope="col">开始日期</th>
					<th scope="col">结束日期</th>
					<th scope="col">操作	</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem"><td colspan="12">暂无数据</td>	</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td><s:property value="#s.count+#counts"/></td>
							<td align="center">
								<s:if test="%{#bean.reward_src == 1}">推荐注册</s:if>
								<s:elseif test="%{#bean.reward_src == 2}">累计推荐注册</s:elseif>
								<s:elseif test="%{#bean.reward_src == 3}">推荐单次投资</s:elseif>
								<s:elseif test="%{#bean.reward_src == 4}">累计推荐投资</s:elseif>
								<s:else>无</s:else>
							</td>
							<td align="center">
								<s:if test="%{#bean.user_type == 1}">个人</s:if>
								<s:else>机构</s:else>
							</td>
							<td align="center">
								<s:if test="%{#bean.level_flag == 2}">二层</s:if>
								<s:else>一层</s:else>
							</td>
							<td align="center">
								<s:if test="%{#bean.give_way == 1}">现金</s:if>
								<s:else>红包</s:else>
							</td>
							<td align="center">
								<s:if test="%{#bean.min_invest_amount == ''}">0.00</s:if>
								<s:else>${min_invest_amount}</s:else>
							</td>
							<td align="center">
								<s:if test="%{#bean.max_invest_amount == ''}">0.00</s:if>
								<s:else>${max_invest_amount}</s:else>
							</td>
							<td align="center">
								${brokerage_one}
								<s:if test="%{#bean.return_type == 1}">%</s:if>
								<s:else>元</s:else>
							</td>
							<td align="center">
								${brokerage_two}
								<s:if test="%{#bean.return_type == 1}">%</s:if>
								<s:else>元</s:else>
							</td>
							<td align="center">
								<s:if test="%{#bean.isopne == 1}">是</s:if>
								<s:else>否</s:else>
							</td>
							<td align="center">${start_time}</td>
							<td align="center">${end_time}</td>
							<td align="center">
							 	<a href="javascript:updateInit(${id});">编辑</a> &nbsp;|&nbsp;
							 	<a href="javascript:deleteInit(${id});">删除</a> 
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	<p/>	
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
