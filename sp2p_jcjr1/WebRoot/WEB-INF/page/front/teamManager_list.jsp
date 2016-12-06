<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<script type="text/javascript" src="script/jquery-2.0.js"></script>
	<script type="text/javascript" src="script/front.js"></script>
	<!--<link rel="stylesheet" type="text/css" href="css/site.css" />-->


<div id="showcontent" class="s_tdjs">
         <ul>
         	<s:if test="pageBean.page==null || pageBean.page.size==0"><li class="clearfix">暂无数据</li></s:if>
         	<s:else>
					<s:iterator value="pageBean.page" var="bean">
					<li class="clearfix">
						<div class="jg_pic"><img src="${bean.imgPath}" /></div>
						<div class="jg_txt">
							<h5>${bean.userName}</h5>
							<p>${bean.intro}</p>
						</div>	
					</li>
					</s:iterator>
			</s:else>
		</ul>
		<div class="paging">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </div>
</div>

