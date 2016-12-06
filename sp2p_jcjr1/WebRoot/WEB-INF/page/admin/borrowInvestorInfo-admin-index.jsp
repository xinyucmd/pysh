<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<title></title>
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
		$(function(){
			initListInfo(param);
			$("#bt_search").click(function(){
				param["investor"] = $("#investor").val();
		        initListInfo(param);
			});
			
		});
		
		function initListInfo(praData){
	 		$.post("queryAdminBorrowInvestorList.do",praData,initCallBack);
	 	}
	 	
	 	function initCallBack(data){
			$("#dataInfo").html(data);
		}
		
		
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a" id="paying">
								<a href="adminSuccessBorrowInit.do">成功借款</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a" id="paying">
								<a href="adminPaymentInit.do">正在还款的借款</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a" id="paying">
								<a href="adminAllDetailsInit.do">还款明细账</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="main_alll_h2" id="paying">
								<a href="adminBorrowInvestorInit.do">借款明细账</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a" id="paying">
								<a href="adminPayoffBorrowInit.do">已还完的借款</a>
							</td>
						
							<td width="2">
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
								<td class="f66" align="left" width="80%" height="36px">
										投资者：
										<input type="text" class="inp140" id="investor" name="investor" value="${paramMap.investor}"/>
										<input id="bt_search" type="button" value="查找" />
									</td>
								</tr>
							</tbody>
						</table>
					<span id="dataInfo"> </span>
					</div>
				</div>
					</div>
				</div>
			</div>
		</body>
	</html>				