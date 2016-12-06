<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" /> 
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


<div class="ne_wdzh"></div>
<div class="wrap" style="padding-top:10px;">
<div class="lne_cent">

	 <%@include file="/include/left.jsp" %>
	 
	<div class="lne_centr s_centr">
    	 <!--标题-->
         <!--内容-->
         <div class="myhome_content tab_content clearfix" style="padding:0;">
             	<div class="box" style="background:#fff; padding:20px 0;" >
      <div class="boxmain2" >
      
        <div class="biaoge2">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr style="line-height:36px;">
    <td style="text-align:right;" >真实姓名：</td>
    <td width="70%"><strong>${realName }</strong></td>
  </tr>
  <tr style="line-height:36px;">
    <td style="text-align:right;">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
    <td><strong>${email }</strong></td>
  </tr>
  <tr style="line-height:36px;">
    <td style="text-align:right;">充值金额：</td>
    <td><input id="money" type="text" class="form_s" maxlength="15"/>元
    <span style="color: red; float: none;" id="money_tip" class="formtips"></span>
    </td>
  </tr>
  <tr style="line-height:36px;">
   	   <td style="text-align:right;">充值类型：</td>
       <td style="">
       <s:if test="#request.gopay==2">
          <input id="gopay" type="radio" name="pay" value="2" style="float: none;">线上充值</input>
       </s:if>
       <s:if test="#request.IPS==2">
       <input id="ipay" type="radio" name="pay"  value="1" style="float: none;" />
       <img src="images/ipay.png" width="121px" height="33px"/>
        </s:if>
       
       <s:if test="#session.lineDownLicai==1">
          <input type="radio"  name="pay"  id="xxpay" value="3" style="float: none;">线上充值</input>
            
       </s:if>
       </td>
   </tr>  
   
     
   <tr style="line-height:36px;">
    <td style="text-align:right;">充值方式：</td>
     
     <td>
         <input id="reacherState" value="1" type="hidden"/>
	     <input  type="radio" id="payMethod1"  name="payMethod"   value="1"  checked="checked"/>
	           连连支付 
   
	     <input  type="radio" id="payMethod2"  name="payMethod"   value="2" />
	           国付宝支付 
     </td>
    </tr>
   
   
   
   
   
   <tr>
  	<td colspan="2">
  		  <div class="border_" id="div_gopay" style="display:none; ">
  <input  type="hidden" id="bankType"  name="bankType"   value="DEFAULT" checked="checked"  />
</div>
 <div class="border_" id="div_ipay"  style="display:none; " >
  <table border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
          <td colspan="5" ><strong>请选择支付方式：</strong></td>
    </tr>
        <tr valign="middle">
          <td><input  type="radio"  name="bankType" value="01" id="rd_ipay_jieji" />
            人民币借记卡</td>
          <td><input type="radio"  name="bankType"  value="128" />
            信用卡支付</td>
          <td><input type="radio"   name="bankType"  value="04" />
            IPS账户支付</td>
          <td><input type="radio"  name="bankType"  value="16" />
           电话支付</td>
          <td><input type="radio"  name="bankType" value="32" />
            手机支付</td>
        </tr>
        <tr>
          <td colspan="5"><input type="radio"  name="bankType"  value="1024" />
            手机语音支付</td>
         
        </tr>
       <tr>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
        </tr>
       
        <tr>
         
          <td></td>
          <td></td>
          <td></td>
        </tr>
	  </table>
	</div>
	  	</td>
	  </tr>
	  <tr>
	  	  <td colspan="2" style="padding-top:13px;"> 
	    <div id="xxdiv"  style="display:none;">
	    	<table  style="line-height:30px;">
	    		<tr align="center" height="30px">
	    			<td >&nbsp;</td>
	    			<td width="70px">银行</td>
	    			<td width="200px">账号</td>
	    			<td width="200px">开户人</td>
	    			<td width="200px">开户行</td>
	    		</tr>
	    	<s:iterator value="#request.banksList" var="bank" status="count">
	    		<tr align="center">
	    			<td style=" margin-left:10px; margin-top:10px;"><input type="radio"  value="${bank.bankname }" name="paramMap.banks" style="float:none;"
	    			<s:if test="#count.count == 1">checked="checked"</s:if> /></td>
	    			<td><image src="${bank.bankimage }" style="height:35px; margin-left:8px; margin-top:10px;"/></td>
	    			<td style="margin:0 10px; padding:10px 0; width:0;">${bank.Account }</td>
	    			<td style="margin:0 15px; padding:10px 0; width:0;">${bank.accountname }</td>
	    			<td style="margin:0 10px; padding:10px 0; width:0;">${bank.accountbank }</td>
	    		</tr>
	    	</s:iterator>
				         <tr style="background:#fff;">
					    <td align="right" style="width:130px; ">
					  交易流水号：
					    </td>
					    <td align="center">
				 <input type="text" class="inp140" style="margin-top: 10px; width: 180px;float:none; position:relative;top:-5px;" id="rechargeNumber_text"/></td>
					    <td colspan="3" style="margin-top:12px;  position:relative; left:10px;">填写网上交易流水号或ATM机汇款交易号或其他号码，只供查询用 
					    </td>
				  		</tr>
				         <tr style="background:#fff;">
					    <td align="right" style="width:130px;">
					  线下充值备注：
					    </td>
					    <td align="center">
					    <input type="text" class="inp140" style="margin-top: 10px;width: 180px;float:none;"  id="remark_text"/></td>
					    <td colspan="3" style="position:relative; left:10px;">请注明您的用户名，转账银行卡和转账流水号，以及转账时间 
					    </td>
				  </tr>
				  <tr style="background:#fff;">
				  <td colspan="5" align="center">
				  <a id="saveii" class="btn btn-warning btn-lg">提交充值</a>
				  </td>
				  </tr>
	    	</table>
        </div>			
				</td>	
	  </tr>
	</table>

        </div>
        <div id="btn_submit">
        <input type="button" class="btn btn-warning btn-lg"" value="保存并继续" id="addrecharge" style="margin-top:0; margin-left:230px;"/></div><%--
        <p id="p_about" class="tips2" style="border:none;">
        </p>
        --%><!-- <div class="biaoge" >
           <span id="rechargeInfo"></span>
          </div> -->
      
      
      </div>
	</div>  
        <div class="personal-rule">
            <p class="blue-color">温馨提示</p>
            <ul>
                <li>1. 为了您的账户安全，请在充值前进行身份认证、手机绑定以及交易密码设置。</li>
                <li>2. 请注意您的银行卡充值限制，以免造成不便。</li>
                <li>3. 禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。</li>
                <li>4. 充值过程中遇到问题请及时联系客服，400-001-6007 （9:00-21:00）</li>
            </ul>
    	</div>           	
         </div>
    </div>
