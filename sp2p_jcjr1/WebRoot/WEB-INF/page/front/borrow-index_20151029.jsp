<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
		<title></title>
		<jsp:include page="/include/common.jsp"></jsp:include>
        <meta http-equiv="keywords" content="网络贷款|小额贷款|p2p网上贷款|网络借贷|贷款平台 |个人无抵押贷款">
        <meta http-equiv="description" content="微信贷在全国有几十家分支，面向企业和个人提供优质借款服务，申请借款高效、快捷、费用低。">
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script>
	function switchCode() {
		var timenow = new Date();
		$('#codeNum').attr('src',
				'${sitemap.adminUrl}/imageCode.do?pageId=apply&d=' + timenow);
	};
</script>
	</head>
	<body>
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="s_rzsqbody clearfix">
		<div class="s_rzsqleft">
			<h2>借款申请</h2>
			<form>
				<table cellspacing="0" cellpadding="0" border="0">
					<tr height="34">
						<th><span class="fred">*</span>真实姓名：</th>
						<td><input type="text" class="text" name="paramMap.tname" id="tname" /></td>
					</tr>
					<tr height="24"></tr>
					<tr height="34">
						<th><span class="fred">*</span>手机号码：</th>
						<td><input type="text" class="text" name="paramMap.telphone" id="telphone" /></td>
						<tr height="24"></tr>
					</tr>
					<tr height="34">
						<th><span class="fred">*</span>所在省份：</th>
						<td>
							<select class="" id="cityaddress">
                            <option value="">--请选择--</option>
                            <s:iterator value="#request.cityList">
                            	<option value="<s:property />"><s:property /></option>
                            </s:iterator>  
                            </select>
						</td>
					</tr>
					<tr height="24"></tr>
					<tr height="34">
						<th><span class="fred">*</span>借款金额：</th>
						<td><input type="text" class="text" name="borrowAmount" id="borrowAmount" /> 元</td>
					</tr>
					<tr height="24"></tr>
					<tr height="34">
						<th><span class="fred">*</span>借款周期：</th>
						<td><input type="text" class="text" name="deadline" id="deadline" /> 月</td>
					</tr>
					<tr height="24"></tr>
					<tr height="34">
						<th><span class="fred">*</span>借款用途：</th>
						<td>
							<input type="text" class="text" name="borrowPurpose" id="borrowPurpose" />
						</td>
					</tr>
					<tr height="24"></tr>
					<tr height="34">
						<th><span class="fred">*</span>验证码：</th>
						<td><input type="text" class="yzm" name="paramMap.code" id="code"/>
                            <img src="${sitemap.adminUrl}/imageCode.do?pageId=apply" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />&nbsp;&nbsp;
                            <a href="javascript:void()" onclick="switchCode()" style="color: blue;">看不清，换一换</a></td>
					</tr>
					<tr height="24"></tr>
					<tr height="40">
						<th></th>
						<td><input class="submit" type="button" value="立即申请" id="borrowfa"/></td>
					</tr>
				</table>
				</form>
		</div>
		<div class="s_rzsqright">
			<img src="images/pic111.png" />
		</div>
	</div>
		
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
							/*var companyname = $("#companyname").val();
							if (companyname == "") {
								alert("请填写企业名称");
								return;
							} 
							var registnumber = $("#registnumber").val();
							if (registnumber == "") {
								alert("请填写注册号");
								return;
							} */
							
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
								alert("请填写联系电话");
								return;
							} else if (!/^1[3,4,5,7,8]\d{9}$/.test(telphone)) {
								alert("请正确填写联系电话");
								return;
							}

							var cityaddress = $("#cityaddress").val();
							if (cityaddress == "") {
								alert("请选择城市所在地！");
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
