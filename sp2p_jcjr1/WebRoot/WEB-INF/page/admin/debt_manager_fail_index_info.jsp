<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						标题
					</th>
					<th scope="col">
						借款人
					</th>
					<th scope="col">
						转让人
					</th>
					<th scope="col">
						转让系数
					</th>
					<th scope="col">
						年利率
					</th>
					<th scope="col">
						债权价值
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan=12">暂无数据</td>
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
							<a href="../financeDetail.do?id=${borrowId }" target="_blank">${borrowTitle}</a>
						</td>
						<td align="center">
							${borrowerName}
						</td>
						<td align="center">
							${alienatorName}
						</td>
						<td align="center">
							${transRatio}
						</td>
						<td align="center">
							${annualRate}
						</td>
						<td align="center">
							${debtSum}
						</td>
						<td>
							<a href="queryManageDebtDetail.do?id=${debtId}&paramMap.borrowerName=${borrowerName}&paramMap.borrowTitle=${borrowTitle}&paramMap.alienatorName=${alienatorName }&paramMap.remainPeriod=${remainPeriod }" target="main"> 查看</a> 		
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr >
				<td  colspan="2"><strong>合计项</strong></td>
				<td colspan="3" align="right" >当前页失败的债权转让金额合计:</td>
				<td  align="center" >￥${debtSumm}</td>	
				<td colspan="3" align="center" >所有失败债权转让金额合计:
				￥
				<s:if test="%{#request.repaymentMap.failapplydebtSum==''}">0.00</s:if>
				<s:else> <s:property value="#request.repaymentMap.failapplydebtSum" default="0"/></s:else>
				
				</td>		
				</tr>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
