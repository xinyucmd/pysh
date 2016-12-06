<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
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
        	<h4>联系客服</h4>
            <div class="h_zxkf clearfix">
            	 <s:if test="#request.lists == null || #request.lists.size<=0">
				           暂无数据
				      </s:if>
				      <s:else>
				       <ul class="clearfix">
				        <s:iterator value="#request.lists" var="bean" status="sta">
				              <li>
				              <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes" class="tx">
				              <shove:img src="${bean.kefuImage }" defaulImg="images/hslogo_42.jpg" title="${bean.name }" width="72" height="72"  ></shove:img>
				              </a><br/>
						      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes">${bean.name }</a><br/>
						      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes">
						      <img border="0" src="http://wpa.qq.com/pa?p=1:${QQ}:1" 
						      alt="点击这里给我发消息" title="点击这里给我发消息"/></a>
				              </li>
				        </s:iterator>
				        </ul>
				   </s:else>
              </div>
        </div>
        <!--右内容 结束-->
    </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
    function showAnswer(id){
        //alert(id);
		$.shovePost("callcenterAnswer.do",{id:id},initCallBack);
 	}
 	
 	function initCallBack(data){
 		$("#dataInfo").html(data);
 	}
 	
 	function cp(id){
	    $("#qs1").html("答："+$("#qss"+id).text());
	}
	
	 $("#jumpPage").attr("href","javascript:void(null)");
	 $("#curPageText").attr("class","cpage");
		$("#jumpPage").click(function(){
		     var curPage = $("#curPageText").val();
			 if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			 }
	    window.location.href="callcenter.do?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
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