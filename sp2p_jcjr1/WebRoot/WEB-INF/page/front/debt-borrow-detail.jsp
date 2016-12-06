<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<jsp:include page="/include/head.jsp"></jsp:include>
		<jsp:include page="/include/common.jsp"></jsp:include>
	</head>
	<body>
    <div class="p_xq001">
        <div class="p_xq002"><a href="javascript:history.go(-1);"><img src="images/p_ico006.png" /> 返回</a><span><img src="images/p_ico007.png" /> 发布时间：${paramMap.publishTime }</span></div>
        <div class="p_xq003">
        	<s:if test="%{#request.isLogin==0}"></s:if>
            <s:else>
	        	<span>账户可用投资金额：${usableSum}元</span><a href="rechargeInit.do">立即充值</a>
            </s:else>
        </div>
    </div>
    <div class="p_xq004" style="height:460px;">
        <div class="p_xq005" style="height:450px;">
            <h5>
            	<!--<s:property value="#request.borrowDetailMap.borrowTitle" default="---"/>-->
            	${paramMap.debtBorrowTile }
            	<span></span></h5>
            <div class="p_xq006" style="height:180px; overflow:hidden;">
                <div class="p_xq009" style="width:220px;">
                    <p>债权价值</p>
                    <p class="p_xq010"><span>￥${paramMap.debtValue }</span></p>
                    <p>转让价格</p>
                    <p class="p_xq010"><span>￥${paramMap.debtPrice }</span></p>
                </div>
                <div class="p_xq009" style="width:230px; height:180px; text-align:center;">
                
                	<p>原始年化收益率</p>
                    <p class="p_xq010">
                   		<!-- <fmt:formatNumber value="${borrowDetailMap.annualRate}" pattern="#0.00" /> --> 
                   		<fmt:formatNumber value="${paramMap.annualRate }" pattern="#0.00" />
                   		<span>%</span>
                    </p>
                    <p>转让年化收益率</p>
                    <p class="p_xq010">
                   		<!-- <fmt:formatNumber value="${borrowDetailMap.annualRate}" pattern="#0.00" /> --> 
                   		<fmt:formatNumber value="${paramMap.annualRateDebtBDDouble }" pattern="#0.00" />
                   		<span>%</span>
                    </p>
                </div>
                <div class="p_xq011">
                    <p>原始期数</p>
                    <p class="p_xq012"><s:property value="#request.paramMap.deadline " default="---"/><s:if test="%{#request.borrowDetailMap.isDayThe ==1}">个月</s:if><s:else>天</s:else></p>
	                    <p>剩余天数</p>
	                    <p class="p_xq012">
	                    
		                    <fmt:formatNumber value="${paramMap.remainingDays }" pattern="#0" /> 天
		                    <!-- 
		                    <s:if test="%{#request.borrowDetailMap.isDayThe ==1}">个月</s:if><s:else>天</s:else>
		                     -->
	                    </p>
                </div>
            </div>
            <div class="p_xq013">	
                <p class="clearfix">
                
                	<span class="fl">转让系数：<em class="p_7aa">
                		<fmt:formatNumber value="${paramMap.transRatio }" pattern="#0.00" />                		
                	</em></span>
                	<span class="fl" style="margin-left:100px;">待收本息：<em class="p_7aa">
                	<fmt:formatNumber value="${paramMap.awaitPI }" pattern="#0.00" /></em>元</span><br/>
        			<span class="fl">还款方式：<em class="p_7aa">
				    	<s:if test="%{#request.borrowDetailMap.isDayThe ==2}">
				    	 到期还本付息
				    	</s:if>
					    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 1}">等额本息</s:elseif>
					    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
					    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
					    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
						</em>
					</span>
					<s:if test="%{paramMap.debtStatus==2}"></s:if>
                    <s:else>
	                    <span class="fl" style="margin-left:68px;">下一还款日：
							<em class="p_7aa">
								<s:if test="%{#request.paramMap.nextRepayDateValue == '已还完'}">已还完</s:if>
								<s:if test="%{#request.paramMap.nextRepayDateValue == ''}">已满标</s:if>
	                			<s:else>${nextRepayDate }</s:else>
							</em>
						</span>
                	</s:else>
					
					<span class="fl" style="position:absolute; right:15px; top:0;"><img src="images/p_ico038.png" /> 100%本息保障</span>
				</p>
				
                <s:if test="%{#request.borrowDetailMap.borrowWay ==4}">
					<p>担  保 方：<em class="p_7aa">中国能源投资有限公司</em></p>
                	<p><a id="showImg">查看抵押物</a></p>
				</s:if>
                
            </div>
            <div class="p_xq015">
           		 <s:if test="%{paramMap.debtStatus==2}"></s:if>
                 <s:else><p>到期获得收益 <span class="p_xq016"><fmt:formatNumber value="${paramMap.awaitPI - paramMap.debtPrice}" pattern="#0.00" /></span> 元</p></s:else>
            </div>
        </div>
        <div class="p_xq017" style="margin-top:100px;">
            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="s_jingpai-tab">
				<tr>
					<th>剩余购买时间：</th>
					<td><strong><span id="remainTimeTwo"></span></strong></td>
				</tr>
				<s:if test="%{paramMap.bonus_able_6_24>0}"> 
					<tr>
						<th>剩余红包：</th>
						<td><strong><span id=""><fmt:formatNumber value="${paramMap.bonus_able_6_24}" pattern="#0.00" /></span></strong></td>
					</tr>
					<tr>
						<th>是否使用红包：</th>
						<td><strong><span id=""><input type="checkbox" id="isUseHb_6_24" value="0"/></span></strong></td>
					</tr>
				</s:if>
			</table>
            <div class="p_xq021" style="margin-top:30px">
            <s:if test='%{paramMap.debtStatus==2||paramMap.remainDays=="过期"}'>
	        	<img src="images/tubiao5.png" />
	        </s:if>
	        <s:else>
		         <s:if test="%{#session.user==null}">
			        <s:if test='%{paramMap.debtStatus==1&&paramMap.remainDays!="过期"}'>
			        	<a id="investDebt" href="javascript:void(0);">立即投标</a>
			        </s:if>
			        <s:elseif test="%{paramMap.debtStatus==2"><a href="#">已完成</a></s:elseif>
			        <s:elseif test="%{paramMap.debtStatus==3"><a href="#">购买失败</a></s:elseif>
		        </s:if>
		        <s:else>
			        <s:if test="%{#session.user.id!=paramMap.alienatorId && #session.user.id!=paramMap.borrowerId}">
				        <s:if test='%{paramMap.debtStatus==1&&paramMap.remainDays!="过期"}'>
				        	<a id="investDebt" href="javascript:void(0);">立即投标</a>
				        </s:if>
				        <s:elseif test="%{paramMap.debtStatus==2"><a href="#">已完成</a></s:elseif>
			        	<s:elseif test="%{paramMap.debtStatus==3"><a href="#">购买失败</a></s:elseif>
			        </s:if>
		        </s:else>
	        </s:else>
	        <s:hidden id="debtStatus" name="paramMap.debtStatus"></s:hidden>
            </div>
        </div>
    </div>
    <div class="p_xq022">
        <ul class="p_tabul">
            <li>项目详情</li>
            <li id="repay">还款计划</li>
            <li>投资记录</li>
            <!-- whb转让记录
            <li>转让记录</li> -->
        </ul>
        <span><a href="finance.do?m=1&type=4"><img src="images/p_ico008.png" /> 
        		进入更多债权转让列表</a></span></div>
    <div class="p_tab p_xq023">
        <div class="p_xq024">
            <p>${borrowDetailMap.borrowInfo}</p>
        </div>
        <div class="p_xq025">
        	<s:if test="%{#request.list !=null && #request.list.size()>0}">
      			<s:iterator value="#request.list" id="bean"  status="status">
      				<s:if test="#status.getIndex()==0">
      					<dl>
			                <dt><s:if test="#bean.auditStatus == 3">
			                <img src="images/p_ico016.png" />
			                </s:if><s:else><img src="images/p_ico017.png" /></s:else></dt>
			                <dd>身份认证</dd>
			            </dl>
      				</s:if>
      				<s:elseif test="#status.getIndex()==1">
      					<dl>
			                <dt><s:if test="#bean.auditStatus == 3">
			                <img src="images/p_ico018.png" />
			                </s:if><s:else><img src="images/p_ico019.png" /></s:else></dt>
			                <dd>工作认证</dd>
			            </dl>
      				</s:elseif>
      				<s:elseif test="#status.getIndex()==2">
      					<dl>
			                <dt><s:if test="#bean.auditStatus == 3">
			                <img src="images/p_ico020.png" />
			                </s:if><s:else><img src="images/p_ico021.png" /></s:else></dt>
			                <dd>居住地认证</dd>
			            </dl>
      				</s:elseif>
      				<s:elseif test="#status.getIndex()==3">
      					    <dl>
				                <dt><s:if test="#bean.auditStatus == 3">
				                <img src="images/p_ico022.png" />
				                </s:if><s:else><img src="images/p_ico023.png" /></s:else></dt>
				                <dd>信用认证</dd>
				            </dl>
      				</s:elseif>
      				<s:elseif test="#status.getIndex()==4">
      					    <dl>
				                <dt><s:if test="#bean.auditStatus == 3">
				                <img src="images/p_ico024.png" />
				                </s:if><s:else><img src="images/p_ico025.png" /></s:else></dt>
				                <dd>收入认证</dd>
				            </dl>
      				</s:elseif>
      			</s:iterator></s:if>
        </div>
        <div class="p_xq026">
           <table width="100%">
			   <tr>
					<td width="20%">性别：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.sex}</strong></s:if><s:else>${borrowUserMap.sex}</s:else></strong></td>
					<td width="20%">年龄：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.age}</strong></s:if><s:else>${borrowUserMap.age}</s:else></strong></td>
					<td width="20%">婚姻状况：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.maritalStatus}</strong></s:if><s:else>${borrowUserMap.maritalStatus}</s:else></strong></td>
					<td width="20%"></td>
					<td width="20%"></td>
			   </tr>
			    <tr>
					<td>工作城市：<strong><s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.workPro}&nbsp;${borrowUserMap.workCity}</strong></s:if><s:else>${borrowUserMap.workPro}&nbsp;${borrowUserMap.workCity}</s:else></strong></td>
					<td>公司行业：<strong><s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.companyLine}</strong></s:if><s:else>${borrowUserMap.companyLine}</s:else></strong></td>
					<td>公司规模：<strong><s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.companyScale}</strong></s:if><s:else>${borrowUserMap.companyScale}</s:else></strong></td>
					<td> 职位：<strong><s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.job}</strong></s:if><s:else>${borrowUserMap.job}</s:else></strong></td>
					<td></td>
			   </tr>
			    <tr>
					<td>有无购房：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasHourse}</strong></s:if><s:else>${borrowUserMap.hasHourse}</s:else></strong></td>
					<td>有无房贷：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasHousrseLoan}</strong></s:if><s:else>${borrowUserMap.hasHousrseLoan}</s:else></strong></td>
					<td>有无购车：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasCar}</strong></s:if><s:else>${borrowUserMap.hasCar}</s:else></strong></td>
					<td>有无车贷：<strong><s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasCarLoan}</strong></s:if><s:else>${borrowUserMap.hasCarLoan}</s:else></strong></td>
					<td></td>
			   </tr>
			   <tr>
			       <th colspan="5">微信贷借款记录</th>
			   </tr>
			    <tr>
					<td>发布借款笔数：<strong><s:if test="#request.borrowRecordMap.publishBorrow !=''">${borrowRecordMap.publishBorrow}</s:if><s:else>0</s:else></strong></td>
					<td>成功借款笔数：<strong><s:if test="#request.borrowRecordMap.successBorrow !=''">${borrowRecordMap.successBorrow}</s:if><s:else>0</s:else></strong></td>
					<td>还清笔数：<strong><s:if test="#request.borrowRecordMap.repayBorrow !=''">${borrowRecordMap.repayBorrow}</s:if><s:else>0</s:else></strong></td>
					<td>逾期次数：<strong><s:if test="#request.borrowRecordMap.hasFICount !=''">${borrowRecordMap.hasFICount}</s:if><s:else>0</s:else></strong></td>
					<td>严重逾期次数：<strong><s:if test="#request.borrowRecordMap.badFICount !=''">${borrowRecordMap.badFICount}</s:if><s:else>0</s:else></strong></td>
			   </tr>
			    <tr>
					<td>共借入：<strong><s:if test="#request.borrowRecordMap.borrowAmount !=''">${borrowRecordMap.borrowAmount}</s:if><s:else>0</s:else></strong></td>
					<td>待还金额：<strong><s:if test="#request.borrowRecordMap.forRepayPI !=''">${borrowRecordMap.forRepayPI}</s:if><s:else>0</s:else></strong></td>
					<td>逾期金额：<strong><s:if test="#request.borrowRecordMap.hasI !=''">${borrowRecordMap.hasI}</s:if><s:else>0</s:else></strong></td>
					<td>共借出：<strong><s:if test="#request.borrowRecordMap.investAmount !=''">${borrowRecordMap.investAmount}</s:if><s:else>0</s:else></strong></td>
					<td>待收金额：<strong><s:if test="#request.borrowRecordMap.forPI !=''">${borrowRecordMap.forPI}</s:if><s:else>0</s:else></strong></td>
			   </tr>
		   </table>
        </div>
    </div>
    <div class="p_tab none p_xq028" id = "contentList">
    
    </div>
    <div class="p_tab none p_xq028">
      <ul><li>目前总投标金额：<span class="red">￥${borrowDetailMap.hasInvestAmount}</span></li><li>剩余投标金额：<span class="red">￥${borrowDetailMap.investAmount}</span></li><li>剩余投标时间：<span class="red" id="remainTimeTwo"></span></li></ul>
      <table width="95%">
      <tr height="30" bgcolor="#e9e9e9"><td>投资人</td><td>投资金额</td><td>投资时间</td></tr>
      <s:if test="%{#request.investList !=null && #request.investList.size()>0}">
      	<s:iterator value="#request.investList" id="investList">
      		<tr height="30"><td><s:property value="#investList.username" default="---"/></td><td><span class="red">￥<s:property value="#investList.investAmount"/></span></td><td><s:property value="#investList.investTime"/></td></tr>
      		
      	</s:iterator></s:if>
      	<s:else>
		      <td colspan="3" align="center">没有数据</td>
		  </s:else>
      </table>
    </div>

