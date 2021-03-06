﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<form id="form" action="addCirculationBorrow.do" method="post">

<div class="s_jkmain clearfix">
      <!--左导航 开始-->	
      <div class="l_nav clearfix">
           <ul class="clearfix">
            <li><a href="querBaseData.do?from=1"><span>STEP1</span> 基本资料</a></li>
            <li><a href="userPassData.do?from=1"><span>STEP2</span> 上传资料</a></li>
            <li class="on"><a href="javascript:void(0);"><span>STEP3</span> 发布贷款</a></li>
         </ul>
      </div>
      <!--左导航 结束-->
      
      <!--右内容 开始-->	
      <div class="r_main clearfix">
      	<!--标题-->
      	 <div class="jkdet_tit tab_meun">
         	<ul id="ul">
            	<li class="on">发布贷款</li><span class="fred" style="color: red;font-size: 12px; padding-left: 80px;line-height: 50px;"><s:fielderror fieldName="enough"></s:fielderror></span>
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
                    <span><em class="fred">* </em>借款图片：<input type="hidden" id="imgPath"  name="paramMap.imgPath" value="${paramMap.imgPath}"/></span>
                    <input type="hidden" id="personalImg" value="${user.personalHead}"/>
        <label><input type="radio" name="radio" id="r_1" checked="checked" value="1" />上传借款图片</label> 
        <label><input type="radio" name="radio" id="r_2" value="2" />使用用户头像 </label>
        <label><input type="radio" name="radio" id="r_3" value="3" />使用系统头像</label>
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
					            <s:iterator var="sysImage" value="sysImageList"><%--
					              <span><img src="${sysImage.selectName}" class="selimg"/></span>
					            --%></s:iterator>
					            </td>
					        </tr>
					    </table>
					    </div>
                </li>
                <li>
                    <span>&nbsp;</span>
                    <shove:img src="${headImg}"   defaulImg="images/default-img.jpg" id="img" width="62" height="62"></shove:img>
                </li>
                <li>
                    <span>&nbsp;</span>
                    （推荐使用您的生活近照，或其他与借款用途相关的图片，有助增加借款成功几率。严禁使用他人照片）
                </li>
                <li>
                    <span>借款标的：</span><span id="typeName" class="fred">流转标</span>&nbsp;
                </li>
                <input type="hidden" id="paramMap.paymentinsti"  name="paramMap.paymentinsti" value="instiList.selectValue"/>
                <input type="hidden" id="paramMap.paymentcounter"  name="paramMap.paymentcounter" value="counterList.selectValue"/>
                <li>
                    <span><em class="fred">* </em>借款目的：</span>
                    <span><s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel" listKey="selectValue" listValue="selectName"  headerKey="" headerValue="--请选择--"></s:select></span>
    <span class="fred"><s:fielderror fieldName="paramMap['purpose']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>回购期限：</span>
                    <span><s:select list="borrowDeadlineMonthList" id="deadLine" name="paramMap.deadLine" cssClass="sel" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select></span>
    <span><s:select list="borrowDeadlineDayList" id="deadDay" cssStyle="display:none;" name="paramMap.deadDay" cssClass="sel" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select></span>
    <span class="fred"><s:fielderror fieldName="paramMap['deadLine']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>还款方式：</span><span class="fred">一次性还款</span>&nbsp;
                    
                </li>
                <li>
                    <span><em class="fred">* </em>借款总额：</span>
                    <span><input type="hidden" name="paramMap.borrowMoneyfirst" value="${paramMap.borrowMoneyfirst }" /></span>
    <input type="hidden" name="paramMap.borrowMoneyend" value="${paramMap.borrowMoneyend }"/>
      <input type="hidden" name="paramMap.accountmultiple" value="${paramMap.accountmultiple }"/>
    <input type="text" id="amount" name="paramMap.amount" class="test" value="${paramMap.amount}"/><span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span>
    【借款总额】的金额范围<a style="color: red;">${paramMap.borrowMoneyfirst }~${paramMap.borrowMoneyend }元，而且是${paramMap.accountmultiple }的倍数</a>
                </li>
                
                 <!-- whb 添加是否专享标-->
                <li>
                    <span><em class="fred">* </em>是否专享标：</span>
				    	<input type="hidden" id="isExclus" name="paramMap.isExclus" value="${paramMap.isExclus}"/>&nbsp;
				        <span><label><input type="radio" name="isExclusRadio" id="isExclus_0" value="0" checked="checked"/>否 </label></span>
				        <span><label><input type="radio" name="isExclusRadio" id="isExclus_1" value="1" />是</label></span>
                </li>
                
                <li>
                    <span><em class="fred">* </em>年利率：</span>
                    <input type="hidden" name="paramMap.aprfirst" value="${paramMap.aprfirst }"/>
      <input type="hidden" name="paramMap.aprend" value="${paramMap.aprend }"/>
    <input type="text" name="paramMap.annualRate" maxlength="5" value="${paramMap.annualRate}" class="inp100x" />%<span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span>
     【年利率】的比率范围<a style="color: red;">${paramMap.aprfirst }~${paramMap.aprend }%</a>
                </li>
                <li>
                    <span><em class="fred">* </em>最小流转单位：</span>
                    <input type="text" name="paramMap.smallestFlowUnit" maxlength="20" value="${paramMap.smallestFlowUnit}" class="test" />
                    <span class="fred"><s:fielderror fieldName="paramMap['smallestFlowUnit']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>单次最大投标金额：</span>
                    <input type="text" name="paramMap.bigestFlowUnit" maxlength="20" value="${paramMap.bigestFlowUnit}" class="test" />
                    <span class="fred"><s:fielderror fieldName="paramMap['bigestFlowUnit']"></s:fielderror></span>&nbsp;
                </li>
                
                <li>
                    <span><em class="fred">* </em>累计投标金额：</span>
                    <input type="text" name="paramMap.maxMoneyValue" maxlength="20" value="${paramMap.maxMoneyValue}" class="test" />
                    <span class="fred"><s:fielderror fieldName="paramMap['maxMoneyValue']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>商业详情：</span>
                    <textarea name="paramMap.businessDetail" class="tre">${paramMap.businessDetail}</textarea>
                </li>
                <li><span class="fred"><s:fielderror fieldName="paramMap['businessDetail']"></s:fielderror></span></li>
                <li>
                    <span><em class="fred">* </em>资产情况：</span>
                    <textarea name="paramMap.assets" class="tre">${paramMap.assets}</textarea>
                </li>
                <li><span class="fred"><s:fielderror fieldName="paramMap['assets']"></s:fielderror></span>&nbsp;</li>
                <li>
                    <span><em class="fred">* </em>资金用途：</span>
                    <textarea name="paramMap.moneyPurposes" class="tre">${paramMap.moneyPurposes}</textarea>
                </li>
                <li><span class="fred"><s:fielderror fieldName="paramMap['moneyPurposes']"></s:fielderror></span>&nbsp;</li>	
             </ul>
             <h5>投标奖励</h5>
             <ul class="jk_fbdk clearfix" style="padding-left:80px;">
                <li><input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></li>
                <li><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" /> 不设置奖励</li>
                
                <li><input type="hidden" name="paramMap.accountfirst" value="${paramMap.accountfirst }"/><input type="hidden" name="paramMap.accountend" value="${paramMap.accountend }"/>
                <input type="radio" name="excitationType" id="radio_2" value="2" />
      固定总额按投标比例分配奖励
      <input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray" disabled="disabled"/>
      元<br />(奖励金额在<a style="color: red;">${paramMap.accountfirst }~${paramMap.accountend }</a>元区间【固定总额奖励】)<a style="color: red;"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></a></li>
                
                <li><input type="hidden" id="excitationMode" name="paramMap.excitationMode" value="${paramMap.excitationMode}"/>
                <input type="radio" name="excitationType" id="radio_3" value="3" />
    借款总额百分比分配奖励
        <input type="hidden"  name="paramMap.scalefirst" value="${paramMap.scalefirst }"/>
         <input type="hidden" name="paramMap.scaleend" value="${paramMap.scaleend }"/>
      <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="inp100x gray" disabled="disabled"/>
      % <br />(奖励比例在<a style="color: red;">${paramMap.scalefirst }~${paramMap.scaleend }%</a> 区间【借款总额比例奖励】)<a style="color: red"><a style="color: red;"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></a></li>  
             </ul>
             <h5>提交借款信息</h5>
             <ul class="jk_fbdk clearfix">
                <li>
                    <span>验证码：</span>
                    <span><input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="100" height="30" onclick="javascript:switchCode()" /></span>
    <span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span>&nbsp;</span>
                    <a id="bcbtn" style="cursor:pointer;" class="bcbtn">提交发布</a>
                </li>
             </ul>          
         </div>		
      </div>
      <!--右内容 结束-->	 
