<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户管理-用户信息管理</title>
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
		<script type="text/javascript">
      
			
			function check(){  
			  param['paramMap.id'] = $("#id").val();
			  param['paramMap.state'] = $("input[name='paramMap.state']:checked").val();
			  
			  $.shovePost("updatestate.do",param,function(data){
			     var callBack = data.msg;
	      if(callBack == undefined || callBack == ''){
		                 $(this).show();
		                 }
			     if(data.msg == 1 ||data.msg == 0 ){
			     	alert("操作成功");
			     	var para1 = {};
			     	         window.parent.ffff();

			        return;
			     }else{
			       	alert(data.msg);
			        return;
			     }
			  });
		
			}
					//function cancel(){ 
			
			
		   // window.location.reload();
		    //window.location.ClosePop();
   			//}			
		</script>
	</head>
	<body>
		<div id="right"
			style="background-image: url(images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<strong>申请详情</strong>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							
							<tbody>
								<tr>
									<td class="f66" align="left" width="30%" height="36px">
										企业名称：&nbsp;&nbsp;
										${map.companyname} 
										<input type="hidden" id="id" value="<s:property value='#request.map.id'/>"/>
								  </td>
								  <td>
								    &nbsp;
								  </td>
								</tr>
								<tr>
									<td class="f66" align="left" width="30%" height="36px">
										注册号：&nbsp;&nbsp;
										${map.registnumber }
										&nbsp;&nbsp;
								  </td>
								  <td>
								    &nbsp;
								  </td>
								</tr>
								<tr>
									
									<td class="f66" align="left" width="30%" height="36px">
									联系人：
									  ${map.tname }
									</td>
								</tr>
								<tr>
								<td class="f66" align="left" width="50%" height="36px">
									联系电话：${map.telephone }
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="30%" height="36px">
									城市所在地：${map.cityaddress }
									
									</td>
									
								</tr>
								<tr>
								<td class="f66" align="left" width="50%" height="36px">
								          借款金额：${map.borrowAmount}元
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="30%" height="36px">
									借款期限：${map.deadline }个月
									</td>
									<td>
									&nbsp;
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="30%" height="36px">
									借款用途：${map.borrowPurpose }
									</td>
									<td class="f66" align="left" width="50%" height="36px">
									&nbsp;
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="30%" height="36px">
									状态：
     								<s:radio name="paramMap.state" list="#{1:'已处理',0:'未处理' }"/>
									
								</td>
								<td>
									&nbsp;
									</td>
							</tr>
							</tbody>
						</table>
						
					</div>
					<div
					 style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
				<input  type="button" id="borrowfabiao"  class="bcbtn" value="返回" onclick="window.parent.ffff();"/>				
				<input  type="button" id="btnSave"  class="bcbtn" value="修改状态" onclick="check();"/>				
					</div>
				</div>
			</div>
			</div>
	</body>
</html>