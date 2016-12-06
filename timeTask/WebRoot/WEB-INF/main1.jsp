<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/base.css"/>
  <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/main/main.css"/>
  <script src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
  <script>
  $(function(){
	var  $window ,$middle,$menuA,$menuLeft,$mainconter,
		 windowWidth,
		 windowHeight;
		 
		 $window = $(window);
		 $middle = $('#mainconeer');
		 $menuA = $('#menuA');
		 $menuLeft = $('#menuLeft');
		 $mainconter = $('#mainconter');
		 
		 
		 windowWidth = $window.width(); 
		 windowHeight = $window.height(); 
		 $middle.css('height',windowHeight-107-30);
		 $menuA.css('width',windowWidth-337);
		 $menuLeft.css("height",windowHeight-107-30);
		 $mainconter.css("height",windowHeight-107-30-30);
		 $mainconter.css("width",windowWidth-220);
		 
		 $('.nve1').css("width",windowWidth-285);
		 $('.nve2').css("left",windowWidth-7);
	  })
	  
	  
	  function repay(){
	  
	  $('#myframe').attr('src',"<%=request.getContextPath()%>/repay_toRepayList.action");
	  
	  }
	  // 合同补录
	  function blpact(){
	  	$('#myframe').attr('src',"<%=request.getContextPath()%>/putout_toAdditiList.action");
	  }
	  // 生成还款计划
	  function creatPlan(){
	  	$('#myframe').attr('src',"<%=request.getContextPath()%>/plan_creatPlan.action?dueNo=222222");
	  }
	 
	  
  
  </script>
  </head>
  <body>
  	<!-- 顶部开始 -->
  	<div id="top"  class="topdiv">
  		<div class="logo" ></div>
  		<div class="firstMenu"  id="menuA">
  		
  		</div>
  	</div>
  	<!-- 顶部结束 -->
	<!-- 阴影 -->
  	<div class="bottomShadow"></div>
  	
  	<!--中间部分开始 -->
  	
  	<div id="mainconeer"  class="middlediv">
  			<div class="leftMenu"   id="menuLeft">
  				<ul>
  					<li onclick="repay()"> 还款管理</li>
  					<li onclick="blpact()"> 合同补录</li>
  					<li onclick="creatPlan()">还款计划</li>
  				</ul>
  			</div>
  			<div class="nve"></div>
  			<div class="nve1"></div>
  			<div class="nve2"></div>
  			
  			
  			<div class="mainframe"  id="mainconter" >
  				<iframe id="myframe" style="width:100%; height:100%;border:0px;padding0px"  src="http://www.baidu.com"  ></iframe>
  			</div>
  	</div>
  	<!--中间部分结束 -->
  		
  	<div id="bottom"  class="bottomdiv"></div>
  	
  	
  </body>
</html>
