<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>投标统计管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" height="28"  class="main_alll_h2">
								投标统计
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="investStatisRankInit.do">投标排名</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="investRecordInit.do">投标记录</a>
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
									<input id="investor" value="" />&nbsp;&nbsp; 
									真实姓名:
									<input id="realName" value="" />&nbsp;&nbsp; 
									用户组:
									<s:select id="group" list="userGroupList" listKey="selectValue" listValue="selectName" headerKey="-1" headerValue="--请选择--"></s:select>&nbsp;&nbsp; 
									投资时间:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									投资总额(年化):
									<input id="amountDown" value=""  class="inp80" />&nbsp;&nbsp;
									--
									<input id="amountUp" value=""  class="inp80" />&nbsp;&nbsp;		
									标的类型：
									<s:select id="borrowWay" list="#{-1:'--请选择--',3:'优选宝',4:'定息宝',6:'活利宝',7:'交易宝'}"></s:select>&nbsp;&nbsp; 
									<input id="search" type="button" value="查找" name="search" />&nbsp;&nbsp;
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
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});	
				
				$("#excel").click(function(){
				    $("#excel").attr("disabled",true);
				  window.location.href=encodeURI(encodeURI("exportinvestStatis.do?realName="+$("#realName").val()+"&&investor="+$("#investor").val()+"&&borrowWay="+$("#borrowWay").val()+"&&amountDown="+$("#amountDown").val()+"&&amountUp="+$("#amountUp").val()+"&&timeStart="+$("#timeStart").val()+"&&timeEnd="+$("#timeEnd").val()+"&&group="+$("#group").val()));
				   setTimeout("test()",4000);
				});				
			});	
			function test(){
			   $("#excel").attr("disabled",false);
			   }		
			function initListInfo(praData){
				praData["paramMap.realName"] = $("#realName").val();
				praData["paramMap.investor"] = $("#investor").val();
				praData["paramMap.timeStart"] = $("#timeStart").val();
				praData["paramMap.timeEnd"] = $("#timeEnd").val();
				praData["paramMap.amountDown"] = $("#amountDown").val();
				praData["paramMap.amountUp"] = $("#amountUp").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.group"] = $("#group").val();
		 		$.shovePost("investStatisList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
