<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>红包参数设置页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 0 10px 10px">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">红包参数设置</a>
							</td>
							<td width="2">&nbsp;
								
							</td>
							<td>&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1200px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
									<label style="width:100px;">红包使用期限:</label>
									<input id="use_deadline" value="${bonusMap.use_deadline}"/>天&nbsp;&nbsp;
									<label style="width:100px;">注册红包投资期限:</label>
									<input id="reg_deadline" value="${bonusMap.reg_deadline}"/>月 &nbsp;&nbsp;
									<label style="width:100px;">推荐红包投资期限:</label>
									<input id="recom_deadline" value="${bonusMap.recom_deadline}"/>月 &nbsp;&nbsp;
									<label style="width:100px;">投资红包投资期限:</label>
									<input id="invest_deadline" value="${bonusMap.invest_deadline}"/>月 
								</td>
                            </tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
									<label style="width:100px;">推荐注册红包:</label>
									<input id="reg_recommended_red" value="${bonusMap.reg_recommended_red}"/>元&nbsp;&nbsp;
									<label style="width:100px;">注册红包:</label>
									<input id="reg_registration_red" value="${bonusMap.reg_registration_red}"/>元 &nbsp;&nbsp;
									<label style="width:100px;">注册奖励:</label>
									<input id="reg_registration_cash" value="${bonusMap.reg_registration_cash}"/>元 &nbsp;&nbsp;
									<label style="width:100px;">超级账户id:</label>
									<input id="super_id" value="${bonusMap.super_id}"/>
								</td>
                            </tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
									<label style="width:100px;">投资比例:</label>
									<input id="invest_proportion" value="${bonusMap.invest_proportion}"/>&nbsp;&nbsp;
									<label style="width:100px;">最低投资金额:</label>
									<input id="low_amount" value="${bonusMap.low_amount}"/>元 &nbsp;&nbsp;
									<label style="width:100px;">红包上限:</label>
									<input id="red_limit" value="${bonusMap.red_limit}"/>元 
								</td>
                            </tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
									<label style="width:100px;">红包规则开始日期:</label>
									<input id="start_time" class="in_text_250 input_a" value="${bonusMap.start_time}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />&nbsp;&nbsp;
									<label style="width:100px;">红包规则结束日期:</label>
									<input id="end_time" class="in_text_250 input_a" value="${bonusMap.end_time}" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									<input type="hidden" id="bonusId" value="${bonusMap.id}">
									<input type="hidden" id="min" value="${map.min}">
									<input type="hidden" id="max" value="${map.max}">
								</td>
                            </tr>
                            <tr>
                                <td class="f66" align="left" width="50%" height="36px" style="line-height:40px; overflow:hidden;">
                                	<label style="width:120px; font-size:14px; font-weight:bold;">投资红包发放金额</label><br/>
                                	<a href="###" id="data_add" class="scbtn"  onclick="data()" style="font-size:12px;">添加</a>
                                	<div id = "bonus_amount">
                                		<s:iterator value="#request.list" id="bean">
                                			<div id = "div_update_${bean.id }">
								                                          最小投资金额： <input id="update_min_invest_amount_${bean.id }" value="${bean.min_invest_amount }">&nbsp;&nbsp;
								                                          最大投资金额：  <input id="update_max_invest_amount_${bean.id }" value="${bean.max_invest_amount }">&nbsp;&nbsp;
								                                          红包金额： <input id="update_red_value_${bean.id }" value="${bean.red_value }">&nbsp;&nbsp;
	                                            <a href="###" class="scbtn" onClick="delete_data_update(${bean.id})" style="font-size:12px;">删除</a><br/>
                                			</div>
                                        </s:iterator>
                                	</div>
                                </td>
                            </tr>
                            <tr>
                                <td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
                                <input id="save_setting" type="button" value="保存设置" name="save_setting" />
                                </td>
                            </tr>
						</tbody>
					</table>
		             <span id="loading" style="display:none"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
	var param = {};
	var n = -100;
	//添加金额区间
	function data(){
		var dataContent = $("#bonus_amount");
	  	var strHtml = "<div id = 'div_" + n + "'>";
	  	strHtml += "<label>最小投资金额：</label>";
	  	strHtml += "<input  id='min_invest_amount_" + n + "' value='' /> &nbsp;&nbsp;";
	  	strHtml += "<label>最大投资金额：</label>";
	  	strHtml += "<input  id='max_invest_amount_" + n + "' value='' /> &nbsp;&nbsp;";
	  	strHtml += "<label>红包金额：</label>";
	  	strHtml += "<input  id='red_value_" + n + "' value='' /> &nbsp;&nbsp;";
		strHtml += "<a href='###' id='delete_data_" + n + "' class='scbtn'  onclick='delete_data(" + n + ")' style='font-size:12px;'>删除</a><br/>";
		strHtml += "</div>";
		dataContent.append(strHtml);
		n++;
	}
	
	//添加方法删除图片
	function delete_data(id){
		$("#div_"+id+"").remove();
	}
	
	//修改方法删除图片
	function delete_data_update(id){
		if(confirm("确定要删除吗?")){
			var param = {}
			param["id"] = id;
		    $.ajax({
				    type: "post",
				    url: "deleteBonusAmount.do",
				    dataType: "json",
				    data:param,
				    success: function (data) {
				    	if(data.state == 1){
					    	$("#div_update_"+id+"").remove();
				    	}else if(data.state == 0){
				    		alert("删除失败!");
				    	}
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
						alert(errorThrown);
				    }
			});
	    }
	}
	
	function initParam(){
		param["paramMap.super_id"] = $("#super_id").val();
		param["paramMap.use_deadline"] = $("#use_deadline").val();
		param["paramMap.reg_deadline"] = $("#reg_deadline").val();
		param["paramMap.recom_deadline"] = $("#recom_deadline").val();
		param["paramMap.invest_deadline"] = $("#invest_deadline").val();
		param["paramMap.reg_recommended_red"] = $("#reg_recommended_red").val();
		param["paramMap.reg_registration_red"] = $("#reg_registration_red").val();
		param["paramMap.reg_registration_cash"] = $("#reg_registration_cash").val();
		param["paramMap.invest_proportion"] = $("#invest_proportion").val();
		param["paramMap.low_amount"] = $("#low_amount").val();
		param["paramMap.red_limit"] = $("#red_limit").val();
		param["paramMap.start_time"] = $("#start_time").val();
		param["paramMap.end_time"] = $("#end_time").val();
	}

	$(function(){
		$('#save_setting').click(function(){
		   $("#loading").show();
		   var flag = check();//校验
		   if(flag == 0){
			   $("#loading").hide();
			   return;
		   }
		   //投资红包金额区间
			var min_invest_amount = "";
			var max_invest_amount = "";
			var red_value = "";
		   if(n > -100){
				for(var i=-100;i<n;i++){
					if($("#min_invest_amount_"+i+"").val() != undefined && $("#max_invest_amount_"+i+"").val() != undefined && $("#red_value_"+i+"").val() != undefined){
						if(($("#min_invest_amount_"+i+"").val()-$("#max_invest_amount_"+i+"").val()) > 0){
							alert("最小投资金额不能大于最大投资金额");
							$("#loading").hide();
							return;
						}
						if($("#red_value_"+i+"").val() <= 0){
							alert("红包金额不能为空");
							$("#loading").hide();
							return;
						}
						//拼接值
						min_invest_amount += $("#min_invest_amount_"+i+"").val() + ",";
						max_invest_amount += $("#max_invest_amount_"+i+"").val() + ",";
						red_value += $("#red_value_"+i+"").val() + ",";
					}
				}
				param["paramMap.min_invest_amount"] = min_invest_amount;
				param["paramMap.max_invest_amount"] = max_invest_amount;
				param["paramMap.red_value"] = red_value;
		   }
		   
		  //修改投资红包金额区间
			var	min = $("#min").val();
			var	max = $("#max").val();
			var update_min_invest_amount = "";
			var update_max_invest_amount = "";
			var update_red_value = "";
			var upda = 0;
			var ids = "";//修改id
			for(var j=min;j<=max;j++){
				if($("#update_min_invest_amount_"+j+"").val() != undefined && $("#update_max_invest_amount_"+j+"").val() != undefined && $("#update_red_value_"+j+"").val() != undefined){
					if(($("#update_min_invest_amount_"+j+"").val()-$("#update_max_invest_amount_"+j+"").val()) > 0){
						alert("最小投资金额不能大于最大投资金额");
						$("#loading").hide();
						return;
					}
					if($("#update_red_value_"+j+"").val() <= 0){
						alert("红包金额不能为空");
						$("#loading").hide();
						return;
					}
					//拼接值
					update_min_invest_amount += $("#update_min_invest_amount_"+j+"").val() + ",";
					update_max_invest_amount += $("#update_max_invest_amount_"+j+"").val() + ",";
					update_red_value += $("#update_red_value_"+j+"").val() + ",";
					ids += j + ",";
				}
			}
			//if(update_max_invest_amount =="" && max_invest_amount == ""){
				//alert("投资红包金额区间不能为空");
				//$("#loading").hide();
				//return;
			//}
			param["paramMap.update_min_invest_amount"] = update_min_invest_amount;
			param["paramMap.update_max_invest_amount"] = update_max_invest_amount;
			param["paramMap.update_red_value"] = update_red_value;
			param["paramMap.ids"] = ids;
		   
		   initParam();
		   var id = $("#bonusId").val();
		   var url = "updateBonusConfig.do?bonusId="+id;
			$.ajax({
			    type: "post",
			    url: url,
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	$("#loading").hide();
			    	if(data == 1){
				    	alert("红包使用期限不能为空");
			    	}
			    	if(data == 4){
				    	alert("投资比例不能大于1");
			    	}
			    	if(data == 5){
				    	alert("最低投资金额不能为空");
			    	}
			    	if(data == 6){
				    	alert("红包上限不能为空");
			    	}
			    	if(data == 7){
				    	alert("注册红包投资期限不能为空");
			    	}
			    	if(data == 8){
				    	alert("投资红包投资期限不能为空");
			    	}
			    	if(data == 10){
				    	alert("有效日期不能为空");
			    	}
			    	if(data == 11){
				    	alert("推荐红包投资期限不能为空");
			    	}
			    	if(data == 12){
				    	alert("超级账户id不能为空");
			    	}
			    	if(data == -1){
				    	alert("操作成功");
				    	window.location.href="updateBonusConfigInit.do";
			    	}
			    	if(data == -2){
				    	alert("红包配置修改成功");
				    	window.location.href="updateBonusConfigInit.do";
			    	}
			    	if(data == -3){
				    	alert("红包金额区间修改成功");
				    	window.location.href="updateBonusConfigInit.do";
			    	}
			    	if(data == 0){
				    	alert("操作失败");
			    	}
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
			    	$("#loading").hide();
			       alert(errorThrown);
			    }
			});
		});
	});
	
	function check(){
	   if($("#super_id").val() <= 0){
			alert("超级账户id不能为空");
			return 0;
	   }
	   if($("#use_deadline").val() <= 0){
			alert("红包使用期限不能为空");
			return 0;
	   }
	   if($("#reg_deadline").val() <= 0){
			alert("注册红包投资期限不能为空");
			return 0;
	   }
	   if($("#recom_deadline").val() <= 0){
			alert("推荐红包投资期限不能为空");
			return 0;
	   }
	   if($("#invest_deadline").val() <= 0){
			alert("投资红包投资期限不能为空");
			return 0;
	   }
	   if($("#invest_proportion").val() > 1){
			alert("投资比例不能大于1");
			return 0;
	   }
	   if($("#low_amount").val() <= 0){
			alert("最低投资金额不能为空");
			return 0;
	   }
	   if($("#red_limit").val() <= 0){
			alert("红包上限不能为空");
			return 0;
	   }
	   if($("#start_time").val() > $("#end_time").val()){
			alert("开始日期不能大于结束日期");
			return 0;
	   }
	   if($("#start_time").val() == "" || $("#end_time").val() == ""){
			alert("开始日期,结束日期不能为空");
			return 0;
	   }
	}
</script>
</html>
