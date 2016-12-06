<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>推广返现统计</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../script/pagination.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.pagination.js"></script>
	</head>
	 
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td width="100" height="28"  class="main_alll_h2">
								<a href="javascript:void(0);">推广返现统计</a>
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
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1200px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									被推荐人:
									<input id="be_username" value="" class="inp80" />&nbsp;&nbsp;
								
									推荐人:
									<input id="username" value="" class="inp80" />&nbsp;&nbsp;
									真实姓名:
									<input id="realName" value=""  class="inp80" />&nbsp;&nbsp;
									活动编号:<input id="code" value="1001"  class="inp80">
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px" id="smsContent">
									电影票总数:<font color="red" id="allTicketCount"></font>&nbsp;&nbsp;&nbsp;&nbsp;
									已发条数:<font color="red" id="hasTicketCount"></font>&nbsp;&nbsp;&nbsp;&nbsp;
									剩余条数:<font color="red" id="ticketCount"></font>&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="btnFailCount" type="button" value="未发送:0" />&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList">
	             		<img src="../images/admin/load.gif" class="load" alt="加载中..." id="loading"/>
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" id="tab_content"></table>
		             </span>
	              	<div id="Pagination" class="paging">  
						<div>
						</div>
					</div>
			</div>
		</div>
	</body>
<%@ include file="activity/recommmend_detail.jsp"%>

<script type="text/javascript">
var pageNum = 0; 
var pageSize = 10; 
var totalNum = 0;
var param={};


//翻页调用  
function PageCallback(pageNum, jq) {
	searchData(pageNum);  
} 

searchData(pageNum);

$("#search").click(
		function (){
			searchData(pageNum);
		}
);


function initSms(){
	$("#allTicketCount").empty();
	$("#ticketCount").empty();
	$("#hasTicketCount").empty();
	$("#btnFailCount").attr("value","未发送：(0)");
	var url = "querySmsRecommendSummary.do";
	$("#loading").show();
	$.ajax({
	    type: "post",
	    url: url,
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	$("#loading").hide();
	    	if(data.recommendSmsSummaryList != null && data.recommendSmsSummaryList != undefined && data.recommendSmsSummaryList != ''){
	    		$("#allTicketCount").append( data.recommendSmsSummaryList.allTicketCount);
	    		// 短信剩余条数
	    		$("#ticketCount").append( data.recommendSmsSummaryList.ticketCount);
	    		$("#hasTicketCount").append( data.recommendSmsSummaryList.hasTicketCount);
	    		if(data.ticketListCount!=null ){
	    			// 未发送短信条数
	        		$("#btnFailCount").attr("value", "未发送：("+data.ticketListCount+")点击重新发送");
	    		}
	    	}else{
	    		alert('没有数据！');
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	$("#loading").hide();
	       alert(errorThrown);
	    }
	});	
}

initSms();

$("#btnFailCount").click(
	function(){
		
		var returnVal = window.confirm("确定重新发送？", "重要提示");
		if(returnVal) {
			var url = "sendSmsTicket.do";
			$("#loading").show();
			$.ajax({
			    type: "post",
			    url: url,
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	$("#loading").hide();
			    	if(data.res>0){
			    		initSms();
			    	}else{
			    		alert(data);
			    	}
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
			    	$("#loading").hide();
			       alert(errorThrown);
			    }
			});	
		}
	}
);
	
 
function searchData(pageNum){
	
	param["username"] = $("#username").val();
	param["realName"] = $("#realName").val();
	param["be_username"] = $("#be_username").val();
	
	param["pageNum"] = pageNum+1;
	param["pageSize"] = pageSize;
	 
	var url = "queryAllRecommendSummary.do";
	$("#loading").show();
	$.ajax({
	    type: "post",
	    url: url,
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	$("#loading").hide();
	    	var tr_list_o = $("#tab_content");
	    	tr_list_o.empty();
	    	
	    	totalNum = data.totalNum;
	    	$("#Pagination").pagination(totalNum, {  
	    	    callback: PageCallback, 
	    	    maxentries:4,
	    	    prev_text: '上一页',  
	    	    next_text: '下一页',  
	    	    items_per_page: pageSize,  //每页显示条目数
	    	    num_display_entries: 6,//连续分页主体部分分页条目数  
	    	    current_page: pageNum,//当前页索引  
	    	    num_edge_entries: 2//两侧首尾分页条目数  
	    	}); 
	    	 	
	    	if(data.recommendSummaryList != null && data.recommendSummaryList != undefined && data.recommendSummaryList != ''){
		    	var strHtml = "";
			
			strHtml +="<tr>";
    		strHtml +="<td class='f66' align='center' height='36px'>编号</td>";
			strHtml +="<td class='f66' align='center' height='36px'>用户姓名</td>";
			strHtml +="<td class='f66' align='center' height='36px'>真实姓名</td>";
			strHtml +="<td class='f66' align='center' height='36px'> 推荐人投资金额</td>";
			strHtml +="<td class='f66' align='center' height='36px'> 推荐人数</td>";
			strHtml +="<td class='f66' align='center' height='36px'> 被推荐人投资总额</td>";
			strHtml +="<td class='f66' align='center' height='36px'> 被推荐人实名认证</td>";
			strHtml +="<td class='f66' align='center' height='36px'>返现金额</td>";
			strHtml +="<td class='f66' align='center' height='36px'>投资佣金</td>";
			strHtml +="<td class='f66' align='center' height='36px'>操作</td>";
			strHtml +="</tr>";
		    	
	    		for(var i=0;i<data.recommendSummaryList.length;i++){
		    		var o = data.recommendSummaryList[i];
		    		strHtml +="<tr>";
		    		strHtml +="<td class='f66' align='center' height='36px'>"+(i+1)+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.username+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.realName+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.recommendAmountTotal+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.beRecommendCount+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.beRecommendInvstCount+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.beRecomendInvstAmountTotal+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.beRecommendIdCount+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.cashAmountTotal+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'>"+o.invstBrokerageTotal+"</td>";
					strHtml +="<td class='f66' align='center' height='36px'><a href='javascript:detail("+o.recommendUserId+")'>查看</a></td>";
					strHtml +="</tr>";
		    	}
	    		tr_list_o.append(strHtml);
	    	}else{
	    		alert('没有数据！');
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	$("#loading").hide();
	       alert(errorThrown);
	    }
	});
	
}

function detail(recommendUserId){
	queryRecommendInfo(recommendUserId);
}

</script>
</html>
