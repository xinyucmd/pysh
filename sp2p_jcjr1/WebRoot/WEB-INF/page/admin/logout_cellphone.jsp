<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>手机号注销</title>
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
		var praData = {};
	    $(function(){
		  	
		    $("#cell1").click(function(){
		    	praData["paramMap.cell1"] = $("#cell1").val();
				praData["paramMap.cell2"] = '03321152594';
		    	var url = "updateUserByCell.do";
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	alert(data.msg);
				    	window.location.href="logoutCellphone.do";
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				       alert(errorThrown);
				    }
				});
		    });
		    
		    $("#mlNote").click(function(){
		    	praData["paramMap.cell1"] = $("#mlNote").val();
				praData["paramMap.cell2"] = '03718853545';
		    	var url = "updateUserByCell.do";
				$.ajax({
				    type: "post",
				    url: url,
				    dataType: "json",
				    data:praData,
				    success: function (data) {
				    	alert(data.msg);
				    	window.location.href="logoutCellphone.do";
				    },
				    error: function (XMLHttpRequest, textStatus, errorThrown) {
				       alert(errorThrown);
				    }
				});
		    });
		    
	    });
	</script>

	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							手机号注销
						</tr>
						<br/>
						<tr>
							 <input id="cell1" type="button" value="13321152594" name="cell1" class="form-control" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <input id="mlNote" type="button" value="13718853545" name="mlNote" class="form-control" />
						</tr>
						
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
