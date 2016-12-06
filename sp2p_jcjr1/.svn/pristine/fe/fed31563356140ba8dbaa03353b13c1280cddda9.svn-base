<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
   <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <jsp:include page="/include/head.jsp"></jsp:include>
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <jsp:include page="/include/common.jsp"></jsp:include>
    
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="p_xqpicbox">
    <div class="p_xqpic"><img src="images/p_ico005.png" /></div>
</div>
<input type="hidden" id="idStr" value="${idStr}"/>

<!-------------内容区 begin-------->
<div class="p_xqmainbox">
    <div class="p_xq001">
        <div class="p_xq002"><a href="javascript:history.go(-1);"><img src="images/p_ico006.png" /> 返回</a><span><img src="images/p_ico007.png" /> <s:if test="%{#request.borrowDetailMap.applyTime > #request.borrowDetailMap.publishTime}">发布时间：${borrowDetailMap.applyTime}</s:if><s:else>发布时间：${borrowDetailMap.publishTime}</s:else></span></div>
        <div class="p_xq003"><span>账户可用投资金额：${usableSum}元</span><a href="rechargeInit.do">立即充值</a></div>
    </div>
    <div class="p_xq004">
        <div class="p_xq005">
            <h5><s:property value="#request.borrowDetailMap.borrowTitle" default="---"/><span></span></h5>
            <div class="p_xq006">
                <div class="p_xq007">
                    <p>本期总额</p>
                    <p class="p_xq008"><span>￥</span><s:property value="#request.borrowDetailMap.borrowAmount" default="---"/></p>
                </div>
                <div class="p_xq009">
                    <p>收益率</p>
                    <p class="p_xq010"><fmt:formatNumber value="${borrowDetailMap.annualRate}" pattern="#0.0" /><span>%</span></p>
                </div>
                <div class="p_xq011">
                    <p>投资周期</p>
                    <p class="p_xq012"><s:property value="#request.borrowDetailMap.deadline" default="---"/><s:if test="%{#request.borrowDetailMap.isDayThe ==1}">个月</s:if><s:else>天</s:else></p>
                </div>
            </div>
            <div class="p_xq013">
                <p class="clearfix"><span class="fl">还款方式：<em class="p_7aa">
							    	<s:if test="%{#request.borrowDetailMap.isDayThe ==2}">
							    	 到期还本付息
							    	</s:if>
							    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款</s:elseif>
							    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
							    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
							     <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
							</em></span><span class="fr"><img src="images/p_ico038.png" /> 100%本息保障</span></p>
                <s:if test="%{#request.borrowDetailMap.borrowWay ==4}">
					<p>担  保 方：<em class="p_7aa">中国能源投资有限公司</em></p>
                	<p><a  id ="showImg" >查看抵押物</a></p>
				</s:if>
                <s:if test="%{#request.borrowDetailMap.borrowShow ==2}">
					<p>已流转份数：<em class="p_7aa">${borrowDetailMap.hasCirculationNumber}</em>份</p>
                	<p>剩余份数：<em class="p_7aa">${borrowDetailMap.remainCirculationNumber}</em>份</p>
				</s:if>
				<!--弹框-->
				<div class="s_diyawubg"></div>
				<div class="s_diyawu">
					<span class="close-btn">×</span>
					<img src="images/pic23.png" width="400" height="400"/>
				</div>

            </div>
            <div class="p_xq015">
                <p>投标 <span class="p_7aa">${earnMap.realAmount }</span> 元，到期获得收益 <span class="p_xq016">${earnMap.totalInterest }</span> 元</p>
            </div>
        </div>
        <div class="p_xq017">
            <h5>剩余金额：<span><s:property value="#request.borrowDetailMap.investAmount" default="0"/></span>元</h5>
            <p>&nbsp;</p>
            <p>完成进度 <span class="p_xq018"><s:property value="#request.borrowDetailMap.schedules" default="0"/>%</span></p>
            <p>预期收益 <span id="span_profit" class="p_xq018"></span> 元</p>
            <div class="p_xq019">
                <div style="width:<s:property value="#request.borrowDetailMap.schedules" default="0"/>%"></div>
            </div>
            <div class="p_xq020">
            	<s:if test="%{#request.borrowDetailMap.borrowShow ==2}"></s:if><s:else>
            		<input id="amountMoney" type="text" value="输入金额" onmousedown="if(this.value=='输入金额'){this.value=''}" 
            		onblur="if(this.value==''){this.value='输入金额'}" onkeyup="formula(this.value)"/>
            	</s:else>
            </div>
            
            <s:if test="%{#request.borrowDetailMap.borrowShow ==2}">
            	<p class="mt5">最小流转单位：<span class="p_7aa"><s:property value="#request.borrowDetailMap.smallestFlowUnit" default="0"/></span>元</p>
            </s:if><s:else>
            	<p class="mt5">起投金额：<span class="p_7aa"><s:property value="#request.borrowDetailMap.minTenderedSum" default="0"/></span>元</p>
            </s:else>
            
            <div class="p_xq021">
	            <s:if test="#session.DEMO==2">
	        	<s:if test="%{#request.borrowDetailMap.borrowStatus == 4}">
		           		 <a href="javascript:void(0);">还款中</a>
		        </s:if>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
		           		<a href="javascript:void(0);">已还完</a>
	      		  </s:elseif>
		         <s:elseif test="%{#request.wStatus != ''}">
		           		<a href="javascript:void(0);">等待资料认证</a>
		        </s:elseif>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 1}">
		            	<a href="javascript:void(0);">初审中</a>
		        </s:elseif>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 2}">
	            <a href="javascript:void(0);" id="invest">立即投资</a>
		        </s:elseif>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
		            	<a href="javascript:void(0);">复审中</a>
		        </s:elseif>
			        <s:else>
			           	<a href="javascript:void(0);">流标</a>
			        </s:else>
	        </s:if>
	        <s:else>
	        	<s:if test="%{#request.borrowDetailMap.borrowStatus == 1}">
	            		<a href="javascript:void(0);">初审中</a>
	      	  	</s:if>
	      	  	 <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 2}">
	            <a href="javascript:void(0);" id="invest">立即投资</a>
		        </s:elseif>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
		            	<a href="javascript:void(0);">复审中</a>
		        </s:elseif>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
		            	<a href="javascript:void(0);">还款中</a>
		        </s:elseif>
		        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
		           		<a href="javascript:void(0);">已还完</a>
	      		  </s:elseif>
			        <s:else>
			           	<a href="javascript:void(0);">流标</a>
			        </s:else>
	        </s:else>
            </div>
        </div>
    </div>
    <div class="p_xq022">
        <ul class="p_tabul">
            <li>项目详情</li>
            <li id="repay">还款计划</li>
            <li>投资记录</li>
        </ul>
        <span><a href="finance.do?m=1&type=${request.borrowWay}"><img src="images/p_ico008.png" /> 
        		进入更多<s:if test="#request.borrowWay==1">净值借款</s:if>
        		<s:elseif test="#request.borrowWay==2">秒还借款</s:elseif>
        		<s:elseif test="#request.borrowWay==3">项目优选</s:elseif>
        		<s:elseif test="#request.borrowWay==4">定息宝</s:elseif>
        		<s:elseif test="#request.borrowWay==5">担保借款</s:elseif>
        		<s:elseif test="#request.borrowWay==6">活利宝</s:elseif>产品列表</a></span></div>
    <!--<div class="p_tab p_xq023">
        <div class="p_xq024">
            <p><s:if test="%{#request.borrowDetailMap.borrowShow ==2}"></s:if>${borrowDetailMap.businessDetail}<s:else>${borrowDetailMap.borrowInfo}</s:else></p>
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
    </div>-->
    
    <div class="p_tab p_xq023">
        <div class="p_xq030">  
            <p>项目投向为中安信业（初始债权方及回购方）优质债权的收益权。转出收益权期限至初始债权原期限到期前3个月为止，初始债权方中安信业承诺项目到期无限回购.</p>
        </div>
        <div class="p_xq031">
        	<p><img src="images/z_img6.jpg" width="217" height="43" /></p>
            <p>中安信业成立与2003年10月，是一家专门协助银行微笑企业，个体户及工薪阶层提供快速简便、免抵押免担保小额贷款服务的专业现代化小额信贷服务技术公司是
