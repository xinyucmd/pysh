<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
</script>
<style>
.nymain p{padding: 0;text-indent: 0}</style>
</head>
<body>
<div class="nymain" style="width: 560px;margin: 0 auto;line-height: 22px;text-align: justify;" >
    <h2 style="text-align:center;font-size: 16px;padding-bottom: 10px;padding-top:20px;"></h2>
    <div class="zw">
        <s:form action="homeupload.do" enctype="multipart/form-data" method="post">
				    <s:file name="homefile" id="td" style="float:left;margin-left:50px;"></s:file>
				    <s:submit value="上传头像" style="float:left;margin-left:20px;" cssClass="bcbtn"></s:submit>
   	 </s:form>
    </div>
</div>
<!-- 引用底部公共部分 -->     
</body>
</html>
