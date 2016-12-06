<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${infomationMap.title}</title>
<script src="js/jquery.min.js"></script>
<script src="js/common.js"></script>
<jsp:include page="/include/common.jsp"></jsp:include>
</head>
<body>
 <jsp:include page="/include/top.jsp"></jsp:include>
 
 
 
           
 
<!--我的微信贷主要内容-->
<div class="wrap" style="overflow:hidden; background:#fff; margin-top:10px; padding-top:10px;">
	 <div class="lne_cent">
		<ol class="breadcrumb">
          <li>
          	<a>当前位置</a>
          </li>
          <li>
          	<a href="#">微信贷</a>
          </li>
          <li>
         	 <a href="#">行业资讯贷</a>
          </li>
          
        </ol>
        
        <div class="newsView_content">
            <div class="content_bar"></div>
            <div class="newsView_list">
            	<div class="newsView_title">${infomationMap.title}</div>
                <div class="newsView_pdate">
                    <span>发布时间：${infomationMap.publishTime}</span>
                  
                </div>
                <div class="newsView_ctx">
                     <span>${infomationMap.content}</span>
                </div>
                <div class="newsView_brief">
                    <s:if test="#request.infomationMapAgo!=null">
                      <div class="left mediaTitle">
                                                             上一篇： <a href="infomationById.do?id=${infomationMapAgo.id}" style="max-width: 30px; overflow: hidden;">${infomationMapAgo.title}</a>
                    </div>
                    </s:if>
                    <s:if test="#request.infomationMapNext!=null">
                    <div class="right mediaTitle">
                                                       下一篇：
                        <a href="infomationById.do?id=${infomationMapNext.id}" style="max-width: 30px; overflow: hidden;">${infomationMapNext.title}</a>
                    </div>
                    </s:if>
                    <div class="clear"></div>
               </div>
    </div>      
</div>
</div>
</div>
 <div class="footer">
    <jsp:include page="/include/footer.jsp"></jsp:include>
</div>
</body>
</html>
