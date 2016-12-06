<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>管理首页</title>
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
	    	param["pageBean.pageNum"]=1;
		    initListInfo(param);
		  	
		    $("#search").click(function(){
		    	param["pageBean.pageNum"] = 1;
				initListInfo(param);
		    });
		    
		    $("#doDebt").click(function(){
		    	var param = {};
		    	param["debtArr"] = JSON.stringify(debtPriceArr);
		    	param["alieneeName"] = $("#alieneeName").val();
		    	param["eqsDeadline"] = $("#eqsDeadline").val();
	   			
		    	$.shovePost("addAuctingDebt.do",param,addAuctingDebtHandller);
	   		});
		   
	    });

		function addAuctingDebtHandller(data){
			if(data != null){
				var status = data.status;
				var msg = data.msg;
				
				// 刷新页面
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				alert(msg);
			}else{
				alert("前端函数调用错误！");	
				
			}
		}
	    
	   function initListInfo(praData) {
		   param["paramMap.alienatorName"] = $("#alienatorName").val();
		   param["paramMap.debtAnnualRate"] = $("#debtAnnualRate").val();
		   param["paramMap.debtDeadline"] = $("#debtDeadline").val();
		   param["paramMap.eqsDeadline"] = $("#eqsDeadline").val();
		   
	   		$.shovePost("auditRaiseIndexInfo.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		var debtPriceArr =  null;
   		function checkAllDebt(){
   		 	if($("#checkBoxAll").attr("checked")=="checked" || $("#checkBoxAll").attr("checked")=="on"){
	   		 	$("#gvNews input[type='checkbox']").each(function(){
	   		 		$("#"+this.id).attr("checked",true);
	   		 	});
   		 	}else{
	   		 	$("#gvNews input[type='checkbox']").each(function(){
	   		        $("#"+this.id).attr("checked",false);                   
	   		    });
   		 	}
   		 	
   		 	buildPriceArr();
   			sumDebtPrice();
   		}
   		
   		// 刷新 
   		function checkDebt(){
   			buildPriceArr();
   			sumDebtPrice();
   		}
   		
   		function buildPriceArr(){
   			debtPriceArr = new Array();
   			$("#gvNews input[type='checkbox']").each(function(){
   				if($("#"+this.id).attr("checked")=="checked" || $("#"+this.id).attr("checked")=="on"){
	   		     	if(this.id != "checkBoxAll"){
		   		     	var o = {};
		   		     	o["investId"] = this.id;
		   		        o["debtPrice"]=$("#"+this.id+"_debtPrice").attr("value");
		   		        o["debtValue"] = $("#"+this.id+"_debtValue").attr("value");
		   		        o["borrowId"]  = $("#"+this.id+"_borrowId").attr("value");
		   		        o["investor"]  = $("#"+this.id+"_investor").attr("value");
		   		     	o["investorName"]  = $("#"+this.id+"_investorName").attr("value");
		   		     	o["annualRateDebtBDDouble"] = $("#"+this.id+"_annualRateDebtBDDouble").attr("value");
		   		    	o["lastRepayment"] = $("#"+this.id+"_lastRepayment").attr("value");
		   		     
		   		     	debtPriceArr.push(o);
	   		     	}
   				}
   		    });
   		}
   		
   		function sumDebtPrice(){
   			var debtPriceSum = 0;
   			for(var i=0;i<debtPriceArr.length;i++){
   				debtPriceSum += parseFloat(debtPriceArr[i].debtPrice);
   			}
   			
   			$("#debtPriceSum").html(debtPriceSum.toFixed(2));
   		}
   		
	</script>

	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryInvestDebtApplyInit.do">债权投资审核</a>
								</td>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryAuctingAssignmentDebtInit.do">转让债权中</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28" class="xxk_all_a">
									<a href="querySuccessAssignmentDebtInit.do">转让成功</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28" class="main_alll_h2">
									<a href="queryFailDebtInit.do">转让失败</a>
								</td>
								
								<td>
									&nbsp;
								</td>
								
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryFailDebtInit.do">筹集债权</a>
								</td>
								
							</tr>
						</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									转让人：
									<input id="alienatorName" name="paramMap.alienatorName" />&nbsp;&nbsp; 
									转让利率：
									<input id="debtAnnualRate" name="paramMap.debtAnnualRate" />&nbsp;&nbsp; 
									转让期限：
									<input id="debtDeadline" name="paramMap.debtDeadline" />&nbsp;&nbsp; 
									固定期限:<input id="eqsDeadline" name="paramMap.eqsDeadline" />(0，固定，其他均大于转让期限)
									<input id="search" type="button" value="查找" name="search" />
									<input id="doDebt" type="button" value="确认转让" name="doDebt" />
								</td>
							</tr>
							
							<!-- 转让参数设置 
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									受让人：
									<input id="alieneeName" name="paramMap.alieneeName" />&nbsp;&nbsp; 
									投标密码：
									<input id="alieneePwd" name="paramMap.alieneePwd" type="password"/>&nbsp;&nbsp; 
									
								</td>
							</tr>-->
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
				
			</div>
		</div>
	</body>
</html>