全国探索商业化可持续小额贷款业务最早的企业之一；也是全国首家专门为银行提供小额信贷技术服务的公司。</p>
      </div>
  		<div class="p_xq032">
            <dl>
                <dt><img src="images/z_img1.png" /></dt>
                <dd>营业执照</dd>
            </dl>
            <dl>
            <dt><img src="images/z_img2.png"/></dt>
                <dd>税务登记证</dd>
            </dl>
            <dl>
            <dt><img src="images/z_img3.png"  /></dt>
                <dd>组织机构代码证</dd>
            </dl>
            <dl>
            <dt><img src="images/z_img4.png" /></dt>
                <dd>开户许可证</dd>
            </dl>
        </div>
  <div class="p_xq033">
        	<div class="fl" style="width:620px; line-height:34px;">
           	  <h4>担保公司意见</h4>
              <p>我公司所转让债权的收益权项目，实际借款人是我公司赛选的优质客户，在银行有良好的授信和用款记录，所转让项目的期限都设定
在我公司原债权到期前3个月，由我公司提供无限回购。</p>
<h4>担保公司意见</h4>
                <p>我公司所转让债权的收益权项目，实际借款人是我公司赛选的优质客户，在银行有良好的授信和用款记录，所转让项目的期限都设定
在我公司原债权到期前3个月，由我公司提供无限回购。</p>
        </div>
            <div class="fl">
   	    		<img src="images/z_img5.jpg" width="172" height="238" />
           </div>
      </div>
    </div>
    
    
    <div class="p_tab none p_xq028" id = "contentList">
    
    </div>
    <div class="p_tab none p_xq028">
      <ul><li>目前总投标金额：<span class="red">￥${borrowDetailMap.hasInvestAmount}</span></li><li>剩余投标金额：<span class="red">￥${borrowDetailMap.investAmount}</span></li>
      	<s:if test="%{#request.borrowDetailMap.borrowWay ==6 || #request.borrowDetailMap.borrowWay == 4}"></s:if><s:else><li>剩余投标时间：<span class="red" id="remainTimeTwo"></span></li></s:else>
      </ul>
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
</div>
<!-------------主体 end----------->
<div id="remainSeconds"  style="display:none;">${borrowDetailMap.remainTime}</div>
<input type="hidden" id="bid" value="${borrowDetailMap.id}"/>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>

