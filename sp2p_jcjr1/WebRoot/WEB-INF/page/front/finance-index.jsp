<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <meta http-equiv="keywords" content="个人投资理财|互联网理财|P2P理财产品|P2P收益|p2p网贷理财" />
    <meta http-equiv="description" content="投资理财找微信贷，微信贷提供高收益P2P理财产品，为个人和企业提供透明、安全、高效的互联网金融服务。" />
</head>
<body> 
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<!-- 隐藏表单 start -->
<input id="pageNum" name="curPage" type="hidden"/>
<input  id="m" name="m" type="hidden"  />
<input  name="title" type="hidden" value="${ paramMap.title}"/>
<input id="type" name="type" type="hidden" />
<!-- 隐藏表单 end -->

<!--投资列表-->
<div style="background:#f3f4f8; width:100%; padding-top:10px;">
	<div class="show-yu">
    	<a href="bespeakInvest.do"class="btn-red"  title="我要预约"></a>
    </div>
	<div class="s_touzi-list clearfix">
		<ul class="s_touzi-option clearfix">
			<li class="li1" id="shidibid">
				<dl>
					<dt>定息宝</dt>
					<dd>省时省心 长期收益</dd>
				</dl>
				<i></i>
			</li>
			<li class="li2" id="flowbid">
				<dl>
					<dt>活利宝</dt>
					<dd>周期短  门槛低  收益快</dd>
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
		
	     <!--即将发售  --> 
	     <s:if test="#request.borrowAllTime!=null">
		 <div class="s_touzi-pros" style="display:block;">
	    	<ul>
	    	    <s:iterator value="#request.borrowAllTime" var="finance">
	        	<li>
					<div class="touzi-top">
	                	<div class="touzi-top-title">
	                	   <a href="#" ><shove:sub size="15" value="#finance.borrowTitle" suffix="..."/></a>
	                	   <s:if test="%{#finance.hasPWD !=-1}">
	                	     <i class="yue-bg"><img src="/new_images/yue-icon.png" /></i>
	                	   </s:if>
	                	</div>
	                    <div class="touzi-top-rule"><span class="sp1">还款方式：
	                            <s:if test="%{#finance.isDayThe ==2}"> 到期还本付息</s:if>
							    <s:elseif test="%{#finance.paymentMode == 1}">按月分期还款</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 3}">秒还</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 4}">一次性还款</s:elseif>
	                    
	                    </span><span>起投金额：${finance.minTenderedSum}元</span></div>
	                </div>
	                <div class="touzi-bottom">
	                    	<ul>
	                        	<li class="investli-1">年化收益<em>${finance.annualRate}</em><i>%</i>
	                        	<s:if test="%{#finance.hasPWD ==-1}">
	                        	    <s:if test="%{#finance.add_interest >0}">
	                        	       <span class="list-add-xi">+${finance.add_interest}%</span>
	                        	    </s:if>
	                        	</s:if>
	                        	</li>
	                            <li class="investli-2">期限<em>${finance.deadline}</em><i>
	                            <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                                <s:else>天</s:else></i></li>
	                            <li class="investli-3">借款总额<em>${finance.borrowAmount}</em><i></i></li>
	                            <li class="investli-4">
	                            	<div class="jindu-list">
	                                  
	                                      <div class="cs-time">
	                                        <img src="/new_images/sz-icon.png"> 
	                                        <i class="cs-time-top"><s:date name="applyTime" format="yyyy-MM-dd HH:mm:ss"/></i>
	                                    </div>
	                                    <div>
	                                        <a href="###" class="but_gray">即将发售</a>
	                                    </div>
	                    			</div>
	                            </li>
	                        </ul>
	                </div>
	            </li>
	            </s:iterator>
	        </ul>
		</div>
		</s:if>  
		
		<!-- 债权转让 --> 
		<s:if test="paramMap.type==7">
		<div class="s_touzi-pros" style="display:block;">
	    	<ul>
	    	 <s:iterator value="pageBean.page" var="bean">
	        	<li>
					<div class="touzi-top">
	                	<div class="touzi-top-title">
		                	<s:if test="#bean.debtStatus !=2">
								<a href="queryDebtDetail.do?id=${id}" target="_self">${borrowTitle }</a>
							</s:if>
							<s:else>
								<a href="queryDebtDetail.do?id=${id}" target="_self">${borrowTitle }</a>
								<!-- <a href="#" target="_self">${borrowTitle }</a> -->
							</s:else>
	                	</div>
	                    <div class="touzi-top-rule">
	                       <span class="sp1" style=" position:relative; right:0;">还款方式：
	                                <s:if test="%{#bean.isDayThe ==2}">到期还本付息</s:if>
							        <s:elseif test="%{#bean.paymentMode == 1}">等额本息</s:elseif>
							        <s:elseif test="%{#bean.paymentMode == 2}">按月付息,到期还本</s:elseif>
							        <s:elseif test="%{#bean.paymentMode == 3}">秒还</s:elseif>
							        <s:elseif test="%{#bean.paymentMode == 4}">一次性还款</s:elseif>
						   </span>
	                     </div>
	                </div>
	                <div class="touzi-bottom">
	                    	<ul>
	                        	<li class="investli-1">预期年化利率<em><fmt:formatNumber value="${annualRateDebtBDDouble}" pattern="#0.00" /></em><i>%</i></li>
	                            <li class="investli-2">期限<em><s:property value="#bean.remainingDays" default="0"/></em><i>天</i>
	                            </li>
	                            <li class="investli-3">债权价格<em>${debtPrice}</em><i>元</i></li>
	                            <li class="investli-4">
	                            	<div class="jindu-list">
	                                   <div class="zr-time">
	                                                                                                      剩余购买时间：
	                                         <s:if test="%{remainDays!='过期'}">
	                                              <i class="time-end">${remainDays}</i>
	                                         </s:if>
	                                         <s:else>
	                                         	<i class="time-end">已完成</i>
	                                         </s:else>
	                                    </div>
	                                    <div>
	                                    	<s:if test="#bean.debtStatus ==0"><a class="join-btn">申请中</a></s:if>
	                                    	<s:elseif test="#bean.debtStatus==1">
										    		<s:if test="%{remainDays!='过期'}">
										    			<a class="but_avg" style="margin-top:30px;" href="queryDebtDetail.do?id=${id}" target="" >立即投标</a>
										    		</s:if>
										    		<s:else>
										    			<a class="but_gray" style="background: none repeat scroll 0 0 #959595">已完成</a>
										    		</s:else>
										    </s:elseif>
										    <s:elseif test="#bean.debtStatus==2">
									    		<a class="but_gray" style="background: none repeat scroll 0 0 #959595">转让成功</a>
									    	</s:elseif>
	                                    <!-- 
	                                        <s:if test="#bean.debtStatus ==0"><a class="join-btn">申请中</a></s:if>
										    <s:elseif test="#bean.debtStatus==1"><a class="but_avg" style="margin-top:30px;" href="queryDebtDetail.do?id=${id}" target="" >立即投标</a></s:elseif>
										    <s:elseif test="#bean.debtStatus==2"><a class="but_gray" style="background: none repeat scroll 0 0 #959595">转让成功</a></s:elseif>
	                                     -->
	                                    </div>
	                    			</div>
	                            </li>
	                        </ul>
	                </div>
	            </li>
	            </s:iterator>
	        </ul>
		</div>
		</s:if>
		
		<!--标的列表  -->
		<s:else>
		  <div class="s_touzi-pros" style="display:block;">
	    	<ul>
	    	    <s:iterator value="pageBean.page" var="finance">
	        	<li>
					<div class="touzi-top">
	                	<div class="touzi-top-title">
	                	   <s:if test="#finance.borrowTitle.indexOf('专属标')>0">
	                	     <a href="financeDetail.do?id=${finance.id}&nav=finance_li" style="color:#FF0088"><shove:sub size="15" value="#finance.borrowTitle" suffix="..."/></a>
	                	   </s:if>
	                	   <s:else>
	                	   <a href="financeDetail.do?id=${finance.id}&nav=finance_li" ><shove:sub size="15" value="#finance.borrowTitle" suffix="..."/></a>
	                	   </s:else>
	                	   <s:if test="%{#finance.hasPWD !=-1}">
	                	     <i class="yue-bg"><img src="/new_images/yue-icon.png" /></i>
	                	   </s:if>
	                	</div>
	                    <div class="touzi-top-rule">
	                    	<span class="sp1">还款方式：
	                            <s:if test="%{#finance.isDayThe ==2}"> 到期还本付息</s:if>
							    <s:elseif test="%{#finance.paymentMode == 1}">按月分期还款</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 3}">秒还</s:elseif>
							    <s:elseif test="%{#finance.paymentMode == 4}">一次性还款</s:elseif>
	                    
		                    </span>
		                    <span>起投金额：
			                    <s:if test="%{#finance.borrowWay==6}">
			                    	100
			                    </s:if>
			                    <s:else>
			                    ${finance.minTenderedSum}元
			                    </s:else>
		                    </span>
	                    </div>
	                </div>
	                <div class="touzi-bottom">
	                    	<ul>
	                        	<li class="investli-1">预期年化利率<em>${finance.annualRate}</em><i>%</i>
	                        	<s:if test="%{#finance.hasPWD ==-1}">
	                        	    <s:if test="%{#finance.add_interest >0}">
	                        	       <span class="list-add-xi">+${finance.add_interest}%</span>
	                        	    </s:if>
	                        	</s:if>
	                        	</li>
	                            <li class="investli-2">期限<em>${finance.deadline}</em><i>
	                            <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
                                <s:else>天</s:else></i></li>
	                            <li class="investli-3">借款总额<em>${finance.borrowAmount}</em><i></i></li>
	                            <li class="investli-4">
	                            	<div class="jindu-list">
	                                 <div class="jindu">
	                                        <div class="jinduing" style="width:${finance.schedules}%"></div>
	                                    </div>
	                        			<p class="percent">${finance.schedules}%</p>
	                                    <!-- <div class="cs-time">
	                                        <img src="/new_images/sz-icon.png"> <i class="cs-time-top">2015-10-19 15:00出售</i>
	                                    </div> -->
	                                    
	                                    <div>
						                        <s:if test="%{#finance.borrowStatus == 1}">
										             <a class="but_gray">初审中</a>
										         </s:if>
										         <s:elseif test="%{#finance.borrowStatus == 2}">
										         	<s:if test="%{#finance.borrowShow == 2}"><a class="but_avg" href="financeDetail.do?id=${finance.id}&nav=finance_li">立即投标</a></s:if>
										         	<s:else><a class="but_avg" href="financeDetail.do?id=${finance.id}&nav=finance_li">立即投标</a></s:else>
										        </s:elseif>
										        <s:elseif test="%{#finance.borrowStatus == 3}">
										             <a class="but_gray" style="margin-top:10px;" >复审中</a> 
										        </s:elseif>
										        <s:elseif test="%{#finance.borrowStatus == 4}">
										            <a class="but_gray" style="margin-top:10px;">
										        	<s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
										          	<s:else>还款中</s:else></a>
										        </s:elseif>
										        <s:elseif test="%{#finance.borrowStatus == 5}">
										            <a class="but_gray" style="margin-top:10px;">已还完</a> 
										        </s:elseif>
										        <s:else>
										               <a class="but_gray" style="margin-top:10px;">流标</a> 
										        </s:else>
	                                        <!-- <a href="#" class="but_avg">马上投标</a> -->
	                                        <!-- <a href="#" class="but_gray">即将发售</a> -->
	                                    </div>
	                    			</div>
	                            </li>
	                        </ul>
	                </div>
	            </li>
	            </s:iterator>
	        </ul>
		</div>
		</s:else>
		
		<s:if test="pageBean.page!=null || pageBean.page.size>0">
		    <div class="s_foot-page">
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

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("#flowbid").click(function(){
			$("#type").val("6");
			window.location.href="finance.do?m=1&type="+$("#type").val()+"&nav=finance_li";
		});

		$("#shidibid").click(function(){
			$("#type").val("4");
			window.location.href="finance.do?m=1&type="+$("#type").val()+"&nav=finance_li";
		});
		
		$("#xinyongbid").click(function(){
			$("#type").val("3");
			window.location.href="finance.do?m=1&type="+$("#type").val()+"&nav=finance_li";
		});

		$("#debtbid").click(function(){
			$("#type").val("7");
			window.location.href="finance.do?m=1&type="+$("#type").val()+"&nav=finance_li";
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
					+"<span>年化利率：</span><span>"+data[i].income2year+"%</span><br/>"
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