</div>
</div>
<div class="theme-popover-mask" id="theme-popover-mask-id">
        <div class="tc_box">
            <div class="tc_box_top">确定进行账户充值,金额为 <em id="money_lbl"></em> 元</div>
            <div class="tc_box_bottom">
                <input name="" type="button" value="确定" onclick="toRecharge()"/>
                <input name="" type="button"  value="取消" onclick="calcelRecharge();" />
            </div>
    	</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script>
 	$("#saveii").click(function(){
		 jQuery.jBox.open("post:queryRechartips.do", "充值委托书", 600,400,{ buttons: { } });
	});
</script>
<script>
		//添加线下充值
		function addRechargeoutline(name){
		   var param ={};
		   param["paramMap.realname"] = name;
		   param["paramMap.bankName"] = $("input[name='paramMap.banks']:checked").val();
		   param["paramMap.money"] = $("#money").val();
		   param["paramMap.rechargeNumber"] = $("#rechargeNumber_text").val();
		   param["paramMap.remark"] = $("#remark_text").val();
		   $.post("addRechargeoutline.do",param,function(data){
			   if(data.msg=='提交成功'){
				 alert(data.msg);
			     window.location.href="rechargeInit.do";
			   }else{
			     alert(data.msg);
			     $("#agreebutton").attr("disabled",false);
			     $('#realname').val("");
			   }
		   });
		}