<!-------------主体 end----------->
<div id="remainSeconds"  style="display:none;">${borrowDetailMap.remainTime}</div>
<input type="hidden" id="bid" value="${borrowDetailMap.id}"/>
	<input type="hidden" id="idStr" value="${idStr}" />
	<div id="remainSeconds" style="display: none;">
		${borrowDetailMap.remainTime}
	</div>

	<script type="text/javascript" src="/script/nav-lc.js"></script>
	<script type="text/javascript" src="/css/popom.js"></script>
	<script type="text/javascript" src="/script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	
	<script>
		$('#investDebt').click(function() {
			if('${session.user}'==''){
				window.location.href="login.do";
			}else{
				
				if($("#isUseHb_6_24").attr("checked")=="checked" || $("#isUseHb_6_24").attr("checked")=="on"){ 
	                $('#isUseHb_6_24').val('1');//使用红包 
	            }else{ 
	                $('#isUseHb_6_24').val('0');//不使用红包 
	            } 
		                
		       	var isUseHb_6_24 = $('#isUseHb_6_24').val();
					
				var url = "auctingDebtInit.do?debtId=${paramMap.debtId }&debtPrice=${paramMap.debtPrice }&debtValue=${paramMap.debtValue }&investId=${paramMap.investId}&usableSum=${usableSum}&isUseHb_6_24="+isUseHb_6_24;
			    $.jBox("iframe:"+url, {
			    		title: "我要购买",
			    		width: 450,
			    		height: 450,
			    		buttons: {  }
			    		});
			}	
		});
		$(function(){
			if($("#debtStatus").val() == 2){
				$("#remainTimeTwo").html("已完成");
			}else{
				$("#remainTimeTwo").html($("#remainDays").val());
			}
			$('#showImg').click(function(){
				showImg(13,'${borrowUserMap.enUserId}');
			});
			//var user_address = $(".user_address").html();
			//var new_address = user_address.substring(0,3);
			//$(".user_address").html(new_address);
			 $('#focusonBorrow').click(function(){
			      var username = '${borrowUserMap.username}';
			      var uname = '${user.userName}';
			      if(username == uname){
			         alert("您不能关注自己发布的借款");
			         return false;
			      }
			     param['paramMap.id'] = '${borrowDetailMap.id}';
			     $.shovePost('focusonBorrow.do',param,function(data){
				   var callBack = data.msg;
				   alert(callBack);
				 });
			   });
			   $('#focusonUser').click(function(){ 
			      var username = '${borrowUserMap.username}';
			      var uname = '${user.userName}';
			      if(username == uname){
			         alert("您不能关注自己");
			         return false;
			      }
			     param['paramMap.id'] = '${borrowUserMap.enUserId}';
			     $.shovePost('focusonUser.do',param,function(data){
			       var callBack = data.msg;
				   alert(callBack);
				 });
			   });
			 
			   
			
			   $('#repay').click(function(){
			      var id = $('#idStr').val();
			      $(this).addClass('on');
			      param['paramMap.id']=id;
			      $.shovePost('financeRepay.do',param,function(data){
			        $('#contentList').html(data);	       
				  });
			      
			   });
		});
		
		function close(){
			ClosePop();
		}		     
	</script>
