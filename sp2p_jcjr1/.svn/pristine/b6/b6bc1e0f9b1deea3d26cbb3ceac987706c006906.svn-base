<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

 				<table border="0" cellspacing="0" cellpadding="0" class="table-con">
                	<tr>
					    <th class="p-w10" >时间</th>
					    <th class="p-w10">操作</th>
					    <th class="p-w10">操作金额</th>
					    <th class="p-w7">类型</th>
					    <th class="p-w12">可用余额</th>
					    <th class="p-w18" >备注</th>
                    </tr>
                    <s:if test="pageBean.page!=null && pageBean.page.size>0">
					  <s:set name="counts" value="#request.pageNum"/>
			            <s:iterator value="pageBean.page"  var="bean" status="s">
						    <tr>
							    <td><s:date name="#bean.recordTime" format="yyyy-MM-dd HH:mm:ss"/></td>
							   	<td>${bean.fundMode }</td>
							    
							    <td>
							    		<s:if test="#bean.income>0"><span class="red">+${bean.income }</span></s:if>
							    		<s:if test="#bean.spending>0">
							    		     <s:if test="#bean.fundMode!='扣除投标冻结金额'">
							    		         <span class="green">-${bean.spending }</span>
							    		      </s:if>
							    		      <s:else>
							    		         <span >0.00</span>
							    		      </s:else>
							    		</s:if>
							    </td>
							     <td>
							    		<s:if test="#bean.income>0">收入</s:if>
							    		<s:if test="#bean.spending>0">支出</s:if>
							    </td>
							    <td>${bean.usableSum }</td>
							    <td>${bean.remarks }</td>
					  		</tr>
						  </s:iterator>
						  <tr>
							<td>合计</td>
                    		<td colspan="9">
                        		<p class="total">总收入：￥${paramMap.SumincomeSum }&nbsp;&nbsp;&nbsp;&nbsp;总支出：￥${paramMap.SumspendingSum }  &nbsp;&nbsp;&nbsp;&nbsp;可用金额：${paramMap.SumusableSum }</p>
                        	</td>
                     	</tr>
					  </s:if>
					  <s:else>
					    <tr><td colspan="10" align="center">暂无信息</td></tr>
					  </s:else>
                 </table>
                 <div class="s_foot-page">
                   <p>
					<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
				   </p>
                 </div> 