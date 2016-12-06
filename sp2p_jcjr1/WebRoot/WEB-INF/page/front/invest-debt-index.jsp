<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  	<head>
	    <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include>
	    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" /> 
	</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="ne_wdzh"></div>
<div class="lne_cent">

	<%@include file="/include/left.jsp" %>

	<div class="lne_centr">
		<div class="s_totletable">
			<h3><span>投资总汇</span></h3>
			<div class="totle-cont">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
					<tr>
						<td>已收总额：￥<s:if test="#request.accmountStatisMap.hasPaySum !=''">${accmountStatisMap.hasPaySum}</s:if><s:else>0.00</s:else></td>
						<td>已收本金：￥<s:if test="#request.accmountStatisMap.hasPayPrincipal !=''">${accmountStatisMap.hasPayPrincipal}</s:if><s:else>0.00</s:else></td>
						<td>已收利息：￥<s:if test="#request.accmountStatisMap.hasPayInterest !=''">${accmountStatisMap.hasPayInterest}</s:if><s:else>0.00</s:else></td>
					</tr>
					<tr>
						<td>待收总额：￥<s:if test="#request.accmountStatisMap.forPaySum !=''">${accmountStatisMap.forPaySum}</s:if><s:else>0.00</s:else></td>
						<td>待收本金：￥<s:if test="#request.accmountStatisMap.forPayPrincipal !=''">${accmountStatisMap.forPayPrincipal}</s:if><s:else>0.00</s:else></td>
						<td>待收利息：￥<s:if test="#request.accmountStatisMap.forPayInterest !=''">${accmountStatisMap.forPayInterest}</s:if><s:else>0.00</s:else></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="s_centr">
			 <!--标题-->
			 <div class="myhome_tit tab_meun">
				<ul>
					<li onclick="jumpUrl('homeBorrowInvestList.do?nav=homeBorrowInvestList');">投资列表</li>
					<li onclick="jumpUrl('homeBorrowTenderInList.do?nav=homeBorrowInvestList');">招标中的借款</li>
					<li onclick="jumpUrl('homeBorrowRecycleList.do?nav=homeBorrowInvestList');">回收中的借款</li>
					<li onclick="jumpUrl('homeBorrowRecycledList.do?nav=homeBorrowInvestList');">已结清的借款</li>
					<li class="on" onclick="jumpUrl('queryAuctedDebtW.do?nav=homeBorrowInvestList');">已转出的债权</li>
				</ul>
			 </div>
			 <!--内容-->
			 <div class="myhome_content tab_content clearfix">
				  <!--成功借出-->
				<div>
					 <div class="myhome_srh">
	              	  	<form id="searchForm" action="queryAuctedDebt.do" method="post">
				         	<input type="hidden" name="curPage" id="pageNum"/>
	                   	 <ul class="clearfix">
	                        <li>标题：<input type="text" name="borrowTitle" value="${paramMap.borrowTitle }" class="inp90 test2"/></li>
	                        <li><input type="button" class="myhome_btn" id="btn_search" value="搜 索" /></li>
	                     </ul>
	                   </form>
	                  </div>
					  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
							<tr>
								<th>标题</th>
	                            <th>年利率</th>
	                            <th>剩余期数</th>
	                            <th>债权价值</th>	
	                            <th>转出价格</th>
	                            <th>手续费</th>
	                            <th>实际收入</th>
	                            <th>购买者</th>
	                            <th>成交时间</th>
	                            <th>转让协议</th>
							</tr>
							 <s:if test="pageBean.page==null || pageBean.page.size() == 0">
					    <tr>
					      <td colspan="11" align="center">暂无记录</td>
					      </tr>
					    </s:if>
					    <s:else>
					   <s:iterator value="pageBean.page" var="bean">
                        <tr>
                        	<td align="center"><a href="financeDetail.do?id=${borrowId}" target="_blank">${borrowTitle }</a></td>
							<td align="center">${annualRate }%</td>
						    <td align="center">${remainPeriod }个月</td>
						    <td align="center"><fmt:formatNumber value="${debtSum }" type="number" pattern="0.00" /></td>
						    <td align="center"><fmt:formatNumber value="${debtPrice }" type="number" pattern="0.00" /></td>
						    <td align="center">${manageFee }</td>
						    <td align="center">${debtPrice - manageFee}</td>
						    <td align="center">${auctionName }</td>
						    <td align="center"><fmt:formatDate value="${createTime }" type="date"/></td>
							<td align="center">
								<form action="protocol.do" id="form<s:property value='#s.count'/>" method="post" target="_blank">
						          <input type="hidden" name="typeId" value="${bean.encTypeId }"/>
						          <input type="hidden" name = "borrowId" value="${bean.encBorrowId }"/>
						          <input type="hidden" name = "investId" value="${bean.encInvestId }"/>
						          <input type="hidden" name = "aid" value="${bean.encDebtId }"/>
						          <a class="blue-font" href="javascript:void(0);" onclick="$('#form<s:property value='#s.count'/>').submit();">转让协议</a>
						      	</form>	
	      					</td>
                        </tr>
			         </s:iterator>
                        </s:else>         
                    </table> 
						 <!--分页器 开始-->
						 <div class="wrap" style="margin:20px 0 10px 0">
							<div class="inwrap">
								<div  class="page" >
								     <shove:page url="homeBorrowInvestList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
								    	<s:param name="publishTimeStart">${paramMap.publishTimeStart}</s:param>
								    	<s:param name="publishTimeEnd">${paramMap.publishTimeEnd}</s:param>
								    	<s:param name="title">${paramMap.title}</s:param>
								    	<s:param name="type">${paramMap.type}</s:param>
								     </shove:page>
							  	</div>            	
							</div>
						</div>
						<!--分页器 结束--> 
				  </div>
			 </div>
		</div>
    </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
	$("#zh_hover").attr('class','nav_first');
	$("#zh_hover div").removeClass('none');
	$(".lne_l3 > ul >li").removeClass("clicked");
	$('#li_4').addClass('clicked');
    $('#li_10').addClass('on');
	$('#li_14').addClass('on');
   	$("#btn_search").click(function(){
   			$("#pageNum").val(1);
   			$("#searchForm").submit();
   		});
   	$("#jumpPage").click(function(){
	 	var curPage = $("#curPageText").val();
	 	 if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		 }
	 	$("#pageNum").val(curPage);
	 	$("#searchForm").submit();
	 });
   });

function jumpUrl(obj){
    window.location.href=obj;
}
function showAgree(investId,borrowId){
    var url = "getMessageBytypeId.do?typeId=24&&borrowId="+borrowId+"&&investId="+investId+"&&styles=1";
    jQuery.jBox.open("post:"+url, "查看协议书", 700,400,{ buttons: { } });
    window.open(optionalArg1, optionalArg2, optionalArg3, optionalArg4)
}			     
</script>
</body>
</html>