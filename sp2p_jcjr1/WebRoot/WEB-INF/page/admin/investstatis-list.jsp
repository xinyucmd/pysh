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
						用户组
					</th>
					<th style="width: 150px;" scope="col">
						投资总额(年化)
					</th>
					<!-- <th style="width: 150px;" scope="col">
						标的类型
					</th> -->
					<th style="width: 150px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="6">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${bean.investor}
						</td>
						<td align="center">
							${bean.realName}
						</td>
						<td align="center">
							${bean.groupName}
						</td>
						<td align="center">
							${bean.realAmount}
						</td>
						<%-- <td align="center">
							<s:if test="#bean.isDebt==2">
								交易宝
							</s:if>
							<s:else>
								<s:if test="#bean.borrowWay==1">
								净值借款
								</s:if>
								<s:elseif test="#bean.borrowWay==2">
								秒还借款
								</s:elseif>
								<s:elseif test="#bean.borrowWay==3">
								优选宝
								</s:elseif>
								<s:elseif test="#bean.borrowWay==4">
								定息宝
								</s:elseif>
								<s:elseif test="#bean.borrowWay==5">
								机构担保借款
								</s:elseif>
								<s:elseif test="#bean.borrowWay==6">
								活利宝
								</s:elseif>
							</s:else>
						</td> --%>
						<td align="center">
							<a href="investDetailInit.do?userId=${bean.investor }" id="detail" target="_blank">查看详情</a>
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
					<td colspan="2"><strong>合计项</strong></td>
					<td colspan="4">投资总额(年化)：${sumAmount}元</td>
				</tr>
				<tr class="gvItem"><td colspan="6" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>