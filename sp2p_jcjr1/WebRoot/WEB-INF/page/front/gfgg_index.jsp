<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
<meta http-equiv="keywords" content="微信贷公告">
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<!-------------内容区 begin-------->
<div class="p_gymainbox clearfix">
   <div class="p_gyleftnavbox">
      <ul>
        <li id="aboutower" class="p_clicked"><span class="jcbanking"><em></em></span><a href="javascript:void(0);">微信贷</a></li>
         <!--  
        <li id="jituan"><span class="p_gy002"><em></em></span><a href="javascript:void(0);">集团介绍</a></li>
        <li id="teammanager"><span class="p_gy003"><em></em></span><a href="#">管理团队</a></li>
        -->
        <li id="teamwork"><span class="partner"><em></em></span><a href="javascript:void(0);">合作伙伴</a></li>
        <li id="mediaReport"><span class="media_a"><em></em></span><a href="javascript:void(0);">媒体报道</a></li>
        <li id="communique"><span class="official"><em></em></span><a href="javascript:void(0);">官方公告</a></li>
        <li  id="invite"><span class="recruiting"><em></em></span><a href="javascript:void(0);">招贤纳士</a></li>
        <li id="touchus"><span class="contact"><em></em></span><a href="javascript:void(0);">联系我们</a></li>
      </ul>
   </div>
   <div class="p_gyrightbox">
      <div class="p_gytitile">
         <h4 id="title">${paramMap.columName}</h4>
         <span><a href="index.jsp">首页</a>丨<a id = "biaoqian" class="p_7aa"></a></span>
      </div>
      <div id="showcontent" class="p_gytext">
          <p>${paramMap.content}</p>
      </div>
   </div>
   
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
     //样式选中
	dqzt(4);
	param["pageBean.pageNum"] = 1; 
	$('#aboutower').removeClass('p_clicked');
	$('#communique').removeClass('p_gy002');
	 $('#communique').addClass('p_clicked');
	 queryGfgg(param);
	$("#biaoqian").html("媒体报道");
	//联系我们 
	 $("#touchus").click(function(){
		 	$('#jituan').removeClass('p_clicked');
			$('#aboutower').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#touchus').removeClass('p_gy002');
			 $('#touchus').addClass('p_clicked');
		    $.post("queryMessageDetail.do","typeId=7",function(data){
		         $("#showcontent").html("");
		         if(data.content==''){
		        	 $("#showcontent").html("<p>暂无数据</p>");
			     }else{
			    	 $("#showcontent").html("<p>"+data.content+"+</p>");
				 }
		         $("#title").html(data.columName);
		         $("#biaoqian").html(data.columName);
		    });
		});
	 $("#teammanager").click(function(){
		 $('#jituan').removeClass('p_clicked');
			$('#aboutower').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#teammanager').removeClass('p_gy002');
			 $('#teammanager').addClass('p_clicked');
			 param["pageBean.pageNum"] = 1; 
			 queryTdgl(param);
		});

		//关于我们
		$("#aboutower").click(function(){
			$('#jituan').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#aboutower').removeClass('p_gy002');
			 $('#aboutower').addClass('p_clicked');
		    $.post("queryMessageDetail.do","typeId=4",function(data){
		         $("#showcontent").html("");
		         if(data.content==''){
		        	 $("#showcontent").html("<p><img src='images/p_ico049.png' /></p>");
			        }else{
			        	$("#showcontent").html("<p>"+data.content+"</p><p><img src='images/p_ico049.png' /></p>");
				    }
		         
		         $("#title").html(data.columName);
		         $("#biaoqian").html("微信贷");
		    });
		});


		//集团介绍
		$("#jituan").click(function(){
			$('#aboutower').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#jituan').removeClass('p_gy002');
			 $('#jituan').addClass('p_clicked');
		    $.post("queryMessageDetail.do","typeId=34",function(data){
		         $("#showcontent").html("");
		         $("#showcontent").html("<p>"+data.content+"</p>");
		         $("#title").html(data.columName);
		         $("#biaoqian").html(data.columName);
		    });
		});

			//资费说明
		$("#costdesc").click(function(){
			 $('#costdesc a').addClass('li_curr');
				$('#invite a').removeClass('li_curr');
				$('#teamwork a').removeClass('li_curr');
				$('#links a').removeClass('li_curr');
				 $('#communique').removeClass('li_curr');
				$('#mediaReport').removeClass('li_curr');
				 $('#touchus a').removeClass('li_curr');
				 $('#aboutower a').removeClass('li_curr');
		    $.post("queryMessageDetail.do","typeId=6",function(data){
		         $("#showcontent").html("");
		         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>"+data.columName+"</h3></div>"+
		                 "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
		    });
		});


			//招贤纳士
		$("#invite").click(function(){
			$('#jituan').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#aboutower').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#invite').removeClass('p_gy002');
			 $('#invite').addClass('p_clicked');
		    $.post("queryMessageDetail.do","typeId=10",function(data){
		         $("#showcontent").html("");
		         if(data.content==''){
		        	 $("#showcontent").html("<p>暂无数据</p>");
			     }else{
			    	 $("#showcontent").html("<p>"+data.content+"+</p>");
				 }
		         $("#title").html(data.columName);
		         $("#biaoqian").html(data.columName);
		    });
		});

				//合作伙伴
		$("#teamwork").click(function(){
			$('#jituan').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#aboutower').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#teamwork').removeClass('p_gy002');
			 $('#teamwork').addClass('p_clicked');
		    $.post("queryMessageDetail.do","typeId=11",function(data){
		         $("#showcontent").html("");
		         if(data.content==''){
		        	 $("#showcontent").html("<p>暂无数据</p>");
			     }else{
			    	 $("#showcontent").html("<p>"+data.content+"+</p>");
				 }
		         $("#title").html(data.columName);
		         $("#biaoqian").html(data.columName);
		    });
		});

					//友情链接
		$("#links").click(function(){
			 $('#invite a').removeClass('li_curr');
			 $('#communique').removeClass('li_curr');
			 $('#costdesc a').removeClass('li_curr');
			 $('#teamwork a').removeClass('li_curr');
			 $('#mediaReport').removeClass('li_curr');
			 $('#touchus a').removeClass('li_curr');
			 $('#aboutower a').removeClass('li_curr');
			 $('#links a').addClass('li_curr');
		    $.post("frontQueryMediaReportdList.do","",function(data){
		         $("#showcontent").html("");
		         $("#showcontent").html("<div class='news_content_wrap'><div class='news_bar'><h3>友情链接</h3></div>"+data+"</div>");
		         $(".boxmain h3").remove();
		         $(".news_content_wrap").append("<div class='clear'></div>");
		         $(".boxmain ul").removeClass("kefu").addClass("media_links");
		         $(".media_links img").css("width","150").css("height","60px");
		         $("#title").html("友情链接");
		         $("#biaoqian").html("友情链接");
		    });
		});

						//媒体报道
		$("#mediaReport").click(function(){
			$('#jituan').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#aboutower').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			 $('#communique').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#mediaReport').removeClass('p_gy002');
			 $('#mediaReport').addClass('p_clicked');
			  param["pageBean.pageNum"] = 1; 
			  queryMtbd(param);
		});

		

		//官方公告
		$("#communique").click(function(){
			$('#jituan').removeClass('p_clicked');
			$('#teammanager').removeClass('p_clicked');
			$('#aboutower').removeClass('p_clicked');
			$('#invite').removeClass('p_clicked');
			 $('#mediaReport').removeClass('p_clicked');
			$('#teamwork').removeClass('p_clicked');
			$('#touchus').removeClass('p_clicked');
			$('#communique').removeClass('p_gy002');
			 $('#communique').addClass('p_clicked');
			  param["pageBean.pageNum"] = 1; 
			 queryGfgg(param);
		});
	
	
	
});
function doMtbdJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryMtbd(param);
	}
	function queryMtbd(parDate){
		 $.post("queryMediaReportListPage.do",parDate,function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	         $("#title").html("媒体报道");
	    });
	}
	//团队管理
	function queryTdgl(parDate){
		 $.post("queryTeamManangerListPage.do",parDate,function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	         $("#title").html("团队管理");
	         $("#biaoqian").html("团队管理");
	    });
	}

	

	function doqueryGfggJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryGfgg(param);
	}
	function queryGfgg(parDate){
		 $.post("queryNewsListPage.do",parDate,function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	         $("#title").html("官方公告");
	         $("#biaoqian").html("官方公告");
	    });
	}
</script>
</body>
</html>
