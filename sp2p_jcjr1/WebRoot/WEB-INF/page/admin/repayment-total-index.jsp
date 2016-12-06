<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
<link href="../../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<link href="../script/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery.pagination.js"></script>
<style>
	body { font-size: 14px;  font-family: "微软雅黑";}
	* { margin: 0; padding: 0; }
	ul, ol { list-style: none; }
	em, i { font-style: normal; }
	img { border: none; vertical-align: middle; }
	.clear { clear: both; }
	.clearfix { zoom: 1; }
	.clearfix:after { display: block; visibility: hidden; content: '.'; height: 0; clear: both; }
	a { font-family: "微软雅黑"; text-decoration: none; color: #666; cursor: pointer; }
	#right{ overflow:hidden; width:auto; margin-top:10px;}
	.right_title{}
	.right_title .cen_input{border: 1px solid #d9d9d9; height: 22px; line-height: 22px;}
	.right_title .cen_btn{border: 1px solid #d9d9d9; height: 24px; line-height: 22px; width:60px; margin:0 10px; cursor:pointer;}
	.right_title span{ margin-left:10px;}
   .zh_table td {border: 1px solid #ddd;text-align: center; width:12.5%; height:22px; line-height:22px;}
   .zh_table1_1 td {border: 1px solid #ddd;text-align: center; width:10%; height:40px; line-height:40px;}
   .zh_table1_1 th {background-color: #e8ecef;border: 1px solid #ddd;font-weight: normal;height: 40px;text-align: center; width:10%;}
</style>
<script>
jQuery(document).ready(function() {
	$('#theme-login').click(function(){
		$('#div_check').slideToggle();
	})
	$('#div_check').dblclick(function(){
		$('#theme-login').click();
	})
	 var ss=<%=request.getAttribute("repayStatus")%>;
	 if(ss==2){
		 $('#repayForms').css('display','block');
		 $('#repayForm').css('display','none');
		 $('#s1').removeClass("main_alll_h2");
		 $('#s2').addClass("main_alll_h2");
		 $('#s1').addClass("xxk_all_a");
		 if('${ status}'=='2'){
	    	  var obj=$('#repayForms #selectId2');
	    	  obj.attr('checked','checked');
	      }else if('${ status}'=='3'){
	    	  var obj=$('#repayForms #selectId3');
	    	  obj.attr('checked','checked');
	      }else if('${ status}'=='4'){
	    	  var obj=$('#repayForms #selectId4');
	    	  obj.attr('checked','checked');
	      }else{
	    	  var obj=$('#repayForms #selectId1');
	    	  obj.attr('checked','checked');
	      }
	 }else if(ss==1){
		 $('#repayForm').css('display','block');
		 $('#repayForms').css('display','none');
		 $('#s2').removeClass("main_alll_h2");
		 $('#s1').addClass("main_alll_h2");
		 $('#s2').addClass("xxk_all_a");
		   if('${status}'=='2'){
	  	  var obj=$('#repayForm #selectId2');
	  	  obj.attr('checked','checked');
	    }else if('${status}'=='3'){
	  	  var obj=$('#repayForm #selectId3');
	  	  obj.attr('checked','checked');
	    }else if('${status}'=='4'){
	  	  var obj=$('#repayForm #selectId4');
	  	  obj.attr('checked','checked');
	    }else{
	  	  var obj=$('#repayForm #selectId1');
	  	  obj.attr('checked','checked');
	    } 
	 }
})
</script>
<script type="text/javascript">
   
  function repayContions(){
	 var obj=document.getElementById("selectId4");
	 if(obj.checked){
	 var startTime = $('#startTime').val();
	 var  endTime = $('#endTime').val();
	 if(startTime==""){
		 alert('请选择时间范围');
		 return;
	 }
	 if(endTime==""){
		 alert('请选择时间范围');
		 return;
	 }
	 if(endTime<startTime){
		 alert('时间范围不合法');
		 return;
	 }
  }
	$('#repayForm').submit();
  }
  function repayConditions(){
		 var obj=document.getElementById("selectId4");
		 if(obj.checked){
		 var startTime = $('#startTime').val();
		 var  endTime = $('#endTime').val();
		 if(startTime==""){
			 alert('请选择时间范围');
			 return;
		 }
		 if(endTime==""){
			 alert('请选择时间范围');
			 return;
		 }
		 if(endTime<startTime){
			 alert('时间范围不合法');
			 return;
		 }
	  }
		$('#repayForms').submit();
	  }
</script>
</head>

<body>

	<div id="right">
	<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" height="28"  class="xxk_all_a">
								<a href="sysBaseStatisInit.do">用户统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="investAll.do">投资统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							 <td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="giveBiao.do">发标统计</a>
							</td> 
							<td>
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="touBiao.do">投标统计</a>
							</td> 
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="s1" >
								<a href="RepayMentList.do?repayStatus=1">待收统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="withdrawAll.do">提现统计</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="s2"  >
								<a href="RepayMentList.do?repayStatus=2">还款统计</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					</div>
    	<div class="right_title">
    	    <form id="repayForm" action="RepayMentList.do?repayStatus=1" method="post">
	        	<span><label><input id="selectId1" type="radio"  name="selectName"  value="1"/>昨日</label></span>
	        	<span><label><input id="selectId2" type="radio"  name="selectName" value="2" />本周</label></span>
	        	<span><label><input id="selectId3" type="radio"  name="selectName"  value="3"/>本月</label></span>
	        	
	        	<span>
	        	      <label><input id="selectId4" type="radio" name="selectName" value="4"/>其他时间</label>
	        	      <input id="startTime" name="startTime" value="${startTime}" type="text" class="cen_input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>至
	        	      <input id="endTime" name="endTime" value="${endTime}" type="text" class="cen_input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	           </span>
 	           <input type="hidden" name="repayStatus" id="repayStatus" value="1"/>
  <%-- 	            <span>用户组：<s:select id="group" name="group" list="#{'1':'真实用户','2':'机构账户','3':'线下理财人'}"  headerKey="-1" headerValue="--请选择--"></s:select></span> 
 --%>	           <input name="" type="button"  value="查找" class="cen_btn" onclick="repayContions()"/>
	           <span id="showEorrs"></span>
	       </form>
	        <form id="repayForms" action="RepayMentList.do?repayStatus=2" method="post">
	        	<span><label><input id="selectId1" type="radio"  name="selectName"  value="1"/>昨日</label></span>
	        	<span><label><input id="selectId2" type="radio"  name="selectName" value="2" />本周</label></span>
	        	<span><label><input id="selectId3" type="radio"  name="selectName"  value="3"/>本月</label></span>
	        	
	        	<span>
	        	      <label><input id="selectId4" type="radio" name="selectName" value="4"/>其他时间</label>
	        	      <input id="startTime" name="startTime" value="${startTime}" type="text" class="cen_input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>至
	        	      <input id="endTime" name="endTime" value="${endTime}" type="text" class="cen_input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
	           </span>
 	           <input type="hidden" name="repayStatus" id="repayStatus" value="1"/>
  <%-- 	            <span>用户组：<s:select id="group" name="group" list="#{'1':'真实用户','2':'机构账户','3':'线下理财人'}"  headerKey="-1" headerValue="--请选择--"></s:select></span> 
 --%>	           <input name="" type="button"  value="查找" class="cen_btn" onclick="repayConditions()"/>
	           <span id="showEorrs"></span>
	       </form>
        </div>
        <div style="width:1200px; margin-top:20px;">
        	<table class="zh_table" width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-top:10px;">
                <tbody>
                    <tr>
                        <td>总累计金额<br/>（元）</td>
                        <td style="color:#ff0000">${allRepay }</td>
                        <td>总新增金额<br/>（元）</td>
                        <td style="color:#ff0000">${allRepay1 }</td>
                    </tr>
                     <tr>
                        <td >真实用户累计金额<br/>（元）</td>
                        <td style="color:#ff0000">${allRepay2 }</td>
                        
                        <td>真实用户新增金额<br/>（元）</td>
                        <td style="color:#ff0000">${allRepay3 }</td>
                    </tr>
                    <tr>
                        <td>机构用户累计金额<br/>（元）</td>
                        <td>${allRepay4 }</td>
                        <td>机构用户新增金额<br/>(元)</td>
                        <td>${allRepay5 }</td>
                    </tr> 
                    <tr>
                        <td>线下理财人累计金额<br/>（元）</td>
                        <td >${allRepay6 }</td>
                        <td>线下理财人新增金额<br/>（元）</td>
                        <td >${allRepay8 }</td>
                    </tr>
                </tbody>
           </table>	
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
   
    
    </script>