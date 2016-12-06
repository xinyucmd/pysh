<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
       <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
    </head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<!-------------内容区 begin-------->
<div class="p_xqmainbox" id ="borrow_detail" >
    
</div>
<!-------------主体 end----------->

<s:hidden id="remainDays" name="paramMap.remainDays"></s:hidden>
    	<s:hidden id="maxAuctionPrice" name="paramMap.maxAuctionPrice"></s:hidden>
    <%-- 	<s:hidden id="auctionMode" name="paramMap.auctionMode"></s:hidden> --%>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="/script/nav-zq.js"></script>
<script type="text/javascript" src="/script/jquery.shove-1.0.js"></script>
<script>
$(function(){
	//var user_address = $(".user_addressed").html();
	//var new_address = user_address.substring(0,3);
	//$(".user_addressed").html(new_address);
	
    //样式选中
    dqzt(1);
     $("#zq_hover").attr('class','nav_first');
	 $("#zq_hover div").removeClass('none');
	  
	 if($("#debtStatus").val() != 2){
	 	$("#span_remainDays").html("");
	 }
	 var param = {};
	 param["id"] = '${paramMap.borrowId}';
	 param["debtId"] = '${paramMap.debtId}';
	 
	 $.shovePost("queryDebtBorrowDetail.do",param,function(data){
	 	$("#borrow_detail").html(data);
	 });
});		     
</script>
</body>
</html>
