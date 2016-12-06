<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>推广奖励设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		
	//全局变量，判断添加还是修改
	var flag = 0;
	//修改id
	var updateId = 0;
			$(function(){
				$("#reward_amount").hide();
 				$("#reward_sum").hide();
 				$("#fixed").hide();
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
			});

			function initListInfo(praData){
				praData["paramMap.rewardSrc"] = $("#rewardSrc").val();
				praData["paramMap.userType"] = $("#userType").val();
				praData["paramMap.rewardLevel"] = $("#rewardLevel").val();
				praData["paramMap.giveWay"] = $("#giveWay").val();
		 		$.shovePost("rewardSettingInfo.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
		 	function cancel(){
	 			$("#div_add").hide();
	 			window.location.href="rewardSettingInit.do";
		 	}
		 	//添加奖励规则
		 	function addInit(){
		 		if($("#div_add").is(":hidden")){
		 			$("#div_add").show();
		 		}else{
		 			$("#div_add").hide();
		 		}
		 	}
		 	
		 	function doReward(){
		 		if($("#div_reward").is(":hidden")){
		 			$("#div_reward").show();
		 		}else{
		 			$("#div_reward").hide();
		 		}
		 	}
		 	
		 	function doExecutePlusInterest(){
		 		if($("#div_reward2").is(":hidden")){
		 			$("#div_reward2").show();
		 		}else{
		 			$("#div_reward2").hide();
		 		}
		 	}
		 	
		 	function executeReward(){
		 		var praData = {};
		 		praData["paramMap.rewardTime"] = $("#reward_time").val();
		 		praData["paramMap.borrowId"] = $("#borrowId").val();
		 		var url = "executeReward.do";
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	if(data == -1){
					    	alert("操作失败");
				    	}else if(data==1){
				    		alert("您选择的日期已经补发！");
				    	}
				    	else if(data == 0){
					    	alert("操作成功");
					    	window.location.href="rewardSettingInit.do";
				    	}
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				       alert(errorThrown);
				    }
				});
		 	}
		 	
		 	function executePlusInterest(){
		 		var praData = {};
		 		
		 		praData["paramMap.rewardTime"] = $("#reward_time2").val();
		 		praData["paramMap.borrowId"] = $("#borrowId2").val();
		 		var url = "executePlusInterest.do";
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	if(data == -1){
					    	alert("操作失败");
				    	}else if(data==1){
				    		alert("您选择的日期已经补发！");
				    	}
				    	else if(data == 0){
					    	alert("操作成功");
					    	window.location.href="rewardSettingInit.do";
				    	}
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				       alert(errorThrown);
				    }
				});
		 	}
		 	
		 	
		 	//删除奖励规则
		 	function deleteInit(id){
		 		var praData = {};
		 		var url = "deleteById.do?id="+id;
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	if(data == 0){
					    	alert("操作失败");
				    	}
				    	if(data == -1){
					    	alert("操作成功");
					    	window.location.href="rewardSettingInit.do";
				    	}
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				       alert(errorThrown);
				    }
				});
		 	}
		 	
		 	//修改奖励规则
		 	function updateInit(id){
		 		var praData = {};
		 		var url = "updateRewardSettingInit.do?id="+id;
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	flag = 1;
				    	updateId = data.id;
				    	//反显
				    	$('select#reward_src').attr('value',data.reward_src);
				    	$("input:radio[name=user_type][value="+data.user_type+"]").attr("checked", "true");
				    	$('#min_invest_amount').attr('value',data.min_invest_amount);
				    	$('#max_invest_amount').attr('value',data.max_invest_amount);
				    	$('#deadline_start').attr('value',data.deadline_start);
				    	$('#deadline_end').attr('value',data.deadline_end);
				    	$('select#deadline_unit').attr('value',data.deadline_unit);
				    	$('select#reward_valid_unit').attr('value',data.reward_valid_unit);
				    	//复选框
				    	var arr = data.reward_level.split(",");
					    $("input[name='reward_level']").each(function(){     
						   thisVal = $(this).val();     
						   for(var c in arr){           
							   if(thisVal==arr[c] && arr[c]!=""){  
								   $(this).attr("checked",true);//打勾
							   }                    
						   }          
					    });
				    	var arr1 = data.borrow_type.split(",");
					    $("input[name='borrow_type']").each(function(){     
						   thisVal = $(this).val();     
						   for(var c in arr1){           
							   if(thisVal==arr1[c] && arr1[c]!=""){  
								   $(this).attr("checked",true);//打勾
							   }                    
						   }          
					    });
					    $("input:radio[name=return_type][value="+data.return_type+"]").attr("checked", "true");
					    $("input:radio[name=isopne][value="+data.isopne+"]").attr("checked", "true");
					    $("input:radio[name=give_way][value="+data.give_way+"]").attr("checked", "true");
				    	$('#up_limit').attr('value',data.up_limit);
				    	$('#down_limit').attr('value',data.down_limit);
				    	$('#start_time').attr('value',data.start_time);
				    	$('#end_time').attr('value',data.end_time);
				    	$('#reward_valid').attr('value',data.reward_valid);
				    	$('#amount_invest_sum').attr('value',data.amount_invest_sum);
				    	$('#reg_sum').attr('value',data.reg_sum);
				    	//奖励方式
				    	if(data.return_type == 1){
				    		$('#brokerage_one_b').attr('value',data.brokerage_one);
				    		$('#brokerage_two_b').attr('value',data.brokerage_two);
				    	}else if(data.return_type == 2){//固定值
				    		$('#brokerage_one_j').attr('value',data.brokerage_one);
				    		$('#brokerage_two_j').attr('value',data.brokerage_two);
				    	}
				    	change('select');
				    	change('radio');
				    	
				    	if($("#div_add").is(":hidden")){
				 			$("#div_add").show();
				 		}else{
				 			$("#div_add").hide();
				 		}
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				       alert(errorThrown);
				    }
				});
		 	}
		 	
		 	//变更事件
		 	 function change(type){
			 		if("select" == type){//下拉框
			 			if($("#reward_src").val() == 2){
			 				$("#reward_sum").show();
			 				$("#reward_validity").hide();
			 				$("#reward_amount").hide();
			 				$("#once_invest").hide();
			 			}
			 			else if($("#reward_src").val() == 3){
			 				$("#once_invest").show();
			 				$("#reward_validity").show();
			 				$("#reward_amount").hide();
			 				$("#reward_sum").hide();
			 				//$("#borrow_type2").attr("checked");
			 			}else if($("#reward_src").val() == 4){
			 				$("#reward_amount").show();
				 			$("#reward_validity").show();
				 			$("#once_invest").hide();
				 			$("#reward_sum").hide();
			 			}else{
			 				$("#reward_validity").hide();
			 				$("#reward_amount").hide();
			 				$("#once_invest").hide();
			 				$("#reward_sum").hide();
			 			}
			 		}else if("radio" == type){//单选框
				 		if($("input[name='return_type']:checked").val() == 1){
				 			$("#proportion").show();
				 			$("#fixed").hide();
				 			if($("#reward_level2").attr("checked")){
				 				$("#div_1").show();
				 			}else{
				 				$("#brokerage_two_b").attr("value",0);
				 				$("#div_1").hide();
				 			}
				 		}else{
				 			$("#fixed").show();
				 			$("#proportion").hide();
				 			if($("#reward_level2").attr("checked")){
				 				$("#div_2").show();
				 			}else{
				 				$("#brokerage_two_j").attr("value",0);
				 				$("#div_2").hide();
				 			}
				 		}
			 		}
			 	}
		 	
		 	//提交
		 	function save(){
		 		//校验
		 		if($("#reward_src").val() == ""){
		 			alert("奖励来源不能为空");
		 			return;
		 		}
		 		if($("#reward_src").val() == 3 || $("#reward_src").val() == 4){
		 			if($("#reward_valid").val() == ""){
			 			alert("推荐投资奖励有效期不能为空");
			 			return;
		 			}
		 		}
		 		if($("#reward_src").val() == 4 && $("#amount_invest_sum").val() == ""){
		 			alert("累计投资额度不能为空");
		 			return;
		 		}
		 		//单次投资
		 		if($("#reward_src").val() == 3){
		 			if($("#min_invest_amount").val() == ""){
			 			alert("最小投资金额不能为空");
			 			return;
			 		}
			 		if($("#max_invest_amount").val() == ""){
			 			alert("最大投资金额不能为空");
			 			return;
			 		}
					if(($("#min_invest_amount").val() - $("#max_invest_amount").val()) > 0){
						alert("最小投资金额不能大于最大投资金额");
						return;
					}
					if($("#deadline_start").val() == "" || $("#deadline_end").val() == "" || $("#deadline_start").val() > $("#deadline_end").val()){
			 			alert("投资期限输入有误");
			 			return;
			 		}
					if($("#borrow_type").val() == ""){
			 			alert("标种不能为空");
			 			return;
			 		}
		 		}
		 		
				if($("#brokerage_one_b").val() == "" && $("#brokerage_one_j").val() == ""){
		 			alert("第一层返现比例或金额不能为空");
		 			return;
		 		}
				//比例不能大于1
				if($("input[name='return_type']:checked").val() == 1){
					if(($("#brokerage_one_b").val() - 100) > 0 || ($("#brokerage_one_j").val() - 100) > 0){
			 			alert("第一层返现比例不能大于100%");
			 			return;
			 		}
				}
				//下限值不能大于上限值
				if($("#down_limit").val() > 0 && $("#down_limit").val() > $("#up_limit").val()){
		 			alert("下限值不能大于上限值");
		 			return;
				}
				if($("#start_time").val() == ""){
		 			alert("开始日期不能为空");
		 			return;
		 		}
				if($("#end_time").val() == ""){
		 			alert("结束日期不能为空");
		 			return;
		 		}
				if($("#start_time").val() > $("#end_time").val()){
		 			alert("开始日期不能大于结束日期");
		 			return;
		 		}
				
		 		$("#loan_save").show();
		 		//获取复选框值    
		        var reward_level_value = "";
		        $('input[name="reward_level"]:checked').each(function(){
		        	reward_level_value += $(this).val() + ",";  
		        });
		        //现在设置第一层必选
		        if(reward_level_value.indexOf('1') == -1){
		        	alert("第一层必须勾选");
		 			return;
		        }
		        var borrow_type_value = "";
		        $('input[name="borrow_type"]:checked').each(function(){
		        	borrow_type_value += $(this).val() + ",";  
		        });
		 		var praData = {};
		 		praData["paramMap.reward_level"] = reward_level_value;
		 		praData["paramMap.reward_src"] = $("#reward_src").val();
		 		praData["paramMap.reward_valid"] = $("#reward_valid").val();
		 		praData["paramMap.reg_sum"] = $("#reg_sum").val();
		 		praData["paramMap.reward_valid_unit"] = $("#reward_valid_unit").val();
		 		praData["paramMap.amount_invest_sum"] = $("#amount_invest_sum").val();
		 		praData["paramMap.min_invest_amount"] = $("#min_invest_amount").val();
		 		praData["paramMap.max_invest_amount"] = $("#max_invest_amount").val();
		 		praData["paramMap.deadline_start"] = $("#deadline_start").val();
		 		praData["paramMap.deadline_end"] = $("#deadline_end").val();
		 		praData["paramMap.deadline_unit"] = $("#deadline_unit").val();
		 		praData["paramMap.borrow_type"] = borrow_type_value;
		 		if($("#brokerage_one_b").val() == ""){
			 		praData["paramMap.brokerage_one"] = $("#brokerage_one_j").val();
		 		}else{
			 		praData["paramMap.brokerage_one"] = $("#brokerage_one_b").val();
		 		}
		 		if($("#brokerage_two_b").val() == ""){
			 		praData["paramMap.brokerage_two"] = $("#brokerage_two_j").val();
		 		}else{
			 		praData["paramMap.brokerage_two"] = $("#brokerage_two_b").val();
		 		}
		 		praData["paramMap.up_limit"] = $("#up_limit").val();
		 		praData["paramMap.down_limit"] = $("#down_limit").val();
		 		praData["paramMap.start_time"] = $("#start_time").val();
		 		praData["paramMap.end_time"] = $("#end_time").val();
		 		praData["paramMap.return_type"]=$("input[name='return_type']:checked").val();
		 		praData["paramMap.isopne"]=$("input[name='isopne']:checked").val();
		 		praData["paramMap.give_way"]=$("input[name='give_way']:checked").val();
		 		praData["paramMap.user_type"]=$("input[name='user_type']:checked").val();
		 		
		 		var url = "";
		 		if(flag == 0){//add
			 		url = "addRewardSetting.do";
		 		}else if(flag == 1){//update
			 		url = "updateRewardSetting.do";
			 		praData["paramMap.id"] = updateId;
		 		}
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	$("#loan_save").hide();
				    	if(data == 1){
					    	alert("奖励来源不能为空");
				    	}
				    	if(data == 2){
					    	alert("最小投资金额不能大于最大投资金额");
				    	}
				    	if(data == 3){
					    	alert("投资期限不正确");
				    	}
				    	if(data == 4){
					    	alert("标种不能为空");
				    	}
				    	if(data == 5){
					    	alert("奖励计算方式不能为空");
				    	}
				    	if(data == 6){
					    	alert("下限值不能大于上限值");
				    	}
				    	if(data == 7){
					    	alert("第一层返现比例不能为空");
				    	}
				    	if(data == 8){
					    	alert("有效日期不能为空");
				    	}
				    	if(data == 9){
					    	alert("第二层返现比例不能大于第一层返现比例");
				    	}
				    	if(data == 10){
					    	alert("推荐投资奖励有效期不能为空");
				    	}
				    	if(data == 11){
					    	alert("累计投资额度不能为空");
				    	}
				    	if(data == 12){
					    	alert("用户类型不能为空");
				    	}
				    	if(data == 13){
					    	alert("累计推荐注册个数不能为空");
				    	}
				    	if(data == 14){
					    	alert("奖励发放方式不能为空");
				    	}
				    	if(data == 15){
					    	alert("是否启用不能为空");
				    	}
				    	if(data == 16){
					    	alert("奖励层级不能为空");
				    	}
				    	if(data == 0){
					    	alert("操作失败");
				    	}
				    	if(data == -1){
					    	alert("操作成功");
					    	window.location.href="rewardSettingInit.do";
				    	}
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				    	$("#loan_save").hide();
				       alert(errorThrown);
				    }
				});
   			}
		 	jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#div_add').slideUp(200);
		$('#div_reward').slideUp(200);
	})

})
		</script>
        <style>
			ul li{ list-style:none;}
			.con1 ul li{ line-height:34px;}
			.close_btn{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; right:10px; cursor:pointer;}
        </style>
	</head>
	<body>
		<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="28"  class="main_alll_h2">
									<a href="rewardSettingInit.do">规则列表</a>
								</td>
								<td width="2">&nbsp;
									
								</td>
								<td width="100" class="xxk_all_a">
									<a href="javascript:addInit();">添加</a>
								</td>
							<td width="100" colspan="3" >
								<a href="javascript:doReward();" title="补发推荐人佣金奖励">推荐人佣金补发</a>
							</td>
							
							<td width="100" colspan="3" >
								<a href="javascript:doExecutePlusInterest();" title="加息奖励补发">加息奖励补发</a>
							</td>
							
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										奖励来源：
										<s:select id="rewardSrc" list="#{-1:'--请选择--',1:'推荐注册',2:'累计推荐注册',3:'推荐单次投资',4:'累计推荐投资'}"></s:select>&nbsp;&nbsp;
										用户类型：
										<s:select id="userType" list="#{-1:'--请选择--',1:'个人',2:'机构'}"></s:select>&nbsp;&nbsp;
										奖励层级：
										<s:select id="rewardLevel" list="#{-1:'--请选择--',1:'一层',2:'二层'}"></s:select>&nbsp;&nbsp;
										奖励发放方式：
										<s:select id="giveWay" list="#{-1:'--请选择--',1:'现金',2:'红包'}"></s:select>&nbsp;&nbsp;
										
										<input id="bt_search" type="button" value="搜索"  />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			
		<!-- 添加页面 -->
		<div id="div_add" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">
            <div class="close_btn"></div>
			<form id="form">
                <div class="con1">
                <ul>
	                <li><label><span style="color: red">*</span>奖励来源:</label>
	                    <select id="reward_src" onChange="javascript:change('select');" >
	                        <option value="1">推荐注册</option>
	                        <option value="2">累计推荐注册</option>
	                        <option value="3" selected="selected">推荐单次投资</option>
	                        <option value="4">累计推荐投资</option>
	                    </select>
	                </li>
	                
	                <li id = "reward_validity" ><labe><span style="color: red">*</span>推荐奖励有效期:</label>
						<input id="reward_valid" value="" class="input_a"/>
						<select id="reward_valid_unit">
	                        <option value="1" selected="selected">月</option>
	                        <option value="2">年</option>
	                    </select>
						<span  style="color: red" id="reward_valid_span" class="formtips"></span>
					</li>
					<li id = "reward_amount"><label><span style="color: red">*</span>累计投资额度:</label>
						<input id="amount_invest_sum" value="" class="input_a"/>元
					</li>
					<li id = "reward_sum"><label><span style="color: red">*</span>累计推荐注册个数:</label>
						<input id="reg_sum" value="" class="input_a"/>个
					</li>
	                <li id = "once_invest"><label><span style="color: red">*</span>投资金额:</label>
	                	<input id="min_invest_amount" value="" class="input_a"/>元 --
						<input id="max_invest_amount" value="" class="input_a"/>元<br/>
						<label><span style="color: red">*</span>投资期限:</label> 
	                	<input id="deadline_start" value="" class="input_a"/>--
	                	<input id="deadline_end" value="" class="input_a"/>
	                	<select id="deadline_unit">
	                        <option value="1" selected="selected">月</option>
	                        <option value="2">天</option>
	                    </select>
	                	<br/>
	                	
	                	<label><span style="color: red">*</span>标种:</label>
	                	<input type="checkbox" style="position:relative; top:5px;" name="borrow_type" value="6">活利宝&nbsp;&nbsp;
	                	<input type="checkbox" style="position:relative; top:5px;" id ="borrow_type2" name="borrow_type" value="4">定息宝&nbsp;&nbsp;
	                	<input type="checkbox" style="position:relative; top:5px;" name="borrow_type" value="3">优选宝&nbsp;&nbsp;
	                	<input type="checkbox" style="position:relative; top:5px;" name="borrow_type" value="7">交易宝
	                </li>
	              
					<li><label><span style="color: red">*</span>用户类型:</label>
						<input type="radio" name="user_type" id="user_type1" value="1" checked="checked"
							onclick="javascript:change('radio');"/>
						个人
						<input type="radio" name="user_type" id="user_type2" value="2"
							onclick="javascript:change('radio');" />
						机构
					</li>
					<li><label><span style="color: red">*</span>奖励发放方式:</label>
						<input type="radio" name="give_way" id="give_way1" value="1" checked="checked" readonly="readonly"
							onclick="javascript:change('radio');"/>
						现金
						<input type="radio" name="give_way" id="give_way2" value="2"
							onclick="javascript:change('radio');" />
						红包
					</li>
					<li><label><span style="color: red">*</span>奖励层级:</label>
						<input type="checkbox" style="position:relative; top:5px;" id = "reward_level1" name="reward_level" value="1" checked="checked" readonly="readonly">第一层&nbsp;&nbsp;
	                	<input type="checkbox" style="position:relative; top:5px;" id = "reward_level2" name="reward_level" value="2"  onclick="javascript:change('radio');">第二层
					</li>
					<li><label><span style="color: red">*</span>奖励计算方式:</label>
						<input type="radio" name="return_type" id="return_type1" value="1" checked="checked"
							onclick="javascript:change('radio');"/>
						按比例
						<input type="radio" name="return_type" id="return_type2" value="2"
							onclick="javascript:change('radio');" />
						固定值
					</li>
					
					<li id = "proportion"><labe><span style="color: red">*</span>第一层返现比例:</label>
						<input id="brokerage_one_b" value="${map.brokerage_one}" class="input_a"/>%<br/>
						<div id="div_1" style="display:none;"><labe><span style="color: red">*</span>第二层返现比例:</label>
							 <input id="brokerage_two_b" value="" class="input_a"/>%
						</div>
						<labe>上限值:</label>
						<input id="up_limit" value="${map.up_limit}" class="input_a"/>
						<labe>下限值:</label>
						<input id="down_limit" value="${map.down_limit}" class="input_a"/>
					</li>
					
					<li id = "fixed"><labe><span style="color: red">*</span>第一层返现金额:</label>
						<input id="brokerage_one_j" value="" class="input_a"/><br/>
						<div id="div_2" style="display:none;"><labe><span style="color: red">*</span>第二层返现金额:</label>
							<input id="brokerage_two_j" value="" class="input_a"/>
						</div>
					</li>
					
					<li><label style="width:170px;"><span style="color: red">*</span>开始日期:</label>
						<input id="start_time" class="in_text_250 input_a" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
					</li>
					<li><label style="width:170px;"><span style="color: red">*</span>结束日期:</label>
						<input id="end_time" class="in_text_250 input_a" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
					</li>
					
					<li><label><span style="color: red">*</span>是否启用:</label>
						<input type="radio" name="isopne" id="isopne1" value="1" checked="checked"/>
						是
						<input type="radio" name="isopne" id="isopne1" value="0"/>
						否
					</li>
					
					<li style="margin-left:130px;">
						<input type="button" value="提交"  class="input_b" onClick="save()"/>
						<input type="button" value="取消" class="input_b" onClick="cancel()"/> 
					</li>
                </ul>
                <span id="loan_save" style="display:none"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
                </div>
            </form>
        	<table id="loan_tab" style=" color: #333333;width:100%; border-collapse: collapse;" cellspacing="0" cellpadding="0" bgcolor="#fff" border="0" class="zh_table"></table>
    	</div>
    	
    	<!-- 添加页面 -->
		<div id="div_reward" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">
            <div class="close_btn"></div>
        	<ul>
	        	<li><label style="width:170px;"><span style="color: red">*</span>补发日期:</label>
					<input id="reward_time" class="in_text_250 input_a" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
					<input id="borrowId" class="in_text_50 input_a" type="text" />
					<input type="button" value="提交"  class="input_b" onClick="executeReward()"/>
				</li>
			</ul>
        </div>
        
        <!-- 添加页面 -->
		<div id="div_reward2" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">
            <div class="close_btn"></div>
        	<ul>
	        	<li><label style="width:170px;"><span style="color: red">*</span>补发日期:</label>
					<input id="reward_time2" class="in_text_250 input_a" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" />
					<input id="borrowId2" class="in_text_50 input_a" type="text" />
					<input type="button" value="提交"  class="input_b" onClick="executePlusInterest()"/>
				</li>
			</ul>
        </div>
    	
	</body>
</html>
