<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${sitemap.siteName}</title>
		<jsp:include page="/include/head.jsp"></jsp:include>
        <jsp:include page="/include/common.jsp"></jsp:include> 
		<link href="css/inside.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<script language="javascript" type="text/javascript"
			src="My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
<div class="ne_wdzh"></div>
<div class="lne_cent">
           <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
		<div class="lne_centr s_centr">
				<div class="myhome_tit tab_meun">
						<ul>
							<%--<li onclick="jumpUrl('owerInformationInit.do');">
								个人详细信息
							</li>
							 	<li onclick="loadWorkInit('queryWorkInit.do');">
								工作认证信息
							</li>
							--%>
							<li class="on">
								修改密码
							</li>
							<%--<li id="li_bp" onclick="bindingPhoneLoad('boundcellphone.do');">
								更换手机
							</li>
							<li onclick="jumpUrl('szform.do');">
								通知设置
							</li>
							<li id="li_tx" onclick="loadBankInfo('yhbound.do');">
								银行卡设置
							</li>
							<s:if test="#request.isApplyPro==1">
								<li onclick="jumpUrl('queryQuestion.do'); ">
									申请密保设置
								</li>
							</s:if>
							<s:if test="#request.isApplyPro==2">
								<li onclick=jump('answerPwdQuestion.do');;
>
									修改密保设置
								</li>
							</s:if>--%>
						</ul>
					</div>
					    	 <div class="myhome_content tab_content clearfix">
									<div class="s_changepw">
				<s:if test="#request.flag == 0">
              	   <div class="mmxg clearfix">
                   	   <h5>会员登录密码修改</h5>
                       <ul class="clearfix">
                           <li><span>原密码：</span><input type="password" class="test" id="oldPassword"/><strong class="notice">*输入您现在的帐号密码</strong></li>
                           <li><span>新密码：</span><input type="password" class="test" id="newPassword"/><strong class="notice">*输入您的新密码</strong></li>
                           <li><span>确认新密码：</span><input type="password" class="test" id="confirmPassword"/><strong class="notice">*请再次输入您的新密码</strong></li>
                           <li><span>&nbsp;</span><input type="button" value="提 交 " class="myhome_btn" onclick="javascript:updateLoginPassword();"/></li>
                           <li><span>&nbsp;</span><span style="color: red; float: none;" id="s_tip"
												class="formtips"></span></li>
                       </ul>
                   </div>
                 </s:if>
                 <s:else>
                   <div class="mmxg clearfix">
                   	   <h5>交易密码修改<span class="font-lt">（初始的交易密码即为初始登录密码）</span></h5>
                       <ul class="clearfix">
                           <li><span>原密码：</span><input type="password" class="test" id="oldDealpwd" /><strong class="notice">*输入您现在的帐号密码</strong></li>
                           <li><span>新密码：</span><input type="password" class="test" id="newDealpwd" id="confirmDealpwd"/><strong class="notice">*输入您的新密码</strong></li>
                           <li><span>确认新密码：</span><input type="password" class="test" id="confirmDealpwd"/><strong class="notice">*请再次输入您的新密码</strong></li>
                           <li><span>&nbsp;</span><input type="button" value=" 提 交 " class="myhome_btn" onclick="javascript:updateDealPassword();"/>
                           	   <a href="forgetdealpwd.do" class="forget-pw">忘记密码？</a>
                           </li>
                           <li><span>&nbsp;</span><span style="color: red; float: none;" id="d_tip"
												class="formtips"></span></li>	
                       </ul>
                   </div>	
                 </s:else>	
              </div>
						

							</div>
						</div>
					</div>
				
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
