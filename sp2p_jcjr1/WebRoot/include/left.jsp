<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$.ajax({
	    type: "post",
	    url: "queryUserAccount.do",
	    dataType: "json",
	    success: function (data) {
			var ua = data.userAccount;
			$("#user_account").text(ua+"元");
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	    }
	});
</script>
<script type="text/javascript" src="/script/m/jquery.rye-core-1.0.js"></script>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="lne_centl">
 <div class="lne_l1">
	<div class="lne_l1-top">
       	<ul>
            <li style="width:57px;">
               <img src="/new_images/personal-img1.jpg">
             </li>
              <li class="lne-w1">
               <em>当前用户:</em><a href="home.do">${session.user.userName}</a>
             </li>
         </ul>
	</div>
	<div class="lne_l1-bottom">
      	<p>
            
            <s:if test="#session.user.authStep>=2">
                <img src="/new_images/zhengy.png" title="个人信息已认证"/>
            </s:if>
            <s:else>
               <img src="/new_images/zheng.png" title="个人信息未认证" />
            </s:else>
            
            <s:if test="#session.user.email!=null && #session.user.email!=''">
               <img src="/new_images/xiny.png" title="邮箱已认证"/>
             </s:if>
            <s:else>
               <img src="/new_images/xin.png" title="邮箱未认证"/>
             </s:else>
            <img src="/new_images/phoney.png" title="手机号码已认证"/> 
           
        </p>
        </div>
         <div>
            <a href="rechargeInit.do" class="s-blue-btn" >充值</a>
           	<a href="withdrawLoad.do" class="s-blue-btn" style="margin-left:10px;">提现</a>
        </div>
    </div>
    <div class="lne_l3 sidebar-menu">
        <ul>
            <li class="level1">
                <h2>我的账户</h2>
                <ul class="level2">
                	<li><a id="home" href="home.do?nav=home"><i class="personal-arrow2 nav-account1"></i>账户总览</a></li>
                    <li><a id="owerInformationInit1" href="owerInformationInit1.do?nav=owerInformationInit1"><i class="personal-arrow2 nav-account2"></i>基本信息</a></li>
                    <li><a id="queryFundrecordInit" href="queryFundrecordInit.do?nav=queryFundrecordInit"><i class="personal-arrow2 nav-account3"></i>资金记录</a></li>
                    <li><a id="yhbound" href="yhbound.do?nav=yhbound"><i class="personal-arrow2 nav-account4"></i>银行卡管理</a></li>
                    <li><a id="mailNoticeInit" href="mailNoticeInit.do?nav=mailNoticeInit"><i class="personal-arrow2 nav-account5"></i>系统消息</a></li>
                </ul>
            </li>
            <li class="level1">
                <h2>理财管理</h2>
                <ul class="level2">
                    <li><a id="homeBorrowInvestList" href="homeBorrowInvestList.do?nav=homeBorrowInvestList"><i class="personal-arrow2 nav-account6"></i>我的投资</a></li>
                    <li><a id="queryCanAssignmentDebt" href="queryCanAssignmentDebt.do?nav=queryCanAssignmentDebt"><i class="personal-arrow2 nav-account7"></i>债权转让</a></li>
                    <li><a id="bespeakInvest" href="bespeakInvest.do?nav=bespeakInvest"><i class="personal-arrow2 nav-account8"></i>预约投标</a></li>
                    <li><a id="automaticBidInit" href="automaticBidInit.do?nav=automaticBidInit"><i class="personal-arrow2 nav-account9"></i>自动投标</a></li>
                </ul>
            </li>
            <li class="level1">
                <h2>借款管理</h2>
                <ul class="level2">
                    <li><a id="owerInformationInit" href="owerInformationInit.do?nav=owerInformationInit"><i class="personal-arrow2 nav-account10"></i>认证信息</a></li>
                    <li><a id="queryMySuccessBorrowList" href="queryMySuccessBorrowList.do?nav=queryMySuccessBorrowList"><i class="personal-arrow2 nav-account11"></i>还款管理</a></li>
                    <li><a id="homeBorrowAuditList" href="homeBorrowAuditList.do?nav=homeBorrowAuditList"><i class="personal-arrow2 nav-account12"></i>申请中的借款</a></li>
                    <li><a id="loanStatisInit" href="loanStatisInit.do?nav=loanStatisInit"><i class="personal-arrow2 nav-account13"></i>借款统计</a></li>
                    <li><a id="borrowType" href="borrowType.do?nav=borrowType"><i class="personal-arrow2 nav-account14"></i>我要借款</a></li>
                </ul>
            </li>
            <li class="level1">
                <h2>好友和奖励</h2>
                <ul class="level2">
                    <li><a id="toReconmmendInfo" href="toReconmmendInfo.do?nav=toReconmmendInfo"><i class="personal-arrow2 nav-account15"></i>我的推荐</a></li>
                    <li><a id="toRewardInfo" href="toRewardInfo.do?nav=toRewardInfo"><i class="personal-arrow2 nav-account16"></i>我的奖励</a></li>
                </ul>
            </li>
    </ul>
</div>
</div>

<script>
	var _param = {};
	var br = new BaseRye(_param);
	var nav = br.getUrlParam("nav");
	if(nav != '' && nav!=undefined && nav != 'none' && nav != 'undefined'){
		// 非首页
	}else{
		nav = 'home';
	}
	
	//alert(nav);
	$("#"+nav).addClass("clicked");

</script>
