<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/common.jsp"></jsp:include> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信贷-推广返现</title>
<style>
	body { font-size: 14px; color: #666; font-family: "微软雅黑"; background-color:#F3F4F8; }
	* { margin: 0; padding: 0; }
	ul, ol { list-style: none; }
	em, i { font-style: normal; }
	img { border: none; vertical-align: middle; }
	.clear { clear: both; }
	.clearfix { zoom: 1; }
	.clearfix:after { display: block; visibility: hidden; content: '.'; height: 0; clear: both; }
	a { font-family: "微软雅黑"; text-decoration: none; color: #666; cursor: pointer; }
	.activity{ width:100%; height:auto; font-family:"微软雅黑";}
	.activity .banner{ width:100%; background:url(/images/m/image/big_banner.jpg) no-repeat center; height:640px;}
	.activity .reg_list{ height:640px; width:100%; background:#fff;}
	.activity .reg_list .reg_list_con{ width:1005px; margin:0 auto; position:relative;}
	.activity .reg_list .list_con_top{ height:440px; overflow:hidden;}
	.activity .reg_list .list_con_top ul{ margin:0; padding:0;}
	.activity .reg_list .list_con_top ul li{ width:335px; float:left;}
	.mt{ margin-top:73px;}
	.activity .reg_list .list_con_bot{ height:102px; background:url(/images/m/image/ac_btn.jpg) no-repeat; margin-top:70px;}
	.activity .reg_list .list_con_bot ul{ width:450px; float:left;}
	.wenxin{ background:url(/images/m/image/ac_icon1.jpg) no-repeat 40px 20px; height:102px;}
	.wenxin a{ position:relative; top:28px; left:145px;}
	.lianjie{ background:url(/images/m/image/ac_icon2.jpg) no-repeat 80px 20px; height:102px;}
	.lianjie a{ position:relative; top:28px; left:175px;}
	.recommend{ background:#84c3ec; width:100%; height:auto; padding:80px 0 70px 0;}
	.recommend .gift{ width:1005px; height:auto; margin:0 auto;}
	.friend{ width:1023px; margin:60px auto 0 auto; height:auto; overflow:hidden; position:relative; left:-10px;}
	.friend .friend_title{ background:url(/images/m/image/ac_img7.jpg) no-repeat; width:1023px; height:102px; overflow:hidden;}
	.friend .friend_title ul{ padding:0; margin-top:25px; position:absolute; right:40px;}
	.friend .friend_title ul li{ float:left; font-size:20px; color:#686868; margin:0 10px;}
	.friend .friend_title ul li a{ color:#19aaed; text-decoration:underline;}
	.friend_con .coff{ background:#fff; height:50px; line-height:50px;}
	.friend_con{ width:1005px; margin-left:24px;}
	.friend_con .coff li{ float:left; color:#19aaed; font-size:24px; width:335px; text-align:center;}
	.friend_content{ width:1005px; height:auto; margin-left:24px;  background:#19aaed; overflow:hidden;}
	.friend_content ul{ width:1005px; overflow:hidden;height:70px;  background:url(/images/m/image/ac_link.jpg) bottom no-repeat;}
	.friend_content ul:last-child{ background:none;}
	.friend_content ul li{ float:left; font-size:24px; color:#fff; text-align:center; width:335px; height:70px; line-height:70px;}
	.friend_content ul li img{ position:relative; top:20px\0;}
	.friend_bot{ height:28px; width:1005px; margin-left:19px; background:url(/images/m/image/ac_friend_bg.jpg) no-repeat;}
	.reg_con{ width:100%; background:#4cb1f1;}
	.reg_con .reg_con_m{ width:890px; margin:0 auto; text-align:center; padding:60px 0; font-size:14px; color:#fff; font-weight:bold;}
	.reg_con .reg_con_m p{ margin-top:13px; line-height:28px;}
	.reg_con .reg_con_m p img{ margin-right:5px;}
	.rule{ width:100%; background:#2881d3; height:auto;}
	.rule .rule_con{ width:1005px; margin:0 auto; padding:30px 0;}
	.rule .rule_con ul{ margin-top:30px;}
	.rule .rule_con ul li{ font-size:14px; color:#fff; line-height:40px; padding:0 15px;}
	.rule .rule_con ul li span{background:url(/images/m/image/ac_icon7.jpg) no-repeat;  height:22px; padding:2px 6px; margin-right:10px; font-size:12px;}
	.reg_bg{ width:1005px; height:135px; background:url(/images/m/image/reg_bg.png) no-repeat; margin:0 auto; font-size:20px; color:#686868; text-align:center; line-height:135px; margin-top:60px;}
	.reg_bg a{ text-decoration:underline; color:#68bbef;}
	.theme-popover{ width:300px; height:300px; position:fixed; display:none; z-index:9999; top:0; background:#fbfbfb; border-radius:8px; left:35%; border:solid 2px #dbdada; font-size:14px;}
	.close{ background:url(/images/m/image/close.png) no-repeat;right:5px;  position:absolute; z-index:10001; height:27px; width:27px;}
	.theme-popover .tibo{ height:30px; padding-left:20px;}
	.login_box{ width:530px; height:677px; position:fixed; left:30%; top:0; display:none; z-index:9999;}
	.login_box .login_content{ background:#61cfe6; border-radius: 5px;bottom: 80px;height: 345px;position: relative;width: 430px; padding:110px 0 0 100px;}
	.thao{ height: 300px;left: 20px;overflow: hidden;position: relative;z-index:10000;}
	.login_content ul{ margin:0;}
	.login_content ul li{ height:58px; margin-bottom:30px; line-height:58px; }
	.login_content .user_bg1{  height:58px; background:url(/images/m/image/ac_input1.jpg) no-repeat;}
	.login_content label{ padding:10px; margin-left:20px;}
	.login_content i{ border-right:1px solid #afafaf; padding:5px 1px; margin:0 8px;}
	.login_content a{ color:#fff; font-size:20px; margin-left:20px;}
	.login_content .login_user_input{  border:none; color:#aeaeae; font-size:20px; background:#f9f9f9;}
	.bg1{ background:url(/images/m/image/ac_icon8.jpg) 0 7px no-repeat;}
	.bg2{ background:url(/images/m/image/ac_icon8.jpg) -26px 7px no-repeat;}
	.login_content .user_bg2{  height:58px; background:url(/images/m/image/ac_input2.jpg) no-repeat; padding-left:15px;}
	.w1{ width:240px;}
	.w2{ width:150px;}
	.login_content .login_user_btn{ width:300px; height:50px; border:none; border-radius:5px; background:#1c68aa; text-align:center; font-size:30px; color:#fff; cursor:pointer;}
	.theme-popover-mask{z-index: 9998;position:fixed;top:0;left:0;width:100%;height:100%;background:#000;opacity:0.4;filter:alpha(opacity=40);display:none}
	.tp1{ position:absolute; top:240px;}
	.tp2{ position:absolute; top:5px;}
	.load_con{ width:100%; height:300%; position:absolute; background:#fff; filter:alpha(opacity=30);  -moz-opacity:0.3;  -khtml-opacity: 0.3;  opacity: 0.3; top:0; }

</style>

<script>
			
jQuery(document).ready(function($) {
	$('.theme-login').click(function(){
		genCode();
	})

})
</script>
<script type="text/javascript">
	var geo_protocol = (location.protocol == "https:");
	document.write('<script type="text/javascript" async="async" src="'+ (geo_protocol ? "https://sslcdn.geotmt.com" : "http://ga.istreamsche.com") +'/CPA/GEO_REACH.js"></scri'+'pt>');
</script>

</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="activity">
	<div class="banner"></div>
<!--注册方式-->
    <div class="reg_list">
    	<div class="reg_list_con">
        <div style="position:absolute; top:-21px;"><img src="activity_images/ac_img11.jpg" width="200" height="64" /></div>
        	<div class="list_con_top">
                <ul>
                    <li class="mt"><img src="activity_images/ac_img1.jpg" width="331" height="365" /></li>
                    <li class="mt"><img src="activity_images/ac_img2.jpg" width="335" height="365" /></li>
                    <li style="margin-top:5px;"><img src="activity_images/ac_img3.jpg" width="341" height="434" /></li>
                </ul>
            </div>
            <div class="list_con_bot">
            	<ul>
                	<li class="wenxin"><a href="javascript:;" class="theme-login"><img src="activity_images/ac_bnt1.jpg" width="208" height="50" /></a></li>
                </ul>
                <ul style="margin-left:100px;">
                	<li class="lianjie"><a href="#"><img id="img_cp_link" src="activity_images/ac_bnt2.jpg" width="208" height="50" data-clipboard-target="reconmmend_link"/></a></li>
                </ul>
            </div>
      </div>
    </div>
<!--注册方式end-->
<!--推荐开始-->
<div class="recommend">
<!--推荐享好礼-->
	<div class="gift">
    	<ul>
        	<li><img src="activity_images/ac_img4.jpg" width="1004" height="393" /></li>
            <li><img src="activity_images/ac_img5.jpg" width="1004" height="290" /></li>
            <li><img src="activity_images/ac_img6.jpg" width="1004" height="290" /></li>
        </ul>
    </div>
<!--推荐享好礼end-->
<!--我的邀请记录-->
<div class="friend" id="friend_list">
	<div class="friend_title">
    	<ul>
        	<li>累计现金奖励：<span id="cashAmountTotal"></span>元</li>
            <li>剩余邀请券：<span id="availableRecommendCount"></span>张</li>
            <li><a href="finance.do?m=1&type=7">获得邀请券</a></li>
        </ul>
    </div>
    <div class="friend_con">
    	<ul class="coff">
        	<li>用户名</li>
            <li>是否身份验证</li>
            <li>奖励</li>
        </ul>
    </div>
    <div class="friend_content" id="friend_content">
    </div>
    <div class="friend_bot"></div>
</div>
 <!--我的邀请记录end-->
 <div class="reg_bg" id="reg_login">
 	<a href="javascript:toLogin();">登陆</a>查看邀请记录
 </div>
</div>
<!--推荐end-->
<!--注册得电影票-->
<div class="reg_con">
	<div class="reg_con_m">
    	<p><a href="javascript:window.location.href='cellPhoneinit.do'"><img src="/images/m/image/ac_btn3.jpg" width="173" height="177" /></a></p>
        <p><img src="/images/m/image/ac_icon5.jpg" width="22" height="20" />在活动中，凡是被推荐新注册用户，注册完成后，即可获得一张姜文导演2015贺岁大片《一步之遥》（也可选其它电影）电影票，以电子兑换码形式直接发放到手机，登陆蜘蛛网电影（film.spider.com.cn）可直接在线选座，参加约影购票。</p>
        <p><img src="/images/m/image/ac_icon6.jpg" width="20" height="19" />被推荐的新用户在注册后，投资1000起，可立马由新人变成推荐人，享注册及推荐的双重壕礼！</p>
      <p>注：蜘蛛电影通兑券不支持兑换</p>
        <p><img src="/images/m/image/xicon.jpg" width="14" height="13" />特殊场次：4D、IMAX场次、双片场、连映场</p>
        <p><img src="/images/m/image/xicon.jpg" width="14" height="13" />特殊时段：2月14日情人节、12月24日平安夜、12月25日圣诞节、12月31日迎新夜、1月1日元旦等</p>
        <p><img src="/images/m/image/xicon.jpg" width="14" height="13" />特殊影厅：导演厅、VIP厅、IMAX厅等特殊影厅</p>
        <p><img src="/images/m/image/xicon.jpg" width="14" height="13" />蜘蛛网标注的"高价"场次</p>
    </div>
</div>
<!--注册得电影票end-->
<!--规则-->
<div class="rule">
	<div class="rule_con">
        <p><img src="/images/m/image/ac_img10.jpg" width="1005" height="33" /></p>
        <ul>
          	<li><span>1</span>被推荐用户和手机号必须真实有效，平台客服也会对活动期间新增注册用户电话进行核实，如发现恶意刷奖的用户，微信贷有权取消其活动资格，所得奖励
         不予承兑。</li>
            <li><span>2</span>每个手机号码、身份证仅限参加一次，刷奖、冒用他人身份证、银行卡者一经核实，取消活动资格，所得奖励不予承兑。</li>
            <li><span>3</span>用户邀请一位好友就会消耗一张邀请券，如果好友未在微信贷单笔投资达500元，将不能获得50元奖励，但邀请券将消耗。</li>
            <li><span>4</span>新用户在PC端登录网站注册时，需填写推荐人的微信贷登陆名（用户名ID），漏填或错填无奖励，也不接受人工补录申请。</li>
            <li><span>5</span>推荐人的投资返利发放按照投资人（被推荐人）收到的利息同步进行分期发放。</li>
            <li><span>6</span>活动期间，没有推荐人的新注册用户不享受注册优惠奖励。</li>
            <li><span>7</span>活动期结束后如邀请奖励券未使用自动作废。</li>
            <li><span>8</span>活动期间产生的任何提现费用及由用户自理。</li>
            <li><span>9</span>活动结束时间2014-12-15-00:00到2015-1-15-00:00</li>
            <li><span><i style="margin-left:-5px;">10</i></span>本活动最终解释权归微信贷所有。</li> 
        </ul>
    </div>
</div>
</div>
<!--规则end-->
<form method="post">
<div class="login_box">
	<div class="thao"><img src="/images/m/image/tuhao.png" width="500" height="301" /></div>
  <div class="login_content">
  	<ul>
    	<li class="user_bg1"><label class="bg1"></label><i></i><input name="paramMap.email" id="email" type="text" class="login_user_input w1"  value="请输入用户名"onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" onFocus="if (value =='请输入用户名'){value =''}" onBlur="if (value ==''){value='请输入用户名'}"/><a href="cellPhoneinit.do">立即注册</a></li>
        <li class="user_bg1"><label class="bg2"></label><i></i>
        
        <input  id="ipt_pwd" class="login_user_input w1" type="text" value="请输入密码"
         onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" 
        onFocus="pwdFocus();" onBlur="if (value ==''){value='请输入密码'}"/>
      
        <input name="paramMap.password" id="ipt_pwd_value"  class="login_user_input w1" type="password" value=""
         onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" onFocus="if (value =='请输入密码'){type = 'password';value =''}"
         onBlur="pwdBlur();"/>
        
        <a href="forgetpassword.do">忘记密码？</a></li>
        <li class="user_bg2"><input name="paramMap.code" id="code" type="text" class="login_user_input w2"  value="请输入验证码"
        onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" onFocus="if (value =='请输入验证码'){value =''}" 
        onBlur="if (value ==''){value='请输入验证码'}"/>
			 <img src="${sitemap.adminUrl}/imageCode.do?pageId=userlogin" title="点击更换验证码"
											style="cursor: pointer; margin-left:10px;" id="codeNum" width="120" height="40"
											onclick="javascript:switchCode()" />
			
		</li>
    </ul>
    <p><input id="btn_login" name="" type="button" class="login_user_btn"  value="登陆"/></p>
  </div>
  <div class="theme-poptit"><a href="javascript:close2();" title="关闭" class="close tp1"></a></div>
</div>
</form>
<div class="theme-popover-mask"></div>
    <div class="theme-popover">
        <div>
       	  	<p class="tibo" style="border-bottom:solid 1px #c9c9c9; padding-top:20px;" >分享到微信朋友圈</p>
            <div id="codes" style="margin: 20px auto;left:50px;width:135px;height:135px"></div>
            <p class="tibo" style="border-top:solid 1px #c9c9c9; padding-top:10px;">打开微信使用"扫一扫"即可将网页分享到我的微信朋友圈。</p>
        </div>	
        <div class="theme-poptit"><a href="javascript:close();" title="关闭" class="close tp2"></a></div>
    </div>
    <div class="load_con" id="loading"><span><img src="../images/admin/load.gif" class="load" alt="加载中..."  style="position:absolute; top:50%; left:50%;"/></span></div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<input type="hidden" id="userId" value="${user.id}"/>
<input type="hidden" id="reconmmend_link" value="${reconmmend_url}"/>
<input type="hidden" id="reconmmend_url_code" value="${reconmmend_url_code}"/>

<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="js/ZeroClipboard.js"></script>

<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>
<script>
var user = new MUser();
$("#ipt_pwd_value").hide();
function pwdFocus(){
	var pwd = $("#ipt_pwd");
	var pwd_value = $("#ipt_pwd_value");
	if (pwd.val() =='请输入密码'){
		pwd_value.show();
		pwd.hide();
		
		pwd_value.focus();
	}
}

function pwdBlur(){
	var pwd = $("#ipt_pwd");
	var pwd_value = $("#ipt_pwd_value");
	if (pwd_value.val() ==''){
		pwd_value.hide();
		pwd.show();
	}
}


	var param = {};
	
	if(islogin()){
		$("#friend_list").show();
		$("#reg_login").hide();
		getList();
	}else{
		$("#reg_login").show();
		$("#friend_list").hide();
		$("#loading").hide();
	}
	
	function islogin(){
		var userId = $("#userId").val();
		if(userId!=null && userId != undefined && userId !='' && userId>0){
			return true;
		}else{
			return false;
		}
	}
	
	function toLogin(){
		$('.theme-popover-mask').fadeIn(100);
		$('.login_box').slideDown(200);
	}
	
	function getList(){
		var url = "/queryReconmmendInfo.do";
		param["type"] = 1;
		$.ajax({
		    type: "post",
		    url: url,
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	var friend_content = $("#friend_content");
		    	friend_content.empty();
		    	var strHtml = "";
		        $("#reconmmend_link").attr("value",data.reconmmend_url);
		        $("#reconmmend_url_code").val(data.reconmmend_url_code);
		        var rsb = data.recommendBase;
		    	var rs = data.recommnedSummary;
		    	settingSummary(rsb,rs);
		        
		    	for(var i=0;i<data.result.length;i++){
		    		var o = data.result[i];
		    		strHtml +="<ul>";
		    		strHtml +="<li>"+o.username+"</li>";
		    		if(o.authentication==1){
		    			strHtml +="<li><img src='activity_images/ac_icon3.jpg' width='31' height='22' /></li>";
		    		}else{
		    			strHtml +="<li><img src='activity_images/ac_icon4.jpg' width='31' height='22' /></li>";
		    		}
					if(o.cashAmountTotal>0){
						strHtml +="<li><img src='activity_images/ac_img8.jpg' width='115' height='37' /></li>";
					}else{
						strHtml +="<li><img src='activity_images/ac_img9.jpg' width='115' height='37' /></li>";
					}
					strHtml +="</ul>";
		    	}
		    	friend_content.append(strHtml);
				$("#loading").hide();
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	$("#loading").hide();
		       alert(errorThrown);
		    }
		});
	}
	
	function settingSummary(rsb,rs){
		// 设置汇总数据
		if(rsb!=null && rsb!= 'undefined' && rsb!=undefined){
			$("#availableRecommendCount").append(rsb.availableRecommendCount);
		}else{
			$("#availableRecommendCount").append("0");
		}
		
		if(rs!=null && rs!= 'undefined' && rs!=undefined){
	    	$("#cashAmountTotal").append(rs.cashAmountTotal);
		}else{
	    	$("#cashAmountTotal").append("0");
		}
		
	}
	
	var temp=0;
	function genCode(){
		if(islogin()){
			$('.theme-popover-mask').fadeIn(100);
			$('.theme-popover').slideDown(200);
			var conCent= $("#reconmmend_url_code").val();
			if(temp==0){
				user.genQCode("codes", conCent);
			}
			temp = 1;
		}else{
			$('.theme-popover-mask').fadeIn(100);
			$('.login_box').slideDown(200);
			//window.location.href = "toActivityPageLogin.do";
		}
	}
	
	function close(){
		$('.theme-popover-mask').css("display","none");
		$('.theme-popover').css("display","none");
	}
	
	function close2(){
		$('.theme-popover-mask').css("display","none");
		$('.login_box').css("display","none");
		
	}
	
	
	// 定义一个新的复制对象
	var clip = new ZeroClipboard($("#img_cp_link"), {
	  moviePath: "js/ZeroClipboard.swf"
	} );

	// 复制内容到剪贴板成功后的操作
	clip.on('complete', function(client, args) {
		if(islogin()){
	   		//alert("复制成功："+ args.text);
	   		alert("复制成功,复制内容可直接粘贴!");
		}else{
			$('.theme-popover-mask').fadeIn(100);
			$('.login_box').slideDown(200);
		}
	} );
	
</script>

<script>

//初始化
function switchCode(){
	var timenow = new Date();
	$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=userlogin&d="+timenow);
}
$(document).ready(function(){
//===========input失去焦点
     $("form :input").blur(function(){
       //email
             if($(this).is("#email")){   
            if(this.value==""){   
                $("#s_email").attr("class", "formtips onError");
                $("#s_email").html("*请输入用户名或邮箱地址");
            }else if(/^([a-zA-Z0-9_-])+((\.(([a-zA-Z0-9_-])+)){0,1})+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.value)){ //判断用户输入的是否是邮箱地址
            	checkRegister('email');
            }else if((/^1[3,5,8]\d{9}$/.test(this.value))){
                checkRegister('cellphone');
            }else{ 
            	checkRegister('userName');
            	  $("#s_email").attr("class", "formtips");
            }   
        }  
     //password
   	   if($(this).attr("id")=="password"){
   	    pwd=this.value; 
     if(this.value==""){
      	$("#s_password").attr("class", "formtips onError");
        $("#s_password").html("*请輸入您的密码");  
     }else if(this.value.length<6){ 
      	$("#s_password").attr("class", "formtips onError");
      $("#s_password").html("*密码长度为6-20个字符"); 
     }else{
     $("#s_password").html(""); 
      	$("#s_password").attr("class", "formtips");
     }
   }
   
   	  //验证码
   	   if($(this).attr("id")=="code"){
     if(this.value==""){
     $("#s_code").attr("class", "formtips onError");
      $("#s_code").html("*请输入验证码"); 
     }else{   
        		$("#s_code").attr("class", "formtips");
                $("#s_code").html(""); 
            } 
   }
       
      });
});


//===验证数据
function checkRegister(str){
  var param = {};
  	if(str == "userName"){
		param["paramMap.email"]  = "";
		param["paramMap.userName"] = $("#email").val();
	}else if(str=="email"){
		param["paramMap.email"] = $("#email").val();
		param["paramMap.userName"] = "";
	}else{
		param["paramMap.email"] = "";
		param["paramMap.userName"] = "";
		param["paramMap.cellphone"] = $("#email").val();
	}
	$.post("ajaxCheckLog.do",param,function(data){
	    $("#email_").hide();
          if(data == 2 && str == "userName"){
             $("#s_email").html("*无效用户！");
          }else if(data == 3 && str == "userName"){
             $("#s_email").html("*该用户还没激活！");
             //add by houli
             $("#email_").show();
          }else if(data == 4&& str == "userName"){
          	$("#s_email").attr("class", "formtips");
        	$("#s_email").html("");
          }
          if(data == 0 && str == "email"){
             $("#s_email").html("*无效邮箱！");
          }else if(data == 1 && str == "email"){
             $("#s_email").html("*该邮箱用户还没激活！");
             //add by houli
             $("#email_").show();
          }else if(data == 4&& str == "email"){
          	$("#s_email").attr("class", "formtips");
        	$("#s_email").html("");
          } 
          if(data == 5 && str == "cellphone"){
             $("#s_email").html("*用户不存在！");
          }else if(data == 4 && str == "cellphone"){
            $("#s_email").html("");
          }
	});
}


</script>

<script>
function login(){
	$(this).attr('disabled',true);
    if($("#email").val()==""){
            alert("请输用户名或邮箱地址");
        }
        if($("#ipt_pwd_value").val()==""){
        	alert("请输入密码");   
           // $("#retake_password").hide();
        }  
     $('#btn_login').attr('value','登录中...');
    var param = {};
	param["paramMap.pageId"] = "userlogin";
	param["paramMap.email"] = $("#email").val();
	param["paramMap.password"] = $("#ipt_pwd_value").val();
	param["paramMap.code"] = $("#code").val();

	$.post("logining.do",param,function(data){
		if(data == 1){
			window.location.href = "toActivityPage.do";
		}else if (data == 2) {
			$('#btn_login').attr('value','登录');
			alert("验证码错误！"); 
			switchCode();
			$("#btn_login").attr("disabled",false); 
		} else if (data == 3) {
		 $('#btn_login').attr('value','登录');
				alert("用户名或密码错误！"); 
	            switchCode();
	            $("#btn_login").attr('disabled',false); 
		} else if (data == 4) {
		 $('#btn_login').attr('value','登录');
			 switchCode();
			 alert("该用户已被禁用！"); 
	         $("#btn_login").attr('disabled',false); 
		}else if (data == 5) {
			 $('#btn_login').attr('value','登录');
				 switchCode();
				 alert("该用户已被限制登录，请于三小时以后登录！"); 
	                $("#btn_login").attr('disabled',false); 
			} else if (data == 7) {
				$('#btn_login').attr('value','登录');
				switchCode();
				alert("该用户账号出现异常，请速与管理员联系！"); 
	            $("#btn_login").attr('disabled',false); 
			}
	});
}

function reSendEmail(){
   if($("#email").val()==""){
     alert("请输入邮箱");
     return;
   }
   window.location.href = "reActivateEmail.do?email="+$("#email").val();
}

 $("#btn_login").click(function(){
		    login();  
		  });


</script>

</body>
</html>