<script type="text/javascript"> 
 var SysSecond; 
 var InterValObj; 
  
 $(document).ready(function() { 
  SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
  InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
 }); 
 
 function showImg(typeId,userId){
		var session = '<%=session.getAttribute("user")%>';
		if (session == 'null'){
			window.location.href='login.do';
			return ;
		}
          var url = "showImg.do?typeId="+typeId+"&userId="+userId;
          $.jBox("iframe:" + url, {
   		     title: "抵押物",
   		     width: 650,
   		     height: 500,
   		     buttons: {}
          });
}
 //将时间减去1秒，计算天、时、分、秒 
 function SetRemainTime() { 
  if (SysSecond > 0) { 
   SysSecond = SysSecond - 1; 
   var second = Math.floor(SysSecond % 60);             // 计算秒     
   var minite = Math.floor((SysSecond / 60) % 60);      //计算分 
   var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时 
   var day = Math.floor((SysSecond / 3600) / 24);        //计算天 
   var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
  //whb $("#remainTimeOne").html(times);
   $("#remainTimeTwo").html(times); 
  } else {//剩余时间小于或等于0的时候，就停止间隔函数 
   window.clearInterval(InterValObj); 
   //这里可以添加倒计时时间为0后需要执行的事件 
  } 
 } 
</script>
	</body>
</html>
