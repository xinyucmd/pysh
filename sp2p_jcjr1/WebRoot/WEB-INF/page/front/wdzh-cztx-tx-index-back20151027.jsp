<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${sitemap.siteName}</title>
   <jsp:include page="/include/head.jsp"></jsp:include>
   <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
   <jsp:include page="/include/common.jsp"></jsp:include> 
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="ne_wdzh"></div>
<div class="lne_cent">

	<jsp:include page="/include/left.jsp"></jsp:include>

	<div class="lne_centr s_centr">
    	 <!--标题-->
      	 <div class="myhome_tit tab_meun">
         	<ul>
                <li class="on">提 现</li>
            </ul>
         </div>
         <!--内容-->
         <div class="myhome_content tab_content clearfix">
              <!--提现-->
              <div>
                    <div class="myhome_tx clearfix">
                    	<ul>
                        	<li><span>真实姓名：</span><strong><s:property value="#request.realName" default="---" ></s:property></strong></li>
                            <li><span>电话号码：</span><strong> <s:property value="#request.bindingPhone" default="---" ></s:property> </strong></li>
                            <li><span>账户余额：</span><strong><s:property value="#request.handleSum" default="---" ></s:property>元 </strong></li>
                            <li>
                            <span>可用余额：</span>
                            <strong>
                               <s:property value="#request.usableSum" default="---" ></s:property>元
                                <s:if test="#request.isLimteTyj==0">
                                      -(其中不可提现金额：${getLx}元)
                                </s:if>
                            </strong>
                            </li>
                            <li><span>冻结总额：</span><strong><s:property value="#request.freezeSum" default="---" ></s:property>元 </strong></li>
                            <li><span>提现银行：</span>以下是绑定的银行卡信息，如果没有银行卡请先进行提现银行卡设置</li>
                            
                    		<s:if test="#request.banks!=null && #request.banks.size>0">
                    		    <s:if test="#request.temp==0">
                    		       <s:iterator value="#request.banks"  var="bean" status="sta">
								        <s:if test="#bean.cardStatus==1">
									      <li><span>&nbsp;</span><input type="radio" class="check" name="wbank" value="${ bean.id}" onclick="setUniqueMark(${ bean.id})"/>${bean.bankName}&nbsp;&nbsp;${bean.branchBankName}</li>
									    </s:if>
								    </s:iterator>
                    		    </s:if>
                    		    
                    		    <s:if test="#request.temp==1">
                    		       <s:iterator value="#request.banks"  var="bean" status="sta">
								        <s:if test="#bean.cardStatus==1">
									          <s:if test="#bean.unique_mark==1">
										        <li><span>&nbsp;</span><input type="radio" checked="checked" class="check" name="wbank" value="${ bean.id}"/>${bean.bankName}&nbsp;&nbsp;${bean.branchBankName}</li>
										      </s:if>
										      <s:if test="#bean.unique_mark==0">
										        <li><span>&nbsp;</span><input type="radio" disabled="disabled" class="check" name="wbank" value="${ bean.id}"/>${bean.bankName}&nbsp;&nbsp;${bean.branchBankName}</li>
										      </s:if>
									    </s:if>
								    </s:iterator>
                    		    </s:if>
                    		    
							</s:if>
							<s:else>
							    <li><span>&nbsp;</span>暂未设置提现银行，请先进行<a href="yhbound.do" style="text-decoration:underline; color:#06C;">设置</a></li>
							</s:else>
                            <li>
                            	<span>交易密码：</span>
                            	<input type="password" class="inp140 test" id="dealpwd" />
                            	<span class="txt" style="color: red; width:200px;text-align: left" >输入您平台设置的交易密码</span>
    							<em style="color: red; float: none;" id="pwd_tip" class="formtips"></em>
    						</li>
                            <li>
                            	<span>提现金额：</span>
                            	<input type="text" class="inp140 test" id="dealMoney" />元
    							<em style="color: red; float: none;" id="money_tip" class="formtips"></em>
                            </li>
                            
                            <li>
                            	<span>图片验证码：</span>
                            	 <input id="ipt_mCode" class="inp100x test1" type="text"/>
                            	 <img src="${sitemap.adminUrl}/imageCode.do?pageId=tx" title="点击更换验证码"
											style="cursor: pointer;" id="codeNum" width="60" height="25" 
											onclick="javascript:switchCode()" /> 
                            </li>
                            
                            <li>
                            	<span>验证码：</span>
                            	<input type="text" class="inp100x test1" id="code"/>
                            	<span id="clickCodeSpan">
                            	   <a id="clickCode"   class="yzmbtn longbtn" href="javascript:void(0);">发送手机验证码</a>
                            	</span>
                            	<span id="sendingCodeSpan" style="display: none">
                            	   <a id="sendingCode" class="yzmbtn longbtn" href="javascript:void(0);">正在发送...</a>
                            	</span>
                            	
                            	<input id="type" name="type" value="drawcash" type="hidden"/>
                            	<em style="color: red; float: none; margin-left:15px;" id="code_tip" class="formtips">
                            	<s:if test="#request.ISDEMO==1">* 演示站点不发送短信</s:if>
      							</em>
                            </li>
                            
                       		<li>
                       			<span>&nbsp;</span>
                       			<input type="button" id="btn_submit" value="确定提交" class="myhome_bnt1" />
                       		</li>
                        	<li style="color:red;"><span>&nbsp;</span>* 温馨提示：禁止信用卡套现</li>
                        </ul>
                    </div>
                   
                    
                    <span id="withdraw"></span>
                    
                    <!--分页器 结束--> 
					<p class="tips">
						温馨提示：<br />
						1、在您申请提现前，请先在个人账户信息页面绑定银行卡，收到您的提现请求后，微信贷将在1至2个工作日（双休日或法定节假日顺延）处理您的提现申请。 <br />
						2、为保障您的账户资金安全，申请提现时，您选择的银行卡开户名必须与您微信贷账户实名认证一致，否则提现申请将不予受理。 <br />
						3、提现时，您不能选择将资金提现至信用卡账户，请您选择银行储蓄卡账户提交提现申请。 <br />
						4、严禁利用微信贷进行套现、洗钱、匿名转账，对于频繁的非正常投资为目的的资金充提行为，一经发现，微信贷将通过原充值渠道进行资金清退，已收取手续费将不予返还。 <br />
						5、每笔金额的提现变动，微信贷平台会给您发送站内信进行提示。<br />
						6、每笔提现收取2元/笔的手续费，单笔提现金额最高不能超过1000000元。<br />
                    </p>
              </div>
             	
         </div>
    </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script>
	var flags = false;
  	//手机号码绑定
  	var timers=60;
  	var tipId;
  	
  	function switchCode(){
  		var timenow = new Date();
  		$("#codeNum").attr("src","/shoveeims/imageCode.do?pageId=tx&date"+timenow);
  	}
  	
		 
		   $(function(){
			   $(".lne_l3 > ul >li").removeClass("clicked");
				 $('#li_2').addClass('clicked');
		       $("#clickCode").click(function(){
		    	   var imgCode = $("#ipt_mCode").val();
		    	   if(imgCode == ""){
		    		   alert("请输入图片验证码");
		    		   return null;
		    	   }
		    	   
		    	   if($("input:radio[name='wbank'][checked]").val()==undefined){
	    	          alert("请设置或者选择提现银行卡信息");
	    	          return;
	    	       }else if($("#dealpwd").val()==""){
	    	           alert("请填写交易密码");
	    	           return;
	    	       }else if($("#dealMoney").val()=="" ){
	    	           alert("请填写提现金额");
	    	           return;
	    	       }
	    	       if($("#money_tip").text() != ""){
	    	           alert("请填写正确的金额");
	    	           $("#dealMoney").attr("value","")
	    	           return;
	    	       }
		    	   else{
		    		   $.post("imgCodeCheck.do","imgCode="+imgCode+"&pageId=tx",function(data){
							if(data.ret==1){
								getMCode();
							}else{
								alert(data.result);	
							}
						});
		    	   }
                   
		       });
		   });
		   
		   function getMCode(){
			   var phone=${request.bindingPhone};
               if($("#clickCode").html()=="重新发送验证码"||$("#clickCode").html()=="发送手机验证码") { 
              	 $('#clickCodeSpan').hide();
		    	     $('#sendingCodeSpan').show();
               }else{
              	 return;
               }
                  $.post("phoneCheck.do","phone="+phone,function(datas){

                      if(datas.ret != 1){
                          alert(datas.msg);
                          $('#clickCodeSpan').show();
     		    	        $('#sendingCodeSpan').hide();
                        return;
                      }
                  	  phone = datas.phone;

                      var test={};
                      test["paramMap.phone"] = phone;
                      $.post("sendSMS.do",test,function(data){
                      if(data == "virtual"){
                      	$('#clickCodeSpan').show();
     		    	        $('#sendingCodeSpan').hide();
                          		window.location.href = "noPermission.do";
                              	return ;
                        }

                        if(data==1){
                            timers=180;
                            tipId=window.setInterval(timer,1000);
                        }else{
                          	$('#clickCodeSpan').show();
		       		    	$('#sendingCodeSpan').hide();
                            alert("手机验证码发送失败");
                        }
                      });

                  });
			   
		   }
		   
		   //定时
		   function timer(){
		    
		       if(timers>=0){
		         $("#clickCode").html("验证码获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           $("#clickCode").html("重新发送验证码");
		           $.post("removeCode.do","",function(data){});
		           
		       }
		       $('#clickCodeSpan').show();
		       $('#sendingCodeSpan').hide();
		   }
		</script>

<script type="text/javascript">
var flag = false;
  $(function(){
        $("#zh_hover").attr('class','nav_first');
	      $('#li_2').addClass('on');
		  $('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
       }); 
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
  
   function initListInfo(praData){
		$.shovePost("queryWithdrawList.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#withdraw").html(data);
	}
	
	function jumpUrl(obj){
       window.location.href=obj;
    }
	 
	$("#dealpwd").blur(function(){
	     if($("#dealpwd").val()==""){
	       $("#pwd_tip").html("交易密码不能为空");
	     }else{
	       $("#pwd_tip").html("");
	     }
    });
    
	$("#dealMoney").blur(function(){
	     if($("#dealMoney").val()=="" ){
	       $("#money_tip").html("提现金额不能为空");
	     }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#dealMoney").val())){
	       $("#money_tip").html("请输入正确的提现金额，必须为大于0的数字"); 
	     }else if($("#dealMoney").val()< 3 ){
	       $("#money_tip").html("提现金额不得低于3元"); 
	     }else if( parseFloat($("#dealMoney").val()) - parseFloat('${usableSum}')>0){
	       $("#money_tip").html("提现金额不能大于可用余额"); 
	     }else{
	       $("#money_tip").html(""); 
	     }
   });
    
    $("#code").blur(function(){
	     if($("#code").val()==""){
	       $("#code_tip").html("");
	     }else{
	       $("#code_tip").html("");
	    }
    });
    
    $("#btn_submit").click(function(){   
		   addWithdraw();
		});
		
    function addWithdraw(){
        if($("input:radio[name='wbank'][checked]").val()==undefined){
          alert("请设置或者选择提现银行卡信息");
             return;
        }else if($("#dealpwd").val()==""){
           alert("请填写交易密码");
             return;
         }else if($("#dealMoney").val()=="" ){
            alert("请填写提现金额");
             return;
         }
         if($("#money_tip").text() != ""){
           alert("请填写正确的金额");
           $("#dealMoney").attr("value","")
             return;
         }
        
        if(!window.confirm("确定申请提现")){
          return;
        }else{
         $("#btn_submit").attr("disabled",true);
        }
        
         param["paramMap.dealpwd"] = $("#dealpwd").val();
         param["paramMap.money"] = $("#dealMoney").val();
         param["paramMap.cellPhone"] = '${bindingPhone}';
         param["paramMap.code"] = $("#code").val();
         param["paramMap.bankId"] = $("input:radio[name='wbank'][checked]").val();
         param["paramMap.type"] = $("#type").val();
         if(flag)
            return;
       	 $.shovePost("addWithdraw.do",param,function(data){
    	   flag = false;
	       if(data.msg == 1){
              flag  = true;
              window.clearInterval(tipId);
              $("#clickCode").html("发送手机验证码");
              alert("申请提现成功");
              jumpUrl('withdrawLoad.do');
          }else{
           $("#btn_submit").attr("disabled",false);
          	  alert(data.msg);
          	jumpUrl('withdrawLoad.do');
          }
       });
    }
    
    $("#send_phoneCode").click(function(){
		var param = {"type":"drawcash"};
		$.shovePost("sendPhoneCode.do",param,function(data){
			if(data==1){
				alert("发送成功");
			}
			alert("验证码：" + data);
		});
	});
    
    //设置使用一张银行卡
    function setUniqueMark(bank_id){
    	  
    	 if(confirm("考虑您的账户资金安全，请设置一张银行卡作为您的长期提现的卡号，谢谢"))
    	 {
    		  var param = {};
     		  param["paramMap.bank_id"] = bank_id;
     		  $.shovePost("setUniqueBankCardMark.do",param,function(data){
     			if(data.msg==1){
     				 location.href="withdrawLoad.do";
     			}
     			if(data.msg==0){
    				 alert('操作失败');
    				 location.href="withdrawLoad.do";
    			}
     		});
    	 }else{
    		 location.href="withdrawLoad.do";
    	 }
    		
    	 
    }
</script>

</body>
</html>