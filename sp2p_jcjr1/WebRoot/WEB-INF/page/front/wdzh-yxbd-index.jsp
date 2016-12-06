<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <meta http-equiv="keywords" content="个人投资理财|互联网理财|P2P理财产品|P2P收益|p2p网贷理财" />
    <meta http-equiv="description" content="投资理财找微信贷，微信贷提供高收益P2P理财产品，为个人和企业提供透明、安全、高效的互联网金融服务。" />
</head>
<body> 
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="wrap" style="padding-top:10px;">   
<div class="ne_wdzh"></div>
<div class="lne_cent">
           <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
		<div class="lne_centr s_centr">
        
 <div class="myhome_content tab_content clearfix">
 	 <div class="mmxg s_changepw clearfix">
                   <ul class="clearfix">
                       <li><span>邮箱设置：</span>
                       		<s:if test="paramMap.flag==2">
						   		<span style="width:auto; padding-right:10px;">${paramMap.email }</span>
						   		<input type="hidden" name="paramMap.email" class="inp188" id="mails" style="float:left;" value="${paramMap.email }"/>
						   		<input type="hidden" name="paramMap.flag" id="mailSettingFlag" value="${paramMap.flag}"/>
						      	<span style="width:60px;"><input type="button" class="chaxun" value="重新激活" id="saveEmail" /></span>
						     </s:if>
						     <s:elseif test="paramMap.flag==3">
						   		<input type="hidden" name="paramMap.flag" id="mailSettingFlag" value="${paramMap.flag}"/>
								<input type="text" name="paramMap.email" class="inp188" id="mails" style="float:left;" value="${paramMap.email }"/>
						      	<span style="width:60px;"><input type="button" class="chaxun" value="确认激活" id="saveEmail" /></span>
						     </s:elseif>
						     <s:else>
						     	<input type="text" name="paramMap.email" class="inp188" id="mails" style="float:left;" value="${paramMap.email }"/>
						      	<span style="width:60px; margin-right:10px;"><input type="button" class="chaxun" value="确认激活" id="saveEmail" /></span><span style="width:110px;">
						      	<strong class="notice">(你还没绑定邮箱)</strong></span>
								<span style="color: red; width:10px;">*<s:fielderror fieldName="paramMap.email" /></span>						
							</s:else>	
							</li>
                       <li><div id="ok" style="color: red; height: 20px; margin-left:50px;" >${paramMap.msg }</div></li>
                   </ul>
               </div> 
         </div>
   
    </div>
    </div>
</div>
 <!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>	
 <script>
$(function(){
    $("#zh_hover").attr('class','nav_first');
	$("#zh_hover div").removeClass('none');
	$('#li_16').addClass('on');
	$('#yq_address_btn').css('cursor','pointer');
    $('#yq_address_btn').click(function(){
        if($.browser.msie){
           var txt = '复制文本到剪贴板:\n\n';
           txt = txt+$('#yq_address_input').html();
           window.clipboardData.setData('text', $('#yq_address_input').html());
           alert(txt);
        }
    });
    $("#attention").click(function(){
           var param = {};
          param["paramMap.id"] =${user.id}
          param["paramMap.attention"] ="attention";
          $.shovePost("userFrends.do",param,function(data){
           $("#userfrends").html(data);
         });
    });
    
	$('.tabtil').find('li').click(function(){
	    $('.tabtil').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.tabtil').nextAll('div').hide();
         $('.tabtil').nextAll('div').eq($(this).index()).show();
	});
	init();
});
function init(){
  if(!$.browser.msie){
      $('#yq_address_btn').zclip({
         path:'script/ZeroClipboard.swf',
         copy:function(){return $('#yq_address_input').html();}
      });
  }
}
</script>
<script>
 $(function(){
 
 
 
 //提交邮箱添加
	$("#saveEmail").click(function(){
				   var param = {};
			       param["paramMap.email"] = $("#mails").val();
			       
			       // 修改邮箱标志
			       var msf = $("#mailSettingFlag").val();
			       var url = "SendUserEmailSet.do";
			       if(msf==2 || msf == 3){
			    	 url = "updattingUserEmail.do";
			    	 param["paramMap.flag"] = msf;
			       }
			       
			       if(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#mails").val())){
						        $.post(url,param,function(data){
						        if(data.mailAddress=='0'){
						             alert("邮箱不能为空");
						        }else if(data.mailAddress=='1'){
						          $("#ok").html("该邮箱不存在");
						        }else if (data.mailAddress=='4'){
						        	 $("#ok").html("该邮箱已被绑定,请重新输入");
							    }else{
						          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+"  target='_blank'><font color='red'> 登录</font></a>到你的邮箱验证");
						        }
						       });
			       }else{
			         alert("邮箱输入有误");
			       }
			       
       
       
       
       		/*
	        var param = {};
	        param["paramMap.email"] = $("#mails").val();
	        $.post("addEmails.do",param,function(data){
			        if(data.msg=="添加成功"){
			         alert("添加成功");
			         window.location.reload();
			        }else{
			          alert(data.msg);
			        }
	        });
			addEmails
			*/
       
			
			
		});
 
 
 
 
 });
	
	</script>
	<script>
  $(function(){
      var flag = '${paramMap.msg}';
      if(flag=='邮箱绑定成功'){
       alert(flag);
       window.location.href='emailManagerInit.do';
      }       
  });
	</script>
</body>
</html>

