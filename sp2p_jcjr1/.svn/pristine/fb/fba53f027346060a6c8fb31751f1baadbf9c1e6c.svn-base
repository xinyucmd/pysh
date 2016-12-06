<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="css/css.css" />
</head>
<body>
<input type="hidden" id="payId" value="${payMap.id}"/>
<div class="nymain" style="width:400px;margin-top:-10px;">
  <div class="wdzh" style="border:none;" >
    <div class="r_main" style="border:none;" >
      <div class="box" style="border:none;" >
        <div class="boxmain2" style="border:none;" >
          <div class="biaoge2" style="border:none;" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="16%">流转标标题： </td>
    <td width="84%" style="margin-left: 0px;"><strong>${borrowDetailMap.borrowTitle } </strong></td>
  </tr>
  <tr>
    <td width="16%">投资限额： </td>
    <td width="84%" style="margin-left: 0px;"><strong>${maxMoneyValue}元 </strong></td>
  </tr>
  <tr>
    <td width="16%">单次限额： </td>
    <td width="84%" style="margin-left: 0px;"><strong>${maxTenderedSum}元 </strong></td>
  </tr>
  <tr>
    <td>最小流转单位:</td>
    <td><strong>${borrowDetailMap.smallestFlowUnit }元 </strong></td>
  </tr>
  <tr>
    <td>年利率： </td>
    <td><strong> ${borrowDetailMap.annualRate } % </strong></td>
  </tr>
  <tr>
    <td>还剩份数：</td>
    <td><strong>${borrowDetailMap.remainCirculationNumber } 份</strong></td>
  </tr>
  <tr>
    <td>购买份数：</td>
    <td><a href="javascript:void(0);" onclick="cutimg()"><img src="images/cut.png" title="减1" /></a>
    <strong><input type="text" class="inp40" value="1" id="result" maxlength="20"/>
    <a href="javascript:void(0);" onclick="addimg(${borrowDetailMap.remainCirculationNumber})"><img src="images/add.png" title="加1" /></a>&nbsp;份</strong></td>
  </tr>
	<tr>
    <td colspan="2">
      <strong>已投金额：${hasInvestAmount}元，
                                     可投金额：${ borrowDetailMap.investAmount_t}元，
                                     最多可认购：${borrowDetailMap.remainCirculationNumber_t} 份
      </strong>
      <br />
      <input type="hidden" id = "remainCirculationNumber_t" value="${borrowDetailMap.remainCirculationNumber_t}"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="left" style="padding-left: 100px;"><strong><span id="error_id"></span></strong></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td style="padding-top:20px;"><a style="cursor:pointer;" id="invest" class="bcbtn">确认购买</a></td>
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
<script>
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=subscode&d='+timenow);
};
function addimg(maxNum){
	//判断只能为数字
	if(isNaN($("#result").val()))
       	{
        	$("#error_id").html(" * 只能输入数字");
       		return  false;
       	}
       	if($("#result").val().indexOf(".")!= -1)//限制小数的输入
       	{
       	 $("#error_id").html(" * 只能输入数字");
       		return false;
       	}
       	if($("#result").val().substring(0, 1)==0)//输入的第一个数字不能为0
       	{
       	 $("#error_id").html(" * 只能输入数字,且不能以0开头");
       		return  false;
       	}
       	var number = $('#result').val()-0;
    	if ( number < maxNum){
           	$("#result").val(number+1);
        }   	
}
function cutimg(){
	//判断只能为数字
	if(isNaN($("#result").val()))
       	{
        	$("#error_id").html(" * 只能输入数字");
       		return  false;
       	}
       	if($("#result").val().indexOf(".")!= -1)//限制小数的输入
       	{
       	 $("#error_id").html(" * 只能输入数字");
       		return false;
       	}
       	if($("#result").val().substring(0, 1)==0)//输入的第一个数字不能为0
       	{
       	 $("#error_id").html(" * 只能输入数字,且不能以0开头");
       		return  false;
       	}
       	var number = $('#result').val()-0;
    	if ( number > 1){
           	$("#result").val(number-1);
        }   	
}
</script>
<script>
 $('#invest').click(function(){
	      var username = '${borrowUserMap.username}';
	      var uname = '${user.userName}';
	      if(username == uname){
	         alert("您不能认购自己发布的借款");
	         return false;
	      }
	      
	     var lineDownLicai = '${session.lineDownLicai}';
	     
	     if(lineDownLicai==0){
	     if($('#remainCirculationNumber_t').val()==0){
	    	 alert("您的认购份额已达到上限");
	    	 return false;
	     }
	      
	     if($('#result').val() - $('#remainCirculationNumber_t').val()>0){
	    	 alert("您最多可认购"+$('#remainCirculationNumber_t').val()+"份");
	         return false;
	     }
	     
	     var z = $('#result').val();
	     var n = '${n}'; 
	     if(z-n>0){
	    	 alert("您单次认购份额最多"+n+"份"); 
	    	 return;
	     }
	     }
	     param['paramMap.id'] = '${borrowDetailMap.id}';
	     param['paramMap.result'] = $('#result').val();
	     param['paramMap.amountUnit'] = '${borrowDetailMap.smallestFlowUnit}';
	     param['paramMap.isUseHb_6_24'] = '${isUseHb_6_24}';
	     $('#invest').text('受理中...');
	     $.shovePost('subscribe.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack !=undefined){
		     if(callBack == 1){
		       alert('认购成功');
		       $('#invest').text('确认购买');
		       parent.location.href = parent.location.href;
		       return;
		     }
		     alert(callBack);
		     parent.location.href = parent.location.href;
		   }else{
			   parent.location.href = parent.location.href;
			  //window.location.reload();
		   }
		 });
	 });
</script>
</body>
</html>
