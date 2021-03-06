﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
</head>
<body>
<script type="text/javascript" language="javascript">
$(function(){
	      
	     if('${bonding_letter_path}'==""){
		     $("#bonding_letter_path_img").attr("src","../images/NoImg.GIF");
	     }else{
		     $('#bonding_letter_path_img').attr('src',"../"+'${bonding_letter_path}');
	     }
});
</script>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<form id="form" action="addBorrow.do" method="post">
<div class="s_jkmain clearfix">
      <!--左导航 开始-->	
      <div class="l_nav clearfix">
         <ul class="clearfix">
		    <li><a href="querBaseData.do?from=1"><span>step1 </span> 基本资料</a></li>
		    <li><a href="userPassData.do?from=1"><span>step2 </span> 上传资料</a></li>
		    <li class="on last"><a href="javascript:void(0);"><span>step3 </span> 发布贷款</a></li>
	    </ul>
      </div>
      <!--左导航 结束-->
      
      <!--右内容 开始-->	
      <div class="r_main clearfix">
      	<!--标题-->
      	 <div class="jkdet_tit tab_meun">
         	<ul  id="ul">
            	<li class="on">发布贷款</li>
            	<span class="fred" style="color: red;font-size: 12px; padding-left: 80px;line-height: 50px;"><s:fielderror fieldName="enough"></s:fielderror></span>
            </ul>
         </div>
         <!--发布贷款-->	
         <div class="jkdet_content clearfix">
			 <p class="tips"><em class="fred">* </em>为必填项，所有资料均会严格保密。</p>
             <h5>借款基本信息</h5>
             <ul class="jk_fbdk clearfix">
                <li>
                    <span><em class="fred">* </em>借款标题：</span>
                    <span><input name="paramMap.title" type="text" class="test" maxlength="20" value="${paramMap.title}"/></span>
    				<span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>借款图片：</span><input type="hidden" id="imgPath"  name="paramMap.imgPath" value="${paramMap.imgPath}"/>
				    	<input type="hidden" id="personalImg" value="${user.personalHead}"/>&nbsp;
				        <span><label><input type="radio" name="radio" id="r_1" checked="checked" value="1" />上传借款图片</label></span> 
				        <span><label><input type="radio" name="radio" id="r_2" value="2" />使用用户头像 </label></span>
				        <span><label><input type="radio" name="radio" id="r_3" value="3" />使用系统头像</label></span>
				        <span class="fred"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span>
				        <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}"/>
                </li>
                <li>
                    <span>&nbsp;</span>
                    <div id="tab_1"><a href="javascript:void(0);" id="btn_personalHead" class="scbtn">上传图片</a></div>
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
                </li>
                <li>
                    <span>&nbsp;</span>
                    <img id="img" src="${headImg}" width="62" height="62"/>
                </li>
                <li>
                    <span>&nbsp;</span>
                    （推荐使用您的生活近照，或其他与借款用途相关的图片，有助增加借款成功几率。严禁使用他人照片）
                </li>
                <li>
                    <span>借款标的：</span><span id="typeName" class="fred">${session.typeName}</span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>借款目的：</span>
                    <span><s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectValue" listValue="selectName"  headerKey="" headerValue="--请选择--"></s:select></span>
    				<span class="fred"><s:fielderror fieldName="paramMap['purpose']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>借款期限：</span>
                    <span><s:select list="borrowDeadlineMonthList" id="deadLine" name="paramMap.deadLine" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select></span>
    <span><s:select list="borrowDeadlineDayList" id="deadDay" cssStyle="display:none;" name="paramMap.deadDay" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select></span>
    <span class="fred"><s:fielderror fieldName="paramMap['deadLine']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span>&nbsp;</span>
                    <label><s:checkbox name="paramMap.daythe" id="daythe"/>&nbsp;置为天标</label> 
                </li>
                <li>
                    <span><em class="fred">* </em>还款方式：</span>
                    <span><s:select id="paymentMode" name="paramMap.paymentMode" list="borrowRepayWayList" cssClass="sel_140" listKey="id" listValue="name" headerKey="" headerValue="--请选择--"></s:select></span>
    <span class="fred"><s:fielderror fieldName="paramMap['paymentMode']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>借款总额：</span>
                    <span><input type="text" id="amount" name="paramMap.amount" class="test" value="${paramMap.amount}"/></span><span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span>&nbsp;
                </li>
                
                <!-- whb 添加是否专享标-->
                <li>
                    <span><em class="fred">* </em>是否专享标：</span>
				    	<input type="hidden" id="isExclus" name="paramMap.isExclus" value="${paramMap.isExclus}"/>&nbsp;
				        <span style="min-width:30px;"><label><input type="radio" name="isExclusRadio" id="isExclus_0" value="0" checked="checked"/>否 </label></span>
				        <span style="min-width:30px;"><label><input type="radio" name="isExclusRadio" id="isExclus_1" value="1" />是</label></span>
                </li>
                
                <s:if test="#request.borrow_sdkc==1">
                <li>
	                <span><em class="fred">* </em>是否显示抵押物：</span>
					<span style="min-width:30px;"><label><input type="radio" name="isShowDyw"  value="0" />否 </label></span>
					<span style="min-width:30px;"><label><input type="radio" name="isShowDyw" value="1" checked="checked"/>是</label></span>
                </li>
                </s:if>
                
                <li>
                    <span><em class="fred">* </em>年利率：</span>
                    <span><input type="text" name="paramMap.annualRate" maxlength="5" value="${paramMap.annualRate}" class="inp100x" />%</span><span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span>&nbsp;
                </li>
                
                <s:if test="#request.borrow_sdkc==1">
                <li>
	                 <span><em class="fred"> </em>加息利率：</span>
	                 <span><input type="text" name="paramMap.add_interest" maxlength="5" value="${paramMap.add_interest}" class="inp100x" />%</span><span class="fred"><s:fielderror fieldName="paramMap['add_interest']"></s:fielderror></span>&nbsp;
                </li>
                
                </s:if>
                
                <s:if test="#request.subscribeStatus!=1">
                <li>
                    <span><em class="fred">* </em>最低投标金额：</span>
                    <span><s:select list="borrowMinAmountList" name="paramMap.minTenderedSum" cssClass="sel_140" listKey="key" listValue="value"   ></s:select></span>
    <span class="fred"><s:fielderror fieldName="paramMap['minTenderedSum']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>最多投标金额：</span>
                    <s:select list="borrowMaxAmountList" name="paramMap.maxTenderedSum" cssClass="sel_140" listKey="key" listValue="value"  headerKey="" headerValue="没有限制" ></s:select>
                </li>
                </s:if>
                <s:else>
                	<span><em class="fred">* </em>最小认购单位：
                	<s:textfield name="paramMap.subscribe"  cssClass="inp100x"/>元</span>
				    <span class="fred"><s:fielderror fieldName="paramMap['subscribe']"></s:fielderror></span>&nbsp;
				    	<input name="subscribeStatus" type="hidden" id="subscribeStatus"  value="${subscribeStatus}"/>
                </s:else>
                <li>
                    <span><em class="fred">* </em>筹款期限：</span>
                    <span><input type="hidden" name="validateDay" value="${validateDay }" /></span>
                    <s:if test="#request.validateDay==1">
				    	<span><input type="hidden" name="paramMap.raiseTerm" value="0" />无限制</span>
				    </s:if>
				    <s:else>
				    	<span><s:select list="borrowRaiseTermList" id="raiseTerm" name="paramMap.raiseTerm" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select></span>
				    	<span class="fred"><s:fielderror fieldName="paramMap['raiseTerm']"></s:fielderror></span>&nbsp;
				    </s:else>
                </li>
                <li>
                    <span><em class="fred">* </em>借款详情：</span>
                    <textarea name="paramMap.detail" class="txt420">${paramMap.detail}</textarea>
                </li>
                <li>
                    <span>&nbsp;</span>
                    <span class="fred"><s:fielderror fieldName="paramMap['detail']"></s:fielderror></span>&nbsp;
                </li>
                
                
                
                <s:if test="#request.modelType==1">
                  <li>
                        <span>
                           <em class="fred">* </em>担保机构：
                           <input type="hidden" id="loanId" name="" value="${loan_comp_id}"/>
                           <input type="hidden" id="bonding_ids" name="" value="${bonding_ids}"/>
                        </span>
                        <span>
					    <select id="bondingId" name="bondingId" onchange="onCompanyBondingByIdData()">
							<option value="0">请选择担保机构</option>
						    <c:forEach items="${modelList}" var="list">
					           <option value="${list.bonding_comp_id}">${list.name}</option>
					        </c:forEach>
				        </select>
				        </span>
				        <span class="fred"><s:fielderror fieldName="paramMap['companyBondingMsg']"></s:fielderror></span>
                        <br />
                  </li>
                  <div id="bondingCompanyDiv">
                    <li>
                        
                        <span><em class="fred">  </em>担保函: </span>
                        <span>
                        <input type="hidden" id="changeModelType" name="changeModelType" value="${changeModelType }"/>
					    <input type="hidden" id="bonding_letter_path" name="bonding_letter_path" value="${bonding_letter_path}"  class="input_a"/>
				        <img id="bonding_letter_path_img" src="" width="62" height="62"/>
				        <a href="###" id="" class="scbtn"  onclick="bonding_letter_path_a()">上传图片</a>
				        </span>
				        <span class="fred"><s:fielderror fieldName="paramMap['bonding_letter_path']"></s:fielderror></span>
                    </li>
                  </div>
                </s:if>
                		
             </ul>
             <s:if test="#request.award_status==1">
             	<h5>投标奖励</h5>
	             <ul class="jk_fbdk clearfix" style="padding-left:80px;">
	             	<li><input type="hidden" name="award_status" value="${award_status }" /><input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></li>
	             	<li><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" />不设置奖励</li>
	                <li><input type="radio" name="excitationType" id="radio_2" value="2" />按固定比例金额分摊奖励<input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray" disabled="disabled"/> 元
	                <span class="fred"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></span>&nbsp;</li>
	                <input type="hidden" id="excitationMode" name="paramMap.excitationMode" value="${paramMap.excitationMode}"/>
	                <li><input type="radio" name="excitationType" id="radio_3" value="3" />借款总额百分比分配奖励 <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="inp100x gray" disabled="disabled"/>
	                 %<span class="fred"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></span>&nbsp;</li>  
	             </ul>
             </s:if>
             <s:if test="#request.password_status == 1">
             	<h5>设置投标密码</h5>
	             <ul class="jk_fbdk clearfix">
	             	<input type="hidden" name="password_status" value="${password_status }" />
	                <li>
	                    <span>投标密码：</span>
	                    <input type="password" id="investPWD" value="${paramMap.investPWD }" disabled="disabled" name="paramMap.investPWD" maxlength="20" class="inp100x" />&nbsp;
	                    <label><s:checkbox id="hasPWD" name="paramMap.hasPWD" />&nbsp;有密码</label>
	                </li>
	                <li><span class="fred"><s:fielderror fieldName="paramMap['investPWD']"></s:fielderror></span>&nbsp;</li>
	             </ul>
             </s:if>
             
             <h5>提交借款信息</h5>
             <ul class="jk_fbdk clearfix">
                <li>
                    <span>验证码：</span>
                    <span><input type="text" class="inp100x" name="paramMap.code" id="code"/>
						 <img src="" title="点击更换验证码" style="cursor: pointer;"
						 	  id="codeNum" width="100" height="30" onclick="javascript:switchCode()" /></span>
				    <span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>&nbsp;
                </li>
                <li><span class="fred"><s:fielderror fieldName="paramMap['allError']"></s:fielderror></span>&nbsp;</li>
                <li>
                    <span>&nbsp;</span>
                    <a id="bcbtn" style="cursor:pointer;" class="bcbtn">提交发布</a>
                </li>
                <li>
                <span>&nbsp;</span>
                <span class="fred" style="display: none"><s:fielderror fieldName="paramMap['checkDate']"></s:fielderror></span>
                </li>
             </ul>          
         </div>		
      </div>
      <!--右内容 结束-->	 
