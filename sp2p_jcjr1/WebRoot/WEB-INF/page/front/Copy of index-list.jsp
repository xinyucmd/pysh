<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/Site2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-2.0.js"></script>
<script type="text/javascript" src="script/front.js"></script>
<!--<link rel="stylesheet" type="text/css" href="css/site.css" />-->
</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="slider-wrap">
    <div class="banner">
	<div id="container">
    	<div id="example">
    		<div id="slides">
    		<s:if test="#request.bannerList!=null">
    		 <s:iterator value="#request.bannerList" status="s" var="banner">
        		 <div class="slides_container"> 
                   <!--  <div class="prolist"> <img src="" width="1920" height="356" alt="Slide 1" id="imgs<s:property value="#s.count"/>"> </div> -->
                    <div class="prolist"><a  href="${banner.companyURL }" target="_blank"> <img src="${banner.companyImg }" width="1920" height="356"  alt="Slide 1"></img></a> </div>
        		 </div> 
        	</s:iterator>
        	</s:if>
        	<s:else>
        		<div class="slides_container">
                    <div class="prolist"> <img src="images/baner_16.jpg" width="1920" height="356" alt="Slide 1"  > </div>
                    <div class="prolist"> <img src="images/baner_16.jpg" width="1920" height="356" alt="Slide 1"  > </div>
                    <div class="prolist"> <img src="images/baner_16.jpg" width="1920" height="356" alt="Slide 1"  > </div>
                    <div class="prolist"> <img src="images/baner_16.jpg" width="1920" height="356" alt="Slide 1"  > </div>
        		</div>
        	</s:else>
        		<div class="arrowbox"><a href="#" class="prev"></a> <a href="#" class="next"></a></div>
      		</div>
    	</div>
	</div>
    <div class="cle"></div>
    <div class="ban_c">
    	<div class="llogin">
    	<s:if test="%{#session.user==null}">
        	<ul>
            	<li><i></i><span>用户登录</span><a href="reg.do">免费注册</a></li>
            	<li><div class="text_k1"><span></span><input type="text" value="请输入用户名" class="index_yhm" name="paramMap.email"
											id="email"/>
										</div></li>
            	<li><div class="text_k2"><span></span><input type="password" class="pass_oo" name="paramMap.password"
											id="password"/><label id="index_mm">请输入密码</label></div></li>
            	<li><div class="text_k3"><input type="text" class="inp100" name="paramMap.code"
											id="code" />
											<span><img src="${sitemap.adminUrl}/imageCode.do?pageId=userlogin" title="点击更换验证码"
											style="cursor: pointer;" id="codeNum" width="73" height="31"
											onclick="javascript:switchCode()" />
											</span><a href="forgetpassword.do" class="login_tab_a2">忘记密码？</a></div></li>
                <li><input type="button" value="登录" class="login_tab_but" id="btn_login" onclick="login();" /></li>
            </ul>
            </s:if>
            <s:else>
            <div class="ll_loginl">
                	<h3>欢迎使用${sitemap.siteName}</h3>
                    <p>你当前正在使用的帐号：</p>
                    <p>用户名：<b>${user.userName}</b></p>
                    <p>账户余额：<b><fmt:formatNumber pattern="#,##0.00#"	value="${paramMap.usableSum}" />元<br /></b></p>
                    <input type="button" value="管理我的账户" class="login_tab_but" onclick="window.location.href='home.do'"/>
                    <ul>
                    	<li><a href="finance.do">投资</a></li>
                    	<li class="li_ilaa"><a href="rechargeInit.do">充值</a></li>
                    	<li><a href="withdrawLoad.do">提现</a></li>
                    </ul>
                </div>
                </s:else>
        </div>
    </div>
    <div class="cle"></div>
