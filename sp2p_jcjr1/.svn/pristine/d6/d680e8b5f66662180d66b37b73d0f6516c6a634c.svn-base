
function Recommend(_param){
	JcBanking.call(this,_param);
}

Recommend.prototype = new JcBanking(); 


Recommend.prototype.list = function(pageNum,pageSize){
	_param={};
	_param["pageNum"] = pageNum;
	_param["pageSize"] = pageSize;
	
	MUser.prototype.httpPost(
			'/queryRecommendUserInfo.do',
			_param,
			listComplete,
			function(msg){
				alert(msg)
			}
	);
}

function listComplete(data){
	if(!Recommend.prototype.isNotBank(data.recommendUserSummary)||
			!Recommend.prototype.isNotBank(data.recommendUserInfo)){
		$("#tab_list").append("<tr><td>暂无数据</td></tr>");
		//alert('未查询到数据');
		//Recommend.prototype.log('未查询到数据');
		return;
	}
	var recommendUserSummary = data.recommendUserSummary;
	var recommendUserInfo = data.recommendUserInfo;
	totalPageNum = data.totalPageNum;
	
	$("#userCount").empty();
	$("#userIdNoCount").empty();
	$("#totalPage").empty();
	
	$("#userCount").append(recommendUserSummary.userCount);
	$("#userIdNoCount").append(recommendUserSummary.userIdNoCount);
	$("#totalPage").append(totalPageNum);
	
	
	$("#tab_list").empty();
	var content_html = "";
	for(var i=0;i<recommendUserInfo.length;i++){
		content_html += "<tr>";
		var o = recommendUserInfo[i];
		content_html +="<td>"+(i+1)+"</td>";
		content_html +="<td>"+o.username+"</td>";
		if(o.realName == "" || o.realName == null){
			content_html +="<td></td>";
		}else{
			content_html +="<td>"+o.realName+"</td>";
		}
		content_html +="<td>"+o.createTime+"</td>";
		content_html +="<td>"+o.isAuth+"</td>";
		if(o.src == 1){
			content_html +="<td>邀请链接</td>";
		}else if(o.src == 2){
			content_html +="<td>二维码扫码</td>";
		}else if(o.src == 3){
			content_html +="<td>填写推荐人</td>";
		}else{
			content_html +="<td>无</td>";
		}
		content_html +="<td>"+o.investCount+"</td>";
		content_html += "</tr>";
	}
	$("#tab_list").append(content_html);
	
}



