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
<style type="text/css">
.form_s{
 	width:226px;
 	height:30px;
 	 padding:0 10px;
 	 border:1px solid #96ccf4;
 	border-radius:3px;
 	 
}
.form_s:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
}
</style>
</head>
<body> 
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

        
 
<!--我的微信贷主要内容-->
<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
 
        <jsp:include page="/include/left.jsp" ></jsp:include>
        <div class="lne_centr">
           <!--还款情况--> 
            <div class="lne-centr-con">
                <div class="lne-centr-con-content">
                	<div style="padding:0 20px;">
                    	<p class="blue-title"><i>微信贷温馨提示：</i>我们会保管您的资料安全不被泄露，请你放心填写。</p>
                        	
                        	<s:if test="#request.flag == 0">
                        	   <div class="tb_list clearfix">
                            	<p class="login-p">登陆密码修改<i style="font-size:12px;"></i></p>
                                <form>
                                    <div class="form-group">
                                      <label >原密码</label>
                                      <input type="password" class="form_s" placeholder="请输入您现在的帐号密码" id="oldPassword"/>
                                    </div>
                                     <div class="form-group">
                                      <label >新密码</label>
                                       <input type="password" class="form_s" placeholder="请输入新密码" id="newPassword"/><i style="font-color:#96ccf4;">密码为6-20个字符</i>
                                    </div>
                                    <div class="form-group">
                                      <label >确认新密码</label>
                                      <input type="password" class="form_s" placeholder="请再次输入您的新密码" id="confirmPassword"/>
                                    </div>
                                    <input type="button" value="提 交 " class="btn btn-warning btn-lg" style=" margin-left:150px;" onclick="javascript:updateLoginPassword();"/>
                                    <span id="s_tip" class="formtips"></span>
                      		</form>
						    </div>
						    </s:if>
						    <s:else>
						     <div class="tb_list clearfix">
                            	<p class="login-p">交易密码修改<i style="font-size:12px;">（初始的交易密码即为初始登录密码）</i></p>
                                <form>
                                    <div class="form-group">
                                      <label >原密码</label>
                                      <input type="password" class="form_s" placeholder="请输入您现在的帐号密码" id="oldDealpwd" />
                                    </div>
                                     <div class="form-group">
                                      <label >新密码</label>
                                       <input type="password" class="form_s" placeholder="请输入新密码" id="newDealpwd"/> <i style="font-color:#96ccf4;">密码为6-20个字符</i>
                                    </div>
                                    <div class="form-group">
                                      <label >确认新密码</label>
                                      <input type="password" class="form_s" placeholder="请再次输入您的新密码" id="confirmDealpwd"/>
                                    </div>
                                    <input type="button" value="提 交 " class="btn btn-warning btn-lg" style="margin-left:150px;" onclick="javascript:updateDealPassword();"/>
                                    <a href="forgetdealpwd.do"  style="margin-left:5px;">忘记密码？</a> 
                                    <span id="d_tip"
												class="formtips"></span>
                      		</form>
						    </div>
						    </s:else>
						 
						 
                        </div>
                </div>    
            </div>
        </div>
    </div>
