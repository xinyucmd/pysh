<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
<style>
input {
	height: 25px;
}

b {
	color: red;
}
.fred{
	color:red;
}

li {
	font-size: 14px;
	font-family: "宋体"
}
</style>
</head>
<body>
	<div id="right" style="padding: 15px 10px 0px 10px;">
		<div>
			<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
							<s:if test="#request.type==1">
								<a href="persioninfo.do?id=${userId}">基本信息</a>
								</s:if>
								<s:else>
								<a href="persioninfo.do?id=${userId}">基本信息</a>
								</s:else>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
							<s:if test="#request.userId==-1">
							   上传资料
							</s:if>
							<s:else>
							  <s:if test="#request.type==1">
							    <a href="persionData.do?id=${userId}">上传资料</a>
							  </s:if>
							  <s:else>
							  <a href="persionData.do?id=${userId}">上传资料</a>
							  </s:else>
							</s:else>
								
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="main_alll_h2">
							<s:if test="#request.authStep==2">
							<a href="personpublishBorrowInit.do?id=${userId}">发布借款</a>
							</s:if>
							<s:else>
							  发布借款
							</s:else>
							</td>
							<td width="2">
								&nbsp;
							</td>
						</tr>
					</table>
		</div>
			<p  style="font-size:12px;margin-left: 30px;"> <span class="fred">*</span>为必填项，所有资料均会严格保密。 </p>
			<p  style="font-size:12px;margin-top:20px;margin-left: 150px;font-weight:bold ;"> 借款基本信息
			<span class="fred" style="color: red;font-size: 12px; padding-left: 80px;line-height: 50px;"><s:fielderror fieldName="enough"></s:fielderror></span>
			 </p>
		    <div id="borrowInfo">
		       <form id="form" action="bgAddBorrowSeconds.do" method="post">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
					<tr style="">
							<td align="right">
								借款身份：&nbsp;
							</td>
							<td>
							
							<s:if test="#request.type==1">
							    <span id="typeName" class="fred">个人借款</span>
							    <input type="hidden" name="paramMap.borrowType" id="borrowType" value="1"/>

								</s:if>
								<s:else>
								  <span id="typeName" class="fred">企业借款</span>
							      <input type="hidden" name="paramMap.borrowType" id="borrowType" value="2"/>
								</s:else>
<%--								<s:select id="borrowType" name="paramMap.borrowType" list="#{1:'个人借款',2:'企业借款'}" cssClass="sel_140"></s:select>--%>
							</td>
						</tr>
						<tr height="8px;">
										<td></td>
										<td></td>
									</tr>
					<tr style="">
							<td align="right">
								借款类型：<span class="fred">*</span>
								<input name="paramMap.applyId" id="applyId" value="${paramMap.applyId}" type="hidden"/>
								<input name="paramMap.userId" id="userId" value="${paramMap.userId}" type="hidden"/>
								<input name="paramMap.type" id="type" value="${paramMap.type}" type="hidden"/>
								<input name="paramMap.authStep" id="authStep" value="${authStep}" type="hidden"/>
							</td>
							<td>
								<s:select id="borrowWay" name="paramMap.borrowWay" list="#{-1:'--请选择--',1:'净值借款',2:'秒还借款',3:'信用标',4:'定息宝',5:'抵押担保借款',6:'活利宝'}" cssClass="sel_140"></s:select>
							</td>
						</tr>
					<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
						<tr>
							    <td align="right">借款标题：<span class="fred">*</span></td>
							    <td><s:textfield name="paramMap.title" cssClass="inp280"/>
							        <span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
							    </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">借款图片：<span class="fred">*</span>
							    <input type="hidden" id="imgPath"  name="paramMap.imgPath" value="${paramMap.imgPath}"/>
							    </td>
							    <td>
							    <input type="hidden" id="personalImg" value="${user.personalHead}"/>
							    <input type="radio" name="radio" id="r_1" checked="checked" value="1" />
							      上传借款图片 
