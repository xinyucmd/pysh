<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<form id="form" action="addBorrowSeconds.do" method="post">

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
         	<ul id="ul">
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
                    <span><s:textfield name="paramMap.title" cssClass="inp280"/></span>
        <span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>借款图片：</span>
                     <s:hidden name="paramMap.imgPath" id="imgPath"/>
                    <input type="hidden" id="personalImg" value="${user.personalHead}"/>
    <span><input type="radio" name="radio" id="r_1" checked="checked" value="1" />
      上传借款图片 
        <input type="radio" name="radio" id="r_2" value="2" />
        使用用户头像 
        <input type="radio" name="radio" id="r_3" value="3" />
        <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}"/>
        使用系统头像</span><span class="fred"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span>&nbsp;
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
                    <img id="img" src="" width="62" height="62"/>
                </li>
                <li>
                    <span>&nbsp;</span>
                    （推荐使用您的生活近照，或其他与借款用途相关的图片，有助增加借款成功几率。严禁使用他人照片）
                </li>
                <li>
                    <span>借款标的：</span><span id="typeName">${session.typeName}</span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>借款目的：</span>
                    <s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel" listKey="selectValue" listValue="selectName"  value="1"></s:select>
                </li>
                <li>
                    <span><em class="fred">* </em>还款方式：</span>
                    	秒还<s:hidden name="paramMap.borrowWay" value="3"/>
                </li>
                <li>
                    <span><em class="fred">* </em>借款总额：</span>
                    <span><s:textfield name="paramMap.amount" cssClass="test"/></span>
    <span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>年利率：</span>
                    <span><s:textfield name="paramMap.annualRate" cssClass="test"/>%</span>
    <span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span>&nbsp;
                </li>
                <s:if test="#request.subscribeStatus!=1">
                <li>
                    <span><em class="fred">* </em>最低投标金额：</span>
                    <span><s:select list="borrowMinAmountList" name="paramMap.minTenderedSum" cssClass="sel" listKey="key" listValue="value"  ></s:select></span> 
    <span class="fred"><s:fielderror fieldName="paramMap['minTenderedSum']"></s:fielderror></span>&nbsp;
                </li>
                <li>
                    <span><em class="fred">* </em>最多投标金额：</span>
                    <s:select list="borrowMaxAmountList" name="paramMap.maxTenderedSum" cssClass="sel" listKey="key" listValue="value" headerKey="" headerValue="没有限制" ></s:select>
                </li>
                </s:if><s:else>
                	<li>
                    <span><em class="fred">* </em>最小认购单位：</span>
                    <span><s:textfield name="paramMap.subscribe"  cssClass="test"/>元
    <span class="fred"><s:fielderror fieldName="paramMap['subscribe']"></s:fielderror></span>
    	<input name="subscribeStatus" type="hidden" id="subscribeStatus"  value="${subscribeStatus}"/></span>&nbsp;
                </li>
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
                    <s:textarea name="paramMap.detail" cssClass="tre"/>
                </li>	
                <li><s:fielderror fieldName="paramMap['detail']"></s:fielderror></li>	
             </ul>
              <s:if test="#request.award_status==1">
             <h5>投标奖励</h5>
             <ul class="jk_fbdk clearfix" style="padding-left:80px;">
                <li><input type="hidden" name="award_status" value="${award_status }" /><input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></li>
                <li><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" />不设置奖励</li>
                <li><input type="radio" name="excitationType" id="radio_2" value="2" />固定总额按投标比例分配奖励<input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray" disabled="disabled"/>
      元<span class="fred"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></span></li>
      <li><input type="radio" name="excitationType" id="radio_3" value="3" />
      借款总额百分比分配奖励
      <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="inp100x gray" disabled="disabled"/>
      %<span class="fred"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></span></li>
             </ul>
             </s:if>
 			<s:if test="#request.password_status == 1">
             	<h5>设置投标密码</h5>
	             <ul class="jk_fbdk clearfix">
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
             </ul>           
         </div>		
      </div>
      <!--右内容 结束-->	 
</div>
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
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
 
	 if($('#imgPath').val()==""){
		 $('#imgPath').val("images/default-img.jpg");
		 }
	 $('#img').attr('src',$('#imgPath').val());
	 $('#bcbtn').click(function(){
		 
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
</body>
</html>