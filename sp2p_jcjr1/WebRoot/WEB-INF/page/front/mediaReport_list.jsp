<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<script type="text/javascript" src="script/jquery-2.0.js"></script>
	<script type="text/javascript" src="script/front.js"></script>
	<!--<link rel="stylesheet" type="text/css" href="css/site.css" />-->
<script type="text/javascript">
       function mediaDetail(param){
      
          $.post("frontMediaReportDetails.do","id="+param,function(data){
                  $("#showcontent").html("");
	              $("#showcontent").html("<h3>"+data.title+"</h3>"+
	                             "<p class='time'></p>"+
	                           "<p class='zw'>"+data.content+"</p>");
          });
       }
</script>

<div id="showcontent" class="about_ul clearfix">
	
				<ul class="clearfix about_ul1">
				<s:if test="pageBean.page==null || pageBean.page.size==0"><li class="clearfix">暂无数据</li></s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean">
					<li class="clearfix">
						<div class="jg_pic"><a href="${bean.url}" target="_blank"><img src="${bean.imgPath}" /></a></div>
						<div class="jg_txt">
							<h5><a href="${bean.url}" target="_blank">${bean.title}</a><span>${bean.publishTime}</span></h5>
							<p>${bean.content}
								<a href="frontMedialinkId.do?id=${bean.id}" class="more3">查看详情</a></p>
						</div>	
					</li>
					</s:iterator>
				</s:else>
				</ul>
				<div class="paging">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </div>
</div>

