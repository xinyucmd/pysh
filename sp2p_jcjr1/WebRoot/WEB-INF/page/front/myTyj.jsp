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
 -->					        <li  class="on" onclick="jumpUrl('myTyj.do');" style="width:120px; width:115px\9;">我的体验金</li>
					        <li onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px; width:115px\9;">奖励规则</li>
                            <li onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
                        </ul>
                    </div> 
                </div>
		 
         	 <div class="tab_content">
              <div class="lne-centr-con-content">
              <form action="myTyj.do" id="searchForm" method="post">
              <input type="hidden" name="curPage" id="pageNum" />
                <table border="0" cellspacing="0" cellpadding="0" class="table-con">
                  	       <tr>
								<th>序号</th>
								<th>体验金</th>
								<th>获取时间</th>
								<th>到期时间</th>
								<th>状态</th>
							</tr>
							 
							<s:if test="pageBean.page==null || pageBean.page.size==0">
							<tr align="center">
								 <td colspan="5">暂无数据</td>
							</tr>
							</s:if>
							<s:else>
						    <s:set name="counts" value="#request.pageNum"/>
							<s:iterator value="pageBean.page" var="bean" status="s">
										<tr>
											<td><s:property value="#s.count+#counts"/></td>
											<td>${bean.amount}</td>
											<td>${bean.createTime_f}</td>
											<td>${bean.endTime_f}</td>
											<td>
												<s:if test="%{#bean.state==0}">未使用</s:if>
												<s:if test="%{#bean.state==1}">已使用</s:if>
												<s:if test="%{#bean.state==2}">已过期</s:if>
					    					</td>
										</tr>
							</s:iterator>
						    </s:else>
							 
              </table> 
              </form>
              <div class="s_foot-page">
                                 <p>
								     <shove:page url="myTyj.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
								     </shove:page>
								     </p>
							  	</div>            	
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