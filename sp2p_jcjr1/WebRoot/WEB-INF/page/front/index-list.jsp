<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
<head> 
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
<meta http-equiv="keywords" content="微信贷|江川金融微信贷|网络理财| P2P理财|P2P网贷|个人小额理财|P2P借款|互联网金融">
<meta http-equiv="description" content="微信贷，基于互联网平台和风险管理技术，为小微企业和个人提供 微小数额 的资金融通平台品牌，其核心含义是 微型信用贷款。">
<script>
var browser = {
        versions: function () {
            var u = navigator.userAgent, app = navigator.appVersion;
            console.log(u);
            return {//移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/)
                || !!u.match(/AppleWebKit/), //是否为移动终端
                //ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1,//是否web应该程序，没有头部与底部
                google: u.indexOf('Chrome') > -1,
                weixin:u.match(/MicroMessenger/i)=="MicroMessenger"
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    };
    
    console.log("language"+browser.language);
    console.log('是否为移动端'+ browser.versions.mobile);

</script>

</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>

<!--banner-->
<div class="banner">
   	<div class="main_visual">
   		<div class="flicking_con">
      		<div class="flicking_inner">
      			<s:if test="#request.bannerList!=null">
		            <s:iterator value="#request.bannerList" status="s" var="banner" >
		        		<a href="javascript:">${s.count}</a>
		        	</s:iterator>
	        	</s:if>
	        	<s:else>
	        		<a href="javascript:">1</a>
	                <a href="javascript:">2</a>
	                <a href="javascript:">3</a>
	                <a href="javascript:">4</a>
	                <a href="javascript:">5</a>
	        	</s:else>
            </div>
        </div>
        <div class="main_image">
            <ul>
            	<s:if test="#request.bannerList!=null">
            		<s:iterator value="#request.bannerList" status="s" var="banner" >
            			<li>
            				<a href="${banner.companyURL }" target = "_blank">
            					<span class="img_1" style="background: url('${banner.companyImg }') center top no-repeat"></span>
            				</a>
            			</li>
            		</s:iterator>
            	</s:if>
            	<s:else>
	                <li><span class="img_1"></span></li>
	                <li><span class="img_2"></span></li>
	                <li><span class="img_3"></span></li>
	                <li><span class="img_4"></span></li>
	                <li><span class="img_5"></span></li>
                </s:else>
            </ul>
            <a href="javascript:;" id="btn_prev"></a>
            <a href="javascript:;" id="btn_next"></a>
        </div>
	</div>
	<div class="login-box">
		<div class="login-main">
			<h5>微信贷为您的财富增值</h5>
			<h4>预期年化收益</h4>
			<p class="g"><img src="/new_images/12.png" /><i style="font-size:40px; position:relative; top:20px;">%</i></p>
           	<a href = "cellPhonereginit.do" class="btn-default f16">免费注册</a>
            <em class="login-bottom">已有账号,<a href="login.do">立即登录</a></em>
		</div>	
		<div class="login-bg"></div>	
	</div>
</div>

<div class="wrap">
<!--公告-->
<div style="height:130px; width:980px; margin:0 auto; background:#fff;">
<div class="describe">
            <div class="describe-con">
                <%--<a href="#">--%>
                    <dl class="describe-freshman">
                        <dt><a href="newPerson.do">新手引导</a></dt>
                        <dd><span class="icon describe-freshman-img"></span>1分钟理财 理财伴1生 微信贷帮你开启理财之路</dd>
                    </dl>
                <%--</a>--%>
                <%--<a href="#">--%>
                    <dl class="describe-statistics">
                        <dt><a href="bespeakInvest.do">预约投标</a></dt>
                        <dd>
                            <span class="icon describe-freshman-img-1"></span>用户可通过此通道跳转至即将发标页面
                        </dd>
                    </dl>
                <%--</a>--%>
            </div>
                    </div>
<!-- 公告 -->
<div style="position:relative;">
	<div style="position:absolute; z-index:1000; top:7px;"> <img src="/new_images/lab.png" ></div>
    <div id="content" class="infocontent">   
            <div id="top" class="infolist">  
                <ul>  
                  <s:iterator value="#request.newsList" var="bean">
                  <li>
                    <a href="frontNewsDetails.do?id=${bean.id }">${bean.title }</a>
                    <em><s:date name="publishTime" format="yyyy-MM-dd"/></em>
                    <a href="getMessageBytypeId.do?typeId=35" class="more">更多公告</a>  
                   </li> 
                   </s:iterator> 
                 
                </ul>
            </div>  
            <div id="bottom" class="infolist"></div>        
    </div>  
    </div>
</div>
<!--安全菜单-->
<div class="menu-safe">
    	<div class="media">
            <div class="media-left media-middle">
                <a href="#">
                 <img src="/new_images/big_icon1.png">
                </a>
            </div>
            <div class="media-body">
                <h4 class="media-heading">重风控&nbsp;&nbsp;全保障</h4>
                <i class="f13">专业风控、5重保障、9层过滤为财富</i>
                <p class="f32"><a href="capitalEnsure.do">保驾护航</a></p>
            </div>
   		</div>
        <div class="media ">
            <div class="media-left media-middle">
                <a href="#">
                 <img src="/new_images/big_icon2.png">
                </a>
            </div>
            <div class="media-body">
                <h4 class="media-heading">低门槛&nbsp;&nbsp;高收益</h4>
                <i class="f13">100元轻松理财&nbsp;&nbsp;预期年化收益</i>
                <p class="f32">5~10%</p>
            </div>
   		</div>
        <div class="media" style="background:none;">
            <div class="media-left media-middle">
                <a href="#">
                 <img src="/new_images/big_icon3.png">
                </a>
            </div>
            <div class="media-body">
                <h4 class="media-heading">多产品&nbsp;&nbsp;多灵活</h4>
                <i class="f13">长短期项目、自由选择、灵活转让</i>
                <p class="f32">随时变现</p>
            </div>
   		</div>
        <div class="clearfix"></div>
</div>

<div class="main">
<!-- 新手体验 -->
      <div class="diyaBox">
      <%--<a target="_blank" href="#">--%>
	      <div class="biao_tit position5" id="biao_tit2">
	       		<ul>
                	<li class="kk-f"><a href ="###">新手体验标</a></li>
                    <li>新手注册</li>
                    <li>送8888元体验金</li>
                </ul>
	      </div>
      <%--</a>--%>
      <div class="biao" id="biao2"> 
      		<div class="red-big"></div>
   		<div class="biao-line"><a href="queryTyjInvestDetail.do?borrowId=${tyjMap.id}"><h4>${tyjMap.name}</h4></a></div>
            <div class="biao-tip"><span class="span1">还款方式：到期付息</span><span class="span2">起投金额:8888.00元</span></div>
      	   		   <div class="biao_con">      
                        <div class="biao_con_block">
                            <ul class="clearfix item-list">
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="10.0">8%</b></em>
                                    <p>预期年化利率</p>
                                </li>
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="3">8</b> 天</em>
                                    <p>项目期限</p>
                                </li>
                                <li>
                                    <em class="c1"><b class="scrollNum" data-numtarget="180">${tyjMap.amount_sum/10000}万</b></em>
                                    <p>项目总额</p>
                                </li>  
                          </ul>
                   </div>
	              <div class="biao_btn">
                        <!-- <div class="cs-time">
                            <img src="/new_images/sz-icon.png"> <i class="cs-time-top">2015-10-19 15:00出售</i>
                        </div> -->
                        <div class="jindu-z">
                         <div class="jindu">
                        	<div class="jinduing" style="width:${tyjMap.amount}%"></div>
                         </div>
                         <p class="percent">${tyjMap.amount}%</p>
                         </div>
                        <div style="margin-top:20px;">
                          <s:if test="#request.tyjMap.amount<100">
                             <a href="queryTyjInvestDetail.do?borrowId=${tyjMap.id}" class="but_avg">马上投标</a>
                          </s:if>
                          <s:else>
                             <a href="###" class="but_gray">投标结束</a>
                          </s:else>
                           
                        </div>
	           </div>
	       </div>     
	      </div>
     </div>   
    <!-- 新手体验 -->
    <!-- 定息宝 -->
    <s:if test="#request.mapListB.size>0">
    <s:iterator value="#request.mapListB" var="maplistB">
    <div class="diyaBox">
      <%--<a target="_blank" href="#">--%>
	      <div class="position6 biao_tit " id="biao_tit2">
	       		<ul>
                	<li class="kk-f"><a href ="finance.do?m=1&type=4">定息宝</a></li>
                    <li>多种选择</li>
                    <li>长期收益</li>
                </ul>
	      </div>
      <%--</a>--%>
      <div class="biao" id="biao2"> 
      		<div class="biao-line">
      		  <a href="financeDetail.do?id=${maplistB.id}" style="display:inline-block;"><h4>${maplistB.borrowTitle }</h4>
      		      <s:if test="%{#maplistB.hasPWD !=-1}">
      		          <i class="yue-bg"><img src="/new_images/yue-icon.png" /></i>
      		      </s:if>
      		      <s:else>
      		            <s:if test="%{#maplistB.add_interest >0}">
      		                 <i class="add-xi">+${maplistB.add_interest}%</i>
      		            </s:if>
      		      </s:else>
      		     
      		  </a>
      		</div>
            <div class="biao-tip"><span class="span1">还款方式：
            <s:if test="%{#maplistB.isDayThe ==2}">到期还本付息</s:if>
							    <s:elseif test="%{#maplistB.paymentMode == 1}">等额本息</s:elseif>
							    <s:elseif test="%{#maplistB.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#maplistB.paymentMode == 3}">秒还</s:elseif>
							    <s:elseif test="%{#maplistB.paymentMode == 4}">一次性还款</s:elseif>
            
            </span><span class="span2">起投金额:1000.00元</span></div>
      	   		   <div class="biao_con">      
                        <div class="biao_con_block">
                            <ul class="clearfix item-list">
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="10.0">${maplistB.annualRate}%</b></em>
                                    <p>预期年化利率</p>
                                </li>
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="3">${maplistB.deadline}</b>个月</em>
                                    <p>项目期限</p>
                                </li>
                                <li>
                                    <em class="c1"><b class="scrollNum" data-numtarget="180">${maplistB.borrowAmount/10000}万</b></em>
                                    <p>项目总额</p>
                                </li>  
                          </ul>
                   </div>
	               <div class="biao_btn">
                   <s:if test="#request.maplistB.bState==1">
               	   <div class="jindu-z">
                       <div class="jindu">
                        	<div class="jinduing" style="width:${maplistB.schedules}%"></div>
                        </div>
                        <p class="percent">${maplistB.schedules}%</p>
                    </div>
                    <div style="margin-top:20px;">
                        
                          <s:if test="#request.maplistB.schedules<100">
                             <a href="financeDetail.do?id=${maplistB.id}" class="but_avg">立即投标</a>
                          </s:if>
                          <s:else>
                             <a href="###" class="but_gray">投标结束</a>
                          </s:else>
                    </div>
                    </s:if>
                     <s:elseif test="#request.maplistB.bState==0">
                         <div class="cs-time">
                            <img src="/new_images/sz-icon.png"> <i class="cs-time-top">${maplistB.applyTime}</i>
                        </div>
                        <div style="margin-top:20px;">
                          <a href="###" class="but_gray">即将发售</a>
                    </div>
                     </s:elseif>
	           </div>
	       </div>     
	      </div>
     </div>
     </s:iterator>  
     </s:if> 
    <!-- 定息宝 -->
    
    <!-- 活利宝 -->
     <s:if test="#request.mapListC.size>0">
     <s:iterator value="#request.mapListC" var="maplistC">
      <div class="diyaBox">
      <%--<a target="_blank" href="#">--%>
	      <div class="biao_tit position7" id="biao_tit2">
	       		<ul>
                	<li class="kk-f"><a href ="finance.do?m=1&type=6">活利宝</a></li>
                    <li>周期短</li>
                    <li>门槛低</li>
                    <li>收益快</li>
                </ul>
	      </div>
      <%--</a>--%>
      <div class="biao" id="biao2"> 
      		<div class="biao-line"><a href="financeDetail.do?id=${maplistC.id}"><h4>${maplistC.borrowTitle}</h4></a></div>
            <div class="biao-tip"><span class="span1">还款方式：
            <s:if test="%{#maplistC.isDayThe ==2}">到期还本付息</s:if>
							    <s:elseif test="%{#maplistC.paymentMode == 1}">等额本息</s:elseif>
							    <s:elseif test="%{#maplistC.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#maplistC.paymentMode == 3}">秒还</s:elseif>
							    <s:elseif test="%{#maplistC.paymentMode == 4}">一次性还款</s:elseif>
            
            </span><span class="span2">起投金额:100.00元</span></div>
      	   		   <div class="biao_con">      
                        <div class="biao_con_block">
                            <ul class="clearfix item-list">
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="10.0">${maplistC.annualRate}%</b></em>
                                    <p>预期年化利率</p>
                                </li>
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="3">${maplistC.deadline}</b>个月</em>
                                    <p>项目期限</p>
                                </li>
                                <li>
                                    <em class="c1"><b class="scrollNum" data-numtarget="180">${maplistC.borrowAmount/10000}万</b></em>
                                    <p>项目总额</p>
                                </li>  
                          </ul>
                   </div>
	               <div class="biao_btn">
	               <s:if test="#request.maplistC.bState==1">
               	   <div class="jindu-z">
                   <div class="jindu">
                        <div class="jinduing" style="width:${maplistC.schedules}%"></div>
                   </div>
                   <p class="percent">${maplistC.schedules}%</p>
                   </div>
                   <div style="margin-top:20px;">
                          <s:if test="#request.maplistC.schedules<100">
                             <a href="financeDetail.do?id=${maplistC.id}" class="but_avg">立即投标</a>
                          </s:if>
                          <s:else>
                             <a href="###" class="but_gray">投标结束</a>
                          </s:else>
                   </div>
                   </s:if> 
                   <s:elseif test="#request.maplistC.bState==0">
                       <div class="cs-time">
                            <img src="/new_images/sz-icon.png"> <i class="cs-time-top">${maplistC.applyTime}</i>
                       </div>
                       <div style="margin-top:20px;">
                          <a href="###" class="but_gray">即将发售</a>
                       </div>
                   </s:elseif>
	           </div>
	       </div>     
	      </div>
     </div>   
     </s:iterator>
     </s:if>
    <!-- 活利宝 -->
    
    <!-- 优选宝 -->
     <s:if test="#request.mapListA.size>0">
     <s:iterator value="#request.mapListA" var="maplistA">
      <div class="diyaBox">
      <%--<a target="_blank" href="#">--%>
	      <div class="biao_tit position8" id="biao_tit2">
	       		<ul>
                	<li class="kk-f"><a href ="finance.do?m=1&type=3">优选宝</a></li>
                    <li>优选项目</li>
                    <li>随时转让</li>
                </ul>
	      </div>
      <%--</a>--%>
      <div class="biao" id="biao2"> 
      		<div class="biao-line"><a href="financeDetail.do?id=${maplistA.id}"><h4>${maplistA.borrowTitle}</h4></a></div>
            <div class="biao-tip"><span class="span1">还款方式：
            <s:if test="%{#maplistA.isDayThe ==2}">到期还本付息</s:if>
							    <s:elseif test="%{#maplistA.paymentMode == 1}">等额本息</s:elseif>
							    <s:elseif test="%{#maplistA.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#maplistA.paymentMode == 3}">秒还</s:elseif>
							    <s:elseif test="%{#maplistA.paymentMode == 4}">一次性还款</s:elseif>
            </span><span class="span2">起投金额:100.00元</span></div>
      	   		   <div class="biao_con">      
                        <div class="biao_con_block">
                            <ul class="clearfix item-list">
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="10.0">${maplistA.annualRate}%</b></em>
                                    <p>预期年化利率</p>
                                </li>
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="3">${maplistA.deadline}</b>个月</em>
                                    <p>项目期限</p>
                                </li>
                                <li>
                                    <em class="c1"><b class="scrollNum" data-numtarget="180">${maplistA.borrowAmount/10000}万</b></em>
                                    <p>项目总额</p>
                                </li>  
                          </ul>
                   </div>
	               <div class="biao_btn">
               		<s:if test="#request.maplistA.bState==1">
               		<div class="jindu-z">
                       <div class="jindu">
                        	<div class="jinduing" style="width:${maplistA.schedules}%"></div>
                        </div>
                        <p class="percent">${maplistA.schedules}%</p>
                    </div>
                    <div style="margin-top:20px;">
                          <s:if test="#request.maplistA.schedules<100">
                             <a href="financeDetail.do?id=${maplistA.id}" class="but_avg">立即投标</a>
                          </s:if>
                          <s:else>
                             <a href="###" class="but_gray">投标结束</a>
                          </s:else>
                    </div>
                    </s:if>
                    <s:elseif test="#request.maplistA.bState==0">
                         <div class="cs-time">
                            <img src="/new_images/sz-icon.png"> <i class="cs-time-top">${maplistA.applyTime}</i>
                        </div>
                        <div style="margin-top:20px;">
                            <a href="###" class="but_gray">即将发售</a>
                        </div>
                    </s:elseif>
	           </div>
	       </div>     
	      </div>
     </div>
     </s:iterator>
     </s:if>  
    <!-- 优选宝 -->
    
    <!-- 交易宝 -->
     <s:if test="#request.debtList.size>0">
     <s:iterator value="#request.debtList" var="debtlist">
     <div class="diyaBox">
      <%--<a target="_blank" href="#">--%>
	      <div class="biao_tit position9" id="biao_tit2">
	       		<ul>
                	<li class="kk-f"><a href ="finance.do?m=1&type=7">交易宝</a></li>
                    <li>自由转换</li>
                    <li>增加资金流动性</li>
                </ul>
	      </div>
      <%--</a>--%>
      <div class="biao" id="biao2"> 
      		<div class="biao-line"><a href="queryDebtDetail.do?id=${debtlist.id}"><h4>${debtlist.borrowTitle}</h4></a></div>
            <div class="biao-tip"><span class="span1">还款方式：
            <s:if test="%{#debtlist.isDayThe ==2}">到期还本付息</s:if>
							    <s:elseif test="%{#debtlist.paymentMode == 1}">等额本息</s:elseif>
							    <s:elseif test="%{#debtlist.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#debtlist.paymentMode == 3}">秒还</s:elseif>
							    <s:elseif test="%{#debtlist.paymentMode == 4}">一次性还款</s:elseif>
            </span><span class="span2">起投金额:100.00元</span></div>
      	   		   <div class="biao_con">      
                        <div class="biao_con_block">
                            <ul class="clearfix item-list">
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="10.0">${debtlist.annualRate}%</b></em>
                                    <p>预期年化利率</p>
                                </li>
                                <li class="con">
                                    <em class="c1"><b class="scrollNum" data-numtarget="3">${debtlist.deadline}</b>个月</em>
                                    <p>项目期限</p>
                                </li>
                                <li>
                                    <em class="c1"><b class="scrollNum" data-numtarget="180">${debtSum/10000}万</b></em>
                                    <p>项目总额</p>
                                </li>  
                          </ul>
                   </div>
	             <div class="biao_btn">
               		<div class="jindu-z">
               		   <s:if test="#request.debtlist.debtStatus==2">
                              <div class="jindu">
                        	     <div class="jinduing" style="width:100.00%"></div>
                              </div>
                              <p class="percent">100.00%</p>
                          </s:if>
                          <s:else>
                          	<s:if test="#request.debtlist.remainDays!='过期'">
                              <div class="jindu">
                        	     <div class="jinduing" style="width:0.00%"></div>
                              </div>
                              <p class="percent">0.00%</p>
                            </s:if>
                            <s:else>
                            	 <div class="jindu">
                        	     	<div class="jinduing" style="width:100.00%"></div>
	                             </div>
	                             <p class="percent">100.00%</p>
                            </s:else>
                          </s:else> 
                       
                    </div>
                        <!--<div class="cs-time">
                            <img src="/new_images/sz-icon.png"> <i class="cs-time-top">2015-10-19 15:00出售</i>
                        </div>-->
                        <div style="margin-top:20px;">
                        	 
                          <s:if test="#request.debtlist.debtStatus==2">
                             <a href="###" class="but_gray">投标结束</a>
                          </s:if>
                          <s:else>
                          	<s:if test="#request.debtlist.remainDays!='过期'">
                            	<a href="queryDebtDetail.do?id=${debtlist.id}" class="but_avg">立即投标</a>
                            </s:if>
                            <s:else>
				    			<a class="but_gray">已完成</a>
				    		</s:else>
                          </s:else> 
                        </div>
	           </div>
	       </div>     
	      </div>
     </div>   
     </s:iterator>
     </s:if>
    <!-- 交易宝 -->
    </div>
    
    <!--媒体报道-->
   <div class="mtbd">
     	<p class="item-top1"><em class="arrow1 position10"></em>媒体报道<a href="getMessageBytypeId.do?typeId=34">更多></a></p>
        <ul>
            <s:if test="#request.meikuStick.size>=1">
              <s:iterator value="#request.meikuStick" var="stick" status="stst"> 
                 <li><a href="${url}"><img src="${imgPath}"></a></li>
              </s:iterator>
            </s:if>
        </ul>
  </div>
  <!--媒体报道-->
    <!--新闻-->
  <div class="news">
    	 <div class="item grid-1" >
            <p class="item-top1"><em class="arrow1 position1"></em>最新投资<!-- <a href="###">更多</a> --></p>
            <div id="demo" style="overflow:hidden;height:160px;">
                    <ul id="demo1" class="invest-list">
                     <s:if test="#request.investList.size>0">
                          <s:iterator value="#request.investList" var="investlist">  
                             <li class="list-w ">${investlist.investTime}&nbsp;&nbsp;用户${investlist.username}投资${investlist.investAmount}元</li>
                          </s:iterator>
                     </s:if>
                    </ul> 
                    <div id="demo2" class="invest-list"></div>
            </div> 
        </div>
    	<div class="item grid-1">
              <p class="item-top1"><em class="arrow1 position2"></em>行业资讯<!-- <a href="###">更多></a> --></p>
              <ul class="show-list">
               <s:if test="#request.infomationList.size>0">
                <s:iterator value="#request.infomationList" var="infomationlist">  
                  <li class="list-w "><a href="infomationById.do?id=${infomationlist.id}"><span>${infomationlist.title}</span></a></li>
                </s:iterator>
               </s:if>
              </ul>  	         	
        </div>
        <div class="item grid-1" style="background:none;">
              <p class="item-top1"><em class="arrow1 position3"></em>理财知识<!-- <a href="###">更多></a> --></p>
               <ul class="show-list">
               <s:if test="#request.licaiList.size>0">
                <s:iterator value="#request.licaiList" var="licailist"> 
                  <li class="list-w "><a href="licaiById.do?id=${licailist.id }"><span>${licailist.title }</span></a></li>
                </s:iterator>
               </s:if>
              </ul>  	         	
        </div>
  	</div>
    <!--新闻-->
    <!--合作机构-->
     <div class="hzjg">
     	<p class="item-top1"><em class="arrow1 position4"></em>合作机构<a href="getMessageBytypeId.do?typeId=4&name=hzhb">更多></a></p>
        <ul>
        	<li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img1.png" /></a></li>
            <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img9.png" /></a></li>
            <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img3.png" /></a></li>
            <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img10.png" /></a></li>
        </ul>
        <ul>
        	  <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img5.png" /></a></li>
              <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img6.png" /></a></li>   
              <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img7.png" /></a></li>
              <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb"><img src="new_images/hz-img11.png" /></a></li>   
        </ul>
     </div>
   <!--合作机构-->
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
