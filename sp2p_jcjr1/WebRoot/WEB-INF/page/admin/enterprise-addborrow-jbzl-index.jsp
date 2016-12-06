<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>基本信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			function AddCopyInfo(){
				
		        
		     var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		    if($("#companyPhone").val() ==""){
		    $("#u_companyPhone").attr("class", "formtips onError");
		    $("#u_companyPhone").html("请填写居住电话");
		    return;
		    }else if(!mdd.test($("#companyPhone").val())){
		       $("#u_companyPhone").attr("class", "formtips onError");
		       $("#u_companyPhone").html("请填写正确的居住电话"	);
		       return;
		    }else{
		    $("#u_companyPhone").attr("class", "formtips");
		       $("#u_companyPhone").html("");
		    }
		  
				
				var param = {};
				param["paramMap.companyName"] = $("#companyName").val();
				param["paramMap.legalPerson"] = $("#legalPerson").val();
				param["paramMap.registeredTime"] = $("#registeredTime").val();
				param["paramMap.registeredCapital"] = $("#registeredCapital").val();
				param["paramMap.businessCode"] = $("#businessCode").val();
				param["paramMap.companyAddress"] = $("#companyAddress").val();
				param["paramMap.companyPhone"] = $("#companyPhone").val();
				param["paramMap.borrowCause"] = $("#borrowCause").val();
				param["paramMap.applyId"] = '${applyId}';
				param["paramMap.userId"] = '${userId}';
				var applyId = '${applyId}';
				//var userId = '${userId}';
				
				
				$.post("updateEnterpriseUserBaseInfo.do",param,function(data){
					if(data.msg=="保存成功"){
			            alert("保存成功"); 
			            window.location.href='enterpriseUserUploadInit.do?paramMap.applyId='+applyId+'&paramMap.type=2';
			       }else if(data.msg=="保存失败"){
			    	   alert("保存失败");
				   }

					 if(data.msg=="请正确填写企业名称"){
					       alert("请正确填写企业名称");
					 }

					 if(data.msg=="请正确填写法定人姓名"){
					       alert("请正确填写法定人姓名");
					 }
					 
					 if(data.msg=="法定人姓名的长度为不小于2和大于20"){
					       alert("法定人姓名的长度为不小于2和大于20");
					 }

					 if(data.msg=="请正确填写注册资金"){
					       alert(" 请正确填写注册资金");
					 }
					 
					 if(data.msg=="请正确填写成立日期"){
					       alert("请正确填写成立日期");
					 }
					 
					 if(data.msg=="请正确填写营业执照号"){
					       alert("请正确填写营业执照号");
					 }
					 
					 if(data.msg=="请正确填写注册地址"){
					       alert("请正确填写注册地址");
					 }
					 
					 if(data.msg=="请正确填写公司电话"){
					       alert("请正确填写公司电话");
					 }
					 
					 if(data.msg=="请正确填写借款原因"){
					       alert("请正确填写借款原因");
					 }  

				       
				});
			}
		</script>

	</head>
	<body>
	<form id="editForm"  method="post">
			<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="28"  class="main_alll_h2">
									<a href="enterpriseUserBaseInfoInit.do?paramMap.applyId=${applyId }">基本信息</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="xxk_all_a">
								<s:if test="#request.uId==-1">
							   		<a href="enterpriseUserUploadInit.do?paramMap.applyId=${applyId }&paramMap.type=2">上传资料</a>
								</s:if>
								<s:else>
								        <a href="enterpriseUserUploadInit.do?paramMap.applyId=${applyId }&paramMap.type=2">上传资料</a>
								</s:else>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28"  class="xxk_all_a">
								<s:if test="#request.authStep==2">
									<a href="publishBorrowInit.do?paramMap.applyId=${applyId }&paramMap.type=2">发布借款</a>
								</s:if>
								<s:else>
							 		<a href="publishBorrowInit.do?paramMap.applyId=${applyId }&paramMap.type=2">发布借款</a>
								</s:else>
								</td>
								<td width="2">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
					<div id="u90_rtf"><p style="text-align:left;">
					<span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#FF0000;">*</span>
					<span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;"> 
					为必填项，所有资料均会严格保密。</span></p></div>
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 企业名称：
								</td>
								<td align="left" class="f66">
									<input type="text" class="inp188" name="paramMap.companyName"
								id="companyName" value="${map.companyName }"
								 />
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 法定人：
								</td>
								<td align="left" class="f66">
									<input type="text" class="inp188" name="paramMap.legalPerson"
								id="legalPerson" value="${map.legalPerson }"
								 />
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 注册资金：
								</td>
								<td align="left" class="f66">
									<input type="text" class="inp188" name="paramMap.registeredCapital"
								id="registeredCapital" value="${map.registeredCapital }"
								 />
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 成立日期：
								</td>
								<td align="left" class="f66">
<%--									<input id="registeredTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>--%>
							<input type="text" class="inp188 Wdate" name="paramMap.registeredTime"
								id="registeredTime" value="${registeredTime }"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" 
								/>
							<span style="color: red; float: none;" id="u_registeredTime" class="formtips"></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 营业执照号：
								</td>
								<td align="left" class="f66">
									<input type="text" class="inp188" name="paramMap.businessCode"
								id="businessCode" value="${map.businessCode }"
								 />
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 注册地址：
								</td>
								<td align="left" class="f66">
									<input type="text" class="inp188" name="paramMap.companyAddress"
								id="companyAddress" value="${map.companyAddress }"
								 />
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 公司电话：
								</td>
								<td align="left" class="f66">
									<input type="text" class="inp188" name="paramMap.companyPhone"
								id="companyPhone" value="${map.companyPhone }"
								 />
								 <span style="color: red; float: none;" id="u_companyPhone"
														class="formtips"></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span style="color: red;">* </span> 借款原因：
								</td>
								<td align="left" class="f66">
									<textarea style="height: 100px; width: 320px; border: 1px solid #ddd;" id="borrowCause" >${map.borrowCause }</textarea> 
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
                                <input id="jc_btn" value="修改并保存" onclick="AddCopyInfo();" style="background: #666666;color: white;width: auto;" type="button"  />
                                <span style="color: red;">
                             	  <s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror>
                                </span>
                            </td>
							</tr>
						</table>
						<br />
					</div>
				</div>
		</form>
	</body>
</html>