</script>
<script>
  $(function(){
         //$("#zh_hover").attr('class','nav_first');
	     // $('#li_2').addClass('on');
		  //$('.tabmain').find('li').click(function(){
		 // $('.tabmain').find('li').removeClass('on');
		 $(".lne_l3 > ul >li").removeClass("clicked");
		 $('#li_1').addClass('clicked');
        
       //}); 
       /**
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	   */
	    $("#gopay").click(function(){
	    	$("#div_gopay").attr("style","display:block;");
	    	$("#btn_submit").attr("style","display:block;");
	    	 $("#xxdiv").attr("style","display:none;");
			$("#div_ipay").attr("style","display:none;");
			$("#rd_gopay_icbc").attr("checked","checked");
	    	$("#p_about").html("国付宝简介:国付宝信息科技有限公司（以下简称“国付宝”）是商务部中国国际电子商务中心（以下简称“CIECC”）与海航商业控股有限公司（以下简称“海航商业”）合资成立，"+
	    	"针对政府及企业的需求和电子商务的发展，精心打造的国有背景的，引入社会诚信体系的独立第三方电子支付平台，也是“金关工程”的重要组成部分。"+
			"国付宝信息科技有限公司成立于2011年1月25日，由商务部中国国际电子商务中心与海航商业控股有限公司合作成立，主要经营第三方支付业务。"+
			"公司注册资本14285.72万元，主要经营第三方支付业务，互联网支付及移动电话支付（全国）。");
	    });
	    $("#ipay").click(function(){
	    	$("#div_gopay").attr("style","display:none;");
	   	 	$("#div_ipay").attr("style","display:block;");
	   	 	$("#btn_submit").attr("style","display:block;");
	    	 $("#xxdiv").attr("style","display:none;");
			$("#rd_ipay_jieji").attr("checked","checked");
	    	$("#p_about").html(" 环迅支付简介:迅付信息科技有限公司（简称：环迅支付），是中国最早成立的第三方支付企业。公司在2011年获颁中国人民银行首批《支付业务许可证》。"+
	    	"公司平台支持所有国内主流银行及国际信用卡，为全球60万家商户及2000万用户提供金融级的支付体验。");
	    });
	    $("#money").keydown(function (e) { /*只允许输入数字*/
	        if (e.shiftKey)
	            return false;

	        var key = [8, 37, 39, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 110, 190];
	        if ($.inArray(e.which, key) < 0) {
	            return false;
	        }
	    });


	    //线下充值
	    $("#xxpay").click(function(){
	    	$("#rechargeDetail").html("以下是通过线下进行充值。");
	        $("#div_gopay").attr("style","display:none;");
	   	 	$("#div_ipay").attr("style","display:none;");
	        $("#btn_submit").attr("style","display:none;");
	        $("#xxdiv").attr("style","display:block;");
	        $("#xxdiv").attr("class","biaoge");   
	        
	        $("#p_about").attr("style"," border:none; display:none;");
	        $("#p_about").html(" 线下充值的金额最小10000，奖励千分之三，小于10000没有奖励。");
	        
	    });
	    $("#gopay").click();
	 });
	 
	 function initListInfo(praData){
	 	
		$.shovePost("queryRechargeInfo.do",praData,initCallBack);
	}
	function initCallBack(data){
		/* $("#rechargeInfo").html(data); */
	}
   
   	$("#addrecharge").click(function(){   
		   addRechargeInfo();
		});
   	
   	var payType = -1;
   	var bankType = "";
   function addRechargeInfo(){
	 payType = -1;
	 bankType = "";
      if($("#money").val() == ""){
        $("#money_tip").html("请输入充值金额");
        return;
      }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#money").val())){
	       $("#money_tip").html("请输入正确的提现金额，必须为大于0的数字"); 
	       return;
	  }else if($("#money").val() < 0.01){
	      $("#money_tip").html("最小金额不能低于0.01"); 
	       return;
	  }else{
	     $("#money_tip").html("");
	  }
      
      if($("#gopay").attr("checked")=="checked"){
		    payType= 1;
      }	else if($("#ipay").attr("checked")=="checked"){
     	 	payType = 2;
      }else{
      	  alert("请选择充值类型");
      	  return ;
      }
      
	  
	   bankType=$("#bankType").val();
	   
      if(bankType=="" || bankType==undefined){
    	  alert("请选择银行类型");
    	  return;
      }   
	  
	 // if(!window.confirm("确定进行帐户充值")){
     //   return;
     // }else{
	 // 	$("#addrecharge").attr("disabled",true);
	 // }
     // toRecharge();
     
     showDilog();
   }
   
   function toRecharge(){
	 
	 $("#addrecharge").attr("disabled",true);
	 var money = $("#money").val();
     var type = "";
      /* if($("#ipay").attr("checked")=="checked"){
      	window.open("ipayPayment.do?divType=li_2&money="+money+"&gatewayType="+bankType);
      }else if($("#gopay").attr("checked")=="checked"){
      	window.open("gopayPayment.do?divType=li_2&money="+money+"&bankCode="+bankType);
      }else{
      	alert("请选择充值类型");
      }
      
      $("#addrecharge").attr("disabled",false);
      calcelRecharge(); */
     
     
     
    	 if($("#payMethod2").attr("checked")=="checked"){//国付宝支付
   	      if($("#ipay").attr("checked")=="checked"){
   	      	window.open("ipayPayment.do?divType=li_2&money="+money+"&gatewayType="+bankType);
   	      }else if($("#gopay").attr("checked")=="checked"){
   	      	window.open("gopayPayment.do?divType=li_2&money="+money+"&bankCode="+bankType);
   	      }else{
   	      	alert("请选择充值类型");
   	      }
   	      
   	      $("#addrecharge").attr("disabled",false);
   	      calcelRecharge();
       }
       if($("#payMethod1").attr("checked")=="checked"){//连连支付
   	   var plain = 'plain';
   	 
   	   window.open("lianPayment.do?paymod="+plain+"&money_order="+money);
   	   
   	   $("#addrecharge").attr("disabled",false);
   	   calcelRecharge();
       }
    	 
     }
   function showDilog(){
	   $("#money_lbl").html($("#money").val());
	   $("#theme-popover-mask-id").show();
   }
   
   function calcelRecharge(){
	   $("#theme-popover-mask-id").hide(); 
   }
   
   
   function jumpUrl(obj){
       window.location.href=obj;
    }
</script>
</body>
</html>
