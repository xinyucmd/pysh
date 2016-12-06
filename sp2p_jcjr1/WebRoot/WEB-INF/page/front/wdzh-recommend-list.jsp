<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${sitemap.siteName}</title>
	<jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include> 
	 <style>
	        ul li{ list-style:none;}
	    /*	.lne_cent{width:800px; height:auto; display:block; position:absolute; left:15%; top:10%;font-size: 14px; color: #666; font-family: "微软雅黑";}*/
	     /*	.weixin{ background:#c7c7c7; width:32px; height:26px; border-radius:2px; float:left; margin-left:10px;}
	        .weixin img{ position:relative; top:3px; left:5px;}*/
	        .p_text{  line-height:26px; margin-top:20px; font-size:12px;}
	        .p_con{ overflow:hidden; width:650px; margin-top:10px; margin-left:30px;}
	    /*	.p_con ul{ background:#f1f1f1; padding:12px 0; overflow:hidden; margin:5px 0;}*/
	        .p_con .item1 li{ float:left; width:130px; text-align:center;}
	        .p_con .item2 li{ float:left; width:160px; text-align:center;}
	        .p_con h5{ font-size:14px;}
	        .p_con h4{ font-size:14px; color:#ff6960; margin-top:8px; text-align:center;}
	        .p_po{width:690px;margin:20px auto; }
	        .p_list{ width:650px;  margin:20px auto;}
	        table { border-collapse: collapse; }
	        .zh_table{line-height:30px;	}	
	        .zh_table th{border:1px solid #ddd; background-color:#f9f9f9;font-weight:normal;text-align:center; color:#666; height:40px; width:92px; }
	        .zh_table td{border:1px solid #ddd;text-align:center; color:#666; font-size:14px; border-top:none; width:92px; }
	        .p_con_left{ float:left; width:500px;}
	        .p_con_left ul{ width:500px; padding:0; overflow:hidden;}
	        .p_con_left ul li{ float:left; width:230px; font-weight:bold; color:#666; font-family:"微软雅黑";}
	        .p_con_left ul li a{ color:#03F; font-size:12px;}
	        .p_con_right{  width:500px; overflow:hidden;}
	        .weixin{ float:left; margin-top:15px;}
	        .lianjie{ float:left; margin-left:50px;  margin-top:15px;}
	        .theme-popover{ width:385px; height:380px; position:fixed; display:none; z-index:9999; top:0; background:#fbfbfb; border-radius:8px; left:35%; border:solid 2px #dbdada; font-size:14px;}
	        .close{ background:url(/images/recommend/close.jpg) no-repeat; top:5px;right:5px;  position:absolute; z-index:10001; height:27px; width:27px;}
	        .theme-popover .tibo{ height:60px; line-height:60px; padding-left:20px;}
	        .load_con{ width:100%; height:200%; position:absolute; background:#fff; filter:alpha(opacity=30);  -moz-opacity:0.3;  -khtml-opacity: 0.3;  opacity: 0.3; top:0; }
			.page{ text-align:center; margin-top:25px;}
			.page .disabled {border-color: #eee;border-style: solid;border-width: 1px; color: #ddd;margin: 2px;padding: 2px 5px;}
			.page .current {background-color: #000000;border-color: #000000;border-right: 1px solid #000000;border-style: solid;border-width: 1px; color: #fff;
    font-weight: bold;margin: 2px;padding: 2px 5px;}
			.page a {border-color: #000000; border-right: 1px solid #000000;border-style: solid;border-width: 1px;color: #000000;margin: 2px;padding: 2px 5px;
    text-decoration: none;}
	    </style>
	    
	 
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<script type="text/javascript" src="js/ZeroClipboard.js"></script>

<div class="wrap" style="padding-top:10px;">
<div class="lne_cent">
<%@include file="/include/left.jsp" %>
 <div class="lne_centr">
           <!--债权转让--> 
            <div class="lne-centr-con">
            	<div style="background:#fff;padding-bottom:5px; margin-bottom:10px;">
                    <div class="mg-height" style="margin-top:0; line-height:40px;">   
                    </div>
                    <div class="my-mode">
                    	我要成为推荐人：
                    	<a  href="javascript:;" class="theme-login"><i class="my-arrow my-icon1"></i>微信推广</a>
                         <a href="#" id="img_cp_link" data-clipboard-target="reconmmend_link"><i class="my-arrow my-icon2"></i>复制链接</a>
                         <input type="hidden" id="reconmmend_link" value="http://www.jcbanking.com/cellPhonereginit.do?refferee=${userName}&src=1"/>
                    </div>
                </div>
                <p class="lne-centr-con-title mt18">
                	<span class="fl"><i class="my-arrow1 my-icon3"></i>总好友：<em class="blue-color"><font id="userCount"></font>人</em></span>
                    <span class="fl" style="margin-left:30px;"><i class="my-arrow1 my-icon4"></i>已认证：<em class="blue-color"><font id="userIdNoCount"></font>人</em></span>
                </p>
    
      		 
            	 
                 
                	<!-- <li>已认证的好友个数：<font id="userIdNoCount"></font>人</li> -->
               
            
            <%-- <div class="p_con_right">
            	<div class="weixin"><a href="javascript:;" class="theme-login"><img src="/images/recommend/wx.png" width="176" height="49" /></a></div> 
                <div class="lianjie"><a href="#"><img id="img_cp_link" src="/images/recommend/lianjie.png" width="176" height="49" data-clipboard-target="reconmmend_link"/></a></div> 
	            <input type="hidden" id="reconmmend_link" value="http://www.jcbanking.com/cellPhonereginit.do?refferee=${userName}&src=1"/>
        	</div> --%>
        <div class="tab_content">
                    <div class="lne-centr-con-content tab_content">
                        <table border="0" cellspacing="0" cellpadding="0" class="table-con1">
                       <tr>
                       	 <th>序号</th>
                           <th>用户名</th>
                           <th>真实姓名</th>
                           <th>注册时间</th>
                           <th>身份认证</th>
                           <th>好友来源</th>
                           <th>投资次数</th>
                       </tr>             
              </table>
              <table id="tab_list" border="0" cellspacing="0" cellpadding="0" class="table-con1">
              </table>  
         </div>
          <div class="s_foot-page">
            <p>
           <%--  <shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page> --%>
         	<a id="backPage">上一页</a>
            <span class="current" id="currentPage">1</span>
            /
            <span class="current" id="totalPage">1</span>
            <a id = "nextPage">下一页</a>
           </p>
         </div>
     </div>	  
</div>
</div>
    <div class="theme-popover">
        <div>
       	  	<p class="tibo" style="border-bottom:solid 1px #c9c9c9;" >分享到微信朋友圈</p>
            <p style="margin:15px 0; text-align:center;" id="imgCode"></p>
            <p class="tibo" style="border-top:solid 1px #c9c9c9;">打开微信使用"扫一扫"即可将网页分享到我的微信朋友圈。</p>
        </div>	
        <div class="theme-poptit"><a  title="关闭" class="close"></a></div>
    </div>
    </div></div>
    <!-- 
     <div class="load_con" id="loading"><span><img src="../images/admin/load.gif" class="load" alt="加载中..."  style="position:absolute; top:25%; left:50%;"/></span></div>
	 -->
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>
<script src="/script/recommend/rye-jc-recommend-list.js"></script>
<script >
	var param = {};
	var recommend = new Recommend(param);
	var user = new MUser(param);
	var pageNum = 1;
	var totalPageNum = 1;
	
	recommend.list(pageNum, 10);
	var baseUrl = "http://www.jcbanking.com/page/recommend/reg.html";
	// 加载二维码
	user.login_pc_check(function(data){
			if(user.isNotBank(data)){
				if(data.status == 1){
					showCode(data);
					return true;
				}else{
					alert('未登录');
				}
			}else{
				alert('检查登录状态失败！');	
			}
		});
	
	function showCode(data){
		// 加载二维码
		user.genQCode("imgCode", baseUrl+"?userName="+data.userName);
	}
	
	function flushPage(){
		$("#currentPage").empty();
		$("#currentPage").append(pageNum);
	}
	
	$("#backPage").click(function(){
		if(pageNum == 1){
			alert('已经是第一页了');
			$("#backPage").attr("disabled","disabled");
		}else{
			pageNum -=1;
			recommend.list(pageNum, 10);
		}
		
		flushPage();
	});
	
	$("#nextPage").click(function(){
		if(pageNum == totalPageNum){
			alert('没有更多数据了~！');
		}else{
			pageNum +=1;
			recommend.list(pageNum, 10);
		}
		
		flushPage();
		
	});
	
	// 定义一个新的复制对象
	var clip = new ZeroClipboard($("#img_cp_link"), {
	  moviePath: "js/ZeroClipboard.swf"
	} );

	// 复制内容到剪贴板成功后的操作
	clip.on( 'complete', function(client, args) {
	   //alert("复制成功，复制内容为："+ args.text);
		alert("复制成功,复制内容可直接粘贴!");
	} );
</script>
<script>
	    jQuery(document).ready(function($) {
	        $('.theme-login').click(function(){
	        	 $('.theme-popover').show();
	            $('.theme-popover').slideDown(200);
	        })
	        $('.theme-poptit .close').click(function(){
				 $('.theme-popover').hide();
	            $('.theme-popover').slideUp(200);
	        })
	    
	    })
    </script>
</body>
</html>