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
		<script type="text/javascript">
		$(function(){
			initListInfo(param);
			$("#bt_search").click(function(){
				param["paramMap.name"] = $("#name").val();
				param["paramMap.telephone"] = $("#telephone").val();
				param["paramMap.borrowAmount"] = $("#borrowAmount").val();
		     initListInfo(param);
			});
			
				
			$("#excel").click(function(){
				$("#excel").attr("disabled",true);
				  window.location.href=encodeURI(encodeURI("exportApplyList.do?name="+$("#name").val()+"&&telephone="+$("#telephone").val()+"&&borrowAmount="+$("#borrowAmount").val()));
				setTimeout("test()",4000);
				});
		});
		
		function test(){
			$("#excel").attr("disabled",false);
			}	
		
		function initListInfo(praData){
	 		$.post("queryEnterpriseApplyList.do",praData,initCallBack);
	 	}
	 	
	 	function initCallBack(data){
			$("#dataInfo").html(data);
		}
		
		
	 	function del(id){
	 		if(!window.confirm("确定要删除吗?")){
	 			return;
	 		}
	 		
	 	}
	 	
	 	function checkAll(e){
	   		if(e.checked){
	   			$(".downloadId").attr("checked","checked");
	   		}else{
	   			$(".downloadId").removeAttr("checked");
	   		}
		}
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="enterpriseAddBorrowInit.do">企业融资</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
								&nbsp;<a href="addApplyInit.do">添加企业融资</a>
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
								<td class="f66" align="left" width="80%" height="36px">
										联系人：
										<input id="name" type="text"/>&nbsp;&nbsp;
										联系电话：
										<input id="telephone" type="text"/>&nbsp;&nbsp;
										借款金额：
										<input id="borrowAmount" type="text"/>&nbsp;&nbsp;
										<input id="excel" type="button" value="导出Excel" name="excel" />
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