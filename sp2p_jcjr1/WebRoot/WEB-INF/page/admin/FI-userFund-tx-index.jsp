<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户资金管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/popom.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				$("#excel").click(function(){
				 $("#excel").attr("disabled",true);
				       var userId=$("#userId").val();
				        var applyTime=$("#startTime").val();
				        var endTime=$("#endTime").val();
				       var sum=$("#sum").val();
				       var statss=$("#status").val();
				       var userName=$("#userName").val();
                       window.location.href=encodeURI(encodeURI("exportUserFundWithdraw.do?userId="+userId+"&&applyTime="+applyTime+"&&sum="+sum+"&&statss="+statss+"&&userName="+userName+"&&endTime="+endTime));
                setTimeout("test()",3000);
                });
			});
			function test(){
			   $("#excel").attr("disabled",false);
			   }
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.startTime"] = $("#startTime").val();
				param["paramMap.endTime"] = $("#endTime").val();
				param["paramMap.sum"] = $("#sum").val();
				param["paramMap.status"] = $("#status").val();
				param["paramMap.userId"] = $("#userId").val();
		 		$.shovePost("queryUserFundWithdrawList.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
						
		</script>
	</head>
	<body>
	<s:hidden id="userId" name="paramMap.userId" ></s:hidden>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28"  class="xxk_all_a">
								<a href="userFundInit.do">资金管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28"  class="xxk_all_a"">
								<a href="queryAllUserFundRecordInit.do">资金明细</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28"  class="main_alll_h2">
								<a href="javascript:void(0)">提现记录</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
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
										用户名：
										<input id="userName" name="paramMap.username" type="text"/>
										&nbsp;&nbsp;
										提现时间：
										<input id="startTime" name="paramMap.startTime" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;&nbsp;&nbsp;--&nbsp;
										<input id="endTime" name="paramMap.endTime" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
		
										总金额：
										<input id="sum" name="paramMap.sum" type="text"/>
										&nbsp;&nbsp;
										状态：
										<s:select id="status" list="status" name="paramMap.status" 
										listKey="statusId" listValue="statusValue"  />
										&nbsp;&nbsp;
									   
										<input id="bt_search" type="button" value="查 找"  />
										&nbsp;&nbsp;
										<input id="excel" type="button" value="导出EXCEL"  />
									</td>
								</tr>
								
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
