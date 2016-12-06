<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
   function deletes(id){
    if(confirm("确定要删除吗?")){
    	window.location.href='deleteEnterpriseApplyById.do?id='+id
	 }

   }
</script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						序号
					</th>
						<th style="width: 100px;" scope="col">
						姓名
					</th>
						<th style="width: 100px;" scope="col">
						联系电话
					</th>
						<th style="width: 100px;" scope="col">
						借款金额
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
							<td>
							<s:property value="#s.count+#counts"/>
							</td>
							<td>
							${tname }
							</td>
							<td>
							${telephone }
							</td>
							<td>
							${borrowAmount } 
							</td>
							<td>
							<s:if test="#bean.authStep == null">
								<a href="enterpriseUserBaseInfoInit.do?paramMap.applyId=${id }" style="color:red;">  基本信息未填写</a>
							</s:if>
							<s:else>
							    <a href="enterpriseUserBaseInfoInit.do?paramMap.applyId=${id }">基本信息完整</a>
							</s:else>
							</td>
							<td>
							<s:if test="#bean.authStep == 2">
								 <a href="enterpriseUserUploadInit.do?paramMap.applyId=${id }&paramMap.type=2">查看资料 </a>
							</s:if>
							<s:else>
							    <a href="enterpriseUserUploadInit.do?paramMap.applyId=${id }&paramMap.type=2" style="color:red;">上传资料</a>
							</s:else>
							</td>
							<td> 
							<s:if test="#bean.authStep == 2">
								  <a href="publishBorrowInit.do?paramMap.applyId=${id }">发布借款</a>
							</s:if>
							<s:else>
							     <a href="enterpriseUserBaseInfoInit.do?paramMap.applyId=${id }">发布借款</a>
							 </s:else>
							</td>
							<td>
								<a href="enterpriseUserBaseInfoInit.do?paramMap.applyId=${id }">修改</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" onclick="javascript:deletes(${id})">删除</a> 
							</td>
						</tr>
			</s:iterator>
			</s:else>
			</tbody>
		</table>
	</div>
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>