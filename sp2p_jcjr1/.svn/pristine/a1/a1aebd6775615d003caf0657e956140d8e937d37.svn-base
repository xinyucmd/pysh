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
						注册时间
					</th>
					<th style="width: 150px;" scope="col">
						是否身份认证
					</th>
					<th style="width: 150px;" scope="col">
						好友来源
					</th>
					<th style="width: 150px;" scope="col">
						是否投资
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
						    ${createTime}
						</td>
						<td>
						    ${isAuth}
						</td>
						<td>
						    <s:if test="%{#bean.src == 1}">邀请链接</s:if>
							<s:elseif test="%{#bean.src == 2}">二维码扫码</s:elseif>
							<s:elseif test="%{#bean.src == 3}">填写推荐人</s:elseif>
							<s:else></s:else>
						</td>
						<td>
						    <s:if test="%{#bean.investCount > 0}">是</s:if>
							<s:else>否</s:else>
						</td>
					</tr>
				</s:iterator>
				</s:else>
				<tr >
						<td  colspan="2"><strong>合计项</strong></td>
						<td colspan="8">推荐总人数：${map.recomendSum}人，推荐认证人数：${map.recomendRzSum}人，
						推荐投资人数：${map.recomendTzSum}人，推荐投资总额：${map.recomendInvestAmount}元</td>
					</tr>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>