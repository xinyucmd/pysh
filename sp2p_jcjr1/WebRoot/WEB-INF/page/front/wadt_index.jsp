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
          
<!--登陆-->
<div class="wrap" style=" overflow:hidden;">
 <div class="lne_cent">
	<ol class="breadcrumb" style="margin-bottom:0;">
        <li><a>当前位置</a></li>
        <li><a href="#">微信贷</a></li>
        <li><a href="#">网站地图</a></li>
     </ol>
 	<div class="clearfix fluid mb_10">
        <div class="module padding">
            <h3 class="title">网站地图</h3>
            <ul class="map">
                <li class="bg">
                    <span>我要投资</span>
                    <a target="_blank" href="finance.do?m=1&type=4">定息宝</a>
                    <a target="_blank" href="finance.do?m=1&type=6">活利宝</a>
                    <a target="_blank" href="finance.do?m=1&type=3">优选宝</a>
                    <a target="_blank" href="finance.do?m=1&type=7">交易宝</a>
                </li>
              
                <li class="bg">
                    <span>新手指引</span>
                    <a target="_blank" href="newPerson.do">新手指引</a>
                    <a target="_blank" href="cellPhonereginit.do">我要注册</a>
                    <a target="_blank" href="finance.do?m=1&type=4">灵活投资</a>
                    <a target="_blank" href="borrow.do">我要借款</a>
                </li>
                <li class="bg">
                    <span>关于我们</span>
                    <a target="_blank" href="getMessageBytypeId.do?typeId=4">公司简介</a>
                    <a target="_blank" href="getMessageBytypeId.do?typeId=4&name=hzhb">合作伙伴</a>
                    <a target="_blank" href="getMessageBytypeId.do?typeId=34">媒体报道</a>
                    <a target="_blank" href="getMessageBytypeId.do?typeId=35">官方公告</a>
                    <a target="_blank" href="getMessageBytypeId.do?typeId=4&name=rczp">招贤纳士</a>
                    <a target="_blank" href="getMessageBytypeId.do?typeId=4&name=lxwm">联系我们</a>
                </li>
                <li>
                    <span>行业资讯</span>
                    <a target="_blank" href="infomations.do?type=1">行业资讯</a>
                    <a target="_blank" href="licais.do?type=2">理财知识</a>
                    <a target="_blank" href="wxdCreates.do?type=3">微信贷原创</a>
                </li>
            </ul>
            </div>
        </div>
  </div>
</div>

<jsp:include page="/include/footer.jsp"></jsp:include>
 <!--footer-->
</body>
</html>
