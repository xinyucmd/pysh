<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
	<div >
	<div  id="borrow_payoff_list">
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						标题
					</th>
<%--						<th style="width: 100px;" scope="col">--%>
<%--						协议--%>
<%--					</th>--%>
						<th style="width: 100px;" scope="col">
						借款类型
					</th>
						<th style="width: 100px;" scope="col">
						借款金额
					</th>
						<th style="width: 100px;" scope="col">
						年利率
					</th>
					<th style="width: 100px;" scope="col">
					         还款期限
					</th>
						<th style="width: 100px;" scope="col">
						借款时间
					</th>
						<th style="width: 100px;" scope="col">
						偿还本息
					</th>
						<th style="width: 100px;" scope="col">
						已还本息
					</th>
						<th style="width: 100px;" scope="col">
						已还逾期罚息
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
						    <td align="center"><a href="../financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle }</a></td>
<%--						    <td align="center"><a href="javascript:showAgree('${bean.borrowId}');">查看协议</a>--%>
<%--				    <a href="protocol.do?typeId=15&&borrowId=${bean.borrowId}" target="_blank">查看协议</a>--%>
<%--			        <form action=""></form>--%>
<%--			 		<form action="../protocol.do" id="form<s:property value='#s.count'/>" method="post" target="_blank">--%>
<%--				          <input type="hidden" name="typeId" value="${bean.encTypeId }"/>--%>
<%--				          <input type="hidden" name = "borrowId" value="${bean.encBorrowId }"/>--%>
<%--				          <a href="javascript:void(0);" onclick="$('#form<s:property value='#s.count'/>').submit();">查看协议</a>--%>
<%--				      </form>--%>
<%--						    </td>--%>
						    <td align="center">${bean.borrowWay }</td>
						    <td align="center">${bean.borrowAmount }元</td>
						    <td align="center">${bean.annualRate }%</td>
						    <td align="center">${bean.deadline }
						    <s:if test="#bean.isDayThe == 1">
						      个月
						    </s:if>
						    <s:else>
						     天
						    </s:else>
						    </td>
						    <td align="center">${bean.publishTime }</td>
						    <td align="center">￥${bean.stillTotalSum }</td>
						    <td align="center">￥${bean.stillTotalSum }</td>
						    <td align="center">￥${bean.hasFI }</td>
						    <td align="center"><a href="javascript:payingDetails1(${bean.borrowId });">还款明细</a></td>
			  	</tr>
					  
				    </s:iterator>
			</s:else>
			</tbody>
		</table>
		<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	</div>
		
</div>
<span id="borrow_payoff_detail"></span> 
<script type="text/javascript" src=../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../script/nav-zh.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script>


	function payingDetails1(id){
		 $("#borrow_des").attr("value",id);
		 $("#payoff_srh").hide();
		 $("#borrow_payoff_list").hide();
		 var param = {};
		 param["paramMap.borrowId"] = id;
		 param["pageBean.pageNum"]=1;
		 param["paramMap.status"] = "2";
        $.shovePost("queryAdminPayingDetails.do",param,function(data){
	       $("#borrow_payoff_detail").html(data);
	    });
     }
     
	function showAgree(borrowId){
	    var url = "protocol.do?typeId=15&&borrowId="+borrowId;
		     jQuery.jBox.open("post:"+url, "查看协议书", 650,400,{ buttons: { } });
		     
	    }
</script>