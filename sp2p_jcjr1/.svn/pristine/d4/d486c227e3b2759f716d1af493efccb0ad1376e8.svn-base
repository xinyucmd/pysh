<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
    <meta http-equiv="description" content="微信贷解决您在P2P投资、借款、平台操作上遇到的各种问题。这里提供大量P2P理财/网络借款知识，满足有相同或类似问题的用户需求。">
  </head> 
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
    
 
 
 
<!--我的微信贷主要内容-->
<div class="wrap" style="padding-top:10px; background:#fff; border-top:solid 1px #e6e6e6; margin-top:10px;">
	 <div class="lne_cent">
     	<div class="left-sidenav">
                <div class="sidenav-top">
                    <ul>
                     <s:if test="#request.type==1">
                        <li style="border-top:none;"><a href="infomations.do?type=1" class="clicked"><i class="news-icon hy-new"></i>行业资讯</a></li>
                        <li><a href="licais.do?type=2" ><i class="news-icon lc-knowledge"></i>理财知识</a></li>
                        <li><a href="wxdCreates.do?type=3" ><i class="news-icon wxd-yc"></i>微信贷原创</a></li>
                     </s:if>
                     <s:if test="#request.type==2">
                        <li style="border-top:none;"><a href="infomations.do?type=1"><i class="news-icon hy-new"></i>行业资讯</a></li>
                        <li><a class="clicked" href="licais.do?type=2" ><i class="news-icon lc-knowledge"></i>理财知识</a></li>
                        <li><a href="wxdCreates.do?type=3" ><i class="news-icon wxd-yc"></i>微信贷原创</a></li> 
                     </s:if>
                     <s:if test="#request.type==3">
                        <li style="border-top:none;" ><a href="infomations.do?type=1"><i class="news-icon hy-new"></i>行业资讯</a></li>
                        <li><a href="licais.do?type=2" ><i class="news-icon lc-knowledge"></i>理财知识</a></li>
                        <li><a class="clicked" href="wxdCreates.do?type=3" ><i class="news-icon wxd-yc"></i>微信贷原创</a></li> 
                     </s:if>
                    </ul>
                </div>
                <div class="new-item grid-1" >
                    <p class="item-top1"><em class="arrow1 position1"></em>浏览资讯</p>
                            <ul class="invest-list">  
                               <s:iterator value="pageBean.page" var="bean">
                                <li style="height: 40px">${bean.title}</li>
                               </s:iterator>
                            </ul>   
                 </div> 
        </div>
        <div class="right-con">
            <ol class="breadcrumb">
             <s:if test="#request.type==1">
             	<li><a>当前位置</a></li><li><a>微信贷</a></li><li><a>行业资讯</a></li>
             </s:if>
             <s:if test="#request.type==2">
             	<li><a>当前位置</a></li><li><a>微信贷</a></li><li><a>理财知识</a></li>
             </s:if>
             <s:if test="#request.type==3">
             	<li><a>当前位置</a></li><li><a>微信贷</a></li><li><a>微信贷原创</a></li>
             </s:if>
            </ol>
            <div id="showcontent" class="about_ul clearfix">
                <ul class="clearfix about_ul1">
                    <!-- 行业资讯 -->
                    <s:if test="#request.type==1">
                    <s:iterator value="pageBean.page" var="bean">
                    <li class="clearfix">
                        <!-- 
                        <div class="jg_pic">
                            <a target="_blank" href="#">
                            <img src="img/201510231732463300.png"> 
                        	</a>
                        </div>
                         -->
                        <div class="jg_txt" style="width:690px;">
                            <h5>
                                <a target="_blank" href="infomationById.do?id=${bean.id}">${bean.title}</a>
                                <span><i>发布时间：${bean.publishTime}</i><i style="margin-left:50px;">新闻来源：江川金融</i></span>
                            </h5>
	                        <p>
	                           <shove:sub size="150" value="#bean.content" suffix="..."/>
	                           <a class="more3" href="infomationById.do?id=${bean.id}">查看详情</a>
	                        </p>
                        </div>
                    </li>
                    </s:iterator>
                    </s:if>
                    
                     <!-- 理财知识 -->
                    <s:if test="#request.type==2">
                    <s:iterator value="pageBean.page" var="bean">
                    <li class="clearfix">
                        <div class="jg_txt" style="width:690px;">
                            <h5>
                                <a target="_blank" href="licaiById.do?id=${bean.id}">${bean.title}</a>
                                <span><i>发布时间：${bean.publishTime}</i><i style="margin-left:50px;">新闻来源：江川金融</i></span>
                            </h5>
	                        <p>
	                           <shove:sub size="150" value="#bean.content" suffix="..."/>
	                           <a class="more3" href="licaiById.do?id=${bean.id}">查看详情</a>
	                        </p>
                        </div>
                    </li>
                    </s:iterator>
                    </s:if>
                    
                     <!-- 微信贷原创 -->
                    <s:if test="#request.type==3">
                    <s:iterator value="pageBean.page" var="bean">
                    <li class="clearfix">
                        <div class="jg_txt" style="width:690px;">
                            <h5>
                                <a target="_blank" href="wxdCreateById.do?id=${bean.id}">${bean.title}</a>
                                <span><i>发布时间：${bean.publishTime}</i><i style="margin-left:50px;">新闻来源：江川金融</i></span>
                            </h5>
	                        <p>
	                           <shove:sub size="150" value="#bean.content" suffix="..."/>
	                           <a class="more3" href="wxdCreateById.do?id=${bean.id}">查看详情</a>
	                        </p>
                        </div>
                    </li>
                    </s:iterator>
                    </s:if>
                    
                </ul>
             </div>
              <div class="s_foot-page">
	            <p>
	            <s:if test="#request.type==1">
	               <shove:page url="infomations.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
				     <s:param name="type">${type}</s:param>
				   </shove:page>
				</s:if>
				 <s:if test="#request.type==2">
	               <shove:page url="licais.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
				     <s:param name="type">${type}</s:param>
				   </shove:page>
				</s:if>
				<s:if test="#request.type==3">
	               <shove:page url="wxdCreates.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
				     <s:param name="type">${type}</s:param>
				   </shove:page>
				</s:if>
	            </p>
		   </div>
        </div>
    </div>      
</div>
<!--我的微信贷主要内容-->
 
    <!--footer-->
 
<jsp:include page="/include/footer.jsp"></jsp:include>
 <!--footer-->
 
<script type="text/javascript">
  if('${type}'==1){
       $("#jumpPage").click(function(){
			     var curPage = $("#curPageText").val();
			    
				 if(isNaN(curPage)){
					alert("输入格式不正确!");
					return;
				 }
				 $("#pageNum").val(curPage);
		        
				 window.location.href = "infomations.do?type=1&curPage="+curPage;
		});
  }
  
  if('${type}'==2){
      $("#jumpPage").click(function(){
			     var curPage = $("#curPageText").val();
			    
				 if(isNaN(curPage)){
					alert("输入格式不正确!");
					return;
				 }
				 $("#pageNum").val(curPage);
		        
				 window.location.href = "licais.do?type=2&curPage="+curPage;
		});
 }
  
  if('${type}'==3){
      $("#jumpPage").click(function(){
			     var curPage = $("#curPageText").val();
			    
				 if(isNaN(curPage)){
					alert("输入格式不正确!");
					return;
				 }
				 $("#pageNum").val(curPage);
		        
				 window.location.href = "wxdCreates.do?type=3&curPage="+curPage;
		});
 }
</script>
</body>
</html>
