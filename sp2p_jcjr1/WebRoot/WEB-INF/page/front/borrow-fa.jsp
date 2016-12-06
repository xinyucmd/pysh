<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link href="css/inside.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script>
	function switchCode() {
		var timenow = new Date();
		$('#codeNum').attr('src',
				'${sitemap.adminUrl}/imageCode.do?pageId=apply&d=' + timenow);
	};
</script>
		<script>
	//样式选中
	$("#zh_hover").attr('class', 'nav_first');
	$("#zh_hover div").removeClass('none');
	$('#li_1').addClass('on');
</script>

	</head>
	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/topss.jsp"></jsp:include>

		<div class="nymain">
			<div class="bigbox">
				<div class="til" align="center">
					企业融资
				</div>
				<div class="C_htfb">
					<table cellpadding="0" cellspacing="0">

					<tr>
							<td class="C_ts_td222">
								<span>*</span>企业名称:
							</td>
							<td>
								<input type="text" name="paramMap.companyname" id="companyname"/>
							</td>

						</tr>
						<tr>
							<td class="C_ts_td222">
								<span>*</span>注册号:
							</td>
							<td>
								<input type="text" name="paramMap.registnumber" id="registnumber"/>
							</td>

						</tr>
						
						<tr>
							<td class="C_ts_td222">
								<span>*</span>联系人:
							</td>
							<td>
								<input type="text" name="paramMap.tname" id="tname"/>
							</td>

						</tr>
						
						<tr>
							<td class="C_ts_td222">
								<span>*</span>联系电话:
							</td>
							<td>
								<input type="text" name="paramMap.telphone" id="telphone"/>
							</td>

						</tr>
						
						<tr>
							<td class="C_ts_td222">
								<span>*</span>城市所在地:
							</td>
							<td>
								<input type="text" name="paramMap.cityaddress" id="cityaddress"/>
							</td>

						</tr>
							<tr>
							<td class="C_ts_td222">
								<span>*</span>借款金额:
							</td>
							<td>
								<input type="text" name="borrowAmount" id="borrowAmount" />元
							</td>
						</tr>

						<tr>
							<td class="C_ts_td222">
								<span>*</span>借款期限:
							</td>
							<td>
								<input type="text" name="deadline" id="deadline"/>
								个月
							</td>
						</tr>
						
						<tr>
							<td class="C_ts_td222">
								<span>*</span>借款用途:
							</td>
							<td>
								<input type="text" name="borrowPurpose" id="borrowPurpose"/>
							</td>
						</tr>
						
						<tr>
							<td class="C_ts_td222">
								<span>*</span>验证码:
							</td>
							<td>
								<input type="text" style="width: 80px;" name="paramMap.code" id="code"/>
								<img src="${sitemap.adminUrl}/imageCode.do?pageId=apply" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />&nbsp;&nbsp; 
									<a href="javascript:void()" onclick="switchCode()" style="color: blue;">看不清，换一张！</a>
							</td>

						</tr>
						<tr>
							<td class="C_ts_td22"></td>
							<td>
								<a href="#" class="houtai_btn" id="borrowfa">立即申请</a>
							</td>

						</tr>
					</table>

				</div>

			</div>
		</div>

		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>

		<script>
	$(function() {
		//样式选中
		dqzt(2);
	});
</script>

		<script>
	$(function() {

		$("#borrowfa").click(
						function() {
							var companyname = $("#companyname").val();
							if (companyname == "") {
								alert("请填写企业名称");
								return;
							} 
							var registnumber = $("#registnumber").val();
							if (registnumber == "") {
								alert("请填写注册号");
								return;
							} 
							
							var tname = new Object();
							tname = /^[a-zA-Z\u4e00-\u9fa5]+$/;
							if ($("#tname").val() == "") {
								alert("请正确填写姓名");
								return;
							} else if ($("#tname").val().length < 2
									|| $("#tname").val().length > 20) {
								alert("名字长度为2-20个字符");
								return;
							} else if (!tname.test($("#tname").val())) {
								$("#tname").html("姓名输入有误");
								alert("姓名输入有误");
								return;
							}

							/*var sex = $("input[name='paramMap.sex']:checked")
									.val();
							if (sex == "" || typeof (sex) == "undefined") {
								alert("请勾选性别！");
								return;
							}*/

							var telphone = $("#telphone").val();
							if (telphone == "") {
								alert("请填写手机号码");
								return;
							} else if (!/^1[3,4,5,7,8]\d{9}$/.test(telphone)) {
								alert("请正确填写手机号码");
								return;
							}

							var cityaddress = $("#cityaddress").val();
							if (cityaddress == "") {
								alert("请正确填写地址！");
								return;
							}
							var borrowAmount = $("#borrowAmount").val();
							if (borrowAmount == "") {
								alert("请填写借款金额！");
								return;
							}
							var baRex = new Object();
							baRex = /^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$/;
							if (!baRex.test(borrowAmount)) {
								alert("借款金额格式不正确！");
								return;
							}

							var deadline = $("#deadline").val();
							var deadlineRex = new Object();
							deadlineRex = /^\+?[1-9][0-9]*$/;
							if (deadline == "") {
								alert("请填写借款期限！");
								return;
							} else if (!deadlineRex.test(deadline)) {
								alert("借款期限格式不正确！");
								return;
							}

							var borrowPurpose = $("#borrowPurpose").val();
							if (borrowPurpose == "") {
								alert("请选择借款用途！");
								return;
							}
							
							var code = $("#code").val();
							if (code == "") {
								alert("请正确填写验证码！");
								return;
							}
							var param = {};
							param["paramMap.companyname"] = $("#companyname").val();
							param["paramMap.registnumber"] = $("#registnumber").val();
							param["paramMap.tname"] = $("#tname").val();
							param["paramMap.telphone"] = $("#telphone").val();
							param["paramMap.cityaddress"] = $("#cityaddress").val();
							param["paramMap.borrowAmount"] = $("#borrowAmount").val();
							param["paramMap.deadline"] = $("#deadline").val();
							param["paramMap.borrowPurpose"] = $("#borrowPurpose").val();
							param["paramMap.code"] = $("#code").val();

							$.post("addApply.do", param, function(data) {
								if (data.msg == 1) {
									alert("申请成功");
									window.location.href = 'borrowfa.do';
								} else {
									alert(data.msg);
								}
							});
						});

	});
</script>
	</body>
</html>
