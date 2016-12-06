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
	body { font-size: 14px;  font-family: "微软雅黑"; background-color:#f1e5a0; }
	* { margin: 0; padding: 0; }
	ul, ol { list-style: none; }
	em, i { font-style: normal; }
	img { border: none; vertical-align: middle; }
	.clear { clear: both; }
	.clearfix { zoom: 1; }
	.clearfix:after { display: block; visibility: hidden; content: '.'; height: 0; clear: both; }
	a { font-family: "微软雅黑"; text-decoration: none; color: #666; cursor: pointer; }
	.activity{ width:100%; height:auto; font-family:"微软雅黑";}
	.activity .banner{ width:100%; background:url(/activity_images/images/big_banner.jpg) no-repeat center; height:726px;}
	.main{ width:1083px; margin:0 auto;}
	.reg_con{ overflow:hidden; height:435px;}
	.reg_con .left{ float:left; width:580px; margin-top:25px;}
	.reg_con .left p{ font-size:22px; color:#121212;}
	.reg_con .right{ float:right; background:url(/activity_images/images/reg_bg.jpg) no-repeat; width:347px; height:405px; padding:30px 0 0 28px;}
	.right li{ margin-top:30px;}
	.big_input{ width:306px; height:40px; line-height:40px; border:none; background:url(/activity_images/images/input_bg.jpg) no-repeat; font-size:12px; color:#414141; padding-left:10px;}
	.center_input{ width:169px; height:40px; line-height:40px; background:url(/activity_images/images/input_bg1.jpg) no-repeat; font-size:12px; color:#414141; border:none; padding-left:10px;}
	.center_btn{ width:118px; height:37px; background:#3db1ed; border-radius:5px; border:none; color:#fff; font-size:16px; font-weight:bold; margin-left:10px; cursor:pointer;}
	.check_box{ font-size:16px; margin:18px 0;}
	.check_box a{ color:#414141;}
	.red_btn{ width:314px; height:38px; background:#cf2b2b; border-radius:5px; border:none; color:#fff; cursor:pointer; font-size:16px; font-weight:bold;}
	.link{ background:url(/activity_images/images/link1.jpg) no-repeat; height:3px; margin:50px 0 30px 0;}
	.flow{ font-size:22px; color:#1e1e1e;}
	.flow img{ margin:55px 0 90px 0;}
	.list{ background:#cf2b2b; width:100%; height:auto; color:#f2dddd; font-size:20px;}
	.list_con{ width:1089px; margin:0 auto; padding:60px 0;}
	.list_con p{ line-height:50px;}
	.list_con ul{ margin-left:20px;}
	.list_con ul li{ line-height:40px; background:url(/activity_images/images/icon.jpg) 0 13px no-repeat; padding-left:20px;}
	.bp5{ background:url(/activity_images/images/check_box.jpg) 0 0 no-repeat;  position:relative;  cursor:pointer;}
</style>

<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>

<script>
$(function(){
$(".bp5").toggle(
function () {
$(".agree").attr("checked", true);
	$(this).css({ "background-position": "0 0"});
},
function () {
	$(".agree").attr("checked", false);
	$(this).css({"background-position": "0 -20px"});
	}
);
});

$(function(){
	$(".bp5").click( function () {
		if($('.agree').is(':checked')) {
			$(".agree").attr("checked", false);
			$(this).css({ "background-position": "0 0"});
		}else{
			$(".agree").attr("checked", true);
			$(this).css({"background-position": "0 -20px"});
		}
	});
});

var user = new MUser();

function mFocus(){
	var mn = $("#ipt_mobileNo").val();
	if (mn =='请输入手机号码'){
		$("#ipt_mobileNo").attr("value","");
	}
}

function mBlur(){
	var mn = $("#ipt_mobileNo").val();
	var mobileNo = $("#ipt_mobileNo").val();
	if (mn ==''){
		$("#ipt_mobileNo").attr("value","请输入手机号码");
	}else{
		user.requestMcode(mobileNo,complete);
	}
}

function complete(data){
	if(data.error != -1){
    	alert(data.msg);
    	return;
    }else{
    	// $("#btn_register").attr("disabled","");
    	$("#ipt_randomCode").attr("value",data.randomCode);
    	$("#ipt_recivePhone").attr("value",data.recivePhone);
    }
}

function regester(){
	var mobileNo = $("#ipt_mobileNo").val();
	var pwd = $("#ipt_pwd_value").val();
	var mCode = $("#ipt_mCode").val();
	var recommendUserName = $("#ipt_recommend").val();
	if(pwd == '' || pwd == '请输入密码'){
		alert("密码不能为空");
		return;
	}
	
	if(mCode=='' || mCode=='请输入验证码'){
		alert("请输入手机验证码！");
		return;
	}
	
	if(recommendUserName == '请输入推荐人用户名（可不填）'){
		recommendUserName = '';
	}
	
	if($("#ckb_agree").attr("checked")!='checked'){
		alert("您没有同意会员注册协议！");
		return;
	}
	
	user.regester(mobileNo,pwd,mCode,$("#ipt_randomCode").val(),$("#ipt_recivePhone").val(),recommendUserName,complete_reg);
}

function complete_reg(data){
	if(data.error != -1){
    	alert(data.msg);
    	return;
    }else{
    	window.location.href='login.do';
    }
}
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
    <div class="main">
    	<div class="reg_con">
    		<div class="left">
   	      		<img src="/activity_images/images/img1.jpg" width="428" height="160" />
                <p style="margin-top:74px;">活动时间：2014年12月15日-2015年01月15日</p>
                <p style="margin-top:35px; line-height:40px;">活动内容：用户（包含新老用户）活动期间在微信贷单笔投<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资500元，免费得到<em style="color:#cf2b2b; font-size:28px; font-style:normal;">电影票兑换码1张！</em></p>
            </div>
            <form method="post">
            <div class="right">
            	<ul>
                	<li><input id="ipt_mobileNo"  name="" type="text"  class="big_input" value="请输入手机号码" onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" onFocus="mFocus();" onBlur="mBlur();"/></li>
                    <li>
                    
                    <input id="ipt_pwd" name="" type="text"  class="big_input" value="请输入密码" 
                    onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" 
                    onFocus="pwdFocus();" onBlur="if (value ==''){value='请输入密码'}"/>
                    
                     <input id="ipt_pwd_value" name="" type="password"  class="big_input" value=""
                    onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" 
                    onFocus="if (value =='请输入密码'){value =''}" onBlur="pwdBlur();"/>
                    
                    </li>
                    <li><input id="ipt_mCode" name="" type="text"  class="center_input" value="请输入验证码" onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" onFocus="if (value =='请输入验证码'){value =''}" onBlur="if (value ==''){value='请输入验证码'}"/><input name="" type="button"  class="center_btn" value="获取验证码"/></li>
                    <li><input id="ipt_recommend" name="" type="text"  class="big_input" value="请输入推荐人用户名（可不填）" onmouseover="this.style.borderColor='#FF6600'" onmouseout="this.style.borderColor=''" onFocus="if (value =='请输入推荐人用户名（可不填）'){value =''}" onBlur="if (value ==''){value='请输入推荐人用户名（可不填）'}"/></li>
                </ul>
                <div class="check_box"><i class="bp5" style="width:20px; height:20px; float:left;"> <input class="agree" id="ckb_agree" name="agree" style="display: none;" type="checkbox"/></i><a href="javascript:void(0);">我已经阅读并同意会员注册及服务协议</a></div>
                <div>
                	<input name="" type="button" onclick="regester();"  value="立刻注册" class="red_btn"/>
                </div>
            </div>
            </form>
        </div>
        <div class="link"></div>
      <div class="flow">
       	<p>活动流程</p>
        <p><img src="/activity_images/images/img2.jpg" width="271" height="209" /><img src="/activity_images/images/img3.jpg" width="294" height="209" /><img src="/activity_images/images/img4.jpg" width="291" height="209" /><img src="/activity_images/images/img5.jpg" width="222" height="209" /></p>
       </div>
    </div>
    <div class="list">
    	<div class="list_con">
        	<h3>活动说明</h3>
        	<p>1、获得的电影票以电子兑换码形式直接发放到获奖人手机</p>
            <p>2、用户凭电子兑换码，可兑换一张2015贺岁大片《一步之遥》（也可选其它电影）电影票，登陆蜘蛛网电影（http://
     film.spider.com.cn/）能在线选座，兑换码有效期截止至：2015年1月31日</p>
            <p>3、活动期间，用户单笔投资≥500元，可获得1张电影券，单个用户仅限参加一次，刷奖等作弊行为一经核实，取消活动资格，所得奖励不予承兑</p>
            <p>4、活动结束时间2014-12-15-00:00到2015-1-15-00:00</p>
            <p>5、本活动最终解释权归微信贷所有</p>
            <p>注：蜘蛛电影通兑券不支持兑换</p>
            <ul>
                <li>特殊场次：4D、IMAX场次、双片场、连映场</li>
                <li>特殊时段：2月14日情人节、12月24日平安夜、12月25日圣诞节、12月31日迎新夜、1月1日元旦等</li>
                <li>特殊影厅：导演厅、VIP厅、IMAX厅等特殊影厅</li>
                <li>蜘蛛网标注的"高价"场次</li>
            </ul>
        </div>
    </div>
</div>
<input id="ipt_randomCode" type="hidden" value=""/>
<input id="ipt_recivePhone" type="hidden" value=""/>
<jsp:include page="/include/footer.jsp"></jsp:include>

<script>
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
</script>

</body>

</html>