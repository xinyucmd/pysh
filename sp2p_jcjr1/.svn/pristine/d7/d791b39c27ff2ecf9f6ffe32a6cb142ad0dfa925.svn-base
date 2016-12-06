<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>奖励发放统计首页</title>
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
				<div style="width:200px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" height="28"  class="main_alll_h2">
								投资人奖励
							</td>
                            <td width="2">
								
							</td>
							<td width="120" height="28"  id="tomorrow"  class="xxk_all_a" >
								<a href="queryRecommendInit.do">推荐人奖励</a>
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
									<input id="userName" value="" class="inp80" />&nbsp;&nbsp;
									推荐人:
									<input id="recommendUser" value="" class="inp80" />&nbsp;&nbsp;
									奖励总额:
									<input id="amountDown" value=""  class="inp80" />&nbsp;&nbsp;
									--
									<input id="amountUp" value=""  class="inp80" />&nbsp;&nbsp;
									推荐人类型:
									<s:select id="type" name = "paramMap.type" list="#{-1:'--请选择--',1:'QQ群主',2:'九华',3:'普通用户',0:'没有推荐'}"></s:select>&nbsp;&nbsp;
									<input id="search" type="button" value="查找" name="search" />
									
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
				$('#search').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});				
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.recommendUser"] = $("#recommendUser").val();
				praData["paramMap.amountDown"] = $("#amountDown").val();
				praData["paramMap.amountUp"] = $("#amountUp").val();
				praData["paramMap.type"] = $("#type").val();
		 		$.shovePost("queryRewardList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
