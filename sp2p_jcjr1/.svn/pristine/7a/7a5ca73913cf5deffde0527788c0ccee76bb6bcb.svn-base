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
            <div class="lne-centr-con" style="margin-top:10px;">
            	<div style="background:#fff;padding-bottom:5px; margin-bottom:10px;">
                	<div class="myhome_tit tab_meun">
                        <ul>
                            <li onclick="jumpUrl('toRewardInfo.do');" style="width:120px; width:115px\9;">现金奖励</li>
					       <!--  <li onclick="jumpUrl('myBonus.do');" style="width:120px;width:115px\9;">红包奖励</li> -->
<!-- 					        <li onclick="jumpUrl('mySixBonus.do');" style="width:120px;width:115px\9;">6.24红包</li>
 -->					        <li onclick="jumpUrl('myTyj.do');" style="width:120px;width:115px\9;">我的体验金</li>
					        <li class="on" onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px;width:115px\9;">奖励规则</li>
                            <li onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
                        </ul>
                    </div> 
                </div>
                 <div class="lne-centr-top" style="border-bottom:solid 10px #f6f6f6 ; padding:10px 0 10px 10px;">
                           <p><em class="blue-color">老手新投奖励：</em>注册未投资用户首投任意标的1000元及以上即可获得200元新手红包。</p>
                           <p style="border-bottom:none;"><em class="blue-color">推荐投资奖励：</em>具体投资金额及返利比例如下：</p>
                        </div>
		 	<table border="0" cellspacing="0" cellpadding="0" class="table-con">
				<tr>
					<th>好友单次投资金额</th>
					<th>年化返利比例</th>
					
				</tr>
				<tr>
					<td>200万以上</td>
					<td>1.7%</td>
					
				</tr>
				<tr>
					<td>100万＜投资≤200万</td>
					<td>1.50%</td>
					
				</tr>
				<tr>
					<td>50万＜投资≤100万</td>
					<td>1.20%</td>
					
				</tr>
				<tr>
					<td>50万及以下</td>
					<td>1%</td>
					
				</tr>
			</table>
			 <div class="play-time">
                        	<p>活动截止日期：2016年7月31日</p>
                            <p>详细活动说明请看活动页面</p>
                            <div class="my-mode" style="border-top:none;">
                                                                                     推荐好友方式：
                                
                                <a  href="javascript:;" class="theme-login"><i class="my-arrow my-icon1"></i>微信扫码二维码推荐</a>
                         <a href="#" id="img_cp_link" data-clipboard-target="reconmmend_link"><i class="my-arrow my-icon2"></i>好友点开链接进行注册</a>
                         <input type="hidden" id="reconmmend_link" value="http://www.jcbanking.com/cellPhonereginit.do?refferee=${userName}&src=1"/>
                    		</div>
             </div>  
        	</div>
        	<br/>
           
		 </div>
		 <div class="theme-popover">
        <div>
       	  	<p class="tibo" style="border-bottom:solid 1px #c9c9c9;" >分享到微信朋友圈</p>
            <p style="margin:15px 0; text-align:center;" id="imgCode"></p>
            <p class="tibo" style="border-top:solid 1px #c9c9c9;">打开微信使用"扫一扫"即可将网页分享到我的微信朋友圈。</p>
        </div>	
        <div class="theme-poptit"><a  title="关闭" class="close"></a></div>
    </div>
	</div>
</div>

<!-- 引用底部公共部分 -->     

<jsp:include page="/include/footer.jsp"></jsp:include>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>
<script src="/script/recommend/rye-jc-recommend-list.js"></script>
<script >

function jumpUrl(obj){
    window.location.href=obj;
}

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