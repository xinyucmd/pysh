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
<div class="lne_cent clearfix">
	<div class="kfzx clearfix">
    	<!--左导航 开始-->
        <div class="kf_nav clearfix">
            <dl class="clearfix">
            	<dt>常见问题</dt>
            	<s:iterator value="#request.types" id="callcenter" var="bean">
	            	<s:if test="#bean.id==#request.typeId">
	            		<dd class="on"><a href="callcenter.do?type=true&cid=${bean.id}" >${bean.helpDescribe }</a></dd>
				    </s:if>
				    <s:else>
				    	<dd><a href="callcenter.do?type=true&cid=${bean.id}" >${bean.helpDescribe }</a></dd>
				    </s:else>		
            	</s:iterator>
            </dl>
            <dl class="clearfix">
            	<dt>联系客服<a href="callkfs.do" class="s_morekf">更多</a></dt>               
            </dl>
            <s:if test="#request.lists ==null || #request.lists.size<=0">
			        暂无数据
			    </s:if>
			    <s:else>
			    <ul class="s_kfpic clearfix">
				    <s:iterator value="#request.lists"  var="bean">
					     <li>
					      <a target="_blank" 
					      href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes" >
					      <shove:img src="${bean.kefuImage }" defaulImg="images/hslogo_42.jpg" title="${bean.name }" width="72" height="72"  ></shove:img>
					      </a>
					      </li>
				    </s:iterator>
			     </ul>
			    </s:else>  
        </div>
        <!--左导航 结束-->
        <!--右内容 开始-->
        <div class="kf_right clearfix">
        	<h4><s:property value="#request.curDes" default="常见问题" ></s:property></h4>
            <div class="kf_content clearfix">
            	 <ul class="s_helpfaq">
            	 <s:if test="pageBean.page!=null || pageBean.page.size>0">
            	 	<s:iterator value="pageBean.page"  var="bean" status="sta">
            	 			<li>
            	 				<dl class="s_helpfaqcont">
            	 				<dt><a id="qs" href="javascript:showAnswer(${bean.questionId})" onclick="javascript:cp(${bean.questionId});"><strong>问：</strong><span id="qss${bean.questionId}">${bean.question}</span></a></dt>
		                            <dd ><p><strong><span id="qs${bean.questionId}"></span></strong></p>
		                            	<p><span id="dataInfo${bean.questionId}"></span></p>
		                            </dd>
		                        </dl>
	                     </li>
	                     </s:iterator>
            	 	</s:if><s:else>暂无数据</s:else>
                 </ul>
                  <!--分页器 开始
                  <div class="wrap" style="margin:20px 0 10px 0">
                 <div>
					<p>
					   	<shove:page url="callcenter.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
					   		<s:param name="cid" >${cid}</s:param>
					   		<s:param name="type" >true</s:param>
						</shove:page>
				    </p>
				</div> 
				</div>
                分页器 结束-->
            </div>
        </div>
        <!--右内容 结束-->
    </div>
</div>
<div id="qusId" style="display:none"></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
    function showAnswer(id){
    	$("#qusId").html(id);
		$.shovePost("callcenterAnswer.do",{id:id},initCallBack);
 	}
 	
 	function initCallBack(data){
 	 	var id = $("#qusId").html();
 		$("#dataInfo"+id).html(data);
 	}
 	
 	function cp(id){
	    $("#qs"+id).html("答："+$("#qss"+id).text());
	}
	
	 $("#jumpPage").attr("href","javascript:void(null)");
	 $("#curPageText").attr("class","cpage");
		$("#jumpPage").click(function(){
		     var curPage = $("#curPageText").val();
		     
		     var totalNum = parseInt("${pageBean.totalNum }", 0);
		     var size = parseInt("${pageBean.pageSize }", 0);
		     var pageNum = parseInt( totalNum / size , 0 ) + 1;
		     
		     if(curPage > pageNum){
		    	 curPage = pageNum;
		     }
		     
			 if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			 }
	    window.location.href="callcenter.do?curPage="+curPage+"&pageSize=${pageBean.pageSize}"+"&cid="+"${cid}"+"&type=true";
		});
	
	$(function(){
    //样式选中
	var sd=parseInt($(".l_navmain").css("height"));
	var sf=parseInt($(".r_main").css("height"));
	if(sd<sf){
	$(".l_navmain").css("height",sf)
	}
		dqzt(6);
 
	});	
</script>

</body>
</html>