<%--							        <input type="radio" name="radio" id="r_2" value="2" />--%>
<%--							        使用用户头像 --%>
							        <input type="radio" name="radio" id="r_3" value="3" />
							        <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}"/>
							        使用系统头像<span class="fred"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span></td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">&nbsp;</td>
							    <td>
							    <div id="tab_1"><input id="btn_personalHead" value="上传图片"  style="background: #666666;color: white;width: auto;" type="button" /></div>
							    <div id="tab_2" style="display:none;"></div>
							    <div id="tab_3" style="display:none;">
							    <table id="sysimg" style="width:400px;">
							        <tr>
							            <td class="tx">
							            <s:iterator var="sysImage" value="sysImageList">
							              <span><img src="${sysImage.selectName}" class="selimg"/></span>
							            </s:iterator>
							            </td>
							        </tr>
							    </table>
							    </div>
							    </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">&nbsp;</td>
							     <td class="tx"><img id="img" src="" width="62" height="62"/></td>
							  </tr>
							  <tr>
							    <td align="right">&nbsp;</td>
							    <td class="tishi">（推荐使用您的生活近照，或其他与借款用途相关的图片，<br />
							      有助增加借款成功几率。严禁使用他人照片） </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">借款标的：</td>
							    <td><span id="typeName" class="fred">${request.typeName}</span></td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">借款目的：</td>
							    <td class="tishi">
							    <s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectValue" listValue="selectName"  value="1"></s:select>
							    </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">还款方式：</td>
							    <td >
							                    秒还
							    </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">借款总额：<span class="fred">*</span></td>
							    <td>
							    <s:textfield name="paramMap.amount" cssClass="inp100x" id="amount"/>
							    <span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span></td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">年利率：<span class="fred">*</span></td>
							    <td>
							    <s:textfield name="paramMap.annualRate" cssClass="inp100x"/>%
							    <span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span></td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <s:if test="#request.subscribeStatus!=1">
							  <tr>
							    <td align="right">最低投标金额：</td>
							    <td>
							   
							     <s:select list="borrowMinAmountList" name="paramMap.minTenderedSum" cssClass="sel_140" listKey="key" listValue="value"  id="minTenderedSum"></s:select> 
							    <span class="fred"><s:fielderror fieldName="paramMap['minTenderedSum']"></s:fielderror></span>
							    </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">最多投标金额：${subscribe_status }</td>
							    <td>
							    <s:select list="borrowMaxAmountList" name="paramMap.maxTenderedSum" cssClass="sel_140" listKey="key" listValue="value" headerKey="" headerValue="没有限制" id="maxTenderedSum"></s:select>
							    </td>
							  </tr>
							  </s:if>
							  <s:else>
							  	 <td align="right">最小认购单位：<span class="fred">*</span></td>
							    <td>
							    <s:textfield name="paramMap.subscribe"  cssClass="inp100x"/>元
							    <span class="fred"><s:fielderror fieldName="paramMap['subscribe']"></s:fielderror></span>
							    	<input name="subscribeStatus" type="hidden" id="subscribeStatus"  value="${subscribeStatus}"/>
							  
							    </td>
							  </s:else>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">筹标期限：<span class="fred">*</span></td>
							    <td>
							     <input type="hidden" name="validateDay" value="${validateDay }" />
							    <s:if test="#request.validateDay==1">
							    	<input type="hidden" name="paramMap.raiseTerm" value="0" />无限制
							    </s:if>
							    <s:else>
							    	<s:select list="borrowRaiseTermList" id="raiseTerm" name="paramMap.raiseTerm" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
							    	<span class="fred"><s:fielderror fieldName="paramMap['raiseTerm']"></s:fielderror></span>
							    </s:else>
							    </td>
							  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
						    <td align="right">借款详情：<span class="fred">*</span></td>
						    <td align="left" class="f66">
						    	<textarea name="paramMap.detail" style="height: 280px; width: 520px; border: 1px solid #ddd;">
									<s:property value="paramMap.detail"/>
								</textarea>
								<span class="require-field"><s:fielderror fieldName="paramMap['detail']"></s:fielderror></span>
						    </td>
						  </tr>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							     <td>&nbsp;</td>
							     <td><s:fielderror fieldName="paramMap['detail']"></s:fielderror></td>
							  </tr>
							   <tr> 
						  <td align="right">发布时间：<span class="fred">*</span>   </td> 
						  <td><input id="timeStart" name="paramMap.publishTime" class="Wdate" value="${paramMap.publishTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
						  <span class="fred"><s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
						  </td>
						    </tr>
							 <s:if test="#request.award_status==1">
							  <tr> 
						  <td align="right"><p  style="font-size:12px;font-weight:bold ;"> 投标奖励 </p>  </td> 
						  <td>&nbsp;</td>
						    </tr>
							    <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right">&nbsp;<input type="hidden" name="award_status" value="${award_status }" /><input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></td>
							    <td><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" />
							      不设置奖励</td>
							  </tr>
							  <tr>
							    <td align="right">&nbsp;</td>
							    <td><input type="radio" name="excitationType" id="radio_2" value="2" />
							      固定总额按投标比例分配奖励
							      <input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray" disabled="disabled"/>
							      元<span class="fred"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></span></td>
							  </tr>
							  <tr>
							    <td align="right">&nbsp;<input type="hidden" id="excitationMode" name="paramMap.excitationMode" value="${paramMap.excitationMode}"/></td>
							    <td><input type="radio" name="excitationType" id="radio_3" value="3" />
							      借款总额百分比分配奖励
							      <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="inp100x gray" disabled="disabled"/>
							      %<span class="fred"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></span></td>
							  </tr>
							  </s:if>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							   <s:if test="#request.password_status == 1">
							 <tr>
						    <td align="right"><p  style="font-size:12px;font-weight:bold ;">设置投标密码 </p>  </td> 
						    <td>&nbsp;</td>
						    </tr>
							  <tr>
							    <td align="right">&nbsp;</td>
							    <td>投标密码：
							    <input type="password" name="paramMap.investPWD" value="${paramMap.investPWD}" id="investPWD" class="inp100x" disabled="disabled"/>&nbsp;
							    <label><s:checkbox id="hasPWD" name="paramMap.hasPWD" />&nbsp;有密码</label>
							    </td>
							  </tr>
							  <tr><td></td><td><span class="fred"><s:fielderror fieldName="paramMap['investPWD']"></s:fielderror></span></td></tr>
							  
							  </s:if>
							  <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							   <tr>
						   <td align="right"><p  style="font-size:12px;font-weight:bold ;"> 提交借款信息 </p>  </td> 
						  <td>&nbsp;</td>
						    </tr>
							    <tr height="8px;">
						<td></td>
						<td></td>
					</tr>
							  <tr>
							    <td align="right"> 验证码：</td>
							    <td>
							    <s:textfield name="paramMap.code" id="code" cssClass="inp100x"/>
									 <img src="${sitemap.adminUrl}/imageCode.do?pageId=bgborrow" title="点击更换验证码" style="cursor: pointer;"
									 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
							    <span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>
							    </td>
							  </tr>
							    <tr>
							    <td align="right">&nbsp;</td>
							    <td style="padding-top:20px;"><input id="bcbtn" value="发布借款"  style="background: #666666;color: white;width: auto;margin-left: 100px;" type="button" /></td>
							  </tr>
							     <tr height="8px;">
							<td></td>
							<td></td>
						</tr>
						 <tr height="8px;">
							<td></td>
							<td></td>
						</tr>
							    </table>
					</form>
			</div>
		</div>
