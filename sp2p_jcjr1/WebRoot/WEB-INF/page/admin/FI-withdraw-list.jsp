<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	
	<div> 
		<table id="help" style=" color: #333333;width:100%"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户名
					</th>
					<th scope="col">
						真实姓名
					</th>
					
					<th scope="col">
						提现账号
					</th>
					<th scope="col">
						提现银行
					</th>
					<th scope="col">
						支  行
					</th>
					<th scope="col">
						提现总额
					</th>
					<th scope="col">
						到账金额
					</th>
					<th scope="col">
						手续费
					</th>
					<th scope="col">
						提现时间
					</th>
					<th scope="col">
						状态
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="12">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td align="center" >
							<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.name}
							</td>
							<td>
								${bean.realName}
							</td>
							<td>
							  ${bean.acount}
							</td>
							<td>
								${bean.bankName}
							</td>
							<td>
								${bean.branchBankName}
							</td>		
							<td>
								${bean.sum}
							</td>
							<td>
								${bean.realMoney}
							</td>
							<td>
								${bean.poundage }
							</td>
							<td>
							  <s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss" ></s:date>
							</td>
							
							  <td>
							  <s:if test="#bean.status==1">审核中</s:if>
							  <s:elseif test="#bean.status==2">已提现</s:elseif>
							  <s:elseif test="#bean.status==3">取消</s:elseif>
							  <s:elseif test="#bean.status==4">转账中</s:elseif>
							  <s:elseif test="#bean.status==5">失败</s:elseif>
							  </td>
								<td>
								 <s:if test="#bean.status==1">
							     <a href="javascript:withdraw_check('${bean.ids}');">审核</a>
							     </s:if>
								 <s:elseif test="#bean.status==4">
							     <a href="javascript:withdraw_trans('${bean.ids}');"> 转账</a>
							     </s:elseif>
							     <s:else>
							      <a href="javascript:withdraw_show('${bean.ids}');">查看</a>
							     </s:else>
								</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="2">总计：</td>
						<td colspan="2">提现总额：${map.sums }</td>
						<td colspan="2">到帐金额：${map.realMoneys }</td>
						<td colspan="2">手续费：${map.poundages }</td>
					</tr>
				  </s:else>
				  <tr class="gvItem"><td colspan="12" align="left"><font size="2">共有${totalNum }条记录</font></td></tr>
			</tbody>
		</table>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>

</div>
		
	<script type="text/javascript" src="../css/admin/popom.js"></script>
	<script>
	  function withdraw_check(wid){
			    var url = "queryWithdrawInfo.do?wid="+wid;
               ShowIframe("提现审核",url,650,600);
			}
			
			function withdraw_trans(wid){
			    var url = "queryWithdrawTransInfo.do?wid="+wid;
               ShowIframe("转账审核",url,600,600);
			}
			
			function withdraw_show(wid){
			    var url = "queryWithdrawShowInfo.do?wid="+wid;
               ShowIframe("提现查看",url,600,600);
			}
	</script>