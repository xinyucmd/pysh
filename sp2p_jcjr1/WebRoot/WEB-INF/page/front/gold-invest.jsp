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

			 <!--内容-->
			 <div class="myhome_content tab_content clearfix">
				<div style="width:980px; position:relative; left:30%;">
					<div class="myhome_srh">
						<form action="dataProvidedToJintou.do" id="searchForm" method="post">
	          				<input type="hidden" name="curPage" id="pageNum" />
							<ul class="clearfix">
								<li>查询日期：
									<input style="width:160px;"type="text" id="starTime" size="200px" name="starTime" value="${starTime}" class="inp90 Wdate test2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								&nbsp;&nbsp;到&nbsp;&nbsp;
									<input type="text" style="width:160px;" id="endTime"  name="endTime" value="${endTime}" class="inp90 Wdate test2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/></li>
								<li><input type="button" class="myhome_btn" value="搜 索" id="search"/></li>
								<li><input type="button" class="myhome_btn" value="清空" id="clear"/></li>
							</ul>
						</form>
					</div>
					  <table width="60%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
							<tr>
								<th>序号</th>
								<th>日期</th>
								<th>激活用户数</th>
								<th>投资总额</th>
							</tr>
							<s:if test="pageBean.page==null || pageBean.page.size==0">
							   <tr align="center">
								  <td colspan="4">暂无数据</td>
							   </tr>
							</s:if>
							<s:else>
								<s:set name="counts" value="#request.pageNum"/>
								<s:iterator value="pageBean.page" var="bean" status="s">
										<tr>
										 	<td align="center"><s:property value="#s.count+#counts"/></td>
										 	<td align="center">${bean.investTime}</td>
										 	<td align="center">${bean.countPer}人</td>
										 	<td align="center">${bean.investSum}元</td>
										</tr>
								</s:iterator>
							</s:else>						
						</table> 
						 <!--分页器 开始-->
						 <div class="wrap" style="margin:20px 0 10px 0">
							<div class="inwrap">
								<div  class="page" >
								     <shove:page url="dataProvidedToJintou.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
								    	<s:param name="starTime">${paramMap.starTime}</s:param>
								    	<s:param name="endTime">${paramMap.endTime}</s:param>
								    	<s:param name="pid">4</s:param>
								     </shove:page>
							  	</div>            	
							</div>
						</div>
						<!--分页器 结束--> 
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
//dqzt(2)
     $('#li_10').addClass('on');
	 $('#clear').click(function(){
		$("#starTime").val("");
		$("#endTime").val("");
     });
	 $('#search').click(function(){
	 	if($("#starTime").val() >$("#endTime").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
		$("#pageNum").val(1);
	 	$("#searchForm").submit();
     });
     
     $("#jumpPage").click(function(){
		if($("#starTime").val() >$("#endTime").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
	 	var curPage = $("#curPageText").val();
	 	if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		}
	 	$("#pageNum").val(curPage);
	 	$("#searchForm").submit();
	});
});

</script>
</body>
</html>