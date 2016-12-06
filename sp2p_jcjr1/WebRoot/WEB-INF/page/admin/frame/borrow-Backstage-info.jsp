<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						序号
					</th>
						<th style="width: 100px;" scope="col">
						用户名
					</th>
						<th style="width: 100px;" scope="col">
						真实姓名
					</th>
						<th style="width: 100px;" scope="col">
						身份证
					</th>
						<th style="width: 100px;" scope="col">
						基本信息
					</th>
						<th style="width: 100px;" scope="col">
						上传资料
					</th>
					<th style="width: 100px;" scope="col">
					    发布借款
					</th>
					<th style="width: 100px;" scope="col">
					    操作
					</th>
				</tr>
				
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
					<td style="width: 100px;" scope="col">
						<s:property value="#s.count+#counts"/>
					</td>
						<td style="width: 100px;" scope="col">
						<s:property value="username"/>
					</td>
						<td style="width: 100px;" scope="col">
						<s:property value="realName"/>
					</td>
						<td style="width: 100px;" scope="col">
						<s:property value="idNo"/>
					</td>
						<td style="width: 100px;" scope="col">
						<s:if test="#bean.authStep > 1">
							<a href="persioninfo.do?id=<s:property value="id"/>">基本信息完整</a>
						</s:if>
						<s:else>
							<a href="persioninfo.do?id=<s:property value="id"/>" style="color:#FF0000;">基本信息未填写</a>
						</s:else>
					</td>
						<td style="width: 100px;" scope="col">
						<s:if test="#bean.authStep > 2">
							<a href="persionData.do?id=<s:property value="id"/>" style="color:#FF0000;">查看资料</a>
						</s:if>
						<s:else>
							<a href="persionData.do?id=<s:property value="id"/>" style="color:#FF0000;">上传资料</a>
						</s:else>
					</td>
					<td style="width: 100px;" scope="col">
					    <a href="personpublishBorrowInit.do?id=<s:property value="id"/>">发布借款</a>
					</td>
					<td style="width: 100px;" scope="col">
					    <a href="persioninfo.do?id=<s:property value="id"/>">修改</a>
					</td>
				</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
