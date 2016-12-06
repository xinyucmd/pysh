<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript" language="javascript">
	    $(function(){
	    	param["pageBean.pageNum"]=1;
		    initListInfo(param);
		  	
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
				initListInfo(param);
		    });
		    
		    $("#excel").click(function(){
				 window.location.href=encodeURI(encodeURI("exportdebtMangrFee.do?alienatorName="+$("#alienatorName").val()+"&&auctionName="+$("#auctionName").val()+"&&timeStart="+$("#timeStart").val()+"&&timeEnd="+$("#timeEnd").val()));
			});	
		   
	    });
	    //加载留言信息
	   function initListInfo(praData) {
		   praData["paramMap.alienatorName"] = $("#alienatorName").val();
		   praData["paramMap.auctionName"] = $("#auctionName").val();
		   praData["paramMap.timeStart"] = $("#timeStart").val();
			praData["paramMap.timeEnd"] = $("#timeEnd").val();
	   		$.shovePost("queryAssignmentDebtFeeInfo.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		
	</script>

	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" id="today" height="28" class="xxk_all_a">
								<a href="borrowStatisInit.do">借款管理费</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" id="tomorrow"  class="xxk_all_a3">
								<a href="borrowStatisInterestInit.do">投资利息管理费</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" id="debt"  class="main_alll_h2">
								债权转让手续费
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									转让人：
									<input id="alienatorName" name="paramMap.alienatorName" />&nbsp;&nbsp; 
									购买人：
									<input id="auctionName" name="paramMap.auctionName" />&nbsp;&nbsp; 
									成交时间:
									<input id="timeStart" class="Wdate" style="width: 100px;" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" style="width: 100px;" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									
									<input id="search" type="button" value="查找" name="search" />
									&nbsp;&nbsp;
									<input id="excel" type="button" value="导出Excel" name="excel" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
				</div>
			</div>
		</div>
	</body>
</html>
