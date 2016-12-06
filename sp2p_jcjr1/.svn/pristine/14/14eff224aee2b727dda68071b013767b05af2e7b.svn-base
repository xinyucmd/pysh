<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

<form id="editForm">
      <table class="s_tztab" border="0" cellspacing="0" cellpadding="0">
         <tr height="38">
		    <td align="right">可用金额：&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    <td><input type="text" class="text" id="" name="" value="${usableSum}" class="inp140" maxlength="20" disabled="disabled"/>&nbsp;元</td>
		  </tr>
		  <tr height="38">
		    <td width="120" align="right">转让价格：&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    <td width="300">
		    <s:hidden name="paramMap.debtId"></s:hidden>
		    <s:hidden name="paramMap.investId"></s:hidden>
		    <input type="text" class="text" id="debtPrice" name="paramMap.debtPrice" value="${debtPrice}" class="inp140" maxlength="20" readonly="readonly"/> 
		    <input type="hidden" id="debtValue" name="paramMap.debtValue" value="${paramMap.debtValue}"/> 
		    元<strong style="color: red;"><br />&nbsp;债权转让必须全部购买<%-- <s:fielderror fieldName="paramMap.auctionPrice"></s:fielderror> --%></strong></td>
		  </tr>
		  <tr height="38">
		    <td align="right">交易密码：&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    <td><input type="password" class="text" id="pwd" name="paramMap.pwd" value="" class="inp140" maxlength="20" style="width:100px; height:24px; border:solid 1px #ccc;"/>&nbsp;<!-- <a href="querytraninput.do">忘记密码?</a> -->
		    <strong style="color: red;"><br /><s:fielderror fieldName="paramMap.pwd"></s:fielderror></strong></td>
		  </tr>
		  <s:if test="%{paramMap.investPwd != ''}">
		  		<tr>
		  			 <td align="right">投标密码：&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  			 <td><input type="password" class="text" id="pwd" name="paramMap.investPwd" value="" class="inp140" maxlength="20" style="width:100px; height:24px; border:solid 1px #ccc;"/></td>
		  
		  		</tr>
		  </s:if>
		  <s:else>
			  	
		  </s:else>
		  <tr height="38">
		    <td align="right"> 验证码：&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    <td><input type="text" class="inp100x" name="paramMap.code" id="code" />
				 <img src="${sitemap.adminUrl}/imageCode.do?pageId=auction" title="点击更换验证码" style="cursor: pointer;"
				 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
				 	  <strong style="color: red;"><br /><s:fielderror fieldName="paramMap.code"></s:fielderror></strong></td>
		  </tr>
		  <tr height="32">
		    <td colspan="2" class="tishi" style="text-align:center;">注意：点击按钮表示您将确认购买债权并同意支付。</td>
		    </tr>
		  <tr height="42">
		  	<td></td>
		    <td style="padding-top:15px; padding-bottom:15px;">
		  	  <input id="btnsave" type="button" style="cursor: pointer;" class="qdgmbtn" value=""/>
		    </td>
		  </tr>
	</table>
	<input name="paramMap.isUseHb_6_24" value="${isUseHb_6_24}" type="hidden"/>
</form>
<script type="text/javascript">
var flag = true;
$(function(){
	$('#debtPrice').val('${request.debtPrice}');
	$('#debtValue').val('${request.debtValue}');
	 $('#btnsave').click(function(){
	   	 var param = $("#editForm").serializeArray();
	   	$('#btnsave').attr('disabled',true);
	     if(flag){
	     flag=false;
	     $.post("doAddAuctingDebt.do?showDate"+new Date().getTime(),param,function(data){
		  	if(data == "virtual"){
					window.location.href = "noPermission.do";
					return ;
	     	}
		  	if(data == 1){
		  		alert("购买成功");
		  		parent.location.href=parent.location.href;
		  		//window.location.href = "queryFrontAllDebt.do";
		  	}else if(data == -1){
		  	   flag=true;
		  		alert("验证码错误");
		  	}else if(data == -2){
		  	    flag=true;
		  		alert("不能投自己转让的的债权");
		  	}else if(data == -3){
		  	     flag=true;
		  		alert("交易密码不对");
		  	}else if(data == -4){
		  	    flag=true;
		  		alert("可用余额不足");
		  	}else if(data == -5){
		  	     flag=true;
		  		alert("购买金额不能大于债权总额");
		  	}else if(data == -6){
		  	    flag=true;
		  		alert("购买金额不能小于转让者设置的最小购买金额");
		  	}else if(data == -7){
		  	    flag=true;
		  		alert("购买失败");
		  	}else if(data == -8){
		  	     flag=true;
		  		alert("对不起，该债权您只能购买两次!");
		  	}else if(data == -9){
		       	flag=true;
		  		alert("借款者不能购买该债权");
		  	}else if(data == -10){
		  	    flag=true;
		  		alert("第二次购买金额应大于第一次购买金额");
		  	}else if(data == -11){
		  	    flag=true;
		  		alert("购买金额要大于最高购买金额");
		  	}else if (data == -12){
		  		flag=true;
		  		alert("购买金额不能为空");
			}else if (data == -13){
		  		flag=true;
		  		alert("购买金额格式不正确");
			}else if (data == -14){
		  		flag=true;
		  		alert("购买失败");
			}
			else if (data == -333){
		  		flag=true;
		  		alert("投标密码不正确");
			}
			else if (data == 9988){
				flag=true;
		  		alert("超级用户债权投资申请成功！");
		  		parent.location.href=parent.location.href;
			}else{
		  	     flag=true;
		  		$("#div_editform").html(data);
		  	}
		  	if(flag = true){
		  		$('#btnsave').attr('disabled',false);
		  	}
		    switchCode();
		 });
		 }
	 });
	 switchCode();
});

function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=auction&d='+timenow);
};	
</script>