</div>
</div>
<div class="clear"></div>
<div class="content">
    <div class="main_content">
        <div class="loan_bar"><span class="loan_list_bar">最新借款列表</span><span class="loan_more"><a href="finance.do">更多>></a></span>
            <div class="clear"></div>
        </div>
        <div class="">
            <div class="list_main2">
            <ul id="lastestborrow">
                <s:if test="#request.mapList.size>0">
                    <s:iterator value="#request.mapList" var="finance">
                        <li>
                            <s:if test="%{#finance.borrowStatus == 1}">
                                <div class="bottm2">初审中</div>
                            </s:if>
                            <s:elseif test="%{#finance.borrowStatus == 2}">
                                <div class="bottm">
                                    <s:if test="%{#finance.borrowShow == 2}"><a href="javascript:void(0);"  onclick="checkTou(${finance.id},2)">立即认购</a></s:if>
                                    <s:else><a href="javascript:void(0);"  onclick="checkTou(${finance.id},1)">立即投标</a></s:else>
                                </div>
                            </s:elseif>
                            <s:elseif test="%{#finance.borrowStatus == 3}">
                                <div class="bottm2">复审中</div>
                                  </s:elseif>
                            <s:elseif test="%{#finance.borrowStatus == 4}">
                                <div class="bottm2">
                                    <s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
                                    <s:else>还款中</s:else>
                                </div>
                                  </s:elseif>
                            <s:elseif test="%{#finance.borrowStatus == 5}">
                                <div class="bottm2">已还完</div>
                                  </s:elseif>
                            <s:else>
                                <div class="bottm2">流标</div>
                                  </s:else>
                            <div class="list_tx"><a href="financeDetail.do?id=${finance.id}" target="_blank" >
                                <shove:img src="${finance.imgPath}" defaulImg="images/hslogo_42.jpg" width="80" height="79"></shove:img>
                                </a></div>
                            <div class="list_txt">
                            <table width="600" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <th colspan="3" align="left"> <a href="financeDetail.do?id=${finance.id}" target="_blank" >
                                        <shove:sub value="#finance.borrowTitle" suffix="..." size="15"/>
                                        </a>
                                        <s:if test="#finance.borrowWay ==1"> <img src="images/neiye1_53.jpg" width="15" height="16" title="净值借款" /> </s:if>
                                        <s:if test="#finance.borrowWay ==2"> <img src="images/neiye1_55.jpg" width="15" height="16" title="秒还借款"  /> </s:if>
                                        <s:if test="#finance.borrowWay ==4"> <img src="images/tubiao2.png" title="实地考察借款"/> </s:if>
                                        <s:if test="#finance.borrowWay ==5"> <img src="images/tubiao1.png" title="机构担保借款" /> </s:if>
                                        <s:if test="#finance.isDayThe ==2"> <img src="images/neiye1_67.jpg" width="15" height="16" title="天标" /> </s:if>
                                        <s:if test="#finance.hasPWD ==1"> <img src="images/lock.png" width="15" height="16" title="投标时需要填写密码" /> </s:if>
                                        <s:if test="#finance.auditStatus ==3"> <img src="images/neiye1_62.jpg" width="15" height="16" title="该用户通过抵押认证"/> </s:if>
                                        <s:if test="#finance.excitationType==2"> <span class="list_txtjl"><span>￥${finance.excitationSum }</span></span> </s:if>
                                        <s:if test="#finance.excitationType==3"> <span class="list_txtjl"><span>${finance.excitationSum }%</span></span> </s:if>
                                    </th>
                                </tr>
                                <tr>
                                    <td width="166">借款金额：<span >￥
                                        <s:property value="#finance.borrowAmount" default="0"/>
                                        </span></td>
                                    <td width="214">年利率：<span >￥
                                        <s:property value="#finance.annualRate" default="0"/>
                                        %</span></td>
                                    <td width="220">借款期限：<span >
                                        <s:property value="#finance.deadline" default="0"/>
                                        <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                                        <s:else>天</s:else>
                                        </span></td>
                                </tr>
                                <tr>
                                    <td>信用等级：<img src="images/ico_<s:property value="#finance.credit" default="---"/>.jpg" title="
                                        <s:property value="#finance.creditrating" default="0"/>
                                        分" /></td>
                                    <td><div>
                                            <div style="float:left;">借款进度：</div>
                                            <div class="progeos">
                                                <div style="width:<s:property value="#finance.schedules" default="0"/>%"></div>
                                        </div>
                                        <div style="float:left;"><span>
                                            <s:property value="#finance.schedules" default="0"/>
                                            %</span></div>
                                </div>
                                </td>
                                
                                <td>还需：<span >
                                        <s:property value="#finance.investNum" default="---"/>
                                        </span></td>
                                </tr>
                            </table>
                        </div>
                        <div style="clear:both"></div>
                        </li>
                    </s:iterator>
                </s:if>
                <s:else>
                    <li style="text-align: center;">没有数据</li>
                </s:else>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="aside">
    <div class="ofical_ad">
        <div class="of_ad_bar"> <span class="left"><img src="images/aside_bar.jpg" align="absmiddle"/>&nbsp;<span class="aside_title">官方公告</span></span> <span class="right"><a href="getMessageBytypeId.do?typeId=35">更多>></a></span> <span class="clear"></span> </div>
        <div class="ofical_content">
            <ul>
                <s:iterator value="#request.newsList" var="bean">
                    <li style="overflow : hidden"> <span class="gfgg_left"><a href="frontNewsDetails.do?id=${bean.id }" id="infotitle" title="${bean.title }" target="_blank"  >
                        <shove:sub value="title" size="14" />
                        </a> </span> <span class="gfgg_right">
                        <s:date name="publishTime" format="MM-dd"/>
                        </span> </li>
                </s:iterator>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <div class="ofical_ad">
        <div class="of_ad_bar"> <span class="left"><img src="images/aside_bar.jpg" align="absmiddle"/>&nbsp;<span class="aside_title">媒体报道</span></span> <span class="right"><a href="getMessageBytypeId.do?typeId=34">更多>></a></span> </div>
        <div class="ofical_content">
            <ul>
                <li class="mtbd">
                    <s:if test="#request.meikuStick.size>0">
                        <s:iterator value="#request.meikuStick" var="stick" status="sts" >
                            <s:if test="%{#sts.index<1}"> <a href="frontMedialinkId.do?id=${id}" target="_blank" title="${title }">
                                <h3>
                                    <shove:sub value="title" size="16" suffix="..."/>
                                </h3>
                                </a> </s:if>
                        </s:iterator>
                    </s:if>
                    <!--  <div class="foucs-describle" >
    	<s:if test="#request.meikuList.size>0">
    	<s:iterator value="#request.meikuList" var="meiti" status="s">
    	<s:if test="#s.index+1<3">
      <a id="meiti" title="${meiti.title }" href="frontMedialinkId.do?id=${meiti.id}" target="_blank" >
       [<shove:sub value="title" size="18" suffix="..."/>] 
      </a><br/><div style="3px;"></div>
      </s:if>
      </s:iterator>
      </s:if>
      </div>
       -->
                </li>
                <li  class="mtbd">
                    <s:if test="#request.meikuStick.size>=1">
                        <s:iterator value="#request.meikuStick" var="stick" status="stst">
                            <s:if test="#stst.index>=1"> <a href="frontMedialinkId.do?id=${id}" target="_blank" title="${title }">
                                <h3>
                                    <shove:sub value="title" size="16" suffix="..."/>
                                </h3>
                                </a> </s:if>
                        </s:iterator>
                    </s:if>
                    <!--  <div class="foucs-describle">
      <s:if test="#request.meikuList.size>0">
    	<s:iterator value="#request.meikuList" var="meiti" status="s">
       <s:if test="#s.index+1>=3">
     <a id="meiti2" title="${meiti.title }"  href="frontMedialinkId.do?id=${meiti.id}" target="_blank" >
     [<shove:sub value="title" size="10" suffix="..."/>]
     </a>
     </s:if>
      </s:iterator>
      </s:if> </div>
       -->
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <div class="ofical_ad">
        <div class="of_ad_bar">
            <ul class="top_loan" id="top_loan">
                <li class="top_loan_hover"  id="touzi_now" onclick="javascript:checkTime(0)"> 全部</li>
                <li  id="touzi_year"  onclick="javascript:checkTime(1)">年</li>
                <!--<li  id="touzi_quarter"  onclick="javascript:checkTime(2)">季</li>  -->
                <li  id="touzi_month" onclick="javascript:checkTime(3)"> 月</li>
                <li  id="touzi_week"  onclick="javascript:checkTime(4)">周</li>
                <li   id="touzi_year" > 排行榜</li>
                <%--<li   id="touzi_year" onclick="javascript:checkTime(5)"> 日</li>
      	--%>
            </ul>
        </div>
        <div class="all_content">
            <!--全部-->
            <div class="top_content" id="top_loan_list">
                <ul class="ofical_ul" id="touzib">
                    <s:if test="#request.rankList!=null || #request.rankList.size>0">
                        <s:iterator value="#request.rankList" var="rank" status="step">
                            <li> <span class="ofi_title3"> <a href="#"><span class="top_loan_bg top_rest"></span> <span class="top_name">${rank.username}</span><span class="top_count">￥${rank.sumMoney }</span> </a> </span> </li>
                        </s:iterator>
                    </s:if>
                    <s:else>
                        <li style="text-align: center;">暂无排名</li>
                    </s:else>
                </ul>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
				$(function(){
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
			</script>
    <div class="ofical_ad">
        <div class="of_ad_bar"> <span class="left"><img src="images/aside_bar.jpg" align="absmiddle"/>&nbsp;<span class="aside_title">新手指引</span></span> </div>
        <div class="ofical_content2">
            <div class="guide_first center"> <a href="reg.do"><img src="images/guide_reg.jpg" /></a> </div>
            <div  class="guide_next center"> <img src="images/guide_next.jpg" /> </div>
            <div  class="guide_second center"> <a href="rechargeInit.do"><img src="images/guide_cz.jpg" /></a> </div>
            <div  class="guide_next center"> <img src="images/guide_next.jpg" /> </div>
            <div  class="guide_third center"> <a href="finance.do"><img src="images/guide_tz.jpg" /></a> </div>
        </div>
    </div>
    <div class="fxbzj">
        <div class="bzj_bar"><img src="images/fxbzj.jpg" /></div>
        <div class="bzj">
            <div class="left bzj_img"> <img src="images/baozj.jpg" /> </div>
            <div class="left bzj_text">
                <div>本站风险保证金累计:</div>
                <div>
                    <s:property value="#request.totalRiskMap.total"/>
                    元</div>
                <div>当日支出:
                    <s:if test="#request.currentRiskMap.riskSpending !=''">${currentRiskMap.riskSpending}</s:if>
                    <s:else>0.00</s:else>
                </div>
                <div>当日收入:
                    <s:if test="#request.currentRiskMap.riskInCome !=''">${currentRiskMap.riskInCome}</s:if>
                    <s:else>0.00</s:else>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="clear"></div>
    <div class="imgs">
        <s:if test="%{#request.listsGGList!=null}">
            <s:iterator var="GGList" value="#request.listsGGList">
                <div style="margin-top:5px;">
                    <shove:img src="${GGList.companyImg }" defaulImg="images/banner/xiaobanner1.jpg" width="240" height="102"  cssclass="img" ></shove:img>
                </div>
            </s:iterator>
        </s:if>
        <s:else> <a href="#"> <img src="images/banner/xiaobanner1.jpg" class="img" width="240px" /></a> <br/>
            <a href="#"><img src="images/banner/xiaobanner2.jpg" class="img"  width="240px"/></a><br/>
        </s:else>
    </div>
</div>
<div class="clear"></div>
</div>
<div class="cooperators">
    <div class="left cooper_img"><img src="images/cooperators.jpg" /></div>
    <div class="left coopers" id="scrollleft">
        <ul class="coopers_ul">
            <s:if test="#request.linksList.size>0">
                <s:iterator var="links" value="#request.linksList">
                    <li><a href="${companyURL}" target="_blank" >
                        <shove:img src="${links.companyImg }" defaulImg="images/bank_11.jpg" width="101px;" height="41px;"></shove:img>
                        </a></li>
                </s:iterator>
            </s:if>
        </ul>
    </div>
    <div class="clear"></div>
</div>
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
		document.getElementById("img1").src="images/baner_16.jpg";
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
