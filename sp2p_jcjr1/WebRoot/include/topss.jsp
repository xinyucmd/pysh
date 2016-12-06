<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="css/site.css" />
 <link rel="stylesheet" type="text/css" href="css/css.css" />
<!--顶部状态栏 开始-->
<div class="l_top_top">
	<div class="l_top_top_center">
		<div class="l_top_top_center_left">
			<!-- <a href="update.jsp"><img src="images/mobile_01.png" />手机版</a>
			<span>|</span> -->
			<a href="javascript:addCookie();">设为首页</a>
			<span>|</span>
			<a href="javascript:setHomepage();">加入收藏</a>
			<span>|</span>
			<a href="callcenter.do">帮助中心</a>
		</div>
		<div class="l_top_top_center_right">
             <s:if	 test="#session.user !=null">
				     Hi,<a href="owerInformationInit.do">[${user.userName}]</a><span></span> <a href="logout.do">退出</a>
				  </s:if>
				  <s:else>
				  <a href="login.do">登录</a><span>|</span><a href="reg.do">注册</a>&nbsp;
				   </s:else>
				  <span> | </span><a href="callkfs.do" class="kf">客服</a>
			<!-- <img src="images/weibo.png" /><a href="javascript:void(0);">新浪共享</a>
			<img src="images/qq_gx_img.png" /><a href="javascript:void(0);">QQ共享</a> 
				  -->			
		</div>
	</div>
</div>
<div class="top">
<!-- <script type="text/javascript" src="script/gg.js"></script>  -->
  <div class="logo"> <a href=""><img src="<%= application.getAttribute("basePath") %>/images/index9_03.jpg"/></a></div>
  <div class="top-right">
  	<div class="login-information">
    <div class="login-informationstp1">
    <div class="login-informationstpn"> 
           <!-- Baidu Button BEGIN -->
				<span id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
				<a class="bds_tsina"></a>
				<span class="bds_more"></span>
				</span>
				<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=6638061" ></script>
				<script type="text/javascript" id="bdshell_js"></script>
				<script type="text/javascript">
				document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
				</script> &nbsp;分享
				<!-- Baidu Button END -->
    </div></div>
  <div class="login-informationstp1">
  <div class="login-informationstpn">您好！
  <span class="red">${user.userName}</span>| <a href="home.do"><s:if test="#session.user==null">&nbsp;登&nbsp;录&nbsp;</s:if><s:else>个人中心</s:else></a> | <s:if test="#session.user==null"><a href="reg.do">&nbsp;注&nbsp;册&nbsp;</a>  | <a href="cellPhonereginit.do">手机注册</a></s:if><s:else><a href="logout.do">退出</a></s:else></div></div>
    </div>
    <div class="top-tel"><img src="<%= application.getAttribute("basePath") %>/images/tel_08.png" /><a href="callkfs.do"><img src="<%= application.getAttribute("basePath") %>/images/tel_09.jpg" /></a></div>
  </div>

</div>
<div style="height:80px">
<div class="nav-zdh">
  <div class="bar"><!--这是下拉背景--></div>
  <div class="nav_main">
    <ul>
      <li id="sy_hover"><a href="index.jsp">首页</a>
        <div class="h_menu none" id="sy_menu">
          <ul>
            <li class="first"><a href="getMessageBytypeId.do?typeId=3">平台原理</a></li>
            <li class="s-line2"></li>
            <li><a href="frontTeam.do">团队介绍</a></li>
            <li class="s-line2"></li>            
            <li><a href="getMessageBytypeId.do?typeId=5">法律政策</a></li>
            <!-- <li class="s-line2"></li>
            <li><a href="getMessageBytypeId.do?typeId=4">关于我们</a></li> -->
            <li class="s-line2"></li>
            <li><a href="download.do">下载专区</a></li>
          </ul>
        </div>
      </li>
      <li id="licai_hover"><a href="finance.do">我要理财</a>
      <div class="h_menu none" id="licai_menu">
          <ul>
            <li class="first"><a href="getMessageBytypeId.do?typeId=8">如何理财</a></li>
            <li class="s-line2"></li>
            <li class="two" ><a href="financetool.do">工具箱</a></li>
            <!--  <li class="three" ><a href="become2FinanceInit.do">成为理财人</a></li>-->
          </ul>
        </div>
      </li>
      <li id="jk_hover"><a href="borrow.do">我要借款</a>
      <div class="h_menu none" id="jk_menu">
          <ul>
            <li class="first"><a href="borrow.do">申请贷款</a></li>
            <li></li>
          </ul>
        </div>
      </li>
      <li id="zq_hover"><a href="queryFrontAllDebt.do">债权转让</a></li>
      <li id="zh_hover"><a href="home.do">个人中心</a></li>
      <li id="bj_hover"><a href="capitalEnsure.do">本金保障</a></li>
      <li id="kf_hover"><a href="callcenter.do?cid=1">客服中心</a></li>
      <li id="lt_hover" style="background:none;"><a target="_blank"  href="getMessageBytypeId.do?typeId=4">关于我们</a></li>
    </ul>
  </div>
<div class="nav-tzggz">
  <div class="nav-tzgg">
  	<div class="nav-tzggleft"><ul>
    <li>${sitemap.siteName}投资总金额：${investMap.totalInvestment }</li>
    <li>${sitemap.siteName}待收总金额：${investMap.dueinfund }  </li>
    
    <li>${sitemap.siteName}投资总金额：${investMap.totalInvestment } </li>
    <li>${sitemap.siteName}待收总金额：${investMap.dueinfund }  </li>
    
    <li>${sitemap.siteName}投资总金额：${investMap.totalInvestment } </li>
    <li>${sitemap.siteName}待收总金额：${investMap.dueinfund }  </li>
    </ul>
    </div>
    <div class="nav-tzggright">
    <ul>
    <li>${sitemap.siteName}逾期总金额：${investMap.exceedInvest }</li>
    <li>${sitemap.siteName}逾期总金额：${investMap.exceedInvest }</li>
    </ul>
    </div>
  </div>
</div>
</div>

</div>
        <!--顶部主导航 结束-->