<script type="text/javascript">
$(function(){
	$("#borrowWay").change(function(){
		var applyId = '${applyId}';
		var userId = '${userId}'; 
		var authStep = '${authStep}';
		var type='${type}';
		if($(this).val()==-1){ 
			window.location.href='publishBorrowInit.do?paramMap.applyId='+applyId+'&paramMap.userId='+userId+'&paramMap.authStep='+authStep+'&paramMap.type='+type; 
		}else{
			if($("#borrowWay").val()=="6"){
				window.location.href='underCirculationBorrow.do?i='+userId; 
			}else{
				window.location.href='publishBorrow.do?paramMap.borrowWay='+$("#borrowWay").val()+'&paramMap.applyId='+applyId+'&paramMap.userId='+userId+'&paramMap.authStep='+authStep+'&paramMap.type='+type; 
			}
		}
	});
});
</script>
<script>
var editor_content;
KindEditor.ready(function(K) {
	editor_content = K.create('textarea[name="paramMap.detail"]', {
		cssPath : '../kindeditor/plugins/code/prettify.css',
		uploadJson : 'kindEditorUpload.do',
		fileManagerJson : 'kindEditorManager.do',
		allowFileManager : true,
	});
});
</script>
<script>
var flag = true;
$(document).ready(function(){ 
   var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
     var radioval = $('#radioval').val();
     if(radioval !=''){
          $('#r_'+radioval).attr('checked','true');
          if(radioval ==1){
             $('#tab_1').css('display','block');
          }
          if(radioval ==2){
             $('#tab_2').css('display','block');
             $('#tab_1').css('display','none');
          }
          if(radioval ==3){
             $('#tab_3').css('display','block');
             $('#tab_1').css('display','none');
          }
     }
});
$(function(){
     //样式选中
 
<%--	 if($('#imgPath').val()==""){--%>
<%--		 $('#imgPath').val("images/default-img.jpg");--%>
<%--		 }--%>
<%--	 $('#img').attr('src',$('#imgPath').val());--%>

		var img='${paramMap.imgPath}';
		var image;
		if(img=="" ){
			img = "../images/default-img.jpg";
			image="images/default-img.jpg";
		}else{
			img ="../"+img;
			image = img.substring(3);
		}
		$('#imgPath').val(image);
		$("#img").attr("src",img);

	 $('#bcbtn').click(function(){
		 var borrowWay =$("#borrowWay").val();
		 if(borrowWay==-1){
			 alert('请选择借款类型!');
			 return false;
		}else if(borrowWay == 6){
			alert('请跳转到正确的页面');
			return false;
		}
		  if($("#subscribeStatus").val()!=1){
		       var arStart = $('#minTenderedSum').val();
		       var amount = $('#amount').val();
			   arStart = parseFloat(arStart);
			   amount = parseFloat(amount);
			   var arEnd = $('#maxTenderedSum').val();
			   arEnd = parseFloat(arEnd);
			   if(arStart > arStart){
				   alert('最多投标金额不能小于最低投标金额!');
				   $('#maxTenderedSum')[0].selectedIndex = 0;
			      return false;
			   }
			   if(arStart > amount){
				   alert('最低投标金额不能超过借款金额!');
				   $('#minTenderedSum')[0].selectedIndex = 0;
				   return false;
			   }
			   if(arEnd > amount){
				   alert('最多投标金额不能超过借款金额!');
				   $('#maxTenderedSum')[0].selectedIndex = 0;
				   return false;
			   }
		   }
		   if(flag){
			  editor_content.sync();
	          $('#form').submit();
	          flag = false;
		   }
	 });
	 $("#maxTenderedSum").change(function(){
	       var arStart = $('#minTenderedSum').val();
		   arStart = parseFloat(arStart);
		   var arEnd = $(this).val();
		   arEnd = parseFloat(arEnd);
		   if(arEnd < arStart){
			   alert('最多投标金额不能小于最低投标金额!');
			   $('#maxTenderedSum')[0].selectedIndex = 0;
		   }
	 });
	 $("#minTenderedSum").change(function(){
	       $('#maxTenderedSum')[0].selectedIndex = 0;
	 });
	 //上传图片
	 $("#btn_personalHead").click(function(){
			var dir = getDirNum();
			var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
			json = encodeURIComponent(json);
			window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
			var headImgPath = $("#img").attr("src");
			if(headImgPath ==""){
				alert("上传失败！");	
			}
	  });


	 $('input[name="excitationType"]').click(function(){
		    if($(this).val() == 2){
		       $('#sum').removeClass('gray');
		       $('#sum').removeAttr('disabled',true);
		       $('#sumRate').addClass('gray');
		       $('#sumRate').attr('disabled',true);
		       $('#sumRate').val('');
		    }else if($(this).val() == 3){
		       $('#sumRate').removeClass('gray');
		       $('#sumRate').removeAttr('disabled',true);
		       $('#sum').addClass('gray');
		       $('#sum').attr('disabled',true);
		       $('#sum').val('');
		    }else{
		       $('#sumRate').addClass('gray');
		       $('#sumRate').attr('disabled',true);
		       $('#sumRate').val('');
		       $('#sum').addClass('gray');
		       $('#sum').attr('disabled',true);
		       $('#sum').val('');
		    }
		    $('#excitation').val($(this).val());
		 });




	  
	  $('#sysimg img').click(function(){
	      $('#imgPath').val($(this).attr('src'));
	      $('#img').attr('src',$(this).attr('src'));
	  });
	  $('#r_1').click(function(){
	      $('#tab_1').css('display','block');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','none');
	      $('#radioval').val('1');
	      img = "../images/default-img.jpg";
	      $("#img").attr("src",img);
	      $("#imgPath").val("images/default-img.jpg");
	  });
<%--	  $('#r_2').click(function(){--%>
<%--	      var personalImg=$('#personalImg').val();--%>
<%--	      $('#tab_1').css('display','none');--%>
<%--	      $('#tab_2').css('display','block');--%>
<%--	      $('#tab_3').css('display','none');--%>
<%--	      $('#radioval').val('2');--%>
<%--	      $('#img').attr('src',personalImg);--%>
<%--	      $('#imgPath').val(personalImg);--%>
<%--	  });--%>
	  $('#r_3').click(function(){
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','block');
	      $("#img").css('display','block');
	      $('#radioval').val('3');
	     // img = "images/default-img.jpg";
	     // $("#img").attr("src",img);
	  });
	  $('#hasPWD').click(function(){
	    var hasPWD = $('#hasPWD').attr('checked');
        if(hasPWD =='checked'){
          $('#investPWD').attr('disabled',false);
        }else{
          $('#investPWD').attr('disabled',true);
        }
        $('#investPWD').val('');
	  });
      switchCode();
});
function switchCode(){
        var hasPWD = $('#hasPWD').attr('checked');
        if(hasPWD =='checked'){
          $('#investPWD').attr('disabled',false);
        }else{
          $('#investPWD').attr('disabled',true);
        }
	    var timenow = new Date();
	    $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=bgborrow&d='+timenow);
};				     
</script>
<script>
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "../upload/"+basepath+"/"+fileName;
		var imgPath = "upload/"+basepath+"/"+fileName;
		$("#img").attr("src",path);
		$("#setImg").attr("src",path);
		$("#imgPath").val(imgPath);
	}
}
function getDirNum(){
	var date = new Date();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m<10){
		m = "0"+m;
    }
	if(d<10){
	   d = "0"+d;
	}
	var dirName = date.getFullYear()+""+m+""+d;
	return dirName; 
}
</script>
</body>
</html>