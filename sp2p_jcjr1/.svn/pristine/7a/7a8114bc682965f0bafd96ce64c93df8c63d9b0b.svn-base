<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>投资统计管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="/css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="/css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="/script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
		
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="tomorrow"  class="main_alll_h2">
								<a href="javascript:void(0);">投资详情记录</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									投资时间:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									
									<input id="search" type="button" value="查找" name="search" />&nbsp;&nbsp;
									<%-- <c:choose>
									<c:when test="${sessionScope.admin.userName=='liuyingsheng'||sessionScope.admin.userName=='jiatai'}">
									<input id="excel" type="button"  value="导出Excel" name="excel" />
									</c:when>
									<c:otherwise >
									<input id="excel" type="button" disabled="disabled" value="导出Excel" name="excel" />
									</c:otherwise>
									</c:choose> --%>
									<input id="flag" type="hidden" value="record"/>
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>


<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});	
				
				$("#excel").click(function(){
				    $("#excel").attr("disabled",true);
				  window.location.href=encodeURI(encodeURI("exportinvestDetails.do?timeStart="+$("#timeStart").val()+"&&timeEnd="+$("#timeEnd").val()));
				   setTimeout("test()",4000);
				});	
			});	
			function test(){
			   $("#excel").attr("disabled",false);
			   }		
			function initListInfo(praData){
				praData["paramMap.timeStart"] = $("#timeStart").val();
				praData["paramMap.timeEnd"] = $("#timeEnd").val();
		 		$.shovePost("investDetailLists.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
