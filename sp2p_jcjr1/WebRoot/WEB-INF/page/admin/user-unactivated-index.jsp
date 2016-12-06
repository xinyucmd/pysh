<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							  <td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">未激活的用户</a>
							</td>
							<td width="2">
								&nbsp;
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
									用户名:
									<input id="userName" value="" />&nbsp;&nbsp;
									邮箱：
									<input id="email" value="" />&nbsp;&nbsp;  
									创建时间：
									<input id="createtimeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="createtimeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									&nbsp;&nbsp; <input id="search" type="button" value="查找" name="search" />
												<input id="excel" type="button" value="导出Excel" name="excel" />
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
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				
				$("#excel").click(function(){
					  $("#excel").attr("disabled",true);
					   window.location.href=encodeURI(encodeURI("exportuserenable.do?userName="+$("#userName").val()+"&&email="+$("#email").val()+"&&createtimeStart="+$("#createtimeStart").val()+"&&createtimeEnd="+$("#createtimeEnd").val()));
					  setTimeout("test()",3000);
					});
				
				function test(){
					   $("#excel").attr("disabled",false);
					   }
				
				$('#search').click(function(){
				   param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});				
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.email"] = $("#email").val();
				praData["paramMap.createtimeStart"] = $("#createtimeStart").val();
				praData["paramMap.createtimeEnd"] = $("#createtimeEnd").val();
		 		$.shovePost("unactivatedinfo.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			function del(id){
		 		if(!window.confirm("确定要激活该用户吗?")){
		 			return;
		 		}
	 			window.location.href= "updateActicate.do?userId="+id;
		 	}
</script>
</html>
