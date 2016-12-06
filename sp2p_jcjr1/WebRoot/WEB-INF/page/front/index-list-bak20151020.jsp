<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
<meta http-equiv="keywords" content="微信贷|江川金融微信贷|网络理财| P2P理财|P2P网贷|个人小额理财|P2P借款|互联网金融">
<meta http-equiv="description" content="微信贷，基于互联网平台和风险管理技术，为小微企业和个人提供 微小数额 的资金融通平台品牌，其核心含义是 微型信用贷款。">
<style>
.w1180 {width: 100%;margin:0 auto; z-index:998; font-family:"微软雅黑";}
.front{ position:absolute; top:140px;}
.login_front{width:270px; height:390px; color:#fff; background:url(images/loginBg.png) no-repeat; position:absolute; top:54px; left:67%; padding:25px;}
.login_front .t{padding:0 20px; text-align:center;}
.login_front .m{padding:20px; text-align:center;}
.login_front .g{padding:0 20px; text-align:center; color:#fff; }
.login_front .btn_orange{ text-align:center; width:270px; height:43px; display:block; font-size:20px; line-height:43px; margin-top:15px;}
.login_front .orange{ color:#fff; font-weight:700;  display:block; text-align:center; margin-top:15px;}
.login_front .orange:hover{ text-decoration:underline; color:#fff;}
.btn_orange { background-color: #e74509;border: 1px solid #e74509;color: #fff;}
.btn_orange:hover{ color:#fff;}
.fs_18 {font-size: 16px; font-weight:600;}
.fs_24 {font-size: 24px; font-weight:500; letter-spacing:1px;}
.fs_60 {font-size: 60px; font-weight:500;}
</style>
<script type="text/javascript" src="js/jquery.scrollify.min.js"></script>
<script type="text/javascript">
if (!navigator.userAgent.match(/mobile/i)) {
$(function() {
$.scrollify({
section: '.index-main'
});
});
}
</script> 

<script type="text/javascript">
	var geo_protocol = (location.protocol == "https:");
	document.write('<script type="text/javascript" async="async" src="'+ (geo_protocol ? "https://sslcdn.geotmt.com" : "http://ga.istreamsche.com") +'/CPA/GEO_REACH.js"></scri'+'pt>');
</script>

</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="index_main w1180 front">
	<div class="login_front">
    	<p class="fs_24 bold m" ><img src="images/zg.png" /></p>
       <p class="fs_60 bold g"><img src="images/13.png" /><i style="font-size:40px; position:relative; top:10px;">%</i></p>
    	<p class="fs_24 bold m">引入第三方资金托管</p>
    	<p class="fs_18 t">安全、放心、注册有好礼</p>
         	<a class="btn_orange" href="cellPhonereginit.do"  title="免费注册">免费注册</a>
         	<a href="login.do" class="orange"  title="马上登录">已有账号？马上登录</a>
    </div>
</div>
<div class="s_indexmain index-main">
    <div class="s_banner">
        <div class="banner-scroll">
            <div class="banner-prev"></div>
            <div class="banner-next"></div>
            <ul class="clearfix">
	            <s:if test="#request.bannerList!=null">
		            <s:iterator value="#request.bannerList" status="s" var="banner">
		        		 <li><a href="${banner.companyURL }" target = "_blank"><img src="${banner.companyImg }" width="2000" height="484" /></a></li>
		        	</s:iterator>
	        	</s:if>
	        	<s:else>
	                <li><a href="#"><img src="images/s_pic01.png" width="2000" height="484" /></a></li>
	                <li><a href="#"><img src="images/s_pic01.png" width="2000" height="484" /></a></li>
	                <li><a href="#"><img src="images/s_pic01.png" width="2000" height="484" /></a></li>
	                <li><a href="#"><img src="images/s_pic01.png" width="2000" height="484" /></a></li>
	                <li><a href="#"><img src="images/s_pic01.png" width="2000" height="484" /></a></li>
                </s:else>
            </ul>
        </div>
       <!-- <div class="s_banner-floatbg"></div>-->
        <!--<div class="s_banner-float">
            <img src="images/s_pic03.png" width="560" height="46" />
            <a href="cellPhonereginit.do">我要注册</a>
            <span> <font style="position:relative; top:1px;">5</font> 倍定期存款收益</span>
            <span>30倍活期存款收益</span>    
        </div>-->
    </div>
    <div class="s_csrollnews clearfix">
			<strong>官方公告:</strong>
			<div class="scroll-area">
				<ul>
					<s:iterator value="#request.newsList" var="bean">
                    	<li><a href="frontNewsDetails.do?id=${bean.id }" id="infotitle" title="${bean.title }" target="_blank"  ><shove:sub value="title" size="40" /></a>
                    	<span><s:date name="publishTime" format="MM-dd"/></span></li>
                </s:iterator>
				</ul>
			</div>
			<a href="getMessageBytypeId.do?typeId=35" class="more-btn">更多公告</a>
		</div>
    <div class="s_index-main1 index-main">
        <div class="main1-cont1 clearfix">
            <dl class="dl1">
                <dt>低门槛 高收益</dt>
                <dd>100元起轻松理财，预期稳定年化收益最高12.5%</dd>
            </dl>
            <dl class="dl2">
                <dt>重风控 全保障</dt>
                <dd>专业风控，5重保障、9层过滤为财富护航</dd>
            </dl>
            <dl class="dl3">
                <dt>多产品 好灵活</dt>
                <dd>长短期项目自由选择，灵活转让，随时变现</dd>
            </dl>
        </div>
        <div class="main1-cont2 clearfix">
            <div class="pro-tab">
                <ul>
                    <li class="li1"><a href="finance.do?m=1&type=6"><img src="images/s_pic07.png" /></a> <strong><big>6%</big></strong><span class="s-ico"></span></li>
                    <li class="li2"><strong><big>1</big>个月</strong><span>100元起投</span></li>
                    <li class="li3"><span class="span1">已投金额：<big>
                    <s:if test="#request.huoyueAmount!=null"><fmt:formatNumber pattern="0.00" value="${request.huoyueAmount}"></fmt:formatNumber></s:if><s:else>0</s:else></big>元</span><span class="span2">加入人数：<big><s:property value="#request.huoyueRenshu" default="0"/></big>人</span></li>
                </ul>
                <a href="finance.do?m=1&type=6" class="join-btn">立即加入</a>
            </div>
            <div class="pro-tab">
                <ul>
                    <li class="li1">
                    	<a href="finance.do?m=1&type=4"><img src="images/s_pic08.png" /></a> 
                        <strong>最高<big>12.5%</big></strong>
                        <span class="s-ico"></span>
                    </li>
                        <li class="li2">
                        <strong><big>3</big>个月<big>6</big>个月<big>12</big>个月</strong>
                        <span>1000元起投</span>
                    </li>
                    <li class="li3">
                    	<span class="span1">已投金额：<big>
                    	<s:if test="#request.dingxiAmount!=null"><fmt:formatNumber pattern="0.00" value="${request.dingxiAmount}"></fmt:formatNumber></s:if><s:else>0</s:else></big>元</span>
                        <span class="span2">加入人数：<big><s:property value="#request.dingxiRenshu" default="0"/></big>人</span>
                    </li>
                </ul>
                <a href="finance.do?m=1&type=4" class="join-btn">立即加入</a>
            </div>
        </div>
    </div>
    <div class="s_index-main2bg index-main">
    	<div class="s_index-main2">
            <dl class="main2-title">
                <dt>理财优选 灵活自主</dt>
                <dd>
                    <!-- <span>自主选标&nbsp;&nbsp;&nbsp;&nbsp;自动投标</span> -->
                    
                    <span>还本付息&nbsp;&nbsp;&nbsp;&nbsp;月月都有现金流</span>
                    <span>100元即可开启理财之旅</span>	
                </dd>
            </dl>
            <ul class="s_protab-option">
            	<li class="li1 clicked"><span></span>优选直投</li>
                <li class="li2"><span></span>交易宝</li>
            </ul>
            <div class="s_protabs">
            	<ul class="clearfix">
            		<s:if test="#request.mapList.size>0">
            			<s:iterator value="#request.mapList" var="finance">
            				<s:if test="%{#finance.borrowWay ==3}">
            				<li>
		                        <h3>
		                            <b><a href="financeDetail.do?id=${finance.id}">${finance.borrowTitle}</a></b><span></span>
		                        </h3>
		                        <p class="p1">
		                            <span class="span1 orange"><strong><s:property value="#finance.annualRate" default="0"/>%</strong></span>
		                            <span class="span2 orange"><strong><s:property value="#finance.deadline" default="0"/>
                        <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                        <s:else>天</s:else></strong></span>
		                            <span class="span3"><strong><s:property value="#finance.borrowAmount" default="0"/></strong></span>
		                        </p>
		                        <div class="clearfix">
		                            <div class="s_jindu">
		                                <s:iterator value="{10,20,30,40,50,60,70,80,90,100}" id="number">
									<s:if test="%{#finance.schedules>=#number}"><span class="done"></span></s:if>
									<s:else><span></span></s:else>
								</s:iterator>
		                            </div>
		                            <span class="s_jindu-num">完成进度<b><s:property value="#finance.schedules" default="0"/>%</b></span>
		                           
		                            <s:if test="%{#finance.borrowStatus == 1}">
							             <a class="join-btn">初审中</a>
							        </s:if>
							         <s:elseif test="%{#finance.borrowStatus == 2}">
							         
							         	<s:if test="%{#finance.borrowShow == 2}"><a class="join-btn" href="financeDetail.do?id=${finance.id}">加入</a></s:if>
							         	  <s:else><a class="join-btn" href="financeDetail.do?id=${finance.id}">加入</a></s:else>
							         
							        </s:elseif>
							        <s:elseif test="%{#finance.borrowStatus == 3}">
							           <a class="join-btn" >复审中</a>&nbsp;
							        </s:elseif>
							        <s:elseif test="%{#finance.borrowStatus == 4}">
							        
							        	<s:if test="%{#finance.borrowShow == 2}"><a class="join-btn" >回购中</a></s:if>
							          	<s:else><a class="join-btn" >还款中</a></s:else>&nbsp;
							        </s:elseif>
							        <s:elseif test="%{#finance.borrowStatus == 5}">
							           <a class="join-btn">已还完</a>&nbsp;
							        </s:elseif>
							        <s:else>
							              <a class="join-btn">流标</a>&nbsp;
							        </s:else>
		                         </div>
		                    </li>
		                    </s:if>
            			</s:iterator>
            		</s:if>
            		<s:else>
                    	<%--<div class="s_protabs pro-sellout"><a href="#" class="other-btn">请关注其他产品</a></div>
                	--%></s:else>
                </ul>
                <p class="more-pros"><a href="finance.do?m=1&type=3">更多优选项目</a></p>
            </div>
			<!--没有项目显示该div
				<div class="s_protabs pro-sellout">
					<a href="#" class="other-btn">请关注其他产品</a>
				</div>
			-->
            <div class="s_protabs" style="display:none;">
            <s:if test="#request.debtList.size>0">
            	<ul class="clearfix">
            		
            			<s:iterator value="#request.debtList" var="finance">
            				<li>
		                        <h3>
		                            <b><a href="queryDebtDetail.do?id=${id}" target="" >${finance.borrowTitle}</a></b><span></span>
		                        </h3>
		                        <p class="p1">
		                            <span class="span1 orange"><strong><s:property value="#finance.annualRate" default="0"/>%</strong></span>
		                            <span class="span2 orange"><strong><s:property value="#finance.deadline" default="0"/>
				                        <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
				                        <s:else>天</s:else></strong>
                                     </span>
		                            <span class="span3"><strong>${debtSum}</strong></span>
		                        </p>
		                        <p class="p2">
		                            <span class="span1">剩余时间：<strong>${remainDays}</strong></span>
		                             <!--<span class="span2">竞拍底价：<strong>100,000.00</strong></span>-->
									<s:if test="#finance.debtStatus ==1"><a href="queryDebtDetail.do?id=${id}" target="" class="join-btn">转让中</a></s:if>
										    	<s:elseif test="#finance.debtStatus==2"><a class="join-btn" href="queryDebtDetail.do?id=${id}" target="" >加入</a></s:elseif>
										    	<s:elseif test="#finance.debtStatus==3"><a class="join-btn">已完成</a></s:elseif>
		                        </p>
		                    </li>
            			</s:iterator>
                </ul>
                </s:if>
            		<s:else>
                    	<div align=center><img src="images/s_pic50.png"/></div>
                	</s:else>
                <p class="more-pros"><a href="finance.do?m=1&type=7">更多转让项目</a></p>
            </div>
			<!--没有项目显示该div
				<div class="s_protabs pro-sellout">
					<a href="#" class="other-btn">请关注其他产品</a>
				</div>
			-->
        </div>
    </div>
    <div class="s_index-main3 index-main">
        <div class="main3-cont">
            <ul class="clearfix">
                <li>
					<h3 class="main3-title">普惠金融的倡导者,小微金融的实践者</h3>
					<p class="main3-title">微信贷-江川金融微型信用贷款</p>
                    <img src="images/1.png" width="2000" height="437" />
                </li>
                 <li>
					<h3 class="main3-title">小微信贷的风控专家</h3>
					<p class="main3-title">微信贷-江川金融微型信用贷款</p>
                    <img src="images/2.png" width="2000" height="437" />
                </li>
                 <li>
					<h3 class="main3-title">最贴心的金融理财助手</h3>
					<p class="main3-title">微信贷-江川金融微型信用贷款</p>
                    <img src="images/3.png" width="2000" height="437" />
                </li>
            </ul>
        </div>
        <ul class="main3-control clearfix">
        	<li class="hover"></li>
            <li></li>
            <li></li>
        </ul>
    </div>
    <div class="s_index-main4bg index-main">
    	<div class="s_index-main4">
        	<h3 class="main4-title">媒体报道</h3>
            <p class="main4-title"></p>
            <div class="news-cont">
                <ul class="news-list clearfix">
                <s:if test="#request.meikuStick.size>=1">
                        <s:iterator value="#request.meikuStick" var="stick" status="stst">
                            <s:if test="#stst.index>=0">
                                <li class="li1">
			                        <a class="news-pic" href="${url}" target="_blank"><img src="${imgPath}" /></a>
			                        <dl>
			                            <dt><a href="frontMedialinkId.do?id=${id}"  title="${title }" target="_blank"><shove:sub value="title" size="16" suffix="..."/></a></dt>
			                            <dd><a href="frontMedialinkId.do?id=${id}"  title="${title }" target="_blank"></a></dd>
			                        </dl>
			                    </li>
                            </s:if>
                        </s:iterator>
                    </s:if>
                </ul>
                <a href="getMessageBytypeId.do?typeId=34" class="more-news">更多报道</a>
            </div>
            <ul class="out-links clearfix">
           	<h3 class="main4-title">合作机构</h3>
            <p class="main4-title"></p>
            <div style="width:980px;">
            <ul>	
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic25.png" /></a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic27.png" /></a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic28.png" /></a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic29.png" /></a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic30.png" /></a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic031.png" /></a></li>   
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic033.png" /></a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="images/s_pic035.png" /></a></li>                
            </ul>
            </div>
            </ul>
            
        </div>
        <a href="#" class="go-top">TOP</a>
    </div>
</div>
<!-------------主体 end----------->

<div class="footer">
    <jsp:include page="/include/footer.jsp"></jsp:include>
</div>
<!-- 引用底部公共部分-->
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
//===验证数据

$(function(){
	$('#slides').slides({
		preload: true,
		preloadImage: 'images/loading.gif',
		play: 4000,
		pause: 2500,
		hoverPause: true
	});
	if('${request.bannerList}' != ''){
		//document.getElementById("imgs1").src='${imgs0}';
	}else{
		document.getElementById("img1").src='images/baner_16.jpg';
	}
	dqzt(0);
		var tim;
		$("#scrollleft ul").css("width",$("#scrollleft li").length*125);
		function scrooatu(){
			clearTimeout(tim);
		$("#scrollleft").animate({scrollLeft:$("#scrollleft").scrollLeft()+122},"slow",function(){
			$("#scrollleft").animate({scrollLeft:0},0);
			$("#scrollleft li:first").appendTo($("#scrollleft ul"));
			});
		
		tim = setTimeout(scrooatu,3000);
		}
		scrooatu();
		$("#code").bind('keyup', function(event){
			   if (event.keyCode=="13"){
			      login();  
			   }
			});	
		$("#scrollleft").hover(function(){
			clearTimeout(tim);
		},
		function(){
			setTimeout(scrooatu,1000);
		});
	
	});
		 function checkTime(num){			
			 var param = {};
			 
			 param["paramMap.number"] = num;
			 $.post("investRank.do",param,function(data){
					$("#touzib").html(data);
					var index;
					$("#touzib li").each(function(){
						index = $(this).index();
						$(this).find(".top_loan_bg").text(index+1);
						if(index==0){
							$(this).find(".top_loan_bg").addClass("top1").removeClass("top_rest");
							}	
						if(index==1){
							$(this).find(".top_loan_bg").addClass("top2").removeClass("top_rest");
							}
						if(index==2){
							$(this).find(".top_loan_bg").addClass("top3").removeClass("top_rest");
							}					
						});	
			});
			 var index;
				$("#touzib li").each(function(){
					index = $(this).index();
					$(this).find(".top_loan_bg").text(index+1);
					if(index==0){
						$(this).find(".top_loan_bg").addClass("top1").removeClass("top_rest");
						}	
					if(index==1){
						$(this).find(".top_loan_bg").addClass("top2").removeClass("top_rest");
						}
					if(index==2){
						$(this).find(".top_loan_bg").addClass("top3").removeClass("top_rest");
						}					
					});	
		}
		 function  checkTou(id,type){
			 var param = {};
			 param["id"] = id;
		     $.shovePost('financeInvestInit.do',param,function(data){
			   var callBack = data.msg;
			   if(callBack !=undefined){
			     alert(callBack);
			   }else{
				   if(type==2){
					 		var url = "subscribeinit.do?borrowid="+id;
					     	 $.jBox("iframe:"+url, {
						    		title: "我要购买",
						    		width: 450,
						    		height: 450,
						    		buttons: {  }
						    		});
					}else{
						 window.location.href= 'financeInvestInit.do?id='+id;
				   }
			   }
			 });
		 }
		  function closeMthod(){
				window.jBox.close();
		    	window.location.reload();
		    }
</script>

<script type="text/javascript">
	$(function(){
		<%--
			var self=$("#scroll ul")
			var sd=null;
			var leng=parseInt($("#scroll ul").css("width",self.find("li").length*459).css("width"));
			$("#scroll ul").css("width",self.find("li").length*459);
				function scrollatuo(){
				clearTimeout(sd);
				if($("#scroll").scrollLeft()>=leng-490){
					$("#scroll").animate({scrollLeft:0},"slow");
					$(".control a").removeClass("cur")
					$(".control a:first").addClass("cur")
					sd=setTimeout(scrollatuo,3000);
				}
				else{
				$("#scroll").animate({scrollLeft:$("#scroll").scrollLeft()+459},"slow",function(){
						var insd=parseInt($("#scroll").scrollLeft()/459);
				$(".control a").removeClass("cur")
				$(".control a").eq(insd).addClass("cur")
				});}		
				sd=setTimeout(scrollatuo,3000);
			
				}
			sd=setTimeout(scrollatuo,1000);
			//控制区
			$(".control a").hover(function(){
				var sf=$(".control a").index(this);
				$("#scroll").animate({scrollLeft:459*sf},"slow");
				clearTimeout(sd);
				$(".control a").not(this).removeClass("cur")
				$(this).addClass("cur");
			},function(){
					sd=setTimeout(scrollatuo,3000);	
			});
			//选项卡
			$("#second-tal li").click(function(){
			var sd=$("#second-tal li").index(this);
		
			$(".job-list").hide();
			$(".job-list").eq(sd).show();
			$("#second-tal li").removeClass("cur");
			$(this).addClass("cur");
			});
			--%>
			handleSlider();
			 function handleSlider(){
		            var $slider = $("#slider_ul").children(); 
					var $lights = $("#slider_lights").children();
		            var index = 1;    
		            var light_index = 0;
		            var wrap_width = $slider.outerWidth();			
		            $slider.parent().css("width",$slider.length*100+"%"); //设置图片div宽度
					var idx;			
					//动态添加指示灯
		            $slider.each(function(){					
						if($lights.length==0){
							 idx = $(this).index()+1;
							$("#slider_lights").append("<a>"+idx+"</a>");
						}				
		            });  			
				   $("#slider_lights a").first().addClass("lights_active");	//默认第一个指示灯亮起		   
					//自动播放			
					var scroll = function(){
						<%--
						if('${request.bannerList}' != ''){
							if('${imgs1}' != ''){
		            			document.getElementById("imgs2").src='${imgs1}';
		            		}
		            		if('${imgs2}' != ''){
		            			document.getElementById("imgs3").src='${imgs2}';
		            		}
		            		if('${imgs3}' != ''){
		            			document.getElementById("imgs4").src='${imgs3}';
		            		}
						}else{
							document.getElementById("img2").src="images/baner_16.jpg";
							document.getElementById("img3").src="images/baner_16.jpg";
							document.getElementById("img4").src="images/baner_16.jpg";
						}
						--%>
						if(index <=$slider.length-1){					
							$slider.parent().animate({left:"-"+index*wrap_width+"px"},"slow");//左右滑动
							$("#slider_lights a").eq(index).addClass("lights_active").siblings().removeClass("lights_active");					
						}else{
							index = 0;
							$("#slider_lights a").first().addClass("lights_active").siblings().removeClass("lights_active");	
							$slider.parent().animate({left:"-"+index*wrap_width+"px"},"slow");//左右滑动							
						}					
						index+=1;
					};
					var autoPlay = setInterval(scroll,3000);			
					//点击指示灯切换图片		
		            $("#slider_lights a").click(function(){ 
		            	<%--
		            	if('${request.bannerList}' != ''){
		            		if('${imgs1}' != ''){
		            			document.getElementById("imgs2").src='${imgs1}';
		            		}
		            		if('${imgs2}' != ''){
		            			document.getElementById("imgs3").src='${imgs2}';
		            		}
		            		if('${imgs3}' != ''){
		            			document.getElementById("imgs4").src='${imgs3}';
		            		}
		            	}else{
		            		document.getElementById("img2").src="images/baner_16.jpg";
		            		document.getElementById("img3").src="images/baner_16.jpg";
		            		document.getElementById("img4").src="images/baner_16.jpg";
		            	}
		            	--%>
						clearInterval(autoPlay);
		                light_index = $(this).index();   
		                $(this).addClass("lights_active").siblings().removeClass("lights_active");
		                $slider.parent().animate({left:"-"+light_index*wrap_width+"px"},"800");//左右滑动				
						index = light_index;
						autoPlay = setInterval(scroll,"2000");
		            }); 
					//鼠标悬停			
					$(".slider").hover(function(){
						clearInterval(autoPlay);
					},function(){
						autoPlay = setInterval(scroll,"3000");
					});		
		        };         
					
		});
</script>
</body>
</html>
