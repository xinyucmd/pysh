<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
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


<div class="wrap" style="padding-top:10px;">
<div class="lne_cent">
<%@include file="/include/left.jsp" %>
   <div class="lne_centr">    
            <div class="lne-centr-con" style="margin-top:10px;">
            	<div style="background:#fff;padding-bottom:5px; margin-bottom:10px;">
                	<div class="myhome_tit tab_meun">
                        <ul>
                            <li onclick="jumpUrl('toRewardInfo.do');" style="width:120px; width:115px\9;">现金奖励</li>
					     <!--    <li onclick="jumpUrl('myBonus.do');" style="width:120px; width:115px\9;">红包奖励</li> -->
<!-- 					        <li onclick="jumpUrl('mySixBonus.do');" style="width:120px; width:115px\9;">6.24红包</li>
 -->					        <li onclick="jumpUrl('myTyj.do');" style="width:120px; width:115px\9;">我的体验金</li>
					        <li onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px; width:115px\9;">奖励规则</li>
                            <li class="on" onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
                        </ul>
                    </div> 
                </div>
		 
         	 <div class="tab_content">
              <div class="lne-centr-con-content">
              <form action="activityCi.do" id="searchForm" method="post">
              <input type="hidden" name="curPage" id="pageNum" />
                <table border="0" cellspacing="0" cellpadding="0" class="table-con">
                  	       <tr>
								<th>序号</th>
								<th>项目名称</th>
								<th>奖励金额</th>
								<th>发放奖励时间</th>
								<th>投标时间</th>
							    <th>状态</th>
							</tr>
							 
							<s:if test="#request.activityCiList==null || #request.activityCiList.size==0">
							<tr align="center">
								 <td colspan="5">暂无数据</td>
							</tr>
							</s:if>
							<s:else>
						    <s:set name="counts" value="#request.pageNum"/>
							<s:iterator value="#request.activityCiList" var="bean" status="s">
										<tr>
											<td><s:property value="#s.index+1"/></td>
											<td>${bean.borrowTitle}</td>
											<td>${bean.amount}元</td>
											<td>${bean.end_time}</td>
										    <td>${bean.create_time}</td>
											<td>
												<s:if test="%{#bean.status==0}">未发放</s:if>
												<s:if test="%{#bean.status==1}">已发放</s:if>
												 
					    					</td>
										</tr>
							</s:iterator>
						    </s:else>
							 
              </table> 
              </form>
                  	
			 </div>
			</div>
			<!--分页器 结束-->  
        	</div>
                             
           
     </div>	  
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>
<script src="/script/recommend/rye-jc-recommend-bonus.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script >

$("#jumpPage").click(function(){
	
 	var curPage = $("#curPageText").val();
 	if(isNaN(curPage)){
		alert("输入格式不正确!");
		return;
	}
 	$("#pageNum").val(curPage);
 	$("#searchForm").submit();

});

	function jumpUrl(obj){
	    window.location.href=obj;
	}
</script>
</body>
</html>