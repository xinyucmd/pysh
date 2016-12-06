<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>网站统计管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
		<div>
		<table width="90%">
		<tr >
			       <th class="main_alll_h2" align="left" >网站统计</th>
			    </tr>
			    <tr>
			      <td>&nbsp;</td>
			      <td align="right"><input id="excel" type="button" value="导出Excel" name="excel" /></td>
			    </tr>
		 </table>
		<table id="gvNews" style="width: 1120px; "
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 30%;" scope="col">
						统计项
					</th>
					<th style="width: 70%;" scope="col">
						金额
					</th>
				</tr>
					<tr class="gvItem">
						<td>
							网站会员总金额：
						</td>
						<td align="center">
							${webMap.webUserAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员真实总待收本金：
						</td>
						<td align="center">
							${webMap.webUserPrincipalAmount}
						</td>
					</tr>
					<tr class="gvItem">
						<td>
							网站会员真实总待收利息：
						</td>
						<td align="center">
							${webMap.webUserInterestAmount}
						</td>
					</tr>
			</tbody>
		</table>
</div>
</div>
   <div>
	</div>
</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
  $(function(){
      $("#excel").click(function(){
      $("#excel").attr("disabled",true);
        window.location.href="exportwebStatis.do";
        setTimeout("test()",3000);
      });
  
  
  });
  
  function test(){
			   $("#excel").attr("disabled",false);
			   }
</script>
</html>