</div>
</form>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script>
var flag = true;
$(document).ready(function(){
 var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
	 
	 var img='${paramMap.imgPath}';
	 if(img=="" ){
		img = "images/default-img.jpg";
	}
	 $('#imgPath').val(img);
	 $("#img").attr("src",img);
     //$('#imgPath').val('${paramMap.imgPath}');
    // $("#img").attr("src",'${paramMap.imgPath}');
     $('#purpose').val('${paramMap.purpose}');
     $('#deadLine').val('${paramMap.deadLine}');
     $('#paymentMode').val('${paramMap.paymentMode}');
     $('#raiseTerm').val('${paramMap.raiseTerm}');
     $('#excitation').val('${paramMap.excitationType}');
     $('#excitationMode').val('${paramMap.excitationMode}');
     $('#radioval').val('${paramMap.radioval}');
     var excitation = $('#excitation').val();
     var mode = $('#excitationMode').val();
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
     if(excitation != ''){
        $('#radio_'+excitation).attr('checked','true');
        if(excitation == 2){
	       $('#sum').removeClass('gray');
	       $('#sum').removeAttr('disabled');
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	    }else if(excitation == 3){
	       $('#sumRate').removeClass('gray');
	       $('#sumRate').removeAttr('disabled');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
	       $('#sum').val('');
	    }
     }else{
        $('#excitation').val('1');
     }
     if(mode == '2'){
        $('#mode').attr('checked','true');
     }else{
        $('#excitationMode').val("1");
     }
});
var paymentModeHtml ;
$(function(){
   dqzt(2);
   //样式选中
   paymentModeHtml = $("#paymentMode").html();
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
	 $('#mode').click(function(){
	    var check = $(this).attr('checked');
	    if(check == 'checked'){
	        $('#excitationMode').val('2');	    
	    }else{
	        $('#excitationMode').val('1');
	    }
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
	  
	 $('#daythe').click(function(){
	      showdays();
	 });
	 $('#bcbtn').click(function(){
		 //whb 添加是否专享标
		 $('#isExclus').val($('input[name="isExclusRadio"]:checked').val());
		 
		 if($("#subscribeStatus").val()!=1){
	       var arStart = $('#paramMap_minTenderedSum').val();
	       var amount = $('#amount').val();
		   arStart = parseFloat(arStart);
		   amount = parseFloat(amount);
		   var arEnd = $('#paramMap_maxTenderedSum').val();
		   arEnd = parseFloat(arEnd);
		   if(arStart > arStart){
			   alert('最多投标金额不能小于最低投标金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
		      return false;
		   }
		   if(arStart > amount){
			   alert('最低投标金额不能超过借款金额!');
			   $('#paramMap_minTenderedSum')[0].selectedIndex = 0;
			   return false;
		   }
		   if(arEnd > amount){
			   alert('最多投标金额不能超过借款金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
			   return false;
		   }
		 }
		 //  alert("您申请的借款已经提交管理人员进行审核，谢谢！")
		   if(flag){
	           $('#form').submit();
	           flag = false;
		   }
	 });
	 $("#paramMap_maxTenderedSum").change(function(){
	       var arStart = $('#paramMap_minTenderedSum').val();
		   arStart = parseFloat(arStart);
		   var arEnd = $(this).val();
		   arEnd = parseFloat(arEnd);
		   if(arEnd < arStart){
			   alert('最多投标金额不能小于最低投标金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
		   }
	 });
	 $("#paramMap_minTenderedSum").change(function(){
	       $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
	 });
	 //上传图片
	 $("#btn_personalHead").click(function(){
			var dir = getDirNum();
			var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':1.0,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
			json = encodeURIComponent(json);
			window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
			var headImgPath = $("#img").attr("src");
			if(headImgPath ==""){
				alert("上传失败！");	
			}
	  });
	  $('#sysimg img').click(function(){
	      $('#imgPath').val($(this).attr('src'));
	      $('#img').attr('src',$(this).attr('src'));
	  });
	  $('#r_1').click(function(){
	      $('#tab_1').css('display','block');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','none');
	      $("#img").css('display','block');
	      $('#radioval').val('1');
	      img = "images/default-img.jpg";
	      $("#img").attr("src",img);
	  });
	 

 
	  $('#r_2').click(function(){
	      var personalImg=$('#personalImg').val();
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','block');
	      $('#tab_3').css('display','none');
	      $("#img").css('display','block');
	      $('#radioval').val('2');
	      $('#img').attr('src',personalImg);
	      $('#imgPath').val(personalImg);
	  });
	  $('#r_3').click(function(){
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','block');
	      $("#img").css('display','block');
	      $('#radioval').val('3');
	     // img = "images/default-img.jpg";
	      //$("#img").attr("src",img);
	  });
	  $('#paymentMode').change(function(){
	      var check = $('#daythe').attr('checked');
	      if(check == true){
	         $('#paymentMode').get(0).selectedIndex = 1;
	      }
	  });
	  
	 
	  
      switchCode();
      showdays();
      
      //add by houli 进入页面发布借款的时候给用户提示信息
    var msg = '${request.msg}';
    var ulimit = '${request.usableCreditLimit}';
    var limit = '${request.creditLimit}';
    if(msg != null && msg !=""){
       alert(msg);
    }
    if($("#investPWD").val()!=''){
    	  $('#investPWD').attr('disabled',false);
    	  $('#hasPWD').attr('checked',true);
     }
//--------end
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=borrow&d='+timenow);
};			
	     
function showdays(){
          var check = $('#daythe').attr('checked');
	      if(check == 'checked'){
	        $('#deadLine').css('display','none');
	        $('#deadDay').css('display','block');
	        $('#paymentMode').attr('disabled',true);
	       // $('#paymentMode').get(0).selectedIndex = 1;
	       	 
	         $("#paymentMode").html("<option value='1'>等额本息</option>");
	      }else{
	        $('#paymentMode').attr('disabled',false);
	        $('#paymentMode').get(0).selectedIndex = 0;
	        $('#deadLine').css('display','block');
	        $('#deadDay').css('display','none');
	        $("#paymentMode").html("");
	        $("#paymentMode").html(paymentModeHtml);
	      }
}
</script>
<script>
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#img").attr("src",path);
		$("#setImg").attr("src",path);
		$("#imgPath").val(path);
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

//担保公司 担保函图片
function bonding_letter_path_a(){
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'bondingLetter/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadBondingLetterCall','cp':'bonding_letter_path_img'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#bonding_letter_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//担保公司担保函图片路径回调	    	
function uploadBondingLetterCall(basepath,fileName,cp){
	if(cp == "bonding_letter_path_img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#bonding_letter_path_img").attr("src","../"+path);
		$("#bonding_letter_path").val(path);
	}
}

//选择担保机构
function onCompanyBondingByIdData(){
	if($('#bondingId').val()==0){
		$('#bondingCompanyDiv').hide();
		return;
	}
	var param = {};
	param["loanId"] = $('#loanId').val();
	param["bondingId"] = $('#bondingId').val();
	$.ajax({
	    type: "post",
	    url: "queryCompanyModelType.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	      var TYPE = data.type;
	      $('#changeModelType').val(TYPE);
	      if(TYPE==1){
	    	    $('#bondingCompanyDiv').hide();
	      }
	      if(TYPE==2){
		    	 $('#bondingCompanyDiv').show();
		      }
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	       alert(errorThrown);
	    }
	});
	
	
}

var obj1=document.getElementById("bondingId");
 
for(i=0;i<=obj1.options.length;i++){
 if((obj1.options[i].value)=='${bonding_ids}'){      
	   obj1.options[i].selected=true;   
      break;
 }
}
</script>
<script type="">
   if('${checkeds}'==1){
     alert('${values}');
}
</script>
</body>
</html>