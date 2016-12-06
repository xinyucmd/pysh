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
						投资者
					</th>
						<th style="width: 100px;" scope="col">
						借入总额
					</th>
						<th style="width: 100px;" scope="col">
						还款总额
					</th>
						<th style="width: 100px;" scope="col">
						已还本金
					</th>
						<th style="width: 100px;" scope="col">
						已还利息
					</th>
					<th style="width: 100px;" scope="col">
					         待还本金
					</th>
						<th style="width: 100px;" scope="col">
						待还利息
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					 <input id="borrow_des" name="borrow_des" value="" type="hidden"/>
				    <s:iterator value="pageBean.page"  var="bean" status="s"> 
					  <tr class="gvItem">
					    <td>${bean.username} </td>
					    <td><strong>￥${bean.realAmount }</strong></td>
					    <td>￥${bean.recivedPI}</td>
					    <td>￥${bean.hasPrincipal}</td>
					    <td>￥${bean.hasInterest}</td>
					    <td>￥${bean.forPrincipal}</td>
					    <td>￥${bean.forInterest}</td>
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
    
</script>