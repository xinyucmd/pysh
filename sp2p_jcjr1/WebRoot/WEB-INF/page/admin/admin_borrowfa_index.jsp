<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
			$(function(){	
				$("#excel").click(function(){
				  $("#excel").attr("disabled",true);
	 window.location.href=encodeURI(encodeURI("exportinborrowfa.do?paramMap.tame="+$("#tame").val()+"&paramMap.telphone="+$("#telphone").val()));
				setTimeout("test()",4000);
				});
				});
				function test(){
			   $("#excel").attr("disabled",false);
			   }
</script>

	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
			
			
				<tr class=gvHeader>
				 <th scope="col">
						选中
					</th>
					<th style="width: 100px;" scope="col">
						序号
					</th>
						<th style="width: 100px;" scope="col">
						企业名称
					</th>
						<th style="width: 100px;" scope="col">
						注册号
					</th>
						<th style="width: 100px;" scope="col">
						联系人
					</th>
						<th style="width: 100px;" scope="col">
						联系电话
					</th>
						<th style="width: 100px;" scope="col">
						城市所在地
					</th>
						<th style="width: 100px;" scope="col">
						借款金额
					</th>
						<th style="width: 100px;" scope="col">
						借款期限
					</th>
					
					<th style="width: 100px;" scope="col">
						状态
					</th>
					
					<th style="width: 100px;" scope="col">
						操作
					</th>
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
					  <td>
						<input id="gvNews_ctl02_cbID" class="biaoid" type="checkbox"
								value="${id}" name="cb_ids" />
							</td>
							<td>
							<s:property value="#s.count+#counts"/>
							</td>
					    <td align="center">
							${companyname }
							</td>
						<td align="center">
							${registnumber}
							</td>
						<td align="center">
							${tname }
							</td>
						<td align="center">
							 ${telephone }
							</td>
						<td align="center">
							${cityaddress }
							</td>
						<td align="center">
							${borrowAmount }
							</td>
					    <td align="center">
					        ${deadline }
					        </td>
						<td align="center">
							  <s:if test="#bean.state==0">
							    未处理
							  </s:if>
							  <s:elseif test="#bean.state==1">
							    已处理
							  </s:elseif>
							</td>
						<td>
								<a href="javascript:fff(${id });">查看</a> 
								<a href="javascript:void(0);" 
								onclick="javascript:deletes(${id})">删除</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 8px" align="left">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnTrans" onclick="deleteBorrowfabiao();" type="button"
						value="批量删除选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
   <script>
	  function fff(wid){
			    var url = "queryfabiaoInfo.do?id="+wid;
               ShowIframe("基本信息查看",url,600,600);
			}
		    function ffff(){
		     window.location.reload();
		      ClosePop();
		    }
	
   </script>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
