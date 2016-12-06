<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>债权转让参数设置</title>
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
	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 0 10px 10px">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">债权转让参数设置</a>
							</td>
							<td width="2">&nbsp;
								
							</td>
							<td>&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
									<label style="width:100px;">债权持有最短期限:</label>
									<input id="holdShortDays" value="${settingDebt.hold_short_days} "/>天&nbsp;&nbsp;
									<label style="width:90px;">距离到期日期限:</label>
									<input id="rangeExpiryDays" value="${settingDebt.range_expiry_days} " />天&nbsp;&nbsp;
									</td>
                                   </tr>
                                    <tr>
                                        <td class="f66" align="left" width="50%" height="36px" style="line-height:40px; overflow:hidden;">
                                        <label style="width:120px; font-size:14px; font-weight:bold;">每月可转让金额</label><br />
                                        <s:iterator value="#request.groupList" id="map">
                                            <label style="width:90px; float:left; float:none\9; text-align:left;">${map.groupName }:</label>
                                            <input id="${map.id }" name="transmoneyMonth" value="${map.transMoney }" />元<br/>
                                        </s:iterator>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
                                        <input type="hidden" id="borrowTypes_value" value="${settingDebt.borrow_types}">
                                        <input type="hidden" id="setId" value="${settingDebt.id}">
                                        
                                        <label style="width:90px; float:left; text-align:left;">转让系数范围:</label><input type="text" id="transRatio" value="${settingDebt.trans_ratio} ">（0≤X≤1）&nbsp;&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="f66" align="left" width="50%" height="36px" style="line-height:40px;">
                                        <label style="width:100px; float:left; text-align:left;">可转让标的类型：</label>
                                        <s:iterator value="#request.list" id="bean">
                                            ${bean.name }
                                            <s:if test="%{#bean.nid == 'flow'}"><input type="checkbox" style="position:relative; top:5px;" name="borrowTypes" value="${bean.id }" disabled>&nbsp;&nbsp;</s:if>
                                            <s:else><input type="checkbox" style="position:relative; top:5px;"name="borrowTypes" value="${bean.id }">&nbsp;&nbsp;</s:else>
                                        </s:iterator>
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
	var praData = {};
	$(function(){
		init();//反显复选框
		//initParam(praData);
		$('#save_setting').click(function(){
			$("#loading").show();
		   //债权持有最短期限
		   if($("#holdShortDays").val() < 30){
				alert("债权持有最短期限不能小于30天!");
				$("#loading").hide();
				return;
			}
		 	//获取每月债转金额    
	        var transmoneyMonth_value = "";
	        $('input[name="transmoneyMonth"]').each(function(){
	        	if($(this).val() == "" || $(this).val() == null || $(this).val() <= 0){
		        	transmoneyMonth_value += "-";  
	        	}else{
		        	transmoneyMonth_value += $(this).attr("id") + ":" + $(this).val() + ",";  
	        	}
	        });
	        //判断所有用户组是否都分配转让金额
	        if(transmoneyMonth_value.indexOf("-") >= 0){
	        	alert("请为所有用户组分配转让金额");
	        	$("#loading").hide();
	        	return;
	        }
		   initParam(transmoneyMonth_value);
		   var id = $("#setId").val();
		   var url = "updateSettingDebt.do?setId="+id;
			$.ajax({
			    type: "post",
			    url: url,
			    dataType: "json",
			    data:praData,
			    success: function (data) {
			    	$("#loading").hide();
			    	alert(data.msg);
			    	window.location.href="toDebtSetting.do";
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
			    	$("#loading").hide();
			       alert(errorThrown);
			    }
			});
		});				
	});
	
	function initParam(transmoneyMonth_value){
		//获取复选框值    
        var borrowTypes_value = "";
        $('input[name="borrowTypes"]:checked').each(function(){
        	borrowTypes_value += $(this).val() + ",";  
        });
		praData["paramMap.holdShortDays"] = $("#holdShortDays").val();
		praData["paramMap.rangeExpiryDays"] = $("#rangeExpiryDays").val();
		praData["paramMap.transmoneyMonth"] = transmoneyMonth_value;
		praData["paramMap.borrowTypes"] = borrowTypes_value;
		praData["paramMap.transRatio"] = $("#transRatio").val();
		
	}
	
	function init(){
		//反显checkBox 
		var thisVal = "";
		var arr = $("#borrowTypes_value").val().split(",");
	    $("input[name='borrowTypes']").each(function(){     
		   thisVal = $(this).val();     
		   for(var c in arr){           
			   if(thisVal==arr[c] && arr[c]!=""){  
				   $(this).attr("checked",true);//打勾
			   }                    
		   }          
	    });
	}

</script>
</html>
