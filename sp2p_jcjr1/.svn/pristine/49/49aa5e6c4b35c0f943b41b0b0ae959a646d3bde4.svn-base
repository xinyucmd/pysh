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
						投资金额
					</th>
					<th style="width: 150px;" scope="col">
						投资项目
					</th>
					<th style="width: 150px;" scope="col">
						标的类型
					</th>
					<th style="width: 150px;" scope="col">
						投资期限
					</th>
					<!-- <th style="width: 150px;" scope="col">
						是否自动投标
					</th> -->
					<th style="width: 150px;" scope="col">
						投资时间
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
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
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
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
						</td>
						<td align="center">
							${bean.deadline}
						</td>
					<%-- 	<td align="center">
						    <s:if test="#bean.isAutoBid==1">
							  否
							</s:if>
							<s:elseif test="#bean.isAutoBid==2">
							  是
							</s:elseif>
						</td> --%>
						<td align="center">
							${bean.investTime}
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
					<td colspan="2"><strong>合计项</strong></td>
					<td colspan="7">投资金额：${sumAmount}元</td>
				</tr>
				<tr class="gvItem"><td colspan="9" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>