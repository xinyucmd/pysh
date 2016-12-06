<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="css/css.css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
<link rel="stylesheet" href="../../../css/style.css" type="text/css"></link></head>

<body>
<input type="hidden" id="payId" value="${payMap.id}"/>
<input type="hidden" id="borrowId" value="${payMap.borrowId}"/>
<div class="nymain" style="width:400px;margin-top:-10px;">
  <div class="wdzh" style="border:none;" >
    <div class="r_main" style="border:none;" >
      <div class="box" style="border:none;" >
        <div class="boxmain2" style="border:none;" >
          <div class="biaoge2" style="border:none;" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="16%">账户余额： </td>
    <td width="84%"><strong>${payMap.totalSum}元 </strong></td>
  </tr>
  <tr>
    <td>可用余额： </td>
    <td><strong>${payMap.usableSum }元 </strong></td>
  </tr>
  <tr>
    <td>还款日期： </td>
    <td><strong> ${payMap.repayDate }  </strong></td>
  </tr>
  <tr>
    <td>待还本息：</td>
    <td><strong>${payMap.forPI }元</strong></td>
  </tr>
  <tr>
    <td>逾期本息：</td>
    <td><strong>${payMap.lateFI }元</strong></td>
  </tr>
  <tr>
    <td>需还总额：</td>
    <td><strong>${payMap.needSum }元</strong></td>
  </tr>
  <tr>
    <td>交易密码：</td>
    <td><input type="password" class="inp140" id="pwd" maxlength="20"/>
    <span style="color: red; float: none;" id="pwd_tip" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td>验证码：</td>
    <td><input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="${sitemap.adminUrl}/imageCode.do?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="100" height="30" onclick="javascript:switchCode()" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td style="padding-top:20px;">
    <input type="button" id="btnsave" class="bcbtn" style="cursor:pointer;" value="确认还款"/></td>
    <!--<a style="cursor:pointer;" id="btnsave" class="bcbtn">确认还款</a></td>-->
  </tr>
        </table>
        </div>
    </div>
</div>
    </div>
  </div>
</div>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
var flag = true;
$(function(){
     $('#btnsave').click(function(){
	     param['paramMap.id'] = $("#payId").val();
	     param['paramMap.borrowId'] = $("#borrowId").val();
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.pwd'] = $('#pwd').val();
	     if(flag){
			$("#btnsave").attr("disabled",true);
	     	$('#btnsave').attr("value","受理中...");
	     	$.shovePost('submitPay.do',param,function(data){
		   		var callBack = data.msg;
		   		if(callBack == ''){
		   		  flag = false;
		          alert("操作成功!");
		          //parent.location.reload();
		          parent.location.href = parent.location.href;
		          return false;
		   		}
		   		alert(callBack);
		   		switchCode();
		   		$("#btnsave").attr("disabled",false);
	       		$('#btnsave').attr("value","确认还款");
	       		flag = true;
			});	     
	     }
	 });
	 switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=invest&d='+timenow);
};
</script>
</body>
</html>