</div>
<!--我的微信贷主要内容-->
<!--返回顶部-->
 			
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="script/nav-zh.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script>
	$(function() {
		dqzt(4);
		$('#li_5').addClass('on');
	});
	function jumpUrl(obj) {
		window.location.href = obj;
	}
	function jump(url) {
		
		alert("请先回答密保问题！");
		window.location.href = url;
}
	function bindingPhoneLoad(url) {
		/**		var bb = '${person}';//申请手机绑定必须先填写个人资料
				var cc = '${pass}';
				if (bb == "0") {
					alert("请先完善个人信息");
					window.location.href='owerInformationInit.do';
				} else if (cc != 3) {
					alert("请等待个人信息审核通过");
					return ;
				} else {*/
		window.location.href = url;
		//		}
	}
	//加载该用户提现银行卡信息
	function loadBankInfo(url) {
		var bb = '${person}';//设置提现银行卡必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href = "owerInformationInit.do";
		} else {
			window.location.href = url;
		}

	}
	//工作认证
	function loadWorkInit(url) {
		var bb = '${person}';//填写工作信息必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href = "owerInformationInit.do";
		} else {
			window.location.href = url;
		}
	}

	function updatePwd(url) {
		window.location.href=url;
		/*var bb = '${isApplyPro}';
		if (bb == 1) { 
			alert("请先设置安全问题");
			window.location.href="queryQuestion.do";
		}else if(bb == 2){
			alert("请先回答安全问题");
			window.location.href="getQuestion.do";
		}*/
	}
	//登录密码修改
	function updateLoginPassword() {
		param["paramMap.oldPassword"] = $("#oldPassword").val();
		param["paramMap.newPassword"] = $("#newPassword").val();
		param["paramMap.confirmPassword"] = $("#confirmPassword").val();
		param["paramMap.type"] = "login";
		if ($("#oldPassword").val() == "" || $("#newPassword").val() == ""
				|| $("#confirmPassword").val() == "") {
			$("#s_tip").html("请完整填写密码信息");
			return;
		} else if ($("#newPassword").val().length < 6
				|| $("#newPassword").val().length > 20) {
			$("#s_tip").html("密码长度必须为6-20个字符");
			return;
		} else if ($("#newPassword").val() != $("#confirmPassword").val()) {
			$("#s_tip").html("两次密码不一致");
			return;
		}
		$.shovePost("updateLoginPass.do", param, function(data) {
			if (data == 1) {
				alert("两次密码输入不一致");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			} else if (data == 3) {
				alert("新密码修改失败");
				$("#oldPassword").attr("value", "");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			} else if (data == 2) {
				alert("旧密码错误");
				$("#oldPassword").attr("value", "");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			} else if (data == 5) {
				alert("*修改失败！你的账号出现异常，请速与管理员联系！");
				$("#oldPassword").attr("value", "");
				$("#newPassword").attr("value", "");
				$("#confirmPassword").attr("value", "");
			}else if (data == 6) {
				alert("登录密码不能和交易密码一样！");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else {//密码修改成功，跳到登录页面
					alert("修改密码成功");
					$("#oldPassword").attr("value", "");
					$("#newPassword").attr("value", "");
					$("#confirmPassword").attr("value", "");
					window.location.href = "owerInformationInit1.do";
				}
			});
	}

	//交易密码修改
	function updateDealPassword() {
		param["paramMap.oldPassword"] = $("#oldDealpwd").val();
		param["paramMap.newPassword"] = $("#newDealpwd").val();
		param["paramMap.confirmPassword"] = $("#confirmDealpwd").val();
		param["paramMap.type"] = "deal";
		if ($("#oldDealpwd").val() == "" || $("#newDealpwd").val() == ""
				|| $("#confirmDealpwd").val() == "") {
			$("#d_tip").html("请完整填写密码信息");
			return;
		} else if ($("#newDealpwd").val().length < 6
				|| $("#newDealpwd").val().length > 20) {
			$("#d_tip").html("密码长度必须为6-20个字符");
			return;
		} else if ($("#newDealpwd").val() != $("#confirmDealpwd").val()) {
			$("#d_tip").html("两次密码不一致");
			return;
		}
		$.shovePost("updateLoginPass.do", param, function(data) {
			if (data == 1) {
				alert("两次密码输入不一致");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 3) {
				alert("新密码修改失败");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 2) {
				alert("旧密码错误");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 4) {
				//add by lw
				alert("密码长度输入错误,密码长度范围为6-20");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else if (data == 5) {
				alert("*修改失败！你的账号出现异常，请速与管理员联系！");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			}  else if (data == 7) {
				alert("交易密码不能和登录密码一样！");
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
			} else {//密码修改成功，跳到登录页面
				alert("修改密码成功");
					//alert("修改密码成功,新密码为:" + $("#newDealpwd").val());
					//window.location.href='login.do';
				$("#oldDealpwd").attr("value", "");
				$("#newDealpwd").attr("value", "");
				$("#confirmDealpwd").attr("value", "");
				window.location.href = "owerInformationInit1.do";
			}
		});
	}
</script>
	</body>
</html>
 