<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${sitemap.siteName}</title>
   <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include> 
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="wrap" style="padding-top:10px;">
<div class="lne_cent">

	<jsp:include page="/include/left.jsp"></jsp:include>
	<div class="lne_centr">

	<div class="lne_centr s_centr">
    	  <!--标题-->
      	 <div class="myhome_tit tab_meun">
             <ul><li class="on" onclick="goUrl('queryCanAssignmentDebt.do')">可转让的债权</li>
	        <li onclick="goUrl('queryAuctingDebt.do');">转让中的债权</li>
	        <li onclick="goUrl('queryAuctedDebt.do');">已转出的债权</li>
	        <li onclick="goUrl('queryInDebt.do');" style="border-right:none;">已转入的债权</li>
	        </ul>
         </div>
         <!--内容-->
         <div class="myhome_content tab_content clearfix">
              <!--可以转让的借款-->
              <div>           	
              	<div id="zrzq_div" class="tcbox" style="display: none;">
			      <div class="tcmain">
			      <p class="zqsm">
			     		 简单的债权说明
			      </p>
			      <div id="debt_add" class="xzzl">
			     		<s:include value="debt-manger-add.jsp"></s:include>
			      </div>
			      </div>
			    </div>
              	  <div class="myhome_srh">
              	  	<form id="searchForm" action="queryCanAssignmentDebt.do" method="post">
                     <div class="form-group">
			         	<input type="hidden" name="curPage" id="pageNum"/>
                  
                       标题：<input type="text" name="borrowTitle" value="${paramMap.borrowTitle }" class="form-control form-xs Wdate" style="width:130px; height:28px;" />
                  	</div>
                    <input type="button" class="btn btn-primary btn-lg" id="btn_search" value="搜 索" style="position:relative; top:-3px\0; *top:-45px; *left:180px;"/></li>
                   </form>
                  </div>
                   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
                        <tr>
                            <th>标题</th>
                            <th>剩余期数</th>
                            <th>下个还款日</th>
                            <th>年利率</th>
                            <th>待收本息</th>
                            <th>债权价值</th>	
                            <th>操作</th>	
                        </tr>
                        <s:if test="pageBean.page==null || pageBean.page.size() == 0">
					    <tr>
					      <td colspan="9" align="center">暂无记录</td>
					      </tr>
					    </s:if>
					    <s:else>
					   <s:iterator value="pageBean.page" var="bean">
                        <tr>
                       		<s:if test="#bean.debtStatus==null||#bean.debtStatus==3||#bean.debtStatus==4">
	                            <td><a href="financeDetail.do?id=${borrowId}"  target="_blank">${borrowTitle }</a></td>
	                            <td>${remainPeriod }个月</td>
	                            <td>${nextRepayDate }</td>
	                            <td>${annualRate }%</td>
		    					<%-- whb债权修改<td><fmt:formatNumber value="${realAmount }" type="number" pattern="0.00" /></td>
		    					<td><fmt:formatNumber value="${ hasPI}" type="number" pattern="0.00" /></td> --%>
		    					<td><fmt:formatNumber value="${recievedPI-hasPI}" type="number" pattern="0.00" /></td>
	                            <td>${debtValue}</td>
	                            <td>
							    	<a class="blue-font" href="javascript:void(0)" onclick="addAssignmentDebt('${borrowId}','${debtValue}','${remainPeriod }','${investId}','${term}','${interest}','${principalBalance}','${debtPrice}')"> 债权转让</a>
							    <%-- 	<s:elseif test="#bean.debtStatus==1">
							    		<a class="blue-font" href="cancelApplyDebt.do?debtId=${debtId}" >撤回</a>
							    	</s:elseif> --%>
	                            </td>
	                         </s:if>
	                         
                        </tr>
                        </s:iterator>
                        </s:else>         
                    </table> 
                     <!--分页器 开始-->
                     <div class="page">
				    	<shove:page url="queryCanAssignmentDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
							<s:param name="borrowerName" >${paramMap.borrowerName }</s:param>
							<s:param name="borrowTitle" >${paramMap.borrowTitle }</s:param>
						</shove:page>
				    </div>
                    <!--分页器 结束--> 		
              </div>
         </div>
    </div>
</div>
</div>
</div>

 
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

<script>
   $(function(){
       //$("#zh_hover").attr('class','nav_first');
	 //$("#zh_hover div").removeClass('none');
	 //$('#li_14').addClass('on');
	 $(".lne_l3 > ul >li").removeClass("clicked");
	 $('#li_5').addClass('clicked');
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
	
	function addAssignmentDebt(borrowId,debtValue,remainPeriod,investId,term,interest,principalBalance,debtPrice){
		//alert(debtValue+",debtPrice"+debtPrice);
		$("#term").val(term);
		$("#interest").val(interest);
		$("#principalBalance").val(principalBalance);
		$("#debtPrice").val(debtPrice);
		$("#debtSum").val(debtValue);
		$("#span_debtSum").html(debtValue);
		$("#span_debtPrice").html(debtPrice);
		$("#zrzq_div").attr("style","");

		$("#borrowId").val(borrowId);
		$("#span_remainPeriod").html(remainPeriod);
		$("#investId").val(investId);
	} 
	function goUrl(url){
   	   window.location.href = url;
   }	
	
  
   
   
</script>
</body>
</html>