function mycirculationPay(ids){
	 param["id"] = ids;
    $.shovePost('financeInvestInit.do',param,function(data){
	   var callBack = data.msg;
	   if(callBack !=undefined){
	     alert(callBack);
	   }else{
	  var url = "subscribeinit.do?borrowid="+ids;
	 $.jBox("iframe:"+url, {
   		title: "我要购买",
   		width: 450,
   		height: 450,
   		buttons: {  }
   		});
 	// ShowIframe("我要购买",url,450,450);
  }
});}
	$(function() {
		$("#span_profit").html("0");
		dqzt(1);
		$('#showImg').click(function(){
			showImg(13,'${borrowUserMap.enUserId}');
		});
	/*	$('#btnsave').click(function(){
		     var id =$("#id").val();
		     param['paramMap.id'] = id;
		     param["paramMap.code"] = $("#code").val();
		     param['paramMap.annualRate'] = $("#annualRate").val();
		     param["paramMap.deadline"] = $("#deadline").val();
		     param['paramMap.amount'] = $('#amount').val();
		     param['paramMap.investPWD'] = $('#investPWD').val();
		     alert(2);
		     if(flag){
	           flag = false;
		       $.shovePost('financeInvest.do',param,function(data){
			      var callBack = data.msg;
			      if(callBack == undefined || callBack == ''){
			         $('#content').html(data);
			         flag = true;
			      }else{
			         if(callBack == 1){
			          alert("操作成功!");
			          window.location.href= 'financeDetail.do?id='+id;
			          flag = false;
			          return false;
			         }
			         alert(callBack);
			         flag = true;
			      }
			    });
			 }
		 });*/
		//var user_address = $(".user_address").html();
		//var new_address = user_address.substring(0,3);
		//$(".user_address").html(new_address);
		//样式选中
	//     $("#licai_hover").attr('class','nav_first');
		var param = {};
		$('#invest').click(function() {
			var bid = $('#bid').val();
			//分为流转标的认购和一般投标
			if('${request.borrowDetailMap.borrowShow}'=='2'){
				mycirculationPay(bid);
			}else{
				if('${session.user}'==''){
					window.location.href="login.do";
				}else{
					var step = '${session.user.authStep}';
					var username = '${borrowUserMap.username_2}';
					var uname = '${user.userName}';
					if (username == uname) {
						alert("无效操作,不能投自己发布的标");
						return false;
					}
					var url = "financeInvestInit.do?id="+bid+"&amountMoney="+$('#amountMoney').val();
			     	 $.jBox("iframe:"+url, {
				    		title: "我要购买",
				    		width: 450,
				    		height: 450,
				    		buttons: {  }
				    		});
				}
			}	
				
		});
		$('#focusonBorrow').click(function() {
			var username = '${borrowUserMap.username_2}';
			var uname = '${user.userName}';
			if (username == uname) {
				alert("您不能关注自己发布的借款");
				return false;
			}
			param['paramMap.id'] = '${borrowDetailMap.id}';
			$.shovePost('focusonBorrow.do', param, function(data) {
				var callBack = data.msg;
				alert(callBack);
			});
		});
		$('#focusonUser').click(function() {
			var username = '${borrowUserMap.username_2}';
			var uname = '${user.userName}';
			if (username == uname) {
				alert("您不能关注自己");
				return false;
			}
			param['paramMap.id'] = '${borrowUserMap.enUserId}';
			$.shovePost('focusonUser.do', param, function(data) {
				var callBack = data.msg;
				alert(callBack);
			});
		});
		$('#sendmail').click(function() {
			var id = '${borrowUserMap.enUserId}';
			var username = '${borrowUserMap.username_2}';
			var username2 = '${borrowUserMap.username}';
			var url = "mailInit.do?id=" + id + "&username=" + username2;
			var uname = '${user.userName}';
			if (username == uname) {
				alert("您不能给自己发送站内信");
				return false;
			}
			
			
			
			$.shovePost('mailInit.do', param, function(data) {
				 $.jBox("iframe:" + url, {
	      		     title: "发送站内信",
	      		     width: 500,
	      		     height: 400,
	      		     buttons: {  }
	   		 });
			});
		});
		$('#reportuser').click(function() {
			var id = '${borrowUserMap.enUserId}';
			var username = '${borrowUserMap.username_2}';
			var username2 = '${borrowUserMap.username}';
			var url = "reportInit.do?id=" + id + "&username=" + username2;
			
			var uname = '${user.userName}';
			if (username == uname) {
				alert("您不能举报自己");
				return false;
			}
			
			
			
			$.shovePost('reportInit.do', param, function(data) {
				 $.jBox("iframe:" + url, {
	      		     title: "举报此人",
	      		     width: 500,
	      		     height: 400,
	      		     buttons: {  }
	   		  });
			});
		});
		$('#audit').click(function() {
			var id = $('#idStr').val();
			$(this).addClass('on');
			$('#repay').removeClass('on');
			$('#collection').removeClass('on');
			param['paramMap.id'] = id;
			param['paramMap.publisherWay'] = '${borrowDetailMap.publisherWay}';
			$.shovePost('financeAudit.do', param, function(data) {
				$('#contentList').html(data);
			});
		});
		$('#repay').click(function() {
			var id = $('#idStr').val();
			$(this).addClass('on');
			$('#audit').removeClass('on');
			$('#collection').removeClass('on');
			param['paramMap.id'] = id;
			$.shovePost('financeRepay.do', param, function(data) {
				$('#contentList').html(data);
			});

		});
		$('#collection').click(function() {
			var id = $('#idStr').val();
			$(this).addClass('on');
			$('#audit').removeClass('on');
			$('#repay').removeClass('on');
			param['paramMap.id'] = id;
			$.shovePost('financeCollection.do', param, function(data) {
				$('#contentList').html(data);
			});

		});
		initListInfo(param);
	});
	function initListInfo(param) {
		param['paramMap.id'] = '${borrowDetailMap.id}';
		$.shovePost('borrowmessage.do', param, function(data) {
			$('#msg').html(data);
		});
	}
	function showImg(typeId, userId) {
		var session = '<%=session.getAttribute("user")%>';
		if (session == 'null'){
			window.location.href='login.do';
			return ;
		}
		var url = "showImg.do?typeId=" + typeId + "&userId=" + userId;
		  $.jBox("iframe:"+url, {
	    		title: "查看认证图片(点击图片放大)",
	    		width: 500,
	    		height: 428,
	    		buttons: {  }
	    		});
	}
	function close() {
		ClosePop();
	}
	
	function formula(amount) {
		var profit = 0.00;
		profit = '${earnMap.totalInterest }' * amount / '${earnMap.realAmount }';
		$("#span_profit").html(profit.toFixed(2));
	}
</script>
<script type="text/javascript">
	var SysSecond;
	var InterValObj;

	$(document).ready(function() {
		SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
		});

	//将时间减去1秒，计算天、时、分、秒 
	function SetRemainTime() {
		if (SysSecond > 0) {
			SysSecond = SysSecond - 1;
			var second = Math.floor(SysSecond % 60); // 计算秒     
			var minite = Math.floor((SysSecond / 60) % 60); //计算分 
			var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
			var day = Math.floor((SysSecond / 3600) / 24); //计算天 
			var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
			$("#remainTimeOne").html(times);
			$("#remainTimeTwo").html(times);
		} else {//剩余时间小于或等于0的时候，就停止间隔函数 
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件 
		}
	}
</script>
</body>
</html>
