<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<jsp:include page="/include/head.jsp"></jsp:include>
		<jsp:include page="/include/common.jsp"></jsp:include>
		<link href="css/inside.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="javascript/jquery-2.0.js"></script>
		<script type="text/javascript" src="javascript/front.js"></script>
		<!--<link rel="stylesheet" type="text/css" href="css/site.css" />-->
	</head>
	<body>
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="content">
			<div class="newsView_content_wrap">
				<div class="newsView_bar">
					<span class="newsView_local"><a target="_self"  href="getMessageBytypeId.do?typeId=4">关于我们</a> > <a href="getMessageBytypeId.do?typeId=35" >官方公告</a> > ${paramMap.title}</span>
				</div>
				<div class="newsView_content">
					<div class="content_bar"></div>
					<div class="newsView_list">
						<div class="newsView_title">
							${paramMap.title}
						</div>
						<div class="newsView_pdate">
							<span>发布时间：${paramMap.publishTime}</span>
							<span>浏览次数：${paramMap.visits}</span>
							<span>发布者：${paramMap.username}</span>
						</div>
						<div class="newsView_ctx">
							${paramMap.content }
						</div>

						<div class="newsView_brief">
							<div class="left">
								<s:if test="#request.lastDate!=null">
         上一篇：<a style="max-width: 30px; overflow: hidden;"
										href="frontNewsDetails.do?id=${request.lastDate.id}">${request.lastDate.title}</a>
								</s:if>
								<s:else>
									<a>&nbsp;&nbsp;&nbsp;</a>
								</s:else>
							</div>
							<div class="right">
								<s:if test="#request.previousDate!=null">
      下一篇：<a style="max-width: 30px; overflow: hidden;"
										href="frontNewsDetails.do?id=${request.previousDate.id}">${request.previousDate.title}</a>
								</s:if>
								<s:else>
									<a>&nbsp;&nbsp;&nbsp;</a>
								</s:else>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
	</body>
</html>