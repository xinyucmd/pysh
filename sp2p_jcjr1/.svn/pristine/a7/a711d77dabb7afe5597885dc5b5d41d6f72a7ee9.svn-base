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
             <ul><li onclick="goUrl('queryCanAssignmentDebt.do')">可转让的债权</li>
	        <li class="on" onclick="goUrl('queryAuctingDebt.do');">转让中的债权</li>
	        <li onclick="goUrl('queryAuctedDebt.do');">已转出的债权</li>
	        <li onclick="goUrl('queryInDebt.do');" style="border-right:none;">已转入的债权</li>
	        </ul>
         </div>
         <!--内容-->
         <div class="myhome_content tab_content clearfix">
              <div>
              
              	  <div class="myhome_srh">
              	  	<form id="searchForm" action="queryAuctingDebt.do" method="post">
                   	<div class="form-group">
			         	<input type="hidden" name="curPage" id="pageNum"/>
                   	
                        标题：<input type="text" name="borrowTitle" value="${paramMap.borrowTitle }" class="form-control form-xs Wdate" style="width:130px; height:28px;" />
                     </div>
                     <input type="button" class="btn btn-primary btn-lg"id="btn_search" value="搜 索" style="position:relative; top:-3px\0; *top:-45px; *left:180px;" />
                   </form>
                  </div>
                   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
                        <tr>
                            <th>标题</th>
                            <th>剩余期数</th>
                            <!-- <th>年利率</th> -->
                            <th>债权价值</th>
                            <th>转让价格</th>
                            <th>转让系数</th>
                            <th>申请转让时间</th>	
                            <th>操作</th>
                        </tr>
                        
                        <s:if test="pageBean.page==null || pageBean.page.size() == 0">
					    <tr>
					      <td colspan="10" align="center">暂无记录</td>
					      </tr>
					    </s:if>
					    <s:else>
					   <s:iterator value="pageBean.page" var="bean">
                        <tr>
                            <td><a href="financeDetail.do?id=${borrowId}"  target="_blank">${borrowTitle }</a></td>
                            <td>${remainPeriod }个月</td>
                           <!-- <td>${annualRate }%</td>-->
                            <td>${debtValue} 元</td>
                            <td>${debtPrice} 元</td>
                            <td>${transRatio}</td>
						    <td><fmt:formatDate value="${publishTime }" type="date"/></td>
						    <td><a class="blue-font" href="cancelApplyDebt.do?debtId=${debtId}" >撤回</a></td>
                          <%-- //whb去掉竞拍  <td><fmt:formatNumber value="${realAmount }" type="number" pattern="0.00" /></td>
					 	    <td><fmt:formatNumber value="${auctionBasePrice }" type="number" pattern="0.00" /></td>
						    <td><fmt:formatNumber value="${auctionHighPrice }" type="number" pattern="0.00" /></td>
						    
		                    <td>
						    	<a class="blue-font" href="auctingDebtEnd.do?debtId=${debtId}">成交</a> <a class="blue-font" href="queryDebtDetail.do?id=${debtId }">查看</a>
                            </td> --%>
                        </tr>
                        </s:iterator>
                        </s:else>         
                    </table> 
                     <!--分页器 开始-->
                     <div class="page">
				    	<shove:page url="queryAuctingDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
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
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

<script>
   $(function(){
      $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
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
	 
   function goUrl(url){
   
   	   window.location.href = url;
   }	
	
  
   
   
</script>
</body>
</html>
