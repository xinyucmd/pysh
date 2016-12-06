<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		
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
								alert("请正确填写联系人");
								return;
							} else if ($("#tname").val().length < 2
									|| $("#tname").val().length > 20) {
								alert("名字长度为2-20个字符");
								return;
							} else if (!tname.test($("#tname").val())) {
								$("#tname").html("联系人输入有误");
								alert("联系人输入有误");
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
							} else if (!/^1[3,5,8]\d{9}$/.test(telphone)) {
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
							
							
							var param = {};
							param["paramMap.companyname"] = $("#companyname").val();
							param["paramMap.registnumber"] = $("#registnumber").val();
							param["paramMap.tname"] = $("#tname").val();
							param["paramMap.telphone"] = $("#telphone").val();
							param["paramMap.cityaddress"] = $("#cityaddress").val();
							param["paramMap.borrowAmount"] = $("#borrowAmount").val();
							param["paramMap.deadline"] = $("#deadline").val();
							param["paramMap.borrowPurpose"] = $("#borrowPurpose").val();
							

							$.post("addApplyAction.do", param, function(data) {
								if (data.msg == 1) {
									alert("申请成功");
									window.location.href = 'enterpriseAddBorrowInit.do';
								} else {
									alert(data.msg);
								}
							});
						});

	});
</script>
		
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
								<a href="enterpriseAddBorrowInit.do">企业融资</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="main_alll_h2">
								&nbsp;<a href="addApplyInit.do">添加企业融资</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
						
						<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						
						<table width="400" border="0" cellspacing="0" cellpadding="0">
                        <tr height="45">
                            <td width="100">企业名称</td>
                            <td width="300"><input type="text" class="jky_inp"  name="paramMap.companyname" id="companyname" />
                            </td>
                        </tr>
                        <tr height="45">
                            <td>注册号</td>
                            <td><input type="text" class="jky_inp" name="paramMap.registnumber" id="registnumber" /></td>
                        </tr>
                        <tr height="45">
                            <td>联系人</td>
                            <td><input type="text" class="jky_inp" name="paramMap.tname" id="tname" /></td>
                        </tr>
                        <tr height="45">
                            <td>联系电话</td>
                            <td><input type="text" class="jky_inp" name="paramMap.telphone" id="telphone" /></td>
                        </tr>
                        <tr height="45">
                            <td>城市所在地</td>
                            <td><select class="jky_inp inpa1" id="cityaddress">
                            <option value="">--请选择--</option>
                            <s:iterator value="#request.cityList">
                            	<option value="<s:property />"><s:property /></option>
                            </s:iterator>  
                            </select></td>
                        </tr>
                        <tr height="45">
                            <td>借款金额</td>
                            <td><input type="text" class="jky_inp inpa2" name="borrowAmount" id="borrowAmount" /> 元</td>
                        </tr>
                        <tr height="45">
                            <td>借款期限</td>
                            <td><input type="text" class="jky_inp inpa2" name="deadline" id="deadline" /> 月</td>
                        </tr>
                        <tr height="45">
                            <td>借款用途</td>
                            <td><input type="text" class="jky_inp" name="borrowPurpose" id="borrowPurpose" /></td>
                        </tr>
                        <tr height="45">
                            <td><input type="button" value="立即申请" id="borrowfa"/></td>
                        </tr>
                        
                        
                        
                    </table>
						
					</div>
				</div>
					</div>
				</div>
			</div>
		</body>
	</html>				