<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <meta http-equiv="keywords" content="个人投资理财|互联网理财|P2P理财产品|P2P收益|p2p网贷理财">
    <meta http-equiv="description" content="投资理财找微信贷，微信贷提供高收益P2P理财产品，为个人和企业提供透明、安全、高效的互联网金融服务。">
<style>
	.banner-main{ width:980px; margin:0 auto; color:#fff; font-family:"微软雅黑";  overflow:hidden; height:225px; position:relative;}
	.banner-main-left{ float:left; height:225px; width:730px;}
	.top-img{ background:url(images/small-bg.png) no-repeat; width:260px; height:43px;  font-size:18px; color:#060101; font-weight:500; padding-top:10px;text-align:center;}
	.banner-main-con{  height:90px; margin-top:15px;}
	.con-list{ float:left;}
	.con-list ul{ font-family:"宋体";}
	.con-list li{ float:left; text-align:center;}
	.con-list li em{  font-style:normal; position:relative;}
	.con-list li i{  font-style:normal; position:relative; }
	.con-list li .radius-write{text-align:center; margin:0 auto; overflow:hidden; height:55px;}
	.btn-green{ float:right; height:41px; margin-right:20px;}
	.btn-green a{ background:url(images/btn-write.png) no-repeat;  height:41px; padding:4px 90px; position:relative;  display:block;}
	.btn-green a:hover{ background:url(images/btn-red.png) no-repeat; height:41px; padding:4px 90px;}
	.font18{  font-size:18px; margin-top:10px; text-align:center; font-weight:bold; }
	.foot-rule{ font-size:15px; margin-top:10px; height:50px;}
</style>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="s_top-banner">
    <div class="banner-main">
        <div class="banner-main-left">
           <div class="top-img">${borrowNewMap.name }</div>
           <!--<div style="position:absolute; top:14px; left:300px;"><img src="images/tui.png" width="414" height="40" /></div>-->
      		   <div class="banner-main-con">
                    <ul class="con-list">
                        <li style="width:190px;">
                            <p class="radius-write"><em style="font-size:47px;">${borrowNewMap.rate}</em><i style="font-size:31px;">%</i></p>
                            <p class="font18">年化利率</p>
                        </li>
                        <li style="width:130px;">
                            <p class="radius-write"><em style="font-size:49px;">${borrowNewMap.day}</em><i style="font-size:34px;">天</i></p>
                            <p class="font18">投资期限</p>
                        </li>
                        <li style="width:215px;" >
                            <p class="radius-write">
                                <em style="font-size:45px;">
                                   ${borrowNewMap.amount_sum/10000}
                                </em>
                             </p>
                            <p class="font18">借款金额（万元）</p>
                        </li>
                        <li style="width:195px;">
                            <p class="radius-write">
                              <em style="font-size:47px;">
                                 ${borrowNewMap.amount}
                              </em>
                              <i style="font-size:31px;">%</i>
                            </p>
                            <p class="font18">已投： ${borrowNewMap.amount_able/10000}万元</p>
                        </li>
                    </ul>  
           	</div>
           <div class="foot-rule">
           		<div style="float:left; position:relative; top:15px; left:5px; font-weight:bold;">注：体验标仅限使用体验金投资&nbsp;&nbsp;还款方式：一次性还款100%本息保障</div>
                <div class="btn-green"><a href="queryTyjInvestDetail.do?borrowId=${borrowNewMap.id}"></a></div>
                <!-- 
                 <div class="btn-green"><a href="queryEmployeeBorrowDetail.do"></a></div>
                 -->
               
           </div>
        </div>
    </div>
</div>
   	<input id="pageNum" name="curPage" type="hidden"/>
    <input  id="m" name="m" type="hidden"  />
    <input  name="title" type="hidden" value="${ paramMap.title}"/>
    <input id="type" name="type" type="hidden" />
<div class="s_touzi-list clearfix">
	<ul class="s_touzi-option clearfix">
		<li class="li1" id="flowbid">
			<dl>
				<dt>活利宝</dt>
				<dd>周期短  门槛低  收益快</dd>
			</dl>
			<i></i>
		</li>
		<li class="li2" id="shidibid">
			<dl>
				<dt>定息宝</dt>
				<dd>省时省心  长期收益</dd>
			</dl>
			<i></i>
		</li>
		<li class="li3" id="xinyongbid">
			<dl>
				<dt>优选宝</dt>
				<dd>理财优选 灵活自主</dd>
			</dl>
			<i></i>
		</li>
		<li class="li4" id="debtbid">
			<dl>
				<dt>交易宝</dt>
				<dd>自由转让 增加资金流动性</dd>
			</dl>
			<i></i>
		</li>
	</ul>
	<s:if test="paramMap.type==4">
    <p style="text-align:right; position:absolute; width:980px;">       
            <a href="bespeakInvest.do"><img src="../images/btn-small.jpg" width="82" height="32"></a>
    </p>
	</s:if>
	<s:if test="paramMap.type==7">
		<!-- 债权转让列表-->
		<div class="s_touzi-list s_zhuanrang-list clearfix">
			<div class="s_touzi-pros">
            	
				 <s:if test="pageBean.page==null || pageBean.page.size<=0">
				    <li style="text-align: center;padding-top: 20px;padding-bottom: 20px;">没有数据</li>
				  </s:if>  
				  <s:else>
					   <s:iterator value="pageBean.page" var="bean">
			   						<dl>
										<dt><a href="queryDebtDetail.do?id=${id}" target="_self">${borrowTitle }</a></dt>
										<dd>
											<ul class="clearfix">
												<li class="li1" style="width:90px;"><strong class="orange">${annualRate}%</strong> </li>
												<li class="li2" style="width:100px;"><strong class="orange"><s:property value="#bean.deadline" default="0"/>
                        <s:if test="%{#bean.isDayThe ==1}">个月</s:if>
                        <s:else>天</s:else></strong></strong></li>
                         						<s:if test="%{#bean.debtPrice >0}">
													<li class="li3" style="width:180px;"><strong>${debtPrice}</strong></li>
												</s:if>
												<li class="li4"><span></span></li>
												<li class="li5">
									<s:if test="%{#bean.isDayThe ==2}">
							    	 到期还本付息
							    	</s:if>
							    <s:elseif test="%{#bean.paymentMode == 1}">等额本息</s:elseif>
							    <s:elseif test="%{#bean.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#bean.paymentMode == 3}">秒还</s:elseif>
							     <s:elseif test="%{#bean.paymentMode == 4}">一次性还款</s:elseif>
							</li>
												<li class="li7">
												<s:if test="#bean.debtStatus ==0"><a class="join-btn">申请中</a></s:if>
										    	<s:elseif test="#bean.debtStatus==1"><a class="join-btn" href="queryDebtDetail.do?id=${id}" target="" >立即购买</a></s:elseif>
										    	<s:elseif test="#bean.debtStatus==2"><a class="join-btn" style="background: none repeat scroll 0 0 #959595">已转让</a></s:elseif>
										    	<s:else>&nbsp;</s:else></li>
											</ul>
										</dd>
										<dd>
											<ul class="clearfix">
											<%-- 	//whb去掉竞拍<li class="li8">竞拍方式：<strong class="orange"><s:if test="#bean.auctionMode ==1">明拍</s:if><s:else>暗拍	</s:else></strong></li>
												<li class="li9">竞拍底价：<strong class="orange">${auctionBasePrice}元</strong></li> --%>
												<li class="li10">剩余购买时间：<strong class="orange">${remainDays}</strong></li>
											</ul>
										</dd>
									</dl>
					   </s:iterator>
				   </s:else>
				<s:if test="pageBean.page!=null || pageBean.page.size>0">
	    <div class="s_foot-page" sytle="width:1000px;">
	                    <p>
	                       <shove:page url="finance.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
						        <s:param name="m">${paramMap.m}</s:param>
						        <s:param name="type">${paramMap.type}</s:param>
						   </shove:page>
	                    </p>
	   </div>    
	    </s:if>

			</div>
		</div>
	</s:if>
	<s:else>
		<!-- 标的列表 -->
	<div class="s_touzi-pros">
		<s:if test="pageBean.page!=null || pageBean.page.size>0">
			<s:iterator value="pageBean.page" var="finance">
				<dl>
					<dt><a href="financeDetail.do?id=${finance.id}" ><shove:sub size="15" value="#finance.borrowTitle" suffix="..."/></a>
						<s:if test="%{#finance.hasPWD ==1}">
							<img src="images/reserve.png" style="position: relative; top: -2px;"/>
						</s:if><s:else></s:else>
					</dt>
					<dd>
						<ul class="clearfix">
							<li class="li1"><strong class="orange"><s:property value="#finance.annualRate" default="0"/>%</strong>
							     <s:if test="%{#finance.hasPWD !=1}">
							     <s:if test="%{#finance.add_interest !=0}">
								     <span style="background: none repeat scroll 0 0 red;
								                   border-radius: 4px;color: #fff;font-size: 12px;
								                   font-weight: bold;padding: 0 2px;
								                   position:absolute;top:-4px; left:90px;">+<s:property value="#finance.add_interest"/>%
								                   
								     </span>
							     </s:if>
							     </s:if>
                            </li>
							<li class="li2"><strong class="orange"><s:property value="#finance.deadline" default="0"/>
                        <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                        <s:else>天</s:else></strong></li>
							<li class="li3"><strong><s:property value="#finance.borrowAmount" default="0"/></strong></li>
							<li class="li4"><img src="images/s_pic38.png" /></li>
							<li class="li5">
							<s:if test="%{#finance.isDayThe ==2}">
							    	 到期还本付息
							    	</s:if>
							    <s:elseif test="%{#finance.paymentMode == 1}">按月分期还款</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 3}">秒还</s:elseif>
							     <s:elseif test="%{#finance.paymentMode == 4}">一次性还款</s:elseif></li>
							<li class="li6">
								<div class="s_jindu">
								<s:iterator value="{10,20,30,40,50,60,70,80,90,100}" id="number">
									<s:if test="%{#finance.schedules>=#number}"><span class="done"></span></s:if>
									<s:else><span></span></s:else>
								</s:iterator>
								</div>
								<span class="s_jindu-num">完成进度<b><s:property value="#finance.schedules" default="0"/>%</b></span>
							</li>
							<s:if test="%{#finance.borrowStatus == 1}">
					             <li class="li7"><a class="join-btn">初审中</a></li>
					        </s:if>
					         <s:elseif test="%{#finance.borrowStatus == 2}">
					         <li class="li7">
					         	<s:if test="%{#finance.borrowShow == 2}"><a class="join-btn" href="financeDetail.do?id=${finance.id}">立即认购</a></s:if>
					         	  <s:else><a class="join-btn" href="financeDetail.do?id=${finance.id}">立即投标</a></s:else>
					         </li>
					        </s:elseif>
					        <s:elseif test="%{#finance.borrowStatus == 3}">
					            <li class="li7"><a class="join-btn" >复审中</a></li>&nbsp;
					        </s:elseif>
					        <s:elseif test="%{#finance.borrowStatus == 4}">
					        <li class="li7"><a class="join-btn">
					        	<s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
					          	<s:else>还款中</s:else></a></li>&nbsp;
					        </s:elseif>
					        <s:elseif test="%{#finance.borrowStatus == 5}">
					           <li class="li7"><a class="join-btn">已还完</a></li>&nbsp;
					        </s:elseif>
					        <s:else>
					              <li class="li7"><a class="join-btn">流标</a></li>&nbsp;
					        </s:else>
						</ul>
					</dd>
				</dl>
			</s:iterator>
		</s:if>
		<s:else>
            <li style="text-align: center;padding-top: 20px;padding-bottom: 20px;">没有数据</li>
        </s:else>
     <s:if test="pageBean.page!=null || pageBean.page.size>0">
	    <div class="s_foot-page" sytle="width:1000px;">
	                    <p>
	                       <shove:page url="finance.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
						        <s:param name="m">${paramMap.m}</s:param>
						        <s:param name="type">${paramMap.type}</s:param>
						   </shove:page>
	                    </p>
	   </div>    
	    </s:if>  
	</div>
	</s:else>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("#flowbid").click(function(){
			$("#type").val("6");
			window.location.href="finance.do?m=1&type="+$("#type").val();
		});

		$("#shidibid").click(function(){
			$("#type").val("4");
			window.location.href="finance.do?m=1&type="+$("#type").val();
		});
		
		$("#xinyongbid").click(function(){
			$("#type").val("3");
			window.location.href="finance.do?m=1&type="+$("#type").val();
		});

		$("#debtbid").click(function(){
			$("#type").val("7");
			window.location.href="finance.do?m=1&type="+$("#type").val();
		});
		
	});