</div>
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-jk.js"></script>
<script>
$(document).ready(function(){
	var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
	
  /*   $('#imgPath').val('${paramMap.imgPath}');
     $("#img").attr("src",'${paramMap.imgPath}');
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
     */
	 var img='${paramMap.imgPath}';
	 if(img.length==0 ){
		img = "images/default-img.jpg";
	}
	 $('#imgPath').val(img);
	 $("#img").attr("src",img);
    
     $('#purpose').val('${paramMap.purpose}');
     $('#deadLine').val('${paramMap.deadLine}');
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

$(function(){
	 dqzt(2);
     //样式选中
     $("#jk_hover").attr('class','nav_first');
	 $("#jk_hover div").removeClass('none');
	 var flag = true;
	 $('#bcbtn').click(function(){
		//whb 添加是否专享标
		 $('#isExclus').val($('input[name="isExclusRadio"]:checked').val());
	      if(flag){  
		       flag = false;
		       $('#form').submit();
	       }
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
	  $('#sysimg img').click(function(){
	      $('#imgPath').val($(this).attr('src'));
	      $('#img').attr('src',$(this).attr('src'));
	  });
	  $('#r_1').click(function(){
	      $('#tab_1').css('display','block');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','none');
	      $('#radioval').val('1');
	      img = "images/default-img.jpg";
	      $("#img").attr("src",img);
	  });
	  $('#r_2').click(function(){
	      var personalImg=$('#personalImg').val();
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','block');
	      $('#tab_3').css('display','none');
	      $('#radioval').val('2');
	      $('#img').attr('src',personalImg);
	      $('#imgPath').val(personalImg);
	  });
	  $('#r_3').click(function(){
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','block');
	      $('#radioval').val('3');
	      img = "images/default-img.jpg";
	      $("#img").attr("src",img);
	  });
      switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=borrow&d='+timenow);
};				     
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
</script>


<script>
$(function(){
$('input[name="excitationType"]').click(function(){
	    if($(this).val() == 2){
	       $('#sum').removeClass('gray');
	       $('#sum').removeAttr('disabled');
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	    }else if($(this).val() == 3){
	       $('#sumRate').removeClass('gray');
	       $('#sumRate').removeAttr('disabled');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
	       $('#sum').val('');
	    }else{
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
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
});
</script>
</body>
</html>