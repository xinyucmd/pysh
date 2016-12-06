
function Recommend(_param){
	JcBanking.call(this,_param);
}

Recommend.prototype = new JcBanking(); 


Recommend.prototype.list = function(pageNum,pageSize,flag){
	_param={};
	_param["pageNum"] = pageNum;
	_param["pageSize"] = pageSize;
	_param["flag"] = flag;
	
	MUser.prototype.httpPost(
			'/queryBonusInfo.do',
			_param,
			listComplete,
			function(msg){
				alert(msg)
			}
	);
}

function listComplete(data){
	if(!Recommend.prototype.isNotBank(data.bonusList)){
		$("#tab_list").empty();
		$("#tab_list").append("<tr><td>没有更多数据</td></tr>");
		return;
	}else{
		
		var bonusList = data.bonusList;
		var flag = data.flag;
		totalPageNum = data.totalPageNum;
		
		$("#totalPage").empty();
		$("#totalPage").append(totalPageNum);
		
		$("#tab_list").empty();
		var content_html = "";
		if(1 == flag){
			content_html = " <tr><td>序号</td><td>红包金额</td><td>可用金额</td><td>已用金额</td><td>使用时间</td> " +
			"<td>类型</td><td>领取时间</td><td>截止日期</td><td>状态</td></tr>";
			for(var i=0;i<bonusList.length;i++){
				content_html += "<tr>";
				var o = bonusList[i];
				content_html +="<td>"+(i+1)+"</td>";
				content_html +="<td>￥"+o.bonus_money+"</td>";
				content_html +="<td>￥"+o.bonus_avaliable+"</td>";
				content_html +="<td>￥"+o.bonus_used+"</td>";
				content_html +="<td>"+o.lastTime+"</td>";
				if(o.bonus_type==1){
					content_html +="<td>注册</td>";
				}
				else if(o.bonus_type==2){
					content_html +="<td>投资</td>";
				}
				else if(o.bonus_type==3){
					content_html +="<td>推荐注册</td>";
				}else{
					content_html +="<td></td>";
				}
				
				content_html +="<td>"+o.createTime+"</td>";
				content_html +="<td>"+o.endTime+"</td>";
				if(o.status==1){
					content_html +="<td>有效</td>";
				}
				else if(o.status==2){
					content_html +="<td>无效</td>";
				}else{
					content_html +="<td>无效</td>";
				}
				content_html += "</tr>";
			}
		}else{
			content_html = " <tr><td>序号</td><td>红包金额</td><td>可用金额</td><td>已用金额</td>" +
			"<td>领取时间</td><td>截止日期</td><td>状态</td></tr>";
			for(var i=0;i<bonusList.length;i++){
				content_html += "<tr>";
				var o = bonusList[i];
				content_html +="<td>"+(i+1)+"</td>";
				content_html +="<td>￥"+o.bonus_sum+"</td>";
				content_html +="<td>￥"+o.bonus_able+"</td>";
				content_html +="<td>￥"+o.bonus_already+"</td>";
				content_html +="<td>"+o.startTime+"</td>";
				content_html +="<td>"+o.endTime+"</td>";
				if(o.state==1){
					content_html +="<td>有效</td>";
				}
				else if(o.state==2){
					content_html +="<td>无效</td>";
				}else{
					content_html +="<td>无效</td>";
				}
				content_html += "</tr>";
			}
		}
		
	}
	
	$("#tab_list").append(content_html);
}