</script>
<script type="text/javascript">
$(document).ready(function(){
	    var ck_type = "${paramMap.type}";
	    var no = ck_type.split(',');
	    if(no != ''){
	       for(var i=0;i<no.length;i++){
	          $('#ck_mode_'+no[i]).attr('checked','checked');
	       }
	    }	
});

function  checkTou(id,type){
	 var param = {};
	 param["id"] = id;
     $.shovePost('financeInvestInit.do',param,function(data){
	   var callBack = data.msg;
	   if(callBack !=undefined){
	     alert(callBack);
	   }else{
		   if(type==2){
				var url = "subscribeinit.do?borrowid="+id;
		     	 $.jBox("iframe:"+url, {
			    		title: "我要购买",
			    		width: 450,
			    		height: 450,
			    		buttons: {  }
			    		});
			}else{
				 window.location.href= 'financeInvestInit.do?id='+id;
		   }
	   }
	 });
}
function closeMthod(){
	window.jBox.close();
	window.location.reload();
}

function clearComponentVal(){
        param = {};
        $('#titles').val('');
        $('#paymentMode').get(0).selectedIndex=0;
        $('#purpose').get(0).selectedIndex=0;
        $('#deadline').get(0).selectedIndex=0;
        $('#reward').get(0).selectedIndex=0;
        $('#arStart').get(0).selectedIndex=0;
}


		function rateNumJudge(){//验证利息计算输入数字是否正确
	 	   if($("#borrowSum").val()==""){
	 	      $("#show_error").html("&nbsp;&nbsp;投资金额不能为空");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())){
	 	       $("#show_error").html("&nbsp;&nbsp;请输入正确的投资金额进行计算");
	 	       $("#showIncome").html("");
	 	   }else 
	 	   if($("#yearRate").val()==""){
	 	      $("#show_error").html("&nbsp;&nbsp;年利率不能为空");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())){
	 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的年利率");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if($("#borrowTime").val()==""){
	 	       $("#show_error").html("&nbsp;&nbsp;投资期限不能为空");
	 	       $("#showIncome").html("");
	 	   }else 
	 	   if(!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())){
		 	    $("#show_error").html("&nbsp;&nbsp;请输入正确投资期限");
		 	    $("#showIncome").html("");
	 	   }else 
	 	    if(!/^[0-9].*$/.test($("#bidReward").val())){
		 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的投资奖励");
		 	      $("#showIncome").html("");
	 	   }else 
		 	if(!/^[0-9].*$/.test($("#bidRewardMoney").val())){
			 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的现金奖励 ");
			 	      $("#showIncome").html("");
	 	    }else{
	 	      $("#show_error").html("");
	 	   }
	 	}
	 	
	 	function rateCalculate(){//利息计算
	 	    var str = rateNumJudge();
	 	    param["paramMap.borrowSum"] = $("#borrowSum").val();
	        param["paramMap.yearRate"] = $("#yearRate").val();
	        param["paramMap.borrowTime"] = $("#borrowTime").val();
	        param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
	        param["paramMap.bidReward"] = $("#bidReward").val();
	        param["paramMap.bidRewardMoney"] = $("#bidRewardMoney").val();
	        
	        var _array = [];
	        
			if($("#show_error").text()!=""){
			   return;
			}
			$.shovePost("incomeCalculate.do",param,function(data){
			   //只有一条记录
			   if(data == 1){
			     $("#show_error").html("请填写完整信息进行计算");
			     return;
			   }
			   _array.push("<table>");
			    for(var i = 0; i < data.length; i ++){
			    	data[i].income2year = data[i].income2year < 1 ? "0" + data[i].income2year : data[i].income2year;
			    	data[i].rateSum = data[i].rateSum < 1 ? "0" + data[i].rateSum : data[i].rateSum;
					_array.push("<tr><td style='padding-left:20px'><span>投标奖励：</span><span>"+data[i].reward+"元</span><br/>"
					+"<span>年化收益：</span><span>"+data[i].income2year+"%</span><br/>"
					+"<span>总计利息：</span><span>"+data[i].rateSum+"元</span><br/>"
					+"<span>每月还款：</span><span>"+data[i].monPay+"元</span><br/>"
					+"<span>总共收益：</span><span>"+data[i].allSum+"元</span><br/>"
					+"<span>总计收益：</span><span>"+data[i].netIncome+"元(扣除10%管理费)</span></td></tr>");
					/*_array.push("<p>投标奖励："+data[i].reward+"元</p><br /><br />"
					+"<p>年化收益："+data[i].income2year+"元</p><br /><br />"
					+"<p>总收益："+data[i].allSum+"元</p><br /><br />"
					+"<p>每月还款："+data[i].monPay+"元</p><br /><br />"
					+"<p>总计收益(扣除10%管理费)："+data[i].netIncome+"元</p>");*/
				}
				//_array.push("</table>");
				$("#showIncome").html(_array.join(""));
			});
	 	}

