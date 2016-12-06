<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 45px;" scope="col">
						排名
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						用户组
					</th>
					<th style="width: 150px;" scope="col">
						真实姓名
					</th>
					<th style="width: 200px;" scope="col">
						期间成功投标金额
					</th>
					<th style="width: 200px;" scope="col">
						期间投标金额总计
					</th>
					<th style="width: 200px;" scope="col">
						期间年化投标总额
					</th>
					<th style="width: 150px;" scope="col">
						账户总额
					</th>
					<th style="width: 150px;" scope="col">
						可用金额
					</th>
					<th style="width: 100px;" scope="col">
						待收总额
					</th>
					<th style="width: 100px;" scope="col">
						注册时间
					</th>
					<!-- <th style="width: 100px;" scope="col">
						会员积分
					</th>
					<th style="width: 100px;" scope="col">
						信用积分
					</th> -->
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="11">暂无数据</td>
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
							${bean.groupName}
						</td>
						<td align="center">
							${bean.realName}
						</td>
						<td align="center">
							${bean.realAmount}
						</td>
						<td align="center">
							${bean.investAmount}
						</td>
						<td align="center">
							${bean.yearAmount}
						</td>
						<td align="center">
							${bean.totalSum}
						</td>
						<td align="center">
							${bean.usableSum}
						</td>
						<td align="center">
							${bean.forPI}
						</td>
						<td align="center">
							${bean.createTime}
						</td>
						<%-- <td align="center">
							${bean.rating}
						</td>
						<td align="center">
							${bean.creditrating}
						</td> --%>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
					<td colspan="2"><strong>合计项</strong></td>
					<td colspan="9">成功投标金额：${sumInvestAmount}元，&nbsp;&nbsp;投标金额：${sumRealAmount}元，&nbsp;&nbsp;年化投标总额：${sumYearAmount}元</td>
				</tr>
				<tr class="gvItem"><td colspan="11" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>