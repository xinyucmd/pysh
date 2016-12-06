<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="css/css.css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
<link rel="stylesheet" href="../../../css/style.css" type="text/css"></link>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-lc.js"></script>
</head>
<body>

	<ul class="s_tzlist">
	    <li><span class="li-l">可用余额：</span><strong>${userMap.usableSum} 元</strong></li>
	    <li><span class="li-l">借款标题：</span><strong>${investMaps.borrowTitle} </strong></li>
	    <li><span class="li-l">投标金额：</span>
	        <strong>
	          <input  type="text" id="amount" name="paramMap.amount" class="inp140" maxlength="20" style="height: 30px"/>
	        </strong>
	    </li>
	    <li><span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span></li>
	    <s:if test="#session.hasPWD != -1">
	    <li><span class="li-l">投标密码：</span><input type="password" id="investPWD" name="paramMap.investPWD" class="inp140" maxlength="20" /></li>
	    </s:if>
	    
	    <li><span class="li-l">  </span>
	        <strong>
	          <input  type="checkbox"  checked="checked" name="" disabled="disabled" />
	          <a href="###">您同意支付本投标金额！</a>
	        </strong>
	    </li>
	    
	    <li>
	        <span class="li-l"></span><input id="btnsave" type="button" class="qdtbbtn" value="投标" />
	        <span class="li-l"></span><input id="recharge" type="button" class="qdtbbtn" value="充值"  style="position:relative; margin-left:-120px;"/>
	    </li>
	</ul>

<input name="smallestFlowUnit" type="hidden"  id="smallestFlowUnit"  value="${investMaps.smallestFlowUnit}"/>
<input name="subscribes" type="hidden"  id="subscribes"  value="${subscribes}"/>
<s:hidden name="id" value="%{investDetailMap.id}"></s:hidden>
<s:hidden name="annualRate" value="%{investDetailMap.annualRate}"></s:hidden>
<s:hidden name="deadline" value="%{investDetailMap.deadline}"></s:hidden>
 <s:hidden id="content"></s:hidden>
 
 <script type="text/javascript">
var flag = true;
$(function(){
	$("#amount").val('${request.amountMoney}');
	var amount = '${request.amountMoney}';
	if(!/^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$/.test(amount)){
		$("#amount").val('');
	}
	
     //样式选中
     //dqzt(1);
//     $("#licai_hover").attr('class','nav_first');
//	 $("#licai_hover div").removeClass('none');
//	 $('.tabmain').find('li').click(function(){
//	    $('.tabmain').find('li').removeClass('on');
//	    $(this).addClass('on');
//	    $('.lcmain_l').children('div').hide();
//        $('.lcmain_l').children('div').eq($(this).index()).show();
//	 });
	  $('#invest').click(function(){
	     var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = 1;
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     param['paramMap.result'] = $('#result').val();//认购份数
	     param['paramMap.smallestFlowUnit'] = $('#smallestFlowUnit').val();//每份认购金额
	     param['paramMap.subscribes'] = $('#subscribes').val();//认购模式是否开启
	     if(flag){
           flag = false;
	       $.shovePost('financeInvest.do',param,function(data){
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         $('#content').html(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("操作成功!");
		          window.location.href= 'financeDetail.do?id='+id;
		          flag = false;
		          return false;
		         }
		         alert(callBack);
		         flag = true;
		      }
		    });
		 }
	 });
		
	 $('#btnsave').click(function(){
	     
		 var id =$("#id").val();
	     param['paramMap.id'] = id;
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.annualRate'] = $("#annualRate").val();
	     param["paramMap.deadline"] = $("#deadline").val();
	     param['paramMap.amount'] = $('#amount').val();
	     param['paramMap.investPWD'] = $('#investPWD').val();
	     param['paramMap.bonus_avaliable'] = '${request.bonus_avaliable}';//可用红包金额
	     param['paramMap.isUseHb'] = '${request.isUseHb}';//是否使用红包  1使用0不使用
	     param['paramMap.bonus_type'] = '${request.bonus_type}';//红包类型 1-注册，2-投资，3-总和
	     param['paramMap.low_amount'] = '${request.low_amount}';//使用红包时候最低投标金额
	     param['paramMap.isUseHb_6_24'] = '${request.isUseHb_6_24}';
	     
	    
	     //if('${request.isUseHb}'==1){
	    	  //alert('已经选择使用红包${request.isUseHb}');
	    	 // alert('可用红包金额${request.bonus_avaliable}');
	    	  //alert('红包类型    ${request.bonus_type}');
	 	     // alert('使用红包最低投资金额：${request.low_amount}');
	     //}
	   
	     if(flag){
           flag = false;
	       $.shovePost('financeInvest.do',param,function(data){
		      var callBack = data.msg;
		      if(callBack == undefined || callBack == ''){
		         $('#content').html(data);
		         flag = true;
		      }else{
		         if(callBack == 1){
		          alert("投标成功!");
		           //parent.location.href=parent.location.href;
		           parent.location.href= 'financeDetail.do?id='+id;
		          flag = false;
		          return false;
		         }
		         alert(callBack);
		         flag = true;
		      }
		    });
		 }
	 });
	
});		

//自动填入
function getAllUserAbleSum(){
	var a = '${userMap.usableSum}';
    $('#amount').val(Math.floor(a));
}

//充值
$('#recharge').click(function(){
	 parent.location.href ='rechargeInit.do';
});	

</script>

</body>
</html>