</script>
<script type="text/javascript">
		$(function(){
			//选取样式
			if('${type}'==null||'${type}'==''||'${type}'==6){
				//$("#type").val("6");
				$("#flowbid").addClass("hover");
			}else if('${type}'==4){
				$("#type").val("4");
				$("#shidibid").addClass("hover");
			}else if('${type}'==3){
				$("#type").val("3");
				$("#xinyongbid").addClass("hover");
			}else if('${type}'==7){
				$("#type").val("7");
				$("#debtbid").addClass("hover");
			} 
			$("#jumpPage").click(function(){
			     var curPage = $("#curPageText").val();
			    
				 if(isNaN(curPage)){
					alert("输入格式不正确!");
					return;
				 }
				 $("#pageNum").val(curPage);
		        
		        window.location.href = "finance.do?m=1&type="+$("#type").val()+"&curPage="+curPage+"";
			});
			dqzt(1);
			$("span#tit").each(function(){
				if($(this).text().length > 6){
					$(this).text($(this).text().substring(0,8)+"..");
				}
			});
	
			var m = '${paramMap.m}';
			if(m == ''){
				m = 1;
			}
			
			re= /select|update|delete|exec|count|'|"|=|;|>|<|%/i;
			$sMsg = "请您不要在参数中输入特殊字符和SQL关键字！"
			if ( re.test($("#title").val()) )
			{
			alert( $sMsg );
			return false;
			} 
			
			$("#tab_"+m).addClass("on");
			$("#tab_1").click(function(){
			    window.location.href = "finance.do?m=1"
			});
			$("#tab_2").click(function(){
			   window.location.href = "finance.do?m=2"
			});
			$("#tab_3").click(function(){
               window.location.href = "finance.do?m=3"
			});
			$("#tab_5").click(function(){
			   window.location.href = "finance.do?m=5"
			});
			
			
			$("#search").click(function(){
				var chk_value = [];
				$('input[name="ck_mode"]:checked').each(function(){  
		             chk_value.push($(this).val());  
		        });
		        if(chk_value.length != 0){
		             $("#type").val(chk_value);
		            $("#m").val('1');             
		        }else{
		            $("#type").val("");
		        }
		        
                $("#searchForm").submit();
			});
			$("#arEnd").change(function(){
			    var arStart = $('#arStart').val();
			    arStart = parseInt(arStart);
			    var arEnd = $(this).val();
			    arEnd = parseInt(arEnd);
			    if(arEnd < arStart){
			      alert('金额范围不能小于开始!');
			    }
			});
			$("#arStart").change(function(){
			    var arEnd = $('#arEnd').val();
			    arEnd = parseInt(arEnd);
			    var arStart = $(this).val();
			    arStart = parseInt(arStart);
			    if(arEnd < arStart){
			      alert('金额范围不能小于开始!');
			    }
			});
			
			
		});
		
	
</script>
<script>
 function investBth(){
	    var amount = '${borrowNewMap.amount}';
	    
	    var borrowId = '${borrowNewMap.id}';
		var param = {}
		param["borrowId"] = borrowId;
		$.shovePost("addTyjInvest.do",param,function(data){
		   alert(data.msg);
		});
 }
 
  
  
</script>
</body>
</html>