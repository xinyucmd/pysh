<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
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
					剩余期数
				</th>
				<th scope="col">
					年利率
				</th>
				<th scope="col">
					债权价值
				</th>
				<th scope="col">
					转让系数
				</th>
				<th scope="col">
					申请转让时间
				</th>
				<th scope="col">
					剩余时间
				</th>
				<th style="width: 90px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<s:if test="#bean.applyState==1">
					<tr class="gvItem">

						<td align="center">
							<s:property value="#s.count+#counts" />
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
							${remainPeriod}个月
						</td>
						<td align="center">
							${annualRate}
						</td>
						<td align="center">
							${debtSum}
						</td>
						<td align="center">
							${transRatio}
						</td>
						<td align="center">
							${publishTime}
						</td>
						<td align="center">
							${remainDays }
						</td>
						<td>
						     <!-- 
						     <a  id="chengjiao" onclick="chengjiao(${id});" href="javascript:void(0)">成交</a>
						      -->
						    <s:if test="#bean.applyState==1">
							<a  id="chengjiao" onclick="tongguo(${debtId});" href="javascript:void(0)">通过</a>
							<a  id="chehui" onclick="chehui(${debtId});" href="javascript:void(0)">撤回</a>
							</s:if>
							<a href="../queryDebtDetail.do?id=${debtId}" target="_blank">查看</a>
							
						</td>
					</tr>
					</s:if>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<script type="text/javascript">
  function chengjiao(param){
     if(!confirm("确认成交该债权转让？")){
         return ;
     }else{
      window.location.href="debtEndSuccess.do?id="+param;
     }
  }
  
  function chehui(param){
     if(!confirm("确认撤回该债权转让？")){
         return ;
     }else{
      window.location.href="cancelManageDebt.do?id="+param;
     }
  }
  
  function tongguo(param){
	    
	     if(!confirm("确认审核通过该债权转让？")){
	         return ;
	     }else{
	      window.location.href="goManageDebt.do?id="+param;
	     }
	  }
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
