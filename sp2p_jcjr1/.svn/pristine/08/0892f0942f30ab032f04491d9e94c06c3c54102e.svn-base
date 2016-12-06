<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@include file="/include/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${sitemap.siteName}</title>
	<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
  </head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="ne_wdzh"></div>
<div class="lne_cent">
           <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
		<div class="lne_centr s_centr">
				<div class="myhome_tit tab_meun">
		         	<ul>
      						<%-- <li  onclick="jumpUrl('owerInformationInit.do');">
								个人详细信息
							</li>
							<li onclick="loadWorkInit('queryWorkInit.do');">
								工作认证信息
							</li>
							
							<li onclick="updatePwd('setPwd.do');">
								修改密码
							</li>
							<li id="li_bp" onclick="bindingPhoneLoad('boundcellphone.do');">
								更换手机
							</li>
							<li  onclick="jumpUrl('szform.do');">
								通知设置
							</li>
							<li id="li_tx"  onclick="loadBankInfo('yhbound.do');">
								银行卡设置
							</li>
							--%><s:if test="#request.isApplyPro==1">
							<li  class="on"  onclick="jumpUrl('queryQuestion.do'); ">
								申请密保设置
							</li>
							</s:if>
							<s:if test="#request.isApplyPro==2">
							<li  class="on"  onclick="jump('answerPwdQuestion.do'); ">
								修改密保设置
							</li>
							</s:if>
        </ul>
     </div>

 <div class="myhome_content tab_content clearfix">
        <div class="s_changepw">	
         	 	<div class="mmxg clearfix">
                   	   <h5>会员申请密码保护</h5>
                       <ul class="clearfix">
                           <li><span>问题一：</span><s:select list="#request.list" id="questionOne"  name="paramMap.questionOne"
	             listKey="id" listValue="requestion"  headerKey="" headerValue="--请选择--"></s:select></li>
                           <li><span>问题一答案：</span><input type="text" class="inp188" id="answerOne" name="answerOne"/></li>
                            <li><span>问题二：</span><s:select list="#request.list" id="questionTwo"  
	             listKey="id" listValue="requestion"  headerKey="" headerValue="--请选择--"></s:select></li>
                           <li><span>问题二答案：</span><input type="text" class="inp188" id="answerOne" name="answerTwo"/></li>
                            <li><span>问题三：</span><s:select list="#request.list" id="questionThree" 
             listKey="id" listValue="requestion"  headerKey="" headerValue="--请选择--"></s:select></li>
                           <li><span>问题三答案：</span><input type="text" class="inp188" id="answerOne" name="answerThree"/></li>
                           <li><span style="color: red; font-size:12px;"><s:fielderror fieldName="paramMap.code" /></span></li>
                           <li><span>&nbsp;</span><input type="button" value="提 交 " class="myhome_btn" onclick="javascript:saveOrUpdatePwdAnswer();" /><span style="color: red; float: none;" id="s_tip" class="formtips">
      	<s:if test="#request.ISDEMO==1">* 演示站点随便输入答案</s:if></span></li>	
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
<script type="text/javascript" src="common/date/calendar.js"></script>
 <script type="text/javascript" src="css/popom.js"></script>    
<script>
	$(function() {
		dqzt(4);
  		$('#li_5').addClass('on');
	});
	  function jumpUrl(obj){
          window.location.href=obj;
       }
	  function bindingPhoneLoad(url) {
	/**		var bb = '${person}';//申请手机绑定必须先填写个人资料
			var cc = '${pass}';
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href='owerInformationInit.do';
			} else if (cc != 3) {
				alert("请等待个人信息审核通过");
				return ;
			} else {*/
				   window.location.href=url;
	//		}
		}
	  function jump(url) {
			
			alert("请先回答密保问题！");
			window.location.href = url;
	}
	//加载该用户提现银行卡信息
 		function loadBankInfo(url) {
 			var bb = '${person}';//设置提现银行卡必须先填写个人资料
 			if (bb == "0") {
 				alert("请先完善个人信息");
 				window.location.href="owerInformationInit.do";
 			} else {
 				window.location.href=url;
 			}

 		}
		//工作认证
 		function loadWorkInit(url){
 			var bb = '${person}';//填写工作信息必须先填写个人资料
 			if (bb == "0") {
 				alert("请先完善个人信息");
 				window.location.href="owerInformationInit.do";
 			} else {
 				window.location.href=url;
 			}
 		}
 		function updatePwd(url) {
 			window.location.href=url;
 			/*var bb = '${isApplyPro}';
 			if (bb == 1) { 
 				alert("请先设置安全问题");
 				window.location.href="queryQuestion.do";
 			}else if(bb == 2){
 				alert("请先回答安全问题");
 				window.location.href="getQuestion.do";
 			}*/
 		}
	//登录密码修改
	   function saveOrUpdatePwdAnswer(){
		   	param["paramMap.questionOne"] = $("#questionOne").find("option:selected").text();
	        param["paramMap.questionTwo"] = $("#questionTwo").find("option:selected").text();
	        param["paramMap.questionThree"] = $("#questionThree").find("option:selected").text();
			
	        param["paramMap.answerOne"] = $("#answerOne").val();
	        param["paramMap.answerTwo"] = $("#answerTwo").val();
	        param["paramMap.answerThree"] = $("#answerThree").val();
	     
	        if($("#questionOne").val()=="" || $("#questionTwo").val()=="" ||$("#questionThree").val()=="" ){
	           $("#s_tip").html("请选择问题回答");
	           return;
	        }
	        if($("#questionOne").val()==$("#questionTwo").val() || $("#questionTwo").val()==$("#questionThree").val() ||$("#questionThree").val()==$("#questionOne").val() ){
		           $("#s_tip").html("您不能选择重复的问题！");
		           return;
		        }
	        if($("#answerOne").val()=="" || $("#answerTwo").val()=="" ||$("#answerThree").val()==""){
	        	 $("#s_tip").html("请填写问题答案");
		           return;
		        }
	        $.shovePost("saveOrUpdatePwdAnswer.do",param,function(data){
	            if(data == 1){
	              alert("密保申请失败！");
	               $("#answerOne").attr("value","");
	               $("#answerTwo").attr("value","");
	               $("#answerThree").attr("value","");
	               window.location.href = "owerInformationInit.do";
	            }else if(data==2){
	               alert("密保申请成功！");
	               $("#answerOne").attr("value","");
	               $("#answerTwo").attr("value","");
	               $("#answerThree").attr("value","");
	               window.location.href="owerInformationInit.do";
	            }
	            else if(data==3){
		               alert("修改密保失败！");
		               $("#answerOne").attr("value","");
		               $("#answerTwo").attr("value","");
		               $("#answerThree").attr("value","");
		               window.location.href = "owerInformationInit.do";
		            }
	            else if(data==4){
		               alert("修改密保成功！");
		               $("#answerOne").attr("value","");
		               $("#answerTwo").attr("value","");
		               $("#answerThree").attr("value","");
		               window.location.href="owerInformationInit.do";
		            }
	        });
	   }
	   
	   
	</script>
</body>
</html>
