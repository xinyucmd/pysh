<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

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
						联系方式
					</th>
					<th style="width: 150px;" scope="col">
						投资金额
					</th>
					<th style="width: 150px;" scope="col">
						投资项目
					</th>
					<th style="width: 150px;" scope="col">
						投资期限
					</th>
					 <th style="width: 150px;" scope="col">
						推荐人
					</th> 
					 <th style="width: 150px;" scope="col">
						推荐人电话
					</th> 
					<th style="width: 150px;" scope="col">
						投资时间
					</th>
					<th style="width: 150px;" scope="col">
						推荐人用户名
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
							${bean.username}
						</td>
						<td align="center">
							${bean.realName}
						</td>
						 <td align="center">
							${bean.mobilePhone}
						</td> 
						<td align="center">
							${bean.investAmount}
						</td>
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
							${bean.deadline}
						</td>
						<td align="center">
							${bean.rrealName}
						</td>
						<td align="center">
							${bean.rmobilePhone}
						</td>
						<td align="center">
							${fn:substring(bean.investTime, 0, 19)}
						</td>
						<td align="center">
							${bean.rusername}
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr>
					<td colspan="2"><strong>合计项</strong></td>
					<td colspan="7">投资金额：${sumAmount}元</td>
				</tr>
				<tr class="gvItem"><td colspan="11" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>