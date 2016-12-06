<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
	<div id="biaoge_details">
	<div  >
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						标题
					</th>
						<th style="width: 100px;" scope="col">
						第几期
					</th>
						<th style="width: 100px;" scope="col">
						应还款日期
					</th>
						<th style="width: 100px;" scope="col">
						实际还款日期
					</th>
						<th style="width: 100px;" scope="col">
						本期应还本息
					</th>
					<th style="width: 100px;" scope="col">
					         利息
					</th>
						<th style="width: 100px;" scope="col">
						逾期罚款
					</th>
	
						<th style="width: 100px;" scope="col">
						逾期天数
					</th>
						<th style="width: 100px;" scope="col">
						还款状态
					</th>
					<th style="width: 100px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					 <input id="borrow_des" name="borrow_des" value="" type="hidden"/>
				    <s:iterator value="pageBean.page"  var="bean" status="s"> 
					  <tr class="gvItem">
					    <td><a href="../financeDetail.do?id=${bean.id}"  target="_blank" >${bean.borrowTitle }</a></td>
					    <td>${bean.repayPeriod }</td>
					    <td>${bean.repayDate}</td>
					    <td>${bean.realRepayDate}</td>
					    <td>￥${bean.forPI} </td>
					    <td>￥${bean.stillInterest}</td>
					    <td>￥${bean.lateFI}</td>
					    <td>${bean.lateDay }</td>
					    <td>
					        <s:if test="#bean.repayStatus==1">未偿还</s:if>
					        <s:else>已偿还</s:else>
					    </td>
					    <td>
					    <s:if test="#bean.repayStatus==1"><a href="javascript:myPay(${bean.payId });">还款</a></s:if>
					        <s:else>---</s:else>
					    </td>
	  			  </tr>
					  
				    </s:iterator>
			</s:else>
			</tbody>
		</table>
	</div>
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
<script type="text/javascript" src=../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../script/nav-zh.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
	function showAgree(){
		var url = "protocol.do?typeId=1";
		 jQuery.jBox.open("post:"+url, "查看协议书", 600,400,{ buttons: {} });
			
    }
    
    function myPay(id){//还款
       var url = "queryAdminPayData.do?payId="+id;
       jQuery.jBox.open("post:"+url, "还款",350,350,{ buttons: { } });
       
    }
</script>